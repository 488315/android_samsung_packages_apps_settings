package com.samsung.android.settings.analyzestorage.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.FileOperationArgs;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/dialog/SpinnerProgressDialogFragment;",
            "Lcom/samsung/android/settings/analyzestorage/ui/dialog/AbsDialog;",
            ApnSettings.MVNO_NONE,
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class SpinnerProgressDialogFragment extends AbsDialog {
    public int textResId = -1;
    public final String SPINNER_PROGRESS_DIALOG_TAG = "spin_dialog";

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final AlertDialog createDialog$2() {
        TextView textView;
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext);
        View inflate =
                LayoutInflater.from(requireContext)
                        .inflate(R.layout.as_spinner_progress_layout, (ViewGroup) null);
        if (this.textResId != -1
                && (textView = (TextView) inflate.findViewById(R.id.progress_text)) != null) {
            textView.setText(this.textResId);
        }
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.setCancelable(false);
        create.setCanceledOnTouchOutside(false);
        return create;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final DialogInterface.OnKeyListener getOnKeyListener() {
        return null;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final boolean isCanceledOnTouchOutside() {
        return false;
    }

    public final void onPrepareProgress(FileOperationArgs fileOperationArgs) {
        if (fileOperationArgs == null) {
            Log.e("SpinnerProgressDialogFragment", "onPrepareProgress - args is null");
            return;
        }
        FragmentManager fragmentManager = this.baseFragmentManager;
        Fragment findFragmentByTag =
                fragmentManager != null
                        ? fragmentManager.findFragmentByTag(this.SPINNER_PROGRESS_DIALOG_TAG)
                        : null;
        if (findFragmentByTag == null || !findFragmentByTag.isResumed()) {
            String str = this.SPINNER_PROGRESS_DIALOG_TAG;
            FragmentManager fragmentManager2 = this.baseFragmentManager;
            if (fragmentManager2 != null) {
                show(fragmentManager2, str);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("SpinnerProgressDialogFragment", "onResume");
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        outState.putInt("text_id", this.textResId);
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final void restoreStateOnCreate(Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(savedInstanceState, "savedInstanceState");
        this.textResId = savedInstanceState.getInt("text_id");
    }
}
