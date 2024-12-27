package com.android.settings.network.telephony;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccSlotInfo;
import android.text.TextUtils;
import android.util.Log;

import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.SidecarFragment;
import com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2;
import com.android.settings.network.EnableMultiSimSidecar;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.SwitchToEuiccSubscriptionSidecar;
import com.android.settings.network.SwitchToRemovableSlotSidecar;
import com.android.settings.network.UiccSlotUtil;
import com.android.settings.sim.SimActivationNotifier;

import com.google.common.collect.ImmutableList;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleSubscriptionDialogActivity extends SubscriptionActionDialogActivity
        implements SidecarFragment.Listener, ConfirmDialogFragment.OnConfirmListener {

    @VisibleForTesting public static final String ARG_enable = "enable";
    public boolean isRtlMode;
    public List mActiveSubInfos;
    public boolean mEnable;
    public EnableMultiSimSidecar mEnableMultiSimSidecar;
    public boolean mIsEsimOperation;
    public SubscriptionInfo mSubInfo;
    public SwitchToEuiccSubscriptionSidecar mSwitchToEuiccSubscriptionSidecar;
    public SwitchToRemovableSlotSidecar mSwitchToRemovableSlotSidecar;
    public TelephonyManager mTelMgr;

    public static Intent getIntent(Context context, int i, boolean z) {
        Intent intent = new Intent(context, (Class<?>) ToggleSubscriptionDialogActivity.class);
        intent.putExtra("sub_id", i);
        intent.putExtra(ARG_enable, z);
        intent.setFlags(268435456);
        return intent;
    }

    public final void handleTogglePsimAction() {
        SubscriptionInfo subscriptionInfo;
        if (!this.mSubscriptionManager.canDisablePhysicalSubscription()
                || (subscriptionInfo = this.mSubInfo) == null) {
            Log.i(
                    "ToggleSubscriptionDialogActivity",
                    "The device does not support toggling pSIM. It is enough to just enable the"
                        + " removable slot.");
        } else {
            this.mSubscriptionManager.setUiccApplicationsEnabled(
                    subscriptionInfo.getSubscriptionId(), this.mEnable);
            finish();
        }
    }

    public final boolean isMultipleEnabledProfilesSupported$1() {
        List<UiccCardInfo> uiccCardsInfo = this.mTelMgr.getUiccCardsInfo();
        if (uiccCardsInfo != null) {
            return uiccCardsInfo.stream()
                    .anyMatch(new ToggleSubscriptionDialogActivity$$ExternalSyntheticLambda0(0));
        }
        Log.w("ToggleSubscriptionDialogActivity", "UICC cards info list is empty.");
        return false;
    }

    @Override // com.android.settings.network.telephony.ConfirmDialogFragment.OnConfirmListener
    public final void onConfirm(int i, int i2, boolean z) {
        List list;
        if (!z && i != 3 && i != 4) {
            finish();
            return;
        }
        SubscriptionInfo subscriptionInfo = null;
        if (i == 1) {
            if (!this.mIsEsimOperation) {
                Log.i("ToggleSubscriptionDialogActivity", "Disabling the pSIM profile.");
                handleTogglePsimAction();
                return;
            } else {
                Log.i("ToggleSubscriptionDialogActivity", "Disabling the eSIM profile.");
                showProgressDialog(
                        getString(R.string.privileged_action_disable_sub_dialog_progress), false);
                SubscriptionInfo subscriptionInfo2 = this.mSubInfo;
                this.mSwitchToEuiccSubscriptionSidecar.run(
                        -1, subscriptionInfo2 != null ? subscriptionInfo2.getPortIndex() : 0, null);
                return;
            }
        }
        if (i != 2) {
            if (i == 3) {
                if (!z) {
                    Log.i(
                            "ToggleSubscriptionDialogActivity",
                            "User cancel the dialog to enable DSDS.");
                    showEnableSimConfirmDialog();
                    return;
                } else if (this.mTelMgr.doesSwitchMultiSimConfigTriggerReboot()) {
                    Log.i(
                            "ToggleSubscriptionDialogActivity",
                            "Device does not support reboot free DSDS.");
                    ConfirmDialogFragment.show(
                            this,
                            4,
                            getString(R.string.sim_action_restart_title),
                            getString(R.string.sim_action_enable_dsds_text),
                            getString(R.string.sim_action_reboot),
                            getString(R.string.sim_action_cancel));
                    return;
                } else {
                    Log.i("ToggleSubscriptionDialogActivity", "Enabling DSDS without rebooting.");
                    showProgressDialog(
                            getString(R.string.sim_action_enabling_sim_without_carrier_name),
                            false);
                    this.mEnableMultiSimSidecar.run$1();
                    return;
                }
            }
            if (i == 4) {
                if (!z) {
                    Log.i(
                            "ToggleSubscriptionDialogActivity",
                            "User cancel the dialog to reboot to enable DSDS.");
                    showEnableSimConfirmDialog();
                    return;
                } else {
                    Log.i(
                            "ToggleSubscriptionDialogActivity",
                            "User confirmed reboot to enable DSDS.");
                    SimActivationNotifier.setShowSimSettingsNotification(this, true);
                    this.mTelMgr.switchMultiSimConfig(2);
                    return;
                }
            }
            if (i != 5) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(
                                i,
                                "Unrecognized confirmation dialog tag: ",
                                "ToggleSubscriptionDialogActivity");
                return;
            } else if (i2 != -1 && (list = this.mActiveSubInfos) != null) {
                subscriptionInfo = (SubscriptionInfo) list.get(i2);
            }
        }
        Log.i("ToggleSubscriptionDialogActivity", "User confirmed to enable the subscription.");
        showProgressDialog(
                getString(
                        R.string.sim_action_switch_sub_dialog_progress,
                        new Object[] {
                            SubscriptionUtil.getUniqueSubscriptionDisplayName(this, this.mSubInfo)
                        }),
                subscriptionInfo != null);
        if (this.mIsEsimOperation) {
            this.mSwitchToEuiccSubscriptionSidecar.run(
                    this.mSubInfo.getSubscriptionId(), -1, subscriptionInfo);
        } else {
            this.mSwitchToRemovableSlotSidecar.run(subscriptionInfo);
        }
    }

    @Override // com.android.settings.network.telephony.SubscriptionActionDialogActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        boolean isRemovableSimEnabled;
        super.onCreate(bundle);
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra("sub_id", -1);
        this.mTelMgr = (TelephonyManager) getSystemService(TelephonyManager.class);
        if (!((UserManager) getSystemService(UserManager.class)).isAdminUser()) {
            Log.e(
                    "ToggleSubscriptionDialogActivity",
                    "It is not the admin user. Unable to toggle subscription.");
            finish();
            return;
        }
        if (!SubscriptionManager.isUsableSubscriptionId(intExtra)) {
            Log.e("ToggleSubscriptionDialogActivity", "The subscription id is not usable.");
            finish();
            return;
        }
        this.mActiveSubInfos = SubscriptionUtil.getActiveSubscriptions(this.mSubscriptionManager);
        SubscriptionInfo subById = SubscriptionUtil.getSubById(this.mSubscriptionManager, intExtra);
        this.mSubInfo = subById;
        boolean z = false;
        this.mIsEsimOperation = subById != null && subById.isEmbedded();
        this.mSwitchToEuiccSubscriptionSidecar =
                SwitchToEuiccSubscriptionSidecar.get(getFragmentManager());
        FragmentManager fragmentManager = getFragmentManager();
        int i = SwitchToRemovableSlotSidecar.$r8$clinit;
        this.mSwitchToRemovableSlotSidecar =
                (SwitchToRemovableSlotSidecar)
                        SidecarFragment.get(
                                fragmentManager,
                                "SwitchRemovableSidecar",
                                SwitchToRemovableSlotSidecar.class);
        FragmentManager fragmentManager2 = getFragmentManager();
        int i2 = EnableMultiSimSidecar.$r8$clinit;
        this.mEnableMultiSimSidecar =
                (EnableMultiSimSidecar)
                        SidecarFragment.get(
                                fragmentManager2,
                                "EnableMultiSimSidecar",
                                EnableMultiSimSidecar.class);
        this.mEnable = intent.getBooleanExtra(ARG_enable, true);
        this.isRtlMode = getResources().getConfiguration().getLayoutDirection() == 1;
        Log.i(
                "ToggleSubscriptionDialogActivity",
                "isMultipleEnabledProfilesSupported():" + isMultipleEnabledProfilesSupported$1());
        if (bundle == null) {
            if (!this.mEnable) {
                CharSequence uniqueSubscriptionDisplayName =
                        SubscriptionUtil.getUniqueSubscriptionDisplayName(this, this.mSubInfo);
                ConfirmDialogFragment.show(
                        this,
                        1,
                        (this.mSubInfo == null || TextUtils.isEmpty(uniqueSubscriptionDisplayName))
                                ? getString(
                                        R.string
                                                .privileged_action_disable_sub_dialog_title_without_carrier)
                                : getString(
                                        R.string.privileged_action_disable_sub_dialog_title,
                                        new Object[] {uniqueSubscriptionDisplayName}),
                        null,
                        getString(R.string.sim_action_turn_off),
                        getString(R.string.sim_action_cancel));
                return;
            }
            Log.d("ToggleSubscriptionDialogActivity", "Handle subscription enabling.");
            if (this.mTelMgr.isMultiSimEnabled()) {
                Log.d(
                        "ToggleSubscriptionDialogActivity",
                        "DSDS is already enabled. Condition not satisfied.");
            } else {
                if (this.mTelMgr.isMultiSimSupported() == 0) {
                    boolean z2 =
                            SubscriptionUtil.getActiveSubscriptions(this.mSubscriptionManager)
                                            .size()
                                    > 0;
                    if (isMultipleEnabledProfilesSupported$1() && z2) {
                        Log.d(
                                "ToggleSubscriptionDialogActivity",
                                "Device supports MEP and eSIM operation and eSIM profile is"
                                    + " enabled. DSDS condition satisfied.");
                    } else {
                        TelephonyManager telephonyManager = this.mTelMgr;
                        if (telephonyManager == null) {
                            isRemovableSimEnabled = false;
                        } else {
                            UiccSlotInfo[] uiccSlotsInfo = telephonyManager.getUiccSlotsInfo();
                            isRemovableSimEnabled =
                                    UiccSlotUtil.isRemovableSimEnabled(
                                            uiccSlotsInfo == null
                                                    ? ImmutableList.of()
                                                    : ImmutableList.copyOf(uiccSlotsInfo));
                        }
                        if (this.mIsEsimOperation && isRemovableSimEnabled) {
                            Log.d(
                                    "ToggleSubscriptionDialogActivity",
                                    "eSIM operation and removable SIM is enabled. DSDS condition"
                                        + " satisfied.");
                        } else {
                            boolean anyMatch =
                                    SubscriptionUtil.getActiveSubscriptions(
                                                    this.mSubscriptionManager)
                                            .stream()
                                            .anyMatch(new EidStatus$$ExternalSyntheticLambda2(1));
                            if (this.mIsEsimOperation || !anyMatch) {
                                Log.d(
                                        "ToggleSubscriptionDialogActivity",
                                        "DSDS condition not satisfied.");
                            } else {
                                Log.d(
                                        "ToggleSubscriptionDialogActivity",
                                        "Removable SIM operation and eSIM profile is enabled. DSDS"
                                            + " condition satisfied.");
                            }
                        }
                    }
                    ConfirmDialogFragment.show(
                            this,
                            3,
                            getString(R.string.sim_action_enable_dsds_title),
                            getString(R.string.sim_action_enable_dsds_text),
                            getString(R.string.sim_action_yes),
                            getString(R.string.sim_action_no_thanks));
                    return;
                }
                Log.d("ToggleSubscriptionDialogActivity", "Hardware does not support DSDS.");
            }
            if (!this.mIsEsimOperation && this.mTelMgr.isMultiSimEnabled()) {
                TelephonyManager telephonyManager2 = this.mTelMgr;
                if (telephonyManager2 != null) {
                    UiccSlotInfo[] uiccSlotsInfo2 = telephonyManager2.getUiccSlotsInfo();
                    z =
                            UiccSlotUtil.isRemovableSimEnabled(
                                    uiccSlotsInfo2 == null
                                            ? ImmutableList.of()
                                            : ImmutableList.copyOf(uiccSlotsInfo2));
                }
                if (z) {
                    Log.i(
                            "ToggleSubscriptionDialogActivity",
                            "Toggle on pSIM, no dialog displayed.");
                    handleTogglePsimAction();
                    finish();
                    return;
                }
            }
            showEnableSimConfirmDialog();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        this.mEnableMultiSimSidecar.removeListener(this);
        this.mSwitchToRemovableSlotSidecar.removeListener(this);
        this.mSwitchToEuiccSubscriptionSidecar.removeListener(this);
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mSwitchToEuiccSubscriptionSidecar.addListener(this);
        this.mSwitchToRemovableSlotSidecar.addListener(this);
        this.mEnableMultiSimSidecar.addListener(this);
    }

    @Override // com.android.settings.SidecarFragment.Listener
    public final void onStateChange(SidecarFragment sidecarFragment) {
        SwitchToEuiccSubscriptionSidecar switchToEuiccSubscriptionSidecar =
                this.mSwitchToEuiccSubscriptionSidecar;
        if (sidecarFragment == switchToEuiccSubscriptionSidecar) {
            int i = switchToEuiccSubscriptionSidecar.mState;
            if (i == 2) {
                Log.i(
                        "ToggleSubscriptionDialogActivity",
                        "Successfully "
                                + (this.mEnable ? ARG_enable : "disable")
                                + " the eSIM profile.");
                this.mSwitchToEuiccSubscriptionSidecar.setState(0, 0);
                dismissProgressDialog();
                finish();
                return;
            }
            if (i != 3) {
                return;
            }
            Log.i(
                    "ToggleSubscriptionDialogActivity",
                    "Failed to " + (this.mEnable ? ARG_enable : "disable") + " the eSIM profile.");
            this.mSwitchToEuiccSubscriptionSidecar.setState(0, 0);
            dismissProgressDialog();
            AlertDialogFragment.show(
                    this,
                    getString(R.string.privileged_action_disable_fail_title),
                    getString(R.string.privileged_action_disable_fail_text));
            return;
        }
        SwitchToRemovableSlotSidecar switchToRemovableSlotSidecar =
                this.mSwitchToRemovableSlotSidecar;
        if (sidecarFragment == switchToRemovableSlotSidecar) {
            int i2 = switchToRemovableSlotSidecar.mState;
            if (i2 == 2) {
                Log.i(
                        "ToggleSubscriptionDialogActivity",
                        "Successfully switched to removable slot.");
                this.mSwitchToRemovableSlotSidecar.setState(0, 0);
                handleTogglePsimAction();
                dismissProgressDialog();
                finish();
                return;
            }
            if (i2 != 3) {
                return;
            }
            Log.e("ToggleSubscriptionDialogActivity", "Failed switching to removable slot.");
            this.mSwitchToRemovableSlotSidecar.setState(0, 0);
            dismissProgressDialog();
            AlertDialogFragment.show(
                    this,
                    getString(R.string.sim_action_enable_sim_fail_title),
                    getString(R.string.sim_action_enable_sim_fail_text));
            return;
        }
        EnableMultiSimSidecar enableMultiSimSidecar = this.mEnableMultiSimSidecar;
        if (sidecarFragment == enableMultiSimSidecar) {
            int i3 = enableMultiSimSidecar.mState;
            if (i3 != 2) {
                if (i3 != 3) {
                    return;
                }
                enableMultiSimSidecar.setState(0, 0);
                Log.i(
                        "ToggleSubscriptionDialogActivity",
                        "Failed to switch to DSDS without rebooting.");
                dismissProgressDialog();
                AlertDialogFragment.show(
                        this,
                        getString(R.string.dsds_activation_failure_title),
                        getString(R.string.dsds_activation_failure_body_msg2));
                return;
            }
            enableMultiSimSidecar.setState(0, 0);
            Log.i(
                    "ToggleSubscriptionDialogActivity",
                    "Successfully switched to DSDS without reboot.");
            if (this.mIsEsimOperation) {
                Log.i(
                        "ToggleSubscriptionDialogActivity",
                        "DSDS enabled, start to enable profile: "
                                + this.mSubInfo.getSubscriptionId());
                this.mSwitchToEuiccSubscriptionSidecar.run(
                        this.mSubInfo.getSubscriptionId(), -1, null);
                return;
            }
            Log.i(
                    "ToggleSubscriptionDialogActivity",
                    "DSDS enabled, start to enable pSIM profile.");
            handleTogglePsimAction();
            dismissProgressDialog();
            finish();
        }
    }

    public final void showEnableSimConfirmDialog() {
        List list = this.mActiveSubInfos;
        if (list == null || list.isEmpty()) {
            Log.i("ToggleSubscriptionDialogActivity", "No active subscriptions available.");
            showNonSwitchSimConfirmDialog();
            return;
        }
        Log.i("ToggleSubscriptionDialogActivity", "mActiveSubInfos:" + this.mActiveSubInfos);
        boolean z =
                this.mIsEsimOperation
                        && this.mActiveSubInfos.stream()
                                .anyMatch(
                                        new ToggleSubscriptionDialogActivity$$ExternalSyntheticLambda0(
                                                1));
        boolean isMultiSimEnabled = this.mTelMgr.isMultiSimEnabled();
        if (isMultiSimEnabled && !isMultipleEnabledProfilesSupported$1() && !z) {
            showNonSwitchSimConfirmDialog();
            return;
        }
        if (!isMultiSimEnabled || !isMultipleEnabledProfilesSupported$1()) {
            SubscriptionInfo subscriptionInfo =
                    (isMultiSimEnabled && z)
                            ? (SubscriptionInfo)
                                    this.mActiveSubInfos.stream()
                                            .filter(
                                                    new ToggleSubscriptionDialogActivity$$ExternalSyntheticLambda0(
                                                            2))
                                            .findFirst()
                                            .get()
                            : (SubscriptionInfo) this.mActiveSubInfos.get(0);
            String string =
                    this.mIsEsimOperation
                            ? getString(
                                    R.string.sim_action_switch_sub_dialog_title,
                                    new Object[] {
                                        SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                                this, this.mSubInfo)
                                    })
                            : getString(R.string.sim_action_switch_psim_dialog_title);
            CharSequence uniqueSubscriptionDisplayName =
                    SubscriptionUtil.getUniqueSubscriptionDisplayName(this, this.mSubInfo);
            CharSequence uniqueSubscriptionDisplayName2 =
                    SubscriptionUtil.getUniqueSubscriptionDisplayName(this, subscriptionInfo);
            StringBuilder sb = new StringBuilder();
            if (z && this.mIsEsimOperation) {
                sb.append(
                        getString(
                                R.string.sim_action_switch_sub_dialog_text_downloaded,
                                new Object[] {
                                    uniqueSubscriptionDisplayName, uniqueSubscriptionDisplayName2
                                }));
            } else if (this.mIsEsimOperation) {
                sb.append(
                        getString(
                                R.string.sim_action_switch_sub_dialog_text,
                                new Object[] {
                                    uniqueSubscriptionDisplayName, uniqueSubscriptionDisplayName2
                                }));
            } else {
                sb.append(
                        getString(
                                R.string.sim_action_switch_sub_dialog_text_single_sim,
                                new Object[] {uniqueSubscriptionDisplayName2}));
            }
            if (this.isRtlMode) {
                sb.insert(0, "\u200f")
                        .insert(sb.indexOf("\n") - 1, "\u200f")
                        .insert(sb.indexOf("\n") + 2, "\u200f")
                        .insert(sb.length(), "\u200f");
            }
            ConfirmDialogFragment.show(
                    this,
                    2,
                    string,
                    sb.toString(),
                    this.mIsEsimOperation
                            ? getString(
                                    R.string.sim_action_switch_sub_dialog_confirm,
                                    new Object[] {
                                        SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                                this, this.mSubInfo)
                                    })
                            : getString(R.string.sim_switch_button),
                    getString(R.string.sim_action_cancel));
            return;
        }
        if (this.mActiveSubInfos.size() < 2) {
            showNonSwitchSimConfirmDialog();
            return;
        }
        Log.d("ToggleSubscriptionDialogActivity", "showMepSwitchSimConfirmDialog");
        CharSequence uniqueSubscriptionDisplayName3 =
                SubscriptionUtil.getUniqueSubscriptionDisplayName(this, this.mSubInfo);
        String string2 =
                getString(
                        R.string.sim_action_switch_sub_dialog_mep_title,
                        new Object[] {uniqueSubscriptionDisplayName3});
        StringBuilder sb2 = new StringBuilder();
        sb2.append(
                getString(
                        R.string.sim_action_switch_sub_dialog_mep_text,
                        new Object[] {uniqueSubscriptionDisplayName3}));
        if (this.isRtlMode) {
            sb2.insert(0, "\u200f").insert(sb2.length(), "\u200f");
        }
        String sb3 = sb2.toString();
        ArrayList<String> arrayList =
                new ArrayList<>(
                        (Collection<? extends String>)
                                this.mActiveSubInfos.stream()
                                        .map(
                                                new Function() { // from class:
                                                                 // com.android.settings.network.telephony.ToggleSubscriptionDialogActivity$$ExternalSyntheticLambda3
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        ToggleSubscriptionDialogActivity
                                                                toggleSubscriptionDialogActivity =
                                                                        ToggleSubscriptionDialogActivity
                                                                                .this;
                                                        String str =
                                                                ToggleSubscriptionDialogActivity
                                                                        .ARG_enable;
                                                        toggleSubscriptionDialogActivity.getClass();
                                                        return toggleSubscriptionDialogActivity
                                                                .getString(
                                                                        R.string
                                                                                .sim_action_switch_sub_dialog_carrier_list_item_for_turning_off,
                                                                        new Object[] {
                                                                            SubscriptionUtil
                                                                                    .getUniqueSubscriptionDisplayName(
                                                                                            toggleSubscriptionDialogActivity,
                                                                                            (SubscriptionInfo)
                                                                                                    obj)
                                                                        });
                                                    }
                                                })
                                        .collect(Collectors.toList()));
        arrayList.add(getString(R.string.sim_action_cancel));
        ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UniversalCredentialUtil.AGENT_TITLE, string2);
        bundle.putCharSequence("msg", sb3);
        bundle.putString("pos_button_string", null);
        bundle.putString("neg_button_string", null);
        bundle.putStringArrayList("list", arrayList);
        BaseDialogFragment.setListener(
                this, ConfirmDialogFragment.OnConfirmListener.class, 5, bundle);
        confirmDialogFragment.setArguments(bundle);
        confirmDialogFragment.show(getSupportFragmentManager(), "ConfirmDialogFragment");
    }

    public final void showNonSwitchSimConfirmDialog() {
        CharSequence uniqueSubscriptionDisplayName =
                SubscriptionUtil.getUniqueSubscriptionDisplayName(this, this.mSubInfo);
        ConfirmDialogFragment.show(
                this,
                2,
                (this.mSubInfo == null || TextUtils.isEmpty(uniqueSubscriptionDisplayName))
                        ? getString(
                                R.string.sim_action_enable_sub_dialog_title_without_carrier_name)
                        : getString(
                                R.string.sim_action_enable_sub_dialog_title,
                                new Object[] {uniqueSubscriptionDisplayName}),
                null,
                getString(R.string.condition_turn_on),
                getString(R.string.sim_action_cancel));
    }
}
