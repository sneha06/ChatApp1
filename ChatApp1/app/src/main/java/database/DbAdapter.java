package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.ChatAdapter;

/**
 * Created by sneha on 15/4/15.
 */
public class DbAdapter {

    DbHelper helper;
    public DbAdapter(Context context) {

        helper = new DbHelper(context);
    }


    public long insertData(String name, String senderId,String message){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DbHelper.NAME, name);
        contentValue.put(DbHelper.SENDERID, senderId);
        contentValue.put(DbHelper.MESSAGE, message);
        long id = db.insert(DbHelper.TABLE_NAME, null, contentValue);
        return id;

    }
    public String getAllData(Context context,ListView userList){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {helper.UID,helper.NAME,helper.SENDERID,helper.MESSAGE};
        Cursor cursor = db.query(helper.TABLE_NAME, columns, null, null, null,null,null);
        StringBuffer buffer = new StringBuffer();

        String[] k = {"s"};
        List<String> name = new ArrayList<String>();
        List<String> senderId = new ArrayList<String>();
        List<String> message = new ArrayList<String>();
        while (cursor.moveToNext()){

            int index1 = 	cursor.getColumnIndex(helper.UID);
            int index2 = cursor.getColumnIndex(helper.NAME);
            int index3 = cursor.getColumnIndex(helper.SENDERID);
            int index4 = cursor.getColumnIndex(helper.MESSAGE);

            int cid = cursor.getInt(index1);

             name.add(cursor.getString(index2));
            senderId.add(cursor.getString(index3)) ;
            message.add(cursor.getString(index4));
            //myList = new ArrayList<String>(Arrays.asList(message));
            buffer.append(cid+" "+name+" "+senderId+" "+message+"\n" );


        }
        ChatAdapter adapter = new ChatAdapter(context,k,message,senderId);
        userList.setAdapter(adapter);

        return buffer.toString();
    }

    static class DbHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "database";
        private static final String TABLE_NAME = "userName";
        private static final int DATABASE_VERSION = 4;
        private static final String UID = "_id";
        private static final String NAME = "Name";
        private static final String MESSAGE = "Message";
        private static final String SENDERID = "SenderId";



        private static final String CREATE= "CREATE TABLE "+ TABLE_NAME+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(255),"+SENDERID+" VARCHAR(255),"+MESSAGE+" VARCHAR(255))";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

        private Context context;

        /**
         * Create a helper object to create, open, and/or manage a database.
         * This method always returns very quickly.  The database is not actually
         * created or opened until one of {@link #getWritableDatabase} or
         * {@link #getReadableDatabase} is called.
         *  @param context to use to open or create the database
         *
         */
        public DbHelper(Context context) {
            super(context,DATABASE_NAME, null,DATABASE_VERSION);
            this.context = context;
        }

        /**
         * Called when the database is created for the first time. This is where the
         * creation of tables and the initial population of the tables should happen.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

        }

        /**
         * Called when the database needs to be upgraded. The implementation
         * should use this method to drop tables, add tables, or do anything else it
         * needs to upgrade to the new schema version.
         * <p/>
         * <p>
         * The SQLite ALTER TABLE documentation can be found
         * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
         * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
         * you can use ALTER TABLE to rename the old table, then create the new table and then
         * populate the new table with the contents of the old table.
         * </p><p>
         * This method executes within a transaction.  If an exception is thrown, all changes
         * will automatically be rolled back.
         * </p>
         *
         * @param db         The database.
         * @param oldVersion The old database version.
         * @param newVersion The new database version.
         */


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

}
