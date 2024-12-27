package com.samsung.android.settings.accessibility.dexterity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.BodyContainedButton;
import com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchAndHoldFragment extends InstrumentedFragment {
    public Context mContext;
    public RadioButton mCustomRadioButton;
    public TextView mDescription;
    public RadioButton mLongRadioButton;
    public RadioButton mMediumRadioButton;
    public View mRootView;
    public RadioButton mShortRadioButton;
    public BodyContainedButton mTryButton;
    public RadioButton mVeryShortRadioButton;
    public final AnonymousClass1 radioGroupButtonChangeListener =
            new RadioGroup
                    .OnCheckedChangeListener() { // from class:
                                                 // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldFragment.1
                @Override // android.widget.RadioGroup.OnCheckedChangeListener
                public final void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.very_short_option) {
                        TouchAndHoldFragment.this.mVeryShortRadioButton.setChecked(true);
                        Settings.Secure.putInt(
                                TouchAndHoldFragment.this.mContext.getContentResolver(),
                                "long_press_timeout",
                                300);
                        return;
                    }
                    if (i == R.id.short_option) {
                        TouchAndHoldFragment.this.mShortRadioButton.setChecked(true);
                        Settings.Secure.putInt(
                                TouchAndHoldFragment.this.mContext.getContentResolver(),
                                "long_press_timeout",
                                500);
                        return;
                    }
                    if (i == R.id.medium_option) {
                        TouchAndHoldFragment.this.mMediumRadioButton.setChecked(true);
                        Settings.Secure.putInt(
                                TouchAndHoldFragment.this.mContext.getContentResolver(),
                                "long_press_timeout",
                                1000);
                    } else if (i == R.id.long_option) {
                        TouchAndHoldFragment.this.mLongRadioButton.setChecked(true);
                        Settings.Secure.putInt(
                                TouchAndHoldFragment.this.mContext.getContentResolver(),
                                "long_press_timeout",
                                1500);
                    } else if (i == R.id.custom_option) {
                        TouchAndHoldFragment.this.mCustomRadioButton.setChecked(true);
                    }
                }
            };

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5005;
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int applyDimension =
                (int)
                        TypedValue.applyDimension(
                                0,
                                getResources()
                                        .getDimension(
                                                R.dimen.touch_settings_bottom_btn_bottom_padding),
                                getResources().getDisplayMetrics());
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) this.mTryButton.getLayoutParams();
        marginLayoutParams.setMargins(
                marginLayoutParams.leftMargin,
                marginLayoutParams.topMargin,
                marginLayoutParams.rightMargin,
                applyDimension);
        this.mTryButton.setLayoutParams(marginLayoutParams);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_touch_and_hold, viewGroup, false);
        this.mRootView = inflate;
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.mMetricsFeatureProvider.clicked(5005, "A11Y0001");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        LinearLayout linearLayout =
                (LinearLayout) this.mRootView.findViewById(R.id.radio_group_container);
        linearLayout.semSetRoundedCorners(15);
        linearLayout.semSetRoundedCornerColor(
                15, this.mContext.getColor(R.color.rounded_corner_color));
        ((RadioGroup) this.mRootView.findViewById(R.id.radio_group))
                .setOnCheckedChangeListener(this.radioGroupButtonChangeListener);
        BodyContainedButton bodyContainedButton =
                (BodyContainedButton) this.mRootView.findViewById(R.id.try_button);
        this.mTryButton = bodyContainedButton;
        final int i = 0;
        bodyContainedButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ TouchAndHoldFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i2 = i;
                        TouchAndHoldFragment touchAndHoldFragment = this.f$0;
                        switch (i2) {
                            case 0:
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(touchAndHoldFragment.mContext);
                                String name = TouchHoldDelayExperienceFragment.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mDestinationName = name;
                                subSettingLauncher.setTitleRes(
                                        R.string.touch_and_hold_delay_header, null);
                                subSettingLauncher.addFlags(335544320);
                                launchRequest.mSourceMetricsCategory = 5005;
                                subSettingLauncher.launch();
                                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                                if (featureFactoryImpl == null) {
                                    throw new UnsupportedOperationException(
                                            "No feature factory configured");
                                }
                                featureFactoryImpl
                                        .getA11ySettingsMetricsFeatureProvider()
                                        .clicked(5005, "A11Y5015");
                                return;
                            default:
                                SubSettingLauncher subSettingLauncher2 =
                                        new SubSettingLauncher(touchAndHoldFragment.getContext());
                                SubSettingLauncher.LaunchRequest launchRequest2 =
                                        subSettingLauncher2.mLaunchRequest;
                                launchRequest2.mSourceMetricsCategory = 5005;
                                launchRequest2.mDestinationName =
                                        TouchAndHoldCustomFragment.class.getCanonicalName();
                                subSettingLauncher2.setTitleRes(
                                        R.string.longpress_custom_button, null);
                                subSettingLauncher2.launch();
                                return;
                        }
                    }
                });
        this.mVeryShortRadioButton =
                (RadioButton) linearLayout.findViewById(R.id.very_short_option);
        this.mShortRadioButton = (RadioButton) linearLayout.findViewById(R.id.short_option);
        this.mMediumRadioButton = (RadioButton) linearLayout.findViewById(R.id.medium_option);
        this.mLongRadioButton = (RadioButton) linearLayout.findViewById(R.id.long_option);
        this.mCustomRadioButton = (RadioButton) linearLayout.findViewById(R.id.custom_option);
        SecAccessibilityUtils.setButtonsColor(this.mContext, this.mTryButton);
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "long_press_timeout", 500)
                == 300) {
            this.mVeryShortRadioButton.setChecked(true);
        } else if (Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "long_press_timeout", 500)
                == 500) {
            this.mShortRadioButton.setChecked(true);
        } else if (Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "long_press_timeout", 500)
                == 1000) {
            this.mMediumRadioButton.setChecked(true);
        } else if (Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "long_press_timeout", 500)
                == 1500) {
            this.mLongRadioButton.setChecked(true);
        } else {
            this.mCustomRadioButton.setChecked(true);
        }
        this.mDescription = (TextView) this.mRootView.findViewById(R.id.description);
        final int i2 = 1;
        this.mCustomRadioButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ TouchAndHoldFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i22 = i2;
                        TouchAndHoldFragment touchAndHoldFragment = this.f$0;
                        switch (i22) {
                            case 0:
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(touchAndHoldFragment.mContext);
                                String name = TouchHoldDelayExperienceFragment.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mDestinationName = name;
                                subSettingLauncher.setTitleRes(
                                        R.string.touch_and_hold_delay_header, null);
                                subSettingLauncher.addFlags(335544320);
                                launchRequest.mSourceMetricsCategory = 5005;
                                subSettingLauncher.launch();
                                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                                if (featureFactoryImpl == null) {
                                    throw new UnsupportedOperationException(
                                            "No feature factory configured");
                                }
                                featureFactoryImpl
                                        .getA11ySettingsMetricsFeatureProvider()
                                        .clicked(5005, "A11Y5015");
                                return;
                            default:
                                SubSettingLauncher subSettingLauncher2 =
                                        new SubSettingLauncher(touchAndHoldFragment.getContext());
                                SubSettingLauncher.LaunchRequest launchRequest2 =
                                        subSettingLauncher2.mLaunchRequest;
                                launchRequest2.mSourceMetricsCategory = 5005;
                                launchRequest2.mDestinationName =
                                        TouchAndHoldCustomFragment.class.getCanonicalName();
                                subSettingLauncher2.setTitleRes(
                                        R.string.longpress_custom_button, null);
                                subSettingLauncher2.launch();
                                return;
                        }
                    }
                });
    }
}
