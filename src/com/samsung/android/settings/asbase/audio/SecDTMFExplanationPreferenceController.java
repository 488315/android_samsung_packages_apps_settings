package com.samsung.android.settings.asbase.audio;

import com.android.settings.Utils;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecDTMFExplanationPreferenceController extends AbstractPreferenceController {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "waiting_tone_volume_explanation";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Rune.isDomesticKTTModel()
                && Rune.isDomesticModel()
                && Utils.isVoiceCapable(this.mContext);
    }
}
