package com.samsung.android.settings.wifi.advanced;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Debug;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class PasspointPreferenceSwitchController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause, PasspointPreferenceHelper {
    private static final boolean DBG = Debug.semIsProductDev();
    private static final String KEY_HOTSPOT_PROFILE = "wifi_hs20_profile";
    private static final String TAG = "ConfigureWifiSettings.Hotspot20";
    private Preference mPasspoint;
    private BroadcastReceiver mReceiver;
    private final WifiManager mWifiManager;

    public PasspointPreferenceSwitchController(Context context, String str) {
        super(context, str);
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        refreshSummary(preferenceScreen.findPreference(KEY_HOTSPOT_PROFILE));
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPasspoint = findPreference;
        this.mReceiver = getReceiver(findPreference, getPreferenceKey());
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return getDetailAvailableStatus(this.mWifiManager, this.mContext, getPreferenceKey());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public int getDetailAvailableStatus(WifiManager wifiManager, Context context, String str) {
        int i = wifiManager.isWifiEnabled() ? 0 : 5;
        if (KnoxUtils.isApplicationRestricted(context, str, "hide")) {
            return 3;
        }
        if (KnoxUtils.isApplicationRestricted(context, str, "grayout")) {
            return 5;
        }
        return i;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    public BroadcastReceiver getReceiver(Preference preference, String str) {
        return new BroadcastReceiver() { // from class:
                                         // com.samsung.android.settings.wifi.advanced.PasspointPreferenceHelper.1
            public final /* synthetic */ String val$key;
            public final /* synthetic */ Preference val$preference;

            public AnonymousClass1(Preference preference2, String str2) {
                r2 = preference2;
                r3 = str2;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                    int intExtra = intent.getIntExtra("wifi_state", 4);
                    if (r2 == null || PasspointPreferenceHelper.this.isNeedToGrayOut(context, r3)) {
                        return;
                    }
                    r2.setEnabled(intExtra == 3);
                }
            }
        };
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_connections;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Resources resources = this.mContext.getResources();
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return "DCM".equals(Utils.getSalesCode())
                ? resources.getString(R.string.wifi_hotspot20_summary_dcm)
                : ("TMB".equals(Utils.getSalesCode()) || "TMK".equals(Utils.getSalesCode()))
                        ? resources.getString(R.string.wifi_hotspot20_summary_tmo)
                        : super.getSummary();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return this.mWifiManager.isWifiPasspointEnabled();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.wifi.advanced.PasspointPreferenceHelper
    public boolean isNeedToGrayOut(Context context, String str) {
        return KnoxUtils.isApplicationRestricted(context, str, "grayout");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mContext.registerReceiver(
                this.mReceiver,
                AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                        "android.net.wifi.WIFI_STATE_CHANGED"));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        WifiInfo connectionInfo;
        this.mWifiManager.setWifiPasspointEnabled(z);
        if (!z
                && (connectionInfo = this.mWifiManager.getConnectionInfo()) != null
                && connectionInfo.isPasspointAp()) {
            Context context = this.mContext;
            ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                    .reportIssue(
                            100,
                            WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                    -1,
                                    "disconnect",
                                    context.getPackageManager().getNameForUid(context.getUserId()),
                                    context.getPackageName()));
            this.mWifiManager.disconnect();
        }
        SALogging.insertSALog(z ? 1L : 0L, "WIFI_200", "1240");
        refreshSummary(this.mPasspoint);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
