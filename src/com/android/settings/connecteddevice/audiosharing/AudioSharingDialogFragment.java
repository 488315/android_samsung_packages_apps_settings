package com.android.settings.connecteddevice.audiosharing;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.google.common.collect.Iterables;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingDialogFragment extends InstrumentedDialogFragment {
    public static final Pair[] sEventData = new Pair[0];

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DialogEventListener {}

    public Pair<Integer, Object>[] getEventData() {
        return sEventData;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2086;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        List list = (List) requireArguments().getParcelable("bundle_key_device_items", List.class);
        AudioSharingDialogFactory.DialogBuilder newBuilder =
                AudioSharingDialogFactory.newBuilder(getActivity());
        newBuilder.setTitleIcon(R.drawable.ic_bt_le_audio_sharing);
        newBuilder.mIsCustomBodyEnabled = true;
        if (list == null) {
            Log.d("AudioSharingDialog", "Create dialog error: null deviceItems");
            return newBuilder.build();
        }
        if (list.isEmpty()) {
            newBuilder.setTitle(R.string.audio_sharing_share_dialog_title);
            ImageView imageView =
                    (ImageView) newBuilder.mCustomBody.findViewById(R.id.description_image);
            imageView.setImageResource(R.drawable.audio_sharing_guidance);
            imageView.setVisibility(0);
            newBuilder.setCustomMessage(R.string.audio_sharing_dialog_connect_device_content);
            newBuilder.mBuilder.setNegativeButton(
                    R.string.audio_sharing_close_button_label,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingDialogFragment$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            AudioSharingDialogFragment audioSharingDialogFragment =
                                    AudioSharingDialogFragment.this;
                            Pair[] pairArr = AudioSharingDialogFragment.sEventData;
                            audioSharingDialogFragment.dismissInternal(false, false);
                        }
                    });
        } else if (list.size() == 1) {
            AudioSharingDeviceItem audioSharingDeviceItem =
                    (AudioSharingDeviceItem) Iterables.getOnlyElement(list);
            ((TextView) newBuilder.mCustomTitle.findViewById(R.id.title_text))
                    .setText(
                            getString(
                                    R.string.audio_sharing_share_with_dialog_title,
                                    audioSharingDeviceItem.mName));
            newBuilder.setCustomMessage(R.string.audio_sharing_dialog_share_content);
            AudioSharingDialogFragment$$ExternalSyntheticLambda1
                    audioSharingDialogFragment$$ExternalSyntheticLambda1 =
                            new AudioSharingDialogFragment$$ExternalSyntheticLambda1(
                                    this, audioSharingDeviceItem);
            Button button = (Button) newBuilder.mCustomBody.findViewById(R.id.positive_btn);
            button.setText(R.string.audio_sharing_share_button_label);
            button.setOnClickListener(audioSharingDialogFragment$$ExternalSyntheticLambda1);
            button.setVisibility(0);
            newBuilder.setCustomNegativeButton(
                    R.string.audio_sharing_no_thanks_button_label,
                    new AudioSharingDialogFragment$$ExternalSyntheticLambda1(this, 1));
        } else {
            newBuilder.setTitle(R.string.audio_sharing_share_with_more_dialog_title);
            newBuilder.setCustomMessage(R.string.audio_sharing_dialog_share_more_content);
            AudioSharingDeviceAdapter audioSharingDeviceAdapter =
                    new AudioSharingDeviceAdapter(
                            getContext(),
                            list,
                            new AudioSharingDeviceAdapter
                                    .OnClickListener() { // from class:
                                                         // com.android.settings.connecteddevice.audiosharing.AudioSharingDialogFragment$$ExternalSyntheticLambda3
                                @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingDeviceAdapter.OnClickListener
                                public final void onClick() {
                                    Pair[] pairArr = AudioSharingDialogFragment.sEventData;
                                    AudioSharingDialogFragment.this.dismissInternal(false, false);
                                }
                            },
                            AudioSharingDeviceAdapter.ActionType.SHARE);
            RecyclerView recyclerView =
                    (RecyclerView) newBuilder.mCustomBody.findViewById(R.id.device_btn_list);
            recyclerView.setAdapter(audioSharingDeviceAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(1));
            recyclerView.setVisibility(0);
            newBuilder.setCustomNegativeButton(
                    R.string.cancel,
                    new AudioSharingDialogFragment$$ExternalSyntheticLambda1(this, 2));
        }
        return newBuilder.build();
    }
}
