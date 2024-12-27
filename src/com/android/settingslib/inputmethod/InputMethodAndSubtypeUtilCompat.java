package com.android.settingslib.inputmethod;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;

import java.util.HashMap;
import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class InputMethodAndSubtypeUtilCompat {
    public static final TextUtils.SimpleStringSplitter sStringInputMethodSplitter =
            new TextUtils.SimpleStringSplitter(':');
    public static final TextUtils.SimpleStringSplitter sStringInputMethodSubtypeSplitter =
            new TextUtils.SimpleStringSplitter(';');

    public static HashMap getEnabledInputMethodsAndSubtypeList(ContentResolver contentResolver) {
        String string = Settings.Secure.getString(contentResolver, "enabled_input_methods");
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(string)) {
            sStringInputMethodSplitter.setString(string);
            while (true) {
                TextUtils.SimpleStringSplitter simpleStringSplitter = sStringInputMethodSplitter;
                if (!simpleStringSplitter.hasNext()) {
                    break;
                }
                String next = simpleStringSplitter.next();
                TextUtils.SimpleStringSplitter simpleStringSplitter2 =
                        sStringInputMethodSubtypeSplitter;
                simpleStringSplitter2.setString(next);
                if (simpleStringSplitter2.hasNext()) {
                    HashSet hashSet = new HashSet();
                    String next2 = simpleStringSplitter2.next();
                    while (true) {
                        TextUtils.SimpleStringSplitter simpleStringSplitter3 =
                                sStringInputMethodSubtypeSplitter;
                        if (!simpleStringSplitter3.hasNext()) {
                            break;
                        }
                        hashSet.add(simpleStringSplitter3.next());
                    }
                    hashMap.put(next2, hashSet);
                }
            }
        }
        return hashMap;
    }

    public static void removeUnnecessaryNonPersistentPreference(Preference preference) {
        SharedPreferences sharedPreferences;
        String key = preference.getKey();
        if (preference.isPersistent()
                || key == null
                || (sharedPreferences = preference.getSharedPreferences()) == null
                || !sharedPreferences.contains(key)) {
            return;
        }
        sharedPreferences.edit().remove(key).apply();
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x01cd, code lost:

       if (r5 != (-1)) goto L97;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void saveInputMethodSubtypeListForUserInternal(
            androidx.preference.PreferenceFragmentCompat r25,
            android.content.ContentResolver r26,
            java.util.List r27,
            boolean r28,
            int r29) {
        /*
            Method dump skipped, instructions count: 494
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.inputmethod.InputMethodAndSubtypeUtilCompat.saveInputMethodSubtypeListForUserInternal(androidx.preference.PreferenceFragmentCompat,"
                    + " android.content.ContentResolver, java.util.List, boolean, int):void");
    }
}
