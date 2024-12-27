package com.android.settings.vpn2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConfirmLockdownFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ConfirmLockdownListener {
        void onConfirmLockdown(Bundle bundle, boolean z, boolean z2);
    }

    public static void show(
            Fragment fragment, boolean z, boolean z2, boolean z3, boolean z4, Bundle bundle) {
        if (fragment.getFragmentManager().findFragmentByTag("ConfirmLockdown") != null) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("replacing", z);
        bundle2.putBoolean("always_on", z2);
        bundle2.putBoolean("lockdown_old", z3);
        bundle2.putBoolean("lockdown_new", z4);
        bundle2.putParcelable(ImsProfile.SERVICE_OPTIONS, bundle);
        ConfirmLockdownFragment confirmLockdownFragment = new ConfirmLockdownFragment();
        confirmLockdownFragment.setArguments(bundle2);
        confirmLockdownFragment.setTargetFragment(fragment, 0);
        confirmLockdownFragment.show(fragment.getFragmentManager(), "ConfirmLockdown");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 548;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (getTargetFragment() instanceof ConfirmLockdownListener) {
            ((ConfirmLockdownListener) getTargetFragment())
                    .onConfirmLockdown(
                            (Bundle) getArguments().getParcelable(ImsProfile.SERVICE_OPTIONS),
                            getArguments().getBoolean("always_on"),
                            getArguments().getBoolean("lockdown_new"));
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        boolean z = getArguments().getBoolean("replacing");
        getArguments().getBoolean("always_on");
        boolean z2 = getArguments().getBoolean("lockdown_old");
        boolean z3 = getArguments().getBoolean("lockdown_new");
        int i =
                z3
                        ? R.string.vpn_require_connection_title
                        : z ? R.string.vpn_replace_vpn_title : R.string.vpn_set_vpn_title;
        int i2 = z ? R.string.vpn_replace : z3 ? R.string.vpn_turn_on : R.string.okay;
        int i3 =
                z3
                        ? z
                                ? R.string.vpn_replace_always_on_vpn_enable_message
                                : R.string.vpn_first_always_on_vpn_message
                        : z2
                                ? R.string.vpn_replace_always_on_vpn_disable_message
                                : R.string.vpn_replace_vpn_message;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(i);
        builder.setMessage(i3);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setPositiveButton(i2, this);
        return builder.create();
    }
}
