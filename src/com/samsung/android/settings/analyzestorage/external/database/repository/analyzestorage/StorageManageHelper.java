package com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage;

import android.os.UserHandle;

import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;
import com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils;
import com.samsung.android.settings.analyzestorage.presenter.utils.fileutils.FileWrapper;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class StorageManageHelper {
    public static long getSystemPartitionSize(int i) {
        String rootPath;
        if (!DomainType.isInternalStorage(i)
                || (rootPath = StoragePathUtils.getRootPath(i)) == null) {
            return 0L;
        }
        long totalSpace = new FileWrapper(rootPath).getTotalSpace();
        if (totalSpace > 0) {
            return StorageUsageManager.correctionStorageSize(totalSpace) - totalSpace;
        }
        return 0L;
    }

    public static boolean isSupportCapacityOfSubUsers(int i) {
        if (!DomainType.isInternalStorage(i)) {
            return false;
        }
        if (UserInfoManager.sUserInfoChecker == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
            throw null;
        }
        try {
            if (UserHandle.semGetMyUserId() == 0) {
                return true;
            }
        } catch (NoSuchMethodError e) {
            Log.e("UserInfoCheckerImpl", "isUserOwner() ] Exception e : " + e.getMessage());
        }
        Log.d(
                "StorageManageHelper",
                "isSupportCapacityOfSubUsers ] this is only supported in Owner User.");
        return false;
    }
}
