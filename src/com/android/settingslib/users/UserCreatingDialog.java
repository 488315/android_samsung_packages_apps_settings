package com.android.settingslib.users;

import android.R;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserCreatingDialog extends ProgressDialog {
    public UserCreatingDialog(FragmentActivity fragmentActivity, boolean z) {
        super(
                fragmentActivity,
                (fragmentActivity.getResources().getConfiguration().uiMode & 48) == 32
                        ? R.style.Theme.DeviceDefault.Dialog.Alert
                        : R.style.Theme.DeviceDefault.Light.Dialog.Alert);
        setCancelable(false);
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(
                                com.android.settings.R.layout.user_creation_progress_dialog,
                                (ViewGroup) null);
        String string =
                getContext()
                        .getString(
                                z
                                        ? com.android.settings.R.string
                                                .creating_new_guest_dialog_message
                                        : com.android.settings.R.string
                                                .creating_new_user_dialog_message);
        setMessage(string);
        inflate.setAccessibilityPaneTitle(string);
        ((TextView) inflate.findViewById(com.android.settings.R.id.message)).setText(string);
        setView(inflate);
        getWindow().setType(2010);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.privateFlags = 272;
        getWindow().setAttributes(attributes);
    }
}
