package com.android.settings.sim;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.network.SubscriptionUtil;

import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EnableAutoDataSwitchDialogFragment extends SimDialogFragment
        implements DialogInterface.OnClickListener {
    public int mBackupDataSubId = -1;

    public int getDefaultDataSubId() {
        return SubscriptionManager.getDefaultDataSubscriptionId();
    }

    public String getMessage() {
        SubscriptionInfo orElse;
        final int defaultDataSubId = getDefaultDataSubId();
        if (defaultDataSubId == -1) {
            return null;
        }
        Log.d("EnableAutoDataSwitchDialogFragment", "DDS SubId: " + defaultDataSubId);
        SubscriptionManager createForAllUserProfiles =
                ((SubscriptionManager) getContext().getSystemService(SubscriptionManager.class))
                        .createForAllUserProfiles();
        List<SubscriptionInfo> activeSubscriptionInfoList =
                createForAllUserProfiles.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null
                || (orElse =
                                activeSubscriptionInfoList.stream()
                                        .filter(
                                                new Predicate() { // from class:
                                                                  // com.android.settings.sim.EnableAutoDataSwitchDialogFragment$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Predicate
                                                    public final boolean test(Object obj) {
                                                        return ((SubscriptionInfo) obj)
                                                                        .getSubscriptionId()
                                                                != defaultDataSubId;
                                                    }
                                                })
                                        .findFirst()
                                        .orElse(null))
                        == null) {
            return null;
        }
        int subscriptionId = orElse.getSubscriptionId();
        this.mBackupDataSubId = subscriptionId;
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) getContext().getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(subscriptionId);
        if (createForSubscriptionId == null) {
            RecyclerView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("telephonyManager for "),
                    this.mBackupDataSubId,
                    " is null",
                    "EnableAutoDataSwitchDialogFragment");
            return null;
        }
        if (createForSubscriptionId.isMobileDataPolicyEnabled(3)) {
            Log.d("EnableAutoDataSwitchDialogFragment", "AUTO_DATA_SWITCH already enabled");
            return null;
        }
        Log.d("EnableAutoDataSwitchDialogFragment", "Backup data sub Id: " + this.mBackupDataSubId);
        String string =
                getContext()
                        .getString(
                                R.string.enable_auto_data_switch_dialog_message,
                                SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                        getContext(), orElse));
        UserManager userManager = (UserManager) getContext().getSystemService(UserManager.class);
        if (userManager == null) {
            return string;
        }
        UserHandle subscriptionUserHandle =
                createForAllUserProfiles.getSubscriptionUserHandle(defaultDataSubId);
        UserHandle subscriptionUserHandle2 =
                createForAllUserProfiles.getSubscriptionUserHandle(this.mBackupDataSubId);
        boolean z = false;
        boolean z2 =
                subscriptionUserHandle != null
                        && userManager.isManagedProfile(subscriptionUserHandle.getIdentifier());
        if (subscriptionUserHandle2 != null
                && userManager.isManagedProfile(subscriptionUserHandle2.getIdentifier())) {
            z = true;
        }
        Utils$$ExternalSyntheticOutline0.m653m(
                "isDdsManaged= ", z2, " isNDdsManaged=", z, "EnableAutoDataSwitchDialogFragment");
        if (!(z2 ^ z)) {
            return string;
        }
        StringBuilder m =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(string);
        m.append(getContext().getString(R.string.auto_data_switch_dialog_managed_profile_warning));
        return m.toString();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2008;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        SimDialogActivity simDialogActivity = (SimDialogActivity) getActivity();
        if (this.mBackupDataSubId != -1) {
            simDialogActivity.onSubscriptionSelected(getDialogType(), this.mBackupDataSubId);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton(R.string.yes, this);
        builder.setNegativeButton(
                R.string.sim_action_no_thanks, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        updateDialog(create);
        return create;
    }

    public final void updateDialog(AlertDialog alertDialog) {
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("Dialog updated, dismiss status: "),
                this.mWasDismissed,
                "EnableAutoDataSwitchDialogFragment");
        if (this.mWasDismissed) {
            return;
        }
        if (alertDialog == null) {
            Log.d("EnableAutoDataSwitchDialogFragment", "Dialog is null.");
            dismiss();
            return;
        }
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(
                                R.layout.sim_confirm_dialog_multiple_enabled_profiles_supported,
                                (ViewGroup) null);
        TextView textView = inflate != null ? (TextView) inflate.findViewById(R.id.msg) : null;
        String message = getMessage();
        if (TextUtils.isEmpty(message) || textView == null) {
            onDismiss(alertDialog);
            return;
        }
        textView.setText(message);
        textView.setVisibility(0);
        alertDialog.setView$1(inflate);
        TextView textView2 =
                (TextView)
                        LayoutInflater.from(getContext())
                                .inflate(
                                        R.layout
                                                .sim_confirm_dialog_title_multiple_enabled_profiles_supported,
                                        (ViewGroup) null)
                                .findViewById(R.id.title);
        textView2.setText(getContext().getString(getArguments().getInt("title_id")));
        alertDialog.mAlert.mCustomTitleView = textView2;
    }

    @Override // com.android.settings.sim.SimDialogFragment
    public final void updateDialog() {
        updateDialog((AlertDialog) this.mDialog);
    }
}
