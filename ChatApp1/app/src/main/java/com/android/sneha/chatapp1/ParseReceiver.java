package com.android.sneha.chatapp1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sneha on 10/4/15.
 */
public class ParseReceiver extends ParsePushBroadcastReceiver {

   protected  static Bundle b;
   protected  static String s;
    protected  static String a;
    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);

        try {

            String action = intent.getAction();
            String channel = intent.getExtras().getString("com.parse.Channel");
            System.out.println("on pushopen" + action + channel);
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            System.out.println(json);
             s =json.getString("alert");
             a = json.getString("senderId");

              ChatActivity.showMessage(s, a);


//            System.out.println("on pushopen" + action + channel+json+"................"+json.getString("alert"));
//            Iterator itr = json.keys();
//            while(itr.hasNext()){
//                String key = (String) itr.next();
//                if(key.equals("String")){
//                    String msg = json.getString(key);
//                    System.out.println("on pushopen" + msg);
//                }
           // }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}