package com.android.settings.datausage;

import android.content.Context;
import android.net.NetworkPolicyManager;
import android.util.SparseIntArray;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.utils.ThreadUtils;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataSaverBackend {
    public final Context mContext;
    public final DynamicDenylistManager mDynamicDenylistManager;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final NetworkPolicyManager mPolicyManager;
    public final ArrayList mListeners = new ArrayList();
    public final SparseIntArray mUidPolicies = new SparseIntArray();
    public final AnonymousClass1 mPolicyListener = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.datausage.DataSaverBackend$1, reason: invalid class name */
    public final class AnonymousClass1 extends NetworkPolicyManager.Listener {
        public AnonymousClass1() {}

        public final void onRestrictBackgroundChanged(final boolean z) {
            ThreadUtils.postOnMainThread(
                    new Runnable() { // from class:
                                     // com.android.settings.datausage.DataSaverBackend$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DataSaverBackend.AnonymousClass1 anonymousClass1 =
                                    DataSaverBackend.AnonymousClass1.this;
                            boolean z2 = z;
                            DataSaverBackend dataSaverBackend = DataSaverBackend.this;
                            for (int i = 0; i < dataSaverBackend.mListeners.size(); i++) {
                                ((DataSaverBackend.Listener) dataSaverBackend.mListeners.get(i))
                                        .onDataSaverChanged(z2);
                            }
                        }
                    });
        }

        public final void onUidPoliciesChanged(final int i, final int i2) {
            ThreadUtils.postOnMainThread(
                    new Runnable() { // from class:
                                     // com.android.settings.datausage.DataSaverBackend$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            DataSaverBackend.AnonymousClass1 anonymousClass1 =
                                    DataSaverBackend.AnonymousClass1.this;
                            int i3 = i;
                            int i4 = i2;
                            DataSaverBackend dataSaverBackend = DataSaverBackend.this;
                            for (int i5 : dataSaverBackend.mPolicyManager.getUidsWithPolicy(4)) {
                                dataSaverBackend.mUidPolicies.put(i5, 4);
                            }
                            for (int i6 : dataSaverBackend.mPolicyManager.getUidsWithPolicy(1)) {
                                dataSaverBackend.mUidPolicies.put(i6, 1);
                            }
                            int i7 = dataSaverBackend.mUidPolicies.get(i3, 0);
                            if (i4 == 0) {
                                dataSaverBackend.mUidPolicies.delete(i3);
                            } else {
                                dataSaverBackend.mUidPolicies.put(i3, i4);
                            }
                            boolean z = i7 == 4;
                            boolean z2 = i7 == 1;
                            boolean z3 = i4 == 4;
                            boolean z4 = i4 == 1;
                            if (z != z3) {
                                for (int i8 = 0; i8 < dataSaverBackend.mListeners.size(); i8++) {
                                    ((DataSaverBackend.Listener)
                                                    dataSaverBackend.mListeners.get(i8))
                                            .onAllowlistStatusChanged(i3, z3);
                                }
                            }
                            if (z2 != z4) {
                                for (int i9 = 0; i9 < dataSaverBackend.mListeners.size(); i9++) {
                                    ((DataSaverBackend.Listener)
                                                    dataSaverBackend.mListeners.get(i9))
                                            .onDenylistStatusChanged(i3, z4);
                                }
                            }
                        }
                    });
        }
    }

    public DataSaverBackend(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mPolicyManager = NetworkPolicyManager.from(applicationContext);
        this.mDynamicDenylistManager = DynamicDenylistManager.getInstance(applicationContext);
    }

    public final void addListener(Listener listener) {
        this.mListeners.add(listener);
        if (this.mListeners.size() == 1) {
            this.mPolicyManager.registerListener(this.mPolicyListener);
        }
        listener.onDataSaverChanged(this.mPolicyManager.getRestrictBackground());
    }

    public final void remListener(Listener listener) {
        this.mListeners.remove(listener);
        if (this.mListeners.size() == 0) {
            this.mPolicyManager.unregisterListener(this.mPolicyListener);
        }
    }

    public final void setIsAllowlisted(int i, String str, boolean z) {
        int i2 = z ? 4 : 0;
        this.mDynamicDenylistManager.setUidPolicyLocked(i, i2);
        this.mUidPolicies.put(i, i2);
        if (z) {
            this.mMetricsFeatureProvider.action(this.mContext, 395, str);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {
        void onDataSaverChanged(boolean z);

        default void onAllowlistStatusChanged(int i, boolean z) {}

        default void onDenylistStatusChanged(int i, boolean z) {}
    }
}
