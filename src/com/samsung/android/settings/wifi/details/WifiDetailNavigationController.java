package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.WifiEntry;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.scloud.SCloudWifiDataManager;
import com.samsung.android.settings.wifi.WifiDevicePolicyManager;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiDetailNavigationController
        implements LifecycleObserver,
                WifiEntry.DisconnectCallback,
                WifiEntry.ForgetCallback,
                SecWifiPreferenceControllerHelper.ValidationUpdater,
                WifiDppAuthenticationErrorListener {
    public final WifiDetailNavigationController mAuthErrorListener;
    public View mBottomButtonBarView;
    public RelativeLayout mBottomLayout;
    public BottomNavigationView mBottomNavigationView;
    public final Context mContext;
    public final Fragment mFragment;
    public final boolean mInManageNetwork;
    public final boolean mInSetupWizardFinished;
    public final boolean mIsMaintenanceMode;
    public boolean mIsQuickShareAvailable;
    public final MetricsFeatureProvider mMetricsFeatureProvider;
    public final String mSAScreenId;
    public WifiConfiguration mWifiConfig;
    public final WifiEntry mWifiEntry;
    public WifiInfo mWifiInfo;
    public final WifiManager mWifiManager;
    public boolean mIsEphemeral = false;
    public boolean mIsSavedVzwEapAka = false;
    public int mBottomMode = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.details.WifiDetailNavigationController$1, reason: invalid class name */
    public final class AnonymousClass1
            implements BottomNavigationView.OnNavigationItemSelectedListener {
        public AnonymousClass1() {}

        @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
        public final boolean onNavigationItemSelected(MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            final WifiDetailNavigationController wifiDetailNavigationController =
                    WifiDetailNavigationController.this;
            if (itemId == R.id.qrcode_button) {
                wifiDetailNavigationController.setMode(0);
                final int i = 0;
                WifiUtils.authenticateUser(
                        55,
                        new Runnable(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.details.WifiDetailNavigationController$1$$ExternalSyntheticLambda0
                            public final /* synthetic */ WifiDetailNavigationController
                                            .AnonymousClass1
                                    f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i2 = i;
                                WifiDetailNavigationController.AnonymousClass1 anonymousClass1 =
                                        this.f$0;
                                switch (i2) {
                                    case 0:
                                        WifiDetailNavigationController.this
                                                .launchWifiDppConfiguratorActivity$1$1();
                                        break;
                                    default:
                                        WifiDetailNavigationController.this.shareNetwork$1();
                                        break;
                                }
                            }
                        },
                        wifiDetailNavigationController.mAuthErrorListener,
                        wifiDetailNavigationController.mFragment);
                return true;
            }
            if (itemId == R.id.quickshare_button) {
                if (!wifiDetailNavigationController.mIsQuickShareAvailable
                        || !wifiDetailNavigationController.mInSetupWizardFinished
                        || wifiDetailNavigationController.mWifiConfig == null) {
                    return true;
                }
                wifiDetailNavigationController.setMode(0);
                final int i2 = 1;
                WifiUtils.authenticateUser(
                        255,
                        new Runnable(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.details.WifiDetailNavigationController$1$$ExternalSyntheticLambda0
                            public final /* synthetic */ WifiDetailNavigationController
                                            .AnonymousClass1
                                    f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i22 = i2;
                                WifiDetailNavigationController.AnonymousClass1 anonymousClass1 =
                                        this.f$0;
                                switch (i22) {
                                    case 0:
                                        WifiDetailNavigationController.this
                                                .launchWifiDppConfiguratorActivity$1$1();
                                        break;
                                    default:
                                        WifiDetailNavigationController.this.shareNetwork$1();
                                        break;
                                }
                            }
                        },
                        wifiDetailNavigationController.mAuthErrorListener,
                        wifiDetailNavigationController.mFragment);
                return true;
            }
            if (itemId != R.id.forget_button) {
                return true;
            }
            if (!wifiDetailNavigationController.mWifiEntry.isSubscription()) {
                wifiDetailNavigationController.forgetNetwork$1();
                return true;
            }
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(wifiDetailNavigationController.mFragment.getActivity());
            builder.setMessage(R.string.forget_passpoint_dialog_message);
            builder.setTitle(R.string.wifi_forget_dialog_title);
            builder.setPositiveButton(
                    R.string.wifi_forget,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.wifi.details.WifiDetailNavigationController$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            WifiDetailNavigationController.this.forgetNetwork$1();
                        }
                    });
            builder.setNegativeButton(
                    R.string.common_cancel,
                    new WifiDetailNavigationController$$ExternalSyntheticLambda1());
            builder.create().show();
            return true;
        }
    }

    public WifiDetailNavigationController(
            WifiEntry wifiEntry,
            Context context,
            Fragment fragment,
            boolean z,
            Lifecycle lifecycle,
            MetricsFeatureProvider metricsFeatureProvider,
            String str) {
        boolean z2 = false;
        this.mContext = context;
        this.mWifiEntry = wifiEntry;
        this.mWifiConfig = wifiEntry.getWifiConfiguration();
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mMetricsFeatureProvider = metricsFeatureProvider;
        this.mFragment = fragment;
        lifecycle.addObserver(this);
        this.mInManageNetwork = z;
        this.mSAScreenId = str;
        if (WifiUtils.isSupportQuickShare(context) && WifiUtils.isQuickShareEnabled(context)) {
            if (!wifiEntry.canShare()) {
                Log.d("WifiUtils", "canShare is FALSE");
            } else if (WifiUtils.isSecurityTypeSupportQrCode(context, wifiEntry)) {
                z2 = true;
            } else {
                Log.d("WifiUtils", "isSecurityTypeSupportQrCode is FALSE");
            }
        }
        this.mIsQuickShareAvailable = z2;
        this.mInSetupWizardFinished = Utils.isDeviceProvisioned(context);
        this.mIsMaintenanceMode = Rune.isMaintenanceMode();
        this.mAuthErrorListener = this;
    }

    public final boolean canModifyNetwork() {
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry == null) {
            return false;
        }
        if (!WifiUtils.isBlockedByEnterprise(this.mContext, wifiEntry.getSsid())
                && !WifiUtils.isNetworkLockedDown(
                        this.mContext, wifiEntry.getWifiConfiguration())) {
            return WifiDevicePolicyManager.canModifyNetwork(this.mContext, this.mWifiConfig)
                    && !WifiUtils.isNetworkLockedDown(this.mContext, this.mWifiConfig);
        }
        Log.d("WifiDetailNavigationCtrl", "modify is blocked");
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x0155, code lost:

       if (r0.isSubscription() != false) goto L115;
    */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x018f, code lost:

       if (r0.isEphemeral() != false) goto L115;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0193, code lost:

       if (r10.mWifiConfig != null) goto L115;
    */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0141  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void displayBottomNavigation() {
        /*
            Method dump skipped, instructions count: 421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiDetailNavigationController.displayBottomNavigation():void");
    }

    public final void forgetNetwork$1() {
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry != null) {
            if (wifiEntry instanceof HotspotNetworkEntry) {
                Log.i("WifiDetailNavigationCtrl", "Instant Mobile Hotspot network");
                ((HotspotNetworkEntry) wifiEntry).disconnect(null);
            }
            SCloudWifiDataManager.getInstance(this.mContext.getApplicationContext())
                    .syncToRemove(wifiEntry.getWifiConfiguration());
            if (wifiEntry.semIsEphemeral()) {
                wifiEntry.disconnect(this);
            } else {
                wifiEntry.forget(this);
            }
        }
        Fragment fragment = this.mFragment;
        this.mMetricsFeatureProvider.action(fragment.getActivity(), 137, new Pair[0]);
        SALogging.insertSALog(this.mSAScreenId, "1028");
        Log.d("WifiDetailNavigationCtrl", "Exiting the WifiNetworkDetailsPage");
        setMode(0);
        if (fragment.getActivity() instanceof SettingsActivity) {
            ((SettingsActivity) fragment.getActivity()).finishPreferencePanel(null);
            return;
        }
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    public final void initBottom() {
        this.mBottomLayout =
                (RelativeLayout) this.mFragment.getActivity().findViewById(R.id.button_bar);
        setMode(1);
    }

    public final void launchWifiDppConfiguratorActivity$1$1() {
        SALogging.insertSALog(this.mSAScreenId, "1050");
        FragmentActivity activity = this.mFragment.getActivity();
        Bundle bundle = new Bundle();
        bundle.putBoolean("key_setup_wizard", !Utils.isDeviceProvisioned(this.mContext));
        bundle.putParcelable("key_config", this.mWifiEntry.getWifiConfiguration());
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$WifiQrCodeActivity");
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override // com.samsung.android.settings.wifi.details.WifiDppAuthenticationErrorListener
    public final void onAuthenticationError() {
        initBottom();
    }

    @Override // com.android.wifitrackerlib.WifiEntry.DisconnectCallback
    public final void onDisconnectResult(int i) {
        if (i != 0) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Failed to disconnect Wi-Fi network reason=", "WifiDetailNavigationCtrl");
        } else {
            Log.d("WifiDetailNavigationCtrl", "Succeeded to disconnect Wi-Fi network");
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry.ForgetCallback
    public final void onForgetResult(int i) {
        if (i == 0) {
            Log.d("WifiDetailNavigationCtrl", "Succeeded to forget Wi-Fi network");
            return;
        }
        Log.e("WifiDetailNavigationCtrl", "Forget Wi-Fi network failed");
        if (this.mFragment.getActivity() != null) {
            Toast.makeText(this.mContext, R.string.wifi_failed_forget_message, 0).show();
        }
    }

    public final void setMode(int i) {
        RelativeLayout relativeLayout;
        if (this.mBottomMode == i) {
            return;
        }
        this.mBottomMode = i;
        if (i == 0) {
            RelativeLayout relativeLayout2 = this.mBottomLayout;
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(8);
                return;
            }
            return;
        }
        Fragment fragment = this.mFragment;
        if (i != 1) {
            if (i != 2 || (relativeLayout = this.mBottomLayout) == null) {
                return;
            }
            relativeLayout.removeAllViews();
            View inflate =
                    fragment.getLayoutInflater()
                            .inflate(R.layout.sec_wifi_button_bar, (ViewGroup) null);
            this.mBottomButtonBarView = inflate;
            this.mBottomLayout.addView(inflate);
            this.mBottomLayout.setVisibility(0);
            return;
        }
        RelativeLayout relativeLayout3 = this.mBottomLayout;
        if (relativeLayout3 != null) {
            relativeLayout3.removeAllViews();
            BottomNavigationView bottomNavigationView =
                    (BottomNavigationView)
                            ((LayoutInflater)
                                            fragment.getActivity()
                                                    .getSystemService("layout_inflater"))
                                    .inflate(
                                            R.layout.sec_wifi_detail_bottom_layout,
                                            (ViewGroup) this.mBottomLayout,
                                            false);
            this.mBottomNavigationView = bottomNavigationView;
            this.mBottomLayout.addView(bottomNavigationView);
            this.mBottomNavigationView.selectedListener = new AnonymousClass1();
            this.mBottomLayout.setVisibility(0);
        }
        displayBottomNavigation();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void shareNetwork$1() {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiDetailNavigationController.shareNetwork$1():void");
    }

    @Override // com.samsung.android.settings.wifi.details.SecWifiPreferenceControllerHelper.ValidationUpdater
    public final void update(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "update triggered by Validator ", "WifiDetailNavigationCtrl", z);
    }
}
