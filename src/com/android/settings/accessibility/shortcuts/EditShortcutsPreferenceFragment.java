package com.android.settings.accessibility.shortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.icu.text.ListFormatter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.settings.R;
import com.android.settings.accessibility.AccessibilitySetupWizardUtils;
import com.android.settings.accessibility.PreferredShortcuts;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifPreferenceLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EditShortcutsPreferenceFragment extends DashboardFragment {
    static final String ARG_KEY_SHORTCUT_TARGETS = "targets";
    public static final Uri BUTTON_SHORTCUT_MODE_SETTING;
    public static final Uri BUTTON_SHORTCUT_SETTING;
    public static final Uri QUICK_SETTINGS_SHORTCUT_SETTING;
    static final String SAVED_STATE_IS_EXPANDED = "isExpanded";
    static final Uri[] SHORTCUT_SETTINGS;
    public static final Uri TRIPLE_TAP_SHORTCUT_SETTING;
    public static final Uri TWO_FINGERS_DOUBLE_TAP_SHORTCUT_SETTING;
    public static final Uri VOLUME_KEYS_SHORTCUT_SETTING;
    public AnonymousClass1 mSettingsObserver;
    public Set mShortcutTargets;
    public EditShortcutsPreferenceFragment$$ExternalSyntheticLambda1
            mTouchExplorationStateChangeListener;

    static {
        Uri uriFor = Settings.Secure.getUriFor("accessibility_shortcut_target_service");
        VOLUME_KEYS_SHORTCUT_SETTING = uriFor;
        Uri uriFor2 = Settings.Secure.getUriFor("accessibility_button_mode");
        BUTTON_SHORTCUT_MODE_SETTING = uriFor2;
        Uri uriFor3 = Settings.Secure.getUriFor("accessibility_button_targets");
        BUTTON_SHORTCUT_SETTING = uriFor3;
        Uri uriFor4 = Settings.Secure.getUriFor("accessibility_display_magnification_enabled");
        TRIPLE_TAP_SHORTCUT_SETTING = uriFor4;
        Uri uriFor5 =
                Settings.Secure.getUriFor(
                        "accessibility_magnification_two_finger_triple_tap_enabled");
        TWO_FINGERS_DOUBLE_TAP_SHORTCUT_SETTING = uriFor5;
        Uri uriFor6 = Settings.Secure.getUriFor("accessibility_qs_targets");
        QUICK_SETTINGS_SHORTCUT_SETTING = uriFor6;
        SHORTCUT_SETTINGS = new Uri[] {uriFor, uriFor2, uriFor3, uriFor4, uriFor5, uriFor6};
    }

    public static Pair<String, String> getTitlesFromAccessibilityTargetList(
            Set<String> set, List<AccessibilityTarget> list, Resources resources) {
        ArrayList arrayList = new ArrayList();
        final ArrayMap arrayMap = new ArrayMap();
        list.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Map map = arrayMap;
                        AccessibilityTarget accessibilityTarget = (AccessibilityTarget) obj;
                        Uri uri = EditShortcutsPreferenceFragment.VOLUME_KEYS_SHORTCUT_SETTING;
                        map.put(accessibilityTarget.getId(), accessibilityTarget.getLabel());
                    }
                });
        for (String str : set) {
            if (!arrayMap.containsKey(str)) {
                throw new IllegalStateException(
                        AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "Shortcut target does not have a label: ", str));
            }
            arrayList.add((CharSequence) arrayMap.get(str));
        }
        if (arrayList.size() == 1) {
            return new Pair<>(
                    resources.getString(R.string.accessibility_shortcut_title, arrayList.get(0)),
                    null);
        }
        if (arrayList.size() != 0) {
            return new Pair<>(
                    resources.getString(R.string.accessibility_shortcut_edit_screen_title),
                    resources.getString(
                            R.string.accessibility_shortcut_edit_screen_prompt,
                            ListFormatter.getInstance().format(arrayList)));
        }
        throw new IllegalStateException("Found no labels for any shortcut targets.");
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "EditShortcutsPreferenceFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1812;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_edit_shortcuts;
    }

    public void initializePreferenceControllerArguments() {
        final boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(getIntent());
        getPreferenceControllers().stream()
                .flatMap(new EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3())
                .filter(new EditShortcutsPreferenceFragment$$ExternalSyntheticLambda4())
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                EditShortcutsPreferenceFragment editShortcutsPreferenceFragment =
                                        EditShortcutsPreferenceFragment.this;
                                boolean z = isAnySetupWizard;
                                Uri uri =
                                        EditShortcutsPreferenceFragment
                                                .VOLUME_KEYS_SHORTCUT_SETTING;
                                editShortcutsPreferenceFragment.getClass();
                                ShortcutOptionPreferenceController
                                        shortcutOptionPreferenceController =
                                                (ShortcutOptionPreferenceController)
                                                        ((AbstractPreferenceController) obj);
                                shortcutOptionPreferenceController.setShortcutTargets(
                                        editShortcutsPreferenceFragment.mShortcutTargets);
                                shortcutOptionPreferenceController.setInSetupWizard(z);
                            }
                        });
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments == null || arguments.isEmpty()) {
            throw new IllegalArgumentException(
                    "EditShortcutsPreferenceFragment requires non-empty shortcut targets");
        }
        String[] stringArray = arguments.getStringArray(ARG_KEY_SHORTCUT_TARGETS);
        if (stringArray == null) {
            throw new IllegalArgumentException(
                    "EditShortcutsPreferenceFragment requires non-empty shortcut targets");
        }
        this.mShortcutTargets = Set.of((Object[]) stringArray);
        initializePreferenceControllerArguments();
    }

    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$1] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null && bundle.getBoolean(SAVED_STATE_IS_EXPANDED)) {
            ((AdvancedShortcutsPreferenceController)
                            use(AdvancedShortcutsPreferenceController.class))
                    .setExpanded(true);
            ((TripleTapShortcutOptionController) use(TripleTapShortcutOptionController.class))
                    .setExpanded(true);
            refreshPreferenceController(AdvancedShortcutsPreferenceController.class);
            refreshPreferenceController(TripleTapShortcutOptionController.class);
        }
        this.mSettingsObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        if (EditShortcutsPreferenceFragment.VOLUME_KEYS_SHORTCUT_SETTING.equals(
                                uri)) {
                            EditShortcutsPreferenceFragment.this.refreshPreferenceController(
                                    VolumeKeysShortcutOptionController.class);
                        } else if (EditShortcutsPreferenceFragment.BUTTON_SHORTCUT_MODE_SETTING
                                        .equals(uri)
                                || EditShortcutsPreferenceFragment.BUTTON_SHORTCUT_SETTING.equals(
                                        uri)) {
                            EditShortcutsPreferenceFragment editShortcutsPreferenceFragment =
                                    EditShortcutsPreferenceFragment.this;
                            editShortcutsPreferenceFragment.refreshPreferenceController(
                                    GestureShortcutOptionController.class);
                            editShortcutsPreferenceFragment.refreshPreferenceController(
                                    FloatingButtonShortcutOptionController.class);
                            editShortcutsPreferenceFragment.refreshPreferenceController(
                                    NavButtonShortcutOptionController.class);
                        } else if (EditShortcutsPreferenceFragment.TRIPLE_TAP_SHORTCUT_SETTING
                                .equals(uri)) {
                            EditShortcutsPreferenceFragment.this.refreshPreferenceController(
                                    TripleTapShortcutOptionController.class);
                        } else if (EditShortcutsPreferenceFragment
                                .TWO_FINGERS_DOUBLE_TAP_SHORTCUT_SETTING
                                .equals(uri)) {
                            EditShortcutsPreferenceFragment.this.refreshPreferenceController(
                                    TwoFingerDoubleTapShortcutOptionController.class);
                        } else if (EditShortcutsPreferenceFragment.QUICK_SETTINGS_SHORTCUT_SETTING
                                .equals(uri)) {
                            EditShortcutsPreferenceFragment.this.refreshPreferenceController(
                                    QuickSettingsShortcutOptionController.class);
                        }
                        if (EditShortcutsPreferenceFragment.this.getContext() != null) {
                            PreferredShortcuts.updatePreferredShortcutsFromSettings(
                                    EditShortcutsPreferenceFragment.this.getContext(),
                                    EditShortcutsPreferenceFragment.this.mShortcutTargets);
                        }
                    }
                };
        for (Uri uri : SHORTCUT_SETTINGS) {
            getContentResolver().registerContentObserver(uri, false, this.mSettingsObserver);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        FragmentActivity activity = getActivity();
        if (activity.getIntent()
                .getAction()
                .equals("android.settings.ACCESSIBILITY_SHORTCUT_SETTINGS")) {
            Pair<String, String> titlesFromAccessibilityTargetList =
                    getTitlesFromAccessibilityTargetList(
                            this.mShortcutTargets,
                            AccessibilityTargetHelper.getInstalledTargets(
                                    activity.getBaseContext(), 2),
                            activity.getResources());
            activity.setTitle((CharSequence) titlesFromAccessibilityTargetList.first);
            findPreference(getString(R.string.accessibility_shortcut_description_pref))
                    .setSummary((CharSequence) titlesFromAccessibilityTargetList.second);
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView onCreateRecyclerView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return viewGroup instanceof GlifPreferenceLayout
                ? ((GlifPreferenceLayout) viewGroup).recyclerMixin.recyclerView
                : super.onCreateRecyclerView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        if (this.mSettingsObserver != null) {
            getContentResolver().unregisterContentObserver(this.mSettingsObserver);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mTouchExplorationStateChangeListener != null) {
            ((AccessibilityManager) getSystemService(AccessibilityManager.class))
                    .removeTouchExplorationStateChangeListener(
                            this.mTouchExplorationStateChangeListener);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!getString(R.string.accessibility_shortcuts_advanced_collapsed)
                .equals(preference.getKey())) {
            return super.onPreferenceTreeClick(preference);
        }
        ((AdvancedShortcutsPreferenceController) use(AdvancedShortcutsPreferenceController.class))
                .setExpanded(true);
        ((TripleTapShortcutOptionController) use(TripleTapShortcutOptionController.class))
                .setExpanded(true);
        refreshPreferenceController(AdvancedShortcutsPreferenceController.class);
        refreshPreferenceController(TripleTapShortcutOptionController.class);
        writePreferenceClickMetric(preference);
        return true;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda1] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mTouchExplorationStateChangeListener =
                new AccessibilityManager
                        .TouchExplorationStateChangeListener() { // from class:
                                                                 // com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda1
                    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                    public final void onTouchExplorationStateChanged(boolean z) {
                        EditShortcutsPreferenceFragment editShortcutsPreferenceFragment =
                                EditShortcutsPreferenceFragment.this;
                        Uri uri = EditShortcutsPreferenceFragment.VOLUME_KEYS_SHORTCUT_SETTING;
                        editShortcutsPreferenceFragment.refreshPreferenceController(
                                QuickSettingsShortcutOptionController.class);
                        editShortcutsPreferenceFragment.refreshPreferenceController(
                                GestureShortcutOptionController.class);
                    }
                };
        ((AccessibilityManager) getSystemService(AccessibilityManager.class))
                .addTouchExplorationStateChangeListener(this.mTouchExplorationStateChangeListener);
        PreferredShortcuts.updatePreferredShortcutsFromSettings(
                getContext(), this.mShortcutTargets);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(
                SAVED_STATE_IS_EXPANDED,
                ((AdvancedShortcutsPreferenceController)
                                use(AdvancedShortcutsPreferenceController.class))
                        .isExpanded());
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (view instanceof GlifPreferenceLayout) {
            GlifPreferenceLayout glifPreferenceLayout = (GlifPreferenceLayout) view;
            Intent intent = getIntent();
            AccessibilitySetupWizardUtils.updateGlifPreferenceLayout(
                    getContext(),
                    glifPreferenceLayout,
                    intent != null ? intent.getStringExtra(":settings:show_fragment_title") : null,
                    null,
                    null);
            AccessibilitySetupWizardUtils.setPrimaryButton(
                    getContext(),
                    (FooterBarMixin) glifPreferenceLayout.getMixin(FooterBarMixin.class),
                    new Runnable() { // from class:
                                     // com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            EditShortcutsPreferenceFragment editShortcutsPreferenceFragment =
                                    EditShortcutsPreferenceFragment.this;
                            Uri uri = EditShortcutsPreferenceFragment.VOLUME_KEYS_SHORTCUT_SETTING;
                            editShortcutsPreferenceFragment.setResult(0);
                            editShortcutsPreferenceFragment.finish();
                        }
                    });
        }
    }

    public final void refreshPreferenceController(Class cls) {
        AbstractPreferenceController use = use(cls);
        if (use == null || getPreferenceScreen() == null) {
            return;
        }
        use.displayPreference(getPreferenceScreen());
        if (TextUtils.isEmpty(use.getPreferenceKey())) {
            return;
        }
        use.updateState(findPreference(use.getPreferenceKey()));
    }
}
