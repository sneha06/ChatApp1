package com.free.hindi.shayari;

import com.parse.ParseObject;

/**
 * Created by sneha on 28/3/15.
 */
public class Love extends ParseObject {

    public Love(){

    }


    public String getString() {
        return getString("shayari_name");
    }

    public void setString(String shayari_name) {
        put("shayari_name", shayari_name);
    }
}
