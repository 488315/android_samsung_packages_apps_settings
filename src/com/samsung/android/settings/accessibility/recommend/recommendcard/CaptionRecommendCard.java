package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.content.Context;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CaptionRecommendCard implements RecommendCardItem {
    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final RecommendCardItem.ActionButtonInfo getActionButtonInfo(final Context context) {
        return new RecommendCardItem.ActionButtonInfo() { // from class:
            // com.samsung.android.settings.accessibility.recommend.recommendcard.CaptionRecommendCard.1
            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final CharSequence getButtonContentDescription(Context context2) {
                return context2.getString(
                        R.string.recommend_description_button_settings,
                        context2.getString(R.string.hearing_enhancements_title));
            }

            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final void onButtonClicked() {
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName =
                        "com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment";
                subSettingLauncher.setTitleRes(R.string.hearing_enhancements_title, null);
                launchRequest.mSourceMetricsCategory = 0;
                subSettingLauncher.launch();
            }
        };
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final CharSequence getCardContent(Context context) {
        return context.getText(R.string.recommend_caption_content);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final int getSupportProfileType() {
        return 24;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final boolean isAvailable(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "odi_captions_enabled", 0) == 0;
    }
}
