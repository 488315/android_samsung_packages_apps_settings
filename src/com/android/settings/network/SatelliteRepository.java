package com.android.settings.network;

import android.content.Context;
import android.telephony.satellite.SatelliteManager;
import android.telephony.satellite.SatelliteModemStateCallback;
import android.util.Log;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.CallbackToFutureAdapter;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.scheduling.DefaultScheduler;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SatelliteRepository {
    public static final Boolean isSessionStarted = null;
    public final Context context;

    public SatelliteRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public static Flow getIsSessionStartedFlow$default(SatelliteRepository satelliteRepository) {
        DefaultScheduler defaultDispatcher = Dispatchers.Default;
        satelliteRepository.getClass();
        Intrinsics.checkNotNullParameter(defaultDispatcher, "defaultDispatcher");
        SatelliteManager satelliteManager =
                (SatelliteManager)
                        satelliteRepository.context.getSystemService(SatelliteManager.class);
        if (satelliteManager != null) {
            return FlowKt.flowOn(
                    FlowKt.callbackFlow(
                            new SatelliteRepository$getIsSessionStartedFlow$1(
                                    satelliteManager,
                                    defaultDispatcher,
                                    satelliteRepository,
                                    null)),
                    defaultDispatcher);
        }
        Log.w("SatelliteRepository", "SatelliteManager is null");
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
    }

    public final ListenableFuture requestIsSessionStarted(final Executor executor) {
        Intrinsics.checkNotNullParameter(executor, "executor");
        Boolean bool = isSessionStarted;
        if (bool != null) {
            return Futures.immediateFuture(bool);
        }
        final SatelliteManager satelliteManager =
                (SatelliteManager) this.context.getSystemService(SatelliteManager.class);
        if (satelliteManager != null) {
            return CallbackToFutureAdapter.getFuture(
                    new CallbackToFutureAdapter
                            .Resolver() { // from class:
                                          // com.android.settings.network.SatelliteRepository$requestIsSessionStarted$2
                        @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                        public final Object attachCompleter(
                                final CallbackToFutureAdapter.Completer completer) {
                            final SatelliteManager satelliteManager2 = satelliteManager;
                            final SatelliteRepository satelliteRepository = this;
                            int registerForModemStateChanged =
                                    satelliteManager2.registerForModemStateChanged(
                                            executor,
                                            new SatelliteModemStateCallback() { // from class:
                                                                                // com.android.settings.network.SatelliteRepository$requestIsSessionStarted$2$callback$1
                                                public final void onSatelliteModemStateChanged(
                                                        int i) {
                                                    SatelliteRepository.this.getClass();
                                                    boolean z =
                                                            (i == -1 || i == 4 || i == 5)
                                                                    ? false
                                                                    : true;
                                                    Log.i(
                                                            "SatelliteRepository",
                                                            "Satellite modem state changed: state="
                                                                    + i
                                                                    + ", isSessionStarted="
                                                                    + z);
                                                    completer.set(Boolean.valueOf(z));
                                                    satelliteManager2
                                                            .unregisterForModemStateChanged(this);
                                                }
                                            });
                            if (registerForModemStateChanged == 0) {
                                return "requestIsSessionStarted";
                            }
                            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                                    registerForModemStateChanged,
                                    "Failed to register for satellite modem state change: ",
                                    "SatelliteRepository");
                            completer.set(Boolean.FALSE);
                            return "requestIsSessionStarted";
                        }
                    });
        }
        Log.w("SatelliteRepository", "SatelliteManager is null");
        return Futures.immediateFuture(Boolean.FALSE);
    }
}
