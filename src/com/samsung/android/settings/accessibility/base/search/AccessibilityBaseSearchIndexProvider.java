package com.samsung.android.settings.accessibility.base.search;

import android.content.Context;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityBaseSearchIndexProvider extends BaseSearchIndexProvider {
    public final String parentFragmentName;
    public final String parentPrefKey;

    public AccessibilityBaseSearchIndexProvider(int i, String str, String str2) {
        super(i);
        this.parentFragmentName = str;
        this.parentPrefKey = str2;
    }

    public static boolean retrieveParentIndexProviders(Context context, String str, String str2) {
        Indexable$SearchIndexProvider indexable$SearchIndexProvider = null;
        try {
            indexable$SearchIndexProvider =
                    (Indexable$SearchIndexProvider)
                            Class.forName(str).getField("SEARCH_INDEX_DATA_PROVIDER").get(null);
        } catch (ClassNotFoundException e) {
            SemLog.w(
                    "A11ySearchIndexProvider",
                    "getParentSearchIndexProvider - Class not exists : " + str,
                    e);
        } catch (IllegalAccessException e2) {
            SemLog.w(
                    "A11ySearchIndexProvider",
                    "getParentSearchIndexProvider - cannot access field : " + str,
                    e2);
        } catch (NoSuchFieldException e3) {
            SemLog.w(
                    "A11ySearchIndexProvider",
                    "getParentSearchIndexProvider - Field not exists",
                    e3);
        }
        if (indexable$SearchIndexProvider == null) {
            return true;
        }
        if (indexable$SearchIndexProvider.getNonIndexableKeys(context).contains(str2)) {
            return false;
        }
        if (!(indexable$SearchIndexProvider instanceof AccessibilityBaseSearchIndexProvider)) {
            return true;
        }
        AccessibilityBaseSearchIndexProvider accessibilityBaseSearchIndexProvider =
                (AccessibilityBaseSearchIndexProvider) indexable$SearchIndexProvider;
        return retrieveParentIndexProviders(
                context,
                accessibilityBaseSearchIndexProvider.parentFragmentName,
                accessibilityBaseSearchIndexProvider.parentPrefKey);
    }

    @Override // com.android.settings.search.BaseSearchIndexProvider
    public final boolean isPageSearchEnabled(Context context) {
        String str;
        String str2 = this.parentFragmentName;
        if (str2 == null || (str = this.parentPrefKey) == null) {
            return true;
        }
        return retrieveParentIndexProviders(context, str2, str);
    }
}
