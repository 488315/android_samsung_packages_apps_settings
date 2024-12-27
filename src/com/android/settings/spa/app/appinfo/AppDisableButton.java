package com.android.settings.spa.app.appinfo;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.UserManager;

import com.android.settings.applications.ApplicationFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppDisableButton {
    public final AppButtonRepository appButtonRepository;
    public final ApplicationFeatureProviderImpl applicationFeatureProvider;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final PackageInfoPresenter packageInfoPresenter;
    public final PackageManager packageManager;
    public final Resources resources;
    public final UserManager userManager;

    public AppDisableButton(PackageInfoPresenter packageInfoPresenter) {
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        this.packageInfoPresenter = packageInfoPresenter;
        Context context = packageInfoPresenter.context;
        this.context = context;
        this.appButtonRepository = new AppButtonRepository(context);
        this.resources = context.getResources();
        this.packageManager = context.getPackageManager();
        this.userManager = ContextsKt.getUserManager(context);
        this.devicePolicyManager = ContextsKt.getDevicePolicyManager(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.applicationFeatureProvider = featureFactoryImpl.getApplicationFeatureProvider();
    }
}
