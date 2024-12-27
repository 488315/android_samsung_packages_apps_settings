package com.android.settings.network.telephony;

import android.app.PendingIntent;
import android.telephony.SubscriptionInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeleteEuiccSubscriptionSidecar extends EuiccOperationSidecar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public List mSubscriptions;

    public final void deleteSubscription() {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) this.mSubscriptions.remove(0);
        PendingIntent createCallbackIntent = createCallbackIntent();
        Log.i(
                "DeleteEuiccSubscriptionSidecar",
                "Deleting subscription ID: " + subscriptionInfo.getSubscriptionId());
        this.mEuiccManager.deleteSubscription(
                subscriptionInfo.getSubscriptionId(), createCallbackIntent);
    }

    @Override // com.android.settings.network.telephony.EuiccOperationSidecar
    public final String getReceiverAction() {
        return "com.android.settings.network.DELETE_SUBSCRIPTION";
    }

    @Override // com.android.settings.network.telephony.EuiccOperationSidecar
    public final void onActionReceived() {
        if (this.mResultCode != 0 || ((ArrayList) this.mSubscriptions).isEmpty()) {
            super.onActionReceived();
        } else {
            deleteSubscription();
        }
    }
}
