package com.android.settings.homepage.contextualcards.conditional;

import android.content.Context;

import com.android.settings.homepage.contextualcards.ContextualCard;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface ConditionalCardController {
    ContextualCard buildContextualCard();

    long getId();

    boolean isDisplayable();

    void onActionClick();

    void onPrimaryClick(Context context);

    void startMonitoringStateChange();

    void stopMonitoringStateChange();
}
