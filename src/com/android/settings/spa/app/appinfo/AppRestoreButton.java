package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import com.android.settings.R;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppRestoreButton {
    public boolean broadcastReceiverIsCreated;
    public final StateFlowImpl buttonTextIndexStateFlow;
    public final int[] buttonTexts;
    public final Context context;
    public CoroutineScope coroutineScope;
    public final PackageInstaller packageInstaller;
    public final String packageName;
    public StandaloneCoroutine updateButtonTextJob;
    public final UserHandle userHandle;
    public final PackageManager userPackageManager;

    public AppRestoreButton(PackageInfoPresenter packageInfoPresenter) {
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        this.context = packageInfoPresenter.context;
        PackageManager userPackageManager = packageInfoPresenter.getUserPackageManager();
        this.userPackageManager = userPackageManager;
        PackageInstaller packageInstaller = userPackageManager.getPackageInstaller();
        Intrinsics.checkNotNullExpressionValue(packageInstaller, "getPackageInstaller(...)");
        this.packageInstaller = packageInstaller;
        this.packageName = packageInfoPresenter.packageName;
        this.userHandle = UserHandle.of(packageInfoPresenter.userId);
        this.buttonTexts =
                new int[] {
                    R.string.restore,
                    R.string.restoring_step_one,
                    R.string.restoring_step_two,
                    R.string.restoring_step_three,
                    R.string.restoring_step_four
                };
        this.buttonTextIndexStateFlow = StateFlowKt.MutableStateFlow(0);
    }
}
