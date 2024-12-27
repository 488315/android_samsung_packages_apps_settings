package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.bluetooth.VolumeControlProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AudioSharingUtils {
    public static void addSourceToTargetSinks(
            List list, LocalBluetoothManager localBluetoothManager) {
        if (localBluetoothManager == null) {
            Log.d(
                    "AudioSharingUtils",
                    "skip addSourceToTargetDevices: LocalBluetoothManager is null!");
            return;
        }
        if (list.isEmpty()) {
            Log.d("AudioSharingUtils", "Skip addSourceToTargetDevices. No sinks.");
            return;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager.mProfileManager;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                localBluetoothProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("AudioSharingUtils", "skip addSourceToTargetDevices. Broadcast profile is null.");
            return;
        }
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                localBluetoothProfileManager.mLeAudioBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null) {
            Log.d("AudioSharingUtils", "skip addSourceToTargetDevices. Assistant profile is null.");
            return;
        }
        BluetoothLeBroadcastMetadata latestBluetoothLeBroadcastMetadata =
                localBluetoothLeBroadcast.getLatestBluetoothLeBroadcastMetadata();
        if (latestBluetoothLeBroadcastMetadata == null) {
            Log.d(
                    "AudioSharingUtils",
                    "skip addSourceToTargetDevices: There is no broadcastMetadata.");
            return;
        }
        List devicesMatchingConnectionStates =
                localBluetoothLeBroadcastAssistant.getDevicesMatchingConnectionStates(
                        new int[] {2});
        Iterator it = list.iterator();
        while (it.hasNext()) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) it.next();
            if (devicesMatchingConnectionStates.contains(bluetoothDevice)) {
                Log.d(
                        "AudioSharingUtils",
                        "Add broadcast with broadcastId: "
                                + latestBluetoothLeBroadcastMetadata.getBroadcastId()
                                + " to the device: "
                                + bluetoothDevice.getAnonymizedAddress());
                localBluetoothLeBroadcastAssistant.addSource(
                        bluetoothDevice, latestBluetoothLeBroadcastMetadata, false);
            } else {
                Log.d(
                        "AudioSharingUtils",
                        "Skip add broadcast with broadcastId: "
                                + latestBluetoothLeBroadcastMetadata.getBroadcastId()
                                + " to the not connected device: "
                                + bluetoothDevice.getAnonymizedAddress());
            }
        }
    }

    public static Pair[] buildAudioSharingDialogEventData(
            boolean z, int i, int i2, int i3, int i4) {
        return new Pair[] {
            Pair.create(0, Integer.valueOf(i)),
            Pair.create(1, Integer.valueOf(i2)),
            Pair.create(2, Integer.valueOf(z ? 1 : 0)),
            Pair.create(3, Integer.valueOf(i3)),
            Pair.create(4, Integer.valueOf(i4))
        };
    }

    public static List buildOrderedConnectedLeadAudioSharingDeviceItem(
            LocalBluetoothManager localBluetoothManager, Map map, boolean z) {
        return (List)
                buildOrderedConnectedLeadDevices(localBluetoothManager, map, z).stream()
                        .map(new AudioSharingUtils$$ExternalSyntheticLambda2())
                        .collect(Collectors.toList());
    }

    public static List buildOrderedConnectedLeadDevices(
            LocalBluetoothManager localBluetoothManager, Map map, boolean z) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((HashMap) map).values().iterator();
        while (it.hasNext()) {
            CachedBluetoothDevice leadDevice = getLeadDevice((List) it.next());
            if (leadDevice == null) {
                Log.d("AudioSharingUtils", "Skip due to no lead device");
            } else if (!z
                    || BluetoothUtils.hasConnectedBroadcastSource(
                            leadDevice, localBluetoothManager)) {
                arrayList.add(leadDevice);
            } else {
                Log.d(
                        "AudioSharingUtils",
                        "Filtered the device due to not in sharing session: "
                                + leadDevice.mDevice.getAnonymizedAddress());
            }
        }
        arrayList.sort(new AudioSharingUtils$$ExternalSyntheticLambda3());
        return arrayList;
    }

    public static Map fetchConnectedDevicesByGroupId(LocalBluetoothManager localBluetoothManager) {
        HashMap hashMap = new HashMap();
        if (localBluetoothManager == null) {
            Log.d(
                    "AudioSharingUtils",
                    "Skip fetchConnectedDevicesByGroupId due to bt manager is null");
            return hashMap;
        }
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                localBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null) {
            Log.d(
                    "AudioSharingUtils",
                    "Skip fetchConnectedDevicesByGroupId due to assistant profile is null");
            return hashMap;
        }
        for (BluetoothDevice bluetoothDevice :
                localBluetoothLeBroadcastAssistant.getDevicesMatchingConnectionStates(
                        new int[] {2})) {
            CachedBluetoothDevice findDevice =
                    localBluetoothManager.mCachedDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                Log.d(
                        "AudioSharingUtils",
                        "Skip device due to not being cached: "
                                + bluetoothDevice.getAnonymizedAddress());
            } else {
                int groupId = getGroupId(findDevice);
                if (groupId == -1) {
                    Log.d(
                            "AudioSharingUtils",
                            "Skip device due to no valid group id: "
                                    + bluetoothDevice.getAnonymizedAddress());
                } else {
                    if (!hashMap.containsKey(Integer.valueOf(groupId))) {
                        hashMap.put(Integer.valueOf(groupId), new ArrayList());
                    }
                    ((List) hashMap.get(Integer.valueOf(groupId))).add(findDevice);
                }
            }
        }
        Log.d("AudioSharingUtils", "fetchConnectedDevicesByGroupId: " + hashMap);
        return hashMap;
    }

    public static int getGroupId(CachedBluetoothDevice cachedBluetoothDevice) {
        int i = cachedBluetoothDevice.mGroupId;
        String anonymizedAddress = cachedBluetoothDevice.mDevice.getAnonymizedAddress();
        if (i != -1) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "getGroupId by CSIP profile for device: ",
                    anonymizedAddress,
                    "AudioSharingUtils");
            return i;
        }
        for (LocalBluetoothProfile localBluetoothProfile : cachedBluetoothDevice.getProfiles()) {
            if (localBluetoothProfile instanceof LeAudioProfile) {
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "getGroupId by LEA profile for device: ",
                        anonymizedAddress,
                        "AudioSharingUtils");
                BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                BluetoothLeAudio bluetoothLeAudio =
                        ((LeAudioProfile) localBluetoothProfile).mService;
                if (bluetoothLeAudio == null) {
                    return -1;
                }
                return bluetoothLeAudio.getGroupId(bluetoothDevice);
            }
        }
        DialogFragment$$ExternalSyntheticOutline0.m(
                "getGroupId return invalid id for device: ",
                anonymizedAddress,
                "AudioSharingUtils");
        return -1;
    }

    public static CachedBluetoothDevice getLeadDevice(List list) {
        if (list.isEmpty()) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
            if (!cachedBluetoothDevice.mMemberDevices.isEmpty()) {
                return cachedBluetoothDevice;
            }
        }
        CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) list.get(0);
        Log.d(
                "AudioSharingUtils",
                "No lead device in the group, pick arbitrary device as the lead: "
                        + cachedBluetoothDevice2.mDevice.getAnonymizedAddress());
        return cachedBluetoothDevice2;
    }

    public static boolean isAudioSharingProfileReady(
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant;
        VolumeControlProfile volumeControlProfile;
        return (localBluetoothProfileManager == null
                        || (localBluetoothLeBroadcast =
                                        localBluetoothProfileManager.mLeAudioBroadcast)
                                == null
                        || !localBluetoothLeBroadcast.mIsBroadcastProfileReady
                        || (localBluetoothLeBroadcastAssistant =
                                        localBluetoothProfileManager.mLeAudioBroadcastAssistant)
                                == null
                        || !localBluetoothLeBroadcastAssistant.mIsProfileReady
                        || (volumeControlProfile =
                                        localBluetoothProfileManager.mVolumeControlProfile)
                                == null
                        || !volumeControlProfile.mIsProfileReady)
                ? false
                : true;
    }

    public static boolean isBroadcasting(LocalBluetoothManager localBluetoothManager) {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
        return (localBluetoothManager == null
                        || (localBluetoothLeBroadcast =
                                        localBluetoothManager.mProfileManager.mLeAudioBroadcast)
                                == null
                        || !localBluetoothLeBroadcast.isEnabled(null))
                ? false
                : true;
    }

    public static void postOnMainThread(Context context, Runnable runnable) {
        context.getMainExecutor().execute(runnable);
    }

    public static void toastMessage(final Context context, final String str) {
        context.getMainExecutor()
                .execute(
                        new Runnable() { // from class:
                                         // com.android.settings.connecteddevice.audiosharing.AudioSharingUtils$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                Toast.makeText(context, str, 1).show();
                            }
                        });
    }
}
