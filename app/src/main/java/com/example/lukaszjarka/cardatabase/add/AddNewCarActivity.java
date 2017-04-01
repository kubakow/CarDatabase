package com.example.lukaszjarka.cardatabase.add;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lukaszjarka.cardatabase.Car;
import com.example.lukaszjarka.cardatabase.CarBuilder;
import com.example.lukaszjarka.cardatabase.CarsTableContract;
import com.example.lukaszjarka.cardatabase.MotoContentProvider;
import com.example.lukaszjarka.cardatabase.MotoDatabaseOpenHelper;
import com.example.lukaszjarka.cardatabase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewCarActivity extends AppCompatActivity {


    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.year)
    EditText year;

    @BindView(R.id.model)
    EditText model;

    @BindView(R.id.make)
    EditText make;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.add_car)
    void onAddCarButtonClick() {
        Car car = new CarBuilder()
                .setMake(make.getText().toString())
                .setModel(model.getText().toString())
                .setYear(Integer.parseInt(year.getText().toString()))
                .setImage(imageUrl)
                .createCar();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarsTableContract.COLUMN_MAKE, car.getMake());
        contentValues.put(CarsTableContract.COLUMN_MODEL, car.getModel());
        contentValues.put(CarsTableContract.COLUMN_IMAGE, car.getImage());
        contentValues.put(CarsTableContract.COLUMN_YEAR, car.getYear());

        Uri uri = getContentResolver().insert(CarsTableContract.DATA_CONTENT_URI, contentValues);

        if (uri != null) {
            Toast.makeText(this, "Dodano nowy samochód", Toast.LENGTH_LONG).show();
            make.setText(null);
            model.setText(null);
            imageUrl = null;
            imageView.setImageResource(R.drawable.placeholder_image);
            year.setText(null);
        }
    }

    @OnClick(R.id.add_image)
    void onAddImageButtonClick() {
        View promptView = LayoutInflater.from(this).inflate(R.layout.dialog_prompt,
                null);
        EditText urlEditText = (EditText) promptView.findViewById(R.id.url_edittext);


        new AlertDialog.Builder(this)
                .setView(promptView)
                .setPositiveButton("OK", (dialog, which) -> {
                    imageUrl = urlEditText.getText().toString();
                    Glide.with(AddNewCarActivity.this)
                            .load(imageUrl)
                            .into(imageView);
                }).show();
    }
}

