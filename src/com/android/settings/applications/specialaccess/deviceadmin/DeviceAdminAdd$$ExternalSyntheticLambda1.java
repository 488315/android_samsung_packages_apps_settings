package com.android.settings.applications.specialaccess.deviceadmin;

import com.android.settings.R;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DeviceAdminAdd$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeviceAdminAdd f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DeviceAdminAdd$$ExternalSyntheticLambda1(
            DeviceAdminAdd deviceAdminAdd, CharSequence charSequence, int i) {
        this.$r8$classId = i;
        this.f$0 = deviceAdminAdd;
        this.f$1 = charSequence;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.getString(
                        R.string.device_admin_warning_simplified, new Object[] {(String) this.f$1});
            case 1:
                return this.f$0.getString(
                        R.string.device_admin_status, new Object[] {(CharSequence) this.f$1});
            default:
                return this.f$0.getString(
                        R.string.device_admin_warning, new Object[] {(CharSequence) this.f$1});
        }
    }

    public /* synthetic */ DeviceAdminAdd$$ExternalSyntheticLambda1(
            DeviceAdminAdd deviceAdminAdd, String str) {
        this.$r8$classId = 0;
        this.f$0 = deviceAdminAdd;
        this.f$1 = str;
    }
}
