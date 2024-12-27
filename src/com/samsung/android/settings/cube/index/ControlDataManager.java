package com.samsung.android.settings.cube.index;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ControlDataManager {
    public static final String[] SELECT_COLUMNS_ALL = {
        GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
        UniversalCredentialUtil.AGENT_TITLE,
        UniversalCredentialUtil.AGENT_SUMMARY,
        "screen_title",
        "keywords",
        "icon_uri",
        "fragment",
        "controller",
        "control_type",
        "recoverable",
        "restore_key"
    };
    public final ControlsIndexer mControlsIndexer;
    public final ControlsDatabaseHelper mHelper;

    public ControlDataManager(Context context) {
        this.mHelper = ControlsDatabaseHelper.getInstance(context);
        ControlsIndexer controlsIndexer = new ControlsIndexer();
        controlsIndexer.mHelper = ControlsDatabaseHelper.getInstance(context);
        ControlDataConverter controlDataConverter = new ControlDataConverter();
        controlDataConverter.mContext = context;
        controlsIndexer.mConverter = controlDataConverter;
        this.mControlsIndexer = controlsIndexer;
    }

    public static ControlData buildControlData(Cursor cursor) {
        String string =
                cursor.getString(
                        cursor.getColumnIndexOrThrow(GoodSettingsContract.EXTRA_NAME.POLICY_KEY));
        String string2 =
                cursor.getString(cursor.getColumnIndex(UniversalCredentialUtil.AGENT_TITLE));
        String string3 =
                cursor.getString(cursor.getColumnIndex(UniversalCredentialUtil.AGENT_SUMMARY));
        String string4 = cursor.getString(cursor.getColumnIndex("screen_title"));
        String string5 = cursor.getString(cursor.getColumnIndex("keywords"));
        String string6 = cursor.getString(cursor.getColumnIndex("icon_uri"));
        String string7 = cursor.getString(cursor.getColumnIndex("fragment"));
        String string8 = cursor.getString(cursor.getColumnIndex("controller"));
        int i = cursor.getInt(cursor.getColumnIndex("control_type"));
        int i2 = cursor.getInt(cursor.getColumnIndex("recoverable"));
        String string9 = cursor.getString(cursor.getColumnIndex("restore_key"));
        ControlData.Builder builder = new ControlData.Builder();
        builder.mKey = string;
        builder.mTitle = string2;
        builder.mSummary = string3;
        builder.mScreenTitle = string4;
        builder.mKeywords = string5;
        builder.mIconUri = TextUtils.isEmpty(string6) ? null : Uri.parse(string6);
        builder.mFragmentClassName = string7;
        builder.mControllerClassName = string8;
        builder.mControlType = i;
        builder.mRecoverable = i2 == 1;
        builder.mRestoreKey = string9;
        return builder.build();
    }

    public final ControlData getControlDataFromKey(String str) {
        int count;
        try {
            Cursor indexedControlData = getIndexedControlData("key = ?", new String[] {str});
            if (indexedControlData == null) {
                count = 0;
            } else {
                try {
                    count = indexedControlData.getCount();
                } finally {
                }
            }
            if (count == 0) {
                throw new IllegalStateException("Invalid Controls key: " + str);
            }
            if (count <= 1) {
                ControlData buildControlData = buildControlData(indexedControlData);
                indexedControlData.close();
                return buildControlData;
            }
            throw new IllegalStateException("Should not match more than 1 action with key: " + str);
        } catch (IllegalStateException e) {
            Log.e("ControlDataManager", e.getMessage());
            return null;
        }
    }

    public final Cursor getIndexedControlData(String str, String[] strArr) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mControlsIndexer.indexControlData();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Cursor query =
                    this.mHelper
                            .getReadableDatabase()
                            .query(
                                    "controls_index",
                                    SELECT_COLUMNS_ALL,
                                    str,
                                    strArr,
                                    null,
                                    null,
                                    null);
            if (query != null) {
                query.moveToFirst();
            }
            return query;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
