package com.itechnotion.allinone.settings;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.itechnotion.allinone.R;
import com.itechnotion.allinone.activity.HomeActivity;
import com.itechnotion.allinone.activity.WebviewActivity;
import com.itechnotion.allinone.model.SocialListBean;
import com.itechnotion.allinone.utils.AppConstants;
import com.itechnotion.allinone.utils.SharedObjects;

import java.util.List;

public class AppsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mFbTv;
    private Switch mFbSwitch;
    private LinearLayout mFbLin;
    private ImageView mAppsArrow;
    private TextView mInstaTv;
    private Switch mInstaSwitch;
    private LinearLayout mInstaLin;
    private TextView mPinTv;
    private Switch mPinSwitch;
    private LinearLayout mPinLin;
    private TextView mTwitTv;
    private Switch mTwitSwitch;
    private LinearLayout mTwitLin;
    private TextView mSkypeTv;
    private Switch mSkypeSwitch;
    private LinearLayout mSkypeLin;
    private TextView mSnapchatTv;
    private Switch mSnapchatSwitch;
    private LinearLayout mSnapchatLin;
    private TextView mGplusTv;
    private Switch mGplusSwitch;
    private LinearLayout mGplusLin;
    private TextView mTelegramTv;
    private Switch mTelegramSwitch;
    private LinearLayout mTelegramLin;
    private TextView mLinkedinTv;
    private Switch mLinkedinSwitch;
    private LinearLayout mLinkedinLin;
    private TextView mQuoraTv;
    private Switch mQuoraSwitch;
    private LinearLayout mQuoraLin;
    SharedObjects sharedObjects;
    ImageView imghome, imgsetting;
    LinearLayout linearLayout;
    FrameLayout frameLayout;
    TextView imgappmanage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        initView();
    }

    private void initView() {
        mFbTv = findViewById(R.id.tv_fb);
        mFbSwitch = findViewById(R.id.fbSwitch);
        mFbSwitch.setOnClickListener(this);
        mFbLin = findViewById(R.id.lin_fb);
        mFbLin.setOnClickListener(this);
        mAppsArrow = findViewById(R.id.arrow_apps);
        mInstaTv = findViewById(R.id.tv_insta);
        mInstaSwitch = findViewById(R.id.instaSwitch);
        mInstaSwitch.setOnClickListener(this);
        mInstaLin = findViewById(R.id.lin_insta);
        mInstaLin.setOnClickListener(this);
        mPinTv = findViewById(R.id.tv_pin);
        mPinSwitch = findViewById(R.id.pinSwitch);
        mPinSwitch.setOnClickListener(this);
        mPinLin = findViewById(R.id.lin_pin);
        mPinLin.setOnClickListener(this);
        mTwitTv = findViewById(R.id.tv_twit);
        mTwitSwitch = findViewById(R.id.twitSwitch);
        mTwitSwitch.setOnClickListener(this);
        mTwitLin = findViewById(R.id.lin_twit);
        mTwitLin.setOnClickListener(this);
        mSkypeTv = findViewById(R.id.tv_skype);
        mSkypeSwitch = findViewById(R.id.skypeSwitch);
        mSkypeSwitch.setOnClickListener(this);
        mSkypeLin = findViewById(R.id.lin_skype);
        mSkypeLin.setOnClickListener(this);
        mSnapchatTv = findViewById(R.id.tv_snapchat);
        mSnapchatSwitch = findViewById(R.id.snapchatSwitch);
        mSnapchatSwitch.setOnClickListener(this);
        mSnapchatLin = findViewById(R.id.lin_snapchat);
        mSnapchatLin.setOnClickListener(this);
        mGplusTv = findViewById(R.id.tv_gplus);
        mGplusSwitch = findViewById(R.id.gplusSwitch);
        mGplusSwitch.setOnClickListener(this);
        mGplusLin = findViewById(R.id.lin_gplus);
        mGplusLin.setOnClickListener(this);
        mTelegramTv = findViewById(R.id.tv_telegram);
        mTelegramSwitch = findViewById(R.id.telegramSwitch);
        mTelegramSwitch.setOnClickListener(this);
        mTelegramLin = findViewById(R.id.lin_telegram);
        mTelegramLin.setOnClickListener(this);
        mLinkedinTv = findViewById(R.id.tv_linkedin);
        mLinkedinSwitch = findViewById(R.id.linkedinSwitch);
        mLinkedinSwitch.setOnClickListener(this);
        mLinkedinLin = findViewById(R.id.lin_linkedin);
        mLinkedinLin.setOnClickListener(this);
        mQuoraTv = findViewById(R.id.tv_quora);
        mQuoraSwitch = findViewById(R.id.quoraSwitch);
        mQuoraSwitch.setOnClickListener(this);
        mQuoraLin = findViewById(R.id.lin_quora);
        mQuoraLin.setOnClickListener(this);
        imghome = findViewById(R.id.imghome);
        imgsetting = findViewById(R.id.imgsetting);
        linearLayout = findViewById(R.id.ll_tab);
        frameLayout = findViewById(R.id.flContent);
        imgappmanage = findViewById(R.id.imgappmanage);
        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
              /*  Intent i = new Intent(AppsActivity.this, HomeActivity.class);
                startActivity(i);*/
            }
        });

        imgsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //onBackPressed();
                Intent i = new Intent(AppsActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });

        sharedObjects = new SharedObjects(this);

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


        mFbSwitch.setChecked(fbShow);
        mInstaSwitch.setChecked(instaShow);
        mPinSwitch.setChecked(pinterestShow);
        mTwitSwitch.setChecked(twitterShow);
        mSkypeSwitch.setChecked(skypeShow);
        mSnapchatSwitch.setChecked(snapchatShow);
        mGplusSwitch.setChecked(gplusShow);
        mTelegramSwitch.setChecked(telegramShow);
        mLinkedinSwitch.setChecked(linkedinShow);
        mQuoraSwitch.setChecked(quoraShow);


        mFbSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.FACEBOOK, true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.FACEBOOK, false);
                }
            }
        });


        mInstaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.INSTAGRAM, true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.INSTAGRAM, false);
                }
            }
        });

        mPinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.PINTEREST, true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.PINTEREST, false);
                }
            }
        });

        mTwitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.TWITTER, true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.TWITTER, false);
                }
            }
        });

        mSkypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.SKYPE, true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.SKYPE, false);
                }
            }
        });

        mSnapchatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.SNAPCHAT, true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.SNAPCHAT, false);
                }
            }
        });


        mGplusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.GPLUS, true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.GPLUS, false);
                }
            }
        });

        mTelegramSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.TELEGRAM, true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.TELEGRAM, false);
                }
            }
        });

        mLinkedinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.LINKEDIN, true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.LINKEDIN, false);
                }
            }
        });

        mQuoraSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.QUORA, true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean(AppConstants.QUORA, false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fbSwitch:
                // TODO 18/04/30

                break;
            case R.id.lin_fb:
                // TODO 18/04/30
                if (mFbSwitch.isChecked()) {
                    mFbSwitch.setChecked(false);
                } else {
                    mFbSwitch.setChecked(true);
                }
                break;
            case R.id.instaSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_insta:
                // TODO 18/04/30
                if (mInstaSwitch.isChecked()) {
                    mInstaSwitch.setChecked(false);
                } else {
                    mInstaSwitch.setChecked(true);
                }
                break;
            case R.id.pinSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_pin:
                // TODO 18/04/30
                if (mPinSwitch.isChecked()) {
                    mPinSwitch.setChecked(false);
                } else {
                    mPinSwitch.setChecked(true);
                }
                break;
            case R.id.twitSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_twit:
                // TODO 18/04/30
                if (mTwitSwitch.isChecked()) {
                    mTwitSwitch.setChecked(false);
                } else {
                    mTwitSwitch.setChecked(true);
                }
                break;
            case R.id.skypeSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_skype:
                // TODO 18/04/30
                if (mSkypeSwitch.isChecked()) {
                    mSkypeSwitch.setChecked(false);
                } else {
                    mSkypeSwitch.setChecked(true);
                }
                break;
            case R.id.snapchatSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_snapchat:
                // TODO 18/04/30
                if (mSnapchatSwitch.isChecked()) {
                    mSnapchatSwitch.setChecked(false);
                } else {
                    mSnapchatSwitch.setChecked(true);
                }
                break;
            case R.id.gplusSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_gplus:
                // TODO 18/04/30
                if (mGplusSwitch.isChecked()) {
                    mGplusSwitch.setChecked(false);
                } else {
                    mGplusSwitch.setChecked(true);
                }
                break;
            case R.id.telegramSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_telegram:
                // TODO 18/04/30
                if (mTelegramSwitch.isChecked()) {
                    mTelegramSwitch.setChecked(false);
                } else {
                    mTelegramSwitch.setChecked(true);
                }
                break;
            case R.id.linkedinSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_linkedin:
                // TODO 18/04/30
                if (mLinkedinSwitch.isChecked()) {
                    mLinkedinSwitch.setChecked(false);
                } else {
                    mLinkedinSwitch.setChecked(true);
                }
                break;
            case R.id.quoraSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_quora:
                // TODO 18/04/30
                if (mQuoraSwitch.isChecked()) {
                    mQuoraSwitch.setChecked(false);
                } else {
                    mQuoraSwitch.setChecked(true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
