package com.android.settings.network;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MobileNetworkRepository$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MobileNetworkRepository$$ExternalSyntheticLambda5(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((MobileNetworkRepository) obj).onSubscriptionsChanged();
                break;
            default:
                MobileNetworkRepository.PhoneCallStateTelephonyCallback
                        phoneCallStateTelephonyCallback =
                                (MobileNetworkRepository.PhoneCallStateTelephonyCallback) obj;
                MobileNetworkRepository mobileNetworkRepository =
                        phoneCallStateTelephonyCallback.this$0;
                Context context = mobileNetworkRepository.mContext;
                int i2 = phoneCallStateTelephonyCallback.mSubId;
                mobileNetworkRepository.insertMobileNetworkInfo(
                        context,
                        i2,
                        mobileNetworkRepository.getTelephonyManagerBySubId(context, i2));
                break;
        }
    }
}