package com.android.settingslib.spa.framework.compose;

import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.platform.DelegatingSoftwareKeyboardController;
import androidx.compose.ui.platform.SoftwareKeyboardController;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

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
final class KeyboardsKt$rememberLazyListStateAndHideKeyboardWhenStartScroll$1$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ SoftwareKeyboardController $keyboardController;
    final /* synthetic */ LazyListState $listState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardsKt$rememberLazyListStateAndHideKeyboardWhenStartScroll$1$1(
            LazyListState lazyListState,
            SoftwareKeyboardController softwareKeyboardController,
            Continuation continuation) {
        super(2, continuation);
        this.$listState = lazyListState;
        this.$keyboardController = softwareKeyboardController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyboardsKt$rememberLazyListStateAndHideKeyboardWhenStartScroll$1$1(
                this.$listState, this.$keyboardController, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardsKt$rememberLazyListStateAndHideKeyboardWhenStartScroll$1$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        Unit unit = Unit.INSTANCE;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final LazyListState lazyListState = this.$listState;
            Flow distinctUntilChanged =
                    FlowKt.distinctUntilChanged(
                            SnapshotStateKt.snapshotFlow(
                                    new Function0() { // from class:
                                                      // com.android.settingslib.spa.framework.compose.KeyboardsKt$rememberLazyListStateAndHideKeyboardWhenStartScroll$1$1.1
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return Boolean.valueOf(
                                                    LazyListState.this.isScrollInProgress());
                                        }
                                    }));
            final SoftwareKeyboardController softwareKeyboardController = this.$keyboardController;
            FlowCollector flowCollector =
                    new FlowCollector() { // from class:
                                          // com.android.settingslib.spa.framework.compose.KeyboardsKt$rememberLazyListStateAndHideKeyboardWhenStartScroll$1$1.3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).booleanValue();
                            SoftwareKeyboardController softwareKeyboardController2 =
                                    SoftwareKeyboardController.this;
                            if (softwareKeyboardController2 != null) {
                                ((DelegatingSoftwareKeyboardController) softwareKeyboardController2)
                                        .hide();
                            }
                            return Unit.INSTANCE;
                        }
                    };
            this.label = 1;
            Object collect =
                    distinctUntilChanged.collect(
                            new KeyboardsKt$rememberLazyListStateAndHideKeyboardWhenStartScroll$1$1$invokeSuspend$$inlined$filter$1$2(
                                    flowCollector),
                            this);
            if (collect != coroutineSingletons) {
                collect = unit;
            }
            if (collect == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return unit;
    }
}
