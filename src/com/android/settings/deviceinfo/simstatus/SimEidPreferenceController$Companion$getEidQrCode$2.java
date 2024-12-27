package com.android.settings.deviceinfo.simstatus;

import android.util.Log;

import com.android.settingslib.qrcode.QrCodeGenerator;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class SimEidPreferenceController$Companion$getEidQrCode$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ String $eid;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimEidPreferenceController$Companion$getEidQrCode$2(
            String str, Continuation continuation) {
        super(2, continuation);
        this.$eid = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimEidPreferenceController$Companion$getEidQrCode$2(this.$eid, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimEidPreferenceController$Companion$getEidQrCode$2)
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
        try {
            return QrCodeGenerator.encodeQrCode$default(600, this.$eid);
        } catch (Exception e) {
            Log.w("SimEidPreferenceController", "Error when creating QR code width 600", e);
            return null;
        }
    }
}
