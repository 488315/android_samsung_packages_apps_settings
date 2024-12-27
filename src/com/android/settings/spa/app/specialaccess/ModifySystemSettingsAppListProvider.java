package com.android.settings.spa.app.specialaccess;

import android.content.Context;

import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListProvider;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ModifySystemSettingsAppListProvider implements TogglePermissionAppListProvider {
    public static final ModifySystemSettingsAppListProvider INSTANCE =
            new ModifySystemSettingsAppListProvider();

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListProvider
    public final TogglePermissionAppListModel createModel(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new ModifySystemSettingsListModel(context);
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListProvider
    public final String getPermissionType() {
        return "ModifySystemSettings";
    }
}
