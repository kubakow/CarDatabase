package com.example.lukaszjarka.cardatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.Toast;

import com.example.lukaszjarka.cardatabase.add.AddNewCarActivity;
import com.example.lukaszjarka.cardatabase.listing.ListingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.autocomplete_textview)
    AutoCompleteTextView autoCompleteTextView;

    private MotoDatabaseOpenHelper databaseOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        databaseOpenHelper = new MotoDatabaseOpenHelper(this);

        AutocompleteAdapter adapter = new AutocompleteAdapter(this,
                databaseOpenHelper.getAllItems());
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                return databaseOpenHelper.searchQuery(constraint);
            }
        });
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                autoCompleteTextView.setText(cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_MAKE)));
            }
        });

    }

    @OnClick(R.id.cry_for_content_provider)
    void onCryForContentProvider(View view){

        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.lukaszjarka.cardatabase/CARS/1"), null, null, null, null);
        Toast.makeText(this, ""+ cursor.getCount(), Toast.LENGTH_SHORT).show();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CarsTableContract.COLUMN_MAKE, "Opel");
        contentValues.put(CarsTableContract.COLUMN_MODEL, "Corsa");
        contentValues.put(CarsTableContract.COLUMN_IMAGE, "");
        contentValues.put(CarsTableContract.COLUMN_YEAR, 1992);
        getContentResolver().insert(Uri.parse("content://com.example.lukaszjarka.cardatabase/CARS"), contentValues);

    }

    @OnClick(R.id.add_new_car)
    void onAddNewCarButtonClick() {
        Intent intent = new Intent(this, AddNewCarActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.search_button)
    void onSearchButtonClick() {
        startActivity(ListingActivity.createIntent(MainActivity.this,
                autoCompleteTextView.getText().toString()));
    }
}
