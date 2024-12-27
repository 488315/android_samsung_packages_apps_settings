package com.android.settings.connecteddevice.audiosharing;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingConfirmDialogFragment extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2085;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AudioSharingDialogFactory.DialogBuilder newBuilder =
                AudioSharingDialogFactory.newBuilder(getActivity());
        newBuilder.setTitle(R.string.audio_sharing_confirm_dialog_title);
        newBuilder.setTitleIcon(R.drawable.ic_bt_le_audio_sharing);
        newBuilder.mIsCustomBodyEnabled = true;
        newBuilder.setCustomMessage(R.string.audio_sharing_comfirm_dialog_content);
        newBuilder.mBuilder.setPositiveButton(
                R.string.okay,
                new ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0());
        AlertDialog build = newBuilder.build();
        build.setCanceledOnTouchOutside(true);
        return build;
    }
}