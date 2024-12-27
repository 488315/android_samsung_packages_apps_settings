package com.android.settings.development;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BackAnimationPreferenceDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    private BackAnimationPreferenceDialog() {}

    public static void show(Fragment fragment) {
        FragmentActivity activity = fragment.getActivity();
        if (activity == null) {
            return;
        }
        FragmentManagerImpl supportFragmentManager = activity.getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag("BackAnimationDlg") == null) {
            BackAnimationPreferenceDialog backAnimationPreferenceDialog =
                    new BackAnimationPreferenceDialog();
            backAnimationPreferenceDialog.setTargetFragment(fragment, 0);
            backAnimationPreferenceDialog.show(supportFragmentManager, "BackAnimationDlg");
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1925;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.back_navigation_animation)
                .setMessage(R.string.back_navigation_animation_dialog)
                .setPositiveButton(android.R.string.ok, this)
                .create();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {}
}
