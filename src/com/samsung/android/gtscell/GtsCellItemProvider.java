package com.samsung.android.gtscell;

import com.samsung.android.gtscell.data.GtsConfiguration;
import com.samsung.android.gtscell.data.GtsItem;
import com.samsung.android.gtscell.data.GtsItemSupplierGroup;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u00006\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J(\u0010\b\u001a\u00020"
                + "\t2\u0006\u0010\n"
                + "\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r"
                + "\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&¨\u0006\u0011"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/GtsCellItemProvider;",
            ApnSettings.MVNO_NONE,
            "getGtsItemGroups",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/gtscell/data/GtsItemSupplierGroup;",
            "category",
            ApnSettings.MVNO_NONE,
            "getPrivateCategory",
            "setGtsItem",
            ApnSettings.MVNO_NONE,
            "fromCategory",
            "fromItem",
            "Lcom/samsung/android/gtscell/data/GtsItem;",
            "fromConfiguration",
            "Lcom/samsung/android/gtscell/data/GtsConfiguration;",
            "resultCallback",
            "Lcom/samsung/android/gtscell/ResultCallback;",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public interface GtsCellItemProvider {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            k = 3,
            mv = {1, 1, 16})
    public static final class DefaultImpls {
        public static String getPrivateCategory(
                GtsCellItemProvider gtsCellItemProvider, String category) {
            Intrinsics.checkParameterIsNotNull(category, "category");
            return null;
        }
    }

    List<GtsItemSupplierGroup> getGtsItemGroups(String category);

    String getPrivateCategory(String category);

    void setGtsItem(
            String fromCategory,
            GtsItem fromItem,
            GtsConfiguration fromConfiguration,
            ResultCallback resultCallback);
}
