package com.android.settings.connecteddevice.usb;

import android.content.Context;
import android.os.UserHandle;

import com.android.settings.R;
import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConnectedUsbDeviceUpdater {
    public final DevicePreferenceCallback mDevicePreferenceCallback;
    public final DashboardFragment mFragment;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    UsbConnectionBroadcastReceiver.UsbConnectionListener mUsbConnectionListener =
            new ConnectedUsbDeviceUpdater$$ExternalSyntheticLambda1(this);
    RestrictedPreference mUsbPreference;
    UsbConnectionBroadcastReceiver mUsbReceiver;

    public ConnectedUsbDeviceUpdater(
            Context context,
            DashboardFragment dashboardFragment,
            DevicePreferenceCallback devicePreferenceCallback,
            UsbBackend usbBackend) {
        this.mFragment = dashboardFragment;
        this.mDevicePreferenceCallback = devicePreferenceCallback;
        this.mUsbReceiver =
                new UsbConnectionBroadcastReceiver(
                        context, this.mUsbConnectionListener, usbBackend);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    public final void initUsbPreference(Context context) {
        RestrictedPreference restrictedPreference = new RestrictedPreference(context, null);
        this.mUsbPreference = restrictedPreference;
        restrictedPreference.setTitle(R.string.usb_pref);
        this.mUsbPreference.setIcon(R.drawable.ic_usb);
        this.mUsbPreference.setKey("connected_usb");
        this.mUsbPreference.setDisabledByAdmin(
                RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(
                        context, UserHandle.myUserId()));
        this.mUsbPreference.setOnPreferenceClickListener(
                new ConnectedUsbDeviceUpdater$$ExternalSyntheticLambda1(this));
        this.mUsbReceiver.register();
    }

    public final void registerCallback() {
        this.mUsbReceiver.register();
    }

    public final void unregisterCallback() {
        UsbConnectionBroadcastReceiver usbConnectionBroadcastReceiver = this.mUsbReceiver;
        if (usbConnectionBroadcastReceiver.mListeningToUsbEvents) {
            usbConnectionBroadcastReceiver.mContext.unregisterReceiver(
                    usbConnectionBroadcastReceiver);
            usbConnectionBroadcastReceiver.mListeningToUsbEvents = false;
        }
    }
}
