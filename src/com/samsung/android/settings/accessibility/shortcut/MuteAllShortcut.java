package com.samsung.android.settings.accessibility.shortcut;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MuteAllShortcut extends AppCompatActivity
        implements DialogInterface.OnDismissListener {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        boolean z = !(Settings.System.getInt(getContentResolver(), "all_sound_off", 0) != 0);
        if (SecAccessibilityUtils.checkAutoAnsweringMemo(z, this, this, null)) {
            SecAccessibilityUtils.enableMuteAllSounds(this, z);
            Toast.makeText(
                            this,
                            z
                                    ? getString(
                                            R.string.accessibility_shortcut_on,
                                            getString(R.string.mute_all_sounds_title))
                                    : getString(
                                            R.string.accessibility_shortcut_off,
                                            getString(R.string.mute_all_sounds_title)),
                            0)
                    .show();
            finish();
        } else {
            setTitle(ApnSettings.MVNO_NONE);
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.flags = 2;
            attributes.dimAmount = 0.3f;
            getWindow().setAttributes(attributes);
        }
    }
}
