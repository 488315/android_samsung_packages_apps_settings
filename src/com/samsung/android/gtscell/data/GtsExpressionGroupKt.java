package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000\u0018\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¨\u0006\u0007"
        },
        d2 = {
            "of",
            "Lcom/samsung/android/gtscell/data/GtsExpressionGroup;",
            "name",
            ApnSettings.MVNO_NONE,
            "expressions",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/gtscell/data/GtsExpressionRaw;",
            "gtscell_release"
        },
        k = 2,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsExpressionGroupKt {
    public static final GtsExpressionGroup of(String name, List<GtsExpressionRaw> expressions) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(expressions, "expressions");
        return new GtsExpressionGroup(name, expressions, 0, 4, null);
    }
}
