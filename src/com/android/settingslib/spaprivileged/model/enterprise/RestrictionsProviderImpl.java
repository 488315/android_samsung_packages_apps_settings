package com.android.settingslib.spaprivileged.model.enterprise;

import android.content.Context;
import android.os.UserManager;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RestrictionsProviderImpl {
    public final Context context;
    public final Flow restrictedMode;
    public final Restrictions restrictions;
    public final Lazy userManager$delegate;

    public RestrictionsProviderImpl(Context context, Restrictions restrictions) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(restrictions, "restrictions");
        this.context = context;
        this.restrictions = restrictions;
        this.userManager$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settingslib.spaprivileged.model.enterprise.RestrictionsProviderImpl$userManager$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return UserManager.get(RestrictionsProviderImpl.this.context);
                            }
                        });
        this.restrictedMode =
                FlowKt.flowOn(
                        new SafeFlow(new RestrictionsProviderImpl$restrictedMode$1(this, null)),
                        Dispatchers.IO);
    }
}
