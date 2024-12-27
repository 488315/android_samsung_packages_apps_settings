package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.content.Context;
import android.service.notification.ZenPolicy;
import android.util.Pair;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleCustomSwitchPreferenceController
        extends AbstractZenCustomRulePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public final int mCategory;
    public final int mMetricsCategory;

    public ZenRuleCustomSwitchPreferenceController(
            Context context, Lifecycle lifecycle, String str, int i, int i2) {
        super(context, str, lifecycle);
        this.mCategory = i;
        this.mMetricsCategory = i2;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean z = ZenModeSettingsBase.DEBUG;
        int i = this.mCategory;
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.KEY);
            sb.append(" onPrefChange mRule=");
            sb.append(this.mRule);
            sb.append(" mCategory=");
            sb.append(i);
            sb.append(" allow=");
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    sb, booleanValue, "PrefControllerMixin");
        }
        this.mMetricsFeatureProvider.action(
                this.mContext,
                this.mMetricsCategory,
                Pair.create(1602, Integer.valueOf(booleanValue ? 1 : 0)),
                Pair.create(1603, this.mId));
        this.mRule.setZenPolicy(
                new ZenPolicy.Builder(this.mRule.getZenPolicy())
                        .allowCategory(i, booleanValue)
                        .build());
        this.mBackend.updateZenRule(this.mId, this.mRule);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        AutomaticZenRule automaticZenRule = this.mRule;
        if (automaticZenRule == null || automaticZenRule.getZenPolicy() == null) {
            return;
        }
        ((TwoStatePreference) preference)
                .setChecked(this.mRule.getZenPolicy().isCategoryAllowed(this.mCategory, false));
    }
}
