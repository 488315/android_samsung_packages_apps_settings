package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityDashboardFragment extends DashboardFragment {
    public Context context;
    public final Map uriListMap = new HashMap();
    public final AnonymousClass1 contentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    AccessibilityDashboardFragment.this.update(uri);
                }
            };

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        enableAutoFlowLogging(false);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getA11ySettingsMetricsFeatureProvider();
        final FragmentActivity activity = getActivity();
        getPreferenceControllers().stream()
                .flatMap(new EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3())
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AccessibilityDashboardFragment accessibilityDashboardFragment =
                                        AccessibilityDashboardFragment.this;
                                FragmentActivity fragmentActivity = activity;
                                Object obj2 = (AbstractPreferenceController) obj;
                                accessibilityDashboardFragment.getClass();
                                if (obj2 instanceof AccessibilityObservableController) {
                                    for (Uri uri :
                                            ((AccessibilityObservableController) obj2)
                                                    .getUriList()) {
                                        List list =
                                                (List)
                                                        ((HashMap)
                                                                        accessibilityDashboardFragment
                                                                                .uriListMap)
                                                                .get(uri);
                                        if (list == null) {
                                            ArrayList arrayList = new ArrayList();
                                            arrayList.add(obj2);
                                            ((HashMap) accessibilityDashboardFragment.uriListMap)
                                                    .put(uri, arrayList);
                                        } else {
                                            list.add(obj2);
                                        }
                                    }
                                }
                                if (!(obj2 instanceof DescriptionPreference)
                                        || fragmentActivity == null) {
                                    return;
                                }
                                DescriptionPreference descriptionPreference =
                                        (DescriptionPreference) obj2;
                                Objects.requireNonNull(descriptionPreference);
                                fragmentActivity.addOnConfigurationChangedListener(
                                        new AccessibilityDashboardFragment$$ExternalSyntheticLambda0(
                                                descriptionPreference));
                            }
                        });
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        FragmentActivity activity = getActivity();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (activity == null || preferenceScreen == null) {
            return;
        }
        for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (preference instanceof DescriptionPreference) {
                DescriptionPreference descriptionPreference = (DescriptionPreference) preference;
                Objects.requireNonNull(descriptionPreference);
                activity.addOnConfigurationChangedListener(
                        new AccessibilityDashboardFragment$$ExternalSyntheticLambda0(
                                descriptionPreference));
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.mMetricsFeatureProvider.clicked(getMetricsCategory(), "A11Y0001");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mMetricsFeatureProvider.visible(null, 0, getMetricsCategory(), 0);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        ((HashMap) this.uriListMap)
                .keySet()
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AccessibilityDashboardFragment accessibilityDashboardFragment =
                                        AccessibilityDashboardFragment.this;
                                accessibilityDashboardFragment
                                        .context
                                        .getContentResolver()
                                        .registerContentObserver(
                                                (Uri) obj,
                                                false,
                                                accessibilityDashboardFragment.contentObserver);
                            }
                        });
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.context.getContentResolver().unregisterContentObserver(this.contentObserver);
    }

    public void update(Uri uri) {
        List<AbstractPreferenceController> list = (List) ((HashMap) this.uriListMap).get(uri);
        if (list != null) {
            for (AbstractPreferenceController abstractPreferenceController : list) {
                Preference findPreference =
                        findPreference(abstractPreferenceController.getPreferenceKey());
                if (findPreference != null) {
                    abstractPreferenceController.updateState(findPreference);
                } else {
                    Log.i(
                            "AccessibilityDashboardFragment",
                            "no preference for controller now. key : "
                                    + abstractPreferenceController.getPreferenceKey());
                }
            }
        }
    }
}
