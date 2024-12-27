package com.samsung.android.settings.analyzestorage.presenter.controllers.filelist;

import com.samsung.android.settings.analyzestorage.domain.entity.DataInfo;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ListItemHandler$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ListItemHandler$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((HashSet) ((ListItemHandler) obj2).mCheckableItemList)
                        .contains((DataInfo) obj);
            default:
                return ((Set) obj2).contains((DataInfo) obj);
        }
    }
}
