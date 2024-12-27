package com.android.settings.spa.app.appinfo;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class AppDisableButton$confirmDialogPresenter$1 extends FunctionReferenceImpl
        implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final /* bridge */ /* synthetic */ Object mo1068invoke() {
        m1010invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: collision with other method in class */
    public final void m1010invoke() {
        PackageInfoPresenter packageInfoPresenter = (PackageInfoPresenter) this.receiver;
        packageInfoPresenter.logAction(874);
        BuildersKt.launch$default(
                packageInfoPresenter.coroutineScope,
                Dispatchers.IO,
                null,
                new PackageInfoPresenter$disable$1(packageInfoPresenter, null),
                2);
    }
}
