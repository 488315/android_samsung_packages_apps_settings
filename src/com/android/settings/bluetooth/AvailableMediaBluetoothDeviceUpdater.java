package com.android.settings.bluetooth;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AvailableMediaBluetoothDeviceUpdater extends BluetoothDeviceUpdater
        implements Preference.OnPreferenceClickListener {
    public final AudioManager mAudioManager;

    static {
        Log.isLoggable("BluetoothDeviceUpdater", 3);
    }

    public AvailableMediaBluetoothDeviceUpdater(
            Context context, DevicePreferenceCallback devicePreferenceCallback, int i) {
        super(context, devicePreferenceCallback, i);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final String getLogTag() {
        return "AvailableMediaBluetoothDeviceUpdater";
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x008a, code lost:

       if (r6.getConnectionStatus(r7.mDevice) == 2) goto L34;
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x009f, code lost:

       if (r6.getConnectionStatus(r7.mDevice) == 2) goto L34;
    */
    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isFilterMatched(com.android.settingslib.bluetooth.CachedBluetoothDevice r7) {
        /*
            r6 = this;
            android.media.AudioManager r0 = r6.mAudioManager
            int r0 = r0.getMode()
            r1 = 2
            r2 = 1
            if (r0 == r2) goto L12
            if (r0 == r1) goto L12
            r3 = 3
            if (r0 != r3) goto L10
            goto L12
        L10:
            r0 = r1
            goto L13
        L12:
            r0 = r2
        L13:
            boolean r3 = r6.isDeviceConnected(r7)
            r4 = 0
            if (r3 == 0) goto Lbd
            boolean r6 = r6.isDeviceInCachedDevicesList(r7)
            if (r6 == 0) goto Lbd
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r3 = "isFilterMatched() current audio profile : "
            r6.<init>(r3)
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            java.lang.String r3 = "AvailableMediaBluetoothDeviceUpdater"
            android.util.Log.d(r3, r6)
            boolean r6 = r7.isConnectedLeAudioDevice()
            java.lang.String r5 = "isFilterMatched() device : "
            if (r6 == 0) goto L57
            android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r7 = r7.getName()
            r6.append(r7)
            java.lang.String r7 = ", the LE Audio profile is connected and not in sharing if broadcast enabled."
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            android.util.Log.d(r3, r6)
            return r2
        L57:
            boolean r6 = r7.isConnectedAshaHearingAidDevice()
            if (r6 == 0) goto L76
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r7 = r7.getName()
            r6.append(r7)
            java.lang.String r7 = ", the Hearing Aid profile is connected."
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            android.util.Log.d(r3, r6)
            return r2
        L76:
            if (r0 == r2) goto L90
            if (r0 == r1) goto L7b
            goto La2
        L7b:
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r6 = r7.mProfileManager
            if (r6 != 0) goto L80
            goto La2
        L80:
            com.android.settingslib.bluetooth.A2dpProfile r6 = r6.mA2dpProfile
            if (r6 == 0) goto L8d
            android.bluetooth.BluetoothDevice r0 = r7.mDevice
            int r6 = r6.getConnectionStatus(r0)
            if (r6 != r1) goto L8d
            goto L8e
        L8d:
            r2 = r4
        L8e:
            r4 = r2
            goto La2
        L90:
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r6 = r7.mProfileManager
            if (r6 != 0) goto L95
            goto La2
        L95:
            com.android.settingslib.bluetooth.HeadsetProfile r6 = r6.mHeadsetProfile
            if (r6 == 0) goto L8d
            android.bluetooth.BluetoothDevice r0 = r7.mDevice
            int r6 = r6.getConnectionStatus(r0)
            if (r6 != r1) goto L8d
            goto L8e
        La2:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r7 = r7.getName()
            r6.append(r7)
            java.lang.String r7 = ", isFilterMatched : "
            r6.append(r7)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            android.util.Log.d(r3, r6)
        Lbd:
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.bluetooth.AvailableMediaBluetoothDeviceUpdater.isFilterMatched(com.android.settingslib.bluetooth.CachedBluetoothDevice):boolean");
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {
        forceUpdate();
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        this.mMetricsFeatureProvider.logClickedPreference(preference, this.mMetricsCategory);
        this.mDevicePreferenceCallback.onDeviceClick(preference);
        return true;
    }

    @Override // com.android.settings.bluetooth.BluetoothDeviceUpdater
    public final void update(CachedBluetoothDevice cachedBluetoothDevice) {
        super.update(cachedBluetoothDevice);
        Log.d("AvailableMediaBluetoothDeviceUpdater", "Map : " + this.mPreferenceMap);
    }
}
