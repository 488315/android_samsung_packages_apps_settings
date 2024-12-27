package com.android.settings.datausage;

import android.content.Intent;
import android.content.pm.PackageManager;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", "Landroid/content/Intent;", "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class AppDataUsageAppSettingsController$update$2 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ AppDataUsageAppSettingsController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsageAppSettingsController$update$2(
            AppDataUsageAppSettingsController appDataUsageAppSettingsController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appDataUsageAppSettingsController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppDataUsageAppSettingsController$update$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppDataUsageAppSettingsController$update$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Iterable<String> iterable;
        PackageManager packageManager;
        int i;
        AppDataUsageAppSettingsController.Companion companion;
        Intent intent;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        iterable = this.this$0.packageNames;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        for (String str : iterable) {
            companion = AppDataUsageAppSettingsController.Companion;
            companion.getClass();
            intent = AppDataUsageAppSettingsController.SettingsIntent;
            arrayList.add(new Intent(intent).setPackage(str));
        }
        AppDataUsageAppSettingsController appDataUsageAppSettingsController = this.this$0;
        for (Object obj2 : arrayList) {
            packageManager = appDataUsageAppSettingsController.packageManager;
            i = appDataUsageAppSettingsController.userId;
            if (packageManager.resolveActivityAsUser((Intent) obj2, 0, i) != null) {
                return obj2;
            }
        }
        return null;
    }
}
