package com.samsung.android.settings.accessibility.vision.controllers;

import com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class CustomDisplayModePreferenceController$$ExternalSyntheticLambda0
        implements SingleChoicePreference.CheckItemDefaultKey {
    public final /* synthetic */ CustomDisplayModePreferenceController f$0;

    public /* synthetic */ CustomDisplayModePreferenceController$$ExternalSyntheticLambda0(
            CustomDisplayModePreferenceController customDisplayModePreferenceController) {
        this.f$0 = customDisplayModePreferenceController;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference.CheckItemDefaultKey
    public final String getDefaultKey() {
        String checkDefaultKey;
        checkDefaultKey = this.f$0.checkDefaultKey();
        return checkDefaultKey;
    }
}
