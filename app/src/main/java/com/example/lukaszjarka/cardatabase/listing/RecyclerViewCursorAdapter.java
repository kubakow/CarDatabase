package com.example.lukaszjarka.cardatabase.listing;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lukaszjarka.cardatabase.CarsTableContract;
import com.example.lukaszjarka.cardatabase.R;

import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;

public class RecyclerViewCursorAdapter
        extends RecyclerView.Adapter<RecyclerViewCursorAdapter.ViewHolder> {

    private String lastDeletedItemId = "";
    private OnDeleteButtonClickListener onDeleteButtonClickListener;
    private OnCarItemClickListener onCarItemClickListener;
    private Cursor cursor;


    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener onDeleteButtonClickListener) {
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }


    public void setOnCarItemClickListener(OnCarItemClickListener onCarItemClickListener) {
        this.onCarItemClickListener = onCarItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        cursor.moveToPosition(position);
        String imageUrl = cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_IMAGE));
        String make = cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_MAKE));
        String model = cursor.getString(cursor.getColumnIndex(CarsTableContract.COLUMN_MODEL));
        int year = cursor.getInt(cursor.getColumnIndex(CarsTableContract.COLUMN_YEAR));

        holder.year.setText("Rok produkcji: " + year);
        holder.makeAndModel.setText(make + " " + model);
        Glide.with(holder.imageView.getContext())
                .load(imageUrl).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCarItemClickListener != null) {
                    cursor.moveToPosition(position);
                    onCarItemClickListener.onCarItemClick(String.valueOf(cursor.getInt(0)));
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToPosition(position);
                if(onDeleteButtonClickListener != null) {
                    lastDeletedItemId = cursor.getString(0);
                    onDeleteButtonClickListener.onDeleteButtonClick(cursor.getString(0));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public void setCursor(@Nullable Cursor cursor) {

        int lastDeletedItemPosition = 0;

        boolean shouldNotifyOnlyOneItem = false;
        if(this.cursor!=null & cursor!= null && cursor.getCount() +1 ==  this.cursor.getCount()){

            this.cursor.moveToFirst();

            do{
                if(lastDeletedItemId.equals(this.cursor.getString(0))){
                    lastDeletedItemPosition = this.cursor.getPosition();
                    shouldNotifyOnlyOneItem = true;
                }
            }while(this.cursor.moveToNext());

        }

        if(this.cursor!=null)
        this.cursor.close();

        this.cursor = cursor;

        if(shouldNotifyOnlyOneItem){
         notifyItemRemoved(lastDeletedItemPosition);

        } else {
            notifyDataSetChanged();
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        TextView makeAndModel;

        TextView year;

        ImageView deleteButton;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = findById(itemView, R.id.image);
            makeAndModel = (TextView) itemView.findViewById(R.id.make_and_model);
            year = findById(itemView, R.id.year);
            deleteButton = findById(itemView, R.id.delete_from_database);
        }
    }

}
