package com.example.zika.contactapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    public static List<ContactPeople> mList;
    private ListView mContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();

        loadContacts();
    }

    private void initComponent() {

        mContactList = findViewById(R.id.listviewContacts);

        mContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Ime: " + mList.get(position).name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, SingleContactActivity.class);
                intent.putExtra("item_position", position);
                startActivity(intent);

            }
        });
    }

    private void loadContacts() {
        // Checking the Android SDK version and if permision is granted (minimum sdk version 23)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {

            mList = getContactsDisplay();

            List<String> names = new ArrayList<>();

            for (int i = 0; i < mList.size(); i++) {

                String fName = mList.get(i).name;

                names.add(fName);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
            mContactList.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Permission is OK
                loadContacts();

            } else {
                Toast.makeText(this, R.string.permission_not_granted, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<ContactPeople> getContactsDisplay() {

        List<ContactPeople> contacts = new ArrayList<>();

        ContentResolver resolver = getContentResolver();

        // Making cursor for all contacts
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        // Check if cursor is NULL. If TRUE - Move the cursor to first.
        if (cursor != null && cursor.moveToFirst()) {

                do {

                    ContactPeople item = new ContactPeople();

                    item.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    item.photoURI = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    item.phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                    contacts.add(item);

                } while (cursor.moveToNext());

                cursor.close();

        }
        return contacts;
    }
}