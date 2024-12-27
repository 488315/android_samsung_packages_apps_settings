package com.android.settings.development;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemProperties;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NfcRebootDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnNfcRebootDialogConfirmedListener {}

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1932;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        OnNfcRebootDialogConfirmedListener onNfcRebootDialogConfirmedListener =
                (OnNfcRebootDialogConfirmedListener) getTargetFragment();
        if (onNfcRebootDialogConfirmedListener == null) {
            return;
        }
        if (i != -1) {
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment =
                    (DevelopmentSettingsDashboardFragment) onNfcRebootDialogConfirmedListener;
            ((NfcSnoopLogPreferenceController)
                                    developmentSettingsDashboardFragment
                                            .getDevelopmentOptionsController(
                                                    NfcSnoopLogPreferenceController.class))
                            .mChanged =
                    false;
            ((NfcVerboseVendorLogPreferenceController)
                                    developmentSettingsDashboardFragment
                                            .getDevelopmentOptionsController(
                                                    NfcVerboseVendorLogPreferenceController.class))
                            .mChanged =
                    false;
            return;
        }
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment2 =
                (DevelopmentSettingsDashboardFragment) onNfcRebootDialogConfirmedListener;
        if (((NfcSnoopLogPreferenceController)
                        developmentSettingsDashboardFragment2.getDevelopmentOptionsController(
                                NfcSnoopLogPreferenceController.class))
                .mChanged) {
            try {
                if (SystemProperties.get("persist.nfc.snoop_log_mode", "filtered")
                        .equals("filtered")) {
                    SystemProperties.set("persist.nfc.snoop_log_mode", "full");
                } else {
                    SystemProperties.set("persist.nfc.snoop_log_mode", "filtered");
                }
            } catch (RuntimeException e) {
                DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Fail to set nfc system property: "), "NfcSnoopLog");
            }
        }
        NfcVerboseVendorLogPreferenceController nfcVerboseVendorLogPreferenceController =
                (NfcVerboseVendorLogPreferenceController)
                        developmentSettingsDashboardFragment2.getDevelopmentOptionsController(
                                NfcVerboseVendorLogPreferenceController.class);
        if (nfcVerboseVendorLogPreferenceController.mChanged) {
            try {
                if (SystemProperties.get("persist.nfc.vendor_debug_enabled", "false")
                        .equals("false")) {
                    SystemProperties.set("persist.nfc.vendor_debug_enabled", "true");
                } else {
                    SystemProperties.set("persist.nfc.vendor_debug_enabled", "false");
                }
                nfcVerboseVendorLogPreferenceController.updateState(
                        nfcVerboseVendorLogPreferenceController.mPreference);
            } catch (RuntimeException e2) {
                DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                        e2,
                        new StringBuilder("Fail to set nfc system property: "),
                        "NfcVerboseVendorLog");
            }
        }
        ((PowerManager) getContext().getSystemService(PowerManager.class)).reboot(null);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.nfc_reboot_dialog_message);
        builder.setTitle(R.string.nfc_reboot_dialog_title);
        builder.setPositiveButton(R.string.nfc_reboot_dialog_confirm, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }
}
