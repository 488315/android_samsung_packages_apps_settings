package com.samsung.android.settings.privacy;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecurityAndPrivacySettings$1$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        AbstractPreferenceController abstractPreferenceController =
                (AbstractPreferenceController) obj;
        return (abstractPreferenceController instanceof BasePreferenceController)
                && "privacy_chart_overview".equals(abstractPreferenceController.getPreferenceKey());
    }
}
