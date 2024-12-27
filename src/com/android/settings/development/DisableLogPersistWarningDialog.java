package com.android.settings.development;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DisableLogPersistWarningDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1225;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        LogPersistDialogHost logPersistDialogHost = (LogPersistDialogHost) getTargetFragment();
        if (logPersistDialogHost == null) {
            return;
        }
        if (i != -1) {
            ((LogPersistPreferenceController)
                            ((DevelopmentSettingsDashboardFragment) logPersistDialogHost)
                                    .getDevelopmentOptionsController(
                                            LogPersistPreferenceController.class))
                    .updateLogpersistValues();
            return;
        }
        LogPersistPreferenceController logPersistPreferenceController =
                (LogPersistPreferenceController)
                        ((DevelopmentSettingsDashboardFragment) logPersistDialogHost)
                                .getDevelopmentOptionsController(
                                        LogPersistPreferenceController.class);
        logPersistPreferenceController.setLogpersistOff(true);
        logPersistPreferenceController.updateLogpersistValues();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dev_logpersist_clear_warning_title);
        builder.setMessage(R.string.dev_logpersist_clear_warning_message);
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }
}
