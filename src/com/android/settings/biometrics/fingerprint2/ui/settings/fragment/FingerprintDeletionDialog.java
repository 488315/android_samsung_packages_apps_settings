package com.android.settings.biometrics.fingerprint2.ui.settings.fragment;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResourcesManager;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.fingerprint.Fingerprint;
import android.os.Bundle;
import android.os.UserManager;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/android/settings/biometrics/fingerprint2/ui/settings/fragment/FingerprintDeletionDialog;", "Lcom/android/settings/core/instrumentation/InstrumentedDialogFragment;", "<init>", "()V", "applications__sources__apps__SecSettings__android_common__SecSettings-core"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintDeletionDialog extends InstrumentedDialogFragment {
    public FingerprintData fingerprintViewModel;
    public boolean isLastFingerprint;
    public DialogInterface.OnCancelListener onCancelListener;
    public DialogInterface.OnClickListener onClickListener;
    public DialogInterface.OnClickListener onNegativeClickListener;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 570;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        DialogInterface.OnCancelListener onCancelListener = this.onCancelListener;
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialog);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("onCancelListener");
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12, types: [T] */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v7, types: [T, java.lang.Object, java.lang.String] */
    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        DevicePolicyResourcesManager resources;
        Object obj = requireArguments().get("fingerprint");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.hardware.fingerprint.Fingerprint");
        Fingerprint fingerprint = (Fingerprint) obj;
        this.fingerprintViewModel = new FingerprintData(fingerprint.getDeviceId(), fingerprint.getBiometricId(), fingerprint.getName().toString());
        this.isLastFingerprint = requireArguments().getBoolean("IS_LAST_FINGERPRINT");
        FingerprintData fingerprintData = this.fingerprintViewModel;
        if (fingerprintData == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fingerprintViewModel");
            throw null;
        }
        String string = getString(R.string.fingerprint_delete_title, fingerprintData.name);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        FingerprintData fingerprintData2 = this.fingerprintViewModel;
        if (fingerprintData2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fingerprintViewModel");
            throw null;
        }
        ?? string2 = getString(R.string.fingerprint_v2_delete_message, fingerprintData2.name);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        ref$ObjectRef.element = string2;
        final Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        if (this.isLastFingerprint) {
            boolean isManagedProfile = UserManager.get(requireContext).isManagedProfile(requireContext.getUserId());
            String str = isManagedProfile ? "Settings.WORK_PROFILE_FINGERPRINT_LAST_DELETE_MESSAGE" : PeripheralBarcodeConstants.Symbology.UNDEFINED;
            final int i = isManagedProfile ? R.string.fingerprint_last_delete_message_profile_challenge : R.string.fingerprint_last_delete_message;
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) requireContext().getSystemService(DevicePolicyManager.class);
            ?? string3 = (devicePolicyManager == null || (resources = devicePolicyManager.getResources()) == null) ? 0 : resources.getString(str, new Supplier() { // from class: com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintDeletionDialog$onCreateDialog$1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Ref$ObjectRef.this.element + "\n\n" + requireContext.getString(i);
                }
            });
            if (string3 == 0) {
                string3 = ApnSettings.MVNO_NONE;
            }
            ref$ObjectRef.element = string3;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        alertParams.mMessage = (CharSequence) ref$ObjectRef.element;
        DialogInterface.OnClickListener onClickListener = this.onClickListener;
        if (onClickListener == null) {
            Intrinsics.throwUninitializedPropertyAccessException("onClickListener");
            throw null;
        }
        builder.setPositiveButton(R.string.security_settings_fingerprint_enroll_dialog_delete, onClickListener);
        DialogInterface.OnClickListener onClickListener2 = this.onNegativeClickListener;
        if (onClickListener2 != null) {
            builder.setNegativeButton(R.string.cancel, onClickListener2);
            return builder.create();
        }
        Intrinsics.throwUninitializedPropertyAccessException("onNegativeClickListener");
        throw null;
    }
}
