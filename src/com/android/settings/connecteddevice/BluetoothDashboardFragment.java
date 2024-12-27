package com.android.settings.connecteddevice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.bluetooth.BluetoothDeviceRenamePreferenceController;
import com.android.settings.bluetooth.BluetoothSwitchPreferenceController;
import com.android.settings.bluetooth.RestrictionUtils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.password.PasswordUtils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.MainSwitchBarController;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothDashboardFragment extends DashboardFragment {
    public static final boolean DEBUG = Log.isLoggable("BluetoothDashboardFrag", 3);
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.bluetooth_screen);
    public BluetoothSwitchPreferenceController mController;
    public FooterPreference mFooterPreference;
    public SettingsMainSwitchBar mSwitchBar;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BluetoothDashboardFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1390;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.bluetooth_screen;
    }

    public boolean isAlwaysDiscoverable(String str, String str2) {
        if (TextUtils.equals("com.android.settings.SEARCH_RESULT_TRAMPOLINE", str2)) {
            return false;
        }
        return TextUtils.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, str)
                || TextUtils.equals("com.android.systemui", str);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        String callingAppPackageName =
                PasswordUtils.getCallingAppPackageName(getActivity().getActivityToken());
        String action = getIntent() != null ? getIntent().getAction() : ApnSettings.MVNO_NONE;
        if (DEBUG) {
            Log.d(
                    "BluetoothDashboardFrag",
                    "onActivityCreated() calling package name is : "
                            + callingAppPackageName
                            + ", action : "
                            + action);
        }
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.setTitle(
                getContext().getString(R.string.bluetooth_main_switch_title));
        BluetoothSwitchPreferenceController bluetoothSwitchPreferenceController =
                new BluetoothSwitchPreferenceController(
                        settingsActivity,
                        new RestrictionUtils(),
                        new MainSwitchBarController(this.mSwitchBar),
                        this.mFooterPreference);
        this.mController = bluetoothSwitchPreferenceController;
        bluetoothSwitchPreferenceController.mIsAlwaysDiscoverable =
                isAlwaysDiscoverable(callingAppPackageName, action);
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        if (settingsLifecycle != null) {
            settingsLifecycle.addObserver(this.mController);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((BluetoothDeviceRenamePreferenceController)
                        use(BluetoothDeviceRenamePreferenceController.class))
                .setFragment(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFooterPreference = (FooterPreference) findPreference("bluetooth_screen_footer");
        finish();
        startActivity(new Intent("android.settings.BLUETOOTH_SETTINGS"));
    }
}
