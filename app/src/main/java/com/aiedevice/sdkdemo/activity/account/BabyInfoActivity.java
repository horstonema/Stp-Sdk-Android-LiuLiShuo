package com.aiedevice.sdkdemo.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.sdk.account.AccountManager;
import com.aiedevice.sdkdemo.R;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class BabyInfoActivity extends StpBaseActivity {
    private static final String TAG = BabyInfoActivity.class.getSimpleName();

    private static final String MSG_TYPE = "type";
    private static final String MSG_BABY_NAME = "name";
    private static final String MSG_BABY_ID = "id";
    private static final String MSG_BABY_BIRTHDAY = "birthday";
    private static final String MSG_BABY_GENDER = "gender";
    private static final String MSG_BABY_BIRTHPLACE = "birthplace";
    public static final int MSG_TYPE_ADD = 0;
    public static final int MSG_TYPE_EDIT = 1;

    // logic
    private AccountManager mAccountManager;
    private String mBabyID;
    private int mType;

    // view
    @BindView(R.id.et_name)
    public EditText etBabyName;
    @BindView(R.id.et_eng_name)
    public EditText etBabyEngName;
    @BindView(R.id.et_birthday)
    public EditText etBirthday;
    @BindView(R.id.et_birthplace)
    public EditText etBirthplace;
    @BindView(R.id.rb_boy)
    public RadioButton rb_boy;
    @BindView(R.id.rb_gril)
    public RadioButton rb_gril;

    public static void launch(Context context, int type, AccountManager.BabyMessage info) {
        Intent intent = new Intent(context, BabyInfoActivity.class);
        intent.putExtra(MSG_TYPE, type);
        if (type == MSG_TYPE_EDIT) {
            intent.putExtra(MSG_BABY_NAME, info.nickName);
            intent.putExtra(MSG_BABY_ID, info.babyID);
            intent.putExtra(MSG_BABY_BIRTHDAY, info.getBirthdayDate());
            intent.putExtra(MSG_BABY_GENDER, info.gender);
            intent.putExtra(MSG_BABY_BIRTHPLACE, info.birthplace);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("宝宝信息");
        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_baby_info;
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent == null)
            return;

        mType = intent.getIntExtra(MSG_TYPE, 0);
        if (mType == 1) {
            etBabyName.setText(intent.getStringExtra(MSG_BABY_NAME));
            etBirthday.setText(intent.getStringExtra(MSG_BABY_BIRTHDAY));
            etBirthplace.setText(intent.getStringExtra(MSG_BABY_BIRTHPLACE));
            mBabyID = intent.getStringExtra(MSG_BABY_ID);
            if (intent.getStringExtra(MSG_BABY_GENDER).equals("boy")) {
                rb_boy.setChecked(true);
            } else {
                rb_gril.setChecked(true);
            }

        }
    }

    private void initLogic() {
        mAccountManager = new AccountManager(mContext);
    }

    @OnClick(R.id.btn_save)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                saveInfo();
                break;
        }
    }

    private String getBirthday() {
        if (TextUtils.isEmpty(etBirthday.getText()))
            return null;

        String birthdayStr = etBirthday.getText().toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.parse(birthdayStr);
            return birthdayStr;
        } catch (Exception e) {
            return null;
        }
    }

    private void saveInfo() {
        AccountManager.BabyMessage info = new AccountManager.BabyMessage();
        String birthday = getBirthday();
        if (TextUtils.isEmpty(birthday)) {
            Toaster.show("请输入合法日期 yyyy-MM-dd");
            return;
        }

        if (TextUtils.isEmpty(etBabyName.getText()))
            return;

        info.birthday = birthday;
        info.nickName = etBabyName.getText().toString();
        info.gender = rb_boy.isChecked() ? AccountManager.BabyMessage.BOY : AccountManager.BabyMessage.GIRL;
        info.birthplace = etBirthplace.getText().toString();

        if (mType == MSG_TYPE_ADD) {
            mAccountManager.addBabyInfo(info, new ResultListener() {
                @Override
                public void onSuccess(ResultSupport resultSupport) {
                    Toaster.show("添加宝宝信息成功");
                    finish();
                }

                @Override
                public void onError(int code, String message) {
                    Log.d(TAG, "code = " + code + "；message = " + message);
                    Toaster.show("添加宝宝信息失败 错误:" + message);
                }
            });
        } else {
            info.babyID = mBabyID;
            mAccountManager.editBabyInfo(info, new ResultListener() {
                @Override
                public void onSuccess(ResultSupport resultSupport) {
                    Toaster.show("修改宝宝信息成功");
                    finish();
                }

                @Override
                public void onError(int code, String message) {
                    Log.d(TAG, "code = " + code + "；message = " + message);
                    Toaster.show("修改宝宝信息失败 错误:" + message);
                }
            });
        }
    }
}
