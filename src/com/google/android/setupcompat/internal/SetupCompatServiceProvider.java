package com.google.android.setupcompat.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;

import com.google.android.setupcompat.ISetupCompatService;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SetupCompatServiceProvider {
    public static volatile SetupCompatServiceProvider instance;
    public final Context context;
    public static final Logger LOG = new Logger("SetupCompatServiceProvider");
    static final Intent COMPAT_SERVICE_INTENT =
            new Intent()
                    .setPackage(PartnerConfigHelper.SUW_PACKAGE_NAME)
                    .setAction("com.google.android.setupcompat.SetupCompatService.BIND");
    static boolean disableLooperCheckForTesting = false;
    final ServiceConnection serviceConnection =
            new ServiceConnection() { // from class:
                                      // com.google.android.setupcompat.internal.SetupCompatServiceProvider.1
                @Override // android.content.ServiceConnection
                public final void onBindingDied(ComponentName componentName) {
                    SetupCompatServiceProvider.this.swapServiceContextAndNotify(
                            new ServiceContext(State.REBIND_REQUIRED));
                }

                @Override // android.content.ServiceConnection
                public final void onNullBinding(ComponentName componentName) {
                    SetupCompatServiceProvider.this.swapServiceContextAndNotify(
                            new ServiceContext(State.SERVICE_NOT_USABLE));
                }

                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    ISetupCompatService iSetupCompatService;
                    State state = State.CONNECTED;
                    if (iBinder == null) {
                        state = State.DISCONNECTED;
                        SetupCompatServiceProvider.LOG.w(
                                "Binder is null when onServiceConnected was called!");
                    }
                    SetupCompatServiceProvider setupCompatServiceProvider =
                            SetupCompatServiceProvider.this;
                    int i = ISetupCompatService.Stub.$r8$clinit;
                    if (iBinder == null) {
                        iSetupCompatService = null;
                    } else {
                        IInterface queryLocalInterface =
                                iBinder.queryLocalInterface(
                                        "com.google.android.setupcompat.ISetupCompatService");
                        if (queryLocalInterface == null
                                || !(queryLocalInterface instanceof ISetupCompatService)) {
                            ISetupCompatService.Stub.Proxy proxy =
                                    new ISetupCompatService.Stub.Proxy();
                            proxy.mRemote = iBinder;
                            iSetupCompatService = proxy;
                        } else {
                            iSetupCompatService = (ISetupCompatService) queryLocalInterface;
                        }
                    }
                    setupCompatServiceProvider.swapServiceContextAndNotify(
                            new ServiceContext(state, iSetupCompatService));
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    SetupCompatServiceProvider.this.swapServiceContextAndNotify(
                            new ServiceContext(State.DISCONNECTED));
                }
            };
    public volatile ServiceContext serviceContext = new ServiceContext(State.NOT_STARTED);
    public final AtomicReference connectedConditionRef = new AtomicReference();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class State {
        public static final /* synthetic */ State[] $VALUES;
        public static final State BINDING;
        public static final State BIND_FAILED;
        public static final State CONNECTED;
        public static final State DISCONNECTED;
        public static final State NOT_STARTED;
        public static final State REBIND_REQUIRED;
        public static final State SERVICE_NOT_USABLE;

        static {
            State state = new State("NOT_STARTED", 0);
            NOT_STARTED = state;
            State state2 = new State("BIND_FAILED", 1);
            BIND_FAILED = state2;
            State state3 = new State("BINDING", 2);
            BINDING = state3;
            State state4 = new State("CONNECTED", 3);
            CONNECTED = state4;
            State state5 = new State("DISCONNECTED", 4);
            DISCONNECTED = state5;
            State state6 = new State("SERVICE_NOT_USABLE", 5);
            SERVICE_NOT_USABLE = state6;
            State state7 = new State("REBIND_REQUIRED", 6);
            REBIND_REQUIRED = state7;
            $VALUES = new State[] {state, state2, state3, state4, state5, state6, state7};
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }
    }

    public SetupCompatServiceProvider(Context context) {
        this.context = context.getApplicationContext();
    }

    public static SetupCompatServiceProvider getInstance(Context context) {
        Preconditions.checkNotNull(context, "Context object cannot be null.");
        SetupCompatServiceProvider setupCompatServiceProvider = instance;
        if (setupCompatServiceProvider == null) {
            synchronized (SetupCompatServiceProvider.class) {
                try {
                    setupCompatServiceProvider = instance;
                    if (setupCompatServiceProvider == null) {
                        setupCompatServiceProvider =
                                new SetupCompatServiceProvider(context.getApplicationContext());
                        instance = setupCompatServiceProvider;
                        instance.requestServiceBind();
                    }
                } finally {
                }
            }
        }
        return setupCompatServiceProvider;
    }

    public static void setInstanceForTesting(
            SetupCompatServiceProvider setupCompatServiceProvider) {
        instance = setupCompatServiceProvider;
    }

    public CountDownLatch createCountDownLatch() {
        return new CountDownLatch(1);
    }

    public State getCurrentState() {
        return this.serviceContext.state;
    }

    public ISetupCompatService getService(long j, TimeUnit timeUnit)
            throws TimeoutException, InterruptedException {
        ServiceContext serviceContext;
        if (!disableLooperCheckForTesting && Looper.getMainLooper() == Looper.myLooper()) {
            throw new IllegalStateException(
                    "getService blocks and should not be called from the main thread.");
        }
        synchronized (this) {
            serviceContext = this.serviceContext;
        }
        switch (serviceContext.state.ordinal()) {
            case 0:
                LOG.w("NOT_STARTED state only possible before instance is created.");
                return null;
            case 1:
            case 5:
                return null;
            case 2:
            case 4:
                return waitForConnection(j, timeUnit);
            case 3:
                return serviceContext.compatService;
            case 6:
                requestServiceBind();
                return waitForConnection(j, timeUnit);
            default:
                throw new IllegalStateException("Unknown state = " + serviceContext.state);
        }
    }

    public final synchronized void requestServiceBind() {
        boolean z;
        synchronized (this) {
        }
        State state = this.serviceContext.state;
        if (state == State.CONNECTED) {
            LOG.atInfo("Refusing to rebind since current state is already connected");
            return;
        }
        if (state != State.NOT_STARTED) {
            LOG.atInfo("Unbinding existing service connection.");
            this.context.unbindService(this.serviceConnection);
        }
        try {
            z = this.context.bindService(COMPAT_SERVICE_INTENT, this.serviceConnection, 1);
        } catch (SecurityException e) {
            LOG.e("Unable to bind to compat service. " + e);
            z = false;
        }
        if (!z) {
            swapServiceContextAndNotify(new ServiceContext(State.BIND_FAILED));
            LOG.e("Context#bindService did not succeed.");
        } else if (getCurrentState() != State.CONNECTED) {
            swapServiceContextAndNotify(new ServiceContext(State.BINDING));
            LOG.atInfo("Context#bindService went through, now waiting for service connection");
        }
    }

    public void swapServiceContextAndNotify(ServiceContext serviceContext) {
        LOG.atInfo("State changed: " + this.serviceContext.state + " -> " + serviceContext.state);
        this.serviceContext = serviceContext;
        CountDownLatch countDownLatch = (CountDownLatch) this.connectedConditionRef.getAndSet(null);
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public final ISetupCompatService waitForConnection(long j, TimeUnit timeUnit) {
        ServiceContext serviceContext;
        CountDownLatch countDownLatch;
        ServiceContext serviceContext2;
        synchronized (this) {
            serviceContext = this.serviceContext;
        }
        if (serviceContext.state == State.CONNECTED) {
            return serviceContext.compatService;
        }
        do {
            countDownLatch = (CountDownLatch) this.connectedConditionRef.get();
            if (countDownLatch != null) {
                break;
            }
            countDownLatch = createCountDownLatch();
        } while (!this.connectedConditionRef.compareAndSet(null, countDownLatch));
        Logger logger = LOG;
        logger.atInfo("Waiting for service to get connected");
        if (countDownLatch.await(j, timeUnit)) {
            synchronized (this) {
                serviceContext2 = this.serviceContext;
            }
            logger.atInfo(
                    "Finished waiting for service to get connected. Current state = "
                            + serviceContext2.state);
            return serviceContext2.compatService;
        }
        requestServiceBind();
        throw new TimeoutException(
                "Failed to acquire connection after [" + j + " " + timeUnit + "]");
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class ServiceContext {
        public final ISetupCompatService compatService;
        public final State state;

        public ServiceContext(State state, ISetupCompatService iSetupCompatService) {
            this.state = state;
            this.compatService = iSetupCompatService;
            if (state == State.CONNECTED) {
                Preconditions.checkNotNull(
                        iSetupCompatService,
                        "CompatService cannot be null when state is connected");
            }
        }

        public ServiceContext(State state) {
            this(state, null);
        }
    }
}
