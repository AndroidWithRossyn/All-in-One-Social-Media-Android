package com.itechnotion.allinone.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.omadahealth.lollipin.lib.managers.AppLock;
import com.itechnotion.allinone.R;
import com.itechnotion.allinone.activity.HomeActivity;
import com.itechnotion.allinone.activity.WebviewActivity;
import com.itechnotion.allinone.lock_screen.CustomPinActivity;
import com.itechnotion.allinone.utils.SharedObjects;
import com.onesignal.OneSignal;

import java.io.File;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_ENABLE = 11;

    private TextView mManageappTv;
    private ImageView mImgabouterrow;
    private LinearLayout mManageappLin;
    private TextView mApplockTv;
    private Switch mApplockSwitch;
    private LinearLayout mApplockLin;
    private ImageView mAppsArrow;
    private TextView mEnablelocationTv;
    private Switch mEnablelocationSwitch;
    private LinearLayout mEnableLin;
    private TextView mNotifyTv;
    private Switch mNotificationSwitch;
    private LinearLayout mNotificationLin;
    private TextView mSavedataTv;
    private Switch mSavedataSwitch;
    private LinearLayout mSavedataLin;
    private TextView mClearcacheTv;
    private Switch mClearcacheSwitch;
    private LinearLayout mClearcacheLin;
    private TextView mTextsizeTv;
    private ImageView mTextsizeSwitch;
    private LinearLayout mTextLin;

    ImageView imghome, imgsetting;
    LinearLayout linearLayout;
    FrameLayout frameLayout;
    TextView imgappmanage;
    RadioButton bigBtn, smallbtn, largeBtn, exlargbtn, exsmallbtn;
    private static boolean activityStarted;
    SharedObjects sharedObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mManageappTv = findViewById(R.id.tv_manageapp);
        mImgabouterrow = (ImageView) findViewById(R.id.imgabouterrow);
        mManageappLin = findViewById(R.id.lin_manageapp);
        mManageappLin.setOnClickListener(this);
        mApplockTv = findViewById(R.id.tv_applock);
        mApplockSwitch = findViewById(R.id.applockSwitch);
        mApplockSwitch.setOnClickListener(this);
        mApplockLin = findViewById(R.id.lin_Applock);
        mApplockLin.setOnClickListener(this);
        mAppsArrow = findViewById(R.id.arrow_apps);
        mEnablelocationTv = (TextView) findViewById(R.id.tv_enablelocation);
        mEnablelocationSwitch = (Switch) findViewById(R.id.enablelocationSwitch);
        mEnablelocationSwitch.setOnClickListener(this);
        mEnableLin = (LinearLayout) findViewById(R.id.lin_enable);
        mEnableLin.setOnClickListener(this);
        mNotifyTv = (TextView) findViewById(R.id.tv_notify);
        mNotificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        mNotificationSwitch.setOnClickListener(this);
        mNotificationLin = (LinearLayout) findViewById(R.id.lin_notification);
        mNotificationLin.setOnClickListener(this);
        mSavedataTv = (TextView) findViewById(R.id.tv_savedata);
        mSavedataSwitch = (Switch) findViewById(R.id.savedataSwitch);
        mSavedataSwitch.setOnClickListener(this);
        mSavedataLin = (LinearLayout) findViewById(R.id.lin_savedata);
        mSavedataLin.setOnClickListener(this);
        mClearcacheTv = (TextView) findViewById(R.id.tv_clearcache);
        mClearcacheSwitch = (Switch) findViewById(R.id.clearcacheSwitch);
        mClearcacheSwitch.setOnClickListener(this);
        mClearcacheLin = (LinearLayout) findViewById(R.id.lin_clearcache);
        mClearcacheLin.setOnClickListener(this);
        mTextsizeTv = (TextView) findViewById(R.id.tv_textsize);
        mTextsizeSwitch = (ImageView) findViewById(R.id.textsizeSwitch);
        mTextLin = (LinearLayout) findViewById(R.id.lin_text);
        mTextLin.setOnClickListener(this);

        imghome = findViewById(R.id.imghome);
        imgsetting = findViewById(R.id.imgsetting);
        linearLayout = findViewById(R.id.ll_tab);
        frameLayout = findViewById(R.id.flContent);
        imgappmanage = findViewById(R.id.imgappmanage);
        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent i = new Intent(SettingActivity.this, HomeActivity.class);
                startActivity(i);*/
               onBackPressed();
            }
        });

        imgsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  onBackPressed();
                Intent i = new Intent(SettingActivity.this, AppsActivity.class);
                startActivity(i);
                //onBackPressed();
            }
        });
        sharedObjects = new SharedObjects(this);
        Boolean clearShow = sharedObjects.preferencesEditor.getBoolean("CLEAR");
        Boolean notify = sharedObjects.preferencesEditor.getBoolean("NOTIFICATION");
        Boolean enablelocation = sharedObjects.preferencesEditor.getBoolean("LOCATION");
        Boolean savedata = sharedObjects.preferencesEditor.getBoolean("saveData");
        mClearcacheSwitch.setChecked(clearShow);
        mEnablelocationSwitch.setChecked(enablelocation);
        mNotificationSwitch.setChecked(notify);

        //OneSignal.setSubscription(false);
        mNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    OneSignal.setSubscription(true);
                    if (activityStarted
                            && getIntent() != null
                            && (getIntent().getFlags() & Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) != 0) {
                        finish();
                        return;
                    }
                    activityStarted = true;
                    sharedObjects.preferencesEditor.setBoolean("NOTIFICATION", true);
                } else {
                    //do something when unchecked
                    OneSignal.setSubscription(false);
                    sharedObjects.preferencesEditor.setBoolean("NOTIFICATION", false);
                }
            }
        });

        mEnablelocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean("LOCATION", true);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean("LOCATION", false);
                }
            }
        });


        if (savedata) {
            mSavedataSwitch.setChecked(false);
        } else {
            mSavedataSwitch.setChecked(true);
        }
        mSavedataSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean("saveData", false);
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean("saveData", true);
                }
            }
        });

        Boolean isLock = sharedObjects.preferencesEditor.getBoolean("isLock");
        if (isLock) {
            mApplockSwitch.setChecked(false);
        } else {
            mApplockSwitch.setChecked(true);
        }

        mApplockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean("isLock", false);
                    Intent intent = new Intent(SettingActivity.this, CustomPinActivity.class);
                    intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
                    startActivityForResult(intent, REQUEST_CODE_ENABLE);

                } else {
                    //do something when unchecked
                    Intent intent = new Intent(SettingActivity.this, CustomPinActivity.class);
                    sharedObjects.preferencesEditor.setBoolean("isLock", true);
                    intent.putExtra(AppLock.EXTRA_TYPE, AppLock.UNLOCK_PIN);
                    startActivity(intent);

                }
            }
        });

        mClearcacheSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    sharedObjects.preferencesEditor.setBoolean("CLEAR", true);
                    CookieSyncManager.createInstance(SettingActivity.this);
                    CookieManager cookieManager = CookieManager.getInstance();
                    cookieManager.removeAllCookie();
                    //clearApplicationData();
                } else {
                    //do something when unchecked
                    sharedObjects.preferencesEditor.setBoolean("CLEAR", false);
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_manageapp:
                // TODO 18/04/30
                Intent intent = new Intent(SettingActivity.this, AppsActivity.class);
                startActivity(intent);
                break;
            case R.id.applockSwitch:

                // TODO 18/04/30
                break;
            case R.id.lin_Applock:
                if (mApplockSwitch.isChecked()) {
                    mApplockSwitch.setChecked(false);
                } else {
                    mApplockSwitch.setChecked(true);
                }
                // TODO 18/04/30
                break;
            case R.id.enablelocationSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_enable:
                // TODO 18/04/30
                if (mEnablelocationSwitch.isChecked()) {
                    mEnablelocationSwitch.setChecked(false);
                } else {
                    mEnablelocationSwitch.setChecked(true);
                }
                break;
            case R.id.notificationSwitch:
                // TODO 18/04/30

                break;
            case R.id.lin_notification:
                // TODO 18/04/30
                if (mNotificationSwitch.isChecked()) {
                    mNotificationSwitch.setChecked(false);
                } else {
                    mNotificationSwitch.setChecked(true);
                }
                break;
            case R.id.savedataSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_savedata:
                // TODO 18/04/30
                if (mSavedataSwitch.isChecked()) {
                    mSavedataSwitch.setChecked(false);
                } else {
                    mSavedataSwitch.setChecked(true);
                }
                break;
            case R.id.clearcacheSwitch:
                // TODO 18/04/30
                break;
            case R.id.lin_clearcache:
                // TODO 18/04/30
                if (mClearcacheSwitch.isChecked()) {
                    mClearcacheSwitch.setChecked(false);
                } else {
                    mClearcacheSwitch.setChecked(true);
                }
                break;
            case R.id.lin_text:
                // TODO 18/04/30
                showRadioButtonDialog();
                break;
            default:
                break;
        }
    }

    private void showRadioButtonDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(SettingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);
        dialog.setTitle("Text Size");
        TextView cancle = dialog.findViewById(R.id.btncancle);
        final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
        bigBtn = dialog.findViewById(R.id.radioBig);
        smallbtn = dialog.findViewById(R.id.radioSmall);
        largeBtn = dialog.findViewById(R.id.radiostand);
        exlargbtn = dialog.findViewById(R.id.radioexlarge);
        exsmallbtn = dialog.findViewById(R.id.radioexsmall);

        Boolean big = sharedObjects.preferencesEditor.getBooleanRadio("big");
        Boolean small = sharedObjects.preferencesEditor.getBooleanRadio("small");
        Boolean regular = sharedObjects.preferencesEditor.getBooleanRadio("reguler");
        Boolean exlarge = sharedObjects.preferencesEditor.getBooleanRadio("exlarge");
        Boolean exsmall = sharedObjects.preferencesEditor.getBooleanRadio("exsmall");
        if (big) {
            bigBtn.setChecked(true);
        }
        if (small) {
            smallbtn.setChecked(true);
        }
        if (regular) {
            largeBtn.setChecked(true);
        }
        if (exlarge) {
            exlargbtn.setChecked(true);
        }
        if (exsmall) {
            exsmallbtn.setChecked(true);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = rg.findViewById(checkedId);
                int index = rg.indexOfChild(radioButton);
                // Add logic here
                switch (index) {
                    case 0:
                        sharedObjects.preferencesEditor.setBoolean("big", true);
                        sharedObjects.preferencesEditor.setBoolean("small", false);
                        sharedObjects.preferencesEditor.setBoolean("reguler", false);
                        sharedObjects.preferencesEditor.setBoolean("exlarge", false);
                        sharedObjects.preferencesEditor.setBoolean("exsmall", false);
                        dialog.dismiss();
                        break;
                    case 1:
                        sharedObjects.preferencesEditor.setBoolean("small", true);
                        sharedObjects.preferencesEditor.setBoolean("reguler", false);
                        sharedObjects.preferencesEditor.setBoolean("big", false);
                        sharedObjects.preferencesEditor.setBoolean("exlarge", false);
                        sharedObjects.preferencesEditor.setBoolean("exsmall", false);
                        dialog.dismiss();
                        break;
                    case 2:
                        sharedObjects.preferencesEditor.setBoolean("reguler", true);
                        sharedObjects.preferencesEditor.setBoolean("small", false);
                        sharedObjects.preferencesEditor.setBoolean("big", false);
                        sharedObjects.preferencesEditor.setBoolean("exlarge", false);
                        sharedObjects.preferencesEditor.setBoolean("exsmall", false);
                        dialog.dismiss();
                        break;
                    case 3:
                        sharedObjects.preferencesEditor.setBoolean("exlarge", true);
                        sharedObjects.preferencesEditor.setBoolean("exsmall", false);
                        sharedObjects.preferencesEditor.setBoolean("reguler", false);
                        sharedObjects.preferencesEditor.setBoolean("small", false);
                        sharedObjects.preferencesEditor.setBoolean("big", false);
                        dialog.dismiss();
                        break;
                    case 4:
                        sharedObjects.preferencesEditor.setBoolean("exsmall", true);
                        sharedObjects.preferencesEditor.setBoolean("exlarge", false);
                        sharedObjects.preferencesEditor.setBoolean("reguler", false);
                        sharedObjects.preferencesEditor.setBoolean("small", false);
                        sharedObjects.preferencesEditor.setBoolean("big", false);
                        dialog.dismiss();
                        break;
                }

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void clearApplicationData() {
        File cache = SettingActivity.this.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_ENABLE:
                Toast.makeText(SettingActivity.this, "PinCode enabled", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
