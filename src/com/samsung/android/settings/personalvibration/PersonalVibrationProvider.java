package com.samsung.android.settings.personalvibration;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.asbase.vibration.VibPickerConstants;
import com.samsung.android.settings.asbase.vibration.VibUri;

import java.util.HashMap;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PersonalVibrationProvider extends ContentProvider {
    public Locale mLocale;

    public static String getTableByUri(Uri uri) {
        return uri.toString().contains("registerinfo")
                ? "registerinfo"
                : uri.toString().contains("notification")
                        ? "notification"
                        : uri.toString().contains("customindex") ? "customindex" : "registerinfo";
    }

    @Override // android.content.ContentProvider
    public final int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        long currentTimeMillis = System.currentTimeMillis();
        Log.d("PersonalVibrationProvider", "bulkInsert start");
        SQLiteDatabase writableDatabase =
                PersonalVibrationDatabaseHelper.getInstance(getContext()).getWritableDatabase();
        try {
            writableDatabase.beginTransaction();
            int i = 0;
            for (ContentValues contentValues : contentValuesArr) {
                if (PersonalVibrationDatabaseHelper.getInstance(getContext())
                                .getWritableDatabase()
                                .insertOrThrow(
                                        getTableByUri(uri), ApnSettings.MVNO_NONE, contentValues)
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
                    "PersonalVibrationProvider",
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
    public final int delete(Uri uri, String str, String[] strArr) {
        int delete =
                PersonalVibrationDatabaseHelper.getInstance(getContext())
                        .getWritableDatabase()
                        .delete(getTableByUri(uri), str, strArr);
        if (delete > 0) {
            notifyChange(uri);
        }
        return delete;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        long insert =
                PersonalVibrationDatabaseHelper.getInstance(getContext())
                        .getWritableDatabase()
                        .insert(getTableByUri(uri), null, contentValues);
        if (insert <= 0) {
            return null;
        }
        notifyChange(uri);
        return ContentUris.withAppendedId(uri, insert);
    }

    public final void notifyChange(Uri uri) {
        try {
            getContext().getContentResolver().notifyChange(uri, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.content.ContentProvider, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        Locale locale = this.mLocale;
        if (locale == null) {
            this.mLocale = configuration.getLocales().get(0);
            updateSilentPatternName();
        } else if (locale != configuration.getLocales().get(0)) {
            this.mLocale = configuration.getLocales().get(0);
            updateSilentPatternName();
        }
        super.onConfigurationChanged(configuration);
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        Trace.beginSection("PersonalVibrationProvider#onCreate");
        Trace.endSection();
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int match = VibPickerConstants.PATH_URI_MATCHER.match(uri);
        VibUri vibUri = (VibUri) ((HashMap) VibUri.typeMap).get(Integer.valueOf(match));
        Cursor query =
                PersonalVibrationDatabaseHelper.getInstance(getContext())
                        .getReadableDatabase()
                        .query(
                                getTableByUri(uri),
                                strArr,
                                vibUri.getSelection(str),
                                vibUri.getSelectionArgs(uri, strArr2),
                                null,
                                null,
                                str2);
        if (query != null) {
            query.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return query;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int update =
                PersonalVibrationDatabaseHelper.getInstance(getContext())
                        .getWritableDatabase()
                        .update(getTableByUri(uri), contentValues, str, strArr);
        if (update > 0) {
            notifyChange(uri);
        }
        return update;
    }

    public final void updateSilentPatternName() {
        ContentValues contentValues = new ContentValues();
        String string = getContext().getString(R.string.sec_vib_picker_silent_pattern);
        contentValues.put("vibration_name", string);
        int update =
                update(
                        Uri.parse(
                                "content://com.android.settings.personalvibration.PersonalVibrationProvider/registerinfo"),
                        contentValues,
                        "vibration_pattern=?",
                        new String[] {Integer.toString(50084)});
        StringBuilder sb = new StringBuilder("Updating the name of the ringtone silent pattern ");
        sb.append(update == -1 ? "fail" : "success");
        sb.append(" to ");
        sb.append(string);
        Log.d("PersonalVibrationProvider", sb.toString());
        int update2 =
                update(
                        Uri.parse(
                                "content://com.android.settings.personalvibration.PersonalVibrationProvider/notification"),
                        contentValues,
                        "vibration_pattern=?",
                        new String[] {Integer.toString(50084)});
        StringBuilder sb2 =
                new StringBuilder("Updating the name of the notification silent pattern ");
        sb2.append(update2 == -1 ? "fail" : "success");
        sb2.append(" to ");
        sb2.append(string);
        Log.d("PersonalVibrationProvider", sb2.toString());
    }
}
