package com.android.settings.accessibility;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothUuid;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.os.SystemProperties;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.bluetooth.BluetoothDevicePreference;
import com.android.settings.bluetooth.BluetoothProgressCategory;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.HearingAidInfo;
import com.android.settingslib.bluetooth.HearingAidStatsLogUtils;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class HearingDevicePairingFragment extends RestrictedDashboardFragment
        implements BluetoothCallback {
    public BluetoothProgressCategory mAvailableHearingDeviceGroup;
    public BluetoothAdapter mBluetoothAdapter;
    public CachedBluetoothDeviceManager mCachedDeviceManager;
    public final List mConnectingGattList;
    public final Map mDevicePreferenceMap;
    public final AnonymousClass1 mLeScanCallback;
    public List mLeScanFilters;
    public LocalBluetoothManager mLocalManager;
    public BluetoothDevice mSelectedDevice;
    public final List mSelectedDeviceList;
    public boolean mShowDevicesWithoutNames;

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.accessibility.HearingDevicePairingFragment$1] */
    public HearingDevicePairingFragment() {
        super("no_config_bluetooth");
        this.mSelectedDeviceList = new ArrayList();
        this.mConnectingGattList = new ArrayList();
        this.mDevicePreferenceMap = new HashMap();
        this.mLeScanCallback =
                new ScanCallback() { // from class:
                                     // com.android.settings.accessibility.HearingDevicePairingFragment.1
                    @Override // android.bluetooth.le.ScanCallback
                    public final void onBatchScanResults(List list) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            HearingDevicePairingFragment.this.handleLeScanResult(
                                    (ScanResult) it.next());
                        }
                    }

                    @Override // android.bluetooth.le.ScanCallback
                    public final void onScanFailed(int i) {
                        RecordingInputConnection$$ExternalSyntheticOutline0.m(
                                i,
                                "BLE Scan failed with error code ",
                                "HearingDevicePairingFragment");
                    }

                    @Override // android.bluetooth.le.ScanCallback
                    public final void onScanResult(int i, ScanResult scanResult) {
                        HearingDevicePairingFragment.this.handleLeScanResult(scanResult);
                    }
                };
    }

    public final void addDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null
                && bluetoothAdapter.getState() == 12
                && ((HashMap) this.mDevicePreferenceMap).get(cachedBluetoothDevice) == null) {
            String address = cachedBluetoothDevice.mDevice.getAddress();
            BluetoothDevicePreference bluetoothDevicePreference =
                    (BluetoothDevicePreference) getCachedPreference(address);
            if (bluetoothDevicePreference == null) {
                bluetoothDevicePreference =
                        new BluetoothDevicePreference(
                                getPrefContext(),
                                cachedBluetoothDevice,
                                this.mShowDevicesWithoutNames);
                bluetoothDevicePreference.setKey(address);
                bluetoothDevicePreference.mHideSecondTarget = true;
            }
            BluetoothProgressCategory bluetoothProgressCategory = this.mAvailableHearingDeviceGroup;
            if (bluetoothProgressCategory != null) {
                bluetoothProgressCategory.addPreference(bluetoothDevicePreference);
            }
            ((HashMap) this.mDevicePreferenceMap)
                    .put(cachedBluetoothDevice, bluetoothDevicePreference);
            Log.d("HearingDevicePairingFragment", "Add device. device: " + cachedBluetoothDevice);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "HearingDevicePairingFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2025;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.hearing_device_pairing_fragment;
    }

    public final void handleLeScanResult(ScanResult scanResult) {
        if (this.mCachedDeviceManager == null) {
            return;
        }
        final BluetoothDevice device = scanResult.getDevice();
        final CachedBluetoothDevice findDevice = this.mCachedDeviceManager.findDevice(device);
        if (findDevice == null) {
            findDevice = this.mCachedDeviceManager.addDevice(device);
        } else if (findDevice.mBondState == 12) {
            Log.d(
                    "HearingDevicePairingFragment",
                    "Skip this device, already bonded: " + findDevice);
            return;
        }
        if (findDevice.mHearingAidInfo == null) {
            Log.d("HearingDevicePairingFragment", "Set hearing aid info on device: " + findDevice);
            findDevice.setHearingAidInfo(new HearingAidInfo(-1, -1, 0L));
        }
        if (((HashMap) this.mDevicePreferenceMap).get(findDevice) == null
                && this.mConnectingGattList.stream()
                        .noneMatch(
                                new Predicate() { // from class:
                                                  // com.android.settings.accessibility.HearingDevicePairingFragment$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((BluetoothGatt) obj).getDevice().equals(device);
                                    }
                                })) {
            ScanRecord scanRecord = scanResult.getScanRecord();
            if (scanRecord != null) {
                List<ParcelUuid> serviceUuids = scanRecord.getServiceUuids();
                if (serviceUuids != null
                        && (serviceUuids.contains(BluetoothUuid.HEARING_AID)
                                || serviceUuids.contains(BluetoothUuid.HAS))) {
                    Log.d(
                            "HearingDevicePairingFragment",
                            "Scan record uuid matched, compatible with Android. device: "
                                    + scanResult.getDevice());
                } else if (scanRecord.getServiceData(BluetoothUuid.HEARING_AID) == null
                        && scanRecord.getServiceData(BluetoothUuid.HAS) == null) {
                    Log.d(
                            "HearingDevicePairingFragment",
                            "Scan record mismatched, not compatible with Android. device: "
                                    + scanResult.getDevice());
                } else {
                    Log.d(
                            "HearingDevicePairingFragment",
                            "Scan record service data matched, compatible with Android. device: "
                                    + scanResult.getDevice());
                }
                addDevice(findDevice);
                return;
            }
            Log.d(
                    "HearingDevicePairingFragment",
                    "Scan record is null, not compatible with Android. device: "
                            + scanResult.getDevice());
            Log.d(
                    "HearingDevicePairingFragment",
                    "connectGattToCheckCompatibility, device: " + findDevice);
            ((ArrayList) this.mConnectingGattList)
                    .add(
                            findDevice.mDevice.connectGatt(
                                    getContext(),
                                    false,
                                    new BluetoothGattCallback() { // from class:
                                        // com.android.settings.accessibility.HearingDevicePairingFragment.2
                                        @Override // android.bluetooth.BluetoothGattCallback
                                        public final void onConnectionStateChange(
                                                BluetoothGatt bluetoothGatt, int i, int i2) {
                                            super.onConnectionStateChange(bluetoothGatt, i, i2);
                                            StringBuilder m =
                                                    RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0
                                                            .m(
                                                                    "onConnectionStateChange,"
                                                                        + " status: ",
                                                                    ", newState: ",
                                                                    i,
                                                                    i2,
                                                                    ", device: ");
                                            m.append(findDevice);
                                            Log.d("HearingDevicePairingFragment", m.toString());
                                            if (i == 0 && i2 == 2) {
                                                bluetoothGatt.discoverServices();
                                            } else {
                                                bluetoothGatt.disconnect();
                                                ((ArrayList)
                                                                HearingDevicePairingFragment.this
                                                                        .mConnectingGattList)
                                                        .remove(bluetoothGatt);
                                            }
                                        }

                                        @Override // android.bluetooth.BluetoothGattCallback
                                        public final void onServicesDiscovered(
                                                BluetoothGatt bluetoothGatt, int i) {
                                            super.onServicesDiscovered(bluetoothGatt, i);
                                            StringBuilder m =
                                                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                            i,
                                                            "onServicesDiscovered, status: ",
                                                            ", device: ");
                                            m.append(findDevice);
                                            Log.d("HearingDevicePairingFragment", m.toString());
                                            if (i != 0) {
                                                bluetoothGatt.disconnect();
                                                ((ArrayList)
                                                                HearingDevicePairingFragment.this
                                                                        .mConnectingGattList)
                                                        .remove(bluetoothGatt);
                                            } else {
                                                if (bluetoothGatt.getService(
                                                                        BluetoothUuid.HEARING_AID
                                                                                .getUuid())
                                                                == null
                                                        && bluetoothGatt.getService(
                                                                        BluetoothUuid.HAS.getUuid())
                                                                == null) {
                                                    return;
                                                }
                                                Log.d(
                                                        "HearingDevicePairingFragment",
                                                        "compatible with Android, device: "
                                                                + findDevice);
                                                HearingDevicePairingFragment.this.addDevice(
                                                        findDevice);
                                            }
                                        }
                                    }));
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((ViewAllBluetoothDevicesPreferenceController)
                        use(ViewAllBluetoothDevicesPreferenceController.class))
                .init(this);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        if (i == 10) {
            finish();
        } else {
            if (i != 12) {
                return;
            }
            startScanning();
            Toast.makeText(getContext(), R.string.connected_device_bluetooth_turned_on_toast, 0)
                    .show();
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(getActivity(), Utils.mOnInitCallback);
        this.mLocalManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e("HearingDevicePairingFragment", "Bluetooth is not supported on this device");
            return;
        }
        this.mBluetoothAdapter =
                ((BluetoothManager) getSystemService(BluetoothManager.class)).getAdapter();
        this.mCachedDeviceManager = this.mLocalManager.mCachedDeviceManager;
        this.mShowDevicesWithoutNames =
                SystemProperties.getBoolean("persist.bluetooth.showdeviceswithoutnames", false);
        this.mAvailableHearingDeviceGroup =
                (BluetoothProgressCategory) findPreference("available_hearing_devices");
        ArrayList arrayList = new ArrayList();
        this.mLeScanFilters = arrayList;
        ScanFilter.Builder builder = new ScanFilter.Builder();
        ParcelUuid parcelUuid = BluetoothUuid.HEARING_AID;
        arrayList.add(builder.setServiceUuid(parcelUuid).build());
        ((ArrayList) this.mLeScanFilters)
                .add(new ScanFilter.Builder().setServiceData(parcelUuid, new byte[0]).build());
        List list = this.mLeScanFilters;
        ScanFilter.Builder builder2 = new ScanFilter.Builder();
        ParcelUuid parcelUuid2 = BluetoothUuid.HAS;
        ((ArrayList) list).add(builder2.setServiceUuid(parcelUuid2).build());
        ((ArrayList) this.mLeScanFilters)
                .add(new ScanFilter.Builder().setServiceData(parcelUuid2, new byte[0]).build());
        List list2 = this.mLeScanFilters;
        ScanFilter.Builder builder3 = new ScanFilter.Builder();
        ParcelUuid parcelUuid3 = BluetoothUuid.MFI_HAS;
        ((ArrayList) list2).add(builder3.setServiceUuid(parcelUuid3).build());
        ((ArrayList) this.mLeScanFilters)
                .add(new ScanFilter.Builder().setServiceData(parcelUuid3, new byte[0]).build());
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        Log.d(
                "HearingDevicePairingFragment",
                "onDeviceBondStateChanged: " + cachedBluetoothDevice + ", state = " + i);
        if (i == 12) {
            setResult(-1);
            finish();
            return;
        }
        if (i == 11) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            SettingsMetricsFeatureProvider metricsFeatureProvider =
                    featureFactoryImpl.getMetricsFeatureProvider();
            FragmentActivity activity = getActivity();
            metricsFeatureProvider.getClass();
            HearingAidStatsLogUtils.sDeviceAddressToBondEntryMap.put(
                    cachedBluetoothDevice.mDevice.getAddress(),
                    Integer.valueOf(
                            AccessibilityStatsLogUtils.convertToHearingAidInfoBondEntry(
                                    MetricsFeatureProvider.getAttribution(activity))));
        }
        BluetoothDevice bluetoothDevice = this.mSelectedDevice;
        if (bluetoothDevice != null
                && bluetoothDevice.equals(cachedBluetoothDevice.mDevice)
                && i == 10) {
            startScanning();
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        removeDevice(cachedBluetoothDevice);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!(preference instanceof BluetoothDevicePreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        stopScanning();
        BluetoothDevicePreference bluetoothDevicePreference =
                (BluetoothDevicePreference) preference;
        BluetoothDevice bluetoothDevice = bluetoothDevicePreference.mCachedDevice.mDevice;
        this.mSelectedDevice = bluetoothDevice;
        if (bluetoothDevice != null) {
            ((ArrayList) this.mSelectedDeviceList).add(bluetoothDevice);
        }
        bluetoothDevicePreference.onClicked();
        return true;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        if (cachedBluetoothDevice.isConnected()) {
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            if (bluetoothDevice == null
                    || !((ArrayList) this.mSelectedDeviceList).contains(bluetoothDevice)) {
                removeDevice(cachedBluetoothDevice);
            } else {
                setResult(-1);
                finish();
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mLocalManager == null || this.mBluetoothAdapter == null || isUiRestricted()) {
            return;
        }
        this.mLocalManager.setForegroundActivity(getActivity());
        this.mLocalManager.mEventManager.registerCallback(this);
        if (this.mBluetoothAdapter.isEnabled()) {
            startScanning();
        } else {
            this.mBluetoothAdapter.enable();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        if (this.mLocalManager == null || isUiRestricted()) {
            return;
        }
        stopScanning();
        ((HashMap) this.mDevicePreferenceMap).clear();
        BluetoothProgressCategory bluetoothProgressCategory = this.mAvailableHearingDeviceGroup;
        if (bluetoothProgressCategory != null) {
            bluetoothProgressCategory.removeAll();
        }
        Iterator it = ((ArrayList) this.mConnectingGattList).iterator();
        while (it.hasNext()) {
            ((BluetoothGatt) it.next()).disconnect();
        }
        ((ArrayList) this.mConnectingGattList).clear();
        this.mLocalManager.setForegroundActivity(null);
        this.mLocalManager.mEventManager.unregisterCallback(this);
    }

    public final void removeDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        Log.d("HearingDevicePairingFragment", "removeDevice: " + cachedBluetoothDevice);
        BluetoothDevicePreference bluetoothDevicePreference =
                (BluetoothDevicePreference)
                        ((HashMap) this.mDevicePreferenceMap).remove(cachedBluetoothDevice);
        BluetoothProgressCategory bluetoothProgressCategory = this.mAvailableHearingDeviceGroup;
        if (bluetoothProgressCategory == null || bluetoothDevicePreference == null) {
            return;
        }
        bluetoothProgressCategory.removePreference(bluetoothDevicePreference);
    }

    public final void startScanning() {
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mCachedDeviceManager;
        if (cachedBluetoothDeviceManager != null) {
            cachedBluetoothDeviceManager.clearNonBondedDevices();
        }
        ((HashMap) this.mDevicePreferenceMap).clear();
        BluetoothProgressCategory bluetoothProgressCategory = this.mAvailableHearingDeviceGroup;
        if (bluetoothProgressCategory != null) {
            bluetoothProgressCategory.removeAll();
        }
        if (this.mBluetoothAdapter == null) {
            return;
        }
        Log.v("HearingDevicePairingFragment", "startLeScanning");
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            Log.w("HearingDevicePairingFragment", "LE scanner not found, cannot start LE scanning");
            return;
        }
        bluetoothLeScanner.startScan(
                this.mLeScanFilters,
                new ScanSettings.Builder().setScanMode(2).setLegacy(false).build(),
                this.mLeScanCallback);
        BluetoothProgressCategory bluetoothProgressCategory2 = this.mAvailableHearingDeviceGroup;
        if (bluetoothProgressCategory2 != null) {
            bluetoothProgressCategory2.mProgress = true;
            bluetoothProgressCategory2.notifyChanged();
        }
    }

    public final void stopScanning() {
        if (this.mBluetoothAdapter == null) {
            return;
        }
        Log.v("HearingDevicePairingFragment", "stopLeScanning");
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mLeScanCallback);
            BluetoothProgressCategory bluetoothProgressCategory = this.mAvailableHearingDeviceGroup;
            if (bluetoothProgressCategory != null) {
                bluetoothProgressCategory.mProgress = false;
                bluetoothProgressCategory.notifyChanged();
            }
        }
    }
}
