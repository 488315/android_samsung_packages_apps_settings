package com.samsung.android.sdk.routines.v3.interfaces;

import android.content.Context;
import android.util.Log;

import com.samsung.android.sdk.routines.v3.data.ActionValidity$Default;
import com.samsung.android.sdk.routines.v3.data.ErrorContents;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.template.UiTemplate;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface RoutineActionHandler {
    default void checkValidity(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        responseCallback.setResponse(new ActionValidity$Default());
    }

    void getCurrentParameterValues(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback);

    void getParameterLabel(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback);

    default void getPreviewImageFileDescriptor(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ResponseCallback responseCallback) {
        Log.e(
                "RoutineActionHandler",
                "getPreviewImageFileDescriptor: this should not be called without overriding!!!");
        responseCallback.setResponse(null);
    }

    SupportStatus isSupported(Context context, String str);

    default String onMigrate(Context context, List list) {
        Log.e("RoutineActionHandler", "onMigrate: this should not be called without overriding!!!");
        return null;
    }

    void onPerformAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionResultCallback actionResultCallback);

    void onPerformReverseAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionResultCallback actionResultCallback);

    ErrorContents onRequestErrorDialogContents(Context context, String str, int i, long j);

    UiTemplate onRequestTemplateContents(Context context, String str);
}
