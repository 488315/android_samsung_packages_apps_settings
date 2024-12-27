package com.android.settings.dashboard.suggestions;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SuggestionStateProvider extends ContentProvider {
    static final String EXTRA_CANDIDATE_ID = "candidate_id";
    static final String METHOD_GET_SUGGESTION_STATE = "getSuggestionState";

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0091, code lost:

       if (r8.getEnrolledFingerprints().size() == 1) goto L27;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0115, code lost:

       if (r11.isAllowUserControl() == false) goto L27;
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01c5, code lost:

       if (r8 != false) goto L27;
    */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle call(
            java.lang.String r9, java.lang.String r10, android.os.Bundle r11) {
        /*
            Method dump skipped, instructions count: 503
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.dashboard.suggestions.SuggestionStateProvider.call(java.lang.String,"
                    + " java.lang.String, android.os.Bundle):android.os.Bundle");
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("delete operation not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        throw new UnsupportedOperationException("getType operation not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("insert operation not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new UnsupportedOperationException("query operation not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("update operation not supported currently.");
    }
}
