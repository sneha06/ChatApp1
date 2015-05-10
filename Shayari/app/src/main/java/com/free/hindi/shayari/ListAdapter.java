package com.free.hindi.shayari;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by sneha on 28/3/15.
 */
public class ListAdapter extends ParseQueryAdapter<ParseObject> {

    int i=0;
    String  img;
     protected String itemId;
    public ListAdapter(Context context, QueryFactory<ParseObject> item, String mimage) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {

                ParseQuery query = new ParseQuery("Love");
                return query;
            }
        });
        img=mimage;
    }


    public View getItemView(final ParseObject parseObject,View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), com.free.hindi.shayari.R.layout.item_list_shayari, null);
        }

        super.getItemView(parseObject, v, parent);

        ImageView mealImage = (ImageView) v.findViewById(com.free.hindi.shayari.R.id.favorite_star);

        if (img.equals("bewafa")) {
            mealImage.setImageResource(com.free.hindi.shayari.R.drawable.bewafa);
        }if(img.equals("dard")){
            mealImage.setImageResource(com.free.hindi.shayari.R.drawable.dard);
        }if(img.equals("love")){
            mealImage.setImageResource(com.free.hindi.shayari.R.drawable.love);
        }if(img.equals("romantic")){
            mealImage.setImageResource(com.free.hindi.shayari.R.drawable.romantic);
        }



        TextView titleTextView = (TextView) v.findViewById(com.free.hindi.shayari.R.id.text1);
        titleTextView.setText(parseObject.getString("shayari_name"));

        itemId = parseObject.getObjectId();
        System.out.println(itemId);
        return v;
    }


}
