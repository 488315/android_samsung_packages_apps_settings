package com.android.settings.biometrics.fingerprint2;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;

import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.SettingsApplication;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settings.biometrics.fingerprint2.data.repository.DebuggingRepositoryImpl;
import com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl;
import com.android.settings.biometrics.fingerprint2.debug.data.repository.UdfpsEnrollDebugRepositoryImpl;
import com.android.settings.biometrics.fingerprint2.debug.domain.interactor.DebugTouchEventInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.AccessibilityInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.DebuggingInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.DisplayDensityInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.EnrollStageInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintEnrollInteractor;
import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintEnrollInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintSensorInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.FoldStateInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.OrientationInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.UdfpsEnrollInteractorImpl;
import com.android.settings.biometrics.fingerprint2.domain.interactor.VibrationInteractorImpl;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorCoroutineDispatcherImpl;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BiometricsEnvironment implements ViewModelStoreOwner {
    public final Lazy accessibilityInteractor$delegate;
    public final ContextScope applicationScope;
    public final ExecutorCoroutineDispatcherImpl backgroundDispatcher;
    public final Lazy debuggingInteractor$delegate;
    public final DebuggingRepositoryImpl debuggingRepository;
    public final Lazy displayDensityInteractor$delegate;
    public final Lazy enrollStageInteractor$delegate;
    public final Lazy fingerprintEnrollInteractor$delegate;
    public final FingerprintManager fingerprintManager;
    public final Lazy fingerprintManagerInteractor$delegate;
    public final FingerprintSensorRepositoryImpl fingerprintSensorRepository;
    public final Lazy foldStateInteractor$delegate;
    public final GatekeeperPasswordProvider gateKeeperPasswordProvider;
    public final Lazy orientationInteractor$delegate;
    public final Lazy sensorInteractor$delegate;
    public final Lazy touchEventInteractor$delegate;
    public final UdfpsEnrollDebugRepositoryImpl udfpsDebugRepo;
    public final Lazy udfpsEnrollInteractor$delegate;
    public final Lazy vibrationInteractor$delegate;
    public final ViewModelStore viewModelStore;

    public BiometricsEnvironment(final SettingsApplication context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Intrinsics.checkNotNullExpressionValue(executorService, "executorService");
        ExecutorCoroutineDispatcherImpl executorCoroutineDispatcherImpl =
                new ExecutorCoroutineDispatcherImpl(executorService);
        this.backgroundDispatcher = executorCoroutineDispatcherImpl;
        SupervisorJobImpl SupervisorJob$default = SupervisorKt.SupervisorJob$default();
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        ContextScope contextScope =
                new ContextScope(SupervisorJob$default.plus(MainDispatcherLoader.dispatcher));
        this.applicationScope = contextScope;
        this.gateKeeperPasswordProvider =
                new GatekeeperPasswordProvider(new LockPatternUtils(context));
        FingerprintManager fingerprintManager =
                (FingerprintManager) context.getSystemService("fingerprint");
        this.fingerprintManager = fingerprintManager;
        this.fingerprintSensorRepository =
                new FingerprintSensorRepositoryImpl(
                        fingerprintManager, executorCoroutineDispatcherImpl, contextScope);
        this.debuggingRepository = new DebuggingRepositoryImpl();
        this.udfpsDebugRepo = new UdfpsEnrollDebugRepositoryImpl();
        this.fingerprintEnrollInteractor$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$fingerprintEnrollInteractor$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                SettingsApplication settingsApplication = SettingsApplication.this;
                                FingerprintManager fingerprintManager2 = this.fingerprintManager;
                                return new FingerprintEnrollInteractorImpl(settingsApplication);
                            }
                        });
        this.fingerprintManagerInteractor$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$fingerprintManagerInteractor$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                SettingsApplication settingsApplication = SettingsApplication.this;
                                BiometricsEnvironment biometricsEnvironment = this;
                                ExecutorCoroutineDispatcherImpl executorCoroutineDispatcherImpl2 =
                                        biometricsEnvironment.backgroundDispatcher;
                                FingerprintManager fingerprintManager2 =
                                        biometricsEnvironment.fingerprintManager;
                                FingerprintEnrollInteractor fingerprintEnrollInteractor =
                                        (FingerprintEnrollInteractor)
                                                biometricsEnvironment
                                                        .fingerprintEnrollInteractor$delegate
                                                        .getValue();
                                return new FingerprintManagerInteractorImpl(
                                        settingsApplication,
                                        executorCoroutineDispatcherImpl2,
                                        fingerprintManager2,
                                        biometricsEnvironment.fingerprintSensorRepository,
                                        biometricsEnvironment.gateKeeperPasswordProvider,
                                        fingerprintEnrollInteractor);
                            }
                        });
        this.accessibilityInteractor$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$accessibilityInteractor$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                Object systemService =
                                        SettingsApplication.this.getSystemService(
                                                AccessibilityManager.class);
                                Intrinsics.checkNotNull(systemService);
                                return new AccessibilityInteractorImpl(
                                        (AccessibilityManager) systemService,
                                        this.applicationScope);
                            }
                        });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$foldStateInteractor$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return new FoldStateInteractorImpl(SettingsApplication.this);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$orientationInteractor$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return new OrientationInteractorImpl(SettingsApplication.this);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$vibrationInteractor$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        SettingsApplication applicationContext = SettingsApplication.this;
                        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
                        VibrationInteractorImpl vibrationInteractorImpl =
                                new VibrationInteractorImpl();
                        Object systemService =
                                applicationContext.getSystemService((Class<Object>) Vibrator.class);
                        Intrinsics.checkNotNull(systemService);
                        return vibrationInteractorImpl;
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$displayDensityInteractor$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return new DisplayDensityInteractorImpl(
                                SettingsApplication.this, this.applicationScope);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$debuggingInteractor$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return new DebuggingInteractorImpl(
                                BiometricsEnvironment.this.debuggingRepository);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$enrollStageInteractor$2
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return new EnrollStageInteractorImpl();
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$udfpsEnrollInteractor$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return new UdfpsEnrollInteractorImpl(
                                SettingsApplication.this,
                                (AccessibilityInteractorImpl)
                                        this.accessibilityInteractor$delegate.getValue());
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$sensorInteractor$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        FingerprintSensorRepositoryImpl repo =
                                BiometricsEnvironment.this.fingerprintSensorRepository;
                        Intrinsics.checkNotNullParameter(repo, "repo");
                        return new FingerprintSensorInteractorImpl();
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$touchEventInteractor$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        BiometricsEnvironment biometricsEnvironment = BiometricsEnvironment.this;
                        return biometricsEnvironment.debuggingRepository.isBuildDebuggable
                                ? new DebugTouchEventInteractorImpl(
                                        biometricsEnvironment.udfpsDebugRepo)
                                : new Object() { // from class:
                                                 // com.android.settings.biometrics.fingerprint2.BiometricsEnvironment$touchEventInteractor$2.1
                                    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1
                                            touchEvent;

                                    {
                                        new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(
                                                new MotionEvent[0]);
                                    }
                                };
                    }
                });
        this.viewModelStore = new ViewModelStore();
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public final ViewModelStore getViewModelStore() {
        return this.viewModelStore;
    }
}
