package com.android.settingslib.spaprivileged.model.enterprise;

import android.app.admin.DevicePolicyResourcesManager;
import android.content.Context;

import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnterpriseRepository {
    public final Context context;
    public final Lazy resources$delegate;

    public EnterpriseRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.resources$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settingslib.spaprivileged.model.enterprise.EnterpriseRepository$resources$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return ContextsKt.getDevicePolicyManager(
                                                EnterpriseRepository.this.context)
                                        .getResources();
                            }
                        });
    }

    public final String getEnterpriseString(final int i, String str) {
        String string =
                ((DevicePolicyResourcesManager) this.resources$delegate.getValue())
                        .getString(
                                str,
                                new Supplier() { // from class:
                                                 // com.android.settingslib.spaprivileged.model.enterprise.EnterpriseRepository$getEnterpriseString$1
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return EnterpriseRepository.this.context.getString(i);
                                    }
                                });
        if (string != null) {
            return string;
        }
        throw new IllegalStateException("Required value was null.".toString());
    }
}
