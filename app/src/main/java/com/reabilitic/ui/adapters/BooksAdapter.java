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

public class BooksAdapter extends RecyclerView.Adapter {

    List<BookModel> books;
    Context mContext;
    protected ItemListener mListener;

    public BooksAdapter(Context context, List<BookModel> books) {

        this.books = books;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvAuthor;
        public TextView tvTitle;
        public TextView tvDescription;
        public TextView tvPages;
        public ImageView ivPoster;
        BookModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tvAuthor = (TextView) v.findViewById(R.id.tvAuthor);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            tvPages = (TextView) v.findViewById(R.id.tvPages);
            ivPoster = (ImageView) v.findViewById(R.id.ivPoster);

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                item = books.get(getLayoutPosition());
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_view_book, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        BookModel book = books.get(i);

        holder.tvAuthor.setText(book.getAuthor());
        holder.tvTitle.setText(book.getName());
        holder.tvDescription.setText(book.getShortDesc());
        holder.tvPages.setText(String.format(mContext.getString(R.string.book_text_pages_quantity), book.getPages()));
        Glide.with(mContext)
                .load(book.getImage())
                .into(holder.ivPoster);
    }


    @Override
    public int getItemCount() {
        Log.d("LOG", "getItemCount: "+books.size());
        return books.size();
    }

    public interface ItemListener {
        void onItemClick(BookModel item);
    }

    public void setOnItemClickListener(ItemListener itemListener) {
        mListener = itemListener;
    }
}
