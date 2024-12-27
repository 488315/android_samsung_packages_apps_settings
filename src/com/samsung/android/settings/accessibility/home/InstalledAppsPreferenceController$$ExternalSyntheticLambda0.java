package com.samsung.android.settings.accessibility.home;

import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;

import com.android.settingslib.RestrictedPreference;

import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class InstalledAppsPreferenceController$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ InstalledAppsPreferenceController$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean lambda$getSummary$0;
        switch (this.$r8$classId) {
            case 0:
                lambda$getSummary$0 =
                        InstalledAppsPreferenceController.lambda$getSummary$0(
                                (RestrictedPreference) obj);
                return lambda$getSummary$0;
            case 1:
                return Objects.nonNull((ResolveInfo) obj);
            default:
                return Objects.nonNull((ServiceInfo) obj);
        }
    }
}
