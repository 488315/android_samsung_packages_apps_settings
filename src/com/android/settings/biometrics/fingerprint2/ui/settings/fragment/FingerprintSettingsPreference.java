package com.android.settings.biometrics.fingerprint2.ui.settings.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.fingerprint.Fingerprint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImeAwareEditText;

import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel;
import com.android.settingslib.widget.TwoTargetPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"
        },
        d2 = {
            "Lcom/android/settings/biometrics/fingerprint2/ui/settings/fragment/FingerprintSettingsPreference;",
            "Lcom/android/settingslib/widget/TwoTargetPreference;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintSettingsPreference extends TwoTargetPreference {
    public final FingerprintData fingerprintViewModel;
    public final FingerprintSettingsV2Fragment fragment;
    public final boolean isLastFingerprint;
    public View myView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsPreference(
            Context context,
            FingerprintData fingerprintViewModel,
            FingerprintSettingsV2Fragment fragment,
            boolean z) {
        super(context);
        Intrinsics.checkNotNullParameter(fingerprintViewModel, "fingerprintViewModel");
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        this.fingerprintViewModel = fingerprintViewModel;
        this.fragment = fragment;
        this.isLastFingerprint = z;
        setKey("FINGERPRINT_" + fingerprintViewModel.fingerId);
        String key = getKey();
        StringBuilder sb = new StringBuilder("FingerprintPreference ");
        sb.append(this);
        sb.append(" with frag ");
        sb.append(fragment);
        sb.append(" ");
        MainClearConfirm$$ExternalSyntheticOutline0.m(sb, key, "FingerprintSettingsPreference");
        setTitle(fingerprintViewModel.name);
        setPersistent();
        setIcon(R.drawable.ic_fingerprint_24dp);
        setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener() { // from class:
                    // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference.1

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
                    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference$1$1, reason: invalid class name and collision with other inner class name */
                    final class C00251 extends SuspendLambda implements Function2 {
                        int label;
                        final /* synthetic */ FingerprintSettingsPreference this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C00251(
                                FingerprintSettingsPreference fingerprintSettingsPreference,
                                Continuation continuation) {
                            super(2, continuation);
                            this.this$0 = fingerprintSettingsPreference;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new C00251(this.this$0, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            C00251 c00251 =
                                    (C00251) create((CoroutineScope) obj, (Continuation) obj2);
                            Unit unit = Unit.INSTANCE;
                            c00251.invokeSuspend(unit);
                            return unit;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons =
                                    CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException(
                                        "call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            FingerprintSettingsPreference fingerprintSettingsPreference =
                                    this.this$0;
                            FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment =
                                    fingerprintSettingsPreference.fragment;
                            FingerprintData fingerprintViewModel =
                                    fingerprintSettingsPreference.fingerprintViewModel;
                            fingerprintSettingsV2Fragment.getClass();
                            Intrinsics.checkNotNullParameter(
                                    fingerprintViewModel, "fingerprintViewModel");
                            StringBuilder sb = new StringBuilder("onPrefClicked(");
                            sb.append(fingerprintViewModel);
                            MainClearConfirm$$ExternalSyntheticOutline0.m(
                                    sb, ")", "FingerprintSettingsV2Fragment");
                            FingerprintSettingsViewModel fingerprintSettingsViewModel =
                                    fingerprintSettingsV2Fragment.settingsViewModel;
                            if (fingerprintSettingsViewModel != null) {
                                fingerprintSettingsViewModel.onPrefClicked(fingerprintViewModel);
                                return Unit.INSTANCE;
                            }
                            Intrinsics.throwUninitializedPropertyAccessException(
                                    "settingsViewModel");
                            throw null;
                        }
                    }

                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        FingerprintSettingsPreference fingerprintSettingsPreference =
                                FingerprintSettingsPreference.this;
                        BuildersKt.launch$default(
                                LifecycleOwnerKt.getLifecycleScope(
                                        fingerprintSettingsPreference.fragment),
                                null,
                                null,
                                new C00251(fingerprintSettingsPreference, null),
                                3);
                        return true;
                    }
                });
    }

    public final Object askUserToDeleteDialog(Continuation continuation) {
        final int i = 1;
        FingerprintData fingerprintData = this.fingerprintViewModel;
        boolean z = this.isLastFingerprint;
        FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment = this.fragment;
        final CancellableContinuationImpl cancellableContinuationImpl =
                new CancellableContinuationImpl(
                        1, IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
        cancellableContinuationImpl.initCancellability();
        final FingerprintDeletionDialog fingerprintDeletionDialog = new FingerprintDeletionDialog();
        final int i2 = 0;
        fingerprintDeletionDialog.onClickListener =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintDeletionDialog$Companion$showInstance$2$1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        switch (i2) {
                            case 0:
                                cancellableContinuationImpl.resumeWith(Boolean.TRUE);
                                break;
                            default:
                                cancellableContinuationImpl.resumeWith(Boolean.FALSE);
                                break;
                        }
                    }
                };
        fingerprintDeletionDialog.onNegativeClickListener =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintDeletionDialog$Companion$showInstance$2$1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        switch (i) {
                            case 0:
                                cancellableContinuationImpl.resumeWith(Boolean.TRUE);
                                break;
                            default:
                                cancellableContinuationImpl.resumeWith(Boolean.FALSE);
                                break;
                        }
                    }
                };
        fingerprintDeletionDialog.onCancelListener =
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintDeletionDialog$Companion$showInstance$2$3
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        cancellableContinuationImpl.resumeWith(Boolean.FALSE);
                    }
                };
        cancellableContinuationImpl.invokeOnCancellation(
                new Function1() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintDeletionDialog$Companion$showInstance$2$4
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        FingerprintDeletionDialog.this.dismissInternal(false, false);
                        return Unit.INSTANCE;
                    }
                });
        Bundle bundle = new Bundle();
        bundle.putObject(
                "fingerprint",
                new Fingerprint(
                        fingerprintData.name, fingerprintData.fingerId, fingerprintData.deviceId));
        bundle.putBoolean("IS_LAST_FINGERPRINT", z);
        fingerprintDeletionDialog.setArguments(bundle);
        fingerprintDeletionDialog.show(
                fingerprintSettingsV2Fragment.getParentFragmentManager(),
                FingerprintDeletionDialog.class.toString());
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return result;
    }

    public final Object askUserToRenameDialog(Continuation continuation) {
        final FingerprintData fingerprintData = this.fingerprintViewModel;
        FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment = this.fragment;
        final CancellableContinuationImpl cancellableContinuationImpl =
                new CancellableContinuationImpl(
                        1, IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
        cancellableContinuationImpl.initCancellability();
        final FingerprintSettingsRenameDialog fingerprintSettingsRenameDialog =
                new FingerprintSettingsRenameDialog();
        fingerprintSettingsRenameDialog.onClickListener =
                new DialogInterface.OnClickListener() { // from class:
                    // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsRenameDialog$Companion$showInstance$2$onClick$1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ImeAwareEditText requireViewById =
                                FingerprintSettingsRenameDialog.this
                                        .requireDialog()
                                        .requireViewById(R.id.fingerprint_rename_field);
                        Intrinsics.checkNotNull(
                                requireViewById,
                                "null cannot be cast to non-null type"
                                    + " android.widget.ImeAwareEditText");
                        String obj = requireViewById.getText().toString();
                        if (TextUtils.equals(obj, fingerprintData.name)) {
                            cancellableContinuationImpl.resumeWith(null);
                            return;
                        }
                        Log.d(
                                "FingerprintSettingsRenameDialog",
                                "rename "
                                        + fingerprintData
                                        + ".name to "
                                        + obj
                                        + " for "
                                        + FingerprintSettingsRenameDialog.this);
                        cancellableContinuationImpl.resumeWith(new Pair(fingerprintData, obj));
                    }
                };
        fingerprintSettingsRenameDialog.onCancelListener =
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsRenameDialog$Companion$showInstance$2$1
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        Log.d(
                                "FingerprintSettingsRenameDialog",
                                "onCancelListener clicked " + FingerprintSettingsRenameDialog.this);
                        cancellableContinuationImpl.resumeWith(null);
                    }
                };
        cancellableContinuationImpl.invokeOnCancellation(
                new Function1() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsRenameDialog$Companion$showInstance$2$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Log.d(
                                "FingerprintSettingsRenameDialog",
                                "invokeOnCancellation " + FingerprintSettingsRenameDialog.this);
                        FingerprintSettingsRenameDialog.this.dismissInternal(false, false);
                        return Unit.INSTANCE;
                    }
                });
        Bundle bundle = new Bundle();
        bundle.putObject(
                "fingerprint",
                new Fingerprint(
                        fingerprintData.name, fingerprintData.fingerId, fingerprintData.deviceId));
        fingerprintSettingsRenameDialog.setArguments(bundle);
        Log.d(
                "FingerprintSettingsRenameDialog",
                "showing dialog " + fingerprintSettingsRenameDialog);
        fingerprintSettingsRenameDialog.show(
                fingerprintSettingsV2Fragment.getParentFragmentManager(),
                FingerprintSettingsRenameDialog.class.toString());
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return result;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return R.layout.preference_widget_delete;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object highlight(kotlin.coroutines.Continuation r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference$highlight$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference$highlight$1 r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference$highlight$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference$highlight$1 r0 = new com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference$highlight$1
            r0.<init>(r8, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            java.lang.String r4 = "myView"
            r5 = 0
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r8 = r0.L$0
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference r8 = (com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference) r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L86
        L2e:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L36:
            kotlin.ResultKt.throwOnFailure(r9)
            com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment r9 = r8.fragment
            androidx.fragment.app.FragmentActivity r9 = r9.getActivity()
            if (r9 == 0) goto La6
            r2 = 2131232710(0x7f0807c6, float:1.8081537E38)
            android.graphics.drawable.Drawable r9 = r9.getDrawable(r2)
            if (r9 == 0) goto La6
            android.view.View r2 = r8.myView
            if (r2 == 0) goto La2
            int r2 = r2.getWidth()
            float r2 = (float) r2
            r6 = 1073741824(0x40000000, float:2.0)
            float r2 = r2 / r6
            android.view.View r7 = r8.myView
            if (r7 == 0) goto L9e
            int r7 = r7.getHeight()
            float r7 = (float) r7
            float r7 = r7 / r6
            r9.setHotspot(r2, r7)
            android.view.View r2 = r8.myView
            if (r2 == 0) goto L9a
            r2.setBackground(r9)
            android.view.View r9 = r8.myView
            if (r9 == 0) goto L96
            r9.setPressed(r3)
            android.view.View r9 = r8.myView
            if (r9 == 0) goto L92
            r2 = 0
            r9.setPressed(r2)
            r0.L$0 = r8
            r0.label = r3
            r2 = 300(0x12c, double:1.48E-321)
            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r2, r0)
            if (r9 != r1) goto L86
            return r1
        L86:
            android.view.View r8 = r8.myView
            if (r8 == 0) goto L8e
            r8.setBackground(r5)
            goto La6
        L8e:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r5
        L92:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r5
        L96:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r5
        L9a:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r5
        L9e:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r5
        La2:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            throw r5
        La6:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference.highlight(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View itemView = preferenceViewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        this.myView = itemView;
        View findViewById = preferenceViewHolder.itemView.findViewById(R.id.delete_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(
                    new View.OnClickListener() { // from class:
                        // com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference$onBindViewHolder$1

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
                        /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsPreference$onBindViewHolder$1$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            int label;
                            final /* synthetic */ FingerprintSettingsPreference this$0;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(
                                    FingerprintSettingsPreference fingerprintSettingsPreference,
                                    Continuation continuation) {
                                super(2, continuation);
                                this.this$0 = fingerprintSettingsPreference;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(
                                    Object obj, Continuation continuation) {
                                return new AnonymousClass1(this.this$0, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                AnonymousClass1 anonymousClass1 =
                                        (AnonymousClass1)
                                                create((CoroutineScope) obj, (Continuation) obj2);
                                Unit unit = Unit.INSTANCE;
                                anonymousClass1.invokeSuspend(unit);
                                return unit;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons =
                                        CoroutineSingletons.COROUTINE_SUSPENDED;
                                if (this.label != 0) {
                                    throw new IllegalStateException(
                                            "call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                                FingerprintSettingsPreference fingerprintSettingsPreference =
                                        this.this$0;
                                FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment =
                                        fingerprintSettingsPreference.fragment;
                                FingerprintData fingerprintViewModel =
                                        fingerprintSettingsPreference.fingerprintViewModel;
                                fingerprintSettingsV2Fragment.getClass();
                                Intrinsics.checkNotNullParameter(
                                        fingerprintViewModel, "fingerprintViewModel");
                                StringBuilder sb = new StringBuilder("onDeletePrefClicked(");
                                sb.append(fingerprintViewModel);
                                MainClearConfirm$$ExternalSyntheticOutline0.m(
                                        sb, ")", "FingerprintSettingsV2Fragment");
                                FingerprintSettingsViewModel fingerprintSettingsViewModel =
                                        fingerprintSettingsV2Fragment.settingsViewModel;
                                if (fingerprintSettingsViewModel != null) {
                                    fingerprintSettingsViewModel.onDeleteClicked(
                                            fingerprintViewModel);
                                    return Unit.INSTANCE;
                                }
                                Intrinsics.throwUninitializedPropertyAccessException(
                                        "settingsViewModel");
                                throw null;
                            }
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            BuildersKt.launch$default(
                                    LifecycleOwnerKt.getLifecycleScope(
                                            FingerprintSettingsPreference.this.fragment),
                                    null,
                                    null,
                                    new AnonymousClass1(FingerprintSettingsPreference.this, null),
                                    3);
                        }
                    });
        }
    }
}
