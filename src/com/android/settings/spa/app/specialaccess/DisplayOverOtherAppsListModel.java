package com.android.settings.spa.app.specialaccess;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.spaprivileged.model.app.AppOps;
import com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel;
import com.android.settingslib.spaprivileged.template.app.AppOpPermissionRecord;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DisplayOverOtherAppsListModel extends AppOpPermissionListModel {
    public final AppOps appOps;
    public final int footerResId;
    public final int pageTitleResId;
    public final String permission;
    public final int switchTitleResId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayOverOtherAppsListModel(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.pageTitleResId = R.string.system_alert_window_settings;
        this.switchTitleResId = R.string.permit_draw_overlay;
        this.footerResId = R.string.allow_overlay_description;
        this.appOps = new AppOps(24, 0, false, 6);
        this.permission = "android.permission.SYSTEM_ALERT_WINDOW";
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
        Intrinsics.checkNotNullParameter(record, "record");
        String[] stringArray =
                this.context
                        .getResources()
                        .getStringArray(R.array.display_over_apps_permission_change_exempt);
        Intrinsics.checkNotNullExpressionValue(stringArray, "getStringArray(...)");
        if (ArraysKt___ArraysKt.contains(record.app.packageName, stringArray)
                && record.app.isSystemApp()) {
            return false;
        }
        return super.isChangeable(record);
    }

    @Override // com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel,
              // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final void setAllowed(AppOpPermissionRecord record, boolean z) {
        Intrinsics.checkNotNullParameter(record, "record");
        super.setAllowed(record, z);
        int i = z ? 770 : 771;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getMetricsFeatureProvider()
                .action(this.context, i, ApnSettings.MVNO_NONE);
    }
}
