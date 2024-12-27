package com.android.settings.homepage.contextualcards.conditional;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.android.settings.R;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settingslib.WirelessUtils;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AirplaneModeConditionController implements ConditionalCardController {
    public final Context mAppContext;
    public final ConditionManager mConditionManager;
    public final ConnectivityManager mConnectivityManager;
    public final Receiver mReceiver = new Receiver();
    public static final int ID = Objects.hash("AirplaneModeConditionController");
    public static final IntentFilter AIRPLANE_MODE_FILTER =
            new IntentFilter("android.intent.action.AIRPLANE_MODE");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.AIRPLANE_MODE".equals(intent.getAction())) {
                AirplaneModeConditionController.this.mConditionManager.onConditionChanged();
            }
        }
    }

    public AirplaneModeConditionController(Context context, ConditionManager conditionManager) {
        this.mAppContext = context;
        this.mConditionManager = conditionManager;
        this.mConnectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final ContextualCard buildContextualCard() {
        ConditionalContextualCard.Builder builder = new ConditionalContextualCard.Builder();
        builder.mConditionId = ID;
        builder.mMetricsConstant = 377;
        builder.mActionText = this.mAppContext.getText(R.string.condition_turn_off);
        builder.mName =
                this.mAppContext.getPackageName()
                        + "/"
                        + ((Object) this.mAppContext.getText(R.string.condition_airplane_title));
        builder.mTitleText = this.mAppContext.getText(R.string.condition_airplane_title).toString();
        builder.mSummaryText =
                this.mAppContext.getText(R.string.condition_airplane_summary).toString();
        builder.mIconDrawable = this.mAppContext.getDrawable(R.drawable.ic_airplanemode_active);
        builder.mViewType = R.layout.conditional_card_half_tile;
        return builder.build();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final long getId() {
        return ID;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final boolean isDisplayable() {
        return WirelessUtils.isAirplaneModeOn(this.mAppContext);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onActionClick() {
        this.mConnectivityManager.setAirplaneMode(false);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onPrimaryClick(Context context) {
        context.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void startMonitoringStateChange() {
        this.mAppContext.registerReceiver(this.mReceiver, AIRPLANE_MODE_FILTER);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void stopMonitoringStateChange() {
        this.mAppContext.unregisterReceiver(this.mReceiver);
    }
}
