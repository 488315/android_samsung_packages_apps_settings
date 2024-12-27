package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.content.Context;

import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FoldStateInteractorImpl {
    public final CallbackFlowBuilder isFolded;
    public final ScreenSizeFoldProvider screenSizeFoldProvider;

    public FoldStateInteractorImpl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.screenSizeFoldProvider = new ScreenSizeFoldProvider(context);
        FlowKt.callbackFlow(new FoldStateInteractorImpl$isFolded$1(this, context, null));
    }
}
