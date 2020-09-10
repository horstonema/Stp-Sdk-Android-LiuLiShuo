package com.aiedevice.sdkdemo.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.sdk.account.LoginManager;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordActivity extends StpBaseActivity {
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();

    // logic
    private LoginManager mLoginManager;

    // views
    @BindView(R.id.et_old_passwd)
    public EditText mEtOldPasswd;
    @BindView(R.id.et_new_passwd)
    public EditText mNewPasswd;
    @BindView(R.id.et_new_passwd_again)
    public EditText mNewPasswdAgain;

    public static void launch(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("修改密码");
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_pwd;
    }

    @OnClick({R.id.btn_change_passwd})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_passwd:
                changePasswd();
                break;
        }
    }

    private void changePasswd() {
        String oldPasswd = mEtOldPasswd.getText().toString();
        String newPasswd = mNewPasswd.getText().toString();
        String newPasswdAgain = mNewPasswdAgain.getText().toString();

        if (newPasswd.equals(newPasswdAgain)) {
            mLoginManager.updatePwd(oldPasswd, newPasswd, new ResultListener() {
                @Override
                public void onSuccess(ResultSupport resultSupport) {
                    Toaster.show("修改密码成功");
                }

                @Override
                public void onError(int code, String message) {
                    Log.d(TAG, "code = " + code + "；message = " + message);
                    Toaster.show("修改密码失败 错误：" + message);
                }
            });
        } else {
            Toaster.show("两次密码输入不一致");
        }
    }

    private void initLogic() {
        mLoginManager = new LoginManager(mContext);
    }
}
