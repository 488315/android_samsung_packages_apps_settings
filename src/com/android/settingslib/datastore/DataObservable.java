package com.android.settingslib.datastore;

import kotlin.jvm.internal.Intrinsics;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataObservable implements Observable {
    public final WeakHashMap observers = new WeakHashMap();

    @Override // com.android.settingslib.datastore.Observable
    public final void addObserver$1(
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

    @Override // com.android.settingslib.datastore.Observable
    public final void notifyChange(final int i) {
        int i2;
        Map.Entry[] entryArr;
        synchronized (this.observers) {
            Set entrySet = this.observers.entrySet();
            Intrinsics.checkNotNullExpressionValue(entrySet, "<get-entries>(...)");
            entryArr = (Map.Entry[]) entrySet.toArray(new Map.Entry[0]);
        }
        for (Map.Entry entry : entryArr) {
            final BackupRestoreStorageManager.StorageWrapper storageWrapper =
                    (BackupRestoreStorageManager.StorageWrapper) entry.getKey();
            ((Executor) entry.getValue())
                    .execute(
                            new Runnable() { // from class:
                                             // com.android.settingslib.datastore.DataObservable$notifyChange$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BackupRestoreStorageManager.StorageWrapper.this.onKeyChanged(
                                            i, null);
                                }
                            });
        }
    }

    @Override // com.android.settingslib.datastore.Observable
    public final void removeObserver$1(BackupRestoreStorageManager.StorageWrapper storageWrapper) {
        synchronized (this.observers) {
        }
    }
}
