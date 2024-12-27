package com.android.settings.development;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemProperties;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothRebootDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnRebootDialogListener {}

    public static void show(
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        FragmentManagerImpl supportFragmentManager =
                developmentSettingsDashboardFragment.getActivity().getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag("BluetoothReboot") == null) {
            BluetoothRebootDialog bluetoothRebootDialog = new BluetoothRebootDialog();
            bluetoothRebootDialog.setTargetFragment(developmentSettingsDashboardFragment, 0);
            bluetoothRebootDialog.show(supportFragmentManager, "BluetoothReboot");
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1441;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        BluetoothAdapter bluetoothAdapter;
        OnRebootDialogListener onRebootDialogListener =
                (OnRebootDialogListener) getTargetFragment();
        if (onRebootDialogListener == null) {
            return;
        }
        if (i != -1) {
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment =
                    (DevelopmentSettingsDashboardFragment) onRebootDialogListener;
            ((BluetoothA2dpHwOffloadPreferenceController)
                                    developmentSettingsDashboardFragment
                                            .getDevelopmentOptionsController(
                                                    BluetoothA2dpHwOffloadPreferenceController
                                                            .class))
                            .mChanged =
                    false;
            ((BluetoothLeAudioHwOffloadPreferenceController)
                                    developmentSettingsDashboardFragment
                                            .getDevelopmentOptionsController(
                                                    BluetoothLeAudioHwOffloadPreferenceController
                                                            .class))
                            .mChanged =
                    false;
            ((BluetoothLeAudioPreferenceController)
                                    developmentSettingsDashboardFragment
                                            .getDevelopmentOptionsController(
                                                    BluetoothLeAudioPreferenceController.class))
                            .mChanged =
                    false;
            ((BluetoothLeAudioModePreferenceController)
                                    developmentSettingsDashboardFragment
                                            .getDevelopmentOptionsController(
                                                    BluetoothLeAudioModePreferenceController.class))
                            .mChanged =
                    false;
            return;
        }
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment2 =
                (DevelopmentSettingsDashboardFragment) onRebootDialogListener;
        if (((BluetoothA2dpHwOffloadPreferenceController)
                        developmentSettingsDashboardFragment2.getDevelopmentOptionsController(
                                BluetoothA2dpHwOffloadPreferenceController.class))
                .mChanged) {
            boolean z =
                    SystemProperties.getBoolean("persist.bluetooth.a2dp_offload.disabled", false);
            boolean z2 = !z;
            SystemProperties.set("persist.bluetooth.a2dp_offload.disabled", Boolean.toString(z2));
            if (!z) {
                SystemProperties.set(
                        "persist.bluetooth.leaudio_offload.disabled", Boolean.toString(z2));
            }
        }
        if (((BluetoothLeAudioHwOffloadPreferenceController)
                        developmentSettingsDashboardFragment2.getDevelopmentOptionsController(
                                BluetoothLeAudioHwOffloadPreferenceController.class))
                .mChanged) {
            SystemProperties.set(
                    "persist.bluetooth.leaudio_offload.disabled",
                    Boolean.toString(
                            !SystemProperties.getBoolean(
                                    "persist.bluetooth.leaudio_offload.disabled", false)));
        }
        BluetoothLeAudioPreferenceController bluetoothLeAudioPreferenceController =
                (BluetoothLeAudioPreferenceController)
                        developmentSettingsDashboardFragment2.getDevelopmentOptionsController(
                                BluetoothLeAudioPreferenceController.class);
        if (bluetoothLeAudioPreferenceController.mChanged
                && (bluetoothAdapter = bluetoothLeAudioPreferenceController.mBluetoothAdapter)
                        != null) {
            SystemProperties.set(
                    "persist.bluetooth.leaudio_switcher.disabled",
                    Boolean.toString(!(bluetoothAdapter.isLeAudioSupported() != 10)));
        }
        BluetoothLeAudioModePreferenceController bluetoothLeAudioModePreferenceController =
                (BluetoothLeAudioModePreferenceController)
                        developmentSettingsDashboardFragment2.getDevelopmentOptionsController(
                                BluetoothLeAudioModePreferenceController.class);
        if (bluetoothLeAudioModePreferenceController.mChanged) {
            SystemProperties.set(
                    "persist.bluetooth.leaudio_dynamic_switcher.mode",
                    bluetoothLeAudioModePreferenceController.mNewMode);
        }
        ((PowerManager) getContext().getSystemService(PowerManager.class)).reboot(null);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.bluetooth_disable_hw_offload_dialog_message);
        builder.setTitle(R.string.bluetooth_disable_hw_offload_dialog_title);
        builder.setPositiveButton(R.string.bluetooth_disable_hw_offload_dialog_confirm, this);
        builder.setNegativeButton(R.string.bluetooth_disable_hw_offload_dialog_cancel, this);
        return builder.create();
    }
}
