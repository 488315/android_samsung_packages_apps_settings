package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.PkgUtils;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EditLockScreenRecommendCard implements RecommendCardItem {
    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final RecommendCardItem.ActionButtonInfo getActionButtonInfo(final Context context) {
        return new RecommendCardItem.ActionButtonInfo() { // from class:
            // com.samsung.android.settings.accessibility.recommend.recommendcard.EditLockScreenRecommendCard.1
            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final CharSequence getButtonText(Context context2) {
                return context2.getText(R.string.recommend_button_try_it_out);
            }

            @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem.ActionButtonInfo
            public final void onButtonClicked() {
                try {
                    context.startActivity(
                            new Intent(
                                            "com.samsung.dressroom.intent.action.SHOW_LOCK_ACCESSIBILITY")
                                    .addFlags(268468224)
                                    .setPackage(
                                            context.getString(
                                                    R.string
                                                            .config_sec_toplevel_wallpaper_package)));
                } catch (ActivityNotFoundException e) {
                    SemLog.w(
                            "EditLockScreenRecommendCard",
                            "ActivityNotFoundException occurred.",
                            e);
                }
            }
        };
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final CharSequence getCardContent(Context context) {
        return context.getText(R.string.recommend_edit_lock_screen_content);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final int getSupportProfileType() {
        return 4;
    }

    @Override // com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem
    public final boolean isAvailable(Context context) {
        return !SecAccessibilityUtils.isDesktopDualModeMonitorDisplay(context)
                && PkgUtils.hasPackage(
                        context, context.getString(R.string.config_sec_toplevel_wallpaper_package))
                && Settings.System.getInt(context.getContentResolver(), "minimal_battery_use", 0)
                        == 0;
    }
}
