package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.content.Context;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollInteractorImpl implements FingerprintEnrollInteractor {
    public FingerprintEnrollInteractorImpl(Context applicationContext) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        StateFlowKt.MutableStateFlow(Boolean.FALSE);
    }
}
