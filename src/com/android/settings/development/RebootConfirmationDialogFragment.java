package com.android.settings.development;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RebootConfirmationDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public final int mCancelButtonId;
    public final RebootConfirmationDialogHost mHost;
    public final int mMessageId;

    public RebootConfirmationDialogFragment(
            int i, int i2, RebootConfirmationDialogHost rebootConfirmationDialogHost) {
        this.mMessageId = i;
        this.mCancelButtonId = i2;
        this.mHost = rebootConfirmationDialogHost;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1914;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            this.mHost.onRebootConfirmed(getContext());
        } else {
            this.mHost.onRebootCancelled();
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.mMessageId);
        builder.setPositiveButton(R.string.reboot_dialog_reboot_now, this);
        builder.setNegativeButton(this.mCancelButtonId, this);
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        this.mHost.onRebootDialogDismissed();
    }
}
