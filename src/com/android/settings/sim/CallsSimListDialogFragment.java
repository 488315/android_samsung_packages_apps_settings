package com.android.settings.sim;

import android.content.Context;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CallsSimListDialogFragment extends SimListDialogFragment {
    @Override // com.android.settings.sim.SimListDialogFragment
    public final List getCurrentSubscriptions() {
        SubscriptionInfo activeSubscriptionInfo;
        Context context = getContext();
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        TelecomManager telecomManager =
                (TelecomManager) context.getSystemService(TelecomManager.class);
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        List<PhoneAccountHandle> callCapablePhoneAccounts =
                telecomManager.getCallCapablePhoneAccounts();
        ArrayList arrayList = new ArrayList();
        if (callCapablePhoneAccounts == null) {
            return arrayList;
        }
        Iterator<PhoneAccountHandle> it = callCapablePhoneAccounts.iterator();
        while (it.hasNext()) {
            int subscriptionId = telephonyManager.getSubscriptionId(it.next());
            if (subscriptionId != -1
                    && (activeSubscriptionInfo =
                                    subscriptionManager.getActiveSubscriptionInfo(subscriptionId))
                            != null
                    && (!activeSubscriptionInfo.isEmbedded()
                            || (activeSubscriptionInfo.getProfileClass() != 1
                                    && !activeSubscriptionInfo.isOnlyNonTerrestrialNetwork()))) {
                arrayList.add(subscriptionManager.getActiveSubscriptionInfo(subscriptionId));
            }
        }
        return arrayList;
    }

    @Override // com.android.settings.sim.SimListDialogFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1708;
    }
}
