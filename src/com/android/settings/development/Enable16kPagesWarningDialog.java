package com.android.settings.development;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class Enable16kPagesWarningDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public Enable16kPagesPreferenceController mHost;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2042;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            this.mHost.on16kPagesDialogConfirmed();
            return;
        }
        Enable16kPagesPreferenceController enable16kPagesPreferenceController = this.mHost;
        Preference preference = enable16kPagesPreferenceController.mPreference;
        if (preference == null) {
            return;
        }
        enable16kPagesPreferenceController.updateState(preference);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        boolean z = getArguments().getBoolean("SHOW_16K_DIALOG");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(
                z
                        ? R.string.confirm_enable_16k_pages_title
                        : R.string.confirm_enable_4k_pages_title);
        builder.setMessage(
                z ? R.string.confirm_enable_16k_pages_text : R.string.confirm_enable_4k_pages_text);
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        Enable16kPagesPreferenceController enable16kPagesPreferenceController = this.mHost;
        Preference preference = enable16kPagesPreferenceController.mPreference;
        if (preference == null) {
            return;
        }
        enable16kPagesPreferenceController.updateState(preference);
    }
}
