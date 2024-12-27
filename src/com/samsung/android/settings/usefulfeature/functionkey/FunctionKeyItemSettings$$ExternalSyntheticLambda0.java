package com.samsung.android.settings.usefulfeature.functionkey;

import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class FunctionKeyItemSettings$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = FunctionKeyItemSettings.$r8$clinit;
        return ((AbstractPreferenceController) obj)
                instanceof SecRadioButtonGearPreferenceController;
    }
}
