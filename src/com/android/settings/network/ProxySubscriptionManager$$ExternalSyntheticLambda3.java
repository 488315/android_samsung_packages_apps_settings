package com.android.settings.network;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ProxySubscriptionManager$$ExternalSyntheticLambda3
        implements Predicate {
    public final /* synthetic */ ProxySubscriptionManager f$0;
    public final /* synthetic */ ProxySubscriptionManager.OnActiveSubscriptionChangedListener f$1;

    public /* synthetic */ ProxySubscriptionManager$$ExternalSyntheticLambda3(
            ProxySubscriptionManager proxySubscriptionManager,
            ProxySubscriptionManager.OnActiveSubscriptionChangedListener
                    onActiveSubscriptionChangedListener) {
        this.f$0 = proxySubscriptionManager;
        this.f$1 = onActiveSubscriptionChangedListener;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ProxySubscriptionManager proxySubscriptionManager = this.f$0;
        ProxySubscriptionManager.OnActiveSubscriptionChangedListener
                onActiveSubscriptionChangedListener = this.f$1;
        ProxySubscriptionManager.OnActiveSubscriptionChangedListener
                onActiveSubscriptionChangedListener2 =
                        (ProxySubscriptionManager.OnActiveSubscriptionChangedListener) obj;
        proxySubscriptionManager.getClass();
        if (onActiveSubscriptionChangedListener2 == onActiveSubscriptionChangedListener) {
            return true;
        }
        onActiveSubscriptionChangedListener2.getClass();
        return false;
    }
}
