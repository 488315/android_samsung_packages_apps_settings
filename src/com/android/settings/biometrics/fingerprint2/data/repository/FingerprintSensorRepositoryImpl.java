package com.android.settings.biometrics.fingerprint2.data.repository;

import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;

import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintSensorRepositoryImpl {
    public static final FingerprintSensorPropertiesInternal DEFAULT_PROPS;
    public final ReadonlyStateFlow fingerprintPropsInternal;
    public final SafeFlow fingerprintSensor;

    static {
        EmptyList emptyList = EmptyList.INSTANCE;
        SensorLocationInternal DEFAULT = SensorLocationInternal.DEFAULT;
        Intrinsics.checkNotNullExpressionValue(DEFAULT, "DEFAULT");
        DEFAULT_PROPS =
                new FingerprintSensorPropertiesInternal(
                        -1,
                        0,
                        0,
                        emptyList,
                        0,
                        false,
                        true,
                        CollectionsKt__CollectionsJVMKt.listOf(DEFAULT));
    }

    public FingerprintSensorRepositoryImpl(
            FingerprintManager fingerprintManager,
            CoroutineDispatcher backgroundDispatcher,
            CoroutineScope coroutineScope) {
        Intrinsics.checkNotNullParameter(backgroundDispatcher, "backgroundDispatcher");
        this.fingerprintSensor =
                new SafeFlow(
                        new FingerprintSensorRepositoryImpl$special$$inlined$transform$1(
                                FlowKt.stateIn(
                                        FlowKt.callbackFlow(
                                                new FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1(
                                                        backgroundDispatcher,
                                                        fingerprintManager,
                                                        null)),
                                        coroutineScope,
                                        SharingStarted.Companion.Eagerly,
                                        DEFAULT_PROPS),
                                null));
    }
}
