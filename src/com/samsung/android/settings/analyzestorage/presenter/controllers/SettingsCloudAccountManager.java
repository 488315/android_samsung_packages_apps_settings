package com.samsung.android.settings.analyzestorage.presenter.controllers;

import com.samsung.android.settings.analyzestorage.presenter.account.QuotaInfo;
import com.samsung.android.settings.analyzestorage.presenter.constant.CloudConstants$CloudType;

import kotlin.jvm.internal.Intrinsics;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SettingsCloudAccountManager {
    public static volatile SettingsCloudAccountManager sInstance;
    public Map accountInfoMap;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AccountInfo {
        public String accountId;
        public CloudConstants$CloudType cloudType;
        public QuotaInfo quotaInfo;

        public final boolean equals(Object obj) {
            boolean z;
            if (!(obj instanceof AccountInfo)) {
                return super.equals(obj);
            }
            AccountInfo accountInfo = (AccountInfo) obj;
            if (this.cloudType != accountInfo.cloudType) {
                return false;
            }
            String str = this.accountId;
            if (str != null) {
                z = str.equals(accountInfo.accountId);
            } else {
                String str2 = accountInfo.accountId;
                z = str2 == null || str2.length() == 0;
            }
            return z && Intrinsics.areEqual(this.quotaInfo, accountInfo.quotaInfo);
        }

        public final int hashCode() {
            return this.quotaInfo.hashCode() + (Objects.hash(this.cloudType, this.accountId) * 31);
        }
    }

    public final boolean isSignedIn(CloudConstants$CloudType cloudType) {
        Intrinsics.checkNotNullParameter(cloudType, "cloudType");
        AccountInfo accountInfo = (AccountInfo) ((EnumMap) this.accountInfoMap).get(cloudType);
        String str = accountInfo != null ? accountInfo.accountId : null;
        return !(str == null || str.length() == 0);
    }
}
