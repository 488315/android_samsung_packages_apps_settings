package com.android.settings.display;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TwilightLocationDialog {
    public static void show(final Context context) {
        new AlertDialog.Builder(context)
                .setPositiveButton(
                        R.string.twilight_mode_launch_location,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.display.TwilightLocationDialog$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                Context context2 = context;
                                Log.d("TwilightLocationDialog", "clicked forget");
                                Intent intent = new Intent();
                                intent.setClass(context2, Settings.LocationSettingsActivity.class);
                                context2.startActivity(intent);
                            }
                        })
                .setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null)
                .setMessage(R.string.twilight_mode_location_off_dialog_message)
                .create()
                .show();
    }
}
