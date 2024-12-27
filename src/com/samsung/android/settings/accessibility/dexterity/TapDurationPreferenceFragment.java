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
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SeslSwitchBar;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.network.apn.ApnPreference$$ExternalSyntheticOutline0;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.accessibility.AccessibilityNotificationUtil;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.PickerCompat;
import com.samsung.android.settings.accessibility.base.widget.PickerFragment;
import com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TapDurationPreferenceFragment extends PickerFragment
        implements PickerCompat.setOnValueChangeListener, CompoundButton.OnCheckedChangeListener {
    public AlertDialog mAlertDialog;
    public Context mContext;
    public final AnonymousClass1 mTapDurationObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.accessibility.dexterity.TapDurationPreferenceFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    Log.d("TapDurationPreferenceFragment", "onChange");
                    TapDurationPreferenceFragment tapDurationPreferenceFragment =
                            TapDurationPreferenceFragment.this;
                    tapDurationPreferenceFragment.mTapDurationPeriod =
                            Settings.Secure.getFloat(
                                    tapDurationPreferenceFragment.mContext.getContentResolver(),
                                    "tap_duration_threshold",
                                    0.1f);
                    TapDurationPreferenceFragment tapDurationPreferenceFragment2 =
                            TapDurationPreferenceFragment.this;
                    float f = tapDurationPreferenceFragment2.mTapDurationPeriod;
                    tapDurationPreferenceFragment2.getClass();
                    Log.d("TapDurationPreferenceFragment", "updateSwitchBarToggleSwitch");
                    boolean isTapDurationEnabled =
                            TapDurationUtils.isTapDurationEnabled(
                                    tapDurationPreferenceFragment2.mContext);
                    if (((SeslSwitchBar) tapDurationPreferenceFragment2.switchBar)
                                    .mSwitch.isChecked()
                            != isTapDurationEnabled) {
                        tapDurationPreferenceFragment2.switchBar.setCheckedInternal(
                                isTapDurationEnabled);
                    }
                }
            };
    public float mTapDurationPeriod;
    public SettingsMainSwitchBar switchBar;

    @Override // com.samsung.android.settings.accessibility.base.widget.PickerFragment
    public final int getLayoutId() {
        return R.layout.layout_touch_settings_picker;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5006;
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
        if (z) {
            this.mAlertDialog.show();
            return;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getA11ySettingsMetricsFeatureProvider()
                .action(
                        5006,
                        "A11Y5017",
                        Map.of(
                                "From",
                                WizardManagerHelper.isUserSetupComplete(this.mContext)
                                        ? "Settings"
                                        : "SetupWizard"));
        Context context = this.mContext;
        Settings.Secure.putInt(context.getContentResolver(), "tap_duration_enabled", 0);
        AccessibilityNotificationUtil.updateTapDurationNotification(context);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.mMetricsFeatureProvider.clicked(5006, "A11Y0001");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getContext().getContentResolver().unregisterContentObserver(this.mTapDurationObserver);
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mAlertDialog.dismiss();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("tap_duration_enabled"),
                        true,
                        this.mTapDurationObserver);
        Log.d("TapDurationPreferenceFragment", "updateSwitchBarToggleSwitch");
        boolean isTapDurationEnabled = TapDurationUtils.isTapDurationEnabled(this.mContext);
        if (((SeslSwitchBar) this.switchBar).mSwitch.isChecked() != isTapDurationEnabled) {
            this.switchBar.setCheckedInternal(isTapDurationEnabled);
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.PickerCompat.setOnValueChangeListener
    public final void onValueChange(String str) {
        Context context = this.mContext;
        Settings.Secure.putFloat(
                context.getContentResolver(), "tap_duration_threshold", Float.parseFloat(str));
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        this.mPickerDescription.setText(getString(R.string.tap_duration_desc));
        this.mPickerDescription.setMovementMethod(new ScrollingMovementMethod());
        this.mPickerDescription.setClickable(false);
        this.mPickerDescription.setLongClickable(false);
        setButton(
                getString(R.string.accessibility_common_button_try_it_out),
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.TapDurationPreferenceFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(TapDurationPreferenceFragment.this.mContext);
                        String name = TapDurationExperienceFragment.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        subSettingLauncher.setTitleRes(R.string.tap_duration_title, null);
                        subSettingLauncher.addFlags(335544320);
                        launchRequest.mSourceMetricsCategory = 5006;
                        subSettingLauncher.launch();
                        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                        if (featureFactoryImpl == null) {
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                        featureFactoryImpl
                                .getA11ySettingsMetricsFeatureProvider()
                                .clicked(5006, "A11Y5034");
                    }
                });
        SecAccessibilityUtils.setButtonsColor(this.mContext, this.mTestBtn);
        setupPicker(Float.toString(TapDurationUtils.getTapDurationValue(this.mContext)), "4.0");
        this.mPicker.mValueChangeListener = this;
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (settingsActivity != null) {
            this.switchBar = settingsActivity.mMainSwitch;
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.switchBar;
        if (settingsMainSwitchBar != null) {
            setSwitchBarPadding(settingsMainSwitchBar);
            this.switchBar.setChecked(TapDurationUtils.isTapDurationEnabled(this.mContext));
            this.switchBar.addOnSwitchChangeListener(this);
            this.switchBar.show();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        String string = this.mContext.getString(R.string.tap_duration_turn_on_popup_title);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        alertParams.mMessage = this.mContext.getString(R.string.tap_duration_turn_on_popup_message);
        final int i = 0;
        builder.setPositiveButton(
                R.string.tap_duration_turn_on_popup_positive_button_keep_on,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.dexterity.TapDurationPreferenceFragment$$ExternalSyntheticLambda1
                    public final /* synthetic */ TapDurationPreferenceFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        int i3 = i;
                        TapDurationPreferenceFragment tapDurationPreferenceFragment = this.f$0;
                        switch (i3) {
                            case 0:
                                Context context = tapDurationPreferenceFragment.mContext;
                                Settings.Secure.putInt(
                                        context.getContentResolver(), "tap_duration_enabled", 1);
                                AccessibilityNotificationUtil.updateTapDurationNotification(
                                        context);
                                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                                if (featureFactoryImpl == null) {
                                    throw new UnsupportedOperationException(
                                            "No feature factory configured");
                                }
                                featureFactoryImpl
                                        .getA11ySettingsMetricsFeatureProvider()
                                        .action(
                                                5006,
                                                "A11Y5017",
                                                Map.of(
                                                        "From",
                                                        WizardManagerHelper.isUserSetupComplete(
                                                                        tapDurationPreferenceFragment
                                                                                .mContext)
                                                                ? "Settings"
                                                                : "SetupWizard"));
                                return;
                            default:
                                Context context2 = tapDurationPreferenceFragment.mContext;
                                ApnPreference$$ExternalSyntheticOutline0.m(
                                        context2,
                                        R.string.tap_duration_turn_on_popup_cancel_toast_message,
                                        context2,
                                        0);
                                tapDurationPreferenceFragment.switchBar.setCheckedInternal(false);
                                return;
                        }
                    }
                });
        final int i2 = 1;
        builder.setNegativeButton(
                R.string.accessibility_turn_off,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.dexterity.TapDurationPreferenceFragment$$ExternalSyntheticLambda1
                    public final /* synthetic */ TapDurationPreferenceFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i22) {
                        int i3 = i2;
                        TapDurationPreferenceFragment tapDurationPreferenceFragment = this.f$0;
                        switch (i3) {
                            case 0:
                                Context context = tapDurationPreferenceFragment.mContext;
                                Settings.Secure.putInt(
                                        context.getContentResolver(), "tap_duration_enabled", 1);
                                AccessibilityNotificationUtil.updateTapDurationNotification(
                                        context);
                                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                                if (featureFactoryImpl == null) {
                                    throw new UnsupportedOperationException(
                                            "No feature factory configured");
                                }
                                featureFactoryImpl
                                        .getA11ySettingsMetricsFeatureProvider()
                                        .action(
                                                5006,
                                                "A11Y5017",
                                                Map.of(
                                                        "From",
                                                        WizardManagerHelper.isUserSetupComplete(
                                                                        tapDurationPreferenceFragment
                                                                                .mContext)
                                                                ? "Settings"
                                                                : "SetupWizard"));
                                return;
                            default:
                                Context context2 = tapDurationPreferenceFragment.mContext;
                                ApnPreference$$ExternalSyntheticOutline0.m(
                                        context2,
                                        R.string.tap_duration_turn_on_popup_cancel_toast_message,
                                        context2,
                                        0);
                                tapDurationPreferenceFragment.switchBar.setCheckedInternal(false);
                                return;
                        }
                    }
                });
        alertParams.mCancelable = true;
        alertParams.mOnCancelListener =
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.accessibility.dexterity.TapDurationPreferenceFragment$$ExternalSyntheticLambda3
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        TapDurationPreferenceFragment tapDurationPreferenceFragment =
                                TapDurationPreferenceFragment.this;
                        Context context = tapDurationPreferenceFragment.mContext;
                        ApnPreference$$ExternalSyntheticOutline0.m(
                                context,
                                R.string.tap_duration_turn_on_popup_cancel_toast_message,
                                context,
                                0);
                        tapDurationPreferenceFragment.switchBar.setCheckedInternal(false);
                    }
                };
        this.mAlertDialog = builder.create();
    }
}
