package com.android.settings.applications.intentpicker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.icu.text.MessageFormat;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.ArraySet;
import android.util.Log;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SupportedLinksDialogFragment extends InstrumentedDialogFragment {
    public String mPackage;
    public List mSupportedLinkWrapperList;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1979;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPackage = getArguments().getString("app_package");
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
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(SupportedLinkViewModel.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        this.mSupportedLinkWrapperList =
                ((SupportedLinkViewModel)
                                m.getViewModel$lifecycle_viewmodel_release(
                                        kotlinClass,
                                        "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                                .concat(qualifiedName)))
                        .mSupportedLinkWrapperList;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        FragmentActivity activity = getActivity();
        SupportedLinksAdapter supportedLinksAdapter =
                new SupportedLinksAdapter(activity, this.mSupportedLinkWrapperList);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        int size = this.mSupportedLinkWrapperList.size();
        MessageFormat messageFormat =
                new MessageFormat(
                        getResources().getString(R.string.app_launch_supported_links_title),
                        Locale.getDefault());
        HashMap hashMap = new HashMap();
        hashMap.put("count", Integer.valueOf(size));
        String format = messageFormat.format(hashMap);
        boolean z = IntentPickerUtils.DEBUG;
        SpannableString spannableString = new SpannableString(format);
        spannableString.setSpan(
                new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, format.length(), 0);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = spannableString;
        builder.setAdapter(supportedLinksAdapter, null);
        alertParams.mCancelable = true;
        builder.setPositiveButton(
                R.string.app_launch_supported_links_add,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.applications.intentpicker.SupportedLinksDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        SupportedLinksDialogFragment supportedLinksDialogFragment =
                                SupportedLinksDialogFragment.this;
                        DomainVerificationManager domainVerificationManager =
                                (DomainVerificationManager)
                                        supportedLinksDialogFragment
                                                .getActivity()
                                                .getSystemService(DomainVerificationManager.class);
                        DomainVerificationUserState domainVerificationUserState =
                                IntentPickerUtils.getDomainVerificationUserState(
                                        domainVerificationManager,
                                        supportedLinksDialogFragment.mPackage);
                        if (domainVerificationUserState == null
                                || supportedLinksDialogFragment.mSupportedLinkWrapperList == null) {
                            return;
                        }
                        ArraySet arraySet = new ArraySet();
                        for (SupportedLinkWrapper supportedLinkWrapper :
                                supportedLinksDialogFragment.mSupportedLinkWrapperList) {
                            if (supportedLinkWrapper.mIsChecked) {
                                arraySet.add(supportedLinkWrapper.mHost);
                            }
                        }
                        if (arraySet.size() > 0) {
                            try {
                                domainVerificationManager.setDomainVerificationUserSelection(
                                        domainVerificationUserState.getIdentifier(),
                                        arraySet,
                                        true);
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.w(
                                        "SupportedLinksDialogFrg",
                                        "addSelectedItems : " + e.getMessage());
                            }
                        }
                        for (Fragment fragment :
                                supportedLinksDialogFragment
                                        .getActivity()
                                        .getSupportFragmentManager()
                                        .mFragmentStore
                                        .getFragments()) {
                            if ((fragment instanceof AppLaunchSettings)
                                    && ((AppLaunchSettings) fragment).getLinksNumber(1) != 0) {
                                throw null;
                            }
                        }
                    }
                });
        builder.setNegativeButton(
                R.string.app_launch_dialog_cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }
}
