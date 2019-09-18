package com.shenjing.dengyuejinfu.ui.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.ui.fragment.IndexFragment;
import com.shenjing.dengyuejinfu.ui.fragment.MemberFragment;
import com.shenjing.dengyuejinfu.ui.fragment.MineFragment;
import com.shenjing.dengyuejinfu.ui.fragment.ReceiptFragment;
import com.shenjing.dengyuejinfu.ui.fragment.ReplacementFragment;
import com.shenjing.dengyuejinfu.utils.ScreenUtils;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1216:25
 * version: 1.0
 * desc   :mainActivity
 */

@Route(path = ARouterUrl.MainActivityUrl)
public class MainActivity extends BaseActivity {
    private static final String TAG_HOME = "HomeFrag";
    private static final String TAG_REPLACE = "Replace";
    private static final String TAG_MEMBER = "Member";
    private static final String TAG_RECEIPT = "Receipt";
    private static final String TAG_MINE = "Mine";
    @BindView(R.id.main_fragment)
    FrameLayout mainFragment;
    @BindView(R.id.main_BottomBar)
    BottomNavigationBar mBottomTabs;
    private IndexFragment indexFragment;
    private ReplacementFragment replacementFragment;
    private MemberFragment memberFragment;
    private ReceiptFragment receiptFragment;
    private MineFragment mineFragment;
    private SPUtils spUtils;
    private String user_name;
    private String user_token;
    private String user_id;
    private long exitTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        hideStatusBar();
        initBottomTabs();
        hideAll();
        indexFragment = IndexFragment.newInstance();
        showFragmentOne();
    }

    @Override
    protected void initFunc() {
        //     spUtils = SPUtils.getInstance();
        //  user_name = spUtils.getString(BaseParams.USER_NAME_KEY);
        //  user_token = spUtils.getString(BaseParams.USER_TOKEN_KEY);
    }

    private void hideAll() {
        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (indexFragment != null) transaction.hide(indexFragment);
        if (replacementFragment != null) transaction.hide(replacementFragment);
        if (memberFragment != null) transaction.hide(memberFragment);
        if (receiptFragment != null) transaction.hide(receiptFragment);
        if (mineFragment != null) transaction.hide(mineFragment);
        transaction.commitAllowingStateLoss();
    }

    private void initBottomTabs() {
        mBottomTabs.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);//BACKGROUND_STYLE_STATIC

        mBottomTabs.setActiveColor(R.color.blue1)
                .setInActiveColor(R.color.tab_text_normal)
                .setBarBackgroundColor(R.color.white);
        mBottomTabs
                .addItem(new BottomNavigationItem(R.drawable.icon_index_select, R.string.home_index)
                        .setInactiveIconResource(R.drawable.icon_index_nor))
                .addItem(new BottomNavigationItem(R.drawable.icon_replacement_select, R.string.home_replacement)
                        .setInactiveIconResource(R.drawable.icon_replacement_nor))
                .addItem(new BottomNavigationItem(R.drawable.icon_member_select, R.string.home_member)
                        .setInactiveIconResource(R.drawable.icon_member_nor))
                .addItem(new BottomNavigationItem(R.drawable.icon_receipt_select, R.string.home_receipt)
                        .setInactiveIconResource(R.drawable.icon_receipt_nor))
                .addItem(new BottomNavigationItem(R.drawable.icon_mine_select, R.string.home_mine)
                        .setInactiveIconResource(R.drawable.icon_mine_nor))
                .setTabSelectedListener(listener)
                .setFirstSelectedPosition(0)
                .initialise();
    }

    /**
     * 底部导航栏操作监听
     */
    public BottomNavigationBar.OnTabSelectedListener listener = new BottomNavigationBar.OnTabSelectedListener() {

        @Override
        public void onTabSelected(int position) {

            hideAll();
            switch (position) {
                case 0:
                    showFragmentOne();
                    break;
                case 1:
                    if (!StringUtils.isSpace(BaseParams.userName) && !StringUtils.isSpace(BaseParams.userToken)) {
                        showFragmentTwo();
                    } else {
                        Arouter(1);
//                        ARouter.getInstance().build(ARouterUrl.LoginActivityUrl)
//                                .withInt(BaseParams.Router_type_mainActivity, 1001)
//                                .withInt(BaseParams.Router_position_mainActivity, 1)
//                                .navigation(MainActivity.this, BaseParams.Router_code_mainActivity);
                    }
                    break;
                case 2:
                    if (!StringUtils.isSpace(BaseParams.userName) && !StringUtils.isSpace(BaseParams.userToken)) {
                        showFragmentThree();
                    } else {
                        Arouter(2);
//                        ARouter.getInstance().build(ARouterUrl.LoginActivityUrl)
//                                .withInt(BaseParams.Router_type_mainActivity, 1001)
//                                .withInt(BaseParams.Router_position_mainActivity, 2)
//                                .navigation(MainActivity.this, BaseParams.Router_code_mainActivity);
                    }

                    break;
                case 3:
                    if (!StringUtils.isSpace(BaseParams.userName) && !StringUtils.isSpace(BaseParams.userToken)) {
                        showFragmentFour();
                    } else {
                        Arouter(3);
//                        ARouter.getInstance().build(ARouterUrl.LoginActivityUrl)
//                                .withInt(BaseParams.Router_type_mainActivity, 1001)
//                                .withInt(BaseParams.Router_position_mainActivity, 3)
//                                .navigation(MainActivity.this, BaseParams.Router_code_mainActivity);
                    }
                    break;
                case 4:
                    if (!StringUtils.isSpace(BaseParams.userName) && !StringUtils.isSpace(BaseParams.userToken)) {
                        showFragmentFive();
                    } else {
                        Arouter(4);
//                        ARouter.getInstance().build(ARouterUrl.LoginActivityUrl)
//                                .withInt(BaseParams.Router_type_mainActivity, 1001)
//                                .withInt(BaseParams.Router_position_mainActivity, 4)
//                                .navigation(MainActivity.this, BaseParams.Router_code_mainActivity);
                    }
                    break;
            }
        }

        @Override
        public void onTabUnselected(int position) {
        }

        @Override
        public void onTabReselected(int position) {
        }
    };

    /**
     * 展示第一个页面
     */
    private void showFragmentOne() {

        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        indexFragment = (IndexFragment) manager.findFragmentByTag(TAG_HOME);
        if (null == indexFragment) {
            indexFragment = IndexFragment.newInstance();
            transaction.add(R.id.main_fragment, indexFragment, TAG_HOME);
            transaction.show(indexFragment);
        } else {
            transaction.show(indexFragment);
        }
        transaction.commitAllowingStateLoss();
        //头部标题栏操作
        // mStatusBar.setVisibility(View.VISIBLE);
        // mStatusBar.setBackgroundResource(R.color.white);
        ScreenUtils.setTextColorStatusBar(this, true);
    }

    /**
     * 展示第二个页面
     */
    private void showFragmentTwo() {
        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        replacementFragment = (ReplacementFragment) manager.findFragmentByTag(TAG_REPLACE);
        if (null == replacementFragment) {
            replacementFragment = ReplacementFragment.newInstance();
            transaction.add(R.id.main_fragment, replacementFragment, TAG_REPLACE);
            transaction.show(replacementFragment);
        } else {
            transaction.show(replacementFragment);
        }
        transaction.commitAllowingStateLoss();
        //头部标题栏操作
        // mStatusBar.setVisibility(View.VISIBLE);
        // mStatusBar.setBackgroundResource(R.color.white);
        ScreenUtils.setTextColorStatusBar(this, true);
    }

    /**
     * 展示第三个页面
     */
    private void showFragmentThree() {
        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        memberFragment = (MemberFragment) manager.findFragmentByTag(TAG_MEMBER);
        if (null == memberFragment) {
            memberFragment = MemberFragment.newInstance();
            transaction.add(R.id.main_fragment, memberFragment, TAG_MEMBER);
            transaction.show(memberFragment);
        } else {
            transaction.show(memberFragment);
        }
        transaction.commitAllowingStateLoss();
        //头部标题栏操作
        // mStatusBar.setVisibility(View.VISIBLE);
        // mStatusBar.setBackgroundResource(R.color.white);
        ScreenUtils.setTextColorStatusBar(this, true);
    }

    /**
     * 展示第四个页面
     */
    private void showFragmentFour() {
        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        receiptFragment = (ReceiptFragment) manager.findFragmentByTag(TAG_RECEIPT);
        if (null == receiptFragment) {
            receiptFragment = ReceiptFragment.newInstance();
            transaction.add(R.id.main_fragment, receiptFragment, TAG_RECEIPT);
            transaction.show(receiptFragment);
        } else {
            transaction.show(receiptFragment);
        }
        transaction.commitAllowingStateLoss();
        //头部标题栏操作
        // mStatusBar.setVisibility(View.VISIBLE);
        // mStatusBar.setBackgroundResource(R.color.white);
        ScreenUtils.setTextColorStatusBar(this, false);
    }

    /**
     * 展示第五个页面
     */
    private void showFragmentFive() {
        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        mineFragment = (MineFragment) manager.findFragmentByTag(TAG_MINE);
        if (null == mineFragment) {
            mineFragment = MineFragment.newInstance();
            transaction.add(R.id.main_fragment, mineFragment, TAG_MINE);
            transaction.show(mineFragment);
        } else {
            transaction.show(mineFragment);
        }
        transaction.commitAllowingStateLoss();
        //头部标题栏操作
        // mStatusBar.setVisibility(View.VISIBLE);
        // mStatusBar.setBackgroundResource(R.color.white);
        ScreenUtils.setTextColorStatusBar(this, false);
    }

    /**
     * 跳转到登录页面
     *
     * @param position
     */
    private void Arouter(int position) {
        ARouter.getInstance().build(ARouterUrl.LoginActivityUrl)
                .withInt(BaseParams.Router_type, BaseParams.MainActivity_Type)
                .withInt(BaseParams.Router_position_mainActivity, position)
                .navigation(MainActivity.this, BaseParams.Router_code_mainActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1001) {  //登陆页面传来
            if (data != null) {
                int position = data.getIntExtra("position", 0);
                BackToTargetPage(position);
            }

        }
    }

    /**
     * 处理登录页面回调的跳转
     *
     * @param position
     */
    private void BackToTargetPage(int position) {
        hideAll();
        mBottomTabs.selectTab(0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        hideAll();
        LogUtils.d("重新加载");
        mBottomTabs.selectTab(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出
     */
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showSnackBar(mBottomTabs, getString(R.string.double_click_exit));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
