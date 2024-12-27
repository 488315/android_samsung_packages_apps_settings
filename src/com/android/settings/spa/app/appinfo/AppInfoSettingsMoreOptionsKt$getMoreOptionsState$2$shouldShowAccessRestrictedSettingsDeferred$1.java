package com.android.settings.spa.app.appinfo;

import android.app.ecm.EnhancedConfirmationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final
class AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2$shouldShowAccessRestrictedSettingsDeferred$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ ApplicationInfo $this_getMoreOptionsState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public
    AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2$shouldShowAccessRestrictedSettingsDeferred$1(
            Context context, ApplicationInfo applicationInfo, Continuation continuation) {
        super(2, continuation);
        this.$this_getMoreOptionsState = applicationInfo;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2$shouldShowAccessRestrictedSettingsDeferred$1(
                this.$context, this.$this_getMoreOptionsState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2$shouldShowAccessRestrictedSettingsDeferred$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ApplicationInfo applicationInfo = this.$this_getMoreOptionsState;
        Context context = this.$context;
        boolean z = false;
        if (Flags.enhancedConfirmationModeApisEnabled()
                && android.security.Flags.extendEcmToAllSettings()) {
            Object systemService =
                    context.getSystemService((Class<Object>) EnhancedConfirmationManager.class);
            Intrinsics.checkNotNull(systemService);
            try {
                z =
                        ((EnhancedConfirmationManager) systemService)
                                .isClearRestrictionAllowed(applicationInfo.packageName);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        } else if (ContextsKt.getAppOpsManager(context)
                        .noteOpNoThrow(
                                119,
                                applicationInfo.uid,
                                applicationInfo.packageName,
                                (String) null,
                                (String) null)
                == 1) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}