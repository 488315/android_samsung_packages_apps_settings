package com.android.settings.datetime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationToggleDisabledDialogFragment extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1876;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.location_time_zone_detection_location_is_off_dialog_title)
                .setIcon(R.drawable.ic_warning_24dp)
                .setMessage(R.string.location_time_zone_detection_location_is_off_dialog_message)
                .setPositiveButton(
                        R.string.location_time_zone_detection_location_is_off_dialog_ok_button,
                        new DialogInterface.OnClickListener() { // from class:
                            // com.android.settings.datetime.LocationToggleDisabledDialogFragment$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                LocationToggleDisabledDialogFragment
                                        locationToggleDisabledDialogFragment =
                                                LocationToggleDisabledDialogFragment.this;
                                locationToggleDisabledDialogFragment.getClass();
                                locationToggleDisabledDialogFragment
                                        .getContext()
                                        .startActivity(
                                                new Intent(
                                                                "android.settings.LOCATION_SOURCE_SETTINGS")
                                                        .setPackage(
                                                                locationToggleDisabledDialogFragment
                                                                        .getContext()
                                                                        .getPackageName()));
                            }
                        })
                .setNegativeButton(
                        R.string.location_time_zone_detection_location_is_off_dialog_cancel_button,
                        new ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0())
                .create();
    }
}
