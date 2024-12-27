package com.samsung.android.settings.eternal.service;

import com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper;
import com.samsung.android.scloud.oem.lib.utils.FileTool;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class EternalService$$ExternalSyntheticLambda3
        implements FileTool.PDMProgressListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531 f$0;

    public /* synthetic */ EternalService$$ExternalSyntheticLambda3(
            QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531 c00531, int i) {
        this.$r8$classId = i;
        this.f$0 = c00531;
    }

    @Override // com.samsung.android.scloud.oem.lib.utils.FileTool.PDMProgressListener
    public final void transferred(long j, long j2) {
        int i = this.$r8$classId;
        this.f$0.onProgress(j, j2);
    }
}
