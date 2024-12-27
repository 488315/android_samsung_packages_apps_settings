package com.android.settings.development;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.applications.ProcessStatsSummary;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DisableDevSettingsDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public static DisableDevSettingsDialogFragment newInstance() {
        return new DisableDevSettingsDialogFragment();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1591;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Fragment targetFragment = getTargetFragment();
        boolean z = targetFragment instanceof DevelopmentSettingsDashboardFragment;
        if (!z && !(targetFragment instanceof ProcessStatsSummary)) {
            Log.e("DisableDevSettingDlg", "getTargetFragment return unexpected type");
        }
        if (z) {
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment =
                    (DevelopmentSettingsDashboardFragment) targetFragment;
            if (i != -1) {
                developmentSettingsDashboardFragment.mSwitchBar.setChecked(true);
                return;
            } else {
                developmentSettingsDashboardFragment.disableDeveloperOptions();
                ((PowerManager) getContext().getSystemService(PowerManager.class)).reboot(null);
                return;
            }
        }
        if (targetFragment instanceof ProcessStatsSummary) {
            ProcessStatsSummary processStatsSummary = (ProcessStatsSummary) targetFragment;
            if (i != -1) {
                processStatsSummary.mForceEnablePssProfiling.setChecked(
                        Settings.Global.getInt(
                                        processStatsSummary.getContext().getContentResolver(),
                                        "force_enable_pss_profiling",
                                        0)
                                == 1);
                return;
            }
            Settings.Global.putInt(
                    processStatsSummary.getContext().getContentResolver(),
                    "force_enable_pss_profiling",
                    processStatsSummary.mForceEnablePssProfiling.mChecked ? 1 : 0);
            if (Flags.removeAppProfilerPssCollection()) {
                processStatsSummary.mMemoryInfoPrefCategory.setVisible(
                        processStatsSummary.mForceEnablePssProfiling.mChecked);
            }
            ((PowerManager) getContext().getSystemService(PowerManager.class)).reboot(null);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.bluetooth_disable_hw_offload_dialog_message);
        builder.setTitle(R.string.bluetooth_disable_hw_offload_dialog_title);
        builder.setPositiveButton(R.string.bluetooth_disable_hw_offload_dialog_confirm, this);
        builder.setNegativeButton(R.string.bluetooth_disable_hw_offload_dialog_cancel, this);
        return builder.create();
    }
}
