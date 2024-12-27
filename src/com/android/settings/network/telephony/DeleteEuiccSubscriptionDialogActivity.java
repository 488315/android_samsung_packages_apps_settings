package com.android.settings.network.telephony;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SidecarFragment;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.SubscriptionUtil$$ExternalSyntheticLambda6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DeleteEuiccSubscriptionDialogActivity extends SubscriptionActionDialogActivity
        implements SidecarFragment.Listener, ConfirmDialogFragment.OnConfirmListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public DeleteEuiccSubscriptionSidecar mDeleteEuiccSubscriptionSidecar;
    public SubscriptionInfo mSubscriptionToBeDeleted;
    public List mSubscriptionsToBeDeleted;

    @Override // com.android.settings.network.telephony.ConfirmDialogFragment.OnConfirmListener
    public final void onConfirm(int i, int i2, boolean z) {
        if (!z) {
            finish();
            return;
        }
        if (i != 1) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(
                            i,
                            "Unrecognized confirmation dialog tag: ",
                            "DeleteEuiccSubscriptionDialogActivity");
            return;
        }
        Log.i("DeleteEuiccSubscriptionDialogActivity", "Subscription deletion confirmed");
        showProgressDialog(getString(R.string.erasing_sim), false);
        DeleteEuiccSubscriptionSidecar deleteEuiccSubscriptionSidecar =
                this.mDeleteEuiccSubscriptionSidecar;
        List list = this.mSubscriptionsToBeDeleted;
        deleteEuiccSubscriptionSidecar.getClass();
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Subscriptions cannot be empty.");
        }
        deleteEuiccSubscriptionSidecar.setState(1, 0);
        deleteEuiccSubscriptionSidecar.mSubscriptions = new ArrayList(list);
        deleteEuiccSubscriptionSidecar.deleteSubscription();
    }

    @Override // com.android.settings.network.telephony.SubscriptionActionDialogActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        List singletonList;
        super.onCreate(bundle);
        int intExtra = getIntent().getIntExtra("sub_id", -1);
        this.mSubscriptionToBeDeleted =
                SubscriptionUtil.getSubById(this.mSubscriptionManager, intExtra);
        SubscriptionManager subscriptionManager = this.mSubscriptionManager;
        SubscriptionInfo subById = SubscriptionUtil.getSubById(subscriptionManager, intExtra);
        if (subById == null) {
            singletonList = Collections.emptyList();
        } else {
            ParcelUuid groupUuid = subById.getGroupUuid();
            List availableSubscriptionInfoList =
                    subscriptionManager.getAvailableSubscriptionInfoList();
            singletonList =
                    (availableSubscriptionInfoList == null
                                    || availableSubscriptionInfoList.isEmpty()
                                    || groupUuid == null)
                            ? Collections.singletonList(subById)
                            : (List)
                                    availableSubscriptionInfoList.stream()
                                            .filter(
                                                    new SubscriptionUtil$$ExternalSyntheticLambda6(
                                                            2, groupUuid))
                                            .collect(Collectors.toList());
        }
        this.mSubscriptionsToBeDeleted = singletonList;
        if (this.mSubscriptionToBeDeleted == null || singletonList.isEmpty()) {
            Log.e(
                    "DeleteEuiccSubscriptionDialogActivity",
                    "Cannot find subscription with sub ID: " + intExtra);
            finish();
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        int i = DeleteEuiccSubscriptionSidecar.$r8$clinit;
        this.mDeleteEuiccSubscriptionSidecar =
                (DeleteEuiccSubscriptionSidecar)
                        SidecarFragment.get(
                                fragmentManager,
                                "DeleteEuiccSubscriptionSidecar",
                                DeleteEuiccSubscriptionSidecar.class);
        if (bundle == null) {
            ConfirmDialogFragment.show(
                    this,
                    1,
                    getString(R.string.erase_sim_dialog_title),
                    getString(
                            R.string.erase_sim_dialog_text,
                            new Object[] {
                                SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                        this, this.mSubscriptionToBeDeleted)
                            }),
                    getString(R.string.erase_sim_confirm_button),
                    getString(R.string.cancel));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        this.mDeleteEuiccSubscriptionSidecar.removeListener(this);
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mDeleteEuiccSubscriptionSidecar.addListener(this);
    }

    @Override // com.android.settings.SidecarFragment.Listener
    public final void onStateChange(SidecarFragment sidecarFragment) {
        DeleteEuiccSubscriptionSidecar deleteEuiccSubscriptionSidecar =
                this.mDeleteEuiccSubscriptionSidecar;
        if (sidecarFragment == deleteEuiccSubscriptionSidecar) {
            int i = deleteEuiccSubscriptionSidecar.mState;
            if (i == 2) {
                Log.i(
                        "DeleteEuiccSubscriptionDialogActivity",
                        "Successfully delete the subscription.");
                this.mDeleteEuiccSubscriptionSidecar.setState(0, 0);
                dismissProgressDialog();
                finish();
                return;
            }
            if (i != 3) {
                return;
            }
            Log.e("DeleteEuiccSubscriptionDialogActivity", "Failed to delete the subscription.");
            this.mDeleteEuiccSubscriptionSidecar.setState(0, 0);
            AlertDialogFragment.show(
                    this,
                    getString(R.string.erase_sim_fail_title),
                    getString(R.string.erase_sim_fail_text));
        }
    }
}
