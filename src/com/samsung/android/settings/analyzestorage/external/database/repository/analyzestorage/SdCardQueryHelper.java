package com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage;

import com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SdCardQueryHelper extends LocalStorageQueryHelper {
    @Override // com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.LocalStorageQueryHelper
    public final int getDomainType() {
        return 1;
    }

    @Override // com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.LocalStorageQueryHelper
    public final String getRootPath() {
        return StoragePathUtils.sExtSdCardPath;
    }

    @Override // com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.LocalStorageQueryHelper
    public final String getStorageName() {
        return "external";
    }
}
