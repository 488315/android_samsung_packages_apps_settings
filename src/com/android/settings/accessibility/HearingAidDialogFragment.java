package com.android.settings.accessibility;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.bluetooth.BluetoothPairingDetail;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class HearingAidDialogFragment extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1512;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.accessibility_hearingaid_pair_instructions_title);
        builder.setMessage(R.string.accessibility_hearingaid_pair_instructions_message);
        builder.setPositiveButton(
                R.string.accessibility_hearingaid_instruction_continue_button,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.accessibility.HearingAidDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(HearingAidDialogFragment.this.getActivity());
                        String name = BluetoothPairingDetail.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        launchRequest.mSourceMetricsCategory = 1512;
                        subSettingLauncher.launch();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }
}
