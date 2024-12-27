package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.content.Context;
import android.content.Intent;

import com.android.settings.R;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.settings.accessibility.AccessibilityConstant;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TalkBackGestureRecommendCard implements RecommendCardItem {
    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final RecommendCardItem.ActionButtonInfo getActionButtonInfo(final Context context) {
        return new RecommendCardItem
                .ActionButtonInfo() { // from class:
                                      // com.samsung.android.settings.accessibility.recommend.recommendcard.TalkBackGestureRecommendCard.1
            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final CharSequence getButtonContentDescription(Context context2) {
                return context2.getString(
                        R.string.recommend_description_button_settings,
                        context2.getString(R.string.talkback_title));
            }

            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final void onButtonClicked() {
                context.startActivity(
                        new Intent()
                                .setComponent(
                                        AccessibilityConstant
                                                .COMPONENT_NAME_SAMSUNG_TALKBACK_SETTING_ACTIVITY));
            }
        };
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final CharSequence getCardContent(Context context) {
        return context.getText(R.string.recommend_talkback_content);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final int getSupportProfileType() {
        return -1;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final boolean isAvailable(Context context) {
        return AccessibilityUtils.getEnabledServicesFromSettings(context)
                .contains(AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK);
    }
}
