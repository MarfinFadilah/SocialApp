package com.example.fancester.link;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class contact extends AppCompatActivity {

    private List<People> Speople = new ArrayList<People>();
    private List<People> Tpeople = new ArrayList<People>();
    TextView nameText, statusText, phoneText;
    private Bitmap bitmap;
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        s = getIntent().getStringExtra("EXTRA_SESSION_ID");
        populatePeopleList();
        populateListView();
        switch (s) {
            case "MyUser":
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myuser);
                break;
            case "Ney":
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user1);
                break;
            case "Axel":
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user2);
                break;
            case "Rod":
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user3);
                break;
        }
        /*String msg = bitmap.getWidth() + ", "+ bitmap.getHeight();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();*/

    }

    //Add row
    private void populatePeopleList() {
        Speople.add(new People(s, "Using this app", R.drawable.ic_callout, "08123477891"));
        switch (s) {
            case "MyUser" :
                Speople.add(new People("Unknown", "Using this app", R.drawable.ic_skull, "08123422891"));
                break;
            case "Ney" :
                Speople.add(new People("Unknown", "Gabut", R.drawable.ic_skull, "08123422891"));
                break;
            case "Axel" :
                Speople.add(new People("Unknown", "Playing something adorable adorable ok check lines go go go go go go go ok?", R.drawable.ic_skull, "08123422891"));
                break;
            case "Rod" :
                Speople.add(new People("Unknown", "Playing", R.drawable.ic_skull, "08123422891"));
                break;
        }
        Speople.add(new People("Axel", "Playing", R.drawable.ic_skull, "08123422891"));
    }

    private void populateListView() {
        ArrayAdapter<People> adapter = new MyContactAdapter();
        ListView lst = (ListView) findViewById(R.id.peopleProfileView);
        lst.setLayoutParams(new RelativeLayout.LayoutParams(1550,1150));
        lst.setAdapter(adapter);
    }

    private class MyContactAdapter extends ArrayAdapter<People> {
        public MyContactAdapter() {
            super(contact.this, R.layout.item_contactheader, Speople);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup Parent) {
            View itemView = convertView;
            People currentPeople = Speople.get(position);
            if (position == 0) { //cannot be null
                itemView = getLayoutInflater().inflate(R.layout.item_contactheader, Parent, false);

                ImageView imgs = (ImageView) itemView.findViewById(R.id.icon2);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                imgs.setImageDrawable(roundedBitmapDrawable);

                nameText = (TextView) itemView.findViewById(R.id.textView2);
                nameText.setText(currentPeople.getNama());

                phoneText = (TextView) itemView.findViewById(R.id.textView3);
                phoneText.setText(currentPeople.getNumber());
            }
            else if (position == 1) {
                itemView = getLayoutInflater().inflate(R.layout.item_contactsatuts, Parent, false);
                statusText = (TextView) itemView.findViewById(R.id.fullstatus);
                statusText.setText(currentPeople.getStatus());
            }
            else {
                itemView = getLayoutInflater().inflate(R.layout.item_contactradio, Parent, false);
            }
            return itemView;

            // return super.getView(position, convertView, Parent);
        }
    }
}