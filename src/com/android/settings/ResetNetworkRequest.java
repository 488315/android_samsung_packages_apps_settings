package com.android.settings;

import android.content.Context;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Looper;
import android.os.SystemClock;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.network.ResetNetworkOperationBuilder;
import com.android.settings.network.ResetNetworkOperationBuilder$$ExternalSyntheticLambda0;
import com.android.settings.network.ResetNetworkOperationBuilder$$ExternalSyntheticLambda7;
import com.android.settings.network.apn.PreferredApnRepository;

import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final class ResetNetworkRequest {
    protected static final String KEY_APN_SUBID = "resetApnSubId";
    protected static final String KEY_ESIM_PACKAGE = "resetEsimPackage";
    protected static final String KEY_RESET_OPTIONS = "resetNetworkOptions";
    protected static final String KEY_TELEPHONY_NET_POLICY_MANAGER_SUBID =
            "resetTelephonyNetPolicySubId";
    public String mResetEsimPackageName;
    public int mResetOptions;
    public int mResetTelephonyManager = -1;
    public int mResetApn = -1;
    public int mSubscriptionIdToResetIms = -1;

    public ResetNetworkRequest(int i) {
        this.mResetOptions = i;
    }

    public final ResetNetworkOperationBuilder toResetNetworkOperationBuilder(
            Context context, final Looper looper) {
        final ResetNetworkOperationBuilder resetNetworkOperationBuilder =
                new ResetNetworkOperationBuilder(context);
        int i = this.mResetOptions;
        if ((i & 1) != 0) {
            resetNetworkOperationBuilder.attachSystemServiceWork(
                    "connectivity", new ResetNetworkOperationBuilder$$ExternalSyntheticLambda7(3));
        }
        if ((i & 2) != 0) {
            resetNetworkOperationBuilder.attachSystemServiceWork(
                    "vpn_management",
                    new ResetNetworkOperationBuilder$$ExternalSyntheticLambda7(0));
        }
        if ((i & 4) != 0) {
            resetNetworkOperationBuilder.attachSystemServiceWork(
                    ImsProfile.PDN_WIFI,
                    new ResetNetworkOperationBuilder$$ExternalSyntheticLambda7(4));
        }
        if ((i & 8) != 0) {
            resetNetworkOperationBuilder.attachSystemServiceWork(
                    "wifip2p",
                    new Consumer() { // from class:
                                     // com.android.settings.network.ResetNetworkOperationBuilder$$ExternalSyntheticLambda11
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ResetNetworkOperationBuilder resetNetworkOperationBuilder2 =
                                    ResetNetworkOperationBuilder.this;
                            WifiP2pManager wifiP2pManager = (WifiP2pManager) obj;
                            WifiP2pManager.Channel initialize =
                                    wifiP2pManager.initialize(
                                            resetNetworkOperationBuilder2.mContext, looper, null);
                            if (initialize != null) {
                                wifiP2pManager.factoryReset(initialize, null);
                            }
                        }
                    });
        }
        String str = this.mResetEsimPackageName;
        if (str != null) {
            resetNetworkOperationBuilder.resetEsim(str, null);
        }
        int i2 = this.mResetTelephonyManager;
        if (i2 != -1) {
            resetNetworkOperationBuilder.resetTelephonyAndNetworkPolicyManager(i2);
        } else {
            resetNetworkOperationBuilder.resetNetworkDataUsesChina(i2);
        }
        if ((i & 16) != 0) {
            resetNetworkOperationBuilder.attachSystemServiceWork(
                    "bluetooth", new ResetNetworkOperationBuilder$$ExternalSyntheticLambda7(1));
        }
        final int i3 = this.mResetApn;
        if (i3 != -1) {
            ((ArrayList) resetNetworkOperationBuilder.mResetSequence)
                    .add(
                            new Runnable() { // from class:
                                             // com.android.settings.network.ResetNetworkOperationBuilder$$ExternalSyntheticLambda6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ResetNetworkOperationBuilder resetNetworkOperationBuilder2 =
                                            ResetNetworkOperationBuilder.this;
                                    int i4 = i3;
                                    resetNetworkOperationBuilder2.getClass();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    Uri uri = PreferredApnRepository.RestorePreferredApnUri;
                                    if (SubscriptionManager.isUsableSubscriptionId(i4)) {
                                        uri =
                                                Uri.withAppendedPath(
                                                        uri, "subId/" + String.valueOf(i4));
                                    }
                                    resetNetworkOperationBuilder2
                                            .mContext
                                            .getContentResolver()
                                            .delete(uri, null, null);
                                    Log.i(
                                            "ResetNetworkOpBuilder",
                                            "Reset "
                                                    + uri
                                                    + ", takes "
                                                    + (SystemClock.elapsedRealtime()
                                                            - elapsedRealtime)
                                                    + " ms");
                                }
                            });
        }
        if ((i & 32) != 0) {
            final int i4 = this.mSubscriptionIdToResetIms;
            resetNetworkOperationBuilder.attachSystemServiceWork(
                    "phone",
                    new Consumer() { // from class:
                                     // com.android.settings.network.ResetNetworkOperationBuilder$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i5 = i4;
                            TelephonyManager telephonyManager = (TelephonyManager) obj;
                            if (i5 == -1) {
                                return;
                            }
                            if (i5 != Integer.MAX_VALUE) {
                                int slotIndex = SubscriptionManager.getSlotIndex(i5);
                                telephonyManager.resetIms(slotIndex);
                                Log.i(
                                        "ResetNetworkOpBuilder",
                                        "IMS was reset for slot " + slotIndex);
                                return;
                            }
                            for (int i6 = 0; i6 < telephonyManager.getActiveModemCount(); i6++) {
                                telephonyManager.resetIms(i6);
                                Log.i("ResetNetworkOpBuilder", "IMS was reset for slot " + i6);
                            }
                        }
                    });
        }
        if ((i & 128) != 0) {
            ((ArrayList) resetNetworkOperationBuilder.mResetSequence)
                    .add(
                            new ResetNetworkOperationBuilder$$ExternalSyntheticLambda0(
                                    resetNetworkOperationBuilder, 0));
        }
        if ((i & 64) != 0) {
            ((ArrayList) resetNetworkOperationBuilder.mResetSequence)
                    .add(
                            new ResetNetworkOperationBuilder$$ExternalSyntheticLambda0(
                                    resetNetworkOperationBuilder, 2));
        }
        return resetNetworkOperationBuilder;
    }
}
