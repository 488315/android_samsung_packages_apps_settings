package com.samsung.android.settings.galaxysystemupdate;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;

import com.android.internal.app.AlertActivity;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GalaxySystemUpdateAlertDialog extends AlertActivity
        implements DialogInterface.OnClickListener {
    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
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
        ((AlertActivity) this).mAlertParams.mTitle = getString(R.string.galaxy_system_update);
        ((AlertActivity) this).mAlertParams.mMessage =
                getString(R.string.galaxy_system_update_alert_message);
        ((AlertActivity) this)
                .mAlert.setButton(
                        -1,
                        (CharSequence) null,
                        (DialogInterface.OnClickListener) null,
                        (Message) null);
        ((AlertActivity) this).mAlertParams.mPositiveButtonText = getString(R.string.common_ok);
        ((AlertActivity) this).mAlertParams.mPositiveButtonListener = this;
        setupAlert();
        Context applicationContext = getApplicationContext();
        if (Settings.Global.getInt(
                        applicationContext.getContentResolver(),
                        "galaxy_system_update_notification_once",
                        -1)
                == 1) {
            return;
        }
        Settings.Global.putInt(
                applicationContext.getContentResolver(),
                "galaxy_system_update_notification_once",
                1);
        Settings.Global.putInt(
                applicationContext.getContentResolver(),
                "galaxy_system_update",
                Settings.Global.getInt(
                                        applicationContext.getContentResolver(),
                                        "galaxy_system_update",
                                        1)
                                == 1
                        ? 1
                        : -1);
        Settings.Global.putInt(
                applicationContext.getContentResolver(),
                "galaxy_system_update_use_wifi_only",
                Settings.Global.getInt(
                                        applicationContext.getContentResolver(),
                                        "galaxy_system_update_use_wifi_only",
                                        1)
                                == 1
                        ? 1
                        : -1);
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
