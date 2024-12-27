package com.samsung.android.settings.navigationbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;
import android.view.IRotationWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NavigationBarGestureDetailedSettings extends SettingsPreferenceFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass5();
    public NavigationBarBackGesturePreference mBackGesturePref;
    public final AnonymousClass2 mBackGestureSensitivityObserver;
    public SecSwitchPreference mBlockGestureWithSPenPreference;
    public SecInsetCategoryPreference mButtonDivider;
    public final AnonymousClass2 mButtonOrderObserver;
    public SecPreferenceCategory mButtonOrderPreference;
    public SecDropDownPreference mButtonPositionPreference;
    public Context mContext;
    public SecInsetCategoryPreference mDetailedTypeDivider;
    public NavigationBarGestureDetailedPreference mDetailedTypePref;
    public SecSwitchPreference mGestureHintPreference;
    public NavigationBarBackGestureIndicatorView mIndicatorView;
    public boolean mIsTaskBarEnabled;
    public Configuration mLastConfiguration;
    public NavigationBarOverlayInteractor mOverlayInteractor;
    public SecSwitchPreference mSwitchAppsWhenHintHidden;
    public WindowManager mWindowManager;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final IntentFilter mIntentFilter =
            new IntentFilter("android.intent.action.OVERLAY_CHANGED");
    public final AnonymousClass1 mOverlayChangedReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    NavigationBarGestureDetailedSettings navigationBarGestureDetailedSettings =
                            NavigationBarGestureDetailedSettings.this;
                    navigationBarGestureDetailedSettings.onConfigurationChanged(
                            navigationBarGestureDetailedSettings
                                    .getContext()
                                    .getResources()
                                    .getConfiguration());
                }
            };
    public final AnonymousClass4 mRotationWatcher =
            new IRotationWatcher
                    .Stub() { // from class:
                              // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings.4
                public final void onRotationChanged(int i) {
                    NavigationBarBackGestureIndicatorView navigationBarBackGestureIndicatorView =
                            NavigationBarGestureDetailedSettings.this.mIndicatorView;
                    if (navigationBarBackGestureIndicatorView != null) {
                        navigationBarBackGestureIndicatorView.mRotation = i;
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings$5, reason: invalid class name */
    public final class AnonymousClass5 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            int navBarMode = NavigationBarSettingsUtil.getNavBarMode(context);
            int i =
                    Settings.Global.getInt(
                            context.getContentResolver(), "navigation_bar_gesture_detail_type", 1);
            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                    NavigationBarGestureDetailedSettings.SEARCH_INDEX_DATA_PROVIDER;
            boolean z = navBarMode == 1 && i == 1;
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (navBarMode == 0) {
                ArrayList arrayList = (ArrayList) nonIndexableKeys;
                AccessibilitySettings$$ExternalSyntheticOutline0.m(
                        arrayList,
                        "back_gesture",
                        "detailed_type",
                        "gesture_hint",
                        "block_gestures_with_spen");
                arrayList.add("switch_apps_when_hint_hidden");
                String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                if (Utils.isTablet()) {
                    Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
                } else {
                    arrayList.add("button_position");
                }
                if (Rune.supportNavigationBarForHardKey()) {
                    arrayList.add("button_order_category");
                    arrayList.add("button_order");
                }
            } else {
                ArrayList arrayList2 = (ArrayList) nonIndexableKeys;
                arrayList2.add("button_position");
                if (z || KnoxUtils.isApplicationRestricted(context, "button_order_category")) {
                    arrayList2.add("button_order_category");
                    arrayList2.add("button_order");
                }
                if (NavigationBarSettingsUtil.isGestureHintOn(context)) {
                    arrayList2.add("switch_apps_when_hint_hidden");
                }
                String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                if (SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION")
                        <= 0) {
                    arrayList2.add("block_gestures_with_spen");
                }
                if (!NavigationBarSettingsUtil.isSupportLegacyFeatures(context)) {
                    arrayList2.add("detailed_type");
                    arrayList2.add("gesture_hint");
                }
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            int navBarMode = NavigationBarSettingsUtil.getNavBarMode(context);
            if (Rune.supportNavigationBarForHardKey()) {
                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                        NavigationBarGestureDetailedSettings.SEARCH_INDEX_DATA_PROVIDER;
                if (navBarMode == 1) {
                    Resources resources = context.getResources();
                    ArrayList arrayList = new ArrayList();
                    SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                            NavigationBarGestureDetailedSettings.class.getName();
                    searchIndexableRaw.title =
                            String.valueOf(R.string.navigationbar_full_screen_gestures);
                    searchIndexableRaw.screenTitle =
                            resources.getString(R.string.navigationbar_gesture_more_options);
                    ((SearchIndexableData) searchIndexableRaw).key = "detailed_type";
                    arrayList.add(searchIndexableRaw);
                    SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw2).intentTargetClass =
                            NavigationBarGestureDetailedSettings.class.getName();
                    searchIndexableRaw2.title =
                            String.valueOf(R.string.navigationbar_gesture_hint_title);
                    searchIndexableRaw2.screenTitle =
                            resources.getString(R.string.navigationbar_gesture_more_options);
                    ((SearchIndexableData) searchIndexableRaw2).key = "gesture_hint";
                    arrayList.add(searchIndexableRaw2);
                    SearchIndexableRaw searchIndexableRaw3 = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw3).intentTargetClass =
                            NavigationBarGestureDetailedSettings.class.getName();
                    searchIndexableRaw3.title =
                            String.valueOf(R.string.navigationbar_switch_apps_when_hint_hidden);
                    searchIndexableRaw3.screenTitle =
                            resources.getString(R.string.navigationbar_gesture_more_options);
                    ((SearchIndexableData) searchIndexableRaw3).key =
                            "switch_apps_when_hint_hidden";
                    arrayList.add(searchIndexableRaw3);
                    SearchIndexableRaw searchIndexableRaw4 = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw4).intentTargetClass =
                            NavigationBarGestureDetailedSettings.class.getName();
                    searchIndexableRaw4.title =
                            String.valueOf(R.string.navigationbar_block_gestures_with_spen_title);
                    searchIndexableRaw4.screenTitle =
                            resources.getString(R.string.navigationbar_gesture_more_options);
                    ((SearchIndexableData) searchIndexableRaw4).key = "block_gestures_with_spen";
                    arrayList.add(searchIndexableRaw4);
                    SearchIndexableRaw searchIndexableRaw5 = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw5).intentTargetClass =
                            NavigationBarGestureDetailedSettings.class.getName();
                    searchIndexableRaw5.title =
                            String.valueOf(R.string.navigationbar_back_gesture_sensitivity_title);
                    searchIndexableRaw5.screenTitle =
                            resources.getString(R.string.navigationbar_gesture_more_options);
                    ((SearchIndexableData) searchIndexableRaw5).key = "back_gesture";
                    arrayList.add(searchIndexableRaw5);
                    SearchIndexableRaw searchIndexableRaw6 = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw6).intentTargetClass =
                            NavigationBarGestureDetailedSettings.class.getName();
                    searchIndexableRaw6.title = String.valueOf(R.string.navigationbar_button_order);
                    searchIndexableRaw6.screenTitle =
                            resources.getString(R.string.navigationbar_gesture_more_options);
                    ((SearchIndexableData) searchIndexableRaw6).key = "button_order";
                    arrayList.add(searchIndexableRaw6);
                    return arrayList;
                }
            }
            return super.getRawDataToIndex(context);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            if (Rune.supportNavigationBarForHardKey()) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className =
                    NavigationBarGestureDetailedSettings.class.getName();
            searchIndexableResource.xmlResId =
                    R.xml.samsung_navigationbar_settings_gesture_detailed_new;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return Rune.supportNavigationBar() && Rune.isNavigationBarEnabled(context);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings$2] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings$4] */
    public NavigationBarGestureDetailedSettings() {
        final int i = 0;
        this.mBackGestureSensitivityObserver =
                new ContentObserver(
                        this,
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings.2
                    public final /* synthetic */ NavigationBarGestureDetailedSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                NavigationBarBackGesturePreference
                                        navigationBarBackGesturePreference =
                                                this.this$0.mBackGesturePref;
                                navigationBarBackGesturePreference.setSeekBarContentDescription(
                                        Integer.toString(
                                                navigationBarBackGesturePreference.mValue));
                                break;
                            default:
                                NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda0
                                        navigationBarGestureDetailedPreference$$ExternalSyntheticLambda0 =
                                                this.this$0.mDetailedTypePref.mPreviewRunnable;
                                if (navigationBarGestureDetailedPreference$$ExternalSyntheticLambda0
                                        != null) {
                                    navigationBarGestureDetailedPreference$$ExternalSyntheticLambda0
                                            .run();
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mButtonOrderObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings.2
                    public final /* synthetic */ NavigationBarGestureDetailedSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                NavigationBarBackGesturePreference
                                        navigationBarBackGesturePreference =
                                                this.this$0.mBackGesturePref;
                                navigationBarBackGesturePreference.setSeekBarContentDescription(
                                        Integer.toString(
                                                navigationBarBackGesturePreference.mValue));
                                break;
                            default:
                                NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda0
                                        navigationBarGestureDetailedPreference$$ExternalSyntheticLambda0 =
                                                this.this$0.mDetailedTypePref.mPreviewRunnable;
                                if (navigationBarGestureDetailedPreference$$ExternalSyntheticLambda0
                                        != null) {
                                    navigationBarGestureDetailedPreference$$ExternalSyntheticLambda0
                                            .run();
                                    break;
                                }
                                break;
                        }
                    }
                };
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return NavigationBarSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    public final void initPreferences$8() {
        updateTitle$2$1();
        addPreferencesFromResource(R.xml.samsung_navigationbar_settings_gesture_detailed_new);
        this.mIsTaskBarEnabled = NavigationBarSettingsUtil.isTaskBarEnabled(this.mContext);
        this.mButtonOrderPreference =
                (SecPreferenceCategory) findPreference("button_order_category");
        SecDropDownPreference secDropDownPreference =
                (SecDropDownPreference) findPreference("button_position");
        this.mButtonPositionPreference = secDropDownPreference;
        if (secDropDownPreference != null) {
            SecPreferenceUtils.applySummaryColor(secDropDownPreference, true);
            updateButtonPosition(this.mIsTaskBarEnabled);
        }
        this.mButtonDivider = (SecInsetCategoryPreference) findPreference("button_divider");
        this.mBlockGestureWithSPenPreference =
                (SecSwitchPreference) findPreference("block_gestures_with_spen");
        NavigationBarBackGesturePreference navigationBarBackGesturePreference =
                (NavigationBarBackGesturePreference) findPreference("back_gesture");
        this.mBackGesturePref = navigationBarBackGesturePreference;
        if (navigationBarBackGesturePreference != null) {
            navigationBarBackGesturePreference.setSeekBarContentDescription(
                    Integer.toString(navigationBarBackGesturePreference.mValue));
            this.mBackGesturePref.mIndicatorView = this.mIndicatorView;
        }
        NavigationBarGestureDetailedPreference navigationBarGestureDetailedPreference =
                (NavigationBarGestureDetailedPreference) findPreference("detailed_type");
        this.mDetailedTypePref = navigationBarGestureDetailedPreference;
        if (navigationBarGestureDetailedPreference != null) {
            if (navigationBarGestureDetailedPreference.mSwipeSideAndBottomAnimator == null) {
                navigationBarGestureDetailedPreference.mSwipeSideAndBottomAnimator =
                        (LottieAnimationView)
                                navigationBarGestureDetailedPreference.mRootView.findViewById(
                                        R.id.navigationbar_gesture_side_and_bottom_animation);
            }
            if (navigationBarGestureDetailedPreference.mSwipeBottomAnimator == null) {
                navigationBarGestureDetailedPreference.mSwipeBottomAnimator =
                        (LottieAnimationView)
                                navigationBarGestureDetailedPreference.mRootView.findViewById(
                                        R.id.navigationbar_gesture_bottom_animation);
            }
            navigationBarGestureDetailedPreference.mAnimationIdx = 0;
            navigationBarGestureDetailedPreference.mLastAnimationIdx = 0;
            boolean isTalkBackEnabled =
                    Utils.isTalkBackEnabled(navigationBarGestureDetailedPreference.getContext());
            navigationBarGestureDetailedPreference.mA11yIdx = isTalkBackEnabled ? 1 : 0;
            navigationBarGestureDetailedPreference.updateDescription(
                    navigationBarGestureDetailedPreference.mAnimationIdx,
                    isTalkBackEnabled ? 1 : 0);
            LottieAnimationView lottieAnimationView =
                    navigationBarGestureDetailedPreference.mSwipeSideAndBottomAnimator;
            lottieAnimationView.lottieDrawable.animator.addUpdateListener(
                    navigationBarGestureDetailedPreference.mAnimatorListener);
        }
        this.mDetailedTypeDivider =
                (SecInsetCategoryPreference) findPreference("detailed_type_divider");
        this.mGestureHintPreference = (SecSwitchPreference) findPreference("gesture_hint");
        this.mSwitchAppsWhenHintHidden =
                (SecSwitchPreference) findPreference("switch_apps_when_hint_hidden");
        final int i = 0;
        this.mButtonPositionPreference.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings$$ExternalSyntheticLambda0
                    public final /* synthetic */ NavigationBarGestureDetailedSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        NavigationBarGestureDetailedSettings navigationBarGestureDetailedSettings =
                                this.f$0;
                        switch (i) {
                            case 0:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                int parseInt = Integer.parseInt((String) obj);
                                LoggingHelper.insertEventLogging(0, 747001, parseInt);
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigationbar_key_position",
                                        (!navigationBarGestureDetailedSettings.mIsTaskBarEnabled
                                                        || parseInt == 0)
                                                ? parseInt
                                                : 2);
                                SecDropDownPreference secDropDownPreference2 =
                                        navigationBarGestureDetailedSettings
                                                .mButtonPositionPreference;
                                if (navigationBarGestureDetailedSettings.mIsTaskBarEnabled) {
                                    parseInt = parseInt == 0 ? 0 : 1;
                                }
                                secDropDownPreference2.setValueIndex(parseInt);
                                break;
                            case 1:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider2 =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                boolean booleanValue = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigation_bar_block_gestures_with_spen",
                                        booleanValue ? 1 : 0);
                                navigationBarGestureDetailedSettings.mBlockGestureWithSPenPreference
                                        .setChecked(booleanValue);
                                LoggingHelper.insertEventLogging(0, 7489, booleanValue ? 1L : 0L);
                                break;
                            case 2:
                                int navBarMode =
                                        NavigationBarSettingsUtil.getNavBarMode(
                                                navigationBarGestureDetailedSettings.mContext);
                                boolean booleanValue2 = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigation_bar_gesture_hint",
                                        booleanValue2 ? 1 : 0);
                                navigationBarGestureDetailedSettings.mGestureHintPreference
                                        .setChecked(booleanValue2);
                                if (navBarMode == 1) {
                                    LoggingHelper.insertEventLogging(
                                            0, 7487, booleanValue2 ? 1L : 0L);
                                }
                                navigationBarGestureDetailedSettings.mOverlayInteractor
                                        .setInteractionMode();
                                break;
                            default:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider3 =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                boolean booleanValue3 = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigationbar_switch_apps_when_hint_hidden",
                                        booleanValue3 ? 1 : 0);
                                navigationBarGestureDetailedSettings.mSwitchAppsWhenHintHidden
                                        .setChecked(booleanValue3);
                                break;
                        }
                        return false;
                    }
                });
        final int i2 = 1;
        this.mBlockGestureWithSPenPreference.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings$$ExternalSyntheticLambda0
                    public final /* synthetic */ NavigationBarGestureDetailedSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        NavigationBarGestureDetailedSettings navigationBarGestureDetailedSettings =
                                this.f$0;
                        switch (i2) {
                            case 0:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                int parseInt = Integer.parseInt((String) obj);
                                LoggingHelper.insertEventLogging(0, 747001, parseInt);
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigationbar_key_position",
                                        (!navigationBarGestureDetailedSettings.mIsTaskBarEnabled
                                                        || parseInt == 0)
                                                ? parseInt
                                                : 2);
                                SecDropDownPreference secDropDownPreference2 =
                                        navigationBarGestureDetailedSettings
                                                .mButtonPositionPreference;
                                if (navigationBarGestureDetailedSettings.mIsTaskBarEnabled) {
                                    parseInt = parseInt == 0 ? 0 : 1;
                                }
                                secDropDownPreference2.setValueIndex(parseInt);
                                break;
                            case 1:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider2 =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                boolean booleanValue = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigation_bar_block_gestures_with_spen",
                                        booleanValue ? 1 : 0);
                                navigationBarGestureDetailedSettings.mBlockGestureWithSPenPreference
                                        .setChecked(booleanValue);
                                LoggingHelper.insertEventLogging(0, 7489, booleanValue ? 1L : 0L);
                                break;
                            case 2:
                                int navBarMode =
                                        NavigationBarSettingsUtil.getNavBarMode(
                                                navigationBarGestureDetailedSettings.mContext);
                                boolean booleanValue2 = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigation_bar_gesture_hint",
                                        booleanValue2 ? 1 : 0);
                                navigationBarGestureDetailedSettings.mGestureHintPreference
                                        .setChecked(booleanValue2);
                                if (navBarMode == 1) {
                                    LoggingHelper.insertEventLogging(
                                            0, 7487, booleanValue2 ? 1L : 0L);
                                }
                                navigationBarGestureDetailedSettings.mOverlayInteractor
                                        .setInteractionMode();
                                break;
                            default:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider3 =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                boolean booleanValue3 = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigationbar_switch_apps_when_hint_hidden",
                                        booleanValue3 ? 1 : 0);
                                navigationBarGestureDetailedSettings.mSwitchAppsWhenHintHidden
                                        .setChecked(booleanValue3);
                                break;
                        }
                        return false;
                    }
                });
        final int i3 = 2;
        this.mGestureHintPreference.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings$$ExternalSyntheticLambda0
                    public final /* synthetic */ NavigationBarGestureDetailedSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        NavigationBarGestureDetailedSettings navigationBarGestureDetailedSettings =
                                this.f$0;
                        switch (i3) {
                            case 0:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                int parseInt = Integer.parseInt((String) obj);
                                LoggingHelper.insertEventLogging(0, 747001, parseInt);
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigationbar_key_position",
                                        (!navigationBarGestureDetailedSettings.mIsTaskBarEnabled
                                                        || parseInt == 0)
                                                ? parseInt
                                                : 2);
                                SecDropDownPreference secDropDownPreference2 =
                                        navigationBarGestureDetailedSettings
                                                .mButtonPositionPreference;
                                if (navigationBarGestureDetailedSettings.mIsTaskBarEnabled) {
                                    parseInt = parseInt == 0 ? 0 : 1;
                                }
                                secDropDownPreference2.setValueIndex(parseInt);
                                break;
                            case 1:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider2 =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                boolean booleanValue = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigation_bar_block_gestures_with_spen",
                                        booleanValue ? 1 : 0);
                                navigationBarGestureDetailedSettings.mBlockGestureWithSPenPreference
                                        .setChecked(booleanValue);
                                LoggingHelper.insertEventLogging(0, 7489, booleanValue ? 1L : 0L);
                                break;
                            case 2:
                                int navBarMode =
                                        NavigationBarSettingsUtil.getNavBarMode(
                                                navigationBarGestureDetailedSettings.mContext);
                                boolean booleanValue2 = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigation_bar_gesture_hint",
                                        booleanValue2 ? 1 : 0);
                                navigationBarGestureDetailedSettings.mGestureHintPreference
                                        .setChecked(booleanValue2);
                                if (navBarMode == 1) {
                                    LoggingHelper.insertEventLogging(
                                            0, 7487, booleanValue2 ? 1L : 0L);
                                }
                                navigationBarGestureDetailedSettings.mOverlayInteractor
                                        .setInteractionMode();
                                break;
                            default:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider3 =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                boolean booleanValue3 = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigationbar_switch_apps_when_hint_hidden",
                                        booleanValue3 ? 1 : 0);
                                navigationBarGestureDetailedSettings.mSwitchAppsWhenHintHidden
                                        .setChecked(booleanValue3);
                                break;
                        }
                        return false;
                    }
                });
        final int i4 = 3;
        this.mSwitchAppsWhenHintHidden.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings$$ExternalSyntheticLambda0
                    public final /* synthetic */ NavigationBarGestureDetailedSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        NavigationBarGestureDetailedSettings navigationBarGestureDetailedSettings =
                                this.f$0;
                        switch (i4) {
                            case 0:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                int parseInt = Integer.parseInt((String) obj);
                                LoggingHelper.insertEventLogging(0, 747001, parseInt);
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigationbar_key_position",
                                        (!navigationBarGestureDetailedSettings.mIsTaskBarEnabled
                                                        || parseInt == 0)
                                                ? parseInt
                                                : 2);
                                SecDropDownPreference secDropDownPreference2 =
                                        navigationBarGestureDetailedSettings
                                                .mButtonPositionPreference;
                                if (navigationBarGestureDetailedSettings.mIsTaskBarEnabled) {
                                    parseInt = parseInt == 0 ? 0 : 1;
                                }
                                secDropDownPreference2.setValueIndex(parseInt);
                                break;
                            case 1:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider2 =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                boolean booleanValue = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigation_bar_block_gestures_with_spen",
                                        booleanValue ? 1 : 0);
                                navigationBarGestureDetailedSettings.mBlockGestureWithSPenPreference
                                        .setChecked(booleanValue);
                                LoggingHelper.insertEventLogging(0, 7489, booleanValue ? 1L : 0L);
                                break;
                            case 2:
                                int navBarMode =
                                        NavigationBarSettingsUtil.getNavBarMode(
                                                navigationBarGestureDetailedSettings.mContext);
                                boolean booleanValue2 = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigation_bar_gesture_hint",
                                        booleanValue2 ? 1 : 0);
                                navigationBarGestureDetailedSettings.mGestureHintPreference
                                        .setChecked(booleanValue2);
                                if (navBarMode == 1) {
                                    LoggingHelper.insertEventLogging(
                                            0, 7487, booleanValue2 ? 1L : 0L);
                                }
                                navigationBarGestureDetailedSettings.mOverlayInteractor
                                        .setInteractionMode();
                                break;
                            default:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider3 =
                                        NavigationBarGestureDetailedSettings
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarGestureDetailedSettings.getClass();
                                boolean booleanValue3 = ((Boolean) obj).booleanValue();
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedSettings.getContentResolver(),
                                        "navigationbar_switch_apps_when_hint_hidden",
                                        booleanValue3 ? 1 : 0);
                                navigationBarGestureDetailedSettings.mSwitchAppsWhenHintHidden
                                        .setChecked(booleanValue3);
                                break;
                        }
                        return false;
                    }
                });
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mLastConfiguration.orientation != configuration.orientation) {
            setPreferenceScreen(null);
            initPreferences$8();
        }
        this.mLastConfiguration.updateFrom(configuration);
        updatePreferences$9();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context applicationContext = getActivity().getApplicationContext();
        this.mContext = applicationContext;
        this.mOverlayInteractor = new NavigationBarOverlayInteractor(applicationContext);
        FragmentActivity activity = getActivity();
        NavigationBarBackGestureIndicatorView navigationBarBackGestureIndicatorView =
                new NavigationBarBackGestureIndicatorView(activity);
        navigationBarBackGestureIndicatorView.mCutoutL = 0;
        navigationBarBackGestureIndicatorView.mCutoutR = 0;
        ViewGroup viewGroup =
                (ViewGroup)
                        LayoutInflater.from(activity)
                                .inflate(
                                        R.layout
                                                .samsung_navigationbar_back_gesture_indicator_container,
                                        (ViewGroup) navigationBarBackGestureIndicatorView,
                                        false);
        navigationBarBackGestureIndicatorView.mLayout = viewGroup;
        if (viewGroup != null) {
            navigationBarBackGestureIndicatorView.addView(viewGroup);
            NavigationBarBackGestureIndicatorDrawable navigationBarBackGestureIndicatorDrawable =
                    new NavigationBarBackGestureIndicatorDrawable(activity, 30);
            navigationBarBackGestureIndicatorView.mBottomDrawable =
                    navigationBarBackGestureIndicatorDrawable;
            ((ImageView) viewGroup.findViewById(R.id.indicator_bottom))
                    .setImageDrawable(navigationBarBackGestureIndicatorDrawable);
            NavigationBarBackGestureIndicatorDrawable navigationBarBackGestureIndicatorDrawable2 =
                    new NavigationBarBackGestureIndicatorDrawable(activity, 10);
            navigationBarBackGestureIndicatorView.mLeftDrawable =
                    navigationBarBackGestureIndicatorDrawable2;
            NavigationBarBackGestureIndicatorDrawable navigationBarBackGestureIndicatorDrawable3 =
                    new NavigationBarBackGestureIndicatorDrawable(activity, 20);
            navigationBarBackGestureIndicatorView.mRightDrawable =
                    navigationBarBackGestureIndicatorDrawable3;
            ImageView imageView = (ImageView) viewGroup.findViewById(R.id.indicator_left);
            ImageView imageView2 = (ImageView) viewGroup.findViewById(R.id.indicator_right);
            imageView.setImageDrawable(navigationBarBackGestureIndicatorDrawable2);
            imageView2.setImageDrawable(navigationBarBackGestureIndicatorDrawable3);
        }
        this.mIndicatorView = navigationBarBackGestureIndicatorView;
        this.mWindowManager = (WindowManager) getActivity().getSystemService("window");
        this.mIndicatorView.mRotation =
                this.mContext.getResources().getConfiguration().windowConfiguration.getRotation();
        try {
            WindowManagerGlobal.getWindowManagerService().watchRotation(this.mRotationWatcher, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setAnimationAllowed(true);
        Configuration configuration = new Configuration();
        this.mLastConfiguration = configuration;
        configuration.updateFrom(this.mContext.getResources().getConfiguration());
        initPreferences$8();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        LottieAnimationView lottieAnimationView;
        super.onDestroy();
        NavigationBarGestureDetailedPreference navigationBarGestureDetailedPreference =
                this.mDetailedTypePref;
        if (navigationBarGestureDetailedPreference == null
                || (lottieAnimationView =
                                navigationBarGestureDetailedPreference.mSwipeSideAndBottomAnimator)
                        == null) {
            return;
        }
        lottieAnimationView.pauseAnimation();
        LottieDrawable lottieDrawable =
                navigationBarGestureDetailedPreference.mSwipeSideAndBottomAnimator.lottieDrawable;
        lottieDrawable.animator.removeAllUpdateListeners();
        lottieDrawable.animator.addUpdateListener(lottieDrawable.progressUpdateListener);
        navigationBarGestureDetailedPreference.mSwipeSideAndBottomAnimator.lottieDrawable.animator
                .removeUpdateListener(navigationBarGestureDetailedPreference.mAnimatorListener);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mWindowManager.removeView(this.mIndicatorView);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        WindowManager windowManager = this.mWindowManager;
        NavigationBarBackGestureIndicatorView navigationBarBackGestureIndicatorView =
                this.mIndicatorView;
        windowManager.addView(
                navigationBarBackGestureIndicatorView,
                navigationBarBackGestureIndicatorView.getLayoutParams());
        updatePreferences$9();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mIntentFilter.addDataScheme("package");
        this.mIntentFilter.addDataSchemeSpecificPart(
                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME, 0);
        this.mContext.registerReceiverAsUser(
                this.mOverlayChangedReceiver, UserHandle.ALL, this.mIntentFilter, null, null);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("navigation_bar_back_gesture_sensitivity"),
                        false,
                        this.mBackGestureSensitivityObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("navigationbar_key_order"),
                        false,
                        this.mButtonOrderObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mContext.unregisterReceiver(this.mOverlayChangedReceiver);
        this.mContext
                .getContentResolver()
                .unregisterContentObserver(this.mBackGestureSensitivityObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mButtonOrderObserver);
        try {
            WindowManagerGlobal.getWindowManagerService()
                    .removeRotationWatcher(this.mRotationWatcher);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void updateButtonPosition(boolean z) {
        if (z) {
            this.mButtonPositionPreference.setEntries(R.array.taskbar_position_entries);
            this.mButtonPositionPreference.setEntryValues(R.array.taskbar_position_values);
        } else {
            this.mButtonPositionPreference.setEntries(R.array.navigationbar_position_entries);
            this.mButtonPositionPreference.setEntryValues(R.array.navigationbar_position_values);
        }
    }

    public final void updatePreferences$9() {
        if (!Rune.isNavigationBarEnabled(this.mContext)) {
            Log.d(
                    "NavigationBarGestureDetailedSettings",
                    "Navigation bar settings can't open in DeX mode or non-primary user.");
            finish();
        }
        int navBarMode = NavigationBarSettingsUtil.getNavBarMode(this.mContext);
        boolean z =
                !Utils.isTablet()
                        && getContext()
                                .getResources()
                                .getBoolean(android.R.bool.config_orderUnlockAndWake);
        boolean z2 =
                Settings.Global.getInt(
                                getContentResolver(), "navigation_bar_block_gestures_with_spen", 0)
                        != 0;
        boolean isTaskBarEnabled = NavigationBarSettingsUtil.isTaskBarEnabled(this.mContext);
        int i = Settings.Global.getInt(getContentResolver(), "navigationbar_key_position", 2);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mDetailedTypePref.setOrder(100);
        this.mDetailedTypeDivider.setOrder(101);
        this.mButtonOrderPreference.setOrder(102);
        this.mButtonPositionPreference.setOrder(103);
        this.mButtonDivider.setOrder(104);
        this.mGestureHintPreference.setOrder(105);
        this.mSwitchAppsWhenHintHidden.setOrder(106);
        this.mBlockGestureWithSPenPreference.setOrder(107);
        this.mBackGesturePref.setOrder(108);
        if (navBarMode == 0) {
            preferenceScreen.removePreference(this.mBlockGestureWithSPenPreference);
            preferenceScreen.removePreference(this.mBackGesturePref);
            preferenceScreen.removePreference(this.mDetailedTypePref);
            preferenceScreen.removePreference(this.mDetailedTypeDivider);
            preferenceScreen.removePreference(this.mGestureHintPreference);
            preferenceScreen.removePreference(this.mSwitchAppsWhenHintHidden);
            preferenceScreen.addPreference(this.mButtonOrderPreference);
            if (Utils.isTablet()) {
                preferenceScreen.addPreference(this.mButtonPositionPreference);
                if (this.mIsTaskBarEnabled != isTaskBarEnabled) {
                    this.mIsTaskBarEnabled = isTaskBarEnabled;
                    updateButtonPosition(isTaskBarEnabled);
                }
                SecDropDownPreference secDropDownPreference = this.mButtonPositionPreference;
                if (this.mIsTaskBarEnabled) {
                    i = i == 0 ? 0 : 1;
                }
                secDropDownPreference.setValueIndex(i);
            } else {
                Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
                Log.d(
                        "NavigationBarGestureDetailedSettings",
                        "This device does not support button position");
                preferenceScreen.removePreference(this.mButtonPositionPreference);
            }
            preferenceScreen.addPreference(this.mButtonDivider);
        } else {
            preferenceScreen.removePreference(this.mButtonOrderPreference);
            preferenceScreen.removePreference(this.mButtonPositionPreference);
            preferenceScreen.removePreference(this.mButtonDivider);
            boolean isGestureHintOn = NavigationBarSettingsUtil.isGestureHintOn(this.mContext);
            boolean z3 =
                    Settings.Global.getInt(
                                    getContentResolver(),
                                    "navigationbar_switch_apps_when_hint_hidden",
                                    0)
                            != 0;
            int i2 =
                    Settings.Global.getInt(
                            this.mContext.getContentResolver(),
                            "navigation_bar_gesture_detail_type",
                            1);
            if (SemFloatingFeature.getInstance()
                            .getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION")
                    > 0) {
                preferenceScreen.addPreference(this.mBlockGestureWithSPenPreference);
            } else {
                Log.d(
                        "NavigationBarGestureDetailedSettings",
                        "This device does not support S pen.");
                preferenceScreen.removePreference(this.mBlockGestureWithSPenPreference);
            }
            this.mBlockGestureWithSPenPreference.setChecked(z2);
            getPreferenceScreen().addPreference(this.mBackGesturePref);
            this.mBackGesturePref.updateDetailInfo();
            this.mIndicatorView.mNavBarCanMove = z;
            if (NavigationBarSettingsUtil.isSupportLegacyFeatures(this.mContext)) {
                preferenceScreen.addPreference(this.mDetailedTypePref);
                this.mHandler.postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedSettings$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                NavigationBarGestureDetailedPreference
                                        navigationBarGestureDetailedPreference =
                                                NavigationBarGestureDetailedSettings.this
                                                        .mDetailedTypePref;
                                if (!navigationBarGestureDetailedPreference.mSwipeBottomAnimator
                                        .lottieDrawable.isAnimating()) {
                                    navigationBarGestureDetailedPreference.mSwipeBottomAnimator
                                            .resumeAnimation();
                                }
                                if (navigationBarGestureDetailedPreference
                                        .mSwipeSideAndBottomAnimator.lottieDrawable.isAnimating()) {
                                    return;
                                }
                                if (!navigationBarGestureDetailedPreference.mIsWaiting) {
                                    navigationBarGestureDetailedPreference
                                            .mSwipeSideAndBottomAnimator.resumeAnimation();
                                } else {
                                    navigationBarGestureDetailedPreference.mHandler
                                            .removeCallbacksAndMessages(null);
                                    navigationBarGestureDetailedPreference.mHandler.postDelayed(
                                            new NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda4(
                                                    navigationBarGestureDetailedPreference, 1),
                                            1000L);
                                }
                            }
                        },
                        100L);
                preferenceScreen.addPreference(this.mDetailedTypeDivider);
                preferenceScreen.addPreference(this.mGestureHintPreference);
                this.mGestureHintPreference.setChecked(isGestureHintOn);
                this.mSwitchAppsWhenHintHidden.setChecked(z3);
                this.mGestureHintPreference.setTitle(
                        this.mContext.getString(
                                i2 == 1
                                        ? R.string.navigationbar_gesture_hint_title
                                        : R.string.navigationbar_gesture_hints_title));
                this.mSwitchAppsWhenHintHidden.setTitle(
                        this.mContext.getString(
                                i2 == 1
                                        ? R.string.navigationbar_switch_apps_when_hint_hidden
                                        : R.string.navigationbar_switch_apps_when_hints_hidden));
                if (isGestureHintOn) {
                    preferenceScreen.removePreference(this.mSwitchAppsWhenHintHidden);
                } else {
                    preferenceScreen.addPreference(this.mSwitchAppsWhenHintHidden);
                }
                if (i2 == 0) {
                    preferenceScreen.addPreference(this.mButtonOrderPreference);
                    if (isGestureHintOn || NavigationBarSettingsUtil.isSupportSearcle()) {
                        preferenceScreen.removePreference(this.mBackGesturePref);
                    }
                    preferenceScreen.addPreference(this.mButtonDivider);
                }
            } else {
                preferenceScreen.removePreference(this.mDetailedTypePref);
                preferenceScreen.removePreference(this.mDetailedTypeDivider);
                preferenceScreen.removePreference(this.mGestureHintPreference);
                preferenceScreen.removePreference(this.mSwitchAppsWhenHintHidden);
            }
        }
        updateTitle$2$1();
    }

    public final void updateTitle$2$1() {
        if (getContext() == null || getActivity() == null) {
            return;
        }
        if (NavigationBarSettingsUtil.getNavBarMode(getContext()) == 0) {
            getActivity().setTitle(R.string.navigationbar_buttons);
        } else {
            getActivity().setTitle(R.string.navigationbar_full_screen_gestures);
        }
    }
}
