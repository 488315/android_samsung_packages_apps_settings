package com.samsung.android.settings.security;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.android.settings.R;
import com.android.settings.security.LockUnificationPreferenceController;

import com.samsung.android.settings.knox.KnoxUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricRemoveWarningDialog extends DialogFragment {
    public static BiometricRemoveWarningDialog mInstance;
    public LockUnificationPreferenceController.AnonymousClass1 mCallback;
    public int mUserId;

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        String format =
                String.format(
                        getActivity()
                                .getString(
                                        R.string
                                                .sec_workspace_locktype_change_nonsecure_biometric_remove_workspace_bio),
                        KnoxUtils.getKnoxName(getActivity(), this.mUserId));
        AlertDialog.Builder builder =
                new AlertDialog.Builder(
                        new ContextThemeWrapper(
                                getActivity(), android.R.style.Theme.DeviceDefault.DayNight));
        builder.setTitle(R.string.sec_screen_locktype_change_nonsecure_title_workspace);
        builder.P.mMessage = format;
        builder.setPositiveButton(
                R.string.sec_unlock_disable_frp_warning_ok_biometric,
                new BiometricRemoveWarningDialog$$ExternalSyntheticLambda0(this, 0));
        builder.setNegativeButton(
                R.string.cancel,
                new BiometricRemoveWarningDialog$$ExternalSyntheticLambda0(this, 1));
        AlertDialog create = builder.create();
        create.setOnKeyListener(
                new BiometricRemoveWarningDialog$$ExternalSyntheticLambda2(this, 0));
        create.setCanceledOnTouchOutside(false);
        return create;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final void show(FragmentManager fragmentManager, String str) {
        if (fragmentManager.findFragmentByTag(str) == null) {
            super.show(fragmentManager, str);
        }
    }
}
