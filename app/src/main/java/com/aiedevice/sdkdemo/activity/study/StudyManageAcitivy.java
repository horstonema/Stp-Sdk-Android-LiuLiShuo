package com.aiedevice.sdkdemo.activity.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.jssdk.study.StudyReportManager;
import com.aiedevice.sdk.study.HistoryReportManager;
import com.aiedevice.sdk.study.TodayReportManager;
import com.aiedevice.sdkdemo.R;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.bean.DateUtil;
import com.aiedevice.sdkdemo.bean.study.FollowReadResult;
import com.aiedevice.sdkdemo.bean.study.TodayAchievement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StudyManageAcitivy extends StpBaseActivity {
    private static final String TAG = StudyManageAcitivy.class.getSimpleName();
    private static final int DEFAULT_PAGE_SIZE = 10;

    // logic
    private TodayReportManager mTodayManager;
    private HistoryReportManager mHisReportManager;
    private StudyReportManager mStudyManager;
    private Handler mHandler;

    // view
    @BindView(R.id.et_start_date)
    EditText etStartDate;
    @BindView(R.id.et_end_date)
    EditText etEndDate;
    @BindView(R.id.et_page_size)
    EditText etPageSize;
    @BindView(R.id.tv_result)
    TextView tvResult;

    public static void launch(Context context) {
        Intent intent = new Intent(context, StudyManageAcitivy.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("学习报告");
        tvResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        initLogic();
        initView();
    }

    private void initLogic() {
        mTodayManager = new TodayReportManager();
        mHisReportManager = new HistoryReportManager();
        mHandler = new Handler();
        mStudyManager = new StudyReportManager();
    }

    private void initView() {
        etStartDate.setText(com.aiedevice.appcommon.util.DateUtil.getLastweek(new Date()));
        etEndDate.setText(DateUtil.getDate());
        etPageSize.setText(String.valueOf(DEFAULT_PAGE_SIZE));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_study_manage;
    }

    @OnClick({R.id.btn_today_achievement, R.id.btn_history_achievement, R.id.btn_follow_reading,
            R.id.btn_history_follow, R.id.btn_study_data, R.id.btn_follow_data})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_today_achievement:
                getTodayAchievement();
                break;

            case R.id.btn_history_achievement:
                getHistoryAchievement();
                break;

            case R.id.btn_follow_reading:
                getTodayFollowReadInfo();
                break;

            case R.id.btn_history_follow:
                getFollowReadHistory();
                break;

            case R.id.btn_study_data:
                getStudyAchieve();
                break;

            case R.id.btn_follow_data:
                getFollowReadData();
                break;
        }
    }

    private void getStudyAchieve() {
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        ResultListener listener = new ResultListener() {
            @Override
            public void onSuccess(final ResultSupport result) {
                Log.d(TAG, "[getStudyAchieve-succ] code=" + result.getResult() + " msg=" + result.getMsg()
                        + " data=" + result.getData());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(result.getData());
                    }
                });
            }

            @Override
            public void onError(int errCode, String errMsg) {
                Log.e(TAG, "[getStudyAchieve-fail] errCode=" + errCode + " errMsg=" + errMsg);
            }
        };

        if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
            //根据时间获取学习数据
            mStudyManager.getStudyAchieveData(startDate, endDate, listener);
        } else {
            //根据分页获取学习数据
            int from = 0;
            int size = Integer.parseInt(etPageSize.getText().toString());
            mStudyManager.getStudyAchieveData(from, size, listener);
        }
    }

    private void getFollowReadData() {
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        ResultListener listener = new ResultListener() {
            @Override
            public void onSuccess(final ResultSupport result) {
                Log.d(TAG, "[getFollowReadData-succ] code=" + result.getResult() + " msg=" + result.getMsg()
                        + " data=" + result.getData());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(result.getData());
                    }
                });
            }

            @Override
            public void onError(int errCode, String errMsg) {
                Log.e(TAG, "[getFollowReadData-fail] errCode=" + errCode + " errMsg=" + errMsg);
            }
        };

        if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
            //根据时间获取跟读数据
            mStudyManager.getFollowReadData(startDate, endDate, listener);
        } else {
            //根据分页获取跟读数据
            int from = 0;
            int size = Integer.parseInt(etPageSize.getText().toString());
            mStudyManager.getFollowReadData(from, size, listener);
        }
    }

    private void getFollowReadHistory() {
        mHisReportManager.getLastWeekFollowReadHistory(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport result) {
                Log.d(TAG, "[onSuccess] code=" + result.getResult() + " msg=" + result.getMsg()
                        + " data=" + result.getData());

                StringBuffer sb = new StringBuffer("最近一周跟读数据\n");
                List<FollowReadResult> followResults = parseFollowReadResults(result.getData());
                for (FollowReadResult frr : followResults) {
                    sb.append("日期：" + DateUtil.formatDate(DateUtil.DEFAULT_DATE_FORMAT, frr.getDate()) + "\n");
                    sb.append("跟读次数：" + frr.getFollowReadCount() + "\n");
                    sb.append("知识点： {\n");
                    for (FollowReadResult.KnowledgePoint point : frr.getKnowledgePoints()) {
                        sb.append("\t单词：" + point.getWordName() + "  得分：" + point.getScore() + "  发音:"
                                + point.getPronounceUrl());
                    }
                    sb.append("}\n");
                }
                updateResult(sb.toString());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "[onError] code=" + code + " message=" + message);
            }
        });
    }

    private void getTodayAchievement() {
        mTodayManager.getTodayStudyScore(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport result) {
                Log.d(TAG, "[onSuccess] code=" + result.getResult() + " msg=" + result.getMsg()
                        + " data=" + result.getData());
                TodayAchievement todayAhieve = parseTodayAchievement(result.getData());
                if (todayAhieve != null) {
                    final StringBuffer sb = new StringBuffer();
                    sb.append("今日阅读次数：" + todayAhieve.getReadCount() + "\n");
                    sb.append("今日点击次数：" + todayAhieve.getClickCount() + "\n");
                    sb.append("今日学习时长：" + todayAhieve.getStudyTime() + "\n");
                    sb.append("点读绘本点读次数：" + todayAhieve.getBookClickCount() + "\n");
                    updateResult(sb.toString());
                } else {
                    updateResult("");
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "[onError] code=" + code + " message=" + message);
            }
        });
    }

    private void getHistoryAchievement() {
        updateResult("");

        //最近一周绘本阅读
        mHisReportManager.getLastWeekBookReadHistory(new MyResultListener("最近一周绘本阅读：\n"));

        //最近一周点读次数
        mHisReportManager.getLastWeekClickReadHistory(new MyResultListener("最近一周点读次数：\n"));

        //最近一周学习时长
        mHisReportManager.getLastWeekStudyTimeHistory(new MyResultListener("最近一周学习时长：\n"));
    }

    private void updateResult(final String result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (result != null) {
                    tvResult.setText(result);
                }
            }
        });
    }

    private void appendResult(final String result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (result != null) {
                    String tmp = tvResult.getText().toString();
                    tmp += result + "\n";
                    tvResult.setText(tmp);
                }
            }
        });
    }

    private void getTodayFollowReadInfo() {
        mTodayManager.getTodayFollowRead(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport result) {
                Log.d(TAG, "[onSuccess] code=" + result.getResult() + " msg=" + result.getMsg()
                        + " data=" + result.getData());

                FollowReadResult frr = parseFollowReadResult(result.getData());
                if (frr != null) {
                    final StringBuffer sb = new StringBuffer();
                    sb.append("日期：" + DateUtil.formatDate(DateUtil.DEFAULT_DATE_FORMAT, frr.getDate()) + "\n");
                    sb.append("跟读次数：" + frr.getFollowReadCount() + "\n");
                    sb.append("知识点： {\n");
                    for (FollowReadResult.KnowledgePoint point : frr.getKnowledgePoints()) {
                        sb.append("\t单词：" + point.getWordName() + "  得分：" + point.getScore() + "  发音:"
                                + point.getPronounceUrl());
                    }
                    sb.append("}\n");
                    updateResult(sb.toString());
                } else {
                    updateResult("");
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "[onError] code=" + code + " message=" + message);
            }
        });
    }

    TodayAchievement parseTodayAchievement(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr))
            return null;

        try {
            JSONArray dataArr = new JSONArray(jsonStr);
            if (dataArr == null || dataArr.length() == 0)
                return null;

            JSONObject dataObj = (JSONObject) dataArr.get(0);
            JSONObject extraObj = dataObj.getJSONObject("extra");
            int pointReadingCnt = extraObj.getInt("pointReadingCnt");
            int bookCnt = extraObj.getInt("bookCnt");
            int duration = extraObj.getInt("duration");

            TodayAchievement todayAch = new TodayAchievement();
            JSONArray books = dataObj.getJSONArray("books");
            if (books != null && books.length() > 0) {
                JSONObject book = books.getJSONObject(0);
                String thumbUrl = book.getString("icon");
                String bookName = book.getString("name");
                int bookClickCount = book.getInt("playCnt");
                todayAch.setThumbUrl(thumbUrl);
                todayAch.setBookName(bookName);
                todayAch.setBookClickCount(bookClickCount);
            }

            todayAch.setStudyTime(duration);
            todayAch.setClickCount(pointReadingCnt);
            todayAch.setReadCount(bookCnt);

            return todayAch;
        } catch (Exception e) {
            Log.e(TAG, "[parseTodayAchievement] err=" + e.toString() + " jsonStr=" + jsonStr);
            return null;
        }
    }

    private FollowReadResult parseFollowReadResult(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr))
            return null;

        try {
            JSONArray dataArr = new JSONArray(jsonStr);
            if (dataArr == null || dataArr.length() == 0)
                return null;

            JSONObject dataObj = (JSONObject) dataArr.get(0);
            JSONObject extraObj = dataObj.getJSONObject("extra");
            Date date = DateUtil.parseDate(dataObj.getString("name"));
            if (date == null) {
                Log.w(TAG, "[parseFollowReadResult] parse date fail. jsonStr=" + jsonStr);
                return null;
            }

            int readCount = extraObj.getInt("cnt");
            FollowReadResult frr = new FollowReadResult();
            frr.setDate(date);
            frr.setFollowReadCount(readCount);

            JSONArray list = extraObj.getJSONArray("list");
            List<FollowReadResult.KnowledgePoint> kpList = new ArrayList<>();
            for (int i = 0; i < list.length(); i++) {
                JSONObject knowledgeObj = (JSONObject) list.get(i);
                if (knowledgeObj == null)
                    continue;

                String wordName = knowledgeObj.getString("text");
                String pronounceUrl = knowledgeObj.getString("url");
                int score = knowledgeObj.getInt("score");
                FollowReadResult.KnowledgePoint point = new FollowReadResult.KnowledgePoint(wordName, pronounceUrl, score);
                kpList.add(point);
            }
            frr.setKnowledgePoints(kpList);

            return frr;
        } catch (Exception e) {
            Log.e(TAG, "[parseTodayAchievement] err=" + e.toString() + " jsonStr=" + jsonStr);
            return null;
        }
    }

    private String parseLastWeekHistory(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr))
            return null;

        StringBuffer sb = new StringBuffer();
        try {
            JSONObject dataObj = new JSONObject(jsonStr);
            int total = dataObj.getInt("total");
            JSONArray list = dataObj.getJSONArray("list");

            sb.append("总计：" + total + "\n");
            for (int i = 0; i < list.length(); i++) {
                JSONObject countObj = list.getJSONObject(i);
                String dt = countObj.getString("day");
                int count = countObj.getInt("value");
                sb.append("日期：" + dt + "  次数:" + count + "\n");
            }

            return sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "[parseLastWeekHistory] err=" + e.toString() + " jsonStr=" + jsonStr);
            return null;
        }
    }

    class MyResultListener extends ResultListener {
        private String title;

        public MyResultListener(String title) {
            this.title = title;
        }

        @Override
        public void onSuccess(ResultSupport result) {
            Log.d(TAG, "[onSuccess] code=" + result.getResult() + " msg=" + result.getMsg()
                    + " data=" + result.getData());

            String text = title;
            text += parseLastWeekHistory(result.getData());
            appendResult(text);
        }

        @Override
        public void onError(int code, String message) {
            Log.d(TAG, "[onError] code=" + code + " message=" + message);
        }
    }

    private List<FollowReadResult> parseFollowReadResults(String jsonStr) {
        try {
            List<FollowReadResult> follReadResults = new ArrayList<>();
            JSONArray dataObj = new JSONArray(jsonStr);
            for (int i = 0; i < dataObj.length(); i++) {
                JSONObject oneDayObj = dataObj.getJSONObject(i);
                Date date = DateUtil.parseDate(oneDayObj.getString("name"));

                JSONObject extra = safeConvertJsonObject(oneDayObj, "extra");
                if (extra == null) {
                    FollowReadResult frr = new FollowReadResult();
                    frr.setDate(date);
                    frr.setFollowReadCount(0);
                    frr.setKnowledgePoints(new ArrayList<FollowReadResult.KnowledgePoint>());
                    follReadResults.add(frr);
                    continue;
                }
                int readCount = extra.getInt("cnt");

                JSONArray list = extra.getJSONArray("list");
                List<FollowReadResult.KnowledgePoint> kpList = new ArrayList<>();
                for (int j = 0; j < list.length(); j++) {
                    JSONObject oneResult = list.getJSONObject(j);
                    String wordName = oneResult.getString("text");
                    String pronounceUrl = oneResult.getString("url");
                    int score = oneResult.getInt("score");
                    FollowReadResult.KnowledgePoint point = new FollowReadResult.KnowledgePoint(wordName, pronounceUrl, score);
                    kpList.add(point);
                }

                FollowReadResult frr = new FollowReadResult();
                frr.setDate(date);
                frr.setFollowReadCount(readCount);
                frr.setKnowledgePoints(kpList);
                follReadResults.add(frr);
            }

            return follReadResults;
        } catch (Exception e) {
            Log.e(TAG, "[parseLastWeekHistory] err=" + e.toString() + " jsonStr=" + jsonStr);
            return null;
        }
    }

    private JSONObject safeConvertJsonObject(JSONObject jsonObj, String key) {
        try {
            return jsonObj.getJSONObject(key);
        } catch (Exception e) {
            return null;
        }
    }
}
