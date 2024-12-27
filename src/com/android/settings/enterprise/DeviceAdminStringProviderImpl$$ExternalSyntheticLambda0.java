package com.android.settings.enterprise;

import com.android.settings.R;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DeviceAdminStringProviderImpl$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeviceAdminStringProviderImpl f$0;

    public /* synthetic */ DeviceAdminStringProviderImpl$$ExternalSyntheticLambda0(
            DeviceAdminStringProviderImpl deviceAdminStringProviderImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = deviceAdminStringProviderImpl;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        DeviceAdminStringProviderImpl deviceAdminStringProviderImpl = this.f$0;
        switch (i) {
            case 0:
                return deviceAdminStringProviderImpl.mContext.getString(
                        R.string.default_admin_support_msg);
            default:
                return deviceAdminStringProviderImpl.mContext.getString(
                        R.string.disabled_by_policy_title);
        }
    }
}
