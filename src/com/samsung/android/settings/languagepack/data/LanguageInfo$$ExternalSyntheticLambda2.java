package com.samsung.android.settings.languagepack.data;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguageInfo$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        PackageInfo packageInfo = (PackageInfo) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = packageInfo.mType;
                if (i == 1 || i == 2 || i == 4 || i == 16) {}
                break;
            case 1:
                if ((packageInfo.mType & 2) == 2) {}
                break;
            case 2:
                if ((packageInfo.mType & 4) == 4) {}
                break;
            case 3:
                if ((packageInfo.mType & 1) == 1) {}
                break;
            case 4:
                if ((packageInfo.mType & 8) == 8) {}
                break;
            case 5:
                if ((packageInfo.mType & 16) == 16) {}
                break;
            default:
                if (packageInfo.mType == 8) {}
                break;
        }
        return true;
    }
}
