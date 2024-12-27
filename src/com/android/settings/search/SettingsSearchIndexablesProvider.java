package com.android.settings.search;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.SearchIndexablesContract;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.slice.SliceViewManagerWrapper;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.CategoryManager;
import com.android.settings.dashboard.DashboardFeatureProviderImpl;
import com.android.settings.dashboard.DashboardFragmentRegistry;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.drawer.ActivityTile;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.search.SearchIndexableResourcesBase;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.search.provider.SecSearchIndexablesProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SettingsSearchIndexablesProvider extends SecSearchIndexablesProvider {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final Collection INVALID_KEYS;
    public Map mSearchEnabledByCategoryKeyMap;

    static {
        ArraySet arraySet = new ArraySet();
        INVALID_KEYS = arraySet;
        arraySet.add(null);
        arraySet.add(ApnSettings.MVNO_NONE);
    }

    public SettingsSearchIndexablesProvider() {
        this.mExtraInfo = new Bundle();
    }

    public static Object[] createIndexableRawColumnObjects(SearchIndexableRaw searchIndexableRaw) {
        Object[] objArr = new Object[SearchIndexablesContract.INDEXABLES_RAW_COLUMNS.length];
        objArr[1] = searchIndexableRaw.title;
        objArr[2] = searchIndexableRaw.summaryOn;
        objArr[3] = searchIndexableRaw.summaryOff;
        objArr[4] = null;
        objArr[5] = searchIndexableRaw.keywords;
        objArr[6] = searchIndexableRaw.screenTitle;
        objArr[7] = ((SearchIndexableData) searchIndexableRaw).className;
        objArr[8] = Integer.valueOf(((SearchIndexableData) searchIndexableRaw).iconResId);
        objArr[9] = ((SearchIndexableData) searchIndexableRaw).intentAction;
        objArr[10] = ((SearchIndexableData) searchIndexableRaw).intentTargetPackage;
        objArr[11] = ((SearchIndexableData) searchIndexableRaw).intentTargetClass;
        objArr[12] = ((SearchIndexableData) searchIndexableRaw).key;
        objArr[13] = Integer.valueOf(((SearchIndexableData) searchIndexableRaw).userId);
        return objArr;
    }

    public List<SearchIndexableRaw> getInjectionIndexableRawData(Context context) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        DashboardFeatureProviderImpl dashboardFeatureProvider =
                featureFactoryImpl.getDashboardFeatureProvider();
        ArrayList arrayList = new ArrayList();
        String packageName = context.getPackageName();
        for (DashboardCategory dashboardCategory : dashboardFeatureProvider.getAllCategories()) {
            if (((ArrayMap) this.mSearchEnabledByCategoryKeyMap)
                    .containsKey(dashboardCategory.key)) {
                if (!((Boolean)
                                ((ArrayMap) this.mSearchEnabledByCategoryKeyMap)
                                        .get(dashboardCategory.key))
                        .booleanValue()) {
                    Utils$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Skip indexing category: "),
                            dashboardCategory.key,
                            "SettingsSearchIndexablesProvider");
                }
            }
            Iterator it = ((ArrayList) dashboardCategory.getTiles()).iterator();
            while (it.hasNext()) {
                Tile tile = (Tile) it.next();
                if (isEligibleForIndexing(packageName, tile)) {
                    SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                    CharSequence title = tile.getTitle(context);
                    String charSequence = TextUtils.isEmpty(title) ? null : title.toString();
                    searchIndexableRaw.title = charSequence;
                    if (!TextUtils.isEmpty(charSequence)) {
                        ((SearchIndexableData) searchIndexableRaw).key =
                                dashboardFeatureProvider.getDashboardKeyForTile(tile);
                        CharSequence summary = tile.getSummary(context);
                        String charSequence2 =
                                TextUtils.isEmpty(summary) ? null : summary.toString();
                        searchIndexableRaw.summaryOn = charSequence2;
                        searchIndexableRaw.summaryOff = charSequence2;
                        ((SearchIndexableData) searchIndexableRaw).className =
                                (String)
                                        ((ArrayMap)
                                                        DashboardFragmentRegistry
                                                                .CATEGORY_KEY_TO_PARENT_MAP)
                                                .get(tile.mCategory);
                        arrayList.add(searchIndexableRaw);
                    }
                }
            }
        }
        return arrayList;
    }

    public boolean isEligibleForIndexing(String str, Tile tile) {
        if (TextUtils.equals(str, tile.mComponentPackage) && (tile instanceof ActivityTile)) {
            return false;
        }
        Bundle bundle = tile.mMetaData;
        return bundle == null || bundle.getBoolean("com.android.settings.searchable", true);
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        this.mSearchEnabledByCategoryKeyMap = new ArrayMap();
        return true;
    }

    @Override // com.samsung.android.settings.search.provider.SecSearchIndexablesProvider
    public final Cursor queryDynamicRawData() {
        List<SearchIndexableRaw> list;
        long currentTimeMillis = System.currentTimeMillis();
        Context searchContext = getSearchContext();
        ArrayList arrayList = new ArrayList();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        Iterator it =
                ((HashSet)
                                ((SearchIndexableResourcesBase)
                                                featureFactoryImpl
                                                        .getSearchFeatureProvider()
                                                        .getSearchIndexableResources())
                                        .mProviders)
                        .iterator();
        while (it.hasNext()) {
            com.android.settingslib.search.SearchIndexableData searchIndexableData =
                    (com.android.settingslib.search.SearchIndexableData) it.next();
            long currentTimeMillis2 = System.currentTimeMillis();
            try {
                list =
                        searchIndexableData.mSearchIndexProvider.getDynamicRawDataToIndex(
                                searchContext);
            } catch (Exception e) {
                Log.e(
                        "SettingsSearchIndexablesProvider",
                        "Exception - getDynamicSearchIndexableRawData() class : "
                                + searchIndexableData.mTargetClass.getSimpleName()
                                + " / "
                                + e.getMessage());
                list = null;
            }
            if (list == null) {
                list = new ArrayList();
            } else {
                for (SearchIndexableRaw searchIndexableRaw : list) {
                    if (TextUtils.isEmpty(((SearchIndexableData) searchIndexableRaw).className)) {
                        ((SearchIndexableData) searchIndexableRaw).className =
                                searchIndexableData.mTargetClass.getName();
                    }
                }
            }
            arrayList.addAll(list);
            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                    searchIndexableData.mSearchIndexProvider;
            if (indexable$SearchIndexProvider instanceof BaseSearchIndexProvider) {
                refreshSearchEnabledState(
                        searchContext, (BaseSearchIndexProvider) indexable$SearchIndexProvider);
            }
            long currentTimeMillis3 = System.currentTimeMillis() - currentTimeMillis2;
            if (currentTimeMillis3 > 50) {
                Log.i(
                        "SettingsSearchIndexablesProvider",
                        "queryDynamicRawData() "
                                + searchIndexableData.mTargetClass
                                + " took "
                                + currentTimeMillis3);
            }
        }
        MatrixCursor matrixCursor =
                new MatrixCursor(SearchIndexablesContract.INDEXABLES_RAW_COLUMNS);
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            matrixCursor.addRow(createIndexableRawColumnObjects((SearchIndexableRaw) it2.next()));
        }
        Log.i(
                "SettingsSearchIndexablesProvider",
                "queryDynamicRawData() took " + (System.currentTimeMillis() - currentTimeMillis));
        return matrixCursor;
    }

    @Override // com.samsung.android.settings.search.provider.SecSearchIndexablesProvider
    public final Cursor queryNonIndexableKeys() {
        long currentTimeMillis = System.currentTimeMillis();
        MatrixCursor matrixCursor =
                new MatrixCursor(SearchIndexablesContract.NON_INDEXABLES_KEYS_COLUMNS);
        Context context = getContext();
        long currentTimeMillis2 = System.currentTimeMillis();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        Set set =
                ((SearchIndexableResourcesBase)
                                featureFactoryImpl
                                        .getSearchFeatureProvider()
                                        .getSearchIndexableResources())
                        .mProviders;
        ArrayList arrayList = new ArrayList();
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            com.android.settingslib.search.SearchIndexableData searchIndexableData =
                    (com.android.settingslib.search.SearchIndexableData) it.next();
            long currentTimeMillis3 = System.currentTimeMillis();
            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                    searchIndexableData.mSearchIndexProvider;
            try {
                List nonIndexableKeys = indexable$SearchIndexProvider.getNonIndexableKeys(context);
                if (nonIndexableKeys == null || nonIndexableKeys.isEmpty()) {
                    long currentTimeMillis4 = System.currentTimeMillis() - currentTimeMillis3;
                    if (DEBUG || currentTimeMillis4 >= 50) {
                        StringBuilder m =
                                SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                                        currentTimeMillis4,
                                        "No indexable, total time ",
                                        ", Class ");
                        m.append(searchIndexableData.mTargetClass.getName());
                        Log.d("SettingsSearchIndexablesProvider", m.toString());
                    }
                } else {
                    if (nonIndexableKeys.removeAll(INVALID_KEYS)) {
                        Log.v(
                                "SettingsSearchIndexablesProvider",
                                indexable$SearchIndexProvider
                                        + " tried to add an empty non-indexable key");
                    }
                    long currentTimeMillis5 = System.currentTimeMillis() - currentTimeMillis3;
                    if (DEBUG || currentTimeMillis5 >= 50) {
                        Log.d(
                                "SettingsSearchIndexablesProvider",
                                "getNonIndexableKeysFromProvider() "
                                        + nonIndexableKeys.size()
                                        + ", total time "
                                        + currentTimeMillis5
                                        + ", Class "
                                        + searchIndexableData.mTargetClass.getName());
                    }
                    arrayList.addAll(nonIndexableKeys);
                }
            } catch (Exception e) {
                if (System.getProperty("debug.com.android.settings.search.crash_on_error")
                        != null) {
                    throw new RuntimeException(e);
                }
                Log.e(
                        "SettingsSearchIndexablesProvider",
                        "Error trying to get non-indexable keys from: "
                                .concat(searchIndexableData.mTargetClass.getName()),
                        e);
            }
        }
        Log.d(
                "SettingsSearchIndexablesProvider",
                "getNonIndexableKeysFromProvider() total time : "
                        + (System.currentTimeMillis() - currentTimeMillis2));
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            Object[] objArr =
                    new Object[SearchIndexablesContract.NON_INDEXABLES_KEYS_COLUMNS.length];
            objArr[0] = str;
            matrixCursor.addRow(objArr);
        }
        Log.i(
                "SettingsSearchIndexablesProvider",
                "queryNonIndexableKeysFromProvider() took "
                        + (System.currentTimeMillis() - currentTimeMillis));
        return matrixCursor;
    }

    @Override // com.samsung.android.settings.search.provider.SecSearchIndexablesProvider
    public final Cursor queryRawData() {
        List<SearchIndexableRaw> rawDataToIndex;
        long currentTimeMillis = System.currentTimeMillis();
        MatrixCursor matrixCursor =
                new MatrixCursor(SearchIndexablesContract.INDEXABLES_RAW_COLUMNS);
        Context context = getContext();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        Set set =
                ((SearchIndexableResourcesBase)
                                featureFactoryImpl
                                        .getSearchFeatureProvider()
                                        .getSearchIndexableResources())
                        .mProviders;
        ArrayList arrayList = new ArrayList();
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            com.android.settingslib.search.SearchIndexableData searchIndexableData =
                    (com.android.settingslib.search.SearchIndexableData) it.next();
            long currentTimeMillis2 = System.currentTimeMillis();
            try {
                rawDataToIndex =
                        searchIndexableData.mSearchIndexProvider.getRawDataToIndex(context);
            } catch (Exception e) {
                Log.e(
                        "SettingsSearchIndexablesProvider",
                        "Exception - getSearchIndexableRawFromProvider() class : "
                                + searchIndexableData.mTargetClass.getSimpleName()
                                + " / "
                                + e.getMessage());
            }
            if (rawDataToIndex != null) {
                for (SearchIndexableRaw searchIndexableRaw : rawDataToIndex) {
                    if (TextUtils.isEmpty(((SearchIndexableData) searchIndexableRaw).className)) {
                        ((SearchIndexableData) searchIndexableRaw).className =
                                searchIndexableData.mTargetClass.getName();
                    }
                }
                arrayList.addAll(rawDataToIndex);
                long currentTimeMillis3 = System.currentTimeMillis() - currentTimeMillis2;
                if (currentTimeMillis3 > 50) {
                    Log.i(
                            "SettingsSearchIndexablesProvider",
                            "getRawDataFromProvider() - "
                                    + searchIndexableData.mTargetClass.getName()
                                    + " took "
                                    + currentTimeMillis3);
                }
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            matrixCursor.addRow(createIndexableRawColumnObjects((SearchIndexableRaw) it2.next()));
        }
        Log.i(
                "SettingsSearchIndexablesProvider",
                "queryRawData took " + (System.currentTimeMillis() - currentTimeMillis));
        return matrixCursor;
    }

    @Override // com.samsung.android.settings.search.provider.SecSearchIndexablesProvider
    public final Cursor querySiteMapPairs() {
        CharSequence charSequence;
        long currentTimeMillis = System.currentTimeMillis();
        MatrixCursor matrixCursor = new MatrixCursor(SearchIndexablesContract.SITE_MAP_COLUMNS);
        getContext();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        for (DashboardCategory dashboardCategory :
                featureFactoryImpl.getDashboardFeatureProvider().getAllCategories()) {
            String str =
                    (String)
                            ((ArrayMap) DashboardFragmentRegistry.CATEGORY_KEY_TO_PARENT_MAP)
                                    .get(dashboardCategory.key);
            if (str != null) {
                Iterator it = ((ArrayList) dashboardCategory.getTiles()).iterator();
                while (it.hasNext()) {
                    Tile tile = (Tile) it.next();
                    Bundle bundle = tile.mMetaData;
                    String string =
                            bundle != null
                                    ? bundle.getString("com.android.settings.FRAGMENT_CLASS")
                                    : null;
                    if (string == null) {
                        string = tile.mComponentName;
                        charSequence = tile.getTitle(getContext());
                    } else {
                        charSequence = ApnSettings.MVNO_NONE;
                    }
                    if (string != null) {
                        matrixCursor
                                .newRow()
                                .add("parent_class", str)
                                .add("child_class", string)
                                .add("child_title", charSequence);
                    }
                }
            }
        }
        for (String str2 : ((ArrayMap) CustomSiteMapRegistry.CUSTOM_SITE_MAP).keySet()) {
            matrixCursor
                    .newRow()
                    .add(
                            "parent_class",
                            (String) ((ArrayMap) CustomSiteMapRegistry.CUSTOM_SITE_MAP).get(str2))
                    .add("child_class", str2);
        }
        Log.i(
                "SettingsSearchIndexablesProvider",
                "querySiteMapPairs() took " + (System.currentTimeMillis() - currentTimeMillis));
        return matrixCursor;
    }

    @Override // com.samsung.android.settings.search.provider.SecSearchIndexablesProvider
    public final Cursor querySliceUriPairs() {
        SliceViewManagerWrapper sliceViewManagerWrapper = new SliceViewManagerWrapper(getContext());
        MatrixCursor matrixCursor =
                new MatrixCursor(SearchIndexablesContract.SLICE_URI_PAIRS_COLUMNS);
        String string = getContext().getString(R.string.config_non_public_slice_query_uri);
        Uri parse =
                !TextUtils.isEmpty(string)
                        ? Uri.parse(string)
                        : new Uri.Builder()
                                .scheme("content")
                                .authority("com.android.settings.slices")
                                .build();
        Uri build =
                new Uri.Builder().scheme("content").authority("android.settings.slices").build();
        Collection<Uri> sliceDescendants = sliceViewManagerWrapper.getSliceDescendants(parse);
        sliceDescendants.addAll(sliceViewManagerWrapper.getSliceDescendants(build));
        for (Uri uri : sliceDescendants) {
            matrixCursor
                    .newRow()
                    .add(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, uri.getLastPathSegment())
                    .add("slice_uri", uri);
        }
        return matrixCursor;
    }

    @Override // com.samsung.android.settings.search.provider.SecSearchIndexablesProvider
    public final Cursor queryXmlResources() {
        List<SearchIndexableResource> xmlResourcesToIndex;
        long currentTimeMillis = System.currentTimeMillis();
        MatrixCursor matrixCursor =
                new MatrixCursor(SearchIndexablesContract.INDEXABLES_XML_RES_COLUMNS);
        Context context = getContext();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        Set set =
                ((SearchIndexableResourcesBase)
                                featureFactoryImpl
                                        .getSearchFeatureProvider()
                                        .getSearchIndexableResources())
                        .mProviders;
        ArrayList arrayList = new ArrayList();
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            com.android.settingslib.search.SearchIndexableData searchIndexableData =
                    (com.android.settingslib.search.SearchIndexableData) it.next();
            long currentTimeMillis2 = System.currentTimeMillis();
            try {
                xmlResourcesToIndex =
                        searchIndexableData.mSearchIndexProvider.getXmlResourcesToIndex(context);
            } catch (Exception e) {
                Log.e(
                        "SettingsSearchIndexablesProvider",
                        "Exception - getSearchIndexableResourcesFromProvider() class : "
                                + searchIndexableData.mTargetClass.getSimpleName()
                                + " / "
                                + e.getMessage());
            }
            if (xmlResourcesToIndex != null) {
                for (SearchIndexableResource searchIndexableResource : xmlResourcesToIndex) {
                    searchIndexableResource.className =
                            TextUtils.isEmpty(searchIndexableResource.className)
                                    ? searchIndexableData.mTargetClass.getName()
                                    : searchIndexableResource.className;
                }
                arrayList.addAll(xmlResourcesToIndex);
                long currentTimeMillis3 = System.currentTimeMillis() - currentTimeMillis2;
                if (currentTimeMillis3 > 50) {
                    Log.i(
                            "SettingsSearchIndexablesProvider",
                            "getSearchIndexableResources() - "
                                    + searchIndexableData.mTargetClass.getName()
                                    + " took "
                                    + currentTimeMillis3);
                }
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            SearchIndexableResource searchIndexableResource2 = (SearchIndexableResource) it2.next();
            Object[] objArr =
                    new Object[SearchIndexablesContract.INDEXABLES_XML_RES_COLUMNS.length];
            objArr[0] = Integer.valueOf(searchIndexableResource2.rank);
            objArr[1] = Integer.valueOf(searchIndexableResource2.xmlResId);
            objArr[2] = searchIndexableResource2.className;
            objArr[3] = Integer.valueOf(searchIndexableResource2.iconResId);
            objArr[4] = searchIndexableResource2.intentAction;
            objArr[5] = searchIndexableResource2.intentTargetPackage;
            objArr[6] = searchIndexableResource2.intentTargetClass;
            matrixCursor.addRow(objArr);
        }
        Log.i(
                "SettingsSearchIndexablesProvider",
                "queryXmlResources() - " + (System.currentTimeMillis() - currentTimeMillis));
        return matrixCursor;
    }

    public void refreshSearchEnabledState(
            Context context, BaseSearchIndexProvider baseSearchIndexProvider) {
        DashboardCategory tilesByCategory;
        String name = baseSearchIndexProvider.getClass().getName();
        int lastIndexOf = name.lastIndexOf("$");
        if (lastIndexOf > 0) {
            name = name.substring(0, lastIndexOf);
        }
        String str =
                (String)
                        ((ArrayMap) DashboardFragmentRegistry.PARENT_TO_CATEGORY_KEY_MAP).get(name);
        if (str == null
                || (tilesByCategory = CategoryManager.get(context).getTilesByCategory(context, str))
                        == null) {
            return;
        }
        ((ArrayMap) this.mSearchEnabledByCategoryKeyMap)
                .put(
                        tilesByCategory.key,
                        Boolean.valueOf(baseSearchIndexProvider.isPageSearchEnabled(context)));
    }
}
