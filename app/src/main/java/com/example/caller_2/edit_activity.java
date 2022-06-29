package com.example.caller_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class edit_activity extends AppCompatActivity {
Toolbar toolbar;
int id;
    Uri Image_uri=null;
person_table db_person;

    ImageView imageView ;
    TextView name,pirth_day,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);
        toolbar =findViewById(R.id.edit_activity_toolbar);
        setSupportActionBar(toolbar);

        name=findViewById(R.id.name_edit_activity_text_view);
        pirth_day=findViewById(R.id.pirth_day_edit_activity_text_view);
        phone=findViewById(R.id.phone_edit_activity_text_view);
        imageView=findViewById(R.id.image_edit_activity_text_view);

        Intent intent =getIntent();
        id =intent.getIntExtra("key_id",-1);
        //Toast.makeText(getBaseContext(),id+"",Toast.LENGTH_SHORT).show();

        db_person=person_table.getinstance(getBaseContext());


        if (id ==-1){
            enable();
            clearfildes();
        }
        else{
            disable();
            person p;
            db_person.open();
            p=db_person.select_by_id(id);
            db_person.close();
            if (p!=null){
                fillfildes(p);
            }
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1,1011);
            }
        });

    }

    void disable(){
        name.setEnabled(false);
        pirth_day.setEnabled(false);
        phone.setEnabled(false);
        imageView.setEnabled(false);
    }
    void enable(){
        name.setEnabled(true);
        pirth_day.setEnabled(true);
        phone.setEnabled(true);
        imageView.setEnabled(true);
    }
    void fillfildes(person p){
        name.setText(p.getName());
        pirth_day.setText(p.getPirth_day());
        phone.setText(p.getPhone());
        if (p.getImage()!=null&&p.getImage().length()!=0)
            imageView.setImageURI(Uri.parse(p.getImage()));
    }
    void clearfildes(){
        name.setText("");
        pirth_day.setText("");
        phone.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_par,menu);
        MenuItem save = menu.findItem(R.id.save);
        MenuItem Edit = menu.findItem(R.id.edit);
        MenuItem Delete = menu.findItem(R.id.delete);
        MenuItem Call = menu.findItem(R.id.call);
        if (id==-1){
            save.setVisible(true);
            Edit.setVisible(false);
            Call.setVisible(false);
            Delete.setVisible(false);
        }
        else {
            save.setVisible(false);
            Edit.setVisible(true);
            Call.setVisible(true);
            Delete.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        db_person =person_table.getinstance(getBaseContext());


        switch (item.getItemId()){
            case R.id.save:{
                db_person.open();
                person p;
                if (Image_uri!=null)
                    p =new person(id,name.getText().toString(),pirth_day.getText().toString(),Image_uri.toString(),phone.getText().toString());
                else p=new person(id,name.getText().toString(),pirth_day.getText().toString(),null,phone.getText().toString());

                if (id ==-1){
                    boolean res=db_person.insert(p);
                    if (res)Toast.makeText(getBaseContext(),"data inserted successfully",Toast.LENGTH_LONG).show();
                    db_person.close();
                    finish();
                }else{
                    boolean res=db_person.update(p);
                    if (res)Toast.makeText(getBaseContext(),"data Updated successfully",Toast.LENGTH_LONG).show();
                    db_person.close();
                    finish();
                }

                return true;
            }
            case R.id.edit:{
                enable();
                MenuItem save =toolbar.getMenu().findItem(R.id.save);
                MenuItem Edit =toolbar.getMenu().findItem(R.id.edit);
                MenuItem Delete =toolbar.getMenu().findItem(R.id.delete);
                MenuItem Call =toolbar.getMenu().findItem(R.id.call);
                save.setVisible(true);
                Edit.setVisible(false);
                Call.setVisible(false);
                Delete.setVisible(false);
                return true;
            }
            case R.id.delete:{
                db_person.open();
                boolean res=db_person.delete(id);
                if (res)Toast.makeText(getBaseContext(),"data Deleted successfully",Toast.LENGTH_LONG).show();
                db_person.close();
                finish();

                return true;
            }
            case R.id.call:{
                history_table db_history;
                db_history =history_table.get_instance(getBaseContext());
                db_history.open();
                Date currentTime = Calendar.getInstance().getTime();
                db_history.insert(new history(name.getText().toString(),phone.getText().toString(),currentTime.toString()));
                callPhoneNumber();
                db_history.close();
                return true;
            }
        }

        return false;
    }

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(edit_activity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1011 &&resultCode==RESULT_OK){
            if (data !=null){
                Image_uri=data.getData();
                imageView.setImageURI(Image_uri);
            }
        }
    }
}