package com.example.lukaszjarka.cardatabase.listing;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.lukaszjarka.cardatabase.R;
import com.example.lukaszjarka.cardatabase.details.DetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListingActivity extends AppCompatActivity implements OnCarItemClickListener{
    private static final String QUERY = "query";

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        ButterKnife.bind(this);

        String query = getIntent().getStringExtra(QUERY);
        Fragment fragment = ListingFragment.getInstance(query);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public static Intent createIntent(Context context, String query) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(QUERY, query);
        return intent;
    }

    @Override
    public void onCarItemClick(String id) {
        Fragment fragment = DetailsFragment.getInstance(id);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();

        }else {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
