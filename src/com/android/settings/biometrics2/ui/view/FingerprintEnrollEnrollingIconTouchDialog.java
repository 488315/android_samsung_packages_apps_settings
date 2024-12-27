package com.android.settings.biometrics2.ui.view;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

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
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollEnrollingIconTouchDialog;",
            "Landroidx/fragment/app/DialogFragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollEnrollingIconTouchDialog extends DialogFragment {
    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity, 2132084211);
        builder.setTitle(R.string.security_settings_fingerprint_enroll_touch_dialog_title);
        builder.setMessage(R.string.security_settings_fingerprint_enroll_touch_dialog_message);
        builder.setPositiveButton(
                R.string.security_settings_fingerprint_enroll_dialog_ok,
                FingerprintEnrollEnrollingIconTouchDialogKt$bindFingerprintEnrollEnrollingIconTouchDialog$1
                        .INSTANCE);
        return builder.create();
    }
}
