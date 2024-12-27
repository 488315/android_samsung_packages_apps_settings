package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.settings.connecteddevice.audiosharing.AudioSharingUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioStreamsHelper {
    public final LocalBluetoothManager mBluetoothManager;
    public final LocalBluetoothLeBroadcastAssistant mLeBroadcastAssistant;

    public AudioStreamsHelper(LocalBluetoothManager localBluetoothManager) {
        this.mBluetoothManager = localBluetoothManager;
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = null;
        if (localBluetoothManager == null) {
            Log.w(
                    "AudioStreamsHelper",
                    "getLeBroadcastAssistant(): LocalBluetoothManager is null!");
        } else {
            LocalBluetoothProfileManager localBluetoothProfileManager =
                    localBluetoothManager.mProfileManager;
            if (localBluetoothProfileManager == null) {
                Log.w(
                        "AudioStreamsHelper",
                        "getLeBroadcastAssistant(): LocalBluetoothProfileManager is null!");
            } else {
                localBluetoothLeBroadcastAssistant =
                        localBluetoothProfileManager.mLeAudioBroadcastAssistant;
            }
        }
        this.mLeBroadcastAssistant = localBluetoothLeBroadcastAssistant;
    }

    public static String getBroadcastName(
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        String broadcastName = bluetoothLeBroadcastMetadata.getBroadcastName();
        if (broadcastName != null && !broadcastName.isEmpty()) {
            return broadcastName;
        }
        return (String)
                bluetoothLeBroadcastMetadata.getSubgroups().stream()
                        .map(new AudioStreamsHelper$$ExternalSyntheticLambda3(0))
                        .filter(new AudioStreamsHelper$$ExternalSyntheticLambda1(1))
                        .findFirst()
                        .orElse("Broadcast Id: " + bluetoothLeBroadcastMetadata.getBroadcastId());
    }

    public static Optional getCachedBluetoothDeviceInSharingOrLeConnected(
            LocalBluetoothManager localBluetoothManager) {
        if (localBluetoothManager == null) {
            Log.w(
                    "AudioStreamsHelper",
                    "getCachedBluetoothDeviceInSharingOrLeConnected(): LocalBluetoothManager is"
                        + " null!");
            return Optional.empty();
        }
        List buildOrderedConnectedLeadDevices =
                AudioSharingUtils.buildOrderedConnectedLeadDevices(
                        localBluetoothManager,
                        AudioSharingUtils.fetchConnectedDevicesByGroupId(localBluetoothManager),
                        false);
        ArrayList arrayList = (ArrayList) buildOrderedConnectedLeadDevices;
        if (arrayList.isEmpty()) {
            Log.w(
                    "AudioStreamsHelper",
                    "getCachedBluetoothDeviceInSharingOrLeConnected(): No lead device!");
            return Optional.empty();
        }
        Optional findFirst =
                buildOrderedConnectedLeadDevices.stream()
                        .filter(
                                new AudioStreamsHelper$$ExternalSyntheticLambda2(
                                        0, localBluetoothManager))
                        .findFirst();
        if (findFirst.isPresent()) {
            Log.d(
                    "AudioStreamsHelper",
                    "getCachedBluetoothDeviceInSharingOrLeConnected(): Device has connected source"
                        + " found: "
                            + ((CachedBluetoothDevice) findFirst.get()).mDevice.getAddress());
            return findFirst;
        }
        Log.d(
                "AudioStreamsHelper",
                "getCachedBluetoothDeviceInSharingOrLeConnected(): Device connected found: "
                        + ((CachedBluetoothDevice) arrayList.get(0)).mDevice.getAddress());
        return Optional.of((CachedBluetoothDevice) arrayList.get(0));
    }

    public static List getConnectedBluetoothDevices(
            LocalBluetoothManager localBluetoothManager, boolean z) {
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant;
        Optional cachedBluetoothDeviceInSharingOrLeConnected;
        if (localBluetoothManager == null) {
            Log.w(
                    "AudioStreamsHelper",
                    "getConnectedBluetoothDevices(): LocalBluetoothManager is null!");
            return Collections.emptyList();
        }
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager.mProfileManager;
        if (localBluetoothProfileManager == null) {
            Log.w(
                    "AudioStreamsHelper",
                    "getLeBroadcastAssistant(): LocalBluetoothProfileManager is null!");
            localBluetoothLeBroadcastAssistant = null;
        } else {
            localBluetoothLeBroadcastAssistant =
                    localBluetoothProfileManager.mLeAudioBroadcastAssistant;
        }
        if (localBluetoothLeBroadcastAssistant == null) {
            Log.w(
                    "AudioStreamsHelper",
                    "getConnectedBluetoothDevices(): LeBroadcastAssistant is null!");
            return Collections.emptyList();
        }
        List devicesMatchingConnectionStates =
                localBluetoothLeBroadcastAssistant.getDevicesMatchingConnectionStates(
                        new int[] {2});
        if (z) {
            List buildOrderedConnectedLeadDevices =
                    AudioSharingUtils.buildOrderedConnectedLeadDevices(
                            localBluetoothManager,
                            AudioSharingUtils.fetchConnectedDevicesByGroupId(localBluetoothManager),
                            false);
            if (((ArrayList) buildOrderedConnectedLeadDevices).isEmpty()) {
                Log.w("AudioStreamsHelper", "getCachedBluetoothDeviceInSharing(): No lead device!");
                cachedBluetoothDeviceInSharingOrLeConnected = Optional.empty();
            } else {
                cachedBluetoothDeviceInSharingOrLeConnected =
                        buildOrderedConnectedLeadDevices.stream()
                                .filter(
                                        new AudioStreamsHelper$$ExternalSyntheticLambda2(
                                                1, localBluetoothManager))
                                .findFirst();
            }
        } else {
            cachedBluetoothDeviceInSharingOrLeConnected =
                    getCachedBluetoothDeviceInSharingOrLeConnected(localBluetoothManager);
        }
        List list =
                (List)
                        cachedBluetoothDeviceInSharingOrLeConnected
                                .map(
                                        new AudioStreamsHelper$$ExternalSyntheticLambda0(
                                                1, devicesMatchingConnectionStates))
                                .orElse(Collections.emptyList());
        Log.d("AudioStreamsHelper", "getConnectedBluetoothDevices() devices: " + list);
        return list;
    }

    public static boolean hasConnectedBroadcastSource(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothManager localBluetoothManager) {
        if (localBluetoothManager == null) {
            Log.d(
                    "AudioStreamsHelper",
                    "Skip check hasConnectedBroadcastSource due to bt manager is null");
            return false;
        }
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                localBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null) {
            Log.d(
                    "AudioStreamsHelper",
                    "Skip check hasConnectedBroadcastSource due to assistant profile is null");
            return false;
        }
        List allSources =
                localBluetoothLeBroadcastAssistant.getAllSources(cachedBluetoothDevice.mDevice);
        if (!allSources.isEmpty()
                && allSources.stream()
                        .anyMatch(new AudioStreamsHelper$$ExternalSyntheticLambda1(0))) {
            Log.d(
                    "AudioStreamsHelper",
                    "Lead device has connected broadcast source, device = "
                            + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
            return true;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice2 : cachedBluetoothDevice.mMemberDevices) {
            List allSources2 =
                    localBluetoothLeBroadcastAssistant.getAllSources(
                            cachedBluetoothDevice2.mDevice);
            if (!allSources2.isEmpty()
                    && allSources2.stream()
                            .anyMatch(new AudioStreamsHelper$$ExternalSyntheticLambda1(0))) {
                Log.d(
                        "AudioStreamsHelper",
                        "Member device has connected broadcast source, device = "
                                + cachedBluetoothDevice2.mDevice.getAnonymizedAddress());
                return true;
            }
        }
        return false;
    }

    public static boolean isConnected(
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        return bluetoothLeBroadcastReceiveState.getBisSyncState().stream()
                .anyMatch(new AudioStreamsHelper$$ExternalSyntheticLambda1(3));
    }

    public List<BluetoothLeBroadcastReceiveState> getAllConnectedSources() {
        if (this.mLeBroadcastAssistant != null) {
            return getConnectedBluetoothDevices(this.mBluetoothManager, true).stream()
                    .flatMap(new AudioStreamsHelper$$ExternalSyntheticLambda0(0, this))
                    .filter(new AudioStreamsHelper$$ExternalSyntheticLambda1(0))
                    .toList();
        }
        Log.w("AudioStreamsHelper", "getAllSources(): LeBroadcastAssistant is null!");
        return Collections.emptyList();
    }

    public LocalBluetoothLeBroadcastAssistant getLeBroadcastAssistant() {
        return this.mLeBroadcastAssistant;
    }

    public final void startMediaService(Context context, int i, String str) {
        List connectedBluetoothDevices = getConnectedBluetoothDevices(this.mBluetoothManager, true);
        if (connectedBluetoothDevices.isEmpty()) {
            return;
        }
        Intent intent = new Intent(context, (Class<?>) AudioStreamMediaService.class);
        intent.putExtra("audio_stream_media_service_broadcast_id", i);
        intent.putExtra("audio_stream_media_service_broadcast_title", str);
        intent.putParcelableArrayListExtra(
                "audio_stream_media_service_devices", new ArrayList<>(connectedBluetoothDevices));
        context.startService(intent);
    }

    public static String getBroadcastName(
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        return (String)
                bluetoothLeBroadcastReceiveState.getSubgroupMetadata().stream()
                        .map(new AudioStreamsHelper$$ExternalSyntheticLambda3(2))
                        .filter(new AudioStreamsHelper$$ExternalSyntheticLambda1(2))
                        .findFirst()
                        .orElse(
                                "Broadcast Id: "
                                        + bluetoothLeBroadcastReceiveState.getBroadcastId());
    }
}
