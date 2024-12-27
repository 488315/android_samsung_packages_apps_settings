package com.android.settings.development;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemProperties;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RebootWithMteDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public Context mContext;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1984;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        PowerManager powerManager =
                (PowerManager) this.mContext.getSystemService(PowerManager.class);
        SystemProperties.set("arm64.memtag.bootctl", "memtag-once");
        powerManager.reboot(null);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.reboot_with_mte_title);
        builder.setMessage(R.string.reboot_with_mte_message);
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.P.mIconId = android.R.drawable.ic_dialog_alert;
        return builder.create();
    }
}
