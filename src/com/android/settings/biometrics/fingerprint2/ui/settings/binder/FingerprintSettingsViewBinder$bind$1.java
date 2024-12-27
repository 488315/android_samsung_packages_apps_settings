package com.android.settings.biometrics.fingerprint2.ui.settings.binder;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintAuthAttemptModel;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData;
import com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference;
import com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment;
import com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$addFooter$FooterColumn;
import com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$confirmDeviceResultListener$1;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.widget.FooterPreference;

import com.google.android.setupdesign.util.DeviceHelper;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class FingerprintSettingsViewBinder$bind$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FingerprintSettingsViewBinder.FingerprintView $view;
    final /* synthetic */ FingerprintSettingsViewModel $viewModel;
    int label;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.binder.FingerprintSettingsViewBinder$bind$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ FingerprintSettingsViewBinder.FingerprintView $view;

        public /* synthetic */ AnonymousClass1(
                FingerprintSettingsViewBinder.FingerprintView fingerprintView, int i) {
            this.$r8$classId = i;
            this.$view = fingerprintView;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final Object emit(Object obj, Continuation continuation) {
            String str;
            FingerprintData fingerprintData;
            switch (this.$r8$classId) {
                case 0:
                    List enrolledFingerprints = (List) obj;
                    final FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment =
                            (FingerprintSettingsV2Fragment) this.$view;
                    fingerprintSettingsV2Fragment.getClass();
                    Intrinsics.checkNotNullParameter(enrolledFingerprints, "enrolledFingerprints");
                    PreferenceCategory preferenceCategory =
                            (PreferenceCategory)
                                    fingerprintSettingsV2Fragment.findPreference(
                                            "security_settings_fingerprints_enrolled");
                    if (preferenceCategory != null) {
                        preferenceCategory.removeAll();
                    }
                    Iterator it = enrolledFingerprints.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            if (preferenceCategory != null) {
                                preferenceCategory.setVisible(true);
                            }
                            fingerprintSettingsV2Fragment.getPreferenceScreen().setVisible(true);
                            PreferenceCategory preferenceCategory2 =
                                    (PreferenceCategory)
                                            fingerprintSettingsV2Fragment.findPreference(
                                                    "security_settings_fingerprint_footer");
                            final RestrictedLockUtils.EnforcedAdmin
                                    checkIfKeyguardFeaturesDisabled =
                                            RestrictedLockUtilsInternal
                                                    .checkIfKeyguardFeaturesDisabled(
                                                            fingerprintSettingsV2Fragment
                                                                    .getActivity(),
                                                            32,
                                                            fingerprintSettingsV2Fragment
                                                                    .requireActivity()
                                                                    .getUserId());
                            final FragmentActivity requireActivity =
                                    fingerprintSettingsV2Fragment.requireActivity();
                            Intrinsics.checkNotNullExpressionValue(
                                    requireActivity, "requireActivity(...)");
                            final Intent helpIntent =
                                    HelpUtils.getHelpIntent(
                                            requireActivity,
                                            fingerprintSettingsV2Fragment.getString(
                                                    R.string.help_uri_default),
                                            requireActivity.getClass().getName());
                            final int i = 1;
                            View.OnClickListener onClickListener =
                                    new View
                                            .OnClickListener() { // from class:
                                                                 // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$addFooter$2
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            switch (i) {
                                                case 0:
                                                    RestrictedLockUtils
                                                            .sendShowAdminSupportDetailsIntent(
                                                                    requireActivity,
                                                                    (RestrictedLockUtils
                                                                                    .EnforcedAdmin)
                                                                            helpIntent);
                                                    break;
                                                default:
                                                    requireActivity.startActivityForResult(
                                                            (Intent) helpIntent, 0);
                                                    break;
                                            }
                                        }
                                    };
                            ArrayList arrayList = new ArrayList();
                            if (checkIfKeyguardFeaturesDisabled != null) {
                                DevicePolicyManager devicePolicyManager =
                                        (DevicePolicyManager)
                                                fingerprintSettingsV2Fragment.getSystemService(
                                                        DevicePolicyManager.class);
                                FingerprintSettingsV2Fragment$addFooter$FooterColumn
                                        fingerprintSettingsV2Fragment$addFooter$FooterColumn =
                                                new FingerprintSettingsV2Fragment$addFooter$FooterColumn();
                                fingerprintSettingsV2Fragment$addFooter$FooterColumn.title =
                                        devicePolicyManager
                                                .getResources()
                                                .getString(
                                                        "Settings.FINGERPRINT_UNLOCK_DISABLED_EXPLANATION",
                                                        new Supplier() { // from class:
                                                            // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$addFooter$1
                                                            @Override // java.util.function.Supplier
                                                            public final Object get() {
                                                                return FingerprintSettingsV2Fragment
                                                                        .this
                                                                        .getString(
                                                                                R.string
                                                                                        .security_fingerprint_disclaimer_lockscreen_disabled_1);
                                                            }
                                                        });
                                final int i2 = 0;
                                fingerprintSettingsV2Fragment$addFooter$FooterColumn
                                                .learnMoreOnClickListener =
                                        new View
                                                .OnClickListener() { // from class:
                                                                     // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$addFooter$2
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view) {
                                                switch (i2) {
                                                    case 0:
                                                        RestrictedLockUtils
                                                                .sendShowAdminSupportDetailsIntent(
                                                                        requireActivity,
                                                                        (RestrictedLockUtils
                                                                                        .EnforcedAdmin)
                                                                                checkIfKeyguardFeaturesDisabled);
                                                        break;
                                                    default:
                                                        requireActivity.startActivityForResult(
                                                                (Intent)
                                                                        checkIfKeyguardFeaturesDisabled,
                                                                0);
                                                        break;
                                                }
                                            }
                                        };
                                fingerprintSettingsV2Fragment$addFooter$FooterColumn
                                                .learnMoreOverrideText =
                                        fingerprintSettingsV2Fragment.getText(
                                                R.string.admin_support_more_info);
                                arrayList.add(fingerprintSettingsV2Fragment$addFooter$FooterColumn);
                                FingerprintSettingsV2Fragment$addFooter$FooterColumn
                                        fingerprintSettingsV2Fragment$addFooter$FooterColumn2 =
                                                new FingerprintSettingsV2Fragment$addFooter$FooterColumn();
                                fingerprintSettingsV2Fragment$addFooter$FooterColumn2.title =
                                        fingerprintSettingsV2Fragment.getText(
                                                R.string
                                                        .security_fingerprint_disclaimer_lockscreen_disabled_2);
                                fingerprintSettingsV2Fragment$addFooter$FooterColumn2
                                                .learnMoreOverrideText =
                                        fingerprintSettingsV2Fragment.getText(
                                                R.string
                                                        .security_settings_fingerprint_settings_footer_learn_more);
                                fingerprintSettingsV2Fragment$addFooter$FooterColumn2
                                                .learnMoreOnClickListener =
                                        onClickListener;
                                arrayList.add(
                                        fingerprintSettingsV2Fragment$addFooter$FooterColumn2);
                            } else {
                                FingerprintSettingsV2Fragment$addFooter$FooterColumn
                                        fingerprintSettingsV2Fragment$addFooter$FooterColumn3 =
                                                new FingerprintSettingsV2Fragment$addFooter$FooterColumn();
                                fingerprintSettingsV2Fragment$addFooter$FooterColumn3.title =
                                        fingerprintSettingsV2Fragment.getString(
                                                R.string
                                                        .security_settings_fingerprint_enroll_introduction_v3_message,
                                                DeviceHelper.getDeviceName(
                                                        fingerprintSettingsV2Fragment
                                                                .requireActivity()));
                                fingerprintSettingsV2Fragment$addFooter$FooterColumn3
                                                .learnMoreOnClickListener =
                                        onClickListener;
                                fingerprintSettingsV2Fragment$addFooter$FooterColumn3
                                                .learnMoreOverrideText =
                                        fingerprintSettingsV2Fragment.getText(
                                                R.string
                                                        .security_settings_fingerprint_settings_footer_learn_more);
                                arrayList.add(
                                        fingerprintSettingsV2Fragment$addFooter$FooterColumn3);
                            }
                            if (preferenceCategory2 != null) {
                                preferenceCategory2.removeAll();
                            }
                            int size = arrayList.size();
                            for (int i3 = 0; i3 < size; i3++) {
                                FingerprintSettingsV2Fragment$addFooter$FooterColumn
                                        fingerprintSettingsV2Fragment$addFooter$FooterColumn4 =
                                                (FingerprintSettingsV2Fragment$addFooter$FooterColumn)
                                                        arrayList.get(i3);
                                Context requireContext =
                                        fingerprintSettingsV2Fragment.requireContext();
                                CharSequence charSequence =
                                        fingerprintSettingsV2Fragment$addFooter$FooterColumn4.title;
                                FooterPreference footerPreference =
                                        new FooterPreference(requireContext);
                                footerPreference.setSelectable(false);
                                if (TextUtils.isEmpty(charSequence)) {
                                    throw new IllegalArgumentException(
                                            "Footer title cannot be empty!");
                                }
                                footerPreference.setTitle(charSequence);
                                if (!TextUtils.isEmpty(null)) {
                                    footerPreference.setKey(null);
                                }
                                if (!TextUtils.isEmpty(null)
                                        && !TextUtils.equals(
                                                footerPreference.mContentDescription, null)) {
                                    footerPreference.mContentDescription = null;
                                    footerPreference.notifyChanged();
                                }
                                if (!TextUtils.isEmpty(null)) {
                                    footerPreference.setLearnMoreText(null);
                                }
                                if (i3 > 0) {
                                    footerPreference.setIconVisibility();
                                }
                                View.OnClickListener onClickListener2 =
                                        fingerprintSettingsV2Fragment$addFooter$FooterColumn4
                                                .learnMoreOnClickListener;
                                if (onClickListener2 != null) {
                                    footerPreference.setLearnMoreAction(onClickListener2);
                                    if (!TextUtils.isEmpty(
                                            fingerprintSettingsV2Fragment$addFooter$FooterColumn4
                                                    .learnMoreOverrideText)) {
                                        footerPreference.setLearnMoreText(
                                                fingerprintSettingsV2Fragment$addFooter$FooterColumn4
                                                        .learnMoreOverrideText);
                                    }
                                }
                                if (preferenceCategory2 != null) {
                                    preferenceCategory2.addPreference(footerPreference);
                                }
                            }
                            return Unit.INSTANCE;
                        }
                        FingerprintData fingerprintData2 = (FingerprintData) it.next();
                        if (preferenceCategory != null) {
                            Context requireContext2 =
                                    fingerprintSettingsV2Fragment.requireContext();
                            Intrinsics.checkNotNullExpressionValue(
                                    requireContext2, "requireContext(...)");
                            preferenceCategory.addPreference(
                                    new FingerprintSettingsPreference(
                                            requireContext2,
                                            fingerprintData2,
                                            fingerprintSettingsV2Fragment,
                                            enrolledFingerprints.size() == 1));
                        }
                    }
                    break;
                case 1:
                    Pair pair = (Pair) obj;
                    boolean booleanValue = ((Boolean) pair.getFirst()).booleanValue();
                    int intValue = ((Number) pair.getSecond()).intValue();
                    FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment2 =
                            (FingerprintSettingsV2Fragment) this.$view;
                    Preference findPreference =
                            fingerprintSettingsV2Fragment2.findPreference("key_fingerprint_add");
                    Context context = fingerprintSettingsV2Fragment2.getContext();
                    if (context == null
                            || (str =
                                            context.getString(
                                                    R.string.fingerprint_add_max,
                                                    Integer.valueOf(intValue)))
                                    == null) {
                        str = ApnSettings.MVNO_NONE;
                    }
                    if (findPreference != null) {
                        findPreference.setSummary(str);
                    }
                    if (findPreference != null) {
                        findPreference.setEnabled(booleanValue);
                    }
                    if (findPreference != null) {
                        findPreference.setOnPreferenceClickListener(
                                new FingerprintSettingsV2Fragment$confirmDeviceResultListener$1(
                                        fingerprintSettingsV2Fragment2, 3));
                    }
                    if (findPreference != null) {
                        findPreference.setVisible(true);
                    }
                    return Unit.INSTANCE;
                case 2:
                    boolean booleanValue2 = ((Boolean) obj).booleanValue();
                    FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment3 =
                            (FingerprintSettingsV2Fragment) this.$view;
                    PreferenceCategory preferenceCategory3 =
                            (PreferenceCategory)
                                    fingerprintSettingsV2Fragment3.findPreference(
                                            "security_settings_fingerprint_unlock_category");
                    if (preferenceCategory3 != null) {
                        preferenceCategory3.setVisible(booleanValue2);
                    }
                    Preference findPreference2 =
                            fingerprintSettingsV2Fragment3.findPreference(
                                    "security_settings_require_screen_on_to_auth");
                    if (findPreference2 != null) {
                        findPreference2.setVisible(booleanValue2);
                    }
                    return Unit.INSTANCE;
                default:
                    FingerprintAuthAttemptModel fingerprintAuthAttemptModel =
                            (FingerprintAuthAttemptModel) obj;
                    boolean z =
                            fingerprintAuthAttemptModel
                                    instanceof FingerprintAuthAttemptModel.Success;
                    Unit unit = Unit.INSTANCE;
                    FingerprintSettingsViewBinder.FingerprintView fingerprintView = this.$view;
                    if (!z) {
                        if (!(fingerprintAuthAttemptModel
                                instanceof FingerprintAuthAttemptModel.Error)) {
                            return unit;
                        }
                        FingerprintAuthAttemptModel.Error error =
                                (FingerprintAuthAttemptModel.Error) fingerprintAuthAttemptModel;
                        if (error.error != 7) {
                            return unit;
                        }
                        FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment4 =
                                (FingerprintSettingsV2Fragment) fingerprintView;
                        fingerprintSettingsV2Fragment4.getClass();
                        Toast.makeText(
                                        fingerprintSettingsV2Fragment4.getActivity(),
                                        error.message,
                                        0)
                                .show();
                        return unit;
                    }
                    int i4 =
                            ((FingerprintAuthAttemptModel.Success) fingerprintAuthAttemptModel)
                                    .fingerId;
                    for (FingerprintSettingsPreference fingerprintSettingsPreference :
                            ((FingerprintSettingsV2Fragment) fingerprintView)
                                    .fingerprintPreferences()) {
                        if (fingerprintSettingsPreference != null
                                && (fingerprintData =
                                                fingerprintSettingsPreference.fingerprintViewModel)
                                        != null
                                && fingerprintData.fingerId == i4) {
                            Object highlight =
                                    fingerprintSettingsPreference.highlight(continuation);
                            CoroutineSingletons coroutineSingletons =
                                    CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (highlight != coroutineSingletons) {
                                highlight = unit;
                            }
                            return highlight == coroutineSingletons ? highlight : unit;
                        }
                    }
                    throw new NoSuchElementException(
                            "Collection contains no element matching the predicate.");
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsViewBinder$bind$1(
            FingerprintSettingsViewBinder.FingerprintView fingerprintView,
            FingerprintSettingsViewModel fingerprintSettingsViewModel,
            Continuation continuation) {
        super(2, continuation);
        this.$viewModel = fingerprintSettingsViewModel;
        this.$view = fingerprintView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintSettingsViewBinder$bind$1(this.$view, this.$viewModel, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintSettingsViewBinder$bind$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SafeFlow safeFlow = this.$viewModel.enrolledFingerprints;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, 0);
            this.label = 1;
            if (safeFlow.collect(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
