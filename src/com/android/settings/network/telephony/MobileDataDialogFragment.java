package com.android.settings.network.telephony;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.wifi.WifiPickerTrackerHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MobileDataDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public int mSubId;
    public SubscriptionManager mSubscriptionManager;
    public int mType;
    public WifiPickerTrackerHelper mWifiPickerTrackerHelper;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1582;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.mType;
        if (i2 == 0) {
            Log.d("MobileDataDialogFragment", "setMobileDataEnabled: false");
            MobileNetworkUtils.setMobileDataEnabled(this.mSubId, getContext(), false, false);
            WifiPickerTrackerHelper wifiPickerTrackerHelper = this.mWifiPickerTrackerHelper;
            if (wifiPickerTrackerHelper == null
                    || wifiPickerTrackerHelper.isCarrierNetworkProvisionEnabled(this.mSubId)) {
                return;
            }
            this.mWifiPickerTrackerHelper.setCarrierNetworkEnabled(false);
            return;
        }
        if (i2 != 1) {
            throw new IllegalArgumentException("unknown type " + this.mType);
        }
        this.mSubscriptionManager.setDefaultDataSubId(this.mSubId);
        Log.d("MobileDataDialogFragment", "setMobileDataEnabled: true");
        MobileNetworkUtils.setMobileDataEnabled(this.mSubId, getContext(), true, true);
        WifiPickerTrackerHelper wifiPickerTrackerHelper2 = this.mWifiPickerTrackerHelper;
        if (wifiPickerTrackerHelper2 == null
                || wifiPickerTrackerHelper2.isCarrierNetworkProvisionEnabled(this.mSubId)) {
            return;
        }
        this.mWifiPickerTrackerHelper.setCarrierNetworkEnabled(true);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSubscriptionManager =
                (SubscriptionManager) getContext().getSystemService(SubscriptionManager.class);
        this.mWifiPickerTrackerHelper =
                new WifiPickerTrackerHelper(this.mLifecycle, getContext(), null);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Bundle arguments = getArguments();
        Context context = getContext();
        this.mType = arguments.getInt("dialog_type");
        int i = arguments.getInt("subId");
        this.mSubId = i;
        int i2 = this.mType;
        if (i2 == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.data_usage_disable_mobile);
            builder.setPositiveButton(android.R.string.ok, this);
            builder.setNegativeButton(
                    android.R.string.cancel, (DialogInterface.OnClickListener) null);
            return builder.create();
        }
        if (i2 != 1) {
            throw new IllegalArgumentException("unknown type " + this.mType);
        }
        SubscriptionInfo activeSubscriptionInfo =
                this.mSubscriptionManager.getActiveSubscriptionInfo(i);
        SubscriptionInfo activeSubscriptionInfo2 =
                this.mSubscriptionManager.getActiveSubscriptionInfo(
                        SubscriptionManager.getDefaultDataSubscriptionId());
        String string =
                activeSubscriptionInfo2 == null
                        ? getContext()
                                .getResources()
                                .getString(R.string.sim_selection_required_pref)
                        : SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                        getContext(), activeSubscriptionInfo2)
                                .toString();
        String string2 =
                activeSubscriptionInfo == null
                        ? getContext()
                                .getResources()
                                .getString(R.string.sim_selection_required_pref)
                        : SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                        getContext(), activeSubscriptionInfo)
                                .toString();
        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
        String string3 = context.getString(R.string.sim_change_data_title, string2);
        AlertController.AlertParams alertParams = builder2.P;
        alertParams.mTitle = string3;
        alertParams.mMessage = context.getString(R.string.sim_change_data_message, string2, string);
        builder2.setPositiveButton(context.getString(R.string.sim_change_data_ok, string2), this);
        builder2.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder2.create();
    }
}
