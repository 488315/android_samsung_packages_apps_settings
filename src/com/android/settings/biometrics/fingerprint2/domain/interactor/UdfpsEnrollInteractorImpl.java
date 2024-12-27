package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.content.Context;
import android.graphics.PointF;
import android.util.TypedValue;

import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UdfpsEnrollInteractorImpl {
    public final StateFlowImpl _guidedEnrollment;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1
            guidedEnrollmentOffset;
    public final StateFlowImpl isGuidedEnrollment;

    public UdfpsEnrollInteractorImpl(
            Context applicationContext, AccessibilityInteractorImpl accessibilityInteractor) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        Intrinsics.checkNotNullParameter(accessibilityInteractor, "accessibilityInteractor");
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        float applyDimension =
                TypedValue.applyDimension(
                        5, 1.0f, applicationContext.getResources().getDisplayMetrics());
        float f = (-1.8f) * applyDimension;
        float f2 = (-3.33f) * applyDimension;
        CollectionsKt__CollectionsKt.mutableListOf(
                new PointF(2.0f * applyDimension, applyDimension * 0.0f),
                new PointF(0.87f * applyDimension, (-2.7f) * applyDimension),
                new PointF(f, (-1.31f) * applyDimension),
                new PointF(f, 1.31f * applyDimension),
                new PointF(0.88f * applyDimension, 2.7f * applyDimension),
                new PointF(3.94f * applyDimension, (-1.06f) * applyDimension),
                new PointF(2.9f * applyDimension, (-4.14f) * applyDimension),
                new PointF((-0.52f) * applyDimension, (-5.95f) * applyDimension),
                new PointF(f2, f2),
                new PointF((-3.99f) * applyDimension, (-0.35f) * applyDimension),
                new PointF((-3.62f) * applyDimension, 2.54f * applyDimension),
                new PointF((-1.49f) * applyDimension, 5.57f * applyDimension),
                new PointF(2.29f * applyDimension, 4.92f * applyDimension),
                new PointF(3.82f * applyDimension, applyDimension * 1.78f));
        FlowKt.combine(
                StateFlowKt.MutableStateFlow(new PointF(0.0f, 0.0f)),
                accessibilityInteractor.isAccessibilityEnabled,
                MutableStateFlow,
                new UdfpsEnrollInteractorImpl$guidedEnrollmentOffset$1(4, null));
    }
}
