package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppForceStopButton {
    public final AppForceStopRepository appForceStopRepository;
    public final Context context;
    public final PackageInfoPresenter packageInfoPresenter;
    public final PackageManager packageManager;

    public AppForceStopButton(PackageInfoPresenter packageInfoPresenter) {
        AppForceStopRepository appForceStopRepository =
                new AppForceStopRepository(packageInfoPresenter);
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        this.packageInfoPresenter = packageInfoPresenter;
        this.appForceStopRepository = appForceStopRepository;
        Context context = packageInfoPresenter.context;
        this.context = context;
        this.packageManager = context.getPackageManager();
    }

    public final RestrictedLockUtils.EnforcedAdmin getAdminRestriction(ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(app, "app");
        if (!this.packageManager.isPackageStateProtected(
                app.packageName, ApplicationInfosKt.getUserId(app))) {
            return RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                    this.context, ApplicationInfosKt.getUserId(app), "no_control_apps");
        }
        RestrictedLockUtils.EnforcedAdmin deviceOwner =
                RestrictedLockUtilsInternal.getDeviceOwner(this.context);
        return deviceOwner == null ? new RestrictedLockUtils.EnforcedAdmin() : deviceOwner;
    }
}
