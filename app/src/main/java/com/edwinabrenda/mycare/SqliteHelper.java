package com.edwinabrenda.mycare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME="users.db";
    public static  final  int DATABASE_VERSION=1;
    public static final String TABLE_USERS="patients";
    public static final String KEY_ID="id";
    public static  final String KEY_USERNAME="user";
    public static final String KEY_EMAIL="email";
    public static  final String KEY_CONTACT="contact";
    public static final String KEY_PASSWORD="password";
    public static  final String KEY_CPASSWORD="cpassword";

    public static final String SQL_TABLE_USERS="CREATE TABLE "+TABLE_USERS
            +"("
            +KEY_ID+" INTEGER PRIMARY KEY, "
            +KEY_USERNAME+" TEXT, "
            +KEY_EMAIL+" TEXT, "
            +KEY_CONTACT+" TEXT, "
            +KEY_PASSWORD+" TEXT, "
            +KEY_CPASSWORD+" TEXT"
            +")";
    public SqliteHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_USERS);
    }
    public void addUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_USERNAME, user.username);
        values.put(KEY_EMAIL,user.email);
        values.put(KEY_CONTACT,user.contact);
        values.put(KEY_PASSWORD,user.password);
        values.put(KEY_CPASSWORD,user.cpassword);
        long todo_id=db.insert(TABLE_USERS,null,values);
    }
    public User Authenticate(User user){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_USERS,
                new String[]{KEY_ID,KEY_USERNAME,KEY_EMAIL,KEY_CONTACT,KEY_PASSWORD,KEY_CPASSWORD},
                KEY_EMAIL+"=?",
                new String[]{user.email},
                null,null,null);
        if(cursor!=null && cursor.moveToFirst() && cursor.getCount()>0){
            User user1=new User(cursor.getString(0),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5));
            if(user.password.equalsIgnoreCase(user1.password)){
                return user1;
            }
        }
        return null;
    }
    public boolean isEmailExists(String email){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_USERS,
                new String[]{KEY_ID,KEY_USERNAME,KEY_EMAIL,KEY_CONTACT,KEY_PASSWORD,KEY_CPASSWORD},
                KEY_EMAIL+"=?",
                new String[]{email},
                null,null,null);
        if(cursor!=null && cursor.moveToFirst() && cursor.getCount()>0){
            return true;
        }
        return false;
    }
}
