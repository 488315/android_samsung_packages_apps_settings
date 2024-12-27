package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;

import androidx.lifecycle.CoroutineLiveData;
import androidx.lifecycle.CoroutineLiveDataKt;

import com.android.settings.applications.ApplicationFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppTimeSpentPresenter {
    public final ApplicationInfo app;
    public final ApplicationFeatureProviderImpl appFeatureProvider;
    public final Context context;
    public final Intent intent;
    public final CoroutineLiveData summaryLiveData;

    public AppTimeSpentPresenter(Context context, ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        this.context = context;
        this.app = app;
        Intent intent = new Intent("android.settings.action.APP_USAGE_SETTINGS");
        intent.putExtra("android.intent.extra.PACKAGE_NAME", app.packageName);
        this.intent = intent;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.appFeatureProvider = featureFactoryImpl.getApplicationFeatureProvider();
        this.summaryLiveData =
                CoroutineLiveDataKt.liveData$default(
                        Dispatchers.IO, new AppTimeSpentPresenter$summaryLiveData$1(this, null), 2);
    }
}
