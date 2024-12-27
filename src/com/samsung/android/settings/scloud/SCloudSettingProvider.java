package com.samsung.android.settings.scloud;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.samsung.android.scloud.lib.setting.SyncSettingLibProviderServer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SCloudSettingProvider extends SyncSettingLibProviderServer {
    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        Log.i("SCloudSettingProvider", "delete");
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        Log.i("SCloudSettingProvider", "insert");
        return uri;
    }

    @Override // com.samsung.android.scloud.lib.setting.SyncSettingLibProviderServer,
              // android.content.ContentProvider
    public final boolean onCreate() {
        Log.d("SCloudSettingProvider", "onCreate");
        super.onCreate();
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Log.i("SCloudSettingProvider", "query - started.");
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Log.i("SCloudSettingProvider", "update uri=" + uri);
        return 0;
    }
}
