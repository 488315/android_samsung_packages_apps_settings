package com.android.settings.biometrics.fingerprint2.ui.settings.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.fingerprint.Fingerprint;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.widget.ImeAwareEditText;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData;
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
            "Lcom/android/settings/biometrics/fingerprint2/ui/settings/fragment/FingerprintSettingsRenameDialog;",
            "Lcom/android/settings/core/instrumentation/InstrumentedDialogFragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintSettingsRenameDialog extends InstrumentedDialogFragment {
    public DialogInterface.OnCancelListener onCancelListener;
    public DialogInterface.OnClickListener onClickListener;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 570;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Log.d("FingerprintSettingsRenameDialog", "onCancel " + dialog);
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
        Log.d("FingerprintSettingsRenameDialog", "onCreateDialog " + this);
        Object obj = requireArguments().get("fingerprint");
        Intrinsics.checkNotNull(
                obj,
                "null cannot be cast to non-null type android.hardware.fingerprint.Fingerprint");
        Fingerprint fingerprint = (Fingerprint) obj;
        final FingerprintData fingerprintData =
                new FingerprintData(
                        fingerprint.getDeviceId(),
                        fingerprint.getBiometricId(),
                        fingerprint.getName().toString());
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext);
        builder.setView(R.layout.fingerprint_rename_dialog);
        DialogInterface.OnClickListener onClickListener = this.onClickListener;
        if (onClickListener == null) {
            Intrinsics.throwUninitializedPropertyAccessException("onClickListener");
            throw null;
        }
        builder.setPositiveButton(
                R.string.security_settings_fingerprint_enroll_dialog_ok, onClickListener);
        AlertDialog create = builder.create();
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsRenameDialog$onCreateDialog$1
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        Dialog dialog = FingerprintSettingsRenameDialog.this.mDialog;
                        ImeAwareEditText imeAwareEditText =
                                dialog != null
                                        ? (ImeAwareEditText)
                                                dialog.findViewById(R.id.fingerprint_rename_field)
                                        : null;
                        if (imeAwareEditText != null) {
                            FingerprintData fingerprintData2 = fingerprintData;
                            FingerprintSettingsRenameDialog fingerprintSettingsRenameDialog =
                                    FingerprintSettingsRenameDialog.this;
                            imeAwareEditText.setText(fingerprintData2.name);
                            fingerprintSettingsRenameDialog.getClass();
                            imeAwareEditText.setFilters(
                                    new InputFilter[] {
                                        new FingerprintSettingsRenameDialog$getFilters$filter$1()
                                    });
                            imeAwareEditText.selectAll();
                            imeAwareEditText.requestFocus();
                            imeAwareEditText.scheduleShowSoftInput();
                        }
                    }
                });
        return create;
    }
}
