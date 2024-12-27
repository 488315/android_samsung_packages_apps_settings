package com.android.settings.spa.app.specialaccess;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.spaprivileged.model.app.AppOps;
import com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel;
import com.android.settingslib.spaprivileged.template.app.AppOpPermissionRecord;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ModifySystemSettingsListModel extends AppOpPermissionListModel {
    public final AppOps appOps;
    public final int footerResId;
    public final int pageTitleResId;
    public final String permission;
    public final int switchTitleResId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModifySystemSettingsListModel(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.pageTitleResId = R.string.write_system_settings;
        this.switchTitleResId = R.string.permit_write_settings;
        this.footerResId = R.string.write_settings_description;
        this.appOps = new AppOps(23, 0, false, 6);
        this.permission = "android.permission.WRITE_SETTINGS";
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
    public final void setAllowed(AppOpPermissionRecord record, boolean z) {
        Intrinsics.checkNotNullParameter(record, "record");
        super.setAllowed(record, z);
        int i = z ? 774 : 775;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getMetricsFeatureProvider()
                .action(this.context, i, ApnSettings.MVNO_NONE);
    }
}
