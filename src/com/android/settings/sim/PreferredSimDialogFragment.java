package com.android.settings.sim;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.network.SubscriptionUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PreferredSimDialogFragment extends SimDialogFragment
        implements DialogInterface.OnClickListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1709;
    }

    public SubscriptionManager getSubscriptionManager() {
        return (SubscriptionManager) getContext().getSystemService(SubscriptionManager.class);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        SimDialogActivity simDialogActivity = (SimDialogActivity) getActivity();
        Intent intent = getActivity().getIntent();
        int i2 = SimDialogActivity.$r8$clinit;
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex =
                getSubscriptionManager()
                        .getActiveSubscriptionInfoForSimSlotIndex(
                                intent.getIntExtra("preferred_sim", -1));
        if (activeSubscriptionInfoForSimSlotIndex != null) {
            simDialogActivity.onSubscriptionSelected(
                    getDialogType(), activeSubscriptionInfoForSimSlotIndex.getSubscriptionId());
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getArguments().getInt("title_id"));
        builder.setPositiveButton(R.string.yes, this);
        builder.setNegativeButton(R.string.no, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        updateDialog$1(create);
        return create;
    }

    @Override // com.android.settings.sim.SimDialogFragment
    public final void updateDialog() {
        updateDialog$1((AlertDialog) this.mDialog);
    }

    public final void updateDialog$1(AlertDialog alertDialog) {
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("Dialog updated, dismiss status: "),
                this.mWasDismissed,
                "PreferredSimDialogFrag");
        if (this.mWasDismissed) {
            return;
        }
        if (alertDialog == null) {
            Log.d("PreferredSimDialogFrag", "Dialog is null.");
            dismiss();
            return;
        }
        Intent intent = getActivity().getIntent();
        int i = SimDialogActivity.$r8$clinit;
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex =
                getSubscriptionManager()
                        .getActiveSubscriptionInfoForSimSlotIndex(
                                intent.getIntExtra("preferred_sim", -1));
        if (activeSubscriptionInfoForSimSlotIndex == null
                || (activeSubscriptionInfoForSimSlotIndex.isEmbedded()
                        && (activeSubscriptionInfoForSimSlotIndex.getProfileClass() == 1
                                || activeSubscriptionInfoForSimSlotIndex
                                        .isOnlyNonTerrestrialNetwork()))) {
            dismiss();
        } else {
            alertDialog.setMessage(
                    getContext()
                            .getString(
                                    R.string.sim_preferred_message,
                                    SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                            getContext(), activeSubscriptionInfoForSimSlotIndex)));
        }
    }
}
