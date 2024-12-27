package com.android.settingslib.spa.framework.util;

import android.os.Bundle;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.navigation.NamedNavArgument;
import androidx.navigation.NavType;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ParameterKt {
    public static final String navLink(Bundle bundle, List list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return CollectionsKt___CollectionsKt.joinToString$default(
                normalizeArgList(list, bundle, false),
                ApnSettings.MVNO_NONE,
                null,
                null,
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.util.ParameterKt$navLink$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        String arg = (String) obj;
                        Intrinsics.checkNotNullParameter(arg, "arg");
                        return "/".concat(arg);
                    }
                },
                30);
    }

    public static final String navRoute(List list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return CollectionsKt___CollectionsKt.joinToString$default(
                list,
                ApnSettings.MVNO_NONE,
                null,
                null,
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.util.ParameterKt$navRoute$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        NamedNavArgument argument = (NamedNavArgument) obj;
                        Intrinsics.checkNotNullParameter(argument, "argument");
                        return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                new StringBuilder("/{"), argument.name, "}");
                    }
                },
                30);
    }

    public static final Bundle normalize(List list, Bundle bundle, boolean z) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        if (list.isEmpty()) {
            return null;
        }
        Bundle bundle2 = new Bundle();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NamedNavArgument namedNavArgument = (NamedNavArgument) it.next();
            if (z) {
                Intrinsics.checkNotNullParameter(namedNavArgument, "<this>");
                String str = namedNavArgument.name;
                if (StringsKt__StringsJVMKt.startsWith$default(str, "rt_")) {
                    bundle2.putString(str, null);
                }
            }
            NavType navType = namedNavArgument.argument.type;
            boolean areEqual = Intrinsics.areEqual(navType, NavType.StringType);
            String str2 = namedNavArgument.name;
            if (areEqual) {
                String string = bundle != null ? bundle.getString(str2) : null;
                if (string != null) {
                    bundle2.putString(str2, string);
                } else {
                    bundle2.putString("unset_" + str2, null);
                }
            } else if (Intrinsics.areEqual(navType, NavType.IntType)) {
                if (bundle == null || !bundle.containsKey(str2)) {
                    bundle2.putString("unset_" + str2, null);
                } else {
                    bundle2.putInt(str2, bundle.getInt(str2));
                }
            }
        }
        return bundle2;
    }

    public static final List normalizeArgList(List list, Bundle bundle, boolean z) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NamedNavArgument namedNavArgument = (NamedNavArgument) it.next();
            if (z) {
                Intrinsics.checkNotNullParameter(namedNavArgument, "<this>");
                if (!StringsKt__StringsJVMKt.startsWith$default(namedNavArgument.name, "rt_")) {}
            }
            if (bundle == null || !bundle.containsKey(namedNavArgument.name)) {
                arrayList.add("[unset]");
            } else {
                NavType navType = namedNavArgument.argument.type;
                boolean areEqual = Intrinsics.areEqual(navType, NavType.StringType);
                String str = namedNavArgument.name;
                if (areEqual) {
                    String string = bundle.getString(str, ApnSettings.MVNO_NONE);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    arrayList.add(string);
                } else if (Intrinsics.areEqual(navType, NavType.IntType)) {
                    arrayList.add(String.valueOf(bundle.getInt(str)));
                }
            }
        }
        return arrayList;
    }
}
