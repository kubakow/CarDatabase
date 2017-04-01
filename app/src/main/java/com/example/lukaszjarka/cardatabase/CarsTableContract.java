package com.example.lukaszjarka.cardatabase;

import android.net.Uri;
import android.provider.BaseColumns;

public class CarsTableContract implements BaseColumns {
    public static String TABLE_NAME = "CARS";
    public static String COLUMN_MAKE = "make";
    public static String COLUMN_MODEL = "model";
    public static String COLUMN_IMAGE = "image";
    public static String COLUMN_YEAR = "year";
    public static final Uri DATA_CONTENT_URI = MotoContentProvider.CONTENT_URI.buildUpon()
            .appendPath(TABLE_NAME).build();

}
