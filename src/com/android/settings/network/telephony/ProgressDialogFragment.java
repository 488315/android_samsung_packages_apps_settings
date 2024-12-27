package com.android.settings.network.telephony;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.android.settings.R;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ProgressDialogFragment extends DialogFragment {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog
                .getWindow()
                .setBackgroundDrawableResource(R.drawable.sim_progress_dialog_rounded_bg);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getArguments().getString(UniversalCredentialUtil.AGENT_TITLE));
        progressDialog.setOnKeyListener(new ProgressDialogFragment$$ExternalSyntheticLambda0());
        return progressDialog;
    }
}
