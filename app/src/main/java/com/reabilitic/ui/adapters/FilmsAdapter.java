package com.reabilitic.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reabilitic.R;
import com.reabilitic.models.BookModel;
import com.reabilitic.models.FilmModel;

import java.util.List;

public class FilmsAdapter extends RecyclerView.Adapter {

    List<FilmModel> films;
    Context mContext;
    protected ItemListener mListener;

    public FilmsAdapter(Context context, List<FilmModel> films) {

        this.films = films;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        public TextView tvRating;
        public TextView tvEnTitle;
        public TextView tvCountry;
        public TextView tvGenre;
        public TextView tvYear;
        public TextView tvDuration;
        public TextView tvDirector;
        public TextView tvActors;
        public ImageView ivPoster;
        FilmModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvRating = (TextView) v.findViewById(R.id.tvRating);
            tvEnTitle = (TextView) v.findViewById(R.id.tvEnTitle);
            tvCountry = (TextView) v.findViewById(R.id.tvCountry);
            tvGenre = (TextView) v.findViewById(R.id.tvGenre);
            tvYear = (TextView) v.findViewById(R.id.tvYear);
            tvDuration = (TextView) v.findViewById(R.id.tvDuration);
            tvDirector = (TextView) v.findViewById(R.id.tvDirector);
            tvActors = (TextView) v.findViewById(R.id.tvActors);
            ivPoster = (ImageView) v.findViewById(R.id.ivPoster);

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                item = films.get(getLayoutPosition());
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public FilmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_view_film, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        FilmModel film = films.get(i);
        holder.tvTitle.setText(film.getNameRu());
        holder.tvEnTitle.setText(film.getNameEn());
        holder.tvRating.setText(film.getRaiting());
        holder.tvCountry.setText(film.getCountry());
        holder.tvGenre.setText(film.getCategory_name());
        holder.tvYear.setText(film.getYear());
        holder.tvDirector.setText(film.getDirected());
        holder.tvActors.setText(film.getCasts());
        Glide.with(mContext)
                .load(film.getImage())
                .into(holder.ivPoster);
    }


    @Override
    public int getItemCount() {
        Log.d("LOG", "getItemCount: "+films.size());
        return films.size();
    }

    public interface ItemListener {
        void onItemClick(FilmModel item);
    }

    public void setOnItemClickListener(ItemListener itemListener) {
        mListener = itemListener;
    }
}
