package com.samsung.android.settings.actions;

import android.content.Context;
import android.database.Cursor;
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.sec.ims.ImsManager;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionsDatabaseAccessor {
    public static final String[] SELECT_COLUMNS_ALL = {
        GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
        UniversalCredentialUtil.AGENT_TITLE,
        UniversalCredentialUtil.AGENT_SUMMARY,
        "screentitle",
        "keywords",
        "icon",
        "fragment",
        "controller",
        ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE,
        "recoverable",
        "restore_key",
        "target_fragment",
        "payload"
    };
    public final ActionsIndexer mActionsIndexer;
    public final ActionsDatabaseHelper mHelper;

    public ActionsDatabaseAccessor(Context context) {
        this.mHelper = ActionsDatabaseHelper.getInstance(context);
        ActionsIndexer actionsIndexer = new ActionsIndexer();
        actionsIndexer.mHelper = ActionsDatabaseHelper.getInstance(context);
        ActionDataConverter actionDataConverter = new ActionDataConverter();
        actionDataConverter.mIndexableDataMap = new HashMap();
        actionDataConverter.mContext = context;
        actionsIndexer.mConverter = actionDataConverter;
        this.mActionsIndexer = actionsIndexer;
    }

    public static ActionData buildActionData(Cursor cursor) {
        String str;
        LaunchPayload launchPayload;
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
        int i2 = cursor.getInt(cursor.getColumnIndex(ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE));
        int i3 = cursor.getInt(cursor.getColumnIndex("recoverable"));
        String string8 = cursor.getString(cursor.getColumnIndex("restore_key"));
        String string9 = cursor.getString(cursor.getColumnIndex("target_fragment"));
        byte[] blob = cursor.getBlob(cursor.getColumnIndex("payload"));
        if (blob == null) {
            launchPayload = null;
            str = string9;
        } else {
            Parcelable.Creator<LaunchPayload> creator = LaunchPayload.CREATOR;
            Parcel obtain = Parcel.obtain();
            str = string9;
            obtain.unmarshall(blob, 0, blob.length);
            obtain.setDataPosition(0);
            LaunchPayload createFromParcel = creator.createFromParcel(obtain);
            obtain.recycle();
            launchPayload = createFromParcel;
        }
        ActionData.Builder builder = new ActionData.Builder();
        builder.mKey = string;
        builder.mTitle = string2;
        builder.mSummary = string3;
        builder.mScreenTitle = string4;
        builder.mKeywords = string5;
        builder.mIconResource = i;
        builder.mFragmentClassName = string6;
        builder.mPrefControllerClassName = string7;
        builder.mActionType = i2;
        builder.mRecoverable = i3 == 1;
        builder.mRestoreKey = string8;
        builder.mTargetFragment = str;
        builder.mPayload = launchPayload;
        return builder.build();
    }

    public final Cursor getIndexedActionData(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mActionsIndexer.indexActionData();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Cursor query =
                    this.mHelper
                            .getReadableDatabase()
                            .query(
                                    "actions_index",
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
                                "Invalid Actions key from path: ", str));
            }
            if (count <= 1) {
                query.moveToFirst();
                return query;
            }
            query.close();
            throw new IllegalStateException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Should not match more than 1 action with path: ", str));
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
