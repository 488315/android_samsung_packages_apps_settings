package com.android.settings.network;

import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;

import com.android.settings.SidecarFragment;
import com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2;
import com.android.settings.network.telephony.EuiccOperationSidecar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SwitchToRemovableSlotSidecar extends EuiccOperationSidecar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mPhysicalSlotId;
    public SubscriptionInfo mRemovedSubInfo;
    public SwitchToEuiccSubscriptionSidecar mSwitchToSubscriptionSidecar;

    @Override // com.android.settings.network.telephony.EuiccOperationSidecar
    public final String getReceiverAction() {
        return "disable_subscription_and_switch_slot_sidecar";
    }

    @Override // com.android.settings.network.telephony.EuiccOperationSidecar,
              // com.android.settings.SidecarFragment, android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSwitchToSubscriptionSidecar =
                SwitchToEuiccSubscriptionSidecar.get(getChildFragmentManager());
    }

    @Override // com.android.settings.network.telephony.EuiccOperationSidecar, android.app.Fragment
    public final void onPause() {
        this.mSwitchToSubscriptionSidecar.removeListener(this);
        super.onPause();
    }

    @Override // com.android.settings.network.telephony.EuiccOperationSidecar, android.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSwitchToSubscriptionSidecar.addListener(this);
    }

    @Override // com.android.settings.network.telephony.EuiccOperationSidecar,
              // com.android.settings.SidecarFragment.Listener
    public final void onStateChange(SidecarFragment sidecarFragment) {
        SwitchToEuiccSubscriptionSidecar switchToEuiccSubscriptionSidecar =
                this.mSwitchToSubscriptionSidecar;
        if (sidecarFragment != switchToEuiccSubscriptionSidecar) {
            SwitchSlotSidecar switchSlotSidecar = this.mSwitchSlotSidecar;
            if (sidecarFragment != switchSlotSidecar) {
                Log.wtf(
                        "SwitchRemovableSidecar",
                        "Received state change from a sidecar not expected.");
                return;
            }
            int i = switchSlotSidecar.mState;
            if (i == 2) {
                switchSlotSidecar.setState(0, 0);
                Log.i("SwitchRemovableSidecar", "Successfully switched to removable slot.");
                setState(2, 0);
                return;
            } else {
                if (i != 3) {
                    return;
                }
                switchSlotSidecar.setState(0, 0);
                Log.i("SwitchRemovableSidecar", "Failed to switch to removable slot.");
                setState(3, 0);
                return;
            }
        }
        int i2 = switchToEuiccSubscriptionSidecar.mState;
        if (i2 != 2) {
            if (i2 != 3) {
                return;
            }
            switchToEuiccSubscriptionSidecar.setState(0, 0);
            Log.i("SwitchRemovableSidecar", "Failed to disable the active eSIM profile.");
            setState(3, 0);
            return;
        }
        switchToEuiccSubscriptionSidecar.setState(0, 0);
        Log.i(
                "SwitchRemovableSidecar",
                "Successfully disabled eSIM profile. Start to switch to Removable slot.");
        SwitchSlotSidecar switchSlotSidecar2 = this.mSwitchSlotSidecar;
        int i3 = this.mPhysicalSlotId;
        SubscriptionInfo subscriptionInfo = this.mRemovedSubInfo;
        switchSlotSidecar2.getClass();
        SwitchSlotSidecar.Param param = new SwitchSlotSidecar.Param();
        param.command = 0;
        param.slotId = i3;
        param.removedSubInfo = subscriptionInfo;
        param.port = 0;
        switchSlotSidecar2.run(param);
    }

    public final void run(SubscriptionInfo subscriptionInfo) {
        SubscriptionInfo subscriptionInfo2;
        this.mPhysicalSlotId = -1;
        this.mRemovedSubInfo = subscriptionInfo;
        SubscriptionManager createForAllUserProfiles =
                ((SubscriptionManager) getContext().getSystemService(SubscriptionManager.class))
                        .createForAllUserProfiles();
        if (!this.mTelephonyManager.isMultiSimEnabled()
                && SubscriptionUtil.getActiveSubscriptions(createForAllUserProfiles).stream()
                        .anyMatch(new EidStatus$$ExternalSyntheticLambda2(1))) {
            Log.i(
                    "SwitchRemovableSidecar",
                    "There is an active eSIM profile. Disable the profile first.");
            this.mSwitchToSubscriptionSidecar.run(-1, 0, null);
            return;
        }
        if (this.mTelephonyManager.isMultiSimEnabled()
                && (subscriptionInfo2 = this.mRemovedSubInfo) != null) {
            this.mSwitchToSubscriptionSidecar.run(-1, subscriptionInfo2.getPortIndex(), null);
            return;
        }
        Log.i("SwitchRemovableSidecar", "Start to switch to removable slot.");
        SwitchSlotSidecar switchSlotSidecar = this.mSwitchSlotSidecar;
        int i = this.mPhysicalSlotId;
        SubscriptionInfo subscriptionInfo3 = this.mRemovedSubInfo;
        switchSlotSidecar.getClass();
        SwitchSlotSidecar.Param param = new SwitchSlotSidecar.Param();
        param.command = 0;
        param.slotId = i;
        param.removedSubInfo = subscriptionInfo3;
        param.port = 0;
        switchSlotSidecar.run(param);
    }
}
