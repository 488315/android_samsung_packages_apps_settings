package com.android.settings.notification.modes;

import android.content.Context;
import android.os.Bundle;
import android.service.notification.ZenPolicy;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeAppsPreferenceController extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public String mModeId;
    SelectorWithWidgetPreference.OnClickListener mSelectorClickListener;

    public static void $r8$lambda$89JTmq7s6tYNaP39BtnnupMCZtU(
            ZenModeAppsPreferenceController zenModeAppsPreferenceController) {
        zenModeAppsPreferenceController.getClass();
        Bundle bundle = new Bundle();
        String str = zenModeAppsPreferenceController.mModeId;
        if (str != null) {
            bundle.putString("MODE_ID", str);
        }
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(zenModeAppsPreferenceController.mContext);
        String name = ZenModeSelectBypassingAppsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 1400;
        launchRequest.mArguments = bundle;
        subSettingLauncher.launch();
    }

    public ZenModeAppsPreferenceController(
            Context context, String str, ZenModesBackend zenModesBackend) {
        super(context, str, zenModesBackend);
        this.mSelectorClickListener =
                new SelectorWithWidgetPreference
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.modes.ZenModeAppsPreferenceController.1
                    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
                    public final void onRadioButtonClicked(
                            SelectorWithWidgetPreference selectorWithWidgetPreference) {
                        ZenModeAppsPreferenceController.this.onPreferenceChange(
                                selectorWithWidgetPreference, Boolean.TRUE);
                    }
                };
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        String str = this.mKey;
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                (SelectorWithWidgetPreference) preferenceScreen.findPreference(str);
        if (selectorWithWidgetPreference != null) {
            selectorWithWidgetPreference.mListener = this.mSelectorClickListener;
            if (str.equals("zen_mode_apps_priority")) {
                selectorWithWidgetPreference.setExtraWidgetOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.notification.modes.ZenModeAppsPreferenceController$$ExternalSyntheticLambda3
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ZenModeAppsPreferenceController
                                        .$r8$lambda$89JTmq7s6tYNaP39BtnnupMCZtU(
                                                ZenModeAppsPreferenceController.this);
                            }
                        });
            }
        }
        super.displayPreference(preferenceScreen);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        final int i;
        final int i2;
        final int i3;
        i = 2;
        i2 = 0;
        i3 = 1;
        String str = this.mKey;
        str.getClass();
        switch (str) {
            case "zen_mode_apps_all":
                return savePolicy(
                        new Function() { // from class:
                                         // com.android.settings.notification.modes.ZenModeAppsPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj2) {
                                ZenPolicy.Builder builder = (ZenPolicy.Builder) obj2;
                                switch (i) {
                                    case 0:
                                        return builder.allowChannels(1);
                                    case 1:
                                        return builder.allowChannels(2);
                                    default:
                                        return builder.allowChannels(-1);
                                }
                            }
                        });
            case "zen_mode_apps_none":
                return savePolicy(
                        new Function() { // from class:
                                         // com.android.settings.notification.modes.ZenModeAppsPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj2) {
                                ZenPolicy.Builder builder = (ZenPolicy.Builder) obj2;
                                switch (i3) {
                                    case 0:
                                        return builder.allowChannels(1);
                                    case 1:
                                        return builder.allowChannels(2);
                                    default:
                                        return builder.allowChannels(-1);
                                }
                            }
                        });
            case "zen_mode_apps_priority":
                return savePolicy(
                        new Function() { // from class:
                                         // com.android.settings.notification.modes.ZenModeAppsPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj2) {
                                ZenPolicy.Builder builder = (ZenPolicy.Builder) obj2;
                                switch (i2) {
                                    case 0:
                                        return builder.allowChannels(1);
                                    case 1:
                                        return builder.allowChannels(2);
                                    default:
                                        return builder.allowChannels(-1);
                                }
                            }
                        });
            default:
                return true;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        boolean z;
        this.mModeId = zenMode.mId;
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        String str = this.mKey;
        str.getClass();
        switch (str.hashCode()) {
            case -1756964652:
                if (str.equals("zen_mode_apps_all")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 1369060965:
                if (str.equals("zen_mode_apps_none")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 1506648657:
                if (str.equals("zen_mode_apps_priority")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                twoStatePreference.setChecked(zenMode.getPolicy().getAllowedChannels() == -1);
                break;
            case true:
                twoStatePreference.setChecked(zenMode.getPolicy().getAllowedChannels() == 2);
                break;
            case true:
                twoStatePreference.setChecked(zenMode.getPolicy().getAllowedChannels() == 1);
                break;
        }
    }
}
