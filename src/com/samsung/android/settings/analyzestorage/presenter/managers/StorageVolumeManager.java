package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.os.storage.VolumeInfo;
import android.text.TextUtils;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.utils.CollectionUtils;
import com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class StorageVolumeManager {
    public static final List sStorageMountInfoList = new ArrayList();
    public static boolean sIsAppCloneStorageMounted = false;
    public static boolean sIsSdcardMounted = false;
    public static boolean sIsUsbStorageMounted = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StorageMountInfo {
        public int mDomainType;
        public String mFsType;
        public boolean mMounted;
        public String mPath;
        public String mUuid;
        public String mVolumeId;

        public final boolean equals(Object obj) {
            if (!(obj instanceof StorageMountInfo)) {
                return false;
            }
            StorageMountInfo storageMountInfo = (StorageMountInfo) obj;
            return this.mDomainType == storageMountInfo.mDomainType
                    && this.mVolumeId.equals(storageMountInfo.mVolumeId);
        }

        public final int hashCode() {
            return Objects.hash(this.mVolumeId, Integer.valueOf(this.mDomainType));
        }

        public final String toString() {
            return String.valueOf(this.mDomainType)
                    + ':'
                    + this.mVolumeId
                    + ':'
                    + this.mUuid
                    + ':'
                    + this.mMounted
                    + ':'
                    + this.mFsType
                    + ':'
                    + Log.getEncodedMsg(this.mPath);
        }
    }

    public static boolean connected(int i) {
        if (DomainType.isLocalStorage(i)) {
            StorageMountInfo mountInfoByDomainType = getMountInfoByDomainType(i);
            if (i != 0) {
                return i != 1
                        ? i != 2 ? mountInfoByDomainType != null : sIsAppCloneStorageMounted
                        : sIsSdcardMounted || mountInfoByDomainType != null;
            }
            return true;
        }
        Log.e("StorageVolumeManager", "connected() ] Can't check mount state of storage : " + i);
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0084, code lost:

       com.samsung.android.settings.analyzestorage.domain.log.Log.d("StorageVolumeManager", "ensureAppCloneStorageInfo() ] Found the mounted Clone storage. userId : " + r0);
       com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager.sIsAppCloneStorageMounted = true;
       com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils.sInternalAppCloneStorageRoot = r1;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void ensureAppCloneStorageInfo(android.os.storage.StorageManager r6) {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager.ensureAppCloneStorageInfo(android.os.storage.StorageManager):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00a1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void ensureUsbInfo(java.util.List r10) {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager.ensureUsbInfo(java.util.List):void");
    }

    public static String getDeviceNameByDomainType(int i) {
        return DomainType.isSd(i)
                ? SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "SD Card:")
                : DomainType.isUsb(i)
                        ? SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "USB:")
                        : SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Unknown Device:");
    }

    public static StorageMountInfo getIdAndMountInfo(VolumeInfo volumeInfo, boolean z) {
        StorageMountInfo storageMountInfo = new StorageMountInfo();
        storageMountInfo.mDomainType = -1;
        storageMountInfo.mVolumeId = volumeInfo.getId();
        storageMountInfo.mUuid = volumeInfo.getFsUuid();
        storageMountInfo.mMounted = z;
        storageMountInfo.mFsType = volumeInfo.fsType;
        return storageMountInfo;
    }

    public static StorageMountInfo getMountInfoByDomainType(int i) {
        StorageMountInfo storageMountInfo;
        synchronized (StorageVolumeManager.class) {
            try {
                Iterator it = CollectionUtils.getEmptyListIfNull(sStorageMountInfoList).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        storageMountInfo = null;
                        break;
                    }
                    storageMountInfo = (StorageMountInfo) it.next();
                    if (i == storageMountInfo.mDomainType) {}
                }
            } finally {
            }
        }
        return storageMountInfo;
    }

    public static StorageMountInfo getMountInfoByUuid(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (StorageVolumeManager.class) {
            try {
                for (StorageMountInfo storageMountInfo :
                        CollectionUtils.getEmptyListIfNull(sStorageMountInfoList)) {
                    if (TextUtils.equals(str, storageMountInfo.mUuid)) {
                        return storageMountInfo;
                    }
                }
                return null;
            } finally {
            }
        }
    }

    public static boolean mounted(int i) {
        if (!DomainType.isLocalStorage(i)) {
            Log.e("StorageVolumeManager", "mounted() ] Can't check mount state of storage : " + i);
            return false;
        }
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            return sIsSdcardMounted;
        }
        if (i == 2) {
            return sIsAppCloneStorageMounted;
        }
        StorageMountInfo mountInfoByDomainType = getMountInfoByDomainType(i);
        return (mountInfoByDomainType == null
                        || !mountInfoByDomainType.mMounted
                        || StoragePathUtils.sUsbStoragePathArray.get(i) == null)
                ? false
                : true;
    }

    public static void setSdCardPath(String str, boolean z) {
        if (!z) {
            StoragePathUtils.sExtSdCardPath = RestrictionPolicy.EXTERNAL_STORAGE_PATH_SD;
        } else {
            sIsSdcardMounted = true;
            StoragePathUtils.sExtSdCardPath = str;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0110, code lost:

       com.samsung.android.settings.analyzestorage.domain.log.Log.d("StorageVolumeManager", "updateStorageMountState() ] Storage Policy has restriction.");
    */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00a3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a4 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void updateStorageMountState(android.content.Context r11) {
        /*
            Method dump skipped, instructions count: 424
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager.updateStorageMountState(android.content.Context):void");
    }
}
