package com.android.settings.biometrics.fingerprint2.domain.interactor;

import com.android.settings.biometrics.fingerprint2.data.repository.DebuggingRepositoryImpl;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DebuggingInteractorImpl {
    public final DebuggingRepositoryImpl debuggingRepository;

    public DebuggingInteractorImpl(DebuggingRepositoryImpl debuggingRepository) {
        Intrinsics.checkNotNullParameter(debuggingRepository, "debuggingRepository");
        this.debuggingRepository = debuggingRepository;
        new DebuggingInteractorImpl$debuggingEnabled$1(this, null);
        new DebuggingInteractorImpl$udfpsEnrollmentDebuggingEnabled$1(this, null);
    }
}
