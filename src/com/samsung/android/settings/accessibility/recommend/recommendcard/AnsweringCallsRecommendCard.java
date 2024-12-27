package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AnsweringCallsRecommendCard implements RecommendCardItem {
    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final RecommendCardItem.ActionButtonInfo getActionButtonInfo(final Context context) {
        return new RecommendCardItem.ActionButtonInfo() { // from class:
            // com.samsung.android.settings.accessibility.recommend.recommendcard.AnsweringCallsRecommendCard.1
            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final CharSequence getButtonContentDescription(Context context2) {
                return context2.getString(
                        R.string.recommend_description_button_settings,
                        context2.getString(
                                R.string.easy_mode_relative_link_answering_and_ending_calls));
            }

            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final void onButtonClicked() {
                try {
                    context.startActivity(
                            new Intent(
                                            "com.samsung.android.app.telephonyui.action.OPEN_CALL_SETTINGS")
                                    .putExtra("root_key", "ANSWERING_AND_ENDING_CALLS"));
                } catch (ActivityNotFoundException e) {
                    Log.w("RecommendAnsweringCallController", "ActivityNotFoundException!", e);
                }
            }
        };
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final CharSequence getCardContent(Context context) {
        return context.getText(R.string.recommend_answering_call_content);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final int getSupportProfileType() {
        return 32;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final boolean isAvailable(Context context) {
        return Utils.isVoiceCapable(context) && ActivityManager.semGetCurrentUser() == 0;
    }
}
