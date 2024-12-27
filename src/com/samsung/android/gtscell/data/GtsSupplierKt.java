package com.samsung.android.gtscell.data;

import com.sec.ims.IMSParameter;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\u001a9\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0016\b\u0004\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00030\u0005H\u0086\b¨\u0006\u0006"
        },
        d2 = {
            "gtsSupplier",
            "Lcom/samsung/android/gtscell/data/GtsSupplier;",
            "T",
            "R",
            IMSParameter.CALL.ACTION,
            "Lkotlin/Function1;",
            "gtscell_release"
        },
        k = 2,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsSupplierKt {
    public static final <T, R> GtsSupplier<T, R> gtsSupplier(final Function1 action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return new GtsSupplier<
                T,
                R>() { // from class: com.samsung.android.gtscell.data.GtsSupplierKt$gtsSupplier$1
            @Override // com.samsung.android.gtscell.data.GtsSupplier
            public R get(T t) {
                return (R) Function1.this.invoke(t);
            }
        };
    }
}
