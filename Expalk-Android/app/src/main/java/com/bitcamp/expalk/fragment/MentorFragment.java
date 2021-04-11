package com.bitcamp.expalk.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

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

public class MentorFragment extends Fragment implements DialogTechStack.OnTechStackSelected {
    TextView txtToken, txtStart;
    SharedPreferences sharedPreferences;
    ArrayList<MentorData> userList = new ArrayList<>();
    boolean isStop = false;
    boolean isSearching = false;
    String techStack = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mentor, container, false);

        txtToken = view.findViewById(R.id.txtListenerToken);
        txtStart = view.findViewById(R.id.txtListenerStart);
        sharedPreferences = getActivity().getSharedPreferences(Constants.MY_PREF, Context.MODE_PRIVATE);

        txtToken.setText(String.valueOf(sharedPreferences.getLong(Constants.TOKEN,-1)));

        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DialogTechStack(MentorFragment.this, 1);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "Dialog Tech Sack");
            }
        });

        return view;
    }


    @Override
    public void onTechStackSelected(String techStack) {
        DialogProgress dialogProgress = new DialogProgress("Searching mentee...");
        dialogProgress.setCancelable(false);
        dialogProgress.show(getActivity().getSupportFragmentManager(),"Dialog Progress");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Online/Mentor/"+techStack);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getChildrenCount() == 0){
                    dialogProgress.dismiss();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialogNew);
                    final View customLayout
                            = getLayoutInflater()
                            .inflate(R.layout.custom_alert_dialog, null);
                    Button btnDialog = customLayout.findViewById(R.id.btnDialog);
                    ImageView imgDialog = customLayout.findViewById(R.id.imgDialog);
                    imgDialog.setImageDrawable(getResources().getDrawable(R.drawable.emo_sad));
                    builder.setView(customLayout);
                    builder.setTitle("Sorry!");
                    builder.setMessage("No mentee found at the moment. Please try again later.");
                    final AlertDialog alertDialog = builder.create();
                    //alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    alertDialog.show();
                    btnDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }else{

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        MentorData uid = dataSnapshot.getValue(MentorData.class);
                        userList.add(uid);
                    }

                    Random random = new Random();
                    MentorData uid = userList.get(random.nextInt(userList.size()));

                    DatabaseReference databaseReference1 = firebaseDatabase.getReference("Online/Mentor/"+techStack+"/"+uid.getUid());
                    databaseReference1.child("status").setValue(sharedPreferences.getString(Constants.UID,"guest")).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("hello","Connecting to : "+uid);
                                dialogProgress.dismiss();
                                sharedPreferences.edit().putString(Constants.CONNECT_TO,uid.getUid()).apply();
                                sharedPreferences.edit().putString(Constants.CONNECTED_TO,sharedPreferences.getString(Constants.UID,"guest")).apply();
                                Intent intent = new Intent(getActivity(), LiveActivity.class);
                                intent.putExtra("allot_time","5");
                                startActivity(intent);

                                DatabaseReference databaseUpdateToken = firebaseDatabase.getReference("Users/"+sharedPreferences.getString(Constants.UID,"guest"));
                                databaseUpdateToken.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        UserData listenerToken = snapshot.getValue(UserData.class);
                                        long token = listenerToken.getToken();
                                        token = token + 5;
                                        databaseUpdateToken.child("token").setValue(token);
                                        sharedPreferences.edit().putLong(Constants.TOKEN,token).apply();
                                        txtToken.setText(String.valueOf(token));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialogProgress.dismiss();
                new ToastHelper().makeToast(getActivity(),"Something went wrong! Please try again later.", Toast.LENGTH_LONG);
            }
        });
    }
}