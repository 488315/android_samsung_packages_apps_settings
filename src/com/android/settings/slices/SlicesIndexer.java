package com.android.settings.slices;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SlicesIndexer implements Runnable {
    public Context mContext;
    public SlicesDatabaseHelper mHelper;

    /* JADX WARN: Code restructure failed: missing block: B:53:0x0115, code lost:

       if ((r15 instanceof com.android.settings.notification.RingerModeAffectedVolumePreferenceController) == false) goto L36;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.android.settings.slices.SliceData> getSliceData() {
        /*
            Method dump skipped, instructions count: 878
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.slices.SlicesIndexer.getSliceData():java.util.List");
    }

    public final void indexSliceData() {
        SlicesDatabaseHelper slicesDatabaseHelper = this.mHelper;
        if (slicesDatabaseHelper
                        .mContext
                        .getSharedPreferences("slices_shared_prefs", 0)
                        .getBoolean(slicesDatabaseHelper.getBuildTag(), false)
                && slicesDatabaseHelper
                        .mContext
                        .getSharedPreferences("slices_shared_prefs", 0)
                        .getBoolean(Locale.getDefault().toString(), false)) {
            Log.d("SlicesIndexer", "Slices already indexed - returning.");
            return;
        }
        SQLiteDatabase writableDatabase = this.mHelper.getWritableDatabase();
        long currentTimeMillis = System.currentTimeMillis();
        writableDatabase.beginTransaction();
        try {
            this.mHelper
                    .mContext
                    .getSharedPreferences("slices_shared_prefs", 0)
                    .edit()
                    .clear()
                    .apply();
            writableDatabase.execSQL("DROP TABLE IF EXISTS slices_index");
            writableDatabase.execSQL(
                    "CREATE VIRTUAL TABLE slices_index USING fts4(key, slice_uri, title, summary,"
                        + " screentitle, keywords, icon, fragment, controller, slice_type,"
                        + " unavailable_slice_subtitle, public_slice, highlight_menu,"
                        + " user_restriction INTEGER DEFAULT 0 );");
            Log.d("SlicesDatabaseHelper", "Created databases");
            insertSliceData(writableDatabase, getSliceData());
            SlicesDatabaseHelper slicesDatabaseHelper2 = this.mHelper;
            slicesDatabaseHelper2
                    .mContext
                    .getSharedPreferences("slices_shared_prefs", 0)
                    .edit()
                    .putBoolean(slicesDatabaseHelper2.getBuildTag(), true)
                    .apply();
            slicesDatabaseHelper2
                    .mContext
                    .getSharedPreferences("slices_shared_prefs", 0)
                    .edit()
                    .putBoolean(Locale.getDefault().toString(), true)
                    .apply();
            Log.d(
                    "SlicesIndexer",
                    "Indexing slices database took: "
                            + (System.currentTimeMillis() - currentTimeMillis));
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public void insertSliceData(SQLiteDatabase sQLiteDatabase, List<SliceData> list) {
        for (SliceData sliceData : list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, sliceData.mKey);
            contentValues.put("slice_uri", sliceData.mUri.toString());
            contentValues.put(UniversalCredentialUtil.AGENT_TITLE, sliceData.mTitle);
            contentValues.put(UniversalCredentialUtil.AGENT_SUMMARY, sliceData.mSummary);
            CharSequence charSequence = sliceData.mScreenTitle;
            if (charSequence != null) {
                contentValues.put("screentitle", charSequence.toString());
            }
            contentValues.put("keywords", sliceData.mKeywords);
            contentValues.put("icon", Integer.valueOf(sliceData.mIconResource));
            contentValues.put("fragment", sliceData.mFragmentClassName);
            contentValues.put("controller", sliceData.mPreferenceController);
            contentValues.put("slice_type", Integer.valueOf(sliceData.mSliceType));
            contentValues.put("unavailable_slice_subtitle", sliceData.mUnavailableSliceSubtitle);
            contentValues.put("public_slice", Boolean.valueOf(sliceData.mIsPublicSlice));
            contentValues.put("highlight_menu", Integer.valueOf(sliceData.mHighlightMenuRes));
            contentValues.put("user_restriction", sliceData.mUserRestriction);
            sQLiteDatabase.replaceOrThrow("slices_index", null, contentValues);
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        indexSliceData();
    }
}
