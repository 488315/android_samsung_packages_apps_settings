package com.samsung.android.settings.accessibility.routine;

import android.content.Context;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.routines.v3.data.ErrorContents;
import com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityRoutineActionHandler implements RoutineActionHandler {
    protected static final int ERROR_TYPE = 1;
    protected static final int ERROR_TYPE_EXCLUSIVE = 100;

    public abstract String getActionTag();

    public String getErrorDialogMessage(Context context) {
        return ApnSettings.MVNO_NONE;
    }

    public String getErrorDialogTitle(Context context) {
        return context.getString(R.string.routine_error_dialog_title);
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public ErrorContents onRequestErrorDialogContents(Context context, String str, int i, long j) {
        return new ErrorContents(
                getErrorDialogTitle(context), getErrorDialogMessage(context), null);
    }
}
