package com.android.settings.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilityServicePreference extends RestrictedPreference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isLastInList;
    public final AccessibilityServiceInfo mA11yServiceInfo;
    public final ComponentName mComponentName;
    public final ListenableFuture mExtraArgumentsFuture;
    public final PackageManager mPm;
    public final boolean mServiceEnabled;

    public AccessibilityServicePreference(
            Context context,
            String str,
            int i,
            AccessibilityServiceInfo accessibilityServiceInfo,
            boolean z) {
        super(context, str, i);
        String name;
        this.isLastInList = false;
        PackageManager packageManager = context.getPackageManager();
        this.mPm = packageManager;
        this.mA11yServiceInfo = accessibilityServiceInfo;
        this.mServiceEnabled = z;
        ComponentName componentName =
                new ComponentName(str, accessibilityServiceInfo.getResolveInfo().serviceInfo.name);
        this.mComponentName = componentName;
        setKey(componentName.flattenToString());
        setTitle(accessibilityServiceInfo.getResolveInfo().loadLabel(packageManager));
        setSummary(
                AccessibilitySettings.getServiceSummary(getContext(), accessibilityServiceInfo, z));
        int accessibilityServiceFragmentType =
                AccessibilityUtil.getAccessibilityServiceFragmentType(accessibilityServiceInfo);
        if (accessibilityServiceFragmentType == 0) {
            name = VolumeShortcutToggleAccessibilityServicePreferenceFragment.class.getName();
        } else if (accessibilityServiceFragmentType == 1) {
            name = InvisibleToggleAccessibilityServicePreferenceFragment.class.getName();
        } else {
            if (accessibilityServiceFragmentType != 2) {
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                accessibilityServiceFragmentType,
                                "Unsupported accessibility fragment type "));
            }
            name = ToggleAccessibilityServicePreferenceFragment.class.getName();
        }
        setFragment(name);
        setLayoutResource(R.layout.sesl_preference);
        setPersistent();
        setOrder(-1);
        getExtras().putParcelable("component_name", componentName);
        this.mExtraArgumentsFuture =
                ThreadUtils.postOnBackgroundThread(
                        new Runnable() { // from class:
                                         // com.android.settings.accessibility.AccessibilityServicePreference$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                final AccessibilityServicePreference
                                        accessibilityServicePreference =
                                                AccessibilityServicePreference.this;
                                int i2 = AccessibilityServicePreference.$r8$clinit;
                                final String key = accessibilityServicePreference.getKey();
                                final int animatedImageRes =
                                        accessibilityServicePreference.mA11yServiceInfo
                                                .getAnimatedImageRes();
                                final CharSequence loadIntro =
                                        accessibilityServicePreference.mA11yServiceInfo.loadIntro(
                                                accessibilityServicePreference.mPm);
                                final CharSequence serviceDescription =
                                        AccessibilitySettings.getServiceDescription(
                                                accessibilityServicePreference.getContext(),
                                                accessibilityServicePreference.mA11yServiceInfo,
                                                accessibilityServicePreference.mServiceEnabled);
                                final String loadHtmlDescription =
                                        accessibilityServicePreference.mA11yServiceInfo
                                                .loadHtmlDescription(
                                                        accessibilityServicePreference.mPm);
                                final String settingsActivityName =
                                        accessibilityServicePreference.mA11yServiceInfo
                                                .getSettingsActivityName();
                                final String tileServiceName =
                                        accessibilityServicePreference.mA11yServiceInfo
                                                .getTileServiceName();
                                final ResolveInfo resolveInfo =
                                        accessibilityServicePreference.mA11yServiceInfo
                                                .getResolveInfo();
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
                                                                 // com.android.settings.accessibility.AccessibilityServicePreference$$ExternalSyntheticLambda1
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        AccessibilityServicePreference
                                                                accessibilityServicePreference2 =
                                                                        AccessibilityServicePreference
                                                                                .this;
                                                        String str2 = key;
                                                        CharSequence charSequence = loadIntro;
                                                        CharSequence charSequence2 =
                                                                serviceDescription;
                                                        int i3 = animatedImageRes;
                                                        String str3 = loadHtmlDescription;
                                                        ResolveInfo resolveInfo2 = resolveInfo;
                                                        String str4 = settingsActivityName;
                                                        String str5 = tileServiceName;
                                                        int i4 =
                                                                AccessibilityServicePreference
                                                                        .$r8$clinit;
                                                        RestrictedPreferenceHelper.putBasicExtras(
                                                                accessibilityServicePreference2,
                                                                str2,
                                                                accessibilityServicePreference2
                                                                        .getTitle(),
                                                                charSequence,
                                                                charSequence2,
                                                                i3,
                                                                str3,
                                                                accessibilityServicePreference2
                                                                        .mComponentName);
                                                        boolean z2 =
                                                                accessibilityServicePreference2
                                                                        .mServiceEnabled;
                                                        Bundle extras =
                                                                accessibilityServicePreference2
                                                                        .getExtras();
                                                        extras.putParcelable(
                                                                "resolve_info", resolveInfo2);
                                                        extras.putBoolean("checked", z2);
                                                        RestrictedPreferenceHelper
                                                                .putSettingsExtras(
                                                                        accessibilityServicePreference2,
                                                                        accessibilityServicePreference2
                                                                                .getPackageName(),
                                                                        str4);
                                                        RestrictedPreferenceHelper
                                                                .putTileServiceExtras(
                                                                        accessibilityServicePreference2,
                                                                        accessibilityServicePreference2
                                                                                .getPackageName(),
                                                                        str5);
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
                    "AccessibilityServicePreference",
                    "Unable to finish grabbing necessary arguments to open the fragment:"
                        + " componentName: "
                            + this.mComponentName);
        }
        super.performClick();
    }
}
