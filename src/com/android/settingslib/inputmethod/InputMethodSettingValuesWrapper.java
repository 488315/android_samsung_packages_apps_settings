package com.android.settingslib.inputmethod;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InputMethodSettingValuesWrapper {
    public final ContentResolver mContentResolver;
    public final InputMethodManager mImm;
    public final ArrayList mMethodList = new ArrayList();
    public static final Object sInstanceMapLock = new Object();
    public static final SparseArray sInstanceMap = new SparseArray();

    public InputMethodSettingValuesWrapper(Context context) {
        this.mContentResolver = context.getContentResolver();
        this.mImm = (InputMethodManager) context.getSystemService(InputMethodManager.class);
        refreshAllInputMethodAndSubtypes();
    }

    public static InputMethodSettingValuesWrapper getInstance(Context context) {
        int userId = context.getUserId();
        synchronized (sInstanceMapLock) {
            try {
                SparseArray sparseArray = sInstanceMap;
                if (sparseArray.size() == 0) {
                    InputMethodSettingValuesWrapper inputMethodSettingValuesWrapper =
                            new InputMethodSettingValuesWrapper(context);
                    sparseArray.put(userId, inputMethodSettingValuesWrapper);
                    return inputMethodSettingValuesWrapper;
                }
                if (sparseArray.indexOfKey(userId) >= 0) {
                    return (InputMethodSettingValuesWrapper) sparseArray.get(userId);
                }
                InputMethodSettingValuesWrapper inputMethodSettingValuesWrapper2 =
                        new InputMethodSettingValuesWrapper(context);
                sparseArray.put(context.getUserId(), inputMethodSettingValuesWrapper2);
                return inputMethodSettingValuesWrapper2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ArrayList getEnabledInputMethodList() {
        ContentResolver contentResolver = this.mContentResolver;
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                InputMethodAndSubtypeUtil.sStringInputMethodSplitter;
        String string = Settings.Secure.getString(contentResolver, "enabled_input_methods");
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(string)) {
            InputMethodAndSubtypeUtil.sStringInputMethodSplitter.setString(string);
            while (true) {
                TextUtils.SimpleStringSplitter simpleStringSplitter2 =
                        InputMethodAndSubtypeUtil.sStringInputMethodSplitter;
                if (!simpleStringSplitter2.hasNext()) {
                    break;
                }
                String next = simpleStringSplitter2.next();
                TextUtils.SimpleStringSplitter simpleStringSplitter3 =
                        InputMethodAndSubtypeUtil.sStringInputMethodSubtypeSplitter;
                simpleStringSplitter3.setString(next);
                if (simpleStringSplitter3.hasNext()) {
                    HashSet hashSet = new HashSet();
                    String next2 = simpleStringSplitter3.next();
                    while (true) {
                        TextUtils.SimpleStringSplitter simpleStringSplitter4 =
                                InputMethodAndSubtypeUtil.sStringInputMethodSubtypeSplitter;
                        if (!simpleStringSplitter4.hasNext()) {
                            break;
                        }
                        hashSet.add(simpleStringSplitter4.next());
                    }
                    hashMap.put(next2, hashSet);
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mMethodList.iterator();
        while (it.hasNext()) {
            InputMethodInfo inputMethodInfo = (InputMethodInfo) it.next();
            if (hashMap.keySet().contains(inputMethodInfo.getId())) {
                arrayList.add(inputMethodInfo);
            }
        }
        return arrayList;
    }

    public final boolean isAlwaysCheckedIme(InputMethodInfo inputMethodInfo) {
        boolean isEnabledImi = isEnabledImi(inputMethodInfo);
        if (getEnabledInputMethodList().size() <= 1 && isEnabledImi) {
            return true;
        }
        Iterator it = getEnabledInputMethodList().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (InputMethodAndSubtypeUtil.isValidNonAuxAsciiCapableIme(
                    (InputMethodInfo) it.next())) {
                i++;
            }
        }
        if (i == 0) {
            Log.w(
                    "InputMethodSettingValuesWrapper",
                    "No \"enabledValidNonAuxAsciiCapableIme\"s found.");
        }
        return i <= 1
                && (i != 1 || isEnabledImi)
                && inputMethodInfo.isSystem()
                && InputMethodAndSubtypeUtil.isValidNonAuxAsciiCapableIme(inputMethodInfo);
    }

    public final boolean isEnabledImi(InputMethodInfo inputMethodInfo) {
        Iterator it = getEnabledInputMethodList().iterator();
        while (it.hasNext()) {
            if (((InputMethodInfo) it.next()).getId().equals(inputMethodInfo.getId())) {
                return true;
            }
        }
        return false;
    }

    public final void refreshAllInputMethodAndSubtypes() {
        this.mMethodList.clear();
        List inputMethodListAsUser =
                this.mImm.getInputMethodListAsUser(this.mContentResolver.getUserId(), 1);
        for (int i = 0; i < inputMethodListAsUser.size(); i++) {
            InputMethodInfo inputMethodInfo = (InputMethodInfo) inputMethodListAsUser.get(i);
            if (!inputMethodInfo.isVirtualDeviceOnly()) {
                this.mMethodList.add(inputMethodInfo);
            }
        }
    }
}
