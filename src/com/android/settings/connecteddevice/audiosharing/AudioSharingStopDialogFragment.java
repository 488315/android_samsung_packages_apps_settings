package com.android.settings.connecteddevice.audiosharing;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.google.common.collect.Iterables;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingStopDialogFragment extends InstrumentedDialogFragment {
    public static final Pair[] sEventData = new Pair[0];

    public Pair<Integer, Object>[] getEventData() {
        return sEventData;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2050;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Bundle requireArguments = requireArguments();
        List list =
                (List)
                        requireArguments.getParcelable(
                                "bundle_key_device_to_disconnect_items", List.class);
        String string = requireArguments.getString("bundle_key_new_device_name");
        String string2 =
                list != null
                        ? list.size() == 1
                                ? getString(
                                        R.string.audio_sharing_stop_dialog_content,
                                        ((AudioSharingDeviceItem) Iterables.getOnlyElement(list))
                                                .mName)
                                : list.size() == 2
                                        ? getString(
                                                R.string.audio_sharing_stop_dialog_with_two_content,
                                                ((AudioSharingDeviceItem) list.get(0)).mName,
                                                ((AudioSharingDeviceItem) list.get(1)).mName)
                                        : getString(
                                                R.string
                                                        .audio_sharing_stop_dialog_with_more_content)
                        : ApnSettings.MVNO_NONE;
        AudioSharingDialogFactory.DialogBuilder newBuilder =
                AudioSharingDialogFactory.newBuilder(getActivity());
        ((TextView) newBuilder.mCustomTitle.findViewById(R.id.title_text))
                .setText(getString(R.string.audio_sharing_stop_dialog_title, string));
        newBuilder.setTitleIcon(R.drawable.ic_warning_24dp);
        newBuilder.mIsCustomBodyEnabled = true;
        TextView textView = (TextView) newBuilder.mCustomBody.findViewById(R.id.description_text);
        textView.setText(string2);
        textView.setVisibility(0);
        final int i = 0;
        DialogInterface.OnClickListener onClickListener =
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.android.settings.connecteddevice.audiosharing.AudioSharingStopDialogFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ AudioSharingStopDialogFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        int i3 = i;
                        AudioSharingStopDialogFragment audioSharingStopDialogFragment = this.f$0;
                        switch (i3) {
                            case 0:
                                Pair[] pairArr = AudioSharingStopDialogFragment.sEventData;
                                audioSharingStopDialogFragment.getClass();
                                break;
                            default:
                                Pair[] pairArr2 = AudioSharingStopDialogFragment.sEventData;
                                audioSharingStopDialogFragment.mMetricsFeatureProvider.action(
                                        audioSharingStopDialogFragment.getContext(),
                                        1941,
                                        AudioSharingStopDialogFragment.sEventData);
                                break;
                        }
                    }
                };
        AlertDialog.Builder builder = newBuilder.mBuilder;
        builder.setPositiveButton(R.string.audio_sharing_connect_button_label, onClickListener);
        final int i2 = 1;
        builder.setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.android.settings.connecteddevice.audiosharing.AudioSharingStopDialogFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ AudioSharingStopDialogFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i22) {
                        int i3 = i2;
                        AudioSharingStopDialogFragment audioSharingStopDialogFragment = this.f$0;
                        switch (i3) {
                            case 0:
                                Pair[] pairArr = AudioSharingStopDialogFragment.sEventData;
                                audioSharingStopDialogFragment.getClass();
                                break;
                            default:
                                Pair[] pairArr2 = AudioSharingStopDialogFragment.sEventData;
                                audioSharingStopDialogFragment.mMetricsFeatureProvider.action(
                                        audioSharingStopDialogFragment.getContext(),
                                        1941,
                                        AudioSharingStopDialogFragment.sEventData);
                                break;
                        }
                    }
                });
        AlertDialog build = newBuilder.build();
        build.show();
        AudioSharingDialogHelper.updateMessageStyle(build);
        return build;
    }
}
