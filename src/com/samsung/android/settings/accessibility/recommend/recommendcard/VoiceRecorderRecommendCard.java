package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VoiceRecorderRecommendCard implements RecommendCardItem {
    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final RecommendCardItem.ActionButtonInfo getActionButtonInfo(final Context context) {
        return new RecommendCardItem
                .ActionButtonInfo() { // from class:
                                      // com.samsung.android.settings.accessibility.recommend.recommendcard.VoiceRecorderRecommendCard.1
            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final CharSequence getButtonText(Context context2) {
                return context2.getString(
                        R.string.recommend_button_open_app,
                        context2.getString(R.string.recommend_voicerecorder_appname));
            }

            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final void onButtonClicked() {
                try {
                    context.startActivity(
                            new Intent()
                                    .setComponent(
                                            ComponentName.createRelative(
                                                    "com.sec.android.app.voicenote",
                                                    ".main.VNMainActivity"))
                                    .addFlags(67108864));
                } catch (ActivityNotFoundException e) {
                    Log.w("VoiceRecorderRecommendCard", "Activity is not found.", e);
                }
            }
        };
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final CharSequence getCardContent(Context context) {
        return context.getText(R.string.recommend_speech_to_text_content);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final int getSupportProfileType() {
        return 24;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final boolean isAvailable(Context context) {
        return false;
    }
}
