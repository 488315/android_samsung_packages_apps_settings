package com.samsung.android.sdk.scs.ai.asr;

import android.content.Context;

import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;

import java.util.ArrayList;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class Environment$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(((ArrayList) obj).size() > 0);
            case 1:
                return Boolean.valueOf(((ArrayList) obj).size() > 0);
            case 2:
                return Boolean.valueOf(((ArrayList) obj).size() > 0);
            case 3:
                return Boolean.valueOf(((String) obj).length() > 0);
            case 4:
                return ((Context) obj).getContentResolver();
            default:
                return Integer.valueOf(
                        ((Context) obj)
                                .checkSelfPermission(
                                        SpeechRecognitionConst.SYSTEM_PERMISSION_QUERY_CP));
        }
    }
}
