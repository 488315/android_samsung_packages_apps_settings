package com.android.settings.biometrics2.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.R;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollFindSensorViewModel;

import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/biometrics2/ui/view/SkipSetupFindFpsDialog;",
            "Landroidx/fragment/app/DialogFragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class SkipSetupFindFpsDialog extends DialogFragment {
    public FingerprintEnrollFindSensorViewModel mViewModel;

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        ViewModelStore store = requireActivity.getViewModelStore();
        ViewModelProvider.Factory factory = requireActivity.getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras =
                requireActivity.getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl m =
                ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                        defaultViewModelCreationExtras,
                        "defaultCreationExtras",
                        store,
                        factory,
                        defaultViewModelCreationExtras);
        KClass modelClass =
                JvmClassMappingKt.getKotlinClass(FingerprintEnrollFindSensorViewModel.class);
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        String qualifiedName = modelClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        this.mViewModel =
                (FingerprintEnrollFindSensorViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                modelClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        super.onAttach(context);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        DialogInterface.OnClickListener onClickListener =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.biometrics2.ui.view.SkipSetupFindFpsDialog$onCreateDialog$1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        FingerprintEnrollFindSensorViewModel fingerprintEnrollFindSensorViewModel =
                                SkipSetupFindFpsDialog.this.mViewModel;
                        if (fingerprintEnrollFindSensorViewModel != null) {
                            fingerprintEnrollFindSensorViewModel.mActionLiveData.postValue(0);
                        }
                    }
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity, 2132084211);
        builder.setTitle(R.string.setup_fingerprint_enroll_skip_title);
        builder.setPositiveButton(R.string.skip_anyway_button_label, onClickListener);
        builder.setNegativeButton(
                R.string.go_back_button_label, (DialogInterface.OnClickListener) null);
        builder.setMessage(R.string.setup_fingerprint_enroll_skip_after_adding_lock_text);
        return builder.create();
    }
}
