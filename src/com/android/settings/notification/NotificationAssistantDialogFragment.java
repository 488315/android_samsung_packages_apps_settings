package com.android.settings.notification;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationAssistantDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 790;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        ComponentName unflattenFromString =
                ComponentName.unflattenFromString(getArguments().getString("c"));
        ConfigureNotificationSettings configureNotificationSettings =
                (ConfigureNotificationSettings) getTargetFragment();
        PreferenceScreen preferenceScreen = configureNotificationSettings.getPreferenceScreen();
        NotificationAssistantPreferenceController notificationAssistantPreferenceController =
                (NotificationAssistantPreferenceController)
                        configureNotificationSettings.use(
                                NotificationAssistantPreferenceController.class);
        notificationAssistantPreferenceController.setNotificationAssistantGranted(
                unflattenFromString);
        notificationAssistantPreferenceController.updateState(
                preferenceScreen.findPreference(
                        notificationAssistantPreferenceController.getPreferenceKey()));
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        String string =
                getResources().getString(R.string.notification_assistant_security_warning_summary);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = string;
        alertParams.mCancelable = true;
        builder.setPositiveButton(R.string.okay, this);
        return builder.create();
    }
}
