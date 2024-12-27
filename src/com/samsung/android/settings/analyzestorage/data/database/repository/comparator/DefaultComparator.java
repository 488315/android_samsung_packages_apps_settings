package com.samsung.android.settings.analyzestorage.data.database.repository.comparator;

import com.samsung.android.settings.analyzestorage.domain.entity.DataInfo;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.io.Serializable;
import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0018\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u00032\u00020\u0004B\u0007¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\u0007"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/data/database/repository/comparator/DefaultComparator;",
            "Ljava/util/Comparator;",
            "Lcom/samsung/android/settings/analyzestorage/domain/entity/DataInfo;",
            "Lkotlin/Comparator;",
            "Ljava/io/Serializable;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class DefaultComparator implements Comparator<DataInfo>, Serializable {
    private static final long serialVersionUID = 1;

    @Override // java.util.Comparator
    public final int compare(DataInfo dataInfo, DataInfo dataInfo2) {
        DataInfo o1 = dataInfo;
        DataInfo o2 = dataInfo2;
        Intrinsics.checkNotNullParameter(o1, "o1");
        Intrinsics.checkNotNullParameter(o2, "o2");
        return -1;
    }
}
