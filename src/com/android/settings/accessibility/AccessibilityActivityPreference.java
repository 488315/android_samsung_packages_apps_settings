package com.android.settings.accessibility;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilityActivityPreference extends RestrictedPreference {
    public static final String TARGET_FRAGMENT =
            LaunchAccessibilityActivityPreferenceFragment.class.getName();
    public boolean isLastInList;
    public final AccessibilityShortcutInfo mA11yShortcutInfo;
    public final ComponentName mComponentName;
    public final ListenableFuture mExtraArgumentsFuture;
    public final PackageManager mPm;

    public AccessibilityActivityPreference(
            Context context,
            String str,
            int i,
            AccessibilityShortcutInfo accessibilityShortcutInfo) {
        super(context, str, i);
        this.isLastInList = false;
        PackageManager packageManager = context.getPackageManager();
        this.mPm = packageManager;
        this.mA11yShortcutInfo = accessibilityShortcutInfo;
        ComponentName componentName = accessibilityShortcutInfo.getComponentName();
        this.mComponentName = componentName;
        CharSequence loadLabel =
                accessibilityShortcutInfo.getActivityInfo().loadLabel(packageManager);
        setKey(componentName.flattenToString());
        setTitle(loadLabel);
        setSummary(accessibilityShortcutInfo.loadSummary(packageManager));
        setFragment(TARGET_FRAGMENT);
        setLayoutResource(R.layout.sesl_preference);
        setPersistent();
        setOrder(-1);
        getExtras().putParcelable("component_name", componentName);
        this.mExtraArgumentsFuture =
                ThreadUtils.postOnBackgroundThread(
                        new Runnable() { // from class:
                                         // com.android.settings.accessibility.AccessibilityActivityPreference$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                final AccessibilityActivityPreference
                                        accessibilityActivityPreference =
                                                AccessibilityActivityPreference.this;
                                String str2 = AccessibilityActivityPreference.TARGET_FRAGMENT;
                                final String key = accessibilityActivityPreference.getKey();
                                final int animatedImageRes =
                                        accessibilityActivityPreference.mA11yShortcutInfo
                                                .getAnimatedImageRes();
                                final String loadIntro =
                                        accessibilityActivityPreference.mA11yShortcutInfo.loadIntro(
                                                accessibilityActivityPreference.mPm);
                                final String loadDescription =
                                        accessibilityActivityPreference.mA11yShortcutInfo
                                                .loadDescription(
                                                        accessibilityActivityPreference.mPm);
                                final String loadHtmlDescription =
                                        accessibilityActivityPreference.mA11yShortcutInfo
                                                .loadHtmlDescription(
                                                        accessibilityActivityPreference.mPm);
                                final String settingsActivityName =
                                        accessibilityActivityPreference.mA11yShortcutInfo
                                                .getSettingsActivityName();
                                final String tileServiceName =
                                        accessibilityActivityPreference.mA11yShortcutInfo
                                                .getTileServiceName();
                                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                                if (featureFactoryImpl == null) {
                                    throw new UnsupportedOperationException(
                                            "No feature factory configured");
                                }
                                ((AccessibilityMetricsFeatureProviderImpl)
                                                featureFactoryImpl
                                                        .accessibilityMetricsFeatureProvider$delegate
                                                        .getValue())
                                        .getClass();
                                ThreadUtils.getUiThreadHandler()
                                        .post(
                                                new Runnable() { // from class:
                                                                 // com.android.settings.accessibility.AccessibilityActivityPreference$$ExternalSyntheticLambda1
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        AccessibilityActivityPreference
                                                                accessibilityActivityPreference2 =
                                                                        AccessibilityActivityPreference
                                                                                .this;
                                                        String str3 = key;
                                                        CharSequence charSequence = loadIntro;
                                                        CharSequence charSequence2 =
                                                                loadDescription;
                                                        int i2 = animatedImageRes;
                                                        String str4 = loadHtmlDescription;
                                                        String str5 = settingsActivityName;
                                                        String str6 = tileServiceName;
                                                        String str7 =
                                                                AccessibilityActivityPreference
                                                                        .TARGET_FRAGMENT;
                                                        RestrictedPreferenceHelper.putBasicExtras(
                                                                accessibilityActivityPreference2,
                                                                str3,
                                                                accessibilityActivityPreference2
                                                                        .getTitle(),
                                                                charSequence,
                                                                charSequence2,
                                                                i2,
                                                                str4,
                                                                accessibilityActivityPreference2
                                                                        .mComponentName);
                                                        RestrictedPreferenceHelper
                                                                .putSettingsExtras(
                                                                        accessibilityActivityPreference2,
                                                                        accessibilityActivityPreference2
                                                                                .getPackageName(),
                                                                        str5);
                                                        RestrictedPreferenceHelper
                                                                .putTileServiceExtras(
                                                                        accessibilityActivityPreference2,
                                                                        accessibilityActivityPreference2
                                                                                .getPackageName(),
                                                                        str6);
                                                    }
                                                });
                            }
                        });
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedBelow = !this.isLastInList;
    }

    @Override // com.android.settingslib.RestrictedPreference, androidx.preference.Preference
    public final void performClick() {
        try {
            this.mExtraArgumentsFuture.get();
        } catch (InterruptedException | ExecutionException unused) {
            Log.e(
                    "AccessibilityActivityPreference",
                    "Unable to finish grabbing necessary arguments to open the fragment:"
                        + " componentName: "
                            + this.mComponentName);
        }
        super.performClick();
    }
}
