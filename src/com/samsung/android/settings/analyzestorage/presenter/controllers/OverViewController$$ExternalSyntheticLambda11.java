package com.samsung.android.settings.analyzestorage.presenter.controllers;

import com.samsung.android.settings.analyzestorage.data.model.OverViewItemInfo;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class OverViewController$$ExternalSyntheticLambda11
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ OverViewController$$ExternalSyntheticLambda11(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((HashMap) ((OverViewController) obj2).mIsLoading).remove((Integer) obj);
                break;
            default:
                ((List) obj2).add(((OverViewItemInfo) obj).mType);
                break;
        }
    }
}
