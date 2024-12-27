package com.android.settingslib.license;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class LicenseHtmlGeneratorFromXml$$ExternalSyntheticLambda2
        implements BiFunction {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ LicenseHtmlGeneratorFromXml$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                Map map = (Map) obj;
                for (Map.Entry entry : ((Map) obj2).entrySet()) {
                    map.merge(
                            (String) entry.getKey(),
                            (Set) entry.getValue(),
                            new LicenseHtmlGeneratorFromXml$$ExternalSyntheticLambda2(3));
                }
                return map;
            case 1:
                return Integer.valueOf(
                        Integer.sum(((Integer) obj).intValue(), ((Integer) obj2).intValue()));
            case 2:
                Set set = (Set) obj;
                set.addAll((Set) obj2);
                return set;
            default:
                Set set2 = (Set) obj;
                set2.addAll((Set) obj2);
                return set2;
        }
    }
}
