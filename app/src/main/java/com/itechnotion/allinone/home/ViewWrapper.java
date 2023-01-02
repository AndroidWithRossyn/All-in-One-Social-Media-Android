package com.itechnotion.allinone.home;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.itechnotion.allinone.model.SocialListBean;

import java.util.List;

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder{
    public ViewWrapper(V itemView) {
        super(itemView);
    }
}