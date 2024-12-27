package com.android.settings.network.apn;

import android.content.Context;

import com.android.settings.R;

import com.samsung.android.knox.accounts.HostAuth;

import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.Locale;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ApnRepositoryKt {
    public static final String[] Projection = {
        "_id",
        "name",
        "apn",
        "proxy",
        HostAuth.PORT,
        "user",
        "server",
        HostAuth.PASSWORD,
        "mmsc",
        "mmsproxy",
        "mmsport",
        "authtype",
        "type",
        "protocol",
        "carrier_enabled",
        "network_type_bitmask",
        "roaming_protocol",
        "edited",
        "user_editable"
    };
    public static final Set NonDuplicatedKeys =
            SetsKt.setOf(
                    (Object[])
                            new String[] {
                                "apn",
                                "proxy",
                                HostAuth.PORT,
                                "mmsc",
                                "mmsproxy",
                                "mmsport",
                                "protocol",
                                "roaming_protocol"
                            });

    public static final String convertOptions2Protocol(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        String[] stringArray = context.getResources().getStringArray(R.array.apn_protocol_values);
        Intrinsics.checkNotNullExpressionValue(stringArray, "getStringArray(...)");
        return (i < 0 || i > stringArray.length + (-1))
                ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE
                : stringArray[i];
    }

    public static final int convertProtocol2Options(Context context, String str) {
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
        String upperCase = str.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        if (upperCase.equals("IPV4")) {
            upperCase = com.samsung.android.knox.net.apn.ApnSettings.PROTOCOL_IPV4;
        }
        String[] stringArray = context.getResources().getStringArray(R.array.apn_protocol_values);
        Intrinsics.checkNotNullExpressionValue(stringArray, "getStringArray(...)");
        return ArraysKt___ArraysKt.indexOf(upperCase, stringArray);
    }
}
