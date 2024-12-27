package com.android.settings.homepage.contextualcards;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CardDatabaseHelper extends SQLiteOpenHelper {
    static CardDatabaseHelper sCardDatabaseHelper;

    public static synchronized CardDatabaseHelper getInstance(Context context) {
        CardDatabaseHelper cardDatabaseHelper;
        synchronized (CardDatabaseHelper.class) {
            try {
                if (sCardDatabaseHelper == null) {
                    sCardDatabaseHelper =
                            new CardDatabaseHelper(
                                    context.getApplicationContext(), "homepage_cards.db", null, 7);
                }
                cardDatabaseHelper = sCardDatabaseHelper;
            } catch (Throwable th) {
                throw th;
            }
        }
        return cardDatabaseHelper;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(
                "CREATE TABLE cards(name TEXT NOT NULL PRIMARY KEY, type INTEGER NOT NULL, score"
                    + " DOUBLE NOT NULL, slice_uri TEXT, category INTEGER DEFAULT 0, package_name"
                    + " TEXT NOT NULL, app_version INTEGER NOT NULL, dismissed_timestamp"
                    + " INTEGER);");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < i2) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "Reconstructing DB from ", " to ", i, i2, "CardDatabaseHelper");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS cards");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE cards(name TEXT NOT NULL PRIMARY KEY, type INTEGER NOT NULL,"
                        + " score DOUBLE NOT NULL, slice_uri TEXT, category INTEGER DEFAULT 0,"
                        + " package_name TEXT NOT NULL, app_version INTEGER NOT NULL,"
                        + " dismissed_timestamp INTEGER);");
        }
    }
}
