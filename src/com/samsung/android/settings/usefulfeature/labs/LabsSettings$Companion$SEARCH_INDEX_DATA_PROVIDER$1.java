package com.samsung.android.settings.usefulfeature.labs;

import android.content.Context;
import android.provider.SearchIndexableResource;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LabsSettings$Companion$SEARCH_INDEX_DATA_PROVIDER$1
        extends BaseSearchIndexProvider {
    @Override // com.android.settings.search.BaseSearchIndexProvider,
              // com.android.settingslib.search.Indexable$SearchIndexProvider
    public final List getXmlResourcesToIndex(Context context) {
        ArrayList arrayList = new ArrayList();
        SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
        searchIndexableResource.className = LabsSettings.class.getName();
        searchIndexableResource.xmlResId = R.xml.sec_useful_feature_labs;
        arrayList.add(searchIndexableResource);
        return arrayList;
    }
}
