package com.android.settings.network;

import android.net.TetheringInterface;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u001a\n"
                + "\u0000\n"
                + "\u0002\u0010#\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\"\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "tetheringInterfaces",
            ApnSettings.MVNO_NONE,
            "Landroid/net/TetheringInterface;",
            "isBluetoothTetheringOn",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class TetheredRepository$tetheredTypesFlow$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        TetheredRepository$tetheredTypesFlow$1 tetheredRepository$tetheredTypesFlow$1 =
                new TetheredRepository$tetheredTypesFlow$1(3, (Continuation) obj3);
        tetheredRepository$tetheredTypesFlow$1.L$0 = (Set) obj;
        tetheredRepository$tetheredTypesFlow$1.Z$0 = booleanValue;
        return tetheredRepository$tetheredTypesFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set set = (Set) this.L$0;
        boolean z = this.Z$0;
        Set set2 = set;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            arrayList.add(new Integer(((TetheringInterface) it.next()).getType()));
        }
        Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(arrayList);
        if (z) {
            mutableSet.add(new Integer(2));
        }
        return mutableSet;
    }
}
