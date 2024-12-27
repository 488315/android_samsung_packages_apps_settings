package com.samsung.android.settings.analyzestorage.data.model;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CommonAppInfo implements AppInfo {
    private String mIntentAction;
    private long mSize = 0;
    private String mPackageName = ApnSettings.MVNO_NONE;

    public final String getPackageName() {
        return this.mPackageName;
    }

    @Override // com.samsung.android.settings.analyzestorage.domain.entity.DataInfo
    public final long getSize() {
        return this.mSize;
    }

    public final void setPackageName(String str) {
        this.mPackageName = str;
    }

    public final void setSize(long j) {
        this.mSize = j;
    }
}
