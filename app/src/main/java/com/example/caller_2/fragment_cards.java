package com.example.caller_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_cards#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_cards extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_cards() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_cards.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_cards newInstance(String param1, String param2) {
        fragment_cards fragment = new fragment_cards();
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
    person_table db_person;
    FloatingActionButton fab;
    Names_adabter adabter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_cards, container, false);
       rc=v.findViewById(R.id.fragment_names_cards_rc);

        ArrayList<person> p=new ArrayList<>();

        db_person=person_table.getinstance(getContext());
        db_person.open();

        p =db_person.show_all();



       adabter =new Names_adabter(p, new person_click() {
           @Override
           public void onPersonClickListenr(int id) {
              // Toast.makeText(getContext(),id+"",Toast.LENGTH_SHORT).show();
               Intent nextpage =new Intent(getContext(),edit_activity.class);
               nextpage.putExtra("key_id",id);
               startActivityForResult(nextpage,-1);
           }
       });
        RecyclerView.LayoutManager lm =new LinearLayoutManager(getContext());

        rc.setHasFixedSize(true);
        rc.setLayoutManager(lm);
        rc.setAdapter(adabter);
        db_person.close();

        fab =v.findViewById(R.id.fab_cards);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextpage =new Intent(getContext(),edit_activity.class);
                startActivityForResult(nextpage,-1);
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onResume() {
        db_person=person_table.getinstance(getContext());
        super.onResume();
            db_person.open();
            ArrayList<person> c =db_person.show_all();
            db_person.close();

            adabter.setPeople(c);
            adabter.notifyDataSetChanged();
    }
}