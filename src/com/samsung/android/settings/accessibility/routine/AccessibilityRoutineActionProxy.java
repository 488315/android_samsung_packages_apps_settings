package com.samsung.android.settings.accessibility.routine;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

import com.samsung.android.sdk.routines.v3.data.ErrorContents;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback;
import com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback;
import com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda6;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda8;
import com.samsung.android.sdk.routines.v3.template.UiTemplate;
import com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AccessibilityRoutineActionProxy implements ActionInteractor {
    public static final Set HANDLERS =
            (Set)
                    Set.of(
                                    "com.samsung.android.settings.accessibility.hearing.routine.MuteAllSoundsRoutineActionHandler",
                                    "com.samsung.android.settings.accessibility.hearing.routine.LiveCaptionRoutineActionHandler",
                                    "com.samsung.android.settings.accessibility.hearing.routine.MonoAudioRoutineActionHandler",
                                    "com.samsung.android.settings.accessibility.hearing.routine.SoundBalanceRoutineActionHandler",
                                    "com.samsung.android.settings.accessibility.advanced.routine.CameraFlashRoutineActionHandler",
                                    "com.samsung.android.settings.accessibility.advanced.routine.ScreenFlashRoutineActionHandler",
                                    "com.samsung.android.settings.accessibility.vision.routine.ReduceBrightnessRoutineActionHandler",
                                    "com.samsung.android.settings.accessibility.vision.routine.ColorInversionRoutineActionHandler",
                                    "com.samsung.android.settings.accessibility.vision.routine.ColorCorrectionRoutineActionHandler",
                                    "com.samsung.android.settings.accessibility.vision.routine.ColorFilterRoutineActionHandler")
                            .stream()
                            .map(new AccessibilityRoutineActionProxy$$ExternalSyntheticLambda0(1))
                            .filter(new AccessibilityRoutineActionProxy$$ExternalSyntheticLambda3())
                            .collect(Collectors.toSet());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class DummyHandler extends AccessibilityRoutineActionHandler {
        private static final String TAG = "DummyAction";

        public DummyHandler(String str) {
            MotionLayout$$ExternalSyntheticOutline0.m(
                    "AccessibilityRoutineActionProxy.getHandler()returns DummyHandler for tag : ",
                    str,
                    TAG);
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
            return TAG;
        }

        @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
        public void getCurrentParameterValues(
                Context context,
                String str,
                ParameterValues parameterValues,
                long j,
                ResponseCallback responseCallback) {
            Log.w(TAG, "getCurrentParameterValues called.");
        }

        @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
        public void getParameterLabel(
                Context context,
                String str,
                ParameterValues parameterValues,
                long j,
                ResponseCallback responseCallback) {
            Log.w(TAG, "getParameterLabel called.");
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
            Log.w(TAG, "isSupported called.");
            return SupportStatus.NOT_SUPPORTED;
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
            Log.w(TAG, "onPerformAction called.");
        }

        @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
        public void onPerformReverseAction(
                Context context,
                String str,
                ParameterValues parameterValues,
                long j,
                ActionResultCallback actionResultCallback) {
            Log.w(TAG, "onPerformReverseAction called.");
        }

        @Override // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionHandler, com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
        public ErrorContents onRequestErrorDialogContents(
                Context context, String str, int i, long j) {
            Log.w(TAG, "onRequestErrorDialogContents called.");
            return new ErrorContents(null, "wrong", null);
        }

        @Override // com.samsung.android.sdk.routines.v3.interfaces.RoutineActionHandler
        public UiTemplate onRequestTemplateContents(Context context, String str) {
            Log.w(TAG, "onRequestTemplateContents called.");
            return new UiTemplate(new Bundle());
        }
    }

    public static RoutineActionHandler getHandler(final String str) {
        return (RoutineActionHandler)
                HANDLERS.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionProxy$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((AccessibilityRoutineActionHandler) obj)
                                                .getActionTag()
                                                .equals(str);
                                    }
                                })
                        .findFirst()
                        .orElse(new DummyHandler(str));
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final void getCurrentParameterValues(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionDispatcher$$ExternalSyntheticLambda6 actionDispatcher$$ExternalSyntheticLambda6) {
        getHandler(str)
                .getCurrentParameterValues(
                        context,
                        str,
                        parameterValues,
                        j,
                        actionDispatcher$$ExternalSyntheticLambda6);
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final void getParameterLabel(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionDispatcher$$ExternalSyntheticLambda6 actionDispatcher$$ExternalSyntheticLambda6) {
        getHandler(str)
                .getParameterLabel(
                        context,
                        str,
                        parameterValues,
                        j,
                        actionDispatcher$$ExternalSyntheticLambda6);
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final List getSupportTagList() {
        return HANDLERS.stream()
                .map(new AccessibilityRoutineActionProxy$$ExternalSyntheticLambda0(0))
                .toList();
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final SupportStatus isSupported(Context context, String str) {
        return getHandler(str).isSupported(context, str);
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final void onPerformAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionResultCallback actionResultCallback) {
        getHandler(str).onPerformAction(context, str, parameterValues, j, actionResultCallback);
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final void onPerformReverseAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionDispatcher$$ExternalSyntheticLambda8 actionDispatcher$$ExternalSyntheticLambda8) {
        getHandler(str).onPerformReverseAction(context, str, parameterValues, j, null);
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final ErrorContents onRequestErrorDialogContents(
            Context context, String str, int i, long j) {
        return getHandler(str).onRequestErrorDialogContents(context, str, i, j);
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final UiTemplate onRequestTemplateContents(Context context, String str) {
        return getHandler(str).onRequestTemplateContents(context, str);
    }
}
