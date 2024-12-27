package com.samsung.android.settings.wifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.ButtonBarHandler;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;
import com.android.settings.widget.LoadingViewController;
import com.android.settings.wifi.WifiSettings;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;
import com.sec.ims.configuration.DATA;
import com.sec.ims.extensions.WiFiManagerExt;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiSetupWizardActivity extends SuwBaseActivity
        implements ButtonBarHandler, IWifiSetupWizardButtonAction, WifiLoadingControllerListener {
    public static final boolean DEV = Debug.semIsProductDev();
    public ProgressDialog activationLoadingDialog;
    public final AnonymousClass1 mActivationReceiver;
    public WifiSetupWizardActivity mActivity;
    public boolean mAllowCaptiveByKme;
    public DataOnButtonUpdater mButtonUpdater;
    public ConnectivityManager mCm;
    public Context mContext;
    public DataOnButtonUpdater mDataOnButtonUpdater;
    public boolean mIsFristResultReceived;
    public boolean mIsFrpChallengeRequired;
    public boolean mIsKmeSupported;
    public boolean mIsLoading;
    public boolean mIsNetworkCheckingOnGoing;
    public boolean mIsNetworkRequiredByCustomer;
    public boolean mIsNetworkRequiredByFrp;
    public boolean mIsNetworkRequiredByKme;
    public boolean mIsNetworkValidated;
    public boolean mIsSkipButtonEnabled;
    public boolean mIsUsedSprintNewSetupWizard;
    public boolean mIsWifiCompletelyAvailable;
    public boolean mIsWifiTurningOn;
    public long mLastClickTime;
    public LoadingViewController mLoadingViewController;
    public final AnonymousClass1 mReceiver;
    public TelephonyManager mTelephonyManager;
    public WifiManager mWifiManager;
    public WifiSettings mWifiSettings;
    public DataOnButtonUpdater mWifiStateButtonUpdater;
    public AnonymousClass9 mNetworkCallback = null;
    public boolean mIsDialogPopedUp = false;
    public boolean mAutoFinishByConnectNetworkEnabled = true;
    public final boolean mIsEsimSupported =
            SemCscFeature.getInstance().getBoolean("CscFeature_SetupWizard_SupportEsimAsPrimary");
    public boolean mIsEsimDetected = false;
    public boolean mIsPsimDetected = false;
    public final boolean mIsSupportBootstrap =
            SemCscFeature.getInstance().getBoolean("CscFeature_RIL_SupporteSimBootstrap", false);
    public boolean isSupportDeviceActivationCHN = false;
    public int mCurrentWifiState = 4;
    public InternetResultHandler mResultHandler = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.WifiSetupWizardActivity$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiSetupWizardActivity this$0;

        public /* synthetic */ AnonymousClass3(
                WifiSetupWizardActivity wifiSetupWizardActivity, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiSetupWizardActivity;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            switch (this.$r8$classId) {
                case 0:
                    SetupWizardLogMsg.out(
                            "WifiSetupWizard", "selectNetworkPopUpShowing setPositiveButton");
                    this.this$0.setResult(18);
                    this.this$0.finish();
                    break;
                case 1:
                    SetupWizardLogMsg.out(
                            "WifiSetupWizard", "selectNetworkPopUpShowing setNeutralButton");
                    this.this$0.setResult(17);
                    this.this$0.finish();
                    break;
                default:
                    SetupWizardLogMsg.out(
                            "WifiSetupWizard", "activationShowing PositiveButton onClick");
                    this.this$0.mainAction(false);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.WifiSetupWizardActivity$5, reason: invalid class name */
    public final class AnonymousClass5 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            SetupWizardLogMsg.out("WifiSetupWizard", "selectNetworkPopUpShowing setNegativeButton");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.WifiSetupWizardActivity$6, reason: invalid class name */
    public final class AnonymousClass6 implements DialogInterface.OnShowListener {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass6(int i) {
            this.$r8$classId = i;
        }

        @Override // android.content.DialogInterface.OnShowListener
        public final void onShow(DialogInterface dialogInterface) {
            switch (this.$r8$classId) {
                case 0:
                    ((AlertDialog) dialogInterface).getButton(-1).setFocusable(true);
                    break;
                default:
                    ((AlertDialog) dialogInterface).getButton(-1).setFocusable(true);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InternetResultHandler extends Handler {
        public InternetResultHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            final int i = message.what;
            WifiSetupWizardActivity.this.mActivity.runOnUiThread(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.wifi.WifiSetupWizardActivity.InternetResultHandler.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            boolean isNextStepBlocked;
                            boolean z;
                            DataOnButtonUpdater dataOnButtonUpdater;
                            String string =
                                    WifiApUtils.getString(
                                            WifiSetupWizardActivity.this.mActivity,
                                            R.string.wifi_internet_unavailable_message);
                            WifiSetupWizardActivity wifiSetupWizardActivity =
                                    WifiSetupWizardActivity.this;
                            wifiSetupWizardActivity.mIsFristResultReceived = true;
                            int i2 = i;
                            boolean z2 = false;
                            if (i2 != -1) {
                                if (i2 != 0) {
                                    if (i2 != 2) {
                                        if (i2 == 3
                                                && (dataOnButtonUpdater =
                                                                wifiSetupWizardActivity
                                                                        .mButtonUpdater)
                                                        != null) {
                                            z2 = dataOnButtonUpdater.enforceWithPopUp();
                                            isNextStepBlocked =
                                                    WifiSetupWizardActivity.this.mButtonUpdater
                                                            .isNextStepBlocked();
                                            boolean z3 = z2;
                                            z2 = !isNextStepBlocked;
                                            z = z3;
                                        }
                                        z = false;
                                    } else {
                                        DataOnButtonUpdater dataOnButtonUpdater2 =
                                                wifiSetupWizardActivity.mButtonUpdater;
                                        if (dataOnButtonUpdater2 != null) {
                                            WifiSetupWizardActivity wifiSetupWizardActivity2 =
                                                    WifiSetupWizardActivity.this;
                                            if (!wifiSetupWizardActivity2.mAllowCaptiveByKme
                                                    && (Utils.isDeviceProvisioned(
                                                                    wifiSetupWizardActivity2
                                                                            .getApplicationContext())
                                                            || !wifiSetupWizardActivity2
                                                                    .mIsNetworkRequiredByKme
                                                            || wifiSetupWizardActivity2
                                                                    .mIsNetworkRequiredByFrp
                                                            || wifiSetupWizardActivity2
                                                                    .mIsFrpChallengeRequired)) {
                                                string =
                                                        WifiApUtils.getString(
                                                                WifiSetupWizardActivity.this
                                                                        .mActivity,
                                                                R.string
                                                                        .wifi_captive_portal_message);
                                                z2 =
                                                        WifiSetupWizardActivity.this.mButtonUpdater
                                                                .enforceWithPopUp();
                                                isNextStepBlocked =
                                                        WifiSetupWizardActivity.this.mButtonUpdater
                                                                .isNextStepBlocked();
                                                boolean z32 = z2;
                                                z2 = !isNextStepBlocked;
                                                z = z32;
                                            }
                                        }
                                        z = false;
                                    }
                                }
                                z = false;
                                z2 = true;
                            } else {
                                DataOnButtonUpdater dataOnButtonUpdater3 =
                                        wifiSetupWizardActivity.mButtonUpdater;
                                if (dataOnButtonUpdater3 != null) {
                                    isNextStepBlocked = dataOnButtonUpdater3.isNextStepBlocked();
                                    boolean z322 = z2;
                                    z2 = !isNextStepBlocked;
                                    z = z322;
                                }
                                z = false;
                            }
                            WifiSetupWizardActivity wifiSetupWizardActivity3 =
                                    WifiSetupWizardActivity.this;
                            if (wifiSetupWizardActivity3.mButtonUpdater != null) {
                                Utils$$ExternalSyntheticOutline0.m(
                                        Utils$$ExternalSyntheticOutline0.m(
                                                "allow to Go Next : ", z2, "/", z, "/"),
                                        string,
                                        "WifiSetupWizard");
                                wifiSetupWizardActivity3.mIsWifiCompletelyAvailable = z2;
                                wifiSetupWizardActivity3.mButtonUpdater.updateButton(true, z2);
                            }
                            if (z) {
                                try {
                                    wifiSetupWizardActivity3.showNetworkErrorWarningDialogForFrp(
                                            string);
                                } catch (Exception e) {
                                    SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                                            "Exception on allowToGoNext: ", e, "WifiSetupWizard");
                                    if (!wifiSetupWizardActivity3.mActivity.isFinishing()
                                            && !wifiSetupWizardActivity3.mActivity.isDestroyed()) {
                                        wifiSetupWizardActivity3.forgetCurrentNetwork();
                                    }
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }

    /* renamed from: -$$Nest$mactivationToastShowing, reason: not valid java name */
    public static void m1320$$Nest$mactivationToastShowing(
            WifiSetupWizardActivity wifiSetupWizardActivity, int i) {
        Toast.makeText(
                        wifiSetupWizardActivity.mContext,
                        wifiSetupWizardActivity.getResources().getText(i),
                        1)
                .show();
        wifiSetupWizardActivity.mainAction(false);
    }

    /* renamed from: -$$Nest$mputCHNActivationDone, reason: not valid java name */
    public static void m1321$$Nest$mputCHNActivationDone(
            WifiSetupWizardActivity wifiSetupWizardActivity) {
        Settings.System.putString(
                wifiSetupWizardActivity.mContext.getContentResolver(),
                "chn_activation_status",
                "activation_done");
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.samsung.android.settings.wifi.WifiSetupWizardActivity$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.samsung.android.settings.wifi.WifiSetupWizardActivity$1] */
    public WifiSetupWizardActivity() {
        final int i = 0;
        this.mReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.settings.wifi.WifiSetupWizardActivity.1
                    public final /* synthetic */ WifiSetupWizardActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    /* JADX WARN: Code restructure failed: missing block: B:78:0x016e, code lost:

                       if (r7.equals("android.intent.action.SIM_STATE_CHANGED") == false) goto L46;
                    */
                    @Override // android.content.BroadcastReceiver
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onReceive(
                            android.content.Context r6, android.content.Intent r7) {
                        /*
                            Method dump skipped, instructions count: 572
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.wifi.WifiSetupWizardActivity.AnonymousClass1.onReceive(android.content.Context,"
                                    + " android.content.Intent):void");
                    }
                };
        final int i2 = 1;
        this.mActivationReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.settings.wifi.WifiSetupWizardActivity.1
                    public final /* synthetic */ WifiSetupWizardActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        /*
                            Method dump skipped, instructions count: 572
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.wifi.WifiSetupWizardActivity.AnonymousClass1.onReceive(android.content.Context,"
                                    + " android.content.Intent):void");
                    }
                };
    }

    public static boolean isTSSAndOperatorNotFixed() {
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        boolean z =
                SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Wifi_SupportBlockSkipForSetupWizard");
        SetupWizardLogMsg.out(
                "WifiSetupWizard",
                "isVendorSupportTSS : "
                        + z
                        + " SalesCode : "
                        + SystemProperties.get("ro.csc.sales_code", "NONE"));
        if (z) {
            boolean z2 = SemSystemProperties.getBoolean("mdc.singlesku", false);
            boolean z3 = SemSystemProperties.getBoolean("mdc.singlesku.activated", false);
            SetupWizardLogMsg.out("WifiSetupWizard", "isSupportTrueSingleSKU " + z2);
            SetupWizardLogMsg.out("WifiSetupWizard", "isTSSActivated " + z3);
            if (z2 && !z3) {
                return true;
            }
            if (z2) {
                Log.d("WifiSetupWizard", "Support TSS model and TSS Activated");
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readOneLine(java.lang.String r3) {
        /*
            java.lang.String r0 = ""
            java.io.FileReader r1 = new java.io.FileReader     // Catch: java.io.IOException -> L34 java.io.FileNotFoundException -> L37
            r1.<init>(r3)     // Catch: java.io.IOException -> L34 java.io.FileNotFoundException -> L37
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L29
            r2 = 8096(0x1fa0, float:1.1345E-41)
            r3.<init>(r1, r2)     // Catch: java.lang.Throwable -> L29
            java.lang.String r2 = r3.readLine()     // Catch: java.lang.Throwable -> L1f
            r3.close()     // Catch: java.lang.Throwable -> L1d
            r1.close()     // Catch: java.io.IOException -> L19 java.io.FileNotFoundException -> L1b
            goto L41
        L19:
            r3 = move-exception
            goto L3a
        L1b:
            r3 = move-exception
            goto L3e
        L1d:
            r3 = move-exception
            goto L2b
        L1f:
            r2 = move-exception
            r3.close()     // Catch: java.lang.Throwable -> L24
            goto L28
        L24:
            r3 = move-exception
            r2.addSuppressed(r3)     // Catch: java.lang.Throwable -> L29
        L28:
            throw r2     // Catch: java.lang.Throwable -> L29
        L29:
            r3 = move-exception
            r2 = r0
        L2b:
            r1.close()     // Catch: java.lang.Throwable -> L2f
            goto L33
        L2f:
            r1 = move-exception
            r3.addSuppressed(r1)     // Catch: java.io.IOException -> L19 java.io.FileNotFoundException -> L1b
        L33:
            throw r3     // Catch: java.io.IOException -> L19 java.io.FileNotFoundException -> L1b
        L34:
            r3 = move-exception
            r2 = r0
            goto L3a
        L37:
            r3 = move-exception
            r2 = r0
            goto L3e
        L3a:
            r3.printStackTrace()
            goto L41
        L3e:
            r3.printStackTrace()
        L41:
            if (r2 != 0) goto L44
            goto L48
        L44:
            java.lang.String r0 = r2.trim()
        L48:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.WifiSetupWizardActivity.readOneLine(java.lang.String):java.lang.String");
    }

    public final void forgetCurrentNetwork() {
        WifiInfo connectionInfo;
        WifiConfiguration wifiConfiguration;
        Log.d("WifiSetupWizard", "forget current AP");
        if (this.mActivity.isDestroyed()
                || (connectionInfo = this.mWifiManager.getConnectionInfo()) == null
                || connectionInfo.getNetworkId() == -1) {
            return;
        }
        int networkId = connectionInfo.getNetworkId();
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            Iterator<WifiConfiguration> it = configuredNetworks.iterator();
            while (it.hasNext()) {
                wifiConfiguration = it.next();
                if (wifiConfiguration.networkId == networkId) {
                    break;
                }
            }
        }
        wifiConfiguration = null;
        if (wifiConfiguration != null) {
            if (wifiConfiguration.carrierId != -1) {
                Context applicationContext = getApplicationContext();
                ((SemWifiManager)
                                applicationContext.getSystemService(
                                        WiFiManagerExt.SEM_WIFI_SERVICE))
                        .reportIssue(
                                100,
                                WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                        -1,
                                        "disconnect",
                                        applicationContext
                                                .getPackageManager()
                                                .getNameForUid(applicationContext.getUserId()),
                                        applicationContext.getPackageName()));
                this.mWifiManager.disconnect();
                return;
            }
            int networkId2 = connectionInfo.getNetworkId();
            Context applicationContext2 = getApplicationContext();
            ((SemWifiManager) applicationContext2.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                    .reportIssue(
                            102,
                            WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                    networkId2,
                                    "forget",
                                    applicationContext2
                                            .getPackageManager()
                                            .getNameForUid(applicationContext2.getUserId()),
                                    applicationContext2.getPackageName()));
            this.mWifiManager.forget(networkId2, null);
        }
    }

    @Override // com.android.settings.ButtonBarHandler
    public final Button getNextButton() {
        return null;
    }

    @Override // com.android.settings.ButtonBarHandler
    public final boolean hasNextButton() {
        return false;
    }

    public final boolean is523JigOn() {
        File file = new File("/sys/class/sec/switch/attached_dev");
        File file2 = new File("/sys/class/sec/rid_adc/rid_adc");
        File file3 = new File("/sys/class/sec/ccic/novbus_rp22k");
        if (file3.exists()) {
            SetupWizardLogMsg.out("WifiSetupWizard", "22RP exists");
        }
        if (!file.exists() && !file2.exists() && !file3.exists()) {
            SetupWizardLogMsg.out("WifiSetupWizard", "File Does not Exist!");
            SystemProperties.set(
                    "persist.sys.setupwizard.jig_on_wifisetup", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            return false;
        }
        String readOneLine = readOneLine("/sys/class/sec/switch/attached_dev");
        String readOneLine2 = readOneLine("/sys/class/sec/rid_adc/rid_adc");
        String readOneLine3 = readOneLine("/sys/class/sec/ccic/novbus_rp22k");
        StringBuilder m =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "JIG Info ", readOneLine, " / ", readOneLine2, " / ");
        m.append(readOneLine3);
        SetupWizardLogMsg.out("WifiSetupWizard", m.toString());
        try {
            if (!readOneLine.toLowerCase().contains("JIG UART OFF".toLowerCase())
                    && !"JIG UART ON".equalsIgnoreCase(readOneLine)
                    && !"JIG UART ON/VB".equalsIgnoreCase(readOneLine)
                    && !"255K".equalsIgnoreCase(readOneLine2)
                    && !"1".equals(readOneLine3)) {
                SetupWizardLogMsg.out("WifiSetupWizard", "JIG Wrong value");
                SystemProperties.set(
                        "persist.sys.setupwizard.jig_on_wifisetup",
                        DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                return false;
            }
            Toast.makeText(getApplicationContext(), "JIG is inserted. Skip the Wi-Fi step", 0)
                    .show();
            SetupWizardLogMsg.out("WifiSetupWizard", "JIG ON");
            SystemProperties.set("persist.sys.setupwizard.jig_on_wifisetup", "1");
            return true;
        } catch (NumberFormatException e) {
            SetupWizardLogMsg.out(
                    "WifiSetupWizard", "input is not in correct format" + e.getMessage());
            SystemProperties.set(
                    "persist.sys.setupwizard.jig_on_wifisetup", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            return false;
        }
    }

    public final boolean isNetworkAvailable() {
        if (this.mCm == null) {
            this.mCm = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        }
        NetworkInfo activeNetworkInfo = this.mCm.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isConnected();
        }
        return false;
    }

    public final boolean isNetworkRequired() {
        return isTSSAndOperatorNotFixed()
                || this.mIsNetworkRequiredByCustomer
                || this.mIsNetworkRequiredByFrp
                || this.mIsNetworkRequiredByKme
                || this.mIsFrpChallengeRequired;
    }

    public final boolean isWifiNetworkConnected$1() {
        if (this.mCm == null) {
            this.mCm = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        }
        NetworkInfo activeNetworkInfo = this.mCm.getActiveNetworkInfo();
        if (activeNetworkInfo == null || activeNetworkInfo.getType() != 1) {
            return false;
        }
        return activeNetworkInfo.isConnected();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0198  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void mainAction(boolean r10) {
        /*
            Method dump skipped, instructions count: 664
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.WifiSetupWizardActivity.mainAction(boolean):void");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Log.v("WifiSetupWizard", "onActivityResult Called " + i);
        if (i == 1001) {
            setResult(i2);
            finish();
        }
    }

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        boolean isWifiEnabled;
        super.onCreate(bundle);
        SetupWizardLogMsg.out("WifiSetupWizard", "onCreate");
        Log.d("WifiSetupWizard", "onCreate");
        this.mActivity = this;
        Context applicationContext = getApplicationContext();
        this.mContext = applicationContext;
        final int i = 1;
        final int i2 = 0;
        this.isSupportDeviceActivationCHN =
                Utils.hasPackage(
                                applicationContext.getApplicationContext(),
                                "com.samsung.android.activation")
                        && UserHandle.myUserId() == 0;
        Configuration configuration = this.mActivity.getResources().getConfiguration();
        int i3 = configuration.screenWidthDp;
        if ((i3 > 320 || configuration.fontScale < 1.1f)
                && (i3 >= 411 || configuration.fontScale < 1.3f)) {
            setContentView(R.layout.sec_wifi_setupwizard, false);
        } else {
            setContentView(R.layout.sec_wifi_setupwizard_large, false);
        }
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        if (this.mWifiSettings == null) {
            this.mWifiSettings =
                    (WifiSettings)
                            getSupportFragmentManager()
                                    .findFragmentById(R.id.wifi_secsetup_activity_fragment_layout);
        }
        WifiSettings wifiSettings = this.mWifiSettings;
        if (wifiSettings != null) {
            this.mWifiManager = wifiSettings.mWifiManager;
            wifiSettings.mLoadingListener = this;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        registerReceiver(this.mReceiver, intentFilter);
        this.mIsKmeSupported = getIntent().getBooleanExtra("isKmeSupported", false);
        this.mIsNetworkRequiredByFrp = getIntent().getBooleanExtra("is_network_required", false);
        this.mIsNetworkRequiredByKme =
                getIntent().getBooleanExtra("is_network_required_by_kme", false);
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        this.mIsNetworkRequiredByCustomer =
                SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Wifi_SupportNetworkConnectionsRequired");
        this.mAllowCaptiveByKme = getIntent().getBooleanExtra("allowCaptiveByKme", false);
        this.mIsUsedSprintNewSetupWizard =
                getIntent().getBooleanExtra("useSprintNewSetupWizard", false);
        this.mIsFrpChallengeRequired = Utils.isFrpChallengeRequired(this.mContext);
        SetupWizardLogMsg.out(
                "WifiSetupWizard",
                "isTSSAndOperatorNotFixed = "
                        + isTSSAndOperatorNotFixed()
                        + "mIsKmeSupported = "
                        + this.mIsKmeSupported
                        + ", mIsNetworkRequiredByKme = "
                        + this.mIsNetworkRequiredByKme
                        + ", mAllowCaptiveByKme = "
                        + this.mAllowCaptiveByKme
                        + ", mIsNetworkRequiredByFrp = "
                        + this.mIsNetworkRequiredByFrp
                        + ", mIsNetworkRequiredByCustomer = "
                        + this.mIsNetworkRequiredByCustomer
                        + ", mIsFrpChallengeRequired = "
                        + this.mIsFrpChallengeRequired
                        + ", mIsUsedSprintNewSetupWizard = "
                        + this.mIsUsedSprintNewSetupWizard);
        if (isNetworkAvailable()) {
            Log.d(
                    "WifiSetupWizard",
                    "If already network is available, do not skip Wi-Fi Setup automatically");
            this.mAutoFinishByConnectNetworkEnabled = false;
        }
        View findViewById = findViewById(R.id.sud_layout_content);
        findViewById.setPadding(
                findViewById.getPaddingStart(),
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.ssw_wifi_content_top_margin),
                findViewById.getPaddingEnd(),
                findViewById.getPaddingBottom());
        getIntent();
        this.mButtonUpdater = new DataOnButtonUpdater();
        this.mWifiStateButtonUpdater =
                new DataOnButtonUpdater(
                        this,
                        (TextView) findViewById(R.id.wifi_secsetup_activity_manual_button),
                        1);
        this.mDataOnButtonUpdater =
                new DataOnButtonUpdater(
                        this,
                        (TextView) findViewById(R.id.wifi_secsetup_activity_mobile_data_button),
                        0);
        SystemProperties.set(
                "persist.sys.setupwizard.jig_on_wifisetup", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        if (isNetworkRequired()
                && Settings.Global.getInt(
                                getApplicationContext().getContentResolver(),
                                "device_provisioning_mobile_data",
                                1)
                        == 0) {
            DataOnButtonUpdater dataOnButtonUpdater = this.mDataOnButtonUpdater;
            ((TextView) dataOnButtonUpdater.mTextView).setVisibility(0);
            ((TextView) dataOnButtonUpdater.mTextView)
                    .setOnClickListener(
                            new WifiSetupWizardActivity$DataOnButtonUpdater$$ExternalSyntheticLambda0(
                                    0, dataOnButtonUpdater));
            TextView textView = (TextView) dataOnButtonUpdater.mTextView;
            textView.setPaintFlags(textView.getPaintFlags() | 8);
        }
        setHeaderIcon(getApplicationContext().getDrawable(R.drawable.sec_wifi_suw_header));
        setHeaderTitle(R.string.wifi_suw_title);
        setPrimaryActionButton(
                R.string.next_button_label,
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.WifiSetupWizardActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiSetupWizardActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i4 = i2;
                        WifiSetupWizardActivity wifiSetupWizardActivity = this.f$0;
                        switch (i4) {
                            case 0:
                                boolean z = WifiSetupWizardActivity.DEV;
                                wifiSetupWizardActivity.mainAction(false);
                                break;
                            default:
                                boolean z2 = WifiSetupWizardActivity.DEV;
                                wifiSetupWizardActivity.mainAction(false);
                                break;
                        }
                    }
                });
        setSecondaryActionButton(
                R.string.wifi_skip_button,
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.WifiSetupWizardActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiSetupWizardActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i4 = i;
                        WifiSetupWizardActivity wifiSetupWizardActivity = this.f$0;
                        switch (i4) {
                            case 0:
                                boolean z = WifiSetupWizardActivity.DEV;
                                wifiSetupWizardActivity.mainAction(false);
                                break;
                            default:
                                boolean z2 = WifiSetupWizardActivity.DEV;
                                wifiSetupWizardActivity.mainAction(false);
                                break;
                        }
                    }
                });
        DataOnButtonUpdater dataOnButtonUpdater2 = this.mWifiStateButtonUpdater;
        if (DEV && "1".equals(SystemProperties.get("wifi.test.off"))) {
            this.mWifiManager.setWifiEnabled(false);
            isWifiEnabled = false;
        } else if (Settings.Secure.getIntForUser(
                        getContentResolver(), "first_enter_wifisetupwizard", 1, -2)
                == 1) {
            this.mWifiManager.setWifiEnabled(true);
            this.mIsWifiTurningOn = true;
            Settings.Secure.putIntForUser(
                    getContentResolver(), "first_enter_wifisetupwizard", 0, -2);
            isWifiEnabled = true;
        } else {
            isWifiEnabled = this.mWifiManager.isWifiEnabled();
        }
        dataOnButtonUpdater2.getClass();
        dataOnButtonUpdater2.setText(
                isWifiEnabled ? R.string.wifi_suw_turn_off_wifi : R.string.wifi_suw_turn_on_wifi);
        DataOnButtonUpdater dataOnButtonUpdater3 = this.mButtonUpdater;
        WifiSetupWizardActivity wifiSetupWizardActivity = WifiSetupWizardActivity.this;
        boolean z =
                !Utils.isDeviceProvisioned(wifiSetupWizardActivity.getApplicationContext())
                        && (wifiSetupWizardActivity.mIsNetworkRequiredByFrp
                                || wifiSetupWizardActivity.mIsNetworkRequiredByKme);
        WifiSetupWizardActivity wifiSetupWizardActivity2 = WifiSetupWizardActivity.this;
        if (!z || wifiSetupWizardActivity2.isWifiNetworkConnected$1()) {
            dataOnButtonUpdater3.checkAndUpdateButton(
                    wifiSetupWizardActivity2.isNetworkAvailable());
        } else {
            WifiSetupWizardDialogBuilder wifiSetupWizardDialogBuilder =
                    (WifiSetupWizardDialogBuilder) dataOnButtonUpdater3.mTextView;
            if (wifiSetupWizardDialogBuilder.mFlagWarningDialog) {
                Log.d("WifiSetupWizard", "already showing FRP/KME dialog");
            } else {
                Activity activity = wifiSetupWizardDialogBuilder.mActivity;
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                String string =
                        wifiSetupWizardDialogBuilder.mIsNetworkRequiredByFrp
                                ? activity.getString(R.string.wifi_frp_warning)
                                : activity.getString(
                                        R.string.knox_enforce_wifi,
                                        new Object[] {"Knox Cloud Service"});
                AlertController.AlertParams alertParams = builder.P;
                alertParams.mMessage = string;
                alertParams.mCancelable = false;
                builder.setPositiveButton(
                        R.string.common_ok,
                        new WifiSetupWizardDialogBuilder$$ExternalSyntheticLambda0(0));
                builder.create().show();
                wifiSetupWizardDialogBuilder.mFlagWarningDialog = true;
                SetupWizardLogMsg.out("WifiSetupWizard", "show FRP/KME popup");
            }
            dataOnButtonUpdater3.checkAndUpdateButton(false);
        }
        if (this.isSupportDeviceActivationCHN) {
            registerReceiver(
                    this.mActivationReceiver,
                    new IntentFilter("com.samsung.activation.COMPLETED"),
                    2);
            this.mButtonUpdater.updateButton(true, true);
            SetupWizardLogMsg.out("WifiSetupWizard", "skip button gone,show next button");
        }
        if (this.mLoadingViewController == null) {
            this.mLoadingViewController =
                    new LoadingViewController(
                            findViewById(R.id.loading_container),
                            findViewById(R.id.wifi_secsetup_activity_fragment_layout),
                            null);
            View findViewById2 = findViewById(R.id.loading_container);
            if (findViewById2 != null) {
                findViewById2.semSetRoundedCorners(15);
                findViewById2.semSetRoundedCornerColor(
                        15,
                        this.mContext
                                .getResources()
                                .getColor(R.color.sec_wifi_setupwizard_round_and_bg_color));
            }
        }
        runLoading();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        unregisterReceiver(this.mReceiver);
        if (this.isSupportDeviceActivationCHN) {
            unregisterReceiver(this.mActivationReceiver);
            ProgressDialog progressDialog = this.activationLoadingDialog;
            if (progressDialog != null && progressDialog.isShowing()) {
                this.activationLoadingDialog.dismiss();
                this.activationLoadingDialog = null;
            }
        }
        this.mResultHandler = null;
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        unregisterNetworkCallback$1();
        super.onPause();
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.samsung.android.settings.wifi.WifiSetupWizardActivity$9] */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mCurrentWifiState = this.mWifiManager.getWifiState();
        if (this.mCm == null) {
            this.mCm = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        }
        unregisterNetworkCallback$1();
        if (this.mResultHandler == null) {
            this.mResultHandler = new InternetResultHandler();
        }
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addTransportType(1);
        if (this.mNetworkCallback == null) {
            this.mNetworkCallback =
                    new ConnectivityManager
                            .NetworkCallback() { // from class:
                                                 // com.samsung.android.settings.wifi.WifiSetupWizardActivity.9
                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onAvailable(Network network) {
                            Log.i("WifiSetupWizard", "onAvailable");
                            WifiSetupWizardActivity wifiSetupWizardActivity =
                                    WifiSetupWizardActivity.this;
                            if (wifiSetupWizardActivity.mIsNetworkCheckingOnGoing) {
                                return;
                            }
                            wifiSetupWizardActivity.mIsNetworkCheckingOnGoing = true;
                            wifiSetupWizardActivity.mIsFristResultReceived = false;
                            if (wifiSetupWizardActivity.mResultHandler == null) {
                                wifiSetupWizardActivity.mResultHandler =
                                        WifiSetupWizardActivity.this.new InternetResultHandler();
                            }
                            WifiSetupWizardActivity wifiSetupWizardActivity2 =
                                    WifiSetupWizardActivity.this;
                            wifiSetupWizardActivity2.mIsNetworkValidated = false;
                            InternetResultHandler internetResultHandler =
                                    wifiSetupWizardActivity2.mResultHandler;
                            internetResultHandler.sendMessageDelayed(
                                    Message.obtain(internetResultHandler, 3), 7000L);
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onCapabilitiesChanged(
                                Network network, NetworkCapabilities networkCapabilities) {
                            Log.i(
                                    "WifiSetupWizard",
                                    "onCapabilitiesChanged : " + networkCapabilities);
                            WifiSetupWizardActivity.this.mIsNetworkValidated =
                                    networkCapabilities.hasCapability(16);
                            WifiSetupWizardActivity wifiSetupWizardActivity =
                                    WifiSetupWizardActivity.this;
                            if (wifiSetupWizardActivity.mIsNetworkValidated) {
                                wifiSetupWizardActivity.mIsNetworkValidated = true;
                                wifiSetupWizardActivity.mResultHandler.removeMessages(3);
                                InternetResultHandler internetResultHandler =
                                        WifiSetupWizardActivity.this.mResultHandler;
                                internetResultHandler.sendMessage(
                                        Message.obtain(internetResultHandler, 0));
                                return;
                            }
                            if (networkCapabilities.hasCapability(17)) {
                                WifiSetupWizardActivity.this.mResultHandler.removeMessages(3);
                                InternetResultHandler internetResultHandler2 =
                                        WifiSetupWizardActivity.this.mResultHandler;
                                internetResultHandler2.sendMessage(
                                        Message.obtain(internetResultHandler2, 2));
                            } else {
                                WifiSetupWizardActivity wifiSetupWizardActivity2 =
                                        WifiSetupWizardActivity.this;
                                if (wifiSetupWizardActivity2.mIsFristResultReceived) {
                                    wifiSetupWizardActivity2.mResultHandler.removeMessages(3);
                                    InternetResultHandler internetResultHandler3 =
                                            WifiSetupWizardActivity.this.mResultHandler;
                                    internetResultHandler3.sendMessage(
                                            Message.obtain(internetResultHandler3, 3));
                                }
                            }
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onLost(Network network) {
                            Log.i("WifiSetupWizard", "onLost");
                            super.onLost(network);
                            WifiSetupWizardActivity wifiSetupWizardActivity =
                                    WifiSetupWizardActivity.this;
                            wifiSetupWizardActivity.mIsNetworkCheckingOnGoing = false;
                            wifiSetupWizardActivity.mIsFristResultReceived = false;
                            wifiSetupWizardActivity.mIsNetworkValidated = false;
                            wifiSetupWizardActivity.mAutoFinishByConnectNetworkEnabled = true;
                            InternetResultHandler internetResultHandler =
                                    wifiSetupWizardActivity.mResultHandler;
                            internetResultHandler.sendMessage(
                                    Message.obtain(internetResultHandler, -1));
                        }
                    };
        }
        this.mCm.registerNetworkCallback(builder.build(), this.mNetworkCallback);
        updateSimState();
        if (!isTSSAndOperatorNotFixed() || is523JigOn()) {
            return;
        }
        this.mButtonUpdater.checkAndUpdateButton(isNetworkAvailable());
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStart() {
        super.onStart();
        SALogging.insertSALog("WIFI_230");
    }

    public final void runLoading() {
        if (this.mLoadingViewController != null) {
            if ((this.mIsWifiTurningOn
                            || this.mWifiManager.getWifiState() == 3
                            || this.mWifiManager.getWifiState() == 2)
                    && !this.mIsLoading) {
                this.mLoadingViewController.handleLoadingContainer(false, false, false);
                this.mIsLoading = true;
                this.mIsWifiTurningOn = false;
            }
        }
    }

    public final void selectNetworkPopUpShowing() {
        View inflate =
                getLayoutInflater().inflate(R.layout.activation_popup_layout, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.popup_text);
        if ((!Utils.isTablet() || (!Rune.isSimMissing(this.mContext) && Rune.isSimReady()))
                && !com.android.settingslib.Utils.isWifiOnly(this.mContext)) {
            textView.setText(R.string.select_network_content);
        } else {
            textView.setText(R.string.select_network_content_wifi_only);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogAlertTheme);
        builder.setTitle(R.string.no_network_title);
        builder.setView(inflate);
        if (!Utils.isTablet()
                || (!com.android.settingslib.Utils.isWifiOnly(this.mContext)
                        && !Rune.isSimMissing(this.mContext)
                        && Rune.isSimReady())) {
            builder.setPositiveButton(
                    getString(R.string.select_network_mobile_data).toUpperCase(),
                    new AnonymousClass3(this, 0));
            builder.setNeutralButton(
                    getString(R.string.select_network_cancel).toUpperCase(),
                    new AnonymousClass3(this, 1));
        }
        builder.setNegativeButton(
                getString(R.string.select_network_wlan).toUpperCase(), new AnonymousClass5());
        android.app.AlertDialog create = builder.create();
        if (create != null) {
            create.setOnShowListener(new AnonymousClass6(0));
            create.setCancelable(false);
            create.show();
        }
    }

    public final void showNetworkErrorWarningDialogForFrp(String str) {
        if (this.mIsDialogPopedUp) {
            return;
        }
        this.mIsDialogPopedUp = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setPositiveButton(
                R.string.ok,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.WifiSetupWizardActivity$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        WifiSetupWizardActivity wifiSetupWizardActivity =
                                WifiSetupWizardActivity.this;
                        boolean z = WifiSetupWizardActivity.DEV;
                        wifiSetupWizardActivity.forgetCurrentNetwork();
                    }
                });
        builder.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.wifi.WifiSetupWizardActivity$$ExternalSyntheticLambda3
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        WifiSetupWizardActivity wifiSetupWizardActivity =
                                WifiSetupWizardActivity.this;
                        wifiSetupWizardActivity.mIsDialogPopedUp = false;
                        wifiSetupWizardActivity.forgetCurrentNetwork();
                        Log.i("WifiSetupWizard", "Network Warning Dialog for FRP is dismissed");
                    }
                });
        builder.create().show();
    }

    public final void unregisterNetworkCallback$1() {
        if (this.mCm == null) {
            this.mCm = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        }
        try {
            AnonymousClass9 anonymousClass9 = this.mNetworkCallback;
            if (anonymousClass9 != null) {
                this.mCm.unregisterNetworkCallback(anonymousClass9);
            }
        } catch (IllegalArgumentException unused) {
            Log.d("WifiSetupWizard", "No need to unregister");
        }
    }

    public final void updateSimState() {
        boolean z = false;
        this.mIsEsimDetected = false;
        Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        List<SubscriptionInfo> selectableSubscriptionInfoList =
                SubscriptionRepositoryKt.getSelectableSubscriptionInfoList(this);
        if (selectableSubscriptionInfoList != null) {
            for (SubscriptionInfo subscriptionInfo : selectableSubscriptionInfoList) {
                if (subscriptionInfo != null
                        && subscriptionInfo.isEmbedded()
                        && subscriptionInfo.semGetProfileClass() != 1) {
                    this.mIsEsimDetected = true;
                }
            }
        }
        List<SubscriptionInfo> activeSubscriptionInfoList =
                ((SubscriptionManager)
                                this.mContext.getSystemService("telephony_subscription_service"))
                        .getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList != null) {
            Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SubscriptionInfo next = it.next();
                int simState = this.mTelephonyManager.getSimState(next.getSimSlotIndex());
                if (simState != 1 && simState != 0 && !next.isEmbedded()) {
                    z = true;
                    break;
                }
            }
        }
        this.mIsPsimDetected = z;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DataOnButtonUpdater {
        public final Object mTextView;

        public DataOnButtonUpdater(
                WifiSetupWizardActivity wifiSetupWizardActivity, TextView textView, int i) {
            switch (i) {
                case 1:
                    WifiSetupWizardActivity.this = wifiSetupWizardActivity;
                    this.mTextView = textView;
                    textView.setOnClickListener(
                            new WifiSetupWizardActivity$DataOnButtonUpdater$$ExternalSyntheticLambda0(
                                    1, this));
                    break;
                default:
                    WifiSetupWizardActivity.this = wifiSetupWizardActivity;
                    this.mTextView = textView;
                    break;
            }
        }

        public void checkAndUpdateButton(boolean z) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "checkAndUpdateButton isNetworkAvailable:", "WifiSetupWizard", z);
            boolean z2 = WifiSetupWizardActivity.DEV;
            WifiSetupWizardActivity wifiSetupWizardActivity = WifiSetupWizardActivity.this;
            boolean isWifiNetworkConnected$1 = wifiSetupWizardActivity.isWifiNetworkConnected$1();
            if (!z) {
                updateButton(false, !isNextStepBlocked());
                return;
            }
            if (enforceWithPopUp() && isWifiNetworkConnected$1) {
                wifiSetupWizardActivity.getClass();
            }
            if (!isWifiNetworkConnected$1 || wifiSetupWizardActivity.mIsWifiCompletelyAvailable) {
                updateButton(isWifiNetworkConnected$1, true);
            } else {
                Log.d("WifiSetupWizard", "check internet service available or not");
                updateButton(isWifiNetworkConnected$1, !isNextStepBlocked());
            }
        }

        public boolean enforceWithPopUp() {
            WifiSetupWizardActivity wifiSetupWizardActivity = WifiSetupWizardActivity.this;
            return (!Utils.isDeviceProvisioned(wifiSetupWizardActivity.getApplicationContext())
                            && (wifiSetupWizardActivity.mIsNetworkRequiredByFrp
                                    || wifiSetupWizardActivity.mIsNetworkRequiredByKme))
                    || wifiSetupWizardActivity.mIsFrpChallengeRequired;
        }

        public boolean isNextStepBlocked() {
            StringBuilder sb = new StringBuilder();
            WifiSetupWizardActivity wifiSetupWizardActivity = WifiSetupWizardActivity.this;
            sb.append(wifiSetupWizardActivity.mIsNetworkRequiredByFrp);
            sb.append(" ");
            sb.append(wifiSetupWizardActivity.mIsUsedSprintNewSetupWizard);
            sb.append(" ");
            sb.append(wifiSetupWizardActivity.isNetworkRequired());
            sb.append(" ");
            sb.append(wifiSetupWizardActivity.is523JigOn());
            Log.d("WifiSetupWizard", sb.toString());
            return wifiSetupWizardActivity.mIsNetworkRequiredByFrp
                    || !(!wifiSetupWizardActivity.isNetworkRequired()
                            || wifiSetupWizardActivity.is523JigOn()
                            || wifiSetupWizardActivity.mIsUsedSprintNewSetupWizard);
        }

        public void setEnabled(boolean z) {
            ((TextView) this.mTextView).setClickable(z);
            ((TextView) this.mTextView).setAlpha(z ? 1.0f : 0.4f);
        }

        public void setText(int i) {
            ((TextView) this.mTextView).setText(i);
            TextView textView = (TextView) this.mTextView;
            textView.setPaintFlags(textView.getPaintFlags() | 8);
            ((TextView) this.mTextView)
                    .setContentDescription(
                            ((Object) ((TextView) this.mTextView).getText())
                                    + ", "
                                    + WifiSetupWizardActivity.this.getString(R.string.button_tts));
        }

        public void showSkipDialog(boolean z) {
            WifiSetupWizardDialogBuilder wifiSetupWizardDialogBuilder =
                    (WifiSetupWizardDialogBuilder) this.mTextView;
            if (!z) {
                AlertDialog.Builder makeCommonAlertDialogBuilder =
                        wifiSetupWizardDialogBuilder.makeCommonAlertDialogBuilder(false, false);
                AlertController.AlertParams alertParams = makeCommonAlertDialogBuilder.P;
                alertParams.mTitle = null;
                Activity activity = wifiSetupWizardDialogBuilder.mActivity;
                alertParams.mMessage =
                        com.android.settingslib.Utils.isWifiOnly(activity)
                                ? activity.getString(R.string.wifi_tss_guide_text_wifi_only)
                                : activity.getString(R.string.wifi_tss_guide_text);
                makeCommonAlertDialogBuilder.create().show();
                return;
            }
            StringBuilder sb = new StringBuilder("EsimSupported = ");
            WifiSetupWizardActivity wifiSetupWizardActivity = WifiSetupWizardActivity.this;
            sb.append(wifiSetupWizardActivity.mIsEsimSupported);
            sb.append(", eSIM detected = ");
            sb.append(wifiSetupWizardActivity.mIsEsimDetected);
            sb.append(", pSIM detected = ");
            sb.append(wifiSetupWizardActivity.mIsPsimDetected);
            SetupWizardLogMsg.out("WifiSetupWizard", sb.toString());
            if (wifiSetupWizardActivity.mIsSupportBootstrap) {
                AlertDialog.Builder makeCommonAlertDialogBuilder2 =
                        wifiSetupWizardDialogBuilder.makeCommonAlertDialogBuilder(true, true);
                Activity activity2 = wifiSetupWizardDialogBuilder.mActivity;
                String string = activity2.getString(R.string.wifi_and_mobile_continue_title);
                AlertController.AlertParams alertParams2 = makeCommonAlertDialogBuilder2.P;
                alertParams2.mTitle = string;
                alertParams2.mMessage =
                        Utils.isTablet()
                                ? activity2.getString(
                                        R.string.wifi_and_mobile_continue_message_tablet)
                                : activity2.getString(
                                        R.string.wifi_and_mobile_continue_message_phone);
                makeCommonAlertDialogBuilder2.create().show();
                return;
            }
            if (!wifiSetupWizardActivity.mIsEsimSupported
                    || wifiSetupWizardActivity.mIsEsimDetected
                    || wifiSetupWizardActivity.mIsPsimDetected) {
                wifiSetupWizardDialogBuilder
                        .makeCommonAlertDialogBuilder(true, false)
                        .create()
                        .show();
                return;
            }
            AlertDialog.Builder makeCommonAlertDialogBuilder3 =
                    wifiSetupWizardDialogBuilder.makeCommonAlertDialogBuilder(true, false);
            StringBuilder sb2 = new StringBuilder();
            Activity activity3 = wifiSetupWizardDialogBuilder.mActivity;
            sb2.append(activity3.getString(R.string.wifi_and_mobile_skipped_message_header));
            sb2.append("\n\n- ");
            sb2.append(activity3.getString(R.string.wifi_and_mobile_skipped_message_setup_esim));
            sb2.append("\n- ");
            sb2.append(
                    activity3.getString(R.string.wifi_and_mobile_skipped_message_connect_internet));
            sb2.append("\n- ");
            sb2.append(
                    activity3.getString(R.string.wifi_and_mobile_skipped_message_software_update));
            sb2.append("\n- ");
            sb2.append(
                    activity3.getString(
                            R.string.wifi_and_mobile_skipped_message_devices_protection));
            makeCommonAlertDialogBuilder3.P.mMessage = sb2.toString();
            makeCommonAlertDialogBuilder3.create().show();
        }

        public void updateButton(boolean z, boolean z2) {
            FooterBarMixin footerBarMixin;
            Button primaryButtonView;
            SetupWizardLogMsg.out(
                    "WifiSetupWizard", "updateButton isNext:" + z + ", viewEnable:" + z2);
            Utils$$ExternalSyntheticOutline0.m653m(
                    "updateButton isNext:", z, ", viewEnable:", z2, "WifiSetupWizard");
            boolean z3 = WifiSetupWizardActivity.DEV;
            WifiSetupWizardActivity wifiSetupWizardActivity = WifiSetupWizardActivity.this;
            FooterButton footerButton = wifiSetupWizardActivity.mPrimaryButton;
            if (footerButton != null) {
                footerButton.setVisibility(0);
            }
            FooterButton footerButton2 = wifiSetupWizardActivity.mSecondaryButton;
            if (footerButton2 != null) {
                footerButton2.setVisibility(0);
            }
            if (!z && !wifiSetupWizardActivity.isSupportDeviceActivationCHN) {
                FooterButton footerButton3 = wifiSetupWizardActivity.mSecondaryButton;
                if (footerButton3 != null) {
                    footerButton3.setText(
                            ((SuwBaseActivity) wifiSetupWizardActivity).mContext,
                            R.string.wifi_skip_button);
                }
                FooterButton footerButton4 = wifiSetupWizardActivity.mPrimaryButton;
                if (footerButton4 != null) {
                    footerButton4.setVisibility(8);
                }
                wifiSetupWizardActivity.mIsSkipButtonEnabled = z2;
                return;
            }
            FooterButton footerButton5 = wifiSetupWizardActivity.mPrimaryButton;
            if (footerButton5 != null) {
                footerButton5.setText(
                        ((SuwBaseActivity) wifiSetupWizardActivity).mContext,
                        R.string.next_button_label);
            }
            FooterButton footerButton6 = wifiSetupWizardActivity.mPrimaryButton;
            if (footerButton6 != null) {
                footerButton6.setEnabled(z2);
                if (!z2
                        && (footerBarMixin =
                                        (FooterBarMixin)
                                                wifiSetupWizardActivity.mRootLayout.getMixin(
                                                        FooterBarMixin.class))
                                != null
                        && (primaryButtonView = footerBarMixin.getPrimaryButtonView()) != null) {
                    primaryButtonView.setTextColor(
                            wifiSetupWizardActivity
                                    .getResources()
                                    .getColor(R.color.sswl_bottom_primary_button_text_color));
                }
            }
            FooterButton footerButton7 = wifiSetupWizardActivity.mSecondaryButton;
            if (footerButton7 != null) {
                footerButton7.setVisibility(8);
            }
            wifiSetupWizardActivity.mIsSkipButtonEnabled = true;
            if (!wifiSetupWizardActivity.mAutoFinishByConnectNetworkEnabled
                    || !wifiSetupWizardActivity.mIsNetworkValidated) {
                SetupWizardLogMsg.out(
                        "WifiSetupWizard", "Ignore mainAction, mAutoFinishEnabled is false");
            } else {
                wifiSetupWizardActivity.mAutoFinishByConnectNetworkEnabled = false;
                wifiSetupWizardActivity.mainAction(false);
            }
        }

        public DataOnButtonUpdater() {
            if (WifiSetupWizardActivity.DEV) {
                "1".equals(SystemProperties.get("wifi.test.skip"));
            }
            this.mTextView = new WifiSetupWizardDialogBuilder(WifiSetupWizardActivity.this);
        }
    }
}
