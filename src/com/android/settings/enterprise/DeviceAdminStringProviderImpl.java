package com.android.settings.enterprise;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeviceAdminStringProviderImpl {
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;

    public DeviceAdminStringProviderImpl(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }
}
