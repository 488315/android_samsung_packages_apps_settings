package com.samsung.android.settings.asbase.routine.action.ui;

import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MediaVolumeActionActivity extends VolumeActionActivity {
    @Override // com.samsung.android.settings.asbase.routine.action.ui.VolumeActionActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle(R.string.sec_media_volume_action_label);
        String string = getString(R.string.audio_device_type_builtin_speaker);
        if (Utils.isTablet()) {
            string = getString(R.string.audio_device_type_builtin_speaker_tablet);
        }
        addVolumeRow(101, string, false);
        addVolumeRow(102, getString(R.string.audio_device_type_headset), false);
        addVolumeRow(105, getString(R.string.audio_device_type_bt_a2dp), true);
    }
}
