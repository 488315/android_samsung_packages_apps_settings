package com.android.settingslib.datastore;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class KeyedDataObservable$notifyChange$2 implements Runnable {
    public final /* synthetic */ Object $key;
    public final /* synthetic */ BackupRestoreStorageManager.StorageWrapper $observer;
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int $reason;

    public KeyedDataObservable$notifyChange$2(
            BackupRestoreStorageManager.StorageWrapper storageWrapper, Object obj, int i) {
        this.$r8$classId = 0;
        this.$observer = storageWrapper;
        this.$key = obj;
        this.$reason = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.$observer.onKeyChanged(this.$reason, this.$key);
                break;
            case 1:
                this.$observer.onKeyChanged(this.$reason, this.$key);
                break;
            default:
                this.$observer.onKeyChanged(this.$reason, this.$key);
                break;
        }
    }

    public /* synthetic */ KeyedDataObservable$notifyChange$2(
            BackupRestoreStorageManager.StorageWrapper storageWrapper, Object obj, int i, byte b) {
        this.$r8$classId = i;
        this.$observer = storageWrapper;
        this.$key = obj;
        this.$reason = 1;
    }
}
