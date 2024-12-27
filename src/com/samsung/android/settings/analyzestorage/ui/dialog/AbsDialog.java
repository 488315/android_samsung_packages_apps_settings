package com.samsung.android.settings.analyzestorage.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.UserInteractionDialog$Callback;
import com.samsung.android.settings.analyzestorage.presenter.controllers.DialogViewModel;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.util.LinkedHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0003\b'\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/dialog/AbsDialog;",
            "Landroidx/fragment/app/DialogFragment;",
            ApnSettings.MVNO_NONE,
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public abstract class AbsDialog extends DialogFragment {
    public AlertDialog baseDialog;
    public FragmentManager baseFragmentManager;
    public UserInteractionDialog$Callback callback;
    public final Lazy viewModel$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog$viewModel$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            AbsDialog owner = AbsDialog.this;
                            Intrinsics.checkNotNullParameter(owner, "owner");
                            ViewModelStore store = owner.getViewModelStore();
                            ViewModelProvider.Factory factory =
                                    owner.getDefaultViewModelProviderFactory();
                            CreationExtras defaultViewModelCreationExtras =
                                    owner.getDefaultViewModelCreationExtras();
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
                                    JvmClassMappingKt.getKotlinClass(DialogViewModel.class);
                            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
                            String qualifiedName = modelClass.getQualifiedName();
                            if (qualifiedName != null) {
                                return (DialogViewModel)
                                        m.getViewModel$lifecycle_viewmodel_release(
                                                modelClass,
                                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                                        .concat(qualifiedName));
                            }
                            throw new IllegalArgumentException(
                                    "Local and anonymous classes can not be ViewModels".toString());
                        }
                    });
    public String viewModelKey = getClass().getSimpleName();

    public final void cancel() {
        if (this.callback != null) {
            clearDataFromViewModel();
        }
        dismissAllowingStateLoss();
    }

    public void clearDataFromViewModel() {
        DialogViewModel viewModel = getViewModel();
        String key = this.viewModelKey;
        viewModel.getClass();
        Intrinsics.checkNotNullParameter(key, "key");
        viewModel.dialogDataMap.remove(key);
    }

    public abstract AlertDialog createDialog$2();

    public final Context getBaseContext() {
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        return requireContext;
    }

    public DialogInterface.OnKeyListener getOnKeyListener() {
        return new DialogInterface
                .OnKeyListener() { // from class:
                                   // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog$getOnKeyListener$1
            @Override // android.content.DialogInterface.OnKeyListener
            public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                boolean z = 4 == i || 111 == i;
                if (z) {
                    AbsDialog.this.cancel();
                }
                return z || 82 == i;
            }
        };
    }

    public final DialogViewModel getViewModel() {
        return (DialogViewModel) this.viewModel$delegate.getValue();
    }

    public boolean isCanceledOnTouchOutside() {
        return !(this instanceof FormatProgressDialogFragment);
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Log.d("AbsDialog", "onCancel");
        cancel();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            FragmentActivity activity = getActivity();
            this.baseFragmentManager =
                    activity != null ? activity.getSupportFragmentManager() : null;
            restoreStateOnCreate(bundle);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog createDialog$2 = createDialog$2();
        this.baseDialog = createDialog$2;
        createDialog$2.setCanceledOnTouchOutside(isCanceledOnTouchOutside());
        createDialog$2.setOnKeyListener(getOnKeyListener());
        boolean z = bundle != null;
        this.viewModelKey = getClass().getSimpleName();
        if (z) {
            DialogViewModel viewModel = getViewModel();
            String key = this.viewModelKey;
            viewModel.getClass();
            Intrinsics.checkNotNullParameter(key, "key");
            Object obj = ((LinkedHashMap) viewModel.dialogDataMap).get(key);
            if (obj == null) {
                obj = null;
            }
            DialogViewModel.DialogViewData dialogViewData = (DialogViewModel.DialogViewData) obj;
            this.callback = dialogViewData != null ? dialogViewData.dialogCallback : null;
            setDialogAnchorView();
        } else {
            setDialogAnchorView();
        }
        return createDialog$2;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.callback = null;
        this.baseFragmentManager = null;
        this.baseDialog = null;
    }

    public void restoreStateOnCreate(Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(savedInstanceState, "savedInstanceState");
    }

    public final void setDialogAnchorView() {
        DialogViewModel viewModel = getViewModel();
        String key = this.viewModelKey;
        UserInteractionDialog$Callback userInteractionDialog$Callback = this.callback;
        viewModel.getClass();
        Intrinsics.checkNotNullParameter(key, "key");
        Log.v(
                "DialogViewModel",
                "putData, key : "
                        + key
                        + ", callback : "
                        + userInteractionDialog$Callback
                        + ", size : "
                        + viewModel.dialogDataMap.size());
        viewModel.dialogDataMap.put(
                key, new DialogViewModel.DialogViewData(userInteractionDialog$Callback));
    }

    @Override // androidx.fragment.app.DialogFragment
    public final void show(FragmentManager manager, String str) {
        Intrinsics.checkNotNullParameter(manager, "manager");
        try {
            BackStackRecord backStackRecord = new BackStackRecord(manager);
            backStackRecord.doAddOp(0, this, str, 1);
            backStackRecord.commitInternal(true);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public final void showDialog(UserInteractionDialog$Callback userInteractionDialog$Callback) {
        this.callback = userInteractionDialog$Callback;
        FragmentManager fragmentManager = this.baseFragmentManager;
        if (fragmentManager != null) {
            show(fragmentManager, null);
        }
    }
}
