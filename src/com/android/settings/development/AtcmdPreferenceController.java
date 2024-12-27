package com.android.settings.development;

import android.R;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemProperties;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AtcmdPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener,
                PreferenceControllerMixin,
                DialogInterface.OnClickListener {
    public boolean enabled;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "atcmd_developer_settings";
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            ((SwitchPreference) this.mPreference).setChecked(!this.enabled);
            dialogInterface.cancel();
        } else {
            boolean z = this.enabled;
            SystemProperties.set(
                    "persist.radio.block_atcmd.status", z ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN : "1");
            Toast.makeText(this.mContext, "3GPP AT commands : ".concat(z ? "Enable" : "Disable"), 0)
                    .show();
            new Handler()
                    .postDelayed(
                            new Runnable() { // from class:
                                // com.android.settings.development.AtcmdPreferenceController.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Intent intent =
                                            new Intent(
                                                    "com.android.internal.intent.action.REQUEST_SHUTDOWN");
                                    intent.setAction("android.intent.action.REBOOT");
                                    intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
                                    intent.putExtra(
                                            "android.intent.extra.REBOOT_REASON",
                                            "[SecSettings]Block AT Commands");
                                    intent.setFlags(268435456);
                                    try {
                                        ((AbstractPreferenceController)
                                                        AtcmdPreferenceController.this)
                                                .mContext.startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            1500L);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        this.enabled = ((Boolean) obj).booleanValue();
        new AlertDialog.Builder(this.mContext)
                .setIcon(R.drawable.ic_feedback_indicator)
                .setTitle(R.string.dialog_alert_title)
                .setMessage(com.android.settings.R.string.blockat_reboot_message)
                .setPositiveButton(com.android.settings.R.string.okay, this)
                .setNegativeButton(com.android.settings.R.string.cancel, this)
                .show();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((SwitchPreference) this.mPreference)
                .setChecked(SystemProperties.getInt("persist.radio.block_atcmd.status", 1) == 0);
    }
}
