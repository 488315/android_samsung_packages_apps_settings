package com.android.settings.biometrics.fingerprint2.ui.settings.binder;

import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

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
final class FingerprintSettingsViewBinder$bind$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ FingerprintSettingsViewBinder.FingerprintView $view;
    final /* synthetic */ FingerprintSettingsViewModel $viewModel;
    int label;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\f\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u008a@"
            },
            d2 = {
                "<anonymous>",
                ApnSettings.MVNO_NONE,
                "it",
                "Lcom/android/settings/biometrics/fingerprint2/ui/settings/viewmodel/PreferenceViewModel;"
            },
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.binder.FingerprintSettingsViewBinder$bind$4$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FingerprintSettingsViewBinder.FingerprintView $view;
        final /* synthetic */ FingerprintSettingsViewModel $viewModel;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                FingerprintSettingsViewBinder.FingerprintView fingerprintView,
                FingerprintSettingsViewModel fingerprintSettingsViewModel,
                Continuation continuation) {
            super(2, continuation);
            this.$view = fingerprintView;
            this.$viewModel = fingerprintSettingsViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(this.$view, this.$viewModel, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((PreferenceViewModel) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x0052  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x00a8  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) {
            /*
                r7 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r7.label
                kotlin.Unit r2 = kotlin.Unit.INSTANCE
                r3 = 0
                java.lang.String r4 = "FingerprintSettingsViewBinder"
                r5 = 2
                r6 = 1
                if (r1 == 0) goto L2a
                if (r1 == r6) goto L22
                if (r1 != r5) goto L1a
                java.lang.Object r0 = r7.L$0
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel) r0
                kotlin.ResultKt.throwOnFailure(r8)
                goto La0
            L1a:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r8)
                throw r7
            L22:
                java.lang.Object r0 = r7.L$0
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel) r0
                kotlin.ResultKt.throwOnFailure(r8)
                goto L4e
            L2a:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel r8 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel) r8
                if (r8 != 0) goto L34
                return r2
            L34:
                boolean r1 = r8 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel.RenameDialog
                if (r1 == 0) goto L86
                com.android.settings.biometrics.fingerprint2.ui.settings.binder.FingerprintSettingsViewBinder$FingerprintView r1 = r7.$view
                r5 = r8
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel$RenameDialog r5 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel.RenameDialog) r5
                r7.L$0 = r8
                r7.label = r6
                com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment r1 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment) r1
                com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData r5 = r5.fingerprintViewModel
                java.lang.Object r1 = r1.askUserToRenameDialog(r5, r7)
                if (r1 != r0) goto L4c
                return r0
            L4c:
                r0 = r8
                r8 = r1
            L4e:
                kotlin.Pair r8 = (kotlin.Pair) r8
                if (r8 == 0) goto L74
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r5 = "renaming fingerprint "
                r1.<init>(r5)
                r1.append(r0)
                java.lang.String r0 = r1.toString()
                android.util.Log.d(r4, r0)
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel r0 = r7.$viewModel
                java.lang.Object r1 = r8.getFirst()
                com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData r1 = (com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData) r1
                java.lang.Object r8 = r8.getSecond()
                java.lang.String r8 = (java.lang.String) r8
                r0.renameFingerprint(r1, r8)
            L74:
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel r7 = r7.$viewModel
                kotlinx.coroutines.flow.StateFlowImpl r1 = r7._isShowingDialog
            L78:
                java.lang.Object r7 = r1.getValue()
                r8 = r7
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel r8 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel) r8
                boolean r7 = r1.compareAndSet(r7, r3)
                if (r7 == 0) goto L78
                goto Ld3
            L86:
                boolean r1 = r8 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel.DeleteDialog
                if (r1 == 0) goto Ld3
                com.android.settings.biometrics.fingerprint2.ui.settings.binder.FingerprintSettingsViewBinder$FingerprintView r1 = r7.$view
                r6 = r8
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel$DeleteDialog r6 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel.DeleteDialog) r6
                r7.L$0 = r8
                r7.label = r5
                com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment r1 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment) r1
                com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData r5 = r6.fingerprintViewModel
                java.lang.Object r1 = r1.askUserToDeleteDialog(r5, r7)
                if (r1 != r0) goto L9e
                return r0
            L9e:
                r0 = r8
                r8 = r1
            La0:
                java.lang.Boolean r8 = (java.lang.Boolean) r8
                boolean r8 = r8.booleanValue()
                if (r8 == 0) goto Lc2
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                java.lang.String r1 = "deleting fingerprint "
                r8.<init>(r1)
                r8.append(r0)
                java.lang.String r8 = r8.toString()
                android.util.Log.d(r4, r8)
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel r8 = r7.$viewModel
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel$DeleteDialog r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel.DeleteDialog) r0
                com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData r0 = r0.fingerprintViewModel
                r8.deleteFingerprint(r0)
            Lc2:
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel r7 = r7.$viewModel
                kotlinx.coroutines.flow.StateFlowImpl r7 = r7._isShowingDialog
            Lc6:
                java.lang.Object r8 = r7.getValue()
                r0 = r8
                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.PreferenceViewModel) r0
                boolean r8 = r7.compareAndSet(r8, r3)
                if (r8 == 0) goto Lc6
            Ld3:
                return r2
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.biometrics.fingerprint2.ui.settings.binder.FingerprintSettingsViewBinder$bind$4.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsViewBinder$bind$4(
            FingerprintSettingsViewBinder.FingerprintView fingerprintView,
            FingerprintSettingsViewModel fingerprintSettingsViewModel,
            Continuation continuation) {
        super(2, continuation);
        this.$viewModel = fingerprintSettingsViewModel;
        this.$view = fingerprintView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintSettingsViewBinder$bind$4(this.$view, this.$viewModel, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintSettingsViewBinder$bind$4)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintSettingsViewModel fingerprintSettingsViewModel = this.$viewModel;
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1
                    flowKt__ZipKt$combine$$inlined$unsafeFlow$1 =
                            fingerprintSettingsViewModel.isShowingDialog;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(this.$view, fingerprintSettingsViewModel, null);
            this.label = 1;
            if (FlowKt.collectLatest(
                            flowKt__ZipKt$combine$$inlined$unsafeFlow$1, anonymousClass1, this)
                    == coroutineSingletons) {
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
