package com.samsung.android.settings.bluetooth.lebroadcast;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.BluetoothBroadcastDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBluetoothLeBroadcastAssistantActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setFinishOnTouchOutside(true);
        requestWindowFeature(1);
        if (bundle == null) {
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            BackStackRecord m =
                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                            supportFragmentManager, supportFragmentManager);
            Fragment findFragmentByTag =
                    getSupportFragmentManager()
                            .findFragmentByTag("bluetooth.broadcastactivity.fragment");
            if (findFragmentByTag != null) {
                m.remove(findFragmentByTag);
            }
            m.addToBackStack(null);
            BluetoothBroadcastDialog bluetoothBroadcastDialog = new BluetoothBroadcastDialog();
            bluetoothBroadcastDialog.setCancelable(true);
            bluetoothBroadcastDialog.show(
                    getSupportFragmentManager(), "bluetooth.broadcastactivity.fragment");
        }
        getWindow().setGravity(80);
    }
}
