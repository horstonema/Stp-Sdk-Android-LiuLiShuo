package com.aiedevice.sdkdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiedevice.sdkdemo.bean.ChatEntity;
import com.aiedevice.sdkdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<ChatEntity> mItems;

    public ChatAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void clearItems() {
        if (mItems == null) return;
        mItems.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<ChatEntity> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public ChatEntity getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_chat_entity, parent, false);
        ChatEntityViewHolder holder = new ChatEntityViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatEntity chatEntity = getItem(position);
        if (chatEntity == null)
            return;

        ChatEntityViewHolder chatHolder = (ChatEntityViewHolder) holder;
        chatHolder.tvId.setText("id: " + chatEntity.getId());
        chatHolder.tvContent.setText("content: " + chatEntity.getContent());
        chatHolder.tvCreatedAt.setText("time: " + chatEntity.getCreated_at());
    }

    class ChatEntityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_created_at)
        TextView tvCreatedAt;

        public ChatEntityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
