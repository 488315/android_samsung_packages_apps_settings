package com.android.settings.homepage.contextualcards.conditional;

import android.app.Flags;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.ZenModeConfig;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settings.notification.zen.ZenModeSettings;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DndConditionCardController implements ConditionalCardController {
    public final Context mAppContext;
    public final ConditionManager mConditionManager;
    public final NotificationManager mNotificationManager;
    public final Receiver mReceiver = new Receiver();
    public static final int ID = Objects.hash("DndConditionCardController");
    static final IntentFilter DND_FILTER =
            new IntentFilter("android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL"
                    .equals(intent.getAction())) {
                DndConditionCardController.this.mConditionManager.onConditionChanged();
            }
        }
    }

    public DndConditionCardController(Context context, ConditionManager conditionManager) {
        this.mAppContext = context;
        this.mConditionManager = conditionManager;
        this.mNotificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final ContextualCard buildContextualCard() {
        ConditionalContextualCard.Builder builder = new ConditionalContextualCard.Builder();
        builder.mConditionId = ID;
        builder.mMetricsConstant = VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY;
        builder.mActionText = this.mAppContext.getText(R.string.condition_turn_off);
        builder.mName =
                this.mAppContext.getPackageName()
                        + "/"
                        + ((Object) this.mAppContext.getText(R.string.condition_zen_title));
        builder.mTitleText = this.mAppContext.getText(R.string.condition_zen_title).toString();
        builder.mSummaryText =
                ZenModeConfig.areAllZenBehaviorSoundsMuted(
                                this.mNotificationManager.getZenModeConfig())
                        ? this.mAppContext
                                .getText(R.string.condition_zen_summary_phone_muted)
                                .toString()
                        : this.mAppContext
                                .getText(R.string.condition_zen_summary_with_exceptions)
                                .toString();
        builder.mIconDrawable = this.mAppContext.getDrawable(R.drawable.ic_do_not_disturb_on_24dp);
        builder.mViewType = R.layout.conditional_card_half_tile;
        return builder.build();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final long getId() {
        return ID;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final boolean isDisplayable() {
        return this.mNotificationManager.getZenMode() != 0;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onActionClick() {
        if (Flags.modesApi()) {
            this.mNotificationManager.setZenMode(0, null, "DndCondition", true);
        } else {
            this.mNotificationManager.setZenMode(0, null, "DndCondition");
        }
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onPrimaryClick(Context context) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = ZenModeSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = VolteConstants.ErrorCode.SERVER_ERROR;
        subSettingLauncher.setTitleRes(R.string.zen_mode_settings_title, null);
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void startMonitoringStateChange() {
        this.mAppContext.registerReceiver(this.mReceiver, DND_FILTER);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void stopMonitoringStateChange() {
        this.mAppContext.unregisterReceiver(this.mReceiver);
    }
}
