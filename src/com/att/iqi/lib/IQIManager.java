package com.att.iqi.lib;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.SparseArray;

import com.att.iqi.IIQIBroker;
import com.att.iqi.IMetricQueryCallback;
import com.att.iqi.IMetricSourcingCallback;
import com.att.iqi.IProfileChangedCallback;
import com.att.iqi.IServiceStateChangeCallback;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class IQIManager {
    public static IQIManager sInstance;
    public static final Object sLock = new Object();
    public IIQIBroker mIQIService;
    public final Handler mMessageDispatcher;
    public final SparseArray mMetricQueryCallbackMap = new SparseArray();
    public final List mProfileChangeListenerList = new ArrayList();
    public final SparseArray mMetricSourcingListenerMap = new SparseArray();
    public final List mServiceStateChangeListenerList = new ArrayList();
    public final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    public final IMetricQueryCallback mQueryCallback =
            new IMetricQueryCallback.Stub() { // from class: com.att.iqi.lib.IQIManager.2
                @Override // com.att.iqi.IMetricQueryCallback.Stub
                public void onMetricQueried(Metric.ID id, byte[] bArr) throws RemoteException {
                    if (id == null) {
                        return;
                    }
                    IQIManager.this
                            .mMessageDispatcher
                            .obtainMessage(1, id.asInt(), 0, bArr)
                            .sendToTarget();
                }
            };
    public final IMetricSourcingCallback mMetricSourcingCallback =
            new IMetricSourcingCallback.Stub() { // from class: com.att.iqi.lib.IQIManager.3
                @Override // com.att.iqi.IMetricSourcingCallback.Stub
                public void onMetricSourced(Metric.ID id, byte[] bArr) throws RemoteException {
                    if (id == null) {
                        return;
                    }
                    IQIManager.this
                            .mMessageDispatcher
                            .obtainMessage(2, id.asInt(), 0, bArr)
                            .sendToTarget();
                }
            };
    public final IProfileChangedCallback mProfileChangedCallback =
            new IProfileChangedCallback.Stub() { // from class: com.att.iqi.lib.IQIManager.4
                @Override // com.att.iqi.IProfileChangedCallback.Stub
                public void onProfileChanged() throws RemoteException {
                    IQIManager.this.mMessageDispatcher.obtainMessage(3).sendToTarget();
                }
            };
    public final IServiceStateChangeCallback mServiceStateChangedCallback =
            new IServiceStateChangeCallback.Stub() { // from class: com.att.iqi.lib.IQIManager.5
                @Override // com.att.iqi.IServiceStateChangeCallback.Stub
                public void onServiceChange(boolean z) throws RemoteException {
                    IQIManager.this
                            .mMessageDispatcher
                            .obtainMessage(4, z ? 1 : 0, 0)
                            .sendToTarget();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class MessageDispatcherCallback implements Handler.Callback {
        public static final int MSG_ON_METRIC_QUERIED = 1;
        public static final int MSG_ON_METRIC_SOURCED = 2;
        public static final int MSG_ON_PROFILE_CHANGED = 3;
        public static final int MSG_ON_SERVICE_CHANGED = 4;
        public static final int MSG_TIMED_OUT_WAITING_PACKAGE_FORCE_STOPPED = 5;
        public final WeakReference reference;

        public MessageDispatcherCallback(IQIManager iQIManager) {
            this.reference = new WeakReference(iQIManager);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            IQIManager iQIManager = (IQIManager) this.reference.get();
            if (iQIManager == null) {
                return true;
            }
            int i = message.what;
            if (i == 1) {
                final int i2 = message.arg1;
                final MetricQueryCallback metricQueryCallback =
                        (MetricQueryCallback) iQIManager.mMetricQueryCallbackMap.get(i2);
                if (metricQueryCallback != null) {
                    Object obj = message.obj;
                    final ByteBuffer wrap =
                            ByteBuffer.wrap(obj != null ? (byte[]) obj : new byte[0]);
                    final int i3 = 0;
                    iQIManager.mExecutorService.execute(
                            new Runnable() { // from class:
                                             // com.att.iqi.lib.IQIManager$MessageDispatcherCallback$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i3) {
                                        case 0:
                                            IQIManager.MetricQueryCallback metricQueryCallback2 =
                                                    (IQIManager.MetricQueryCallback)
                                                            metricQueryCallback;
                                            int i4 = i2;
                                            metricQueryCallback2.onMetricQueried(
                                                    new Metric.ID(i4), wrap);
                                            break;
                                        default:
                                            IQIManager.MetricSourcingListener
                                                    metricSourcingListener =
                                                            (IQIManager.MetricSourcingListener)
                                                                    metricQueryCallback;
                                            int i5 = i2;
                                            metricSourcingListener.onMetricSourcing(
                                                    new Metric.ID(i5), wrap);
                                            break;
                                    }
                                }
                            });
                }
            } else if (i == 2) {
                final int i4 = message.arg1;
                final MetricSourcingListener metricSourcingListener =
                        (MetricSourcingListener) iQIManager.mMetricSourcingListenerMap.get(i4);
                if (metricSourcingListener != null) {
                    Object obj2 = message.obj;
                    final ByteBuffer wrap2 =
                            ByteBuffer.wrap(obj2 != null ? (byte[]) obj2 : new byte[0]);
                    final int i5 = 1;
                    iQIManager.mExecutorService.execute(
                            new Runnable() { // from class:
                                             // com.att.iqi.lib.IQIManager$MessageDispatcherCallback$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i5) {
                                        case 0:
                                            IQIManager.MetricQueryCallback metricQueryCallback2 =
                                                    (IQIManager.MetricQueryCallback)
                                                            metricSourcingListener;
                                            int i42 = i4;
                                            metricQueryCallback2.onMetricQueried(
                                                    new Metric.ID(i42), wrap2);
                                            break;
                                        default:
                                            IQIManager.MetricSourcingListener
                                                    metricSourcingListener2 =
                                                            (IQIManager.MetricSourcingListener)
                                                                    metricSourcingListener;
                                            int i52 = i4;
                                            metricSourcingListener2.onMetricSourcing(
                                                    new Metric.ID(i52), wrap2);
                                            break;
                                    }
                                }
                            });
                }
            } else if (i == 3) {
                synchronized (iQIManager.mProfileChangeListenerList) {
                    try {
                        Iterator it =
                                ((ArrayList) iQIManager.mProfileChangeListenerList).iterator();
                        while (it.hasNext()) {
                            final ProfileChangeListener profileChangeListener =
                                    (ProfileChangeListener) it.next();
                            ExecutorService executorService = iQIManager.mExecutorService;
                            Objects.requireNonNull(profileChangeListener);
                            executorService.execute(
                                    new Runnable() { // from class:
                                                     // com.att.iqi.lib.IQIManager$MessageDispatcherCallback$$ExternalSyntheticLambda2
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            IQIManager.ProfileChangeListener.this
                                                    .onProfileChanged();
                                        }
                                    });
                        }
                    } finally {
                    }
                }
            } else if (i == 4) {
                final boolean z = message.arg1 == 1;
                synchronized (iQIManager.mServiceStateChangeListenerList) {
                    try {
                        Iterator it2 =
                                ((ArrayList) iQIManager.mServiceStateChangeListenerList).iterator();
                        while (it2.hasNext()) {
                            final ServiceStateChangeListener serviceStateChangeListener =
                                    (ServiceStateChangeListener) it2.next();
                            iQIManager.mExecutorService.execute(
                                    new Runnable() { // from class:
                                                     // com.att.iqi.lib.IQIManager$MessageDispatcherCallback$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            IQIManager.ServiceStateChangeListener.this
                                                    .onServiceChange(z);
                                        }
                                    });
                        }
                    } finally {
                    }
                }
            } else if (i == 5) {
                Object obj3 = message.obj;
                if (obj3 instanceof Runnable) {
                    iQIManager.mExecutorService.execute((Runnable) obj3);
                }
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface MetricQueryCallback {
        void onMetricQueried(Metric.ID id, ByteBuffer byteBuffer);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface MetricSourcingListener {
        void onMetricSourcing(Metric.ID id, ByteBuffer byteBuffer);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ProfileChangeListener {
        void onProfileChanged();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ServiceStateChangeListener {
        void onServiceChange(boolean z);
    }

    public IQIManager() {
        getService();
        HandlerThread handlerThread = new HandlerThread("msg-handler-iqi");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        this.mMessageDispatcher =
                new Handler(
                        looper == null ? Looper.getMainLooper() : looper,
                        new MessageDispatcherCallback(this));
    }

    public static IQIManager getInstance() {
        IQIManager iQIManager;
        synchronized (sLock) {
            try {
                if (sInstance == null) {
                    sInstance = new IQIManager();
                }
                iQIManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return iQIManager;
    }

    public void disableService() {
        try {
            getService();
            this.mIQIService.disableService();
        } catch (Exception unused) {
        }
    }

    @SuppressLint({"WrongConstant"})
    public void forceStopService(Context context, final Runnable runnable) {
        if (runnable != null) {
            Handler handler = this.mMessageDispatcher;
            final Message obtainMessage = handler.obtainMessage(5, runnable);
            context.registerReceiver(
                    new BroadcastReceiver() { // from class: com.att.iqi.lib.IQIManager.1
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context2, Intent intent) {
                            IQIManager.this.mMessageDispatcher.removeMessages(
                                    obtainMessage.what, runnable);
                            IQIManager.this.mExecutorService.execute(runnable);
                            context2.unregisterReceiver(this);
                        }
                    },
                    new IntentFilter("com.att.iqi.action.SERVICE_FORCE_STOPPED"),
                    "com.att.iqi.permission.RECEIVE_FORCE_STOP_NOTIFICATION",
                    handler,
                    2);
            handler.sendMessageDelayed(obtainMessage, 3500L);
        }
        try {
            getService();
            this.mIQIService.forceStopService();
        } catch (Exception unused) {
        }
    }

    public final void getService() {
        if (this.mIQIService != null) {
            return;
        }
        try {
            IBinder iBinder =
                    (IBinder)
                            Class.forName("android.os.ServiceManager")
                                    .getDeclaredMethod("getService", String.class)
                                    .invoke(null, "iqi");
            if (iBinder != null) {
                this.mIQIService = IIQIBroker.Stub.asInterface(iBinder);
            }
        } catch (ClassNotFoundException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException unused) {
        }
    }

    public void registerMetricSourcingListener(
            Metric.ID id, MetricSourcingListener metricSourcingListener) {
        if (id == null || metricSourcingListener == null) {
            return;
        }
        try {
            getService();
            this.mIQIService.registerMetricSourcingCallback(id, this.mMetricSourcingCallback);
            synchronized (this.mMetricSourcingListenerMap) {
                this.mMetricSourcingListenerMap.append(id.asInt(), metricSourcingListener);
            }
        } catch (IllegalArgumentException unused) {
            throw new IllegalArgumentException(
                    "Callback already registered for metric ID " + id.asString());
        } catch (Exception unused2) {
        }
    }

    public void registerProfileChangeListener(ProfileChangeListener profileChangeListener) {
        boolean isEmpty;
        if (profileChangeListener == null) {
            return;
        }
        synchronized (this.mProfileChangeListenerList) {
            isEmpty = ((ArrayList) this.mProfileChangeListenerList).isEmpty();
            ((ArrayList) this.mProfileChangeListenerList).add(profileChangeListener);
        }
        if (isEmpty) {
            try {
                getService();
                this.mIQIService.registerProfileChangedCallback(this.mProfileChangedCallback);
            } catch (Exception unused) {
            }
        }
    }

    public void registerQueryCallback(Metric.ID id, MetricQueryCallback metricQueryCallback) {
        if (id == null || metricQueryCallback == null) {
            return;
        }
        try {
            getService();
            this.mIQIService.registerMetricQueryCallback(id, this.mQueryCallback);
            synchronized (this.mMetricQueryCallbackMap) {
                this.mMetricQueryCallbackMap.append(id.asInt(), metricQueryCallback);
            }
        } catch (IllegalArgumentException unused) {
            throw new IllegalArgumentException(
                    "Callback already registered for metric ID " + id.asString());
        } catch (Exception unused2) {
        }
    }

    public void registerServiceStateChangeListener(
            ServiceStateChangeListener serviceStateChangeListener) {
        boolean isEmpty;
        if (serviceStateChangeListener == null) {
            return;
        }
        synchronized (this.mServiceStateChangeListenerList) {
            isEmpty = ((ArrayList) this.mServiceStateChangeListenerList).isEmpty();
            ((ArrayList) this.mServiceStateChangeListenerList).add(serviceStateChangeListener);
        }
        if (isEmpty) {
            try {
                getService();
                this.mIQIService.registerServiceChangedCallback(this.mServiceStateChangedCallback);
            } catch (Exception unused) {
            }
        }
    }

    public boolean setUnlockCode(long j) {
        try {
            getService();
            return this.mIQIService.setUnlockCode(j);
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean shouldSubmitMetric(Metric.ID id) {
        if (id == null) {
            return false;
        }
        try {
            getService();
            return this.mIQIService.shouldSubmitMetric(id);
        } catch (Exception unused) {
            return false;
        }
    }

    public void submitMetric(Metric metric) {
        if (metric == null) {
            return;
        }
        try {
            getService();
            this.mIQIService.submitMetric(metric);
        } catch (Exception unused) {
        }
    }

    public void unregisterMetricSourcingListener(
            Metric.ID id, MetricSourcingListener metricSourcingListener) {
        if (id == null || metricSourcingListener == null) {
            return;
        }
        try {
            getService();
            this.mIQIService.unregisterMetricSourcingCallback(id, this.mMetricSourcingCallback);
            synchronized (this.mMetricSourcingListenerMap) {
                this.mMetricSourcingListenerMap.remove(id.asInt());
            }
        } catch (Exception unused) {
        }
    }

    public void unregisterProfileChangeListener(ProfileChangeListener profileChangeListener) {
        boolean isEmpty;
        if (profileChangeListener == null) {
            return;
        }
        synchronized (this.mProfileChangeListenerList) {
            ((ArrayList) this.mProfileChangeListenerList).remove(profileChangeListener);
            isEmpty = ((ArrayList) this.mProfileChangeListenerList).isEmpty();
        }
        if (isEmpty) {
            try {
                getService();
                this.mIQIService.unregisterProfileChangedCallback(this.mProfileChangedCallback);
            } catch (Exception unused) {
            }
        }
    }

    public void unregisterQueryCallback(Metric.ID id, MetricQueryCallback metricQueryCallback) {
        if (id == null || metricQueryCallback == null) {
            return;
        }
        try {
            getService();
            this.mIQIService.unregisterMetricQueryCallback(id, this.mQueryCallback);
            synchronized (this.mMetricQueryCallbackMap) {
                this.mMetricQueryCallbackMap.remove(id.asInt());
            }
        } catch (Exception unused) {
        }
    }

    public void unregisterServiceStateChangeListener(
            ServiceStateChangeListener serviceStateChangeListener) {
        boolean isEmpty;
        if (serviceStateChangeListener == null) {
            return;
        }
        synchronized (this.mServiceStateChangeListenerList) {
            ((ArrayList) this.mServiceStateChangeListenerList).remove(serviceStateChangeListener);
            isEmpty = ((ArrayList) this.mServiceStateChangeListenerList).isEmpty();
        }
        if (isEmpty) {
            try {
                getService();
                this.mIQIService.unregisterServiceChangedCallback(
                        this.mServiceStateChangedCallback);
            } catch (Exception unused) {
            }
        }
    }
}
