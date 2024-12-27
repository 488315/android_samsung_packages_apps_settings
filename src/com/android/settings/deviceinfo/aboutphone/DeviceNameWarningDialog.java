package com.android.settings.deviceinfo.aboutphone;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.deviceinfo.DeviceNamePreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DeviceNameWarningDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2006;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        MyDeviceInfoFragment myDeviceInfoFragment = (MyDeviceInfoFragment) getTargetFragment();
        if (i == -1) {
            ((DeviceNamePreferenceController)
                            myDeviceInfoFragment.use(DeviceNamePreferenceController.class))
                    .updateDeviceName(true);
        } else {
            ((DeviceNamePreferenceController)
                            myDeviceInfoFragment.use(DeviceNamePreferenceController.class))
                    .updateDeviceName(false);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.my_device_info_device_name_preference_title);
        builder.setMessage(R.string.about_phone_device_name_warning);
        builder.P.mCancelable = false;
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }
}
