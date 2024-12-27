package com.android.settings.notification.zen;

import android.content.ComponentName;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenRuleInfo {
    public ComponentName configurationActivity;
    public CharSequence packageLabel;
    public String packageName;
    public int ruleInstanceLimit;
    public ComponentName serviceComponent;
    public String settingsAction;
    public String title;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ZenRuleInfo.class != obj.getClass()) {
            return false;
        }
        ZenRuleInfo zenRuleInfo = (ZenRuleInfo) obj;
        if (this.ruleInstanceLimit != zenRuleInfo.ruleInstanceLimit) {
            return false;
        }
        String str = this.packageName;
        if (str == null ? zenRuleInfo.packageName != null : !str.equals(zenRuleInfo.packageName)) {
            return false;
        }
        String str2 = this.title;
        if (str2 == null ? zenRuleInfo.title != null : !str2.equals(zenRuleInfo.title)) {
            return false;
        }
        String str3 = this.settingsAction;
        if (str3 == null
                ? zenRuleInfo.settingsAction != null
                : !str3.equals(zenRuleInfo.settingsAction)) {
            return false;
        }
        ComponentName componentName = this.configurationActivity;
        if (componentName == null
                ? zenRuleInfo.configurationActivity != null
                : !componentName.equals(zenRuleInfo.configurationActivity)) {
            return false;
        }
        ComponentName componentName2 = this.serviceComponent;
        if (componentName2 == null
                ? zenRuleInfo.serviceComponent != null
                : !componentName2.equals(zenRuleInfo.serviceComponent)) {
            return false;
        }
        CharSequence charSequence = this.packageLabel;
        return charSequence != null
                ? charSequence.equals(zenRuleInfo.packageLabel)
                : zenRuleInfo.packageLabel == null;
    }
}
