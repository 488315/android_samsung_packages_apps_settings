package com.samsung.android.settings.accessibility.hearing.routine;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback;
import com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LiveCaptionRoutineActionHandler extends AccessibilityRoutineSimpleOnOffActionHandler {
    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler, com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public /* bridge */ /* synthetic */ void checkValidity(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        super.checkValidity(context, str, parameterValues, j, responseCallback);
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionHandler
    public String getActionTag() {
        return "accessibility_live_caption";
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler, com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public /* bridge */ /* synthetic */ void getPreviewImageFileDescriptor(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        super.getPreviewImageFileDescriptor(context, str, parameterValues, j, responseCallback);
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler
    public String getTitle(Context context) {
        return context.getString(R.string.live_caption_title);
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler
    public boolean isActionEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "odi_captions_enabled", 0) == 1;
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler, com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public SupportStatus isSupported(Context context, String str) {
        List<ResolveInfo> queryIntentActivities =
                context.getPackageManager()
                        .queryIntentActivities(
                                new Intent("com.android.settings.action.live_caption"), 0);
        return (queryIntentActivities == null || queryIntentActivities.isEmpty())
                ? SupportStatus.NOT_SUPPORTED
                : SupportStatus.SUPPORTED;
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler, com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public /* bridge */ /* synthetic */ String onMigrate(Context context, List list) {
        super.onMigrate(context, list);
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler
    public void setActionEnable(Context context, boolean z) {
        Settings.Secure.putInt(context.getContentResolver(), "odi_captions_enabled", z ? 1 : 0);
    }
}
