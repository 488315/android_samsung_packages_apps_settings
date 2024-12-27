package com.samsung.android.settings.asbase.routine.action.ui;

import android.os.Bundle;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class InCallVolumeActionActivity extends VolumeActionActivity {
    @Override // com.samsung.android.settings.asbase.routine.action.ui.VolumeActionActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle(R.string.sec_sound_in_call_volume);
        addVolumeRow(0, ApnSettings.MVNO_NONE, true);
    }
}
