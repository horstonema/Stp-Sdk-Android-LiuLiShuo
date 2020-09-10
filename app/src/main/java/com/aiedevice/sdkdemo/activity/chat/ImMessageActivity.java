package com.aiedevice.sdkdemo.activity.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.adapter.ChatAdapter;
import com.aiedevice.sdkdemo.bean.ChatData;
import com.aiedevice.sdkdemo.bean.ChatEntity;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.device.DeviceManager;

import butterknife.BindView;

public class ImMessageActivity extends StpBaseActivity {
    private static final String TAG = ImMessageActivity.class.getSimpleName();

    // logic
    private DeviceManager mDeviceManager;
    private ChatAdapter mChatAdapter;

    // view
    @BindView(R.id.rv_res_list)
    public RecyclerView mRecyclerView;
    @BindView(R.id.text_view)
    public TextView mTextView;

    public static void launch(Context context) {
        Intent intent = new Intent(context, ImMessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("聊天记录");
        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_im_message_list;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatAdapter = new ChatAdapter(mContext);
        mRecyclerView.setAdapter(mChatAdapter);
    }

    private void initLogic() {
        mDeviceManager = new DeviceManager(mContext);

        getMessageList();
    }

    private void getMessageList() {
        mDeviceManager.getChatMessageList(0, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                ChatData data = gson.fromJson(resultSupport.getModel("data").toString(), ChatData.class);
                Log.i(TAG, "chat total=" + data.getTotal());
                mTextView.setText("消息总数:" + data.getTotal());
                for (ChatEntity entity : data.getChatEntityList()) {
                    Log.i(TAG, "content=" + entity.getContent() + " id=" + entity.getId());
                }

                mChatAdapter.clearItems();
                mChatAdapter.setItems(data.getChatEntityList());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取IM失败 错误:" + message);
            }
        });
    }
}
