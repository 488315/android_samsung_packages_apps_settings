package com.android.settings.notification.app;

import android.app.AppOpsManager;
import android.content.AttributionSource;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.RestrictedSwitchPreference;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FullScreenIntentPermissionPreferenceController
        extends NotificationPreferenceController implements Preference.OnPreferenceChangeListener {
    public final AppOpsManager appOpsManager;
    public final PackageManager packageManager;
    public final PermissionManager permissionManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FullScreenIntentPermissionPreferenceController(
            Context context, NotificationBackend backend) {
        super(context, backend);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(backend, "backend");
        PackageManager packageManager = this.mPm;
        Intrinsics.checkNotNull(packageManager);
        this.packageManager = packageManager;
        Object systemService = context.getSystemService((Class<Object>) PermissionManager.class);
        Intrinsics.checkNotNull(systemService);
        this.permissionManager = (PermissionManager) systemService;
        Object systemService2 = context.getSystemService((Class<Object>) AppOpsManager.class);
        Intrinsics.checkNotNull(systemService2);
        this.appOpsManager = (AppOpsManager) systemService2;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "fsi_permission";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        boolean z = this.mChannelGroup == null && this.mChannel == null;
        if (!z) {
            Log.e("FsiPermPrefController", "Belongs only in app-wide notification preferences!");
        }
        if (!super.isAvailable() || !z) {
            return false;
        }
        try {
            String[] strArr =
                    this.packageManager.getPackageInfo(this.mAppRow.pkg, 4096).requestedPermissions;
            if (strArr == null) {
                strArr = new String[0];
            }
            for (String str : strArr) {
                if ("android.permission.USE_FULL_SCREEN_INTENT".equals(str)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("FsiPermPrefController", "isPermissionRequested failed: " + e);
            return false;
        }
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return false;
    }

    public final boolean isPermissionGranted() {
        AttributionSource build =
                new AttributionSource.Builder(this.mAppRow.uid)
                        .setPackageName(this.mAppRow.pkg)
                        .build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return this.permissionManager.checkPermissionForPreflight(
                        "android.permission.USE_FULL_SCREEN_INTENT", build)
                == 0;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object value) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        Intrinsics.checkNotNullParameter(value, "value");
        if (!"fsi_permission".equals(preference.getKey())) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (!(preference instanceof RestrictedSwitchPreference)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (!(value instanceof Boolean)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (Boolean.valueOf(isPermissionGranted()).equals(value)) {
            return true;
        }
        this.appOpsManager.setUidMode(
                133, this.mAppRow.uid, ((Boolean) value).booleanValue() ? 0 : 2);
        PackageManager packageManager = this.packageManager;
        NotificationBackend.AppRow appRow = this.mAppRow;
        packageManager.updatePermissionFlags(
                "android.permission.USE_FULL_SCREEN_INTENT",
                appRow.pkg,
                1,
                1,
                UserHandle.getUserHandleForUid(appRow.uid));
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!"fsi_permission".equals(preference.getKey())) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (!(preference instanceof RestrictedSwitchPreference)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference) preference;
        restrictedSwitchPreference.setDisabledByAdmin(this.mAdmin);
        restrictedSwitchPreference.setEnabled(!restrictedSwitchPreference.mHelper.mDisabledByAdmin);
        restrictedSwitchPreference.setChecked(isPermissionGranted());
    }
}
