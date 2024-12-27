package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.SettingsApplication;
import com.android.settings.Utils;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.homepage.SettingsHomepageActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AbstractRoutineRecommendCard implements RecommendCardItem {
    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final RecommendCardItem.ActionButtonInfo getActionButtonInfo(final Context context) {
        return new RecommendCardItem
                .ActionButtonInfo() { // from class:
                                      // com.samsung.android.settings.accessibility.recommend.recommendcard.AbstractRoutineRecommendCard.1
            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final CharSequence getButtonContentDescription(Context context2) {
                return context2.getString(
                        R.string.recommend_description_button_settings,
                        context2.getString(R.string.sec_modes_and_routines));
            }

            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final CharSequence getButtonText(Context context2) {
                return context2.getText(R.string.recommend_routines_button_title);
            }

            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final void onButtonClicked() {
                SettingsHomepageActivity homeActivity;
                try {
                    Intent intent =
                            new Intent("com.samsung.android.app.routines.LAUNCH_SETTINGS_MAIN")
                                    .setPackage("com.samsung.android.app.routines");
                    Context applicationContext = context.getApplicationContext();
                    if (!(applicationContext instanceof SettingsApplication)
                            || (homeActivity =
                                            ((SettingsApplication) applicationContext)
                                                    .getHomeActivity())
                                    == null
                            || !ActivityEmbeddingUtils.isAlreadyEmbedded(homeActivity)) {
                        context.startActivity(intent);
                    } else {
                        homeActivity.mMainFragment.setHighlightMenuKey(
                                context.getString(R.string.menu_key_modes));
                        context.startActivity(intent.putExtra("from_settings", true));
                    }
                } catch (ActivityNotFoundException unused) {
                    Log.e(
                            AbstractRoutineRecommendCard.this.getClass().getSimpleName(),
                            "ActivityNotFoundException : Can not found Routine Activity");
                }
            }
        };
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final boolean isAvailable(Context context) {
        return Utils.hasPackage(context, "com.samsung.android.app.routines")
                && UserHandle.myUserId() == 0;
    }
}
