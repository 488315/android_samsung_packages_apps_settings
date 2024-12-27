package com.samsung.android.settings.accessibility.dexterity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SeslSwitchBar;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.PickerCompat;
import com.samsung.android.settings.accessibility.base.widget.PickerFragment;
import com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class IgnoreRepeatPreferenceFragment extends PickerFragment
        implements PickerCompat.setOnValueChangeListener, CompoundButton.OnCheckedChangeListener {
    public View mCircleCue;
    public Context mContext;
    public ImageView mIgnoreImageView;
    public final AnonymousClass1 mIgnoreRepeatObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.accessibility.dexterity.IgnoreRepeatPreferenceFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    Log.d("IgnoreRepeatPreferenceFragment", "onChange");
                    IgnoreRepeatPreferenceFragment ignoreRepeatPreferenceFragment =
                            IgnoreRepeatPreferenceFragment.this;
                    ignoreRepeatPreferenceFragment.mIgnoreRepeatPeriod =
                            Settings.Secure.getFloat(
                                    ignoreRepeatPreferenceFragment.mContext.getContentResolver(),
                                    "touch_blocking_period",
                                    0.1f);
                    IgnoreRepeatPreferenceFragment ignoreRepeatPreferenceFragment2 =
                            IgnoreRepeatPreferenceFragment.this;
                    float f = ignoreRepeatPreferenceFragment2.mIgnoreRepeatPeriod;
                    ignoreRepeatPreferenceFragment2.getClass();
                    Log.d("IgnoreRepeatPreferenceFragment", "updateSwitchBarToggleSwitch");
                    boolean isIgnoreRepeatEnabled =
                            IgnoreRepeatUtils.isIgnoreRepeatEnabled(
                                    ignoreRepeatPreferenceFragment2.mContext);
                    if (((SeslSwitchBar) ignoreRepeatPreferenceFragment2.switchBar)
                                    .mSwitch.isChecked()
                            != isIgnoreRepeatEnabled) {
                        ignoreRepeatPreferenceFragment2.switchBar.setCheckedInternal(
                                isIgnoreRepeatEnabled);
                    }
                }
            };
    public float mIgnoreRepeatPeriod;
    public ImageView mTestImageView;
    public SettingsMainSwitchBar switchBar;

    @Override // com.samsung.android.settings.accessibility.base.widget.PickerFragment
    public final int getLayoutId() {
        return R.layout.layout_touch_settings_picker;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5007;
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "touch_blocking_enabled", z ? 1 : 0);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                R.layout.layout_ignore_repeated_touches_circle_cue,
                                (ViewGroup) null);
        this.mCircleCue = inflate;
        this.mTestImageView = (ImageView) inflate.findViewById(R.id.tap);
        this.mIgnoreImageView = (ImageView) this.mCircleCue.findViewById(R.id.tap_ignore);
        this.mTestImageView.setVisibility(8);
        this.mIgnoreImageView.setVisibility(8);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.mMetricsFeatureProvider.clicked(5007, "A11Y0001");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getContext().getContentResolver().unregisterContentObserver(this.mIgnoreRepeatObserver);
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getContext()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("touch_blocking_enabled"),
                        true,
                        this.mIgnoreRepeatObserver);
        Log.d("IgnoreRepeatPreferenceFragment", "updateSwitchBarToggleSwitch");
        boolean isIgnoreRepeatEnabled = IgnoreRepeatUtils.isIgnoreRepeatEnabled(this.mContext);
        if (((SeslSwitchBar) this.switchBar).mSwitch.isChecked() != isIgnoreRepeatEnabled) {
            this.switchBar.setCheckedInternal(isIgnoreRepeatEnabled);
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.PickerCompat.setOnValueChangeListener
    public final void onValueChange(String str) {
        Context context = this.mContext;
        Settings.Secure.putFloat(
                context.getContentResolver(), "touch_blocking_period", Float.parseFloat(str));
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        setupPicker(Float.toString(IgnoreRepeatUtils.getIgnoreRepeatValue(this.mContext)), "4.0");
        this.mPicker.mValueChangeListener = this;
        this.mPickerDescription.setText(
                getString(R.string.accessibility_ignore_repeat_description));
        this.mPickerDescription.setMovementMethod(new ScrollingMovementMethod());
        this.mPickerDescription.setClickable(false);
        this.mPickerDescription.setLongClickable(false);
        setButton(
                getString(R.string.accessibility_common_button_try_it_out),
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.IgnoreRepeatPreferenceFragment$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(
                                        IgnoreRepeatPreferenceFragment.this.mContext);
                        String name = IgnoreRepeatedTouchesExperienceFragment.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        subSettingLauncher.setTitleRes(R.string.accessibility_ignore_repeat, null);
                        subSettingLauncher.addFlags(335544320);
                        launchRequest.mSourceMetricsCategory = 5007;
                        subSettingLauncher.launch();
                    }
                });
        SecAccessibilityUtils.setButtonsColor(this.mContext, this.mTestBtn);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (settingsActivity != null) {
            this.switchBar = settingsActivity.mMainSwitch;
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.switchBar;
        if (settingsMainSwitchBar != null) {
            setSwitchBarPadding(settingsMainSwitchBar);
            this.switchBar.setChecked(IgnoreRepeatUtils.isIgnoreRepeatEnabled(this.mContext));
            this.switchBar.addOnSwitchChangeListener(this);
            this.switchBar.setOnBeforeCheckedChangeListener(
                    new SettingsMainSwitchBar
                            .OnBeforeCheckedChangeListener() { // from class:
                                                               // com.samsung.android.settings.accessibility.dexterity.IgnoreRepeatPreferenceFragment$$ExternalSyntheticLambda0
                        @Override // com.android.settings.widget.SettingsMainSwitchBar.OnBeforeCheckedChangeListener
                        public final boolean onBeforeCheckedChanged(boolean z) {
                            final IgnoreRepeatPreferenceFragment ignoreRepeatPreferenceFragment =
                                    IgnoreRepeatPreferenceFragment.this;
                            if (!z) {
                                ignoreRepeatPreferenceFragment.getClass();
                                return false;
                            }
                            AlertDialog createExclusiveDialog =
                                    AccessibilityDialogUtils.createExclusiveDialog(
                                            ignoreRepeatPreferenceFragment.mContext,
                                            "ignore_repeated_touches",
                                            new DialogInterface
                                                    .OnClickListener() { // from class:
                                                                         // com.samsung.android.settings.accessibility.dexterity.IgnoreRepeatPreferenceFragment$$ExternalSyntheticLambda2
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i) {
                                                    IgnoreRepeatPreferenceFragment.this.switchBar
                                                            .setCheckedInternal(true);
                                                }
                                            },
                                            null);
                            if (createExclusiveDialog == null) {
                                return false;
                            }
                            createExclusiveDialog.show();
                            return true;
                        }
                    });
            this.switchBar.show();
        }
    }
}
