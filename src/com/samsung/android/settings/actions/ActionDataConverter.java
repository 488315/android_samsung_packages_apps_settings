package com.samsung.android.settings.actions;

import android.content.Context;
import android.provider.SearchIndexableData;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.search.SearchIndexableRaw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionDataConverter {
    public Context mContext;
    public HashMap mIndexableDataMap;

    public static List uniqueActionData(List list, List list2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = ((ArrayList) list2).iterator();
        while (it.hasNext()) {
            ActionData actionData = (ActionData) it.next();
            if (!arrayList2.contains(actionData.mKey)) {
                arrayList2.add(actionData.mKey);
                arrayList.add(actionData);
            }
        }
        Iterator it2 = ((ArrayList) list).iterator();
        while (it2.hasNext()) {
            ActionData actionData2 = (ActionData) it2.next();
            if (!arrayList2.contains(actionData2.mKey)) {
                arrayList2.add(actionData2.mKey);
                arrayList.add(actionData2);
            }
        }
        return arrayList;
    }

    public final void addIndexableToMap(SearchIndexableRaw searchIndexableRaw) {
        if (TextUtils.isEmpty(((SearchIndexableData) searchIndexableRaw).key)
                || TextUtils.isEmpty(searchIndexableRaw.title)) {
            throw new ActionData.InvalidActionDataException(
                    "Both Key and Title should not be null");
        }
        String str = ((SearchIndexableData) searchIndexableRaw).key;
        if (isIndexed(str)) {
            this.mIndexableDataMap.remove(str);
        }
        if (this.mIndexableDataMap.containsKey(((SearchIndexableData) searchIndexableRaw).key)) {
            return;
        }
        this.mIndexableDataMap.put(
                ((SearchIndexableData) searchIndexableRaw).key, searchIndexableRaw);
    }

    public final ActionData buildActionData(
            String str, String str2, BasePreferenceController basePreferenceController) {
        if (!isIndexed(str)) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "Cannot find a indexable for : ", str, "Command/ActionDataConverter");
            return null;
        }
        if (basePreferenceController == null || !basePreferenceController.isControllable()) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "Not controllable : ", str, "Command/ActionDataConverter");
            return null;
        }
        SearchIndexableRaw searchIndexableRaw =
                isIndexed(str) ? (SearchIndexableRaw) this.mIndexableDataMap.get(str) : null;
        String str3 = searchIndexableRaw.screenTitle;
        String str4 = searchIndexableRaw.title;
        try {
            str4 = this.mContext.getString(Integer.parseInt(str4));
        } catch (NumberFormatException unused) {
        }
        String str5 = searchIndexableRaw.summaryOn;
        int i = ((SearchIndexableData) searchIndexableRaw).iconResId;
        int controlType = basePreferenceController.getControlType();
        if (TextUtils.isEmpty(str)) {
            Log.d("Command/ActionDataConverter", "Key cannot be empty : " + str2 + ", " + str4);
            return null;
        }
        if (TextUtils.isEmpty(str4)) {
            Log.d("Command/ActionDataConverter", "Title cannot be empty : " + str2 + ", " + str);
            return null;
        }
        ActionData.Builder builder = new ActionData.Builder();
        builder.mKey = str;
        builder.mTitle = str4;
        builder.mSummary = str5;
        builder.mIconResource = i;
        builder.mScreenTitle = str3;
        if (TextUtils.isEmpty(str2)) {
            str2 = ((SearchIndexableData) searchIndexableRaw).className;
        }
        builder.mFragmentClassName = str2;
        builder.mPrefControllerClassName = basePreferenceController.getClass().getName();
        builder.mActionType = controlType;
        return builder.build();
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x01ed, code lost:

       if (r11 == null) goto L82;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01b4, code lost:

       if (r11 != null) goto L75;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getActionDataFromProvider(
            com.android.settingslib.search.Indexable$SearchIndexProvider r20,
            java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 733
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.actions.ActionDataConverter.getActionDataFromProvider(com.android.settingslib.search.Indexable$SearchIndexProvider,"
                    + " java.lang.String):java.util.List");
    }

    public final boolean isIndexed(String str) {
        return this.mIndexableDataMap.containsKey(str) && this.mIndexableDataMap.get(str) != null;
    }
}
