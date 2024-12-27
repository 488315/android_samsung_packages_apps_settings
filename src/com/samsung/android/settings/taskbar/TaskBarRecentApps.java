package com.samsung.android.settings.taskbar;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TaskBarRecentApps extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public LinearLayout mButtonFour;
    public LinearLayout mButtonThree;
    public LinearLayout mButtonTwo;
    public LayoutPreference mMainContainerPref;
    public RadioButton mRadioButtonFour;
    public RadioButton mRadioButtonThree;
    public RadioButton mRadioButtonTwo;
    public LinearLayout mRadioGroupContainer;
    public SettingsMainSwitchBar mRecentAppsEnabledSwitch;
    public TextView mTaskbarRecentAppSummary;
    public int mTaskbarRecentAppsCount;
    public TextView mTextViewFour;
    public TextView mTextViewThree;
    public TextView mTextViewTwo;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    public final void isGroupEnabled(boolean z) {
        if (!z) {
            this.mButtonTwo.setEnabled(false);
            this.mButtonThree.setEnabled(false);
            this.mButtonFour.setEnabled(false);
            this.mRadioGroupContainer.setAlpha(0.3f);
            return;
        }
        if (this.mRadioGroupContainer != null) {
            this.mButtonTwo.setEnabled(true);
            this.mButtonThree.setEnabled(true);
            this.mButtonFour.setEnabled(true);
            this.mRadioGroupContainer.setAlpha(1.0f);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mRecentAppsEnabledSwitch = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(
                    Settings.Global.getInt(getContentResolver(), "taskbar_recent_apps_enabled", 1)
                            == 1);
            this.mRecentAppsEnabledSwitch.addOnSwitchChangeListener(this);
            this.mRecentAppsEnabledSwitch.show();
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Log.i("TaskBarRecentAppsActivity", "TaskBar Recent Apps Switch is clicked to -> " + z);
        Settings.Global.putInt(getContentResolver(), "taskbar_recent_apps_enabled", z ? 1 : 0);
        isGroupEnabled(
                Settings.Global.getInt(getContentResolver(), "taskbar_recent_apps_enabled", 1)
                        == 1);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        final int i = 0;
        final int i2 = 1;
        final int i3 = 2;
        super.onCreate(bundle);
        if (TaskBarUtils.isFrontDisplay(getContext())
                || TaskBarUtils.isSmartViewEnabled(getContext())) {
            finish();
        }
        addPreferencesFromResource(R.xml.sec_taskbar_recent_apps_container);
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("task_bar_recent_radio_container");
        this.mMainContainerPref = layoutPreference;
        this.mRadioGroupContainer =
                (LinearLayout) layoutPreference.mRootView.findViewById(R.id.radio_group_container);
        this.mTaskbarRecentAppSummary =
                (TextView)
                        this.mMainContainerPref.mRootView.findViewById(
                                R.id.help_instruction_recent_app_summary);
        this.mButtonTwo =
                (LinearLayout) this.mMainContainerPref.mRootView.findViewById(R.id.rb_two_apps);
        this.mButtonThree =
                (LinearLayout) this.mMainContainerPref.mRootView.findViewById(R.id.rb_three_apps);
        this.mButtonFour =
                (LinearLayout) this.mMainContainerPref.mRootView.findViewById(R.id.rb_four_apps);
        this.mRadioButtonTwo =
                (RadioButton)
                        this.mMainContainerPref.mRootView.findViewById(R.id.rb_two_apps_radio);
        this.mRadioButtonThree =
                (RadioButton)
                        this.mMainContainerPref.mRootView.findViewById(R.id.rb_three_apps_radio);
        this.mRadioButtonFour =
                (RadioButton)
                        this.mMainContainerPref.mRootView.findViewById(R.id.rb_four_apps_radio);
        this.mTextViewTwo =
                (TextView)
                        this.mMainContainerPref.mRootView.findViewById(R.id.rb_two_apps_textview);
        this.mTextViewThree =
                (TextView)
                        this.mMainContainerPref.mRootView.findViewById(R.id.rb_three_apps_textview);
        this.mTextViewFour =
                (TextView)
                        this.mMainContainerPref.mRootView.findViewById(R.id.rb_four_apps_textview);
        this.mTaskbarRecentAppSummary.setText(
                getPrefContext().getResources().getString(R.string.taskbar_recent_app_summary)
                        + " "
                        + getPrefContext()
                                .getResources()
                                .getString(R.string.taskbar_recent_app_summary_2));
        this.mTextViewTwo.setText(
                getPrefContext()
                        .getResources()
                        .getQuantityString(R.plurals.number_of_recent_apps, 2, 2));
        this.mTextViewThree.setText(
                getPrefContext()
                        .getResources()
                        .getQuantityString(R.plurals.number_of_recent_apps, 3, 3));
        this.mTextViewFour.setText(
                getPrefContext()
                        .getResources()
                        .getQuantityString(R.plurals.number_of_recent_apps, 4, 4));
        this.mRadioGroupContainer.semSetRoundedCorners(15);
        this.mRadioGroupContainer.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor, null));
        isGroupEnabled(
                Settings.Global.getInt(getContentResolver(), "taskbar_recent_apps_enabled", 1)
                        == 1);
        Context prefContext = getPrefContext();
        StringBuilder sb = Utils.sBuilder;
        if (!TextUtils.isEmpty(
                Settings.System.getString(
                        prefContext.getContentResolver(), "current_sec_active_themepackage"))) {
            this.mTextViewFour.setTextColor(
                    com.android.settingslib.Utils.getColorAttr(
                            getPrefContext(), android.R.attr.textColorPrimary));
            this.mTextViewThree.setTextColor(
                    com.android.settingslib.Utils.getColorAttr(
                            getPrefContext(), android.R.attr.textColorPrimary));
            this.mTextViewTwo.setTextColor(
                    com.android.settingslib.Utils.getColorAttr(
                            getPrefContext(), android.R.attr.textColorPrimary));
        }
        this.mTaskbarRecentAppsCount =
                Settings.Global.getInt(getContentResolver(), "taskbar_max_recent_count", 2);
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("apps value : "),
                this.mTaskbarRecentAppsCount,
                "TaskBarRecentAppsActivity");
        int i4 = this.mTaskbarRecentAppsCount;
        if (i4 == 3) {
            this.mRadioButtonThree.setChecked(true);
        } else if (i4 == 4) {
            this.mRadioButtonFour.setChecked(true);
        } else {
            this.mRadioButtonTwo.setChecked(true);
        }
        this.mButtonTwo.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.taskbar.TaskBarRecentApps.1
                    public final /* synthetic */ TaskBarRecentApps this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                Settings.Global.putInt(
                                        this.this$0.getContentResolver(),
                                        "taskbar_max_recent_count",
                                        2);
                                this.this$0.mRadioButtonTwo.setChecked(true);
                                this.this$0.mRadioButtonThree.setChecked(false);
                                this.this$0.mRadioButtonFour.setChecked(false);
                                break;
                            case 1:
                                Settings.Global.putInt(
                                        this.this$0.getContentResolver(),
                                        "taskbar_max_recent_count",
                                        3);
                                this.this$0.mRadioButtonTwo.setChecked(false);
                                this.this$0.mRadioButtonThree.setChecked(true);
                                this.this$0.mRadioButtonFour.setChecked(false);
                                break;
                            default:
                                Settings.Global.putInt(
                                        this.this$0.getContentResolver(),
                                        "taskbar_max_recent_count",
                                        4);
                                this.this$0.mRadioButtonTwo.setChecked(false);
                                this.this$0.mRadioButtonThree.setChecked(false);
                                this.this$0.mRadioButtonFour.setChecked(true);
                                break;
                        }
                    }
                });
        this.mButtonThree.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.taskbar.TaskBarRecentApps.1
                    public final /* synthetic */ TaskBarRecentApps this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                Settings.Global.putInt(
                                        this.this$0.getContentResolver(),
                                        "taskbar_max_recent_count",
                                        2);
                                this.this$0.mRadioButtonTwo.setChecked(true);
                                this.this$0.mRadioButtonThree.setChecked(false);
                                this.this$0.mRadioButtonFour.setChecked(false);
                                break;
                            case 1:
                                Settings.Global.putInt(
                                        this.this$0.getContentResolver(),
                                        "taskbar_max_recent_count",
                                        3);
                                this.this$0.mRadioButtonTwo.setChecked(false);
                                this.this$0.mRadioButtonThree.setChecked(true);
                                this.this$0.mRadioButtonFour.setChecked(false);
                                break;
                            default:
                                Settings.Global.putInt(
                                        this.this$0.getContentResolver(),
                                        "taskbar_max_recent_count",
                                        4);
                                this.this$0.mRadioButtonTwo.setChecked(false);
                                this.this$0.mRadioButtonThree.setChecked(false);
                                this.this$0.mRadioButtonFour.setChecked(true);
                                break;
                        }
                    }
                });
        this.mButtonFour.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.taskbar.TaskBarRecentApps.1
                    public final /* synthetic */ TaskBarRecentApps this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i3) {
                            case 0:
                                Settings.Global.putInt(
                                        this.this$0.getContentResolver(),
                                        "taskbar_max_recent_count",
                                        2);
                                this.this$0.mRadioButtonTwo.setChecked(true);
                                this.this$0.mRadioButtonThree.setChecked(false);
                                this.this$0.mRadioButtonFour.setChecked(false);
                                break;
                            case 1:
                                Settings.Global.putInt(
                                        this.this$0.getContentResolver(),
                                        "taskbar_max_recent_count",
                                        3);
                                this.this$0.mRadioButtonTwo.setChecked(false);
                                this.this$0.mRadioButtonThree.setChecked(true);
                                this.this$0.mRadioButtonFour.setChecked(false);
                                break;
                            default:
                                Settings.Global.putInt(
                                        this.this$0.getContentResolver(),
                                        "taskbar_max_recent_count",
                                        4);
                                this.this$0.mRadioButtonTwo.setChecked(false);
                                this.this$0.mRadioButtonThree.setChecked(false);
                                this.this$0.mRadioButtonFour.setChecked(true);
                                break;
                        }
                    }
                });
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mRecentAppsEnabledSwitch;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mRecentAppsEnabledSwitch.hide();
        }
    }
}
