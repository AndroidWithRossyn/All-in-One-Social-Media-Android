package com.itechnotion.allinone.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.github.omadahealth.lollipin.lib.managers.AppLock;
import com.github.omadahealth.lollipin.lib.managers.LockManager;
import com.itechnotion.allinone.R;
import com.itechnotion.allinone.lock_screen.CustomPinActivity;
import com.itechnotion.allinone.lock_screen.LockMainActivity;
import com.itechnotion.allinone.utils.SharedObjects;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 2000;
    SharedObjects sharedObjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LockManager<CustomPinActivity> lockManager = LockManager.getInstance();
        lockManager.enableAppLock(this, CustomPinActivity.class);
        lockManager.getAppLock().setShouldShowForgot(false);
        sharedObjects = new SharedObjects(this);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Boolean isLock = sharedObjects.preferencesEditor.getBoolean("isLock");
                if (!isLock) {
                    Intent intent = new Intent(SplashActivity.this, CustomPinActivity.class);
                    intent.putExtra(AppLock.EXTRA_TYPE, AppLock.UNLOCK_PIN);
                    startActivity(intent);
                } else {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
