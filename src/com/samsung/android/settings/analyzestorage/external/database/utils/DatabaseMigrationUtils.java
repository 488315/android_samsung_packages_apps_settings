package com.samsung.android.settings.analyzestorage.external.database.utils;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DatabaseMigrationUtils {
    public static ContentValues convertCursorToContentValues(Cursor cursor) {
        ContentValues contentValues = new ContentValues();
        String[] columnNames = cursor.getColumnNames();
        if (Objects.isNull(columnNames)) {
            return contentValues;
        }
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            int type = cursor.getType(i);
            if (type == 0) {
                contentValues.putNull(columnNames[i]);
            } else if (type == 1) {
                contentValues.put(columnNames[i], Long.valueOf(cursor.getLong(i)));
            } else if (type == 2) {
                contentValues.put(columnNames[i], Double.valueOf(cursor.getDouble(i)));
            } else if (type == 3) {
                contentValues.put(columnNames[i], cursor.getString(i));
            } else if (type == 4) {
                contentValues.put(columnNames[i], cursor.getBlob(i));
            }
        }
        return contentValues;
    }
}
