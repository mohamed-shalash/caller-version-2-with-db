package com.example.caller_2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogFragment extends androidx.fragment.app.DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Title = "TITLE";
    private static final String ARG_Message = "Message";
    private static final String ARG_icon = "icon";

    // TODO: Rename and change types of parameters
    private String title;
    private String message;
    private int icon;

    public DialogFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DialogFragment newInstance(String title, String message,int icon) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_Title, title);
        args.putString(ARG_Message, message);
        args.putInt(ARG_icon, icon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_Title);
            message = getArguments().getString(ARG_Message);
            icon = getArguments().getInt(ARG_icon);
        }
    }

    private OnpositiveClickListener onpositiveClickListener;
    private OnNegativeClickListener onNegativeClickListener;
    private OnNaturalClickListener onNaturalClickListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnpositiveClickListener){
            onpositiveClickListener= (OnpositiveClickListener) context;
        }
        else throw new RuntimeException("not instance of positive Listener");

        if (context instanceof OnNegativeClickListener){
            onNegativeClickListener= (OnNegativeClickListener) context;
        }
        else throw new RuntimeException("not instance of Negative Listener");

        if (context instanceof OnNaturalClickListener){
            onNaturalClickListener= (OnNaturalClickListener) context;
        }
        else throw new RuntimeException("not instance of Natural Listener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onpositiveClickListener=null;
        onNegativeClickListener=null;
        onNaturalClickListener=null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setIcon(icon);
        alertDialog.setTitle(title);
        alertDialog.setIcon(icon);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onpositiveClickListener.onpositiveClick();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onNegativeClickListener.onNegativeClick();
            }
        });
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  Toast.makeText(getContext(),"Cancel ",Toast.LENGTH_SHORT).show();
                onNaturalClickListener.onNaturalClick();
            }
        });
        return alertDialog.create();
    }

    interface OnpositiveClickListener{
        void onpositiveClick();
    }
    interface OnNegativeClickListener{
        void onNegativeClick();
    }
    interface OnNaturalClickListener{
        void onNaturalClick();
    }

}