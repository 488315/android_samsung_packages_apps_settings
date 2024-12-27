package com.android.settings.connecteddevice.audiosharing;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingCallAudioDialogFragment extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2084;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        List<AudioSharingDeviceItem> list =
                (List) requireArguments().getParcelable("bundle_key_device_items", List.class);
        int i = -1;
        for (AudioSharingDeviceItem audioSharingDeviceItem : list) {
            if (audioSharingDeviceItem.mGroupId
                    == Settings.Secure.getInt(
                            getContext().getContentResolver(),
                            "bluetooth_le_broadcast_fallback_active_group_id",
                            -1)) {
                i = list.indexOf(audioSharingDeviceItem);
            }
        }
        String[] strArr =
                (String[])
                        list.stream()
                                .map(
                                        new AudioSharingCallAudioDialogFragment$$ExternalSyntheticLambda0())
                                .toArray(
                                        new AudioSharingCallAudioDialogFragment$$ExternalSyntheticLambda1());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.audio_sharing_call_audio_title);
        builder.setSingleChoiceItems(
                strArr,
                i,
                new ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0());
        return builder.create();
    }
}
