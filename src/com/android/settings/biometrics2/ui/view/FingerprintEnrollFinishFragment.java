package com.android.settings.biometrics2.ui.view;

import android.content.Context;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.R;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.biometrics2.data.repository.FingerprintRepository;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollFinishViewModel;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;

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
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollFinishFragment;",
            "Landroidx/fragment/app/Fragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollFinishFragment extends Fragment {
    public FingerprintEnrollFinishViewModel _viewModel;
    public final FingerprintEnrollFinishFragment$addButtonClickListener$1 addButtonClickListener;
    public final FingerprintEnrollFinishFragment$addButtonClickListener$1 nextButtonClickListener;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFinishFragment$addButtonClickListener$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFinishFragment$addButtonClickListener$1] */
    public FingerprintEnrollFinishFragment() {
        final int i = 0;
        this.addButtonClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFinishFragment$addButtonClickListener$1
                    public final /* synthetic */ FingerprintEnrollFinishFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                FingerprintEnrollFinishViewModel fingerprintEnrollFinishViewModel =
                                        this.this$0._viewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollFinishViewModel);
                                fingerprintEnrollFinishViewModel.mActionLiveData.postValue(0);
                                break;
                            default:
                                FingerprintEnrollFinishViewModel fingerprintEnrollFinishViewModel2 =
                                        this.this$0._viewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollFinishViewModel2);
                                fingerprintEnrollFinishViewModel2.mActionLiveData.postValue(1);
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.nextButtonClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFinishFragment$addButtonClickListener$1
                    public final /* synthetic */ FingerprintEnrollFinishFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                FingerprintEnrollFinishViewModel fingerprintEnrollFinishViewModel =
                                        this.this$0._viewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollFinishViewModel);
                                fingerprintEnrollFinishViewModel.mActionLiveData.postValue(0);
                                break;
                            default:
                                FingerprintEnrollFinishViewModel fingerprintEnrollFinishViewModel2 =
                                        this.this$0._viewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollFinishViewModel2);
                                fingerprintEnrollFinishViewModel2.mActionLiveData.postValue(1);
                                break;
                        }
                    }
                };
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
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
                JvmClassMappingKt.getKotlinClass(FingerprintEnrollFinishViewModel.class);
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        String qualifiedName = modelClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        this._viewModel =
                (FingerprintEnrollFinishViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                modelClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FingerprintEnrollFinishViewModel fingerprintEnrollFinishViewModel = this._viewModel;
        Intrinsics.checkNotNull(fingerprintEnrollFinishViewModel);
        View inflate =
                inflater.inflate(
                        fingerprintEnrollFinishViewModel.mFingerprintRepository.canAssumeSfps()
                                ? R.layout.sfps_enroll_finish
                                : R.layout.fingerprint_enroll_finish,
                        viewGroup,
                        false);
        Intrinsics.checkNotNull(
                inflate,
                "null cannot be cast to non-null type com.google.android.setupdesign.GlifLayout");
        GlifLayout glifLayout = (GlifLayout) inflate;
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        FingerprintEnrollFinishViewModel fingerprintEnrollFinishViewModel2 = this._viewModel;
        Intrinsics.checkNotNull(fingerprintEnrollFinishViewModel2);
        boolean z = fingerprintEnrollFinishViewModel2.mRequest.isSuw;
        FingerprintEnrollFinishViewModel fingerprintEnrollFinishViewModel3 = this._viewModel;
        Intrinsics.checkNotNull(fingerprintEnrollFinishViewModel3);
        boolean canAssumeSfps =
                fingerprintEnrollFinishViewModel3.mFingerprintRepository.canAssumeSfps();
        FingerprintEnrollFinishViewModel fingerprintEnrollFinishViewModel4 = this._viewModel;
        Intrinsics.checkNotNull(fingerprintEnrollFinishViewModel4);
        int i = fingerprintEnrollFinishViewModel4.mUserId;
        FingerprintRepository fingerprintRepository =
                fingerprintEnrollFinishViewModel4.mFingerprintRepository;
        int numOfEnrolledFingerprintsSize =
                fingerprintRepository.getNumOfEnrolledFingerprintsSize(i);
        FingerprintSensorPropertiesInternal firstFingerprintSensorPropertiesInternal =
                fingerprintRepository.getFirstFingerprintSensorPropertiesInternal();
        boolean z2 =
                numOfEnrolledFingerprintsSize
                        < (firstFingerprintSensorPropertiesInternal != null
                                ? firstFingerprintSensorPropertiesInternal.maxEnrollmentsPerUser
                                : 0);
        FingerprintEnrollFinishFragment$addButtonClickListener$1 nextButtonClickListener =
                this.nextButtonClickListener;
        FingerprintEnrollFinishFragment$addButtonClickListener$1 addButtonClickListener =
                this.addButtonClickListener;
        Intrinsics.checkNotNullParameter(nextButtonClickListener, "nextButtonClickListener");
        Intrinsics.checkNotNullParameter(addButtonClickListener, "addButtonClickListener");
        TextView headerTextView = glifLayout.getHeaderTextView();
        CharSequence text = headerTextView.getText();
        CharSequence text2 =
                requireActivity.getText(R.string.security_settings_fingerprint_enroll_finish_title);
        Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
        if (text != text2) {
            if (!TextUtils.isEmpty(text)) {
                headerTextView.setAccessibilityLiveRegion(1);
            }
            glifLayout.setHeaderText(text2);
            glifLayout.getHeaderTextView().setContentDescription(text2);
            requireActivity.setTitle(text2);
        }
        String string =
                requireActivity.getString(
                        (canAssumeSfps && z2)
                                ? R.string
                                        .security_settings_fingerprint_enroll_finish_v2_add_fingerprint_message
                                : R.string.security_settings_fingerprint_enroll_finish_v2_message);
        if (!TextUtils.equals(glifLayout.getDescriptionText(), string)) {
            glifLayout.setDescriptionText(string);
        }
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        footerBarMixin.setPrimaryButton(
                new FooterButton(
                        requireActivity.getString(
                                z
                                        ? R.string.next_label
                                        : R.string.security_settings_fingerprint_enroll_done),
                        nextButtonClickListener,
                        5,
                        2132083805));
        if (z2) {
            footerBarMixin.setSecondaryButton(
                    new FooterButton(
                            requireActivity.getString(R.string.fingerprint_enroll_button_add),
                            addButtonClickListener,
                            7,
                            2132083806),
                    false);
        }
        return glifLayout;
    }
}
