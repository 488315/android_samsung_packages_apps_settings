package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.app.AlarmManager;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.settings.bluetooth.BluetoothSettings;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LocalBluetoothCastAdapter {
    public static LocalBluetoothCastAdapter sInstance;
    public final AlarmManager mAlarmManager;
    public final AnonymousClass2 mBluetoothCastListener;
    public final ArrayList mCallbacks;
    public SemBluetoothCastAdapter mCastAdapter;
    public LocalBluetoothCastProfileManager mCastProfileManager;
    public final String TAG = "LocalBluetoothCastAdapter";
    public final AnonymousClass1 mDiscoveryAlarmListener =
            new AlarmManager
                    .OnAlarmListener() { // from class:
                                         // com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter.1
                @Override // android.app.AlarmManager.OnAlarmListener
                public final void onAlarm() {
                    Log.d(LocalBluetoothCastAdapter.this.TAG, "Discovery timed out");
                    LocalBluetoothCastAdapter.this.suspendDiscovery();
                }
            };

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter$1] */
    public LocalBluetoothCastAdapter(Context context) {
        SemBluetoothCastAdapter.BluetoothCastAdapterListener bluetoothCastAdapterListener =
                new SemBluetoothCastAdapter
                        .BluetoothCastAdapterListener() { // from class:
                                                          // com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter.2
                    public final void onServiceConnected(
                            SemBluetoothCastAdapter semBluetoothCastAdapter) {
                        Log.d(
                                LocalBluetoothCastAdapter.this.TAG,
                                "SemBluetoothCastAdapter Connected");
                        LocalBluetoothCastAdapter localBluetoothCastAdapter =
                                LocalBluetoothCastAdapter.this;
                        localBluetoothCastAdapter.mCastAdapter = semBluetoothCastAdapter;
                        if (localBluetoothCastAdapter.mCastProfileManager == null) {
                            Log.d(localBluetoothCastAdapter.TAG, "Cannot set BluetoothCastStateOn");
                            return;
                        }
                        Iterator it = localBluetoothCastAdapter.mCallbacks.iterator();
                        while (it.hasNext()) {
                            ((BluetoothSettings.AnonymousClass2) it.next())
                                    .onBluetoothCastAdapterStateChanged(true);
                        }
                    }

                    public final void onServiceDisconnected() {
                        LocalBluetoothCastAdapter localBluetoothCastAdapter =
                                LocalBluetoothCastAdapter.this;
                        if (localBluetoothCastAdapter.mCastAdapter != null) {
                            localBluetoothCastAdapter.mCastAdapter = null;
                        }
                        Iterator it = localBluetoothCastAdapter.mCallbacks.iterator();
                        while (it.hasNext()) {
                            ((BluetoothSettings.AnonymousClass2) it.next())
                                    .onBluetoothCastAdapterStateChanged(false);
                        }
                    }
                };
        Log.d("LocalBluetoothCastAdapter", "LocalBluetoothCastAdapter");
        this.mCallbacks = new ArrayList();
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        SemBluetoothCastAdapter.getProxy(context, bluetoothCastAdapterListener);
    }

    public final void finalize() {
        super.finalize();
        this.mCastAdapter.closeProxy();
    }

    public final void startDiscovery() {
        SemBluetoothCastAdapter semBluetoothCastAdapter = this.mCastAdapter;
        String str = this.TAG;
        if (semBluetoothCastAdapter == null) {
            Log.d(str, "Cannot startDiscovery");
            return;
        }
        Log.d(str, "startDiscovery");
        this.mAlarmManager.setExact(
                2,
                SystemClock.elapsedRealtime() + 12000,
                "Discovery",
                this.mDiscoveryAlarmListener,
                null);
        this.mCastAdapter.startDiscovery();
    }

    public final void suspendDiscovery() {
        SemBluetoothCastAdapter semBluetoothCastAdapter = this.mCastAdapter;
        String str = this.TAG;
        if (semBluetoothCastAdapter == null) {
            Log.d(str, "Cannot suspendDiscovery");
            return;
        }
        Log.d(str, "suspendDiscovery");
        this.mAlarmManager.cancel(this.mDiscoveryAlarmListener);
        this.mCastAdapter.suspendDiscovery();
    }
}
