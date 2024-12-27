package com.samsung.android.settings.applications.cachedb;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settingslib.applications.cachedb.AppListCacheManager;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AppListCacheProvider extends ContentProvider {
    public static final UriMatcher sUriMatcher;
    public final String TAG = "AppListCacheProvider";

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sUriMatcher = uriMatcher;
        uriMatcher.addURI("com.samsung.android.settings.applist", "app_list", 0);
        uriMatcher.addURI("com.samsung.android.settings.applist", "app_list/*", 1);
    }

    @Override // android.content.ContentProvider
    public final ContentProviderResult[] applyBatch(ArrayList arrayList) {
        long currentTimeMillis = System.currentTimeMillis();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        int size = arrayList.size();
        ContentProviderResult[] contentProviderResultArr = new ContentProviderResult[size];
        try {
            try {
                writableDatabase.beginTransaction();
                for (int i = 0; i < size; i++) {
                    contentProviderResultArr[i] =
                            ((ContentProviderOperation) arrayList.get(i))
                                    .apply(this, contentProviderResultArr, i);
                }
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                String str = this.TAG;
                StringBuilder m =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                size, "applyBatch end. operations : ", " ,Took ");
                m.append(System.currentTimeMillis() - currentTimeMillis);
                m.append(" ms");
                Log.d(str, m.toString());
                return contentProviderResultArr;
            } catch (SQLiteException e) {
                Log.e(this.TAG, e.getMessage());
                throw new OperationApplicationException(e);
            }
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    @Override // android.content.ContentProvider
    public final int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        if (!KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(getCallingPackage())) {
            Log.e(this.TAG, "External packages are not allowed to bulkInsert");
            return 0;
        }
        long currentTimeMillis = System.currentTimeMillis();
        Log.d(this.TAG, "bulkInsert start");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            writableDatabase.beginTransaction();
            int i = 0;
            for (ContentValues contentValues : contentValuesArr) {
                if (getWritableDatabase()
                                .replaceOrThrow("appinfo", ApnSettings.MVNO_NONE, contentValues)
                        > 0) {
                    i++;
                }
            }
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            if (i > 0) {
                notifyChange(uri);
            }
            Log.d(
                    this.TAG,
                    "bulkInsert end. Took "
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + " ms");
            return i;
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            Log.e(this.TAG, "call() - method is empty");
            return null;
        }
        str.getClass();
        if (str.equals("get_cached_locale")) {
            return AbsAdapter$1$$ExternalSyntheticOutline0.m(
                    "cached_locale",
                    getContext()
                            .getSharedPreferences("AppListPreference", 0)
                            .getString("CachedLocale", ApnSettings.MVNO_NONE));
        }
        Log.e(this.TAG, "call() - Unsupported method : ".concat(str));
        return null;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        int delete;
        if (!KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(getCallingPackage())) {
            Log.e(this.TAG, "External packages are not allowed to delete");
            return 0;
        }
        int match = sUriMatcher.match(uri);
        if (match == 0) {
            delete = getWritableDatabase().delete("appinfo", null, null);
        } else if (match != 1) {
            delete = getWritableDatabase().delete("appinfo", str, strArr);
        } else {
            delete =
                    getWritableDatabase()
                            .delete(
                                    "appinfo",
                                    "package_name =?",
                                    new String[] {uri.getLastPathSegment()});
        }
        if (delete > 0) {
            notifyChange(uri);
        }
        return delete;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public final SQLiteDatabase getWritableDatabase() {
        return AppListDataBaseHelper.getInstance(getContext()).getWritableDatabase();
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        if (!KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(getCallingPackage())) {
            Log.e(this.TAG, "External packages are not allowed to insert");
            return null;
        }
        getWritableDatabase()
                .delete(
                        "appinfo",
                        "package_name =?",
                        new String[] {contentValues.getAsString("package_name")});
        long replaceOrThrow =
                getWritableDatabase()
                        .replaceOrThrow("appinfo", ApnSettings.MVNO_NONE, contentValues);
        if (replaceOrThrow <= 0) {
            return null;
        }
        notifyChange(uri);
        return ContentUris.withAppendedId(uri, replaceOrThrow);
    }

    public final void notifyChange(Uri uri) {
        try {
            getContext().getContentResolver().notifyChange(uri, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (!AppListCacheManager.isValidCache(getContext())) {
            Log.e(this.TAG, "query() - Invalid cache");
            return null;
        }
        int match = sUriMatcher.match(uri);
        if (match == 0) {
            return AppListDataBaseHelper.getInstance(getContext())
                    .getReadableDatabase()
                    .query("appinfo", null, null, null, null, null, str2);
        }
        if (match != 1) {
            return null;
        }
        return AppListDataBaseHelper.getInstance(getContext())
                .getReadableDatabase()
                .query(
                        "appinfo",
                        null,
                        "package_name =?",
                        new String[] {uri.getLastPathSegment()},
                        null,
                        null,
                        str2);
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        if (!KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(getCallingPackage())) {
            Log.e(this.TAG, "External packages are not allowed to update");
            return 0;
        }
        int update = getWritableDatabase().update("appinfo", contentValues, str, strArr);
        if (update > 0) {
            notifyChange(uri);
        }
        return update;
    }
}
