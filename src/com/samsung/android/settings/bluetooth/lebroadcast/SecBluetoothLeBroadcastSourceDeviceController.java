package com.samsung.android.settings.bluetooth.lebroadcast;

import android.os.Debug;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CsipSetCoordinatorProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.samsung.android.settings.bluetooth.BluetoothCastSettings$$ExternalSyntheticLambda1;
import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothLeBroadcastSourceDeviceController
        implements SemBluetoothCallback, LocalBluetoothProfileManager.ServiceListener {
    public Callback mCallback;
    public List mConnectedLeAudioDevices;
    public LeAudioProfile mLeAudioProfile;
    public LocalBluetoothLeBroadcastAssistant mLeBroadcastAssistant;
    public LocalBluetoothManager mLocalBluetoothManager;
    public LocalBluetoothProfileManager mProfileManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {}

    static {
        Debug.semIsProductDev();
    }

    public final List getConnectedLeAudioDeviceList() {
        Log.d(
                "SecBluetoothLeBroadcastSourceDeviceController",
                "getConnectedLeAudioDeviceList size: " + this.mConnectedLeAudioDevices.size());
        return this.mConnectedLeAudioDevices;
    }

    public final void initConnectedList() {
        Collection cachedDevicesCopy =
                this.mLocalBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy();
        this.mConnectedLeAudioDevices.clear();
        Iterator it = ((ArrayList) cachedDevicesCopy).iterator();
        while (it.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
            if (!this.mConnectedLeAudioDevices.contains(cachedBluetoothDevice)) {
                int profileConnectionState =
                        cachedBluetoothDevice.getProfileConnectionState(this.mLeBroadcastAssistant);
                if (profileConnectionState == 2 || profileConnectionState == 1) {
                    if (Utils.DEBUG) {
                        Log.d(
                                "SecBluetoothLeBroadcastSourceDeviceController",
                                "init : " + cachedBluetoothDevice.getIdentityAddressForLog());
                    }
                    this.mConnectedLeAudioDevices.add(cachedBluetoothDevice);
                } else if (isMemberBassConnected(cachedBluetoothDevice)) {
                    Log.d(
                            "SecBluetoothLeBroadcastSourceDeviceController",
                            "init : " + cachedBluetoothDevice.getIdentityAddressForLog());
                    this.mConnectedLeAudioDevices.add(cachedBluetoothDevice);
                }
            }
        }
        this.mConnectedLeAudioDevices =
                (List)
                        this.mConnectedLeAudioDevices.stream()
                                .sorted(new BluetoothCastSettings$$ExternalSyntheticLambda1())
                                .collect(Collectors.toList());
        Log.d(
                "SecBluetoothLeBroadcastSourceDeviceController",
                "initConnectedList : " + this.mConnectedLeAudioDevices.size());
    }

    public final boolean isBassConnected(CachedBluetoothDevice cachedBluetoothDevice) {
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null) {
            return false;
        }
        int profileConnectionState =
                cachedBluetoothDevice.getProfileConnectionState(localBluetoothLeBroadcastAssistant);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                profileConnectionState,
                "isBassConnected: ",
                "SecBluetoothLeBroadcastSourceDeviceController");
        return profileConnectionState == 2 || profileConnectionState == 1;
    }

    public final boolean isMemberBassConnected(CachedBluetoothDevice cachedBluetoothDevice) {
        Iterator it = ((HashSet) cachedBluetoothDevice.mMemberDevices).iterator();
        while (it.hasNext()) {
            if (isBassConnected((CachedBluetoothDevice) it.next())) {
                Log.i(
                        "SecBluetoothLeBroadcastSourceDeviceController",
                        "isMemberBassConnected true");
                return true;
            }
        }
        return false;
    }

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onProfileStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile,
            int i,
            int i2) {
        if ((localBluetoothProfile instanceof CsipSetCoordinatorProfile)
                && i == 2
                && cachedBluetoothDevice.mLeadDevice != null
                && this.mConnectedLeAudioDevices.contains(cachedBluetoothDevice)) {
            Log.d(
                    "SecBluetoothLeBroadcastSourceDeviceController",
                    "onProfileStateChanged : CSIP connected, remove device : "
                            + cachedBluetoothDevice.getIdentityAddressForLog());
            this.mConnectedLeAudioDevices.remove(cachedBluetoothDevice);
            updateSourceMainUI();
            return;
        }
        if (!(localBluetoothProfile instanceof LocalBluetoothLeBroadcastAssistant)
                || i == 1
                || i == 3) {
            return;
        }
        StringBuilder sb = new StringBuilder("onProfileStateChanged ( ");
        sb.append(cachedBluetoothDevice.getIdentityAddressForLog());
        sb.append(") state changed ");
        sb.append(i2);
        sb.append(" -> ");
        Preference$$ExternalSyntheticOutline0.m(
                sb, i, "SecBluetoothLeBroadcastSourceDeviceController");
        CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mLeadDevice;
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if ((cachedBluetoothDevice2 == null
                        || cachedBluetoothDevice2.getProfileConnectionState(
                                        localBluetoothLeBroadcastAssistant)
                                != i)
                && (cachedBluetoothDevice.getProfileConnectionState(
                                        localBluetoothLeBroadcastAssistant)
                                != i
                        || cachedBluetoothDevice2 == null)) {
            cachedBluetoothDevice2 = cachedBluetoothDevice;
        }
        Log.d(
                "SecBluetoothLeBroadcastSourceDeviceController",
                "onProfileStateChanged: target device: "
                        + cachedBluetoothDevice2.getIdentityAddressForLog());
        if (i == 2) {
            if (this.mConnectedLeAudioDevices.contains(cachedBluetoothDevice2)) {
                return;
            }
            if (Utils.DEBUG) {
                Log.d(
                        "SecBluetoothLeBroadcastSourceDeviceController",
                        "init : " + cachedBluetoothDevice.getIdentityAddressForLog());
            }
            this.mConnectedLeAudioDevices.add(cachedBluetoothDevice2);
        } else if (i == 0) {
            if (!this.mConnectedLeAudioDevices.contains(cachedBluetoothDevice2)) {
                return;
            }
            if (isBassConnected(cachedBluetoothDevice2)
                    || isMemberBassConnected(cachedBluetoothDevice2)) {
                Log.d(
                        "SecBluetoothLeBroadcastSourceDeviceController",
                        "onProfileStateChanged: Do not remove device "
                                + cachedBluetoothDevice2.getIdentityAddressForLog());
                return;
            } else {
                Log.d(
                        "SecBluetoothLeBroadcastSourceDeviceController",
                        "onProfileStateChanged: remove device "
                                + cachedBluetoothDevice2.getIdentityAddressForLog());
                this.mConnectedLeAudioDevices.remove(cachedBluetoothDevice2);
            }
        }
        updateSourceMainUI();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {
        initConnectedList();
        ((SecBluetoothLeBroadcastSourceSetting) this.mCallback)
                .updateDeviceList(this.mConnectedLeAudioDevices);
    }

    public final void updateSourceMainUI() {
        Log.d(
                "SecBluetoothLeBroadcastSourceDeviceController",
                "updateSourceMainUI : " + this.mConnectedLeAudioDevices.size());
        List list =
                (List)
                        this.mConnectedLeAudioDevices.stream()
                                .sorted(new BluetoothCastSettings$$ExternalSyntheticLambda1())
                                .collect(Collectors.toList());
        this.mConnectedLeAudioDevices = list;
        if (list.isEmpty()) {
            return;
        }
        ((SecBluetoothLeBroadcastSourceSetting) this.mCallback)
                .updateDeviceList(this.mConnectedLeAudioDevices);
    }

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onResourceUpdated() {}

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {}

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onSyncDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}
}
