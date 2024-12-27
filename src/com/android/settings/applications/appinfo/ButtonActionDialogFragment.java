package com.android.settings.applications.appinfo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ButtonActionDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    int mId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface AppButtonsDialogListener {
        void handleDialogClick(int i);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 558;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (this.mId == 2) {
            dialogInterface.dismiss();
        }
        ((AppButtonsDialogListener) getTargetFragment()).handleDialogClick(this.mId);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        int i = getArguments().getInt("id");
        this.mId = i;
        Context context = getContext();
        AlertDialog alertDialog = null;
        if (i == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.app_disable_dlg_text);
            builder.setPositiveButton(R.string.app_disable_dlg_positive, this);
            builder.setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
            alertDialog = builder.create();
        } else if (i == 2) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
            builder2.setTitle(R.string.force_stop_dlg_title);
            builder2.setMessage(R.string.force_stop_dlg_text);
            builder2.setPositiveButton(R.string.dlg_ok, this);
            builder2.setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
            alertDialog = builder2.create();
        }
        if (alertDialog != null) {
            return alertDialog;
        }
        throw new IllegalArgumentException("unknown id " + this.mId);
    }
}
