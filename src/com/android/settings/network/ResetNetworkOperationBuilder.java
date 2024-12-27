package com.android.settings.network;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.NetworkPolicyManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.ResetNetworkConfirm$ResetNetworkTask$$ExternalSyntheticLambda0;

import com.samsung.android.settings.Rune;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ResetNetworkOperationBuilder {

    @VisibleForTesting static final String METHOD_RESTART_PHONE_PROCESS = "restartPhoneProcess";

    @VisibleForTesting static final String METHOD_RESTART_RILD = "restartRild";
    public final Context mContext;
    public final List mResetSequence = new ArrayList();

    public ResetNetworkOperationBuilder(Context context) {
        this.mContext = context;
    }

    public final void attachSystemServiceWork(String str, Consumer consumer) {
        Object systemService = this.mContext.getSystemService(str);
        if (systemService == null) {
            return;
        }
        ((ArrayList) this.mResetSequence)
                .add(
                        new ResetNetworkOperationBuilder$$ExternalSyntheticLambda1(
                                consumer, systemService, str));
    }

    @VisibleForTesting
    public ContentProviderClient getUnstableTelephonyContentProviderClient() {
        return this.mContext
                .getContentResolver()
                .acquireUnstableContentProviderClient(
                        this.mContext
                                .getResources()
                                .getString(
                                        R.string.reset_telephony_stack_content_provider_authority));
    }

    public final void resetEsim(
            String str,
            ResetNetworkConfirm$ResetNetworkTask$$ExternalSyntheticLambda0
                    resetNetworkConfirm$ResetNetworkTask$$ExternalSyntheticLambda0) {
        ((ArrayList) this.mResetSequence)
                .add(
                        new ResetNetworkOperationBuilder$$ExternalSyntheticLambda1(
                                this,
                                str,
                                resetNetworkConfirm$ResetNetworkTask$$ExternalSyntheticLambda0));
    }

    public final void resetNetworkDataUsesChina(int i) {
        if (Rune.SUPPORT_SMARTMANAGER_CN) {
            Bundle bundle = new Bundle();
            bundle.putInt("subId", i);
            try {
                this.mContext
                        .getContentResolver()
                        .call(
                                "com.samsung.android.sm.dcapi",
                                "resetDataUsageChnBySubId",
                                (String) null,
                                bundle);
                Log.d("ResetNetworkOpBuilder", "reset data usage success");
            } catch (Exception e) {
                Log.d("ResetNetworkOpBuilder", "reset data usage chn error: " + e.toString());
            }
        }
    }

    public final void resetTelephonyAndNetworkPolicyManager(final int i) {
        final AtomicReference atomicReference = new AtomicReference();
        attachSystemServiceWork(
                "phone",
                new Consumer() { // from class:
                                 // com.android.settings.network.ResetNetworkOperationBuilder$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i2 = i;
                        AtomicReference atomicReference2 = atomicReference;
                        TelephonyManager createForSubscriptionId =
                                ((TelephonyManager) obj).createForSubscriptionId(i2);
                        atomicReference2.set(createForSubscriptionId.getSubscriberId());
                        createForSubscriptionId.resetSettings();
                    }
                });
        attachSystemServiceWork(
                "netpolicy",
                new Consumer() { // from class:
                                 // com.android.settings.network.ResetNetworkOperationBuilder$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ResetNetworkOperationBuilder resetNetworkOperationBuilder =
                                ResetNetworkOperationBuilder.this;
                        int i2 = i;
                        AtomicReference atomicReference2 = atomicReference;
                        resetNetworkOperationBuilder.resetNetworkDataUsesChina(i2);
                        ((NetworkPolicyManager) obj).factoryReset((String) atomicReference2.get());
                    }
                });
    }
}
