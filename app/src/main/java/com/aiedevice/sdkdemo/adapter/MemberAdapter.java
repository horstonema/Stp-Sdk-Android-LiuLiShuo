package com.aiedevice.sdkdemo.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiedevice.sdkdemo.bean.User;
import com.aiedevice.sdkdemo.utils.ImageLoadUtil;
import com.aiedevice.sdkdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<User> mItems;
    private MemberListener memberListener;

    public MemberAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void clearItems() {
        if (mItems == null) return;
        mItems.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<User> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public User getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.member_item_layout, parent, false);
        MemberViewHolder holder = new MemberViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final User user = getItem(position);
        MemberViewHolder memberHolder = (MemberViewHolder) holder;

        //管理员设置为红色
        if (user.isManager()) {
            memberHolder.tvUserRole.setTextColor(Color.RED);
            memberHolder.btnChangetoManager.setVisibility(View.GONE);
        } else {
            memberHolder.btnChangetoManager.setVisibility(View.VISIBLE);
            memberHolder.tvUserRole.setTextColor(Color.BLACK);
        }

        memberHolder.tvNickname.setText("用户昵称 :" + user.getName());
        memberHolder.tvUserRole.setText("用户身份 :" + (user.isManager() ? "管理员" : "普通用户"));
//        Log.d("TAG", "avatar=" + user.getAvatar());
        ImageLoadUtil.showImageForUrl(user.getAvatar(), memberHolder.ivFigure, R.drawable.avatar_default);

        memberHolder.btnDelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext).setTitle("是否删除该用户")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (memberListener != null) {
                                    memberListener.onDelMember(user);
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

        memberHolder.btnChangetoManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.isManager()) {
                    new AlertDialog.Builder(mContext).setTitle(mContext.getString(R.string.trans_manager_msg, user.getName()))
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (memberListener != null) {
                                        memberListener.onTransferManager(user);
                                    }
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).show();
                }
            }
        });
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_res_title)
        TextView tvNickname;
        @BindView(R.id.tv_device_id)
        TextView tvUserRole;
        @BindView(R.id.btn_del_user)
        Button btnDelUser;
        @BindView(R.id.btn_changeto_manager)
        Button btnChangetoManager;
        @BindView(R.id.iv_figure)
        ImageView ivFigure;

        public MemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public void setMemberListener(MemberListener listener) {
        this.memberListener = listener;
    }

    public interface MemberListener {
        void onDelMember(User user);

        void onTransferManager(User user);
    }

}
