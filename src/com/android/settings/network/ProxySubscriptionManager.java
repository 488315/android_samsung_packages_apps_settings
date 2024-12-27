package com.android.settings.network;

import android.content.Context;
import android.os.Looper;
import android.telephony.SubscriptionInfo;
import androidx.annotation.Keep;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.settings.network.ProxySubscriptionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProxySubscriptionManager implements LifecycleObserver {
    public static ProxySubscriptionManager sSingleton;
    public List mActiveSubscriptionsListeners;
    public GlobalSettingsChangeListener mAirplaneModeMonitor;
    public Lifecycle mLifecycle;
    public List mPendingNotifyListeners;
    public ActiveSubscriptionsListener mSubscriptionMonitor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnActiveSubscriptionChangedListener {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.settings.network.ActiveSubscriptionsListener, com.android.settings.network.ProxySubscriptionManager$1] */
    public static ProxySubscriptionManager getInstance(Context context) {
        ProxySubscriptionManager proxySubscriptionManager = sSingleton;
        if (proxySubscriptionManager != null) {
            return proxySubscriptionManager;
        }
        Context applicationContext = context.getApplicationContext();
        ProxySubscriptionManager proxySubscriptionManager2 = new ProxySubscriptionManager();
        Looper mainLooper = applicationContext.getMainLooper();
        final ?? r2 = new ActiveSubscriptionsListener(mainLooper, applicationContext) { // from class: com.android.settings.network.ProxySubscriptionManager.1
            @Override // com.android.settings.network.ActiveSubscriptionsListener
            public final void onChanged() {
                ProxySubscriptionManager.this.notifySubscriptionInfoMightChanged();
            }
        };
        proxySubscriptionManager2.init(applicationContext, r2, new GlobalSettingsChangeListener(mainLooper, applicationContext) { // from class: com.android.settings.network.ProxySubscriptionManager.2
            @Override // com.android.settings.network.GlobalSettingsChangeListener
            public final void onChanged$1() {
                r2.clearCache();
                ProxySubscriptionManager.this.notifySubscriptionInfoMightChanged();
            }
        });
        sSingleton = proxySubscriptionManager2;
        return proxySubscriptionManager2;
    }

    @Keep
    public void addActiveSubscriptionsListener(OnActiveSubscriptionChangedListener onActiveSubscriptionChangedListener) {
        ((ArrayList) this.mPendingNotifyListeners).removeIf(new ProxySubscriptionManager$$ExternalSyntheticLambda3(this, onActiveSubscriptionChangedListener));
        ((ArrayList) this.mActiveSubscriptionsListeners).removeIf(new ProxySubscriptionManager$$ExternalSyntheticLambda3(this, onActiveSubscriptionChangedListener));
        if (onActiveSubscriptionChangedListener != null) {
            ((ArrayList) this.mActiveSubscriptionsListeners).add(onActiveSubscriptionChangedListener);
        }
    }

    public final SubscriptionInfo getAccessibleSubscriptionInfo(int i) {
        ActiveSubscriptionsListener activeSubscriptionsListener = this.mSubscriptionMonitor;
        SubscriptionInfo activeSubscriptionInfo = activeSubscriptionsListener.getActiveSubscriptionInfo(i);
        if (activeSubscriptionInfo != null) {
            return activeSubscriptionInfo;
        }
        List<SubscriptionInfo> availableSubscriptionInfoList = activeSubscriptionsListener.getSubscriptionManager().getAvailableSubscriptionInfoList();
        if (availableSubscriptionInfoList == null) {
            return null;
        }
        for (SubscriptionInfo subscriptionInfo : availableSubscriptionInfoList) {
            if (subscriptionInfo.getSubscriptionId() == i) {
                return subscriptionInfo;
            }
        }
        return null;
    }

    @Keep
    public void init(Context context, ActiveSubscriptionsListener activeSubscriptionsListener, GlobalSettingsChangeListener globalSettingsChangeListener) {
        this.mActiveSubscriptionsListeners = new ArrayList();
        this.mPendingNotifyListeners = new ArrayList();
        this.mSubscriptionMonitor = activeSubscriptionsListener;
        this.mAirplaneModeMonitor = globalSettingsChangeListener;
        activeSubscriptionsListener.monitorSubscriptionsChange(true);
    }

    @Keep
    public void notifySubscriptionInfoMightChanged() {
        if (this.mPendingNotifyListeners == null || this.mActiveSubscriptionsListeners == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mPendingNotifyListeners);
        arrayList.addAll(this.mActiveSubscriptionsListeners);
        ((ArrayList) this.mActiveSubscriptionsListeners).clear();
        this.mPendingNotifyListeners.clear();
        processStatusChangeOnListeners(arrayList);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        this.mSubscriptionMonitor.monitorSubscriptionsChange(false);
        this.mAirplaneModeMonitor.close();
        Lifecycle lifecycle = this.mLifecycle;
        if (lifecycle != null) {
            lifecycle.removeObserver(this);
            this.mLifecycle = null;
            sSingleton = null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mSubscriptionMonitor.monitorSubscriptionsChange(true);
        List list = this.mPendingNotifyListeners;
        this.mPendingNotifyListeners = new ArrayList();
        processStatusChangeOnListeners(list);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mSubscriptionMonitor.monitorSubscriptionsChange(false);
    }

    public final void processStatusChangeOnListeners(List list) {
        Map map = (Map) list.stream().collect(Collectors.groupingBy(new Function() { // from class: com.android.settings.network.ProxySubscriptionManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ProxySubscriptionManager.this.getClass();
                ((ProxySubscriptionManager.OnActiveSubscriptionChangedListener) obj).getClass();
                return 1;
            }
        }));
        final int i = 0;
        map.computeIfPresent(0, new BiFunction(this) { // from class: com.android.settings.network.ProxySubscriptionManager$$ExternalSyntheticLambda1
            public final /* synthetic */ ProxySubscriptionManager f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                int i2 = i;
                ProxySubscriptionManager proxySubscriptionManager = this.f$0;
                List list2 = (List) obj2;
                switch (i2) {
                    case 0:
                        proxySubscriptionManager.mPendingNotifyListeners.addAll(list2);
                        break;
                    default:
                        ((ArrayList) proxySubscriptionManager.mActiveSubscriptionsListeners).addAll(list2);
                        list2.stream().forEach(new ProxySubscriptionManager$$ExternalSyntheticLambda4());
                        break;
                }
                return list2;
            }
        });
        final int i2 = 1;
        map.computeIfPresent(1, new BiFunction(this) { // from class: com.android.settings.network.ProxySubscriptionManager$$ExternalSyntheticLambda1
            public final /* synthetic */ ProxySubscriptionManager f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                int i22 = i2;
                ProxySubscriptionManager proxySubscriptionManager = this.f$0;
                List list2 = (List) obj2;
                switch (i22) {
                    case 0:
                        proxySubscriptionManager.mPendingNotifyListeners.addAll(list2);
                        break;
                    default:
                        ((ArrayList) proxySubscriptionManager.mActiveSubscriptionsListeners).addAll(list2);
                        list2.stream().forEach(new ProxySubscriptionManager$$ExternalSyntheticLambda4());
                        break;
                }
                return list2;
            }
        });
    }

    @Keep
    public void removeActiveSubscriptionsListener(OnActiveSubscriptionChangedListener onActiveSubscriptionChangedListener) {
        ((ArrayList) this.mPendingNotifyListeners).removeIf(new ProxySubscriptionManager$$ExternalSyntheticLambda3(this, onActiveSubscriptionChangedListener));
        ((ArrayList) this.mActiveSubscriptionsListeners).removeIf(new ProxySubscriptionManager$$ExternalSyntheticLambda3(this, onActiveSubscriptionChangedListener));
    }

    public final void setLifecycle(Lifecycle lifecycle) {
        Lifecycle lifecycle2 = this.mLifecycle;
        if (lifecycle2 == lifecycle) {
            return;
        }
        if (lifecycle2 != null) {
            lifecycle2.removeObserver(this);
        }
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.mLifecycle = lifecycle;
        GlobalSettingsChangeListener globalSettingsChangeListener = this.mAirplaneModeMonitor;
        Lifecycle lifecycle3 = globalSettingsChangeListener.mLifecycle;
        if (lifecycle3 != null) {
            lifecycle3.removeObserver(globalSettingsChangeListener);
        }
        if (lifecycle != null) {
            lifecycle.addObserver(globalSettingsChangeListener);
        }
        globalSettingsChangeListener.mLifecycle = lifecycle;
    }
}
