package com.samsung.android.settings.password;

import android.content.Intent;
import android.view.View;

import com.android.settings.password.SetupSkipDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SetupChooseLockGenericForNoneGoogle$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = SetupChooseLockGenericForNoneGoogle.$r8$clinit;
        SetupChooseLockGenericForNoneGoogle.SetupChooseLockGenericForNoneGoogleFragment
                setupChooseLockGenericForNoneGoogleFragment =
                        SetupChooseLockGenericForNoneGoogle
                                .SetupChooseLockGenericForNoneGoogleFragment
                                .mSetupChooseLockGenericForNoneGoogleFragment;
        Intent intent = setupChooseLockGenericForNoneGoogleFragment.getActivity().getIntent();
        SetupSkipDialog.newInstance(
                        intent.getBooleanExtra(":settings:frp_supported", false),
                        intent.getBooleanExtra("isSetupFlow", false))
                .show(setupChooseLockGenericForNoneGoogleFragment.getFragmentManager());
    }
}
