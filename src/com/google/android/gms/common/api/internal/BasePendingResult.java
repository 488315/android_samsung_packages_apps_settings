package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.safetynet.zzaa;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@KeepName
/* loaded from: classes3.dex */
public abstract class BasePendingResult<R extends Result> extends PendingResult {
    public static final zaq zaa = new zaq();
    public Result zaj;
    public Status zak;
    public volatile boolean zal;
    public boolean zan;
    public final Object zae = new Object();
    public final CountDownLatch zaf = new CountDownLatch(1);
    public final ArrayList zag = new ArrayList();
    public final AtomicReference zai = new AtomicReference();
    public boolean zaq = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class CallbackHandler<R extends Result>
            extends com.google.android.gms.internal.base.zaq {
        public CallbackHandler() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Pair pair = (Pair) message.obj;
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(pair.first);
                try {
                    throw null;
                } catch (RuntimeException e) {
                    zaq zaqVar = BasePendingResult.zaa;
                    throw e;
                }
            }
            if (i == 2) {
                ((BasePendingResult) message.obj).forceFailureUnlessReady(Status.RESULT_TIMEOUT);
                return;
            }
            StringBuilder sb = new StringBuilder(45);
            sb.append("Don't know how to handle message: ");
            sb.append(i);
            Log.wtf("BasePendingResult", sb.toString(), new Exception());
        }
    }

    public BasePendingResult(CallbackHandler<R> callbackHandler) {
        Preconditions.checkNotNull(callbackHandler, "CallbackHandler must not be null");
        new WeakReference(null);
    }

    public final void addStatusListener(PendingResult.StatusListener statusListener) {
        synchronized (this.zae) {
            try {
                if (isReady()) {
                    statusListener.onComplete(this.zak);
                } else {
                    this.zag.add(statusListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract zzaa createFailedResult(Status status);

    public final void forceFailureUnlessReady(Status status) {
        synchronized (this.zae) {
            try {
                if (!isReady()) {
                    setResult(createFailedResult(status));
                    this.zan = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isReady() {
        return this.zaf.getCount() == 0;
    }

    public final void setResult(Result result) {
        synchronized (this.zae) {
            try {
                if (this.zan) {
                    return;
                }
                isReady();
                Preconditions.checkState("Results have already been set", !isReady());
                Preconditions.checkState("Result has already been consumed", !this.zal);
                this.zaj = result;
                this.zak = result.getStatus();
                this.zaf.countDown();
                ArrayList arrayList = this.zag;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((PendingResult.StatusListener) arrayList.get(i)).onComplete(this.zak);
                }
                this.zag.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public BasePendingResult(zabv zabvVar) {
        Looper mainLooper;
        if (zabvVar != null) {
            mainLooper = zabvVar.zaa.zag;
        } else {
            mainLooper = Looper.getMainLooper();
        }
        new CallbackHandler(mainLooper);
        new WeakReference(zabvVar);
    }
}
