package com.database.app.activities.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.database.app.activities.entities.Firma;
import com.database.app.activities.entities.Product;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FIRMS_TABLE = "CREATE TABLE " + DatabaseConstants.TABLE_FIRMS + "("
                + DatabaseConstants.KEY_ID + " INTEGER PRIMARY KEY," + DatabaseConstants.KEY_NAME + " TEXT," + DatabaseConstants.KEY_SITY + " TEXT" + ")";
        db.execSQL(CREATE_FIRMS_TABLE);

        String CREATE_SCLAD_TABLE = "CREATE TABLE " + DatabaseConstants.TABLE_SCLAD + "("
                + DatabaseConstants.KEY_NAME + " TEXT PRIMARY KEY," + DatabaseConstants.KEY_FIRMA_ID + " INTEGER," + DatabaseConstants.KEY_PRICE + " INTEGER,"
                + DatabaseConstants.KEY_AMOUNT + " INTEGER" + ")";
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
        try {
            pr = Integer.parseInt(price);
        } catch (Exception e) {
        }
        values.put(DatabaseConstants.KEY_PRICE, pr);
        int am = 1;
        try {
            am = Integer.parseInt(amount);
        } catch (Exception e) {
        }
        values.put(DatabaseConstants.KEY_AMOUNT, am);
        int id = 1;
        try {
            id = Integer.parseInt(fId);
        } catch (Exception e) {
        }
        values.put(DatabaseConstants.KEY_FIRMA_ID, id);


        // Inserting Row
        long x = db.insert(DatabaseConstants.TABLE_SCLAD, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<Firma> getFirms() {
        ArrayList<Firma> items = new ArrayList<Firma>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + DatabaseConstants.TABLE_FIRMS, null);

        if (cursor.moveToFirst()) {
            do {
                Firma item = new Firma();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setSity(cursor.getString(2));
                items.add(item);
            } while (cursor.moveToNext());
        }
        db.close();
        return items;
    }

    public void addFirm(String name, String id, String sity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        int fid = 1;
        try {
            fid = Integer.parseInt(id);
        } catch (Exception e) {
        }
        values.put(DatabaseConstants.KEY_ID, fid);
        values.put(DatabaseConstants.KEY_NAME, name);
        values.put(DatabaseConstants.KEY_SITY, sity);


        // Inserting Row
        long x = db.insert(DatabaseConstants.TABLE_FIRMS, null, values);
        db.close(); // Closing database connection
    }

    public Firma getFirmForId(int firmaId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + DatabaseConstants.TABLE_FIRMS + " where " + DatabaseConstants.KEY_ID + " = ?", new String[]{String.valueOf(firmaId)});

        if (cursor.moveToFirst()) {
            Firma item = new Firma();
            do {
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setSity(cursor.getString(2));
            } while (cursor.moveToNext());
            return item;
        } else return null;
    }

    public void deleteProduct(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseConstants.TABLE_SCLAD, DatabaseConstants.KEY_NAME + " = ?",
                new String[]{name});
        db.close();
    }

    public void editProduct(String name, String price, String amount, String fId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        int pr = 1;
        try {
            pr = Integer.parseInt(price);
        } catch (Exception e) {
        }
        values.put(DatabaseConstants.KEY_PRICE, pr);
        int am = 1;
        try {
            am = Integer.parseInt(amount);
        } catch (Exception e) {
        }
        values.put(DatabaseConstants.KEY_AMOUNT, am);
        int id = 1;
        try {
            id = Integer.parseInt(fId);
        } catch (Exception e) {
        }
        values.put(DatabaseConstants.KEY_FIRMA_ID, id);


        // Inserting Row
        long x = db.update(DatabaseConstants.TABLE_SCLAD, values, DatabaseConstants.KEY_NAME + "= ?", new String[]{name});
        db.close(); // Closing database connection
    }

    public ArrayList<Product> getProductsForFirmId(int id) {

        ArrayList<Product> items = new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + DatabaseConstants.TABLE_SCLAD + " where " + DatabaseConstants.KEY_FIRMA_ID + " = ?", new String[]{String.valueOf(id)});

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

    public void deleteFirm(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseConstants.TABLE_FIRMS, DatabaseConstants.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void editFirma(String name, int id, String sity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseConstants.KEY_NAME, name);
        values.put(DatabaseConstants.KEY_SITY, sity);


        // Inserting Row
        long x = db.update(DatabaseConstants.TABLE_FIRMS, values, DatabaseConstants.KEY_ID + "= ?", new String[]{String.valueOf(id)});
        db.close(); // Closing database connection
    }

    public String searchType1(String pAmount) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT firms.name from firms INNER JOIN sclad ON firms._id = sclad.firma_id " +
                "WHERE (sclad.amount = ?) AND (sclad.price = (SELECT MIN(sclad.price) FROM sclad WHERE sclad.amount = ?))";
        Cursor cursor = db.rawQuery(query, new String[]{pAmount, pAmount});
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        db.close();
        return null;
    }

    public ArrayList<String> searchType2() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();
        String query = "SELECT sclad.price, sclad.name, firms.name from sclad INNER JOIN firms ON sclad.firma_id = firms._id " +
                "WHERE sclad.price = (SELECT MIN(price) from sclad)";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            arrayList.add(cursor.getString(0));
            arrayList.add(cursor.getString(1));
            arrayList.add(cursor.getString(2));
            return arrayList;
        }
        db.close();
        return null;

    }

    public ArrayList<String> searchType3() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();
        String query = "SELECT name from sclad ORDER BY name";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(cursor.getString(0));
            } while (cursor.moveToNext());
            db.close();
            return arrayList;

        }
        return null;
    }
}
