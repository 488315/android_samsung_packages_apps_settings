package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.AccessibilityRune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SmartThingsRecommendCard implements RecommendCardItem {
    public static final boolean IS_GLOBAL_NAMING;

    static {
        IS_GLOBAL_NAMING = !Rune.isChinaModel() || AccessibilityRune.isAtLeastOneUI_6_1();
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final RecommendCardItem.ActionButtonInfo getActionButtonInfo(final Context context) {
        return new RecommendCardItem
                .ActionButtonInfo() { // from class:
                                      // com.samsung.android.settings.accessibility.recommend.recommendcard.SmartThingsRecommendCard.1
            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final CharSequence getButtonText(Context context2) {
                return context2.getString(
                        R.string.recommend_button_open_app,
                        context2.getString(
                                SmartThingsRecommendCard.IS_GLOBAL_NAMING
                                        ? R.string.recommend_smart_things_appname
                                        : R.string.recommend_smart_things_appname_china));
            }

            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final void onButtonClicked() {
                try {
                    context.startActivity(
                            new Intent("android.intent.action.MAIN")
                                    .setComponent(
                                            ComponentName.createRelative(
                                                    "com.samsung.android.oneconnect",
                                                    ".ui.SCMainActivity"))
                                    .setFlags(335544320));
                } catch (ActivityNotFoundException e) {
                    Log.w("SmartThingsRecommendCard", "Activity not found", e);
                }
            }
        };
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final CharSequence getCardContent(Context context) {
        return context.getString(
                R.string.recommend_smart_things_content,
                context.getString(
                        IS_GLOBAL_NAMING
                                ? R.string.recommend_smart_things_appname
                                : R.string.recommend_smart_things_appname_china));
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final int getSupportProfileType() {
        return 24;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final boolean isAvailable(Context context) {
        return Utils.hasPackage(context, "com.samsung.android.oneconnect");
    }
}
