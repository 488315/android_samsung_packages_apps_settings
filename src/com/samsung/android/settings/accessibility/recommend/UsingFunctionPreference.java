package com.samsung.android.settings.accessibility.recommend;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.cube.ControlValue;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class UsingFunctionPreference extends Preference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CharSequence announceText;
    public final CharSequence buttonContentDescription;
    public final CharSequence buttonText;
    public final UsingFunctionItem item;

    public UsingFunctionPreference(Context context, UsingFunctionItem usingFunctionItem) {
        super(context);
        this.buttonText = null;
        this.buttonContentDescription = null;
        this.announceText = null;
        this.item = usingFunctionItem;
        setLayoutResource(R.layout.preference_using_function);
        this.buttonContentDescription =
                usingFunctionItem.usingFunction.getActionButtonDescription(context);
        int usingFunctionType = usingFunctionItem.usingFunction.getUsingFunctionType();
        CharSequence usingFunctionTitle =
                usingFunctionItem.usingFunction.getUsingFunctionTitle(context);
        if (usingFunctionType == 2) {
            this.buttonText = context.getText(R.string.using_functions_set_balance);
            this.announceText =
                    context.getString(
                            R.string.using_functions_announce_set_balance, usingFunctionTitle);
        } else if (usingFunctionType != 101) {
            this.buttonText = context.getText(R.string.accessibility_turn_off);
            if (this.buttonContentDescription == null) {
                this.buttonContentDescription =
                        context.getString(
                                R.string.using_functions_turn_off_button_contentDesc,
                                usingFunctionTitle);
            }
            this.announceText =
                    context.getString(
                            R.string.using_functions_announce_turn_off, usingFunctionTitle);
        } else {
            this.buttonText = context.getText(R.string.using_functions_set_default);
            if (this.buttonContentDescription == null) {
                this.buttonContentDescription =
                        context.getString(
                                R.string.using_functions_set_to_default_button_contentDesc,
                                usingFunctionTitle);
            }
            this.announceText =
                    context.getString(
                            R.string.using_functions_announce_set_default, usingFunctionTitle);
        }
        if (usingFunctionType == 200) {
            setTitle(context.getString(R.string.accessibility_shortcut_title, usingFunctionTitle));
        } else {
            setTitle(usingFunctionTitle);
        }
        AccessibilityUsingFunction accessibilityUsingFunction = usingFunctionItem.usingFunction;
        Bundle m =
                AbsAdapter$1$$ExternalSyntheticOutline0.m(
                        ":settings:fragment_args_key",
                        accessibilityUsingFunction.getUsingFunctionHighlightKey() != null
                                ? accessibilityUsingFunction.getUsingFunctionHighlightKey()
                                : usingFunctionItem.key);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String fragmentClassName = accessibilityUsingFunction.getFragmentClassName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = fragmentClassName;
        launchRequest.mArguments = m;
        launchRequest.mSourceMetricsCategory = 0;
        setIntent(subSettingLauncher.toIntent());
        setIcon(usingFunctionItem.usingFunction.getIcon(context));
        setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.samsung.android.settings.accessibility.recommend.UsingFunctionPreference$$ExternalSyntheticLambda2
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        int i = UsingFunctionPreference.$r8$clinit;
                        UsingFunctionPreference usingFunctionPreference =
                                UsingFunctionPreference.this;
                        usingFunctionPreference.getClass();
                        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                        if (featureFactoryImpl == null) {
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                        featureFactoryImpl
                                .getA11ySettingsMetricsFeatureProvider()
                                .action(
                                        1003,
                                        "A11Y1009",
                                        Map.of(
                                                "function",
                                                usingFunctionPreference.item.usingFunction
                                                        .getFunctionName()));
                        return false;
                    }
                });
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Button button = (Button) preferenceViewHolder.itemView.findViewById(R.id.turn_off_button);
        if (button != null) {
            button.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.accessibility.recommend.UsingFunctionPreference$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            UsingFunctionPreference usingFunctionPreference =
                                    UsingFunctionPreference.this;
                            UsingFunctionItem usingFunctionItem = usingFunctionPreference.item;
                            Context context = usingFunctionPreference.getContext();
                            String str = usingFunctionItem.key;
                            BasePreferenceController basePreferenceController =
                                    usingFunctionItem.controllable;
                            ControlValue.Builder builder =
                                    new ControlValue.Builder(
                                            str, basePreferenceController.getControlType());
                            builder.setValue(
                                    usingFunctionItem.usingFunction.getDefaultValue(context));
                            builder.setControlId("recommend");
                            basePreferenceController.setValue(builder.build());
                            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                            if (featureFactoryImpl == null) {
                                throw new UnsupportedOperationException(
                                        "No feature factory configured");
                            }
                            featureFactoryImpl
                                    .getA11ySettingsMetricsFeatureProvider()
                                    .action(
                                            1003,
                                            "A11Y1008",
                                            Map.of(
                                                    "function",
                                                    usingFunctionPreference.item.usingFunction
                                                            .getFunctionName()));
                            view.announceForAccessibility(usingFunctionPreference.announceText);
                        }
                    });
            button.setText(this.buttonText);
            button.setContentDescription(this.buttonContentDescription);
        }
        Button button2 =
                (Button) preferenceViewHolder.itemView.findViewById(R.id.learn_more_button);
        if (button2 != null) {
            final Intent learnMoreButtonIntent =
                    this.item.usingFunction.getLearnMoreButtonIntent(getContext());
            button2.setVisibility(learnMoreButtonIntent == null ? 8 : 0);
            button2.setOnClickListener(
                    learnMoreButtonIntent == null
                            ? null
                            : new View.OnClickListener() { // from class:
                                // com.samsung.android.settings.accessibility.recommend.UsingFunctionPreference$$ExternalSyntheticLambda1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    UsingFunctionPreference usingFunctionPreference =
                                            UsingFunctionPreference.this;
                                    Intent intent = learnMoreButtonIntent;
                                    int i = UsingFunctionPreference.$r8$clinit;
                                    usingFunctionPreference.getClass();
                                    FeatureFactoryImpl featureFactoryImpl =
                                            FeatureFactoryImpl._factory;
                                    if (featureFactoryImpl == null) {
                                        throw new UnsupportedOperationException(
                                                "No feature factory configured");
                                    }
                                    featureFactoryImpl
                                            .getA11ySettingsMetricsFeatureProvider()
                                            .clicked(1003, "A11Y1007");
                                    try {
                                        usingFunctionPreference.getContext().startActivity(intent);
                                    } catch (ActivityNotFoundException unused) {
                                        Log.w(
                                                "UsingFunctionPreference",
                                                "learnMore intent is no longer available now. key :"
                                                    + " "
                                                        + usingFunctionPreference.getKey());
                                    }
                                }
                            });
        }
        View view = preferenceViewHolder.itemView;
        if (button2 == null || button == null) {
            if (button2 != null) {
                if (button2.getVisibility() == 8) {
                    return;
                }
                view.setNextFocusRightId(button2.getId());
                button2.setNextFocusLeftId(view.getId());
                button2.setNextFocusRightId(-1);
                return;
            }
            if (button == null || button.getVisibility() == 8) {
                return;
            }
            view.setNextFocusRightId(button.getId());
            button.setNextFocusLeftId(view.getId());
            button.setNextFocusRightId(-1);
            return;
        }
        if (button2.getVisibility() == 8 && button.getVisibility() == 8) {
            return;
        }
        if (button2.getVisibility() == 8) {
            view.setNextFocusRightId(button.getId());
            button.setNextFocusLeftId(view.getId());
            button.setNextFocusRightId(-1);
        } else if (button.getVisibility() == 8) {
            view.setNextFocusRightId(button2.getId());
            button2.setNextFocusLeftId(view.getId());
            button2.setNextFocusRightId(-1);
        } else {
            view.setNextFocusRightId(button2.getId());
            button2.setNextFocusLeftId(view.getId());
            button2.setNextFocusRightId(button.getId());
            button.setNextFocusLeftId(button2.getId());
            button.setNextFocusRightId(-1);
        }
    }
}
