package com.android.sneha.sms;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
                cursor.getCount();
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = " ";

                // msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);

                String s = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String a = cursor.getString((cursor.getColumnIndexOrThrow("body")));
                if(a.contains("Mobile")) {
                    System.out.println(s);
                    System.out.println(a);

                }// use msgData
            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
        }

        //Read sms from inbox
//                    //Get all column name and related String
//                    System.out.println(cursor.getColumnName(idx));
//                    System.out.println(cursor.getString(idx));
//                    String s = cursor.getString(cursor.getColumnIndexOrThrow("address"));
//                    String a = cursor.getString((cursor.getColumnIndexOrThrow("body")));
        //  Read sms code end
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
