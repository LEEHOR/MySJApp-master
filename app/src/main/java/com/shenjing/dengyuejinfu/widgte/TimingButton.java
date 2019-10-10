package com.shenjing.dengyuejinfu.widgte;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.blankj.utilcode.util.ObjectUtils;
import com.shenjing.dengyuejinfu.R;

/**
 * author : Leehor
 * date   : 2019/8/1 10:48
 * version: 1.0
 * desc   :倒计时按钮
 */
public class TimingButton extends AppCompatButton {
    private Drawable arrayDrawable;  //默认样式
    private int total, interval;
    private String afterText, beforeText;  //结束时文本/开始时文本
    private TimeCount timeCount;  //计时器
    private int arrayColor;
    private float arrayFloat;

    public TimingButton(Context context) {
        super(context, null);
    }

    public TimingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义属性，并赋值
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TimingButton);
        total = typedArray.getInteger(R.styleable.TimingButton_tb_totalTime, 60000);
        interval = typedArray.getInteger(R.styleable.TimingButton_tb_timeInterval, 1000);
        arrayDrawable = typedArray.getDrawable(R.styleable.TimingButton_tb_background);
        beforeText = typedArray.getString(R.styleable.TimingButton_tb_before_text);
        afterText = typedArray.getString(R.styleable.TimingButton_tb_after_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            arrayColor = typedArray.getColor(R.styleable.TimingButton_tb_text_color, getResources().getColor(R.color.white, null));
        } else {
            arrayColor = typedArray.getColor(R.styleable.TimingButton_tb_text_color, getResources().getColor(R.color.white));
        }
        arrayFloat = typedArray.getFloat(R.styleable.TimingButton_tb_text_size, 14);
        // setBackgroundResource(R.drawable.timing_button); //设置默认样式
        setBackground(arrayDrawable);
        setAfterText(afterText);
        setBeforeText(beforeText);
        setTextColors(arrayColor);
        setTextSizes(arrayFloat);
        setTotalTime(total);
        typedArray.recycle();
    }

    /**
     * 计时时长
     * 单位:毫秒
     */
    public void setTotalTime(int totalTime) {
        if (ObjectUtils.isNotEmpty(totalTime)) {
            this.total = totalTime;
        }

    }

    //计时前显示
    public void setBeforeText(String beforeTexts) {
        if (ObjectUtils.isNotEmpty(beforeTexts)) {
            this.beforeText = beforeTexts;
            setText(beforeText);
        }

    }

    //计时结束时显示
    public void setAfterText(String afterTexts) {
        if (ObjectUtils.isNotEmpty(afterTexts)) {
            this.afterText = afterTexts;
        }

    }

    //设置背景主体
    public void setBackground(Drawable arrayDrawable) {
        if (ObjectUtils.isNotEmpty(arrayDrawable)) {
            setBackgroundDrawable(arrayDrawable);
        }
    }

    //设置文本颜色
    public void setTextColors(int color) {
        if (ObjectUtils.isNotEmpty(color)) {
            this.arrayColor = color;
            setTextColor(arrayColor);
        }
    }

    //设置文本大小
    public void setTextSizes(float textSize) {
        if (ObjectUtils.isNotEmpty(textSize)) {
            this.arrayFloat = textSize;
            setTextSize(arrayFloat);
        }
    }

    //执行开始
    public void start() {
        timeCount = new TimeCount(total, interval);
        timeCount.start();


    }

    //结束
    public void TimeOnFinished() {
        if (timeCount != null) {
            timeCount.onFinish();
        }
    }

    //取消
    public void TimeOnCancel() {
        if (timeCount != null) {
            timeCount.cancel();
            setText(beforeText);
            setEnabled(true);
        }
    }

    public class TimeCount extends CountDownTimer {
        private long countDownInterval;

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            this.countDownInterval = countDownInterval;
        }

        @Override
        public void onFinish() {//计时完毕时触发
            setText(afterText);
            setEnabled(true);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            setEnabled(false);
            setText(millisUntilFinished / countDownInterval + "秒");
        }
    }
}
