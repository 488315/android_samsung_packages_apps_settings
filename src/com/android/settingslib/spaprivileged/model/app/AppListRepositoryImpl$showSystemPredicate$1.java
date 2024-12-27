package com.android.settingslib.spaprivileged.model.app;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class AppListRepositoryImpl$showSystemPredicate$1
        extends FunctionReferenceImpl implements Function3 {
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return AppListRepositoryImpl.access$showSystemPredicate(
                (AppListRepositoryImpl) this.receiver,
                ((Number) obj).intValue(),
                ((Boolean) obj2).booleanValue(),
                (Continuation) obj3);
    }
}
