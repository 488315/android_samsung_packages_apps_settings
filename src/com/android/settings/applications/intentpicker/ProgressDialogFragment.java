package com.android.settings.applications.intentpicker;

import android.app.Dialog;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.utils.ThreadUtils;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProgressDialogFragment extends InstrumentedDialogFragment {
    public DomainVerificationManager mDomainVerificationManager;
    public Handler mHandle;
    public String mPackage;
    public ProgressAlertDialog mProgressAlertDialog;
    public List mSupportedLinkWrapperList;
    public SupportedLinkViewModel mViewModel;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ProgressAlertDialog extends AlertDialog {
        public ProgressBar mProgressBar;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1978;
    }

    public final void launchSupportedLinksDialogFragment() {
        if (((ArrayList) this.mSupportedLinkWrapperList).size() > 0) {
            this.mViewModel.mSupportedLinkWrapperList = this.mSupportedLinkWrapperList;
            Bundle bundle = new Bundle();
            bundle.putString("app_package", this.mPackage);
            SupportedLinksDialogFragment supportedLinksDialogFragment =
                    new SupportedLinksDialogFragment();
            supportedLinksDialogFragment.setArguments(bundle);
            supportedLinksDialogFragment.show(
                    getActivity().getSupportFragmentManager(), "SupportedLinksDialog");
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity owner = getActivity();
        Intrinsics.checkNotNullParameter(owner, "owner");
        ViewModelStore store = owner.getViewModelStore();
        ViewModelProvider.Factory factory = owner.getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras = owner.getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl m =
                ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                        defaultViewModelCreationExtras,
                        "defaultCreationExtras",
                        store,
                        factory,
                        defaultViewModelCreationExtras);
        KClass modelClass = JvmClassMappingKt.getKotlinClass(SupportedLinkViewModel.class);
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        String qualifiedName = modelClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        this.mViewModel =
                (SupportedLinkViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                modelClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        this.mPackage = getArguments().getString("app_package");
        this.mDomainVerificationManager =
                (DomainVerificationManager)
                        getActivity().getSystemService(DomainVerificationManager.class);
        this.mHandle = new Handler(Looper.getMainLooper());
        FragmentActivity activity = getActivity();
        ProgressAlertDialog progressAlertDialog = new ProgressAlertDialog(activity, 0);
        View inflate =
                LayoutInflater.from(activity)
                        .inflate(R.layout.app_launch_progress, (ViewGroup) null);
        ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.scan_links_progressbar);
        progressAlertDialog.mProgressBar = progressBar;
        progressBar.setProgress(0);
        progressAlertDialog.mProgressBar.setMax(100);
        progressAlertDialog.setView$1(inflate);
        String string = activity.getResources().getString(R.string.app_launch_checking_links_title);
        boolean z = IntentPickerUtils.DEBUG;
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(
                new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, string.length(), 0);
        progressAlertDialog.setTitle(spannableString);
        progressAlertDialog.setButton(
                -2,
                activity.getText(R.string.app_launch_dialog_cancel),
                new ProgressDialogFragment$$ExternalSyntheticLambda2());
        progressAlertDialog.setCanceledOnTouchOutside(true);
        this.mProgressAlertDialog = progressAlertDialog;
        return progressAlertDialog;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        ProgressAlertDialog progressAlertDialog = this.mProgressAlertDialog;
        if (progressAlertDialog == null || !progressAlertDialog.isShowing()) {
            return;
        }
        this.mProgressAlertDialog.cancel();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        ThreadUtils.postOnBackgroundThread(
                new ProgressDialogFragment$$ExternalSyntheticLambda1(this, 0));
    }
}
