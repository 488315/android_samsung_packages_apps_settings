package com.samsung.android.settings.accessibility.home;

import android.content.Context;
import android.icu.text.CaseMap;

import com.android.settings.R;
import com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment;
import com.android.settings.utils.LocaleUtils;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.ArrayList;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TalkBackPreferenceFragment extends ToggleAccessibilityServicePreferenceFragment {
    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getExclusiveTaskName() {
        return "talkback_se";
    }

    @Override // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment,
              // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2000;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final CharSequence getShortcutTypeSummary(Context context) {
        if (!SecAccessibilityUtils.needTalkbackDefaultShortcut(context, this.mComponentName)) {
            return super.getShortcutTypeSummary(context);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(context.getText(R.string.accessibility_shortcut_summary_direct));
        arrayList.add(context.getText(R.string.accessibility_shortcut_summary_hardware));
        this.mShortcutPreference.setChecked(true);
        return CaseMap.toTitle()
                .wholeString()
                .noLowercase()
                .apply(Locale.getDefault(), null, LocaleUtils.getConcatenatedString(arrayList));
    }
}
