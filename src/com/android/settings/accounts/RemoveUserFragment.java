package com.android.settings.accounts;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.UserManager;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.users.UserDialogs;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RemoveUserFragment extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 534;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        final int i = getArguments().getInt("userId");
        return UserDialogs.createRemoveDialog(
                getActivity(),
                i,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.accounts.RemoveUserFragment.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        ((UserManager)
                                        RemoveUserFragment.this
                                                .getActivity()
                                                .getSystemService("user"))
                                .removeUser(i);
                    }
                });
    }
}
