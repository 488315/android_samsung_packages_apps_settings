package com.samsung.android.settings.analyzestorage.external.injection;

import android.app.Application;

import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;

import java.util.function.BiFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ControllerFactory$$ExternalSyntheticLambda0
        implements BiFunction {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        return new AppListController((Application) obj, ((Integer) obj2).intValue(), -1, null);
    }
}
