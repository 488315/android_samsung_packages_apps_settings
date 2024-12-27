package com.google.android.material.bottomsheet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class BottomSheetDialogFragment extends AppCompatDialogFragment {
    @Override // androidx.fragment.app.DialogFragment
    public final void dismiss() {
        Dialog dialog = this.mDialog;
        if (dialog instanceof BottomSheetDialog) {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
            if (bottomSheetDialog.behavior == null) {
                bottomSheetDialog.ensureContainerAndBehavior();
            }
            boolean z = bottomSheetDialog.behavior.hideable;
        }
        dismissInternal(false, false);
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment,
              // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Context context = getContext();
        int i = this.mTheme;
        if (i == 0) {
            TypedValue typedValue = new TypedValue();
            i =
                    context.getTheme()
                                    .resolveAttribute(
                                            R.attr.bottomSheetDialogTheme, typedValue, true)
                            ? typedValue.resourceId
                            : 2132084262;
        }
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, i);
        bottomSheetDialog.cancelable = true;
        bottomSheetDialog.canceledOnTouchOutside = true;
        bottomSheetDialog.bottomSheetCallback =
                new BottomSheetBehavior
                        .BottomSheetCallback() { // from class:
                                                 // com.google.android.material.bottomsheet.BottomSheetDialog.5
                    public AnonymousClass5() {}

                    @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
                    public final void onStateChanged(View view, int i2) {
                        if (i2 == 5) {
                            BottomSheetDialog.this.cancel();
                        }
                    }

                    @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
                    public final void onSlide(View view) {}
                };
        bottomSheetDialog.getDelegate().requestWindowFeature(1);
        bottomSheetDialog.edgeToEdgeEnabled =
                bottomSheetDialog
                        .getContext()
                        .getTheme()
                        .obtainStyledAttributes(new int[] {R.attr.enableEdgeToEdge})
                        .getBoolean(0, false);
        return bottomSheetDialog;
    }
}
