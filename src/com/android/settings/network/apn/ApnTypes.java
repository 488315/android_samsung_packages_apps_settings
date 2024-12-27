package com.android.settings.network.apn;

import androidx.compose.ui.text.intl.Locale;
import androidx.compose.ui.text.intl.PlatformLocaleKt;

import com.sec.ims.settings.ImsProfile;

import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt___StringsKt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ApnTypes {
    public static final String[] APN_TYPES = {
        "default",
        "mms",
        "supl",
        "dun",
        "hipri",
        "fota",
        ImsProfile.PDN_IMS,
        "cbs",
        "ia",
        ImsProfile.PDN_EMERGENCY,
        "mcx",
        ImsProfile.PDN_XCAP,
        "vsim",
        "bip",
        "enterprise"
    };
    public static final Set PreSelectedTypes =
            SetsKt.setOf(
                    (Object[])
                            new String[] {
                                "default",
                                "mms",
                                "supl",
                                "dun",
                                "hipri",
                                "fota",
                                "cbs",
                                ImsProfile.PDN_XCAP
                            });

    public static List splitToList(String str) {
        List split$default = StringsKt___StringsKt.split$default(str, new char[] {','});
        ArrayList arrayList =
                new ArrayList(
                        CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
        Iterator it = split$default.iterator();
        while (it.hasNext()) {
            String lowerCase =
                    StringsKt___StringsKt.trim((String) it.next())
                            .toString()
                            .toLowerCase(
                                    ((Locale)
                                                    PlatformLocaleKt.platformLocaleDelegate
                                                            .getCurrent()
                                                            .localeList
                                                            .get(0))
                                            .platformLocale);
            Intrinsics.checkNotNullExpressionValue(
                    lowerCase, "this as java.lang.String).toLowerCase(locale)");
            arrayList.add(lowerCase);
        }
        if (!arrayList.contains("*")) {
            String[] strArr = APN_TYPES;
            for (int i = 0; i < 15; i++) {
                if (!arrayList.contains(strArr[i])) {
                    ArrayList arrayList2 = new ArrayList();
                    for (int i2 = 0; i2 < 15; i2++) {
                        String str2 = strArr[i2];
                        if (arrayList.contains(str2)) {
                            arrayList2.add(str2);
                        }
                    }
                    return arrayList2;
                }
            }
        }
        return CollectionsKt__CollectionsJVMKt.listOf("*");
    }
}
