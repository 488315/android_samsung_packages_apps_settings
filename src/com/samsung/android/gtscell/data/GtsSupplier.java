package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0004\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003J\u0017\u0010\u0004\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0005\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0006¨\u0006\u0007"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsSupplier;",
            "T",
            "R",
            ApnSettings.MVNO_NONE,
            "get",
            "t",
            "(Ljava/lang/Object;)Ljava/lang/Object;",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public interface GtsSupplier<T, R> {
    R get(T t);
}
