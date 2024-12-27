package com.android.settings.slices;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.text.TextUtils;
import android.util.Pair;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SlicesDatabaseAccessor {
    public static final String[] SELECT_COLUMNS_ALL = {
        GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
        UniversalCredentialUtil.AGENT_TITLE,
        UniversalCredentialUtil.AGENT_SUMMARY,
        "screentitle",
        "keywords",
        "icon",
        "fragment",
        "controller",
        "slice_type",
        "unavailable_slice_subtitle",
        "highlight_menu",
        "user_restriction"
    };
    public final Context mContext;
    public final SlicesDatabaseHelper mHelper;

    public SlicesDatabaseAccessor(Context context) {
        this.mContext = context;
        this.mHelper = SlicesDatabaseHelper.getInstance(context);
    }

    public static SliceData buildSliceData(Cursor cursor, Uri uri, boolean z) {
        String string =
                cursor.getString(cursor.getColumnIndex(GoodSettingsContract.EXTRA_NAME.POLICY_KEY));
        String string2 =
                cursor.getString(cursor.getColumnIndex(UniversalCredentialUtil.AGENT_TITLE));
        String string3 =
                cursor.getString(cursor.getColumnIndex(UniversalCredentialUtil.AGENT_SUMMARY));
        String string4 = cursor.getString(cursor.getColumnIndex("screentitle"));
        String string5 = cursor.getString(cursor.getColumnIndex("keywords"));
        int i = cursor.getInt(cursor.getColumnIndex("icon"));
        String string6 = cursor.getString(cursor.getColumnIndex("fragment"));
        String string7 = cursor.getString(cursor.getColumnIndex("controller"));
        int i2 = cursor.getInt(cursor.getColumnIndex("slice_type"));
        String string8 = cursor.getString(cursor.getColumnIndex("unavailable_slice_subtitle"));
        int i3 = cursor.getInt(cursor.getColumnIndex("highlight_menu"));
        String string9 = cursor.getString(cursor.getColumnIndex("user_restriction"));
        if (z) {
            i2 = 0;
        }
        SliceData.Builder builder = new SliceData.Builder();
        builder.mKey = string;
        builder.mTitle = string2;
        builder.mSummary = string3;
        builder.mScreenTitle = string4;
        builder.mKeywords = string5;
        builder.mIconResource = i;
        builder.mFragmentClassName = string6;
        builder.mPrefControllerClassName = string7;
        builder.mUri = uri;
        builder.mSliceType = i2;
        builder.mUnavailableSliceSubtitle = string8;
        builder.mHighlightMenuRes = i3;
        builder.mUserRestriction = string9;
        return builder.build();
    }

    public final Cursor getIndexedSliceData(String str) {
        verifyIndexing();
        Cursor query =
                this.mHelper
                        .getReadableDatabase()
                        .query(
                                "slices_index",
                                SELECT_COLUMNS_ALL,
                                "key = ?",
                                new String[] {str},
                                null,
                                null,
                                null);
        int count = query.getCount();
        if (count == 0) {
            query.close();
            throw new IllegalStateException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Invalid Slices key from path: ", str));
        }
        if (count <= 1) {
            query.moveToFirst();
            return query;
        }
        query.close();
        throw new IllegalStateException(
                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                        "Should not match more than 1 slice with path: ", str));
    }

    public final SliceData getSliceDataFromUri(Uri uri) {
        Pair pathData = SliceBuilderUtils.getPathData(uri);
        if (pathData == null) {
            throw new IllegalStateException("Invalid Slices uri: " + uri);
        }
        Cursor indexedSliceData = getIndexedSliceData((String) pathData.second);
        try {
            SliceData buildSliceData =
                    buildSliceData(
                            indexedSliceData, uri, ((Boolean) pathData.first).booleanValue());
            indexedSliceData.close();
            return buildSliceData;
        } catch (Throwable th) {
            try {
                indexedSliceData.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final List getSliceUris(String str, boolean z) {
        verifyIndexing();
        ArrayList arrayList = new ArrayList();
        Cursor query =
                this.mHelper
                        .getReadableDatabase()
                        .query(
                                "slices_index",
                                new String[] {"slice_uri"},
                                "public_slice".concat(z ? "=1" : "=0"),
                                null,
                                null,
                                null,
                                null);
        try {
            if (!query.moveToFirst()) {
                query.close();
                return arrayList;
            }
            do {
                Uri parse = Uri.parse(query.getString(0));
                if (!TextUtils.isEmpty(str)) {
                    if (TextUtils.equals(str, parse.getAuthority())) {}
                }
                arrayList.add(parse);
            } while (query.moveToNext());
            query.close();
            return arrayList;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final void verifyIndexing() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            SlicesFeatureProviderImpl slicesFeatureProvider =
                    featureFactoryImpl.getSlicesFeatureProvider();
            Context context = this.mContext;
            if (slicesFeatureProvider.mSlicesIndexer == null) {
                Context applicationContext = context.getApplicationContext();
                SlicesIndexer slicesIndexer = new SlicesIndexer();
                slicesIndexer.mContext = applicationContext;
                slicesIndexer.mHelper = SlicesDatabaseHelper.getInstance(applicationContext);
                slicesFeatureProvider.mSlicesIndexer = slicesIndexer;
            }
            slicesFeatureProvider.mSlicesIndexer.indexSliceData();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
