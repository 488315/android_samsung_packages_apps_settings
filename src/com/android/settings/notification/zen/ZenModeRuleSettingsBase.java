package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.app.Flags;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.notification.SystemZenRules;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.widget.SecBottomBarButton;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ZenModeRuleSettingsBase extends ZenModeSettingsBase {
    public static final boolean DEBUG = ZenModeSettingsBase.DEBUG;
    public View mBottomBar;
    public SaveRuleClickListener mCancelRuleClickListener;
    public String mId;
    public boolean mIsPortrait;
    public Menu mMenuLandscape;
    public AutomaticZenRule mRule;
    public SaveRuleClickListener mSaveRuleClickListener;
    public int mSecScheduleTimeType;
    public boolean mIsFirst = false;
    public boolean mIsNew = false;
    public boolean mNameEmpty = false;
    public boolean mDaysEmpty = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SaveRuleClickListener implements View.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ZenModeRuleSettingsBase this$0;

        public /* synthetic */ SaveRuleClickListener(
                ZenModeRuleSettingsBase zenModeRuleSettingsBase, int i) {
            this.$r8$classId = i;
            this.this$0 = zenModeRuleSettingsBase;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.saveRule();
                    break;
                default:
                    this.this$0.getActivity().finish();
                    break;
            }
        }
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((ZenModeSettingsBase) this).mContext = getActivity();
        Intent intent = getActivity().getIntent();
        boolean z = DEBUG;
        if (z) {
            Log.d("ZenModeSettings", "onCreate getIntent()=" + intent);
        }
        if (intent == null) {
            Log.w("ZenModeSettings", "No intent");
            toastAndFinish$1();
            return;
        }
        String stringExtra = intent.getStringExtra("android.service.notification.extra.RULE_ID");
        this.mId = stringExtra;
        if (stringExtra == null) {
            Log.w("ZenModeSettings", "rule id is null");
            this.mIsNew = true;
        }
        if (z) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("mId="), this.mId, "ZenModeSettings");
        }
        this.mIsFirst = true;
        if (bundle != null) {
            Uri parse = Uri.parse(bundle.getString("scheduleId"));
            String string = bundle.getString("rulename");
            boolean z2 = bundle.getBoolean("rulestate");
            this.mSecScheduleTimeType = bundle.getInt("dialogState");
            if (z) {
                Log.d("ZenModeSettings", "restoreRule");
            }
            AutomaticZenRule automaticZenRule =
                    new AutomaticZenRule(
                            string,
                            ZenModeConfig.getScheduleConditionProvider(),
                            null,
                            parse,
                            null,
                            2,
                            z2);
            this.mRule = automaticZenRule;
            if (!setRule(automaticZenRule)) {
                toastAndFinish$1();
            }
        } else if (refreshRuleOrFinish()) {
            return;
        }
        this.mIsPortrait = getResources().getConfiguration().orientation == 1;
        this.mCancelRuleClickListener = new SaveRuleClickListener(this, 1);
        this.mSaveRuleClickListener = new SaveRuleClickListener(this, 0);
        onCreateInternal();
    }

    public abstract void onCreateInternal();

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        if (!this.mIsPortrait) {
            getActivity().getMenuInflater().inflate(R.menu.menu_edit, menu);
            this.mMenuLandscape = menu;
        }
        updateSaveButton();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_done) {
            saveRule();
            return true;
        }
        if (menuItem.getItemId() != R.id.action_cancel) {
            return true;
        }
        getActivity().finish();
        return true;
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (this.mIsPortrait) {
            this.mBottomBar.setVisibility(0);
            SecBottomBarButton secBottomBarButton =
                    (SecBottomBarButton) activity.findViewById(R.id.save_button);
            if (secBottomBarButton != null) {
                secBottomBarButton.setOnClickListener(this.mSaveRuleClickListener);
            }
            SecBottomBarButton secBottomBarButton2 =
                    (SecBottomBarButton) activity.findViewById(R.id.cancel_button);
            if (secBottomBarButton2 != null) {
                secBottomBarButton2.setOnClickListener(this.mCancelRuleClickListener);
            }
        } else {
            this.mBottomBar.setVisibility(8);
        }
        if (isUiRestricted()) {
            return;
        }
        if (this.mIsFirst) {
            this.mIsFirst = false;
        }
        if (!refreshRuleOrFinish()) {
            updateControlsInternal();
        }
        if (DEBUG) {
            Log.d("ZenModeSettings", "onResume");
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        Log.d("ZenModeSettings", "onSaveInstanceState");
        bundle.putString("scheduleId", this.mRule.getConditionId().toString());
        bundle.putString("rulename", this.mRule.getName());
        bundle.putBoolean("rulestate", this.mRule.isEnabled());
        bundle.putInt("dialogState", this.mSecScheduleTimeType);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mBottomBar = getActivity().findViewById(R.id.button_bar);
        Utils.setActionBarShadowAnimation(getActivity(), getSettingsLifecycle(), getListView());
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase
    public final void onZenModeConfigChanged() {
        if (refreshRuleOrFinish()) {
            return;
        }
        updateControlsInternal();
    }

    public final boolean refreshRuleOrFinish() {
        boolean z = this.mIsNew;
        boolean z2 = DEBUG;
        if (z && this.mIsFirst) {
            ZenModeConfig.ScheduleInfo scheduleInfo = new ZenModeConfig.ScheduleInfo();
            scheduleInfo.days = ZenModeConfig.ALL_DAYS;
            scheduleInfo.startHour = 22;
            scheduleInfo.endHour = 7;
            this.mRule =
                    new AutomaticZenRule(
                            ApnSettings.MVNO_NONE,
                            ZenModeConfig.getScheduleConditionProvider(),
                            null,
                            ZenModeConfig.toScheduleConditionId(scheduleInfo),
                            null,
                            2,
                            true);
            if (z2) {
                Log.d("ZenModeSettings", "mRule=" + this.mRule);
            }
            if (setRule(this.mRule)) {
                return false;
            }
            toastAndFinish$1();
            return true;
        }
        if (!this.mIsFirst) {
            if (setRule(this.mRule)) {
                return false;
            }
            toastAndFinish$1();
            return true;
        }
        this.mRule =
                NotificationManager.from(((ZenModeSettingsBase) this).mContext)
                        .getAutomaticZenRule(this.mId);
        if (z2) {
            Log.d("ZenModeSettings", "mRule=" + this.mRule);
        }
        if (setRule(this.mRule)) {
            return false;
        }
        toastAndFinish$1();
        return true;
    }

    public final void saveRule() {
        String str;
        String name = this.mRule.getName();
        if (name == null || TextUtils.isEmpty(name)) {
            getActivity().finish();
        }
        if (this.mIsNew) {
            this.mRule.setModified(true);
            ZenModeBackend zenModeBackend = this.mBackend;
            AutomaticZenRule automaticZenRule = this.mRule;
            zenModeBackend.getClass();
            try {
                str =
                        Flags.modesApi()
                                ? zenModeBackend.mNotificationManager.addAutomaticZenRule(
                                        automaticZenRule, true)
                                : NotificationManager.from(zenModeBackend.mContext)
                                        .addAutomaticZenRule(automaticZenRule);
            } catch (Exception unused) {
                str = null;
            }
            if (str == null) {
                Log.d("ZenModeSettings", "Not able to create schedule");
            }
        } else {
            if (!this.mRule.isModified()
                    && !name.equals(
                            NotificationManager.from(((ZenModeSettingsBase) this).mContext)
                                    .getAutomaticZenRule(this.mId)
                                    .getName())) {
                this.mRule.setModified(true);
            }
            this.mBackend.updateZenRule(this.mId, this.mRule);
        }
        getActivity().finish();
    }

    public abstract boolean setRule(AutomaticZenRule automaticZenRule);

    public final void toastAndFinish$1() {
        Toast.makeText(
                        ((ZenModeSettingsBase) this).mContext,
                        R.string.zen_mode_rule_not_found_text,
                        0)
                .show();
        getActivity().finish();
    }

    public abstract void updateControlsInternal();

    public final void updateEventRule(ZenModeConfig.EventInfo eventInfo) {
        this.mRule.setConditionId(ZenModeConfig.toEventConditionId(eventInfo));
        if (Flags.modesApi() && Flags.modesUi()) {
            this.mRule.setTriggerDescription(
                    SystemZenRules.getTriggerDescriptionForScheduleEvent(
                            ((ZenModeSettingsBase) this).mContext, eventInfo));
        }
        this.mBackend.updateZenRule(this.mId, this.mRule);
    }

    public final void updateSaveButton() {
        SecBottomBarButton secBottomBarButton =
                (SecBottomBarButton) getActivity().findViewById(R.id.save_button);
        boolean z = false;
        if (secBottomBarButton != null) {
            secBottomBarButton.setEnabled((this.mDaysEmpty || this.mNameEmpty) ? false : true);
        }
        Menu menu = this.mMenuLandscape;
        if (menu != null) {
            menu.findItem(R.id.action_done).setTitle(R.string.sec_dnd_schedule_action_save);
            MenuItem findItem = this.mMenuLandscape.findItem(R.id.action_done);
            if (!this.mDaysEmpty && !this.mNameEmpty) {
                z = true;
            }
            findItem.setEnabled(z);
        }
    }

    public final void updateScheduleRule(ZenModeConfig.ScheduleInfo scheduleInfo) {
        this.mRule.setConditionId(ZenModeConfig.toScheduleConditionId(scheduleInfo));
        if (Flags.modesApi() && Flags.modesUi()) {
            this.mRule.setTriggerDescription(
                    SystemZenRules.getTriggerDescriptionForScheduleTime(
                            ((ZenModeSettingsBase) this).mContext, scheduleInfo));
        }
    }
}
