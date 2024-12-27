package com.samsung.android.settings.analyzestorage.domain.entity;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.model.CommonAppInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface AppInfo extends DataInfo {
    default String getLabel() {
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.samsung.android.settings.analyzestorage.domain.entity.DataInfo
    default String getUniqueId() {
        return ((CommonAppInfo) this).getPackageName();
    }
}
