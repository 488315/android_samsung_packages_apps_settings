package com.samsung.android.settings.wifi.intelligent;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.text.TextUtils;

import androidx.lifecycle.LifecycleObserver;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AutoConnectHotspotPrefereneController extends BasePreferenceController
        implements LifecycleObserver, OnResume {
    private static final int AUTO_CONNECT_HOTSPOT_ALWAYS_VALUE = 3;
    private static final int AUTO_CONNECT_HOTSPOT_ASK_BEFORE_CONNECTING_VALUE = 2;
    private static final int AUTO_CONNECT_HOTSPOT_NEVER_VALUE = 1;
    private static final String KEY_COMMON_MODE = "MODE";
    private static final String KEY_IS_FROM_SETTING = "IS_FROM_SETTING";
    private static final String TAG = "ConfigureWifiSettings.AutoConnectHotspotPrefereneController";
    private Preference mAutoConnectHotspot;
    Context mContext;
    private SemWifiManager mWifiManager;

    public AutoConnectHotspotPrefereneController(Context context, String str) {
        super(context, str);
        this.mContext = context;
        this.mWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    private int getSamsungAccountCount() {
        return ((AccountManager) this.mContext.getSystemService("account"))
                .getAccountsByType("com.osp.app.signin")
                .length;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mAutoConnectHotspot = findPreference;
        findPreference.setTitle(WifiApUtils.getStringID(R.string.wifi_auto_connect_hotspot_title));
        refreshSummary(this.mAutoConnectHotspot);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Utils.SPF_SupportMobileApEnhanced ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Resources resources = this.mContext.getResources();
        int advancedAutohotspotConnectSettings =
                this.mWifiManager.getAdvancedAutohotspotConnectSettings();
        String string = resources.getString(R.string.wifi_ap_not_signed_in);
        return getSamsungAccountCount() > 0
                ? advancedAutohotspotConnectSettings != 1
                        ? advancedAutohotspotConnectSettings != 2
                                ? advancedAutohotspotConnectSettings != 3
                                        ? string
                                        : resources.getString(
                                                R.string.wifi_auto_connect_hotspot_always_option)
                                : resources.getString(
                                        R.string
                                                .wifi_auto_connect_hotspot_ask_before_connecting_option)
                        : resources.getString(R.string.wifi_auto_connect_hotspot_never_option)
                : string;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        if (getSamsungAccountCount() == 0) {
            Intent intent = new Intent();
            intent.setAction("com.sems.app.signin.action.ADD_SAMSUNG_ACCOUNT");
            intent.setPackage("com.osp.app.signin");
            intent.setFlags(603979776);
            intent.putExtra(KEY_COMMON_MODE, "ADD_ACCOUNT");
            intent.putExtra(KEY_IS_FROM_SETTING, true);
            intent.putExtra("client_id", "s5d189ajvs");
            this.mContext.startActivity(intent);
        } else {
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            subSettingLauncher.setTitleRes(R.string.wifi_auto_connect_hotspot_title, null);
            String name = AutoConnectHotspotSettings.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mSourceMetricsCategory = 0;
            subSettingLauncher.launch();
        }
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        refreshSummary(this.mAutoConnectHotspot);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
