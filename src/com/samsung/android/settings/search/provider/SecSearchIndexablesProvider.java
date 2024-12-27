package com.samsung.android.settings.search.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchIndexablesContract;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.SettingsSearchIndexablesProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableData;
import com.android.settingslib.search.SearchIndexableResourcesBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecSearchIndexablesProvider extends ContentProvider {
    public String mAuthority;
    public String mAuthority$1;
    public Bundle mExtraInfo;
    public UriMatcher mMatcher;
    public UriMatcher mMatcher$1;

    @Override // android.content.ContentProvider
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        attachInfo$com$samsung$android$settings$search$provider$SearchIndexablesProvider(
                context, providerInfo);
        if (TextUtils.isEmpty(Build.VERSION.INCREMENTAL)) {
            throw new ClassCastException("secQueryGetFingerprint must implement");
        }
        this.mAuthority = providerInfo.authority;
        UriMatcher uriMatcher = new UriMatcher(-1);
        this.mMatcher = uriMatcher;
        uriMatcher.addURI(this.mAuthority, "sec_settings/sec_variable_raw_data", 301);
        this.mMatcher.addURI(this.mAuthority, "sec_settings/sec_non_indexables_key", 300);
    }

    public final void
            attachInfo$com$samsung$android$settings$search$provider$SearchIndexablesProvider(
                    Context context, ProviderInfo providerInfo) {
        this.mAuthority$1 = providerInfo.authority;
        UriMatcher uriMatcher = new UriMatcher(-1);
        this.mMatcher$1 = uriMatcher;
        uriMatcher.addURI(this.mAuthority$1, "settings/indexables_xml_res", 1);
        this.mMatcher$1.addURI(this.mAuthority$1, "settings/indexables_raw", 2);
        this.mMatcher$1.addURI(this.mAuthority$1, "settings/non_indexables_key", 3);
        this.mMatcher$1.addURI(this.mAuthority$1, "settings/site_map_pairs", 4);
        this.mMatcher$1.addURI(this.mAuthority$1, "settings/slice_uri_pairs", 5);
        this.mMatcher$1.addURI(this.mAuthority$1, "settings/dynamic_indexables_raw", 6);
        if (!providerInfo.exported) {
            throw new SecurityException("Provider must be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new SecurityException("Provider must grantUriPermissions");
        }
        if (!"android.permission.READ_SEARCH_INDEXABLES".equals(providerInfo.readPermission)) {
            throw new SecurityException("Provider must be protected by READ_SEARCH_INDEXABLES");
        }
        super.attachInfo(context, providerInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        int i;
        i = 0;
        if (bundle != null) {
            this.mExtraInfo.putInt("isDexMode", bundle.getInt("isDexMode", -1));
        }
        str.getClass();
        switch (str) {
            case "secGetVersion":
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putString("key_version", String.valueOf(1));
                return bundle;
            case "secGetAvailability":
                if (bundle == null) {
                    bundle = new Bundle();
                }
                long currentTimeMillis = System.currentTimeMillis();
                String string = bundle.getString("preference_key");
                String string2 = bundle.getString("class_name");
                Context searchContext =
                        ((SettingsSearchIndexablesProvider) this).getSearchContext();
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
                while (true) {
                    if (it.hasNext()) {
                        SearchIndexableData searchIndexableData = (SearchIndexableData) it.next();
                        if (searchIndexableData.mTargetClass.getCanonicalName().equals(string2)) {
                            i =
                                    searchIndexableData
                                            .mSearchIndexProvider
                                            .getNonIndexableKeys(searchContext)
                                            .contains(string);
                        }
                    } else {
                        Log.i(
                                "SettingsSearchIndexablesProvider",
                                "secQueryAvailability() took "
                                        + (System.currentTimeMillis() - currentTimeMillis));
                    }
                }
                bundle.putInt("availability", i);
                return bundle;
            case "secGetFingerprint":
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putString("key_fingerprint", Build.VERSION.INCREMENTAL);
                return bundle;
            default:
                return super.call(str, str2, bundle);
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Delete not supported");
    }

    public final Context getSearchContext() {
        Context context = getContext();
        if (context == null) {
            return null;
        }
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.semDesktopModeEnabled = this.mExtraInfo.getInt("isDexMode", -1) == 1 ? 1 : -1;
        return context.createConfigurationContext(configuration);
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        int match = this.mMatcher.match(uri);
        return match != 300
                ? match != 301
                        ? getType$com$samsung$android$settings$search$provider$SearchIndexablesProvider(
                                uri)
                        : "vnd.android.cursor.dir/sec_variable_raw_data"
                : "vnd.android.cursor.dir/sec_non_indexables_key";
    }

    public final String
            getType$com$samsung$android$settings$search$provider$SearchIndexablesProvider(Uri uri) {
        int match = this.mMatcher$1.match(uri);
        if (match == 1) {
            return "vnd.android.cursor.dir/indexables_xml_res";
        }
        if (match == 2) {
            return "vnd.android.cursor.dir/indexables_raw";
        }
        if (match == 3) {
            return "vnd.android.cursor.dir/non_indexables_key";
        }
        if (match == 6) {
            return "vnd.android.cursor.dir/indexables_raw";
        }
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Insert not supported");
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Set<String> queryParameterNames;
        int i;
        if (uri != null
                && (queryParameterNames = uri.getQueryParameterNames()) != null
                && !queryParameterNames.isEmpty()) {
            for (String str3 : queryParameterNames) {
                str3.getClass();
                if (str3.equals("isDexMode")) {
                    try {
                        i = Integer.parseInt(uri.getQueryParameter("isDexMode"));
                    } catch (NullPointerException | NumberFormatException e) {
                        Log.e("SecSearchIndexablesProvider", "getValueAsInt() " + uri + " / " + e);
                        i = -1;
                    }
                    this.mExtraInfo.putInt("isDexMode", i);
                }
            }
        }
        int match = this.mMatcher.match(uri);
        if (match != 300) {
            if (match != 301) {
                return query$com$samsung$android$settings$search$provider$SearchIndexablesProvider(
                        uri, strArr, str, strArr2, str2);
            }
            return null;
        }
        Set<String> queryParameterNames2 = uri.getQueryParameterNames();
        if (queryParameterNames2 != null && !queryParameterNames2.isEmpty()) {
            Bundle bundle = new Bundle();
            for (String str4 : queryParameterNames2) {
                bundle.putString(str4, uri.getQueryParameter(str4));
            }
        }
        MatrixCursor matrixCursor =
                new MatrixCursor(SearchIndexablesContract.NON_INDEXABLES_KEYS_COLUMNS);
        Context searchContext = ((SettingsSearchIndexablesProvider) this).getSearchContext();
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
            SearchIndexableData searchIndexableData = (SearchIndexableData) it.next();
            long currentTimeMillis = System.currentTimeMillis();
            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                    searchIndexableData.mSearchIndexProvider;
            if (indexable$SearchIndexProvider != null) {
                try {
                    List nonIndexableKeys =
                            indexable$SearchIndexProvider.getNonIndexableKeys(searchContext);
                    if (nonIndexableKeys == null || nonIndexableKeys.isEmpty()) {
                        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                        if (SettingsSearchIndexablesProvider.DEBUG || currentTimeMillis2 >= 50) {
                            Log.d(
                                    "SettingsSearchIndexablesProvider",
                                    "Class: "
                                            + searchIndexableData.mTargetClass.getName()
                                            + ", No indexable, total time (ms): "
                                            + currentTimeMillis2);
                        }
                    } else {
                        if (nonIndexableKeys.removeAll(
                                SettingsSearchIndexablesProvider.INVALID_KEYS)) {
                            Log.v(
                                    "SettingsSearchIndexablesProvider",
                                    indexable$SearchIndexProvider
                                            + " tried to add an empty non-indexable key");
                        }
                        long currentTimeMillis3 = System.currentTimeMillis() - currentTimeMillis;
                        if (SettingsSearchIndexablesProvider.DEBUG || currentTimeMillis3 >= 50) {
                            Log.d(
                                    "SettingsSearchIndexablesProvider",
                                    "Class: "
                                            + searchIndexableData.mTargetClass.getName()
                                            + ", Non-indexables: "
                                            + nonIndexableKeys.size()
                                            + ", total time (ms): "
                                            + currentTimeMillis3);
                        }
                        arrayList.addAll(nonIndexableKeys);
                    }
                } catch (Exception e2) {
                    if (System.getProperty("debug.com.android.settings.search.crash_on_error")
                            != null) {
                        throw new RuntimeException(e2);
                    }
                    Log.e(
                            "SettingsSearchIndexablesProvider",
                            "Error trying to get non-indexable keys from: "
                                    .concat(searchIndexableData.mTargetClass.getName()),
                            e2);
                }
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            String str5 = (String) it2.next();
            Object[] objArr =
                    new Object[SearchIndexablesContract.NON_INDEXABLES_KEYS_COLUMNS.length];
            objArr[0] = str5;
            matrixCursor.addRow(objArr);
        }
        return matrixCursor;
    }

    public final Cursor query$com$samsung$android$settings$search$provider$SearchIndexablesProvider(
            Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        try {
            switch (this.mMatcher$1.match(uri)) {
                case 1:
                    return queryXmlResources();
                case 2:
                    return queryRawData();
                case 3:
                    return queryNonIndexableKeys();
                case 4:
                    return querySiteMapPairs();
                case 5:
                    return querySliceUriPairs();
                case 6:
                    return queryDynamicRawData();
                default:
                    throw new UnsupportedOperationException("Unknown Uri " + uri);
            }
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (Exception e2) {
            Log.e("IndexablesProvider", "Provider querying exception:", e2);
            return null;
        }
    }

    public abstract Cursor queryDynamicRawData();

    public abstract Cursor queryNonIndexableKeys();

    public abstract Cursor queryRawData();

    public abstract Cursor querySiteMapPairs();

    public abstract Cursor querySliceUriPairs();

    public abstract Cursor queryXmlResources();

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Update not supported");
    }
}
