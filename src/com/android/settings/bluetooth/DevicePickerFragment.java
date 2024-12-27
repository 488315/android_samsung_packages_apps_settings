package com.android.settings.bluetooth;

import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HeadsetProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;

import com.samsung.android.settings.bluetooth.SecBluetoothDevicePreference;
import com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment;
import com.samsung.android.settings.bluetooth.adapter.BluetoothAvailableListAdapter;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DevicePickerFragment extends SecDeviceListPreferenceFragment {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public boolean mCallFromHeadset;
    String mCallingAppPackageName;
    Context mContext;
    public int mFilterType;
    String mLaunchClass;
    String mLaunchPackage;
    public boolean mNeedAuth;
    public MenuItem mProgressItem;
    public MenuItem mScan;
    public String mScreenId;
    public boolean mStartScanOnResume;

    public DevicePickerFragment() {
        super(null);
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment
    public final void addPreferencesForActivity() {
        int i;
        ActionBar supportActionBar;
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null
                && (supportActionBar = appCompatActivity.getSupportActionBar()) != null) {
            supportActionBar.setDisplayOptions(12, 12);
            if (DevicePickerActivity.mMyPlacesCalled) {
                appCompatActivity.setTitle(R.string.bluetooth_settings);
            } else {
                appCompatActivity.setTitle(R.string.device_picker);
            }
            supportActionBar.setHomeButtonEnabled();
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getActivity().getIntent();
        this.mNeedAuth =
                intent.getBooleanExtra("android.bluetooth.devicepicker.extra.NEED_AUTH", false);
        this.mListController.mGuideView.setText(
                Html.fromHtml(
                                getResources()
                                        .getString(R.string.bluetooth_check_visibility_help_text))
                        .toString());
        if (DevicePickerActivity.mMyPlacesCalled) {
            this.mFilterType = 20;
        } else {
            this.mFilterType =
                    intent.getIntExtra("android.bluetooth.devicepicker.extra.FILTER_TYPE", 0);
        }
        BluetoothAvailableListAdapter bluetoothAvailableListAdapter =
                this.mListController.mAvailableListAdapter;
        setFilter(this.mFilterType);
        this.mSelectedAdapter = bluetoothAvailableListAdapter;
        if (this.mStartScanOnResume) {
            this.mLocalManager.mCachedDeviceManager.clearNonBondedDevices();
        }
        addCachedDevices$1();
        this.mListController.setVisibleCastGroup(8);
        this.mListController.setVisiblePairedGroup(8);
        int i2 = this.mFilterType;
        if (i2 == 5 || i2 == 6 || i2 == 105 || i2 == 106) {
            this.mCallFromHeadset = true;
        } else {
            this.mCallFromHeadset = false;
        }
        this.mLaunchPackage =
                intent.getStringExtra("android.bluetooth.devicepicker.extra.LAUNCH_PACKAGE");
        this.mLaunchClass =
                intent.getStringExtra(
                        "android.bluetooth.devicepicker.extra.DEVICE_PICKER_LAUNCH_CLASS");
        String stringExtra =
                intent.getStringExtra("android.bluetooth.devicepicker.extra.CALLING_APP_PACKAGE");
        this.mCallingAppPackageName = stringExtra;
        if (!TextUtils.equals(stringExtra, this.mLaunchPackage)) {
            Log.w(
                    "DevicePickerFragment",
                    "sendDevicePickedIntent() launch package name is not equivalent to calling"
                        + " package name!");
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getSystemService("device_policy");
        if (devicePolicyManager.semGetAllowBluetoothMode(null) == 0) {
            Toast.makeText(getActivity(), R.string.bluetooth_security_mode_disable, 1).show();
            finish();
        } else {
            if (devicePolicyManager.semGetAllowBluetoothMode(null) != 1
                    || (i = this.mFilterType) == 5
                    || i == 6
                    || i == 105
                    || i == 106) {
                return;
            }
            Toast.makeText(getActivity(), R.string.bluetooth_security_mode_handsfree, 1).show();
            finish();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 25;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.device_picker;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "onBluetoothStateChanged :: bluetoothState = ", "DevicePickerFragment");
        if (i == 10) {
            finish();
        }
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        setHasOptionsMenu(true);
        this.mStartScanOnResume = bundle == null;
        this.mScreenId = getString(R.string.screen_device_picker);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.sec_bt_picker_progress, menu);
        MenuItem findItem = menu.findItem(R.id.progress);
        this.mProgressItem = findItem;
        findItem.setActionView(R.layout.sec_bluetooth_progressbar);
        boolean isDiscovering = this.mLocalAdapter.mAdapter.isDiscovering();
        MenuItem add =
                menu.add(
                        0,
                        1,
                        0,
                        isDiscovering
                                ? R.string.service_stop
                                : R.string.bluetooth_scan_for_devices);
        this.mScan = add;
        add.setShowAsAction(2);
        this.mScan.setEnabled(true);
        if (isDiscovering) {
            this.mProgressItem.setEnabled(true);
            this.mProgressItem.setActionView(R.layout.sec_bluetooth_progressbar);
        } else {
            this.mProgressItem.setEnabled(false);
            this.mProgressItem.setActionView((View) null);
            this.mProgressItem.setVisible(false);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        StringBuilder sb = new StringBuilder("onDeviceBondStateChanged :: device = ");
        sb.append(cachedBluetoothDevice.getNameForLog());
        sb.append(", bondState = ");
        sb.append(i);
        sb.append(", mCallFromHeadset");
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                sb, this.mCallFromHeadset, "DevicePickerFragment");
        if (i == 12) {
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            if (!bluetoothDevice.equals(this.mSelectedDevice) || this.mCallFromHeadset) {
                return;
            }
            sendDevicePickedIntent(bluetoothDevice);
            finish();
        }
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment
    public final void onDevicePreferenceClick(
            SecBluetoothDevicePreference secBluetoothDevicePreference) {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("onDevicePreferenceClick :: mSelectedDevice = ");
            sb.append(this.mSelectedDevice.getName());
            sb.append(", mCallFromHeadset = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    sb, this.mCallFromHeadset, "DevicePickerFragment");
        }
        FragmentActivity activity = getActivity();
        String address = this.mSelectedDevice.getAddress();
        SharedPreferences.Editor edit =
                activity.getSharedPreferences("bluetooth_settings", 0).edit();
        edit.putString("last_selected_device", address);
        edit.putLong("last_selected_device_time", System.currentTimeMillis());
        edit.apply();
        SALogging.insertSALog(
                getString(R.string.screen_bluetooth_global),
                getString(R.string.event_bluetooth_bdcc_picker));
        BluetoothClass bluetoothClass =
                secBluetoothDevicePreference.mCachedDevice.mDevice.getBluetoothClass();
        int majorDeviceClass = bluetoothClass != null ? bluetoothClass.getMajorDeviceClass() : 7936;
        SALogging.insertSALog(
                this.mScreenId,
                getString(R.string.event_device_picker_device_list),
                (majorDeviceClass == 256 || majorDeviceClass == 512)
                        ? "Personal Device"
                        : secBluetoothDevicePreference.mCachedDevice.getNameForSAlogging());
        if (DevicePickerActivity.mMyPlacesCalled) {
            if (!secBluetoothDevicePreference.mCachedDevice.isConnected()
                    && secBluetoothDevicePreference.mCachedDevice.mBondState != 12) {
                super.onDevicePreferenceClick(secBluetoothDevicePreference);
                return;
            } else {
                sendDevicePickedIntent(this.mSelectedDevice);
                finish();
                return;
            }
        }
        if (this.mCallFromHeadset) {
            Log.d("DeviceListPreferenceFragment", "inside onDevicePreferenceClickForHeadset");
            CachedBluetoothDevice cachedBluetoothDevice =
                    secBluetoothDevicePreference.mCachedDevice;
            if (cachedBluetoothDevice.mBondState != 11) {
                cachedBluetoothDevice.connect$1();
                return;
            }
            return;
        }
        if (secBluetoothDevicePreference.mCachedDevice.mBondState != 12 && this.mNeedAuth) {
            super.onDevicePreferenceClick(secBluetoothDevicePreference);
        } else {
            sendDevicePickedIntent(this.mSelectedDevice);
            finish();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        String string;
        int itemId = menuItem.getItemId();
        if (itemId != 1) {
            if (itemId != 16908332) {
                return super.onOptionsItemSelected(menuItem);
            }
            SALogging.insertSALog(
                    this.mScreenId, getString(R.string.event_device_picker_navigate_button));
            finish();
            return true;
        }
        if (this.mLocalAdapter.getBluetoothState() == 12) {
            if (this.mLocalAdapter.mAdapter.isDiscovering()) {
                Log.e("DevicePickerFragment", "onOptionsItemSelected :: Stop scanning");
                this.mLocalAdapter.stopScanning();
                string = getString(R.string.detail_bluetooth_scan_stop);
                this.mScanFinishAction = "Button";
            } else {
                Log.e("DevicePickerFragment", "onOptionsItemSelected :: Start scanning");
                this.mLocalAdapter.startScanning(true);
                string = getString(R.string.detail_bluetooth_scan_start);
            }
            SALogging.insertSALog(
                    this.mScreenId, getString(R.string.event_device_picker_scan_button), string);
        }
        return true;
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onProfileStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile,
            int i,
            int i2) {
        StringBuilder sb = new StringBuilder("onProfileStateChanged :: profile = ");
        sb.append(localBluetoothProfile);
        sb.append(", newState = ");
        sb.append(i);
        sb.append(", oldState = ");
        Preference$$ExternalSyntheticOutline0.m(sb, i2, "DevicePickerFragment");
        if (localBluetoothProfile instanceof HeadsetProfile) {
            if (i == 2) {
                int i3 = this.mFilterType;
                if (i3 == 5 || i3 == 105) {
                    finish();
                    return;
                }
                return;
            }
            return;
        }
        if ((localBluetoothProfile instanceof A2dpProfile) && i == 2) {
            int i4 = this.mFilterType;
            if (i4 == 6 || i4 == 106) {
                finish();
            }
        }
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SALogging.insertSALog(this.mScreenId);
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {
        super.onScanningStateChanged(z);
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "onScanningStateChanged :: is start scanning ", "DevicePickerFragment", z);
        MenuItem menuItem = this.mScan;
        if (menuItem != null) {
            menuItem.setTitle(z ? R.string.service_stop : R.string.bluetooth_scan_for_devices);
        }
        if (z) {
            removeSelectedGroupDevices();
            addCachedDevices$1();
        }
        ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        MenuItem menuItem = this.mProgressItem;
        if (menuItem != null) {
            menuItem.setEnabled(false);
            this.mProgressItem.setActionView((View) null);
            this.mProgressItem.setVisible(false);
        }
        MenuItem menuItem2 = this.mScan;
        if (menuItem2 != null) {
            menuItem2.setTitle(R.string.bluetooth_scan_for_devices);
        }
    }

    public final void sendDevicePickedIntent(BluetoothDevice bluetoothDevice) {
        if (DEBUG) {
            Log.d(
                    "DevicePickerFragment",
                    "sendDevicePickedIntent :: device = " + bluetoothDevice.getName());
        }
        if (DevicePickerActivity.mMyPlacesCalled) {
            Intent intent = new Intent();
            intent.putExtra("DeviceName", bluetoothDevice.semGetAliasName());
            intent.putExtra("MacAddress", bluetoothDevice.getAddress());
            getActivity().setResult(-1, intent);
            return;
        }
        Intent intent2 = new Intent("android.bluetooth.devicepicker.action.DEVICE_SELECTED");
        intent2.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice);
        String str = this.mLaunchPackage;
        if (str != null
                && this.mLaunchClass != null
                && TextUtils.equals(this.mCallingAppPackageName, str)) {
            intent2.setClassName(this.mLaunchPackage, this.mLaunchClass);
        }
        intent2.addFlags(268435456);
        this.mContext.sendBroadcast(intent2, "android.permission.BLUETOOTH_CONNECT");
    }
}
