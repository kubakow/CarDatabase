package com.example.lukaszjarka.cardatabase.listing;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lukaszjarka.cardatabase.CarsTableContract;
import com.example.lukaszjarka.cardatabase.MotoDatabaseOpenHelper;
import com.example.lukaszjarka.cardatabase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListingFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, OnDeleteButtonClickListener {
    private static final String QUERY_KEY = "query_key";
    private static final int CARS_LOADER = 1;

    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    private MotoDatabaseOpenHelper openHelper;
    private RecyclerViewCursorAdapter recyclerViewCursorAdapter;

    public static Fragment getInstance(String query) {
        ListingFragment fragment = new ListingFragment();
        Bundle arguments = new Bundle();
        arguments.putString(QUERY_KEY, query);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openHelper = new MotoDatabaseOpenHelper(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(CARS_LOADER, getArguments() ,this);
        return inflater.inflate(R.layout.fragment_listing, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        recyclerViewCursorAdapter = new RecyclerViewCursorAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(recyclerViewCursorAdapter);
        recyclerViewCursorAdapter.setOnCarItemClickListener((OnCarItemClickListener) getActivity());
        recyclerViewCursorAdapter.setOnDeleteButtonClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String query = getArguments().getString(QUERY_KEY);
        return new CursorLoader(getActivity(),
                CarsTableContract.DATA_CONTENT_URI,
                null,
                CarsTableContract.COLUMN_MAKE+ " like ?",
                new String[]{query + "%"},
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        recyclerViewCursorAdapter.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recyclerViewCursorAdapter.setCursor(null);
    }


    @Override
    public void onDeleteButtonClick(String id) {
        getContext().getContentResolver()
                .delete(CarsTableContract.DATA_CONTENT_URI
                        .buildUpon()
                        .appendPath(id)
                        .build(), null, null);
    }
}
