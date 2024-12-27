package com.android.settings.network.telephony;

import android.content.Context;
import android.telephony.SignalStrength;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.ArraySet;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.AnonymousClass3.AnonymousClass1;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SignalStrengthListener {
    public final TelephonyManager mBaseTelephonyManager;
    public final Callback mCallback;
    public final Context mContext;
    Map<Integer, SignalStrengthTelephonyCallback> mTelephonyCallbacks = new TreeMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void onSignalStrengthChanged();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class SignalStrengthTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.SignalStrengthsListener {
        public SignalStrengthTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
        public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
            SignalStrengthListener.this.mCallback.onSignalStrengthChanged();
        }
    }

    public SignalStrengthListener(Context context, Callback callback) {
        this.mBaseTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mCallback = callback;
        this.mContext = context;
    }

    public final void pause() {
        for (Integer num : this.mTelephonyCallbacks.keySet()) {
            this.mBaseTelephonyManager
                    .createForSubscriptionId(num.intValue())
                    .unregisterTelephonyCallback(this.mTelephonyCallbacks.get(num));
        }
    }

    public final void resume() {
        for (Integer num : this.mTelephonyCallbacks.keySet()) {
            this.mBaseTelephonyManager
                    .createForSubscriptionId(num.intValue())
                    .registerTelephonyCallback(
                            this.mContext.getMainExecutor(), this.mTelephonyCallbacks.get(num));
        }
    }

    public final void updateSubscriptionIds(Set set) {
        ArraySet arraySet = new ArraySet(this.mTelephonyCallbacks.keySet());
        Sets.AnonymousClass3.AnonymousClass1 anonymousClass1 =
                Sets.difference(arraySet, set).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            Integer num = (Integer) anonymousClass1.next();
            this.mBaseTelephonyManager
                    .createForSubscriptionId(num.intValue())
                    .unregisterTelephonyCallback(this.mTelephonyCallbacks.get(num));
            this.mTelephonyCallbacks.remove(num);
        }
        Sets.AnonymousClass3.AnonymousClass1 anonymousClass12 =
                Sets.difference(set, arraySet).new AnonymousClass1();
        while (anonymousClass12.hasNext()) {
            Integer num2 = (Integer) anonymousClass12.next();
            int intValue = num2.intValue();
            this.mTelephonyCallbacks.put(num2, new SignalStrengthTelephonyCallback());
            this.mBaseTelephonyManager
                    .createForSubscriptionId(intValue)
                    .registerTelephonyCallback(
                            this.mContext.getMainExecutor(), this.mTelephonyCallbacks.get(num2));
        }
    }
}
