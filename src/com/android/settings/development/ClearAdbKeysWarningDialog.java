package com.android.settings.development;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ClearAdbKeysWarningDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1223;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        AdbClearKeysDialogHost adbClearKeysDialogHost =
                (AdbClearKeysDialogHost) getTargetFragment();
        if (adbClearKeysDialogHost == null) {
            return;
        }
        ClearAdbKeysPreferenceController clearAdbKeysPreferenceController =
                (ClearAdbKeysPreferenceController)
                        ((DevelopmentSettingsDashboardFragment) adbClearKeysDialogHost)
                                .getDevelopmentOptionsController(
                                        ClearAdbKeysPreferenceController.class);
        clearAdbKeysPreferenceController.getClass();
        try {
            clearAdbKeysPreferenceController.mAdbManager.clearDebuggingKeys();
        } catch (RemoteException e) {
            Log.e("ClearAdbPrefCtrl", "Unable to clear adb keys", e);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.adb_keys_warning_message);
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }
}
