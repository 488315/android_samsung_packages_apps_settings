package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AudioStreamsBroadcastAssistantCallback
        implements BluetoothLeBroadcastAssistant.Callback {
    public void onReceiveStateChanged(
            BluetoothDevice bluetoothDevice,
            int i,
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        Log.d(
                "AudioStreamsBroadcastAssistantCallback",
                "onReceiveStateChanged() sink : "
                        + bluetoothDevice.getAddress()
                        + " sourceId: "
                        + i
                        + " state: "
                        + bluetoothLeBroadcastReceiveState);
    }

    public void onSearchStartFailed(int i) {
        RecordingInputConnection$$ExternalSyntheticOutline0.m(
                i, "onSearchStartFailed() reason : ", "AudioStreamsBroadcastAssistantCallback");
    }

    public void onSearchStarted(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "onSearchStarted() reason : ", "AudioStreamsBroadcastAssistantCallback");
    }

    public void onSearchStopFailed(int i) {
        RecordingInputConnection$$ExternalSyntheticOutline0.m(
                i, "onSearchStopFailed() reason : ", "AudioStreamsBroadcastAssistantCallback");
    }

    public void onSearchStopped(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "onSearchStopped() reason : ", "AudioStreamsBroadcastAssistantCallback");
    }

    public void onSourceAddFailed(
            BluetoothDevice bluetoothDevice,
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
            int i) {
        StringBuilder sb = new StringBuilder("onSourceAddFailed() sink : ");
        sb.append(bluetoothDevice.getAddress());
        sb.append(" source: ");
        sb.append(bluetoothLeBroadcastMetadata);
        sb.append(" reason: ");
        Preference$$ExternalSyntheticOutline0.m(sb, i, "AudioStreamsBroadcastAssistantCallback");
    }

    public final void onSourceAdded(BluetoothDevice bluetoothDevice, int i, int i2) {
        StringBuilder sb = new StringBuilder("onSourceAdded() sink : ");
        sb.append(bluetoothDevice.getAddress());
        sb.append(" sourceId: ");
        sb.append(i);
        sb.append(" reason: ");
        Preference$$ExternalSyntheticOutline0.m(sb, i2, "AudioStreamsBroadcastAssistantCallback");
    }

    public void onSourceFound(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        Log.d(
                "AudioStreamsBroadcastAssistantCallback",
                "onSourceFound() broadcastId : "
                        + bluetoothLeBroadcastMetadata.getBroadcastId()
                        + " broadcastName : "
                        + bluetoothLeBroadcastMetadata.getBroadcastName());
    }

    public void onSourceLost(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "onSourceLost() broadcastId : ", "AudioStreamsBroadcastAssistantCallback");
    }

    public void onSourceRemoveFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
        Log.w(
                "AudioStreamsBroadcastAssistantCallback",
                "onSourceRemoveFailed() sourceId : " + i + " reason : " + i2);
    }

    public void onSourceRemoved(BluetoothDevice bluetoothDevice, int i, int i2) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onSourceRemoved() sourceId : ",
                " reason : ",
                i,
                i2,
                "AudioStreamsBroadcastAssistantCallback");
    }

    public final void onSourceModified(BluetoothDevice bluetoothDevice, int i, int i2) {}

    public final void onSourceModifyFailed(BluetoothDevice bluetoothDevice, int i, int i2) {}
}
