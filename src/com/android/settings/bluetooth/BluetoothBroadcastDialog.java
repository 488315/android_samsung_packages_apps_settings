package com.android.settings.bluetooth;

import android.app.Dialog;
import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcast;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothBroadcastDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnDismissListener {
    public FragmentActivity mContext;
    public CharSequence mCurrentAppLabel = "unknown";
    public String mDeviceAddress;
    public boolean mIsMediaStreaming;
    public LocalBluetoothManager mLocalBluetoothManager;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2018;
    }

    public final void launchFindBroadcastsActivity() {
        Intent intent = new Intent();
        intent.setAction("android.settings.LE_ASSISTANT_SETTINGS");
        intent.putExtra("device_address", this.mDeviceAddress);
        intent.addFlags(268435456);
        startActivity(intent);
        this.mContext.sendBroadcast(
                new Intent()
                        .setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)
                        .setAction("com.android.settings.panel.action.CLOSE_PANEL"));
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        dismissInternal(false, false);
        getActivity().finish();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        this.mCurrentAppLabel = getActivity().getIntent().getCharSequenceExtra("app_label");
        this.mDeviceAddress = getActivity().getIntent().getStringExtra("device_address");
        this.mIsMediaStreaming =
                getActivity().getIntent().getBooleanExtra("media_streaming", false);
        this.mLocalBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        if (!getActivity().getIntent().getBooleanExtra("force_assistant", false)) {
            this.mShowsDialog = true;
            return;
        }
        launchFindBroadcastsActivity();
        dismissInternal(false, false);
        getActivity().finish();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        View inflate = View.inflate(this.mContext, R.layout.broadcast_dialog, null);
        TextView textView = (TextView) inflate.findViewById(R.id.dialog_title);
        TextView textView2 = (TextView) inflate.findViewById(R.id.dialog_subtitle);
        Button button = (Button) inflate.findViewById(R.id.positive_btn);
        if (this.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast == null
                || !this.mIsMediaStreaming) {
            textView.setText(this.mContext.getString(R.string.bluetooth_find_broadcast));
            textView2.setText(
                    this.mContext.getString(R.string.bluetooth_broadcast_dialog_find_message));
            button.setVisibility(8);
        } else {
            textView.setText(this.mContext.getString(R.string.bluetooth_broadcast_dialog_title));
            textView2.setText(
                    this.mContext.getString(R.string.bluetooth_broadcast_dialog_broadcast_message));
            button.setVisibility(0);
            if (TextUtils.isEmpty(this.mCurrentAppLabel)) {
                button.setText(this.mContext.getString(R.string.bluetooth_broadcast_dialog_title));
            } else {
                button.setText(
                        this.mContext.getString(
                                R.string.bluetooth_broadcast_dialog_broadcast_app,
                                String.valueOf(this.mCurrentAppLabel)));
            }
            final int i = 0;
            button.setOnClickListener(
                    new View.OnClickListener(this) { // from class:
                        // com.android.settings.bluetooth.BluetoothBroadcastDialog$$ExternalSyntheticLambda0
                        public final /* synthetic */ BluetoothBroadcastDialog f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
                            LocalBluetoothProfileManager localBluetoothProfileManager;
                            int i2 = i;
                            BluetoothBroadcastDialog bluetoothBroadcastDialog = this.f$0;
                            switch (i2) {
                                case 0:
                                    LocalBluetoothManager localBluetoothManager =
                                            bluetoothBroadcastDialog.mLocalBluetoothManager;
                                    byte[] bArr = null;
                                    if (localBluetoothManager == null
                                            || (localBluetoothProfileManager =
                                                            localBluetoothManager.mProfileManager)
                                                    == null
                                            || (localBluetoothLeBroadcast =
                                                            localBluetoothProfileManager
                                                                    .mLeAudioBroadcast)
                                                    == null) {
                                        Log.d(
                                                "BTBroadcastsDialog",
                                                "Can not get LE Audio Broadcast Profile");
                                        localBluetoothLeBroadcast = null;
                                    }
                                    if (localBluetoothLeBroadcast != null) {
                                        localBluetoothLeBroadcast.mNewAppSourceName =
                                                String.valueOf(
                                                        bluetoothBroadcastDialog.mCurrentAppLabel);
                                        if (localBluetoothLeBroadcast.mServiceBroadcast == null) {
                                            Log.d(
                                                    "LocalBluetoothLeBroadcast",
                                                    "The BluetoothLeBroadcast is null when starting"
                                                        + " the broadcast.");
                                        } else {
                                            String str = localBluetoothLeBroadcast.mProgramInfo;
                                            DialogFragment$$ExternalSyntheticOutline0.m(
                                                    "startBroadcast: language = null ,programInfo ="
                                                        + " ",
                                                    str,
                                                    "LocalBluetoothLeBroadcast");
                                            BluetoothLeAudioContentMetadata build =
                                                    localBluetoothLeBroadcast
                                                            .mBuilder
                                                            .setLanguage((String) null)
                                                            .setProgramInfo(str)
                                                            .build();
                                            localBluetoothLeBroadcast
                                                            .mBluetoothLeAudioContentMetadata =
                                                    build;
                                            BluetoothLeBroadcast bluetoothLeBroadcast =
                                                    localBluetoothLeBroadcast.mServiceBroadcast;
                                            byte[] bArr2 = localBluetoothLeBroadcast.mBroadcastCode;
                                            if (bArr2 != null && bArr2.length > 0) {
                                                bArr = bArr2;
                                            }
                                            bluetoothLeBroadcast.startBroadcast(build, bArr);
                                        }
                                        bluetoothBroadcastDialog.mContext.sendBroadcast(
                                                new Intent()
                                                        .setPackage("com.android.systemui")
                                                        .setAction(
                                                                "com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_BROADCAST_DIALOG")
                                                        .putExtra(
                                                                "package_name",
                                                                bluetoothBroadcastDialog
                                                                        .getActivity()
                                                                        .getPackageName()));
                                        bluetoothBroadcastDialog.mContext.sendBroadcast(
                                                new Intent()
                                                        .setPackage(
                                                                KnoxVpnPolicyConstants
                                                                        .ANDROID_SETTINGS_PKG)
                                                        .setAction(
                                                                "com.android.settings.panel.action.CLOSE_PANEL"));
                                    } else {
                                        Log.d(
                                                "BTBroadcastsDialog",
                                                "Can not broadcast successfully");
                                    }
                                    bluetoothBroadcastDialog.dismissInternal(false, false);
                                    break;
                                case 1:
                                    bluetoothBroadcastDialog.launchFindBroadcastsActivity();
                                    bluetoothBroadcastDialog.dismissInternal(false, false);
                                    break;
                                default:
                                    bluetoothBroadcastDialog.dismissInternal(false, false);
                                    bluetoothBroadcastDialog.getActivity().finish();
                                    break;
                            }
                        }
                    });
        }
        Button button2 = (Button) inflate.findViewById(R.id.negative_btn);
        button2.setText(this.mContext.getString(R.string.bluetooth_find_broadcast));
        final int i2 = 1;
        button2.setOnClickListener(
                new View.OnClickListener(this) { // from class:
                    // com.android.settings.bluetooth.BluetoothBroadcastDialog$$ExternalSyntheticLambda0
                    public final /* synthetic */ BluetoothBroadcastDialog f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
                        LocalBluetoothProfileManager localBluetoothProfileManager;
                        int i22 = i2;
                        BluetoothBroadcastDialog bluetoothBroadcastDialog = this.f$0;
                        switch (i22) {
                            case 0:
                                LocalBluetoothManager localBluetoothManager =
                                        bluetoothBroadcastDialog.mLocalBluetoothManager;
                                byte[] bArr = null;
                                if (localBluetoothManager == null
                                        || (localBluetoothProfileManager =
                                                        localBluetoothManager.mProfileManager)
                                                == null
                                        || (localBluetoothLeBroadcast =
                                                        localBluetoothProfileManager
                                                                .mLeAudioBroadcast)
                                                == null) {
                                    Log.d(
                                            "BTBroadcastsDialog",
                                            "Can not get LE Audio Broadcast Profile");
                                    localBluetoothLeBroadcast = null;
                                }
                                if (localBluetoothLeBroadcast != null) {
                                    localBluetoothLeBroadcast.mNewAppSourceName =
                                            String.valueOf(
                                                    bluetoothBroadcastDialog.mCurrentAppLabel);
                                    if (localBluetoothLeBroadcast.mServiceBroadcast == null) {
                                        Log.d(
                                                "LocalBluetoothLeBroadcast",
                                                "The BluetoothLeBroadcast is null when starting the"
                                                    + " broadcast.");
                                    } else {
                                        String str = localBluetoothLeBroadcast.mProgramInfo;
                                        DialogFragment$$ExternalSyntheticOutline0.m(
                                                "startBroadcast: language = null ,programInfo = ",
                                                str,
                                                "LocalBluetoothLeBroadcast");
                                        BluetoothLeAudioContentMetadata build =
                                                localBluetoothLeBroadcast
                                                        .mBuilder
                                                        .setLanguage((String) null)
                                                        .setProgramInfo(str)
                                                        .build();
                                        localBluetoothLeBroadcast.mBluetoothLeAudioContentMetadata =
                                                build;
                                        BluetoothLeBroadcast bluetoothLeBroadcast =
                                                localBluetoothLeBroadcast.mServiceBroadcast;
                                        byte[] bArr2 = localBluetoothLeBroadcast.mBroadcastCode;
                                        if (bArr2 != null && bArr2.length > 0) {
                                            bArr = bArr2;
                                        }
                                        bluetoothLeBroadcast.startBroadcast(build, bArr);
                                    }
                                    bluetoothBroadcastDialog.mContext.sendBroadcast(
                                            new Intent()
                                                    .setPackage("com.android.systemui")
                                                    .setAction(
                                                            "com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_BROADCAST_DIALOG")
                                                    .putExtra(
                                                            "package_name",
                                                            bluetoothBroadcastDialog
                                                                    .getActivity()
                                                                    .getPackageName()));
                                    bluetoothBroadcastDialog.mContext.sendBroadcast(
                                            new Intent()
                                                    .setPackage(
                                                            KnoxVpnPolicyConstants
                                                                    .ANDROID_SETTINGS_PKG)
                                                    .setAction(
                                                            "com.android.settings.panel.action.CLOSE_PANEL"));
                                } else {
                                    Log.d("BTBroadcastsDialog", "Can not broadcast successfully");
                                }
                                bluetoothBroadcastDialog.dismissInternal(false, false);
                                break;
                            case 1:
                                bluetoothBroadcastDialog.launchFindBroadcastsActivity();
                                bluetoothBroadcastDialog.dismissInternal(false, false);
                                break;
                            default:
                                bluetoothBroadcastDialog.dismissInternal(false, false);
                                bluetoothBroadcastDialog.getActivity().finish();
                                break;
                        }
                    }
                });
        final int i3 = 2;
        ((Button) inflate.findViewById(R.id.neutral_btn))
                .setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.android.settings.bluetooth.BluetoothBroadcastDialog$$ExternalSyntheticLambda0
                            public final /* synthetic */ BluetoothBroadcastDialog f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
                                LocalBluetoothProfileManager localBluetoothProfileManager;
                                int i22 = i3;
                                BluetoothBroadcastDialog bluetoothBroadcastDialog = this.f$0;
                                switch (i22) {
                                    case 0:
                                        LocalBluetoothManager localBluetoothManager =
                                                bluetoothBroadcastDialog.mLocalBluetoothManager;
                                        byte[] bArr = null;
                                        if (localBluetoothManager == null
                                                || (localBluetoothProfileManager =
                                                                localBluetoothManager
                                                                        .mProfileManager)
                                                        == null
                                                || (localBluetoothLeBroadcast =
                                                                localBluetoothProfileManager
                                                                        .mLeAudioBroadcast)
                                                        == null) {
                                            Log.d(
                                                    "BTBroadcastsDialog",
                                                    "Can not get LE Audio Broadcast Profile");
                                            localBluetoothLeBroadcast = null;
                                        }
                                        if (localBluetoothLeBroadcast != null) {
                                            localBluetoothLeBroadcast.mNewAppSourceName =
                                                    String.valueOf(
                                                            bluetoothBroadcastDialog
                                                                    .mCurrentAppLabel);
                                            if (localBluetoothLeBroadcast.mServiceBroadcast
                                                    == null) {
                                                Log.d(
                                                        "LocalBluetoothLeBroadcast",
                                                        "The BluetoothLeBroadcast is null when"
                                                            + " starting the broadcast.");
                                            } else {
                                                String str = localBluetoothLeBroadcast.mProgramInfo;
                                                DialogFragment$$ExternalSyntheticOutline0.m(
                                                        "startBroadcast: language = null"
                                                            + " ,programInfo = ",
                                                        str,
                                                        "LocalBluetoothLeBroadcast");
                                                BluetoothLeAudioContentMetadata build =
                                                        localBluetoothLeBroadcast
                                                                .mBuilder
                                                                .setLanguage((String) null)
                                                                .setProgramInfo(str)
                                                                .build();
                                                localBluetoothLeBroadcast
                                                                .mBluetoothLeAudioContentMetadata =
                                                        build;
                                                BluetoothLeBroadcast bluetoothLeBroadcast =
                                                        localBluetoothLeBroadcast.mServiceBroadcast;
                                                byte[] bArr2 =
                                                        localBluetoothLeBroadcast.mBroadcastCode;
                                                if (bArr2 != null && bArr2.length > 0) {
                                                    bArr = bArr2;
                                                }
                                                bluetoothLeBroadcast.startBroadcast(build, bArr);
                                            }
                                            bluetoothBroadcastDialog.mContext.sendBroadcast(
                                                    new Intent()
                                                            .setPackage("com.android.systemui")
                                                            .setAction(
                                                                    "com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_BROADCAST_DIALOG")
                                                            .putExtra(
                                                                    "package_name",
                                                                    bluetoothBroadcastDialog
                                                                            .getActivity()
                                                                            .getPackageName()));
                                            bluetoothBroadcastDialog.mContext.sendBroadcast(
                                                    new Intent()
                                                            .setPackage(
                                                                    KnoxVpnPolicyConstants
                                                                            .ANDROID_SETTINGS_PKG)
                                                            .setAction(
                                                                    "com.android.settings.panel.action.CLOSE_PANEL"));
                                        } else {
                                            Log.d(
                                                    "BTBroadcastsDialog",
                                                    "Can not broadcast successfully");
                                        }
                                        bluetoothBroadcastDialog.dismissInternal(false, false);
                                        break;
                                    case 1:
                                        bluetoothBroadcastDialog.launchFindBroadcastsActivity();
                                        bluetoothBroadcastDialog.dismissInternal(false, false);
                                        break;
                                    default:
                                        bluetoothBroadcastDialog.dismissInternal(false, false);
                                        bluetoothBroadcastDialog.getActivity().finish();
                                        break;
                                }
                            }
                        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, 2132084214);
        builder.setView(inflate);
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        getActivity().finish();
    }
}
