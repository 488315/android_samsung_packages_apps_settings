package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.widget;

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
            "Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/modules/enrolling/rfps/ui/widget/IconTouchDialog;",
            "Lcom/android/settings/core/instrumentation/InstrumentedDialogFragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class IconTouchDialog extends InstrumentedDialogFragment {
    public final DialogInterface.OnCancelListener onCancelListener;
    public final DialogInterface.OnClickListener onDismissListener;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 568;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Log.d("IconTouchDialog", "onCancel " + dialog);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 2132084211);
        builder.setTitle(R.string.security_settings_fingerprint_enroll_touch_dialog_title)
                .setMessage(R.string.security_settings_fingerprint_enroll_touch_dialog_message)
                .setPositiveButton(
                        R.string.security_settings_fingerprint_enroll_dialog_ok,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.widget.IconTouchDialog$onCreateDialog$1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                DialogInterface.OnClickListener onClickListener =
                                        IconTouchDialog.this.onDismissListener;
                                if (onClickListener != null) {
                                    onClickListener.onClick(dialogInterface, i);
                                } else {
                                    Intrinsics.throwUninitializedPropertyAccessException(
                                            "onDismissListener");
                                    throw null;
                                }
                            }
                        })
                .setOnCancelListener(
                        new DialogInterface
                                .OnCancelListener() { // from class:
                                                      // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.widget.IconTouchDialog$onCreateDialog$2
                            @Override // android.content.DialogInterface.OnCancelListener
                            public final void onCancel(DialogInterface dialogInterface) {
                                DialogInterface.OnCancelListener onCancelListener =
                                        IconTouchDialog.this.onCancelListener;
                                if (onCancelListener != null) {
                                    onCancelListener.onCancel(dialogInterface);
                                } else {
                                    Intrinsics.throwUninitializedPropertyAccessException(
                                            "onCancelListener");
                                    throw null;
                                }
                            }
                        });
        AlertDialog create = builder.create();
        Intrinsics.checkNotNullExpressionValue(create, "create(...)");
        return create;
    }
}
