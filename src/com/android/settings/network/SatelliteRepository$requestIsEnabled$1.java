package com.android.settings.network;

import android.os.OutcomeReceiver;
import android.telephony.satellite.SatelliteManager;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.CallbackToFutureAdapter;

import kotlin.jvm.internal.Intrinsics;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SatelliteRepository$requestIsEnabled$1
        implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ Executor $executor;
    public final /* synthetic */ SatelliteManager $satelliteManager;

    public SatelliteRepository$requestIsEnabled$1(
            SatelliteManager satelliteManager, Executor executor) {
        this.$satelliteManager = satelliteManager;
        this.$executor = executor;
    }

    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
    public final Object attachCompleter(final CallbackToFutureAdapter.Completer completer) {
        this.$satelliteManager.requestIsEnabled(
                this.$executor,
                new OutcomeReceiver() { // from class:
                                        // com.android.settings.network.SatelliteRepository$requestIsEnabled$1.1
                    @Override // android.os.OutcomeReceiver
                    public final void onError(Throwable th) {
                        SatelliteManager.SatelliteException error =
                                (SatelliteManager.SatelliteException) th;
                        Intrinsics.checkNotNullParameter(error, "error");
                        super.onError(error);
                        Log.w(
                                "SatelliteRepository",
                                "Can't get satellite modem enabled status",
                                error);
                        CallbackToFutureAdapter.Completer.this.set(Boolean.FALSE);
                    }

                    @Override // android.os.OutcomeReceiver
                    public final void onResult(Object obj) {
                        Boolean bool = (Boolean) obj;
                        AbsAdapter$$ExternalSyntheticOutline0.m(
                                "Satellite modem enabled status: ",
                                "SatelliteRepository",
                                bool.booleanValue());
                        CallbackToFutureAdapter.Completer.this.set(bool);
                    }
                });
        return "requestIsEnabled";
    }
}
