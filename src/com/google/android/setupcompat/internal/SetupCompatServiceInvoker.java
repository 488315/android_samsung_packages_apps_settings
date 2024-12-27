package com.google.android.setupcompat.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;

import com.google.android.setupcompat.ISetupCompatService;
import com.google.android.setupcompat.util.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SetupCompatServiceInvoker {
    public static final Logger LOG = new Logger("SetupCompatServiceInvoker");
    public static final long MAX_WAIT_TIME_FOR_CONNECTION_MS = TimeUnit.SECONDS.toMillis(10);
    public static SetupCompatServiceInvoker instance;
    public final Context context;
    public final ExecutorService loggingExecutor;
    public final long waitTimeInMillisForServiceConnection;

    public SetupCompatServiceInvoker(Context context) {
        this.context = context;
        ExecutorProvider executorProvider = ExecutorProvider.setupCompatServiceInvoker;
        Executor executor = executorProvider.injectedExecutor;
        this.loggingExecutor =
                (ExecutorService) (executor == null ? executorProvider.executor : executor);
        this.waitTimeInMillisForServiceConnection = MAX_WAIT_TIME_FOR_CONNECTION_MS;
    }

    public static synchronized SetupCompatServiceInvoker get(Context context) {
        SetupCompatServiceInvoker setupCompatServiceInvoker;
        synchronized (SetupCompatServiceInvoker.class) {
            try {
                if (instance == null) {
                    instance = new SetupCompatServiceInvoker(context.getApplicationContext());
                }
                setupCompatServiceInvoker = instance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return setupCompatServiceInvoker;
    }

    public static void setInstanceForTesting(SetupCompatServiceInvoker setupCompatServiceInvoker) {
        instance = setupCompatServiceInvoker;
    }

    public final void logMetricEvent(final int i, final Bundle bundle) {
        try {
            this.loggingExecutor.execute(
                    new Runnable() { // from class:
                        // com.google.android.setupcompat.internal.SetupCompatServiceInvoker$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SetupCompatServiceInvoker setupCompatServiceInvoker =
                                    SetupCompatServiceInvoker.this;
                            int i2 = i;
                            Bundle bundle2 = bundle;
                            setupCompatServiceInvoker.getClass();
                            Logger logger = SetupCompatServiceInvoker.LOG;
                            try {
                                ISetupCompatService service =
                                        SetupCompatServiceProvider.getInstance(
                                                        setupCompatServiceInvoker.context)
                                                .getService(
                                                        setupCompatServiceInvoker
                                                                .waitTimeInMillisForServiceConnection,
                                                        TimeUnit.MILLISECONDS);
                                if (service != null) {
                                    ((ISetupCompatService.Stub.Proxy) service)
                                            .logMetric(i2, bundle2, Bundle.EMPTY);
                                } else {
                                    logger.w(
                                            "logMetric failed since service reference is null. Are"
                                                + " the permissions valid?");
                                }
                            } catch (RemoteException
                                    | IllegalStateException
                                    | InterruptedException
                                    | TimeoutException e) {
                                logger.e(
                                        "Exception occurred while trying to log metric = ["
                                                + bundle2
                                                + "]",
                                        e);
                            }
                        }
                    });
        } catch (RejectedExecutionException e) {
            LOG.e(
                    String.format(
                            "Metric of type %d dropped since queue is full.", Integer.valueOf(i)),
                    e);
        }
    }
}
