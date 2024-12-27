package com.android.settings.security;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MemtagRebootDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public int mMessage;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1913;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        ((PowerManager) getContext().getSystemService(PowerManager.class)).reboot(null);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.memtag_reboot_title);
        builder.setMessage(this.mMessage);
        builder.setPositiveButton(R.string.memtag_reboot_yes, this);
        builder.setNegativeButton(
                R.string.memtag_reboot_no, (DialogInterface.OnClickListener) null);
        return builder.create();
    }
}
