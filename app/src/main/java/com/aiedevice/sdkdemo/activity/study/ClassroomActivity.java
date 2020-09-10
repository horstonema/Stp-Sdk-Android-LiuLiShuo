package com.aiedevice.sdkdemo.activity.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aiedevice.bean.Result;
import com.aiedevice.common.ResultListener;
import com.aiedevice.sdk.classroom.ClassRoomManager;
import com.aiedevice.sdkdemo.R;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ClassroomActivity extends StpBaseActivity implements ClassRoomManager.ClickReadMsgListener {
    private static final String TAG = ClassroomActivity.class.getSimpleName();

    private static final String OPEN_STATUS = "开启同步课堂";
    private static final String CLOSE_STATUS = "关闭同步课堂";
    private static final int HEART_BEAT_CODE = 100; //心跳
    private static final int HEART_BEAT_ELAPSE = 30000; //30秒

    private Handler mHandler;

    @BindView(R.id.btn_control)
    Button btnControl;
    @BindView(R.id.tv_click_msg)
    TextView tvClickMsg;

    public static void launch(Context context) {
        Intent intent = new Intent(context, ClassroomActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("同步课堂");

        initLogic();
    }

    private void initLogic() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case HEART_BEAT_CODE:
                        ClassRoomManager.getInstance().sendSyncHeartbeat(new ResultListener() {
                            @Override
                            public void onSuccess(Result result) {
                                Log.d(TAG, "[sendHeartbeat] succ result=" + result.getResult());
                            }

                            @Override
                            public void onError(int errCode, String errMsg) {
                                Log.w(TAG, "[sendHearbeat] errCode=" + errCode + " errMsg=" + errMsg);
                            }
                        });
                        mHandler.sendEmptyMessageDelayed(HEART_BEAT_CODE, HEART_BEAT_ELAPSE);
                        break;
                }
            }
        };

        ClassRoomManager.getInstance().setClickReadMsgListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_classroom;
    }

    @OnClick(R.id.btn_control)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_control:
                setSyncSwitch(!ClassRoomManager.getInstance().getReceiveStatus());
                break;
        }
    }

    private void setSyncSwitch(final boolean isOpen) {
        ClassRoomManager.getInstance().syncSwitch(isOpen, new ResultListener() {
            @Override
            public void onSuccess(Result result) {
                Log.d(TAG, "[setSyncSwitch] isOpen=" + isOpen);

                if (isOpen) {
                    //开始发送心跳
                    startHeartbeat();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            btnControl.setText(CLOSE_STATUS);
                        }
                    });
                } else {
                    //停止发送心跳
                    stopHearbeat();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            btnControl.setText(OPEN_STATUS);
                        }
                    });
                }
            }

            @Override
            public void onError(int errCode, String errMsg) {
                Log.e(TAG, "[setSyncSwitch] errCode=" + errCode + " errMsg=" + errMsg);
            }
        });
    }

    private void startHeartbeat() {
        mHandler.sendEmptyMessageDelayed(HEART_BEAT_CODE, HEART_BEAT_ELAPSE);
    }

    private void stopHearbeat() {
        mHandler.removeMessages(HEART_BEAT_CODE);
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRevClickReadMsg(String clickReadMsg) {
        String text = clickReadMsg + "\n" + tvClickMsg.getText().toString();
        tvClickMsg.setText(text);
    }
}
