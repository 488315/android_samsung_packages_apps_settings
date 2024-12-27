package com.android.settings.homepage.contextualcards.conditional;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.homepage.contextualcards.ContextualCard;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WorkModeConditionController implements ConditionalCardController {
    public static final IntentFilter FILTER;
    public static final int ID = Objects.hash("WorkModeConditionController");
    public final Context mAppContext;
    public final ConditionManager mConditionManager;
    public final DevicePolicyManager mDpm;
    public final Receiver mReceiver = new Receiver();
    public final UserManager mUm;
    public UserHandle mUserHandle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, "android.intent.action.MANAGED_PROFILE_AVAILABLE")
                    || TextUtils.equals(
                            action, "android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                WorkModeConditionController.this.mConditionManager.onConditionChanged();
            }
        }
    }

    static {
        IntentFilter intentFilter = new IntentFilter();
        FILTER = intentFilter;
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
    }

    public WorkModeConditionController(Context context, ConditionManager conditionManager) {
        this.mAppContext = context;
        this.mUm = (UserManager) context.getSystemService(UserManager.class);
        this.mDpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mConditionManager = conditionManager;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final ContextualCard buildContextualCard() {
        String string =
                this.mDpm
                        .getResources()
                        .getString(
                                "Settings.WORK_PROFILE_OFF_CONDITION_TITLE",
                                new Supplier() { // from class:
                                                 // com.android.settings.homepage.contextualcards.conditional.WorkModeConditionController$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return WorkModeConditionController.this.mAppContext
                                                .getString(R.string.condition_work_title);
                                    }
                                });
        ConditionalContextualCard.Builder builder = new ConditionalContextualCard.Builder();
        builder.mConditionId = ID;
        builder.mMetricsConstant = 383;
        builder.mActionText = this.mAppContext.getText(R.string.condition_turn_on);
        builder.mName = this.mAppContext.getPackageName() + "/" + string;
        builder.mTitleText = string;
        builder.mSummaryText = this.mAppContext.getText(R.string.condition_work_summary).toString();
        builder.mIconDrawable = this.mAppContext.getDrawable(R.drawable.ic_signal_workmode_enable);
        builder.mViewType = R.layout.conditional_card_half_tile;
        return builder.build();
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final long getId() {
        return ID;
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final boolean isDisplayable() {
        List profiles = this.mUm.getProfiles(UserHandle.myUserId());
        int size = profiles.size();
        this.mUserHandle = null;
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            UserInfo userInfo = (UserInfo) profiles.get(i);
            if (userInfo.isManagedProfile()) {
                this.mUserHandle = userInfo.getUserHandle();
                break;
            }
            i++;
        }
        UserHandle userHandle = this.mUserHandle;
        return userHandle != null && this.mUm.isQuietModeEnabled(userHandle);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onActionClick() {
        UserHandle userHandle = this.mUserHandle;
        if (userHandle != null) {
            this.mUm.requestQuietModeEnabled(false, userHandle);
        }
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onPrimaryClick(Context context) {
        context.startActivity(
                new Intent(context, (Class<?>) Settings.AccountDashboardActivity.class));
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void startMonitoringStateChange() {
        this.mAppContext.registerReceiver(this.mReceiver, FILTER);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void stopMonitoringStateChange() {
        this.mAppContext.unregisterReceiver(this.mReceiver);
    }
}
