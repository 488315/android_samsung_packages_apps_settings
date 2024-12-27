package com.android.settings.applications.specialaccess.deviceadmin;

import com.android.settings.R;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DeviceAdminAdd$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeviceAdminAdd f$0;

    public /* synthetic */ DeviceAdminAdd$$ExternalSyntheticLambda0(
            DeviceAdminAdd deviceAdminAdd, int i) {
        this.$r8$classId = i;
        this.f$0 = deviceAdminAdd;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        DeviceAdminAdd deviceAdminAdd = this.f$0;
        switch (i) {
            case 0:
                return deviceAdminAdd.getString(R.string.profile_owner_add_title_simplified);
            case 1:
                return deviceAdminAdd.getString(R.string.admin_device_owner_message);
            case 2:
                return deviceAdminAdd.getString(R.string.remove_device_admin);
            case 3:
                return deviceAdminAdd.getString(R.string.remove_and_uninstall_device_admin);
            case 4:
                return deviceAdminAdd.getString(R.string.remove_device_admin);
            case 5:
                return deviceAdminAdd.getString(R.string.add_device_admin_msg);
            case 6:
                return deviceAdminAdd.getString(R.string.adding_profile_owner_warning);
            case 7:
                return deviceAdminAdd.getString(R.string.uninstall_device_admin);
            case 8:
                return deviceAdminAdd.getString(R.string.admin_profile_owner_message);
            case 9:
                return deviceAdminAdd.getString(R.string.add_device_admin);
            case 10:
                return deviceAdminAdd.getString(R.string.remove_managed_profile_label);
            default:
                return deviceAdminAdd.getString(R.string.admin_profile_owner_user_message);
        }
    }
}
