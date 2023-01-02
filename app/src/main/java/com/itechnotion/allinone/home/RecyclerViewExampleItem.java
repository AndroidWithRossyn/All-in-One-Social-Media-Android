package com.itechnotion.allinone.home;



import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itechnotion.allinone.R;
import com.itechnotion.allinone.activity.WebviewActivity;
import com.itechnotion.allinone.model.SocialListBean;
import com.itechnotion.allinone.utils.AppConstants;
import com.itechnotion.allinone.utils.SharedObjects;
import com.itechnotion.allinone.utils.SquareFrameLayout;

import java.util.List;

public class RecyclerViewExampleItem extends FrameLayout {
    ImageView imageView, imageicon;
    TextView textView;
    SquareFrameLayout linearLayout;
    SharedObjects sharedObjects;
    public RecyclerViewExampleItem(final Context context) {
        super(context);
        inflate(context, R.layout.recyclerview_item1, this);
        sharedObjects = new SharedObjects(context);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageicon = (ImageView) findViewById(R.id.image_icon);
        textView = (TextView) findViewById(R.id.textView);
        linearLayout = (SquareFrameLayout) findViewById(R.id.ll_main);
    }

    public void bind(final List<SocialListBean> socialListBeans, final int position, final Context context) {

        textView.setText(socialListBeans.get(position).getName());
        imageView.setImageResource(socialListBeans.get(position).getImage());
        imageicon.setImageResource(socialListBeans.get(position).getIconimg());
        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("comeFrom", socialListBeans.get(position).getName());
                context.startActivity(intent);
            }
        });


    }
}
