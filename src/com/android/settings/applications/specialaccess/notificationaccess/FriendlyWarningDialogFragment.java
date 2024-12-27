package com.android.settings.applications.specialaccess.notificationaccess;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FriendlyWarningDialogFragment extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 552;
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
                                R.string.notification_listener_disable_warning_summary,
                                charSequence);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = string;
        alertParams.mCancelable = true;
        builder.setPositiveButton(
                R.string.notification_listener_disable_warning_confirm,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.applications.specialaccess.notificationaccess.FriendlyWarningDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        FriendlyWarningDialogFragment friendlyWarningDialogFragment =
                                FriendlyWarningDialogFragment.this;
                        boolean z2 = z;
                        final NotificationAccessDetails notificationAccessDetails2 =
                                notificationAccessDetails;
                        final ComponentName componentName = unflattenFromString;
                        if (!z2) {
                            NotificationAccessController.deleteRules(
                                    friendlyWarningDialogFragment.getContext(), componentName);
                            NotificationAccessController.setAccess(
                                    friendlyWarningDialogFragment.getContext(),
                                    componentName,
                                    false);
                            return;
                        }
                        friendlyWarningDialogFragment.getClass();
                        notificationAccessDetails2.getClass();
                        notificationAccessDetails2.logSpecialPermissionChange(
                                true, componentName.getPackageName());
                        notificationAccessDetails2.mNm.setNotificationListenerAccessGranted(
                                componentName, false);
                        AsyncTask.execute(
                                new Runnable() { // from class:
                                                 // com.android.settings.applications.specialaccess.notificationaccess.NotificationAccessDetails$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        NotificationAccessDetails notificationAccessDetails3 =
                                                NotificationAccessDetails.this;
                                        ComponentName componentName2 = componentName;
                                        if (notificationAccessDetails3.mNm
                                                .isNotificationPolicyAccessGrantedForPackage(
                                                        componentName2.getPackageName())) {
                                            return;
                                        }
                                        notificationAccessDetails3.mNm.removeAutomaticZenRules(
                                                componentName2.getPackageName());
                                    }
                                });
                        notificationAccessDetails2.refreshUi();
                    }
                });
        builder.setNegativeButton(
                R.string.notification_listener_disable_warning_cancel,
                new ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0());
        return builder.create();
    }

    public final void setServiceInfo(
            ComponentName componentName, CharSequence charSequence, Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("c", componentName.flattenToString());
        bundle.putCharSequence("l", charSequence);
        setArguments(bundle);
        setTargetFragment(fragment, 0);
    }
}
