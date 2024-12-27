package com.samsung.android.settings.gts;

import android.util.Log;

import com.samsung.android.gtscell.GtsCellProvider;
import com.samsung.android.gtscell.ResultCallback;
import com.samsung.android.gtscell.data.GtsConfiguration;
import com.samsung.android.gtscell.data.GtsExpressionBuilder;
import com.samsung.android.gtscell.data.GtsItem;
import com.samsung.android.gtscell.data.GtsItemBuilder;
import com.samsung.android.gtscell.data.GtsItemSupplier;
import com.samsung.android.gtscell.data.GtsItemSupplierGroupBuilder;
import com.samsung.android.gtscell.data.GtsSupplier;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.cube.gts.GtsCube;
import com.samsung.android.settings.cube.gts.GtsItemWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SettingsGtsProvider extends GtsCellProvider {
    public GtsCube mGtsCube;

    public abstract List getGroups();

    @Override // com.samsung.android.gtscell.GtsCellItemProvider
    public final List getGtsItemGroups(String str) {
        ArrayList arrayList = new ArrayList();
        for (final GtsResources.GtsGroup gtsGroup : getGroups()) {
            GtsCube gtsCube = this.mGtsCube;
            Map map = GtsResources.mResourceMap;
            GtsResources.LazyHolder.INSTANCE.getClass();
            ArrayList arrayList2 =
                    (ArrayList)
                            gtsCube.getGtsItemGroups(
                                    (List)
                                            ((LinkedHashMap) GtsResources.mResourceMap)
                                                    .entrySet().stream()
                                                            .filter(
                                                                    new Predicate() { // from class:
                                                                                      // com.samsung.android.settings.gts.GtsResources$$ExternalSyntheticLambda0
                                                                        @Override // java.util.function.Predicate
                                                                        public final boolean test(
                                                                                Object obj) {
                                                                            return ((Map.Entry) obj)
                                                                                            .getValue()
                                                                                    == GtsResources
                                                                                            .GtsGroup
                                                                                            .this;
                                                                        }
                                                                    })
                                                            .map(
                                                                    new GtsResources$$ExternalSyntheticLambda1())
                                                            .collect(
                                                                    Collectors.toCollection(
                                                                            new GtsResources$$ExternalSyntheticLambda2())));
            if (arrayList2.size() > 0) {
                Log.d(
                        "SettingsGtsProvider",
                        "gts group size : " + gtsGroup.getGroupName() + ", " + arrayList2.size());
                GtsItemSupplierGroupBuilder gtsItemSupplierGroupBuilder =
                        new GtsItemSupplierGroupBuilder(
                                getContext().getString(gtsGroup.getTitleResId()));
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    final GtsItemWrapper gtsItemWrapper = (GtsItemWrapper) it.next();
                    final int i = 0;
                    final int i2 = 1;
                    gtsItemSupplierGroupBuilder.add(
                            new GtsItemSupplier(
                                    gtsItemWrapper.getKey(),
                                    new GtsSupplier() { // from class:
                                                        // com.samsung.android.settings.gts.SettingsGtsProvider$$ExternalSyntheticLambda0
                                        @Override // com.samsung.android.gtscell.data.GtsSupplier
                                        public final Object get(Object obj) {
                                            GtsItemWrapper gtsItemWrapper2 = gtsItemWrapper;
                                            switch (i) {
                                                case 0:
                                                    GtsExpressionBuilder gtsExpressionBuilder =
                                                            (GtsExpressionBuilder) obj;
                                                    String key = gtsItemWrapper2.getKey();
                                                    Map map2 = GtsResources.mResourceMap;
                                                    GtsResources.LazyHolder.INSTANCE.getClass();
                                                    Map map3 = GtsResources.mMinVersionMap;
                                                    return gtsItemWrapper2.buildExpression(
                                                            gtsExpressionBuilder.setVersion(
                                                                    map3.containsKey(key)
                                                                            ? ((Integer)
                                                                                            ((LinkedHashMap)
                                                                                                            map3)
                                                                                                    .get(
                                                                                                            key))
                                                                                    .intValue()
                                                                            : 1));
                                                default:
                                                    return gtsItemWrapper2.buildGtsItem(
                                                            gtsItemWrapper2.getKey(),
                                                            (GtsItemBuilder) obj);
                                            }
                                        }
                                    },
                                    new GtsSupplier() { // from class:
                                                        // com.samsung.android.settings.gts.SettingsGtsProvider$$ExternalSyntheticLambda0
                                        @Override // com.samsung.android.gtscell.data.GtsSupplier
                                        public final Object get(Object obj) {
                                            GtsItemWrapper gtsItemWrapper2 = gtsItemWrapper;
                                            switch (i2) {
                                                case 0:
                                                    GtsExpressionBuilder gtsExpressionBuilder =
                                                            (GtsExpressionBuilder) obj;
                                                    String key = gtsItemWrapper2.getKey();
                                                    Map map2 = GtsResources.mResourceMap;
                                                    GtsResources.LazyHolder.INSTANCE.getClass();
                                                    Map map3 = GtsResources.mMinVersionMap;
                                                    return gtsItemWrapper2.buildExpression(
                                                            gtsExpressionBuilder.setVersion(
                                                                    map3.containsKey(key)
                                                                            ? ((Integer)
                                                                                            ((LinkedHashMap)
                                                                                                            map3)
                                                                                                    .get(
                                                                                                            key))
                                                                                    .intValue()
                                                                            : 1));
                                                default:
                                                    return gtsItemWrapper2.buildGtsItem(
                                                            gtsItemWrapper2.getKey(),
                                                            (GtsItemBuilder) obj);
                                            }
                                        }
                                    }));
                }
                arrayList.add(gtsItemSupplierGroupBuilder.build());
            }
        }
        return arrayList;
    }

    @Override // com.samsung.android.gtscell.GtsCellProvider
    public final boolean isActive() {
        return true;
    }

    @Override // com.samsung.android.gtscell.GtsCellProvider, android.content.ContentProvider
    public final boolean onCreate() {
        Trace.beginSection("SettingsGtsProvider#onCreate");
        this.mGtsCube = new GtsCube(getContext());
        Trace.beginSection("SettingsGtsProvider#super.onCreate");
        boolean onCreate = super.onCreate();
        Trace.endSection();
        Trace.endSection();
        return onCreate;
    }

    @Override // com.samsung.android.gtscell.GtsCellItemProvider
    public final void setGtsItem(
            String str,
            GtsItem gtsItem,
            GtsConfiguration gtsConfiguration,
            ResultCallback resultCallback) {
        this.mGtsCube.setGtsItem(gtsItem, gtsConfiguration, resultCallback);
    }
}
