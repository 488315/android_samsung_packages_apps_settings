package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ActiveSubscriptionsListener
        extends SubscriptionManager.OnSubscriptionsChangedListener implements AutoCloseable {
    public final AtomicInteger mCacheState;
    public List mCachedActiveSubscriptionInfo;
    public final Context mContext;
    public final Looper mLooper;
    public final AtomicInteger mMaxActiveSubscriptionInfos;
    public final IntentFilter mSubscriptionChangeIntentFilter;
    public BroadcastReceiver mSubscriptionChangeReceiver;
    public SubscriptionManager mSubscriptionManager;
    public final int mTargetSubscriptionId;

    public ActiveSubscriptionsListener(Context context, Looper looper) {
        super(looper);
        this.mLooper = looper;
        this.mContext = context;
        this.mTargetSubscriptionId = -1;
        this.mCacheState = new AtomicInteger(0);
        this.mMaxActiveSubscriptionInfos = new AtomicInteger(-1);
        IntentFilter intentFilter = new IntentFilter();
        this.mSubscriptionChangeIntentFilter = intentFilter;
        intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        intentFilter.addAction("android.intent.action.RADIO_TECHNOLOGY");
        intentFilter.addAction("android.telephony.action.MULTI_SIM_CONFIG_CHANGED");
    }

    public final void clearCache() {
        this.mMaxActiveSubscriptionInfos.set(-1);
        this.mCacheState.compareAndSet(4, 3);
        this.mCachedActiveSubscriptionInfo = null;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        monitorSubscriptionsChange(false);
    }

    public final SubscriptionInfo getActiveSubscriptionInfo(int i) {
        List<SubscriptionInfo> activeSubscriptionsInfo = getActiveSubscriptionsInfo();
        if (activeSubscriptionsInfo == null) {
            return null;
        }
        for (SubscriptionInfo subscriptionInfo : activeSubscriptionsInfo) {
            if (subscriptionInfo.getSubscriptionId() == i) {
                return subscriptionInfo;
            }
        }
        return null;
    }

    public final List getActiveSubscriptionsInfo() {
        if (this.mCacheState.get() >= 4) {
            return this.mCachedActiveSubscriptionInfo;
        }
        this.mCachedActiveSubscriptionInfo =
                getSubscriptionManager().getActiveSubscriptionInfoList();
        this.mCacheState.compareAndSet(3, 4);
        return this.mCachedActiveSubscriptionInfo;
    }

    public BroadcastReceiver getSubscriptionChangeReceiver() {
        return new BroadcastReceiver() { // from class:
                                         // com.android.settings.network.ActiveSubscriptionsListener.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                List list;
                if (isInitialStickyBroadcast()) {
                    return;
                }
                String action = intent.getAction();
                if (TextUtils.isEmpty(action)) {
                    return;
                }
                if ("android.telephony.action.CARRIER_CONFIG_CHANGED".equals(action)) {
                    int intExtra =
                            intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                    ActiveSubscriptionsListener activeSubscriptionsListener =
                            ActiveSubscriptionsListener.this;
                    if (activeSubscriptionsListener.mCacheState.get() >= 4
                            && (list = activeSubscriptionsListener.mCachedActiveSubscriptionInfo)
                                    != null) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            if (((SubscriptionInfo) it.next()).getSubscriptionId() == intExtra) {
                                activeSubscriptionsListener.clearCache();
                                if (SubscriptionManager.isValidSubscriptionId(
                                                ActiveSubscriptionsListener.this
                                                        .mTargetSubscriptionId)
                                        && SubscriptionManager.isValidSubscriptionId(intExtra)
                                        && ActiveSubscriptionsListener.this.mTargetSubscriptionId
                                                != intExtra) {
                                    return;
                                }
                            }
                        }
                        return;
                    }
                    return;
                }
                ActiveSubscriptionsListener.this.onSubscriptionsChanged();
            }
        };
    }

    public final SubscriptionManager getSubscriptionManager() {
        if (this.mSubscriptionManager == null) {
            this.mSubscriptionManager =
                    ((SubscriptionManager)
                                    this.mContext.getSystemService(SubscriptionManager.class))
                            .createForAllUserProfiles();
        }
        return this.mSubscriptionManager;
    }

    public final void monitorSubscriptionsChange(boolean z) {
        if (z) {
            if (this.mCacheState.compareAndSet(0, 2)) {
                if (this.mSubscriptionChangeReceiver == null) {
                    this.mSubscriptionChangeReceiver = getSubscriptionChangeReceiver();
                }
                this.mContext.registerReceiver(
                        this.mSubscriptionChangeReceiver,
                        this.mSubscriptionChangeIntentFilter,
                        null,
                        new Handler(this.mLooper),
                        2);
                registerForSubscriptionsChange();
                this.mCacheState.compareAndSet(2, 3);
                return;
            }
            return;
        }
        int andSet = this.mCacheState.getAndSet(1);
        if (andSet <= 1) {
            this.mCacheState.compareAndSet(1, andSet);
            return;
        }
        BroadcastReceiver broadcastReceiver = this.mSubscriptionChangeReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
        getSubscriptionManager().removeOnSubscriptionsChangedListener(this);
        clearCache();
        this.mCacheState.compareAndSet(1, 0);
    }

    public abstract void onChanged();

    @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
    public final void onSubscriptionsChanged() {
        clearCache();
        if (this.mCacheState.get() < 3) {
            return;
        }
        onChanged();
    }

    public void registerForSubscriptionsChange() {
        getSubscriptionManager()
                .addOnSubscriptionsChangedListener(this.mContext.getMainExecutor(), this);
    }
}
