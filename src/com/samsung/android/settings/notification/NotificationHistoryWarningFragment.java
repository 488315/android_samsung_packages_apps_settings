package com.samsung.android.settings.notification;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.settings.R;
import com.android.settings.notification.history.NotificationHistoryActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotificationHistoryWarningFragment extends DialogFragment {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DialogFragmentListener {}

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.notification_history_warning_dialog_title);
        builder.setMessage(R.string.notification_history_warning_dialog_message);
        builder.setPositiveButton(
                R.string.notification_history_warning_dialog_save,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.notification.NotificationHistoryWarningFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ((NotificationHistoryActivity)
                                        ((NotificationHistoryWarningFragment.DialogFragmentListener)
                                                NotificationHistoryWarningFragment.this
                                                        .getActivity()))
                                .onPositiveClick();
                    }
                });
        builder.setNegativeButton(
                R.string.notification_history_warning_dialog_cancel,
                (DialogInterface.OnClickListener) null);
        return builder.create();
    }
}
