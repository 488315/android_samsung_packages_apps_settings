package com.android.settings.biometrics.fingerprint2.debug.data.repository;

import android.graphics.Rect;

import com.android.settings.biometrics.fingerprint2.data.repository.SimulatedTouchEventsRepository;
import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintEnrollInteractor;
import com.android.systemui.biometrics.shared.model.FingerprintSensor;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import com.android.systemui.biometrics.shared.model.SensorStrength;

import kotlin.Pair;

import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UdfpsEnrollDebugRepositoryImpl
        implements FingerprintEnrollInteractor, SimulatedTouchEventsRepository {
    public static final FingerprintSensor sensorProps;
    public static final Rect sensorRect;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 fingerprintSensor;
    public final SafeFlow touchExplorationDebug =
            new SafeFlow(new UdfpsEnrollDebugRepositoryImpl$touchExplorationDebug$1(this, null));

    static {
        Pair pair = new Pair(540, 1713);
        Rect rect =
                new Rect(
                        ((Number) pair.getFirst()).intValue() - 100,
                        ((Number) pair.getSecond()).intValue() - 100,
                        ((Number) pair.getFirst()).intValue() + 100,
                        ((Number) pair.getSecond()).intValue() + 100);
        sensorRect = rect;
        sensorProps =
                new FingerprintSensor(
                        1,
                        SensorStrength.STRONG,
                        5,
                        FingerprintSensorType.UDFPS_OPTICAL,
                        rect,
                        100);
    }

    public UdfpsEnrollDebugRepositoryImpl() {
        new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(sensorProps);
    }
}
