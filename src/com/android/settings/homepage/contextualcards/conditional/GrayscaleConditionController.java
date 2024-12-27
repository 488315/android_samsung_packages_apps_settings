package com.android.settings.homepage.contextualcards.conditional;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.ColorDisplayManager;
import android.os.UserHandle;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.homepage.contextualcards.ContextualCard;

import java.net.URISyntaxException;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GrayscaleConditionController implements ConditionalCardController {
    public final Context mAppContext;
    public final ColorDisplayManager mColorDisplayManager;
    public final ConditionManager mConditionManager;
    public Intent mIntent;
    public final Receiver mReceiver = new Receiver();
    public static final int ID = Objects.hash("GrayscaleConditionController");
    public static final IntentFilter GRAYSCALE_CHANGED_FILTER =
            new IntentFilter("android.settings.action.GRAYSCALE_CHANGED");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.settings.action.GRAYSCALE_CHANGED".equals(intent.getAction())) {
                GrayscaleConditionController.this.mConditionManager.onConditionChanged();
            }
        }
    }

    public GrayscaleConditionController(Context context, ConditionManager conditionManager) {
        this.mAppContext = context;
        this.mConditionManager = conditionManager;
        this.mColorDisplayManager =
                (ColorDisplayManager) context.getSystemService(ColorDisplayManager.class);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final ContextualCard buildContextualCard() {
        ConditionalContextualCard.Builder builder = new ConditionalContextualCard.Builder();
        builder.mConditionId = ID;
        builder.mMetricsConstant = 1683;
        builder.mActionText = this.mAppContext.getText(R.string.condition_turn_off);
        builder.mName =
                this.mAppContext.getPackageName()
                        + "/"
                        + ((Object) this.mAppContext.getText(R.string.condition_grayscale_title));
        builder.mTitleText =
                this.mAppContext.getText(R.string.condition_grayscale_title).toString();
        builder.mSummaryText =
                this.mAppContext.getText(R.string.condition_grayscale_summary).toString();
        builder.mIconDrawable = this.mAppContext.getDrawable(R.drawable.ic_gray_scale_24dp);
        builder.mViewType = R.layout.conditional_card_half_tile;
        return builder.build();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final long getId() {
        return ID;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final boolean isDisplayable() {
        try {
            this.mIntent =
                    Intent.parseUri(
                            this.mAppContext.getString(R.string.config_grayscale_settings_intent),
                            1);
            return this.mColorDisplayManager.isSaturationActivated();
        } catch (URISyntaxException e) {
            Log.w("GrayscaleCondition", "Failure parsing grayscale settings intent, skipping", e);
            return false;
        }
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onActionClick() {
        this.mColorDisplayManager.setSaturationLevel(100);
        Intent intent = new Intent("android.settings.action.GRAYSCALE_CHANGED");
        intent.addFlags(16777216);
        this.mAppContext.sendBroadcastAsUser(
                intent, UserHandle.CURRENT, "android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS");
        this.mConditionManager.onConditionChanged();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onPrimaryClick(Context context) {
        this.mAppContext.startActivity(this.mIntent);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void startMonitoringStateChange() {
        this.mAppContext.registerReceiver(
                this.mReceiver,
                GRAYSCALE_CHANGED_FILTER,
                "android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS",
                null);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void stopMonitoringStateChange() {
        this.mAppContext.unregisterReceiver(this.mReceiver);
    }
}
