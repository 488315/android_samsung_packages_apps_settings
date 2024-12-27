package com.android.settings.spa.app.specialaccess;

import com.android.settingslib.spaprivileged.model.app.AppOps;
import com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiControlAppListModel extends AppOpPermissionListModel {
    public final AppOps appOps;
    public final String broaderPermission;
    public final int footerResId;
    public final int pageTitleResId;
    public final String permission;
    public final int switchTitleResId;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public WifiControlAppListModel(android.content.Context r5) {
        /*
            r4 = this;
            com.android.settingslib.spaprivileged.model.app.PackageManagers r0 = com.android.settingslib.spaprivileged.model.app.PackageManagers.INSTANCE
            java.lang.String r1 = "context"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r1)
            java.lang.String r1 = "packageManagers"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r4.<init>(r5, r0)
            r5 = 2132019490(0x7f140922, float:1.9677316E38)
            r4.pageTitleResId = r5
            r5 = 2132019489(0x7f140921, float:1.9677314E38)
            r4.switchTitleResId = r5
            r5 = 2132019488(0x7f140920, float:1.9677312E38)
            r4.footerResId = r5
            com.android.settingslib.spaprivileged.model.app.AppOps r5 = new com.android.settingslib.spaprivileged.model.app.AppOps
            r0 = 71
            r1 = 4
            r2 = 1
            r3 = 0
            r5.<init>(r0, r2, r3, r1)
            r4.appOps = r5
            java.lang.String r5 = "android.permission.CHANGE_WIFI_STATE"
            r4.permission = r5
            java.lang.String r5 = "android.permission.NETWORK_SETTINGS"
            r4.broaderPermission = r5
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.specialaccess.WifiControlAppListModel.<init>(android.content.Context):void");
    }

    @Override // com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel
    public final AppOps getAppOps() {
        return this.appOps;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel
    public final String getBroaderPermission() {
        return this.broaderPermission;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getFooterResId() {
        return this.footerResId;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getPageTitleResId() {
        return this.pageTitleResId;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel
    public final String getPermission() {
        return this.permission;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel
    public final boolean getPermissionHasAppOpFlag() {
        return false;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getSwitchTitleResId() {
        return this.switchTitleResId;
    }
}
