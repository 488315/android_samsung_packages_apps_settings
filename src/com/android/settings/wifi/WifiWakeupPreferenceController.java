package com.android.settings.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.WifiManager;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.utils.AnnotationSpan;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.gls.GlsIntent;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiWakeupPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnPause, OnResume {
    private static final String KEY_ENABLE_WIFI_WAKEUP = "enable_wifi_wakeup";
    private static final String TAG = "WifiWakeupPrefController";
    private Fragment mFragment;
    private final IntentFilter mLocationFilter;
    LocationManager mLocationManager;
    private final BroadcastReceiver mLocationReceiver;
    TwoStatePreference mPreference;
    private SemWifiManager mSemWifiManager;
    WifiManager mWifiManager;

    public WifiWakeupPreferenceController(Context context) {
        super(context, KEY_ENABLE_WIFI_WAKEUP);
        this.mLocationReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.wifi.WifiWakeupPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        WifiWakeupPreferenceController wifiWakeupPreferenceController =
                                WifiWakeupPreferenceController.this;
                        wifiWakeupPreferenceController.updateState(
                                wifiWakeupPreferenceController.mPreference);
                    }
                };
        this.mLocationFilter = new IntentFilter("android.location.MODE_CHANGED");
        this.mLocationManager =
                (LocationManager) context.getSystemService(GlsIntent.Extras.EXTRA_LOCATION);
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    private boolean getLocationEnabled() {
        return this.mLocationManager.isLocationEnabled();
    }

    private boolean getWifiScanningEnabled() {
        return this.mWifiManager.isScanAlwaysAvailable();
    }

    private boolean getWifiWakeupEnabled() {
        return this.mWifiManager.isAutoWakeupEnabled();
    }

    private void setWifiWakeupEnabled(boolean z) {
        this.mWifiManager.setAutoWakeupEnabled(z);
    }

    private void showScanningDialog() {
        WifiScanningRequiredFragment wifiScanningRequiredFragment =
                new WifiScanningRequiredFragment();
        wifiScanningRequiredFragment.setTargetFragment(this.mFragment, 600);
        wifiScanningRequiredFragment.show(this.mFragment.getFragmentManager(), TAG);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (TwoStatePreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mSemWifiManager.isSupportedAutoWifi() && !Utils.isWifiOnly(this.mContext)) {
            return 3;
        }
        if (this.mFragment == null) {
            return (getLocationEnabled() && getWifiScanningEnabled()) ? 0 : 5;
        }
        return 0;
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

    public CharSequence getNoLocationSummary() {
        return AnnotationSpan.linkify(
                this.mContext.getText(R.string.wifi_wakeup_summary_no_location),
                new AnnotationSpan.LinkInfo(null));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_network;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return !getLocationEnabled()
                ? getNoLocationSummary()
                : this.mContext.getText(R.string.wifi_wakeup_summary);
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
        return getWifiWakeupEnabled() && getWifiScanningEnabled() && getLocationEnabled();
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
        if (i == 600 && getLocationEnabled() && getWifiScanningEnabled()) {
            setWifiWakeupEnabled(true);
            updateState(this.mPreference);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.unregisterReceiver(this.mLocationReceiver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mContext.registerReceiver(this.mLocationReceiver, this.mLocationFilter);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (z) {
            if (!getLocationEnabled()) {
                if (this.mFragment == null) {
                    throw new IllegalStateException("No fragment to start activity");
                }
                this.mFragment.startActivityForResult(
                        new Intent("android.settings.LOCATION_SOURCE_SETTINGS")
                                .setPackage(this.mContext.getPackageName()),
                        600);
                return false;
            }
            if (!getWifiScanningEnabled()) {
                showScanningDialog();
                return false;
            }
        }
        setWifiWakeupEnabled(z);
        return true;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
