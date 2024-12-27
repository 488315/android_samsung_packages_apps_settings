package com.samsung.android.settings.bluetooth.lebroadcast;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothLeBroadcastSubgroup;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothLeBroadcastAssistantSourceItem {
    public final int mBroadcastId;
    public String mBroadcastName;
    public final Context mContext;
    public boolean mIsEncrypted;
    public BluetoothLeBroadcastMetadata mMetadata;
    public String mProgramInfo;
    public BluetoothLeBroadcastReceiveState mRecvState;
    public final Map mRecvStates = new HashMap();
    public int mRssi = 128;
    public final int mSubgroupIndex;
    public String mSummary;
    public String mTitle;

    public SecBluetoothLeBroadcastAssistantSourceItem(Context context, int i, int i2) {
        this.mContext = context;
        this.mBroadcastId = i;
        this.mSubgroupIndex = i2;
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SecBluetoothLeBroadcastAssistantSourceItem)) {
            return false;
        }
        SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem =
                (SecBluetoothLeBroadcastAssistantSourceItem) obj;
        if (secBluetoothLeBroadcastAssistantSourceItem.mSubgroupIndex == this.mSubgroupIndex) {
            return secBluetoothLeBroadcastAssistantSourceItem.mBroadcastId == this.mBroadcastId;
        }
        return false;
    }

    public final boolean isConnected() {
        boolean z;
        Iterator it = ((HashMap) this.mRecvStates).entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState =
                    (BluetoothLeBroadcastReceiveState) ((Map.Entry) it.next()).getValue();
            if (bluetoothLeBroadcastReceiveState != null) {
                z = true;
                if (!this.mIsEncrypted) {
                    if (SecBluetoothLeBroadcastUtils.isPaSyncOrBisSync(
                            bluetoothLeBroadcastReceiveState)) {
                        break;
                    }
                }
                if (!this.mIsEncrypted) {
                    continue;
                } else {
                    if (bluetoothLeBroadcastReceiveState.getPaSyncState() == 2) {
                        break;
                    }
                    if (SecBluetoothLeBroadcastUtils.isBisSync(bluetoothLeBroadcastReceiveState)
                            && bluetoothLeBroadcastReceiveState.getBigEncryptionState() == 2) {
                        break;
                    }
                }
            }
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                RowView$$ExternalSyntheticOutline0.m("isConnected : ", ", mIsEncrypted ", z),
                this.mIsEncrypted,
                "SecBluetoothLeBroadcastAssistantSourceItem");
        return z;
    }

    public final boolean isSync() {
        Iterator it = ((HashMap) this.mRecvStates).entrySet().iterator();
        while (it.hasNext()) {
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState =
                    (BluetoothLeBroadcastReceiveState) ((Map.Entry) it.next()).getValue();
            if (bluetoothLeBroadcastReceiveState != null
                    && (bluetoothLeBroadcastReceiveState.getPaSyncState() != 0
                            || SecBluetoothLeBroadcastUtils.isBisSync(
                                    bluetoothLeBroadcastReceiveState))) {
                Log.d("SecBluetoothLeBroadcastAssistantSourceItem", "isSync : true");
                return true;
            }
        }
        Log.d("SecBluetoothLeBroadcastAssistantSourceItem", "isSync : false");
        return false;
    }

    public final void updateMetadata(
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata, boolean z) {
        this.mMetadata = bluetoothLeBroadcastMetadata;
        this.mIsEncrypted =
                bluetoothLeBroadcastMetadata != null
                        ? bluetoothLeBroadcastMetadata.isEncrypted()
                        : false;
        if (!z) {
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState = this.mRecvState;
        }
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata2 = this.mMetadata;
        if (bluetoothLeBroadcastMetadata2 != null) {
            this.mBroadcastName = bluetoothLeBroadcastMetadata2.getBroadcastName();
        }
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata3 = this.mMetadata;
        if (bluetoothLeBroadcastMetadata3 != null) {
            List subgroups = bluetoothLeBroadcastMetadata3.getSubgroups();
            if (!subgroups.isEmpty()) {
                this.mProgramInfo =
                        ((BluetoothLeBroadcastSubgroup) subgroups.get(this.mSubgroupIndex))
                                .getContentMetadata()
                                .getProgramInfo();
            }
        }
        updateTitleAndSummary();
    }

    public final void updateReceiveState(
            BluetoothDevice bluetoothDevice,
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        String programInfo;
        if (bluetoothDevice == null) {
            Log.i(
                    "SecBluetoothLeBroadcastAssistantSourceItem",
                    "updateByReceiveState: invalid device");
            return;
        }
        if (bluetoothLeBroadcastReceiveState == null) {
            ((HashMap) this.mRecvStates).remove(bluetoothDevice);
            return;
        }
        this.mRecvState = bluetoothLeBroadcastReceiveState;
        ((HashMap) this.mRecvStates).put(bluetoothDevice, bluetoothLeBroadcastReceiveState);
        if (!this.mIsEncrypted
                && bluetoothLeBroadcastReceiveState.getBigEncryptionState() != 0
                && bluetoothLeBroadcastReceiveState.getBigEncryptionState() != 65535) {
            this.mIsEncrypted = true;
        }
        if (bluetoothLeBroadcastReceiveState.getPaSyncState() == 2) {
            List subgroupMetadata = bluetoothLeBroadcastReceiveState.getSubgroupMetadata();
            if (!subgroupMetadata.isEmpty()
                    && (programInfo =
                                    ((BluetoothLeAudioContentMetadata)
                                                    subgroupMetadata.get(this.mSubgroupIndex))
                                            .getProgramInfo())
                            != null) {
                this.mProgramInfo = programInfo;
            }
            updateTitleAndSummary();
        }
    }

    public final void updateTitleAndSummary() {
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata;
        String str = this.mBroadcastName;
        if (str != null) {
            String str2 = this.mProgramInfo;
            if (str2 != null) {
                this.mTitle = str2;
                this.mSummary = str;
                return;
            } else {
                this.mTitle = str;
                this.mSummary = null;
                return;
            }
        }
        String str3 = this.mProgramInfo;
        if (str3 != null) {
            this.mTitle = str3;
            this.mSummary = null;
            return;
        }
        BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState = this.mRecvState;
        BluetoothDevice sourceDevice =
                bluetoothLeBroadcastReceiveState != null
                        ? bluetoothLeBroadcastReceiveState.getSourceDevice()
                        : null;
        if (sourceDevice == null && (bluetoothLeBroadcastMetadata = this.mMetadata) != null) {
            sourceDevice = bluetoothLeBroadcastMetadata.getSourceDevice();
        }
        if (sourceDevice == null) {
            this.mTitle = this.mContext.getString(R.string.device_info_default);
        } else {
            this.mTitle = sourceDevice.getAddress();
        }
        this.mSummary = null;
    }
}
