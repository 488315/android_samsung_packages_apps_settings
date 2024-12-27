package com.android.settings.development;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EnableExt4WarningDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2042;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            throw null;
        }
        throw null;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.confirm_format_ext4_title);
        builder.P.mIconId = R.drawable.ic_delete_accent;
        builder.setMessage(R.string.confirm_format_ext4_text);
        builder.setPositiveButton(R.string.confirm_ext4_button_text, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        throw null;
    }
}
