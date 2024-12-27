package com.android.settings.homepage.contextualcards.slices;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothUpdateWorker extends SliceBackgroundWorker implements BluetoothCallback {
    public static LocalBluetoothManager sLocalBluetoothManager;
    public final LoadBtManagerHandler mLoadBtManagerHandler;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LoadBtManagerHandler extends Handler {
        public static LoadBtManagerHandler sHandler;
        public final Context mContext;
        public final BluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0
                mLoadBtManagerTask;
        public BluetoothUpdateWorker mWorker;

        /* renamed from: -$$Nest$smgetInstance, reason: not valid java name */
        public static LoadBtManagerHandler m939$$Nest$smgetInstance(Context context) {
            if (sHandler == null) {
                HandlerThread handlerThread = new HandlerThread("BluetoothUpdateWorker", 10);
                handlerThread.start();
                sHandler = new LoadBtManagerHandler(context, handlerThread.getLooper());
            }
            return sHandler;
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settings.homepage.contextualcards.slices.BluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0] */
        public LoadBtManagerHandler(Context context, Looper looper) {
            super(looper);
            this.mContext = context;
            this.mLoadBtManagerTask =
                    new Runnable() { // from class:
                                     // com.android.settings.homepage.contextualcards.slices.BluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BluetoothUpdateWorker.LoadBtManagerHandler loadBtManagerHandler =
                                    BluetoothUpdateWorker.LoadBtManagerHandler.this;
                            loadBtManagerHandler.getClass();
                            Log.d(
                                    "BluetoothUpdateWorker",
                                    "LoadBtManagerHandler: start loading...");
                            long currentTimeMillis = System.currentTimeMillis();
                            BluetoothUpdateWorker.sLocalBluetoothManager =
                                    loadBtManagerHandler.getLocalBtManager();
                            Log.d(
                                    "BluetoothUpdateWorker",
                                    "LoadBtManagerHandler took "
                                            + (System.currentTimeMillis() - currentTimeMillis)
                                            + " ms");
                        }
                    };
        }

        public final LocalBluetoothManager getLocalBtManager() {
            LocalBluetoothManager localBluetoothManager =
                    BluetoothUpdateWorker.sLocalBluetoothManager;
            return localBluetoothManager != null
                    ? localBluetoothManager
                    : LocalBluetoothManager.getInstance(
                            this.mContext,
                            new LocalBluetoothManager
                                    .BluetoothManagerCallback() { // from class:
                                                                  // com.android.settings.homepage.contextualcards.slices.BluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda1
                                @Override // com.android.settingslib.bluetooth.LocalBluetoothManager.BluetoothManagerCallback
                                public final void onBluetoothManagerInitialized() {
                                    BluetoothUpdateWorker bluetoothUpdateWorker =
                                            BluetoothUpdateWorker.LoadBtManagerHandler.this.mWorker;
                                    if (bluetoothUpdateWorker != null) {
                                        bluetoothUpdateWorker.notifySliceChange();
                                    }
                                }
                            });
        }
    }

    public BluetoothUpdateWorker(Context context, Uri uri) {
        super(context, uri);
        LoadBtManagerHandler m939$$Nest$smgetInstance =
                LoadBtManagerHandler.m939$$Nest$smgetInstance(context);
        this.mLoadBtManagerHandler = m939$$Nest$smgetInstance;
        if (sLocalBluetoothManager == null) {
            m939$$Nest$smgetInstance.mWorker = this;
            BluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0
                    bluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0 =
                            m939$$Nest$smgetInstance.mLoadBtManagerTask;
            if (m939$$Nest$smgetInstance.hasCallbacks(
                    bluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0)) {
                return;
            }
            m939$$Nest$smgetInstance.post(
                    bluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAclConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {
        notifySliceChange();
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        notifySliceChange();
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        notifySliceChange();
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        notifySliceChange();
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        notifySliceChange();
    }

    @Override // com.android.settings.slices.SliceBackgroundWorker
    public final void onSlicePinned() {
        LoadBtManagerHandler loadBtManagerHandler = this.mLoadBtManagerHandler;
        LoadBtManagerHandler loadBtManagerHandler2 = LoadBtManagerHandler.sHandler;
        LocalBluetoothManager localBtManager = loadBtManagerHandler.getLocalBtManager();
        if (localBtManager == null) {
            return;
        }
        localBtManager.mEventManager.registerCallback(this);
    }

    @Override // com.android.settings.slices.SliceBackgroundWorker
    public final void onSliceUnpinned() {
        LoadBtManagerHandler loadBtManagerHandler = this.mLoadBtManagerHandler;
        LoadBtManagerHandler loadBtManagerHandler2 = LoadBtManagerHandler.sHandler;
        LocalBluetoothManager localBtManager = loadBtManagerHandler.getLocalBtManager();
        if (localBtManager == null) {
            return;
        }
        localBtManager.mEventManager.unregisterCallback(this);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {}
}
