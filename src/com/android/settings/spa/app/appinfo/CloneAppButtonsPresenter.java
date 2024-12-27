package com.android.settings.spa.app.appinfo;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CloneAppButtonsPresenter {
    public final AppCreateButton appCreateButton;
    public final FakeAppForceStopButton appForceStopButton;
    public final FakeAppLaunchButton appLaunchButton;
    public final PackageInfoPresenter packageInfoPresenter;

    public CloneAppButtonsPresenter(PackageInfoPresenter packageInfoPresenter) {
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        this.packageInfoPresenter = packageInfoPresenter;
        this.appLaunchButton = new FakeAppLaunchButton(packageInfoPresenter);
        this.appCreateButton = new AppCreateButton(packageInfoPresenter);
        this.appForceStopButton = new FakeAppForceStopButton(packageInfoPresenter);
    }
}
