package com.shenjing.mytextapp.widgte;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ObjectUtils;
import com.shenjing.mytextapp.R;


/**
 * toolbar
 */

public class TitleBar extends RelativeLayout {

    private Context mContext;

    private ConstraintLayout mLeftMenu;
    private ConstraintLayout mRightMenu;
    private RelativeLayout mCenterMenu;

    private ImageView ivLeft;
    private ImageView ivCenter;
    private ImageView ivRight;
    private ImageView ivRight2;
    private ImageView ivRight3;
    private ImageView ivRight4;

    private TextView tvLeft;
    private TextView tvCenter;
    private TextView tvRight;
    private final View rootView;


    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        LayoutInflater m_inflate = LayoutInflater.from(context);
        rootView = m_inflate.inflate(R.layout.base_toolbar, this, true);

        mLeftMenu = findViewById(R.id.csl_title_left);
        mRightMenu = findViewById(R.id.csl_title_right);
        mCenterMenu = findViewById(R.id.rl_title_center);

        ivLeft = findViewById(R.id.iv_title_left);
        ivCenter = findViewById(R.id.iv_title_center);
        ivRight = findViewById(R.id.iv_title_right);
        ivRight2 = findViewById(R.id.iv_title_right2);
        ivRight3 = findViewById(R.id.iv_right_3);
        ivRight4 = findViewById(R.id.iv_right_4);


        tvLeft = findViewById(R.id.tv_title_left);
        tvCenter = findViewById(R.id.tv_title_center);
        tvRight = findViewById(R.id.tv_title_right);
    }

    public ConstraintLayout getLeftMenu() {
        return mLeftMenu;
    }

    public ConstraintLayout getRightMenu() {
        return mRightMenu;
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public ImageView getIvCenter() {
        return ivCenter;
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public ImageView getIvRight2() {
        return ivRight2;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvCenter() {
        return tvCenter;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    /****----------------对外公开的方法------------------****/

    /**设置左按钮文字 和颜色
     *
     * @param leftText 左文字
     * @param color   左文字颜色
     */
    final public void setLeftText(String leftText,int color,OnOnceClickListener onceClickListener) {

        if (ObjectUtils.isNotEmpty(leftText)) {
            mLeftMenu.setVisibility(VISIBLE);
            tvLeft.setVisibility(VISIBLE);
            tvLeft.setText(leftText);
            tvLeft.setTextColor(color);
        }
        if (ObjectUtils.isNotEmpty(onceClickListener)) {
            mLeftMenu.setOnClickListener(onceClickListener);
        }
    }

    /**设置左按钮文字和颜色
     *
     * @param leftText 左文字--string类型资源引用
     * @param color    左颜色
     */
    final public void setLeftText(int leftText,int color,OnOnceClickListener onceClickListener) {

        if (ObjectUtils.isNotEmpty(leftText)) {
            mLeftMenu.setVisibility(VISIBLE);
            tvLeft.setVisibility(VISIBLE);
            tvLeft.setText(leftText);
            tvLeft.setTextColor(color);
        }
        if (ObjectUtils.isNotEmpty(onceClickListener)) {
            mLeftMenu.setOnClickListener(onceClickListener);
        }
    }

    /**
     * 左侧按钮
     *
     * @param leftIcon 按钮图片
     * @param leftText 按钮文字
     */
    final public void setLeftMenu(int leftIcon, String leftText,int color, OnOnceClickListener onceClickListener) {
        mLeftMenu.setVisibility(VISIBLE);
        if (ObjectUtils.isNotEmpty(leftIcon)) {
            ivLeft.setVisibility(VISIBLE);
            ivLeft.setImageResource(leftIcon);
        } else {
            ivLeft.setVisibility(GONE);
        }

        if (ObjectUtils.isNotEmpty(leftText)) {
            tvLeft.setVisibility(VISIBLE);
            tvLeft.setText(leftText);
            tvLeft.setTextColor(color);
        }

        if (ObjectUtils.isNotEmpty(onceClickListener)) {
            mLeftMenu.setOnClickListener(onceClickListener);
        }
    }


    /**
     * 设置右按钮文字
     *
     * @param rightText 右文字
     */
    final public void setRightText(String rightText,int color,OnOnceClickListener onceClickListener) {

        if (ObjectUtils.isNotEmpty(rightText)) {
            mRightMenu.setVisibility(VISIBLE);
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(rightText);
            tvRight.setTextColor(color);
        }
        if (ObjectUtils.isNotEmpty(onceClickListener)) {
            mRightMenu.setOnClickListener(onceClickListener);
        }

    }

    /**
     * 设置右按钮文字
     *
     * @param rightText 右文字--string类型资源引用
     */
    final public void setRightText(int rightText) {

        if (ObjectUtils.isNotEmpty(rightText)) {
            mRightMenu.setVisibility(VISIBLE);
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(rightText);
        }
    }

    /**
     * 右侧图片按钮和文字按钮
     *
     * @param rightIcon 按钮图片
     * @param rightText 按钮文字
     */
    final public void setRightMenu(int rightIcon, String rightText, OnOnceClickListener onceClickListener) {

        mRightMenu.setVisibility(VISIBLE);
        if (ObjectUtils.isNotEmpty(rightIcon) && rightIcon != -1) {
            ivRight.setVisibility(VISIBLE);
            ivRight.setImageResource(rightIcon);
        } else {
            ivRight.setVisibility(GONE);
        }

        if (ObjectUtils.isNotEmpty(rightText)) {
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(rightText);
        } else {
            tvRight.setVisibility(GONE);
        }

        if (ObjectUtils.isNotEmpty(onceClickListener)) {
            mRightMenu.setOnClickListener(onceClickListener);
        }
    }

    /**
     * 右侧图片按钮
     */
    final public void setRightMenu(int rightIcon, OnOnceClickListener onceClickListener) {
        mRightMenu.setVisibility(VISIBLE);
        if (ObjectUtils.isNotEmpty(rightIcon) && rightIcon != -1) {
            ivRight3.setVisibility(VISIBLE);
            ivRight3.setImageResource(rightIcon);
        } else {
            ivRight3.setVisibility(GONE);
        }
        tvRight.setVisibility(GONE);
        if (ObjectUtils.isNotEmpty(onceClickListener)) {
            mRightMenu.setOnClickListener(onceClickListener);
        }
    }

    final  public void setRightImage(int rightIcon, OnOnceClickListener onceClickListener){
        mRightMenu.setVisibility(VISIBLE);
        if (ObjectUtils.isNotEmpty(rightIcon) && rightIcon != -1) {
            ivRight4.setVisibility(VISIBLE);
            ivRight4.setImageResource(rightIcon);
        } else {
            ivRight4.setVisibility(GONE);
        }
        tvRight.setVisibility(GONE);
        if (ObjectUtils.isNotEmpty(onceClickListener)) {
            mRightMenu.setOnClickListener(onceClickListener);
        }
    }

    /**
     * 设置标题
     *
     * @param titleText 页面标题文字
     */
    final public void setTitleText(int titleText) {
        if (ObjectUtils.isNotEmpty(titleText)) {
            mCenterMenu.setVisibility(VISIBLE);
            tvCenter.setVisibility(VISIBLE);
            tvCenter.setText(titleText);
        }
    }

    /**
     * 设置标题
     *
     * @param titleText 页面标题文字
     */
    final public void setTitleText(int titleText, int Color) {
        if (ObjectUtils.isNotEmpty(titleText)) {
            mCenterMenu.setVisibility(VISIBLE);
            tvCenter.setVisibility(VISIBLE);
            tvCenter.setText(titleText);
        }

        if (ObjectUtils.isNotEmpty(Color)) {
            tvCenter.setTextColor(Color);
        }
    }


    /**
     * 设置标题
     *
     * @param titleText 页面标题文字
     */
    final public void setTitleText(String titleText, int Color) {
        if (ObjectUtils.isNotEmpty(titleText)) {
            mCenterMenu.setVisibility(VISIBLE);
            tvCenter.setVisibility(VISIBLE);
            tvCenter.setText(titleText);
            if (ObjectUtils.isNotEmpty(Color)) {
                tvCenter.setTextColor(Color);
            }
        }
    }

    /**
     * 设置标题
     *
     * @param titleText 页面标题文字和颜色
     */
    final public void setTitleText(String titleText) {
        if (ObjectUtils.isNotEmpty(titleText)) {
            mCenterMenu.setVisibility(VISIBLE);
            tvCenter.setVisibility(VISIBLE);
            tvCenter.setText(titleText);
        }
    }

    final public void setTitleBarBackground(int color) {
        if (color != 0) {
            rootView.setBackgroundColor(color);
        }
    }


}
