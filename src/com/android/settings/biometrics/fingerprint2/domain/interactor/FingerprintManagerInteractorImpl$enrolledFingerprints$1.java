package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.content.Context;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;

import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.flow.FlowCollector;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u0010\u0012\f\u0012\n"
                + "\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/biometrics/fingerprint2/lib/model/FingerprintData;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class FingerprintManagerInteractorImpl$enrolledFingerprints$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Context $applicationContext;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FingerprintManagerInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintManagerInteractorImpl$enrolledFingerprints$1(
            FingerprintManagerInteractorImpl fingerprintManagerInteractorImpl,
            Context context,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintManagerInteractorImpl;
        this.$applicationContext = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FingerprintManagerInteractorImpl$enrolledFingerprints$1
                fingerprintManagerInteractorImpl$enrolledFingerprints$1 =
                        new FingerprintManagerInteractorImpl$enrolledFingerprints$1(
                                this.this$0, this.$applicationContext, continuation);
        fingerprintManagerInteractorImpl$enrolledFingerprints$1.L$0 = obj;
        return fingerprintManagerInteractorImpl$enrolledFingerprints$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintManagerInteractorImpl$enrolledFingerprints$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list;
        List enrolledFingerprints;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            FingerprintManager fingerprintManager = this.this$0.fingerprintManager;
            if (fingerprintManager == null
                    || (enrolledFingerprints =
                                    fingerprintManager.getEnrolledFingerprints(
                                            this.$applicationContext.getUserId()))
                            == null) {
                list = null;
            } else {
                List<Fingerprint> list2 = enrolledFingerprints;
                ArrayList arrayList =
                        new ArrayList(
                                CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                for (Fingerprint fingerprint : list2) {
                    arrayList.add(
                            new FingerprintData(
                                    fingerprint.getDeviceId(),
                                    fingerprint.getBiometricId(),
                                    fingerprint.getName().toString()));
                }
                list = CollectionsKt___CollectionsKt.toList(arrayList);
            }
            this.label = 1;
            if (flowCollector.emit(list, this) == coroutineSingletons) {
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
