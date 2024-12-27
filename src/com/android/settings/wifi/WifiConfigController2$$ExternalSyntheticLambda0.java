package com.android.settings.wifi;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiConfigController2$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        String str = (String) obj;
        for (String str2 : WifiConfigController2.UNDESIRED_CERTIFICATES) {
            if (str.startsWith(str2)) {
                return false;
            }
        }
        return true;
    }
}
