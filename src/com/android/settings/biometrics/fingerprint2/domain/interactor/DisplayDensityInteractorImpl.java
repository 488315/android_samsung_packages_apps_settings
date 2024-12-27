package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.content.Context;

import com.android.settingslib.display.DisplayDensityUtils;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DisplayDensityInteractorImpl {
    public final StateFlowImpl _displayDensity;
    public final StateFlowImpl _fontScale;
    public final ReadonlySharedFlow defaultDisplayDensity;
    public final ReadonlyStateFlow displayDensity;
    public final ReadonlyStateFlow fontScale;

    public DisplayDensityInteractorImpl(Context context, ContextScope scope) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scope, "scope");
        DisplayDensityUtils displayDensityUtils = new DisplayDensityUtils(context);
        StateFlowImpl MutableStateFlow =
                StateFlowKt.MutableStateFlow(
                        Float.valueOf(context.getResources().getConfiguration().fontScale));
        StateFlowImpl MutableStateFlow2 =
                StateFlowKt.MutableStateFlow(
                        Integer.valueOf(
                                displayDensityUtils
                                        .mDefaultDisplayDensityValues[
                                        displayDensityUtils.mCurrentIndex]));
        FlowKt.asStateFlow(MutableStateFlow);
        FlowKt.asStateFlow(MutableStateFlow2);
        FlowKt.shareIn(
                new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(
                        Integer.valueOf(displayDensityUtils.mDefaultDensityForDefaultDisplay)),
                scope,
                SharingStarted.Companion.Eagerly,
                1);
    }
}
