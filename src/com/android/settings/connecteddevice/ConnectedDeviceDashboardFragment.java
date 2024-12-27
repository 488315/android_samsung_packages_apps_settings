package com.android.settings.connecteddevice;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.Uri;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.slices.SlicePreferenceController;
import com.android.settingslib.bluetooth.HearingAidStatsLogUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import kotlin.jvm.internal.Intrinsics;

import java.util.HashMap;
import java.util.LinkedList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConnectedDeviceDashboardFragment extends DashboardFragment {
    static final String KEY_AVAILABLE_DEVICES = "available_device_list";
    static final String KEY_CONNECTED_DEVICES = "connected_device_list";
    public static final boolean DEBUG = Log.isLoggable("ConnectedDeviceFrag", 3);
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.connected_devices);

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ConnectedDeviceFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 747;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.connected_devices;
    }

    public boolean isAlwaysDiscoverable(String str, String str2) {
        if (TextUtils.equals("com.android.settings.SEARCH_RESULT_TRAMPOLINE", str2)) {
            return false;
        }
        return TextUtils.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, str)
                || TextUtils.equals("com.android.systemui", str);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        String str;
        super.onAttach(context);
        boolean z = DeviceConfig.getBoolean("settings_ui", "bt_near_by_suggestion_enabled", true);
        String initialCallingPackage =
                ((SettingsActivity) getActivity()).getInitialCallingPackage();
        String action = getIntent() != null ? getIntent().getAction() : ApnSettings.MVNO_NONE;
        if (DEBUG) {
            Log.d(
                    "ConnectedDeviceFrag",
                    "onAttach() calling package name is : "
                            + initialCallingPackage
                            + ", action : "
                            + action);
        }
        BluetoothAdapter.getDefaultAdapter();
        ((AvailableMediaDeviceGroupController) use(AvailableMediaDeviceGroupController.class))
                .init(this);
        ((ConnectedDeviceGroupController) use(ConnectedDeviceGroupController.class)).init(this);
        ((PreviouslyConnectedDevicePreferenceController)
                        use(PreviouslyConnectedDevicePreferenceController.class))
                .init(this);
        ((SlicePreferenceController) use(SlicePreferenceController.class))
                .setSliceUri(
                        z ? Uri.parse(getString(R.string.config_nearby_devices_slice_uri)) : null);
        ((DiscoverableFooterPreferenceController) use(DiscoverableFooterPreferenceController.class))
                .setAlwaysDiscoverable(isAlwaysDiscoverable(initialCallingPackage, action));
        HashMap hashMap = HearingAidStatsLogUtils.sDeviceAddressToBondEntryMap;
        synchronized (HearingAidStatsLogUtils.class) {
            LinkedList history = HearingAidStatsLogUtils.getHistory(context, 1);
            if (history == null || history.size() < 7) {
                LinkedList history2 = HearingAidStatsLogUtils.getHistory(context, 3);
                if (history2 == null || history2.size() < 7) {
                    str = ApnSettings.MVNO_NONE;
                } else {
                    LinkedList history3 = HearingAidStatsLogUtils.getHistory(context, 2);
                    str =
                            (history3 == null || history3.size() < 1)
                                    ? "A11yHearingDevicesUser"
                                    : "A11yNewHearingDevicesUser";
                }
            } else {
                LinkedList history4 = HearingAidStatsLogUtils.getHistory(context, 0);
                str =
                        (history4 == null || history4.size() < 1)
                                ? "A11yHearingAidsUser"
                                : "A11yNewHearingAidsUser";
            }
        }
        if (str.isEmpty()) {
            return;
        }
        if (FeatureFactoryImpl._factory == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        Intrinsics.checkNotNullParameter(context, "context");
    }
}
