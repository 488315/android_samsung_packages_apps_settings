package com.samsung.android.settings.bixbyroutinehandler.interactor;

import android.content.Context;

import com.samsung.android.sdk.routines.v3.data.ErrorContents;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda6;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda8;
import com.samsung.android.sdk.routines.v3.template.UiTemplate;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface ActionInteractor {
    void getCurrentParameterValues(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionDispatcher$$ExternalSyntheticLambda6 actionDispatcher$$ExternalSyntheticLambda6);

    void getParameterLabel(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionDispatcher$$ExternalSyntheticLambda6 actionDispatcher$$ExternalSyntheticLambda6);

    List getSupportTagList();

    SupportStatus isSupported(Context context, String str);

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
            ActionDispatcher$$ExternalSyntheticLambda8 actionDispatcher$$ExternalSyntheticLambda8);

    ErrorContents onRequestErrorDialogContents(Context context, String str, int i, long j);

    UiTemplate onRequestTemplateContents(Context context, String str);
}
