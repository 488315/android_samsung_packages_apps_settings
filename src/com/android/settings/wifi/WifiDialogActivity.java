package com.android.settings.wifi;

import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;

import androidx.lifecycle.Lifecycle;

import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settingslib.core.lifecycle.ObservableActivity;
import com.android.settingslib.wifi.AccessPoint;
import com.android.wifitrackerlib.NetworkDetailsTracker;
import com.android.wifitrackerlib.WifiEntry;

import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.util.ThemeHelper;

import java.lang.ref.WeakReference;
import java.time.ZoneOffset;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiDialogActivity extends ObservableActivity
        implements WifiDialog.WifiDialogListener,
                WifiDialog2.WifiDialog2Listener,
                DialogInterface.OnDismissListener {
    static final String KEY_CONNECT_FOR_CALLER = "connect_for_caller";
    static final int REQUEST_CODE_WIFI_DPP_ENROLLEE_QR_CODE_SCANNER = 0;
    static final int RESULT_CONNECTED = 1;
    public AccessPoint mAccessPoint;
    WifiDialog mDialog;
    WifiDialog2 mDialog2;
    public Intent mIntent;
    public boolean mIsWifiTrackerLib;
    public LockScreenMonitor mLockScreenMonitor;
    public NetworkDetailsTracker mNetworkDetailsTracker;
    public WifiManager mWifiManager;
    public HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.WifiDialogActivity$1, reason: invalid class name */
    public final class AnonymousClass1 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class LockScreenMonitor
            implements KeyguardManager.KeyguardLockedStateListener {
        public KeyguardManager mKeyguardManager;
        public final WeakReference mWifiDialogActivity;

        public LockScreenMonitor(WifiDialogActivity wifiDialogActivity) {
            this.mWifiDialogActivity = new WeakReference(wifiDialogActivity);
            KeyguardManager keyguardManager =
                    (KeyguardManager) wifiDialogActivity.getSystemService(KeyguardManager.class);
            this.mKeyguardManager = keyguardManager;
            keyguardManager.addKeyguardLockedStateListener(
                    wifiDialogActivity.getMainExecutor(), this);
        }

        @Override // android.app.KeyguardManager.KeyguardLockedStateListener
        public final void onKeyguardLockedStateChanged(boolean z) {
            WifiDialogActivity wifiDialogActivity;
            if (z
                    && (wifiDialogActivity = (WifiDialogActivity) this.mWifiDialogActivity.get())
                            != null) {
                WifiDialog wifiDialog = wifiDialogActivity.mDialog;
                if (wifiDialog != null) {
                    wifiDialog.dismiss();
                    wifiDialogActivity.mDialog = null;
                }
                WifiDialog2 wifiDialog2 = wifiDialogActivity.mDialog2;
                if (wifiDialog2 != null) {
                    wifiDialog2.dismiss();
                    wifiDialogActivity.mDialog2 = null;
                }
                Log.e(
                        "WifiDialogActivity",
                        "Dismiss Wi-Fi dialog to prevent leaking user data on lock screen!");
                EventLog.writeEvent(
                        1397638484, "231583603", -1, "Leak Wi-Fi dialog on lock screen");
            }
        }
    }

    public void createDialogWithSuwTheme() {
        Logger logger = ThemeHelper.LOG;
        int i = PartnerConfigHelper.isSetupWizardDayNightEnabled(this) ? 2132083879 : 2132083880;
        if (!this.mIsWifiTrackerLib) {
            this.mDialog = new WifiDialog(this, this, this.mAccessPoint, i, false);
        } else {
            this.mDialog2 =
                    new WifiDialog2(
                            this,
                            this,
                            this.mNetworkDetailsTracker.getWifiEntry(),
                            1,
                            i,
                            false,
                            224);
        }
    }

    @Override // android.app.Activity
    public final void finish() {
        overridePendingTransition(0, 0);
        super.finish();
    }

    public final boolean hasPermissionForResult() {
        String callingPackage = getCallingPackage();
        if (callingPackage == null) {
            Log.d(
                    "WifiDialogActivity",
                    "Failed to get the calling package, don't return the result.");
            EventLog.writeEvent(1397638484, "185126813", -1, "no calling package");
            return false;
        }
        if (getPackageManager()
                        .checkPermission("android.permission.ACCESS_FINE_LOCATION", callingPackage)
                == 0) {
            Log.d(
                    "WifiDialogActivity",
                    "The calling package has ACCESS_FINE_LOCATION permission for result.");
            return true;
        }
        Log.d(
                "WifiDialogActivity",
                "The calling package does not have the necessary permissions for result.");
        try {
            EventLog.writeEvent(
                    1397638484,
                    "185126813",
                    Integer.valueOf(getPackageManager().getPackageUid(callingPackage, 0)),
                    "no permission");
        } catch (PackageManager.NameNotFoundException e) {
            EventLog.writeEvent(1397638484, "185126813", -1, "no permission");
            Log.w(
                    "WifiDialogActivity",
                    "Cannot find the UID, calling package: ".concat(callingPackage),
                    e);
        }
        return false;
    }

    public final boolean hasWifiManager() {
        if (this.mWifiManager != null) {
            return true;
        }
        WifiManager wifiManager = (WifiManager) getSystemService(WifiManager.class);
        this.mWifiManager = wifiManager;
        return wifiManager != null;
    }

    public boolean isAddWifiConfigAllowed() {
        UserManager userManager = (UserManager) getSystemService(UserManager.class);
        if (userManager == null || !userManager.hasUserRestriction("no_add_wifi_config")) {
            return true;
        }
        Log.e("WifiDialogActivity", "The user is not allowed to add Wi-Fi configuration.");
        return false;
    }

    public boolean isConfigWifiAllowed() {
        UserManager userManager = (UserManager) getSystemService(UserManager.class);
        if (userManager == null) {
            return true;
        }
        boolean z = !userManager.hasUserRestriction("no_config_wifi");
        if (!z) {
            Log.e("WifiDialogActivity", "The user is not allowed to configure Wi-Fi.");
            EventLog.writeEvent(
                    1397638484,
                    "226133034",
                    Integer.valueOf(getApplicationContext().getUserId()),
                    "The user is not allowed to configure Wi-Fi.");
        }
        return z;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 0 && i2 == -1) {
            if (hasPermissionForResult()) {
                setResult(1, intent);
            } else {
                setResult(1);
            }
            finish();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Intent intent = getIntent();
        this.mIntent = intent;
        if (intent.getBooleanExtra("firstRun", false)) {
            int theme = SetupWizardUtils.getTheme(this, this.mIntent);
            Logger logger = ThemeHelper.LOG;
            int i = 2132083322;
            int i2 =
                    PartnerConfigHelper.isSetupWizardDayNightEnabled(this)
                            ? 2132083320
                            : 2132083322;
            if (theme == 2132083327) {
                i = 2132083329;
            } else if (theme == 2132083331) {
                i = 2132083333;
            } else if (theme == 2132083319) {
                i = 2132083320;
            } else if (theme != 2132083321) {
                i =
                        theme == 2132083316
                                ? 2132083583
                                : theme == 2132083317
                                        ? 2132083584
                                        : theme == R.style.GlifV3Theme
                                                ? 2132083335
                                                : theme == R.style.GlifV2Theme
                                                        ? 2132083323
                                                        : theme == R.style.GlifTheme
                                                                ? 2132083585
                                                                : i2;
            }
            setTheme(i);
        }
        super.onCreate(bundle);
        if (!isConfigWifiAllowed() || !isAddWifiConfigAllowed()) {
            finish();
            return;
        }
        boolean z = !TextUtils.isEmpty(this.mIntent.getStringExtra("key_chosen_wifientry_key"));
        this.mIsWifiTrackerLib = z;
        if (!z) {
            Bundle bundleExtra = this.mIntent.getBundleExtra("access_point_state");
            if (bundleExtra != null) {
                this.mAccessPoint = new AccessPoint(this, bundleExtra);
                return;
            }
            return;
        }
        HandlerThread handlerThread =
                new HandlerThread(
                        "WifiDialogActivity{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        SimpleClock anonymousClass1 = new AnonymousClass1(ZoneOffset.UTC);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                featureFactoryImpl.getWifiTrackerLibProvider();
        Lifecycle lifecycle = getLifecycle();
        Handler handler = new Handler(Looper.getMainLooper());
        Handler threadHandler = this.mWorkerThread.getThreadHandler();
        String stringExtra = this.mIntent.getStringExtra("key_chosen_wifientry_key");
        wifiTrackerLibProvider.getClass();
        this.mNetworkDetailsTracker =
                WifiTrackerLibProviderImpl.createNetworkDetailsTracker(
                        lifecycle, this, handler, threadHandler, anonymousClass1, stringExtra);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        if (this.mIsWifiTrackerLib) {
            WifiDialog2 wifiDialog2 = this.mDialog2;
            if (wifiDialog2 != null && wifiDialog2.isShowing()) {
                this.mDialog2 = null;
            }
            this.mWorkerThread.quit();
        } else {
            WifiDialog wifiDialog = this.mDialog;
            if (wifiDialog != null && wifiDialog.isShowing()) {
                this.mDialog = null;
            }
        }
        LockScreenMonitor lockScreenMonitor = this.mLockScreenMonitor;
        if (lockScreenMonitor != null) {
            KeyguardManager keyguardManager = lockScreenMonitor.mKeyguardManager;
            if (keyguardManager != null) {
                keyguardManager.removeKeyguardLockedStateListener(lockScreenMonitor);
                lockScreenMonitor.mKeyguardManager = null;
            }
            this.mLockScreenMonitor = null;
        }
        super.onDestroy();
    }

    @Override // androidx.core.app.ComponentActivity,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.mDialog2 = null;
        this.mDialog = null;
        finish();
    }

    @Override // com.android.settings.wifi.WifiDialog2.WifiDialog2Listener
    public final void onForget(WifiDialog2 wifiDialog2) {
        WifiEntry wifiEntry = wifiDialog2.getController().mWifiEntry;
        if (wifiEntry != null && wifiEntry.canForget()) {
            wifiEntry.forget(null);
        }
        setResult(2);
        finish();
    }

    @Override // com.android.settings.wifi.WifiDialog2.WifiDialog2Listener
    public final void onScan(WifiDialog2 wifiDialog2, String str) {
        Intent enrolleeQrCodeScannerIntent =
                WifiDppUtils.getEnrolleeQrCodeScannerIntent(wifiDialog2.getContext(), str);
        WizardManagerHelper.copyWizardManagerExtras(this.mIntent, enrolleeQrCodeScannerIntent);
        startActivityForResult(enrolleeQrCodeScannerIntent, 0);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStart() {
        super.onStart();
        if (this.mDialog2 == null && this.mDialog == null && hasWifiManager()) {
            if (WizardManagerHelper.isAnySetupWizard(getIntent())) {
                createDialogWithSuwTheme();
            } else if (this.mIsWifiTrackerLib) {
                this.mDialog2 =
                        new WifiDialog2(
                                this,
                                this,
                                this.mNetworkDetailsTracker.getWifiEntry(),
                                1,
                                0,
                                false,
                                false,
                                "com.android.systemui".equals(getLaunchedFromPackage()));
            } else {
                this.mDialog = new WifiDialog(this, this, this.mAccessPoint, 0, false);
            }
            if (this.mIsWifiTrackerLib) {
                WifiDialog2 wifiDialog2 = this.mDialog2;
                if (wifiDialog2 != null) {
                    wifiDialog2.show();
                    this.mDialog2.setOnDismissListener(this);
                }
            } else {
                WifiDialog wifiDialog = this.mDialog;
                if (wifiDialog != null) {
                    wifiDialog.show();
                    this.mDialog.setOnDismissListener(this);
                }
            }
            if (this.mDialog2 == null && this.mDialog == null) {
                return;
            }
            this.mLockScreenMonitor = new LockScreenMonitor(this);
        }
    }

    @Override // com.android.settings.wifi.WifiDialog2.WifiDialog2Listener
    public final void onSubmit(WifiDialog2 wifiDialog2) {
        if (hasWifiManager()) {
            WifiEntry wifiEntry = wifiDialog2.getController().mWifiEntry;
            WifiConfiguration config = wifiDialog2.getController().getConfig();
            Intent intent = null;
            if (getIntent().getBooleanExtra(KEY_CONNECT_FOR_CALLER, true)) {
                if (config == null && wifiEntry != null && wifiEntry.canConnect()) {
                    wifiEntry.connect(null);
                } else {
                    this.mWifiManager.connect(config, null);
                }
            }
            if (hasPermissionForResult()) {
                intent = new Intent();
                if (config != null) {
                    intent.putExtra("wifi_configuration", config);
                }
            }
            setResult(1, intent);
            finish();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0369  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0397  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0339  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0348  */
    /* JADX WARN: Type inference failed for: r16v0, types: [android.app.Activity, com.android.settings.wifi.WifiDialogActivity] */
    /* JADX WARN: Type inference failed for: r8v24 */
    /* JADX WARN: Type inference failed for: r8v25, types: [int] */
    /* JADX WARN: Type inference failed for: r8v26 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onSubmit(com.android.settings.wifi.WifiDialog r17) {
        /*
            Method dump skipped, instructions count: 974
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.WifiDialogActivity.onSubmit(com.android.settings.wifi.WifiDialog):void");
    }
}
