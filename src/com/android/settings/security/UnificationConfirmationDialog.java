package com.android.settings.security;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UnificationConfirmationDialog extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 532;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        final SecuritySettings securitySettings = (SecuritySettings) getParentFragment();
        boolean z = getArguments().getBoolean("compliant");
        String str =
                z
                        ? "Settings.WORK_PROFILE_UNIFY_LOCKS_DETAIL"
                        : "Settings.WORK_PROFILE_UNIFY_LOCKS_NONCOMPLIANT";
        final int i =
                z
                        ? R.string.lock_settings_profile_unification_dialog_body
                        : R.string.lock_settings_profile_unification_dialog_uncompliant_body;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.lock_settings_profile_unification_dialog_title);
        builder.P.mMessage =
                ((DevicePolicyManager) getContext().getSystemService(DevicePolicyManager.class))
                        .getResources()
                        .getString(
                                str,
                                new Supplier() { // from class:
                                                 // com.android.settings.security.UnificationConfirmationDialog$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return UnificationConfirmationDialog.this.getString(i);
                                    }
                                });
        builder.setPositiveButton(
                z
                        ? R.string.lock_settings_profile_unification_dialog_confirm
                        : R.string.lock_settings_profile_unification_dialog_uncompliant_confirm,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.security.UnificationConfirmationDialog$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        ((LockUnificationPreferenceController)
                                        SecuritySettings.this.use(
                                                LockUnificationPreferenceController.class))
                                .startUnification();
                    }
                });
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        ((LockUnificationPreferenceController)
                        ((SecuritySettings) getParentFragment())
                                .use(LockUnificationPreferenceController.class))
                .updateState(null);
    }
}
