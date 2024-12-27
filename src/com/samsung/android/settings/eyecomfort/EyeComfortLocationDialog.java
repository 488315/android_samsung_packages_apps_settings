package com.samsung.android.settings.eyecomfort;

import android.R;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EyeComfortLocationDialog extends AlertActivity
        implements DialogInterface.OnClickListener {
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        super.onApplyThemeResource(
                theme,
                (getResources().getConfiguration().uiMode & 48) == 32
                        ? R.style.Theme.DeviceDefault.Dialog.Alert
                        : R.style.Theme.DeviceDefault.Light.Dialog.Alert,
                z);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            Settings.Secure.putInt(getContentResolver(), "location_mode", 3);
            Settings.System.putInt(getContentResolver(), "blue_light_filter", 1);
            LoggingHelper.insertEventLogging(46, 4221, 1L);
            Intent intent = new Intent();
            intent.setComponent(
                    new ComponentName(
                            "com.samsung.android.bluelightfilter",
                            "com.samsung.android.bluelightfilter.BlueLightFilterService"));
            intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", 24);
            getApplicationContext().startService(intent);
        }
        finish();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle =
                getText(
                        com.android.settings.R.string
                                .sec_blue_light_filter_dlg_turn_on_location_title);
        alertParams.mMessage =
                getText(com.android.settings.R.string.sec_blue_light_filter_dlg_turn_on_location);
        alertParams.mPositiveButtonText =
                getResources().getString(com.android.settings.R.string.sec_dlg_turn_on);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonText =
                getResources().getString(com.android.settings.R.string.dlg_cancel);
        alertParams.mNegativeButtonListener = this;
        getWindow().setGravity(80);
        setupAlert();
    }
}
