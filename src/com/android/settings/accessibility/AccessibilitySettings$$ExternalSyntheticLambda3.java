package com.android.settings.accessibility;

import com.android.settingslib.core.AbstractPreferenceController;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AccessibilitySettings$$ExternalSyntheticLambda3
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AccessibilitySettings$$ExternalSyntheticLambda3(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((List) obj2).addAll((List) obj);
                break;
            default:
                AccessibilitySettings accessibilitySettings = (AccessibilitySettings) obj2;
                AbstractPreferenceController abstractPreferenceController =
                        (AbstractPreferenceController) obj;
                String[] strArr = AccessibilitySettings.CATEGORIES;
                accessibilitySettings.getClass();
                abstractPreferenceController.updateState(
                        accessibilitySettings.findPreference(
                                abstractPreferenceController.getPreferenceKey()));
                break;
        }
    }
}
