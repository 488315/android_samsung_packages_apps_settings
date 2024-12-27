package com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage;

import android.widget.ProgressBar;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0010\b\u0001\u0010\u0003*\n"
                + "\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0004*\u00020\u0005H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            ImsProfile.TIMER_NAME_E,
            "T",
            "Lcom/samsung/android/settings/analyzestorage/presenter/controllers/AbsPageController;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes4.dex */
final class AnalyzeStorageSubList$observeLoadingState$1$1 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ AnalyzeStorageSubList this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnalyzeStorageSubList$observeLoadingState$1$1(
            AnalyzeStorageSubList analyzeStorageSubList, Continuation continuation) {
        super(2, continuation);
        this.this$0 = analyzeStorageSubList;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AnalyzeStorageSubList$observeLoadingState$1$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AnalyzeStorageSubList$observeLoadingState$1$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(1000L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        ProgressBar progressBar = this.this$0.progressBar;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
        return Unit.INSTANCE;
    }
}
