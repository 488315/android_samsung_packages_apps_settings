package com.samsung.android.settings.analyzestorage.presenter.managers.broadcast;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import java.util.ConcurrentModificationException;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BroadcastReceiveCenter implements BroadcastHandler$NotifyBroadcastListener {
    public static final SparseArray sInstanceMap = new SparseArray();
    public final StorageBroadcastHandler mStorageBroadcastHandler = new StorageBroadcastHandler();
    public final Map mDynamicListenerMap = new EnumMap(BroadcastType.class);
    public final Map mListenerMap = new EnumMap(BroadcastType.class);
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class InstanceHolder {
        public static final BroadcastReceiveCenter INSTANCE = new BroadcastReceiveCenter();
    }

    public final void addDynamicListener(
            BroadcastType broadcastType, BroadcastListener broadcastListener) {
        ((List)
                        this.mDynamicListenerMap.computeIfAbsent(
                                broadcastType,
                                new BroadcastReceiveCenter$$ExternalSyntheticLambda2(1)))
                .add(broadcastListener);
    }

    public final void notify(BroadcastType broadcastType, Bundle bundle, Map map) {
        List list = (List) ((EnumMap) map).get(broadcastType);
        if (list != null) {
            try {
                Log.i(
                        "BroadcastReceiveCenter",
                        "notify (" + broadcastType.name() + ") - " + list.size());
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    this.mHandler.postAtFrontOfQueue(
                            new BroadcastReceiveCenter$$ExternalSyntheticLambda0(
                                    (BroadcastListener) it.next(), broadcastType, bundle, 0));
                }
            } catch (ConcurrentModificationException e) {
                Log.e(
                        "BroadcastReceiveCenter",
                        "notify() ] ConcurrentModificationException e : " + e.getMessage());
            }
        }
    }

    public final void notifyDynamicBroadcast(BroadcastType broadcastType, Bundle bundle) {
        if (BroadcastType.MEDIA_EJECTED != broadcastType
                && BroadcastType.MEDIA_UNMOUNTED != broadcastType
                && BroadcastType.MEDIA_MOUNTED != broadcastType
                && BroadcastType.MEDIA_REMOVED != broadcastType) {
            notify(broadcastType, bundle, this.mDynamicListenerMap);
            return;
        }
        List list = (List) ((EnumMap) this.mDynamicListenerMap).get(broadcastType);
        if (list != null) {
            try {
                Log.i(
                        "BroadcastReceiveCenter",
                        "notifySequentially (" + broadcastType.name() + ") - " + list.size());
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    this.mHandler.post(
                            new BroadcastReceiveCenter$$ExternalSyntheticLambda0(
                                    (BroadcastListener) it.next(), broadcastType, bundle, 1));
                }
            } catch (ConcurrentModificationException e) {
                Log.e(
                        "BroadcastReceiveCenter",
                        "notifySequentially() ] ConcurrentModificationException e : "
                                + e.getMessage());
            }
        }
    }

    public final void removeDynamicListener(
            BroadcastType broadcastType, BroadcastListener broadcastListener) {
        List list = (List) ((EnumMap) this.mDynamicListenerMap).get(broadcastType);
        if (list != null) {
            list.remove(broadcastListener);
        }
    }

    public final void removeListener(
            BroadcastType broadcastType, BroadcastListener broadcastListener) {
        List list = (List) ((EnumMap) this.mListenerMap).get(broadcastType);
        if (list != null) {
            list.remove(broadcastListener);
        }
    }
}
