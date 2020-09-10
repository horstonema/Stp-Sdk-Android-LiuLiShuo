package com.aiedevice.sdkdemo.activity.resource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.adapter.ResIndexAdapter;
import com.aiedevice.sdkdemo.bean.HomeCatModluesData;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.resource.ResourceManager;

import butterknife.BindView;

public class ResourceIndexActivity extends StpBaseActivity {
    private static final String TAG = ResourceIndexActivity.class.getSimpleName();

    // logic
    private ResIndexAdapter mResIndexAdapter;
    private ResourceManager mResourceManager;

    // view
    @BindView(R.id.rv_res_list)
    public RecyclerView mRecyclerView;

    public static void launch(Context context, int index) {
        Log.d(TAG, "[launch] index=" + index);
        Intent intent = new Intent(context, ResourceIndexActivity.class);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("资源页面");
        initView();
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_resource_index;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mResIndexAdapter = new ResIndexAdapter(mContext);
        mRecyclerView.setAdapter(mResIndexAdapter);
    }

    private void initLogic() {
        mResourceManager = new ResourceManager(this);

        int index = getIntent().getIntExtra("index", 0);
        refreshResource(index);
    }

    private void refreshResource(int index) {
        if (index == 0) {
            mResourceManager.getCustomAlbumList(new ResultListener() {
                @Override
                public void onSuccess(ResultSupport resultSupport) {
                    startAlbumActivity(resultSupport);
                }

                @Override
                public void onError(int code, String message) {
                    Log.d(TAG, "code = " + code + "；message = " + message);
                    Toaster.show("获取自定义专辑失败 错误：" + message);
                }
            });
        } else if (index == -1) {
//            getCateResource(132962, 1);
            getCateResource(0, 1);
        } else {
            mResourceManager.getAlbumList(index, 1, new ResultListener() {
                @Override
                public void onSuccess(ResultSupport resultSupport) {
                    startAlbumActivity(resultSupport);
                }

                @Override
                public void onError(int code, String message) {
                    Log.d(TAG, "code = " + code + "；message = " + message);
                    Toaster.show("获取专辑失败 错误：" + message);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.addFavorite:
                Intent intent = new Intent(ResourceIndexActivity.this, FavoriteActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void startAlbumActivity(ResultSupport resultSupport) {
        Gson gson = GsonUtils.getGson();
        HomeCatModluesData modluesData = gson.fromJson(resultSupport.getModel("data").toString(), HomeCatModluesData.class);
        mResIndexAdapter.setItems(modluesData.getCategories());
    }

    /**
     * act 如果为cate就是多级歌单
     * 专辑列表(系列歌单)
     *
     * @param pId       专辑id
     * @param pageIndex 页数下标从1开始
     */
    private void getCateResource(int pId, int pageIndex) {
        mResourceManager.getCateResource(pId, pageIndex, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                startAlbumActivity(resultSupport);
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取专辑列表(系列歌单)资源失败");
            }
        });
    }
}
