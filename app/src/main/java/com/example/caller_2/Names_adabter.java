package com.example.caller_2;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Names_adabter extends RecyclerView.Adapter<Names_adabter.holder> {

    ArrayList<person> people;
    person_click click;

    public ArrayList<person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<person> people) {
        this.people = people;
    }

    public Names_adabter(ArrayList<person> people, person_click click) {
        this.people = people;
        this.click =click;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.people_names_cards,null,false);
        holder hold =new holder(v);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        person p =people.get(position);
        holder.Name.setText(p.getName());
        holder.Phone.setText(p.getPhone());
        holder.Age.setText(p.getPirth_day()+"");
        if (p.getImage()!=null&&p.getImage().length()!=0)
            holder.image.setImageURI(Uri.parse(p.getImage()));
        else {
            holder.image.setImageResource(R.drawable.person);
        }
holder.Name.setTag(p.getId());
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        TextView Name,Phone,Age;
        ImageView image;
        public holder(@NonNull View itemView) {
            super(itemView);
            Name= itemView.findViewById(R.id.people_names_cards_Name);
            Phone= itemView.findViewById(R.id.people_names_cards_Phone);
            Age= itemView.findViewById(R.id.people_names_cards_age);
            image= itemView.findViewById(R.id.people_names_cards_Image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) Name.getTag();
                    click.onPersonClickListenr(id);
                }
            });
        }
    }
}


