package com.android.sneha.chatapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.ChatAdapter;


public class ChatActivity extends ActionBarActivity {


    protected List<ParseObject> mMessages;
    protected List<ParseObject> senderNames;

    Button sendButton;
    EditText sendtext;
    String sendMessage;
    ListView userList;

    protected static String recipientId;
    public static String userName;
     static String f;
    static String d;
    List<String> smessage;

    String[] k = {"s"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        smessage = new ArrayList<String>();
        recipientId = getIntent().getStringExtra("ruid");
        userName = getIntent().getStringExtra("uName");
        //recipientId = getIntent().getStringExtra("ruid");
        userList = (ListView) findViewById(R.id.list);
        if(d != null){
            smessage.add(d);
            ChatAdapter adapter = new ChatAdapter(ChatActivity.this,k,smessage,f);
            userList.setAdapter(adapter);

//            AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
//            builder.setTitle("Message").setMessage(d.toString())
//                    .setPositiveButton(android.R.string.ok, null);
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
        }

//        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
//        installation.put("senderId",ParseUser.getCurrentUser().getObjectId());
//        installation.saveInBackground();

        sendtext = (EditText) findViewById(R.id.sendText);

        sendButton = (Button) findViewById(R.id.sendButton);
                sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f = null;
                String text = sendtext.getText().toString().trim();
                JSONObject object = new JSONObject();
                try {
                    object.put("alert",text);
                    object.put("senderId", ParseUser.getCurrentUser().getObjectId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(!text.isEmpty()) {
//                    ParseQuery<ParseInstallation> query = ParseInstallation.getQuery();
//                    query.whereEqualTo("senderId", ParseUser.getCurrentUser().getObjectId());
                    ParsePush.subscribeInBackground(ParseUser.getCurrentUser().getObjectId()+"_"+recipientId );
                    System.out.println("recipientId chatActivity s" + recipientId);
                    ParsePush push = new ParsePush();
                    //push.setQuery(query);
                    push.setChannel(recipientId+"_"+ParseUser.getCurrentUser().getObjectId());

                   // push.setMessage(text);
                    push.setData(object);
                    push.sendInBackground();
                    sendtext.setText("");
                    smessage.add(text);
                    ChatAdapter adapter = new ChatAdapter(ChatActivity.this,k,smessage,f);
                    userList.setAdapter(adapter);

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
                Intent intent = new Intent(ChatActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected static void showMessage(String s,String a){
        d = s.toString();
        f=a;
    }
}
