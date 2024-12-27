package com.android.settings.sim.receivers;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/sim/receivers/SimSlotChangeService;",
            "Landroid/app/job/JobService;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class SimSlotChangeService extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public StandaloneCoroutine job;

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters params) {
        Intrinsics.checkNotNullParameter(params, "params");
        Log.i("SimSlotChangeService", "on Start Job called");
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        SupervisorJobImpl SupervisorJob$default = SupervisorKt.SupervisorJob$default();
        defaultScheduler.getClass();
        this.job =
                BuildersKt.launch$default(
                        CoroutineScopeKt.CoroutineScope(
                                CoroutineContext.DefaultImpls.plus(
                                        (CoroutineContext) defaultScheduler,
                                        (CoroutineContext) SupervisorJob$default)),
                        null,
                        null,
                        new SimSlotChangeService$onStartJob$1(this, params, null),
                        3);
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters params) {
        Intrinsics.checkNotNullParameter(params, "params");
        StandaloneCoroutine standaloneCoroutine = this.job;
        if (standaloneCoroutine == null) {
            return false;
        }
        standaloneCoroutine.cancel(null);
        return false;
    }
}
