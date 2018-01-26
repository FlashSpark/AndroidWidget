package com.mt.android.finance.miuitargetwidget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mt.android.finance.miuitargetwidget.tool.DirectionUtil;
import com.mt.android.finance.miuitargetwidget.tool.MtTimer;

import java.util.Locale;

public class MainUIActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private static final String TAG = "Gesture";
    private static final int SECOND = 1000;
    private static final int CIRCLE = 100;

    MtTimer timer;
    int curMinute;
    int curSecond;
    int totalSecond;

    ImageButton btnStart;
    ImageButton btnStop;

    private GestureDetector mGestureDetector;
    private MoveEventFilter moveEventFilter;

    private TextView tvCountDownTime;
    private CircularSeekBar seekBar;
    private int timeTicks = 0;
    private Handler mHandler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            countDown();
            updateUI();
        }
    };

    private void updateUI() {
        String text = format(curMinute, curSecond);
        tvCountDownTime.setText(text);
        seekBar.setProgress(getProgressValue());
    }

    private void countDown() {
        timeTicks++;
        int remainingTime = curMinute * 60 + curSecond;
        if (remainingTime <= 0) {
            timer.stop();
            return;
        }
        if (timeTicks % (SECOND / CIRCLE) == 0) {
            remainingTime = remainingTime - 1;
            curMinute = remainingTime / 60;
            curSecond = remainingTime % 60;
        }
    }

    /**
     * 换算至 0 - 120
     *
     * @return 0 - 120 之间的一个float值
     */
    private float getProgressValue() {
        int remainingSeconds = curMinute * 60 + curSecond;
        int progressSeconds = totalSecond - remainingSeconds;
        if (totalSecond == 0) {
            return 0;
        }
        float percent = (float) progressSeconds / (float) totalSecond;

        return 120 * percent;
    }

    private void setValue() {
        String text = tvCountDownTime.getText().toString();
        String[] values = text.split(":");
        if (values.length == 2) {
            curMinute = Integer.parseInt(values[0]);
            curSecond = Integer.parseInt(values[1]);
        }
    }

    private String format(int minute, int second) {
        return String.format(Locale.ENGLISH, "%02d:%02d", minute, second);
    }

    private void init() {
        timer = new MtTimer();
        curMinute = 0;
        curSecond = 0;
        totalSecond = 0;
        moveEventFilter = new MoveEventFilter();
    }

    private void bindView() {
        seekBar = findViewById(R.id.circularSeekBar);
        tvCountDownTime = findViewById(R.id.time);
        btnStart = findViewById(R.id.btn_tm_start);
        btnStop = findViewById(R.id.btn_tm_stop);
        mGestureDetector = new GestureDetector(this, this);
    }

    private void setPageListener() {

//        btnStop.setOnClickListener(this);
//        btnStart.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main_ui);

        hideActionBar();
        init();
        bindView();
        setPageListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendWidgetUpdateBroadcast();
    }

    private void sendWidgetUpdateBroadcast() {
        Intent intent = new Intent();
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        sendBroadcast(intent);
    }

    private void hideActionBar() {
        getSupportActionBar().hide();
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_tm_start:
//                btnStart.setVisibility(View.GONE);
//                btnStop.setVisibility(View.VISIBLE);
//                timer.scheduleAtFixRate(runnable, 0, 100);
//                break;
//            case R.id.btn_tm_stop:
//                btnStart.setVisibility(View.VISIBLE);
//                btnStop.setVisibility(View.GONE);
//                timer.stop();
//                break;
//        }
//    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(TAG, "GESTURE:DOWN");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.d(TAG, "GESTURE:SHOW_PRESS");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d(TAG, "GESTURE:SINGLE_TAP_UP");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG, "GESTURE:SCROLL");
        if (!moveEventFilter.isAvailable()) {
            return false;
        }
        int d = DirectionUtil.getDirection(motionEvent.getX(), motionEvent.getY(), motionEvent1.getX(), motionEvent1.getY());
        if (d == DirectionUtil.Direction.UP && curMinute < 99) {
            curMinute++;
        } else if (d == DirectionUtil.Direction.DOWN && curMinute > 0) {
            curMinute--;
        } else if (d == DirectionUtil.Direction.RIGHT && curSecond < 59) {
            curSecond++;
        } else if (d == DirectionUtil.Direction.LEFT && curSecond > 0) {
            curSecond--;
        }
        updateUI();

        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.d(TAG, "GESTURE:LONG PRESS");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG, "GESTURE:FLING");
        return false;
    }

    private class MoveEventFilter {
        final int limit = 6;
        int value;

        public MoveEventFilter() {
            value = -1;
        }

        public boolean isAvailable() {
            value++;
            if (value == limit) {
                value = -1;
            }
            return value == 0;
        }
    }
}
