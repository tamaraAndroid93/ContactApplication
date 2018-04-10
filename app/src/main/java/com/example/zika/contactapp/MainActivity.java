package com.example.zika.contactapp;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQ = 100;

    private RecyclerView mRecyclerView;
    public static List<ContactPeople> mList = new ArrayList<>();

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        loadContacts();
    }

    private void initComponent() {
        mRecyclerView = findViewById(R.id.recyclerviewContacts);
    }

    private void loadContacts() {
        // Checking the Android SDK version and if permision is granted (minimum sdk version 23)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQ);

        } else {

            //Add contacts to set and remove duplicates
            Set<ContactPeople> tempSet = new HashSet<ContactPeople>();
            tempSet.addAll(getContactsDisplay());

            //add contacts to arraylis non duplicates and move it to adapter
            mList.addAll(tempSet);

            if (mList.size() > 0) {
                Collections.sort(mList, new Comparator<ContactPeople>() {
                    @Override
                    public int compare(final ContactPeople object1, final ContactPeople object2) {
                        return object1.getName().compareTo(object2.getName());
                    }
                });
            }

            mAdapter = new ContactsRecyclerAdapter(mList, getApplicationContext());
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PERMISSION_REQ) {

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

                    item.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))) ;
                    item.setPhone(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    item.setPhotoURI(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)));

                    contacts.add(item);

                } while (cursor.moveToNext());

                cursor.close();
        }
        return contacts;
    }

}
