package com.aiedevice.sdkdemo.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aiedevice.sdkdemo.activity.account.BabyInfoActivity;
import com.aiedevice.sdkdemo.activity.account.BabyListActivity;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.sdk.account.AccountManager;
import com.aiedevice.sdkdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BabyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = BabyListAdapter.class.getSimpleName();

    private List<AccountManager.BabyMessage> mItems;
    private LayoutInflater mLayoutInflater;
    private AccountManager mAccountManager;
    private Handler mHandler;
    private Context mContext;

    public BabyListAdapter(Context context, Handler handler) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mAccountManager = new AccountManager(context);
        mHandler = handler;
    }

    public void setItems(List<AccountManager.BabyMessage> items) {
        mItems = new ArrayList<AccountManager.BabyMessage>();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void cleanItems() {
        if (mItems == null) return;
        mItems.clear();
        notifyDataSetChanged();
    }

    public AccountManager.BabyMessage getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.baby_list_item, parent, false);
        BabyListAdapter.ViewHolder holder = new BabyListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AccountManager.BabyMessage detail = getItem(position);

        BabyListAdapter.ViewHolder mainViewHolder = (BabyListAdapter.ViewHolder) holder;
        mainViewHolder.tvNickname.setText("名字：" + detail.nickName);
        mainViewHolder.tvGender.setText("性别：" + detail.gender);
        mainViewHolder.tvBirthday.setText("出生日期：" + detail.getBirthdayDate());
        mainViewHolder.tvBirthplace.setText("出生地：" + detail.birthplace);

        mainViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAccountManager.deleteBabyInfo(detail.babyID, new ResultListener() {
                    @Override
                    public void onSuccess(ResultSupport resultSupport) {
                        mHandler.sendEmptyMessage(BabyListActivity.MSG_DELETE);
                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.e(TAG, "code = " + code + "；message = " + message);
                    }
                });
            }
        });

        mainViewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BabyInfoActivity.launch(mContext, BabyInfoActivity.MSG_TYPE_EDIT, detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cate_name)
        TextView tvNickname;
        @BindView(R.id.tv_gender)
        TextView tvGender;
        @BindView(R.id.tv_birthday)
        TextView tvBirthday;
        @BindView(R.id.tv_birthplace)
        TextView tvBirthplace;

        @BindView(R.id.btn_delete)
        Button mDelete;
        @BindView(R.id.btn_edit)
        Button mEdit;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
