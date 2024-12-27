package com.android.settings.spa.app.specialaccess;

import android.content.Context;

import com.android.settings.R;
import com.android.settingslib.spaprivileged.model.app.AppOps;
import com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UseFullScreenIntentListModel extends AppOpPermissionListModel {
    public final AppOps appOps;
    public final int footerResId;
    public final int pageTitleResId;
    public final String permission;
    public final int switchTitleResId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UseFullScreenIntentListModel(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.pageTitleResId = R.string.full_screen_intent_title;
        this.switchTitleResId = R.string.permit_full_screen_intent;
        this.footerResId = R.string.footer_description_full_screen_intent;
        this.appOps = new AppOps(133, 0, true, 2);
        this.permission = "android.permission.USE_FULL_SCREEN_INTENT";
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
}
