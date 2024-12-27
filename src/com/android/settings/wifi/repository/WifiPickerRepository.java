package com.android.settings.wifi.repository;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.WifiTrackerLibProviderImpl;
import com.android.wifitrackerlib.WifiPickerTracker;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

import java.time.Clock;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiPickerRepository {
    public final Function2 createWifiPickerTracker;

    public WifiPickerRepository(final Context context) {
        Function2 function2 =
                new Function2() { // from class:
                                  // com.android.settings.wifi.repository.WifiPickerRepository.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        HandlerThread workerThread = (HandlerThread) obj;
                        WifiPickerTracker.WifiPickerTrackerCallback callback =
                                (WifiPickerTracker.WifiPickerTrackerCallback) obj2;
                        Intrinsics.checkNotNullParameter(workerThread, "workerThread");
                        Intrinsics.checkNotNullParameter(callback, "callback");
                        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                        if (featureFactoryImpl == null) {
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                        WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                                featureFactoryImpl.getWifiTrackerLibProvider();
                        Context context2 = context;
                        Handler handler = new Handler(Looper.getMainLooper());
                        Handler threadHandler = workerThread.getThreadHandler();
                        Clock elapsedRealtimeClock = SystemClock.elapsedRealtimeClock();
                        wifiTrackerLibProvider.getClass();
                        return WifiTrackerLibProviderImpl.createWifiPickerTracker(
                                null,
                                context2,
                                handler,
                                threadHandler,
                                elapsedRealtimeClock,
                                callback);
                    }
                };
        Intrinsics.checkNotNullParameter(context, "context");
        this.createWifiPickerTracker = function2;
    }

    public final Flow connectedWifiEntryFlow() {
        return FlowKt.flowOn(
                FlowKt.onEach(
                        FlowKt.buffer$default(
                                FlowKt.callbackFlow(
                                        new WifiPickerRepository$connectedWifiEntryFlow$1(
                                                this, null)),
                                -1),
                        new WifiPickerRepository$connectedWifiEntryFlow$2(2, null)),
                Dispatchers.Default);
    }
}
