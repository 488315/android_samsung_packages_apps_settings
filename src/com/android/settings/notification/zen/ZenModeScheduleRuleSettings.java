package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.service.notification.ZenModeConfig;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.picker.app.SeslTimePickerDialog;
import androidx.picker.widget.SeslTimePicker;
import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

import com.google.android.material.textfield.TextInputLayout;
import com.samsung.android.settings.datetime.CustomTimePickerDialog;
import com.samsung.android.settings.notification.zen.SecZenScheduleRepeatButton;
import com.samsung.android.settings.notification.zen.SecZenScheduleRepeatPreference;
import com.samsung.android.settings.widget.SecBottomBarButton;
import com.samsung.android.settings.widget.SecButtonPreference;

import java.util.Calendar;
import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeScheduleRuleSettings extends ZenModeRuleSettingsBase
        implements SeslTimePickerDialog.OnTimeSetListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mConfirmDialog;
    public CustomTimePickerDialog mCustomTimePickerDialog;
    public SecZenScheduleRepeatPreference mRepeatButtonGroup;
    public ZenModeConfig.ScheduleInfo mSchedule;
    public LayoutPreference mScheduleNamePreference;
    public EditText mScheduleNameView;
    public TextInputLayout mScheduleNameView_layout;
    public SecPreference mSecScheduleStart;
    public boolean mIsDialogShowing = false;
    public boolean mIsDeleteDialogShowing = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeScheduleRuleSettings$2, reason: invalid class name */
    public final class AnonymousClass2 implements TextView.OnEditorActionListener {
        @Override // android.widget.TextView.OnEditorActionListener
        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if ((keyEvent == null || keyEvent.getKeyCode() != 66) && i != 6 && i != 5) {
                return true;
            }
            Log.i("ZenModeSettings", "Enter pressed");
            InputMethodManager.getInstance().semForceHideSoftInput();
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeScheduleRuleSettings$4, reason: invalid class name */
    public final class AnonymousClass4 implements Preference.OnPreferenceClickListener {
        public /* synthetic */ AnonymousClass4() {}

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public boolean onPreferenceClick(Preference preference) {
            ZenModeScheduleRuleSettings zenModeScheduleRuleSettings =
                    ZenModeScheduleRuleSettings.this;
            zenModeScheduleRuleSettings.mSecScheduleTimeType = 1;
            ZenModeConfig.ScheduleInfo scheduleInfo = zenModeScheduleRuleSettings.mSchedule;
            CustomTimePickerDialog customTimePickerDialog =
                    new CustomTimePickerDialog(
                            ((ZenModeSettingsBase) zenModeScheduleRuleSettings).mContext,
                            (scheduleInfo.startHour * 60) + scheduleInfo.startMinute,
                            (scheduleInfo.endHour * 60) + scheduleInfo.endMinute,
                            zenModeScheduleRuleSettings.new AnonymousClass4());
            zenModeScheduleRuleSettings.mCustomTimePickerDialog = customTimePickerDialog;
            customTimePickerDialog.getWindow().setSoftInputMode(16);
            zenModeScheduleRuleSettings.mCustomTimePickerDialog.show();
            return false;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 144;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_schedule_rule_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        Calendar calendar = Calendar.getInstance();
        if (i == 1) {
            calendar.set(11, this.mSchedule.startHour);
            calendar.set(12, this.mSchedule.startMinute);
            SeslTimePickerDialog seslTimePickerDialog =
                    new SeslTimePickerDialog(
                            getActivity(),
                            this,
                            calendar.get(11),
                            calendar.get(12),
                            DateFormat.is24HourFormat(getActivity()));
            seslTimePickerDialog.getWindow().setSoftInputMode(16);
            return seslTimePickerDialog;
        }
        if (i != 2) {
            throw new IllegalArgumentException();
        }
        calendar.set(11, this.mSchedule.endHour);
        calendar.set(12, this.mSchedule.endMinute);
        SeslTimePickerDialog seslTimePickerDialog2 =
                new SeslTimePickerDialog(
                        getActivity(),
                        this,
                        calendar.get(11),
                        calendar.get(12),
                        DateFormat.is24HourFormat(getActivity()));
        seslTimePickerDialog2.getWindow().setSoftInputMode(16);
        return seslTimePickerDialog2;
    }

    @Override // com.android.settings.notification.zen.ZenModeRuleSettingsBase
    public final void onCreateInternal() {
        LayoutPreference layoutPreference = (LayoutPreference) findPreference("schedule_name");
        this.mScheduleNamePreference = layoutPreference;
        EditText editText = (EditText) layoutPreference.mRootView.findViewById(R.id.edittext);
        this.mScheduleNameView = editText;
        editText.setPrivateImeOptions("disableImage=true");
        if (this.mIsNew) {
            this.mScheduleNameView.requestFocus();
        }
        this.mScheduleNameView_layout =
                (TextInputLayout)
                        this.mScheduleNamePreference.mRootView.findViewById(
                                R.id.schedule_name_input_layout);
        if (TextUtils.isEmpty(this.mRule.getName())) {
            this.mScheduleNameView.setHint(R.string.zen_mode_rule_name_hint);
        } else {
            this.mScheduleNameView.setText(this.mRule.getName());
        }
        if (this.mRule.getName().length() > 20) {
            TextInputLayout textInputLayout = this.mScheduleNameView_layout;
            if (!textInputLayout.indicatorViewController.errorEnabled) {
                textInputLayout.setError(
                        ((ZenModeSettingsBase) this)
                                .mContext.getString(
                                        R.string.zen_mode_rule_character_limit_warning));
                this.mScheduleNameView_layout.setErrorEnabled(true);
            }
        }
        this.mScheduleNameView.setOnFocusChangeListener(
                new View
                        .OnFocusChangeListener() { // from class:
                                                   // com.android.settings.notification.zen.ZenModeScheduleRuleSettings.1
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        if (z) {
                            return;
                        }
                        AbsAdapter$$ExternalSyntheticOutline0.m(
                                "onFocusChange : ", "ZenModeSettings", z);
                        ZenModeScheduleRuleSettings zenModeScheduleRuleSettings =
                                ZenModeScheduleRuleSettings.this;
                        int i = ZenModeScheduleRuleSettings.$r8$clinit;
                        zenModeScheduleRuleSettings.getClass();
                        InputMethodManager inputMethodManager = InputMethodManager.getInstance();
                        if (view == null) {
                            Log.i(
                                    "ZenModeSettings",
                                    "hideSoftKeyboard focusedView is null force hide");
                            inputMethodManager.semForceHideSoftInput();
                        } else {
                            Log.i("ZenModeSettings", "hideSoftKeyboard");
                            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 2);
                        }
                    }
                });
        this.mScheduleNameView.setOnEditorActionListener(new AnonymousClass2());
        this.mScheduleNameView.addTextChangedListener(
                new TextWatcher() { // from class:
                                    // com.android.settings.notification.zen.ZenModeScheduleRuleSettings.3
                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {
                        ZenModeScheduleRuleSettings.this.mRule.setName(charSequence.toString());
                        boolean z = false;
                        if (TextUtils.isEmpty(charSequence.toString())) {
                            ZenModeScheduleRuleSettings.this.mScheduleNameView.setHint(
                                    R.string.zen_mode_rule_name_hint);
                            ZenModeScheduleRuleSettings.this.mNameEmpty = true;
                        } else {
                            ZenModeScheduleRuleSettings.this.mNameEmpty = false;
                        }
                        if (charSequence.length() >= 20) {
                            ZenModeScheduleRuleSettings zenModeScheduleRuleSettings =
                                    ZenModeScheduleRuleSettings.this;
                            TextInputLayout textInputLayout2 =
                                    zenModeScheduleRuleSettings.mScheduleNameView_layout;
                            if (!textInputLayout2.indicatorViewController.errorEnabled) {
                                textInputLayout2.setError(
                                        ((ZenModeSettingsBase) zenModeScheduleRuleSettings)
                                                .mContext.getString(
                                                        R.string
                                                                .zen_mode_rule_character_limit_warning));
                                ZenModeScheduleRuleSettings.this.mScheduleNameView_layout
                                        .setErrorEnabled(true);
                            }
                        } else {
                            ZenModeScheduleRuleSettings.this.mScheduleNameView_layout.setError(
                                    null);
                            ZenModeScheduleRuleSettings.this.mScheduleNameView_layout
                                    .setErrorEnabled(false);
                        }
                        if (charSequence.length() <= 20) {
                            ZenModeScheduleRuleSettings.this.updateSaveButton();
                            return;
                        }
                        ZenModeScheduleRuleSettings zenModeScheduleRuleSettings2 =
                                ZenModeScheduleRuleSettings.this;
                        zenModeScheduleRuleSettings2.mNameEmpty = true;
                        SecBottomBarButton secBottomBarButton =
                                (SecBottomBarButton)
                                        zenModeScheduleRuleSettings2
                                                .getActivity()
                                                .findViewById(R.id.save_button);
                        if (secBottomBarButton != null) {
                            secBottomBarButton.setEnabled(false);
                        }
                        Menu menu = zenModeScheduleRuleSettings2.mMenuLandscape;
                        if (menu != null) {
                            MenuItem findItem = menu.findItem(R.id.action_done);
                            if (!zenModeScheduleRuleSettings2.mDaysEmpty
                                    && !zenModeScheduleRuleSettings2.mNameEmpty) {
                                z = true;
                            }
                            findItem.setEnabled(z);
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {}

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {}
                });
        if (this.mRepeatButtonGroup == null) {
            SecZenScheduleRepeatPreference secZenScheduleRepeatPreference =
                    (SecZenScheduleRepeatPreference)
                            ((LayoutPreference) findPreference("schedule_reapeat"))
                                    .mRootView.findViewById(R.id.sec_schedule_repeat);
            this.mRepeatButtonGroup = secZenScheduleRepeatPreference;
            secZenScheduleRepeatPreference.mRepeatButton =
                    (SecZenScheduleRepeatButton)
                            secZenScheduleRepeatPreference.findViewById(R.id.custom_repeat_btn);
            this.mRepeatButtonGroup.mRepeatButton.mRepeatClickListener = new AnonymousClass4();
        }
        SecPreference secPreference = (SecPreference) findPreference("schedule_time_start");
        this.mSecScheduleStart = secPreference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
        this.mSecScheduleStart.setOnPreferenceClickListener(new AnonymousClass4());
        if (!this.mIsNew) {
            SecButtonPreference secButtonPreference =
                    new SecButtonPreference(((ZenModeSettingsBase) this).mContext);
            secButtonPreference.mTitle = getResources().getString(R.string.schedule_delete_button);
            secButtonPreference.setDefaultRoundButtonStyle();
            secButtonPreference.mOnClickListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.notification.zen.ZenModeScheduleRuleSettings$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ZenModeScheduleRuleSettings zenModeScheduleRuleSettings =
                                    ZenModeScheduleRuleSettings.this;
                            int i = ZenModeScheduleRuleSettings.$r8$clinit;
                            zenModeScheduleRuleSettings.showConfirmDialog();
                        }
                    };
            getPreferenceScreen().addPreference(secButtonPreference);
        }
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        if (scheduleInfo != null) {
            SecZenScheduleRepeatPreference secZenScheduleRepeatPreference2 =
                    this.mRepeatButtonGroup;
            int[] iArr = scheduleInfo.days;
            SecZenScheduleRepeatButton secZenScheduleRepeatButton =
                    secZenScheduleRepeatPreference2.mRepeatButton;
            if (iArr == null) {
                secZenScheduleRepeatButton.clickRepeatButton();
                return;
            }
            secZenScheduleRepeatButton.getClass();
            for (int i : iArr) {
                int i2 =
                        SecZenScheduleRepeatButton.VALUES_DAYS[
                                ((i + 7) - Calendar.getInstance().getFirstDayOfWeek()) % 7];
                secZenScheduleRepeatButton.mRepeatBtn[i2].setChecked(true);
                secZenScheduleRepeatButton.mRepeatAnimatingView[i2].setVisibility(0);
                secZenScheduleRepeatButton.setSelectionMarkAnimator(i2, true);
                secZenScheduleRepeatButton.clickRepeatButton();
            }
        }
    }

    @Override // com.android.settings.notification.zen.ZenModeRuleSettingsBase,
              // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mNameEmpty = TextUtils.isEmpty(this.mRule.getName());
        int[] iArr = this.mSchedule.days;
        if (iArr == null) {
            this.mDaysEmpty = true;
        } else {
            this.mDaysEmpty = iArr.length == 0;
        }
        if (this.mRule.getName().length() > 20) {
            this.mNameEmpty = true;
        }
    }

    @Override // com.android.settings.notification.zen.ZenModeRuleSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        CustomTimePickerDialog customTimePickerDialog = this.mCustomTimePickerDialog;
        if (customTimePickerDialog != null) {
            bundle.putBoolean("isDialogShowing", customTimePickerDialog.isShowing());
        }
        AlertDialog alertDialog = this.mConfirmDialog;
        if (alertDialog != null) {
            bundle.putBoolean("isDeleteDialogShowing", alertDialog.isShowing());
        }
    }

    @Override // androidx.picker.app.SeslTimePickerDialog.OnTimeSetListener
    public final void onTimeSet(SeslTimePicker seslTimePicker, int i, int i2) {
        if (getActivity() != null) {
            int i3 = this.mSecScheduleTimeType;
            if (i3 == 1) {
                ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
                scheduleInfo.startHour = i;
                scheduleInfo.startMinute = i2;
            } else if (i3 == 2) {
                ZenModeConfig.ScheduleInfo scheduleInfo2 = this.mSchedule;
                scheduleInfo2.endHour = i;
                scheduleInfo2.endMinute = i2;
            }
            updateScheduleRule(this.mSchedule);
            SecPreference secPreference = this.mSecScheduleStart;
            if (secPreference != null) {
                secPreference.setSummary(updateSecScheduleStartSummary());
            }
        }
    }

    @Override // com.android.settings.notification.zen.ZenModeRuleSettingsBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (bundle != null && bundle.containsKey("isDialogShowing")) {
            this.mIsDialogShowing = bundle.getBoolean("isDialogShowing");
        }
        if (bundle != null && bundle.containsKey("isDeleteDialogShowing")) {
            this.mIsDeleteDialogShowing = bundle.getBoolean("isDeleteDialogShowing");
        }
        if (this.mCustomTimePickerDialog == null && this.mIsDialogShowing) {
            ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
            CustomTimePickerDialog customTimePickerDialog =
                    new CustomTimePickerDialog(
                            ((ZenModeSettingsBase) this).mContext,
                            (scheduleInfo.startHour * 60) + scheduleInfo.startMinute,
                            (scheduleInfo.endHour * 60) + scheduleInfo.endMinute,
                            new AnonymousClass4());
            this.mCustomTimePickerDialog = customTimePickerDialog;
            customTimePickerDialog.getWindow().setSoftInputMode(16);
            this.mCustomTimePickerDialog.show();
        }
        if (this.mConfirmDialog == null && this.mIsDeleteDialogShowing) {
            showConfirmDialog();
        }
    }

    @Override // com.android.settings.notification.zen.ZenModeRuleSettingsBase
    public final boolean setRule(AutomaticZenRule automaticZenRule) {
        ZenModeConfig.ScheduleInfo tryParseScheduleConditionId =
                automaticZenRule != null
                        ? ZenModeConfig.tryParseScheduleConditionId(
                                automaticZenRule.getConditionId())
                        : null;
        this.mSchedule = tryParseScheduleConditionId;
        return tryParseScheduleConditionId != null;
    }

    public final void showConfirmDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(((ZenModeSettingsBase) this).mContext);
        builder.setMessage(R.string.schedule_delete_dialog_title);
        builder.setPositiveButton(
                R.string.schedule_delete_dialog_delete,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.zen.ZenModeScheduleRuleSettings$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ZenModeScheduleRuleSettings zenModeScheduleRuleSettings =
                                ZenModeScheduleRuleSettings.this;
                        int i2 = ZenModeScheduleRuleSettings.$r8$clinit;
                        zenModeScheduleRuleSettings.mBackend.removeZenRule(
                                zenModeScheduleRuleSettings.mId);
                        zenModeScheduleRuleSettings.getActivity().finish();
                    }
                });
        builder.setNegativeButton(
                R.string.schedule_delete_dialog_cancel, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        this.mConfirmDialog = create;
        create.show();
        this.mConfirmDialog
                .getButton(-1)
                .setTextColor(
                        getContext().getColor(R.color.sec_dnd_schedule_delete_button_text_color));
    }

    @Override // com.android.settings.notification.zen.ZenModeRuleSettingsBase
    public final void updateControlsInternal() {
        SecPreference secPreference = this.mSecScheduleStart;
        if (secPreference != null) {
            secPreference.setSummary(updateSecScheduleStartSummary());
        }
    }

    public final String updateSecScheduleStartSummary() {
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        int i = scheduleInfo.startHour;
        int i2 = scheduleInfo.startMinute;
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(DateFormat.is24HourFormat(((ZenModeSettingsBase) this).mContext) ? 11 : 10, i);
        calendar.set(12, i2);
        sb.append(
                DateFormat.getTimeFormat(((ZenModeSettingsBase) this).mContext)
                        .format(new Date(calendar.getTimeInMillis())));
        ZenModeConfig.ScheduleInfo scheduleInfo2 = this.mSchedule;
        int i3 = scheduleInfo2.startHour;
        int i4 = scheduleInfo2.startMinute;
        int i5 = scheduleInfo2.endHour;
        int i6 = scheduleInfo2.endMinute;
        Calendar calendar2 = Calendar.getInstance();
        calendar2.clear();
        calendar2.set(
                DateFormat.is24HourFormat(((ZenModeSettingsBase) this).mContext) ? 11 : 10, i5);
        calendar2.set(12, i6);
        String format =
                DateFormat.getTimeFormat(((ZenModeSettingsBase) this).mContext)
                        .format(new Date(calendar2.getTimeInMillis()));
        if ((i3 * 60) + i4 >= (i5 * 60) + i6) {
            format =
                    ((ZenModeSettingsBase) this)
                            .mContext
                            .getResources()
                            .getString(R.string.zen_mode_end_time_next_day_summary_format, format);
        }
        sb.append(" - " + format);
        return sb.toString();
    }
}
