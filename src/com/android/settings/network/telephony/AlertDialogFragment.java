package com.android.settings.network.telephony;

import android.R;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AlertDialogFragment extends BaseDialogFragment
        implements DialogInterface.OnClickListener {
    public static void show(FragmentActivity fragmentActivity, String str, String str2) {
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UniversalCredentialUtil.AGENT_TITLE, str);
        bundle.putString("msg", str2);
        alertDialogFragment.setArguments(bundle);
        alertDialogFragment.show(
                fragmentActivity.getSupportFragmentManager(), "AlertDialogFragment");
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (getActivity() != null) {
            getActivity().finish();
        }
        dismissInternal(false, false);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String string = getArguments().getString(UniversalCredentialUtil.AGENT_TITLE);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        builder.setPositiveButton(R.string.ok, this);
        if (!TextUtils.isEmpty(getArguments().getString("msg"))) {
            alertParams.mMessage = getArguments().getString("msg");
        }
        return builder.create();
    }
}
