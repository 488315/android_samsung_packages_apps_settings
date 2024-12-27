package com.android.settings.homepage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.Utils;
import com.android.settings.activityembedding.ActivityEmbeddingRulesController;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.core.PreferenceXmlParserUtils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.HighlightableTopLevelPreferenceAdapter;
import com.android.settings.widget.HomepagePreference;
import com.android.settings.widget.HomepagePreferenceLayoutHelper;
import com.android.settingslib.core.instrumentation.Instrumentable;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.ProKioskManager;
import com.samsung.android.knox.custom.SettingsManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.homepage.HomepageUtils;
import com.samsung.android.settings.homepage.SecTopLevelFeature;
import com.samsung.android.settings.homepage.TopLevelSamsungAccountPreferenceController;
import com.samsung.android.settings.homepage.TopLevelTipsPreferenceController;
import com.samsung.android.settings.homepage.TopLevelUserManualPreferenceController;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.widget.SecDividerItemDecorator;
import com.samsung.android.settings.widget.SecFillBottomItemDecorator;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TopLevelSettings extends DashboardFragment
        implements SplitLayoutListener, PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();
    public ActivityEmbeddingController mActivityEmbeddingController;
    public boolean mGoodSettingsSupported;
    public TopLevelHighlightMixin mHighlightMixin;
    public boolean mIsEmbeddingActivityEnabled;
    public long mLastUpdated;
    public SecDividerItemDecorator mRoundedDecoration;
    public boolean mScrollNeeded;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.homepage.TopLevelSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (new TopLevelSamsungAccountPreferenceController(
                            context, "top_level_samsung_account_for_search")
                    .isAvailable()) {
                ((ArrayList) nonIndexableKeys).remove("top_level_samsung_account_for_search");
            }
            if (new TopLevelTipsPreferenceController(context, "top_level_tips_for_search")
                    .isAvailable()) {
                ((ArrayList) nonIndexableKeys).remove("top_level_tips_for_search");
            }
            if (new TopLevelUserManualPreferenceController(
                            context, "top_level_user_manual_for_search")
                    .isAvailable()) {
                ((ArrayList) nonIndexableKeys).remove("top_level_user_manual_for_search");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            Utils.OnlineHelpMenuState onlineHelpMenuState = Utils.getOnlineHelpMenuState(context);
            new SearchIndexableRaw(context);
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = "top_level_samsung_account_for_search";
            searchIndexableRaw.title = resources.getString(R.string.app_name_samsung_account);
            ((SearchIndexableData) searchIndexableRaw).iconResId =
                    R.drawable.sec_ic_samsung_account;
            ((SearchIndexableData) searchIndexableRaw).intentAction = "android.intent.action.MAIN";
            ((SearchIndexableData) searchIndexableRaw).intentTargetPackage =
                    resources.getString(R.string.config_sec_toplevel_samsung_account_package);
            ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                    resources.getString(R.string.config_sec_toplevel_samsung_account_class);
            searchIndexableRaw.screenTitle = resources.getString(R.string.app_name_samsung_account);
            arrayList.add(searchIndexableRaw);
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw2).key = "top_level_tips_for_search";
            searchIndexableRaw2.title = String.valueOf(Utils.getTipAndUserManualTitleRes(context));
            ((SearchIndexableData) searchIndexableRaw2).iconResId = R.drawable.sec_ic_settings_tips;
            ((SearchIndexableData) searchIndexableRaw2).intentAction = "android.intent.action.MAIN";
            ((SearchIndexableData) searchIndexableRaw2).intentTargetPackage =
                    resources.getString(R.string.config_sec_toplevel_tips_package);
            ((SearchIndexableData) searchIndexableRaw2).intentTargetClass =
                    resources.getString(R.string.config_sec_toplevel_tips_class);
            searchIndexableRaw2.keywords =
                    resources.getString(R.string.keyword_sec_user_manual_for_search);
            searchIndexableRaw2.screenTitle =
                    resources.getString(Utils.getTipAndUserManualTitleRes(context));
            arrayList.add(searchIndexableRaw2);
            if (!onlineHelpMenuState.removeTile) {
                SearchIndexableRaw searchIndexableRaw3 = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw3).key =
                        "top_level_user_manual_for_search";
                searchIndexableRaw3.title = String.valueOf(onlineHelpMenuState.titleRes);
                ((SearchIndexableData) searchIndexableRaw3).iconResId = onlineHelpMenuState.iconRes;
                searchIndexableRaw3.keywords =
                        resources.getString(R.string.keyword_sec_user_manual_for_search);
                ((SearchIndexableData) searchIndexableRaw3).intentAction =
                        "android.intent.action.MAIN";
                ((SearchIndexableData) searchIndexableRaw3).intentTargetPackage =
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG;
                ((SearchIndexableData) searchIndexableRaw3).intentTargetClass =
                        Settings.UserManualLaunchActivity.class.getName();
                searchIndexableRaw3.screenTitle = resources.getString(onlineHelpMenuState.titleRes);
                arrayList.add(searchIndexableRaw3);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            ProKioskManager proKioskManager = ProKioskManager.getInstance();
            if (proKioskManager != null ? proKioskManager.getProKioskState() : false) {
                searchIndexableResource.xmlResId = R.xml.sec_top_level_settings_prokiosk;
            } else {
                searchIndexableResource.xmlResId = R.xml.sec_top_level_settings;
            }
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface PreferenceJob {
        void doForEach(Preference preference);
    }

    public TopLevelSettings() {
        this.mScrollNeeded = true;
        this.mRoundedDecoration = null;
        this.mGoodSettingsSupported = false;
        this.mLastUpdated = 0L;
        Trace.beginSection("TopLevelSettings#Constructor");
        Bundle bundle = new Bundle();
        bundle.putBoolean("need_search_icon_in_action_bar", true);
        setArguments(bundle);
        Trace.endSection();
    }

    public static void iteratePreferences(
            PreferenceGroup preferenceGroup, PreferenceJob preferenceJob) {
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = preferenceGroup.getPreference(i);
            preferenceJob.doForEach(preference);
            if (preference instanceof PreferenceCategory) {
                iteratePreferences((PreferenceCategory) preference, preferenceJob);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final Preference createPreference(Tile tile) {
        return new HomepagePreference(getPrefContext());
    }

    public final void enableDividerItemDecorator(RecyclerView recyclerView, boolean z) {
        if (recyclerView == null) {
            return;
        }
        if (!z) {
            recyclerView.removeItemDecoration(this.mRoundedDecoration);
            return;
        }
        int itemDecorationCount = recyclerView.getItemDecorationCount();
        boolean z2 = false;
        for (int i = 0; i < itemDecorationCount; i++) {
            if (recyclerView.getItemDecorationAt(i) == this.mRoundedDecoration) {
                z2 = true;
            }
        }
        if (z2) {
            return;
        }
        recyclerView.addItemDecoration(this.mRoundedDecoration);
    }

    public final String findPreferenceKey(String str) {
        PreferenceScreen preferenceScreen;
        if (TextUtils.isEmpty(str) || (preferenceScreen = getPreferenceScreen()) == null) {
            return ApnSettings.MVNO_NONE;
        }
        int preferenceCount = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (TextUtils.equals(str, preference.getTitle())) {
                return preference.getKey();
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "TopLevelSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 35;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        return (proKioskManager == null || !proKioskManager.getProKioskState())
                ? R.xml.sec_top_level_settings
                : R.xml.sec_top_level_settings_prokiosk;
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final void highlightPreferenceIfNeeded() {
        HighlightableTopLevelPreferenceAdapter highlightableTopLevelPreferenceAdapter;
        TopLevelHighlightMixin topLevelHighlightMixin = this.mHighlightMixin;
        if (topLevelHighlightMixin == null
                || (highlightableTopLevelPreferenceAdapter =
                                topLevelHighlightMixin.mTopLevelAdapter)
                        == null) {
            return;
        }
        highlightableTopLevelPreferenceAdapter.requestHighlight$2();
    }

    public boolean isActivityEmbedded() {
        if (this.mActivityEmbeddingController == null) {
            this.mActivityEmbeddingController =
                    ActivityEmbeddingController.getInstance(getActivity());
        }
        return this.mActivityEmbeddingController.isActivityEmbedded(getActivity());
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(final Context context) {
        List<Bundle> extractMetadata;
        Trace.beginSection("TopLevelSettings#onAttach");
        super.onAttach(context);
        int preferenceScreenResId = getPreferenceScreenResId();
        Map map = HighlightableMenu.MENU_TO_PREFERENCE_KEY_MAP;
        synchronized (HighlightableMenu.class) {
            if (!HighlightableMenu.sXmlParsed) {
                Log.d("HighlightableMenu", "parsing highlightable menu from xml");
                if (preferenceScreenResId == R.xml.sec_top_level_settings) {
                    SecTopLevelFeature secTopLevelFeature = SecTopLevelFeature.getInstance();
                    if (secTopLevelFeature.mTopLevelPreferenceXmlMeta == null) {
                        secTopLevelFeature.loadTopLevelPreferenceXmlMeta(context);
                    }
                    extractMetadata = secTopLevelFeature.mTopLevelPreferenceXmlMeta;
                } else {
                    try {
                        extractMetadata =
                                PreferenceXmlParserUtils.extractMetadata(
                                        context, preferenceScreenResId, 8194);
                    } catch (IOException | XmlPullParserException e) {
                        Log.e(
                                "HighlightableMenu",
                                "Failed to parse preference xml for getting highlightable menu"
                                    + " keys",
                                e);
                    }
                }
                for (Bundle bundle : extractMetadata) {
                    String string = bundle.getString("highlightable_menu_key");
                    if (!TextUtils.isEmpty(string)) {
                        String string2 =
                                bundle.getString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
                        if (TextUtils.isEmpty(string2)) {
                            Log.w(
                                    "HighlightableMenu",
                                    "Highlightable menu requires android:key but it's missing in"
                                        + " xml: "
                                            + string);
                        } else {
                            ((ArrayMap) HighlightableMenu.MENU_TO_PREFERENCE_KEY_MAP)
                                    .put(string, string2);
                        }
                    }
                }
                if (!((ArrayMap) HighlightableMenu.MENU_TO_PREFERENCE_KEY_MAP).isEmpty()) {
                    HighlightableMenu.sXmlParsed = true;
                    ((ArrayMap) HighlightableMenu.MENU_KEY_COMPAT_MAP)
                            .forEach(
                                    new BiConsumer() { // from class:
                                                       // com.android.settings.homepage.HighlightableMenu$$ExternalSyntheticLambda0
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj, Object obj2) {
                                            String str = (String) obj;
                                            String string3 =
                                                    context.getString(((Integer) obj2).intValue());
                                            Map map2 = HighlightableMenu.MENU_TO_PREFERENCE_KEY_MAP;
                                            String str2 = (String) ((ArrayMap) map2).get(string3);
                                            if (str2 != null) {
                                                ((ArrayMap) map2).put(str, str2);
                                            }
                                        }
                                    });
                }
            }
        }
        Trace.endSection();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        highlightPreferenceIfNeeded();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Trace.beginSection("TopLevelSettings#onCreate");
        super.onCreate(bundle);
        if (SecTopLevelFeature.getInstance().getBoolean("feature_is_embedding_activity_enabled")) {
            this.mIsEmbeddingActivityEnabled = true;
        } else {
            this.mIsEmbeddingActivityEnabled =
                    ActivityEmbeddingUtils.isEmbeddingActivityEnabled(getContext());
        }
        if (this.mRoundedDecoration == null) {
            this.mRoundedDecoration =
                    new SecDividerItemDecorator(
                            (int)
                                    (getResources()
                                                    .getDimension(
                                                            R.dimen
                                                                    .sec_dashboard_tile_image_margin_end)
                                            + getContext()
                                                    .obtainStyledAttributes(
                                                            new int[] {
                                                                android.R.attr
                                                                        .listPreferredItemPaddingStart
                                                            })
                                                    .getDimension(0, 0.0f)
                                            + getResources()
                                                    .getDimension(
                                                            R.dimen.sec_dashboard_tile_image_size)),
                            getContext(),
                            getResources().getDrawable(R.drawable.sec_top_level_list_divider));
        }
        if (!this.mIsEmbeddingActivityEnabled) {
            Trace.endSection();
            return;
        }
        boolean isActivityEmbedded = isActivityEmbedded();
        if (bundle != null) {
            TopLevelHighlightMixin topLevelHighlightMixin =
                    (TopLevelHighlightMixin) bundle.getParcelable("highlight_mixin");
            this.mHighlightMixin = topLevelHighlightMixin;
            if (topLevelHighlightMixin != null) {
                this.mScrollNeeded =
                        !topLevelHighlightMixin.mActivityEmbedded && isActivityEmbedded;
                topLevelHighlightMixin.mActivityEmbedded = isActivityEmbedded;
            }
        }
        if (this.mHighlightMixin == null) {
            TopLevelHighlightMixin topLevelHighlightMixin2 = new TopLevelHighlightMixin();
            topLevelHighlightMixin2.mActivityEmbedded = isActivityEmbedded;
            this.mHighlightMixin = topLevelHighlightMixin2;
        }
        Trace.endSection();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        if (!this.mIsEmbeddingActivityEnabled
                || !(getActivity() instanceof SettingsHomepageActivity)) {
            return super.onCreateAdapter(preferenceScreen);
        }
        TopLevelHighlightMixin topLevelHighlightMixin = this.mHighlightMixin;
        boolean z = this.mScrollNeeded;
        if (TextUtils.isEmpty(topLevelHighlightMixin.mCurrentKey)) {
            Bundle arguments = getArguments();
            String string = arguments.getString(":settings:fragment_args_key");
            String string2 = arguments.getString(":settings:fragment_args_title");
            String findPreferenceKey =
                    (!TextUtils.isEmpty(string) || TextUtils.isEmpty(string2))
                            ? (String)
                                    ((ArrayMap) HighlightableMenu.MENU_TO_PREFERENCE_KEY_MAP)
                                            .get(string)
                            : findPreferenceKey(string2);
            if (TextUtils.isEmpty(findPreferenceKey)) {
                Log.e("TopLevelHighlightMixin", "Invalid highlight menu key: " + string);
                Log.e("TopLevelHighlightMixin", "Invalid highlight menu title: " + string2);
            } else {
                Log.d("TopLevelHighlightMixin", "Menu key: " + string);
                Log.d("TopLevelHighlightMixin", "Menu title: " + string2);
            }
            topLevelHighlightMixin.mCurrentKey = findPreferenceKey;
        }
        Log.d(
                "TopLevelHighlightMixin",
                "onCreateAdapter, pref key: " + topLevelHighlightMixin.mCurrentKey);
        RecyclerView listView = getListView();
        listView.setItemAnimator(null);
        HighlightableTopLevelPreferenceAdapter highlightableTopLevelPreferenceAdapter =
                new HighlightableTopLevelPreferenceAdapter(
                        (SettingsHomepageActivity) getActivity(),
                        preferenceScreen,
                        listView,
                        topLevelHighlightMixin.mCurrentKey,
                        z);
        topLevelHighlightMixin.mTopLevelAdapter = highlightableTopLevelPreferenceAdapter;
        return highlightableTopLevelPreferenceAdapter;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        Trace.beginSection("TopLevelSettings#onCreatePreferences");
        super.onCreatePreferences(bundle, str);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        setDivider(null);
        enableDividerItemDecorator(getListView(), true);
        getListView().addItemDecoration(new SecFillBottomItemDecorator(getContext()));
        FragmentActivity activity = getActivity();
        boolean z = ActivityEmbeddingUtils.SHOULD_ENABLE_LARGE_SCREEN_OPTIMIZATION;
        updateLayout(
                !ActivityEmbeddingController.getInstance(activity).isActivityEmbedded(activity));
        return onCreateView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.preference.PreferenceFragmentCompat.OnPreferenceStartFragmentCallback
    public final void onPreferenceStartFragment(
            PreferenceFragmentCompat preferenceFragmentCompat, Preference preference) {
        if (getActivity() == null) {
            Log.w("TopLevelSettings", "it not attached to an activity.");
            return;
        }
        Bundle extras = preference.getExtras();
        extras.putBoolean("need_search_icon_in_action_bar", true);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getActivity());
        String fragment = preference.getFragment();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = fragment;
        launchRequest.mArguments = extras;
        launchRequest.mSourceMetricsCategory =
                preferenceFragmentCompat instanceof Instrumentable
                        ? ((Instrumentable) preferenceFragmentCompat).getMetricsCategory()
                        : 0;
        int i = -1;
        subSettingLauncher.setTitleRes(-1, null);
        launchRequest.mIsSecondLayerPage = true;
        Intent intent = subSettingLauncher.toIntent();
        FragmentActivity activity = getActivity();
        ArrayList arrayList = HomepageUtils.SEPARATORS;
        if (!Utils.isLaunchModeSingleInstance(activity, intent)
                && (intent.getFlags() & 268435456) == 0) {
            i = CustomDeviceManager.QUICK_PANEL_ALL;
        }
        HomepageUtils.startActivity(activity, intent, i, null);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (this.mHighlightMixin != null
                && TextUtils.equals(
                        preference.getKey(), this.mHighlightMixin.getHighlightPreferenceKey())
                && isActivityEmbedded()) {
            return true;
        }
        ActivityEmbeddingRulesController.registerSubSettingsPairRule(getContext(), true);
        setHighlightPreferenceKey(preference.getKey());
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Trace.beginSection("TopLevelSettings#onResume");
        super.onResume();
        Trace.endSection();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        TopLevelHighlightMixin topLevelHighlightMixin = this.mHighlightMixin;
        if (topLevelHighlightMixin != null) {
            bundle.putParcelable("highlight_mixin", topLevelHighlightMixin);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x0168 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0153 A[SYNTHETIC] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStart() {
        /*
            Method dump skipped, instructions count: 820
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.homepage.TopLevelSettings.onStart():void");
    }

    public final void setHighlightMenuKey(String str) {
        TopLevelHighlightMixin topLevelHighlightMixin = this.mHighlightMixin;
        if (topLevelHighlightMixin == null || topLevelHighlightMixin.mTopLevelAdapter == null) {
            return;
        }
        DialogInterface dialogInterface = topLevelHighlightMixin.mDialog;
        if (dialogInterface != null) {
            topLevelHighlightMixin.onCancel(dialogInterface);
            topLevelHighlightMixin.mDialog.dismiss();
        }
        String str2 = (String) ((ArrayMap) HighlightableMenu.MENU_TO_PREFERENCE_KEY_MAP).get(str);
        if (TextUtils.isEmpty(str2)) {
            Log.e("TopLevelHighlightMixin", "Invalid highlight menu key: " + str);
            return;
        }
        DialogFragment$$ExternalSyntheticOutline0.m("Menu key: ", str, "TopLevelHighlightMixin");
        topLevelHighlightMixin.mCurrentKey = str2;
        topLevelHighlightMixin.mTopLevelAdapter.highlightPreference(str2, true);
    }

    public final void setHighlightPreferenceKey(String str) {
        if (this.mHighlightMixin == null || TextUtils.equals(str, "top_level_support")) {
            return;
        }
        if (TextUtils.equals(str, "my_files_menu") && this.mIsEmbeddingActivityEnabled) {
            return;
        }
        TopLevelHighlightMixin topLevelHighlightMixin = this.mHighlightMixin;
        if (topLevelHighlightMixin.mTopLevelAdapter != null) {
            DialogInterface dialogInterface = topLevelHighlightMixin.mDialog;
            if (dialogInterface != null) {
                topLevelHighlightMixin.onCancel(dialogInterface);
                topLevelHighlightMixin.mDialog.dismiss();
            }
            topLevelHighlightMixin.mPreviousKey = topLevelHighlightMixin.mCurrentKey;
            topLevelHighlightMixin.mCurrentKey = str;
            topLevelHighlightMixin.mTopLevelAdapter.highlightPreference(str, false);
        }
    }

    public final void setMenuHighlightShowed(boolean z) {
        TopLevelHighlightMixin topLevelHighlightMixin = this.mHighlightMixin;
        if (topLevelHighlightMixin == null || topLevelHighlightMixin.mTopLevelAdapter == null) {
            return;
        }
        DialogInterface dialogInterface = topLevelHighlightMixin.mDialog;
        if (dialogInterface != null) {
            topLevelHighlightMixin.onCancel(dialogInterface);
            topLevelHighlightMixin.mDialog.dismiss();
        }
        if (z) {
            topLevelHighlightMixin.mCurrentKey = topLevelHighlightMixin.mHiddenKey;
            topLevelHighlightMixin.mHiddenKey = null;
        } else {
            if (topLevelHighlightMixin.mHiddenKey == null) {
                topLevelHighlightMixin.mHiddenKey = topLevelHighlightMixin.mCurrentKey;
            }
            topLevelHighlightMixin.mCurrentKey = null;
        }
        topLevelHighlightMixin.mTopLevelAdapter.highlightPreference(
                topLevelHighlightMixin.mCurrentKey, z);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final boolean shouldForceRoundedIcon() {
        return false;
    }

    public final void updateLayout(boolean z) {
        RecyclerView listView;
        PreferenceScreen preferenceScreen;
        PreferenceScreen preferenceScreen2;
        if (this.mIsEmbeddingActivityEnabled && (listView = getListView()) != null) {
            ColorDrawable colorDrawable =
                    new ColorDrawable(
                            getResources().getColor(R.color.sec_widget_round_and_bgcolor, null));
            if (z) {
                colorDrawable = null;
            }
            listView.setBackground(colorDrawable);
            enableDividerItemDecorator(listView, z);
            seslSetRoundedCorner(z);
            TopLevelSettings$$ExternalSyntheticLambda2 topLevelSettings$$ExternalSyntheticLambda2 =
                    new TopLevelSettings$$ExternalSyntheticLambda2(this, z);
            if (getPreferenceManager() != null
                    && (preferenceScreen2 = getPreferenceScreen()) != null) {
                iteratePreferences(preferenceScreen2, topLevelSettings$$ExternalSyntheticLambda2);
            }
            final ColorStateList colorAttr =
                    z
                            ? com.android.settingslib.Utils.getColorAttr(
                                    getContext(), android.R.attr.textColorPrimary)
                            : getResources()
                                    .getColorStateList(
                                            R.color.sec_dashboard_simplified_title_text, null);
            final ColorStateList colorAttr2 =
                    z
                            ? com.android.settingslib.Utils.getColorAttr(
                                    getContext(), android.R.attr.textColorSecondary)
                            : getResources()
                                    .getColorStateList(
                                            R.color.sec_dashboard_simplified_summary_text, null);
            PreferenceJob preferenceJob =
                    new PreferenceJob() { // from class:
                                          // com.android.settings.homepage.TopLevelSettings$$ExternalSyntheticLambda3
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // com.android.settings.homepage.TopLevelSettings.PreferenceJob
                        public final void doForEach(Preference preference) {
                            ColorStateList colorStateList = colorAttr;
                            ColorStateList colorStateList2 = colorAttr2;
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    TopLevelSettings.SEARCH_INDEX_DATA_PROVIDER;
                            if (preference
                                    instanceof
                                    HomepagePreferenceLayoutHelper.HomepagePreferenceLayout) {
                                HomepagePreferenceLayoutHelper.HomepagePreferenceLayout
                                        homepagePreferenceLayout =
                                                (HomepagePreferenceLayoutHelper
                                                                .HomepagePreferenceLayout)
                                                        preference;
                                HomepagePreferenceLayoutHelper helper =
                                        homepagePreferenceLayout.getHelper();
                                helper.mTitleTextColor = colorStateList;
                                TextView textView = helper.mTitle;
                                if (textView != null && colorStateList != null) {
                                    textView.setTextColor(colorStateList);
                                }
                                HomepagePreferenceLayoutHelper helper2 =
                                        homepagePreferenceLayout.getHelper();
                                helper2.mSummaryTextColor = colorStateList2;
                                TextView textView2 = helper2.mSummary;
                                if (textView2 == null || colorStateList2 == null) {
                                    return;
                                }
                                textView2.setTextColor(colorStateList2);
                            }
                        }
                    };
            if (getPreferenceManager() != null
                    && (preferenceScreen = getPreferenceScreen()) != null) {
                iteratePreferences(preferenceScreen, preferenceJob);
            }
            RecyclerView.Adapter adapter = listView.getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final void updatePreferenceStates() {
        String str = KnoxUtils.mDeviceType;
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        if (proKioskManager != null && proKioskManager.getProKioskState()) {
            int settingsEnabledItems = proKioskManager.getSettingsEnabledItems();
            int settingsHiddenState = SettingsManager.getInstance().getSettingsHiddenState();
            if (settingsEnabledItems != 0) {
                if (((settingsHiddenState & 1) != 0 || (settingsEnabledItems & 1) == 0)
                        && (((settingsHiddenState & 2) != 0 || (settingsEnabledItems & 2) == 0)
                                && ((settingsHiddenState & 1024) != 0
                                        || (settingsEnabledItems & 4) == 0))) {
                    Log.d("KnoxUtils", "No Settings features Available, finishing");
                }
            }
            finish();
            return;
        }
        super.updatePreferenceStates();
    }

    public TopLevelSettings(TopLevelHighlightMixin topLevelHighlightMixin) {
        this();
        this.mHighlightMixin = topLevelHighlightMixin;
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final Fragment getCallbackFragment() {
        return this;
    }
}
