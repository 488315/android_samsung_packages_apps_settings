package com.android.settings.homepage.contextualcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ContextualCardFeatureProviderImpl {
    public final Context mContext;

    public ContextualCardFeatureProviderImpl(Context context) {
        this.mContext = context;
    }

    public int resetDismissedTime(long j) {
        SQLiteDatabase writableDatabase =
                CardDatabaseHelper.getInstance(this.mContext).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.putNull("dismissed_timestamp");
        int update =
                writableDatabase.update(
                        "cards",
                        contentValues,
                        "dismissed_timestamp < ? AND dismissed_timestamp IS NOT NULL",
                        new String[] {String.valueOf(j)});
        if (Build.IS_DEBUGGABLE) {
            MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                    update,
                    "Reset ",
                    " records of dismissed time.",
                    "ContextualCardFeatureProvider");
        }
        return update;
    }
}
