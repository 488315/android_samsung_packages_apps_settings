package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.provider.Settings;

import com.android.settings.R;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AudioDescriptionTryItem extends AbstractTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem
    public final String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.vision.VisibilityEnhancementsFragment";
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem
    public final String getHighlightablePreferenceKey() {
        return "toggle_audio_description";
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of("talkback_preference");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(
                R.string.accessibility_toggle_audio_description_preference_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "enabled_accessibility_audio_description_by_default",
                        0)
                == 0;
    }
}
