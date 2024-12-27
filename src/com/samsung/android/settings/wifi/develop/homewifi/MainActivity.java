package com.samsung.android.settings.wifi.develop.homewifi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.BssidInfo;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.SsidInfo;
import com.samsung.android.util.SemLog;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@SuppressLint({"MissingPermission"})
/* loaded from: classes3.dex */
public class MainActivity extends AppCompatActivity implements ProgressDecorator {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AdviserFragment mAdviserFragment;
    public View mBottomView;
    public Fragment mCurrentFragment;
    public TextView mLeftButton;
    public View mProgressBar;
    public View mProgressFullImageView;
    public View mProgressLayout;
    public TextView mRightButton;
    public ScanFragment mScanFragment;
    public ArrayList mSelectedBssids;
    public SignalGuideFragment mSignalGuideFragment;
    public SignalMeasureFragment mSignalMeasureFragment;
    public TextView mTitleView;
    public WifiManager mWifiManager;
    public final ArrayList mProgressViews = new ArrayList();
    public final ArrayList mProgressAniViews = new ArrayList();
    public final int mColorBlue = Color.parseColor("#0381fe");
    public final int mColorGray = Color.parseColor("#d9d3d3");
    public boolean mWaitingForScanResult = false;
    public final AnonymousClass1 mScanReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.develop.homewifi.MainActivity.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    MainActivity mainActivity;
                    WifiManager wifiManager;
                    MainActivity mainActivity2 = MainActivity.this;
                    if (mainActivity2.mWaitingForScanResult) {
                        mainActivity2.mWaitingForScanResult = false;
                        if (mainActivity2.mCurrentFragment == mainActivity2.mScanFragment) {
                            mainActivity2.mLeftButton.setEnabled(true);
                            MainActivity.this.mLeftButton.setVisibility(0);
                            MainActivity.this.mRightButton.setVisibility(0);
                            MainActivity.this.mProgressBar.setVisibility(8);
                        }
                    }
                    if (!intent.getBooleanExtra("resultsUpdated", true)
                            || (wifiManager = (mainActivity = MainActivity.this).mWifiManager)
                                    == null) {
                        return;
                    }
                    mainActivity.scanResultUpdated(wifiManager.getScanResults());
                }
            };
    public final AnonymousClass2 mOnBackPressedCallBack =
            new OnBackPressedCallback() { // from class:
                                          // com.samsung.android.settings.wifi.develop.homewifi.MainActivity.2
                @Override // androidx.activity.OnBackPressedCallback
                public final void handleOnBackPressed() {
                    StringBuilder sb = new StringBuilder("handleOnBackPressed: fragment=");
                    MainActivity mainActivity = MainActivity.this;
                    sb.append(mainActivity.mCurrentFragment);
                    SemLog.d("HomeWifi.MainActivity", sb.toString());
                    Fragment fragment = mainActivity.mCurrentFragment;
                    if (fragment == mainActivity.mScanFragment) {
                        mainActivity.finish();
                        return;
                    }
                    if (fragment == mainActivity.mAdviserFragment) {
                        mainActivity.initScanFragment();
                    } else if (fragment == mainActivity.mSignalGuideFragment) {
                        mainActivity.initAdviserFragment();
                    } else {
                        mainActivity.getClass();
                        new AlertDialog.Builder(mainActivity)
                                .setTitle("Do you want to exit?")
                                .setMessage("Home Wi-Fi inspection is on-going.")
                                .setPositiveButton(
                                        "Exit",
                                        new MainActivity$$ExternalSyntheticLambda3(mainActivity, 0))
                                .setNegativeButton("Resume", (DialogInterface.OnClickListener) null)
                                .show();
                    }
                }
            };

    public final void initAdviserFragment() {
        SemLog.d("HomeWifi.MainActivity", "initAdviserFragment");
        if (this.mAdviserFragment == null) {
            AdviserFragment adviserFragment = new AdviserFragment();
            this.mAdviserFragment = adviserFragment;
            adviserFragment.mProgressDecorator = this;
        }
        this.mCurrentFragment = this.mAdviserFragment;
        SsidListAdapter ssidListAdapter = this.mScanFragment.mSsidListAdapter;
        ssidListAdapter.getClass();
        ArrayList arrayList = new ArrayList();
        Iterator it = ssidListAdapter.mData.iterator();
        while (it.hasNext()) {
            final SsidListAdapter.Data data = (SsidListAdapter.Data) it.next();
            if (data.selected) {
                arrayList.addAll(
                        (Collection)
                                data.bssids.stream()
                                        .map(
                                                new Function() { // from class:
                                                                 // com.samsung.android.settings.wifi.develop.homewifi.SsidListAdapter$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        BssidInfo bssidInfo = (BssidInfo) obj;
                                                        String str = SsidListAdapter.Data.this.ssid;
                                                        ExtendedBssidInfo extendedBssidInfo =
                                                                new ExtendedBssidInfo();
                                                        extendedBssidInfo.bssid = bssidInfo.bssid;
                                                        extendedBssidInfo.freq = bssidInfo.freq;
                                                        extendedBssidInfo.rssi = bssidInfo.rssi;
                                                        extendedBssidInfo.cu = bssidInfo.cu;
                                                        extendedBssidInfo.sta = bssidInfo.sta;
                                                        extendedBssidInfo.wifiStandard =
                                                                bssidInfo.wifiStandard;
                                                        extendedBssidInfo.channelWidth =
                                                                bssidInfo.channelWidth;
                                                        extendedBssidInfo.security =
                                                                bssidInfo.security;
                                                        extendedBssidInfo.extra11e =
                                                                bssidInfo.extra11e;
                                                        extendedBssidInfo.extra11k =
                                                                bssidInfo.extra11k;
                                                        extendedBssidInfo.extra11v =
                                                                bssidInfo.extra11v;
                                                        extendedBssidInfo.extra11r =
                                                                bssidInfo.extra11r;
                                                        extendedBssidInfo.pmfRequired =
                                                                bssidInfo.pmfRequired;
                                                        extendedBssidInfo.pmfCapable =
                                                                bssidInfo.pmfCapable;
                                                        extendedBssidInfo.multiLink =
                                                                bssidInfo.multiLink;
                                                        extendedBssidInfo.rnr = bssidInfo.rnr;
                                                        extendedBssidInfo.ssid = str;
                                                        return extendedBssidInfo;
                                                    }
                                                })
                                        .collect(Collectors.toList()));
            }
        }
        arrayList.sort(new SsidListAdapter$$ExternalSyntheticLambda2());
        this.mSelectedBssids = arrayList;
        AdviserFragment adviserFragment2 = this.mAdviserFragment;
        adviserFragment2.getClass();
        SemLog.i("HomeWifi.AdviserFragment", "selected bssids=" + arrayList.size());
        adviserFragment2.mBssids = arrayList;
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        BackStackRecord m =
                BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                        supportFragmentManager, supportFragmentManager);
        m.replace(R.id.container, this.mAdviserFragment, null);
        m.commitInternal(false);
        setProgress(1, "Advice for home Wi-Fi setup");
        setButtons("Back", this.mSelectedBssids.size() > 0 ? "Next" : null);
        resetProgressAnimation();
        this.mLeftButton.setOnClickListener(new MainActivity$$ExternalSyntheticLambda1(this, 2));
        this.mRightButton.setOnClickListener(new MainActivity$$ExternalSyntheticLambda1(this, 3));
    }

    public final void initScanFragment() {
        SemLog.d("HomeWifi.MainActivity", "initScanFragment");
        if (this.mScanFragment == null) {
            this.mScanFragment = new ScanFragment();
        }
        this.mCurrentFragment = this.mScanFragment;
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        BackStackRecord m =
                BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                        supportFragmentManager, supportFragmentManager);
        m.replace(R.id.container, this.mScanFragment, null);
        m.commitInternal(false);
        setProgress(0, "Select targeting Wi-Fi networks");
        setButtons("Refresh", "Next");
        resetProgressAnimation();
        setProgressAnimation(0, 3);
        this.mLeftButton.setOnClickListener(new MainActivity$$ExternalSyntheticLambda1(this, 0));
        this.mRightButton.setOnClickListener(new MainActivity$$ExternalSyntheticLambda1(this, 1));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        SemLog.d("HomeWifi.MainActivity", "onCreate");
        super.onCreate(bundle);
        setContentView(R.layout.sec_wifi_development_homewifi_main_activity);
        View findViewById = findViewById(R.id.progressbar);
        this.mProgressBar = findViewById;
        findViewById.setVisibility(8);
        this.mProgressLayout = findViewById(R.id.progress_layout);
        this.mProgressFullImageView = findViewById(R.id.progress_full_image);
        this.mLeftButton = (TextView) findViewById(R.id.button_left);
        this.mRightButton = (TextView) findViewById(R.id.button_right);
        this.mProgressViews.add(findViewById(R.id.progress_circle1));
        this.mProgressViews.add(findViewById(R.id.progress_line1));
        this.mProgressViews.add(findViewById(R.id.progress_circle2));
        this.mProgressViews.add(findViewById(R.id.progress_line2));
        this.mProgressViews.add(findViewById(R.id.progress_circle3));
        this.mProgressViews.add(findViewById(R.id.progress_line3));
        this.mProgressViews.add(findViewById(R.id.progress_circle4));
        this.mProgressAniViews.add((LottieAnimationView) findViewById(R.id.progress_ani1));
        this.mProgressAniViews.add((LottieAnimationView) findViewById(R.id.progress_ani2));
        this.mProgressAniViews.add((LottieAnimationView) findViewById(R.id.progress_ani3));
        this.mProgressAniViews.add((LottieAnimationView) findViewById(R.id.progress_ani4));
        this.mTitleView = (TextView) findViewById(R.id.title);
        this.mBottomView = findViewById(R.id.bottom);
        getOnBackPressedDispatcher().addCallback(this, this.mOnBackPressedCallBack);
        WifiManager wifiManager = (WifiManager) getSystemService(ImsProfile.PDN_WIFI);
        this.mWifiManager = wifiManager;
        scanResultUpdated(wifiManager.getScanResults());
        initScanFragment();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        unregisterReceiver(this.mScanReceiver);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        registerReceiver(this.mScanReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        this.mWaitingForScanResult = false;
    }

    public final void resetProgressAnimation() {
        Iterator it = this.mProgressAniViews.iterator();
        while (it.hasNext()) {
            ((LottieAnimationView) it.next()).setVisibility(4);
        }
    }

    public final void scanResultUpdated(List list) {
        ScanFragment scanFragment;
        SemLog.d(
                "HomeWifi.MainActivity",
                "Scan result updated=" + list.size() + ", fragment=" + this.mCurrentFragment);
        Repository repository = Repository.LazyHolder.INSTANCE;
        repository.update(list);
        HashSet hashSet = new HashSet();
        Iterator it = repository.ssidList.iterator();
        while (it.hasNext()) {
            SsidInfo ssidInfo = (SsidInfo) it.next();
            Iterator it2 = ssidInfo.bssids.iterator();
            while (it2.hasNext()) {
                BssidInfo bssidInfo = (BssidInfo) it2.next();
                for (String str : bssidInfo.security.split("\\[")) {
                    String[] split = str.split("-");
                    if (split.length >= 3) {
                        hashSet.addAll(Arrays.asList(split[1].split("\\+")));
                    } else if (split.length == 1) {
                        hashSet.addAll(
                                Arrays.asList(
                                        split[0].replace("]", ApnSettings.MVNO_NONE).split("\\+")));
                    }
                }
                if (!bssidInfo.extra11e) {
                    SemLog.d("HomeWifi.MainActivity", "SSID=" + ssidInfo.ssid);
                }
            }
        }
        final StringBuilder sb = new StringBuilder();
        hashSet.forEach(
                new Consumer() { // from class:
                                 // com.samsung.android.settings.wifi.develop.homewifi.MainActivity$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        StringBuilder sb2 = sb;
                        int i = MainActivity.$r8$clinit;
                        sb2.append((String) obj);
                        sb2.append(" ");
                    }
                });
        SemLog.d("HomeWifi.MainActivity", "dumpAllKeyMgmt: " + sb.toString());
        Fragment fragment = this.mCurrentFragment;
        if (fragment != null && fragment == (scanFragment = this.mScanFragment)) {
            final SsidListAdapter ssidListAdapter = scanFragment.mSsidListAdapter;
            ArrayList arrayList = scanFragment.mRepo.ssidList;
            ssidListAdapter.getClass();
            HashSet hashSet2 = new HashSet();
            int size = ssidListAdapter.mData.size();
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                SsidInfo ssidInfo2 = (SsidInfo) it3.next();
                Integer num = (Integer) ssidListAdapter.mSsidSearchHelper.get(ssidInfo2.ssid);
                if (num != null) {
                    SsidListAdapter.Data data =
                            (SsidListAdapter.Data) ssidListAdapter.mData.get(num.intValue());
                    int size2 = data.bssids.size();
                    Iterator it4 = ssidInfo2.bssids.iterator();
                    while (it4.hasNext()) {
                        BssidInfo bssidInfo2 = (BssidInfo) it4.next();
                        int i = 0;
                        while (true) {
                            if (i >= size2) {
                                break;
                            }
                            if (bssidInfo2.bssid.equals(((BssidInfo) data.bssids.get(i)).bssid)) {
                                data.bssids.set(i, bssidInfo2);
                                break;
                            }
                            i++;
                        }
                        if (i == size2) {
                            data.bssids.add(bssidInfo2);
                        }
                    }
                    hashSet2.add(num);
                } else {
                    ssidListAdapter.mSsidSearchHelper.put(
                            ssidInfo2.ssid, Integer.valueOf(ssidListAdapter.mData.size()));
                    ssidListAdapter.mData.add(new SsidListAdapter.Data(ssidInfo2));
                }
            }
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            size, "# of SSIDs added: ", " -> ");
            m.append(ssidListAdapter.mData.size());
            m.append(", updated: ");
            m.append(hashSet2.size());
            SemLog.i("HomeWifi.SsidListAdapter", m.toString());
            ssidListAdapter.notifyItemRangeInserted(size, ssidListAdapter.mData.size() - size);
            hashSet2.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.wifi.develop.homewifi.SsidListAdapter$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            SsidListAdapter.this.notifyItemChanged(((Integer) obj).intValue());
                        }
                    });
        }
    }

    public final void setButtons(String str, String str2) {
        if (str != null) {
            this.mLeftButton.setText(str);
            this.mLeftButton.setVisibility(0);
            this.mLeftButton.setEnabled(true);
        } else {
            this.mLeftButton.setVisibility(4);
        }
        if (str2 == null) {
            this.mRightButton.setVisibility(4);
            return;
        }
        this.mRightButton.setText(str2);
        this.mRightButton.setVisibility(0);
        this.mRightButton.setEnabled(true);
    }

    public final void setProgress(int i, String str) {
        this.mProgressLayout.setVisibility(0);
        this.mProgressFullImageView.setVisibility(8);
        int min = Math.min(Math.max(0, i), 3);
        int i2 = 0;
        while (i2 < (min * 2) + 1) {
            if (i2 % 2 == 0) {
                ((View) this.mProgressViews.get(i2))
                        .setBackgroundResource(R.drawable.sec_wifi_circle);
            } else {
                ((View) this.mProgressViews.get(i2))
                        .setBackgroundTintList(ColorStateList.valueOf(this.mColorBlue));
            }
            ((View) this.mProgressViews.get(i2)).setVisibility(0);
            i2++;
        }
        while (i2 < this.mProgressViews.size()) {
            if (i2 % 2 == 0) {
                ((View) this.mProgressViews.get(i2))
                        .setBackgroundResource(R.drawable.sec_wifi_torus);
            } else {
                ((View) this.mProgressViews.get(i2))
                        .setBackgroundTintList(ColorStateList.valueOf(this.mColorGray));
            }
            ((View) this.mProgressViews.get(i2)).setVisibility(0);
            i2++;
        }
        this.mTitleView.setText(str);
    }

    public final void setProgressAnimation(int i, int i2) {
        if (i < 0 || i >= this.mProgressAniViews.size()) {
            return;
        }
        ((View) this.mProgressViews.get(i * 2)).setVisibility(4);
        if (i2 == 0) {
            ((LottieAnimationView) this.mProgressAniViews.get(i))
                    .setBackgroundResource(R.drawable.sec_wifi_labs_progress_circle_background);
            ((LottieAnimationView) this.mProgressAniViews.get(i))
                    .setAnimation("sec_wifi_labs_progress_loading.json");
            ((LottieAnimationView) this.mProgressAniViews.get(i)).setRepeatCount(-1);
            ((LottieAnimationView) this.mProgressAniViews.get(i)).playAnimation$1();
            ((LottieAnimationView) this.mProgressAniViews.get(i)).setVisibility(0);
            return;
        }
        if (i2 == 1) {
            ((LottieAnimationView) this.mProgressAniViews.get(i))
                    .setBackgroundResource(R.drawable.sec_wifi_labs_progress_circle_background);
            ((LottieAnimationView) this.mProgressAniViews.get(i))
                    .setAnimation("sec_wifi_labs_progress_loading_after.json");
            ((LottieAnimationView) this.mProgressAniViews.get(i)).setRepeatCount(0);
            ((LottieAnimationView) this.mProgressAniViews.get(i)).playAnimation$1();
            ((LottieAnimationView) this.mProgressAniViews.get(i)).setVisibility(0);
            return;
        }
        if (i2 == 2) {
            ((LottieAnimationView) this.mProgressAniViews.get(i))
                    .setBackgroundResource(R.drawable.sec_wifi_labs_progress_circle_background);
            ((LottieAnimationView) this.mProgressAniViews.get(i))
                    .setAnimation("sec_wifi_labs_progress_loading_completed.json");
            ((LottieAnimationView) this.mProgressAniViews.get(i)).setRepeatCount(0);
            ((LottieAnimationView) this.mProgressAniViews.get(i)).playAnimation$1();
            ((LottieAnimationView) this.mProgressAniViews.get(i)).setVisibility(0);
            return;
        }
        if (i2 == 3) {
            ((LottieAnimationView) this.mProgressAniViews.get(i))
                    .setBackgroundResource(R.drawable.sec_wifi_labs_progress_circle_background);
            ((LottieAnimationView) this.mProgressAniViews.get(i))
                    .setAnimation("sec_wifi_labs_progress_loading_waiting.json");
            ((LottieAnimationView) this.mProgressAniViews.get(i)).setRepeatCount(-1);
            ((LottieAnimationView) this.mProgressAniViews.get(i)).playAnimation$1();
            ((LottieAnimationView) this.mProgressAniViews.get(i)).setVisibility(0);
            return;
        }
        if (i2 == 4) {
            ((LottieAnimationView) this.mProgressAniViews.get(i))
                    .setImageResource(R.drawable.sec_wifi_labs_progress_circle_alert);
            ((LottieAnimationView) this.mProgressAniViews.get(i)).cancelAnimation();
            ((LottieAnimationView) this.mProgressAniViews.get(i)).setVisibility(0);
        } else {
            if (i2 != 5) {
                return;
            }
            this.mProgressLayout.setVisibility(8);
            this.mProgressFullImageView.setVisibility(0);
        }
    }
}
