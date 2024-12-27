package com.android.settings.network.telephony;

import android.content.Context;
import android.telephony.TelephonyManager;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkSelectRepository {
    public final TelephonyManager telephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NetworkRegistrationAndForbiddenInfo {
        public final List forbiddenPlmns;
        public final List networkList;

        public NetworkRegistrationAndForbiddenInfo(List list, List forbiddenPlmns) {
            Intrinsics.checkNotNullParameter(forbiddenPlmns, "forbiddenPlmns");
            this.networkList = list;
            this.forbiddenPlmns = forbiddenPlmns;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NetworkRegistrationAndForbiddenInfo)) {
                return false;
            }
            NetworkRegistrationAndForbiddenInfo networkRegistrationAndForbiddenInfo =
                    (NetworkRegistrationAndForbiddenInfo) obj;
            return Intrinsics.areEqual(
                            this.networkList, networkRegistrationAndForbiddenInfo.networkList)
                    && Intrinsics.areEqual(
                            this.forbiddenPlmns,
                            networkRegistrationAndForbiddenInfo.forbiddenPlmns);
        }

        public final int hashCode() {
            return this.forbiddenPlmns.hashCode() + (this.networkList.hashCode() * 31);
        }

        public final String toString() {
            return "NetworkRegistrationAndForbiddenInfo(networkList="
                    + this.networkList
                    + ", forbiddenPlmns="
                    + this.forbiddenPlmns
                    + ")";
        }
    }

    public NetworkSelectRepository(Context context, int i) {
        Object systemService = context.getSystemService((Class<Object>) TelephonyManager.class);
        Intrinsics.checkNotNull(systemService);
        this.telephonyManager = ((TelephonyManager) systemService).createForSubscriptionId(i);
    }
}
