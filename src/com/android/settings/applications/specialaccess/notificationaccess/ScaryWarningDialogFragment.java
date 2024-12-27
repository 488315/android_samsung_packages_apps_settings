package com.android.settings.applications.specialaccess.notificationaccess;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScaryWarningDialogFragment extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2017;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Bundle arguments = getArguments();
        CharSequence charSequence = arguments.getCharSequence("l");
        final ComponentName unflattenFromString =
                ComponentName.unflattenFromString(arguments.getString("c"));
        final NotificationAccessDetails notificationAccessDetails =
                getTargetFragment() instanceof NotificationAccessDetails
                        ? (NotificationAccessDetails) getTargetFragment()
                        : null;
        final boolean z = notificationAccessDetails != null;
        String string =
                getResources()
                        .getString(
                                R.string.notification_listener_security_warning_title,
                                charSequence);
        String string2 =
                getResources()
                        .getString(
                                R.string.notification_listener_security_warning_summary,
                                charSequence);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = string2;
        alertParams.mTitle = string;
        alertParams.mCancelable = true;
        builder.setPositiveButton(
                R.string.allow,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.applications.specialaccess.notificationaccess.ScaryWarningDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ScaryWarningDialogFragment scaryWarningDialogFragment =
                                ScaryWarningDialogFragment.this;
                        boolean z2 = z;
                        NotificationAccessDetails notificationAccessDetails2 =
                                notificationAccessDetails;
                        ComponentName componentName = unflattenFromString;
                        if (!z2) {
                            NotificationAccessController.setAccess(
                                    scaryWarningDialogFragment.getContext(), componentName, true);
                            return;
                        }
                        scaryWarningDialogFragment.getClass();
                        notificationAccessDetails2.getClass();
                        notificationAccessDetails2.logSpecialPermissionChange(
                                true, componentName.getPackageName());
                        notificationAccessDetails2.mNm.setNotificationListenerAccessGranted(
                                componentName, true);
                        notificationAccessDetails2.refreshUi();
                    }
                });
        builder.setNegativeButton(
                R.string.deny,
                new ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0());
        return builder.create();
    }

    public final void setServiceInfo$1(
            ComponentName componentName, CharSequence charSequence, Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("c", componentName.flattenToString());
        bundle.putCharSequence("l", charSequence);
        setArguments(bundle);
        setTargetFragment(fragment, 0);
    }
}
