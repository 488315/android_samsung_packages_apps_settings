package com.android.settings.spa.app.appinfo;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppButtonsPresenter {
    public final AppArchiveButton appArchiveButton;
    public final AppClearButton appClearButton;
    public final AppDisableButton appDisableButton;
    public final AppForceStopButton appForceStopButton;
    public final AppInstallButton appInstallButton;
    public final AppLaunchButton appLaunchButton;
    public final AppRestoreButton appRestoreButton;
    public final AppUninstallButton appUninstallButton;
    public final FeatureFlags featureFlags;
    public final PackageInfoPresenter packageInfoPresenter;

    public AppButtonsPresenter(
            PackageInfoPresenter packageInfoPresenter,
            MutableStateFlow isHibernationSwitchEnabledStateFlow,
            FeatureFlags featureFlags) {
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        Intrinsics.checkNotNullParameter(
                isHibernationSwitchEnabledStateFlow, "isHibernationSwitchEnabledStateFlow");
        Intrinsics.checkNotNullParameter(featureFlags, "featureFlags");
        this.packageInfoPresenter = packageInfoPresenter;
        this.featureFlags = featureFlags;
        this.appLaunchButton = new AppLaunchButton(packageInfoPresenter);
        this.appInstallButton = new AppInstallButton(packageInfoPresenter);
        this.appDisableButton = new AppDisableButton(packageInfoPresenter);
        this.appUninstallButton = new AppUninstallButton(packageInfoPresenter);
        this.appClearButton = new AppClearButton(packageInfoPresenter);
        this.appForceStopButton = new AppForceStopButton(packageInfoPresenter);
        this.appArchiveButton =
                new AppArchiveButton(packageInfoPresenter, isHibernationSwitchEnabledStateFlow);
        this.appRestoreButton = new AppRestoreButton(packageInfoPresenter);
    }
}
