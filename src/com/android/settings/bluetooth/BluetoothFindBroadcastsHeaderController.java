package com.android.settings.bluetooth;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Collections;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothFindBroadcastsHeaderController extends BluetoothDetailsController {
    public BluetoothFindBroadcastsFragment mBluetoothFindBroadcastsFragment;
    public PreferenceCategory mBroadcastSourceList;
    public LinearLayout mBtnBroadcastLayout;
    public Button mBtnFindBroadcast;
    public Button mBtnLeaveBroadcast;
    public LayoutPreference mLayoutPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_find_broadcast_header";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        this.mLayoutPreference =
                (LayoutPreference)
                        preferenceScreen.findPreference("bluetooth_find_broadcast_header");
        this.mBroadcastSourceList =
                (PreferenceCategory) preferenceScreen.findPreference("broadcast_source_list");
        refresh();
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        if (this.mCachedDevice != null) {
            refresh();
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        CachedBluetoothDevice cachedBluetoothDevice;
        LayoutPreference layoutPreference = this.mLayoutPreference;
        if (layoutPreference == null || (cachedBluetoothDevice = this.mCachedDevice) == null) {
            return;
        }
        ((TextView) layoutPreference.mRootView.findViewById(R.id.entity_header_title))
                .setText(cachedBluetoothDevice.getName());
        ((TextView) this.mLayoutPreference.mRootView.findViewById(R.id.entity_header_summary))
                .setText(ApnSettings.MVNO_NONE);
        Button button =
                (Button) this.mLayoutPreference.mRootView.findViewById(R.id.button_find_broadcast);
        this.mBtnFindBroadcast = button;
        final int i = 1;
        button.setOnClickListener(
                new View.OnClickListener(this) { // from class:
                    // com.android.settings.bluetooth.BluetoothFindBroadcastsHeaderController$$ExternalSyntheticLambda1
                    public final /* synthetic */ BluetoothFindBroadcastsHeaderController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CachedBluetoothDevice cachedBluetoothDevice2;
                        int i2 = i;
                        BluetoothFindBroadcastsHeaderController
                                bluetoothFindBroadcastsHeaderController = this.f$0;
                        switch (i2) {
                            case 0:
                                BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment =
                                        bluetoothFindBroadcastsHeaderController
                                                .mBluetoothFindBroadcastsFragment;
                                if (bluetoothFindBroadcastsFragment != null) {
                                    LocalBluetoothLeBroadcastAssistant
                                            localBluetoothLeBroadcastAssistant =
                                                    bluetoothFindBroadcastsFragment
                                                            .mLeBroadcastAssistant;
                                    if (localBluetoothLeBroadcastAssistant != null
                                            && (cachedBluetoothDevice2 =
                                                            bluetoothFindBroadcastsFragment
                                                                    .mCachedDevice)
                                                    != null) {
                                        localBluetoothLeBroadcastAssistant.removeSource(
                                                cachedBluetoothDevice2.mDevice,
                                                bluetoothFindBroadcastsFragment.mSourceId);
                                        break;
                                    } else {
                                        Log.w(
                                                "BtFindBroadcastsFrg",
                                                "leaveBroadcastSession: LeBroadcastAssistant or"
                                                    + " CachedDevice is null!");
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment2 =
                                        bluetoothFindBroadcastsHeaderController
                                                .mBluetoothFindBroadcastsFragment;
                                if (bluetoothFindBroadcastsFragment2 != null) {
                                    LocalBluetoothLeBroadcastAssistant
                                            localBluetoothLeBroadcastAssistant2 =
                                                    bluetoothFindBroadcastsFragment2
                                                            .mLeBroadcastAssistant;
                                    if (localBluetoothLeBroadcastAssistant2 != null) {
                                        localBluetoothLeBroadcastAssistant2
                                                .startSearchingForSources(Collections.emptyList());
                                        break;
                                    } else {
                                        Log.w(
                                                "BtFindBroadcastsFrg",
                                                "scanBroadcastSource: LeBroadcastAssistant is"
                                                    + " null!");
                                        break;
                                    }
                                }
                                break;
                            default:
                                bluetoothFindBroadcastsHeaderController.getClass();
                                Intent intent =
                                        new Intent(
                                                ((BluetoothDetailsController)
                                                                bluetoothFindBroadcastsHeaderController)
                                                        .mContext,
                                                (Class<?>) QrCodeScanModeActivity.class);
                                intent.setAction(
                                                "android.settings.BLUETOOTH_LE_AUDIO_QR_CODE_SCANNER")
                                        .putExtra("bluetooth_sink_is_group", true)
                                        .putExtra(
                                                "bluetooth_device_sink",
                                                bluetoothFindBroadcastsHeaderController
                                                        .mCachedDevice
                                                        .mDevice);
                                bluetoothFindBroadcastsHeaderController
                                        .mBluetoothFindBroadcastsFragment.startActivityForResult(
                                        intent, 0);
                                break;
                        }
                    }
                });
        this.mBtnBroadcastLayout =
                (LinearLayout)
                        this.mLayoutPreference.mRootView.findViewById(R.id.button_broadcast_layout);
        Button button2 =
                (Button) this.mLayoutPreference.mRootView.findViewById(R.id.button_leave_broadcast);
        this.mBtnLeaveBroadcast = button2;
        final int i2 = 0;
        button2.setOnClickListener(
                new View.OnClickListener(this) { // from class:
                    // com.android.settings.bluetooth.BluetoothFindBroadcastsHeaderController$$ExternalSyntheticLambda1
                    public final /* synthetic */ BluetoothFindBroadcastsHeaderController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CachedBluetoothDevice cachedBluetoothDevice2;
                        int i22 = i2;
                        BluetoothFindBroadcastsHeaderController
                                bluetoothFindBroadcastsHeaderController = this.f$0;
                        switch (i22) {
                            case 0:
                                BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment =
                                        bluetoothFindBroadcastsHeaderController
                                                .mBluetoothFindBroadcastsFragment;
                                if (bluetoothFindBroadcastsFragment != null) {
                                    LocalBluetoothLeBroadcastAssistant
                                            localBluetoothLeBroadcastAssistant =
                                                    bluetoothFindBroadcastsFragment
                                                            .mLeBroadcastAssistant;
                                    if (localBluetoothLeBroadcastAssistant != null
                                            && (cachedBluetoothDevice2 =
                                                            bluetoothFindBroadcastsFragment
                                                                    .mCachedDevice)
                                                    != null) {
                                        localBluetoothLeBroadcastAssistant.removeSource(
                                                cachedBluetoothDevice2.mDevice,
                                                bluetoothFindBroadcastsFragment.mSourceId);
                                        break;
                                    } else {
                                        Log.w(
                                                "BtFindBroadcastsFrg",
                                                "leaveBroadcastSession: LeBroadcastAssistant or"
                                                    + " CachedDevice is null!");
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment2 =
                                        bluetoothFindBroadcastsHeaderController
                                                .mBluetoothFindBroadcastsFragment;
                                if (bluetoothFindBroadcastsFragment2 != null) {
                                    LocalBluetoothLeBroadcastAssistant
                                            localBluetoothLeBroadcastAssistant2 =
                                                    bluetoothFindBroadcastsFragment2
                                                            .mLeBroadcastAssistant;
                                    if (localBluetoothLeBroadcastAssistant2 != null) {
                                        localBluetoothLeBroadcastAssistant2
                                                .startSearchingForSources(Collections.emptyList());
                                        break;
                                    } else {
                                        Log.w(
                                                "BtFindBroadcastsFrg",
                                                "scanBroadcastSource: LeBroadcastAssistant is"
                                                    + " null!");
                                        break;
                                    }
                                }
                                break;
                            default:
                                bluetoothFindBroadcastsHeaderController.getClass();
                                Intent intent =
                                        new Intent(
                                                ((BluetoothDetailsController)
                                                                bluetoothFindBroadcastsHeaderController)
                                                        .mContext,
                                                (Class<?>) QrCodeScanModeActivity.class);
                                intent.setAction(
                                                "android.settings.BLUETOOTH_LE_AUDIO_QR_CODE_SCANNER")
                                        .putExtra("bluetooth_sink_is_group", true)
                                        .putExtra(
                                                "bluetooth_device_sink",
                                                bluetoothFindBroadcastsHeaderController
                                                        .mCachedDevice
                                                        .mDevice);
                                bluetoothFindBroadcastsHeaderController
                                        .mBluetoothFindBroadcastsFragment.startActivityForResult(
                                        intent, 0);
                                break;
                        }
                    }
                });
        final int i3 = 2;
        ((Button) this.mLayoutPreference.mRootView.findViewById(R.id.button_scan_qr_code))
                .setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.android.settings.bluetooth.BluetoothFindBroadcastsHeaderController$$ExternalSyntheticLambda1
                            public final /* synthetic */ BluetoothFindBroadcastsHeaderController
                                    f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                CachedBluetoothDevice cachedBluetoothDevice2;
                                int i22 = i3;
                                BluetoothFindBroadcastsHeaderController
                                        bluetoothFindBroadcastsHeaderController = this.f$0;
                                switch (i22) {
                                    case 0:
                                        BluetoothFindBroadcastsFragment
                                                bluetoothFindBroadcastsFragment =
                                                        bluetoothFindBroadcastsHeaderController
                                                                .mBluetoothFindBroadcastsFragment;
                                        if (bluetoothFindBroadcastsFragment != null) {
                                            LocalBluetoothLeBroadcastAssistant
                                                    localBluetoothLeBroadcastAssistant =
                                                            bluetoothFindBroadcastsFragment
                                                                    .mLeBroadcastAssistant;
                                            if (localBluetoothLeBroadcastAssistant != null
                                                    && (cachedBluetoothDevice2 =
                                                                    bluetoothFindBroadcastsFragment
                                                                            .mCachedDevice)
                                                            != null) {
                                                localBluetoothLeBroadcastAssistant.removeSource(
                                                        cachedBluetoothDevice2.mDevice,
                                                        bluetoothFindBroadcastsFragment.mSourceId);
                                                break;
                                            } else {
                                                Log.w(
                                                        "BtFindBroadcastsFrg",
                                                        "leaveBroadcastSession:"
                                                            + " LeBroadcastAssistant or"
                                                            + " CachedDevice is null!");
                                                break;
                                            }
                                        }
                                        break;
                                    case 1:
                                        BluetoothFindBroadcastsFragment
                                                bluetoothFindBroadcastsFragment2 =
                                                        bluetoothFindBroadcastsHeaderController
                                                                .mBluetoothFindBroadcastsFragment;
                                        if (bluetoothFindBroadcastsFragment2 != null) {
                                            LocalBluetoothLeBroadcastAssistant
                                                    localBluetoothLeBroadcastAssistant2 =
                                                            bluetoothFindBroadcastsFragment2
                                                                    .mLeBroadcastAssistant;
                                            if (localBluetoothLeBroadcastAssistant2 != null) {
                                                localBluetoothLeBroadcastAssistant2
                                                        .startSearchingForSources(
                                                                Collections.emptyList());
                                                break;
                                            } else {
                                                Log.w(
                                                        "BtFindBroadcastsFrg",
                                                        "scanBroadcastSource: LeBroadcastAssistant"
                                                            + " is null!");
                                                break;
                                            }
                                        }
                                        break;
                                    default:
                                        bluetoothFindBroadcastsHeaderController.getClass();
                                        Intent intent =
                                                new Intent(
                                                        ((BluetoothDetailsController)
                                                                        bluetoothFindBroadcastsHeaderController)
                                                                .mContext,
                                                        (Class<?>) QrCodeScanModeActivity.class);
                                        intent.setAction(
                                                        "android.settings.BLUETOOTH_LE_AUDIO_QR_CODE_SCANNER")
                                                .putExtra("bluetooth_sink_is_group", true)
                                                .putExtra(
                                                        "bluetooth_device_sink",
                                                        bluetoothFindBroadcastsHeaderController
                                                                .mCachedDevice
                                                                .mDevice);
                                        bluetoothFindBroadcastsHeaderController
                                                .mBluetoothFindBroadcastsFragment
                                                .startActivityForResult(intent, 0);
                                        break;
                                }
                            }
                        });
        updateHeaderLayout();
    }

    public final void updateHeaderLayout() {
        CachedBluetoothDevice cachedBluetoothDevice;
        if (this.mBroadcastSourceList.getPreferenceCount() > 0) {
            this.mBtnFindBroadcast.setVisibility(8);
            this.mBtnBroadcastLayout.setVisibility(0);
        } else {
            this.mBtnFindBroadcast.setVisibility(0);
            this.mBtnBroadcastLayout.setVisibility(8);
        }
        this.mBtnLeaveBroadcast.setEnabled(false);
        BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment =
                this.mBluetoothFindBroadcastsFragment;
        if (bluetoothFindBroadcastsFragment == null
                || (cachedBluetoothDevice = this.mCachedDevice) == null) {
            return;
        }
        LocalBluetoothManager localBluetoothManager = bluetoothFindBroadcastsFragment.mManager;
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = null;
        if (localBluetoothManager == null) {
            Log.w("BtFindBroadcastsFrg", "getLeBroadcastAssistant: LocalBluetoothManager is null!");
        } else {
            LocalBluetoothProfileManager localBluetoothProfileManager =
                    localBluetoothManager.mProfileManager;
            if (localBluetoothProfileManager == null) {
                Log.w(
                        "BtFindBroadcastsFrg",
                        "getLeBroadcastAssistant: LocalBluetoothProfileManager is null!");
            } else {
                localBluetoothLeBroadcastAssistant =
                        localBluetoothProfileManager.mLeAudioBroadcastAssistant;
            }
        }
        if (localBluetoothLeBroadcastAssistant == null
                || localBluetoothLeBroadcastAssistant.getConnectionStatus(
                                cachedBluetoothDevice.mDevice)
                        != 2) {
            return;
        }
        this.mBtnLeaveBroadcast.setEnabled(true);
    }
}
