package com.android.settings.biometrics.fingerprint2.debug.domain.interactor;

import com.android.settings.biometrics.fingerprint2.data.repository.SimulatedTouchEventsRepository;
import com.android.settings.biometrics.fingerprint2.debug.data.repository.UdfpsEnrollDebugRepositoryImpl;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DebugTouchEventInteractorImpl {
    public final SafeFlow touchEvent;

    public DebugTouchEventInteractorImpl(
            SimulatedTouchEventsRepository udfpsSimulatedTouchEventsRepository) {
        Intrinsics.checkNotNullParameter(
                udfpsSimulatedTouchEventsRepository, "udfpsSimulatedTouchEventsRepository");
        SafeFlow safeFlow =
                ((UdfpsEnrollDebugRepositoryImpl) udfpsSimulatedTouchEventsRepository)
                        .touchExplorationDebug;
    }
}
