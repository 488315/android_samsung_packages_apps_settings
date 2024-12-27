package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.advanced.SecAccessibilityControlTimeoutPreferenceFragment$$ExternalSyntheticLambda0;
import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;
import com.samsung.android.settings.accessibility.base.widget.DividerItemDecorator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ShortcutSwitchPreferenceFragment extends DashboardFragment {
    public static final HashSet excludeComponentSet;
    public static final IntentFilter intentFilter;
    public ShortcutContentObserver.AnonymousClass1 amplifySoundObserver;
    public ShortcutContentObserver.AnonymousClass1 interactionControlObserver;
    public MenuItem mResetMenu;
    public ShortcutContentObserver.AnonymousClass1 magnificationObserver;
    public AnonymousClass3 shortcutContentObserver;
    public final List preferenceControllers = new ArrayList();
    public final Handler handler = new Handler();
    public final AnonymousClass1 runnable =
            new Runnable() { // from class:
                             // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment.1
                @Override // java.lang.Runnable
                public final void run() {
                    Context context = ShortcutSwitchPreferenceFragment.this.getContext();
                    if (context != null) {
                        ShortcutSwitchPreferenceFragment shortcutSwitchPreferenceFragment =
                                ShortcutSwitchPreferenceFragment.this;
                        IntentFilter intentFilter2 = ShortcutSwitchPreferenceFragment.intentFilter;
                        shortcutSwitchPreferenceFragment.refreshControllerList(context);
                    }
                }
            };
    public final AnonymousClass2 broadcastReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ShortcutSwitchPreferenceFragment shortcutSwitchPreferenceFragment =
                            ShortcutSwitchPreferenceFragment.this;
                    shortcutSwitchPreferenceFragment.handler.postDelayed(
                            shortcutSwitchPreferenceFragment.runnable, 3000L);
                }
            };

    static {
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter = intentFilter2;
        HashSet hashSet = new HashSet();
        excludeComponentSet = hashSet;
        hashSet.add(AccessibilityConstant.COMPONENT_NAME_BIXBY_AGENT.flattenToString());
        if (AccessibilityRune.getFloatingFeatureBooleanValue(
                "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05")) {
            hashSet.add(AccessibilityConstant.COMPONENT_NAME_GOOGLE_TALKBACK.flattenToString());
            hashSet.add(AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK.flattenToString());
            hashSet.add(AccessibilityConstant.COMPONENT_NAME_UNIVERSAL_SWITCH.flattenToString());
            hashSet.add(
                    AccessibilityConstant.COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT
                            .flattenToString());
            hashSet.add(
                    AccessibilityConstant.COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT
                            .flattenToString());
        }
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        int shortcutType = getShortcutType();
        List list = ShortcutFeatureManager.candidateBuilders;
        ArrayList arrayList =
                new ArrayList(
                        ShortcutFeatureManager.getCandidatesMap(context, shortcutType).values());
        Collections.sort(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ShortcutCandidate shortcutCandidate = (ShortcutCandidate) it.next();
            if (getShortcutType() != 16) {
                ((ArrayList) this.preferenceControllers)
                        .add(
                                new ShortcutSwitchPreferenceController(
                                        context, shortcutCandidate, getShortcutType(), false));
            } else if (SecAccessibilityUtils.isSupportQuickSettings(shortcutCandidate.getKey())) {
                ((ArrayList) this.preferenceControllers)
                        .add(
                                new ShortcutSwitchPreferenceController(
                                        context, shortcutCandidate, getShortcutType(), true));
            }
        }
        return this.preferenceControllers;
    }

    public abstract String getDbKey();

    public abstract int getDescriptionResId();

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_shortcut_switch_preference_fragment;
    }

    public abstract int getShortcutType();

    public abstract int getTitleResId();

    /* JADX WARN: Type inference failed for: r4v4, types: [com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment$3] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        int i = ShortcutContentObserver.$r8$clinit;
        this.amplifySoundObserver =
                new ShortcutContentObserver.AnonymousClass1(
                        new Handler(context.getMainLooper()), context, 1);
        this.interactionControlObserver =
                new ShortcutContentObserver.AnonymousClass1(new Handler(), getContext(), 2);
        this.magnificationObserver =
                new ShortcutContentObserver.AnonymousClass1(new Handler(), getContext(), 0);
        this.shortcutContentObserver =
                new ShortcutContentObserver(
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment.3
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        ShortcutSwitchPreferenceFragment.this.mResetMenu.setVisible(
                                !TextUtils.isEmpty(
                                        Settings.Secure.getString(
                                                r4.getContentResolver(), r4.getDbKey())));
                        String shortcutService =
                                SecAccessibilityUtils.getShortcutService(
                                        ShortcutSwitchPreferenceFragment.this.getContext(),
                                        ShortcutSwitchPreferenceFragment.this.getShortcutType());
                        Set emptySet =
                                shortcutService == null
                                        ? Collections.emptySet()
                                        : new HashSet(
                                                Arrays.asList(
                                                        shortcutService.split(
                                                                SecAccessibilityUtils
                                                                        .COMPONENT_NAME_SEPARATOR_STRING)));
                        Iterator it =
                                ((ArrayList)
                                                ShortcutSwitchPreferenceFragment.this
                                                        .preferenceControllers)
                                        .iterator();
                        while (it.hasNext()) {
                            AbstractPreferenceController abstractPreferenceController =
                                    (AbstractPreferenceController) it.next();
                            if (emptySet.contains(
                                    abstractPreferenceController.getPreferenceKey())) {
                                abstractPreferenceController.updateState(
                                        ShortcutSwitchPreferenceFragment.this.findPreference(
                                                abstractPreferenceController.getPreferenceKey()));
                            }
                        }
                        ShortcutSwitchPreferenceFragment.this.updatePreferenceStates();
                    }
                };
        int titleResId = getTitleResId();
        FragmentActivity activity = getActivity();
        if (activity == null || titleResId == -1) {
            return;
        }
        activity.setTitle(titleResId);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.reset_option_menu, menu);
        MenuItem findItem = menu.findItem(R.id.menu_reset);
        this.mResetMenu = findItem;
        findItem.setShowAsAction(5);
        this.mResetMenu.setVisible(
                !TextUtils.isEmpty(Settings.Secure.getString(getContentResolver(), getDbKey())));
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        DescriptionPreference descriptionPreference =
                (DescriptionPreference) findPreference("shortcut_description");
        if (descriptionPreference != null) {
            descriptionPreference.setTitle(getDescriptionResId());
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.addOnConfigurationChangedListener(
                        new SecAccessibilityControlTimeoutPreferenceFragment$$ExternalSyntheticLambda0(
                                descriptionPreference));
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_reset) {
            if ("accessibility_qs_targets".equals(getDbKey())) {
                Set shortcutTargetsFromSettings =
                        ShortcutUtils.getShortcutTargetsFromSettings(
                                getContext(), getShortcutType(), UserHandle.myUserId());
                Context context = getContext();
                int shortcutType = getShortcutType();
                HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
                AccessibilityManager accessibilityManager =
                        (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
                if (accessibilityManager != null) {
                    accessibilityManager.enableShortcutsForTargets(
                            false,
                            shortcutType,
                            shortcutTargetsFromSettings,
                            UserHandle.myUserId());
                }
                if (getView() != null) {
                    getView()
                            .announceForAccessibility(
                                    getContext()
                                            .getString(R.string.accessibility_corner_action_reset));
                }
                updatePreferenceStates();
            } else {
                Settings.Secure.putString(getContentResolver(), getDbKey(), ApnSettings.MVNO_NONE);
                if (getView() != null) {
                    getView()
                            .announceForAccessibility(
                                    getContext()
                                            .getString(R.string.accessibility_corner_action_reset));
                }
                updatePreferenceStates();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        ShortcutContentObserver.AnonymousClass1 anonymousClass1 = this.amplifySoundObserver;
        ContentResolver contentResolver = getContentResolver();
        anonymousClass1.getClass();
        contentResolver.unregisterContentObserver(anonymousClass1);
        ShortcutContentObserver.AnonymousClass1 anonymousClass12 = this.interactionControlObserver;
        ContentResolver contentResolver2 = getContentResolver();
        anonymousClass12.getClass();
        contentResolver2.unregisterContentObserver(anonymousClass12);
        ShortcutContentObserver.AnonymousClass1 anonymousClass13 = this.magnificationObserver;
        ContentResolver contentResolver3 = getContentResolver();
        anonymousClass13.getClass();
        contentResolver3.unregisterContentObserver(anonymousClass13);
        AnonymousClass3 anonymousClass3 = this.shortcutContentObserver;
        ContentResolver contentResolver4 = getContentResolver();
        anonymousClass3.getClass();
        contentResolver4.unregisterContentObserver(anonymousClass3);
        getContext().unregisterReceiver(this.broadcastReceiver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        refreshControllerList(getContext());
        this.amplifySoundObserver.register(getContentResolver());
        this.interactionControlObserver.register(getContentResolver());
        this.magnificationObserver.register(getContentResolver());
        register(getContentResolver());
        getContext().registerReceiver(this.broadcastReceiver, intentFilter);
        MenuItem menuItem = this.mResetMenu;
        if (menuItem != null) {
            menuItem.setVisible(
                    !TextUtils.isEmpty(
                            Settings.Secure.getString(getContentResolver(), getDbKey())));
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setHasOptionsMenu(true);
        setDivider(null);
        int dimensionPixelSize =
                getResources().getDimensionPixelSize(R.dimen.list_item_start_padding);
        getListView()
                .addItemDecoration(
                        new DividerItemDecorator(
                                getContext(),
                                getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen.icon_frame_min_width_size)
                                        + dimensionPixelSize,
                                dimensionPixelSize));
    }

    public final void refreshControllerList(Context context) {
        HashMap candidatesMap = ShortcutFeatureManager.getCandidatesMap(context, getShortcutType());
        Iterator it = ((ArrayList) this.preferenceControllers).iterator();
        while (it.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it.next();
            if (candidatesMap.remove(abstractPreferenceController.getPreferenceKey()) == null) {
                ((ShortcutSwitchPreferenceController) abstractPreferenceController)
                        .setVisible(false);
            } else {
                ((ShortcutSwitchPreferenceController) abstractPreferenceController)
                        .setVisible(
                                !excludeComponentSet.contains(
                                        abstractPreferenceController.getPreferenceKey()));
            }
        }
        if (!candidatesMap.isEmpty()) {
            Iterator it2 = candidatesMap.values().iterator();
            while (it2.hasNext()) {
                ShortcutSwitchPreferenceController shortcutSwitchPreferenceController =
                        new ShortcutSwitchPreferenceController(
                                context, (ShortcutCandidate) it2.next(), getShortcutType(), false);
                if (getShortcutType() != 16) {
                    ((ArrayList) this.preferenceControllers)
                            .add(shortcutSwitchPreferenceController);
                    addPreferenceController(shortcutSwitchPreferenceController);
                    shortcutSwitchPreferenceController.displayPreference(getPreferenceScreen());
                }
            }
        }
        updatePreferenceStates();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final void updatePreferenceStates() {
        Toolbar toolbar;
        super.updatePreferenceStates();
        Context context = getContext();
        if (getActivity() == null
                || (toolbar = (Toolbar) getActivity().findViewById(R.id.action_bar)) == null) {
            return;
        }
        Iterator it = ((ArrayList) this.preferenceControllers).iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            if (((ShortcutSwitchPreferenceController) ((AbstractPreferenceController) it.next()))
                    .getThreadEnabled()) {
                i2++;
            }
        }
        if (i2 == 0) {
            toolbar.setTitle(context.getString(getTitleResId()));
            while (i < toolbar.getChildCount()) {
                View childAt = toolbar.getChildAt(i);
                if (childAt instanceof TextView) {
                    ((TextView) childAt).setContentDescription(context.getString(getTitleResId()));
                }
                i++;
            }
            return;
        }
        toolbar.setTitle(
                context.getString(
                        R.string.shortcut_count,
                        Integer.valueOf(i2),
                        Integer.valueOf(((ArrayList) this.preferenceControllers).size())));
        while (i < toolbar.getChildCount()) {
            View childAt2 = toolbar.getChildAt(i);
            if (childAt2 instanceof TextView) {
                ((TextView) childAt2)
                        .setContentDescription(
                                context.getString(
                                        R.string.shortcut_count_description,
                                        Integer.valueOf(i2),
                                        Integer.valueOf(
                                                ((ArrayList) this.preferenceControllers).size())));
            }
            i++;
        }
    }
}
