package com.android.settings.network;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.system.ResetDashboardFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EraseEuiccDataDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1857;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (!(getTargetFragment() instanceof ResetDashboardFragment)) {
            Log.e("EraseEuiccDataDlg", "getTargetFragment return unexpected type");
        }
        if (i == -1) {
            final Context context = getContext();
            MobileNetworkUtils.showLockScreen(
                    context,
                    new Runnable() { // from class:
                                     // com.android.settings.network.EraseEuiccDataDialogFragment$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            EraseEuiccDataDialogFragment eraseEuiccDataDialogFragment =
                                    EraseEuiccDataDialogFragment.this;
                            Context context2 = context;
                            eraseEuiccDataDialogFragment.getClass();
                            ResetNetworkOperationBuilder resetNetworkOperationBuilder =
                                    new ResetNetworkOperationBuilder(context2);
                            resetNetworkOperationBuilder.resetEsim(
                                    "com.android.settings.network", null);
                            AsyncTask.execute(
                                    new ResetNetworkOperationBuilder$$ExternalSyntheticLambda0(
                                            resetNetworkOperationBuilder, 1));
                        }
                    });
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.reset_esim_title)
                .setMessage(R.string.reset_esim_desc)
                .setPositiveButton(R.string.erase_sim_confirm_button, this)
                .setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null)
                .setOnDismissListener(this)
                .create();
    }
}
