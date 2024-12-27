package com.samsung.android.settings.bluetooth.fragment;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.settingslib.bluetooth.smep.SppByteUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothAboutFragment extends RestrictedDashboardFragment
        implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    public CachedBluetoothDevice mCachedDevice;
    public BluetoothDevice mDevice;
    public String mDeviceAddress;
    public LocalBluetoothManager mManager;
    public Preference mModelPreference;
    public final AnonymousClass1 mReceiver;
    public boolean mReceiverRegistered;
    public Preference mSerialPreference;
    public Preference mSwInfoPreference;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.bluetooth.fragment.BluetoothAboutFragment$1] */
    public BluetoothAboutFragment() {
        super("no_config_bluetooth");
        this.mReceiverRegistered = false;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.bluetooth.fragment.BluetoothAboutFragment.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (action != null) {
                            Log.d("BluetoothAboutFragment", action);
                        }
                        if (!"com.samsung.bluetooth.device.action.SMEP_CONNECTION_STATE_CHANGED"
                                .equals(action)) {
                            if ("com.samsung.bluetooth.device.action.META_DATA_CHANGED"
                                    .equals(action)) {
                                if (BluetoothAboutFragment.this.mDevice.equals(
                                        (BluetoothDevice)
                                                intent.getParcelableExtra(
                                                        "android.bluetooth.device.extra.DEVICE"))) {
                                    BluetoothAboutFragment.this.initPreference$2$1();
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if (BluetoothAboutFragment.this.mDevice.equals(
                                (BluetoothDevice)
                                        intent.getParcelableExtra(
                                                "android.bluetooth.device.extra.DEVICE"))) {
                            int intExtra =
                                    intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                            if (intExtra == 2) {
                                BluetoothAboutFragment.this.initPreference$2$1();
                            } else if (intExtra == 0) {
                                BluetoothAboutFragment.this.finish();
                            }
                        }
                    }
                };
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BluetoothAboutFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_bluetooth_about_fragment;
    }

    public final void initPreference$2$1() {
        this.mSwInfoPreference = findPreference("key_pref_sw_information");
        this.mSerialPreference = findPreference("key_pref_serial");
        this.mModelPreference = findPreference("key_pref_model");
        try {
            byte[] semGetMetadata =
                    this.mDevice.semGetMetadata(
                            SppByteUtil.intToBytes(SmepTag.STATE_BINARY_VERSION_STR.getTag()));
            int length = semGetMetadata.length - 3;
            byte[] bArr = new byte[length];
            System.arraycopy(semGetMetadata, 3, bArr, 0, length);
            this.mSwInfoPreference.setSummary(new String(bArr));
            Log.d("BluetoothAboutFragment", "version data = ".concat(new String(bArr)));
            byte[] semGetMetadata2 =
                    this.mDevice.semGetMetadata(
                            SppByteUtil.intToBytes(SmepTag.STATE_MODEL_STR.getTag()));
            int length2 = semGetMetadata2.length - 3;
            byte[] bArr2 = new byte[length2];
            System.arraycopy(semGetMetadata2, 3, bArr2, 0, length2);
            Log.d("BluetoothAboutFragment", "model data = ".concat(new String(bArr2)));
            this.mModelPreference.setSummary(new String(bArr2));
            byte[] semGetMetadata3 =
                    this.mDevice.semGetMetadata(
                            SppByteUtil.intToBytes(SmepTag.CUSTOM_SERIAL.getTag()));
            int length3 = semGetMetadata3.length - 3;
            byte[] bArr3 = new byte[length3];
            System.arraycopy(semGetMetadata3, 3, bArr3, 0, length3);
            String str = new String(bArr3);
            String str2 =
                    getString(R.string.sec_bluetooth_about_l)
                            + " : "
                            + str.substring(0, str.length() / 2)
                            + "\n"
                            + getString(R.string.sec_bluetooth_about_r)
                            + " : "
                            + str.substring(str.length() / 2, str.length());
            this.mSerialPreference.setSummary(new String(str2));
            Log.d("BluetoothAboutFragment", "serial data = " + str2);
        } catch (RuntimeException e) {
            Log.e("BluetoothAboutFragment", "initPreference:  can't get metadata", e);
            finish();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        boolean z;
        CachedBluetoothDevice findDevice;
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        this.mManager = localBluetoothManager;
        boolean z2 = true;
        if (localBluetoothManager == null) {
            Log.w("BluetoothAboutFragment", "Activity started without bluetooth is not ready");
            z = true;
        } else {
            z = false;
        }
        String string = getArguments().getString("device_address");
        this.mDeviceAddress = string;
        if (string == null) {
            Log.w(
                    "BluetoothAboutFragment",
                    "Activity started without a remote bluetooth device is not ready");
            z = true;
        } else {
            LocalBluetoothManager localBluetoothManager2 = this.mManager;
            if (localBluetoothManager2 == null) {
                findDevice = null;
            } else {
                findDevice =
                        this.mManager.mCachedDeviceManager.findDevice(
                                localBluetoothManager2.mLocalAdapter.mAdapter.getRemoteDevice(
                                        string));
            }
            this.mCachedDevice = findDevice;
        }
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice == null) {
            MainClear$$ExternalSyntheticOutline0.m$1(
                    new StringBuilder("onAttach() CachedDevice is null. address = "),
                    this.mDeviceAddress,
                    "BluetoothAboutFragment");
        } else {
            this.mDevice = cachedBluetoothDevice.mDevice;
            z2 = z;
        }
        super.onAttach(context);
        if (z2) {
            finish();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (!this.mReceiverRegistered || this.mReceiver == null) {
            return;
        }
        getActivity().getApplicationContext().unregisterReceiver(this.mReceiver);
        this.mReceiverRegistered = false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Log.d("BluetoothAboutFragment", "onPreferenceChange :: ");
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        Log.d("BluetoothAboutFragment", "onPreferenceClick :: ");
        return false;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("BluetoothAboutFragment", "onResume");
        if (!this.mReceiverRegistered) {
            getActivity()
                    .getApplicationContext()
                    .registerReceiver(
                            this.mReceiver,
                            ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                                    "com.samsung.bluetooth.device.action.SMEP_CONNECTION_STATE_CHANGED",
                                    "com.samsung.bluetooth.device.action.META_DATA_CHANGED"));
            this.mReceiverRegistered = true;
        }
        if (this.mCachedDevice.isConnected()) {
            initPreference$2$1();
        } else {
            Log.d("BluetoothAboutFragment", "onResume: disconnected");
            finish();
        }
    }
}
