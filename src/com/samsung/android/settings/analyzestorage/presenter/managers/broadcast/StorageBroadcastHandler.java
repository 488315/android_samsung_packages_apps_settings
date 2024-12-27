package com.samsung.android.settings.analyzestorage.presenter.managers.broadcast;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;

import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class StorageBroadcastHandler {
    public BroadcastHandler$1 mBroadcastReceiver;
    public BroadcastHandler$NotifyBroadcastListener mNotifyBroadcastListener;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final ConcurrentHashMap mPendingEjectIntentMap = new ConcurrentHashMap();

    public final void notifyStorageBroadcast(
            BroadcastType broadcastType, StorageVolumeManager.StorageMountInfo storageMountInfo) {
        if (storageMountInfo != null) {
            Bundle bundle = new Bundle();
            bundle.putString("path", storageMountInfo.mPath);
            bundle.putInt("domainType", storageMountInfo.mDomainType);
            Log.v(
                    "StorageBroadcastHandler",
                    "notifyStorageBroadcast() ] type : "
                            + broadcastType
                            + " , path : "
                            + Log.getEncodedMsg(storageMountInfo.mPath)
                            + " , domainType : "
                            + storageMountInfo.mDomainType);
            ((BroadcastReceiveCenter) this.mNotifyBroadcastListener)
                    .notifyDynamicBroadcast(broadcastType, bundle);
        }
    }
}
