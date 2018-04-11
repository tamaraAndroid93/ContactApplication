package com.example.zika.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

//FRAGMENT
public class SingleContactActivity extends AppCompatActivity {
    private ImageView mPhoto;
    private TextView mName;
    private TextView mPhone;
    private ContactPeople item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact);

        Intent intent = getIntent();
        item = (ContactPeople) intent.getSerializableExtra("item");

        initComponent();
        loadData(item);
    }

    private void loadData(ContactPeople item) {
        // metoda private void loadPicture() {
        Picasso.with(getApplicationContext())
                .load(item.photoURI)
                .into(mPhoto);
    }

        mPhone.setText(item.phone);
        mName.setText(item.name);

        mPhone.setContentDescription(item.phone);
        mName.setContentDescription(item.name);
    }

    private void initComponent() {

        mPhone = findViewById(R.id.textViewPhone);
        mPhoto = findViewById(R.id.imageViewPhoto);
        mName = findViewById(R.id.textViewName);
    }
}
