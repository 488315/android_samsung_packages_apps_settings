package com.android.settings.network.apn;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import com.android.settings.R;

import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ApnStatusKt {
    public static final String validateApnData(ApnData apnData, Context context) {
        String str;
        Intrinsics.checkNotNullParameter(apnData, "apnData");
        Intrinsics.checkNotNullParameter(context, "context");
        if (apnData.name.length() == 0) {
            str = context.getResources().getString(R.string.error_name_empty);
        } else if (apnData.apn.length() == 0) {
            str = context.getResources().getString(R.string.error_apn_empty);
        } else if (apnData.apnType.length() == 0) {
            str = context.getResources().getString(R.string.error_apn_type_empty);
        } else {
            String validateMMSC = validateMMSC(context, apnData.mmsc, true);
            if (validateMMSC == null) {
                String[] strArr = ApnRepositoryKt.Projection;
                Map contentValueMap = apnData.getContentValueMap(context);
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : contentValueMap.entrySet()) {
                    if (ApnRepositoryKt.NonDuplicatedKeys.contains((String) entry.getKey())) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                LinkedHashMap linkedHashMap2 =
                        new LinkedHashMap(MapsKt__MapsKt.mapCapacity(linkedHashMap.size()));
                for (Map.Entry entry2 : linkedHashMap.entrySet()) {
                    linkedHashMap2.put(entry2.getKey() + " = ?", entry2.getValue());
                }
                Map mutableMap = MapsKt__MapsKt.toMutableMap(linkedHashMap2);
                int i = apnData.id;
                if (i != -1) {
                    Pair pair = new Pair("_id != ?", Integer.valueOf(i));
                    mutableMap.put(pair.getFirst(), pair.getSecond());
                }
                List list =
                        CollectionsKt___CollectionsKt.toList(
                                ((LinkedHashMap) mutableMap).entrySet());
                String joinToString$default =
                        CollectionsKt___CollectionsKt.joinToString$default(
                                list,
                                " AND ",
                                null,
                                null,
                                new Function1() { // from class:
                                                  // com.android.settings.network.apn.ApnRepositoryKt$isItemExist$selection$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        Map.Entry it = (Map.Entry) obj;
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        return (CharSequence) it.getKey();
                                    }
                                },
                                30);
                ArrayList arrayList =
                        new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(((Map.Entry) it.next()).getValue().toString());
                }
                Cursor query =
                        context.getContentResolver()
                                .query(
                                        Uri.withAppendedPath(
                                                Telephony.Carriers.SIM_APN_URI,
                                                String.valueOf(apnData.subId)),
                                        new String[0],
                                        joinToString$default,
                                        (String[]) arrayList.toArray(new String[0]),
                                        null);
                if (query != null) {
                    try {
                        if (query.getCount() > 0) {
                            String string =
                                    context.getResources()
                                            .getString(R.string.error_duplicate_apn_entry);
                            CloseableKt.closeFinally(query, null);
                            str = string;
                        } else {
                            CloseableKt.closeFinally(query, null);
                        }
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(query, th);
                            throw th2;
                        }
                    }
                }
                str = null;
            } else {
                str = validateMMSC;
            }
        }
        if (str == null) {
            return null;
        }
        Log.d("ApnStatus", "APN data not valid, reason: ".concat(str));
        return str;
    }

    public static final String validateMMSC(Context context, String mmsc, boolean z) {
        Intrinsics.checkNotNullParameter(mmsc, "mmsc");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!z
                || mmsc.equals(com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE)
                || new Regex("^https?:\\/\\/.+").matches(mmsc)) {
            return null;
        }
        return context.getResources().getString(R.string.error_mmsc_valid);
    }
}
