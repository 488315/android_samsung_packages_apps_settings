package com.android.settings.applications.specialaccess.zenaccess;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.notification.zen.ZenAccessSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FriendlyWarningDialogFragment extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 555;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        onCreate(bundle);
        Bundle arguments = getArguments();
        final String string = arguments.getString("p");
        String string2 =
                getResources()
                        .getString(
                                R.string.zen_access_revoke_warning_dialog_title,
                                arguments.getString("l"));
        String string3 =
                getResources().getString(R.string.zen_access_revoke_warning_dialog_summary);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = string3;
        alertParams.mTitle = string2;
        alertParams.mCancelable = true;
        builder.setPositiveButton(
                R.string.okay,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.applications.specialaccess.zenaccess.FriendlyWarningDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        FriendlyWarningDialogFragment friendlyWarningDialogFragment =
                                FriendlyWarningDialogFragment.this;
                        String str = string;
                        ZenAccessController.deleteRules(
                                friendlyWarningDialogFragment.getContext(), str);
                        ZenAccessController.setAccess(
                                friendlyWarningDialogFragment.getContext(), str, false);
                        if (friendlyWarningDialogFragment.getTargetFragment()
                                instanceof ZenAccessSettings) {
                            ((ZenAccessSettings) friendlyWarningDialogFragment.getTargetFragment())
                                    .reloadList();
                        } else {
                            ((ZenAccessDetails) friendlyWarningDialogFragment.getTargetFragment())
                                    .refreshUi();
                        }
                    }
                });
        builder.setNegativeButton(
                R.string.cancel,
                new ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0());
        return builder.create();
    }

    public final void setPkgInfo(String str, CharSequence charSequence, Fragment fragment) {
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("p", str);
        if (!TextUtils.isEmpty(charSequence)) {
            str = charSequence.toString();
        }
        m.putString("l", str);
        setTargetFragment(fragment, 0);
        setArguments(m);
    }
}
