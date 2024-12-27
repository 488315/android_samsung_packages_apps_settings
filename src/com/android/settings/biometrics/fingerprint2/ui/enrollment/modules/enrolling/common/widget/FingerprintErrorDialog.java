package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.common.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/modules/enrolling/common/widget/FingerprintErrorDialog;",
            "Lcom/android/settings/core/instrumentation/InstrumentedDialogFragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintErrorDialog extends InstrumentedDialogFragment {
    public final DialogInterface.OnCancelListener onCancelListener;
    public final DialogInterface.OnClickListener onContinue;
    public final DialogInterface.OnClickListener onTryAgain;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 569;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Log.d("FingerprintErrorDialog", "onCancel " + dialog);
        DialogInterface.OnCancelListener onCancelListener = this.onCancelListener;
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialog);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("onCancelListener");
            throw null;
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Log.d("FingerprintErrorDialog", "onCreateDialog " + this);
        int i = requireArguments().getInt("fingerprint_message");
        int i2 = requireArguments().getInt("fingerprint_title");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        boolean z = requireArguments().getBoolean("should_try_again");
        builder.setTitle(i2).setMessage(i).setCancelable(false);
        if (z) {
            final int i3 = 0;
            final int i4 = 1;
            builder.setPositiveButton(
                            R.string.security_settings_fingerprint_enroll_dialog_try_again,
                            new DialogInterface.OnClickListener(
                                    this) { // from class:
                                            // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.common.widget.FingerprintErrorDialog$onCreateDialog$1
                                public final /* synthetic */ FingerprintErrorDialog this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i5) {
                                    switch (i3) {
                                        case 0:
                                            dialogInterface.dismiss();
                                            this.this$0.getClass();
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "onTryAgain");
                                            throw null;
                                        case 1:
                                            dialogInterface.dismiss();
                                            this.this$0.getClass();
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "onContinue");
                                            throw null;
                                        default:
                                            dialogInterface.dismiss();
                                            this.this$0.getClass();
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "onContinue");
                                            throw null;
                                    }
                                }
                            })
                    .setNegativeButton(
                            R.string.security_settings_fingerprint_enroll_dialog_ok,
                            new DialogInterface.OnClickListener(
                                    this) { // from class:
                                            // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.common.widget.FingerprintErrorDialog$onCreateDialog$1
                                public final /* synthetic */ FingerprintErrorDialog this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i5) {
                                    switch (i4) {
                                        case 0:
                                            dialogInterface.dismiss();
                                            this.this$0.getClass();
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "onTryAgain");
                                            throw null;
                                        case 1:
                                            dialogInterface.dismiss();
                                            this.this$0.getClass();
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "onContinue");
                                            throw null;
                                        default:
                                            dialogInterface.dismiss();
                                            this.this$0.getClass();
                                            Intrinsics.throwUninitializedPropertyAccessException(
                                                    "onContinue");
                                            throw null;
                                    }
                                }
                            });
        } else {
            final int i5 = 2;
            builder.setPositiveButton(
                    R.string.security_settings_fingerprint_enroll_dialog_ok,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.common.widget.FingerprintErrorDialog$onCreateDialog$1
                        public final /* synthetic */ FingerprintErrorDialog this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i52) {
                            switch (i5) {
                                case 0:
                                    dialogInterface.dismiss();
                                    this.this$0.getClass();
                                    Intrinsics.throwUninitializedPropertyAccessException(
                                            "onTryAgain");
                                    throw null;
                                case 1:
                                    dialogInterface.dismiss();
                                    this.this$0.getClass();
                                    Intrinsics.throwUninitializedPropertyAccessException(
                                            "onContinue");
                                    throw null;
                                default:
                                    dialogInterface.dismiss();
                                    this.this$0.getClass();
                                    Intrinsics.throwUninitializedPropertyAccessException(
                                            "onContinue");
                                    throw null;
                            }
                        }
                    });
        }
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        return create;
    }
}
