package com.android.settings.sim;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.network.SubscriptionUtil;

import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SelectSpecificDataSimDialogFragment extends SimDialogFragment
        implements DialogInterface.OnClickListener {
    public SubscriptionInfo mSubscriptionInfo;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1987;
    }

    public SubscriptionManager getSubscriptionManager() {
        return ((SubscriptionManager) getContext().getSystemService(SubscriptionManager.class))
                .createForAllUserProfiles();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        SubscriptionInfo subscriptionInfo;
        SimDialogActivity simDialogActivity = (SimDialogActivity) getActivity();
        if (i != -1 || (subscriptionInfo = this.mSubscriptionInfo) == null) {
            return;
        }
        simDialogActivity.onSubscriptionSelected(
                getDialogType(), subscriptionInfo.getSubscriptionId());
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setNegativeButton(
                R.string.sim_action_no_thanks, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        updateDialog$2(create);
        return create;
    }

    @Override // com.android.settings.sim.SimDialogFragment, androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("Dialog onDismiss, dismiss status: "),
                this.mWasDismissed,
                "PreferredSimDialogFrag");
        if (this.mWasDismissed) {
            return;
        }
        this.mWasDismissed = true;
        ((SimDialogActivity) getActivity()).showEnableAutoDataSwitchDialog();
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override // com.android.settings.sim.SimDialogFragment
    public final void updateDialog() {
        updateDialog$2((AlertDialog) this.mDialog);
    }

    public final void updateDialog$2(AlertDialog alertDialog) {
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
        final SubscriptionInfo defaultDataSubscriptionInfo =
                getSubscriptionManager().getDefaultDataSubscriptionInfo();
        List<SubscriptionInfo> activeSubscriptionInfoList =
                getSubscriptionManager().getActiveSubscriptionInfoList();
        SubscriptionInfo orElse =
                (activeSubscriptionInfoList == null || defaultDataSubscriptionInfo == null)
                        ? null
                        : activeSubscriptionInfoList.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.settings.sim.SelectSpecificDataSimDialogFragment$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return ((SubscriptionInfo) obj).getSubscriptionId()
                                                        != defaultDataSubscriptionInfo
                                                                .getSubscriptionId();
                                            }
                                        })
                                .findFirst()
                                .orElse(null);
        if (orElse == null || defaultDataSubscriptionInfo == null) {
            Log.d("PreferredSimDialogFrag", "one of target SubscriptionInfos is null");
            dismiss();
            return;
        }
        if ((orElse.isEmbedded()
                        && (orElse.getProfileClass() == 1 || orElse.isOnlyNonTerrestrialNetwork()))
                || (defaultDataSubscriptionInfo.isEmbedded()
                        && (defaultDataSubscriptionInfo.getProfileClass() == 1
                                || defaultDataSubscriptionInfo.isOnlyNonTerrestrialNetwork()))) {
            Log.d("PreferredSimDialogFrag", "do not set the provisioning or satellite eSIM");
            dismiss();
            return;
        }
        Log.d(
                "PreferredSimDialogFrag",
                "newSubId: "
                        + orElse.getSubscriptionId()
                        + "currentDataSubID: "
                        + defaultDataSubscriptionInfo.getSubscriptionId());
        this.mSubscriptionInfo = orElse;
        CharSequence uniqueSubscriptionDisplayName =
                SubscriptionUtil.getUniqueSubscriptionDisplayName(getContext(), orElse);
        CharSequence uniqueSubscriptionDisplayName2 =
                SubscriptionUtil.getUniqueSubscriptionDisplayName(
                        getContext(), defaultDataSubscriptionInfo);
        String string =
                getContext()
                        .getString(
                                R.string.select_specific_sim_for_data_button,
                                uniqueSubscriptionDisplayName);
        String string2 =
                getContext()
                        .getString(
                                R.string.select_specific_sim_for_data_msg,
                                uniqueSubscriptionDisplayName,
                                uniqueSubscriptionDisplayName2);
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(
                                R.layout.sim_confirm_dialog_multiple_enabled_profiles_supported,
                                (ViewGroup) null);
        TextView textView = inflate != null ? (TextView) inflate.findViewById(R.id.msg) : null;
        if (!TextUtils.isEmpty(string2) && textView != null) {
            textView.setText(string2);
            textView.setVisibility(0);
        }
        alertDialog.setView$1(inflate);
        TextView textView2 =
                (TextView)
                        LayoutInflater.from(getContext())
                                .inflate(
                                        R.layout
                                                .sim_confirm_dialog_title_multiple_enabled_profiles_supported,
                                        (ViewGroup) null)
                                .findViewById(R.id.title);
        textView2.setText(
                getContext()
                        .getString(
                                getArguments().getInt("title_id"), uniqueSubscriptionDisplayName));
        alertDialog.mAlert.mCustomTitleView = textView2;
        alertDialog.setButton(-1, string, this);
    }
}
