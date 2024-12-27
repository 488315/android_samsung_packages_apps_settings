package com.samsung.android.settings.share.common;

import android.content.pm.ResolveInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ShareUtil$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new LinkedList((List) obj);
            default:
                return (ResolveInfo) ((Queue) obj).poll();
        }
    }
}
