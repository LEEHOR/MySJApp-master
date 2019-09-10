package com.shenjing.mytextapp.ui.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.shenjing.mytextapp.App;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseFragment;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.IndexFragmentContract;
import com.shenjing.mytextapp.ui.presenter.IndexFragmentPresenter;
import com.shenjing.mytextapp.utils.PhoneUtils;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/8/1216:29
 * version: 1.0
 * desc   :首页
 */
public class IndexFragment extends BaseFragment<IndexFragmentPresenter> implements IndexFragmentContract.View {


    @BindView(R.id.index_mStatusBar)
    View mStatusBar;
    @BindView(R.id.index_titleBar)
    TitleBar fragmentCouponTitleBar;
    @BindView(R.id.index_swipe)
    SwipeRefreshLayout indexSwipe;
    @BindView(R.id.index_cardEvaluation)
    CardView indexCardEvaluation;
    @BindView(R.id.index_creditInquiry)
    CardView indexCreditInquiry;

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        setStatusBar(mStatusBar, R.color.white);
        setPageTitle(fragmentCouponTitleBar, "腾云金服", R.color.textColor);
        setTittleBarBackgraound(fragmentCouponTitleBar, R.color.white);
        setTitleRightImage(fragmentCouponTitleBar, R.drawable.icon_message, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                ARouter.getInstance().build(ARouterUrl.AnnouncementActivityUrl).navigation();
            }
        });
        indexSwipe.setColorSchemeResources(R.color.blue4);

    }

    @Override
    protected void initFunc() {

    }

    @OnClick({R.id.index_cardEvaluation, R.id.index_creditInquiry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.index_cardEvaluation:
                ARouter.getInstance().build(ARouterUrl.CardEvaluationActivityUrl).navigation();
                break;
            case R.id.index_creditInquiry:
                // ARouter.getInstance().build(ARouterUrl.CreditInquiryActivityUrl).navigation();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                //ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // LogUtils.d("通讯录",data.getData());
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            ContentResolver cr = App.getAppContext().getContentResolver();
            LogUtils.d("地址", data.getData());
            Cursor cursor = cr.query(data.getData(), null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst())
                {
                    int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA1);
                    int columnIndex1 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA2);
                    int columnIndex2 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA3);
                    int columnIndex3 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA4);
                    int columnIndex4 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA5);
                    int columnIndex5 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA6);
                    int columnIndex6 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA7);
                    int columnIndex7 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA8);
                    int columnIndex8 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA9);
                    int columnIndex9 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA10);
                    int columnIndex10 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA11);
                    int columnIndex11= cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA12);
                    int columnIndex12= cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA13);
                    int columnIndex13= cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA14);
                    int columnIndex14= cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA15);
                    int columnIndex15 = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    String string1 = cursor.getString(columnIndex15);
                    String string = cursor.getString(columnIndex);
                    String string2= cursor.getString(columnIndex1);
                    String string3= cursor.getString(columnIndex2);
                    String string4= cursor.getString(columnIndex3);
                    String string5= cursor.getString(columnIndex4);
                    String string6= cursor.getString(columnIndex5);
                    String string7= cursor.getString(columnIndex6);
                    String string8= cursor.getString(columnIndex7);
                    String string9= cursor.getString(columnIndex8);
                    String string10= cursor.getString(columnIndex9);
                    String string11= cursor.getString(columnIndex10);
                    String string12= cursor.getString(columnIndex11);
                    String string13= cursor.getString(columnIndex12);
                    String string14= cursor.getString(columnIndex13);
                    String string15= cursor.getString(columnIndex14);
                    LogUtils.d("电话1",string+"/"+string2+"/"+string3+"/"+string4+"/"+string5+"/"+string6+"/"+string7+"/"+"/"+string8+"/"+string9+
                            "/"+string10+"/"+string11+"/"+string12+"/"+string13+"/"+string14+"/"+string15+"/"+string1);
                }
                cursor.close();

            }
//                int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
//                int columnIndex1 = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
//                LogUtils.d("地址2",columnIndex,columnIndex1);
//              //  String phoneNumber = cursor.getString(columnIndex1);
        }
    }
}
