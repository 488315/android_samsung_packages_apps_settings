package com.android.settings.homepage.contextualcards.conditional;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.PreciseDataConnectionState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settings.network.GlobalSettingsChangeListener;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CellularDataConditionController implements ConditionalCardController {
    public static final int ID = Objects.hash("CellularDataConditionController");
    public final Context mAppContext;
    public final ConditionManager mConditionManager;
    public boolean mIsListeningConnectionChange;
    public final AnonymousClass2 mPhoneStateListener =
            new PhoneStateListener() { // from class:
                                       // com.android.settings.homepage.contextualcards.conditional.CellularDataConditionController.2
                @Override // android.telephony.PhoneStateListener
                public final void onPreciseDataConnectionStateChanged(
                        PreciseDataConnectionState preciseDataConnectionState) {
                    CellularDataConditionController.this.mConditionManager.onConditionChanged();
                }
            };
    public int mSubId;
    public TelephonyManager mTelephonyManager;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.homepage.contextualcards.conditional.CellularDataConditionController$2] */
    public CellularDataConditionController(Context context, ConditionManager conditionManager) {
        this.mAppContext = context;
        this.mConditionManager = conditionManager;
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        this.mSubId = defaultDataSubscriptionId;
        this.mTelephonyManager =
                ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(defaultDataSubscriptionId);
        new GlobalSettingsChangeListener(
                context) { // from class:
                           // com.android.settings.homepage.contextualcards.conditional.CellularDataConditionController.1
            @Override // com.android.settings.network.GlobalSettingsChangeListener
            public final void onChanged$1() {
                int defaultDataSubscriptionId2 = SubscriptionManager.getDefaultDataSubscriptionId();
                CellularDataConditionController cellularDataConditionController =
                        CellularDataConditionController.this;
                if (defaultDataSubscriptionId2 == cellularDataConditionController.mSubId) {
                    return;
                }
                cellularDataConditionController.mSubId = defaultDataSubscriptionId2;
                if (cellularDataConditionController.mIsListeningConnectionChange) {
                    Context context2 = cellularDataConditionController.mAppContext;
                    cellularDataConditionController.mIsListeningConnectionChange = false;
                    TelephonyManager telephonyManager =
                            cellularDataConditionController.mTelephonyManager;
                    AnonymousClass2 anonymousClass2 =
                            cellularDataConditionController.mPhoneStateListener;
                    telephonyManager.listen(anonymousClass2, 0);
                    cellularDataConditionController.mIsListeningConnectionChange = true;
                    if (SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId2)) {
                        cellularDataConditionController.mTelephonyManager =
                                ((TelephonyManager)
                                                context2.getSystemService(TelephonyManager.class))
                                        .createForSubscriptionId(defaultDataSubscriptionId2);
                    }
                    cellularDataConditionController.mTelephonyManager.listen(anonymousClass2, 4096);
                }
            }
        };
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final ContextualCard buildContextualCard() {
        ConditionalContextualCard.Builder builder = new ConditionalContextualCard.Builder();
        builder.mConditionId = ID;
        builder.mMetricsConstant = VolteConstants.ErrorCode.ALTERNATIVE_SERVICES;
        builder.mActionText = this.mAppContext.getText(R.string.condition_turn_on);
        builder.mName =
                this.mAppContext.getPackageName()
                        + "/"
                        + ((Object) this.mAppContext.getText(R.string.condition_cellular_title));
        builder.mTitleText = this.mAppContext.getText(R.string.condition_cellular_title).toString();
        builder.mSummaryText =
                this.mAppContext.getText(R.string.condition_cellular_summary).toString();
        builder.mIconDrawable = this.mAppContext.getDrawable(R.drawable.ic_cellular_off);
        builder.mViewType = R.layout.conditional_card_half_tile;
        return builder.build();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final long getId() {
        return ID;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final boolean isDisplayable() {
        if (this.mTelephonyManager.isDataCapable() && this.mTelephonyManager.getSimState() == 5) {
            return !this.mTelephonyManager.isDataEnabled();
        }
        return false;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onActionClick() {
        Log.d("CellularDataConditionController", "setDataEnabledForReason true");
        this.mTelephonyManager.setDataEnabled(true);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onPrimaryClick(Context context) {
        context.startActivity(
                new Intent(context, (Class<?>) Settings.DataUsageSummaryActivity.class));
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void startMonitoringStateChange() {
        Context context = this.mAppContext;
        int i = this.mSubId;
        this.mIsListeningConnectionChange = false;
        TelephonyManager telephonyManager = this.mTelephonyManager;
        AnonymousClass2 anonymousClass2 = this.mPhoneStateListener;
        telephonyManager.listen(anonymousClass2, 0);
        this.mIsListeningConnectionChange = true;
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            this.mTelephonyManager =
                    ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                            .createForSubscriptionId(i);
        }
        this.mTelephonyManager.listen(anonymousClass2, 4096);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void stopMonitoringStateChange() {
        this.mIsListeningConnectionChange = false;
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
    }
}
