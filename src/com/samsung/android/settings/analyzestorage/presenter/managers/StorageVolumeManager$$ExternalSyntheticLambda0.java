package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.content.Context;
import android.os.storage.StorageManager;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class StorageVolumeManager$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ StorageOperationManager f$3;

    public /* synthetic */ StorageVolumeManager$$ExternalSyntheticLambda0(
            Context context, int i, int i2, StorageOperationManager storageOperationManager) {
        this.f$0 = context;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = storageOperationManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Context context = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        StorageOperationManager storageOperationManager = this.f$3;
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService("storage");
            StorageVolumeManager.StorageMountInfo mountInfoByDomainType =
                    StorageVolumeManager.getMountInfoByDomainType(i);
            if (mountInfoByDomainType == null || i == -1) {
                Log.e(
                        "StorageVolumeManager",
                        "manageExternalStorage() ] FAIL : can't find the device. (domainType : "
                                + i
                                + " , operationType : "
                                + i2
                                + ')');
                return;
            }
            Log.d(
                    "StorageVolumeManager",
                    "manageExternalStorage() ] Request ["
                            + i2
                            + "] - [Mount:0|Unmount:1|Format:2]");
            Log.d(
                    "StorageVolumeManager",
                    "manageExternalStorage() ] mVolumeId : "
                            + mountInfoByDomainType.mVolumeId
                            + " , Target Device Name : "
                            + StorageVolumeManager.getDeviceNameByDomainType(i));
            if (i2 == 0) {
                storageManager.mount(mountInfoByDomainType.mVolumeId);
            } else if (i2 == 1) {
                storageManager.unmount(mountInfoByDomainType.mVolumeId);
            } else if (i2 == 2) {
                storageManager.partitionPublic(
                        storageManager.findVolumeById(mountInfoByDomainType.mVolumeId).getDiskId());
            }
            storageOperationManager.onSuccess(context, i, i2);
        } catch (IllegalArgumentException e) {
            Log.e(
                    "StorageVolumeManager",
                    " manageExternalStorage() ] IllegalArgumentException e : " + e.getMessage());
        } catch (SecurityException e2) {
            Log.e(
                    "StorageVolumeManager",
                    " manageExternalStorage() ] SecurityException e : " + e2.getMessage());
            storageOperationManager.getClass();
        }
    }
}
