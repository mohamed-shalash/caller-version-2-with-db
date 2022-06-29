package com.example.caller_2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class history_adabter extends RecyclerView.Adapter<history_adabter.holder>  {

    ArrayList<history> his;
    history_clicked clicked;
    OnLongClickListener_event onlongclick;
    Context con;

    public ArrayList<history> getHis() {
        return his;
    }

    public void setHis(ArrayList<history> his) {
        this.his = his;
    }

    public history_adabter(ArrayList<history> his, history_clicked clicked, OnLongClickListener_event onlongclick) {
        this.his = his;
        this.clicked=clicked;
        this.onlongclick=onlongclick;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_adabter_layout,null,false);
        con =parent.getContext();
        holder hold=new holder(v);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        history historys=his.get(position);
        holder.Name.setText(historys.getName());
        holder.Phone.setText(historys.getPhone());
        holder.time.setText(historys.getTime());
        holder.Name.setTag(historys.getId()+"");
        holder.Phone.setTag(historys.getPhone());
    }

    @Override
    public int getItemCount() {
        return his.size();
    }

    int id;

    class holder extends RecyclerView.ViewHolder{
        TextView Name,Phone,time;
        public holder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.history_adbter_name);
            Phone=itemView.findViewById(R.id.history_adbter_phone);
            time=itemView.findViewById(R.id.history_adbter_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pho = (String) Phone.getTag();
                    clicked.OnhistoryClickListner(pho);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    id = Integer.parseInt( Name.getTag().toString());
                    //Toast.makeText(con,"Phone "+id,Toast.LENGTH_SHORT).show();


                    onlongclick.onLongClick(id);
                    return true;
                }
            });
        }
    }
}


interface OnLongClickListener_event{
    void onLongClick(int id);
}