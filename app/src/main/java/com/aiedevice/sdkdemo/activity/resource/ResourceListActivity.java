package com.aiedevice.sdkdemo.activity.resource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aiedevice.sdkdemo.AppConstant;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.adapter.ResourcePageAdapter;
import com.aiedevice.sdkdemo.bean.PlayResourceData;
import com.aiedevice.sdkdemo.bean.PlayResourceEntity;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.resource.ResourceManager;
import com.aiedevice.sdk.resource.bean.CollectionResourceReq;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ResourceListActivity extends StpBaseActivity {
    private static final String TAG = ResourceListActivity.class.getSimpleName();

    // logic
    private ResourcePageAdapter resourcePageAdapter;
    private int mCid = 0;
    private int mPage = 1;
    private int mResId = 0;
    private String mSrc = "";
    private ResourceManager mResourceManager;

    // view
    @BindView(R.id.rv_res_list)
    public RecyclerView mRvResList;

    public static void launch(Context context) {
        Intent intent = new Intent(context, ResourceListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("资源列表");
        initView();
        initLogic();

        findViewById(R.id.btn_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionResourceReq req = new CollectionResourceReq(mCid, 0);
                addFavorite(req);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.resource_index_layout;
    }

    private void initView() {
        mRvResList.setLayoutManager(new LinearLayoutManager(mContext));
        resourcePageAdapter = new ResourcePageAdapter(mContext);
        mRvResList.setAdapter(resourcePageAdapter);

        resourcePageAdapter.setOnPlayOnClickListener(new ResourcePageAdapter.onPlayOnClickListener() {
            @Override
            public void play(PlayResourceEntity detail) {
                playResource(detail);
            }

            @Override
            public void addFavorite(PlayResourceEntity detail) {
                CollectionResourceReq req = new CollectionResourceReq(mCid, detail.getId(), mSrc);
                ResourceListActivity.this.addFavorite(req);
            }

        });
    }

    private void initLogic() {
        mResourceManager = new ResourceManager(mContext);

        mCid = getIntent().getIntExtra("cid", 0);
        mPage = getIntent().getIntExtra("page", 1);
        mSrc = getIntent().getStringExtra("src");

        refreshResData();
    }

    @OnClick({R.id.btn_stop, R.id.btn_prev, R.id.btn_next, R.id.btn_favorite,
            R.id.btn_state})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_stop:
                stopResource();
                break;

            case R.id.btn_prev:
                playPrev();
                break;

            case R.id.btn_next:
                playNext();
                break;

            case R.id.btn_favorite:

                break;

            case R.id.btn_state:
                getPlayState();
                break;
        }
    }

    private void playPrev() {
        mResourceManager.playResource(AppConstant.PLAY_CONTROL_PREV, mCid, mResId, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("切换上一首成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("切换上一首失败 错误:" + message);
            }
        });
    }

    private void playNext() {
        mResourceManager.playResource(AppConstant.PLAY_CONTROL_NEXT, 0, 0, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("切换下一首成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("切换下一首失败 错误:" + message);
            }
        });
    }

    private void addFavorite(CollectionResourceReq req) {
        if (req == null)
            return;

        ArrayList<CollectionResourceReq> list = new ArrayList<>();
        list.add(req);

        mResourceManager.addCollection(list, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                refreshResData();
                Toaster.show("收藏成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("收藏失败");
            }
        });
    }

    private void delFavorite(final PlayResourceEntity detail) {
        if (detail == null)
            return;

        Log.d(TAG, "[delFavorite] id=" + detail.getId());
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(detail.getId());
        mResourceManager.deleteCollection(list, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("取消收藏成功");
                refreshResData();
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("取消收藏失败 错误：" + message);
            }
        });
    }

    private void stopResource() {
        mResourceManager.stopResource(mResId, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("停止成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("停止播放失败");
            }
        });
    }

    private void playResource(PlayResourceEntity detail) {
        mResourceManager.playResource(0, mCid, detail.getId(), new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("播放成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("播放资源失败 错误：" + message);
            }
        });
    }

    private void getPlayState() {
        mResourceManager.getPlayState(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show(resultSupport.getModel("data").toString());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取播放状态失败 错误:" + message);
            }
        });
    }

    private void refreshResData() {
        Log.d(TAG, "[refreshResData] mCid=" + mCid + " mPage=" + mPage + " mSrc=" + mSrc);
        mResourceManager.getResourceList(mCid, mPage, 20, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                PlayResourceData data = gson.fromJson(resultSupport.getModel("data").toString(), PlayResourceData.class);
                resourcePageAdapter.clearItems();
                resourcePageAdapter.setItems(data.getList());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取资源失败 错误：" + message);
            }
        });
    }
}
