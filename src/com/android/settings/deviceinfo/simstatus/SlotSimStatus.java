package com.android.settings.deviceinfo.simstatus;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.android.settings.network.SubscriptionsChangeListener;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SlotSimStatus extends LiveData
        implements DefaultLifecycleObserver,
                SubscriptionsChangeListener.SubscriptionsChangeListenerClient {
    public int mBasePreferenceOrdering;
    public final Phaser mBlocker;
    public final Context mContext;
    public final AtomicLong mDataVersion;
    public final AtomicInteger mNumberOfSlots = new AtomicInteger(0);
    public final ConcurrentHashMap mSubscriptionMap = new ConcurrentHashMap();
    public final SubscriptionsChangeListener mSubscriptionsChangeListener;

    public SlotSimStatus(final Context context, Executor executor, Lifecycle lifecycle) {
        Phaser phaser = new Phaser(1);
        this.mBlocker = phaser;
        AtomicLong atomicLong = new AtomicLong(0L);
        this.mDataVersion = atomicLong;
        this.mContext = context;
        if (executor == null) {
            queryDetails(context);
            setValue(Long.valueOf(atomicLong.incrementAndGet()));
            phaser.arrive();
        } else {
            executor.execute(
                    new Runnable() { // from class:
                                     // com.android.settings.deviceinfo.simstatus.SlotSimStatus$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SlotSimStatus slotSimStatus = SlotSimStatus.this;
                            slotSimStatus.queryDetails(context);
                            slotSimStatus.postValue(
                                    Long.valueOf(slotSimStatus.mDataVersion.incrementAndGet()));
                            slotSimStatus.mBlocker.arrive();
                        }
                    });
        }
        if (lifecycle != null) {
            lifecycle.addObserver(this);
            SubscriptionsChangeListener subscriptionsChangeListener =
                    new SubscriptionsChangeListener(context, this);
            this.mSubscriptionsChangeListener = subscriptionsChangeListener;
            subscriptionsChangeListener.start();
        }
    }

    public final SubscriptionInfo getSubscriptionInfo(int i) {
        if (i >= size()) {
            return null;
        }
        return (SubscriptionInfo) this.mSubscriptionMap.get(Integer.valueOf(i));
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public final void onAirplaneModeChanged(boolean z) {
        if (z) {
            queryDetails(this.mContext);
            setValue(Long.valueOf(this.mDataVersion.incrementAndGet()));
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onDestroy(LifecycleOwner lifecycleOwner) {
        SubscriptionsChangeListener subscriptionsChangeListener = this.mSubscriptionsChangeListener;
        if (subscriptionsChangeListener != null) {
            subscriptionsChangeListener.stop();
        }
        lifecycleOwner.getLifecycle().removeObserver(this);
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public final void onSubscriptionsChanged() {
        queryDetails(this.mContext);
        setValue(Long.valueOf(this.mDataVersion.incrementAndGet()));
    }

    public final void queryDetails(Context context) {
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        if (telephonyManager != null) {
            this.mNumberOfSlots.set(telephonyManager.getPhoneCount());
        }
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        if (subscriptionManager == null) {
            this.mSubscriptionMap.clear();
            return;
        }
        List<SubscriptionInfo> activeSubscriptionInfoList =
                subscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null || activeSubscriptionInfoList.size() <= 0) {
            this.mSubscriptionMap.clear();
            Log.d("SlotSimStatus", "No active SIM.");
            return;
        }
        this.mSubscriptionMap.clear();
        activeSubscriptionInfoList.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.deviceinfo.simstatus.SlotSimStatus$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SlotSimStatus slotSimStatus = SlotSimStatus.this;
                        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                        slotSimStatus.getClass();
                        slotSimStatus.mSubscriptionMap.put(
                                Integer.valueOf(subscriptionInfo.getSimSlotIndex()),
                                subscriptionInfo);
                    }
                });
        Log.d("SlotSimStatus", "Number of active SIM: " + activeSubscriptionInfoList.size());
    }

    public final int size() {
        this.mBlocker.awaitAdvance(0);
        return this.mNumberOfSlots.get();
    }
}
