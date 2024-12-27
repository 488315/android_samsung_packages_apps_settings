package com.samsung.android.settings.connection.tether;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.TetheringManager;
import android.os.Bundle;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.wifi.mobileap.WifiApSwitchEnabler;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecWifiTetherPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnStart, OnPause, OnStop, OnDestroy {
    static final String TAG = "SecWifiTetherPreferenceController";
    private SecTetherSettings mFragment;
    private SemWifiManager mSemWifiManager;
    private TetheringManager mTm;
    private WifiApSwitchEnabler mWifiApSwitchEnabler;
    private SecSwitchPreferenceScreen mWifiApSwitchPreference;

    public SecWifiTetherPreferenceController(Context context, String str) {
        super(context, str);
        this.mTm = (TetheringManager) this.mContext.getSystemService("tethering");
        this.mSemWifiManager =
                (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        Utils.initMHSFeature(this.mContext);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mWifiApSwitchPreference = secSwitchPreferenceScreen;
        boolean isWifiApEnabled = this.mSemWifiManager.isWifiApEnabled();
        secSwitchPreferenceScreen.getClass();
        SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, isWifiApEnabled);
        WifiApSwitchEnabler wifiApSwitchEnabler =
                new WifiApSwitchEnabler(this.mFragment.getActivity(), this.mWifiApSwitchPreference);
        this.mWifiApSwitchEnabler = wifiApSwitchEnabler;
        this.mWifiApSwitchPreference.setOnPreferenceChangeListener(wifiApSwitchEnabler);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0060 A[RETURN] */
    @Override // com.android.settings.core.BasePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAvailabilityStatus() {
        /*
            r8 = this;
            android.net.TetheringManager r0 = r8.mTm
            java.lang.String[] r0 = r0.getTetherableWifiRegexs()
            int r0 = r0.length
            r1 = 1
            r2 = 0
            if (r0 == 0) goto Ld
            r0 = r1
            goto Le
        Ld:
            r0 = r2
        Le:
            com.samsung.android.feature.SemCscFeature r3 = com.samsung.android.feature.SemCscFeature.getInstance()
            java.lang.String r4 = "CscFeature_Common_EnableSprintExtension"
            boolean r3 = r3.getBoolean(r4)
            if (r3 == 0) goto L43
            java.lang.String r3 = "persist.sys.tether_data"
            r4 = -1
            int r3 = android.os.SystemProperties.getInt(r3, r4)
            java.lang.String r5 = com.samsung.android.settings.connection.tether.SecWifiTetherPreferenceController.TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "mTetheredData : "
            r6.<init>(r7)
            r6.append(r3)
            java.lang.String r3 = r6.toString()
            android.util.Log.d(r5, r3)
            java.lang.String r3 = "persist.sys.tether_data_wifi"
            int r3 = android.os.SystemProperties.getInt(r3, r4)
            if (r3 == r4) goto L43
            if (r3 <= 0) goto L41
            goto L44
        L41:
            r1 = r2
            goto L44
        L43:
            r1 = r0
        L44:
            android.content.Context r8 = r8.mContext
            boolean r8 = com.android.settingslib.Utils.isWifiOnly(r8)
            if (r8 == 0) goto L54
            java.lang.String r8 = com.samsung.android.settings.connection.tether.SecWifiTetherPreferenceController.TAG
            java.lang.String r0 = "This model is Wifi Only"
            android.util.Log.d(r8, r0)
            r1 = r2
        L54:
            java.lang.String r8 = com.samsung.android.settings.connection.tether.SecWifiTetherPreferenceController.TAG
            java.lang.String r0 = "wifiAvailable : "
            com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0.m(r0, r8, r1)
            if (r1 != 0) goto L60
            r8 = 4
            return r8
        L60:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.connection.tether.SecWifiTetherPreferenceController.getAvailabilityStatus():int");
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
        return 0;
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
        return this.mSemWifiManager.isWifiApEnabled();
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

    public void onActivityResult(int i, int i2) {
        WifiApSwitchEnabler wifiApSwitchEnabler;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i2, "onActivityResult() - ", TAG);
        if (i == 0) {
            this.mWifiApSwitchEnabler.onActivityResult(i, i2, null);
        } else {
            if (i != 1 || (wifiApSwitchEnabler = this.mWifiApSwitchEnabler) == null) {
                return;
            }
            wifiApSwitchEnabler.enableTethering(true);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mWifiApSwitchEnabler.onDestroy();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mWifiApSwitchEnabler.onPause();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mWifiApSwitchEnabler.onResume();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mWifiApSwitchEnabler.onStart();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mWifiApSwitchEnabler.onStop();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setChecked:", TAG, z);
        return false;
    }

    public void setHost(SecTetherSettings secTetherSettings) {
        this.mFragment = secTetherSettings;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public void onActivityCreated(Bundle bundle) {}

    public void onSaveInstanceState(Bundle bundle) {}
}
