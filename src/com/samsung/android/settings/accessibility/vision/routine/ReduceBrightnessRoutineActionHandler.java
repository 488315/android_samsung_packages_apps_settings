package com.samsung.android.settings.accessibility.vision.routine;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import android.os.Bundle;
import android.util.Log;

import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.notification.zen.lifestyle.LifeStyleDND$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;

import com.samsung.android.sdk.routines.v3.data.ActionResult$Error;
import com.samsung.android.sdk.routines.v3.data.ActionResult$ResultCode;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback;
import com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda8;
import com.samsung.android.sdk.routines.v3.template.UiTemplate;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionHandler;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ReduceBrightnessRoutineActionHandler extends AccessibilityRoutineActionHandler {
    private static final String EXTRA_DIM_ACTION_TAG = "accessibility_extra_dim";
    public static final String KEY_INTENSITY = "intensity";
    public static final String KEY_SWITCH = "switch";
    private static final String TAG = "ExtraDimActionHandler";

    private void setReduceBrightnessDB(Context context, ParameterValues parameterValues) {
        ColorDisplayManager colorDisplayManager =
                (ColorDisplayManager) context.getSystemService(ColorDisplayManager.class);
        boolean booleanValue = parameterValues.getBoolean(KEY_SWITCH, Boolean.FALSE).booleanValue();
        float m = LifeStyleDND$$ExternalSyntheticOutline0.m(0.0f, parameterValues, KEY_INTENSITY);
        Log.i(TAG, "setReduceBrightnessDB activated : " + booleanValue + "  intensity : " + m);
        colorDisplayManager.setReduceBrightColorsActivated(booleanValue);
        colorDisplayManager.setReduceBrightColorsStrength((int) m);
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
        return EXTRA_DIM_ACTION_TAG;
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public void getCurrentParameterValues(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        ColorDisplayManager colorDisplayManager =
                (ColorDisplayManager) context.getSystemService(ColorDisplayManager.class);
        boolean isReduceBrightColorsActivated = colorDisplayManager.isReduceBrightColorsActivated();
        int reduceBrightColorsStrength = colorDisplayManager.getReduceBrightColorsStrength();
        ParameterValues parameterValues2 = new ParameterValues();
        parameterValues2.put(KEY_SWITCH, Boolean.valueOf(isReduceBrightColorsActivated));
        parameterValues2.put(KEY_INTENSITY, Float.valueOf(reduceBrightColorsStrength));
        responseCallback.setResponse(parameterValues2);
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public void getParameterLabel(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        boolean booleanValue = parameterValues.getBoolean(KEY_SWITCH, Boolean.FALSE).booleanValue();
        int intValue = parameterValues.getNumber(KEY_INTENSITY, Float.valueOf(-1.0f)).intValue();
        String string =
                context.getString(
                        booleanValue
                                ? R.string.routine_switch_on_text
                                : R.string.routine_switch_off_text);
        if (!booleanValue) {
            responseCallback.setResponse(string);
            return;
        }
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(string, ": ");
        m.append(Utils.formatPercentage(intValue));
        responseCallback.setResponse(m.toString());
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
        return SecAccessibilityUtils.isSupportReduceBrightness(context)
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
        if (isSupported(context, str) != SupportStatus.SUPPORTED) {
            ((ActionDispatcher$$ExternalSyntheticLambda8) actionResultCallback)
                    .actionFinished(
                            new ActionResult$Error(ActionResult$ResultCode.FAIL_NOT_SUPPORTED));
        } else {
            Log.i(TAG, "onPerformAction called");
            setReduceBrightnessDB(context, parameterValues);
            ((ActionDispatcher$$ExternalSyntheticLambda8) actionResultCallback)
                    .actionFinished(new ActionResult$Error(ActionResult$ResultCode.SUCCESS));
        }
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public void onPerformReverseAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionResultCallback actionResultCallback) {
        setReduceBrightnessDB(context, parameterValues);
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public UiTemplate onRequestTemplateContents(Context context, String str) {
        Log.e(
                "RoutineActionHandler",
                "onRequestTemplateContents: this should not be called without overriding!!!");
        return new UiTemplate(new Bundle());
    }
}
