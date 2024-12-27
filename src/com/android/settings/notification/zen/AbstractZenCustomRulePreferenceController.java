package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractZenCustomRulePreferenceController
        extends AbstractZenModePreferenceController {
    public String mId;
    public AutomaticZenRule mRule;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        return this.mRule != null;
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {}
}
