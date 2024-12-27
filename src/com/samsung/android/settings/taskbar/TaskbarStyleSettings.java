package com.samsung.android.settings.taskbar;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.widget.LayoutPreference;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.settings.widget.SecRadioButtonPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TaskbarStyleSettings extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener {
    public LottieAnimationView animationView;
    public LinearLayout animationViewContainer;
    public SecRadioButtonPreference fixedTaskbar;
    public SecRadioButtonPreference floatingTaskbar;
    public LayoutPreference helpContainerPref;
    public ImageView stayOnScreenImageView;
    public int taskbarStyleValue;
    public final Uri TASK_BAR_REFRESH_URI = Settings.Global.getUriFor("task_bar_type");
    public final AnonymousClass1 mRefreshObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.taskbar.TaskbarStyleSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    Log.i("TaskBarStyleSettings", "onChange: refresh taskbar style");
                    TaskbarStyleSettings taskbarStyleSettings = TaskbarStyleSettings.this;
                    int i =
                            Settings.Global.getInt(
                                    taskbarStyleSettings.getContentResolver(), "task_bar_type", 0);
                    taskbarStyleSettings.taskbarStyleValue = i;
                    taskbarStyleSettings.setCheckedValue(Integer.valueOf(i));
                    taskbarStyleSettings.changeVIIfRequired(
                            Integer.valueOf(taskbarStyleSettings.taskbarStyleValue));
                }
            };

    public final void changeVIIfRequired(Integer num) {
        if (num.intValue() != 0) {
            this.animationView.setVisibility(8);
            this.stayOnScreenImageView.setVisibility(0);
            this.stayOnScreenImageView.setImageResource(R.drawable.taskbar_settings_fixed);
        } else {
            this.stayOnScreenImageView.setVisibility(8);
            this.animationView.setVisibility(0);
            if ((getContext().getResources().getConfiguration().uiMode & 48) == 32) {
                this.animationView.setAnimation("Task_Bar_Help_VI_floating_bar_dark.json");
            } else {
                this.animationView.setAnimation("Task_Bar_Help_VI_floating_bar_light.json");
            }
            this.animationView.playAnimation$1();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (TaskBarUtils.isFrontDisplay(getContext())
                || TaskBarUtils.isSmartViewEnabled(getContext())) {
            finish();
        }
        addPreferencesFromResource(R.xml.sec_taskbar_style_preference_container);
        this.helpContainerPref = (LayoutPreference) findPreference("task_bar_style_help_preview");
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference) findPreference("auto_hide_taskbar_on");
        this.floatingTaskbar = secRadioButtonPreference;
        if (secRadioButtonPreference != null) {
            secRadioButtonPreference.mListener = this;
        }
        SecRadioButtonPreference secRadioButtonPreference2 =
                (SecRadioButtonPreference) findPreference("stay_on_screen");
        this.fixedTaskbar = secRadioButtonPreference2;
        if (secRadioButtonPreference2 != null) {
            secRadioButtonPreference2.mListener = this;
        }
        this.animationViewContainer =
                (LinearLayout)
                        this.helpContainerPref.mRootView.findViewById(R.id.vi_container_for_style);
        this.animationView =
                (LottieAnimationView)
                        this.helpContainerPref.mRootView.findViewById(R.id.vi_taskbar_style);
        this.stayOnScreenImageView =
                (ImageView)
                        this.helpContainerPref.mRootView.findViewById(
                                R.id.image_view_for_stay_on_screen);
        LinearLayout linearLayout = this.animationViewContainer;
        if (linearLayout != null) {
            linearLayout.semSetRoundedCorners(15);
            linearLayout.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor, null));
        }
        this.floatingTaskbar.setSummary(R.string.floating_taskbar_help_summary);
        int i = Settings.Global.getInt(getContentResolver(), "task_bar_type", 0);
        this.taskbarStyleValue = i;
        setCheckedValue(Integer.valueOf(i));
        changeVIIfRequired(Integer.valueOf(this.taskbarStyleValue));
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        if (secRadioButtonPreference.equals(this.floatingTaskbar)) {
            Settings.Global.putInt(getContentResolver(), "task_bar_type", 0);
            setCheckedValue(0);
            changeVIIfRequired(0);
        } else {
            Settings.Global.putInt(getContentResolver(), "task_bar_type", 1);
            setCheckedValue(1);
            changeVIIfRequired(1);
        }
        this.floatingTaskbar.setSummary(R.string.floating_taskbar_help_summary);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        getContext()
                .getContentResolver()
                .registerContentObserver(this.TASK_BAR_REFRESH_URI, false, this.mRefreshObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getContext().getContentResolver().unregisterContentObserver(this.mRefreshObserver);
    }

    public final void setCheckedValue(Integer num) {
        Log.i("TaskBarStyleSettings", "setChecked value:" + num);
        if (num.intValue() == 1) {
            this.fixedTaskbar.setChecked(true);
            this.floatingTaskbar.setChecked(false);
        } else {
            this.fixedTaskbar.setChecked(false);
            this.floatingTaskbar.setChecked(true);
        }
    }
}
