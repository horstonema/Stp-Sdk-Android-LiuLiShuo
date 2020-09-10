package com.aiedevice.sdkdemo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aiedevice.sdkdemo.bean.FamilyDynamicsEntity;
import com.aiedevice.appcommon.util.GlideUtils;
import com.aiedevice.sdkdemo.R;

import java.util.List;

/**
 * Created by $USER_NAME on 10/5/17.
 */


public class PhotoPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mContext;
    private LayoutInflater mLayoutInflater;
    private List<FamilyDynamicsEntity> mItems;

    public PhotoPageAdapter(Activity context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void clearItems() {
        if (mItems == null) return;
        mItems.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<FamilyDynamicsEntity> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public FamilyDynamicsEntity getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = mLayoutInflater.inflate(R.layout.photo_item_layout, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FamilyDynamicsEntity detail = getItem(position);
        PhotoViewHolder mainWordViewHolder = (PhotoViewHolder) holder;
        GlideUtils.loadImageView(mContext, detail.getThumb(), mainWordViewHolder.img);
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        LinearLayout root_layout;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_thumb);
            root_layout = (LinearLayout) itemView.findViewById(R.id.root_layout);
        }

    }
}
