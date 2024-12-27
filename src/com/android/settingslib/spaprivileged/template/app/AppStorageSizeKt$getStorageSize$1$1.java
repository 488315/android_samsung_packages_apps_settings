package com.android.settingslib.spaprivileged.template.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.format.Formatter;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\u0010\u0000\u001a\u00020\u0001*\u0010\u0012\f\u0012\n"
                + " \u0004*\u0004\u0018\u00010\u00030\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE,
            "kotlin.jvm.PlatformType"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppStorageSizeKt$getStorageSize$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ ApplicationInfo $this_getStorageSize;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppStorageSizeKt$getStorageSize$1$1(
            Context context, ApplicationInfo applicationInfo, Continuation continuation) {
        super(2, continuation);
        this.$this_getStorageSize = applicationInfo;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppStorageSizeKt$getStorageSize$1$1 appStorageSizeKt$getStorageSize$1$1 =
                new AppStorageSizeKt$getStorageSize$1$1(
                        this.$context, this.$this_getStorageSize, continuation);
        appStorageSizeKt$getStorageSize$1$1.L$0 = obj;
        return appStorageSizeKt$getStorageSize$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppStorageSizeKt$getStorageSize$1$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Long calculateSizeBytes =
                    AppStorageSizeKt.calculateSizeBytes(this.$context, this.$this_getStorageSize);
            String formatFileSize =
                    calculateSizeBytes != null
                            ? Formatter.formatFileSize(
                                    this.$context, calculateSizeBytes.longValue())
                            : ApnSettings.MVNO_NONE;
            this.label = 1;
            if (flowCollector.emit(formatFileSize, this) == coroutineSingletons) {
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
