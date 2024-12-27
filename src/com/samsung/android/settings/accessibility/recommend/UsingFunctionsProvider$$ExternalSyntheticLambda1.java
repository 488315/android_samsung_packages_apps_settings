package com.samsung.android.settings.accessibility.recommend;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class UsingFunctionsProvider$$ExternalSyntheticLambda1
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((BasePreferenceController) obj) instanceof AccessibilityUsingFunction;
    }
}
