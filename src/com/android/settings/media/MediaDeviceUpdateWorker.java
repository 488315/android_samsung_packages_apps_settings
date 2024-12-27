package com.android.settings.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRouter2Manager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settingslib.Utils;
import com.android.settingslib.media.LocalMediaManager;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MediaDeviceUpdateWorker extends SliceBackgroundWorker
        implements LocalMediaManager.DeviceCallback {
    public final Context mContext;
    LocalMediaManager mLocalMediaManager;
    MediaRouter2Manager mManager;
    public final Collection mMediaDevices;
    public final String mPackageName;
    public final DevicesChangedBroadcastReceiver mReceiver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DevicesChangedBroadcastReceiver extends BroadcastReceiver {
        public DevicesChangedBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (TextUtils.equals("android.media.STREAM_DEVICES_CHANGED_ACTION", intent.getAction())
                    && Utils.isAudioModeOngoingCall(MediaDeviceUpdateWorker.this.mContext)) {
                MediaDeviceUpdateWorker.this.notifySliceChange();
            }
        }
    }

    static {
        Log.isLoggable("MediaDeviceUpdateWorker", 3);
    }

    public MediaDeviceUpdateWorker(Context context, Uri uri) {
        super(context, uri);
        this.mMediaDevices = new CopyOnWriteArrayList();
        this.mContext = context;
        this.mPackageName = uri.getQueryParameter("media_package_name");
        this.mReceiver = new DevicesChangedBroadcastReceiver();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.mLocalMediaManager = null;
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onDeviceListUpdate(List list) {
        ((CopyOnWriteArrayList) this.mMediaDevices).clear();
        ((CopyOnWriteArrayList) this.mMediaDevices).addAll(list);
        notifySliceChange();
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onRequestFailed(int i) {
        notifySliceChange();
    }

    @Override // com.android.settings.slices.SliceBackgroundWorker
    public final void onSlicePinned() {
        ((CopyOnWriteArrayList) this.mMediaDevices).clear();
        LocalMediaManager localMediaManager = this.mLocalMediaManager;
        if (localMediaManager == null
                || !TextUtils.equals(this.mPackageName, localMediaManager.mPackageName)) {
            this.mLocalMediaManager = new LocalMediaManager(this.mContext, this.mPackageName);
        }
        if (this.mManager == null) {
            this.mManager = MediaRouter2Manager.getInstance(this.mContext);
        }
        this.mLocalMediaManager.registerCallback(this);
        this.mContext.registerReceiver(
                this.mReceiver, new IntentFilter("android.media.STREAM_DEVICES_CHANGED_ACTION"));
        this.mLocalMediaManager.mInfoMediaManager.startScanOnRouter();
    }

    @Override // com.android.settings.slices.SliceBackgroundWorker
    public final void onSliceUnpinned() {
        this.mLocalMediaManager.unregisterCallback(this);
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mLocalMediaManager.mInfoMediaManager.stopScanOnRouter();
    }
}
