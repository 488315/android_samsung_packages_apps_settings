package com.android.settingslib.spa.framework.util;

import android.content.Context;
import android.content.res.Resources;
import android.icu.text.MessageFormat;

import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MessageFormatsKt {
    public static final String formatString(Context context, int i, Pair... arguments) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Resources resources = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        Pair[] arguments2 = (Pair[]) Arrays.copyOf(arguments, arguments.length);
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        String format =
                new MessageFormat(resources.getString(i), Locale.getDefault(Locale.Category.FORMAT))
                        .format(
                                MapsKt__MapsKt.mapOf(
                                        (Pair[]) Arrays.copyOf(arguments2, arguments2.length)));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }
}
