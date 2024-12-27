package com.android.settings.bluetooth;

import android.content.Context;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BluetoothDetailsController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                CachedBluetoothDevice.Callback,
                LifecycleObserver,
                OnPause,
                OnResume,
                OnDestroy {
    public final CachedBluetoothDevice mCachedDevice;
    public final Context mContext;
    public final PreferenceFragmentCompat mFragment;
    public boolean mIsCalledDestroy;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;

    public BluetoothDetailsController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context);
        this.mIsCalledDestroy = false;
        this.mContext = context;
        this.mFragment = preferenceFragmentCompat;
        this.mCachedDevice = cachedBluetoothDevice;
        lifecycle.addObserver(this);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        init(preferenceScreen);
        super.displayPreference(preferenceScreen);
    }

    public abstract void init(PreferenceScreen preferenceScreen);

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        this.mIsCalledDestroy = true;
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public void onDeviceAttributesChanged() {
        refresh();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mCachedDevice.unregisterCallback(this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mCachedDevice.registerCallback(this);
        refresh();
    }

    public void onServiceConnected() {
        refresh();
    }

    public void onServiceDisconnected() {
        refresh();
    }

    public abstract void refresh();
}
