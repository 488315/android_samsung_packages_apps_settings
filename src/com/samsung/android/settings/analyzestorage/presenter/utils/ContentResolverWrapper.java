package com.samsung.android.settings.analyzestorage.presenter.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ContentResolverWrapper {
    public static Bundle call(Context context, Uri uri, String str, Bundle bundle) {
        try {
            return context.getContentResolver().call(uri, str, (String) null, bundle);
        } catch (RuntimeException e) {
            Log.w("ContentResolverWrapper", e.getMessage());
            return null;
        }
    }

    public static Cursor query(
            Context context, Uri uri, String[] strArr, String str, String[] strArr2) {
        try {
            return context.getContentResolver().query(uri, strArr, str, strArr2, null);
        } catch (SQLException e) {
            String message = e.getMessage();
            if (!TextUtils.isEmpty(message) && message.contains("ICU")) {
                StringBuilder m =
                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                "search() ] ICU error happened. Check parameters. projection :"
                                    + " null, selection : ",
                                str,
                                " , selectionArgs : ");
                m.append(Arrays.toString(strArr2));
                m.append(" , orderBy : null");
                Log.d("ContentResolverWrapper", m.toString());
            }
            throw e;
        } catch (RuntimeException e2) {
            Log.w("ContentResolverWrapper", e2.getMessage());
            return null;
        }
    }
}
