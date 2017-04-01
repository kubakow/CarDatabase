package com.example.lukaszjarka.cardatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MotoDatabaseOpenHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "moto.db";

    private static String SQL_CREATE_TABLE = "CREATE TABLE " + CarsTableContract.TABLE_NAME + " ("
            + CarsTableContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CarsTableContract.COLUMN_MAKE + " TEXT, "
            + CarsTableContract.COLUMN_MODEL + " TEXT, "
            + CarsTableContract.COLUMN_IMAGE + " TEXT, "
            + CarsTableContract.COLUMN_YEAR + " INT)";

    private static String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + CarsTableContract.TABLE_NAME;

    public MotoDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    public boolean insertCar(Car car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarsTableContract.COLUMN_MAKE, car.getMake());
        contentValues.put(CarsTableContract.COLUMN_MODEL, car.getModel());
        contentValues.put(CarsTableContract.COLUMN_IMAGE, car.getImage());
        contentValues.put(CarsTableContract.COLUMN_YEAR, car.getYear());

        long value = getWritableDatabase()
                .insert(CarsTableContract.TABLE_NAME, null, contentValues);

        return value != -1;
    }

    public Cursor getAllItems() {
        Cursor cursor = getReadableDatabase()
                .query(CarsTableContract.TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    public Car getCarWithId(String id){
        Cursor cursor = getReadableDatabase()
                .query(CarsTableContract.TABLE_NAME, null, CarsTableContract._ID + " =? ", new String[]{id}, null, null, null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            Car car = new CarBuilder().setMake(cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_MAKE)))
                    .setImage(cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_IMAGE)))
                    .setModel(cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_MODEL)))
                    .setYear(cursor.getInt(cursor.getColumnIndex(CarsTableContract.COLUMN_YEAR)))
                    .createCar();

            cursor.close();
            return car;
        }
        cursor.close();
        return null;
    }

    public Cursor searchQuery(CharSequence constraint) {
        Cursor cursor = getReadableDatabase().query(CarsTableContract.TABLE_NAME,
                null,
                CarsTableContract.COLUMN_MAKE + " LIKE ?",
                new String[]{
                        constraint.toString() + "%"
                }, null, null, null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(SQL_DROP_TABLE);
            onCreate(db);
        }
    }
}
