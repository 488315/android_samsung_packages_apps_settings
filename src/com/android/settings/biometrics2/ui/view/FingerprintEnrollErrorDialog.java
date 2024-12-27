package com.android.settings.biometrics2.ui.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.R;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollErrorDialogViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintErrorDialogSetResultAction;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollErrorDialog;",
            "Landroidx/fragment/app/DialogFragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollErrorDialog extends DialogFragment {
    public final FingerprintEnrollErrorDialogViewModel getViewModel() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return null;
        }
        ViewModelStore store = activity.getViewModelStore();
        ViewModelProvider.Factory factory = activity.getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras =
                activity.getDefaultViewModelCreationExtras();
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
                JvmClassMappingKt.getKotlinClass(FingerprintEnrollErrorDialogViewModel.class);
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        String qualifiedName = modelClass.getQualifiedName();
        if (qualifiedName != null) {
            return (FingerprintEnrollErrorDialogViewModel)
                    m.getViewModel$lifecycle_viewmodel_release(
                            modelClass,
                            "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                    .concat(qualifiedName));
        }
        throw new IllegalArgumentException(
                "Local and anonymous classes can not be ViewModels".toString());
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        int i = requireArguments().getInt("error_msg_id");
        final FingerprintErrorDialogSetResultAction fingerprintErrorDialogSetResultAction =
                i == 3
                        ? FingerprintErrorDialogSetResultAction
                                .FINGERPRINT_ERROR_DIALOG_ACTION_SET_RESULT_TIMEOUT
                        : FingerprintErrorDialogSetResultAction
                                .FINGERPRINT_ERROR_DIALOG_ACTION_SET_RESULT_FINISH;
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        FingerprintEnrollErrorDialogViewModel viewModel = getViewModel();
        Intrinsics.checkNotNull(viewModel);
        DialogInterface.OnClickListener onClickListener =
                new DialogInterface.OnClickListener() { // from class:
                    // com.android.settings.biometrics2.ui.view.FingerprintEnrollErrorDialog$onCreateDialog$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    @Metadata(
                            d1 = {
                                "\u0000\n\n"
                                    + "\u0000\n"
                                    + "\u0002\u0010\u0002\n"
                                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
                            },
                            d2 = {
                                "<anonymous>",
                                ApnSettings.MVNO_NONE,
                                "Lkotlinx/coroutines/CoroutineScope;"
                            },
                            k = 3,
                            mv = {1, 9, 0},
                            xi = 48)
                    /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollErrorDialog$onCreateDialog$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        final /* synthetic */ DialogInterface $dialog;
                        int label;
                        final /* synthetic */ FingerprintEnrollErrorDialog this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(
                                FingerprintEnrollErrorDialog fingerprintEnrollErrorDialog,
                                DialogInterface dialogInterface,
                                Continuation continuation) {
                            super(2, continuation);
                            this.this$0 = fingerprintEnrollErrorDialog;
                            this.$dialog = dialogInterface;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(this.this$0, this.$dialog, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1)
                                            create((CoroutineScope) obj, (Continuation) obj2))
                                    .invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons =
                                    CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            Unit unit = Unit.INSTANCE;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                Log.d("FingerprintEnrollErrorDialog", "tryAgain flow");
                                FingerprintEnrollErrorDialogViewModel viewModel =
                                        this.this$0.getViewModel();
                                if (viewModel != null) {
                                    this.label = 1;
                                    viewModel._isDialogShown.compareAndSet(true, false);
                                    Object emit =
                                            viewModel._triggerRetryFlow.emit(new Object(), this);
                                    if (emit != coroutineSingletons) {
                                        emit = unit;
                                    }
                                    if (emit == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException(
                                            "call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                            }
                            DialogInterface dialogInterface = this.$dialog;
                            if (dialogInterface != null) {
                                dialogInterface.dismiss();
                            }
                            return unit;
                        }
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        FragmentActivity activity = FingerprintEnrollErrorDialog.this.getActivity();
                        if (activity != null) {
                            BuildersKt.launch$default(
                                    LifecycleOwnerKt.getLifecycleScope(activity),
                                    null,
                                    null,
                                    new AnonymousClass1(
                                            FingerprintEnrollErrorDialog.this,
                                            dialogInterface,
                                            null),
                                    3);
                        }
                    }
                };
        DialogInterface.OnClickListener onClickListener2 =
                new DialogInterface.OnClickListener() { // from class:
                    // com.android.settings.biometrics2.ui.view.FingerprintEnrollErrorDialog$onCreateDialog$2

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    @Metadata(
                            d1 = {
                                "\u0000\n\n"
                                    + "\u0000\n"
                                    + "\u0002\u0010\u0002\n"
                                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
                            },
                            d2 = {
                                "<anonymous>",
                                ApnSettings.MVNO_NONE,
                                "Lkotlinx/coroutines/CoroutineScope;"
                            },
                            k = 3,
                            mv = {1, 9, 0},
                            xi = 48)
                    /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollErrorDialog$onCreateDialog$2$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        final /* synthetic */ DialogInterface $dialog;
                        final /* synthetic */ FingerprintErrorDialogSetResultAction
                                $okButtonSetResultAction;
                        int label;
                        final /* synthetic */ FingerprintEnrollErrorDialog this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(
                                FingerprintErrorDialogSetResultAction
                                        fingerprintErrorDialogSetResultAction,
                                FingerprintEnrollErrorDialog fingerprintEnrollErrorDialog,
                                DialogInterface dialogInterface,
                                Continuation continuation) {
                            super(2, continuation);
                            this.$okButtonSetResultAction = fingerprintErrorDialogSetResultAction;
                            this.this$0 = fingerprintEnrollErrorDialog;
                            this.$dialog = dialogInterface;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(
                                    this.$okButtonSetResultAction,
                                    this.this$0,
                                    this.$dialog,
                                    continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1)
                                            create((CoroutineScope) obj, (Continuation) obj2))
                                    .invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons =
                                    CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            Unit unit = Unit.INSTANCE;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                Log.d(
                                        "FingerprintEnrollErrorDialog",
                                        "ok flow as " + this.$okButtonSetResultAction);
                                FingerprintEnrollErrorDialogViewModel viewModel =
                                        this.this$0.getViewModel();
                                if (viewModel != null) {
                                    FingerprintErrorDialogSetResultAction
                                            fingerprintErrorDialogSetResultAction =
                                                    this.$okButtonSetResultAction;
                                    this.label = 1;
                                    viewModel._isDialogShown.compareAndSet(true, false);
                                    Object emit =
                                            viewModel._setResultFlow.emit(
                                                    fingerprintErrorDialogSetResultAction, this);
                                    if (emit != coroutineSingletons) {
                                        emit = unit;
                                    }
                                    if (emit == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException(
                                            "call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                            }
                            DialogInterface dialogInterface = this.$dialog;
                            if (dialogInterface != null) {
                                dialogInterface.dismiss();
                            }
                            return unit;
                        }
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        FragmentActivity activity = FingerprintEnrollErrorDialog.this.getActivity();
                        if (activity != null) {
                            BuildersKt.launch$default(
                                    LifecycleOwnerKt.getLifecycleScope(activity),
                                    null,
                                    null,
                                    new AnonymousClass1(
                                            fingerprintErrorDialogSetResultAction,
                                            FingerprintEnrollErrorDialog.this,
                                            dialogInterface,
                                            null),
                                    3);
                        }
                    }
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity);
        String string =
                requireActivity.getString(
                        i != 3
                                ? i != 18
                                        ? R.string
                                                .security_settings_fingerprint_enroll_error_unable_to_process_dialog_title
                                        : R.string
                                                .security_settings_fingerprint_bad_calibration_title
                                : R.string.security_settings_fingerprint_enroll_error_dialog_title);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        boolean z = viewModel.isSuw;
        int i2 = R.string.security_settings_fingerprint_bad_calibration;
        if (z) {
            if (i == 2) {
                i2 =
                        R.string
                                .security_settings_fingerprint_enroll_error_unable_to_process_message_setup;
            } else if (i == 3) {
                i2 =
                        R.string
                                .security_settings_fingerprint_enroll_error_timeout_dialog_message_setup;
            } else if (i != 18) {
                i2 =
                        R.string
                                .security_settings_fingerprint_enroll_error_generic_dialog_message_setup;
            }
        } else if (i == 2) {
            i2 = R.string.security_settings_fingerprint_enroll_error_unable_to_process_message;
        } else if (i == 3) {
            i2 = R.string.security_settings_fingerprint_enroll_error_timeout_dialog_message;
        } else if (i != 18) {
            i2 = R.string.security_settings_fingerprint_enroll_error_generic_dialog_message;
        }
        alertParams.mMessage = requireActivity.getString(i2);
        alertParams.mCancelable = false;
        if (i == 2) {
            builder.setPositiveButton(
                    R.string.security_settings_fingerprint_enroll_dialog_try_again,
                    onClickListener);
            builder.setNegativeButton(
                    R.string.security_settings_fingerprint_enroll_dialog_ok, onClickListener2);
        } else {
            builder.setPositiveButton(
                    R.string.security_settings_fingerprint_enroll_dialog_ok, onClickListener2);
        }
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        return create;
    }
}
