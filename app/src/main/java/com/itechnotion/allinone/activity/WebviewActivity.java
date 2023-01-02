package com.itechnotion.allinone.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.itechnotion.allinone.R;
import com.itechnotion.allinone.settings.SettingActivity;
import com.itechnotion.allinone.utils.SharedObjects;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class WebviewActivity extends AppCompatActivity {
    SharedObjects sharedObjects;
    public static WebView webView, mWebviewPop;
    public String comeFrom, comeFromURL;
    String geoLocationOrigin;
    private static final String TAG = HomeActivity.class.getSimpleName();
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR = 1;
    ProgressBar progressBar;
    ImageView imghome, imgsetting;
    LinearLayout linearLayout;
    FrameLayout frameLayout;
    TextView imgappmanage;
    private AlertDialog.Builder Notify;
    private String currentUrl, contentDisposition, mimeType;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    GeolocationPermissions.Callback geoLocationCallback;
    private static String target_url, target_url_prefix;
    private FrameLayout mContainer;
    private boolean show_content = true, showToolBar = true;
    private Context mContext;
    private String js = "javascript:document.addEventListener(\"DOMContentLoaded\", function(event) { Array.from(document.querySelectorAll('a[target=\"_blank\"]')).forEach(link => link.removeAttribute('target'));console.log('test');});";
    private String ITEM_SKU = "";
    private boolean isPurchased = false;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            //Check if response is positive
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null || intent.getData() == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else {
            if (requestCode == FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }

    SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mContext = this;
        isNetworkConnectionAvailable();
        final Intent intent = getIntent();
        comeFrom = intent.getStringExtra("comeFrom");
        comeFromURL = getComeFromUrl(comeFrom);
        mySwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipeContainer);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.reload();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        imghome = findViewById(R.id.imghome);
        imgsetting = findViewById(R.id.imgsetting);
        linearLayout = findViewById(R.id.ll_tab);
        frameLayout = findViewById(R.id.flContent);
        imgappmanage = findViewById(R.id.imgappmanage);
        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WebviewActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        imgsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WebviewActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });

        InterstitialAd();

        sharedObjects = new SharedObjects(this);
        Boolean textbig = sharedObjects.preferencesEditor.getBoolean("big");
        Boolean textsmall = sharedObjects.preferencesEditor.getBoolean("small");
        Boolean textreguler = sharedObjects.preferencesEditor.getBoolean("reguler");
        Boolean textexlarge = sharedObjects.preferencesEditor.getBoolean("exlarge");
        Boolean textexsmall = sharedObjects.preferencesEditor.getBoolean("exsmall");
        Boolean enablelocation = sharedObjects.preferencesEditor.getBoolean("LOCATION");
        Boolean saveData = sharedObjects.preferencesEditor.getBooleanRadio("saveData");

        webView = (WebView) findViewById(R.id.webview);
        mContainer = findViewById(R.id.webview_frame);
        checkURL(getIntent());
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        webView.setKeepScreenOn(true);
        webView.setWebChromeClient(new UriChromeClient());
        webView.setWebViewClient(new UriWebViewClient());
        //  webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4.4; One Build/KTU84L.H4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.135 Mobile Safari/537.36");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath("data/data/com.exempt/databases");
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " (XY ClientApp)");
        webSettings.setSavePassword(false);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath("");
        webSettings.setAppCacheMaxSize(5 * 1024 * 1024);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(false);
        CookieManager.getInstance().setAcceptCookie(true);
        webSettings.setAllowFileAccess(true);
        webView.requestFocusFromTouch();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 15 && Build.VERSION.SDK_INT < 19) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (!TextUtils.isEmpty(getString(R.string.zoom))) {
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
        }
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            webView.loadUrl(target_url);
            webView.evaluateJavascript(js, null);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        if (saveData) {
            // webView.getSettings().setLoadsImagesAutomatically(false);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }

        if (enablelocation) {
            webSettings.setGeolocationEnabled(true);
        } else {
            webSettings.setGeolocationEnabled(false);
        }

        if (textreguler == true) {
            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
            //webSettings.setTextZoom(150);
        } else if (textsmall == true) {
            webSettings.setTextSize(WebSettings.TextSize.SMALLER);
        } else if (textbig == true) {
            webSettings.setTextSize(WebSettings.TextSize.LARGER);
        } else if (textexlarge == true) {
            webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        } else if (textexsmall == true) {
            webSettings.setTextSize(WebSettings.TextSize.SMALLEST);
        } else {
            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(0);
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT < 19) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (isNetworkConnectionAvailable()) {
            switch (comeFrom) {
                case "Facebook":
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(Color.parseColor("#f14357af"));
                    }
                    linearLayout.setBackgroundColor(Color.parseColor("#3D4FA0"));
                    imgappmanage.setText("Facebook");
                    webView.loadUrl("https://m.facebook.com");
                    break;
                case "Instagram":
                    Window window1 = getWindow();
                    window1.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window1.setStatusBarColor(Color.parseColor("#f53c8e"));
                    }
                    linearLayout.setBackgroundColor(Color.parseColor("#DD3680"));
                    imgappmanage.setText("Instagram");
                    // webView.loadUrl("https://www.agoda.com/pages/agoda/default/DestinationSearchResult.aspx?city=233&cid=-1&checkIn=2018-08-12&checkOut=2018-08-13&los=1&rooms=1&adults=2&children=0&childages=");
                    webView.loadUrl("https://www.instagram.com/");
                    break;
                case "Pinterest":
                    Window window2 = getWindow();
                    window2.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window2.setStatusBarColor(Color.parseColor("#961417"));
                    }
                    linearLayout.setBackgroundColor(Color.parseColor("#c6181c"));
                    imgappmanage.setText("Pinterest");
                    webView.loadUrl("https://in.pinterest.com/");
                    break;
                case "Twitter":
                    Window window3 = getWindow();
                    window3.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window3.setStatusBarColor(Color.parseColor("#58a7e3"));
                    }
                    linearLayout.setBackgroundColor(Color.parseColor("#189AF1"));
                    imgappmanage.setText("Twitter");
                    webView.loadUrl("https://mobile.twitter.com/login");
                    break;
                case "Tumblr":
                    Window window4 = getWindow();
                    window4.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window4.setStatusBarColor(Color.parseColor("#4c769e"));
                    }
                    linearLayout.setBackgroundColor(Color.parseColor("#3A5875"));
                    imgappmanage.setText("Tumblr");
                    webView.loadUrl("https://www.tumblr.com/");
                    break;
                case "Snapchat":
                    Window window5 = getWindow();
                    window5.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window5.setStatusBarColor(Color.parseColor("#f1cb49"));
                    }
                    linearLayout.setBackgroundColor(Color.parseColor("#edcf66"));
                    imgappmanage.setText("Snapchat");
                    webView.loadUrl("https://accounts.snapchat.com/");
                    break;
                case "Google Plus":
                    Window window6 = getWindow();
                    window6.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window6.setStatusBarColor(Color.parseColor("#DD5246"));
                    }
                    linearLayout.setBackgroundColor(Color.parseColor("#df2e1e"));
                    imgappmanage.setText("Google Plus");
                    webView.loadUrl("https://plus.google.com/");
                    break;
                case "Telegram":
                    Window window7 = getWindow();
                    window7.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window7.setStatusBarColor(Color.parseColor("#4fa9d4"));
                    }
                    linearLayout.setBackgroundColor(Color.parseColor("#2295cc"));
                    imgappmanage.setText("Telegram");
                    webView.loadUrl("https://web.telegram.org/");
                    break;
                case "Linkedin":
                    Window window8 = getWindow();
                    window8.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window8.setStatusBarColor(Color.parseColor("#1b9be8"));
                    }
                    linearLayout.setBackgroundColor(Color.parseColor("#0f72b5"));
                    imgappmanage.setText("Linkedin");
                    webView.loadUrl("https://www.linkedin.com/");
                    break;
                case "Quora":
                    Window window9 = getWindow();
                    window9.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window9.setStatusBarColor(Color.parseColor("#e65d38"));
                    }
                    linearLayout.setBackgroundColor(Color.parseColor("#d4380e"));
                    imgappmanage.setText("Quora");
                    webView.loadUrl("https://www.quora.com/");
                    break;
            }
            mContainer.setVisibility(View.VISIBLE);
        }
    }


    public void InterstitialAd() {


        AdRequest adRequest = new AdRequest.Builder().build();

        //test ID : ca-app-pub-3940256099942544/1033173712
        InterstitialAd.load(WebviewActivity.this, "ca-app-pub-7770856719889795/7781622022", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        showInterstitial();
                    }
                }, 15000);
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
            mInterstitialAd.show(WebviewActivity.this);
        }


    }
    public void checkNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView titleView = (TextView) alertDialog.findViewById(android.R.id.message);
        titleView.setGravity(Gravity.CENTER);
    }

    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");

            return false;
        }
    }

    private String getComeFromUrl(String comeFrom) {
        String url = "";

        switch (comeFrom) {
            case "Facebook":
                url = "https://m.facebook.com";
                break;
            case "Instagram":
                //  url = "https://www.agoda.com/pages/agoda/default/DestinationSearchResult.aspx?city=233&cid=-1&checkIn=2018-08-12&checkOut=2018-08-13&los=1&rooms=1&adults=2&children=0&childages=";
                url = "https://www.instagram.com/";
            case "Pinterest":
                url = "https://in.pinterest.com/";
                break;
            case "Twitter":
                url = "https://mobile.twitter.com/login";
                break;
            case "Tumblr":
                url = "https://www.tumblr.com/";
                break;
            case "Snapchat":
                url = "https://accounts.snapchat.com/";
                break;
            case "Google Plus":
                url = "https://plus.google.com/";
                break;
            case "Telegram":
                url = "https://web.telegram.org/";
                break;
            case "Linkedin":
                url = "https://www.linkedin.com/";
                break;
            case "Quora":
                url = "https://www.quora.com/";
                break;
        }
        return url;
    }


    private void checkURL(Intent intent) {

        if (!TextUtils.isEmpty(comeFromURL)) {
            target_url = comeFromURL;
            target_url_prefix = Uri.parse(target_url).getHost();
            currentUrl = target_url;
            return;
        }

        target_url = getString(R.string.target_url);

        if (TextUtils.isEmpty(target_url)) {
            target_url = comeFromURL;
            target_url_prefix = Uri.parse(target_url).getHost();
        } else {
            target_url_prefix = Uri.parse(target_url).getHost();
        }
        currentUrl = target_url;

        if (webView != null) {
            if (mWebviewPop != null) {
                mWebviewPop.setVisibility(View.GONE);
                mContainer.removeView(mWebviewPop);
                mWebviewPop = null;
            }
            webView.setVisibility(View.VISIBLE);
        }
    }

    class UriChromeClient extends WebChromeClient {
        //For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUM = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            WebviewActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FCR);
        }

        // For Android 3.0+, above method not supported in some android 3+ versions, in such case we use this
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUM = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            WebviewActivity.this.startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FCR);
        }

        //For Android 4.1+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUM = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            WebviewActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), WebviewActivity.FCR);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
            callback.invoke(origin, true, false);
        }

        //For Android 5.0+
        public boolean onShowFileChooser(
                WebView webView, ValueCallback<Uri[]> filePathCallback,
                FileChooserParams fileChooserParams) {
            if (mUMA != null) {
                mUMA.onReceiveValue(null);
            }
            mUMA = filePathCallback;
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(WebviewActivity.this.getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCM);
                } catch (IOException ex) {
                    Log.e(TAG, "Image file creation failed", ex);
                }
                if (photoFile != null) {
                    mCM = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }
            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("*/*");
            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[0];
            }
            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
            startActivityForResult(chooserIntent, FCR);
            return true;
        }

        @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            mWebviewPop = new WebView(mContext);
            mWebviewPop.setVerticalScrollBarEnabled(false);
            mWebviewPop.setHorizontalScrollBarEnabled(false);
            mWebviewPop.setWebViewClient(new UriWebViewClient());
            mWebviewPop.getSettings().setJavaScriptEnabled(true);
            mWebviewPop.getSettings().setSavePassword(false);

            mWebviewPop.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebviewPop.getSettings().setSupportMultipleWindows(true);
            mWebviewPop.getSettings().setGeolocationEnabled(true);
            mWebviewPop.getSettings().setDomStorageEnabled(true);
            mWebviewPop.getSettings().setDatabaseEnabled(true);
            mWebviewPop.getSettings().setGeolocationEnabled(true);
            mWebviewPop.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());
           /* if (webAppInterface == null) {
                webAppInterface = new WebAppInterface(MainActivity.this, ITEM_SKU, mWebviewPop);
            }*/
            //mWebviewPop.addJavascriptInterface(new WebAppInterface(MainActivity.this, ITEM_SKU, mWebviewPop), "android");
            mWebviewPop.getSettings().setLoadWithOverviewMode(true);
            mWebviewPop.getSettings().setAllowFileAccess(true);

            if (!TextUtils.isEmpty(getString(R.string.cache))) {
                mWebviewPop.getSettings().setAppCacheEnabled(true);
                mWebviewPop.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
                mWebviewPop.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }

            mWebviewPop.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mContainer.addView(mWebviewPop);
            // mWebviewPop.setDownloadListener(WebviewActivity.this);

            if (Build.VERSION.SDK_INT >= 19) {
                mWebviewPop.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else if (Build.VERSION.SDK_INT >= 15 && Build.VERSION.SDK_INT < 19) {
                mWebviewPop.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            mWebviewPop.evaluateJavascript(js, null);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(mWebviewPop);
            resultMsg.sendToTarget();
            return true;
        }

    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    public boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    private class UriWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String host = Uri.parse(url).getHost();
            Log.e("host", host);
            Log.e("target_url_prefix", "-" + target_url_prefix);
            comeFromURL = url;
            view.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            if (target_url_prefix.equals(host)) {
                if (mWebviewPop != null) {
                    mWebviewPop.setVisibility(View.GONE);
                    mContainer.removeView(mWebviewPop);
                    mWebviewPop = null;
                }
                view.evaluateJavascript(js, null);
                return false;
            }

          /*  if (url.startsWith("www.messenger.com")){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }*/

//            boolean result = Boolean.parseBoolean(comeFromURL);
            boolean result = false;
            if (result) {
                ProgressDialogHelper.dismissProgress();
            } else {
                currentUrl = url;
                if (!show_content) {
                    ProgressDialogHelper.showProgress(WebviewActivity.this);
                }
            }
            view.evaluateJavascript(js, null);
            return result;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (url.startsWith("whatsapp://send")) {
                // WhatsappShare(url);
            } else if (url.contains(".pdf")) {
                super.onPageStarted(view, "https://docs.google.com/gview?embedded=true&url=" + url, favicon);
            } else {
                Log.e("onPageStarturl", url);
                super.onPageStarted(view, url, favicon);
                // mySwipeRefreshLayout.setRefreshing(true);

            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            mySwipeRefreshLayout.setRefreshing(false);
            webView.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('your_class_name')[0].style.display='none'; })()");
            // hide element by id
            webView.loadUrl("javascript:(function() { " +
                    "document.getElementById('your_id')[0].style.display='none';})()");
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getApplicationContext(), "Failed loading app!", Toast.LENGTH_SHORT).show();
        }


        public boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }

            return false;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        progressBar.setVisibility(View.GONE);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        HomeActivity.materialDesignFAM.close(true);
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == PermissionUtil.MY_PERMISSIONS_REQUEST_CALL) {
            //If permissionSelectFile is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                UrlHander.call(WebviewActivity.this, comeFromURL);
            }
        } else if (requestCode == PermissionUtil.MY_PERMISSIONS_REQUEST_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                UrlHander.sms(WebviewActivity.this, comeFromURL);
            }
        } else if (requestCode == PermissionUtil.MY_PERMISSIONS_REQUEST_DOWNLOAD) {
            UrlHander.download(WebviewActivity.this, comeFromURL, contentDisposition, mimeType);
        } else if (requestCode == PermissionUtil.MY_PERMISSIONS_REQUEST_GEOLOCATION) {
            if (geoLocationCallback != null) {
                geoLocationCallback.invoke(geoLocationOrigin, true, false);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public class GeoWebChromeClient extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mySwipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (webView.getScrollY() == 0)
                            mySwipeRefreshLayout.setEnabled(true);
                        else
                            mySwipeRefreshLayout.setEnabled(false);

                    }
                });
    }

    @Override
    public void onStop() {
        mySwipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }
}
