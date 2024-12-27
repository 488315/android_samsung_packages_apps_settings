package com.android.settings.spa.app.appcompat;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.android.settings.applications.appcompat.UserAspectRatioDetails;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class UserAspectRatioAppPreferenceKt$UserAspectRatioAppPreference$2$onClick$1
        extends FunctionReferenceImpl implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final Object mo1068invoke() {
        UserAspectRatioAppPresenter userAspectRatioAppPresenter =
                (UserAspectRatioAppPresenter) this.receiver;
        Context context = userAspectRatioAppPresenter.context;
        ApplicationInfo app = userAspectRatioAppPresenter.app;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        AppInfoDashboardFragment.startAppInfoFragment(
                UserAspectRatioDetails.class, app, context, 20);
        return Unit.INSTANCE;
    }
}
