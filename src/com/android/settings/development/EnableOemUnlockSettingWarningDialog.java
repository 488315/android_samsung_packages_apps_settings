package com.android.settings.development;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.RestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EnableOemUnlockSettingWarningDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1220;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        OemUnlockDialogHost oemUnlockDialogHost = (OemUnlockDialogHost) getTargetFragment();
        if (oemUnlockDialogHost == null) {
            return;
        }
        if (i == -1) {
            OemUnlockPreferenceController oemUnlockPreferenceController =
                    (OemUnlockPreferenceController)
                            ((DevelopmentSettingsDashboardFragment) oemUnlockDialogHost)
                                    .getDevelopmentOptionsController(
                                            OemUnlockPreferenceController.class);
            oemUnlockPreferenceController.mOemLockManager.setOemUnlockAllowedByUser(true);
            oemUnlockPreferenceController.sendBroadcastToKMX(true);
        } else {
            OemUnlockPreferenceController oemUnlockPreferenceController2 =
                    (OemUnlockPreferenceController)
                            ((DevelopmentSettingsDashboardFragment) oemUnlockDialogHost)
                                    .getDevelopmentOptionsController(
                                            OemUnlockPreferenceController.class);
            RestrictedSwitchPreference restrictedSwitchPreference =
                    oemUnlockPreferenceController2.mPreference;
            if (restrictedSwitchPreference == null) {
                return;
            }
            oemUnlockPreferenceController2.updateState(restrictedSwitchPreference);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.confirm_enable_oem_unlock_title);
        builder.setMessage(R.string.confirm_enable_oem_unlock_text);
        builder.setPositiveButton(R.string.enable_text, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        OemUnlockPreferenceController oemUnlockPreferenceController;
        RestrictedSwitchPreference restrictedSwitchPreference;
        super.onDismiss(dialogInterface);
        OemUnlockDialogHost oemUnlockDialogHost = (OemUnlockDialogHost) getTargetFragment();
        if (oemUnlockDialogHost == null
                || (restrictedSwitchPreference =
                                (oemUnlockPreferenceController =
                                                (OemUnlockPreferenceController)
                                                        ((DevelopmentSettingsDashboardFragment)
                                                                        oemUnlockDialogHost)
                                                                .getDevelopmentOptionsController(
                                                                        OemUnlockPreferenceController
                                                                                .class))
                                        .mPreference)
                        == null) {
            return;
        }
        oemUnlockPreferenceController.updateState(restrictedSwitchPreference);
    }
}
