package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.content.Context;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MagnificationRecommendCard implements RecommendCardItem {
    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final RecommendCardItem.ActionButtonInfo getActionButtonInfo(final Context context) {
        return new RecommendCardItem.ActionButtonInfo() { // from class:
            // com.samsung.android.settings.accessibility.recommend.recommendcard.MagnificationRecommendCard.1
            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final CharSequence getButtonContentDescription(Context context2) {
                return context2.getString(
                        R.string.recommend_description_button_settings,
                        context2.getString(R.string.accessibility_screen_magnification_title));
            }

            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final void onButtonClicked() {
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName =
                        "com.samsung.android.settings.accessibility.vision.MagnificationPreferenceFragment";
                launchRequest.mArguments = new Bundle();
                subSettingLauncher.setTitleRes(
                        R.string.accessibility_screen_magnification_title, null);
                launchRequest.mSourceMetricsCategory = 0;
                subSettingLauncher.launch();
            }
        };
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final CharSequence getCardContent(Context context) {
        return context.getText(R.string.recommend_magnification_content);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final int getSupportProfileType() {
        return 4;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final boolean isAvailable(Context context) {
        return ToggleScreenMagnificationPreferenceFragment.getUserShortcutTypeFromSettings(context)
                == 0;
    }
}
