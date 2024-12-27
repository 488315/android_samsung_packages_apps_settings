package com.samsung.android.settings.accessibility.advanced.flashnotification;

import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class SecCameraFlashNotificationPreferenceController$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecCameraFlashNotificationPreferenceController$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean lambda$getSummary$1;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                lambda$getSummary$1 =
                        ((SecCameraFlashNotificationPreferenceController) obj2)
                                .lambda$getSummary$1((String) obj);
                return lambda$getSummary$1;
            default:
                return ((Set) obj2).contains((String) obj);
        }
    }
}
