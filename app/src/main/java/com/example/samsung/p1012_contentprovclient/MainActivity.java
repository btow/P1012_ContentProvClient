package com.example.samsung.p1012_contentprovclient;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private final Uri CONTACT_URI = Uri.parse(
            "content://com.example.samsung.p1011_contentprovider.AddressBook/contacts"
    );
    private final String
        TABLE_NAME = "name",
        TABLE_EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = getContentResolver().query(CONTACT_URI, null, null, null, null);
        startManagingCursor(cursor);

        String[] from = {"name", "email"};
        int[] to = {android.R.id.text1,android.R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_2, cursor, from, to
        );

        ListView lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(adapter);
    }

    public void onClickBtn(View view) {
        String message = "";
        ContentValues contentValues = new ContentValues();

        switch (view.getId()) {

            case R.id.btnInsert :
                contentValues.put(TABLE_NAME, "name 5");
                contentValues.put(TABLE_EMAIL, "email5@example.com");
                Uri newUri = getContentResolver().insert(CONTACT_URI, contentValues);
                message = "MainActivity onClickBtn(): insert, result Uri = " + newUri.toString();
                Messager.sendMessageToAllRecipients(this, message);
                break;
            case R.id.btnUpdate :
                contentValues.put(TABLE_NAME, "name 6");
                contentValues.put(TABLE_EMAIL, "email6@example.com");
                Uri uri = ContentUris.withAppendedId(CONTACT_URI, 2);
                int cnt = getContentResolver().update(uri, contentValues, null, null);
                message = "MainActivity onClickBtn(): update, count = " + cnt;
                Messager.sendMessageToAllRecipients(this, message);
                break;
            case R.id.btnDelete :
                Uri delUri = ContentUris.withAppendedId(CONTACT_URI, 3);
                int delCnt = getContentResolver().delete(delUri, null, null);
                message = "MainActivity onClickBtn(): delete, count = " + delCnt;
                Messager.sendMessageToAllRecipients(this, message);
                break;
            case R.id.btnError :
                Uri errorUri = Uri.parse(
                        "content://com.example.samsung.p1011_contentprovider.AddressBook/phones"
                );
                try {
                    Cursor cursor = getContentResolver().query(errorUri, null, null, null, null);
                } catch (Exception e) {
                    message = "MainActivity onClickBtn(): error: " + e.getClass() + " " + e.getMessage();
                    Messager.sendMessageToAllRecipients(this, message);
                }

        }

    }
}
