package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.SIPUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VoiceInputRecommendCard implements RecommendCardItem {
    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final CharSequence getCardContent(Context context) {
        return context.getText(R.string.recommend_voice_input_content);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final int getSupportProfileType() {
        return 2;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final boolean isAvailable(Context context) {
        return (Rune.isJapanModel()
                        || Utils.isTablet()
                        || SIPUtils.getCurrentInputMethodType(context) != 1)
                ? false
                : true;
    }
}
