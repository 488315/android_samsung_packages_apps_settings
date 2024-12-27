package com.android.settings.gestures;

import android.content.Context;
import android.content.Intent;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityShortcutsTutorial;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerListHelper;
import com.android.settings.core.PreferenceControllerListHelper$$ExternalSyntheticLambda0;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.utils.CandidateInfoExtra;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.IllustrationPreference;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SystemNavigationGestureSettings extends RadioButtonPickerFragment {
    static final String KEY_SYSTEM_NAV_2BUTTONS = "system_nav_2buttons";
    static final String KEY_SYSTEM_NAV_3BUTTONS = "system_nav_3buttons";
    static final String KEY_SYSTEM_NAV_GESTURAL = "system_nav_gestural";
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.system_navigation_gesture_settings);
    public IOverlayManager mOverlayManager;
    public IllustrationPreference mVideoPreference;
    public final Intent mLaunchSandboxIntent =
            new Intent("com.android.quickstep.action.GESTURE_SANDBOX")
                    .setFlags(268435456)
                    .putExtra("use_tutorial_menu", true)
                    .setPackage("com.google.android.apps.nexuslauncher");
    public boolean mA11yTutorialDialogShown = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.gestures.SystemNavigationGestureSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            if (SystemNavigationPreferenceController.isOverlayPackageAvailable(
                    context, "com.android.internal.systemui.navbar.gestural")) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                searchIndexableRaw.title =
                        resources.getString(R.string.edge_to_edge_navigation_title);
                ((SearchIndexableData) searchIndexableRaw).key =
                        SystemNavigationGestureSettings.KEY_SYSTEM_NAV_GESTURAL;
                arrayList.add(searchIndexableRaw);
            }
            if (SystemNavigationPreferenceController.isOverlayPackageAvailable(
                    context, "com.android.internal.systemui.navbar.twobutton")) {
                SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
                searchIndexableRaw2.title =
                        resources.getString(R.string.swipe_up_to_switch_apps_title);
                ((SearchIndexableData) searchIndexableRaw2).key =
                        SystemNavigationGestureSettings.KEY_SYSTEM_NAV_2BUTTONS;
                arrayList.add(searchIndexableRaw2);
            }
            if (SystemNavigationPreferenceController.isOverlayPackageAvailable(
                    context, "com.android.internal.systemui.navbar.threebutton")) {
                SearchIndexableRaw searchIndexableRaw3 = new SearchIndexableRaw(context);
                searchIndexableRaw3.title = resources.getString(R.string.legacy_navigation_title);
                ((SearchIndexableData) searchIndexableRaw3).key =
                        SystemNavigationGestureSettings.KEY_SYSTEM_NAV_3BUTTONS;
                searchIndexableRaw3.keywords =
                        resources.getString(R.string.keywords_3_button_navigation);
                arrayList.add(searchIndexableRaw3);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return SystemNavigationPreferenceController.isGestureAvailable(context);
        }
    }

    public static String getCurrentSystemNavigationMode(Context context) {
        return SystemNavigationPreferenceController.isGestureNavigationEnabled(context)
                ? KEY_SYSTEM_NAV_GESTURAL
                : SystemNavigationPreferenceController.is2ButtonNavigationEnabled(context)
                        ? KEY_SYSTEM_NAV_2BUTTONS
                        : KEY_SYSTEM_NAV_3BUTTONS;
    }

    public static void setCurrentSystemNavigationMode(IOverlayManager iOverlayManager, String str) {
        String str2;
        str.getClass();
        str2 = "com.android.internal.systemui.navbar.gestural";
        switch (str) {
            case "system_nav_2buttons":
                str2 = "com.android.internal.systemui.navbar.twobutton";
                break;
            case "system_nav_3buttons":
                str2 = "com.android.internal.systemui.navbar.threebutton";
                break;
        }
        try {
            iOverlayManager.setEnabledExclusiveInCategory(str2, -2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void bindPreferenceExtra(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {
        if (candidateInfo instanceof CandidateInfoExtra) {
            selectorWithWidgetPreference.setSummary(((CandidateInfoExtra) candidateInfo).mSummary);
            String str3 = ((CandidateInfoExtra) candidateInfo).mKey;
            if (KEY_SYSTEM_NAV_GESTURAL.equals(str3)) {
                final int i = 0;
                selectorWithWidgetPreference.setExtraWidgetOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.android.settings.gestures.SystemNavigationGestureSettings$$ExternalSyntheticLambda2
                            public final /* synthetic */ SystemNavigationGestureSettings f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i2 = i;
                                SystemNavigationGestureSettings systemNavigationGestureSettings =
                                        this.f$0;
                                switch (i2) {
                                    case 0:
                                        BaseSearchIndexProvider baseSearchIndexProvider =
                                                SystemNavigationGestureSettings
                                                        .SEARCH_INDEX_DATA_PROVIDER;
                                        systemNavigationGestureSettings.getClass();
                                        systemNavigationGestureSettings.startActivity(
                                                new Intent(
                                                                "com.android.settings.GESTURE_NAVIGATION_SETTINGS")
                                                        .setPackage(
                                                                systemNavigationGestureSettings
                                                                        .getContext()
                                                                        .getPackageName()));
                                        break;
                                    default:
                                        BaseSearchIndexProvider baseSearchIndexProvider2 =
                                                SystemNavigationGestureSettings
                                                        .SEARCH_INDEX_DATA_PROVIDER;
                                        SubSettingLauncher subSettingLauncher =
                                                new SubSettingLauncher(
                                                        systemNavigationGestureSettings
                                                                .getContext());
                                        String name =
                                                ButtonNavigationSettingsFragment.class.getName();
                                        SubSettingLauncher.LaunchRequest launchRequest =
                                                subSettingLauncher.mLaunchRequest;
                                        launchRequest.mDestinationName = name;
                                        launchRequest.mSourceMetricsCategory = 1374;
                                        subSettingLauncher.launch();
                                        break;
                                }
                            }
                        });
            }
            if (KEY_SYSTEM_NAV_2BUTTONS.equals(str3) || KEY_SYSTEM_NAV_3BUTTONS.equals(str3)) {
                Context context = getContext();
                PreferenceScreen inflateFromResource =
                        getPreferenceManager()
                                .inflateFromResource(
                                        context, R.xml.button_navigation_settings, null);
                List preferenceControllersFromXml =
                        PreferenceControllerListHelper.getPreferenceControllersFromXml(
                                context, R.xml.button_navigation_settings);
                if (inflateFromResource.getPreferenceCount()
                                != ((ArrayList) preferenceControllersFromXml).size()
                        ? false
                        : preferenceControllersFromXml.stream()
                                .noneMatch(
                                        new PreferenceControllerListHelper$$ExternalSyntheticLambda0())) {
                    return;
                }
                final int i2 = 1;
                selectorWithWidgetPreference.setExtraWidgetOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.android.settings.gestures.SystemNavigationGestureSettings$$ExternalSyntheticLambda2
                            public final /* synthetic */ SystemNavigationGestureSettings f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i22 = i2;
                                SystemNavigationGestureSettings systemNavigationGestureSettings =
                                        this.f$0;
                                switch (i22) {
                                    case 0:
                                        BaseSearchIndexProvider baseSearchIndexProvider =
                                                SystemNavigationGestureSettings
                                                        .SEARCH_INDEX_DATA_PROVIDER;
                                        systemNavigationGestureSettings.getClass();
                                        systemNavigationGestureSettings.startActivity(
                                                new Intent(
                                                                "com.android.settings.GESTURE_NAVIGATION_SETTINGS")
                                                        .setPackage(
                                                                systemNavigationGestureSettings
                                                                        .getContext()
                                                                        .getPackageName()));
                                        break;
                                    default:
                                        BaseSearchIndexProvider baseSearchIndexProvider2 =
                                                SystemNavigationGestureSettings
                                                        .SEARCH_INDEX_DATA_PROVIDER;
                                        SubSettingLauncher subSettingLauncher =
                                                new SubSettingLauncher(
                                                        systemNavigationGestureSettings
                                                                .getContext());
                                        String name =
                                                ButtonNavigationSettingsFragment.class.getName();
                                        SubSettingLauncher.LaunchRequest launchRequest =
                                                subSettingLauncher.mLaunchRequest;
                                        launchRequest.mDestinationName = name;
                                        launchRequest.mSourceMetricsCategory = 1374;
                                        subSettingLauncher.launch();
                                        break;
                                }
                            }
                        });
            }
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        Context context = getContext();
        ArrayList arrayList = new ArrayList();
        if (SystemNavigationPreferenceController.isOverlayPackageAvailable(
                context, "com.android.internal.systemui.navbar.gestural")) {
            arrayList.add(
                    new CandidateInfoExtra(
                            context.getText(R.string.edge_to_edge_navigation_title),
                            context.getText(R.string.edge_to_edge_navigation_summary),
                            KEY_SYSTEM_NAV_GESTURAL));
        }
        if (SystemNavigationPreferenceController.isOverlayPackageAvailable(
                context, "com.android.internal.systemui.navbar.twobutton")) {
            arrayList.add(
                    new CandidateInfoExtra(
                            context.getText(R.string.swipe_up_to_switch_apps_title),
                            context.getText(R.string.swipe_up_to_switch_apps_summary),
                            KEY_SYSTEM_NAV_2BUTTONS));
        }
        if (SystemNavigationPreferenceController.isOverlayPackageAvailable(
                context, "com.android.internal.systemui.navbar.threebutton")) {
            arrayList.add(
                    new CandidateInfoExtra(
                            context.getText(R.string.legacy_navigation_title),
                            context.getText(R.string.legacy_navigation_summary),
                            KEY_SYSTEM_NAV_3BUTTONS));
        }
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        return getCurrentSystemNavigationMode(getContext());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1374;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.system_navigation_gesture_settings;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getSuggestionFeatureProvider().getClass();
        context.getSharedPreferences("suggestions", 0)
                .edit()
                .putBoolean("pref_system_navigation_suggestion_complete", true)
                .apply();
        this.mOverlayManager =
                IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        this.mVideoPreference = new IllustrationPreference(context);
        OverlayInfo overlayInfo = null;
        if (context.createWindowContext(2038, null)
                        .getResources()
                        .getConfiguration()
                        .smallestScreenWidthDp
                >= 600) {
            IllustrationPreference illustrationPreference = this.mVideoPreference;
            illustrationPreference.mLottieDynamicColor = true;
            illustrationPreference.notifyChanged();
        }
        setIllustrationVideo(this.mVideoPreference, getDefaultKey());
        setIllustrationClickListener(this.mVideoPreference, getDefaultKey());
        IOverlayManager iOverlayManager = this.mOverlayManager;
        if (SystemNavigationPreferenceController.isGestureNavigationEnabled(context)) {
            try {
                overlayInfo =
                        iOverlayManager.getOverlayInfo(
                                "com.android.internal.systemui.navbar.gestural", -2);
            } catch (RemoteException unused) {
            }
            if (overlayInfo == null || overlayInfo.isEnabled()) {
                return;
            }
            setCurrentSystemNavigationMode(iOverlayManager, KEY_SYSTEM_NAV_GESTURAL);
            Settings.Secure.putFloat(
                    context.getContentResolver(), "back_gesture_inset_scale_left", 1.0f);
            Settings.Secure.putFloat(
                    context.getContentResolver(), "back_gesture_inset_scale_right", 1.0f);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            boolean z = bundle.getBoolean("show_a11y_tutorial_dialog_bool", false);
            this.mA11yTutorialDialogShown = z;
            if (z) {
                AccessibilityShortcutsTutorial.showGestureNavigationTutorialDialog(
                        getContext(),
                        new SystemNavigationGestureSettings$$ExternalSyntheticLambda1(this, 0));
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("show_a11y_tutorial_dialog_bool", this.mA11yTutorialDialogShown);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        setCurrentSystemNavigationMode(this.mOverlayManager, str);
        setIllustrationVideo(this.mVideoPreference, str);
        if (TextUtils.equals(KEY_SYSTEM_NAV_GESTURAL, str)
                && Settings.Secure.getInt(
                                getContext().getContentResolver(), "accessibility_button_mode", -1)
                        != 1
                && ((!((AccessibilityManager)
                                        getContext().getSystemService(AccessibilityManager.class))
                                .getAccessibilityShortcutTargets(1)
                                .isEmpty())
                        || Settings.Secure.getInt(
                                        getContext().getContentResolver(),
                                        "accessibility_display_magnification_navbar_enabled",
                                        0)
                                == 1)) {
            this.mA11yTutorialDialogShown = true;
            AccessibilityShortcutsTutorial.showGestureNavigationTutorialDialog(
                    getContext(),
                    new SystemNavigationGestureSettings$$ExternalSyntheticLambda1(this, 1));
        } else {
            this.mA11yTutorialDialogShown = false;
        }
        setIllustrationClickListener(this.mVideoPreference, str);
        return true;
    }

    public final void setIllustrationClickListener(
            IllustrationPreference illustrationPreference, String str) {
        String str2;
        int hashCode = str.hashCode();
        if (hashCode != -1860313413) {
            if (hashCode != -1375361165) {
                if (hashCode == -117503078) {
                    str2 = KEY_SYSTEM_NAV_3BUTTONS;
                }
            } else if (str.equals(KEY_SYSTEM_NAV_GESTURAL)) {
                Context context = getContext();
                if (context == null
                        || this.mLaunchSandboxIntent.resolveActivity(context.getPackageManager())
                                == null) {
                    illustrationPreference.setOnPreferenceClickListener(null);
                    return;
                }
                CharSequence text =
                        illustrationPreference
                                .getContext()
                                .getText(R.string.nav_tutorial_button_description);
                if (!TextUtils.equals(illustrationPreference.mContentDescription, text)) {
                    illustrationPreference.mContentDescription = text;
                    illustrationPreference.notifyChanged();
                }
                illustrationPreference.setOnPreferenceClickListener(
                        new Preference
                                .OnPreferenceClickListener() { // from class:
                                                               // com.android.settings.gestures.SystemNavigationGestureSettings$$ExternalSyntheticLambda0
                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference) {
                                SystemNavigationGestureSettings systemNavigationGestureSettings =
                                        SystemNavigationGestureSettings.this;
                                systemNavigationGestureSettings.startActivity(
                                        systemNavigationGestureSettings.mLaunchSandboxIntent);
                                return true;
                            }
                        });
                return;
            }
            illustrationPreference.setOnPreferenceClickListener(null);
        }
        str2 = KEY_SYSTEM_NAV_2BUTTONS;
        str.equals(str2);
        illustrationPreference.setOnPreferenceClickListener(null);
    }

    public final void setIllustrationVideo(
            IllustrationPreference illustrationPreference, String str) {
        str.getClass();
        switch (str) {
            case "system_nav_2buttons":
                illustrationPreference.setLottieAnimationResId(R.raw.lottie_system_nav_2_button);
                break;
            case "system_nav_gestural":
                Context context = getContext();
                if (context != null
                        && this.mLaunchSandboxIntent.resolveActivity(context.getPackageManager())
                                != null) {
                    illustrationPreference.setLottieAnimationResId(
                            R.raw.lottie_system_nav_fully_gestural_with_nav);
                    break;
                } else {
                    illustrationPreference.setLottieAnimationResId(
                            R.raw.lottie_system_nav_fully_gestural);
                    break;
                }
            case "system_nav_3buttons":
                illustrationPreference.setLottieAnimationResId(R.raw.lottie_system_nav_3_button);
                break;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void updateCandidates() {
        String defaultKey = getDefaultKey();
        final PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        preferenceScreen.addPreference(this.mVideoPreference);
        addPreferencesFromResource(R.xml.system_navigation_gesture_settings);
        ((ArrayList)
                        PreferenceControllerListHelper.getPreferenceControllersFromXml(
                                getContext(), R.xml.system_navigation_gesture_settings))
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.settings.gestures.SystemNavigationGestureSettings$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SystemNavigationGestureSettings systemNavigationGestureSettings =
                                        SystemNavigationGestureSettings.this;
                                PreferenceScreen preferenceScreen2 = preferenceScreen;
                                BasePreferenceController basePreferenceController =
                                        (BasePreferenceController) obj;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        SystemNavigationGestureSettings.SEARCH_INDEX_DATA_PROVIDER;
                                systemNavigationGestureSettings.getClass();
                                basePreferenceController.updateState(
                                        systemNavigationGestureSettings.findPreference(
                                                basePreferenceController.getPreferenceKey()));
                                basePreferenceController.displayPreference(preferenceScreen2);
                            }
                        });
        Iterator it = ((ArrayList) getCandidates()).iterator();
        while (it.hasNext()) {
            CandidateInfo candidateInfo = (CandidateInfo) it.next();
            SelectorWithWidgetPreference selectorWithWidgetPreference =
                    new SelectorWithWidgetPreference(getPrefContext());
            bindPreference(
                    selectorWithWidgetPreference,
                    candidateInfo.getKey(),
                    candidateInfo,
                    defaultKey);
            bindPreferenceExtra(
                    selectorWithWidgetPreference,
                    candidateInfo.getKey(),
                    candidateInfo,
                    defaultKey);
            preferenceScreen.addPreference(selectorWithWidgetPreference);
        }
        mayCheckOnlyRadioButton();
    }
}
