package com.example.softices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Softices.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_IMAGE = "user_image";
    private static final String COLUMN_USER_FIRSTNAME = "user_firstname";
    private static final String COLUMN_USER_LASTNAME = "user_lastname";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_GENDER = "user_gender";
    private static final String COLUMN_USER_ADDRESS = "user_address";
    private static final String COLUMN_USER_CITY = "user_city";
    private static final String COLUMN_USER_PINCODE = "user_pincode";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private String CREATE_USER_TABLE = "CREATE TABLE "
            + TABLE_USER + "("
            + COLUMN_USER_IMAGE + " BLOB,"
            + COLUMN_USER_FIRSTNAME + " TEXT,"
            + COLUMN_USER_LASTNAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT PRIMARY KEY,"
            + COLUMN_USER_GENDER + " TEXT,"
            + COLUMN_USER_ADDRESS + " TEXT,"
            + COLUMN_USER_CITY + " TEXT,"
            + COLUMN_USER_PINCODE + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT"
            + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    /**
     * @param usermodel
     */
    boolean addUser(UserModel usermodel) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_IMAGE, usermodel.getimage());
            values.put(COLUMN_USER_FIRSTNAME, usermodel.getFirstname());
            values.put(COLUMN_USER_LASTNAME, usermodel.getLastname());
            values.put(COLUMN_USER_EMAIL, usermodel.getEmail());
            values.put(COLUMN_USER_GENDER, usermodel.getGender());
            values.put(COLUMN_USER_ADDRESS, usermodel.getAddress());
            values.put(COLUMN_USER_CITY, usermodel.getCity());
            values.put(COLUMN_USER_PINCODE, usermodel.getPincode());
            values.put(COLUMN_USER_PASSWORD, usermodel.getPassword());
            db.insert(TABLE_USER, null, values);
            db.close();
            Log.e(TABLE_USER, "Created Successfully");
            return true;
        } catch (Exception e) {
            Log.e("addUser", e.toString());
            return false;
        }
    }

    boolean updateUser(UserModel userModel) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_IMAGE, userModel.getimage());
            values.put(COLUMN_USER_FIRSTNAME, userModel.getFirstname());
            values.put(COLUMN_USER_LASTNAME, userModel.getLastname());
            values.put(COLUMN_USER_EMAIL, userModel.getEmail());
            values.put(COLUMN_USER_GENDER, userModel.getGender());
            values.put(COLUMN_USER_ADDRESS, userModel.getAddress());
            values.put(COLUMN_USER_CITY, userModel.getCity());
            values.put(COLUMN_USER_PINCODE, userModel.getPincode());
            values.put(COLUMN_USER_PASSWORD, userModel.getPassword());
            db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?", new String[]{userModel.getEmail()});
            db.close();
        } catch (Exception e) {
            Log.e("updateUser", e.toString());
        }
        return true;
    }

    UserModel getCurrentUser(String email) {
        try {
            String[] columns = {
                    COLUMN_USER_IMAGE,
                    COLUMN_USER_FIRSTNAME,
                    COLUMN_USER_LASTNAME,
                    COLUMN_USER_EMAIL,
                    COLUMN_USER_GENDER,
                    COLUMN_USER_ADDRESS,
                    COLUMN_USER_CITY,
                    COLUMN_USER_PINCODE
            };
            String sortOrder =
                    COLUMN_USER_FIRSTNAME + " ASC";

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = COLUMN_USER_EMAIL + " = ?";
            String[] selectionArgs = {email};

            Cursor cursor = db.query(TABLE_USER, //Table to query
                    columns,    //columns to return
                    selection,        //columns for the WHERE clause
                    selectionArgs,        //The values for the WHERE clause
                    null,       //group the rows
                    null,       //filter by row groups
                    sortOrder); //The sort order
            if (cursor.moveToFirst()) {
                do {
                    UserModel user = new UserModel();
                    user.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_IMAGE)));
                    user.setFirstname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRSTNAME)));
                    user.setLastname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LASTNAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                    user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_USER_GENDER)));
                    user.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADDRESS)));
                    user.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CITY)));
                    user.setPincode(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PINCODE)));
                    return user;
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e("getCurrentUser", e.toString());
        }
        return null;
    }

    List<UserModel> fetchUser() {
        try {
            String[] columns = {
                    COLUMN_USER_FIRSTNAME,
                    COLUMN_USER_LASTNAME,
                    COLUMN_USER_EMAIL,
                    COLUMN_USER_GENDER,
                    COLUMN_USER_ADDRESS,
                    COLUMN_USER_CITY,
                    COLUMN_USER_PINCODE
            };
            String sortOrder =
                    COLUMN_USER_FIRSTNAME + " ASC";
            List<UserModel> userList = new ArrayList<UserModel>();
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_USER, //Table to query
                    columns,    //columns to return
                    null,
                    null,//The values for the WHERE clause
                    null,       //group the rows
                    null,       //filter by row groups
                    sortOrder); //The sort order


            if (cursor.moveToFirst()) {
                do {
                    UserModel user = new UserModel();
                    user.setFirstname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRSTNAME)));
                    user.setLastname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LASTNAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                    user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_USER_GENDER)));
                    user.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADDRESS)));
                    user.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CITY)));
                    user.setPincode(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PINCODE)));
                    userList.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

            return userList;
        } catch (Exception e) {
            Log.e("fetchUser", e.toString());
            return null;
        }
    }

    boolean CheckUser(String email) {
        try {
            String[] columns = {
                    COLUMN_USER_EMAIL
            };
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = COLUMN_USER_EMAIL + " = ?";
            String[] selectionArgs = {email};
            Cursor cursor = db.query(
                    TABLE_USER, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                      //filter by row groups
                    null);                      //The sort order
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();
            if (cursorCount > 0)
                return true;
        } catch (Exception e) {
            Log.e("CheckUser", e.toString());
        }
        return false;
    }

    boolean Checkemailpassword(String email, String password) {
        try {
            String[] columns = {
                    COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD
            };
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
            String[] selectionArgs = {email, password};
            Cursor cursor = db.query(
                    TABLE_USER, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                      //filter by row groups
                    null);                      //The sort order
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();
            if (cursorCount > 0)
                return true;
        } catch (Exception e) {
            Log.e("Checkemailpassword", e.toString());
        }
        return false;
    }

    void deleteuser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_USER_EMAIL + " = ?",
                new String[]{(userModel.getEmail())});
        db.close();
    }

}

