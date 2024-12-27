package com.samsung.android.settings.wifi.intelligent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.wifi.WifiManager;
import android.os.Debug;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MobileWIPSPrefController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final boolean DBG = Debug.semIsProductDev();
    private static final String KEY_MWIPS = "MobileWIPS";
    private static final String TAG = "MobileWIPSPrefController";
    private static final int WIPS_GOING_OFF = 2;
    private static final int WIPS_GOING_ON = 3;
    private static final int WIPS_OFF = 0;
    private static final int WIPS_ON = 1;
    private static final int defaultMWIPS = 0;
    private final IntentFilter mIntentFilter;
    private SwitchPreferenceCompat mMobileWipsPref;
    private final BroadcastReceiver mReceiver;
    private final WifiManager mWifiManager;
    private final ContentObserver mWipsObserver;

    public MobileWIPSPrefController(Context context, String str) {
        super(context, str);
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.wifi.intelligent.MobileWIPSPrefController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("wifi_state", 4);
                            if (intExtra == 3 || intExtra == 1) {
                                MobileWIPSPrefController mobileWIPSPrefController =
                                        MobileWIPSPrefController.this;
                                mobileWIPSPrefController.updateState(
                                        mobileWIPSPrefController.mMobileWipsPref);
                            }
                        }
                    }
                };
        this.mWipsObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.wifi.intelligent.MobileWIPSPrefController.2
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        if (Settings.Secure.getInt(
                                        ((AbstractPreferenceController)
                                                        MobileWIPSPrefController.this)
                                                .mContext.getContentResolver(),
                                        "wifi_mwips",
                                        0)
                                < 2) {
                            MobileWIPSPrefController mobileWIPSPrefController =
                                    MobileWIPSPrefController.this;
                            mobileWIPSPrefController.updateState(
                                    mobileWIPSPrefController.mMobileWipsPref);
                        }
                    }
                };
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        IntentFilter intentFilter = new IntentFilter();
        this.mIntentFilter = intentFilter;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
    }

    private boolean isLargeScreen() {
        Configuration configuration = this.mContext.getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        return (i <= 320 && configuration.fontScale >= 1.1f)
                || (i < 411 && configuration.fontScale >= 1.3f);
    }

    private void setPreferenceBadge(Preference preference) {
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "wifi_mwips", 0) != 1
                || !WifiBadgeUtils.isNewItemForWips(this.mContext)) {
            preference.setLayoutResource(
                    isLargeScreen()
                            ? R.layout.sec_preference_intelligent_wifi_item_large
                            : R.layout.sec_preference_intelligent_wifi_item);
        } else {
            Log.i(TAG, "There is new item for MobileWips. Show N badge");
            preference.setLayoutResource(
                    isLargeScreen()
                            ? R.layout.sec_preference_intelligent_wifi_item_with_badge_large
                            : R.layout.sec_preference_intelligent_wifi_item_with_badge);
        }
    }

    private void setPreferenceEnabled(Preference preference) {
        int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "wifi_mwips", 0);
        boolean isWifiEnabled = this.mWifiManager.isWifiEnabled();
        if (i >= 2 || !isWifiEnabled) {
            preference.setEnabled(false);
        } else {
            preference.setEnabled(true);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SwitchPreferenceCompat switchPreferenceCompat =
                (SwitchPreferenceCompat) preferenceScreen.findPreference(getPreferenceKey());
        this.mMobileWipsPref = switchPreferenceCompat;
        updateState(switchPreferenceCompat);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Utils.isSupportMobileWips) {
            return (!this.mWifiManager.isWifiEnabled()
                            || Settings.Secure.getInt(
                                            this.mContext.getContentResolver(), "wifi_mwips", 0)
                                    > 1)
                    ? 5
                    : 0;
        }
        return 3;
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
        int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "wifi_mwips", 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "MobileWIPSPrefController:isChecked ->  isEnabled = ", TAG);
        return i == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mWipsObserver);
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("wifi_mwips"), false, this.mWipsObserver);
        this.mContext.registerReceiver(
                this.mReceiver,
                this.mIntentFilter,
                "android.permission.CHANGE_NETWORK_STATE",
                null,
                2);
        updateState(this.mMobileWipsPref);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.Secure.putInt(this.mContext.getContentResolver(), "wifi_mwips", z ? 3 : 2);
        SALogging.insertSALog(z ? 1L : 0L, "WIFI_200", "1343");
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        boolean z = false;
        boolean z2 =
                Settings.Secure.getInt(this.mContext.getContentResolver(), "wifi_mwips", 0) < 2
                        && this.mWifiManager.isWifiEnabled();
        if (WifiBadgeUtils.isNewItemForWips(this.mContext) && z2) {
            z = true;
        }
        preference.setDotVisibility(z);
        setPreferenceEnabled(preference);
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
