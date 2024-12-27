package com.android.settings.biometrics2.ui.view;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.Window;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.lifecycle.LifecycleCoroutineScopeImpl;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollFinish;
import com.android.settings.biometrics2.data.repository.FingerprintRepository;
import com.android.settings.biometrics2.factory.BiometricsRepositoryProviderImpl;
import com.android.settings.biometrics2.factory.BiometricsViewModelFactory;
import com.android.settings.biometrics2.ui.model.CredentialModel;
import com.android.settings.biometrics2.ui.model.EnrollmentRequest;
import com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel;
import com.android.settings.biometrics2.ui.viewmodel.CredentialAction;
import com.android.settings.biometrics2.ui.viewmodel.DeviceFoldedViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollEnrollingViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollErrorDialogViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollFindSensorViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollFinishViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollIntroViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollProgressViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollmentViewModel;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.password.SetupChooseLockGeneric;
import com.android.settingslib.Utils;

import com.google.android.setupdesign.util.ThemeHelper;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;

import java.time.Clock;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\b\u0017\u0018\u00002\u00020\u0001:\u0002\u0004\u0005B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0006"
        },
        d2 = {
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollmentActivity;",
            "Landroidx/fragment/app/FragmentActivity;",
            "<init>",
            "()V",
            "InternalActivity",
            "SetupActivity",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public class FingerprintEnrollmentActivity extends FragmentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final FingerprintEnrollmentActivity$chooseLockResultCallback$1 chooseLockResultCallback;
    public final FingerprintEnrollmentActivity$finishActionObserver$1 enrollingActionObserver;
    public final FingerprintEnrollmentActivity$finishActionObserver$1 findSensorActionObserver;
    public final FingerprintEnrollmentActivity$finishActionObserver$1 finishActionObserver;
    public boolean isFirstFragmentAdded;
    public final Lazy viewModelProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$viewModelProvider$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new ViewModelProvider(FingerprintEnrollmentActivity.this);
                        }
                    });
    public final Lazy viewModel$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$viewModel$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            FingerprintEnrollmentActivity fingerprintEnrollmentActivity =
                                    FingerprintEnrollmentActivity.this;
                            int i = FingerprintEnrollmentActivity.$r8$clinit;
                            return (FingerprintEnrollmentViewModel)
                                    fingerprintEnrollmentActivity
                                            .getViewModelProvider()
                                            .get(FingerprintEnrollmentViewModel.class);
                        }
                    });
    public final Lazy autoCredentialViewModel$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$autoCredentialViewModel$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            FingerprintEnrollmentActivity fingerprintEnrollmentActivity =
                                    FingerprintEnrollmentActivity.this;
                            int i = FingerprintEnrollmentActivity.$r8$clinit;
                            return (AutoCredentialViewModel)
                                    fingerprintEnrollmentActivity
                                            .getViewModelProvider()
                                            .get(AutoCredentialViewModel.class);
                        }
                    });
    public final Lazy introViewModel$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$introViewModel$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            FingerprintEnrollmentActivity fingerprintEnrollmentActivity =
                                    FingerprintEnrollmentActivity.this;
                            int i = FingerprintEnrollmentActivity.$r8$clinit;
                            return (FingerprintEnrollIntroViewModel)
                                    fingerprintEnrollmentActivity
                                            .getViewModelProvider()
                                            .get(FingerprintEnrollIntroViewModel.class);
                        }
                    });
    public final Lazy findSensorViewModel$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$findSensorViewModel$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            FingerprintEnrollmentActivity fingerprintEnrollmentActivity =
                                    FingerprintEnrollmentActivity.this;
                            int i = FingerprintEnrollmentActivity.$r8$clinit;
                            return (FingerprintEnrollFindSensorViewModel)
                                    fingerprintEnrollmentActivity
                                            .getViewModelProvider()
                                            .get(FingerprintEnrollFindSensorViewModel.class);
                        }
                    });
    public final Lazy progressViewModel$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$progressViewModel$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            FingerprintEnrollmentActivity fingerprintEnrollmentActivity =
                                    FingerprintEnrollmentActivity.this;
                            int i = FingerprintEnrollmentActivity.$r8$clinit;
                            return (FingerprintEnrollProgressViewModel)
                                    fingerprintEnrollmentActivity
                                            .getViewModelProvider()
                                            .get(FingerprintEnrollProgressViewModel.class);
                        }
                    });
    public final Lazy enrollingViewModel$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$enrollingViewModel$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            FingerprintEnrollmentActivity fingerprintEnrollmentActivity =
                                    FingerprintEnrollmentActivity.this;
                            int i = FingerprintEnrollmentActivity.$r8$clinit;
                            return (FingerprintEnrollEnrollingViewModel)
                                    fingerprintEnrollmentActivity
                                            .getViewModelProvider()
                                            .get(FingerprintEnrollEnrollingViewModel.class);
                        }
                    });
    public final Lazy finishViewModel$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$finishViewModel$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            FingerprintEnrollmentActivity fingerprintEnrollmentActivity =
                                    FingerprintEnrollmentActivity.this;
                            int i = FingerprintEnrollmentActivity.$r8$clinit;
                            return (FingerprintEnrollFinishViewModel)
                                    fingerprintEnrollmentActivity
                                            .getViewModelProvider()
                                            .get(FingerprintEnrollFinishViewModel.class);
                        }
                    });
    public final Lazy errorDialogViewModel$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$errorDialogViewModel$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            FingerprintEnrollmentActivity fingerprintEnrollmentActivity =
                                    FingerprintEnrollmentActivity.this;
                            int i = FingerprintEnrollmentActivity.$r8$clinit;
                            return (FingerprintEnrollErrorDialogViewModel)
                                    fingerprintEnrollmentActivity
                                            .getViewModelProvider()
                                            .get(FingerprintEnrollErrorDialogViewModel.class);
                        }
                    });
    public final ActivityResultRegistry.AnonymousClass2 chooseLockLauncher =
            (ActivityResultRegistry.AnonymousClass2)
                    registerForActivityResult(
                            new ActivityResultContracts$StartActivityForResult(0),
                            new ActivityResultCallback() { // from class:
                                                           // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$chooseLockResultCallback$1
                                @Override // androidx.activity.result.ActivityResultCallback
                                public final void onActivityResult(Object obj) {
                                    ActivityResult result = (ActivityResult) obj;
                                    Intrinsics.checkNotNullParameter(result, "result");
                                    int i = FingerprintEnrollmentActivity.$r8$clinit;
                                    FingerprintEnrollmentActivity.this.onChooseOrConfirmLockResult(
                                            true, result);
                                }
                            });

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\f\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"
            },
            d2 = {
                "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollmentActivity$InternalActivity;",
                "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollmentActivity;",
                "()V",
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0},
            xi = 48)
    public static final class InternalActivity extends FingerprintEnrollmentActivity {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\f\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"
            },
            d2 = {
                "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollmentActivity$SetupActivity;",
                "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollmentActivity;",
                "()V",
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0},
            xi = 48)
    public static final class SetupActivity extends FingerprintEnrollmentActivity {}

    /* JADX WARN: Type inference failed for: r0v18, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$finishActionObserver$1] */
    /* JADX WARN: Type inference failed for: r0v19, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$finishActionObserver$1] */
    /* JADX WARN: Type inference failed for: r0v20, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$finishActionObserver$1] */
    public FingerprintEnrollmentActivity() {
        final int i = 2;
        this.findSensorActionObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$finishActionObserver$1
                    public final /* synthetic */ FingerprintEnrollmentActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        Intent intent = null;
                        FingerprintEnrollmentActivity fingerprintEnrollmentActivity = this.this$0;
                        switch (i) {
                            case 0:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i2 = FingerprintEnrollmentActivity.$r8$clinit;
                                    if (intValue == 0) {
                                        fingerprintEnrollmentActivity.startEnrollingFragment();
                                        break;
                                    } else if (intValue == 1) {
                                        if (fingerprintEnrollmentActivity.getViewModel()
                                                .request
                                                .isSuw) {
                                            intent = new Intent();
                                            FingerprintEnrollmentViewModel viewModel =
                                                    fingerprintEnrollmentActivity.getViewModel();
                                            int i3 =
                                                    fingerprintEnrollmentActivity
                                                            .getAutoCredentialViewModel()
                                                            .credentialModel
                                                            .userId;
                                            viewModel.getClass();
                                            Bundle bundle = new Bundle();
                                            bundle.putInt(
                                                    "fingerprint_enrolled_count",
                                                    viewModel.fingerprintRepository
                                                            .getNumOfEnrolledFingerprintsSize(i3));
                                            intent.putExtras(bundle);
                                        }
                                        fingerprintEnrollmentActivity.onSetActivityResult(
                                                new ActivityResult(1, intent));
                                        break;
                                    } else {
                                        fingerprintEnrollmentActivity.getClass();
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                Integer num2 = (Integer) obj;
                                if (num2 != null) {
                                    int intValue2 = num2.intValue();
                                    int i4 = FingerprintEnrollmentActivity.$r8$clinit;
                                    fingerprintEnrollmentActivity.getClass();
                                    if (intValue2 == 0) {
                                        FingerprintEnrollmentViewModel viewModel2 =
                                                fingerprintEnrollmentActivity.getViewModel();
                                        if (!viewModel2.isNewFingerprintAdded) {
                                            viewModel2.isNewFingerprintAdded = true;
                                        }
                                        FingerprintEnrollFinishViewModel
                                                fingerprintEnrollFinishViewModel =
                                                        (FingerprintEnrollFinishViewModel)
                                                                fingerprintEnrollmentActivity
                                                                        .finishViewModel$delegate
                                                                        .getValue();
                                        fingerprintEnrollFinishViewModel.mActionLiveData.setValue(
                                                null);
                                        fingerprintEnrollFinishViewModel.mActionLiveData.observe(
                                                fingerprintEnrollmentActivity,
                                                fingerprintEnrollmentActivity.finishActionObserver);
                                        if (!fingerprintEnrollmentActivity.getViewModel()
                                                .request
                                                .isSkipFindSensor) {
                                            fingerprintEnrollmentActivity
                                                    .getSupportFragmentManager()
                                                    .popBackStack();
                                            if (fingerprintEnrollmentActivity
                                                            .getSupportFragmentManager()
                                                            .findFragmentByTag("finish")
                                                    != null) {
                                                FragmentManagerImpl supportFragmentManager =
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager();
                                                supportFragmentManager.getClass();
                                                supportFragmentManager.enqueueAction(
                                                        new FragmentManager.PopBackStackState(
                                                                "finish", -1, 1),
                                                        false);
                                            }
                                            FingerprintEnrollmentViewModel viewModel3 =
                                                    fingerprintEnrollmentActivity.getViewModel();
                                            int i5 =
                                                    fingerprintEnrollmentActivity
                                                            .getAutoCredentialViewModel()
                                                            .credentialModel
                                                            .userId;
                                            FingerprintRepository fingerprintRepository =
                                                    viewModel3.fingerprintRepository;
                                            FingerprintSensorPropertiesInternal
                                                    firstFingerprintSensorPropertiesInternal =
                                                            fingerprintRepository
                                                                    .getFirstFingerprintSensorPropertiesInternal();
                                            if ((firstFingerprintSensorPropertiesInternal != null
                                                                    ? firstFingerprintSensorPropertiesInternal
                                                                            .maxEnrollmentsPerUser
                                                                    : 0)
                                                            <= fingerprintRepository
                                                                    .getNumOfEnrolledFingerprintsSize(
                                                                            i5)
                                                    && fingerprintEnrollmentActivity
                                                                    .getSupportFragmentManager()
                                                                    .findFragmentByTag(
                                                                            "find-sensor")
                                                            != null) {
                                                FragmentManagerImpl supportFragmentManager2 =
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager();
                                                supportFragmentManager2.getClass();
                                                supportFragmentManager2.enqueueAction(
                                                        new FragmentManager.PopBackStackState(
                                                                "find-sensor", -1, 1),
                                                        false);
                                            }
                                            FragmentManagerImpl supportFragmentManager3 =
                                                    fingerprintEnrollmentActivity
                                                            .getSupportFragmentManager();
                                            BackStackRecord m =
                                                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0
                                                            .m(
                                                                    supportFragmentManager3,
                                                                    supportFragmentManager3);
                                            m.mReorderingAllowed = true;
                                            m.mEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_open_enter_dynamic_color;
                                            m.mExitAnim = R.anim.shared_x_axis_activity_open_exit;
                                            m.mPopEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_close_enter_dynamic_color;
                                            m.mPopExitAnim =
                                                    R.anim.shared_x_axis_activity_close_exit;
                                            m.replace(
                                                    R.id.fragment_container_view,
                                                    FingerprintEnrollFinishFragment.class,
                                                    null,
                                                    "finish");
                                            m.addToBackStack("finish");
                                            m.commitInternal(false);
                                            break;
                                        } else {
                                            FragmentManagerImpl supportFragmentManager4 =
                                                    fingerprintEnrollmentActivity
                                                            .getSupportFragmentManager();
                                            BackStackRecord m2 =
                                                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0
                                                            .m(
                                                                    supportFragmentManager4,
                                                                    supportFragmentManager4);
                                            m2.mReorderingAllowed = true;
                                            m2.mEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_open_enter_dynamic_color;
                                            m2.mExitAnim = R.anim.shared_x_axis_activity_open_exit;
                                            m2.mPopEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_close_enter_dynamic_color;
                                            m2.mPopExitAnim =
                                                    R.anim.shared_x_axis_activity_close_exit;
                                            m2.replace(
                                                    R.id.fragment_container_view,
                                                    FingerprintEnrollFinishFragment.class,
                                                    null,
                                                    "finish");
                                            m2.commitInternal(false);
                                            break;
                                        }
                                    } else if (intValue2 == 1) {
                                        new FingerprintEnrollEnrollingIconTouchDialog()
                                                .show(
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager(),
                                                        "skip-setup-dialog");
                                        break;
                                    } else if (intValue2 == 2) {
                                        fingerprintEnrollmentActivity.onSetActivityResult(
                                                new ActivityResult(2, null));
                                        break;
                                    } else if (intValue2 == 3) {
                                        if (fingerprintEnrollmentActivity
                                                        .getSupportFragmentManager()
                                                        .getBackStackEntryCount()
                                                <= 0) {
                                            fingerprintEnrollmentActivity.onSetActivityResult(
                                                    new ActivityResult(0, null));
                                            break;
                                        } else {
                                            fingerprintEnrollmentActivity
                                                    .getSupportFragmentManager()
                                                    .popBackStack();
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                Integer num3 = (Integer) obj;
                                if (num3 != null) {
                                    int intValue3 = num3.intValue();
                                    int i6 = FingerprintEnrollmentActivity.$r8$clinit;
                                    fingerprintEnrollmentActivity.getClass();
                                    if (intValue3 == 0) {
                                        fingerprintEnrollmentActivity.onSetActivityResult(
                                                new ActivityResult(2, null));
                                        break;
                                    } else if (intValue3 == 1) {
                                        new SkipSetupFindFpsDialog()
                                                .show(
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager(),
                                                        "skip-setup-dialog");
                                        break;
                                    } else if (intValue3 == 2) {
                                        fingerprintEnrollmentActivity.startEnrollingFragment();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.enrollingActionObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$finishActionObserver$1
                    public final /* synthetic */ FingerprintEnrollmentActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        Intent intent = null;
                        FingerprintEnrollmentActivity fingerprintEnrollmentActivity = this.this$0;
                        switch (i2) {
                            case 0:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i22 = FingerprintEnrollmentActivity.$r8$clinit;
                                    if (intValue == 0) {
                                        fingerprintEnrollmentActivity.startEnrollingFragment();
                                        break;
                                    } else if (intValue == 1) {
                                        if (fingerprintEnrollmentActivity.getViewModel()
                                                .request
                                                .isSuw) {
                                            intent = new Intent();
                                            FingerprintEnrollmentViewModel viewModel =
                                                    fingerprintEnrollmentActivity.getViewModel();
                                            int i3 =
                                                    fingerprintEnrollmentActivity
                                                            .getAutoCredentialViewModel()
                                                            .credentialModel
                                                            .userId;
                                            viewModel.getClass();
                                            Bundle bundle = new Bundle();
                                            bundle.putInt(
                                                    "fingerprint_enrolled_count",
                                                    viewModel.fingerprintRepository
                                                            .getNumOfEnrolledFingerprintsSize(i3));
                                            intent.putExtras(bundle);
                                        }
                                        fingerprintEnrollmentActivity.onSetActivityResult(
                                                new ActivityResult(1, intent));
                                        break;
                                    } else {
                                        fingerprintEnrollmentActivity.getClass();
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                Integer num2 = (Integer) obj;
                                if (num2 != null) {
                                    int intValue2 = num2.intValue();
                                    int i4 = FingerprintEnrollmentActivity.$r8$clinit;
                                    fingerprintEnrollmentActivity.getClass();
                                    if (intValue2 == 0) {
                                        FingerprintEnrollmentViewModel viewModel2 =
                                                fingerprintEnrollmentActivity.getViewModel();
                                        if (!viewModel2.isNewFingerprintAdded) {
                                            viewModel2.isNewFingerprintAdded = true;
                                        }
                                        FingerprintEnrollFinishViewModel
                                                fingerprintEnrollFinishViewModel =
                                                        (FingerprintEnrollFinishViewModel)
                                                                fingerprintEnrollmentActivity
                                                                        .finishViewModel$delegate
                                                                        .getValue();
                                        fingerprintEnrollFinishViewModel.mActionLiveData.setValue(
                                                null);
                                        fingerprintEnrollFinishViewModel.mActionLiveData.observe(
                                                fingerprintEnrollmentActivity,
                                                fingerprintEnrollmentActivity.finishActionObserver);
                                        if (!fingerprintEnrollmentActivity.getViewModel()
                                                .request
                                                .isSkipFindSensor) {
                                            fingerprintEnrollmentActivity
                                                    .getSupportFragmentManager()
                                                    .popBackStack();
                                            if (fingerprintEnrollmentActivity
                                                            .getSupportFragmentManager()
                                                            .findFragmentByTag("finish")
                                                    != null) {
                                                FragmentManagerImpl supportFragmentManager =
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager();
                                                supportFragmentManager.getClass();
                                                supportFragmentManager.enqueueAction(
                                                        new FragmentManager.PopBackStackState(
                                                                "finish", -1, 1),
                                                        false);
                                            }
                                            FingerprintEnrollmentViewModel viewModel3 =
                                                    fingerprintEnrollmentActivity.getViewModel();
                                            int i5 =
                                                    fingerprintEnrollmentActivity
                                                            .getAutoCredentialViewModel()
                                                            .credentialModel
                                                            .userId;
                                            FingerprintRepository fingerprintRepository =
                                                    viewModel3.fingerprintRepository;
                                            FingerprintSensorPropertiesInternal
                                                    firstFingerprintSensorPropertiesInternal =
                                                            fingerprintRepository
                                                                    .getFirstFingerprintSensorPropertiesInternal();
                                            if ((firstFingerprintSensorPropertiesInternal != null
                                                                    ? firstFingerprintSensorPropertiesInternal
                                                                            .maxEnrollmentsPerUser
                                                                    : 0)
                                                            <= fingerprintRepository
                                                                    .getNumOfEnrolledFingerprintsSize(
                                                                            i5)
                                                    && fingerprintEnrollmentActivity
                                                                    .getSupportFragmentManager()
                                                                    .findFragmentByTag(
                                                                            "find-sensor")
                                                            != null) {
                                                FragmentManagerImpl supportFragmentManager2 =
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager();
                                                supportFragmentManager2.getClass();
                                                supportFragmentManager2.enqueueAction(
                                                        new FragmentManager.PopBackStackState(
                                                                "find-sensor", -1, 1),
                                                        false);
                                            }
                                            FragmentManagerImpl supportFragmentManager3 =
                                                    fingerprintEnrollmentActivity
                                                            .getSupportFragmentManager();
                                            BackStackRecord m =
                                                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0
                                                            .m(
                                                                    supportFragmentManager3,
                                                                    supportFragmentManager3);
                                            m.mReorderingAllowed = true;
                                            m.mEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_open_enter_dynamic_color;
                                            m.mExitAnim = R.anim.shared_x_axis_activity_open_exit;
                                            m.mPopEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_close_enter_dynamic_color;
                                            m.mPopExitAnim =
                                                    R.anim.shared_x_axis_activity_close_exit;
                                            m.replace(
                                                    R.id.fragment_container_view,
                                                    FingerprintEnrollFinishFragment.class,
                                                    null,
                                                    "finish");
                                            m.addToBackStack("finish");
                                            m.commitInternal(false);
                                            break;
                                        } else {
                                            FragmentManagerImpl supportFragmentManager4 =
                                                    fingerprintEnrollmentActivity
                                                            .getSupportFragmentManager();
                                            BackStackRecord m2 =
                                                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0
                                                            .m(
                                                                    supportFragmentManager4,
                                                                    supportFragmentManager4);
                                            m2.mReorderingAllowed = true;
                                            m2.mEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_open_enter_dynamic_color;
                                            m2.mExitAnim = R.anim.shared_x_axis_activity_open_exit;
                                            m2.mPopEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_close_enter_dynamic_color;
                                            m2.mPopExitAnim =
                                                    R.anim.shared_x_axis_activity_close_exit;
                                            m2.replace(
                                                    R.id.fragment_container_view,
                                                    FingerprintEnrollFinishFragment.class,
                                                    null,
                                                    "finish");
                                            m2.commitInternal(false);
                                            break;
                                        }
                                    } else if (intValue2 == 1) {
                                        new FingerprintEnrollEnrollingIconTouchDialog()
                                                .show(
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager(),
                                                        "skip-setup-dialog");
                                        break;
                                    } else if (intValue2 == 2) {
                                        fingerprintEnrollmentActivity.onSetActivityResult(
                                                new ActivityResult(2, null));
                                        break;
                                    } else if (intValue2 == 3) {
                                        if (fingerprintEnrollmentActivity
                                                        .getSupportFragmentManager()
                                                        .getBackStackEntryCount()
                                                <= 0) {
                                            fingerprintEnrollmentActivity.onSetActivityResult(
                                                    new ActivityResult(0, null));
                                            break;
                                        } else {
                                            fingerprintEnrollmentActivity
                                                    .getSupportFragmentManager()
                                                    .popBackStack();
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                Integer num3 = (Integer) obj;
                                if (num3 != null) {
                                    int intValue3 = num3.intValue();
                                    int i6 = FingerprintEnrollmentActivity.$r8$clinit;
                                    fingerprintEnrollmentActivity.getClass();
                                    if (intValue3 == 0) {
                                        fingerprintEnrollmentActivity.onSetActivityResult(
                                                new ActivityResult(2, null));
                                        break;
                                    } else if (intValue3 == 1) {
                                        new SkipSetupFindFpsDialog()
                                                .show(
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager(),
                                                        "skip-setup-dialog");
                                        break;
                                    } else if (intValue3 == 2) {
                                        fingerprintEnrollmentActivity.startEnrollingFragment();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i3 = 0;
        this.finishActionObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$finishActionObserver$1
                    public final /* synthetic */ FingerprintEnrollmentActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        Intent intent = null;
                        FingerprintEnrollmentActivity fingerprintEnrollmentActivity = this.this$0;
                        switch (i3) {
                            case 0:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i22 = FingerprintEnrollmentActivity.$r8$clinit;
                                    if (intValue == 0) {
                                        fingerprintEnrollmentActivity.startEnrollingFragment();
                                        break;
                                    } else if (intValue == 1) {
                                        if (fingerprintEnrollmentActivity.getViewModel()
                                                .request
                                                .isSuw) {
                                            intent = new Intent();
                                            FingerprintEnrollmentViewModel viewModel =
                                                    fingerprintEnrollmentActivity.getViewModel();
                                            int i32 =
                                                    fingerprintEnrollmentActivity
                                                            .getAutoCredentialViewModel()
                                                            .credentialModel
                                                            .userId;
                                            viewModel.getClass();
                                            Bundle bundle = new Bundle();
                                            bundle.putInt(
                                                    "fingerprint_enrolled_count",
                                                    viewModel.fingerprintRepository
                                                            .getNumOfEnrolledFingerprintsSize(i32));
                                            intent.putExtras(bundle);
                                        }
                                        fingerprintEnrollmentActivity.onSetActivityResult(
                                                new ActivityResult(1, intent));
                                        break;
                                    } else {
                                        fingerprintEnrollmentActivity.getClass();
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                Integer num2 = (Integer) obj;
                                if (num2 != null) {
                                    int intValue2 = num2.intValue();
                                    int i4 = FingerprintEnrollmentActivity.$r8$clinit;
                                    fingerprintEnrollmentActivity.getClass();
                                    if (intValue2 == 0) {
                                        FingerprintEnrollmentViewModel viewModel2 =
                                                fingerprintEnrollmentActivity.getViewModel();
                                        if (!viewModel2.isNewFingerprintAdded) {
                                            viewModel2.isNewFingerprintAdded = true;
                                        }
                                        FingerprintEnrollFinishViewModel
                                                fingerprintEnrollFinishViewModel =
                                                        (FingerprintEnrollFinishViewModel)
                                                                fingerprintEnrollmentActivity
                                                                        .finishViewModel$delegate
                                                                        .getValue();
                                        fingerprintEnrollFinishViewModel.mActionLiveData.setValue(
                                                null);
                                        fingerprintEnrollFinishViewModel.mActionLiveData.observe(
                                                fingerprintEnrollmentActivity,
                                                fingerprintEnrollmentActivity.finishActionObserver);
                                        if (!fingerprintEnrollmentActivity.getViewModel()
                                                .request
                                                .isSkipFindSensor) {
                                            fingerprintEnrollmentActivity
                                                    .getSupportFragmentManager()
                                                    .popBackStack();
                                            if (fingerprintEnrollmentActivity
                                                            .getSupportFragmentManager()
                                                            .findFragmentByTag("finish")
                                                    != null) {
                                                FragmentManagerImpl supportFragmentManager =
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager();
                                                supportFragmentManager.getClass();
                                                supportFragmentManager.enqueueAction(
                                                        new FragmentManager.PopBackStackState(
                                                                "finish", -1, 1),
                                                        false);
                                            }
                                            FingerprintEnrollmentViewModel viewModel3 =
                                                    fingerprintEnrollmentActivity.getViewModel();
                                            int i5 =
                                                    fingerprintEnrollmentActivity
                                                            .getAutoCredentialViewModel()
                                                            .credentialModel
                                                            .userId;
                                            FingerprintRepository fingerprintRepository =
                                                    viewModel3.fingerprintRepository;
                                            FingerprintSensorPropertiesInternal
                                                    firstFingerprintSensorPropertiesInternal =
                                                            fingerprintRepository
                                                                    .getFirstFingerprintSensorPropertiesInternal();
                                            if ((firstFingerprintSensorPropertiesInternal != null
                                                                    ? firstFingerprintSensorPropertiesInternal
                                                                            .maxEnrollmentsPerUser
                                                                    : 0)
                                                            <= fingerprintRepository
                                                                    .getNumOfEnrolledFingerprintsSize(
                                                                            i5)
                                                    && fingerprintEnrollmentActivity
                                                                    .getSupportFragmentManager()
                                                                    .findFragmentByTag(
                                                                            "find-sensor")
                                                            != null) {
                                                FragmentManagerImpl supportFragmentManager2 =
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager();
                                                supportFragmentManager2.getClass();
                                                supportFragmentManager2.enqueueAction(
                                                        new FragmentManager.PopBackStackState(
                                                                "find-sensor", -1, 1),
                                                        false);
                                            }
                                            FragmentManagerImpl supportFragmentManager3 =
                                                    fingerprintEnrollmentActivity
                                                            .getSupportFragmentManager();
                                            BackStackRecord m =
                                                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0
                                                            .m(
                                                                    supportFragmentManager3,
                                                                    supportFragmentManager3);
                                            m.mReorderingAllowed = true;
                                            m.mEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_open_enter_dynamic_color;
                                            m.mExitAnim = R.anim.shared_x_axis_activity_open_exit;
                                            m.mPopEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_close_enter_dynamic_color;
                                            m.mPopExitAnim =
                                                    R.anim.shared_x_axis_activity_close_exit;
                                            m.replace(
                                                    R.id.fragment_container_view,
                                                    FingerprintEnrollFinishFragment.class,
                                                    null,
                                                    "finish");
                                            m.addToBackStack("finish");
                                            m.commitInternal(false);
                                            break;
                                        } else {
                                            FragmentManagerImpl supportFragmentManager4 =
                                                    fingerprintEnrollmentActivity
                                                            .getSupportFragmentManager();
                                            BackStackRecord m2 =
                                                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0
                                                            .m(
                                                                    supportFragmentManager4,
                                                                    supportFragmentManager4);
                                            m2.mReorderingAllowed = true;
                                            m2.mEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_open_enter_dynamic_color;
                                            m2.mExitAnim = R.anim.shared_x_axis_activity_open_exit;
                                            m2.mPopEnterAnim =
                                                    R.anim
                                                            .shared_x_axis_activity_close_enter_dynamic_color;
                                            m2.mPopExitAnim =
                                                    R.anim.shared_x_axis_activity_close_exit;
                                            m2.replace(
                                                    R.id.fragment_container_view,
                                                    FingerprintEnrollFinishFragment.class,
                                                    null,
                                                    "finish");
                                            m2.commitInternal(false);
                                            break;
                                        }
                                    } else if (intValue2 == 1) {
                                        new FingerprintEnrollEnrollingIconTouchDialog()
                                                .show(
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager(),
                                                        "skip-setup-dialog");
                                        break;
                                    } else if (intValue2 == 2) {
                                        fingerprintEnrollmentActivity.onSetActivityResult(
                                                new ActivityResult(2, null));
                                        break;
                                    } else if (intValue2 == 3) {
                                        if (fingerprintEnrollmentActivity
                                                        .getSupportFragmentManager()
                                                        .getBackStackEntryCount()
                                                <= 0) {
                                            fingerprintEnrollmentActivity.onSetActivityResult(
                                                    new ActivityResult(0, null));
                                            break;
                                        } else {
                                            fingerprintEnrollmentActivity
                                                    .getSupportFragmentManager()
                                                    .popBackStack();
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                Integer num3 = (Integer) obj;
                                if (num3 != null) {
                                    int intValue3 = num3.intValue();
                                    int i6 = FingerprintEnrollmentActivity.$r8$clinit;
                                    fingerprintEnrollmentActivity.getClass();
                                    if (intValue3 == 0) {
                                        fingerprintEnrollmentActivity.onSetActivityResult(
                                                new ActivityResult(2, null));
                                        break;
                                    } else if (intValue3 == 1) {
                                        new SkipSetupFindFpsDialog()
                                                .show(
                                                        fingerprintEnrollmentActivity
                                                                .getSupportFragmentManager(),
                                                        "skip-setup-dialog");
                                        break;
                                    } else if (intValue3 == 2) {
                                        fingerprintEnrollmentActivity.startEnrollingFragment();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    public final void attachFindSensorViewModel() {
        if (getViewModel().request.isSkipFindSensor) {
            return;
        }
        FingerprintEnrollFindSensorViewModel fingerprintEnrollFindSensorViewModel =
                (FingerprintEnrollFindSensorViewModel) this.findSensorViewModel$delegate.getValue();
        fingerprintEnrollFindSensorViewModel.mActionLiveData.setValue(null);
        fingerprintEnrollFindSensorViewModel.mActionLiveData.observe(
                this, this.findSensorActionObserver);
    }

    public final void attachIntroViewModel() {
        EnrollmentRequest enrollmentRequest = getViewModel().request;
        if (enrollmentRequest.isSkipIntro || enrollmentRequest.isSkipFindSensor) {
            return;
        }
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new FingerprintEnrollmentActivity$attachIntroViewModel$1(this, null),
                3);
    }

    public final AutoCredentialViewModel getAutoCredentialViewModel() {
        return (AutoCredentialViewModel) this.autoCredentialViewModel$delegate.getValue();
    }

    @Override // androidx.activity.ComponentActivity,
              // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final CreationExtras getDefaultViewModelCreationExtras() {
        MutableCreationExtras mutableCreationExtras =
                new MutableCreationExtras(super.getDefaultViewModelCreationExtras());
        BiometricsViewModelFactory.AnonymousClass1 anonymousClass1 =
                BiometricsViewModelFactory.CHALLENGE_GENERATOR_KEY;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        BiometricsRepositoryProviderImpl biometricsRepositoryProviderImpl =
                (BiometricsRepositoryProviderImpl)
                        featureFactoryImpl.biometricsRepositoryProvider$delegate.getValue();
        Application application = getApplication();
        biometricsRepositoryProviderImpl.getClass();
        FingerprintRepository fingerprintRepository =
                BiometricsRepositoryProviderImpl.getFingerprintRepository(application);
        Intrinsics.checkNotNull(fingerprintRepository);
        mutableCreationExtras.map.put(
                anonymousClass1,
                new AutoCredentialViewModel.FingerprintChallengeGenerator(fingerprintRepository));
        BiometricsViewModelFactory.AnonymousClass1 anonymousClass12 =
                BiometricsViewModelFactory.ENROLLMENT_REQUEST_KEY;
        Intent intent = getIntent();
        Intrinsics.checkNotNullExpressionValue(intent, "getIntent(...)");
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        mutableCreationExtras.map.put(
                anonymousClass12,
                new EnrollmentRequest(applicationContext, intent, this instanceof SetupActivity));
        BiometricsViewModelFactory.AnonymousClass1 anonymousClass13 =
                BiometricsViewModelFactory.CREDENTIAL_MODEL_KEY;
        Bundle extras = getIntent().getExtras();
        Clock elapsedRealtimeClock = SystemClock.elapsedRealtimeClock();
        Intrinsics.checkNotNullExpressionValue(elapsedRealtimeClock, "elapsedRealtimeClock(...)");
        mutableCreationExtras.map.put(
                anonymousClass13, new CredentialModel(extras, elapsedRealtimeClock));
        return mutableCreationExtras;
    }

    @Override // androidx.activity.ComponentActivity,
              // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return new BiometricsViewModelFactory();
    }

    public final FingerprintEnrollmentViewModel getViewModel() {
        return (FingerprintEnrollmentViewModel) this.viewModel$delegate.getValue();
    }

    public final ViewModelProvider getViewModelProvider() {
        return (ViewModelProvider) this.viewModelProvider$delegate.getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            onChooseOrConfirmLockResult(false, new ActivityResult(i2, intent));
        } else {
            super.onActivityResult(i, i2, intent);
        }
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        theme.applyStyle(R.style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, i, z);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        ColorStateList colorAttr = Utils.getColorAttr(this, android.R.attr.windowBackground);
        window.setStatusBarColor(colorAttr != null ? colorAttr.getDefaultColor() : 0);
    }

    public final void onChooseOrConfirmLockResult(boolean z, ActivityResult result) {
        Intent intent;
        if (!getViewModel().isWaitingActivityResult.compareAndSet(true, false)) {
            Log.w(
                    "FingerprintEnrollmentActivity",
                    "isChooseLock:" + z + ", fail to unset waiting flag");
        }
        AutoCredentialViewModel autoCredentialViewModel = getAutoCredentialViewModel();
        LifecycleCoroutineScopeImpl lifecycleScope = LifecycleOwnerKt.getLifecycleScope(this);
        autoCredentialViewModel.getClass();
        Intrinsics.checkNotNullParameter(result, "result");
        if ((!(z && result.mResultCode == 1) && (z || result.mResultCode != -1))
                || (intent = result.mData) == null) {
            onSetActivityResult(result);
        } else {
            autoCredentialViewModel.generateChallenge(
                    intent.getLongExtra("gk_pw_handle", 0L), true, lifecycleScope);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        ((DeviceFoldedViewModel) getViewModelProvider().get(DeviceFoldedViewModel.class))
                .mScreenSizeFoldProvider.onConfigurationChange(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        CredentialAction credentialAction;
        Intent intent;
        super.onCreate(bundle);
        setTheme(getViewModel().request.theme);
        ThemeHelper.trySetDynamicColor(this);
        getWindow().setStatusBarColor(0);
        setContentView(R.layout.biometric_enrollment_container);
        Fragment findFragmentById =
                getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        Log.d(
                "FingerprintEnrollmentActivity",
                "onCreate() has savedInstance:$(savedInstanceState != null), fragment:"
                        + findFragmentById);
        this.isFirstFragmentAdded = bundle != null;
        if (findFragmentById == null) {
            AutoCredentialViewModel autoCredentialViewModel = getAutoCredentialViewModel();
            LifecycleCoroutineScopeImpl lifecycleScope = LifecycleOwnerKt.getLifecycleScope(this);
            autoCredentialViewModel.getClass();
            if (autoCredentialViewModel.isValidCredential()) {
                credentialAction = CredentialAction.CREDENTIAL_VALID;
            } else {
                LockPatternUtils lockPatternUtils = autoCredentialViewModel.lockPatternUtils;
                CredentialModel credentialModel = autoCredentialViewModel.credentialModel;
                if (lockPatternUtils.getActivePasswordQuality(credentialModel.userId) == 0) {
                    credentialAction = CredentialAction.FAIL_NEED_TO_CHOOSE_LOCK;
                } else {
                    long j = credentialModel.gkPwHandle;
                    if (j != 0) {
                        credentialModel.clearGkPwHandleMillis =
                                Long.valueOf(credentialModel.clock.millis());
                        credentialModel.gkPwHandle = 0L;
                        autoCredentialViewModel.generateChallenge(j, false, lifecycleScope);
                        autoCredentialViewModel.isGeneratingChallengeDuringCheckingCredential =
                                true;
                        credentialAction = CredentialAction.IS_GENERATING_CHALLENGE;
                    } else {
                        credentialAction = CredentialAction.FAIL_NEED_TO_CONFIRM_LOCK;
                    }
                }
            }
            int ordinal = credentialAction.ordinal();
            if (ordinal == 2) {
                AutoCredentialViewModel autoCredentialViewModel2 = getAutoCredentialViewModel();
                boolean z = getViewModel().request.isSuw;
                EnrollmentRequest enrollmentRequest = getViewModel().request;
                enrollmentRequest.getClass();
                Bundle bundle2 = new Bundle(enrollmentRequest._suwExtras);
                autoCredentialViewModel2.getClass();
                if (z) {
                    intent = new Intent(this, (Class<?>) SetupChooseLockGeneric.class);
                    if (StorageManager.isFileEncrypted()) {
                        intent.putExtra("lockscreen.password_type", 131072);
                        intent.putExtra("show_options_button", true);
                    }
                    intent.putExtras(bundle2);
                } else {
                    intent = new Intent(this, (Class<?>) ChooseLockGeneric.class);
                }
                intent.putExtra("hide_insecure_options", true);
                intent.putExtra("request_gk_pw_handle", true);
                intent.putExtra("for_fingerprint", true);
                int i = autoCredentialViewModel2.credentialModel.userId;
                if (i != -10000) {
                    intent.putExtra("android.intent.extra.USER_ID", i);
                }
                if (!getViewModel().isWaitingActivityResult.compareAndSet(false, true)) {
                    Log.w(
                            "FingerprintEnrollmentActivity",
                            "chooseLock, fail to set isWaiting flag to true");
                }
                this.chooseLockLauncher.launch(intent);
            } else if (ordinal == 3) {
                AutoCredentialViewModel autoCredentialViewModel3 = getAutoCredentialViewModel();
                String string = getString(R.string.security_settings_fingerprint_preference_title);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                autoCredentialViewModel3.getClass();
                ChooseLockSettingsHelper.Builder builder =
                        new ChooseLockSettingsHelper.Builder(this);
                builder.mRequestCode = 1;
                builder.mTitle = string;
                builder.mRequestGatekeeperPasswordHandle = true;
                builder.mForegroundOnly = true;
                builder.mReturnCredentials = true;
                int i2 = autoCredentialViewModel3.credentialModel.userId;
                if (i2 != -10000) {
                    builder.mUserId = i2;
                }
                if (!builder.build().launch()) {
                    Log.e("FingerprintEnrollmentActivity", "confirmLock, launched is true");
                    finish();
                } else if (!getViewModel().isWaitingActivityResult.compareAndSet(false, true)) {
                    Log.w(
                            "FingerprintEnrollmentActivity",
                            "confirmLock, fail to set isWaiting flag to true");
                }
            }
            if (getViewModel().request.isSkipFindSensor) {
                startEnrollingFragment();
            } else if (getViewModel().request.isSkipIntro) {
                startFindSensorFragment();
            } else {
                attachIntroViewModel();
                startFragment(FingerprintEnrollIntroFragment.class, "intro");
            }
        } else {
            String tag = findFragmentById.getTag();
            if ("intro".equals(tag)) {
                attachIntroViewModel();
            } else if ("find-sensor".equals(tag)) {
                attachFindSensorViewModel();
                attachIntroViewModel();
            } else if ("enrolling".equals(tag)) {
                FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                        (FingerprintEnrollEnrollingViewModel)
                                this.enrollingViewModel$delegate.getValue();
                fingerprintEnrollEnrollingViewModel.mActionLiveData.setValue(null);
                fingerprintEnrollEnrollingViewModel.mActionLiveData.observe(
                        this, this.enrollingActionObserver);
                attachFindSensorViewModel();
                attachIntroViewModel();
            } else {
                if (!"finish".equals(tag)) {
                    Log.e("FingerprintEnrollmentActivity", "fragment tag " + tag + " not found");
                    finish();
                    return;
                }
                FingerprintEnrollFinishViewModel fingerprintEnrollFinishViewModel =
                        (FingerprintEnrollFinishViewModel) this.finishViewModel$delegate.getValue();
                fingerprintEnrollFinishViewModel.mActionLiveData.setValue(null);
                fingerprintEnrollFinishViewModel.mActionLiveData.observe(
                        this, this.finishActionObserver);
                attachFindSensorViewModel();
                attachIntroViewModel();
            }
        }
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new FingerprintEnrollmentActivity$collectFlows$1(this, null),
                3);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        FingerprintEnrollmentViewModel viewModel = getViewModel();
        int i =
                viewModel.fingerprintRepository.getNumOfEnrolledFingerprintsSize(
                                        getAutoCredentialViewModel().credentialModel.userId)
                                == 1
                        ? 1
                        : 2;
        viewModel
                .getApplication()
                .getPackageManager()
                .setComponentEnabledSetting(
                        new ComponentName(
                                viewModel.getApplication(),
                                FingerprintEnrollFinish.FINGERPRINT_SUGGESTION_ACTIVITY),
                        i,
                        1);
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder(
                        "com.android.settings.SetupFingerprintSuggestionActivity enabled state: "),
                i,
                "FingerprintEnrollmentViewModel");
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        getViewModel()
                .checkFinishActivityDuringOnPause(
                        isFinishing(),
                        isChangingConfigurations(),
                        LifecycleOwnerKt.getLifecycleScope(this));
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onSetActivityResult(androidx.activity.result.ActivityResult r6) {
        /*
            r5 = this;
            com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel r0 = r5.getAutoCredentialViewModel()
            boolean r1 = r0.isGeneratingChallengeDuringCheckingCredential
            if (r1 == 0) goto L2a
            com.android.settings.biometrics2.ui.model.CredentialModel r0 = r0.credentialModel
            byte[] r1 = r0.token
            if (r1 == 0) goto L2a
            long r1 = r0.challenge
            r3 = -1
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L2a
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            byte[] r2 = r0.token
            java.lang.String r3 = "hw_auth_token"
            r1.putByteArray(r3, r2)
            long r2 = r0.challenge
            java.lang.String r0 = "challenge"
            r1.putLong(r0, r2)
            goto L2b
        L2a:
            r1 = 0
        L2b:
            com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollmentViewModel r0 = r5.getViewModel()
            r0.getClass()
            java.lang.String r2 = "result"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r2)
            boolean r2 = r0.isNewFingerprintAdded
            r3 = 1
            if (r2 == 0) goto L3e
            r0 = r3
            goto L48
        L3e:
            com.android.settings.biometrics2.ui.model.EnrollmentRequest r0 = r0.request
            boolean r0 = r0.isAfterSuwOrSuwSuggestedAction
            if (r0 == 0) goto L46
            r0 = 0
            goto L48
        L46:
            int r0 = r6.mResultCode
        L48:
            android.content.Intent r6 = r6.mData
            if (r0 != r3) goto L58
            if (r1 == 0) goto L58
            if (r6 != 0) goto L55
            android.content.Intent r6 = new android.content.Intent
            r6.<init>()
        L55:
            r6.putExtras(r1)
        L58:
            androidx.activity.result.ActivityResult r1 = new androidx.activity.result.ActivityResult
            r5.setResult(r0, r6)
            r5.finish()
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity.onSetActivityResult(androidx.activity.result.ActivityResult):void");
    }

    public final void startEnrollingFragment() {
        ((FingerprintEnrollProgressViewModel) this.progressViewModel$delegate.getValue()).mToken =
                getAutoCredentialViewModel().credentialModel.token;
        FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                (FingerprintEnrollEnrollingViewModel) this.enrollingViewModel$delegate.getValue();
        fingerprintEnrollEnrollingViewModel.mActionLiveData.setValue(null);
        fingerprintEnrollEnrollingViewModel.mActionLiveData.observe(
                this, this.enrollingActionObserver);
        startFragment(
                getViewModel().fingerprintRepository.canAssumeUdfps()
                        ? FingerprintEnrollEnrollingUdfpsFragment.class
                        : getViewModel().fingerprintRepository.canAssumeSfps()
                                ? FingerprintEnrollEnrollingSfpsFragment.class
                                : FingerprintEnrollEnrollingRfpsFragment.class,
                "enrolling");
    }

    public final void startFindSensorFragment() {
        ((FingerprintEnrollProgressViewModel) this.progressViewModel$delegate.getValue()).mToken =
                getAutoCredentialViewModel().credentialModel.token;
        attachFindSensorViewModel();
        startFragment(
                getViewModel().fingerprintRepository.canAssumeUdfps()
                        ? FingerprintEnrollFindUdfpsFragment.class
                        : getViewModel().fingerprintRepository.canAssumeSfps()
                                ? FingerprintEnrollFindSfpsFragment.class
                                : FingerprintEnrollFindRfpsFragment.class,
                "find-sensor");
    }

    public final void startFragment(Class cls, String str) {
        if (!this.isFirstFragmentAdded) {
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            BackStackRecord m =
                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                            supportFragmentManager, supportFragmentManager);
            m.mReorderingAllowed = true;
            m.replace(R.id.fragment_container_view, cls, null, str);
            m.commitInternal(false);
            this.isFirstFragmentAdded = true;
            return;
        }
        FragmentManagerImpl supportFragmentManager2 = getSupportFragmentManager();
        BackStackRecord m2 =
                BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                        supportFragmentManager2, supportFragmentManager2);
        m2.mReorderingAllowed = true;
        m2.mEnterAnim = R.anim.shared_x_axis_activity_open_enter_dynamic_color;
        m2.mExitAnim = R.anim.shared_x_axis_activity_open_exit;
        m2.mPopEnterAnim = R.anim.shared_x_axis_activity_close_enter_dynamic_color;
        m2.mPopExitAnim = R.anim.shared_x_axis_activity_close_exit;
        m2.replace(R.id.fragment_container_view, cls, null, str);
        m2.addToBackStack(str);
        m2.commitInternal(false);
    }
}
