package com.android.settings.connecteddevice.audiosharing;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingDisconnectDialogFragment extends InstrumentedDialogFragment {
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
        return 2051;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        List list =
                (List)
                        requireArguments()
                                .getParcelable("bundle_key_device_to_disconnect_items", List.class);
        AudioSharingDialogFactory.DialogBuilder newBuilder =
                AudioSharingDialogFactory.newBuilder(getActivity());
        newBuilder.setTitle(R.string.audio_sharing_disconnect_dialog_title);
        newBuilder.setTitleIcon(R.drawable.ic_bt_le_audio_sharing);
        newBuilder.mIsCustomBodyEnabled = true;
        newBuilder.setCustomMessage(R.string.audio_sharing_dialog_disconnect_content);
        newBuilder.setCustomNegativeButton(
                R.string.cancel,
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.connecteddevice.audiosharing.AudioSharingDisconnectDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AudioSharingDisconnectDialogFragment audioSharingDisconnectDialogFragment =
                                AudioSharingDisconnectDialogFragment.this;
                        Pair[] pairArr = AudioSharingDisconnectDialogFragment.sEventData;
                        audioSharingDisconnectDialogFragment.mMetricsFeatureProvider.action(
                                audioSharingDisconnectDialogFragment.getContext(),
                                1941,
                                AudioSharingDisconnectDialogFragment.sEventData);
                        audioSharingDisconnectDialogFragment.dismissInternal(false, false);
                    }
                });
        if (list == null) {
            Log.d("AudioSharingDisconnectDialog", "Create dialog error: null deviceItems");
            return newBuilder.build();
        }
        AudioSharingDeviceAdapter audioSharingDeviceAdapter =
                new AudioSharingDeviceAdapter(
                        getContext(),
                        list,
                        new AudioSharingDeviceAdapter
                                .OnClickListener() { // from class:
                                                     // com.android.settings.connecteddevice.audiosharing.AudioSharingDisconnectDialogFragment$$ExternalSyntheticLambda1
                            @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingDeviceAdapter.OnClickListener
                            public final void onClick() {
                                Pair[] pairArr = AudioSharingDisconnectDialogFragment.sEventData;
                                AudioSharingDisconnectDialogFragment.this.dismissInternal(
                                        false, false);
                            }
                        },
                        AudioSharingDeviceAdapter.ActionType.REMOVE);
        RecyclerView recyclerView =
                (RecyclerView) newBuilder.mCustomBody.findViewById(R.id.device_btn_list);
        recyclerView.setAdapter(audioSharingDeviceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        recyclerView.setVisibility(0);
        return newBuilder.build();
    }
}
