package com.android.settings.network.telephony.scan;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.channels.ProducerScope;

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
            "Lkotlinx/coroutines/channels/ProducerScope;",
            "Lcom/android/settings/network/telephony/scan/NetworkScanRepository$NetworkScanResult;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class NetworkScanRepository$networkScanFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NetworkScanRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkScanRepository$networkScanFlow$1(
            NetworkScanRepository networkScanRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = networkScanRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NetworkScanRepository$networkScanFlow$1 networkScanRepository$networkScanFlow$1 =
                new NetworkScanRepository$networkScanFlow$1(this.this$0, continuation);
        networkScanRepository$networkScanFlow$1.L$0 = obj;
        return networkScanRepository$networkScanFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NetworkScanRepository$networkScanFlow$1)
                        create((ProducerScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00a4, code lost:

       if (kotlin.collections.ArraysKt___ArraysKt.contains(2, r7) != false) goto L28;
    */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00e1 A[LOOP:0: B:28:0x00d8->B:30:0x00e1, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00f5 A[EDGE_INSN: B:31:0x00f5->B:32:0x00f5 BREAK  A[LOOP:0: B:28:0x00d8->B:30:0x00e1], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x012d A[RETURN] */
    /* JADX WARN: Type inference failed for: r5v0, types: [T, com.android.settings.network.telephony.scan.NetworkScanRepository$NetworkScanState] */
    /* JADX WARN: Type inference failed for: r6v0, types: [T, kotlin.collections.EmptyList] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            Method dump skipped, instructions count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.scan.NetworkScanRepository$networkScanFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
