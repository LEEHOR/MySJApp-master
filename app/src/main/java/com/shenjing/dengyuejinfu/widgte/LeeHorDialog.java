package com.shenjing.dengyuejinfu.widgte;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

/**
 * author : Leehor
 * date   : 2019/8/29:57
 * version: 1.0
 * desc   :自定义Dialog支持链式调用
 * 方式一: LeeHorDialog.Builder build = LeeHorDialog.build(getActivity());
 *         LeeHorDialog leeHorDialog = build.create();
 *         leeHorDialog.set...();
 *         leeHorDialog.show();
 *
 * 方式二: LeeHorDialog.Builder build = LeeHorDialog.build(getActivity());
 *         build.setView(R.layout.dialog_fragment_repay)
 *                 .setCancelable(false)
 *                 ...
 *                 .show();
 */
public abstract class LeeHorDialog {

    public static Builder build(Context context) {

        return new APi21Builder(context);

    }

    public static Builder build(Context context, int themeResId) {
        return new APi21Builder(context, themeResId);
    }

    /**
     * 对外暴露的方法
     */
    public abstract void setCanceledOnTouchOutside(boolean canceledOnTouchOutside);

    public abstract void setCancelable(boolean cancelable);

    public abstract void show();

    public abstract void dismiss();

    public abstract boolean isShowing();

    public abstract void cancel();

    public abstract Button getButton(int whichButton);

    public abstract void setTilte(CharSequence title);

    public abstract void setCustomTitle(View customTitleView);

    public abstract boolean requestWindowFeature(int widowFeature);

    public abstract boolean supportRequestWindowFeature(int widowFeature);

    /**
     * 设置自定义更新布局
     * show()之后更新布局调用
     *
     * @param layoutResID
     */
    public abstract void setContentView(int layoutResID);

    /*设置自定义更新布局*/
    public abstract void setContentView(View view);

    /**
     * 设置自定义布局
     * show()之前调用
     *
     * @param view
     */
    public abstract void setView(View view);

    public abstract
    @Nullable
    ListView getListView();

    public abstract
    @NonNull
    Context getContext();

    public abstract
    @Nullable
    View getCurrentFocus();

    public abstract
    @NonNull
    LayoutInflater getLayoutInflater();

    public abstract
    @Nullable
    Activity getOwnerActivity();

    public abstract int getVolumeControlStream();

    public abstract
    @Nullable
    Window getWindow();


    /**
     * SDK21以上的dialog
     */
    private static class Api21Dialog extends LeeHorDialog {

        private AlertDialog alertDialogApp;

        public Api21Dialog(AlertDialog alertDialog) {
            this.alertDialogApp = alertDialog;
        }

        @Override
        public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            alertDialogApp.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }

        @Override
        public void setCancelable(boolean cancelable) {
            alertDialogApp.setCancelable(cancelable);
        }

        @Override
        public void show() {
            alertDialogApp.show();
        }

        @Override
        public void dismiss() {
            if (alertDialogApp.isShowing()) {
                alertDialogApp.dismiss();
            }
        }

        @Override
        public boolean isShowing() {

            return alertDialogApp.isShowing();
        }

        @Override
        public void cancel() {
            if (alertDialogApp.isShowing()) {
                alertDialogApp.cancel();
            }

        }

        @Override
        public Button getButton(int whichButton) {
            return alertDialogApp.getButton(whichButton);
        }

        @Override
        public void setTilte(CharSequence title) {

            alertDialogApp.setTitle(title);
        }

        @Override
        public void setCustomTitle(View customTitleView) {
            alertDialogApp.setCustomTitle(customTitleView);

        }

        @Override
        public boolean requestWindowFeature(int widowFeature) {

            return alertDialogApp.requestWindowFeature(widowFeature);
        }

        @Override
        public boolean supportRequestWindowFeature(int widowFeature) {
            return alertDialogApp.supportRequestWindowFeature(widowFeature);
        }

        @Override
        public void setContentView(int layoutResID) {
            alertDialogApp.setContentView(layoutResID);
        }

        @Override
        public void setContentView(View view) {
            alertDialogApp.setContentView(view);
        }

        @Override
        public void setView(View view) {
            alertDialogApp.setView(view);
        }

        @Nullable
        @Override
        public ListView getListView() {
            return alertDialogApp.getListView();
        }

        @NonNull
        @Override
        public Context getContext() {
            return alertDialogApp.getContext();
        }

        @Nullable
        @Override
        public View getCurrentFocus() {
            return alertDialogApp.getCurrentFocus();
        }

        @NonNull
        @Override
        public LayoutInflater getLayoutInflater() {

            return alertDialogApp.getLayoutInflater();
        }

        @Nullable
        @Override
        public Activity getOwnerActivity() {
            return alertDialogApp.getOwnerActivity();
        }

        @Override
        public int getVolumeControlStream() {
            return alertDialogApp.getVolumeControlStream();
        }

        @Nullable
        @Override
        public Window getWindow() {
            return alertDialogApp.getWindow();
        }
    }

    /**
     * 建造者模式
     */
    public interface Builder {
        @NonNull
        Context getContext();

        Builder setTitle(@StringRes int titleId);

        Builder setTitle(CharSequence title);

        Builder setCustomTitle(View customTitleView);

        Builder setMessage(@StringRes int messageId);

        Builder setMessage(CharSequence message);

        Builder setIcon(@DrawableRes int iconId);

        Builder setIcon(Drawable icon);

        Builder setIconAttribute(@AttrRes int attrId);

        Builder setPositiveButton(@StringRes int textId, final DialogInterface.OnClickListener listener);

        Builder setPositiveButton(CharSequence text, final DialogInterface.OnClickListener listener);

        Builder setNegativeButton(@StringRes int textId, final DialogInterface.OnClickListener listener);

        Builder setNegativeButton(CharSequence text, final DialogInterface.OnClickListener listener);

        Builder setNeutralButton(@StringRes int textId, final DialogInterface.OnClickListener listener);

        Builder setNeutralButton(CharSequence text, final DialogInterface.OnClickListener listener);

        Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

        Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener);

        Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener);

        Builder setItems(@ArrayRes int itemsId, final DialogInterface.OnClickListener listener);

        Builder setItems(CharSequence[] items, final DialogInterface.OnClickListener listener);

        Builder setAdapter(final ListAdapter adapter, final DialogInterface.OnClickListener listener);

        Builder setCursor(final Cursor cursor, final DialogInterface.OnClickListener listener, String
                labelColumn);

        Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems, final DialogInterface
                .OnMultiChoiceClickListener listener);

        Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, final DialogInterface
                .OnMultiChoiceClickListener listener);

        Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, final
        DialogInterface.OnMultiChoiceClickListener listener);

        Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, final DialogInterface
                .OnClickListener listener);

        Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, final
        DialogInterface.OnClickListener listener);

        Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, final DialogInterface
                .OnClickListener listener);

        Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, final DialogInterface
                .OnClickListener listener);

        Builder setCancelable(boolean cancelable);

        Builder setOnItemSelectedListener(final AdapterView.OnItemSelectedListener listener);

        Builder setView(int layoutResId);

        Builder setView(View view);

        LeeHorDialog create();

        LeeHorDialog show();

    }

    private static class APi21Builder implements Builder {
        private AlertDialog.Builder builderApp;

        private APi21Builder(@NonNull Context context) {
            this(context, 0);
        }

        private APi21Builder(@NonNull Context context, @StyleRes int themeResId) {
            builderApp = new AlertDialog.Builder(context, themeResId);
        }

        @NonNull
        @Override
        public Context getContext() {
            return builderApp.getContext();
        }

        @Override
        public Builder setTitle(@StringRes int titleId) {
            builderApp.setTitle(titleId);
            return this;
        }

        @Override
        public Builder setTitle(CharSequence title) {
            builderApp.setTitle(title);
            return this;
        }

        @Override
        public Builder setCustomTitle(View customTitleView) {
            builderApp.setCustomTitle(customTitleView);
            return this;
        }

        @Override
        public Builder setMessage(@StringRes int messageId) {
            builderApp.setMessage(messageId);
            return this;
        }

        @Override
        public Builder setMessage(CharSequence message) {
            builderApp.setMessage(message);
            return this;
        }

        @Override
        public Builder setIcon(@DrawableRes int iconId) {
            builderApp.setIcon(iconId);
            return this;
        }

        @Override
        public Builder setIcon(Drawable icon) {
            builderApp.setIcon(icon);
            return this;
        }

        @Override
        public Builder setIconAttribute(@AttrRes int attrId) {
            builderApp.setIconAttribute(attrId);
            return this;
        }

        @Override
        public Builder setPositiveButton(@StringRes int textId, final DialogInterface.OnClickListener
                listener) {
            builderApp.setPositiveButton(textId, listener);
            return this;
        }

        @Override
        public Builder setPositiveButton(CharSequence text, final DialogInterface.OnClickListener listener) {
            builderApp.setPositiveButton(text, listener);
            return this;
        }

        @Override
        public Builder setNegativeButton(@StringRes int textId, final DialogInterface.OnClickListener
                listener) {
            builderApp.setNegativeButton(textId, listener);
            return this;
        }

        @Override
        public Builder setNegativeButton(CharSequence text, final DialogInterface.OnClickListener listener) {
            builderApp.setNegativeButton(text, listener);
            return this;
        }

        @Override
        public Builder setNeutralButton(@StringRes int textId, final DialogInterface.OnClickListener
                listener) {
            builderApp.setNeutralButton(textId, listener);
            return this;
        }

        @Override
        public Builder setNeutralButton(CharSequence text, final DialogInterface.OnClickListener listener) {
            builderApp.setNeutralButton(text, listener);
            return this;
        }

        @Override
        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            builderApp.setOnCancelListener(onCancelListener);
            return this;
        }

        @Override
        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                builderApp.setOnDismissListener(onDismissListener);
            }
            return this;
        }

        @Override
        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            builderApp.setOnKeyListener(onKeyListener);
            return this;
        }

        @Override
        public Builder setItems(@ArrayRes int itemsId, final DialogInterface.OnClickListener listener) {
            builderApp.setItems(itemsId, listener);
            return this;
        }

        @Override
        public Builder setItems(CharSequence[] items, final DialogInterface.OnClickListener listener) {
            builderApp.setItems(items, listener);
            return this;
        }

        @Override
        public Builder setAdapter(final ListAdapter adapter, final DialogInterface.OnClickListener listener) {
            builderApp.setAdapter(adapter, listener);
            return this;
        }

        @Override
        public Builder setCursor(final Cursor cursor, final DialogInterface.OnClickListener listener,
                                 String labelColumn) {
            builderApp.setCursor(cursor, listener, labelColumn);
            return this;
        }

        @Override
        public Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems, final
        DialogInterface.OnMultiChoiceClickListener listener) {
            builderApp.setMultiChoiceItems(itemsId, checkedItems, listener);
            return this;
        }

        @Override
        public Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, final
        DialogInterface.OnMultiChoiceClickListener listener) {
            builderApp.setMultiChoiceItems(items, checkedItems, listener);
            return this;
        }

        @Override
        public Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, final
        DialogInterface.OnMultiChoiceClickListener listener) {
            builderApp.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
            return this;
        }

        @Override
        public Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, final DialogInterface
                .OnClickListener listener) {
            builderApp.setSingleChoiceItems(itemsId, checkedItem, listener);
            return this;
        }

        @Override
        public Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, final
        DialogInterface.OnClickListener listener) {
            builderApp.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
            return this;
        }

        @Override
        public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, final DialogInterface
                .OnClickListener listener) {
            builderApp.setSingleChoiceItems(items, checkedItem, listener);
            return this;
        }

        @Override
        public Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, final DialogInterface
                .OnClickListener listener) {
            builderApp.setSingleChoiceItems(adapter, checkedItem, listener);
            return this;
        }

        @Override
        public Builder setCancelable(boolean cancelable) {
            builderApp.setCancelable(cancelable);
            return this;
        }

        @Override
        public Builder setOnItemSelectedListener(final AdapterView.OnItemSelectedListener listener) {
            builderApp.setOnItemSelectedListener(listener);
            return this;
        }

        @Override
        public Builder setView(int layoutResId) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builderApp.setView(layoutResId);
            }

            return this;
        }

        @Override
        public Builder setView(View view) {
            builderApp.setView(view);
            return this;
        }

        @Override
        public LeeHorDialog create() {
            return new Api21Dialog(builderApp.create());
        }

        @Override
        public LeeHorDialog show() {
            final LeeHorDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
