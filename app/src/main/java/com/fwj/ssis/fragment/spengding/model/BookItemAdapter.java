/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:BookItemAdapter.java
 *    Date:19-6-4 下午12:25
 *    Author:Fanwj
 */

package com.fwj.ssis.fragment.spengding.model;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fwj.ssis.R;
import com.fwj.ssis.fragment.spengding.GlobalVariables;

import org.litepal.crud.DataSupport;

import java.util.List;

import static android.content.ContentValues.TAG;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.ViewHolder> {
    private List<BookItem> mBookList;
    private OnItemClickListener onItemClickListener = null;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View bookView;
        ImageView book_mark;
        TextView book_name;

        public ViewHolder(View view) {
            super(view);
            bookView = view;
            book_mark = (ImageView) view.findViewById(R.id.book_mark);
            book_name = (TextView) view.findViewById(R.id.book_name);
        }
    }

    public BookItemAdapter(List<BookItem> bookItemList) {
        mBookList = bookItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent ,false);

        final ViewHolder holder = new ViewHolder(view);

        holder.bookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                onItemClickListener.onItemClick(holder.itemView, position);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BookItem bookItem = mBookList.get(position);
        holder.book_name.setText(bookItem.getName());

        // 判断是否被选中
        if (position == GlobalVariables.getmBookPos()) {
            holder.bookView.setBackgroundColor(ContextCompat.getColor(holder.bookView.getContext(), R.color.blue));
            holder.book_mark.setImageResource(R.drawable.ic_yellow_yes);
        } else {
            holder.bookView.setBackgroundColor(ContextCompat.getColor(holder.bookView.getContext(), R.color.colorWhite));
            holder.book_mark.setImageResource(R.drawable.home_detail_btn_n);
        }
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }


    // 暴露给外部的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    /*public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mBookList, fromPosition, toPosition);

        notifyItemMoved(fromPosition, toPosition);
    }*/

    public void removeItem(int position) {
        BookItem bookItem = mBookList.get(position);

        DataSupport.deleteAll(IOItem.class, "bookId = ?", String.valueOf(mBookList.get(position).getId()));
        DataSupport.delete(BookItem.class, mBookList.get(position).getId());

        mBookList.remove(position);
        GlobalVariables.setmBookPos(0);
        notifyItemRemoved(position);
    }
}
