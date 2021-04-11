package com.bitcamp.expalk.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitcamp.expalk.R;
import com.bitcamp.expalk.util.ToastHelper;


public class DialogTechStack extends DialogFragment {

    String techStack = "";
    CardView cvJS, cvDatabase, cvHardware, cvMobileDev;
    Button btnTech;

    OnTechStackSelected onTechStackSelected;

    TextView txtJS, txtDatabase, txtHardware, txtMobileDev;

    CardView cvNode, cvAngular, cvSql, cvNoSql, cvAndroid, cvSwift, cvArduino, cvRP;
    TextView txtNode, txtAngular, txtSql, txtNoSql, txtSwift, txtArduino, txtRP, txtAndroid;
    LinearLayout llMentor, llMentee;
    
    int mode = 0;

    public DialogTechStack(OnTechStackSelected onTechStackSelected, int mode){
        this.onTechStackSelected = onTechStackSelected;
        this.mode = mode;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_progress_dialog));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_tech_stack, container, false);

        cvJS = view.findViewById(R.id.cvJS);
        cvDatabase = view.findViewById(R.id.cvDatabase);
        cvHardware = view.findViewById(R.id.cvHardware);
        cvMobileDev = view.findViewById(R.id.cvMobileDev);
        txtJS = view.findViewById(R.id.txtJS);
        txtDatabase = view.findViewById(R.id.txtDatabase);
        txtHardware = view.findViewById(R.id.txtHardware);
        txtMobileDev = view.findViewById(R.id.txtMobileDev);
        btnTech = view.findViewById(R.id.btnSelectTech);

        cvNode = view.findViewById(R.id.cvNode);
        cvAngular = view.findViewById(R.id.cvAngular);
        cvSql = view.findViewById(R.id.cvSql);
        cvNoSql = view.findViewById(R.id.cvNoSql);
        cvAndroid = view.findViewById(R.id.cvAndroid);
        cvSwift = view.findViewById(R.id.cvSwift);
        cvArduino = view.findViewById(R.id.cvArduino);
        cvRP = view.findViewById(R.id.cvRP);
        txtNode = view.findViewById(R.id.txtNode);
        txtAngular = view.findViewById(R.id.txtAngular);
        txtSql = view.findViewById(R.id.txtSql);
        txtNoSql = view.findViewById(R.id.txtNoSql);
        txtAndroid = view.findViewById(R.id.txtAndroid);
        txtSwift = view.findViewById(R.id.txtSwift);
        txtArduino = view.findViewById(R.id.txtArduino);
        txtRP = view.findViewById(R.id.txtRP);
        llMentor = view.findViewById(R.id.llTechMentor);
        llMentee = view.findViewById(R.id.llTechMentee);
        

        if(mode == 1) {
            llMentor.setVisibility(View.VISIBLE);
            llMentee.setVisibility(View.GONE);
            unselectCard(cvJS, txtJS);
            unselectCard(cvDatabase, txtDatabase);
            unselectCard(cvMobileDev, txtMobileDev);
            unselectCard(cvHardware, txtHardware);
        }else {
            llMentor.setVisibility(View.GONE);
            llMentee.setVisibility(View.VISIBLE);
            unselectCard(cvNode, txtNode);
            unselectCard(cvAngular, txtAngular);
            unselectCard(cvSql, txtSql);
            unselectCard(cvNoSql, txtNoSql);
            unselectCard(cvArduino, txtArduino);
            unselectCard(cvRP, txtRP);
            unselectCard(cvAndroid, txtAndroid);
            unselectCard(cvSwift, txtSwift);
        }

        cvNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCard(cvNode, txtNode);
                unselectCard(cvAngular, txtAngular);
                unselectCard(cvSql, txtSql);
                unselectCard(cvNoSql, txtNoSql);
                unselectCard(cvArduino, txtArduino);
                unselectCard(cvRP, txtRP);
                unselectCard(cvAndroid, txtAndroid);
                unselectCard(cvSwift, txtSwift);
            }
        });

        cvAngular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectCard(cvNode, txtNode);
                selectCard(cvAngular, txtAngular);
                unselectCard(cvSql, txtSql);
                unselectCard(cvNoSql, txtNoSql);
                unselectCard(cvArduino, txtArduino);
                unselectCard(cvRP, txtRP);
                unselectCard(cvAndroid, txtAndroid);
                unselectCard(cvSwift, txtSwift);
            }
        });

        cvSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectCard(cvNode, txtNode);
                unselectCard(cvAngular, txtAngular);
                selectCard(cvSql, txtSql);
                unselectCard(cvNoSql, txtNoSql);
                unselectCard(cvArduino, txtArduino);
                unselectCard(cvRP, txtRP);
                unselectCard(cvAndroid, txtAndroid);
                unselectCard(cvSwift, txtSwift);
            }
        });

        cvNoSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectCard(cvNode, txtNode);
                unselectCard(cvAngular, txtAngular);
                unselectCard(cvSql, txtSql);
                selectCard(cvNoSql, txtNoSql);
                unselectCard(cvArduino, txtArduino);
                unselectCard(cvRP, txtRP);
                unselectCard(cvAndroid, txtAndroid);
                unselectCard(cvSwift, txtSwift);
            }
        });

        cvArduino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectCard(cvNode, txtNode);
                unselectCard(cvAngular, txtAngular);
                unselectCard(cvSql, txtSql);
                unselectCard(cvNoSql, txtNoSql);
                selectCard(cvArduino, txtArduino);
                unselectCard(cvRP, txtRP);
                unselectCard(cvAndroid, txtAndroid);
                unselectCard(cvSwift, txtSwift);
            }
        });

        cvRP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectCard(cvNode, txtNode);
                unselectCard(cvAngular, txtAngular);
                unselectCard(cvSql, txtSql);
                unselectCard(cvNoSql, txtNoSql);
                unselectCard(cvArduino, txtArduino);
                selectCard(cvRP, txtRP);
                unselectCard(cvAndroid, txtAndroid);
                unselectCard(cvSwift, txtSwift);
            }
        });

        cvAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectCard(cvNode, txtNode);
                unselectCard(cvAngular, txtAngular);
                unselectCard(cvSql, txtSql);
                unselectCard(cvNoSql, txtNoSql);
                unselectCard(cvArduino, txtArduino);
                unselectCard(cvRP, txtRP);
                selectCard(cvAndroid, txtAndroid);
                unselectCard(cvSwift, txtSwift);
            }
        });

        cvSwift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectCard(cvNode, txtNode);
                unselectCard(cvAngular, txtAngular);
                unselectCard(cvSql, txtSql);
                unselectCard(cvNoSql, txtNoSql);
                unselectCard(cvArduino, txtArduino);
                unselectCard(cvRP, txtRP);
                unselectCard(cvAndroid, txtAndroid);
                selectCard(cvSwift, txtSwift);
            }
        });

        cvJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCard(cvJS, txtJS);
                unselectCard(cvDatabase, txtDatabase);
                unselectCard(cvMobileDev, txtMobileDev);
                unselectCard(cvHardware, txtHardware);
            }
        });

        cvDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectCard(cvJS, txtJS);
                selectCard(cvDatabase, txtDatabase);
                unselectCard(cvMobileDev, txtMobileDev);
                unselectCard(cvHardware, txtHardware);
            }
        });

        cvMobileDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectCard(cvJS, txtJS);
                unselectCard(cvDatabase, txtDatabase);
                selectCard(cvMobileDev, txtMobileDev);
                unselectCard(cvHardware, txtHardware);
            }
        });

        cvHardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unselectCard(cvJS, txtJS);
                unselectCard(cvDatabase, txtDatabase);
                unselectCard(cvMobileDev, txtMobileDev);
                selectCard(cvHardware, txtHardware);
            }
        });

        btnTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(techStack.isEmpty()){
                    new ToastHelper().makeToast(getActivity(),"Select tech stack to proceed!", Toast.LENGTH_LONG);
                }else{
                    dismiss();
                    onTechStackSelected.onTechStackSelected(techStack);
                }
            }
        });

        return view;
    }

    private void unselectCard(CardView cardView, TextView textView){
        cardView.setBackgroundColor(getActivity().getResources().getColor(R.color.white, getActivity().getTheme()));
        textView.setTextColor(getActivity().getResources().getColor(R.color.purpleLight, getActivity().getTheme()));
    }

    private void selectCard(CardView cardView, TextView textView){
        techStack = cardView.getTag().toString();
        cardView.setBackgroundColor(getActivity().getResources().getColor(R.color.purpleLight, getActivity().getTheme()));
        textView.setTextColor(getActivity().getResources().getColor(R.color.white, getActivity().getTheme()));
    }

    public interface OnTechStackSelected{
        void onTechStackSelected(String techStack);
    }
}