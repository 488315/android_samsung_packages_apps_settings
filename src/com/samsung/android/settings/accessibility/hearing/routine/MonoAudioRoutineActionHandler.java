package com.samsung.android.settings.accessibility.hearing.routine;

import android.content.Context;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.sdk.routines.v3.data.ActionResult$Error;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback;
import com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda8;
import com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MonoAudioRoutineActionHandler extends AccessibilityRoutineSimpleOnOffActionHandler {
    private boolean isMuteAllSoundOffEnabled(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "all_sound_off", 0) != 0;
    }

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
        return "accessibility_mono_audio";
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionHandler
    public String getErrorDialogMessage(Context context) {
        return context.getString(R.string.routine_error_dialog_message_mute_all_sound);
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
        return context.getString(R.string.accessibility_toggle_primary_mono_title);
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler
    public boolean isActionEnabled(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), "master_mono", 0, -2)
                == 1;
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler, com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public /* bridge */ /* synthetic */ SupportStatus isSupported(Context context, String str) {
        return SupportStatus.SUPPORTED;
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler, com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public /* bridge */ /* synthetic */ String onMigrate(Context context, List list) {
        super.onMigrate(context, list);
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler, com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public final void onPerformAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionResultCallback actionResultCallback) {
        if (!isMuteAllSoundOffEnabled(context)) {
            super.onPerformAction(context, str, parameterValues, j, actionResultCallback);
        } else {
            ((ActionDispatcher$$ExternalSyntheticLambda8) actionResultCallback)
                    .actionFinished(new ActionResult$Error(1));
        }
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineSimpleOnOffActionHandler
    public void setActionEnable(Context context, boolean z) {
        Settings.System.putIntForUser(context.getContentResolver(), "master_mono", z ? 1 : 0, -2);
    }
}
