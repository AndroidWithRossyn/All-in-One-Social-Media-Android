package com.itechnotion.allinone.home;

import android.content.Context;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.itechnotion.allinone.model.SocialListBean;

import java.util.List;

public class RecyclerExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SocialListBean> socialListBeans;
    private Context mContext;

    public RecyclerExampleAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewWrapper<RecyclerViewExampleItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<RecyclerViewExampleItem>(new RecyclerViewExampleItem(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RecyclerViewExampleItem rvei = (RecyclerViewExampleItem) viewHolder.itemView;
        String str = getItem(position);
        int img = getImage(position);
        int icon = geticonImage(position);
        boolean isShow = isShow(position);
        rvei.bind(socialListBeans, position, mContext);
    }

    @Override
    public int getItemCount() {
        return socialListBeans.size();
    }

    public String getItem(int position) {
        return socialListBeans.get(position).getName();
    }

    public int getImage(int position) {
        return socialListBeans.get(position).getImage();
    }

    public int geticonImage(int position) {
        return socialListBeans.get(position).getIconimg();
    }

    public boolean isShow(int position) {
        return socialListBeans.get(position).isShow();
    }

    public void addAll(List<SocialListBean> lst) {
        this.socialListBeans = lst;
    }
}
