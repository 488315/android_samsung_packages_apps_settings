package com.android.settings;

import android.content.Context;
import android.telephony.SubscriptionManager;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class ResetSubscriptionContract implements AutoCloseable {
    public final Context mContext;
    public ExecutorService mExecutorService;
    public final int[] mResetSubscriptionIds;
    protected SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionsChangedListener;
    public final AtomicBoolean mSubscriptionsUpdateNotify = new AtomicBoolean();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.ResetSubscriptionContract$1, reason: invalid class name */
    public final class AnonymousClass1 extends SubscriptionManager.OnSubscriptionsChangedListener {
        public AnonymousClass1() {}

        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public final void onSubscriptionsChanged() {
            ResetSubscriptionContract.this.mSubscriptionsUpdateNotify.set(true);
            ResetSubscriptionContract.this
                    .mContext
                    .getMainExecutor()
                    .execute(
                            new Runnable() { // from class:
                                             // com.android.settings.ResetSubscriptionContract$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Integer anyMissingSubscriptionId;
                                    ResetSubscriptionContract.AnonymousClass1 anonymousClass1 =
                                            ResetSubscriptionContract.AnonymousClass1.this;
                                    if (!ResetSubscriptionContract.this.mSubscriptionsUpdateNotify
                                                    .getAndSet(false)
                                            || (anyMissingSubscriptionId =
                                                            ResetSubscriptionContract.this
                                                                    .getAnyMissingSubscriptionId())
                                                    == null) {
                                        return;
                                    }
                                    ResetSubscriptionContract resetSubscriptionContract =
                                            ResetSubscriptionContract.this;
                                    int intValue = anyMissingSubscriptionId.intValue();
                                    ResetNetworkConfirm.AnonymousClass1 anonymousClass12 =
                                            (ResetNetworkConfirm.AnonymousClass1)
                                                    resetSubscriptionContract;
                                    anonymousClass12.getClass();
                                    Log.w(
                                            "ResetNetworkConfirm",
                                            "subId " + intValue + " no longer active.");
                                    ResetNetworkConfirm.this.getActivity().onBackPressed();
                                }
                            });
        }
    }

    public ResetSubscriptionContract(Context context, ResetNetworkRequest resetNetworkRequest) {
        this.mContext = context;
        int[] array =
                IntStream.of(
                                resetNetworkRequest.mResetTelephonyManager,
                                resetNetworkRequest.mResetApn,
                                resetNetworkRequest.mSubscriptionIdToResetIms)
                        .sorted()
                        .distinct()
                        .filter(new ResetSubscriptionContract$$ExternalSyntheticLambda0())
                        .toArray();
        this.mResetSubscriptionIds = array;
        if (array.length <= 0) {
            return;
        }
        this.mExecutorService = Executors.newSingleThreadExecutor();
        SubscriptionManager subscriptionManager = getSubscriptionManager();
        if (subscriptionManager == null) {
            return;
        }
        SubscriptionManager.OnSubscriptionsChangedListener changeListener = getChangeListener();
        this.mSubscriptionsChangedListener = changeListener;
        subscriptionManager.addOnSubscriptionsChangedListener(
                this.mExecutorService, changeListener);
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        if (this.mExecutorService == null) {
            return;
        }
        SubscriptionManager subscriptionManager = getSubscriptionManager();
        if (subscriptionManager != null) {
            subscriptionManager.removeOnSubscriptionsChangedListener(
                    this.mSubscriptionsChangedListener);
        }
        this.mExecutorService.shutdownNow();
        this.mExecutorService = null;
    }

    public final Integer getAnyMissingSubscriptionId() {
        if (this.mResetSubscriptionIds.length <= 0) {
            return null;
        }
        SubscriptionManager subscriptionManager = getSubscriptionManager();
        int i = 0;
        if (subscriptionManager == null) {
            Log.w("ResetSubscriptionContract", "Fail to access subscription manager");
            return Integer.valueOf(this.mResetSubscriptionIds[0]);
        }
        while (true) {
            int[] iArr = this.mResetSubscriptionIds;
            if (i >= iArr.length) {
                return null;
            }
            int i2 = iArr[i];
            if (subscriptionManager.getActiveSubscriptionInfo(i2) == null) {
                Log.w("ResetSubscriptionContract", "SubId " + i2 + " no longer active.");
                return Integer.valueOf(i2);
            }
            i++;
        }
    }

    public SubscriptionManager.OnSubscriptionsChangedListener getChangeListener() {
        return new AnonymousClass1();
    }

    public SubscriptionManager getSubscriptionManager() {
        return (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
    }
}
