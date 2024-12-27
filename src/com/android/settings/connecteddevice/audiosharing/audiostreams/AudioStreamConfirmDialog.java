package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.connecteddevice.ConnectedDeviceDashboardFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.bluetooth.BluetoothLeBroadcastMetadataExt;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamConfirmDialog extends InstrumentedDialogFragment {
    public FragmentActivity mActivity;
    public int mAudioStreamConfirmDialogId = 0;
    public BluetoothLeBroadcastMetadata mBroadcastMetadata;
    public BluetoothDevice mConnectedDevice;
    public Context mContext;

    public final String getConnectedDeviceName() {
        BluetoothDevice bluetoothDevice = this.mConnectedDevice;
        if (bluetoothDevice != null) {
            String alias = bluetoothDevice.getAlias();
            return TextUtils.isEmpty(alias)
                    ? getString(R.string.audio_streams_dialog_default_device)
                    : alias;
        }
        Log.w("AudioStreamConfirmDialog", "getConnectedDeviceName : no connected device!");
        return getString(R.string.audio_streams_dialog_default_device);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return this.mAudioStreamConfirmDialogId;
    }

    @Override // com.android.settings.core.instrumentation.InstrumentedDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant;
        this.mContext = context;
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        if (activity == null) {
            Log.w("AudioStreamConfirmDialog", "onAttach() mActivity is null!");
            return;
        }
        String stringExtra = activity.getIntent().getStringExtra("key_broadcast_metadata");
        BluetoothDevice bluetoothDevice = null;
        this.mBroadcastMetadata =
                (stringExtra == null || stringExtra.isEmpty())
                        ? null
                        : BluetoothLeBroadcastMetadataExt.convertToBroadcastMetadata(stringExtra);
        LocalBluetoothManager localBluetoothManager = Utils.getLocalBluetoothManager(getActivity());
        if (localBluetoothManager != null
                && (localBluetoothLeBroadcastAssistant =
                                localBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant)
                        != null) {
            List devicesMatchingConnectionStates =
                    localBluetoothLeBroadcastAssistant.getDevicesMatchingConnectionStates(
                            new int[] {2});
            if (!devicesMatchingConnectionStates.isEmpty()) {
                bluetoothDevice = (BluetoothDevice) devicesMatchingConnectionStates.get(0);
            }
        }
        this.mConnectedDevice = bluetoothDevice;
        BluetoothAdapter.getDefaultAdapter();
        this.mAudioStreamConfirmDialogId = 2089;
        super.onAttach(context);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mShowsDialog = true;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        int i = this.mAudioStreamConfirmDialogId;
        if (i == 2088) {
            AudioStreamsDialogFragment.DialogBuilder dialogBuilder =
                    new AudioStreamsDialogFragment.DialogBuilder(getActivity());
            dialogBuilder.mTitle = getString(R.string.audio_streams_dialog_listen_to_audio_stream);
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = this.mBroadcastMetadata;
            dialogBuilder.mSubTitle1 =
                    bluetoothLeBroadcastMetadata != null
                            ? AudioStreamsHelper.getBroadcastName(bluetoothLeBroadcastMetadata)
                            : ApnSettings.MVNO_NONE;
            dialogBuilder.mSubTitle2 =
                    getString(
                            R.string.audio_streams_dialog_control_volume, getConnectedDeviceName());
            dialogBuilder.mLeftButtonText = getString(R.string.cancel);
            final int i2 = 3;
            dialogBuilder.mLeftButtonOnClickListener =
                    new Consumer(
                            this) { // from class:
                                    // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamConfirmDialog$$ExternalSyntheticLambda0
                        public final /* synthetic */ AudioStreamConfirmDialog f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i3 = i2;
                            AudioStreamConfirmDialog audioStreamConfirmDialog = this.f$0;
                            switch (i3) {
                                case 0:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity != null) {
                                        fragmentActivity.finish();
                                        break;
                                    }
                                    break;
                                case 1:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity2 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity2 != null) {
                                        fragmentActivity2.finish();
                                        break;
                                    }
                                    break;
                                case 2:
                                    SubSettingLauncher subSettingLauncher =
                                            new SubSettingLauncher(
                                                    audioStreamConfirmDialog.mContext);
                                    String name = ConnectedDeviceDashboardFragment.class.getName();
                                    SubSettingLauncher.LaunchRequest launchRequest =
                                            subSettingLauncher.mLaunchRequest;
                                    launchRequest.mDestinationName = name;
                                    launchRequest.mSourceMetricsCategory = 2091;
                                    subSettingLauncher.launch();
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity3 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity3 != null) {
                                        fragmentActivity3.finish();
                                        break;
                                    }
                                    break;
                                case 3:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity4 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity4 != null) {
                                        fragmentActivity4.finish();
                                        break;
                                    }
                                    break;
                                case 4:
                                    audioStreamConfirmDialog.mMetricsFeatureProvider.action(
                                            audioStreamConfirmDialog.getActivity(),
                                            1949,
                                            new Pair[0]);
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable(
                                            "key_broadcast_metadata",
                                            audioStreamConfirmDialog.mBroadcastMetadata);
                                    if (audioStreamConfirmDialog.mActivity != null) {
                                        SubSettingLauncher subSettingLauncher2 =
                                                new SubSettingLauncher(
                                                        audioStreamConfirmDialog.getActivity());
                                        String string =
                                                audioStreamConfirmDialog.getString(
                                                        R.string.audio_streams_activity_title);
                                        SubSettingLauncher.LaunchRequest launchRequest2 =
                                                subSettingLauncher2.mLaunchRequest;
                                        launchRequest2.mTitle = string;
                                        launchRequest2.mDestinationName =
                                                AudioStreamsDashboardFragment.class.getName();
                                        launchRequest2.mArguments = bundle2;
                                        launchRequest2.mSourceMetricsCategory =
                                                audioStreamConfirmDialog
                                                        .mAudioStreamConfirmDialogId;
                                        subSettingLauncher2.launch();
                                    }
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity5 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity5 != null) {
                                        fragmentActivity5.finish();
                                        break;
                                    }
                                    break;
                                default:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity6 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity6 != null) {
                                        fragmentActivity6.finish();
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            dialogBuilder.mRightButtonText = getString(R.string.audio_streams_dialog_listen);
            final int i3 = 4;
            dialogBuilder.mRightButtonOnClickListener =
                    new Consumer(
                            this) { // from class:
                                    // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamConfirmDialog$$ExternalSyntheticLambda0
                        public final /* synthetic */ AudioStreamConfirmDialog f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i32 = i3;
                            AudioStreamConfirmDialog audioStreamConfirmDialog = this.f$0;
                            switch (i32) {
                                case 0:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity != null) {
                                        fragmentActivity.finish();
                                        break;
                                    }
                                    break;
                                case 1:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity2 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity2 != null) {
                                        fragmentActivity2.finish();
                                        break;
                                    }
                                    break;
                                case 2:
                                    SubSettingLauncher subSettingLauncher =
                                            new SubSettingLauncher(
                                                    audioStreamConfirmDialog.mContext);
                                    String name = ConnectedDeviceDashboardFragment.class.getName();
                                    SubSettingLauncher.LaunchRequest launchRequest =
                                            subSettingLauncher.mLaunchRequest;
                                    launchRequest.mDestinationName = name;
                                    launchRequest.mSourceMetricsCategory = 2091;
                                    subSettingLauncher.launch();
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity3 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity3 != null) {
                                        fragmentActivity3.finish();
                                        break;
                                    }
                                    break;
                                case 3:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity4 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity4 != null) {
                                        fragmentActivity4.finish();
                                        break;
                                    }
                                    break;
                                case 4:
                                    audioStreamConfirmDialog.mMetricsFeatureProvider.action(
                                            audioStreamConfirmDialog.getActivity(),
                                            1949,
                                            new Pair[0]);
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable(
                                            "key_broadcast_metadata",
                                            audioStreamConfirmDialog.mBroadcastMetadata);
                                    if (audioStreamConfirmDialog.mActivity != null) {
                                        SubSettingLauncher subSettingLauncher2 =
                                                new SubSettingLauncher(
                                                        audioStreamConfirmDialog.getActivity());
                                        String string =
                                                audioStreamConfirmDialog.getString(
                                                        R.string.audio_streams_activity_title);
                                        SubSettingLauncher.LaunchRequest launchRequest2 =
                                                subSettingLauncher2.mLaunchRequest;
                                        launchRequest2.mTitle = string;
                                        launchRequest2.mDestinationName =
                                                AudioStreamsDashboardFragment.class.getName();
                                        launchRequest2.mArguments = bundle2;
                                        launchRequest2.mSourceMetricsCategory =
                                                audioStreamConfirmDialog
                                                        .mAudioStreamConfirmDialogId;
                                        subSettingLauncher2.launch();
                                    }
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity5 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity5 != null) {
                                        fragmentActivity5.finish();
                                        break;
                                    }
                                    break;
                                default:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity6 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity6 != null) {
                                        fragmentActivity6.finish();
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            return dialogBuilder.build();
        }
        if (i == 2089) {
            AudioStreamsDialogFragment.DialogBuilder dialogBuilder2 =
                    new AudioStreamsDialogFragment.DialogBuilder(getActivity());
            dialogBuilder2.mTitle = getString(R.string.audio_streams_dialog_cannot_listen);
            dialogBuilder2.mSubTitle2 =
                    getString(R.string.audio_streams_dialog_unsupported_device_subtitle);
            dialogBuilder2.mRightButtonText = getString(R.string.audio_streams_dialog_close);
            final int i4 = 5;
            dialogBuilder2.mRightButtonOnClickListener =
                    new Consumer(
                            this) { // from class:
                                    // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamConfirmDialog$$ExternalSyntheticLambda0
                        public final /* synthetic */ AudioStreamConfirmDialog f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i32 = i4;
                            AudioStreamConfirmDialog audioStreamConfirmDialog = this.f$0;
                            switch (i32) {
                                case 0:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity != null) {
                                        fragmentActivity.finish();
                                        break;
                                    }
                                    break;
                                case 1:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity2 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity2 != null) {
                                        fragmentActivity2.finish();
                                        break;
                                    }
                                    break;
                                case 2:
                                    SubSettingLauncher subSettingLauncher =
                                            new SubSettingLauncher(
                                                    audioStreamConfirmDialog.mContext);
                                    String name = ConnectedDeviceDashboardFragment.class.getName();
                                    SubSettingLauncher.LaunchRequest launchRequest =
                                            subSettingLauncher.mLaunchRequest;
                                    launchRequest.mDestinationName = name;
                                    launchRequest.mSourceMetricsCategory = 2091;
                                    subSettingLauncher.launch();
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity3 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity3 != null) {
                                        fragmentActivity3.finish();
                                        break;
                                    }
                                    break;
                                case 3:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity4 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity4 != null) {
                                        fragmentActivity4.finish();
                                        break;
                                    }
                                    break;
                                case 4:
                                    audioStreamConfirmDialog.mMetricsFeatureProvider.action(
                                            audioStreamConfirmDialog.getActivity(),
                                            1949,
                                            new Pair[0]);
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable(
                                            "key_broadcast_metadata",
                                            audioStreamConfirmDialog.mBroadcastMetadata);
                                    if (audioStreamConfirmDialog.mActivity != null) {
                                        SubSettingLauncher subSettingLauncher2 =
                                                new SubSettingLauncher(
                                                        audioStreamConfirmDialog.getActivity());
                                        String string =
                                                audioStreamConfirmDialog.getString(
                                                        R.string.audio_streams_activity_title);
                                        SubSettingLauncher.LaunchRequest launchRequest2 =
                                                subSettingLauncher2.mLaunchRequest;
                                        launchRequest2.mTitle = string;
                                        launchRequest2.mDestinationName =
                                                AudioStreamsDashboardFragment.class.getName();
                                        launchRequest2.mArguments = bundle2;
                                        launchRequest2.mSourceMetricsCategory =
                                                audioStreamConfirmDialog
                                                        .mAudioStreamConfirmDialogId;
                                        subSettingLauncher2.launch();
                                    }
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity5 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity5 != null) {
                                        fragmentActivity5.finish();
                                        break;
                                    }
                                    break;
                                default:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity6 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity6 != null) {
                                        fragmentActivity6.finish();
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            return dialogBuilder2.build();
        }
        if (i != 2091) {
            AudioStreamsDialogFragment.DialogBuilder dialogBuilder3 =
                    new AudioStreamsDialogFragment.DialogBuilder(getActivity());
            dialogBuilder3.mTitle = getString(R.string.audio_streams_dialog_cannot_listen);
            dialogBuilder3.mSubTitle2 =
                    getString(R.string.audio_streams_dialog_cannot_play, getConnectedDeviceName());
            dialogBuilder3.mRightButtonText = getString(R.string.audio_streams_dialog_close);
            final int i5 = 0;
            dialogBuilder3.mRightButtonOnClickListener =
                    new Consumer(
                            this) { // from class:
                                    // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamConfirmDialog$$ExternalSyntheticLambda0
                        public final /* synthetic */ AudioStreamConfirmDialog f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i32 = i5;
                            AudioStreamConfirmDialog audioStreamConfirmDialog = this.f$0;
                            switch (i32) {
                                case 0:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity != null) {
                                        fragmentActivity.finish();
                                        break;
                                    }
                                    break;
                                case 1:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity2 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity2 != null) {
                                        fragmentActivity2.finish();
                                        break;
                                    }
                                    break;
                                case 2:
                                    SubSettingLauncher subSettingLauncher =
                                            new SubSettingLauncher(
                                                    audioStreamConfirmDialog.mContext);
                                    String name = ConnectedDeviceDashboardFragment.class.getName();
                                    SubSettingLauncher.LaunchRequest launchRequest =
                                            subSettingLauncher.mLaunchRequest;
                                    launchRequest.mDestinationName = name;
                                    launchRequest.mSourceMetricsCategory = 2091;
                                    subSettingLauncher.launch();
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity3 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity3 != null) {
                                        fragmentActivity3.finish();
                                        break;
                                    }
                                    break;
                                case 3:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity4 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity4 != null) {
                                        fragmentActivity4.finish();
                                        break;
                                    }
                                    break;
                                case 4:
                                    audioStreamConfirmDialog.mMetricsFeatureProvider.action(
                                            audioStreamConfirmDialog.getActivity(),
                                            1949,
                                            new Pair[0]);
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putParcelable(
                                            "key_broadcast_metadata",
                                            audioStreamConfirmDialog.mBroadcastMetadata);
                                    if (audioStreamConfirmDialog.mActivity != null) {
                                        SubSettingLauncher subSettingLauncher2 =
                                                new SubSettingLauncher(
                                                        audioStreamConfirmDialog.getActivity());
                                        String string =
                                                audioStreamConfirmDialog.getString(
                                                        R.string.audio_streams_activity_title);
                                        SubSettingLauncher.LaunchRequest launchRequest2 =
                                                subSettingLauncher2.mLaunchRequest;
                                        launchRequest2.mTitle = string;
                                        launchRequest2.mDestinationName =
                                                AudioStreamsDashboardFragment.class.getName();
                                        launchRequest2.mArguments = bundle2;
                                        launchRequest2.mSourceMetricsCategory =
                                                audioStreamConfirmDialog
                                                        .mAudioStreamConfirmDialogId;
                                        subSettingLauncher2.launch();
                                    }
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity5 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity5 != null) {
                                        fragmentActivity5.finish();
                                        break;
                                    }
                                    break;
                                default:
                                    audioStreamConfirmDialog.dismissInternal(false, false);
                                    FragmentActivity fragmentActivity6 =
                                            audioStreamConfirmDialog.mActivity;
                                    if (fragmentActivity6 != null) {
                                        fragmentActivity6.finish();
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            return dialogBuilder3.build();
        }
        AudioStreamsDialogFragment.DialogBuilder dialogBuilder4 =
                new AudioStreamsDialogFragment.DialogBuilder(getActivity());
        dialogBuilder4.mTitle = getString(R.string.audio_streams_dialog_no_le_device_title);
        dialogBuilder4.mSubTitle2 = getString(R.string.audio_streams_dialog_no_le_device_subtitle);
        dialogBuilder4.mLeftButtonText = getString(R.string.audio_streams_dialog_close);
        final int i6 = 1;
        dialogBuilder4.mLeftButtonOnClickListener =
                new Consumer(
                        this) { // from class:
                                // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamConfirmDialog$$ExternalSyntheticLambda0
                    public final /* synthetic */ AudioStreamConfirmDialog f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i32 = i6;
                        AudioStreamConfirmDialog audioStreamConfirmDialog = this.f$0;
                        switch (i32) {
                            case 0:
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity != null) {
                                    fragmentActivity.finish();
                                    break;
                                }
                                break;
                            case 1:
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity2 =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity2 != null) {
                                    fragmentActivity2.finish();
                                    break;
                                }
                                break;
                            case 2:
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(audioStreamConfirmDialog.mContext);
                                String name = ConnectedDeviceDashboardFragment.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mDestinationName = name;
                                launchRequest.mSourceMetricsCategory = 2091;
                                subSettingLauncher.launch();
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity3 =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity3 != null) {
                                    fragmentActivity3.finish();
                                    break;
                                }
                                break;
                            case 3:
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity4 =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity4 != null) {
                                    fragmentActivity4.finish();
                                    break;
                                }
                                break;
                            case 4:
                                audioStreamConfirmDialog.mMetricsFeatureProvider.action(
                                        audioStreamConfirmDialog.getActivity(), 1949, new Pair[0]);
                                Bundle bundle2 = new Bundle();
                                bundle2.putParcelable(
                                        "key_broadcast_metadata",
                                        audioStreamConfirmDialog.mBroadcastMetadata);
                                if (audioStreamConfirmDialog.mActivity != null) {
                                    SubSettingLauncher subSettingLauncher2 =
                                            new SubSettingLauncher(
                                                    audioStreamConfirmDialog.getActivity());
                                    String string =
                                            audioStreamConfirmDialog.getString(
                                                    R.string.audio_streams_activity_title);
                                    SubSettingLauncher.LaunchRequest launchRequest2 =
                                            subSettingLauncher2.mLaunchRequest;
                                    launchRequest2.mTitle = string;
                                    launchRequest2.mDestinationName =
                                            AudioStreamsDashboardFragment.class.getName();
                                    launchRequest2.mArguments = bundle2;
                                    launchRequest2.mSourceMetricsCategory =
                                            audioStreamConfirmDialog.mAudioStreamConfirmDialogId;
                                    subSettingLauncher2.launch();
                                }
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity5 =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity5 != null) {
                                    fragmentActivity5.finish();
                                    break;
                                }
                                break;
                            default:
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity6 =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity6 != null) {
                                    fragmentActivity6.finish();
                                    break;
                                }
                                break;
                        }
                    }
                };
        dialogBuilder4.mRightButtonText =
                getString(R.string.audio_streams_dialog_no_le_device_button);
        final int i7 = 2;
        dialogBuilder4.mRightButtonOnClickListener =
                new Consumer(
                        this) { // from class:
                                // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamConfirmDialog$$ExternalSyntheticLambda0
                    public final /* synthetic */ AudioStreamConfirmDialog f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i32 = i7;
                        AudioStreamConfirmDialog audioStreamConfirmDialog = this.f$0;
                        switch (i32) {
                            case 0:
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity != null) {
                                    fragmentActivity.finish();
                                    break;
                                }
                                break;
                            case 1:
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity2 =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity2 != null) {
                                    fragmentActivity2.finish();
                                    break;
                                }
                                break;
                            case 2:
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(audioStreamConfirmDialog.mContext);
                                String name = ConnectedDeviceDashboardFragment.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mDestinationName = name;
                                launchRequest.mSourceMetricsCategory = 2091;
                                subSettingLauncher.launch();
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity3 =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity3 != null) {
                                    fragmentActivity3.finish();
                                    break;
                                }
                                break;
                            case 3:
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity4 =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity4 != null) {
                                    fragmentActivity4.finish();
                                    break;
                                }
                                break;
                            case 4:
                                audioStreamConfirmDialog.mMetricsFeatureProvider.action(
                                        audioStreamConfirmDialog.getActivity(), 1949, new Pair[0]);
                                Bundle bundle2 = new Bundle();
                                bundle2.putParcelable(
                                        "key_broadcast_metadata",
                                        audioStreamConfirmDialog.mBroadcastMetadata);
                                if (audioStreamConfirmDialog.mActivity != null) {
                                    SubSettingLauncher subSettingLauncher2 =
                                            new SubSettingLauncher(
                                                    audioStreamConfirmDialog.getActivity());
                                    String string =
                                            audioStreamConfirmDialog.getString(
                                                    R.string.audio_streams_activity_title);
                                    SubSettingLauncher.LaunchRequest launchRequest2 =
                                            subSettingLauncher2.mLaunchRequest;
                                    launchRequest2.mTitle = string;
                                    launchRequest2.mDestinationName =
                                            AudioStreamsDashboardFragment.class.getName();
                                    launchRequest2.mArguments = bundle2;
                                    launchRequest2.mSourceMetricsCategory =
                                            audioStreamConfirmDialog.mAudioStreamConfirmDialogId;
                                    subSettingLauncher2.launch();
                                }
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity5 =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity5 != null) {
                                    fragmentActivity5.finish();
                                    break;
                                }
                                break;
                            default:
                                audioStreamConfirmDialog.dismissInternal(false, false);
                                FragmentActivity fragmentActivity6 =
                                        audioStreamConfirmDialog.mActivity;
                                if (fragmentActivity6 != null) {
                                    fragmentActivity6.finish();
                                    break;
                                }
                                break;
                        }
                    }
                };
        return dialogBuilder4.build();
    }
}
