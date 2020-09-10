package com.aiedevice.sdkdemo.activity.sdcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.sdcard.DeviceSdcardManager;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.adapter.LocalPicbookListAdapter;
import com.aiedevice.sdkdemo.bean.sdcard.PicBookData;
import com.aiedevice.sdkdemo.bean.sdcard.PicbookList;
import com.aiedevice.sdkdemo.bean.sdcard.SdcardStatus;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;

import butterknife.BindView;

public class SdcardManagerActivity extends StpBaseActivity implements LocalPicbookListAdapter.LocalPicbookListener {
    private static final String TAG = SdcardManagerActivity.class.getSimpleName();

    // logic
    private DeviceSdcardManager mSdcardManager;
    private Handler mHandler;
    private LocalPicbookListAdapter mPicbookAdapter;

    // view
    @BindView(R.id.tv_sdcard_status)
    TextView tvSdcardStatus;
    @BindView(R.id.rv_picbook_list)
    RecyclerView rvPicbookList;

    public static void launch(Context context) {
        Intent intent = new Intent(context, SdcardManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("设备SD卡管理");
        initView();
        initLogic();
    }

    private void initView() {
        mPicbookAdapter = new LocalPicbookListAdapter(mContext);
        mPicbookAdapter.setLocalPicbookListener(this);
        rvPicbookList.setLayoutManager(new LinearLayoutManager(mContext));
        rvPicbookList.setAdapter(mPicbookAdapter);
    }

    private void initLogic() {
        mHandler = new Handler();
        mSdcardManager = new DeviceSdcardManager(mContext);
        updateSdcardStatus();
        refreshLocalBookList();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_sdcard_manage;
    }

    private void updateSdcardStatus() {
        mSdcardManager.getSdcardInfo(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport result) {
                Log.d(TAG, "[updateSdcardStatus-succ] result=" + result.getData() + " msg=" + result.getMsg()
                        + " result=" + result.getResult());
                Gson gson = GsonUtils.getGson();
                SdcardStatus sdcardStatus = gson.fromJson(result.getData(), SdcardStatus.class);
                updateSdcardStatus(sdcardStatus);
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "[updateSdcardStatus-err] code=" + code + " message=" + message);
            }
        });
    }

    private void updateSdcardStatus(final SdcardStatus sdcardStatus) {
        if (sdcardStatus == null)
            return;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String text = "总容量：" + sdcardStatus.getMemoryTotal() + " MB\n";
                text += "已使用容量： " + sdcardStatus.getMemoryUsed() + " MB\n";
                tvSdcardStatus.setText(text);
            }
        });
    }

    private void refreshLocalBookList() {
        int from = 0;    //最后一条的id，默认值0，表示获取首页
        int size = 20;     //请求条数，整数，可选范围1-50，默认20
        mSdcardManager.getLocalPicbookList(from, size, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport result) {
                Log.d(TAG, "[refreshLocalBookList-succ] result=" + result.getData() + " msg=" + result.getMsg()
                        + " result=" + result.getResult());

                Gson gson = GsonUtils.getGson();
                PicbookList picbookList = gson.fromJson(result.getData(), PicbookList.class);
                mPicbookAdapter.setItems(picbookList.getPicbookList());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "[refreshLocalBookList-err] code=" + code + " message=" + message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    @Override
    public void onDelPicbook(PicBookData picBookData) {
        mSdcardManager.deletePicbook(picBookData.getBookId(), new ResultListener() {
            @Override
            public void onSuccess(ResultSupport result) {
                Log.d(TAG, "[onDelPicbook-succ] result=" + result.getData() + " msg=" + result.getMsg()
                        + " result=" + result.getResult());
                if (result.getResult() == 0) {
                    Toaster.show("删除成功");
                    refreshLocalBookList();
                } else {
                    Toaster.show("删除失败");
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "[onDelPicbook-err] code=" + code + " message=" + message);
                Toaster.show("删除失败");
            }
        });
    }
}
