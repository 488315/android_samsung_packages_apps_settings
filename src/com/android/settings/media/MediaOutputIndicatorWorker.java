package com.android.settings.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.bluetooth.Utils;
import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MediaOutputIndicatorWorker extends SliceBackgroundWorker
        implements BluetoothCallback, LocalMediaManager.DeviceCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public LocalBluetoothManager mLocalBluetoothManager;
    LocalMediaManager mLocalMediaManager;
    public final Collection mMediaDevices;
    public String mPackageName;
    public final DevicesChangedBroadcastReceiver mReceiver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DevicesChangedBroadcastReceiver extends BroadcastReceiver {
        public DevicesChangedBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(
                    "android.media.STREAM_DEVICES_CHANGED_ACTION", intent.getAction())) {
                MediaOutputIndicatorWorker.this.notifySliceChange();
            }
        }
    }

    static {
        Log.isLoggable("MediaOutputIndWorker", 3);
    }

    public MediaOutputIndicatorWorker(Context context, Uri uri) {
        super(context, uri);
        this.mMediaDevices = new CopyOnWriteArrayList();
        this.mReceiver = new DevicesChangedBroadcastReceiver();
        this.mContext = context;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.mLocalBluetoothManager = null;
        this.mLocalMediaManager = null;
    }

    public final MediaController getActiveLocalMediaController() {
        return MediaOutputUtils.getActiveLocalMediaController(
                (MediaSessionManager) this.mContext.getSystemService(MediaSessionManager.class));
    }

    public final MediaDevice getCurrentConnectedMediaDevice() {
        LocalMediaManager localMediaManager = this.mLocalMediaManager;
        if (localMediaManager == null) {
            return null;
        }
        return localMediaManager.getCurrentConnectedDevice();
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {
        notifySliceChange();
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onDeviceListUpdate(List list) {
        ((CopyOnWriteArrayList) this.mMediaDevices).clear();
        ((CopyOnWriteArrayList) this.mMediaDevices).addAll(list);
        notifySliceChange();
    }

    @Override // com.android.settings.slices.SliceBackgroundWorker
    public final void onSlicePinned() {
        ((CopyOnWriteArrayList) this.mMediaDevices).clear();
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(super.mContext, Utils.mOnInitCallback);
        this.mLocalBluetoothManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e("MediaOutputIndWorker", "Bluetooth is not supported on this device");
            return;
        }
        this.mContext.registerReceiver(
                this.mReceiver, new IntentFilter("android.media.STREAM_DEVICES_CHANGED_ACTION"));
        this.mLocalBluetoothManager.mEventManager.registerCallback(this);
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.media.MediaOutputIndicatorWorker$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaOutputIndicatorWorker mediaOutputIndicatorWorker =
                                MediaOutputIndicatorWorker.this;
                        int i = MediaOutputIndicatorWorker.$r8$clinit;
                        MediaController activeLocalMediaController =
                                mediaOutputIndicatorWorker.getActiveLocalMediaController();
                        if (activeLocalMediaController == null) {
                            mediaOutputIndicatorWorker.mPackageName = null;
                        } else {
                            mediaOutputIndicatorWorker.mPackageName =
                                    activeLocalMediaController.getPackageName();
                        }
                        LocalMediaManager localMediaManager =
                                mediaOutputIndicatorWorker.mLocalMediaManager;
                        if (localMediaManager == null
                                || !TextUtils.equals(
                                        mediaOutputIndicatorWorker.mPackageName,
                                        localMediaManager.mPackageName)) {
                            mediaOutputIndicatorWorker.mLocalMediaManager =
                                    new LocalMediaManager(
                                            mediaOutputIndicatorWorker.mContext,
                                            mediaOutputIndicatorWorker.mPackageName);
                        }
                        mediaOutputIndicatorWorker.mLocalMediaManager.registerCallback(
                                mediaOutputIndicatorWorker);
                        mediaOutputIndicatorWorker.mLocalMediaManager.mInfoMediaManager
                                .startScanOnRouter();
                    }
                });
    }

    @Override // com.android.settings.slices.SliceBackgroundWorker
    public final void onSliceUnpinned() {
        LocalMediaManager localMediaManager = this.mLocalMediaManager;
        if (localMediaManager != null) {
            localMediaManager.unregisterCallback(this);
            this.mLocalMediaManager.mInfoMediaManager.stopScanOnRouter();
        }
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e("MediaOutputIndWorker", "Bluetooth is not supported on this device");
        } else {
            localBluetoothManager.mEventManager.unregisterCallback(this);
            this.mContext.unregisterReceiver(this.mReceiver);
        }
    }
}
