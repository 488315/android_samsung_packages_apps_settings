package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.view.accessibility.AccessibilityManager;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AccessibilityInteractorImpl {
    public final ReadonlyStateFlow isAccessibilityEnabled;

    public AccessibilityInteractorImpl(
            AccessibilityManager accessibilityManager, ContextScope applicationScope) {
        Intrinsics.checkNotNullParameter(applicationScope, "applicationScope");
        this.isAccessibilityEnabled =
                FlowKt.stateIn(
                        FlowKt.callbackFlow(
                                new AccessibilityInteractorImpl$isAccessibilityEnabled$1(
                                        accessibilityManager, null)),
                        applicationScope,
                        SharingStarted.Companion.WhileSubscribed$default(),
                        Boolean.FALSE);
    }
}
