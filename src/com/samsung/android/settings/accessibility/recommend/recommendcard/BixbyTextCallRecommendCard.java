package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BixbyTextCallRecommendCard implements RecommendCardItem {
    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final RecommendCardItem.ActionButtonInfo getActionButtonInfo(final Context context) {
        return new RecommendCardItem.ActionButtonInfo() { // from class:
            // com.samsung.android.settings.accessibility.recommend.recommendcard.BixbyTextCallRecommendCard.1
            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final CharSequence getButtonContentDescription(Context context2) {
                return context2.getString(
                        R.string.recommend_description_button_settings,
                        context2.getString(
                                AccessibilityRune.isAtLeastOneUI_6_1()
                                        ? R.string.recommend_text_call_title
                                        : R.string.recommend_bixby_text_call_title));
            }

            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final void onButtonClicked() {
                try {
                    Intent intent =
                            new Intent(
                                    "com.samsung.android.app.telephonyui.action.OPEN_CALL_SETTINGS");
                    intent.putExtra("root_key", "SCREEN_CALL");
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.w("BixbyTextCallRecommendCard", "ActivityNotFoundException!", e);
                }
            }
        };
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final CharSequence getCardContent(Context context) {
        return AccessibilityRune.isAtLeastOneUI_6_1()
                ? context.getText(R.string.recommend_text_call_content)
                : context.getText(R.string.recommend_bixby_text_call_content);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final int getSupportProfileType() {
        return 25;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final boolean isAvailable(Context context) {
        if (Rune.isDomesticModel() && Rune.supportBixbyClient()) {
            return ((AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO))
                    .semIsScreenCallAvailable();
        }
        return false;
    }
}
