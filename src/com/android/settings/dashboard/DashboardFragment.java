package com.android.settings.dashboard;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import androidx.core.view.KeyEventDispatcher$Component;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.CategoryMixin;
import com.android.settings.core.PreferenceControllerListHelper;
import com.android.settings.dashboard.DashboardFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.utils.ThreadUtils;
import com.samsung.android.settings.Trace;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DashboardFragment extends SettingsPreferenceFragment implements BasePreferenceController.UiBlockListener {
    public static final String CATEGORY = "category";
    private static final String TAG = "DashboardFragment";
    private static final long TIMEOUT_MILLIS = 50;
    UiBlockerController mBlockerController;
    private DashboardFeatureProvider mDashboardFeatureProvider;
    private boolean mListeningToCategoryChange;
    private DashboardTilePlaceholderPreferenceController mPlaceholderPreferenceController;
    final ArrayMap<String, List<DashboardFeatureProviderImpl.AnonymousClass1>> mDashboardTilePrefKeys = new ArrayMap<>();
    private final Map<Class, List<AbstractPreferenceController>> mPreferenceControllers = new ArrayMap();
    private final List<DashboardFeatureProviderImpl.AnonymousClass1> mRegisteredObservers = new ArrayList();
    private final List<AbstractPreferenceController> mControllers = new ArrayList();
    private boolean mFirstOnResume = true;

    public static void $r8$lambda$LM966RpCTbdrKoeN8Vi8X9x2SYo(DashboardFragment dashboardFragment, ContentResolver contentResolver, DashboardFeatureProviderImpl.AnonymousClass1 anonymousClass1) {
        dashboardFragment.getClass();
        Log.d(TAG, "unregister observer: @" + Integer.toHexString(anonymousClass1.hashCode()) + ", uri: " + anonymousClass1.val$uri);
        dashboardFragment.mRegisteredObservers.remove(anonymousClass1);
        contentResolver.unregisterContentObserver(anonymousClass1);
    }

    public static /* synthetic */ void $r8$lambda$bl5y84BjkU1V5p_2MS5gk70M2Zs(DashboardFragment dashboardFragment, ContentResolver contentResolver, DashboardFeatureProviderImpl.AnonymousClass1 anonymousClass1) {
        if (dashboardFragment.mRegisteredObservers.contains(anonymousClass1)) {
            return;
        }
        dashboardFragment.registerDynamicDataObserver(contentResolver, anonymousClass1);
    }

    public static /* synthetic */ void $r8$lambda$mpXoqG3LnUzwhZpkteEEem44fW4(DashboardFragment dashboardFragment, List list) {
        dashboardFragment.updatePreferenceVisibility(dashboardFragment.mPreferenceControllers);
        ((ArrayList) list).forEach(new DashboardFragment$$ExternalSyntheticLambda5(1));
    }

    public void addPreferenceController(AbstractPreferenceController abstractPreferenceController) {
        if (this.mPreferenceControllers.get(abstractPreferenceController.getClass()) == null) {
            this.mPreferenceControllers.put(abstractPreferenceController.getClass(), new ArrayList());
        }
        this.mPreferenceControllers.get(abstractPreferenceController.getClass()).add(abstractPreferenceController);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.dashboard.DashboardFragment$$ExternalSyntheticLambda8] */
    public void checkUiBlocker(List<AbstractPreferenceController> list) {
        Trace.beginSection("DashboardFragment#checkUiBlocker");
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        list.forEach(new Consumer() { // from class: com.android.settings.dashboard.DashboardFragment$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DashboardFragment dashboardFragment = DashboardFragment.this;
                List list2 = arrayList;
                List list3 = arrayList2;
                AbstractPreferenceController abstractPreferenceController = (AbstractPreferenceController) obj;
                dashboardFragment.getClass();
                if ((abstractPreferenceController instanceof BasePreferenceController.UiBlocker) && abstractPreferenceController.isAvailable()) {
                    BasePreferenceController basePreferenceController = (BasePreferenceController) abstractPreferenceController;
                    basePreferenceController.setUiBlockListener(dashboardFragment);
                    list2.add(abstractPreferenceController.getPreferenceKey());
                    list3.add(basePreferenceController);
                }
            }
        });
        if (!arrayList.isEmpty()) {
            final UiBlockerController uiBlockerController = new UiBlockerController();
            uiBlockerController.mCountDownLatch = new CountDownLatch(arrayList.size());
            uiBlockerController.mBlockerFinished = arrayList.isEmpty();
            HashSet hashSet = new HashSet(arrayList);
            uiBlockerController.mKeys = hashSet;
            uiBlockerController.mTimeoutMillis = 300L;
            this.mBlockerController = uiBlockerController;
            final ?? r0 = new Runnable() { // from class: com.android.settings.dashboard.DashboardFragment$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    DashboardFragment.$r8$lambda$mpXoqG3LnUzwhZpkteEEem44fW4(DashboardFragment.this, arrayList2);
                }
            };
            if (!hashSet.isEmpty()) {
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settings.dashboard.UiBlockerController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UiBlockerController uiBlockerController2 = UiBlockerController.this;
                        Runnable runnable = r0;
                        uiBlockerController2.getClass();
                        try {
                            uiBlockerController2.mCountDownLatch.await(uiBlockerController2.mTimeoutMillis, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException unused) {
                            Log.w("UiBlockerController", "interrupted");
                        }
                        uiBlockerController2.mBlockerFinished = true;
                        ThreadUtils.postOnMainThread(runnable);
                    }
                });
            }
        }
        Trace.endSection();
    }

    public Preference createPreference(Tile tile) {
        int ordinal = tile.getType().ordinal();
        if (ordinal == 1) {
            Preference preference = new Preference(getPrefContext());
            preference.setWidgetLayoutResource(R.layout.preference_external_action_icon);
            return preference;
        }
        if (ordinal == 2) {
            return new SwitchPreferenceCompat(getPrefContext());
        }
        if (ordinal == 3) {
            return new PrimarySwitchPreference(getPrefContext());
        }
        if (ordinal != 4) {
            return new Preference(getPrefContext());
        }
        MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeatureProvider;
        FragmentActivity activity = getActivity();
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(MetricsFeatureProvider.getAttribution(activity), 1835, getMetricsCategory(), 0, tile.getKey(getContext()));
        return new PreferenceCategory(getPrefContext());
    }

    public List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        return null;
    }

    public void displayResourceTilesToScreen(PreferenceScreen preferenceScreen) {
        this.mPreferenceControllers.values().stream().flatMap(new EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3()).forEach(new DashboardFragment$$ExternalSyntheticLambda6(0, preferenceScreen));
    }

    public boolean displayTile(Tile tile) {
        return true;
    }

    public void forceUpdatePreferences() {
        Map<Class, List<AbstractPreferenceController>> map;
        if (getPreferenceScreen() == null || (map = this.mPreferenceControllers) == null) {
            return;
        }
        Iterator<List<AbstractPreferenceController>> it = map.values().iterator();
        while (it.hasNext()) {
            for (AbstractPreferenceController abstractPreferenceController : it.next()) {
                Preference findPreference = findPreference(abstractPreferenceController.getPreferenceKey());
                if (findPreference != null) {
                    boolean isAvailable = abstractPreferenceController.isAvailable();
                    if (isAvailable) {
                        abstractPreferenceController.updateState(findPreference);
                    }
                    findPreference.setVisible(isAvailable);
                }
            }
        }
    }

    public String getCategoryKey() {
        return (String) ((ArrayMap) DashboardFragmentRegistry.PARENT_TO_CATEGORY_KEY_MAP).get(getClass().getName());
    }

    /* renamed from: getLogTag */
    public abstract String getTAG();

    public Collection<List<AbstractPreferenceController>> getPreferenceControllers() {
        return this.mPreferenceControllers.values();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public abstract int getPreferenceScreenResId();

    public void onActivated(boolean z) {
        updatePreferenceStates();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        Iterator<List<AbstractPreferenceController>> it = this.mPreferenceControllers.values().iterator();
        while (it.hasNext()) {
            for (Object obj : it.next()) {
                if (obj instanceof PreferenceManager.OnActivityResultListener) {
                    ((PreferenceManager.OnActivityResultListener) obj).onActivityResult(i, i2, intent);
                }
            }
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        Trace.beginSection("DashboardFragment#onAttach");
        super.onAttach(context);
        Trace.beginSection("DashboardFragment#getDashboardFeatureProvider");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mDashboardFeatureProvider = featureFactoryImpl.getDashboardFeatureProvider();
        Trace.endSection();
        Trace.beginSection("DashboardFragment#createPreferenceControllers");
        List<AbstractPreferenceController> createPreferenceControllers = createPreferenceControllers(context);
        Trace.endSection();
        Trace.beginSection("DashboardFragment#getPreferenceControllersFromXml");
        List preferenceControllersFromXml = PreferenceControllerListHelper.getPreferenceControllersFromXml(context, getPreferenceScreenResId());
        Trace.endSection();
        Trace.beginSection("DashboardFragment#filterControllers");
        List filterControllers = PreferenceControllerListHelper.filterControllers(preferenceControllersFromXml, createPreferenceControllers);
        Trace.endSection();
        if (createPreferenceControllers != null) {
            this.mControllers.addAll(createPreferenceControllers);
        }
        this.mControllers.addAll(filterControllers);
        ArrayList arrayList = (ArrayList) filterControllers;
        arrayList.forEach(new DashboardFragment$$ExternalSyntheticLambda6(1, getSettingsLifecycle()));
        final int metricsCategory = getMetricsCategory();
        this.mControllers.forEach(new Consumer() { // from class: com.android.settings.dashboard.DashboardFragment$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i = metricsCategory;
                AbstractPreferenceController abstractPreferenceController = (AbstractPreferenceController) obj;
                if (abstractPreferenceController instanceof BasePreferenceController) {
                    ((BasePreferenceController) abstractPreferenceController).setMetricsCategory(i);
                }
            }
        });
        DashboardTilePlaceholderPreferenceController dashboardTilePlaceholderPreferenceController = new DashboardTilePlaceholderPreferenceController(context);
        dashboardTilePlaceholderPreferenceController.mOrder = Preference.DEFAULT_ORDER;
        this.mPlaceholderPreferenceController = dashboardTilePlaceholderPreferenceController;
        this.mControllers.add(dashboardTilePlaceholderPreferenceController);
        Iterator<AbstractPreferenceController> it = this.mControllers.iterator();
        while (it.hasNext()) {
            addPreferenceController(it.next());
        }
        Trace.endSection();
    }

    public void onAutoModeChanged(int i) {
        updatePreferenceStates();
    }

    @Override // com.android.settings.core.BasePreferenceController.UiBlockListener
    public void onBlockerWorkFinished(BasePreferenceController basePreferenceController) {
        UiBlockerController uiBlockerController = this.mBlockerController;
        if (((HashSet) uiBlockerController.mKeys).remove(basePreferenceController.getPreferenceKey())) {
            uiBlockerController.mCountDownLatch.countDown();
        }
        basePreferenceController.setUiBlockerFinished(this.mBlockerController.mBlockerFinished);
    }

    public void onCategoriesChanged(Set<String> set) {
        Trace.beginSection("DashboardFragment#onCategoriesChanged");
        Log.i(TAG, "onCategoriesChanged");
        String categoryKey = getCategoryKey();
        if (((DashboardFeatureProviderImpl) this.mDashboardFeatureProvider).getTilesForCategory(categoryKey) == null) {
            Trace.endSection();
            return;
        }
        if (set == null) {
            refreshDashboardTiles(getTAG());
        } else if (set.contains(categoryKey)) {
            Log.i(TAG, "refresh tiles for " + categoryKey);
            refreshDashboardTiles(getTAG());
        }
        Trace.endSection();
    }

    public void onColorTemperatureChanged(int i) {
        updatePreferenceStates();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        Trace.beginSection("DashboardFragment#onCreate");
        super.onCreate(bundle);
        getPreferenceManager().mPreferenceComparisonCallback = new PreferenceManager.SimplePreferenceComparisonCallback();
        if (bundle != null) {
            this.mFirstOnResume = false;
            updatePreferenceStates();
        }
        Trace.endSection();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        Trace.beginSection("DashboardFragment#onCreatePreferences");
        checkUiBlocker(this.mControllers);
        refreshAllPreferences(getTAG());
        this.mControllers.stream().map(new Function() { // from class: com.android.settings.dashboard.DashboardFragment$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                DashboardFragment dashboardFragment = DashboardFragment.this;
                dashboardFragment.getClass();
                return dashboardFragment.findPreference(((AbstractPreferenceController) obj).getPreferenceKey());
            }
        }).filter(new DashboardFragment$$ExternalSyntheticLambda0(1)).forEach(new DashboardFragment$$ExternalSyntheticLambda11(this, 0));
        Trace.endSection();
    }

    public void onCustomEndTimeChanged(LocalTime localTime) {
        updatePreferenceStates();
    }

    public void onCustomStartTimeChanged(LocalTime localTime) {
        updatePreferenceStates();
    }

    public void onExpandButtonClick() {
        this.mMetricsFeatureProvider.action(0, 834, getMetricsCategory(), 0, null);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public boolean onPreferenceTreeClick(Preference preference) {
        Iterator<List<AbstractPreferenceController>> it = this.mPreferenceControllers.values().iterator();
        while (it.hasNext()) {
            Iterator<AbstractPreferenceController> it2 = it.next().iterator();
            while (it2.hasNext()) {
                if (it2.next().handlePreferenceTreeClick(preference)) {
                    writePreferenceClickMetric(preference);
                    return true;
                }
            }
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public void onResume() {
        Trace.beginSection("DashboardFragment#onResume");
        super.onResume();
        updatePreferenceStates();
        Trace.endSection();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStart() {
        Trace.beginSection("DashboardFragment#onStart");
        super.onStart();
        if (((DashboardFeatureProviderImpl) this.mDashboardFeatureProvider).getTilesForCategory(getCategoryKey()) == null) {
            Trace.endSection();
            return;
        }
        KeyEventDispatcher$Component activity = getActivity();
        if (activity instanceof CategoryMixin.CategoryHandler) {
            this.mListeningToCategoryChange = true;
            ((ArrayList) ((CategoryMixin.CategoryHandler) activity).getCategoryMixin().mCategoryListeners).add(this);
        }
        this.mDashboardTilePrefKeys.values().stream().filter(new DashboardFragment$$ExternalSyntheticLambda0(0)).flatMap(new EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3()).forEach(new DashboardFragment$$ExternalSyntheticLambda1(this, getContentResolver(), 0));
        Trace.endSection();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        ArrayList arrayList = new ArrayList(this.mRegisteredObservers);
        if (!arrayList.isEmpty()) {
            arrayList.forEach(new DashboardFragment$$ExternalSyntheticLambda1(this, getContentResolver(), 2));
        }
        if (this.mListeningToCategoryChange) {
            KeyEventDispatcher$Component activity = getActivity();
            if (activity instanceof CategoryMixin.CategoryHandler) {
                ((ArrayList) ((CategoryMixin.CategoryHandler) activity).getCategoryMixin().mCategoryListeners).remove(this);
            }
            this.mListeningToCategoryChange = false;
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Trace.beginSection("DashboardFragment#onViewCreated");
        super.onViewCreated(view, bundle);
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Iterator<AbstractPreferenceController> it = this.mControllers.iterator();
        while (it.hasNext()) {
            it.next().onViewCreated(viewLifecycleOwner);
        }
        Trace.endSection();
    }

    public void refreshAllPreferences(String str) {
        Trace.beginSection("DashboardFragment#refreshAllPreferences");
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.removeAll();
        }
        Trace.beginSection("DashboardFragment#displayResourceTiles");
        int preferenceScreenResId = getPreferenceScreenResId();
        if (preferenceScreenResId <= 0) {
            Trace.endSection();
        } else {
            Trace.beginSection("DashboardFragment#addPreferencesFromResource");
            addPreferencesFromResource(preferenceScreenResId);
            Trace.endSection();
            PreferenceScreen preferenceScreen2 = getPreferenceScreen();
            preferenceScreen2.mOnExpandButtonClickListener = this;
            Iterator<List<AbstractPreferenceController>> it = this.mPreferenceControllers.values().iterator();
            while (it.hasNext()) {
                for (AbstractPreferenceController abstractPreferenceController : it.next()) {
                    Trace.beginSection("displayResourceTiles#Controller_" + abstractPreferenceController.getPreferenceKey());
                    abstractPreferenceController.displayPreference(preferenceScreen2);
                    Trace.endSection();
                }
            }
            Trace.endSection();
        }
        refreshDashboardTiles(str);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Log.d(str, "All preferences added, reporting fully drawn");
            activity.reportFullyDrawn();
        }
        updatePreferenceVisibility(this.mPreferenceControllers);
        Trace.endSection();
    }

    public final void refreshDashboardTiles(String str) {
        ArrayMap arrayMap;
        Iterator it;
        String str2;
        boolean z;
        ArrayList arrayList;
        List bindPreferenceToTileAndGetObservers;
        String str3 = str;
        Trace.beginSection("DashboardFragment#refreshDashboardTiles");
        Log.i(TAG, "refreshDashboardTiles TAG : DashboardFragment");
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        Trace.beginSection("DashboardFragment#getTilesForCategory");
        DashboardCategory tilesForCategory = ((DashboardFeatureProviderImpl) this.mDashboardFeatureProvider).getTilesForCategory(getCategoryKey());
        Trace.endSection();
        if (tilesForCategory == null) {
            Log.d(str3, "NO dashboard tiles for " + str3);
            Trace.endSection();
            return;
        }
        List tiles = tilesForCategory.getTiles();
        Log.i(TAG, "Add category : " + tilesForCategory.key);
        ArrayMap arrayMap2 = new ArrayMap(this.mDashboardTilePrefKeys);
        boolean shouldForceRoundedIcon = shouldForceRoundedIcon();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = (ArrayList) tiles;
        arrayList3.sort(Comparator.comparingInt(new DashboardFragment$$ExternalSyntheticLambda3()));
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            Tile tile = (Tile) it2.next();
            String dashboardKeyForTile = ((DashboardFeatureProviderImpl) this.mDashboardFeatureProvider).getDashboardKeyForTile(tile);
            if (TextUtils.isEmpty(dashboardKeyForTile)) {
                Log.d(str3, "tile does not contain a key, skipping " + tile);
            } else {
                Trace.beginSection("DF#IDT_" + dashboardKeyForTile);
                if (displayTile(tile)) {
                    if (this.mDashboardTilePrefKeys.containsKey(dashboardKeyForTile)) {
                        it = it2;
                        arrayMap = arrayMap2;
                        str2 = dashboardKeyForTile;
                        bindPreferenceToTileAndGetObservers = ((DashboardFeatureProviderImpl) this.mDashboardFeatureProvider).bindPreferenceToTileAndGetObservers(getActivity(), this, shouldForceRoundedIcon, preferenceScreen.findPreference(dashboardKeyForTile), tile, dashboardKeyForTile, this.mPlaceholderPreferenceController.mOrder);
                        Log.i(TAG, "bindPreferenceToTileAndGetObservers : " + str2 + " , tile : " + tile);
                        z = shouldForceRoundedIcon;
                        arrayList = arrayList2;
                    } else {
                        arrayMap = arrayMap2;
                        it = it2;
                        str2 = dashboardKeyForTile;
                        Preference createPreference = createPreference(tile);
                        z = shouldForceRoundedIcon;
                        arrayList = arrayList2;
                        bindPreferenceToTileAndGetObservers = ((DashboardFeatureProviderImpl) this.mDashboardFeatureProvider).bindPreferenceToTileAndGetObservers(getActivity(), this, shouldForceRoundedIcon, createPreference, tile, str2, this.mPlaceholderPreferenceController.mOrder);
                        if ((!TextUtils.isEmpty(tile.mGroupKey)) && this.mDashboardTilePrefKeys.containsKey(tile.mGroupKey)) {
                            Preference findPreference = preferenceScreen.findPreference(tile.mGroupKey);
                            if (findPreference instanceof PreferenceCategory) {
                                ((PreferenceCategory) findPreference).addPreference(createPreference);
                            }
                        } else {
                            preferenceScreen.addPreference(createPreference);
                        }
                        registerDynamicDataObservers(bindPreferenceToTileAndGetObservers);
                        this.mDashboardTilePrefKeys.put(str2, bindPreferenceToTileAndGetObservers);
                        Log.i(TAG, "bindPreferenceToTileAndGetObservers, mDashboardTilePrefKeys.put : " + str2 + " , tile : " + tile);
                    }
                    ArrayList arrayList4 = arrayList;
                    if (bindPreferenceToTileAndGetObservers != null) {
                        arrayList4.addAll(bindPreferenceToTileAndGetObservers);
                    }
                    ArrayMap arrayMap3 = arrayMap;
                    arrayMap3.remove(str2);
                    Trace.endSection();
                    str3 = str;
                    arrayMap2 = arrayMap3;
                    arrayList2 = arrayList4;
                    it2 = it;
                    shouldForceRoundedIcon = z;
                }
            }
        }
        final ArrayList arrayList5 = arrayList2;
        for (Map.Entry entry : arrayMap2.entrySet()) {
            String str4 = (String) entry.getKey();
            this.mDashboardTilePrefKeys.remove(str4);
            Log.i(TAG, "mDashboardTilePrefKeys.remove : " + str4);
            Preference findPreference2 = preferenceScreen.findPreference(str4);
            if (findPreference2 != null) {
                preferenceScreen.removePreference(findPreference2);
            }
            List list = (List) entry.getValue();
            if (list != null && !list.isEmpty()) {
                list.forEach(new DashboardFragment$$ExternalSyntheticLambda1(this, getContentResolver(), 2));
            }
        }
        if (!arrayList5.isEmpty()) {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            new Thread(new Runnable() { // from class: com.android.settings.dashboard.DashboardFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    DashboardFragment dashboardFragment = DashboardFragment.this;
                    List list2 = arrayList5;
                    CountDownLatch countDownLatch2 = countDownLatch;
                    dashboardFragment.getClass();
                    list2.forEach(new DashboardFragment$$ExternalSyntheticLambda11(dashboardFragment, 1));
                    countDownLatch2.countDown();
                }
            }).start();
            Log.d(str, "Start waiting observers");
            try {
                countDownLatch.await(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
            }
            Log.d(str, "Stop waiting observers");
            arrayList5.forEach(new DashboardFragment$$ExternalSyntheticLambda5(0));
        }
        Trace.endSection();
    }

    public final void registerDynamicDataObserver(ContentResolver contentResolver, DashboardFeatureProviderImpl.AnonymousClass1 anonymousClass1) {
        Log.d(TAG, "register observer: @" + Integer.toHexString(anonymousClass1.hashCode()) + ", uri: " + anonymousClass1.val$uri);
        contentResolver.registerContentObserver(anonymousClass1.val$uri, false, anonymousClass1);
        this.mRegisteredObservers.add(anonymousClass1);
    }

    public void registerDynamicDataObservers(List<DashboardFeatureProviderImpl.AnonymousClass1> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        list.forEach(new DashboardFragment$$ExternalSyntheticLambda1(this, getContentResolver(), 1));
    }

    public boolean shouldForceRoundedIcon() {
        return false;
    }

    public void updatePreferenceStates() {
        Trace.beginSection("updatePreferenceStates#Controller");
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen == null) {
            Log.d(TAG, "root preference screen is null.");
            this.mFirstOnResume = false;
            Trace.endSection();
            return;
        }
        Iterator<List<AbstractPreferenceController>> it = this.mPreferenceControllers.values().iterator();
        while (it.hasNext()) {
            for (AbstractPreferenceController abstractPreferenceController : it.next()) {
                Trace.beginSection("updatePreferenceStates#Controller_" + abstractPreferenceController.getPreferenceKey());
                if (this.mFirstOnResume && (abstractPreferenceController instanceof BasePreferenceController)) {
                    BasePreferenceController basePreferenceController = (BasePreferenceController) abstractPreferenceController;
                    if (basePreferenceController.mLastAvailableChecked && !basePreferenceController.mLastAvailable) {
                        Log.i(TAG, "LastAvailableStatus false : " + abstractPreferenceController.getPreferenceKey());
                        Trace.endSection();
                    }
                }
                if (abstractPreferenceController.isAvailable()) {
                    String preferenceKey = abstractPreferenceController.getPreferenceKey();
                    if (TextUtils.isEmpty(preferenceKey)) {
                        Log.d(TAG, "Preference key is " + preferenceKey + " in Controller " + abstractPreferenceController.getClass().getSimpleName());
                        Trace.endSection();
                    } else {
                        Preference findPreference = preferenceScreen.findPreference(preferenceKey);
                        if (findPreference == null) {
                            Log.d(TAG, "Cannot find preference with key " + preferenceKey + " in Controller " + abstractPreferenceController.getClass().getSimpleName());
                            Trace.endSection();
                        } else {
                            if (!findPreference.isVisible()) {
                                findPreference.setVisible(true);
                            }
                            Trace.beginSection("updateState#Controller_" + abstractPreferenceController.getPreferenceKey());
                            abstractPreferenceController.updateState(findPreference);
                            Trace.endSection();
                            Trace.endSection();
                        }
                    }
                } else {
                    try {
                        Preference findPreference2 = preferenceScreen.findPreference(abstractPreferenceController.getPreferenceKey());
                        if (findPreference2 != null && findPreference2.isVisible()) {
                            findPreference2.setVisible(false);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                    Trace.endSection();
                }
            }
        }
        this.mFirstOnResume = false;
        Trace.endSection();
    }

    public void updatePreferenceVisibility(Map<Class, List<AbstractPreferenceController>> map) {
        UiBlockerController uiBlockerController;
        Trace.beginSection("DashboardFragment#updatePreferenceVisibility");
        if (getPreferenceScreen() == null || map == null || (uiBlockerController = this.mBlockerController) == null) {
            Trace.endSection();
            return;
        }
        boolean z = uiBlockerController.mBlockerFinished;
        Iterator<List<AbstractPreferenceController>> it = map.values().iterator();
        while (it.hasNext()) {
            for (AbstractPreferenceController abstractPreferenceController : it.next()) {
                Preference findPreference = findPreference(abstractPreferenceController.getPreferenceKey());
                if (findPreference != null) {
                    boolean z2 = false;
                    if (abstractPreferenceController instanceof BasePreferenceController.UiBlocker) {
                        boolean savedPrefVisibility = ((BasePreferenceController) abstractPreferenceController).getSavedPrefVisibility();
                        if (z && abstractPreferenceController.isAvailable() && savedPrefVisibility) {
                            z2 = true;
                        }
                        findPreference.setVisible(z2);
                    } else {
                        if (z && abstractPreferenceController.isAvailable()) {
                            z2 = true;
                        }
                        findPreference.setVisible(z2);
                    }
                }
            }
        }
        Trace.endSection();
    }

    public <T extends AbstractPreferenceController> T use(Class<T> cls) {
        List<AbstractPreferenceController> list = this.mPreferenceControllers.get(cls);
        if (list == null) {
            return null;
        }
        if (list.size() > 1) {
            Log.w(TAG, "Multiple controllers of Class " + cls.getSimpleName() + " found, returning first one.");
        }
        return (T) list.get(0);
    }

    public <T extends AbstractPreferenceController> List<T> useAll(Class<T> cls) {
        return (List) this.mPreferenceControllers.getOrDefault(cls, Collections.emptyList());
    }
}
