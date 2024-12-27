package com.samsung.android.settings.wifi.develop.homewifi;

import android.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.view.View;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class MainActivity$$ExternalSyntheticLambda1
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MainActivity f$0;

    public /* synthetic */ MainActivity$$ExternalSyntheticLambda1(
            MainActivity mainActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = mainActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        WifiManager wifiManager;
        int i = 4;
        int i2 = this.$r8$classId;
        MainActivity mainActivity = this.f$0;
        switch (i2) {
            case 0:
                if (!mainActivity.mWaitingForScanResult
                        && (wifiManager = mainActivity.mWifiManager) != null) {
                    if (!wifiManager.isWifiEnabled()) {
                        new AlertDialog.Builder(mainActivity)
                                .setTitle("Wi-Fi is off")
                                .setMessage(
                                        "Please turn on Wi-Fi then press 'Refresh' button again.")
                                .setNeutralButton(
                                        R.string.ok, (DialogInterface.OnClickListener) null)
                                .show();
                        break;
                    } else if (!mainActivity.mWifiManager.startScan()) {
                        SemLog.d(
                                "HomeWifi.MainActivity", "Failed to request scan (scan throttle?)");
                        break;
                    } else {
                        SemLog.d("HomeWifi.MainActivity", "Full scan requested");
                        mainActivity.mWaitingForScanResult = true;
                        if (mainActivity.mCurrentFragment == mainActivity.mScanFragment) {
                            mainActivity.mLeftButton.setEnabled(false);
                            mainActivity.mLeftButton.setVisibility(4);
                            mainActivity.mRightButton.setVisibility(4);
                            mainActivity.mProgressBar.setVisibility(0);
                            break;
                        }
                    }
                }
                break;
            case 1:
                int i3 = MainActivity.$r8$clinit;
                mainActivity.initAdviserFragment();
                break;
            case 2:
                int i4 = MainActivity.$r8$clinit;
                mainActivity.initScanFragment();
                break;
            case 3:
                int i5 = MainActivity.$r8$clinit;
                mainActivity.getClass();
                SemLog.d("HomeWifi.MainActivity", "initSignalGuideFragment");
                if (mainActivity.mSignalGuideFragment == null) {
                    mainActivity.mSignalGuideFragment = new SignalGuideFragment();
                }
                mainActivity.mCurrentFragment = mainActivity.mSignalGuideFragment;
                FragmentManagerImpl supportFragmentManager =
                        mainActivity.getSupportFragmentManager();
                BackStackRecord m =
                        BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                                supportFragmentManager, supportFragmentManager);
                m.replace(
                        com.android.settings.R.id.container,
                        mainActivity.mSignalGuideFragment,
                        null);
                m.commitInternal(false);
                mainActivity.setProgress(2, "Prepare to search for weak signal area");
                mainActivity.setButtons("Back", "Start");
                mainActivity.resetProgressAnimation();
                mainActivity.setProgressAnimation(2, 2);
                mainActivity.mLeftButton.setOnClickListener(
                        new MainActivity$$ExternalSyntheticLambda1(mainActivity, i));
                mainActivity.mRightButton.setOnClickListener(
                        new MainActivity$$ExternalSyntheticLambda1(mainActivity, 5));
                break;
            case 4:
                int i6 = MainActivity.$r8$clinit;
                mainActivity.initAdviserFragment();
                break;
            case 5:
                int i7 = MainActivity.$r8$clinit;
                mainActivity.getClass();
                SemLog.d("HomeWifi.MainActivity", "initSignalMeasureFragment");
                if (mainActivity.mSignalMeasureFragment == null) {
                    mainActivity.mSignalMeasureFragment = new SignalMeasureFragment();
                }
                SignalMeasureFragment signalMeasureFragment = mainActivity.mSignalMeasureFragment;
                mainActivity.mCurrentFragment = signalMeasureFragment;
                ArrayList arrayList = mainActivity.mSelectedBssids;
                signalMeasureFragment.getClass();
                SemLog.i("HomeWifi.SignalMeasureFragment", "selected bssids=" + arrayList.size());
                signalMeasureFragment.mBssidList = arrayList;
                FragmentManagerImpl supportFragmentManager2 =
                        mainActivity.getSupportFragmentManager();
                BackStackRecord m2 =
                        BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                                supportFragmentManager2, supportFragmentManager2);
                m2.replace(
                        com.android.settings.R.id.container,
                        mainActivity.mSignalMeasureFragment,
                        null);
                m2.commitInternal(false);
                mainActivity.setProgress(3, "Measure Wi-Fi signal strength");
                mainActivity.setButtons(null, "Done");
                mainActivity.resetProgressAnimation();
                mainActivity.setProgressAnimation(3, 0);
                mainActivity.mLeftButton.setOnClickListener(null);
                mainActivity.mRightButton.setOnClickListener(
                        new MainActivity$$ExternalSyntheticLambda1(mainActivity, 6));
                break;
            default:
                if (mainActivity.mSignalMeasureFragment != null) {
                    SALogging.insertSALog("WIFI_LABS", "2004");
                    SignalMeasureFragment signalMeasureFragment2 =
                            mainActivity.mSignalMeasureFragment;
                    signalMeasureFragment2.getClass();
                    SemLog.d("HomeWifi.SignalMeasureFragment", "stopScan");
                    signalMeasureFragment2.mHandler.removeMessages(1);
                    RssiChart rssiChart = mainActivity.mSignalMeasureFragment.mRssiChartView;
                    boolean isThereWeakArea =
                            rssiChart != null ? rssiChart.isThereWeakArea() : false;
                    new AlertDialog.Builder(mainActivity)
                            .setTitle(
                                    isThereWeakArea
                                            ? "Weak signal area found"
                                            : "No weak signal area")
                            .setMessage(
                                    isThereWeakArea
                                            ? "Internet access may not be available in weak signal"
                                                  + " area. We recommend to install extra Wi-Fi"
                                                  + " routers or to move the location of the"
                                                  + " existing Wi-Fi routers to minimize weak"
                                                  + " signal area."
                                            : "Excellent!\n"
                                                  + "There is no weak signal area in your home.")
                            .setPositiveButton(
                                    "Exit",
                                    new MainActivity$$ExternalSyntheticLambda3(mainActivity, 2))
                            .setNegativeButton(
                                    "Resume",
                                    new MainActivity$$ExternalSyntheticLambda3(mainActivity, 1))
                            .show();
                    break;
                }
                break;
        }
    }
}
