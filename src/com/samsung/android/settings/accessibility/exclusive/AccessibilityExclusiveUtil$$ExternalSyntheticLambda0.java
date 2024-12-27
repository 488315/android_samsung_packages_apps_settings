package com.samsung.android.settings.accessibility.exclusive;

import android.content.ComponentName;

import com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class AccessibilityExclusiveUtil$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AccessibilityExclusiveUtil$$ExternalSyntheticLambda0(
            ComponentName componentName) {
        this.$r8$classId = 3;
        this.f$0 = componentName;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ExclusiveTaskInfo exclusiveTaskInfo = (ExclusiveTaskInfo) obj;
                return exclusiveTaskInfo.getTaskName().equals((String) obj2)
                        && exclusiveTaskInfo.getControllerName() != null;
            case 1:
                ExclusiveTaskInfo exclusiveTaskInfo2 = (ExclusiveTaskInfo) obj;
                return exclusiveTaskInfo2.getTaskName().equals((String) obj2)
                        && exclusiveTaskInfo2.getPreferenceKey() != null;
            case 2:
                return ((ExclusiveTaskInfo) obj).getTaskName().equals((String) obj2);
            default:
                ExclusiveTaskInfo exclusiveTaskInfo3 = (ExclusiveTaskInfo) obj;
                return exclusiveTaskInfo3.getComponentName() != null
                        && exclusiveTaskInfo3.getComponentName().equals((ComponentName) obj2);
        }
    }

    public /* synthetic */ AccessibilityExclusiveUtil$$ExternalSyntheticLambda0(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }
}
