package com.samsung.android.settings.cube.index;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ControlsIndexer {
    public ControlDataConverter mConverter;
    public ControlsDatabaseHelper mHelper;

    public static void insertControlData(SQLiteDatabase sQLiteDatabase, List list) {
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            ControlData controlData = (ControlData) it.next();
            ContentValues contentValues = new ContentValues();
            contentValues.put(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, controlData.mKey);
            contentValues.put(UniversalCredentialUtil.AGENT_TITLE, controlData.mTitle);
            contentValues.put(UniversalCredentialUtil.AGENT_SUMMARY, controlData.mSummary);
            CharSequence charSequence = controlData.mScreenTitle;
            String str = ApnSettings.MVNO_NONE;
            contentValues.put(
                    "screen_title",
                    charSequence == null ? ApnSettings.MVNO_NONE : charSequence.toString());
            contentValues.put("keywords", controlData.mKeywords);
            Uri uri = controlData.mIconUri;
            if (uri != null) {
                str = uri.toString();
            }
            contentValues.put("icon_uri", str);
            contentValues.put("fragment", controlData.mFragmentClassName);
            contentValues.put("controller", controlData.mControllerClassName);
            contentValues.put("control_type", Integer.valueOf(controlData.mControlType));
            contentValues.put(
                    "recoverable",
                    controlData.mRecoverable ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            contentValues.put("restore_key", controlData.mRestoreKey);
            sQLiteDatabase.replaceOrThrow("controls_index", null, contentValues);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x018f A[LOOP:0: B:14:0x00b7->B:29:0x018f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01a1 A[EDGE_INSN: B:30:0x01a1->B:6:0x01a1 BREAK  A[LOOP:0: B:14:0x00b7->B:29:0x018f], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getControlData() {
        /*
            Method dump skipped, instructions count: 429
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.cube.index.ControlsIndexer.getControlData():java.util.List");
    }

    public final void indexControlData() {
        ControlsDatabaseHelper controlsDatabaseHelper = this.mHelper;
        if (controlsDatabaseHelper
                        .mContext
                        .getSharedPreferences("controls_prefs", 0)
                        .getBoolean(Build.FINGERPRINT, false)
                && controlsDatabaseHelper
                        .mContext
                        .getSharedPreferences("controls_prefs", 0)
                        .getBoolean(Locale.getDefault().toString(), false)) {
            Log.d("ControlsIndexer", "Control already indexed - returning.");
            return;
        }
        SQLiteDatabase writableDatabase = controlsDatabaseHelper.getWritableDatabase();
        long currentTimeMillis = System.currentTimeMillis();
        writableDatabase.beginTransaction();
        try {
            controlsDatabaseHelper.reconstruct(writableDatabase);
            insertControlData(writableDatabase, getControlData());
            controlsDatabaseHelper.setIndexedState();
            Log.d(
                    "ControlsIndexer",
                    "Indexing actions database took: "
                            + (System.currentTimeMillis() - currentTimeMillis));
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }
}
