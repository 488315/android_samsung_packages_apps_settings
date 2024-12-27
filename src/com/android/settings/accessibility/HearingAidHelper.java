package com.android.settings.accessibility;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.bluetooth.BluetoothHearingAid;
import android.content.Context;

import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.HapClientProfile;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HearingAidHelper {
    public final BluetoothAdapter mBluetoothAdapter;
    public final CachedBluetoothDeviceManager mCachedDeviceManager;
    public final LocalBluetoothProfileManager mProfileManager;

    public HearingAidHelper(Context context) {
        LocalBluetoothManager localBluetoothManager = Utils.getLocalBluetoothManager(context);
        this.mProfileManager = localBluetoothManager.mProfileManager;
        this.mCachedDeviceManager = localBluetoothManager.mCachedDeviceManager;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public final CachedBluetoothDevice getConnectedHearingAidDevice() {
        List connectedHearingAidDeviceList = getConnectedHearingAidDeviceList();
        if (connectedHearingAidDeviceList.isEmpty()) {
            return null;
        }
        return this.mCachedDeviceManager.findDevice(
                (BluetoothDevice) connectedHearingAidDeviceList.get(0));
    }

    public final List getConnectedHearingAidDeviceList() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled() || !isHearingAidSupported()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        HapClientProfile hapClientProfile = localBluetoothProfileManager.mHapClientProfile;
        if (hapClientProfile != null) {
            int[] iArr = {2, 1, 3};
            BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
            arrayList.addAll(
                    bluetoothHapClient == null
                            ? new ArrayList(0)
                            : bluetoothHapClient.getDevicesMatchingConnectionStates(iArr));
        }
        HearingAidProfile hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile;
        if (hearingAidProfile != null) {
            int[] iArr2 = {2, 1, 3};
            BluetoothHearingAid bluetoothHearingAid = hearingAidProfile.mService;
            arrayList.addAll(
                    bluetoothHearingAid == null
                            ? new ArrayList<>(0)
                            : bluetoothHearingAid.getDevicesMatchingConnectionStates(iArr2));
        }
        return (List)
                arrayList.stream()
                        .distinct()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.settings.accessibility.HearingAidHelper$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return !HearingAidHelper.this.mCachedDeviceManager
                                                .isSubDevice((BluetoothDevice) obj);
                                    }
                                })
                        .collect(Collectors.toList());
    }

    public final boolean isHearingAidSupported() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return false;
        }
        List supportedProfiles = bluetoothAdapter.getSupportedProfiles();
        return supportedProfiles.contains(21) || supportedProfiles.contains(28);
    }
}
