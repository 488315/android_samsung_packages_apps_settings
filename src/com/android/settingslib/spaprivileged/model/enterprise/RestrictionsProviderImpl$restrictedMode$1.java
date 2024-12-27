package com.android.settingslib.spaprivileged.model.enterprise;

import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.flow.FlowCollector;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "Lcom/android/settingslib/spaprivileged/model/enterprise/RestrictedMode;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class RestrictionsProviderImpl$restrictedMode$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ RestrictionsProviderImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RestrictionsProviderImpl$restrictedMode$1(
            RestrictionsProviderImpl restrictionsProviderImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = restrictionsProviderImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RestrictionsProviderImpl$restrictedMode$1 restrictionsProviderImpl$restrictedMode$1 =
                new RestrictionsProviderImpl$restrictedMode$1(this.this$0, continuation);
        restrictionsProviderImpl$restrictedMode$1.L$0 = obj;
        return restrictionsProviderImpl$restrictedMode$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RestrictionsProviderImpl$restrictedMode$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object blockedByEcmImpl;
        Intent checkIfRequiresEnhancedConfirmation;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            RestrictionsProviderImpl restrictionsProviderImpl = this.this$0;
            Restrictions restrictions = restrictionsProviderImpl.restrictions;
            Iterator it = restrictions.keys.iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                int i2 = restrictions.userId;
                if (!hasNext) {
                    Iterator it2 = restrictions.keys.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                                    RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                            restrictionsProviderImpl.context,
                                            i2,
                                            (String) it2.next());
                            if (checkIfRestrictionEnforced != null) {
                                blockedByEcmImpl =
                                        new BlockedByAdminImpl(
                                                restrictionsProviderImpl.context,
                                                checkIfRestrictionEnforced);
                                break;
                            }
                        } else {
                            EnhancedConfirmation enhancedConfirmation =
                                    restrictions.enhancedConfirmation;
                            blockedByEcmImpl =
                                    (enhancedConfirmation == null
                                                    || (checkIfRequiresEnhancedConfirmation =
                                                                    RestrictedLockUtilsInternal
                                                                            .checkIfRequiresEnhancedConfirmation(
                                                                                    restrictionsProviderImpl
                                                                                            .context,
                                                                                    enhancedConfirmation
                                                                                            .key,
                                                                                    enhancedConfirmation
                                                                                            .packageName))
                                                            == null)
                                            ? NoRestricted.INSTANCE
                                            : new BlockedByEcmImpl(
                                                    restrictionsProviderImpl.context,
                                                    checkIfRequiresEnhancedConfirmation);
                        }
                    }
                } else if (((UserManager) restrictionsProviderImpl.userManager$delegate.getValue())
                        .hasBaseUserRestriction((String) it.next(), UserHandle.of(i2))) {
                    blockedByEcmImpl = BaseUserRestricted.INSTANCE;
                    break;
                }
            }
            this.label = 1;
            if (flowCollector.emit(blockedByEcmImpl, this) == coroutineSingletons) {
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
