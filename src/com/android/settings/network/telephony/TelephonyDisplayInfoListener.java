package com.android.settings.network.telephony;

import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.util.ArraySet;

import com.android.settings.network.SubscriptionsPreferenceController;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.AnonymousClass3.AnonymousClass1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TelephonyDisplayInfoListener {
    public static final TelephonyDisplayInfo mDefaultTelephonyDisplayInfo =
            new TelephonyDisplayInfo(0, 0);
    public TelephonyManager mBaseTelephonyManager;
    public SubscriptionsPreferenceController mCallback;
    public Map mDisplayInfos;
    public Map mListeners;

    public final void updateSubscriptionIds(Set set) {
        ArraySet arraySet = new ArraySet(((HashMap) this.mListeners).keySet());
        Sets.AnonymousClass3.AnonymousClass1 anonymousClass1 =
                Sets.difference(arraySet, set).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            Integer num = (Integer) anonymousClass1.next();
            this.mBaseTelephonyManager
                    .createForSubscriptionId(num.intValue())
                    .listen((PhoneStateListener) ((HashMap) this.mListeners).get(num), 0);
            ((HashMap) this.mListeners).remove(num);
            ((HashMap) this.mDisplayInfos).remove(num);
        }
        Sets.AnonymousClass3.AnonymousClass1 anonymousClass12 =
                Sets.difference(set, arraySet).new AnonymousClass1();
        while (anonymousClass12.hasNext()) {
            Integer num2 = (Integer) anonymousClass12.next();
            final int intValue = num2.intValue();
            PhoneStateListener phoneStateListener =
                    new PhoneStateListener() { // from class:
                                               // com.android.settings.network.telephony.TelephonyDisplayInfoListener.1
                        @Override // android.telephony.PhoneStateListener
                        public final void onDisplayInfoChanged(
                                TelephonyDisplayInfo telephonyDisplayInfo) {
                            ((HashMap) TelephonyDisplayInfoListener.this.mDisplayInfos)
                                    .put(Integer.valueOf(intValue), telephonyDisplayInfo);
                            SubscriptionsPreferenceController subscriptionsPreferenceController =
                                    TelephonyDisplayInfoListener.this.mCallback;
                            int i = intValue;
                            subscriptionsPreferenceController.mSubsPrefCtrlInjector.getClass();
                            if (i != SubscriptionManager.getDefaultDataSubscriptionId()) {
                                return;
                            }
                            subscriptionsPreferenceController.mTelephonyDisplayInfo =
                                    telephonyDisplayInfo;
                            subscriptionsPreferenceController.update$2$1();
                        }
                    };
            ((HashMap) this.mDisplayInfos).put(num2, mDefaultTelephonyDisplayInfo);
            ((HashMap) this.mListeners).put(num2, phoneStateListener);
            this.mBaseTelephonyManager
                    .createForSubscriptionId(intValue)
                    .listen((PhoneStateListener) ((HashMap) this.mListeners).get(num2), 1048576);
        }
    }
}
