package com.aiedevice.sdkdemo.activity.sdcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.sdcard.DeviceSdcardManager;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.adapter.PicbookListAdapter;
import com.aiedevice.sdkdemo.bean.sdcard.PicBookData;
import com.aiedevice.sdkdemo.bean.sdcard.PicBookDetail;
import com.aiedevice.sdkdemo.bean.sdcard.PicbookList;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

public class PicbookUploadActivity extends StpBaseActivity implements PicbookListAdapter.PicbookItemListener {
    private static final String TAG = PicbookUploadActivity.class.getSimpleName();

    // logic
    private DeviceSdcardManager mSdcardManager;
    private PicbookListAdapter mPicbookAdapter;

    // view
    @BindView(R.id.rv_picbook_list)
    RecyclerView rvPicbookList;
    @BindView(R.id.et_search_keyword)
    EditText etSearchKeyword;
    @BindView(R.id.tv_header)
    TextView tvHeader;

    public static void launch(Context context) {
        Intent intent = new Intent(context, PicbookUploadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("绘本上传");
        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_picbook_upload;
    }

    private void initView() {
        mPicbookAdapter = new PicbookListAdapter(mContext);
        mPicbookAdapter.setPicbookItemListener(this);
        rvPicbookList.setLayoutManager(new LinearLayoutManager(mContext));
        rvPicbookList.setAdapter(mPicbookAdapter);
    }

    private void initLogic() {
        mSdcardManager = new DeviceSdcardManager(mContext);

        refreshAllBookList();
    }

    @OnClick(R.id.btn_search)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                doSearch();
                break;
        }
    }

    private void doSearch() {
        if (TextUtils.isEmpty(etSearchKeyword.getText())) {
            tvHeader.setText("所有绘本");
            refreshAllBookList();
            return;
        }

        tvHeader.setText("搜索到绘本有");
        String keyword = etSearchKeyword.getText().toString();
        mSdcardManager.searchPicbook(keyword, new ResultListener() {
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

    private void refreshAllBookList() {
        int from = 0;    //最后一条的id，默认值0，表示获取首页
        int size = 20;     //请求条数，整数，可选范围1-50，默认20
        mSdcardManager.getAllPicbookList(from, size, new ResultListener() {
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
    public void onUploadPicbook(PicBookData picbook) {
        mSdcardManager.uploadPicbook(picbook.getBookId(), new ResultListener() {
            @Override
            public void onSuccess(ResultSupport result) {
                Log.d(TAG, "[onUploadPicbook-succ] " + " result=" + result.getResult() +
                        " msg = " + result.getMsg());
                if (result.getResult() == 0) {
                    Toaster.show("添加成功");
                } else {
                    Toaster.show("添加失败");
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "[onUploadPicbook-err] code=" + code + " message=" + message);
                Toaster.show("添加失败");
            }
        });
    }

    @Override
    public void onShowDetail(PicBookData picbook) {
        mSdcardManager.getPicbookDetail(picbook.getBookId(), new ResultListener() {
            @Override
            public void onSuccess(ResultSupport result) {
                Log.d(TAG, "[onShowDetail-succ] result=" + result.getData() + " msg=" + result.getMsg()
                        + " result=" + result.getResult());

                Gson gson = GsonUtils.getGson();
                PicBookDetail picbook = gson.fromJson(result.getData(), PicBookDetail.class);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                StringBuffer sb = new StringBuffer();
                sb.append("绘本名：" + picbook.getBookName() + "\n");
                sb.append("作者：" + picbook.getAuthor() + "\n");
                sb.append("封面url：" + picbook.getCover() + "\n");
                sb.append("封面url：" + picbook.getStatus() + "\n");
                sb.append("内容简介：" + picbook.getReadGuideHtml() + "\n");
                builder.setMessage(sb.toString());
                builder.show();
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "[getPicbookDetail-err] code=" + code + " message=" + message);
            }
        });
    }
}
