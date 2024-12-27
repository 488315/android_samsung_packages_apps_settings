package com.samsung.android.settings.applications.specialaccess;

import com.android.settings.R;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecDeviceAdminAdd$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecDeviceAdminAdd f$0;

    public /* synthetic */ SecDeviceAdminAdd$$ExternalSyntheticLambda0(
            SecDeviceAdminAdd secDeviceAdminAdd, int i) {
        this.$r8$classId = i;
        this.f$0 = secDeviceAdminAdd;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        SecDeviceAdminAdd secDeviceAdminAdd = this.f$0;
        switch (i) {
            case 0:
                int i2 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.profile_owner_add_title_simplified);
            case 1:
                int i3 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.admin_device_owner_message);
            case 2:
                int i4 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.remove_device_admin);
            case 3:
                int i5 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.remove_and_uninstall_device_admin);
            case 4:
                int i6 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.remove_device_admin);
            case 5:
                int i7 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.add_device_admin_msg);
            case 6:
                int i8 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.adding_profile_owner_warning);
            case 7:
                int i9 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.uninstall_device_admin);
            case 8:
                int i10 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.admin_profile_owner_message);
            case 9:
                int i11 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.add_device_admin);
            case 10:
                int i12 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.remove_managed_profile_label);
            default:
                int i13 = SecDeviceAdminAdd.$r8$clinit;
                return secDeviceAdminAdd.getString(R.string.admin_profile_owner_user_message);
        }
    }
}
