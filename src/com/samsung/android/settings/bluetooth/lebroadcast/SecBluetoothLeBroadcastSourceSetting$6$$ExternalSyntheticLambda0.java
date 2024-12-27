package com.samsung.android.settings.bluetooth.lebroadcast;

import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecBluetoothLeBroadcastSourceSetting$6$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecBluetoothLeBroadcastSourceSetting.AnonymousClass6 f$0;

    public /* synthetic */ SecBluetoothLeBroadcastSourceSetting$6$$ExternalSyntheticLambda0(
            SecBluetoothLeBroadcastSourceSetting.AnonymousClass6 anonymousClass6, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass6;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SecBluetoothLeBroadcastSourceSetting.AnonymousClass6 anonymousClass6 = this.f$0;
        switch (i) {
            case 0:
                SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting =
                        SecBluetoothLeBroadcastSourceSetting.this;
                boolean z = SecBluetoothLeBroadcastSourceSetting.DEBUG;
                ActionBarContextView$$ExternalSyntheticOutline0.m(
                        new StringBuilder("handleLeBroadcastStopped, mIsBroadcasting: "),
                        secBluetoothLeBroadcastSourceSetting.mIsBroadcasting,
                        "SecBluetoothLeBroadcastSourceSetting");
                if (secBluetoothLeBroadcastSourceSetting.mIsBroadcasting) {
                    synchronized (secBluetoothLeBroadcastSourceSetting.mSyncedDevicesLock) {
                        ((HashSet) secBluetoothLeBroadcastSourceSetting.mLocalSourceAddedDevices)
                                .clear();
                    }
                    secBluetoothLeBroadcastSourceSetting.mIsBroadcasting = false;
                    secBluetoothLeBroadcastSourceSetting.clearOperationTimeout();
                    Log.d(
                            "SecBluetoothLeBroadcastSourceSetting",
                            "handleLeBroadcastStopped, Update UI");
                    secBluetoothLeBroadcastSourceSetting.refreshUI();
                    return;
                }
                return;
            case 1:
                SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting2 =
                        SecBluetoothLeBroadcastSourceSetting.this;
                boolean z2 = SecBluetoothLeBroadcastSourceSetting.DEBUG;
                Preference$$ExternalSyntheticOutline0.m(
                        new StringBuilder("handleLeBroadcastStarted : "),
                        secBluetoothLeBroadcastSourceSetting2.mBroadcastId,
                        "SecBluetoothLeBroadcastSourceSetting");
                secBluetoothLeBroadcastSourceSetting2.mDeviceAdapter.updateDeviceList(
                        secBluetoothLeBroadcastSourceSetting2.mDeviceController
                                .getConnectedLeAudioDeviceList());
                LeAudioProfile leAudioProfile =
                        secBluetoothLeBroadcastSourceSetting2.mLeAudioProfile;
                if (leAudioProfile != null) {
                    List activeDevices = leAudioProfile.getActiveDevices();
                    Iterator it =
                            ((ArrayList)
                                            secBluetoothLeBroadcastSourceSetting2.mLocalManager
                                                    .mCachedDeviceManager.getCachedDevicesCopy())
                                    .iterator();
                    while (it.hasNext()) {
                        CachedBluetoothDevice cachedBluetoothDevice =
                                (CachedBluetoothDevice) it.next();
                        if (activeDevices.contains(cachedBluetoothDevice.mDevice)) {
                            if (cachedBluetoothDevice.getProfileConnectionState(
                                            secBluetoothLeBroadcastSourceSetting2.mLocalLeAssistant)
                                    == 2) {
                                Log.d(
                                        "SecBluetoothLeBroadcastSourceSetting",
                                        "Broadcast Assistant is connected with "
                                                + cachedBluetoothDevice.getIdentityAddressForLog());
                                return;
                            }
                            Iterator it2 =
                                    ((HashSet) cachedBluetoothDevice.mMemberDevices).iterator();
                            while (it2.hasNext()) {
                                CachedBluetoothDevice cachedBluetoothDevice2 =
                                        (CachedBluetoothDevice) it2.next();
                                if (cachedBluetoothDevice2.getProfileConnectionState(
                                                secBluetoothLeBroadcastSourceSetting2
                                                        .mLocalLeAssistant)
                                        == 2) {
                                    Log.d(
                                            "SecBluetoothLeBroadcastSourceSetting",
                                            "Broadcast Assistant is connected with "
                                                    + cachedBluetoothDevice2
                                                            .getIdentityAddressForLog());
                                    return;
                                } else {
                                    Log.d(
                                            "SecBluetoothLeBroadcastSourceSetting",
                                            "No Broadcast Assistant connection with "
                                                    + cachedBluetoothDevice2
                                                            .getIdentityAddressForLog());
                                }
                            }
                        }
                    }
                }
                secBluetoothLeBroadcastSourceSetting2.clearOperationTimeout();
                secBluetoothLeBroadcastSourceSetting2.mIsBroadcasting = true;
                secBluetoothLeBroadcastSourceSetting2.mMainThreadHandler.postDelayed(
                        new SecBluetoothLeBroadcastSourceSetting$$ExternalSyntheticLambda2(
                                1, secBluetoothLeBroadcastSourceSetting2),
                        700L);
                return;
            default:
                SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting3 =
                        SecBluetoothLeBroadcastSourceSetting.this;
                boolean z3 = SecBluetoothLeBroadcastSourceSetting.DEBUG;
                secBluetoothLeBroadcastSourceSetting3.getClass();
                Log.e("SecBluetoothLeBroadcastSourceSetting", "handleLeBroadcastStartFailed");
                secBluetoothLeBroadcastSourceSetting3.refreshUI();
                return;
        }
    }
}
