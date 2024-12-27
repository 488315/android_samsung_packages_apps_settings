package com.android.settings.inputmethod;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.UserDictionary;
import android.util.ArraySet;

import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserDictionaryCursorLoader extends AsyncTaskLoader {
    static final String[] QUERY_PROJECTION = {"_id", "word", "shortcut"};
    public Cursor mCursor;
    public final String mLocale;

    public UserDictionaryCursorLoader(Context context, String str) {
        super(context);
        new Loader.ForceLoadContentObserver();
        this.mLocale = str;
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final void cancelLoadInBackground() {
        synchronized (this) {
        }
    }

    @Override // androidx.loader.content.AsyncTaskLoader, androidx.loader.content.Loader
    public final void dump(
            String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.print("mUri=");
        printWriter.println((Object) null);
        printWriter.print(str);
        printWriter.print("mProjection=");
        printWriter.println(Arrays.toString((Object[]) null));
        printWriter.print(str);
        printWriter.print("mSelection=");
        printWriter.println((String) null);
        printWriter.print(str);
        printWriter.print("mSelectionArgs=");
        printWriter.println(Arrays.toString((Object[]) null));
        printWriter.print(str);
        printWriter.print("mSortOrder=");
        printWriter.println((String) null);
        printWriter.print(str);
        printWriter.print("mCursor=");
        printWriter.println(this.mCursor);
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        Cursor query;
        String[] strArr = QUERY_PROJECTION;
        MatrixCursor matrixCursor = new MatrixCursor(strArr);
        String str = this.mLocale;
        if (ApnSettings.MVNO_NONE.equals(str)) {
            query =
                    this.mContext
                            .getContentResolver()
                            .query(
                                    UserDictionary.Words.CONTENT_URI,
                                    strArr,
                                    "locale is null",
                                    null,
                                    "UPPER(word)");
        } else {
            if (str == null) {
                str = Locale.getDefault().toString();
            }
            query =
                    this.mContext
                            .getContentResolver()
                            .query(
                                    UserDictionary.Words.CONTENT_URI,
                                    strArr,
                                    "locale=?",
                                    new String[] {str},
                                    "UPPER(word)");
        }
        ArraySet arraySet = new ArraySet();
        query.moveToFirst();
        while (!query.isAfterLast()) {
            int i = query.getInt(0);
            String string = query.getString(1);
            String string2 = query.getString(2);
            int hash = Objects.hash(string, string2);
            if (!arraySet.contains(Integer.valueOf(hash))) {
                arraySet.add(Integer.valueOf(hash));
                matrixCursor.addRow(new Object[] {Integer.valueOf(i), string, string2});
            }
            query.moveToNext();
        }
        query.close();
        return matrixCursor;
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final void onCanceled(Object obj) {
        Cursor cursor = (Cursor) obj;
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        cursor.close();
    }

    @Override // androidx.loader.content.Loader
    public final void onReset() {
        onCancelLoad();
        Cursor cursor = this.mCursor;
        if (cursor != null && !cursor.isClosed()) {
            this.mCursor.close();
        }
        this.mCursor = null;
    }

    @Override // androidx.loader.content.Loader
    public final void onStartLoading() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            deliverResult(cursor);
        }
        boolean z = this.mContentChanged;
        this.mContentChanged = false;
        this.mProcessingChange |= z;
        if (z || this.mCursor == null) {
            onForceLoad();
        }
    }

    @Override // androidx.loader.content.Loader
    public final void onStopLoading() {
        onCancelLoad();
    }

    @Override // androidx.loader.content.Loader
    public final void deliverResult(Cursor cursor) {
        if (this.mReset) {
            if (cursor != null) {
                cursor.close();
                return;
            }
            return;
        }
        Cursor cursor2 = this.mCursor;
        this.mCursor = cursor;
        if (this.mStarted) {
            super.deliverResult((Object) cursor);
        }
        if (cursor2 == null || cursor2 == cursor || cursor2.isClosed()) {
            return;
        }
        cursor2.close();
    }
}
