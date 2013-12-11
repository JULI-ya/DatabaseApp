package com.database.app.activities.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.database.app.activities.entities.Product;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FIRMS_TABLE = "CREATE TABLE " + DatabaseConstants.TABLE_FIRMS + "("
                + DatabaseConstants.KEY_ID + " INTEGER PRIMARY KEY," + DatabaseConstants.KEY_NAME + " TEXT," + DatabaseConstants.KEY_SITY  + " TEXT" + ")";
        db.execSQL(CREATE_FIRMS_TABLE);

        String CREATE_SCLAD_TABLE = "CREATE TABLE " + DatabaseConstants.TABLE_SCLAD + "("
                + DatabaseConstants.KEY_NAME + " TEXT PRIMARY KEY," + DatabaseConstants.KEY_FIRMA_ID + " INTEGER," + DatabaseConstants.KEY_PRICE + " INTEGER,"
                + DatabaseConstants.KEY_AMOUNT  + " INTEGER" + ")";
        db.execSQL(CREATE_SCLAD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_FIRMS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_SCLAD);
        onCreate(db);
    }

    public ArrayList<Product> getProducts() {

        ArrayList<Product> items = new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + DatabaseConstants.TABLE_SCLAD, null);

        if (cursor.moveToFirst()) {
            do {
                Product item = new Product();
                item.setName(cursor.getString(0));
                item.setFirmaId(Integer.parseInt(cursor.getString(1)));
                item.setPrice(Integer.parseInt(cursor.getString(2)));
                item.setAmount(Integer.parseInt(cursor.getString(3)));
                items.add(item);
            } while (cursor.moveToNext());
        }
        db.close();
        return items;
    }

    public void addProduct(String name, String price, String amount, String fId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.KEY_NAME, name);
        int pr = 1;
        try{
            pr = Integer.parseInt(price);
        } catch (Exception e){
        }
        values.put(DatabaseConstants.KEY_PRICE, pr);
        int am = 1;
        try{
            am = Integer.parseInt(amount);
        } catch (Exception e){
        }
        values.put(DatabaseConstants.KEY_AMOUNT, am);
        int id = 1;
        try{
            id = Integer.parseInt(fId);
        } catch (Exception e){
        }
        values.put(DatabaseConstants.KEY_FIRMA_ID, id);


        // Inserting Row
        long x = db.insert(DatabaseConstants.TABLE_SCLAD, null, values);
        db.close(); // Closing database connection
    }
}
