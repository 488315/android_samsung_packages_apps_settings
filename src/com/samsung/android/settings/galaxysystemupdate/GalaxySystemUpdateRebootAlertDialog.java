package com.samsung.android.settings.galaxysystemupdate;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.os.PowerManager;

import com.android.internal.app.AlertActivity;
import com.android.settings.R;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GalaxySystemUpdateRebootAlertDialog extends AlertActivity
        implements DialogInterface.OnClickListener {
    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        ((PowerManager) getApplicationContext().getSystemService("power")).reboot(null);
        finish();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if ((getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32) {
            setTheme(4);
        } else {
            setTheme(5);
        }
        getWindow().setGravity(80);
        if (Utils.isTablet()) {
            ((AlertActivity) this).mAlertParams.mTitle =
                    getString(R.string.galaxy_system_restart_tablet_body);
            ((AlertActivity) this).mAlertParams.mMessage =
                    getString(R.string.galaxy_system_update_tablet_restart_description);
        } else {
            ((AlertActivity) this).mAlertParams.mTitle =
                    getString(R.string.galaxy_system_restart_phone_body);
            ((AlertActivity) this).mAlertParams.mMessage =
                    getString(R.string.galaxy_system_update_phone_restart_description);
        }
        ((AlertActivity) this)
                .mAlert.setButton(
                        -1,
                        (CharSequence) null,
                        (DialogInterface.OnClickListener) null,
                        (Message) null);
        ((AlertActivity) this).mAlertParams.mPositiveButtonText = getString(R.string.restart);
        ((AlertActivity) this).mAlertParams.mPositiveButtonListener = this;
        ((AlertActivity) this)
                .mAlert.setButton(
                        -2,
                        (CharSequence) null,
                        (DialogInterface.OnClickListener) null,
                        (Message) null);
        ((AlertActivity) this).mAlertParams.mNegativeButtonText = getString(R.string.cancel);
        ((AlertActivity) this).mAlertParams.mNegativeButtonListener = this;
        setupAlert();
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final void onPause() {
        super.onPause();
    }

    public final void onResume() {
        try {
            super.onResume();
        } catch (IllegalArgumentException unused) {
        }
    }
}
