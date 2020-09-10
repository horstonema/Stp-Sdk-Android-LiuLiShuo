package com.aiedevice.sdkdemo.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.bean.BabyList;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.adapter.BabyListAdapter;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.account.AccountManager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BabyListActivity extends StpBaseActivity {
    private static final String TAG = BabyListActivity.class.getSimpleName();

    // logic
    public static final int MSG_DELETE = 0;
    private AccountManager mAccountManager;
    private BabyListAdapter mBabyAdapter;
    private List<AccountManager.BabyMessage> mtList;

    // view
    @BindView(R.id.recycler_view_baby_list)
    public RecyclerView mRecyclerView;
    private Handler mHandler;

    public static void launch(Context context) {
        Intent intent = new Intent(context, BabyListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("宝宝列表");
        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_baby_list;
    }

    @OnClick(R.id.btn_add_baby)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_baby:
                BabyInfoActivity.launch(BabyListActivity.this, 0, null);
                break;
        }
    }

    private void initView() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_DELETE:
                        refreshBabyList();
                        break;
                    default:
                        break;
                }
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBabyAdapter = new BabyListAdapter(this, mHandler);
        mRecyclerView.setAdapter(mBabyAdapter);
    }

    private void initLogic() {
        mAccountManager = new AccountManager(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBabyList();
    }

    private void refreshBabyList() {
        mAccountManager.getBabyList(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Log.d(TAG, "[refreshBabyList] onSuccess " + resultSupport.getModel("data").toString());
                Gson gson = GsonUtils.getGson();
                String json = resultSupport.getModel("data").toString();
                try {
                    mtList = gson.fromJson(json, BabyList.class).babyList;
                    mBabyAdapter.cleanItems();
                    mBabyAdapter.setItems(mtList);
                } catch (Exception e) {
                    Log.e(TAG, "[refreshBabyList] err=" + e.toString());
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取宝宝信息失败 错误：" + message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
