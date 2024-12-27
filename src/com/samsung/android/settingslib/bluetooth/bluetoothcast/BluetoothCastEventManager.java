package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.samsung.android.bluetooth.SemBluetoothCastDevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BluetoothCastEventManager {
    public LocalBluetoothCastProfileManager mBluetoothCastProfileManager;
    public final Collection mCallbacks = new ArrayList();
    public final IntentFilter mCastAdapterFilter;
    public final AnonymousClass1 mCastAdapterReceiver;
    public final CachedBluetoothCastDeviceManager mCastDeviceManager;
    public final IntentFilter mCastProfileFilter;
    public final AnonymousClass1 mCastProfileReceiver;
    public final Context mContext;
    public final Map mHandlerMap;
    public final LocalBluetoothCastAdapter mLocalCastAdapter;
    public final ArrayList mReceivers;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AdapterStateChangedHandler implements Handler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BluetoothCastEventManager this$0;

        public /* synthetic */ AdapterStateChangedHandler(
                BluetoothCastEventManager bluetoothCastEventManager, int i) {
            this.$r8$classId = i;
            this.this$0 = bluetoothCastEventManager;
        }

        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        public final void onReceive(
                Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            switch (this.$r8$classId) {
                case 0:
                    int intExtra =
                            intent.getIntExtra(
                                    "android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                    this.this$0.getClass();
                    Log.d(
                            "BluetoothCastEventManager",
                            "AdapterStateChangedHandler :: BluetoothAdapter.ACTION_STATE_CHANGED,"
                                + " state = "
                                    + intExtra);
                    CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager =
                            this.this$0.mCastDeviceManager;
                    synchronized (cachedBluetoothCastDeviceManager) {
                        if (intExtra == 13) {
                            Log.d(
                                    "CachedBluetoothCastDeviceManager",
                                    "onBluetoothStateChanged :: clear mCachedCastDevices");
                            ((ArrayList) cachedBluetoothCastDeviceManager.mCachedCastDevices)
                                    .clear();
                        }
                    }
                    return;
                case 1:
                    if (intent.getIntExtra(
                                    "com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE", 0)
                            == 2) {
                        BluetoothCastEventManager bluetoothCastEventManager = this.this$0;
                        bluetoothCastEventManager.getClass();
                        Log.d(
                                "BluetoothCastEventManager",
                                semBluetoothCastDevice.getAddressForLog() + " found");
                        CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager2 =
                                bluetoothCastEventManager.mCastDeviceManager;
                        CachedBluetoothCastDevice findCastDevice =
                                cachedBluetoothCastDeviceManager2.findCastDevice(
                                        semBluetoothCastDevice);
                        if (findCastDevice == null) {
                            Log.d(
                                    "BluetoothCastEventManager",
                                    "BluetootCastDeviceFoundHandler :: addCastDevice");
                            if (cachedBluetoothCastDeviceManager2.addCastDevice(
                                            bluetoothCastEventManager.mBluetoothCastProfileManager,
                                            semBluetoothCastDevice)
                                    == null) {
                                Log.d(
                                        "BluetoothCastEventManager",
                                        "Failed to created new CachedBluetoothDevice");
                                return;
                            }
                            return;
                        }
                        Log.d(
                                "BluetoothCastEventManager",
                                "BluetootCastDeviceFoundHandler :: processActionFoundEvent");
                        findCastDevice.mCastDevice = semBluetoothCastDevice;
                        findCastDevice.mName = findCastDevice.mCastDevice.getDeviceName();
                        findCastDevice.dispatchAttributesChanged();
                        return;
                    }
                    return;
                default:
                    if (intent.getIntExtra(
                                    "com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE", 0)
                            == 2) {
                        this.this$0.getClass();
                        Log.d(
                                "BluetoothCastEventManager",
                                semBluetoothCastDevice.getAddressForLog() + " removed");
                        CachedBluetoothCastDevice findCastDevice2 =
                                this.this$0.mCastDeviceManager.findCastDevice(
                                        semBluetoothCastDevice);
                        if (findCastDevice2 == null) {
                            this.this$0.getClass();
                            Log.d(
                                    "BluetoothCastEventManager",
                                    "BluetootCastDeviceRemovedHandler :: not found castdevice");
                            return;
                        }
                        this.this$0.getClass();
                        Log.d(
                                "BluetoothCastEventManager",
                                "BluetootCastDeviceRemovedHandler :: removeCastDevice");
                        CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager3 =
                                this.this$0.mCastDeviceManager;
                        synchronized (cachedBluetoothCastDeviceManager3) {
                            Log.d(
                                    "CachedBluetoothCastDeviceManager",
                                    "removeCastDevice : " + findCastDevice2.getName());
                            ((ArrayList) cachedBluetoothCastDeviceManager3.mCachedCastDevices)
                                    .remove(findCastDevice2);
                            BluetoothCastEventManager bluetoothCastEventManager2 =
                                    cachedBluetoothCastDeviceManager3.mBtManager.mCastEventManager;
                            synchronized (bluetoothCastEventManager2.mCallbacks) {
                                try {
                                    Iterator it =
                                            ((ArrayList) bluetoothCastEventManager2.mCallbacks)
                                                    .iterator();
                                    while (it.hasNext()) {
                                        ((BluetoothCastCallback) it.next())
                                                .onCastDeviceRemoved(findCastDevice2);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                        return;
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CastDiscoveryStateChangedHandler implements Handler {
        public final boolean mStarted;

        public CastDiscoveryStateChangedHandler(boolean z) {
            this.mStarted = z;
        }

        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        public final void onReceive(
                Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            synchronized (BluetoothCastEventManager.this.mCallbacks) {
                try {
                    Iterator it =
                            ((ArrayList) BluetoothCastEventManager.this.mCallbacks).iterator();
                    while (it.hasNext()) {
                        ((BluetoothCastCallback) it.next())
                                .onCastDiscoveryStateChanged(this.mStarted);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Handler {
        void onReceive(
                Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager$1] */
    public BluetoothCastEventManager(
            LocalBluetoothCastAdapter localBluetoothCastAdapter,
            CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager,
            Context context) {
        ArrayList arrayList = new ArrayList();
        this.mReceivers = arrayList;
        final int i = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.1
                    public final /* synthetic */ BluetoothCastEventManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i) {
                            case 0:
                                String action = intent.getAction();
                                this.this$0.getClass();
                                Log.v("BluetoothCastEventManager", "onReceive :: " + action);
                                SemBluetoothCastDevice parcelableExtra =
                                        intent.getParcelableExtra(
                                                "com.samsung.android.bluetooth.cast.device.extra.DEVICE");
                                Handler handler =
                                        (Handler) ((HashMap) this.this$0.mHandlerMap).get(action);
                                if (handler != null) {
                                    handler.onReceive(context2, intent, parcelableExtra);
                                    break;
                                }
                                break;
                            default:
                                String action2 = intent.getAction();
                                this.this$0.getClass();
                                Log.v("BluetoothCastEventManager", "onReceive :: " + action2);
                                SemBluetoothCastDevice parcelableExtra2 =
                                        intent.getParcelableExtra(
                                                "com.samsung.android.bluetooth.cast.device.extra.DEVICE");
                                Handler handler2 =
                                        (Handler) ((HashMap) this.this$0.mHandlerMap).get(action2);
                                if (handler2 != null) {
                                    handler2.onReceive(context2, intent, parcelableExtra2);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mCastProfileReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.1
                    public final /* synthetic */ BluetoothCastEventManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i2) {
                            case 0:
                                String action = intent.getAction();
                                this.this$0.getClass();
                                Log.v("BluetoothCastEventManager", "onReceive :: " + action);
                                SemBluetoothCastDevice parcelableExtra =
                                        intent.getParcelableExtra(
                                                "com.samsung.android.bluetooth.cast.device.extra.DEVICE");
                                Handler handler =
                                        (Handler) ((HashMap) this.this$0.mHandlerMap).get(action);
                                if (handler != null) {
                                    handler.onReceive(context2, intent, parcelableExtra);
                                    break;
                                }
                                break;
                            default:
                                String action2 = intent.getAction();
                                this.this$0.getClass();
                                Log.v("BluetoothCastEventManager", "onReceive :: " + action2);
                                SemBluetoothCastDevice parcelableExtra2 =
                                        intent.getParcelableExtra(
                                                "com.samsung.android.bluetooth.cast.device.extra.DEVICE");
                                Handler handler2 =
                                        (Handler) ((HashMap) this.this$0.mHandlerMap).get(action2);
                                if (handler2 != null) {
                                    handler2.onReceive(context2, intent, parcelableExtra2);
                                    break;
                                }
                                break;
                        }
                    }
                };
        Log.d("BluetoothCastEventManager", "BluetoothCastEventManager");
        this.mContext = context;
        this.mLocalCastAdapter = localBluetoothCastAdapter;
        this.mCastDeviceManager = cachedBluetoothCastDeviceManager;
        IntentFilter intentFilter = new IntentFilter();
        this.mCastAdapterFilter = intentFilter;
        this.mCastProfileFilter = new IntentFilter();
        this.mHandlerMap = new HashMap();
        arrayList.clear();
        addCastAdapterHandler(
                "android.bluetooth.adapter.action.STATE_CHANGED",
                new AdapterStateChangedHandler(this, 0));
        addCastAdapterHandler(
                "com.samsung.android.bluetooth.cast.action.DISCOVERY_STARTED",
                new CastDiscoveryStateChangedHandler(true));
        addCastAdapterHandler(
                "com.samsung.android.bluetooth.cast.action.DISCOVERY_FINISHED",
                new CastDiscoveryStateChangedHandler(false));
        addCastAdapterHandler(
                "com.samsung.android.bluetooth.cast.device.action.FOUND",
                new AdapterStateChangedHandler(this, 1));
        addCastAdapterHandler(
                "com.samsung.android.bluetooth.cast.device.action.REMOVED",
                new AdapterStateChangedHandler(this, 2));
        Log.d("BluetoothCastEventManager", "registerReceiver");
        synchronized (arrayList) {
            try {
                if (arrayList.contains(broadcastReceiver)) {
                    context.unregisterReceiver(broadcastReceiver);
                    arrayList.remove(broadcastReceiver);
                    Log.e(
                            "BluetoothCastEventManager",
                            "registerReceiver :: mCastAdapterReceiver was registered already."
                                + " Receiver will refresh.");
                }
                context.registerReceiver(broadcastReceiver, intentFilter);
                arrayList.add(broadcastReceiver);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addCastAdapterHandler(String str, Handler handler) {
        ((HashMap) this.mHandlerMap).put(str, handler);
        this.mCastAdapterFilter.addAction(str);
    }
}
