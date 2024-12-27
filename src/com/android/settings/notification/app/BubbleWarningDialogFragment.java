package com.android.settings.notification.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.notification.NotificationBackend;

import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BubbleWarningDialogFragment extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.RRC_CONNECTION_REJECT;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        onCreate(bundle);
        Bundle arguments = getArguments();
        final String string = arguments.getString("p");
        final int i = arguments.getInt("u");
        final int i2 = arguments.getInt("pref");
        String string2 = getResources().getString(R.string.bubbles_feature_disabled_dialog_title);
        String string3 = getResources().getString(R.string.bubbles_feature_disabled_dialog_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = string3;
        alertParams.mTitle = string2;
        alertParams.mCancelable = true;
        builder.setPositiveButton(
                R.string.bubbles_feature_disabled_button_approve,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.app.BubbleWarningDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        BubbleWarningDialogFragment bubbleWarningDialogFragment =
                                BubbleWarningDialogFragment.this;
                        String str = string;
                        int i4 = i;
                        int i5 = i2;
                        Context context = bubbleWarningDialogFragment.getContext();
                        int i6 = BubblePreferenceController.SYSTEM_WIDE_ON;
                        new NotificationBackend();
                        NotificationBackend.setAllowBubbles(i4, i5, str);
                        Settings.Secure.putInt(
                                context.getContentResolver(), "notification_bubbles", 1);
                    }
                });
        builder.setNegativeButton(
                R.string.bubbles_feature_disabled_button_cancel,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.app.BubbleWarningDialogFragment$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        BubbleWarningDialogFragment bubbleWarningDialogFragment =
                                BubbleWarningDialogFragment.this;
                        String str = string;
                        int i4 = i;
                        bubbleWarningDialogFragment.getContext();
                        int i5 = BubblePreferenceController.SYSTEM_WIDE_ON;
                        new NotificationBackend();
                        NotificationBackend.setAllowBubbles(i4, 0, str);
                    }
                });
        return builder.create();
    }

    public final void setPkgPrefInfo(int i, int i2, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("p", str);
        bundle.putInt("u", i);
        bundle.putInt("pref", i2);
        setArguments(bundle);
    }
}
