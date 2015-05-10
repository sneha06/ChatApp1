package com.android.sneha.chatapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class HomeActivity extends ActionBarActivity {

    ParseObject user;
    ListView userList;
    String[] mid;
    String[] username;
    protected List<ParseUser> mUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(ParseUser.getCurrentUser() == null){
            Intent intent = new Intent(HomeActivity.this,SignUpActivity.class);
            startActivity(intent);
        }

        userList = (ListView) findViewById(R.id.list);

        // ArrayAdapter adapter = new ArrayAdapter<String>(FriendsActivity.this,R.layout.abc_screen_simple);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this,ChatActivity.class);
                intent.putExtra("uName",username[position]);
                intent.putExtra("ruid",mid[position]);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByAscending("username");

        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                if(e == null){

                    mUsers = users;

                     username = new String[mUsers.size()];
                    mid = new String[mUsers.size()];
                    int i = 0;
                    for(ParseUser user :mUsers){

                        username[i] = user.getUsername();
                        mid[i] = users.get(i).getObjectId();

                        i++;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this,
                            android.R.layout.simple_list_item_1,username);
                    //userList.setListAdapter(adapter);
                    userList.setAdapter(adapter);


                }
                else{

                    Log.e("FriendsActivity", e.getMessage());

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            ParseUser.logOut();
            if(ParseUser.getCurrentUser() == null) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
