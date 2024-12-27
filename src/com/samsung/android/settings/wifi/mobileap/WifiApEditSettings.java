package com.samsung.android.settings.wifi.mobileap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.MacAddress;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureBandDropDownController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureBandSeekBarController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureBroadcastChannelDropDownController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureCancelSaveBottomView;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureHiddenNetworkController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureMacAddressTypeDropDownController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureMaxConnectionDropDownController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigurePassWordPreference;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigurePmfController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigurePowerSavingModeController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSSIDPreference;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSecurityDropDownController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSetDataLimitPreferenceController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSupportWifi6Controller;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureTurnOffHotspotTimerDropDownController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureWifiSharingController;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApBandConfig;
import com.samsung.android.settings.wifi.mobileap.hotspotlabs.WifiApHotspotLabsController;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApExpandablePreference;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifi.SemWifiApRestoreHelper;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApEditSettings extends DashboardFragment implements View.OnKeyListener {
    public static final IntentFilter INTENT_FILTER =
            new IntentFilter("android.intent.action.AIRPLANE_MODE");
    public PreferenceCategory mAdvancedSection1PreferenceCategory;
    public PreferenceCategory mAdvancedSection2PreferenceCategory;
    public WifiApExpandablePreference mAdvancedViewExpandablePreference;
    public WifiApConfigureBandDropDownController mBandDropDownController;
    public WifiApConfigureBandSeekBarController mBandSeekBarController;
    public WifiApConfigureBroadcastChannelDropDownController mBroadcastChannelDropDownController;
    public WifiApConfigureCancelSaveBottomView mCancelSaveBottomView;
    public ContentResolver mContentResolver;
    public FragmentActivity mContext;
    public WifiApConfigureHiddenNetworkController mHiddenNetworkController;
    public PreferenceCategory mHotspotLabPreferenceCategory;
    public WifiApConfigureMacAddressTypeDropDownController mMacAddressTypeDropDownController;
    public WifiApConfigureMaxConnectionDropDownController mMaxConnectionDropDownController;
    public WifiApConfigurePassWordPreference mPassWordPreference;
    public WifiApConfigurePmfController mPmfController;
    public WifiApConfigurePowerSavingModeController mPowerSavingModeController;
    public PreferenceScreen mPreferenceScreen;
    public RecyclerView mRecyclerView;
    public WifiApConfigureSSIDPreference mSSIDPreference;
    public WifiApConfigureSecurityDropDownController mSecurityDropDownController;
    public WifiApConfigureSetDataLimitPreferenceController mSetDataLimitPreferenceController;
    public WifiApConfigureSupportWifi6Controller mSupportWifi6Controller;
    public Toast mToast;
    public WifiApConfigureTurnOffHotspotTimerDropDownController
            mTurnOffHotspotTimerDropDownController;
    public WifiApEditReceiver mWifiApEditReceiver;
    public WifiApHotspotLabsController mWifiApHotspotLabsController;
    public WifiApConfigureWifiSharingController mWifiSharingController;
    public int mDeveloperModeCountDown = 8;
    public EnterpriseDeviceManager mEDM = null;
    public boolean resetSoftAp = false;
    public Handler mHandler = null;
    public boolean mIsBandSeekBarUxSupported = false;
    public final AnonymousClass1 mOnScrollListener =
            new RecyclerView
                    .OnScrollListener() { // from class:
                                          // com.samsung.android.settings.wifi.mobileap.WifiApEditSettings.1
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public final void onScrollStateChanged(int i, RecyclerView recyclerView) {
                    if (recyclerView.canScrollVertically(1)) {
                        return;
                    }
                    WifiApEditSettings wifiApEditSettings = WifiApEditSettings.this;
                    if (!wifiApEditSettings.mAdvancedViewExpandablePreference.isVisible()) {
                        Toast toast = wifiApEditSettings.mToast;
                        if (toast != null) {
                            toast.cancel();
                        }
                        if (i != 0) {
                            return;
                        }
                        int i2 = wifiApEditSettings.mDeveloperModeCountDown;
                        if (i2 > 1) {
                            int i3 = i2 - 1;
                            wifiApEditSettings.mDeveloperModeCountDown = i3;
                            if (i3 <= 5) {
                                Toast toast2 = wifiApEditSettings.mToast;
                                if (toast2 != null) {
                                    toast2.cancel();
                                }
                                Toast makeText =
                                        Toast.makeText(
                                                wifiApEditSettings.mContext,
                                                "You are "
                                                        + wifiApEditSettings.mDeveloperModeCountDown
                                                        + " steps away from enabling Hotspot Labs",
                                                0);
                                wifiApEditSettings.mToast = makeText;
                                makeText.show();
                            }
                        } else {
                            Toast toast3 = wifiApEditSettings.mToast;
                            if (toast3 != null) {
                                toast3.cancel();
                            }
                            if (Settings.Secure.getInt(
                                            wifiApEditSettings.mContext.getContentResolver(),
                                            "wifi_ap_hotspot_labs_is_enabled",
                                            0)
                                    == 0) {
                                Toast makeText2 =
                                        Toast.makeText(
                                                wifiApEditSettings.mContext,
                                                "You are a developer now.",
                                                0);
                                wifiApEditSettings.mToast = makeText2;
                                makeText2.show();
                                Settings.Secure.putInt(
                                        wifiApEditSettings.mContext.getContentResolver(),
                                        "wifi_ap_hotspot_labs_is_enabled",
                                        1);
                                wifiApEditSettings.mHandler.postDelayed(
                                        new WifiApEditSettings$$ExternalSyntheticLambda0(
                                                wifiApEditSettings, 6),
                                        10L);
                            }
                        }
                        System.out.println("The RecyclerView is not scrolling");
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApEditSettings$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApEditSettings$4, reason: invalid class name */
    public final class AnonymousClass4 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiApEditSettings this$0;

        public /* synthetic */ AnonymousClass4(WifiApEditSettings wifiApEditSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiApEditSettings;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.getActivity().onBackPressed();
                    break;
                case 1:
                    ((WifiManager) this.this$0.getActivity().getSystemService(ImsProfile.PDN_WIFI))
                            .setWifiEnabled(false);
                    Settings.Secure.putInt(
                            this.this$0.mContext.getContentResolver(), "wifi_saved_state", 0);
                    new Handler()
                            .postDelayed(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.settings.wifi.mobileap.WifiApEditSettings$5$1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            WifiApEditSettings
                                                    .m1359$$Nest$mshowBandSelectionTipsDialog(
                                                            WifiApEditSettings.AnonymousClass4.this
                                                                    .this$0);
                                        }
                                    },
                                    600L);
                    break;
                default:
                    WifiApEditSettings wifiApEditSettings = this.this$0;
                    IntentFilter intentFilter = WifiApEditSettings.INTENT_FILTER;
                    wifiApEditSettings.buildNewSoftApConfiguration();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApEditSettings$7, reason: invalid class name */
    public final class AnonymousClass7 extends SparseIntArray {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SaveButtonClickListener implements View.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiApEditSettings this$0;

        public /* synthetic */ SaveButtonClickListener(
                WifiApEditSettings wifiApEditSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiApEditSettings;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            int i = 0;
            switch (this.$r8$classId) {
                case 0:
                    WifiApSettingsUtils.hideKeyboard(this.this$0.mContext);
                    SALogging.insertSALog("TETH_011", "8008");
                    if (Utils.SPF_SupportMobileApDualAp
                            && !Utils.supportBridgedApStaConcurrency()) {
                        WifiManager wifiManager =
                                (WifiManager)
                                        this.this$0
                                                .getActivity()
                                                .getSystemService(ImsProfile.PDN_WIFI);
                        int wifiState = wifiManager.getWifiState();
                        int wifiApState = wifiManager.getWifiApState();
                        if ((wifiState != 3 && wifiState != 2)
                                || !this.this$0
                                        .getBandPreferenceConfig()
                                        .isThisDualBand2GhzAnd5Ghz()
                                || (wifiApState != 13 && wifiApState != 12)) {
                            WifiApEditSettings.m1359$$Nest$mshowBandSelectionTipsDialog(
                                    this.this$0);
                            break;
                        } else {
                            IntentFilter intentFilter = WifiApEditSettings.INTENT_FILTER;
                            Log.i(
                                    "WifiApEditSettings",
                                    "Dualband Selected but wifi is ON, so popup dialog");
                            final WifiApEditSettings wifiApEditSettings = this.this$0;
                            wifiApEditSettings.getClass();
                            Log.i("WifiApEditSettings", "CASE showDUalApWarningDialog");
                            new AlertDialog.Builder(wifiApEditSettings.mContext, 0)
                                    .setMessage(R.string.wifi_ap_dualap_wifi_off_warn)
                                    .setPositiveButton(
                                            WifiApUtils.getString(
                                                    wifiApEditSettings.mContext,
                                                    R.string.positive_button_off),
                                            new AnonymousClass4(wifiApEditSettings, 1))
                                    .setNegativeButton(
                                            R.string.dlg_cancel,
                                            new AnonymousClass4(wifiApEditSettings, i))
                                    .setOnCancelListener(
                                            new DialogInterface
                                                    .OnCancelListener() { // from class:
                                                                          // com.samsung.android.settings.wifi.mobileap.WifiApEditSettings.3
                                                @Override // android.content.DialogInterface.OnCancelListener
                                                public final void onCancel(
                                                        DialogInterface dialogInterface) {
                                                    WifiApEditSettings.this
                                                            .getActivity()
                                                            .onBackPressed();
                                                }
                                            })
                                    .setTitle(
                                            WifiApUtils.getString(
                                                    wifiApEditSettings.mContext,
                                                    R.string.wifi_ap_wifi_off_warn_title))
                                    .create()
                                    .show();
                            break;
                        }
                    } else {
                        WifiApEditSettings.m1359$$Nest$mshowBandSelectionTipsDialog(this.this$0);
                        break;
                    }
                    break;
                default:
                    SALogging.insertSALog("TETH_011", "8009");
                    this.this$0.getActivity().onBackPressed();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiApEditReceiver extends BroadcastReceiver {
        public WifiApEditReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals("android.intent.action.AIRPLANE_MODE")
                    || Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0)
                            == 0) {
                return;
            }
            IntentFilter intentFilter = WifiApEditSettings.INTENT_FILTER;
            Log.e("WifiApEditSettings", "Exiting Airplane mode enabled");
            if (Utils.isTablet()) {
                WifiApEditSettings.this.finishFragment();
            } else {
                WifiApEditSettings.this.getActivity().finish();
            }
        }
    }

    /* renamed from: -$$Nest$mshowBandSelectionTipsDialog, reason: not valid java name */
    public static void m1359$$Nest$mshowBandSelectionTipsDialog(
            WifiApEditSettings wifiApEditSettings) {
        if (!wifiApEditSettings.mIsBandSeekBarUxSupported
                || !wifiApEditSettings.mBandSeekBarController.isBandTypeModified()) {
            wifiApEditSettings.buildNewSoftApConfiguration();
            return;
        }
        FragmentActivity fragmentActivity = wifiApEditSettings.mContext;
        boolean z = WifiApSettingsUtils.DBG;
        AlertDialog.Builder builder = new AlertDialog.Builder(fragmentActivity, 0);
        WifiApBandConfig bandPreferenceConfig = wifiApEditSettings.getBandPreferenceConfig();
        builder.setTitle(bandPreferenceConfig.mDisplayText);
        builder.setMessage(
                ((ArrayList) WifiApSoftApUtils.getSupportedBandList(bandPreferenceConfig.mContext))
                                        .size()
                                <= 1
                        ? ApnSettings.MVNO_NONE
                        : bandPreferenceConfig.mDisplaySummaryText);
        builder.setPositiveButton(R.string.dlg_ok, new AnonymousClass4(wifiApEditSettings, 2));
        builder.create().show();
    }

    public final void buildNewSoftApConfiguration() {
        Log.i("WifiApEditSettings", "buildNewSoftApConfiguration()");
        WifiApSettingsUtils.hideKeyboard(this.mContext);
        SemWifiManager semWifiManager =
                (SemWifiManager) getActivity().getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        WifiApBigDataLogger wifiApBigDataLogger = new WifiApBigDataLogger(this.mContext);
        SoftApConfiguration softApConfiguration = semWifiManager.getSoftApConfiguration();
        SoftApConfiguration.Builder builder = new SoftApConfiguration.Builder(softApConfiguration);
        if (this.mSSIDPreference.isValid()) {
            builder.setSsid(this.mSSIDPreference.mSSID);
        }
        wifiApBigDataLogger.insertMHSBigData(0, -1);
        int dropDownValue = this.mSecurityDropDownController.getDropDownValue();
        wifiApBigDataLogger.insertMHSBigData(2, dropDownValue);
        if (dropDownValue == 0) {
            builder.setPassphrase((String) null, 0);
        } else if (dropDownValue == 5) {
            builder.setPassphrase((String) null, 5);
        } else {
            builder.setPassphrase(this.mPassWordPreference.mPassWord, dropDownValue);
        }
        if (!getBandPreferenceConfig().isThisBand6Ghz()) {
            Settings.Secure.putInt(
                    this.mContentResolver,
                    "wifi_ap_security_type",
                    this.mSecurityDropDownController.getDropDownValue());
        }
        wifiApBigDataLogger.insertMHSBigData(1, -1);
        WifiApBandConfig bandPreferenceConfig = getBandPreferenceConfig();
        if (bandPreferenceConfig.isThisBand6Ghz()) {
            AnonymousClass7 anonymousClass7 = new AnonymousClass7();
            anonymousClass7.put(4, 0);
            builder.setChannels(anonymousClass7);
            wifiApBigDataLogger.insertMHSBigData(4, 5935);
        } else if (bandPreferenceConfig.isThisDualBand2GhzAnd5Ghz()) {
            AnonymousClass7 anonymousClass72 = new AnonymousClass7();
            anonymousClass72.put(1, 0);
            anonymousClass72.put(2, 149);
            builder.setChannels(anonymousClass72);
        } else if (bandPreferenceConfig.isThisBand5Ghz()) {
            AnonymousClass7 anonymousClass73 = new AnonymousClass7();
            anonymousClass73.put(2, 149);
            builder.setChannels(anonymousClass73);
            wifiApBigDataLogger.insertMHSBigData(4, 149);
        } else if (bandPreferenceConfig.isThisBand2Ghz()) {
            int dropDownValue2 =
                    this.mBroadcastChannelDropDownController.isAvailable()
                            ? this.mBroadcastChannelDropDownController.getDropDownValue()
                            : WifiApSoftApUtils.getBroadcastChannel(this.mContext);
            AnonymousClass7 anonymousClass74 = new AnonymousClass7();
            anonymousClass74.put(1, dropDownValue2);
            builder.setChannels(anonymousClass74);
        }
        builder.setHiddenSsid(this.mHiddenNetworkController.isHiddenNetwork());
        wifiApBigDataLogger.insertMHSBigData(
                5, this.mHiddenNetworkController.isHiddenNetwork() ? 1 : 0);
        if (this.mMacAddressTypeDropDownController.isValueModified()) {
            this.resetSoftAp = true;
            if (this.mMacAddressTypeDropDownController.getDropDownValue() == 1) {
                builder.setMacRandomizationSetting(0);
            } else {
                builder.setMacRandomizationSetting(1);
                builder.setBssid((MacAddress) null);
            }
        }
        final SoftApConfiguration build = builder.build();
        Log.i("WifiApEditSettings", "OLD CONFIG====>: " + softApConfiguration.toString());
        Log.i("WifiApEditSettings", "NEW CONFIG====>:" + build.toString());
        if (!softApConfiguration.equals(build)) {
            this.resetSoftAp = true;
        }
        if (this.mMaxConnectionDropDownController.isValueModified()) {
            this.resetSoftAp = true;
        }
        if (WifiApSoftApUtils.getWifiApBandConfig(this.mContext).isThisBand6Ghz()
                        != getBandPreferenceConfig().isThisBand6Ghz()
                && WifiApFrameworkUtils.isWifiApStateEnablingOrEnabled(this.mContext)) {
            this.resetSoftAp = true;
        }
        if (this.mSupportWifi6Controller.isSupportWifi6SwitchStateModified()) {
            this.resetSoftAp = true;
        }
        if (this.mPmfController.isValueModified()) {
            this.resetSoftAp = true;
        }
        if (this.mPowerSavingModeController.isPowerSavingModeModified()) {
            this.resetSoftAp = true;
        }
        if (!"VZW".equalsIgnoreCase(Utils.CONFIGOPBRANDINGFORMOBILEAP)
                || !this.resetSoftAp
                || !WifiApFrameworkUtils.isWifiApStateEnablingOrEnabled(this.mContext)) {
            saveNewConfiguration(build);
            return;
        }
        Log.i("WifiApEditSettings", "VZW dialog for save button, reset");
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this.mContext, 0);
        builder2.setTitle(R.string.wifi_ap_wifi_save);
        builder2.setMessage(R.string.wifi_ap_wifi_config_summary);
        builder2.setCancelable(true)
                .setPositiveButton(
                        R.string.common_continue,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.wifi.mobileap.WifiApEditSettings$$ExternalSyntheticLambda7
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                WifiApEditSettings wifiApEditSettings = WifiApEditSettings.this;
                                SoftApConfiguration softApConfiguration2 = build;
                                IntentFilter intentFilter = WifiApEditSettings.INTENT_FILTER;
                                wifiApEditSettings.saveNewConfiguration(softApConfiguration2);
                            }
                        });
        builder2.setNegativeButton(
                R.string.cancel, new WifiApEditSettings$$ExternalSyntheticLambda8());
        builder2.setOnCancelListener(new WifiApEditSettings$$ExternalSyntheticLambda9());
        builder2.create().show();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return new ArrayList();
    }

    public final WifiApBandConfig getBandPreferenceConfig() {
        WifiApBandConfig wifiApBandConfig = WifiApSoftApUtils.getWifiApBandConfig(this.mContext);
        if (this.mIsBandSeekBarUxSupported) {
            WifiApConfigureBandSeekBarController wifiApConfigureBandSeekBarController =
                    this.mBandSeekBarController;
            return wifiApConfigureBandSeekBarController != null
                    ? wifiApConfigureBandSeekBarController.getWifiApBandConfig()
                    : wifiApBandConfig;
        }
        WifiApConfigureBandDropDownController wifiApConfigureBandDropDownController =
                this.mBandDropDownController;
        return wifiApConfigureBandDropDownController != null
                ? wifiApConfigureBandDropDownController.getWifiApBandConfig()
                : wifiApBandConfig;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        Intent intent = ((Activity) context).getIntent();
        int intExtra = intent.getIntExtra("intent_key_activity_title", -1);
        if (intExtra == -1) {
            intExtra = intent.getIntExtra(":settings:show_fragment_title_resid", -1);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                intExtra, "getFragmentTitleResId() - mIntentTitleResId: ", "WifiApEditSettings");
        if (intExtra != -1) {
            return WifiApUtils.getStringID(intExtra);
        }
        WifiApSoftApUtils.getSoftApConfiguration(context).getPassphrase();
        return WifiApUtils.getStringID(R.string.wifi_ap_menu_configure_hotspot);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApEditSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5000;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_edit_settings;
    }

    public final int getSecurityType() {
        int securityType = WifiApSoftApUtils.getSecurityType(this.mContext);
        WifiApConfigureSecurityDropDownController wifiApConfigureSecurityDropDownController =
                this.mSecurityDropDownController;
        if (wifiApConfigureSecurityDropDownController != null) {
            return wifiApConfigureSecurityDropDownController.getDropDownValue();
        }
        Log.d("WifiApEditSettings", "mSecurityDropDownController is null");
        return securityType;
    }

    public final WifiPolicy getWifiPolicy() {
        FragmentActivity fragmentActivity;
        if (this.mEDM == null && (fragmentActivity = this.mContext) != null) {
            this.mEDM =
                    EnterpriseDeviceManager.getInstance(fragmentActivity.getApplicationContext());
        }
        EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
        if (enterpriseDeviceManager == null || enterpriseDeviceManager.getWifiPolicy() == null) {
            return null;
        }
        return this.mEDM.getWifiPolicy();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        int i = 1;
        int i2 = 0;
        super.onActivityCreated(bundle);
        Log.i("WifiApEditSettings", "onActivityCreated() - onConfigurationChanged is being called");
        this.mWifiApEditReceiver = new WifiApEditReceiver();
        Intent intent = getActivity().getIntent();
        int intExtra = intent != null ? intent.getIntExtra("intent_key_activity_title", -1) : -1;
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                intExtra, "onActivityCreated() - mIntentTitleResId: ", "WifiApEditSettings");
        if (intExtra != -1) {
            getActivity().setTitle(WifiApUtils.getStringID(intExtra));
        } else {
            getActivity()
                    .setTitle(WifiApUtils.getStringID(R.string.wifi_ap_menu_configure_hotspot));
        }
        FragmentActivity activity = getActivity();
        SaveButtonClickListener saveButtonClickListener = new SaveButtonClickListener(this, i2);
        SaveButtonClickListener saveButtonClickListener2 = new SaveButtonClickListener(this, i);
        WifiApConfigureCancelSaveBottomView wifiApConfigureCancelSaveBottomView =
                new WifiApConfigureCancelSaveBottomView();
        View findViewById = activity.findViewById(R.id.button_bar);
        if (findViewById != null) {
            findViewById.setVisibility(0);
            ((RelativeLayout) findViewById)
                    .addView(
                            activity.getLayoutInflater()
                                    .inflate(
                                            R.layout.sec_wifi_ap_cancel_save_button_bar,
                                            (ViewGroup) null));
            wifiApConfigureCancelSaveBottomView.mCancelButton =
                    (Button) activity.findViewById(R.id.cancel_button);
            wifiApConfigureCancelSaveBottomView.mSaveButton =
                    (Button) activity.findViewById(R.id.save_button);
        }
        Button button = wifiApConfigureCancelSaveBottomView.mSaveButton;
        if (button != null) {
            button.setOnClickListener(saveButtonClickListener);
        } else {
            SemLog.i(
                    "WifiApConfigureCancelSaveBottomView",
                    "WifiApConfigureCancelSaveBottomView() - cstr,mSaveButton is null");
        }
        Button button2 = wifiApConfigureCancelSaveBottomView.mCancelButton;
        if (button2 != null) {
            button2.setOnClickListener(saveButtonClickListener2);
        } else {
            SemLog.i(
                    "WifiApConfigureCancelSaveBottomView",
                    "WifiApConfigureCancelSaveBottomView() - cstr,mCancelButton is null");
        }
        this.mCancelSaveBottomView = wifiApConfigureCancelSaveBottomView;
        this.mAdvancedSection1PreferenceCategory =
                (PreferenceCategory)
                        this.mPreferenceScreen.findPreference("configure_advanced_section_1");
        this.mAdvancedSection2PreferenceCategory =
                (PreferenceCategory)
                        this.mPreferenceScreen.findPreference("configure_advanced_section_2");
        this.mHotspotLabPreferenceCategory =
                (PreferenceCategory) this.mPreferenceScreen.findPreference("hotspot_lab_section");
        WifiApExpandablePreference wifiApExpandablePreference =
                (WifiApExpandablePreference)
                        this.mPreferenceScreen.findPreference(
                                "configure_advanced_expandable_preference");
        this.mAdvancedViewExpandablePreference = wifiApExpandablePreference;
        wifiApExpandablePreference.mOnPreferenceItemCLickListener = new AnonymousClass2();
        Log.i(
                "WifiApEditSettings",
                "Init SoftAP Config: "
                        + WifiApSoftApUtils.getSoftApConfiguration(this.mContext).toString());
        this.mSSIDPreference =
                (WifiApConfigureSSIDPreference)
                        this.mPreferenceScreen.findPreference("network_name_preference");
        this.mPassWordPreference =
                (WifiApConfigurePassWordPreference)
                        this.mPreferenceScreen.findPreference("password_preference");
        this.mIsBandSeekBarUxSupported =
                WifiApFrameworkUtils.isBandSeekBarUxSupported(this.mContext);
        this.mSSIDPreference.mWifiApConfigureSettings = this;
        this.mPassWordPreference.mWifiApConfigureSettings = this;
        Bundle arguments = getArguments();
        String str = ApnSettings.MVNO_NONE;
        if (arguments != null) {
            str = getArguments().getString("intent_key_focus_item", ApnSettings.MVNO_NONE);
        } else {
            Log.i("WifiApEditSettings", "getArguments() is null");
        }
        Log.i("WifiApEditSettings", "focusValue = " + str);
        if (!TextUtils.isEmpty(str)) {
            str.getClass();
            if (str.equals("intent_value_focus_network_name")) {
                WifiApConfigureSSIDPreference wifiApConfigureSSIDPreference = this.mSSIDPreference;
                wifiApConfigureSSIDPreference.mIsRequestFocusPendingFlag = true;
                wifiApConfigureSSIDPreference.notifyChanged();
            } else if (str.equals("intent_value_focus_password")) {
                WifiApConfigurePassWordPreference wifiApConfigurePassWordPreference =
                        this.mPassWordPreference;
                wifiApConfigurePassWordPreference.mIsRequestFocusPendingFlag = true;
                wifiApConfigurePassWordPreference.notifyChanged();
            }
        }
        if (bundle != null) {
            Log.i("WifiApEditSettings", "savedInstanceState is not null");
            if (bundle.containsKey("bundle_key_show_advance_option")
                    && bundle.getBoolean("bundle_key_show_advance_option")) {
                this.mAdvancedViewExpandablePreference.setVisible(false);
                this.mAdvancedSection1PreferenceCategory.setVisible(true);
                this.mAdvancedSection2PreferenceCategory.setVisible(true);
                this.mHotspotLabPreferenceCategory.setVisible(true);
            }
            WifiApConfigureSSIDPreference wifiApConfigureSSIDPreference2 = this.mSSIDPreference;
            wifiApConfigureSSIDPreference2.getClass();
            if (bundle.containsKey("bundle_key_ssid_value")) {
                wifiApConfigureSSIDPreference2.mSSID = bundle.getString("bundle_key_ssid_value");
                Utils$$ExternalSyntheticOutline0.m(
                        new StringBuilder("onRestoreInstanceState: "),
                        wifiApConfigureSSIDPreference2.mSSID,
                        "WifiApConfigureSSIDPreference");
            }
            WifiApConfigurePassWordPreference wifiApConfigurePassWordPreference2 =
                    this.mPassWordPreference;
            wifiApConfigurePassWordPreference2.getClass();
            if (bundle.containsKey("bundle_key_password_value")) {
                wifiApConfigurePassWordPreference2.mPassWord =
                        bundle.getString("bundle_key_password_value");
                Utils$$ExternalSyntheticOutline0.m(
                        new StringBuilder("onRestoreInstanceState: "),
                        wifiApConfigurePassWordPreference2.mPassWord,
                        "WifiApConfigurePassWordPreference");
            }
            this.mBandDropDownController.onRestoreInstanceState(bundle);
            this.mBandSeekBarController.onRestoreInstanceState(bundle);
            this.mSecurityDropDownController.onRestoreInstanceState(bundle);
            this.mMaxConnectionDropDownController.onRestoreInstanceState(bundle);
            this.mBroadcastChannelDropDownController.onRestoreInstanceState(bundle);
            this.mMacAddressTypeDropDownController.onRestoreInstanceState(bundle);
            this.mTurnOffHotspotTimerDropDownController.onRestoreInstanceState(bundle);
            this.mHiddenNetworkController.onRestoreInstanceState(bundle);
            this.mSupportWifi6Controller.onRestoreInstanceState(bundle);
            this.mPmfController.onRestoreInstanceState(bundle);
            this.mPowerSavingModeController.onRestoreInstanceState(bundle);
            this.mWifiSharingController.onRestoreInstanceState(bundle);
        }
        this.mRecyclerView = getListView();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.i("WifiApEditSettings", "onCreate()");
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mContentResolver = activity.getContentResolver();
        Utils.initMHSFeature(this.mContext);
        Bundle arguments = getArguments();
        if (arguments != null) {
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "needresult:", "WifiApEditSettings", arguments.getInt("needresult", 0) == 1);
        }
        this.mHandler = new Handler();
        this.mPreferenceScreen = getPreferenceScreen();
        WifiApConfigureBandDropDownController wifiApConfigureBandDropDownController =
                (WifiApConfigureBandDropDownController)
                        use(WifiApConfigureBandDropDownController.class);
        this.mBandDropDownController = wifiApConfigureBandDropDownController;
        wifiApConfigureBandDropDownController.setHost(this);
        WifiApConfigureBandSeekBarController wifiApConfigureBandSeekBarController =
                (WifiApConfigureBandSeekBarController)
                        use(WifiApConfigureBandSeekBarController.class);
        this.mBandSeekBarController = wifiApConfigureBandSeekBarController;
        wifiApConfigureBandSeekBarController.setHost(this);
        WifiApConfigureSecurityDropDownController wifiApConfigureSecurityDropDownController =
                (WifiApConfigureSecurityDropDownController)
                        use(WifiApConfigureSecurityDropDownController.class);
        this.mSecurityDropDownController = wifiApConfigureSecurityDropDownController;
        wifiApConfigureSecurityDropDownController.setHost(this);
        WifiApConfigureWifiSharingController wifiApConfigureWifiSharingController =
                (WifiApConfigureWifiSharingController)
                        use(WifiApConfigureWifiSharingController.class);
        this.mWifiSharingController = wifiApConfigureWifiSharingController;
        wifiApConfigureWifiSharingController.setHost(this);
        WifiApConfigureMaxConnectionDropDownController
                wifiApConfigureMaxConnectionDropDownController =
                        (WifiApConfigureMaxConnectionDropDownController)
                                use(WifiApConfigureMaxConnectionDropDownController.class);
        this.mMaxConnectionDropDownController = wifiApConfigureMaxConnectionDropDownController;
        wifiApConfigureMaxConnectionDropDownController.setHost(this);
        WifiApConfigureBroadcastChannelDropDownController
                wifiApConfigureBroadcastChannelDropDownController =
                        (WifiApConfigureBroadcastChannelDropDownController)
                                use(WifiApConfigureBroadcastChannelDropDownController.class);
        this.mBroadcastChannelDropDownController =
                wifiApConfigureBroadcastChannelDropDownController;
        wifiApConfigureBroadcastChannelDropDownController.setHost(this);
        WifiApConfigureTurnOffHotspotTimerDropDownController
                wifiApConfigureTurnOffHotspotTimerDropDownController =
                        (WifiApConfigureTurnOffHotspotTimerDropDownController)
                                use(WifiApConfigureTurnOffHotspotTimerDropDownController.class);
        this.mTurnOffHotspotTimerDropDownController =
                wifiApConfigureTurnOffHotspotTimerDropDownController;
        wifiApConfigureTurnOffHotspotTimerDropDownController.setHost(this);
        WifiApConfigureSetDataLimitPreferenceController
                wifiApConfigureSetDataLimitPreferenceController =
                        (WifiApConfigureSetDataLimitPreferenceController)
                                use(WifiApConfigureSetDataLimitPreferenceController.class);
        this.mSetDataLimitPreferenceController = wifiApConfigureSetDataLimitPreferenceController;
        wifiApConfigureSetDataLimitPreferenceController.setHost(this);
        WifiApConfigureMacAddressTypeDropDownController
                wifiApConfigureMacAddressTypeDropDownController =
                        (WifiApConfigureMacAddressTypeDropDownController)
                                use(WifiApConfigureMacAddressTypeDropDownController.class);
        this.mMacAddressTypeDropDownController = wifiApConfigureMacAddressTypeDropDownController;
        wifiApConfigureMacAddressTypeDropDownController.setHost(this);
        WifiApConfigureHiddenNetworkController wifiApConfigureHiddenNetworkController =
                (WifiApConfigureHiddenNetworkController)
                        use(WifiApConfigureHiddenNetworkController.class);
        this.mHiddenNetworkController = wifiApConfigureHiddenNetworkController;
        wifiApConfigureHiddenNetworkController.setHost(this);
        WifiApConfigureSupportWifi6Controller wifiApConfigureSupportWifi6Controller =
                (WifiApConfigureSupportWifi6Controller)
                        use(WifiApConfigureSupportWifi6Controller.class);
        this.mSupportWifi6Controller = wifiApConfigureSupportWifi6Controller;
        wifiApConfigureSupportWifi6Controller.setHost(this);
        WifiApConfigurePmfController wifiApConfigurePmfController =
                (WifiApConfigurePmfController) use(WifiApConfigurePmfController.class);
        this.mPmfController = wifiApConfigurePmfController;
        wifiApConfigurePmfController.setHost(this);
        WifiApConfigurePowerSavingModeController wifiApConfigurePowerSavingModeController =
                (WifiApConfigurePowerSavingModeController)
                        use(WifiApConfigurePowerSavingModeController.class);
        this.mPowerSavingModeController = wifiApConfigurePowerSavingModeController;
        wifiApConfigurePowerSavingModeController.setHost(this);
        WifiApHotspotLabsController wifiApHotspotLabsController =
                (WifiApHotspotLabsController) use(WifiApHotspotLabsController.class);
        this.mWifiApHotspotLabsController = wifiApHotspotLabsController;
        wifiApHotspotLabsController.setHost(this);
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        Log.i(
                "WifiApEditSettings",
                "resetSoftAp:"
                        + this.resetSoftAp
                        + "onKey :: keyCode = "
                        + i
                        + ", event = "
                        + keyEvent.describeContents());
        return i == 4 && this.resetSoftAp;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.i("WifiApEditSettings", "onPause");
        this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.i("WifiApEditSettings", "onResume");
        String str = Build.TYPE;
        if ("eng".equals(str) || "userdebug".equals(str) || Debug.semIsProductDev()) {
            if (Settings.Secure.getInt(
                            this.mContext.getContentResolver(),
                            "wifi_ap_hotspot_labs_is_enabled",
                            0)
                    != 0) {
                this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
            } else {
                this.mRecyclerView.addOnScrollListener(this.mOnScrollListener);
                this.mDeveloperModeCountDown = 8;
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        Log.i("WifiApEditSettings", "onSaveInstanceState");
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(
                "bundle_key_show_advance_option",
                !this.mAdvancedViewExpandablePreference.isVisible());
        bundle.putString("bundle_key_ssid_value", this.mSSIDPreference.mSSID);
        bundle.putString("bundle_key_password_value", this.mPassWordPreference.mPassWord);
        this.mBandDropDownController.onSaveInstanceState(bundle);
        this.mBandSeekBarController.onSaveInstanceState(bundle);
        this.mSecurityDropDownController.onSaveInstanceState(bundle);
        this.mMaxConnectionDropDownController.onSaveInstanceState(bundle);
        this.mBroadcastChannelDropDownController.onSaveInstanceState(bundle);
        this.mMacAddressTypeDropDownController.onSaveInstanceState(bundle);
        this.mTurnOffHotspotTimerDropDownController.onSaveInstanceState(bundle);
        this.mHiddenNetworkController.onSaveInstanceState(bundle);
        this.mSupportWifi6Controller.onSaveInstanceState(bundle);
        this.mPmfController.onSaveInstanceState(bundle);
        this.mPowerSavingModeController.onSaveInstanceState(bundle);
        this.mWifiSharingController.onSaveInstanceState(bundle);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        Log.i("WifiApEditSettings", "onStart");
        WifiPolicy wifiPolicy = getWifiPolicy();
        boolean isWifiApSettingUserModificationAllowed =
                wifiPolicy != null ? wifiPolicy.isWifiApSettingUserModificationAllowed() : true;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "WifiApSettingsAllowedToChange:",
                "WifiApEditSettings",
                isWifiApSettingUserModificationAllowed);
        if (!isWifiApSettingUserModificationAllowed) {
            Log.e("WifiApEditSettings", "Exiting Screen WifiApSettings not allowed");
            finish();
        }
        if (Settings.Global.getInt(this.mContentResolver, "airplane_mode_on", 0) != 0) {
            Log.e("WifiApEditSettings", "Exiting Airplane mode enabled");
            if (Utils.isTablet()) {
                finishFragment();
            } else {
                getActivity().finish();
            }
        }
        Log.d("WifiApEditSettings", "registering broadcast receiver");
        this.mContext.registerReceiver(this.mWifiApEditReceiver, INTENT_FILTER, 2);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        Log.i("WifiApEditSettings", "onStop");
        this.mContext.unregisterReceiver(this.mWifiApEditReceiver);
    }

    public final void saveNewConfiguration(SoftApConfiguration softApConfiguration) {
        this.mMaxConnectionDropDownController.saveValue();
        this.mBroadcastChannelDropDownController.saveValue();
        this.mMacAddressTypeDropDownController.saveValue();
        this.mTurnOffHotspotTimerDropDownController.saveValue();
        this.mSetDataLimitPreferenceController.saveDataLimitChange();
        this.mSupportWifi6Controller.saveSupportWifi6SwitchState();
        this.mPmfController.saveValue();
        this.mPowerSavingModeController.savePowerSavingModeSwitchState();
        this.mWifiSharingController.saveWifiSharingSwitchState();
        WifiApSoftApUtils.setSoftApConfiguration(this.mContext, softApConfiguration);
        Log.d(
                "WifiApEditSettings",
                "sendConfigChangedBroadcastToSmartTethering() -  Sending Broadcast");
        if (Utils.SPF_SupportMobileApEnhanced) {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.server.wifi.softap.smarttethering.ssid_changed");
            this.mContext.sendBroadcast(intent, "android.permission.OVERRIDE_WIFI_CONFIG");
        }
        SemWifiApRestoreHelper.setCurrentApConfiguration(this.mContext);
        if (this.resetSoftAp
                && WifiApFrameworkUtils.isWifiApStateEnablingOrEnabled(this.mContext)) {
            Log.i("WifiApEditSettings", "Finishing Activity For HotspotReset");
            Intent intent2 = new Intent();
            intent2.putExtra("is_hotspot_rest_happened", true);
            setResult(-1, intent2);
        }
        getActivity().onBackPressed();
    }

    public final void updateSaveButtonEnabling() {
        boolean z;
        boolean isValid = this.mSSIDPreference.isValid();
        AbsAdapter$$ExternalSyntheticOutline0.m("isValidSsid: ", "WifiApEditSettings", isValid);
        boolean z2 = false;
        if (isValid) {
            if (this.mSecurityDropDownController.isAnyOpenSecurityTypeSelected()) {
                WifiPolicy wifiPolicy = getWifiPolicy();
                z = wifiPolicy != null ? wifiPolicy.isOpenWifiApAllowed() : true;
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "isOpenWifiApAllowed:", "WifiApEditSettings", z);
            } else {
                WifiApConfigurePassWordPreference wifiApConfigurePassWordPreference =
                        this.mPassWordPreference;
                z =
                        !TextUtils.isEmpty(wifiApConfigurePassWordPreference.mPassWord)
                                && wifiApConfigurePassWordPreference.mPassWord.length() >= 8
                                && wifiApConfigurePassWordPreference.mPassWord.getBytes().length
                                        >= 8
                                && wifiApConfigurePassWordPreference.mPassWord.getBytes().length
                                        <= 63
                                && !wifiApConfigurePassWordPreference.mPassWord.equals(
                                        wifiApConfigurePassWordPreference.mErrPassWord);
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "isValidPassPhrase: ", "WifiApEditSettings", z);
            }
            if (z) {
                z2 = true;
            }
        }
        WifiApConfigureCancelSaveBottomView wifiApConfigureCancelSaveBottomView =
                this.mCancelSaveBottomView;
        Boolean valueOf = Boolean.valueOf(z2);
        wifiApConfigureCancelSaveBottomView.getClass();
        SemLog.i("WifiApConfigureCancelSaveBottomView", "Enabling Save Button - " + valueOf);
        Button button = wifiApConfigureCancelSaveBottomView.mSaveButton;
        if (button != null) {
            button.setEnabled(z2);
        } else {
            SemLog.i(
                    "WifiApConfigureCancelSaveBottomView",
                    "setEnabledSaveButton() - ,mSaveButton is null");
        }
    }
}
