package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.BidiFormatter;
import android.util.Log;
import android.view.View;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.lifecycle.LifecycleCoroutineScopeImpl;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothDeviceFilter;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.collections.EmptyList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000B\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b"
                + "\t\b'\u0018\u00002\u00020\u00012\u00020\u0002:\u0001%J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\b\u001a\u00020\u0005H\u0017¢\u0006\u0004\b\b\u0010"
                + "\tJ\u000f\u0010\n"
                + "\u001a\u00020\u0005H\u0007¢\u0006\u0004\b\n"
                + "\u0010\tJ\u000f\u0010\u000b\u001a\u00020\u0005H\u0017¢\u0006\u0004\b\u000b\u0010"
                + "\tJ\u000f\u0010\f\u001a\u00020\u0005H\u0017¢\u0006\u0004\b\f\u0010"
                + "\tR\u001c\u0010\u000e\u001a\u00020\r"
                + "8\u0006@\u0006X\u0087\u000e¢\u0006\f\n"
                + "\u0004\b\u000e\u0010\u000f\u0012\u0004\b\u0010\u0010"
                + "\tR\u001e\u0010\u0012\u001a\u0004\u0018\u00010\u00118\u0006@\u0006X\u0087\u000e¢\u0006\f\n"
                + "\u0004\b\u0012\u0010\u0013\u0012\u0004\b\u0014\u0010"
                + "\tR,\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u00158\u0006X\u0087\u0004¢\u0006\u0012\n"
                + "\u0004\b\u0018\u0010\u0019\u0012\u0004\b\u001c\u0010"
                + "\t\u001a\u0004\b\u001a\u0010\u001bR*\u0010\u001e\u001a\u0004\u0018\u00010\u001d8\u0006@\u0006X\u0087\u000e¢\u0006\u0018\n"
                + "\u0004\b\u001e\u0010\u001f\u0012\u0004\b$\u0010\t\u001a\u0004\b"
                + " \u0010!\"\u0004\b\"\u0010#¨\u0006&"
        },
        d2 = {
            "Lcom/android/settings/bluetooth/DeviceListPreferenceFragment;",
            "Lcom/android/settings/dashboard/RestrictedDashboardFragment;",
            "Lcom/android/settingslib/bluetooth/BluetoothCallback;",
            "Landroidx/preference/Preference;",
            "myDevicePreference",
            ApnSettings.MVNO_NONE,
            "updateFooterPreference",
            "(Landroidx/preference/Preference;)V",
            "enableScanning",
            "()V",
            "disableScanning",
            "startScanning",
            "stopScanning",
            ApnSettings.MVNO_NONE,
            "mScanEnabled",
            "Z",
            "getMScanEnabled$annotations",
            "Landroidx/preference/PreferenceGroup;",
            "mDeviceListGroup",
            "Landroidx/preference/PreferenceGroup;",
            "getMDeviceListGroup$annotations",
            "Ljava/util/concurrent/ConcurrentHashMap;",
            "Lcom/android/settingslib/bluetooth/CachedBluetoothDevice;",
            "Lcom/android/settings/bluetooth/BluetoothDevicePreference;",
            "devicePreferenceMap",
            "Ljava/util/concurrent/ConcurrentHashMap;",
            "getDevicePreferenceMap",
            "()Ljava/util/concurrent/ConcurrentHashMap;",
            "getDevicePreferenceMap$annotations",
            "Lkotlinx/coroutines/CoroutineScope;",
            "lifecycleScope",
            "Lkotlinx/coroutines/CoroutineScope;",
            "getLifecycleScope",
            "()Lkotlinx/coroutines/CoroutineScope;",
            "setLifecycleScope",
            "(Lkotlinx/coroutines/CoroutineScope;)V",
            "getLifecycleScope$annotations",
            "ScanType",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public abstract class DeviceListPreferenceFragment extends RestrictedDashboardFragment
        implements BluetoothCallback {
    public final ConcurrentHashMap devicePreferenceMap;
    public final BluetoothDeviceFilter.AllFilter filter;
    public final DeviceListPreferenceFragment$leScanCallback$1 leScanCallback;
    public LifecycleCoroutineScopeImpl lifecycleScope;
    public BluetoothAdapter mBluetoothAdapter;
    public CachedBluetoothDeviceManager mCachedDeviceManager;
    public PreferenceCategory mDeviceListGroup;
    public LocalBluetoothManager mLocalManager;
    public boolean mScanEnabled;
    public BluetoothDevice mSelectedDevice;
    public final List mSelectedList;
    public final ScanType scanType;
    public boolean showDevicesWithoutNames;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0010\n"
                    + "\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001¨\u0006\u0002"
            },
            d2 = {
                "Lcom/android/settings/bluetooth/DeviceListPreferenceFragment$ScanType;",
                ApnSettings.MVNO_NONE,
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0})
    public static final class ScanType {
        public static final /* synthetic */ ScanType[] $VALUES;
        public static final ScanType CLASSIC;
        public static final ScanType LE;

        static {
            ScanType scanType = new ScanType("CLASSIC", 0);
            CLASSIC = scanType;
            ScanType scanType2 = new ScanType("LE", 1);
            LE = scanType2;
            ScanType[] scanTypeArr = {scanType, scanType2};
            $VALUES = scanTypeArr;
            EnumEntriesKt.enumEntries(scanTypeArr);
        }

        public static ScanType valueOf(String str) {
            return (ScanType) Enum.valueOf(ScanType.class, str);
        }

        public static ScanType[] values() {
            return (ScanType[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.bluetooth.DeviceListPreferenceFragment$leScanCallback$1] */
    public DeviceListPreferenceFragment() {
        super("no_config_bluetooth");
        this.scanType = ScanType.CLASSIC;
        this.filter = BluetoothDeviceFilter.ALL_FILTER;
        this.devicePreferenceMap = new ConcurrentHashMap();
        this.mSelectedList = new ArrayList();
        this.leScanCallback =
                new ScanCallback() { // from class:
                                     // com.android.settings.bluetooth.DeviceListPreferenceFragment$leScanCallback$1
                    @Override // android.bluetooth.le.ScanCallback
                    public final void onBatchScanResults(List list) {
                        if (list == null) {
                            list = EmptyList.INSTANCE;
                        }
                        for (ScanResult scanResult : list) {
                            DeviceListPreferenceFragment deviceListPreferenceFragment =
                                    DeviceListPreferenceFragment.this;
                            LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl =
                                    deviceListPreferenceFragment.lifecycleScope;
                            if (lifecycleCoroutineScopeImpl != null) {
                                BuildersKt.launch$default(
                                        lifecycleCoroutineScopeImpl,
                                        null,
                                        null,
                                        new DeviceListPreferenceFragment$handleLeScanResult$1(
                                                scanResult, deviceListPreferenceFragment, null),
                                        3);
                            }
                        }
                    }

                    @Override // android.bluetooth.le.ScanCallback
                    public final void onScanFailed(int i) {
                        RecordingInputConnection$$ExternalSyntheticOutline0.m(
                                i,
                                "BLE Scan failed with error code ",
                                "DeviceListPreferenceFragment");
                    }

                    @Override // android.bluetooth.le.ScanCallback
                    public final void onScanResult(int i, ScanResult result) {
                        Intrinsics.checkNotNullParameter(result, "result");
                        DeviceListPreferenceFragment deviceListPreferenceFragment =
                                DeviceListPreferenceFragment.this;
                        LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl =
                                deviceListPreferenceFragment.lifecycleScope;
                        if (lifecycleCoroutineScopeImpl != null) {
                            BuildersKt.launch$default(
                                    lifecycleCoroutineScopeImpl,
                                    null,
                                    null,
                                    new DeviceListPreferenceFragment$handleLeScanResult$1(
                                            result, deviceListPreferenceFragment, null),
                                    3);
                        }
                    }
                };
    }

    public final void disableScanning() {
        if (this.mScanEnabled) {
            stopScanning();
            this.mScanEnabled = false;
        }
    }

    public void enableScanning() {
        if (this.mScanEnabled) {
            return;
        }
        startScanning();
        this.mScanEnabled = true;
    }

    public abstract void initPreferencesFromPreferenceScreen();

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
            Log.e("DeviceListPreferenceFragment", "Bluetooth is not supported on this device");
            return;
        }
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        LocalBluetoothManager localBluetoothManager2 = this.mLocalManager;
        Intrinsics.checkNotNull(localBluetoothManager2);
        this.mCachedDeviceManager = localBluetoothManager2.mCachedDeviceManager;
        this.showDevicesWithoutNames =
                SystemProperties.getBoolean("persist.bluetooth.showdeviceswithoutnames", false);
        initPreferencesFromPreferenceScreen();
        Preference findPreference = findPreference("available_devices");
        Intrinsics.checkNotNull(
                findPreference,
                "null cannot be cast to non-null type androidx.preference.PreferenceCategory");
        this.mDeviceListGroup = (PreferenceCategory) findPreference;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedDevice) {
        Intrinsics.checkNotNullParameter(cachedDevice, "cachedDevice");
        LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl = this.lifecycleScope;
        if (lifecycleCoroutineScopeImpl != null) {
            BuildersKt.launch$default(
                    lifecycleCoroutineScopeImpl,
                    null,
                    null,
                    new DeviceListPreferenceFragment$onDeviceAdded$1(this, cachedDevice, null),
                    3);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedDevice) {
        Intrinsics.checkNotNullParameter(cachedDevice, "cachedDevice");
        BluetoothDevicePreference bluetoothDevicePreference =
                (BluetoothDevicePreference) this.devicePreferenceMap.remove(cachedDevice);
        if (bluetoothDevicePreference != null) {
            PreferenceCategory preferenceCategory = this.mDeviceListGroup;
            Intrinsics.checkNotNull(preferenceCategory);
            preferenceCategory.removePreference(bluetoothDevicePreference);
        }
    }

    public void onDevicePreferenceClick(BluetoothDevicePreference btPreference) {
        Intrinsics.checkNotNullParameter(btPreference, "btPreference");
        btPreference.onClicked();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        if ("bt_scan".equals(preference.getKey())) {
            startScanning();
            return true;
        }
        if (!(preference instanceof BluetoothDevicePreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        BluetoothDevicePreference bluetoothDevicePreference =
                (BluetoothDevicePreference) preference;
        BluetoothDevice bluetoothDevice = bluetoothDevicePreference.mCachedDevice.mDevice;
        this.mSelectedDevice = bluetoothDevice;
        List list = this.mSelectedList;
        Intrinsics.checkNotNull(bluetoothDevice);
        ((ArrayList) list).add(bluetoothDevice);
        onDevicePreferenceClick(bluetoothDevicePreference);
        return true;
    }

    public void onScanningStateChanged(boolean z) {
        if (z || !this.mScanEnabled) {
            return;
        }
        startScanning();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (this.mLocalManager == null || isUiRestricted()) {
            return;
        }
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        Intrinsics.checkNotNull(localBluetoothManager);
        localBluetoothManager.setForegroundActivity(getActivity());
        LocalBluetoothManager localBluetoothManager2 = this.mLocalManager;
        Intrinsics.checkNotNull(localBluetoothManager2);
        localBluetoothManager2.mEventManager.registerCallback(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (this.mLocalManager == null || isUiRestricted()) {
            return;
        }
        this.devicePreferenceMap.clear();
        PreferenceCategory preferenceCategory = this.mDeviceListGroup;
        Intrinsics.checkNotNull(preferenceCategory);
        preferenceCategory.removeAll();
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        Intrinsics.checkNotNull(localBluetoothManager);
        localBluetoothManager.setForegroundActivity(null);
        LocalBluetoothManager localBluetoothManager2 = this.mLocalManager;
        Intrinsics.checkNotNull(localBluetoothManager2);
        localBluetoothManager2.mEventManager.unregisterCallback(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
        this.lifecycleScope = LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner);
    }

    public void startScanning() {
        if (this.scanType == ScanType.LE) {
            BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
            Intrinsics.checkNotNull(bluetoothAdapter);
            bluetoothAdapter
                    .getBluetoothLeScanner()
                    .startScan(
                            (List<ScanFilter>) null,
                            new ScanSettings.Builder().setScanMode(2).build(),
                            this.leScanCallback);
            return;
        }
        BluetoothAdapter bluetoothAdapter2 = this.mBluetoothAdapter;
        Intrinsics.checkNotNull(bluetoothAdapter2);
        if (bluetoothAdapter2.isDiscovering()) {
            return;
        }
        BluetoothAdapter bluetoothAdapter3 = this.mBluetoothAdapter;
        Intrinsics.checkNotNull(bluetoothAdapter3);
        bluetoothAdapter3.startDiscovery();
    }

    public void stopScanning() {
        if (this.scanType == ScanType.LE) {
            BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
            Intrinsics.checkNotNull(bluetoothAdapter);
            BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            if (bluetoothLeScanner != null) {
                bluetoothLeScanner.stopScan(this.leScanCallback);
                return;
            }
            return;
        }
        BluetoothAdapter bluetoothAdapter2 = this.mBluetoothAdapter;
        Intrinsics.checkNotNull(bluetoothAdapter2);
        if (bluetoothAdapter2.isDiscovering()) {
            BluetoothAdapter bluetoothAdapter3 = this.mBluetoothAdapter;
            Intrinsics.checkNotNull(bluetoothAdapter3);
            bluetoothAdapter3.cancelDiscovery();
        }
    }

    public final void updateFooterPreference(Preference myDevicePreference) {
        Intrinsics.checkNotNullParameter(myDevicePreference, "myDevicePreference");
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        Intrinsics.checkNotNull(bluetoothAdapter);
        myDevicePreference.setTitle(
                getString(
                        R.string.bluetooth_footer_mac_message,
                        bidiFormatter.unicodeWrap(bluetoothAdapter.getAddress())));
    }

    public static /* synthetic */ void getDevicePreferenceMap$annotations() {}

    public static /* synthetic */ void getLifecycleScope$annotations() {}

    public static /* synthetic */ void getMDeviceListGroup$annotations() {}

    public static /* synthetic */ void getMScanEnabled$annotations() {}
}
