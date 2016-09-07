package com.kalerkantho.MyDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kalerkantho.Model.Category;
import com.kalerkantho.Model.FvrtModel;

import java.util.ArrayList;
import java.util.Vector;

public class MyDBHandler extends SQLiteOpenHelper {

    private Context con;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "categoryDB.db";
    private static final String TABLE_CAT = "category";
    private static final String TABLE_FAVORITE = "favourite_table";

    public static final String _ID = "_id";
    public static final String CAT_ID = "cat_id";
    public static final String CAT_NAME = "cat_name";
    public static final String CAT_TYPE = "cat_type";

    public static final String FAV_ID = "fav_id";
    public static final String FAV_OBJECT = "fav_object";

    public MyDBHandler(Context context) {
        super(context, MyDBHandler.DATABASE_NAME, null,MyDBHandler.DATABASE_VERSION);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CAT_TABLE = "CREATE TABLE " +
                TABLE_CAT + "("
                + _ID + " INTEGER PRIMARY KEY,"
                + CAT_ID + " TEXT,"
                + CAT_NAME + " TEXT,"
                + CAT_TYPE + " TEXT " + ")";
        db.execSQL(CREATE_CAT_TABLE);


        String CREATE_FAVRT_TABLE = "CREATE TABLE " +
                TABLE_FAVORITE + "("
                + _ID + " INTEGER PRIMARY KEY,"
                + FAV_ID + " TEXT,"
                + FAV_OBJECT + " TEXT" + ")";
        db.execSQL(CREATE_FAVRT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }


    public boolean addCat(Category cat) {
        boolean flag = false;
        final SQLiteDatabase db = getWritableDatabase();
        final String addlist = " Select " + " * FROM "
                + MyDBHandler.TABLE_CAT + " where "
                + MyDBHandler.CAT_ID + "='" + cat.getId() + "' AND "
                + MyDBHandler.CAT_TYPE + "='" + cat.getM_type() + "'";

        final Cursor cursor = db.rawQuery(addlist, null);
        if (cursor.getCount() > 0) {

            final ContentValues values = new ContentValues();

            values.put(MyDBHandler.CAT_ID, cat.getId());
            values.put(MyDBHandler.CAT_NAME, cat.getName());
            values.put(MyDBHandler.CAT_TYPE, cat.getM_type());

            final int updatedRow = db.update(MyDBHandler.TABLE_CAT, values, MyDBHandler.CAT_ID + "='" + cat.getId() + "'",
                    null);

            if (updatedRow > 0) {
                flag = true;
            } else {
                flag = false;
            }

        } else {
            final ContentValues values = new ContentValues();
            values.put(MyDBHandler.CAT_ID, cat.getId());
            values.put(MyDBHandler.CAT_NAME, cat.getName());
            values.put(MyDBHandler.CAT_TYPE, cat.getM_type());

            db.insert(MyDBHandler.TABLE_CAT, null, values);
            flag = true;
        }

        db.close();
        return flag;
    }


    public Vector<Category> getCatList() {

        final Vector<Category> addList = new Vector<Category>();
        addList.removeAllElements();

        final String alladdlist = " SELECT " + " * FROM " + MyDBHandler.TABLE_CAT;

        final SQLiteDatabase db = getWritableDatabase();
        final Cursor cursor = db.rawQuery(alladdlist, null);

        if (cursor.moveToFirst()) {
            do {
                final Category cat = new Category();
                //cat.setId(cursor.getInt(cursor.getColumnIndex(MyDBHandler.CAT_ID)));
                cat.setId(cursor.getString(cursor.getColumnIndex(MyDBHandler.CAT_ID)));
                cat.setName(cursor.getString(cursor.getColumnIndex(MyDBHandler.CAT_NAME)));
                cat.setM_type(cursor.getString(cursor.getColumnIndex(MyDBHandler.CAT_TYPE)));
                addList.addElement(cat);

            } while (cursor.moveToNext());
        }

        db.close();
        return addList;
    }

    public boolean isAdd(Category cat) {
        boolean flag = false;
        final SQLiteDatabase db = getWritableDatabase();

        final String isadd = " select " + " * FROM "
                + MyDBHandler.TABLE_CAT + " where "
                + MyDBHandler.CAT_TYPE + "='" + cat.getM_type() + "'"
                + " AND " + MyDBHandler.CAT_ID + "='" + cat.getId() + "'";
               Log.e("Isadd query", ">>>" + isadd);

        final Cursor cursor = db.rawQuery(isadd, null);

        if (cursor.getCount() > 0) {
            flag = true;
            cursor.moveToNext();
            do {

            } while (cursor.moveToNext());

        } else {
            flag = false;
        }

        db.close();
        return flag;
    }



    public int removeCat(Category cat) {
        final SQLiteDatabase db = getWritableDatabase();
		/*
		 * final String removequery = " Delete " + " FROM " +
		 * DatabaseHandler.TABLE_ORDER + " where " + DatabaseHandler._IMAGEID +
		 * "='" + fl.getId()+ "'";
		 */

        final int numRow = db.delete(MyDBHandler.TABLE_CAT, MyDBHandler.CAT_ID + "='" + cat.getId() + "'", null);
        db.close();
        return numRow;
    }


    // add favrt entry

    public void addFavrtEntry(FvrtModel fm) {
        final SQLiteDatabase db = getWritableDatabase();

        boolean isPreviouslyAdded = false;
        String[] columns = new String[]{FAV_ID,FAV_OBJECT};
        Cursor c = db.query(TABLE_FAVORITE, columns, FAV_ID + "=" +fm.getFvrtId(), null, null, null, null);
        ArrayList a1 = new ArrayList();

        if (c.moveToFirst()) {
            do {
                a1.add(c.getString(1));
            } while (c.moveToNext());

        }
        if(a1.size()>0) {
            for (int i = 0; i < a1.size(); i++) {
                if (a1.get(i).equals(fm.getFvrtId())) {
                    isPreviouslyAdded = true;
                }

            }
        }
        else{
            isPreviouslyAdded = false;
        }

        if(!isPreviouslyAdded) {
            //	Log.e("DB Image;",fm.getFvrtId());
            final ContentValues values = new ContentValues();
            values.put(FAV_ID, fm.getFvrtId());
            values.put(FAV_OBJECT, fm.getFvrtObject());
            // Inserting Row
            db.insert(TABLE_FAVORITE, null, values);
            //Log.e("Insert Obj:",fm.getFvrtObject().toString());
        }
        db.close(); // Closing database connection
    }

    public ArrayList<FvrtModel> getAllFvrtModels() {
        ArrayList<FvrtModel> contactList = new ArrayList<FvrtModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FvrtModel contact = new FvrtModel();
                contact.setFvrtId(cursor.getString(1));
                contact.setFvrtObject(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return contactList;
    }


    public boolean isFavorite(final String newsId) {

        boolean isFavorite=false;

        ArrayList<FvrtModel> contactList = new ArrayList<FvrtModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE + " where " + MyDBHandler.FAV_ID +
         "='" + newsId+ "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.getCount()>0) {
            isFavorite=true;

        }
        db.close();
        // return contact list
        return isFavorite;
    }


    // remove single entry
    public boolean removeSingleFavENtry(String fvId) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete(TABLE_FAVORITE, FAV_ID + "='" + fvId + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

    public void removeAllFavEntry(){
        final SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ TABLE_FAVORITE);
        db.close();
    }
}