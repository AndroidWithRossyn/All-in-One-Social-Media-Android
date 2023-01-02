package com.itechnotion.allinone.home;


import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import com.clockbyte.admobadapter.bannerads.AdmobBannerRecyclerAdapterWrapper;
//import com.clockbyte.admobadapter.bannerads.BannerAdViewWrappingStrategyBase;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.itechnotion.allinone.R;
import com.itechnotion.allinone.model.SocialListBean;
import com.itechnotion.allinone.utils.AppConstants;
import com.itechnotion.allinone.utils.SharedObjects;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView rvMessages;
    //AdmobBannerRecyclerAdapterWrapper adapterWrapper;
    List<SocialListBean> socialListBeanList;
    SharedObjects sharedObjects;
    NestedScrollView scrollView;
    InterstitialAd mInterstitialAd;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerExampleAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                initRecyclerViewItems();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        initRecyclerViewItems();

                                    }
                                }
        );

        InterstitialAd();

        scrollView = v.findViewById(R.id.scrollView);
        rvMessages = v.findViewById(R.id.rvMessages);
        rvMessages.setFocusable(false);
        initRecyclerViewItems();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 1000);
        return v;
    }

    public void InterstitialAd() {


        AdRequest adRequest = new AdRequest.Builder().build();

        //test ID : ca-app-pub-3940256099942544/1033173712
        InterstitialAd.load(getContext(), "ca-app-pub-7770856719889795/7781622022", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                showInterstitial();
                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                    }
                }, 5000);*/
            }
        });


       /* mInterstitialAd = new InterstitialAd(getActivity());

        // set the ad unit ID
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });*/
    }

    private void showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(getActivity());
        }


    }

    private void initRecyclerViewItems() {
        sharedObjects = new SharedObjects(getActivity());
        socialListBeanList = new ArrayList<>();
        rvMessages.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new RecyclerExampleAdapter(getActivity());
        String[] testDevicesIds = new String[]{getString(R.string.testDeviceID), AdRequest.DEVICE_ID_EMULATOR};
       /* adapterWrapper = AdmobBannerRecyclerAdapterWrapper.builder(getActivity())
                .setLimitOfAds(20)
                .setFirstAdIndex(20)
                .setNoOfDataBetweenAds(20)
                .setTestDeviceIds(testDevicesIds)
                .setAdapter(adapter)
                .setAdViewWrappingStrategy(new BannerAdViewWrappingStrategyBase() {
                    @NonNull
                    @Override
                    protected ViewGroup getAdViewWrapper(ViewGroup parent) {
                        return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.native_express_ad_container,
                                parent, false);
                    }

                    @Override
                    protected void recycleAdViewWrapper(@NonNull ViewGroup wrapper, @NonNull AdView ad) {
                        ViewGroup container = (ViewGroup) wrapper.findViewById(R.id.ad_container);
                        for (int i = 0; i < container.getChildCount(); i++) {
                            View v = container.getChildAt(i);
                            if (v instanceof AdView) {
                                container.removeViewAt(i);
                                break;
                            }
                        }
                    }

                    @Override
                    protected void addAdViewToWrapper(@NonNull ViewGroup wrapper, @NonNull AdView ad) {
                        ViewGroup container = (ViewGroup) wrapper.findViewById(R.id.ad_container);
                        container.addView(ad);
                    }
                })
                .build();

        rvMessages.setAdapter(adapterWrapper);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapterWrapper.getItemViewType(position) == adapterWrapper.getViewTypeAdBanner())
                    return 2;
                else return 1;
            }
        });
        rvMessages.setLayoutManager(mLayoutManager);*/

        Boolean fbShow = sharedObjects.preferencesEditor.getBoolean(AppConstants.FACEBOOK);
        Boolean instaShow = sharedObjects.preferencesEditor.getBoolean(AppConstants.INSTAGRAM);
        Boolean pinterestShow = sharedObjects.preferencesEditor.getBoolean(AppConstants.PINTEREST);
        Boolean twitterShow = sharedObjects.preferencesEditor.getBoolean(AppConstants.TWITTER);
        Boolean skypeShow = sharedObjects.preferencesEditor.getBoolean(AppConstants.SKYPE);
        Boolean snapchatShow = sharedObjects.preferencesEditor.getBoolean(AppConstants.SNAPCHAT);
        Boolean gplusShow = sharedObjects.preferencesEditor.getBoolean(AppConstants.GPLUS);
        Boolean telegramShow = sharedObjects.preferencesEditor.getBoolean(AppConstants.TELEGRAM);
        Boolean linkedinShow = sharedObjects.preferencesEditor.getBoolean(AppConstants.LINKEDIN);
        Boolean quoraShow = sharedObjects.preferencesEditor.getBoolean(AppConstants.QUORA);


        if (fbShow)
            socialListBeanList.add(new SocialListBean("Facebook", R.drawable.bg_fb, fbShow, R.drawable.ic_fbicon));

        if (instaShow)
            socialListBeanList.add(new SocialListBean("Instagram", R.drawable.bg_insta, instaShow, R.drawable.ic_instaicon));

        if (pinterestShow)
            socialListBeanList.add(new SocialListBean("Pinterest", R.drawable.bg_pintrest, pinterestShow, R.drawable.ic_pinticon));

        if (twitterShow)
            socialListBeanList.add(new SocialListBean("Twitter", R.drawable.bg_twit, twitterShow, R.drawable.ic_twiticon));

        if (skypeShow)
            socialListBeanList.add(new SocialListBean("Tumblr", R.drawable.bg_tumblr, skypeShow, R.drawable.ic_tumblr));

        if (snapchatShow)
            socialListBeanList.add(new SocialListBean("Snapchat", R.drawable.bg_snapchat, snapchatShow, R.drawable.ic_snapicon));

        if (gplusShow)
            socialListBeanList.add(new SocialListBean("Google Plus", R.drawable.bg_google, gplusShow, R.drawable.ic_gplusicon));

        if (telegramShow)
            socialListBeanList.add(new SocialListBean("Telegram", R.drawable.bg_telegram, telegramShow, R.drawable.ic_teleicon));

        if (linkedinShow)
            socialListBeanList.add(new SocialListBean("Linkedin", R.drawable.bg_linkdedin, linkedinShow, R.drawable.ic_linkdicon));

        if (quoraShow)
            socialListBeanList.add(new SocialListBean("Quora", R.drawable.bg_quora, quoraShow, R.drawable.ic_quroicon));

        adapter.addAll(socialListBeanList);

        rvMessages.setAdapter(adapter);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        /*mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                *//*if (adapter.getItemViewType(position) == adapter.getViewTypeAdBanner())
                    return 2;
                else return 1;*//*
                return (position % 2) == 0 ? 2 : 1;
            }
        });*/
        rvMessages.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
        if (socialListBeanList.size() == 0) {
            Toast.makeText(getActivity(), "All apps hide", Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // adapterWrapper.release();.

    }

    @Override
    public void onPause() {
        //  adapterWrapper.pauseAll();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        //  adapterWrapper.resumeAll();
    }
}
