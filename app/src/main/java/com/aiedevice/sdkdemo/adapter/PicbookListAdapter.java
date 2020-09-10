package com.aiedevice.sdkdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiedevice.sdkdemo.bean.sdcard.PicBookData;
import com.aiedevice.sdkdemo.utils.ImageLoadUtil;
import com.aiedevice.sdkdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicbookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<PicBookData> mItems;
    private PicbookItemListener listener;

    public PicbookListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void clearItems() {
        if (mItems == null) return;
        mItems.clear();
        notifyDataSetChanged();
    }

    public void setPicbookItemListener(PicbookItemListener listener) {
        this.listener = listener;
    }

    public void setItems(List<PicBookData> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public PicBookData getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_picbook_layout, parent, false);
        PicbookListAdapter.PicbookViewHolder holder = new PicbookListAdapter.PicbookViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PicBookData detail = getItem(position);
        PicbookListAdapter.PicbookViewHolder viewholder = (PicbookListAdapter.PicbookViewHolder) holder;
        ImageLoadUtil.showImageForUrl(detail.getCover(), viewholder.ivCover, R.drawable.default_book_cover);
        viewholder.tvBookName.setText(detail.getBookName());
        viewholder.tvAuthor.setText(detail.getAuthor());
        viewholder.tvSize.setText(String.valueOf(detail.getSize()) + " 字节");
        viewholder.tvStatus.setText(detail.getStatusDesc());

        viewholder.btnUpload.setVisibility(View.VISIBLE);
        viewholder.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onUploadPicbook(detail);
            }
        });

        viewholder.btnDetail.setVisibility(View.VISIBLE);
        viewholder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onShowDetail(detail);
            }
        });
    }

    class PicbookViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_book_name)
        TextView tvBookName;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.btn_upload)
        Button btnUpload;
        @BindView(R.id.btn_detail)
        Button btnDetail;

        public PicbookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface PicbookItemListener {
        void onUploadPicbook(PicBookData picbook);

        void onShowDetail(PicBookData picbook);
    }
}