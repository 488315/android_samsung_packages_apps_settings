package com.android.settings.spa.network;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.spa.widget.preference.ListPreferenceOption;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrimarySimRepository {
    public final Context context;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PrimarySimInfo {
        public final List callsAndSmsList;
        public final List dataList;

        public PrimarySimInfo(List list, List list2) {
            this.callsAndSmsList = list;
            this.dataList = list2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PrimarySimInfo)) {
                return false;
            }
            PrimarySimInfo primarySimInfo = (PrimarySimInfo) obj;
            return Intrinsics.areEqual(this.callsAndSmsList, primarySimInfo.callsAndSmsList)
                    && Intrinsics.areEqual(this.dataList, primarySimInfo.dataList);
        }

        public final int hashCode() {
            return this.dataList.hashCode() + (this.callsAndSmsList.hashCode() * 31);
        }

        public final String toString() {
            return "PrimarySimInfo(callsAndSmsList="
                    + this.callsAndSmsList
                    + ", dataList="
                    + this.dataList
                    + ")";
        }
    }

    public PrimarySimRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final PrimarySimInfo getPrimarySimInfo(List selectableSubscriptionInfoList) {
        Intrinsics.checkNotNullParameter(
                selectableSubscriptionInfoList, "selectableSubscriptionInfoList");
        if (selectableSubscriptionInfoList.size() < 2) {
            Log.d("PrimarySimRepository", "Hide primary sim");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = selectableSubscriptionInfoList.iterator();
        while (it.hasNext()) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
            int subscriptionId = subscriptionInfo.getSubscriptionId();
            String valueOf = String.valueOf(subscriptionInfo.getDisplayName());
            String bidiFormattedPhoneNumber =
                    SubscriptionUtil.getBidiFormattedPhoneNumber(this.context, subscriptionInfo);
            if (bidiFormattedPhoneNumber == null) {
                bidiFormattedPhoneNumber = ApnSettings.MVNO_NONE;
            }
            ListPreferenceOption listPreferenceOption =
                    new ListPreferenceOption(subscriptionId, valueOf, bidiFormattedPhoneNumber);
            arrayList.add(listPreferenceOption);
            arrayList2.add(listPreferenceOption);
        }
        String string = this.context.getString(R.string.sim_calls_ask_first_prefs_title);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        arrayList.add(new ListPreferenceOption(-1, string));
        return new PrimarySimInfo(arrayList, arrayList2);
    }
}
