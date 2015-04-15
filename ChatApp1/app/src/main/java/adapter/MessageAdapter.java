package adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by sneha on 12/4/15.
 */
public class MessageAdapter extends ArrayAdapter<String> {
    public MessageAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }
}
