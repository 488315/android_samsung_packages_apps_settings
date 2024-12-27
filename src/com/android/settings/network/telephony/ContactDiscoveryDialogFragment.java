package com.android.settings.network.telephony;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.ims.ImsManager;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContactDiscoveryDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public CharSequence mCarrierName;
    public ImsManager mImsManager;
    public int mSubId;

    public ImsManager getImsManager(Context context) {
        return (ImsManager) context.getSystemService(ImsManager.class);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1963;
    }

    @Override // com.android.settings.core.instrumentation.InstrumentedDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        this.mSubId = arguments.getInt("sub_id_key");
        this.mCarrierName = arguments.getCharSequence("carrier_name_key");
        this.mImsManager = getImsManager(context);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            MobileNetworkUtils.setContactDiscoveryEnabled(this.mImsManager, this.mSubId, true);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        String string;
        String string2;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (TextUtils.isEmpty(this.mCarrierName)) {
            string =
                    getContext()
                            .getString(
                                    R.string
                                            .contact_discovery_opt_in_dialog_title_no_carrier_defined);
            string2 =
                    getContext()
                            .getString(
                                    R.string
                                            .contact_discovery_opt_in_dialog_message_no_carrier_defined);
        } else {
            string =
                    getContext()
                            .getString(
                                    R.string.contact_discovery_opt_in_dialog_title,
                                    this.mCarrierName);
            string2 =
                    getContext()
                            .getString(
                                    R.string.contact_discovery_opt_in_dialog_message,
                                    this.mCarrierName);
        }
        builder.setMessage(string2)
                .setTitle(string)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setPositiveButton(R.string.confirmation_turn_on, this)
                .setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }
}
