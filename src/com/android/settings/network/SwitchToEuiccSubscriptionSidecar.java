package com.android.settings.network;

import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccSlotInfo;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.SidecarFragment;
import com.android.settings.network.telephony.EuiccOperationSidecar;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SwitchToEuiccSubscriptionSidecar extends EuiccOperationSidecar {
    public List mActiveSubInfos;
    public PendingIntent mCallbackIntent;
    public boolean mIsDuringSimSlotMapping;
    public int mPort;
    public SubscriptionInfo mRemovedSubInfo;
    public int mSubId;

    public static SwitchToEuiccSubscriptionSidecar get(FragmentManager fragmentManager) {
        return (SwitchToEuiccSubscriptionSidecar)
                SidecarFragment.get(
                        fragmentManager,
                        "SwitchToEuiccSidecar",
                        SwitchToEuiccSubscriptionSidecar.class);
    }

    @Override // com.android.settings.network.telephony.EuiccOperationSidecar
    public final String getReceiverAction() {
        return "com.android.settings.network.SWITCH_TO_SUBSCRIPTION";
    }

    public final int getTargetSlot() {
        Context context = getContext();
        int i = this.mSubId;
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        List<UiccCardInfo> uiccCardsInfo = telephonyManager.getUiccCardsInfo();
        UiccSlotInfo[] uiccSlotsInfo = telephonyManager.getUiccSlotsInfo();
        final ImmutableList of =
                uiccSlotsInfo == null ? ImmutableList.of() : ImmutableList.copyOf(uiccSlotsInfo);
        SubscriptionInfo subById =
                SubscriptionUtil.getSubById(
                        ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                                .createForAllUserProfiles(),
                        i);
        if (subById != null && subById.isEmbedded()) {
            for (UiccCardInfo uiccCardInfo : uiccCardsInfo) {
                if (uiccCardInfo.getCardId() == subById.getCardId()
                        && uiccCardInfo.getCardId() > -1
                        && uiccCardInfo.isEuicc()
                        && uiccCardInfo.isRemovable()) {
                    Log.d("UiccSlotUtil", "getEsimSlotId: This subInfo is a removable esim.");
                    return uiccCardInfo.getPhysicalSlotIndex();
                }
            }
        }
        int orElse =
                IntStream.range(0, of.size())
                        .filter(
                                new IntPredicate() { // from class:
                                                     // com.android.settings.network.UiccSlotUtil$$ExternalSyntheticLambda0
                                    @Override // java.util.function.IntPredicate
                                    public final boolean test(int i2) {
                                        UiccSlotInfo uiccSlotInfo =
                                                (UiccSlotInfo) ImmutableList.this.get(i2);
                                        if (uiccSlotInfo == null) {
                                            return false;
                                        }
                                        return uiccSlotInfo.getIsEuicc();
                                    }
                                })
                        .findFirst()
                        .orElse(-1);
        MainClearConfirm$$ExternalSyntheticOutline0.m(orElse, "firstEsimSlot: ", "UiccSlotUtil");
        return orElse;
    }

    @Override // com.android.settings.network.telephony.EuiccOperationSidecar
    public final void onActionReceived() {
        if (this.mResultCode != 0 || !this.mIsDuringSimSlotMapping) {
            super.onActionReceived();
            return;
        }
        this.mIsDuringSimSlotMapping = false;
        SwitchSlotSidecar switchSlotSidecar = this.mSwitchSlotSidecar;
        int targetSlot = getTargetSlot();
        int i = this.mPort;
        SubscriptionInfo subscriptionInfo = this.mRemovedSubInfo;
        switchSlotSidecar.getClass();
        SwitchSlotSidecar.Param param = new SwitchSlotSidecar.Param();
        param.command = 1;
        param.slotId = targetSlot;
        param.removedSubInfo = subscriptionInfo;
        param.port = i;
        switchSlotSidecar.run(param);
    }

    @Override // com.android.settings.network.telephony.EuiccOperationSidecar,
              // com.android.settings.SidecarFragment.Listener
    public final void onStateChange(SidecarFragment sidecarFragment) {
        SwitchSlotSidecar switchSlotSidecar = this.mSwitchSlotSidecar;
        if (sidecarFragment != switchSlotSidecar) {
            Log.wtf("SwitchToEuiccSidecar", "Received state change from a sidecar not expected.");
            return;
        }
        int i = switchSlotSidecar.mState;
        if (i == 2) {
            switchSlotSidecar.setState(0, 0);
            Log.i(
                    "SwitchToEuiccSidecar",
                    "Successfully SimSlotMapping. Start to enable/disable esim");
            this.mEuiccManager.switchToSubscription(this.mSubId, this.mPort, this.mCallbackIntent);
        } else {
            if (i != 3) {
                return;
            }
            switchSlotSidecar.setState(0, 0);
            Log.i("SwitchToEuiccSidecar", "Failed to set SimSlotMapping");
            setState(3, 0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x018b, code lost:

       if (r3.stream().anyMatch(new com.android.settings.network.SwitchToEuiccSubscriptionSidecar$$ExternalSyntheticLambda0(r8, r5)) != false) goto L59;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run(int r7, int r8, android.telephony.SubscriptionInfo r9) {
        /*
            Method dump skipped, instructions count: 438
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.SwitchToEuiccSubscriptionSidecar.run(int, int,"
                    + " android.telephony.SubscriptionInfo):void");
    }
}
