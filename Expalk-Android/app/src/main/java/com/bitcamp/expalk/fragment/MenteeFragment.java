package com.bitcamp.expalk.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitcamp.expalk.R;
import com.bitcamp.expalk.data.MentorData;
import com.bitcamp.expalk.data.UserData;
import com.bitcamp.expalk.dialog.DialogTechStack;
import com.bitcamp.expalk.dialog.DialogProgress;
import com.bitcamp.expalk.live.activities.LiveActivity;
import com.bitcamp.expalk.util.Constants;
import com.bitcamp.expalk.util.ToastHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MenteeFragment extends Fragment implements DialogTechStack.OnTechStackSelected {

    TextView txtToken,txtStart;
    SharedPreferences sharedPreferences;
    ArrayList<MentorData> userList = new ArrayList<>();

    boolean isStop = false;
    boolean isSearching = false;
    String techStack = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mentee, container, false);

        txtToken = view.findViewById(R.id.txtSpeakerToken);
        txtStart = view.findViewById(R.id.txtSpeakerStart);
        sharedPreferences = getActivity().getSharedPreferences(Constants.MY_PREF, Context.MODE_PRIVATE);

        txtToken.setText(String.valueOf(sharedPreferences.getLong(Constants.TOKEN,-1)));

        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getLong(Constants.TOKEN,0) == 0){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialogNew);
                    final View customLayout
                            = getLayoutInflater()
                            .inflate(R.layout.custom_alert_dialog, null);
                    Button btnDialog = customLayout.findViewById(R.id.btnDialog);
                    ImageView imgDialog = customLayout.findViewById(R.id.imgDialog);
                    imgDialog.setImageDrawable(getResources().getDrawable(R.drawable.emo_sad));
                    builder.setView(customLayout);
                    builder.setTitle("Oppss!");
                    builder.setMessage("You don't have enough tokens to connect to a mentor! You can earn tokens by mentoring to someone.");
                    final AlertDialog alertDialog = builder.create();
                    //alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    alertDialog.show();
                    btnDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }else {
                    DialogFragment dialogFragment = new DialogTechStack(MenteeFragment.this, 2);
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "Dialog Mood");
                }
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(isSearching){
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Online/Listener/"+techStack+"/"+sharedPreferences.getString(Constants.UID,"guest"));
            databaseReference.removeValue();

            onDestroy();
        }
    }

    @Override
    public void onTechStackSelected(String techStack) {
        this.techStack = techStack;
        DialogProgress dialogProgress = new DialogProgress("Searching mentors...");
        dialogProgress.setCancelable(false);
        dialogProgress.show(getActivity().getSupportFragmentManager(),"Dialog Progress");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Online/Mentor/"+techStack+"/"+sharedPreferences.getString(Constants.UID,"guest"));

        MentorData mentorData = new MentorData(sharedPreferences.getString(Constants.UID,"guest"),"0");

        databaseReference.setValue(mentorData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful() && !isStop){
                    isSearching = true;
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!isStop){
                                MentorData listenerUpdate = snapshot.getValue(MentorData.class);
                                if(!listenerUpdate.getStatus().equals("0")){
                                    isStop = true;
                                    dialogProgress.dismiss();
                                    sharedPreferences.edit().putString(Constants.CONNECT_TO,listenerUpdate.getUid()).apply();
                                    sharedPreferences.edit().putString(Constants.CONNECTED_TO, listenerUpdate.getStatus()).apply();

                                    Intent intent = new Intent(getActivity(), LiveActivity.class);
                                    intent.putExtra("allot_time","5");
                                    startActivity(intent);

                                    databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                isStop = false;
                                            }
                                        }
                                    });

                                    DatabaseReference databaseUpdateToken = firebaseDatabase.getReference("Users/"+sharedPreferences.getString(Constants.UID,"guest"));
                                    databaseUpdateToken.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            UserData listenerToken = snapshot.getValue(UserData.class);
                                            long token = listenerToken.getToken();
                                            token--;
                                            databaseUpdateToken.child("token").setValue(token);
                                            sharedPreferences.edit().putLong(Constants.TOKEN,token).apply();
                                            txtToken.setText(String.valueOf(token));
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            isStop = true;
                            databaseReference.removeValue();
                            dialogProgress.dismiss();
                            new ToastHelper().makeToast(getActivity(),"Something went wrong! Please try again later.", Toast.LENGTH_LONG);
                        }
                    });
                }else{
                    dialogProgress.dismiss();
                    new ToastHelper().makeToast(getActivity(),"Something went wrong! Please try again later.", Toast.LENGTH_LONG);
                }
            }
        });
    }
}