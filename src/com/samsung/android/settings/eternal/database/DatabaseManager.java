package com.samsung.android.settings.eternal.database;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.knox.accounts.Account;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.manager.RepositoryManager;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DatabaseManager implements RepositoryManager {
    public final Context context;

    public DatabaseManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x01cc A[LOOP:3: B:47:0x01c6->B:49:0x01cc, LOOP_END] */
    @Override // com.samsung.android.settings.eternal.manager.RepositoryManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.settings.eternal.data.EpisodeHolder createEpisodeHolder(
            com.samsung.android.settings.eternal.data.RestoreInfo r18, java.util.HashMap r19) {
        /*
            Method dump skipped, instructions count: 509
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.database.DatabaseManager.createEpisodeHolder(com.samsung.android.settings.eternal.data.RestoreInfo,"
                    + " java.util.HashMap):com.samsung.android.settings.eternal.data.EpisodeHolder");
    }

    public final HashMap getBackupSceneMap() {
        Cursor query;
        HashMap hashMap = new HashMap();
        try {
            query = getDatabase().query("backup_scene");
        } catch (Exception e) {
            EternalFileLog.e("Eternal/DatabaseManager", "getBackupSceneMap() - " + e.getMessage());
        }
        if (query != null && query.moveToFirst()) {
            do {
                String string =
                        query.getString(
                                query.getColumnIndexOrThrow(
                                        GoodSettingsContract.EXTRA_NAME.POLICY_KEY));
                byte[] blob = query.getBlob(query.getColumnIndexOrThrow("data"));
                String string2 = query.getString(query.getColumnIndexOrThrow(Account.IS_DEFAULT));
                Parcelable.Creator<Scene> creator = Scene.CREATOR;
                Parcel obtain = Parcel.obtain();
                obtain.unmarshall(blob, 0, blob.length);
                obtain.setDataPosition(0);
                Scene createFromParcel = creator.createFromParcel(obtain);
                obtain.recycle();
                Scene.Builder builder = new Scene.Builder(createFromParcel);
                builder.setDefault(Intrinsics.areEqual(string2, "true"));
                Scene build = builder.build();
                Intrinsics.checkNotNull(string);
                Intrinsics.checkNotNull(build);
                if (!hashMap.containsKey(string)) {
                    hashMap.put(string, new ArrayList());
                }
                List list = (List) hashMap.get(string);
                if (list != null) {
                    list.add(build);
                }
            } while (query.moveToNext());
            query.close();
            return hashMap;
        }
        return hashMap;
    }

    public final DatabaseHelper getDatabase() {
        DatabaseHelper databaseHelper = DatabaseHelper.instance;
        Context context = this.context;
        Intrinsics.checkNotNullParameter(context, "context");
        if (DatabaseHelper.instance == null) {
            Context applicationContext = context.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(
                    applicationContext, "getApplicationContext(...)");
            DatabaseHelper.instance = new DatabaseHelper(applicationContext);
        }
        DatabaseHelper databaseHelper2 = DatabaseHelper.instance;
        Intrinsics.checkNotNull(
                databaseHelper2,
                "null cannot be cast to non-null type"
                    + " com.samsung.android.settings.eternal.database.DatabaseHelper");
        return databaseHelper2;
    }
}
