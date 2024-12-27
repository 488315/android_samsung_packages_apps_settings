package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0014\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "Lcom/android/settings/biometrics/fingerprint2/lib/model/FingerprintAuthAttemptModel;",
            "it",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class FingerprintSettingsViewModel$authFlow$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    Object L$1;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ FingerprintSettingsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsViewModel$authFlow$1(
            FingerprintSettingsViewModel fingerprintSettingsViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = fingerprintSettingsViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        FingerprintSettingsViewModel$authFlow$1 fingerprintSettingsViewModel$authFlow$1 =
                new FingerprintSettingsViewModel$authFlow$1(this.this$0, (Continuation) obj3);
        fingerprintSettingsViewModel$authFlow$1.L$0 = (FlowCollector) obj;
        fingerprintSettingsViewModel$authFlow$1.Z$0 = booleanValue;
        return fingerprintSettingsViewModel$authFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00b0 A[Catch: Exception -> 0x001e, TryCatch #0 {Exception -> 0x001e, blocks: (B:8:0x0019, B:14:0x002f, B:16:0x006a, B:18:0x007a, B:19:0x0083, B:22:0x0099, B:25:0x00aa, B:27:0x00b0, B:30:0x00c4, B:33:0x00d3, B:35:0x00d7, B:37:0x00df, B:38:0x00e6, B:41:0x00f3, B:48:0x003d, B:51:0x004a, B:53:0x0057), top: B:2:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00d3 A[Catch: Exception -> 0x001e, TryCatch #0 {Exception -> 0x001e, blocks: (B:8:0x0019, B:14:0x002f, B:16:0x006a, B:18:0x007a, B:19:0x0083, B:22:0x0099, B:25:0x00aa, B:27:0x00b0, B:30:0x00c4, B:33:0x00d3, B:35:0x00d7, B:37:0x00df, B:38:0x00e6, B:41:0x00f3, B:48:0x003d, B:51:0x004a, B:53:0x0057), top: B:2:0x000f }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x00d0 -> B:15:0x0068). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x00d5 -> B:15:0x0068). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x00dd -> B:15:0x0068). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$authFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
