package com.android.settings.connecteddevice.audiosharing;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingJoinDialogFragment extends InstrumentedDialogFragment {
    public static final Pair[] sEventData = new Pair[0];

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DialogEventListener {}

    public Pair<Integer, Object>[] getEventData() {
        return sEventData;
    }

    public DialogEventListener getListener() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return AudioSharingUtils.isBroadcasting(
                        LocalBluetoothManager.getInstance(getContext(), Utils.mOnInitCallback))
                ? 2086
                : 2049;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Bundle requireArguments = requireArguments();
        List list = (List) requireArguments.getParcelable("bundle_key_device_items", List.class);
        String string = requireArguments.getString("bundle_key_new_device_name");
        AudioSharingDialogFactory.DialogBuilder newBuilder =
                AudioSharingDialogFactory.newBuilder(getActivity());
        newBuilder.setTitle(R.string.audio_sharing_share_dialog_title);
        newBuilder.setTitleIcon(R.drawable.ic_bt_le_audio_sharing);
        newBuilder.mIsCustomBodyEnabled = true;
        newBuilder.setCustomMessage(R.string.audio_sharing_dialog_share_content);
        final int i = 0;
        View.OnClickListener onClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.connecteddevice.audiosharing.AudioSharingJoinDialogFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ AudioSharingJoinDialogFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i2 = i;
                        AudioSharingJoinDialogFragment audioSharingJoinDialogFragment = this.f$0;
                        switch (i2) {
                            case 0:
                                Pair[] pairArr = AudioSharingJoinDialogFragment.sEventData;
                                audioSharingJoinDialogFragment.dismissInternal(false, false);
                                break;
                            default:
                                Pair[] pairArr2 = AudioSharingJoinDialogFragment.sEventData;
                                audioSharingJoinDialogFragment.dismissInternal(false, false);
                                break;
                        }
                    }
                };
        Button button = (Button) newBuilder.mCustomBody.findViewById(R.id.positive_btn);
        button.setText(R.string.audio_sharing_share_button_label);
        button.setOnClickListener(onClickListener);
        button.setVisibility(0);
        final int i2 = 1;
        newBuilder.setCustomNegativeButton(
                R.string.audio_sharing_no_thanks_button_label,
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.connecteddevice.audiosharing.AudioSharingJoinDialogFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ AudioSharingJoinDialogFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i22 = i2;
                        AudioSharingJoinDialogFragment audioSharingJoinDialogFragment = this.f$0;
                        switch (i22) {
                            case 0:
                                Pair[] pairArr = AudioSharingJoinDialogFragment.sEventData;
                                audioSharingJoinDialogFragment.dismissInternal(false, false);
                                break;
                            default:
                                Pair[] pairArr2 = AudioSharingJoinDialogFragment.sEventData;
                                audioSharingJoinDialogFragment.dismissInternal(false, false);
                                break;
                        }
                    }
                });
        AlertDialog build = newBuilder.build();
        if (list == null) {
            Log.d("AudioSharingJoinDialog", "Fail to create dialog: null deviceItems");
        } else if (list.isEmpty()) {
            build.setMessage(string);
        } else {
            build.setMessage(
                    build.getContext()
                            .getString(
                                    R.string.audio_sharing_share_dialog_subtitle,
                                    ((AudioSharingDeviceItem) list.get(0)).mName,
                                    string));
        }
        build.show();
        AudioSharingDialogHelper.updateMessageStyle(build);
        return build;
    }
}
