package com.samsung.android.settings.applications.specialaccess;

import com.android.settings.R;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecDeviceAdminAdd$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecDeviceAdminAdd f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SecDeviceAdminAdd$$ExternalSyntheticLambda1(
            SecDeviceAdminAdd secDeviceAdminAdd, CharSequence charSequence, int i) {
        this.$r8$classId = i;
        this.f$0 = secDeviceAdminAdd;
        this.f$1 = charSequence;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                SecDeviceAdminAdd secDeviceAdminAdd = this.f$0;
                String str = (String) this.f$1;
                int i = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(
                        R.string.device_admin_warning_simplified, new Object[] {str});
            case 1:
                SecDeviceAdminAdd secDeviceAdminAdd2 = this.f$0;
                CharSequence charSequence = (CharSequence) this.f$1;
                int i2 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd2.getString(
                        R.string.device_admin_status, new Object[] {charSequence});
            default:
                SecDeviceAdminAdd secDeviceAdminAdd3 = this.f$0;
                CharSequence charSequence2 = (CharSequence) this.f$1;
                int i3 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd3.getString(
                        R.string.device_admin_warning, new Object[] {charSequence2});
        }
    }

    public /* synthetic */ SecDeviceAdminAdd$$ExternalSyntheticLambda1(
            SecDeviceAdminAdd secDeviceAdminAdd, String str) {
        this.$r8$classId = 0;
        this.f$0 = secDeviceAdminAdd;
        this.f$1 = str;
    }
}
