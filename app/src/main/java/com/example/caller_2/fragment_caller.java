package com.example.caller_2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_caller#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_caller extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_caller() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_caller.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_caller newInstance(String param1, String param2) {
        fragment_caller fragment = new fragment_caller();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
RecyclerView rc;
    history_table db_history;

    history_adabter adabter;
    EditText serch;
    ImageButton Call_button;
    String numper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_caller, container, false);
        rc =v.findViewById(R.id.fragment_caller_rc);
        Call_button =v.findViewById(R.id.caller_image_button);

        db_history =history_table.get_instance(getContext());
        db_history.open();
        ArrayList<history> p=new ArrayList<>();
        /*p.add(new history(1,"mas","01095623040","10"));
        p.add(new history(2,"om","unknown","11"));
        p.add(new history(3,"bas","01012103040","9"));*/

        //db_history.insert(new history(1,"mas","01095623040","1"));
        //db_history.insert(new history(2,"om","01095623040","5"));

        serch =v.findViewById(R.id.editTextPhone_edit_activity);
        serch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                numper = s+"";
                Toast.makeText(getContext(),numper,Toast.LENGTH_SHORT).show();
                db_history.open();
                ArrayList<history> c =db_history.select_by_phone(""+s);

                adabter.setHis(c);
                adabter.notifyDataSetChanged();
                db_history.close();
            }
        });

        Call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numper=serch.getText().toString();
                callPhoneNumber();
            }
        });

        p=db_history.show_all();
        db_history.close();
        adabter=new history_adabter(p, new history_clicked() {
            @Override
            public void OnhistoryClickListner(String Phone) {
                numper=Phone;
                callPhoneNumber();
            }
        }, new OnLongClickListener_event() {
            @Override
            public void onLongClick(int id) {
               /* DialogFragment fragment =DialogFragment.newInstance("Info","Are you shore you want to delete this ?",
                        R.drawable.ic_baseline_info_24);
                idd=id;
                Toast.makeText(getContext(),id+"",Toast.LENGTH_SHORT).show();
                fragment.show(((AppCompatActivity) getContext()).getSupportFragmentManager(),null);*/
                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete this entry?");

                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db_history.open();
                                db_history.delete(id);
                                ArrayList<history> c =db_history.show_all();

                                adabter.setHis(c);
                                adabter.notifyDataSetChanged();
                                db_history.close();
                            }
                        }    );

                        // A null listener allows the button to dismiss the dialog and take no further action.
                /*alert.setNegativeButton(android.R.string.no,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getContext(),"negative y "+id,Toast.LENGTH_SHORT).show();

                    }
                } );*/
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.show();
            }
        });
        RecyclerView.LayoutManager lm =new LinearLayoutManager(getContext());

        rc.setHasFixedSize(true);
        rc.setLayoutManager(lm);
        rc.setAdapter(adabter);

        return v;
    }

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + numper));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + numper));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}