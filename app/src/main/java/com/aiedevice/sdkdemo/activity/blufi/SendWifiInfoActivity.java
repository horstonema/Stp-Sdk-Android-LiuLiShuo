package com.aiedevice.sdkdemo.activity.blufi;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiedevice.sdkdemo.R;
import com.aiedevice.sdkdemo.activity.LoginActivity;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.presenter.SendWifiInfoActivityPresenterImpl;
import com.aiedevice.sdkdemo.view.SendWifiInfoActivityView;
import com.esp.iot.blufi.communiation.BlufiConfigureParams;

import butterknife.BindView;
import butterknife.OnClick;

public class SendWifiInfoActivity extends StpBaseActivity implements SendWifiInfoActivityView {
    private static final String TAG = SendWifiInfoActivity.class.getSimpleName();
    public static final String EXTRA_DEVICE = "EXTRA_DEVICE";
    public static final String EXTRA_PARAM = "EXTRA_PARAM";

    // logic
    private BluetoothDevice mDevice;
    private BlufiConfigureParams mParams;
    private SendWifiInfoActivityPresenterImpl presenter;
    private boolean mIsChangeNetwork;
    private Handler uiHandler;

    // view
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.btn_retry)
    Button btnRetry;

    public static void launch(Context context, BluetoothDevice device,
                              BlufiConfigureParams configureParams, boolean isChangeNetwork) {
        Intent intent = new Intent(context, SendWifiInfoActivity.class);
        intent.putExtra(EXTRA_DEVICE, device);
        intent.putExtra(EXTRA_PARAM, configureParams);
        intent.putExtra("isChangeNetwork", isChangeNetwork);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();

        mIsChangeNetwork = getIntent().getBooleanExtra("isChangeNetwork", false);
        mDevice = getIntent().getParcelableExtra(EXTRA_DEVICE);
        mParams = (BlufiConfigureParams) getIntent().getSerializableExtra(EXTRA_PARAM);
        initLogic();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_send_wifi_info;
    }

    private void initLogic() {
        uiHandler = new Handler();

        presenter.startConfigure(mDevice, mParams);
    }

    protected void attachPresenter() {
        presenter = new SendWifiInfoActivityPresenterImpl(getApplicationContext());
        presenter.attachView(this);
    }

    protected void detachPresenter() {
        presenter.detachView();
        presenter.stopConfigure();
        presenter = null;
    }

    @Override
    public void onSendWifiSuccessful() {
        String text;
        if (mIsChangeNetwork) {
            text = "恭喜！ 修改网络成功";
        } else {
            text = "恭喜！ 绑定设备成功";
        }

        tvState.setText(text);

        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @OnClick(R.id.btn_retry)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_retry:
                btnRetry.setVisibility(View.GONE);
                tvState.setText("网络连接中…");
                ivStatus.setImageResource(R.drawable.config_net_connecting);

                attachPresenter();
                presenter.startConfigure(mDevice, mParams);
                break;
        }
    }

    @Override
    public void onSendWifiFailure(int errCode, String errMsg) {
        Log.e(TAG, "[onSendWifiFailure] errCode=" + errCode + " errMsg=" + errMsg);
        tvState.setText("联网失败. 错误：" + errMsg);
        ivStatus.setImageResource(R.drawable.config_net_connect_fail);
        btnRetry.setVisibility(View.VISIBLE);
        presenter.stopConfigure();
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showEmpty(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachPresenter();
    }
}
