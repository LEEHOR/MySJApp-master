package com.shenjing.mytextapp.widgte.GalleryLayoutManager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author : Leehor
 * date   : 2019/8/1318:03
 * version: 1.0
 * desc   :
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private View view;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public View getView() {
        return view;
    }
}