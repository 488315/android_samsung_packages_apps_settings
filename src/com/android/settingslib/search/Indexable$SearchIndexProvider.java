package com.android.settingslib.search;

import android.content.Context;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface Indexable$SearchIndexProvider {
    List getDynamicRawDataToIndex(Context context);

    List getNonIndexableKeys(Context context);

    List getRawDataToIndex(Context context);

    List getXmlResourcesToIndex(Context context);
}
