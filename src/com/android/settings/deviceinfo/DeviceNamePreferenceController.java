package com.android.settings.deviceinfo;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannedString;

import androidx.fragment.app.FragmentManagerImpl;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.BluetoothLengthDeviceNameFilter;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.deviceinfo.aboutphone.DeviceNameWarningDialog;
import com.android.settings.deviceinfo.aboutphone.MyDeviceInfoFragment;
import com.android.settings.widget.ValidatedEditTextPreference;
import com.android.settings.wifi.tether.WifiDeviceNameTextValidator;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DeviceNamePreferenceController extends BasePreferenceController
        implements ValidatedEditTextPreference.Validator,
                Preference.OnPreferenceChangeListener,
                LifecycleObserver,
                OnSaveInstanceState,
                OnCreate {
    private static final String KEY_PENDING_DEVICE_NAME = "key_pending_device_name";
    static final int RES_SHOW_DEVICE_NAME_BOOL = 2131034163;
    private final BluetoothAdapter mBluetoothAdapter;
    private String mDeviceName;
    private DeviceNamePreferenceHost mHost;
    private String mPendingDeviceName;
    private ValidatedEditTextPreference mPreference;
    private final WifiDeviceNameTextValidator mWifiDeviceNameTextValidator;
    protected WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DeviceNamePreferenceHost {}

    public DeviceNamePreferenceController(Context context, String str) {
        super(context, str);
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mWifiDeviceNameTextValidator = new WifiDeviceNameTextValidator();
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        initializeDeviceName();
    }

    private static final String getFilteredBluetoothString(String str) {
        CharSequence filter =
                new BluetoothLengthDeviceNameFilter()
                        .filter(
                                str,
                                0,
                                str.length(),
                                new SpannedString(ApnSettings.MVNO_NONE),
                                0,
                                0);
        return filter == null ? str : filter.toString();
    }

    private void initializeDeviceName() {
        String string =
                Settings.Global.getString(this.mContext.getContentResolver(), "device_name");
        this.mDeviceName = string;
        if (string == null) {
            this.mDeviceName = Build.MODEL;
        }
    }

    private void setBluetoothDeviceName(String str) {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null) {
            bluetoothAdapter.setName(getFilteredBluetoothString(str));
        }
    }

    private void setDeviceName(String str) {
        this.mDeviceName = str;
        setSettingsGlobalDeviceName(str);
        setBluetoothDeviceName(str);
        setTetherSsidName(str);
        this.mPreference.setSummary(getSummary());
    }

    private void setSettingsGlobalDeviceName(String str) {
        Settings.Global.putString(this.mContext.getContentResolver(), "device_name", str);
    }

    private void setTetherSsidName(String str) {
        this.mWifiManager.setSoftApConfiguration(
                new SoftApConfiguration.Builder(this.mWifiManager.getSoftApConfiguration())
                        .setSsid(str)
                        .build());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (ValidatedEditTextPreference) preferenceScreen.findPreference(getPreferenceKey());
        CharSequence summary = getSummary();
        this.mPreference.setSummary(summary);
        this.mPreference.setText(summary.toString());
        this.mPreference.mValidator = this;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_device_name) ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mDeviceName;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.widget.ValidatedEditTextPreference.Validator
    public boolean isTextValid(String str) {
        return this.mWifiDeviceNameTextValidator.isTextValid(str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.mPendingDeviceName = bundle.getString(KEY_PENDING_DEVICE_NAME, null);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        this.mPendingDeviceName = (String) obj;
        DeviceNamePreferenceHost deviceNamePreferenceHost = this.mHost;
        if (deviceNamePreferenceHost == null) {
            return true;
        }
        MyDeviceInfoFragment myDeviceInfoFragment = (MyDeviceInfoFragment) deviceNamePreferenceHost;
        FragmentManagerImpl supportFragmentManager =
                myDeviceInfoFragment.getActivity().getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag("DeviceNameWarningDlg") != null) {
            return true;
        }
        DeviceNameWarningDialog deviceNameWarningDialog = new DeviceNameWarningDialog();
        deviceNameWarningDialog.setTargetFragment(myDeviceInfoFragment, 0);
        deviceNameWarningDialog.show(supportFragmentManager, "DeviceNameWarningDlg");
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnSaveInstanceState
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(KEY_PENDING_DEVICE_NAME, this.mPendingDeviceName);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setHost(DeviceNamePreferenceHost deviceNamePreferenceHost) {
        this.mHost = deviceNamePreferenceHost;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateDeviceName(boolean z) {
        String str;
        if (!z || (str = this.mPendingDeviceName) == null) {
            this.mPreference.setText(getSummary().toString());
        } else {
            setDeviceName(str);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
