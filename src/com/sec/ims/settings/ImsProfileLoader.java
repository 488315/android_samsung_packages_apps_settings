package com.sec.ims.settings;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ImsProfileLoader {
    public static final String LOG_TAG = "ImsProfileLoader";
    public static String MOCK_MNO_PROPERTY = "persist.ims.mock.mno";
    public static final String PREF_SETTING_DB = "pref_setting_db";
    public static final String SETTING_DB_CREATED = "setting_db_created";
    public static final String SETTING_DB_JUST_CREATED = "setting_db_just_created";

    public ImsProfileLoader(Context context) {}

    public static Uri addProfile(Context context, ImsProfile imsProfile) {
        return context.getContentResolver()
                .insert(
                        Uri.parse("content://com.sec.ims.settings/profile"),
                        getContentValues(imsProfile));
    }

    public static ImsProfile find(Collection<ImsProfile> collection, String str) {
        for (ImsProfile imsProfile : collection) {
            if (imsProfile.getPdn().contains(str)) {
                return imsProfile;
            }
        }
        return null;
    }

    private static ContentValues getContentValues(ImsProfile imsProfile) {
        return imsProfile.getAsContentValues();
    }

    public static ImsProfile getImsProfileFromRow(Context context, Cursor cursor) {
        return new ImsProfile(cursor.getString(cursor.getColumnIndex(ImsProfile.SERVICE_PROFILE)));
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.sec.ims.settings.ImsProfile getProfile(android.content.Context r8, int r9) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "getProfile: id "
            r0.<init>(r1)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "ImsProfileLoader"
            android.util.Log.d(r1, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "content://com.sec.ims.settings/profile/"
            r0.<init>(r2)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            android.net.Uri r3 = android.net.Uri.parse(r9)
            android.content.ContentResolver r2 = r8.getContentResolver()
            r6 = 0
            r7 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r9 = r2.query(r3, r4, r5, r6, r7)
            if (r9 == 0) goto L4d
            java.lang.String r0 = "getProfile: found "
            android.util.Log.d(r1, r0)     // Catch: java.lang.Throwable -> L43
            boolean r0 = r9.moveToFirst()     // Catch: java.lang.Throwable -> L43
            if (r0 == 0) goto L4d
            com.sec.ims.settings.ImsProfile r8 = getImsProfileFromRow(r8, r9)     // Catch: java.lang.Throwable -> L43
            goto L4e
        L43:
            r8 = move-exception
            r9.close()     // Catch: java.lang.Throwable -> L48
            goto L4c
        L48:
            r9 = move-exception
            r8.addSuppressed(r9)
        L4c:
            throw r8
        L4d:
            r8 = 0
        L4e:
            if (r9 == 0) goto L53
            r9.close()
        L53:
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.sec.ims.settings.ImsProfileLoader.getProfile(android.content.Context,"
                    + " int):com.sec.ims.settings.ImsProfile");
    }

    public static List<ImsProfile> getProfileListWithMnoName(Context context, String str, int i) {
        ArrayList arrayList = new ArrayList();
        Cursor query =
                context.getContentResolver()
                        .query(
                                Uri.parse("content://com.sec.ims.settings/profile")
                                        .buildUpon()
                                        .fragment("simslot" + Integer.toString(i))
                                        .build(),
                                null,
                                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                        "mnoname=", str),
                                null,
                                null);
        if (query != null) {
            try {
                Log.d(LOG_TAG, "getProfileList: found " + query.getCount() + " profiles");
                if (query.moveToFirst()) {
                    do {
                        arrayList.add(getImsProfileFromRow(context, query));
                    } while (query.moveToNext());
                }
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    public static int updateProfile(Context context, ImsProfile imsProfile) {
        return context.getContentResolver()
                .update(
                        Uri.parse("content://com.sec.ims.settings/profile/" + imsProfile.getId()),
                        getContentValues(imsProfile),
                        null,
                        null);
    }

    public static int updateProfile(Context context, List<ImsProfile> list) {
        ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
        for (ImsProfile imsProfile : list) {
            arrayList.add(
                    ContentProviderOperation.newUpdate(
                                    Uri.parse(
                                            "content://com.sec.ims.settings/profile/"
                                                    + imsProfile.getId()))
                            .withValues(getContentValues(imsProfile))
                            .build());
        }
        int i = 0;
        try {
            ContentProviderResult[] applyBatch =
                    context.getContentResolver().applyBatch(ImsSettings.AUTHORITY, arrayList);
            int length = applyBatch.length;
            int i2 = 0;
            while (i < length) {
                try {
                    i2 += applyBatch[i].count.intValue();
                    i++;
                } catch (OperationApplicationException e) {
                    e = e;
                    i = i2;
                    e.printStackTrace();
                    return i;
                } catch (RemoteException e2) {
                    e = e2;
                    i = i2;
                    e.printStackTrace();
                    return i;
                }
            }
            return i2;
        } catch (OperationApplicationException e3) {
            e = e3;
        } catch (RemoteException e4) {
            e = e4;
        }
    }
}
