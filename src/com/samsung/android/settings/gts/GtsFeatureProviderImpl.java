package com.samsung.android.settings.gts;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.GtsIndexableData;
import com.android.settingslib.search.GtsIndexableResources;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GtsFeatureProviderImpl {
    public final Map mResourceMap = new LinkedHashMap();

    public GtsFeatureProviderImpl() {
        Iterator it = ((HashSet) new GtsIndexableResources().mProviders).iterator();
        while (it.hasNext()) {
            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                    ((GtsIndexableData) it.next()).mSearchIndexProvider;
            if (indexable$SearchIndexProvider instanceof BaseSearchIndexProvider) {
                ((BaseSearchIndexProvider) indexable$SearchIndexProvider)
                        .getGtsResourceGroup()
                        .forEach(
                                new BiConsumer() { // from class:
                                                   // com.samsung.android.settings.gts.GtsFeatureProviderImpl$$ExternalSyntheticLambda0
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        GtsGroup gtsGroup;
                                        String str = (String) obj;
                                        String str2 = (String) obj2;
                                        Map map = GtsFeatureProviderImpl.this.mResourceMap;
                                        GtsGroup[] values = GtsGroup.values();
                                        int length = values.length;
                                        int i = 0;
                                        while (true) {
                                            if (i >= length) {
                                                gtsGroup = null;
                                                break;
                                            }
                                            gtsGroup = values[i];
                                            if (gtsGroup.getGroupName().equals(str2)) {
                                                break;
                                            } else {
                                                i++;
                                            }
                                        }
                                        map.put(str, gtsGroup);
                                    }
                                });
                ((LinkedHashMap) this.mResourceMap)
                        .forEach(new GtsFeatureProviderImpl$$ExternalSyntheticLambda1());
            }
        }
    }
}
