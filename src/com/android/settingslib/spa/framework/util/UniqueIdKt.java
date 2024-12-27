package com.android.settingslib.spa.framework.util;

import android.os.Bundle;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class UniqueIdKt {
    public static final String genPageId(String sppName, List parameter, Bundle bundle) {
        Intrinsics.checkNotNullParameter(sppName, "sppName");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Bundle normalize = ParameterKt.normalize(parameter, bundle, true);
        String bundle2 = normalize != null ? normalize.toString() : null;
        CharsKt.checkRadix(36);
        String l = Long.toString((sppName + ":" + bundle2).hashCode() & 4294967295L, 36);
        Intrinsics.checkNotNullExpressionValue(l, "toString(...)");
        return l;
    }
}
