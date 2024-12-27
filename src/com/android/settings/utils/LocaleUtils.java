package com.android.settings.utils;

import android.icu.text.ListFormatter;
import android.text.TextUtils;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LocaleUtils {
    public static CharSequence getConcatenatedString(List list) {
        ListFormatter listFormatter = ListFormatter.getInstance(Locale.getDefault());
        ArrayList arrayList = (ArrayList) list;
        CharSequence charSequence =
                (CharSequence) AlertController$$ExternalSyntheticOutline0.m(1, arrayList);
        arrayList.add("fake last item");
        String format = listFormatter.format(list);
        return format.subSequence(
                0, charSequence.length() + TextUtils.indexOf(format, charSequence));
    }
}
