package com.android.settings.development;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.development.AbstractEnableAdbPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EnableAdbWarningDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1222;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        AdbDialogHost adbDialogHost = (AdbDialogHost) getTargetFragment();
        if (adbDialogHost == null) {
            return;
        }
        if (i == -1) {
            ((AdbPreferenceController)
                            ((DevelopmentSettingsDashboardFragment) adbDialogHost)
                                    .getDevelopmentOptionsController(AdbPreferenceController.class))
                    .writeAdbSetting(true);
        } else {
            AdbPreferenceController adbPreferenceController =
                    (AdbPreferenceController)
                            ((DevelopmentSettingsDashboardFragment) adbDialogHost)
                                    .getDevelopmentOptionsController(AdbPreferenceController.class);
            adbPreferenceController.updateState(
                    ((AbstractEnableAdbPreferenceController) adbPreferenceController).mPreference);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.adb_warning_title);
        builder.setMessage(R.string.adb_warning_message);
        builder.setPositiveButton(R.string.common_ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        AdbDialogHost adbDialogHost = (AdbDialogHost) getTargetFragment();
        if (adbDialogHost == null) {
            return;
        }
        AdbPreferenceController adbPreferenceController =
                (AdbPreferenceController)
                        ((DevelopmentSettingsDashboardFragment) adbDialogHost)
                                .getDevelopmentOptionsController(AdbPreferenceController.class);
        adbPreferenceController.updateState(
                ((AbstractEnableAdbPreferenceController) adbPreferenceController).mPreference);
    }
}
