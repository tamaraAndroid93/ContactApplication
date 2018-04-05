package com.example.zika.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleContactActivity extends AppCompatActivity {
    private ImageView mPhoto;
    private TextView mName;
    private TextView mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact);

        Intent intent = getIntent();
        int position = intent.getIntExtra("item_position", 0);

        initComponent();
        loadData(position);
    }

    private void loadData(int position) {
        Picasso.with(getApplicationContext())
                .load(MainActivity.mList.get(position).photoURI)
                .into(mPhoto);

        mPhone.setText(MainActivity.mList.get(position).phone);
        mName.setText(MainActivity.mList.get(position).name);

        mPhone.setContentDescription(MainActivity.mList.get(position).phone);
        mName.setContentDescription(MainActivity.mList.get(position).name);
    }

    private void initComponent() {

        mPhone = findViewById(R.id.textViewPhone);
        mPhoto = findViewById(R.id.imageViewPhoto);
        mName = findViewById(R.id.textViewName);
    }
}
