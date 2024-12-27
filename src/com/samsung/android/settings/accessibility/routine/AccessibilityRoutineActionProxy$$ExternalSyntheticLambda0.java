package com.samsung.android.settings.accessibility.routine;

import android.util.Log;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class AccessibilityRoutineActionProxy$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Object newInstance;
        switch (this.$r8$classId) {
            case 0:
                return ((AccessibilityRoutineActionHandler) obj).getActionTag();
            default:
                String str = (String) obj;
                try {
                    newInstance = Class.forName(str).newInstance();
                } catch (ClassNotFoundException
                        | IllegalAccessException
                        | InstantiationException e) {
                    Log.w(
                            "A11yRoutineActionProxy",
                            "getHandlerInstance occurs exception. className : " + str,
                            e);
                }
                if (newInstance instanceof AccessibilityRoutineActionHandler) {
                    return (AccessibilityRoutineActionHandler) newInstance;
                }
                Log.w("A11yRoutineActionProxy", str + " is not inherited RoutineActionHandler");
                return null;
        }
    }
}
