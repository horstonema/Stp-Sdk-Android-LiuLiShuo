package com.aiedevice.sdkdemo.activity.resource;

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
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.adapter.HistoryListAdapter;
import com.aiedevice.sdkdemo.bean.HistoryDetail;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.resource.ResourceManager;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryActivity extends StpBaseActivity {
    private static final String TAG = HistoryActivity.class.getSimpleName();

    // logic
    public static final int MSG_DELETE_HISTORY = 0;
    private HistoryListAdapter mHistoryAdapter;
    private ResourceManager mResourceManager;
    private Handler mHandler;

    // view
    @BindView(R.id.rv_history_list)
    public RecyclerView mRvHistoryList;

    public static void launch(Context context) {
        Intent intent = new Intent(context, HistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("历史记录");
        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_history_list;
    }

    private void initView() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_DELETE_HISTORY:
                        getHistoryList();
                        break;
                    default:
                        break;
                }
            }
        };

        mHistoryAdapter = new HistoryListAdapter(this, mHandler);
        mRvHistoryList.setAdapter(mHistoryAdapter);
        mRvHistoryList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initLogic() {
        mResourceManager = new ResourceManager(mContext);
        getHistoryList();
    }

    @OnClick(R.id.btn_clear_history)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_history:
                cleanHistory();
                break;
        }
    }

    private void cleanHistory() {
        mResourceManager.cleanPlayHistory(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("清空历史信息成功");
                getHistoryList();
            }

            @Override
            public void onError(int errCode, String message) {
                Toaster.show("清空历史信息失败 错误：" + message);
            }
        });
    }

    private void getHistoryList() {
        mResourceManager.getPlayHistory(0, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                HistoryDetail detail = gson.fromJson(resultSupport.getModel("data").toString(), HistoryDetail.class);
                mHistoryAdapter.setItems(detail.getList());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取历史信息失败 错误:" + message);
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
