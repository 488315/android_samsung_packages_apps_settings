package com.samsung.android.settings.accessibility.routine;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.sdk.routines.v3.data.ActionResult$Error;
import com.samsung.android.sdk.routines.v3.data.ActionResult$ResultCode;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback;
import com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda8;
import com.samsung.android.sdk.routines.v3.template.ToggleTemplate;
import com.samsung.android.sdk.routines.v3.template.UiTemplate;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityRoutineSimpleOnOffActionHandler
        extends AccessibilityRoutineActionHandler {
    private String getLogTag() {
        return getClass().getSimpleName();
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

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public final void getCurrentParameterValues(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        ParameterValues parameterValues2 = new ParameterValues();
        parameterValues2.put("toggle_value", Boolean.valueOf(isActionEnabled(context)));
        responseCallback.setResponse(parameterValues2);
        Log.i(getLogTag(), "getCurrentParameterValues - calling setResponse complete");
    }

    public String getExclusiveTaskName() {
        return null;
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public final void getParameterLabel(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        responseCallback.setResponse(
                context.getString(
                        parameterValues.getBoolean("toggle_value", Boolean.TRUE).booleanValue()
                                ? R.string.routine_switch_on_text
                                : R.string.routine_switch_off_text));
        Log.i(getLogTag(), "getParameterLabel - calling setResponse complete");
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

    public abstract String getTitle(Context context);

    public abstract boolean isActionEnabled(Context context);

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public /* bridge */ /* synthetic */ SupportStatus isSupported(Context context, String str) {
        return SupportStatus.SUPPORTED;
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
        boolean booleanValue =
                parameterValues.getBoolean("toggle_value", Boolean.TRUE).booleanValue();
        if (AccessibilityExclusiveUtil.isExclusiveTaskEnabled(context, getExclusiveTaskName())
                && booleanValue) {
            ((ActionDispatcher$$ExternalSyntheticLambda8) actionResultCallback)
                    .actionFinished(new ActionResult$Error(100));
        } else {
            setActionEnable(context, booleanValue);
            ((ActionDispatcher$$ExternalSyntheticLambda8) actionResultCallback)
                    .actionFinished(new ActionResult$Error(ActionResult$ResultCode.SUCCESS));
        }
        Log.i(getLogTag(), "onPerformAction - calling actionFinished complete");
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public final void onPerformReverseAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionResultCallback actionResultCallback) {
        setActionEnable(
                context, parameterValues.getBoolean("toggle_value", Boolean.FALSE).booleanValue());
        Log.i(getLogTag(), "onPerformReverseAction - calling setActionEnable complete");
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
    public UiTemplate onRequestTemplateContents(Context context, String str) {
        Log.i(getLogTag(), "onRequestTemplateContents - returning new UiTemplate complete");
        Bundle bundle = new Bundle();
        bundle.putString(UniversalCredentialUtil.AGENT_TITLE, getTitle(context));
        bundle.putString("label_on", context.getString(R.string.routine_switch_on_text));
        bundle.putString("label_off", context.getString(R.string.routine_switch_off_text));
        bundle.putBoolean("default_selection", true);
        return new ToggleTemplate(bundle);
    }

    public abstract void setActionEnable(Context context, boolean z);
}
