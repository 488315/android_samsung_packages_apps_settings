package com.samsung.android.settings.asbase.vibration;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VibPickerQueryHelper {
    public final ContentResolver mResolver;
    public Uri mUri;

    public VibPickerQueryHelper(ContentResolver contentResolver) {
        this.mResolver = contentResolver;
    }

    public final List getPatternList(int i) {
        ArrayList arrayList = new ArrayList();
        String str = i == 2 ? "Custom" : "Galaxy";
        try {
            Cursor query =
                    this.mResolver.query(
                            this.mUri,
                            null,
                            "is_custom=?",
                            new String[] {i == 2 ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN},
                            null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        Log.d("VibPickerQueryHelper", str + " db count : " + query.getCount());
                        do {
                            Bundle bundle = new Bundle();
                            bundle.putInt("_id", query.getInt(query.getColumnIndex("_id")));
                            bundle.putString(
                                    "vibration_name",
                                    query.getString(query.getColumnIndex("vibration_name")));
                            bundle.putInt(
                                    "vibration_pattern",
                                    query.getInt(query.getColumnIndex("vibration_pattern")));
                            bundle.putInt(
                                    "vibration_type",
                                    query.getInt(query.getColumnIndex("vibration_type")));
                            bundle.putString(
                                    "custom_data",
                                    query.getString(query.getColumnIndex("custom_data")));
                            bundle.putInt(
                                    "is_custom", query.getInt(query.getColumnIndex("is_custom")));
                            bundle.putInt("position", query.getPosition());
                            arrayList.add(bundle);
                        } while (query.moveToNext());
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public final String getPatternName(int i) {
        String str = null;
        try {
            Cursor query =
                    this.mResolver.query(
                            this.mUri,
                            null,
                            "vibration_pattern=?",
                            new String[] {Integer.toString(i)},
                            null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        str = query.getString(query.getColumnIndex("vibration_name"));
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
