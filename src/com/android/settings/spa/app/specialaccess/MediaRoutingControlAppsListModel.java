package com.android.settings.spa.app.specialaccess;

import android.app.role.RoleManager;
import android.content.Context;

import com.android.media.flags.Flags;
import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.spaprivileged.model.app.AppOps;
import com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel;
import com.android.settingslib.spaprivileged.template.app.AppOpPermissionRecord;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MediaRoutingControlAppsListModel extends AppOpPermissionListModel {
    public final AppOps appOps;
    public final int footerResId;
    public final int pageTitleResId;
    public final String permission;
    public final RoleManager roleManager;
    public final int switchTitleResId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaRoutingControlAppsListModel(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.pageTitleResId = R.string.media_routing_control_title;
        this.switchTitleResId = R.string.allow_media_routing_control;
        this.footerResId = R.string.allow_media_routing_description;
        this.appOps = new AppOps(139, 0, true, 2);
        this.permission = "android.permission.MEDIA_ROUTING_CONTROL";
        this.roleManager = (RoleManager) context.getSystemService(RoleManager.class);
    }

    @Override // com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel
    public final AppOps getAppOps() {
        return this.appOps;
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

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getSwitchTitleResId() {
        return this.switchTitleResId;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel,
              // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final boolean isChangeable(AppOpPermissionRecord record) {
        RoleManager roleManager;
        List roleHolders;
        Intrinsics.checkNotNullParameter(record, "record");
        return Flags.enablePrivilegedRoutingForMediaRoutingControl()
                && super.isChangeable(record)
                && (roleManager = this.roleManager) != null
                && (roleHolders =
                                roleManager.getRoleHolders(
                                        "android.app.role.COMPANION_DEVICE_WATCH"))
                        != null
                && roleHolders.contains(record.app.packageName);
    }

    @Override // com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel,
              // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final void setAllowed(AppOpPermissionRecord record, boolean z) {
        Intrinsics.checkNotNullParameter(record, "record");
        super.setAllowed(record, z);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(this.context, 2064, z ? 1 : 0);
    }
}
