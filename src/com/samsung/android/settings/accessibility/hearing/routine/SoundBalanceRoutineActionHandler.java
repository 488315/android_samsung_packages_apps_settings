package com.samsung.android.settings.accessibility.hearing.routine;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.notification.zen.lifestyle.LifeStyleDND$$ExternalSyntheticOutline0;

import com.samsung.android.sdk.routines.v3.data.ActionResult$Error;
import com.samsung.android.sdk.routines.v3.data.ActionResult$ResultCode;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback;
import com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda8;
import com.samsung.android.sdk.routines.v3.template.UiTemplate;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionHandler;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SoundBalanceRoutineActionHandler extends AccessibilityRoutineActionHandler {
    private static final boolean IS_SUPPORT_STEREO_SPEAKER =
            SecAccessibilityUtils.isSupportStereoSpeaker();

    private static boolean canEnableSoundBalance(Context context) {
        return (isMuteAllSoundOffEnabled(context)
                        || (Settings.System.getInt(
                                        context.getContentResolver(),
                                        "run_amplify_ambient_sound",
                                        0)
                                != 0))
                ? false
                : true;
    }

    private int convertToPercent(float f) {
        return (int) ((1.0f - f) * 50.0f);
    }

    private float getBalance(Context context, String str) {
        return Settings.System.getFloat(context.getContentResolver(), str, 0.0f);
    }

    private static boolean isMuteAllSoundOffEnabled(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "all_sound_off", 0) != 0;
    }

    private void setBalance(Context context, String str, float f) {
        Settings.System.putFloat(context.getContentResolver(), str, f);
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
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
        return "accessibility_sound_balance";
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public void getCurrentParameterValues(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        ParameterValues parameterValues2 = new ParameterValues();
        parameterValues2.put(
                "connected_audio", Float.valueOf(getBalance(context, "master_balance")));
        parameterValues2.put(
                "phone_speakers", Float.valueOf(getBalance(context, "speaker_balance")));
        responseCallback.setResponse(parameterValues2);
    }

    @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionHandler
    public String getErrorDialogMessage(Context context) {
        return isMuteAllSoundOffEnabled(context)
                ? context.getString(R.string.routine_error_dialog_message_mute_all_sound)
                : context.getString(R.string.routine_error_dialog_message_amplify_ambient_sound);
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public void getParameterLabel(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        Float valueOf = Float.valueOf(0.0f);
        float floatValue = parameterValues.getNumber("connected_audio", valueOf).floatValue();
        float floatValue2 = parameterValues.getNumber("phone_speakers", valueOf).floatValue();
        int convertToPercent = convertToPercent(floatValue);
        int convertToPercent2 = convertToPercent(floatValue2);
        StringBuilder sb = new StringBuilder();
        sb.append(
                context.getString(
                        R.string.routine_state_description_connected_audio,
                        Integer.valueOf(convertToPercent),
                        Integer.valueOf(100 - convertToPercent)));
        if (IS_SUPPORT_STEREO_SPEAKER) {
            if (Utils.isTablet()) {
                sb.append('\n');
                sb.append(
                        context.getString(
                                R.string.routine_state_description_tablet_speakers,
                                Integer.valueOf(convertToPercent2),
                                Integer.valueOf(100 - convertToPercent2)));
            } else {
                sb.append('\n');
                sb.append(
                        context.getString(
                                R.string.routine_state_description_phone_speakers,
                                Integer.valueOf(convertToPercent2),
                                Integer.valueOf(100 - convertToPercent2)));
            }
        }
        responseCallback.setResponse(sb.toString());
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public /* bridge */ /* synthetic */ void getPreviewImageFileDescriptor(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        super.getPreviewImageFileDescriptor(context, str, parameterValues, j, responseCallback);
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public SupportStatus isSupported(Context context, String str) {
        return AccessibilityRune.isSupportAudioFeature("soundbalance")
                ? SupportStatus.SUPPORTED
                : SupportStatus.NOT_SUPPORTED;
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public /* bridge */ /* synthetic */ String onMigrate(Context context, List list) {
        super.onMigrate(context, list);
        return null;
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public void onPerformAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionResultCallback actionResultCallback) {
        if (!canEnableSoundBalance(context)) {
            ((ActionDispatcher$$ExternalSyntheticLambda8) actionResultCallback)
                    .actionFinished(new ActionResult$Error(1));
            return;
        }
        ActionResult$ResultCode actionResult$ResultCode = ActionResult$ResultCode.SUCCESS;
        setBalance(
                context,
                "master_balance",
                LifeStyleDND$$ExternalSyntheticOutline0.m(
                        0.0f, parameterValues, "connected_audio"));
        if (IS_SUPPORT_STEREO_SPEAKER) {
            setBalance(
                    context,
                    "speaker_balance",
                    LifeStyleDND$$ExternalSyntheticOutline0.m(
                            0.0f, parameterValues, "phone_speakers"));
        }
        ((ActionDispatcher$$ExternalSyntheticLambda8) actionResultCallback)
                .actionFinished(new ActionResult$Error(actionResult$ResultCode));
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public void onPerformReverseAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionResultCallback actionResultCallback) {
        Float valueOf = Float.valueOf(0.0f);
        setBalance(
                context,
                "master_balance",
                parameterValues.getNumber("connected_audio", valueOf).floatValue());
        if (IS_SUPPORT_STEREO_SPEAKER) {
            setBalance(
                    context,
                    "speaker_balance",
                    parameterValues.getNumber("phone_speakers", valueOf).floatValue());
        }
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public UiTemplate onRequestTemplateContents(Context context, String str) {
        Log.e(
                "RoutineActionHandler",
                "onRequestTemplateContents: this should not be called without overriding!!!");
        return new UiTemplate(new Bundle());
    }
}
