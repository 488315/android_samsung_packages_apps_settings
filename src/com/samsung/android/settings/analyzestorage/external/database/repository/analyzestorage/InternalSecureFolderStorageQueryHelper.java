package com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage;

import android.content.Context;

import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoCheckerImpl;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;
import com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class InternalSecureFolderStorageQueryHelper extends LocalStorageQueryHelper {
    @Override // com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.LocalStorageQueryHelper
    public final int getDomainType() {
        return 4;
    }

    @Override // com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.LocalStorageQueryHelper
    public final String getRootPath() {
        return StoragePathUtils.getInternalSecureFolderStoragePath(this.mContext);
    }

    @Override // com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.LocalStorageQueryHelper
    public final String getStorageName() {
        return "internal_secure_folder";
    }

    @Override // com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.LocalStorageQueryHelper
    public final long[] getTrashCapacity() {
        return getTrashCapacityFromMyFiles();
    }

    @Override // com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.LocalStorageQueryHelper
    public final int getUserId() {
        Context context = this.mContext;
        Intrinsics.checkNotNullParameter(context, "context");
        UserInfoCheckerImpl userInfoCheckerImpl = UserInfoManager.sUserInfoChecker;
        if (userInfoCheckerImpl != null) {
            return userInfoCheckerImpl.getSecureFolderUserId(context);
        }
        Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
        throw null;
    }
}
