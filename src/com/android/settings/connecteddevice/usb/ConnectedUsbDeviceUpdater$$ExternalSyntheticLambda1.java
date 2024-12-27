package com.android.settings.connecteddevice.usb;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.RestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ConnectedUsbDeviceUpdater$$ExternalSyntheticLambda1
        implements UsbConnectionBroadcastReceiver.UsbConnectionListener,
                Preference.OnPreferenceClickListener {
    public final /* synthetic */ ConnectedUsbDeviceUpdater f$0;

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        ConnectedUsbDeviceUpdater connectedUsbDeviceUpdater = this.f$0;
        SettingsMetricsFeatureProvider settingsMetricsFeatureProvider =
                connectedUsbDeviceUpdater.mMetricsFeatureProvider;
        DashboardFragment dashboardFragment = connectedUsbDeviceUpdater.mFragment;
        settingsMetricsFeatureProvider.logClickedPreference(
                preference, dashboardFragment.getMetricsCategory());
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(dashboardFragment.getContext());
        String name = UsbDetailsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.usb_preference, null);
        launchRequest.mSourceMetricsCategory = dashboardFragment.getMetricsCategory();
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.connecteddevice.usb.UsbConnectionBroadcastReceiver.UsbConnectionListener
    public void onUsbConnectionChanged(boolean z, long j, int i, int i2, boolean z2) {
        int i3;
        ConnectedUsbDeviceUpdater connectedUsbDeviceUpdater = this.f$0;
        DevicePreferenceCallback devicePreferenceCallback =
                connectedUsbDeviceUpdater.mDevicePreferenceCallback;
        if (!z) {
            devicePreferenceCallback.onDeviceRemoved(connectedUsbDeviceUpdater.mUsbPreference);
            return;
        }
        RestrictedPreference restrictedPreference = connectedUsbDeviceUpdater.mUsbPreference;
        long j2 = i2 == 2 ? j : 0L;
        if (i != 1) {
            i3 = R.string.usb_summary_charging_only;
            if (i == 2) {
                if (j2 == 4) {
                    i3 = R.string.usb_summary_file_transfers;
                } else if (j2 == 32) {
                    i3 = R.string.usb_summary_tether;
                } else if (j2 == 16) {
                    i3 = R.string.usb_summary_photo_transfers;
                } else if (j2 == 8) {
                    i3 = R.string.usb_summary_MIDI;
                } else if (j2 == 128) {
                    i3 = R.string.usb_summary_UVC;
                }
            }
        } else {
            i3 =
                    j2 == 4
                            ? R.string.usb_summary_file_transfers_power
                            : j2 == 32
                                    ? R.string.usb_summary_tether_power
                                    : j2 == 16
                                            ? R.string.usb_summary_photo_transfers_power
                                            : j2 == 8
                                                    ? R.string.usb_summary_MIDI_power
                                                    : j2 == 128
                                                            ? R.string.usb_summary_UVC_power
                                                            : R.string.usb_summary_power_only;
        }
        restrictedPreference.setSummary(i3);
        devicePreferenceCallback.onDeviceAdded(connectedUsbDeviceUpdater.mUsbPreference);
    }
}
