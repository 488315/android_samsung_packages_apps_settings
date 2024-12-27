package com.samsung.android.settings.datausage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecConfirmLimitFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 551;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        SecBillingCycleManager.getInstance(getContext()).updateScreen();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            SecBillingCycleManager.getInstance(getContext()).updateScreen();
        } else {
            SecBillingCycleManager.getInstance(getContext()).setPolicyLimitBytesByDefault();
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        FragmentActivity activity = getActivity();
        CharSequence charSequence = getArguments().getCharSequence("message");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.data_usage_limit_dialog_title_vzw);
        builder.P.mMessage = charSequence;
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }
}
