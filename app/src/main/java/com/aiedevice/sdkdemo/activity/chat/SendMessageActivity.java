package com.aiedevice.sdkdemo.activity.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.utils.FileUtil;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.sdk.device.DeviceManager;
import com.aiedevice.sdkdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SendMessageActivity extends StpBaseActivity {
    private static final String TAG = SendMessageActivity.class.getSimpleName();

    // logic
    private DeviceManager mDeviceManager;

    // view
    @BindView(R.id.et_text)
    public EditText etText;

    public static void launch(Context context) {
        Intent intent = new Intent(context, SendMessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("发送消息");
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_send_message;
    }

    private void initLogic() {
        mDeviceManager = new DeviceManager(mContext);
    }

    @OnClick({R.id.btn_send_voice, R.id.btn_send_text})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_voice:
                sendVoiceMessage();
                break;

            case R.id.btn_send_text:
                sendTextMessage();
                break;
        }
    }

    private void sendTextMessage() {
        if (TextUtils.isEmpty(etText.getText())) {
            return;
        }

        String text = etText.getText().toString().trim();
        mDeviceManager.sendTTSText(text, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("发送成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("发送失败 错误:" + message);
            }
        });
    }

    private void sendVoiceMessage() {
        String assetFilename = "jintiantianqi.mp3";
        String sdcardFilename = Environment.getExternalStorageDirectory().getPath() + "/" + assetFilename;
        if (!FileUtil.copyAssetFileToSdcard(mContext, assetFilename, sdcardFilename)) {
            Toaster.show("文件拷贝错误");
            return;
        }

        mDeviceManager.sendVoice(sdcardFilename, 10, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("发送成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("发送失败 错误:" + message);
            }
        });
    }
}
