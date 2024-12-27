package com.android.settings.biometrics.fingerprint2.ui.settings.fragment;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintEnrollInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.PressToAuthInteractorImpl;
import com.android.settings.biometrics.fingerprint2.ui.settings.binder.FingerprintSettingsViewBinder;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.EnrollAdditionalFingerprint;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.EnrollFirstFingerprint;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsNavigationViewModel;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FinishSettingsWithResult;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.LaunchConfirmDeviceCredential;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.LaunchedActivity;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.dashboard.DashboardFragment;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"
        },
        d2 = {
            "Lcom/android/settings/biometrics/fingerprint2/ui/settings/fragment/FingerprintSettingsV2Fragment;",
            "Lcom/android/settings/dashboard/DashboardFragment;",
            "Lcom/android/settings/biometrics/fingerprint2/ui/settings/binder/FingerprintSettingsViewBinder$FingerprintView;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintSettingsV2Fragment extends DashboardFragment
        implements FingerprintSettingsViewBinder.FingerprintView {
    public final ActivityResultLauncher confirmDeviceResultListener;
    public final ActivityResultLauncher launchAdditionalFingerprintListener;
    public final ActivityResultLauncher launchFirstEnrollmentListener;
    public FingerprintSettingsNavigationViewModel navigationViewModel;
    public FingerprintSettingsViewModel settingsViewModel;

    public FingerprintSettingsV2Fragment() {
        ActivityResultLauncher registerForActivityResult =
                registerForActivityResult(
                        new ActivityResultContracts$StartActivityForResult(0),
                        new FingerprintSettingsV2Fragment$confirmDeviceResultListener$1(this, 0));
        Intrinsics.checkNotNullExpressionValue(
                registerForActivityResult, "registerForActivityResult(...)");
        this.confirmDeviceResultListener = registerForActivityResult;
        ActivityResultLauncher registerForActivityResult2 =
                registerForActivityResult(
                        new ActivityResultContracts$StartActivityForResult(0),
                        new FingerprintSettingsV2Fragment$confirmDeviceResultListener$1(this, 1));
        Intrinsics.checkNotNullExpressionValue(
                registerForActivityResult2, "registerForActivityResult(...)");
        this.launchAdditionalFingerprintListener = registerForActivityResult2;
        ActivityResultLauncher registerForActivityResult3 =
                registerForActivityResult(
                        new ActivityResultContracts$StartActivityForResult(0),
                        new FingerprintSettingsV2Fragment$confirmDeviceResultListener$1(this, 2));
        Intrinsics.checkNotNullExpressionValue(
                registerForActivityResult3, "registerForActivityResult(...)");
        this.launchFirstEnrollmentListener = registerForActivityResult3;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0094 A[Catch: Exception -> 0x0032, TryCatch #0 {Exception -> 0x0032, blocks: (B:11:0x002e, B:12:0x008a, B:14:0x0094, B:15:0x00a1, B:21:0x0056, B:22:0x0060, B:24:0x0066, B:26:0x006f, B:27:0x0073, B:30:0x0079, B:32:0x007d, B:39:0x00a6, B:40:0x00ad), top: B:7:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object askUserToDeleteDialog(
            com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData r7,
            kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$askUserToDeleteDialog$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$askUserToDeleteDialog$1 r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$askUserToDeleteDialog$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$askUserToDeleteDialog$1 r0 = new com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$askUserToDeleteDialog$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            java.lang.String r3 = "FingerprintSettingsV2Fragment"
            r4 = 1
            if (r2 == 0) goto L3d
            if (r2 != r4) goto L35
            java.lang.Object r6 = r0.L$1
            r7 = r6
            com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData r7 = (com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData) r7
            java.lang.Object r6 = r0.L$0
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment r6 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment) r6
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Exception -> L32
            goto L8a
        L32:
            r6 = move-exception
            goto Lae
        L35:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3d:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r2 = "showing delete dialog for ("
            r8.<init>(r2)
            r8.append(r7)
            java.lang.String r2 = ")"
            r8.append(r2)
            java.lang.String r8 = r8.toString()
            android.util.Log.d(r3, r8)
            java.util.List r8 = r6.fingerprintPreferences()     // Catch: java.lang.Exception -> L32
            java.lang.Iterable r8 = (java.lang.Iterable) r8     // Catch: java.lang.Exception -> L32
            java.util.Iterator r8 = r8.iterator()     // Catch: java.lang.Exception -> L32
        L60:
            boolean r2 = r8.hasNext()     // Catch: java.lang.Exception -> L32
            if (r2 == 0) goto La6
            java.lang.Object r2 = r8.next()     // Catch: java.lang.Exception -> L32
            r5 = r2
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference r5 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference) r5     // Catch: java.lang.Exception -> L32
            if (r5 == 0) goto L72
            com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData r5 = r5.fingerprintViewModel     // Catch: java.lang.Exception -> L32
            goto L73
        L72:
            r5 = 0
        L73:
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r7)     // Catch: java.lang.Exception -> L32
            if (r5 == 0) goto L60
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference r2 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference) r2     // Catch: java.lang.Exception -> L32
            if (r2 == 0) goto L91
            r0.L$0 = r6     // Catch: java.lang.Exception -> L32
            r0.L$1 = r7     // Catch: java.lang.Exception -> L32
            r0.label = r4     // Catch: java.lang.Exception -> L32
            java.lang.Object r8 = r2.askUserToDeleteDialog(r0)     // Catch: java.lang.Exception -> L32
            if (r8 != r1) goto L8a
            return r1
        L8a:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Exception -> L32
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Exception -> L32
            goto L92
        L91:
            r8 = 0
        L92:
            if (r8 == 0) goto La1
            com.android.settingslib.core.instrumentation.MetricsFeatureProvider r0 = r6.mMetricsFeatureProvider     // Catch: java.lang.Exception -> L32
            android.content.Context r6 = r6.getContext()     // Catch: java.lang.Exception -> L32
            int r7 = r7.fingerId     // Catch: java.lang.Exception -> L32
            r1 = 253(0xfd, float:3.55E-43)
            r0.action(r6, r1, r7)     // Catch: java.lang.Exception -> L32
        La1:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r8)     // Catch: java.lang.Exception -> L32
            return r6
        La6:
            java.util.NoSuchElementException r6 = new java.util.NoSuchElementException     // Catch: java.lang.Exception -> L32
            java.lang.String r7 = "Collection contains no element matching the predicate."
            r6.<init>(r7)     // Catch: java.lang.Exception -> L32
            throw r6     // Catch: java.lang.Exception -> L32
        Lae:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "askUserToDeleteDialog exception "
            r7.<init>(r8)
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            android.util.Log.d(r3, r6)
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment.askUserToDeleteDialog(com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x008a A[Catch: Exception -> 0x002e, TryCatch #0 {Exception -> 0x002e, blocks: (B:11:0x002a, B:12:0x0084, B:14:0x008a, B:20:0x0052, B:21:0x005c, B:23:0x0062, B:25:0x006b, B:26:0x006f, B:29:0x0075, B:31:0x0079, B:38:0x009e, B:39:0x00a5), top: B:7:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object askUserToRenameDialog(
            com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData r8,
            kotlin.coroutines.Continuation r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$askUserToRenameDialog$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$askUserToRenameDialog$1 r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$askUserToRenameDialog$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$askUserToRenameDialog$1 r0 = new com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment$askUserToRenameDialog$1
            r0.<init>(r7, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            java.lang.String r3 = "FingerprintSettingsV2Fragment"
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L39
            if (r2 != r4) goto L31
            java.lang.Object r7 = r0.L$0
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment r7 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment) r7
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Exception -> L2e
            goto L84
        L2e:
            r7 = move-exception
            goto La6
        L31:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L39:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r2 = "showing rename dialog for ("
            r9.<init>(r2)
            r9.append(r8)
            java.lang.String r2 = ")"
            r9.append(r2)
            java.lang.String r9 = r9.toString()
            android.util.Log.d(r3, r9)
            java.util.List r9 = r7.fingerprintPreferences()     // Catch: java.lang.Exception -> L2e
            java.lang.Iterable r9 = (java.lang.Iterable) r9     // Catch: java.lang.Exception -> L2e
            java.util.Iterator r9 = r9.iterator()     // Catch: java.lang.Exception -> L2e
        L5c:
            boolean r2 = r9.hasNext()     // Catch: java.lang.Exception -> L2e
            if (r2 == 0) goto L9e
            java.lang.Object r2 = r9.next()     // Catch: java.lang.Exception -> L2e
            r6 = r2
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference r6 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference) r6     // Catch: java.lang.Exception -> L2e
            if (r6 == 0) goto L6e
            com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData r6 = r6.fingerprintViewModel     // Catch: java.lang.Exception -> L2e
            goto L6f
        L6e:
            r6 = r5
        L6f:
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r8)     // Catch: java.lang.Exception -> L2e
            if (r6 == 0) goto L5c
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference r2 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference) r2     // Catch: java.lang.Exception -> L2e
            if (r2 == 0) goto L87
            r0.L$0 = r7     // Catch: java.lang.Exception -> L2e
            r0.label = r4     // Catch: java.lang.Exception -> L2e
            java.lang.Object r9 = r2.askUserToRenameDialog(r0)     // Catch: java.lang.Exception -> L2e
            if (r9 != r1) goto L84
            return r1
        L84:
            kotlin.Pair r9 = (kotlin.Pair) r9     // Catch: java.lang.Exception -> L2e
            goto L88
        L87:
            r9 = r5
        L88:
            if (r9 == 0) goto L9d
            com.android.settingslib.core.instrumentation.MetricsFeatureProvider r8 = r7.mMetricsFeatureProvider     // Catch: java.lang.Exception -> L2e
            android.content.Context r7 = r7.getContext()     // Catch: java.lang.Exception -> L2e
            java.lang.Object r0 = r9.getFirst()     // Catch: java.lang.Exception -> L2e
            com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData r0 = (com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData) r0     // Catch: java.lang.Exception -> L2e
            int r0 = r0.fingerId     // Catch: java.lang.Exception -> L2e
            r1 = 254(0xfe, float:3.56E-43)
            r8.action(r7, r1, r0)     // Catch: java.lang.Exception -> L2e
        L9d:
            return r9
        L9e:
            java.util.NoSuchElementException r7 = new java.util.NoSuchElementException     // Catch: java.lang.Exception -> L2e
            java.lang.String r8 = "Collection contains no element matching the predicate."
            r7.<init>(r8)     // Catch: java.lang.Exception -> L2e
            throw r7     // Catch: java.lang.Exception -> L2e
        La6:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "askUserToRenameDialog exception "
            r8.<init>(r9)
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Log.d(r3, r7)
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment.askUserToRenameDialog(com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final List fingerprintPreferences() {
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) findPreference("security_settings_fingerprints_enrolled");
        if (preferenceCategory == null) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        int preferenceCount = preferenceCategory.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = preferenceCategory.getPreference(i);
            Intrinsics.checkNotNullExpressionValue(preference, "getPreference(...)");
            arrayList.add(preference);
        }
        List list = CollectionsKt___CollectionsKt.toList(arrayList);
        ArrayList arrayList2 =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList2.add((FingerprintSettingsPreference) ((Preference) it.next()));
        }
        return arrayList2;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "FingerprintSettingsV2Fragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 49;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.security_settings_fingerprint_limbo;
    }

    public final void launchConfirmOrChooseLock(int i) {
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                Dispatchers.Default,
                null,
                new FingerprintSettingsV2Fragment$launchConfirmOrChooseLock$1(this, i, null),
                2);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        onConfirmDevice(i2, intent);
    }

    public final void onConfirmDevice(int i, Intent intent) {
        boolean z = true;
        if (i != 1 && i != -1) {
            z = false;
        }
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new FingerprintSettingsV2Fragment$onConfirmDevice$1(
                        this,
                        z,
                        (Long) (intent != null ? intent.getExtra("gk_pw_handle") : null),
                        null),
                3);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            Log.d("FingerprintSettingsV2Fragment", "onCreateWithSavedState");
        } else {
            Log.d("FingerprintSettingsV2Fragment", "onCreate()");
        }
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        int userId = requireContext.getUserId();
        getPreferenceScreen().setVisible(false);
        Object systemService = requireContext.getSystemService("fingerprint");
        Intrinsics.checkNotNull(
                systemService,
                "null cannot be cast to non-null type"
                    + " android.hardware.fingerprint.FingerprintManager");
        FingerprintManager fingerprintManager = (FingerprintManager) systemService;
        DefaultIoScheduler defaultIoScheduler = Dispatchers.IO;
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        requireActivity.getUser().getIdentifier();
        FingerprintSensorRepositoryImpl fingerprintSensorRepositoryImpl =
                new FingerprintSensorRepositoryImpl(
                        fingerprintManager,
                        defaultIoScheduler,
                        LifecycleOwnerKt.getLifecycleScope(this));
        new PressToAuthInteractorImpl(requireContext, defaultIoScheduler);
        Context applicationContext = requireContext().getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        FingerprintEnrollInteractorImpl fingerprintEnrollInteractorImpl =
                new FingerprintEnrollInteractorImpl(applicationContext);
        Context applicationContext2 = requireContext.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
        FingerprintManagerInteractorImpl fingerprintManagerInteractorImpl =
                new FingerprintManagerInteractorImpl(
                        applicationContext2,
                        defaultIoScheduler,
                        fingerprintManager,
                        fingerprintSensorRepositoryImpl,
                        new GatekeeperPasswordProvider(
                                new LockPatternUtils(requireContext.getApplicationContext())),
                        fingerprintEnrollInteractorImpl);
        FingerprintSettingsNavigationViewModel.FingerprintSettingsNavigationModelFactory
                fingerprintSettingsNavigationModelFactory =
                        new FingerprintSettingsNavigationViewModel
                                .FingerprintSettingsNavigationModelFactory(
                                userId,
                                fingerprintManagerInteractorImpl,
                                defaultIoScheduler,
                                getIntent().getByteArrayExtra("hw_auth_token"),
                                Long.valueOf(getIntent().getLongExtra("challenge", -1L)));
        ViewModelStore store = getViewModelStore();
        CreationExtras defaultCreationExtras = getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(defaultCreationExtras, "defaultCreationExtras");
        ViewModelProviderImpl viewModelProviderImpl =
                new ViewModelProviderImpl(
                        store, fingerprintSettingsNavigationModelFactory, defaultCreationExtras);
        KClass kotlinClass =
                JvmClassMappingKt.getKotlinClass(FingerprintSettingsNavigationViewModel.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel =
                (FingerprintSettingsNavigationViewModel)
                        viewModelProviderImpl.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        this.navigationViewModel = fingerprintSettingsNavigationViewModel;
        FingerprintSettingsViewModel.FingerprintSettingsViewModelFactory
                fingerprintSettingsViewModelFactory =
                        new FingerprintSettingsViewModel.FingerprintSettingsViewModelFactory(
                                userId,
                                fingerprintManagerInteractorImpl,
                                defaultIoScheduler,
                                fingerprintSettingsNavigationViewModel);
        ViewModelStore store2 = getViewModelStore();
        CreationExtras defaultCreationExtras2 = getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store2, "store");
        Intrinsics.checkNotNullParameter(defaultCreationExtras2, "defaultCreationExtras");
        ViewModelProviderImpl viewModelProviderImpl2 =
                new ViewModelProviderImpl(
                        store2, fingerprintSettingsViewModelFactory, defaultCreationExtras2);
        KClass kotlinClass2 = JvmClassMappingKt.getKotlinClass(FingerprintSettingsViewModel.class);
        String qualifiedName2 = kotlinClass2.getQualifiedName();
        if (qualifiedName2 == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        FingerprintSettingsViewModel fingerprintSettingsViewModel =
                (FingerprintSettingsViewModel)
                        viewModelProviderImpl2.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass2,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName2));
        this.settingsViewModel = fingerprintSettingsViewModel;
        FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel2 =
                this.navigationViewModel;
        if (fingerprintSettingsNavigationViewModel2 != null) {
            FingerprintSettingsViewBinder.bind(
                    this,
                    fingerprintSettingsViewModel,
                    fingerprintSettingsNavigationViewModel2,
                    LifecycleOwnerKt.getLifecycleScope(this));
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("navigationViewModel");
            throw null;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        StateFlowImpl stateFlowImpl;
        Object value;
        super.onPause();
        FingerprintSettingsViewModel fingerprintSettingsViewModel = this.settingsViewModel;
        if (fingerprintSettingsViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settingsViewModel");
            throw null;
        }
        do {
            stateFlowImpl = fingerprintSettingsViewModel._consumerShouldAuthenticate;
            value = stateFlowImpl.getValue();
            ((Boolean) value).getClass();
        } while (!stateFlowImpl.compareAndSet(value, Boolean.FALSE));
        FragmentManager parentFragmentManager = getParentFragmentManager();
        BackStackRecord m =
                DialogFragment$$ExternalSyntheticOutline0.m(
                        parentFragmentManager, parentFragmentManager);
        for (Fragment fragment : getParentFragmentManager().mFragmentStore.getFragments()) {
            if (fragment instanceof InstrumentedDialogFragment) {
                Log.d(
                        "FingerprintSettingsV2Fragment",
                        "removing dialog settings fragment " + fragment);
                ((InstrumentedDialogFragment) fragment).dismiss();
                m.remove(fragment);
            }
        }
        m.commitInternal(false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        StateFlowImpl stateFlowImpl;
        Object value;
        super.onResume();
        FingerprintSettingsViewModel fingerprintSettingsViewModel = this.settingsViewModel;
        if (fingerprintSettingsViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settingsViewModel");
            throw null;
        }
        do {
            stateFlowImpl = fingerprintSettingsViewModel._consumerShouldAuthenticate;
            value = stateFlowImpl.getValue();
            ((Boolean) value).getClass();
        } while (!stateFlowImpl.compareAndSet(value, Boolean.TRUE));
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        Object value;
        super.onStop();
        FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel =
                this.navigationViewModel;
        if (fingerprintSettingsNavigationViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navigationViewModel");
            throw null;
        }
        boolean isChangingConfigurations = requireActivity().isChangingConfigurations();
        StateFlowImpl stateFlowImpl = fingerprintSettingsNavigationViewModel._nextStep;
        if ((stateFlowImpl.getValue() instanceof LaunchConfirmDeviceCredential)
                || (stateFlowImpl.getValue() instanceof EnrollAdditionalFingerprint)
                || (stateFlowImpl.getValue() instanceof EnrollFirstFingerprint)
                || (stateFlowImpl.getValue() instanceof LaunchedActivity)
                || isChangingConfigurations) {
            return;
        }
        do {
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(
                value, new FinishSettingsWithResult(3, "onStop finishing settings")));
    }
}
