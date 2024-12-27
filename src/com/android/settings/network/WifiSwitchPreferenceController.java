package com.android.settings.network;

import android.content.Context;
import android.net.wifi.WifiManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.GenericSwitchController;
import com.android.settings.widget.SwitchWidgetController;
import com.android.settings.wifi.WifiEnabler;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiSwitchPreferenceController extends AbstractPreferenceController
        implements LifecycleObserver {
    boolean mIsChangeWifiStateAllowed;
    public RestrictedSwitchPreference mPreference;
    WifiEnabler mWifiEnabler;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference) preferenceScreen.findPreference("main_toggle_wifi");
        this.mPreference = restrictedSwitchPreference;
        if (restrictedSwitchPreference == null) {
            return;
        }
        WifiManager wifiManager = (WifiManager) this.mContext.getSystemService(WifiManager.class);
        restrictedSwitchPreference.setChecked(
                wifiManager == null ? false : wifiManager.isWifiEnabled());
        if (this.mIsChangeWifiStateAllowed) {
            return;
        }
        this.mPreference.setEnabled(false);
        this.mPreference.setSummary(R.string.not_allowed_by_ent);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "main_toggle_wifi";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler != null) {
            wifiEnabler.pause();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler != null) {
            wifiEnabler.resume();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        RestrictedSwitchPreference restrictedSwitchPreference = this.mPreference;
        if (restrictedSwitchPreference == null || !this.mIsChangeWifiStateAllowed) {
            return;
        }
        Context context = this.mContext;
        GenericSwitchController genericSwitchController = new GenericSwitchController();
        genericSwitchController.mPreference = restrictedSwitchPreference;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        genericSwitchController.mMetricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
        if (featureFactoryImpl2 == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mWifiEnabler =
                new WifiEnabler(
                        context,
                        genericSwitchController,
                        featureFactoryImpl2.getMetricsFeatureProvider());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        SwitchWidgetController switchWidgetController;
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler == null || (switchWidgetController = wifiEnabler.mSwitchWidget) == null) {
            return;
        }
        if (wifiEnabler.mListeningToOnSwitchChange) {
            switchWidgetController.stopListening();
            wifiEnabler.mListeningToOnSwitchChange = false;
        }
        wifiEnabler.mSwitchWidget.teardownView();
    }
}
