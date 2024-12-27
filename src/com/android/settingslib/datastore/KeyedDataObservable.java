package com.android.settingslib.datastore;

import androidx.collection.MutableScatterMap;

import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class KeyedDataObservable implements KeyedObservable {
    public final WeakHashMap observers = new WeakHashMap();
    public final MutableScatterMap keyedObservers = new MutableScatterMap();

    public static List copy(MutableScatterMap mutableScatterMap) {
        ArrayList arrayList = new ArrayList(mutableScatterMap._size);
        Object[] objArr = mutableScatterMap.keys;
        Object[] objArr2 = mutableScatterMap.values;
        long[] jArr = mutableScatterMap.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            Object obj = objArr[i4];
                            Set entrySet = ((WeakHashMap) objArr2[i4]).entrySet();
                            Intrinsics.checkNotNullExpressionValue(entrySet, "<get-entries>(...)");
                            arrayList.add(new Pair(obj, entrySet.toArray(new Map.Entry[0])));
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    }
                }
                if (i == length) {
                    break;
                }
                i++;
            }
        }
        return arrayList;
    }

    @Override // com.android.settingslib.datastore.KeyedObservable
    public final void addObserver(
            BackupRestoreStorageManager.StorageWrapper observer, Executor executor) {
        Executor executor2;
        Intrinsics.checkNotNullParameter(observer, "observer");
        Intrinsics.checkNotNullParameter(executor, "executor");
        synchronized (this.observers) {
            executor2 = (Executor) this.observers.put(observer, executor);
        }
        if (executor2 == null || executor2.equals(executor)) {
            return;
        }
        throw new IllegalStateException(
                "Add " + observer + " twice, old=" + executor2 + ", new=" + executor);
    }

    @Override // com.android.settingslib.datastore.KeyedObservable
    public final void notifyChange(final int i) {
        Map.Entry[] entryArr;
        List copy;
        synchronized (this.observers) {
            Set entrySet = this.observers.entrySet();
            Intrinsics.checkNotNullExpressionValue(entrySet, "<get-entries>(...)");
            entryArr = (Map.Entry[]) entrySet.toArray(new Map.Entry[0]);
        }
        synchronized (this.keyedObservers) {
            copy = copy(this.keyedObservers);
        }
        for (Map.Entry entry : entryArr) {
            final BackupRestoreStorageManager.StorageWrapper storageWrapper =
                    (BackupRestoreStorageManager.StorageWrapper) entry.getKey();
            ((Executor) entry.getValue())
                    .execute(
                            new Runnable() { // from class:
                                             // com.android.settingslib.datastore.KeyedDataObservable$notifyChange$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BackupRestoreStorageManager.StorageWrapper.this.onKeyChanged(
                                            i, null);
                                }
                            });
        }
        Iterator it = ((ArrayList) copy).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            Object first = pair.getFirst();
            for (Map.Entry entry2 : (Map.Entry[]) pair.getSecond()) {
                ((Executor) entry2.getValue())
                        .execute(
                                new KeyedDataObservable$notifyChange$2(
                                        (BackupRestoreStorageManager.StorageWrapper)
                                                entry2.getKey(),
                                        first,
                                        i));
            }
        }
    }

    @Override // com.android.settingslib.datastore.KeyedObservable
    public final void removeObserver(BackupRestoreStorageManager.StorageWrapper storageWrapper) {
        synchronized (this.observers) {
        }
    }
}
