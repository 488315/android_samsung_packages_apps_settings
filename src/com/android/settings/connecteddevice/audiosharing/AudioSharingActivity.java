package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

import com.android.settings.SettingsActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingActivity extends SettingsActivity {
    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return AudioSharingDashboardFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        BluetoothAdapter.getDefaultAdapter();
        finish();
    }
}
