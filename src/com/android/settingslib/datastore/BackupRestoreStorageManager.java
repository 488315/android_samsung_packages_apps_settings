package com.android.settingslib.datastore;

import android.app.Application;
import android.app.backup.BackupManager;
import android.content.Context;
import android.util.Log;

import com.google.common.util.concurrent.MoreExecutors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackupRestoreStorageManager {
    public static final Companion Companion = new Companion();
    public static volatile BackupRestoreStorageManager instance;
    public final Application application;
    public final ConcurrentHashMap storageWrappers = new ConcurrentHashMap();
    public final Executor executor = MoreExecutors.directExecutor();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {"\u0000\u0004\n\u0002\b\u0002\b\u0081\u0004\u0018\u0000¨\u0006\u0001"},
            d2 = {
                "com/android/settingslib/datastore/BackupRestoreStorageManager$StorageWrapper",
                "frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore"
            },
            k = 1,
            mv = {1, 9, 0})
    public final class StorageWrapper {
        public final BackupRestoreStorage storage;

        /* JADX WARN: Multi-variable type inference failed */
        public StorageWrapper(BackupRestoreStorage backupRestoreStorage) {
            this.storage = backupRestoreStorage;
            if (backupRestoreStorage instanceof KeyedObservable) {
                Executor executor = BackupRestoreStorageManager.this.executor;
                Intrinsics.checkNotNullExpressionValue(executor, "access$getExecutor$p(...)");
                ((KeyedObservable) backupRestoreStorage).addObserver(this, executor);
            } else if (backupRestoreStorage instanceof Observable) {
                Executor executor2 = BackupRestoreStorageManager.this.executor;
                Intrinsics.checkNotNullExpressionValue(executor2, "access$getExecutor$p(...)");
                ((Observable) backupRestoreStorage).addObserver$1(this, executor2);
            } else {
                throw new IllegalArgumentException(
                        this + " does not implement either KeyedObservable or Observable");
            }
        }

        public final void onKeyChanged(int i, Object obj) {
            String name = this.storage.getName();
            if (i == 3) {
                Log.d(
                        "BackupRestoreStorage",
                        "Notify BackupManager dataChanged ignored for restore: storage="
                                + name
                                + " key="
                                + obj);
                return;
            }
            Log.d(
                    "BackupRestoreStorage",
                    "Notify BackupManager dataChanged: storage="
                            + name
                            + " key="
                            + obj
                            + " reason="
                            + i);
            new BackupManager(BackupRestoreStorageManager.this.application).dataChanged();
        }
    }

    public BackupRestoreStorageManager(Application application) {
        this.application = application;
    }

    public static final BackupRestoreStorageManager getInstance(Context context) {
        Companion companion = Companion;
        Intrinsics.checkNotNullParameter(context, "context");
        BackupRestoreStorageManager backupRestoreStorageManager = instance;
        if (backupRestoreStorageManager != null) {
            return backupRestoreStorageManager;
        }
        synchronized (companion) {
            if (instance == null) {
                Context applicationContext = context.getApplicationContext();
                Intrinsics.checkNotNull(
                        applicationContext,
                        "null cannot be cast to non-null type android.app.Application");
                instance = new BackupRestoreStorageManager((Application) applicationContext);
            }
        }
        BackupRestoreStorageManager backupRestoreStorageManager2 = instance;
        Intrinsics.checkNotNull(backupRestoreStorageManager2);
        return backupRestoreStorageManager2;
    }

    public static /* synthetic */ void
            getStorageWrappers$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore$annotations() {}
}
