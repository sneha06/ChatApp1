package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.sneha.chatapp1.R;

import java.util.List;

/**
 * Created by sneha on 9/4/15.
 */
public class ChatAdapter extends ArrayAdapter<String> {

    protected Context mContext;
    protected List<String> mSenderName;
    protected List<String> mMessage ;
    String  senderf;
    List<String> m;
    String s;
    public ChatAdapter(Context context, String[] mmessage,List<String> message,String f) {
        super(context, R.layout.listitem,mmessage);
        // super(context,R.layout.listitem,message);

        System.out.println("Chat Adapter called" + message);
        mContext = context;
        mMessage = message;
        senderf = f;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        System.out.println("getView Chat Adapter called"+mMessage);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.listitem, null);
            holder = new ViewHolder();
            holder.message = (TextView) convertView.findViewById(R.id.senderMessage);
            holder.myMessage = (TextView) convertView.findViewById(R.id.myMessage);
            holder.senderLayout = (LinearLayout) convertView.findViewById(R.id.senderLayout);
            holder.myLayout = (RelativeLayout) convertView.findViewById(R.id.myLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        s = mMessage.get(position).toString();

        if(senderf == null){
            //message send by me
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.senderLayout.setVisibility(View.GONE);
            System.out.println("My layout is visible");

            holder.myMessage.setText(mMessage.get(position).toString());
        }
        else {
            //send by others
            holder.myLayout.setVisibility(View.GONE);
            holder.senderLayout.setVisibility(View.VISIBLE);
            System.out.println("Sender layout is visible");

            holder.message.setText(mMessage.get(position).toString());
        }
        return convertView;
    }

    public static class ViewHolder {

        TextView message;
        TextView myMessage;
        LinearLayout senderLayout;
        RelativeLayout myLayout;
    }
}
