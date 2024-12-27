package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppArchiveButton {
    public final AppButtonRepository appButtonRepository;
    public CharSequence appLabel;
    public boolean broadcastReceiverIsCreated;
    public final Context context;
    public final MutableStateFlow isHibernationSwitchEnabledStateFlow;
    public final PackageInstaller packageInstaller;
    public final String packageName;
    public final UserHandle userHandle;
    public final PackageManager userPackageManager;

    public AppArchiveButton(
            PackageInfoPresenter packageInfoPresenter,
            MutableStateFlow isHibernationSwitchEnabledStateFlow) {
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        Intrinsics.checkNotNullParameter(
                isHibernationSwitchEnabledStateFlow, "isHibernationSwitchEnabledStateFlow");
        this.isHibernationSwitchEnabledStateFlow = isHibernationSwitchEnabledStateFlow;
        Context context = packageInfoPresenter.context;
        this.context = context;
        this.appButtonRepository = new AppButtonRepository(context);
        PackageManager userPackageManager = packageInfoPresenter.getUserPackageManager();
        this.userPackageManager = userPackageManager;
        PackageInstaller packageInstaller = userPackageManager.getPackageInstaller();
        Intrinsics.checkNotNullExpressionValue(packageInstaller, "getPackageInstaller(...)");
        this.packageInstaller = packageInstaller;
        this.packageName = packageInfoPresenter.packageName;
        this.userHandle = UserHandle.of(packageInfoPresenter.userId);
    }
}
