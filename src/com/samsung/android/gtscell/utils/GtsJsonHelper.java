package com.samsung.android.gtscell.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000\"\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0006\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J)\u0010\u0005\u001a\u0004\u0018\u0001H\u0006\"\u0004\b\u0000\u0010\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010"
                + "\t\u001a\b\u0012\u0004\u0012\u0002H\u00060\n"
                + "¢\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\b\"\u0004\b\u0000\u0010\u00062\u0006\u0010\r"
                + "\u001a\u0002H\u0006¢\u0006\u0002\u0010\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n"
                + "\u0000¨\u0006\u000f"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/utils/GtsJsonHelper;",
            ApnSettings.MVNO_NONE,
            "()V",
            "JSON_VERSION",
            ApnSettings.MVNO_NONE,
            "fromJson",
            "T",
            "json",
            ApnSettings.MVNO_NONE,
            "classOfT",
            "Ljava/lang/Class;",
            "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;",
            "toJson",
            NetworkAnalyticsConstants.DataPoints.SOURCE_IP,
            "(Ljava/lang/Object;)Ljava/lang/String;",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsJsonHelper {
    public static final GtsJsonHelper INSTANCE = new GtsJsonHelper();
    private static final double JSON_VERSION = 1.0d;

    private GtsJsonHelper() {}

    public final <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        Intrinsics.checkParameterIsNotNull(json, "json");
        Intrinsics.checkParameterIsNotNull(classOfT, "classOfT");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setVersion();
        gsonBuilder.prettyPrinting = true;
        return (T) gsonBuilder.create().fromJson(json, (Class) classOfT);
    }

    public final <T> String toJson(T src) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setVersion();
        gsonBuilder.prettyPrinting = true;
        String json = gsonBuilder.create().toJson(src);
        Intrinsics.checkExpressionValueIsNotNull(json, "builder.create().toJson(src)");
        return json;
    }
}
