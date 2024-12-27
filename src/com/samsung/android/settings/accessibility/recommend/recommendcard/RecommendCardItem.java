package com.samsung.android.settings.accessibility.recommend.recommendcard;

import android.content.Context;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface RecommendCardItem {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ActionButtonInfo {
        default CharSequence getButtonContentDescription(Context context) {
            return null;
        }

        default CharSequence getButtonText(Context context) {
            return context.getText(R.string.recommend_button_settings);
        }

        void onButtonClicked();
    }

    default ActionButtonInfo getActionButtonInfo(Context context) {
        return null;
    }

    CharSequence getCardContent(Context context);

    int getSupportProfileType();

    default boolean isAvailable(Context context) {
        return true;
    }
}
