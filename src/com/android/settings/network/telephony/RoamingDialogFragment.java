package com.android.settings.network.telephony;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyManager;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RoamingDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public CarrierConfigManager mCarrierConfigManager;
    public int mSubId;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1583;
    }

    @Override // com.android.settings.core.instrumentation.InstrumentedDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mSubId = getArguments().getInt("sub_id_key");
        this.mCarrierConfigManager =
                (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        TelephonyManager createForSubscriptionId;
        if (i != -1
                || (createForSubscriptionId =
                                ((TelephonyManager)
                                                getContext()
                                                        .getSystemService(TelephonyManager.class))
                                        .createForSubscriptionId(this.mSubId))
                        == null) {
            return;
        }
        createForSubscriptionId.setDataRoamingEnabled(true);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        PersistableBundle configForSubId =
                this.mCarrierConfigManager.getConfigForSubId(this.mSubId);
        builder.setMessage(
                        getResources()
                                .getString(
                                        (configForSubId == null
                                                        || !configForSubId.getBoolean(
                                                                "check_pricing_with_carrier_data_roaming_bool"))
                                                ? R.string.roaming_warning
                                                : R.string.roaming_check_price_warning))
                .setTitle(R.string.roaming_alert_title)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this);
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        return create;
    }
}
