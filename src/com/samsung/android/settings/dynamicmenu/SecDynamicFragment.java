package com.samsung.android.settings.dynamicmenu;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.core.view.KeyEventDispatcher$Component;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;
import androidx.preference.SecSwitchPreferenceScreen;
import androidx.preference.SeslSwitchPreferenceScreen;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.core.CategoryMixin;
import com.android.settings.dashboard.DashboardFeatureProviderImpl;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.HighlightablePreferenceGroupAdapter;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.core.SecTileUtils;
import com.samsung.android.settings.external.DynamicMenuData;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.multidevices.MultiDevicesFragment;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.settingslib.SettingsLibRune;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecDynamicFragment extends DashboardFragment {
    public final AnonymousClass1 mDBFromExternalObserver;
    public DashboardFeatureProviderImpl mDashboardFeatureProvider;
    public final AnonymousClass1 mMenuObserver;
    public SecDynamicMenuFeatureProviderImpl mSecDynamicMenuFeatureProvider;
    public final HashMap mUriList = new HashMap();
    public final ArrayList mDynamicKeyList = new ArrayList();
    public final ArrayList mExternalDBUriList = new ArrayList();
    public final HashMap mSettingsDbPreferenceKeyMap = new HashMap();
    public final HashMap mPreferenceKeySettingsDbPMap = new HashMap();
    public boolean mFirstOnResume = true;

    /* JADX WARN: Type inference failed for: r0v6, types: [com.samsung.android.settings.dynamicmenu.SecDynamicFragment$1] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.samsung.android.settings.dynamicmenu.SecDynamicFragment$1] */
    public SecDynamicFragment() {
        final int i = 0;
        this.mDBFromExternalObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.dynamicmenu.SecDynamicFragment.1
                    public final /* synthetic */ SecDynamicFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        Uri uri2;
                        DynamicMenuData bundleFromKey;
                        switch (i) {
                            case 0:
                                if (uri != null) {
                                    String lastPathSegment = uri.getLastPathSegment();
                                    String str =
                                            (String)
                                                    this.this$0.mSettingsDbPreferenceKeyMap.get(
                                                            lastPathSegment);
                                    Log.d(
                                            "SecDynamicFragment",
                                            "mSettingsDBObserver dbKey : "
                                                    + lastPathSegment
                                                    + " , preferenceKey : "
                                                    + str);
                                    if (str != null) {
                                        Preference findPreference = this.this$0.findPreference(str);
                                        if (findPreference instanceof SwitchPreferenceCompat) {
                                            SwitchPreferenceCompat switchPreferenceCompat =
                                                    (SwitchPreferenceCompat) findPreference;
                                            int i2 =
                                                    Settings.System.getInt(
                                                            this.this$0.getContentResolver(),
                                                            lastPathSegment,
                                                            0);
                                            Log.d(
                                                    "SecDynamicFragment",
                                                    "mSettingsDBObserver newValue : " + i2);
                                            boolean isChecked = switchPreferenceCompat.isChecked();
                                            boolean z2 = i2 != 0;
                                            if (isChecked != z2) {
                                                switchPreferenceCompat.setChecked(z2);
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            default:
                                Log.i("SecDynamicFragment", "URI : " + uri);
                                String lastPathSegment2 = uri.getLastPathSegment();
                                Preference findPreference2 =
                                        this.this$0.findPreference(lastPathSegment2);
                                if (findPreference2 != null
                                        && (uri2 = (Uri) this.this$0.mUriList.get(lastPathSegment2))
                                                != null
                                        && (bundleFromKey =
                                                        this.this$0.mSecDynamicMenuFeatureProvider
                                                                .getBundleFromKey(
                                                                        uri2, lastPathSegment2))
                                                != null) {
                                    StringBuilder sb = new StringBuilder("DynamicMenuData key ");
                                    sb.append(bundleFromKey.mKey);
                                    sb.append(" ,title : ");
                                    sb.append(bundleFromKey.mTitle);
                                    sb.append(" ,summary : ");
                                    sb.append(bundleFromKey.mSummary);
                                    sb.append(" ,isCheck : ");
                                    sb.append(bundleFromKey.mIsChecked);
                                    sb.append(" ,isEnable : ");
                                    sb.append(bundleFromKey.mIsEnabled);
                                    sb.append(" ,isVisible : ");
                                    SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                                            sb, bundleFromKey.mIsVisible, "SecDynamicFragment");
                                    findPreference2.setTitle(bundleFromKey.mTitle);
                                    findPreference2.setSummary(bundleFromKey.mSummary);
                                    findPreference2.setEnabled(bundleFromKey.mIsEnabled);
                                    findPreference2.setVisible(bundleFromKey.mIsVisible);
                                    if (findPreference2 instanceof SwitchPreferenceCompat) {
                                        SwitchPreferenceCompat switchPreferenceCompat2 =
                                                (SwitchPreferenceCompat) findPreference2;
                                        boolean isChecked2 = switchPreferenceCompat2.isChecked();
                                        boolean z3 = bundleFromKey.mIsChecked;
                                        if (isChecked2 != z3) {
                                            switchPreferenceCompat2.setChecked(z3);
                                        }
                                    }
                                    SecPreferenceUtils.applySummaryColor(
                                            findPreference2, bundleFromKey.mIsSummaryPrimaryDark);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mMenuObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.dynamicmenu.SecDynamicFragment.1
                    public final /* synthetic */ SecDynamicFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        Uri uri2;
                        DynamicMenuData bundleFromKey;
                        switch (i2) {
                            case 0:
                                if (uri != null) {
                                    String lastPathSegment = uri.getLastPathSegment();
                                    String str =
                                            (String)
                                                    this.this$0.mSettingsDbPreferenceKeyMap.get(
                                                            lastPathSegment);
                                    Log.d(
                                            "SecDynamicFragment",
                                            "mSettingsDBObserver dbKey : "
                                                    + lastPathSegment
                                                    + " , preferenceKey : "
                                                    + str);
                                    if (str != null) {
                                        Preference findPreference = this.this$0.findPreference(str);
                                        if (findPreference instanceof SwitchPreferenceCompat) {
                                            SwitchPreferenceCompat switchPreferenceCompat =
                                                    (SwitchPreferenceCompat) findPreference;
                                            int i22 =
                                                    Settings.System.getInt(
                                                            this.this$0.getContentResolver(),
                                                            lastPathSegment,
                                                            0);
                                            Log.d(
                                                    "SecDynamicFragment",
                                                    "mSettingsDBObserver newValue : " + i22);
                                            boolean isChecked = switchPreferenceCompat.isChecked();
                                            boolean z2 = i22 != 0;
                                            if (isChecked != z2) {
                                                switchPreferenceCompat.setChecked(z2);
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            default:
                                Log.i("SecDynamicFragment", "URI : " + uri);
                                String lastPathSegment2 = uri.getLastPathSegment();
                                Preference findPreference2 =
                                        this.this$0.findPreference(lastPathSegment2);
                                if (findPreference2 != null
                                        && (uri2 = (Uri) this.this$0.mUriList.get(lastPathSegment2))
                                                != null
                                        && (bundleFromKey =
                                                        this.this$0.mSecDynamicMenuFeatureProvider
                                                                .getBundleFromKey(
                                                                        uri2, lastPathSegment2))
                                                != null) {
                                    StringBuilder sb = new StringBuilder("DynamicMenuData key ");
                                    sb.append(bundleFromKey.mKey);
                                    sb.append(" ,title : ");
                                    sb.append(bundleFromKey.mTitle);
                                    sb.append(" ,summary : ");
                                    sb.append(bundleFromKey.mSummary);
                                    sb.append(" ,isCheck : ");
                                    sb.append(bundleFromKey.mIsChecked);
                                    sb.append(" ,isEnable : ");
                                    sb.append(bundleFromKey.mIsEnabled);
                                    sb.append(" ,isVisible : ");
                                    SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                                            sb, bundleFromKey.mIsVisible, "SecDynamicFragment");
                                    findPreference2.setTitle(bundleFromKey.mTitle);
                                    findPreference2.setSummary(bundleFromKey.mSummary);
                                    findPreference2.setEnabled(bundleFromKey.mIsEnabled);
                                    findPreference2.setVisible(bundleFromKey.mIsVisible);
                                    if (findPreference2 instanceof SwitchPreferenceCompat) {
                                        SwitchPreferenceCompat switchPreferenceCompat2 =
                                                (SwitchPreferenceCompat) findPreference2;
                                        boolean isChecked2 = switchPreferenceCompat2.isChecked();
                                        boolean z3 = bundleFromKey.mIsChecked;
                                        if (isChecked2 != z3) {
                                            switchPreferenceCompat2.setChecked(z3);
                                        }
                                    }
                                    SecPreferenceUtils.applySummaryColor(
                                            findPreference2, bundleFromKey.mIsSummaryPrimaryDark);
                                    break;
                                }
                                break;
                        }
                    }
                };
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public Preference createPreference(Tile tile) {
        String string = tile.mMetaData.getString("com.android.settings.PREFERENCE_TYPE");
        if (string == null) {
            string = ApnSettings.MVNO_NONE;
        }
        switch (string) {
            case "SwitchPreferenceScreen":
                return new SecSwitchPreferenceScreen(getPrefContext());
            case "SwitchPreference":
                return new SecSwitchPreference(getPrefContext());
            case "SecInsetCategoryPreference":
                return new SecInsetCategoryPreference(getPrefContext());
            case "PreferenceCategory":
                return new SecPreferenceCategory(getPrefContext());
            default:
                return new SecPreference(getPrefContext(), null);
        }
    }

    public boolean isLoadingSupported() {
        return this instanceof MultiDevicesFragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mDashboardFeatureProvider = featureFactoryImpl.getDashboardFeatureProvider();
        FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
        if (featureFactoryImpl2 == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mSecDynamicMenuFeatureProvider =
                (SecDynamicMenuFeatureProviderImpl)
                        featureFactoryImpl2.secDynamicMenuFeatureProvider$delegate.getValue();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public void onCategoriesChanged(Set set) {
        Log.i("SecDynamicFragment", "onCategoriesChanged");
        super.onCategoriesChanged(set);
        HashMap hashMap = new HashMap(this.mUriList);
        refreshDynamicMenus(getTAG());
        if (hashMap.equals(this.mUriList)) {
            return;
        }
        Log.i("SecDynamicFragment", "updateMenuFromProvider called to onCategoriesChanged().");
        refreshMenuItems();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        refreshDynamicMenus(getTAG());
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(this.mMenuObserver);
        getContentResolver().unregisterContentObserver(this.mDBFromExternalObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        for (Uri uri : this.mUriList.values()) {
            if (uri != null) {
                try {
                    getContentResolver().registerContentObserver(uri, true, this.mMenuObserver);
                } catch (SecurityException e) {
                    Log.e("SecDynamicFragment", "Failed to register for: " + uri, e);
                }
            }
        }
        Iterator it = this.mExternalDBUriList.iterator();
        while (it.hasNext()) {
            Uri uri2 = (Uri) it.next();
            if (uri2 != null) {
                try {
                    getContentResolver()
                            .registerContentObserver(uri2, true, this.mDBFromExternalObserver);
                    String lastPathSegment = uri2.getLastPathSegment();
                    Preference findPreference =
                            findPreference(
                                    (String) this.mSettingsDbPreferenceKeyMap.get(lastPathSegment));
                    if (findPreference instanceof SwitchPreferenceCompat) {
                        ((SwitchPreferenceCompat) findPreference)
                                .setChecked(
                                        Settings.System.getInt(
                                                        getContentResolver(), lastPathSegment, 0)
                                                == 1);
                    }
                } catch (SecurityException e2) {
                    Log.e("SecDynamicFragment", "Failed to register for: " + uri2, e2);
                }
            }
        }
        Log.i("SecDynamicFragment", "updateMenuFromProvider called to onResume().");
        refreshMenuItems();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        KeyEventDispatcher$Component activity = getActivity();
        if (activity instanceof CategoryMixin.CategoryHandler) {
            ((ArrayList)
                            ((CategoryMixin.CategoryHandler) activity)
                                    .getCategoryMixin()
                                    .mCategoryListeners)
                    .add(this);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        KeyEventDispatcher$Component activity = getActivity();
        if (activity instanceof CategoryMixin.CategoryHandler) {
            ((ArrayList)
                            ((CategoryMixin.CategoryHandler) activity)
                                    .getCategoryMixin()
                                    .mCategoryListeners)
                    .remove(this);
        }
    }

    public final void refreshDynamicMenus(String str) {
        HighlightablePreferenceGroupAdapter highlightablePreferenceGroupAdapter;
        Uri uriFromIntent;
        Trace.beginSection("SecDynamicFragment#refreshDynamicMenus");
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        ArrayList arrayList = new ArrayList();
        if (this.mDynamicKeyList.size() > 0) {
            arrayList.addAll(this.mDynamicKeyList);
            this.mDynamicKeyList.clear();
        }
        ArrayList arrayList2 =
                (ArrayList)
                        ((ArrayMap) SecDynamicFragmentRegistry.PARENT_TO_CATEGORY_KEY_MAP)
                                .get(getClass().getName());
        if (arrayList2 == null) {
            Trace.endSection();
            return;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            DashboardCategory tilesForCategory =
                    this.mDashboardFeatureProvider.getTilesForCategory(str2);
            AbsAdapter$$ExternalSyntheticOutline0.m("Category Key : ", str2, "SecDynamicFragment");
            if (tilesForCategory == null) {
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "NO dashboard tiles for ", str, "SecDynamicFragment");
            } else {
                Iterator it2 = ((ArrayList) tilesForCategory.getTiles()).iterator();
                while (it2.hasNext()) {
                    final Tile tile = (Tile) it2.next();
                    Utils$$ExternalSyntheticOutline0.m(
                            new StringBuilder("tile : "),
                            tile.mComponentName,
                            "SecDynamicFragment");
                    String dashboardKeyForTile =
                            this.mDashboardFeatureProvider.getDashboardKeyForTile(tile);
                    if (TextUtils.isEmpty(dashboardKeyForTile)) {
                        Log.i(
                                "SecDynamicFragment",
                                "tile does not contain a key, skipping " + tile);
                    } else {
                        Log.i("SecDynamicFragment", "Preference key : " + dashboardKeyForTile);
                        if (displayTile(tile)) {
                            if (findPreference(dashboardKeyForTile) == null) {
                                Preference createPreference = createPreference(tile);
                                final FragmentActivity activity = getActivity();
                                Log.i(
                                        "SecDynamicFragment",
                                        "bindPreferenceToTile : " + tile.mComponentName);
                                createPreference.setKey(dashboardKeyForTile);
                                if (!(createPreference instanceof SecInsetCategoryPreference)) {
                                    createPreference.setTitle(tile.getTitle(getContext()));
                                }
                                if (!(createPreference instanceof PreferenceCategory)) {
                                    createPreference.setEnabled(true);
                                    if (tile.getSummary(getContext()) != null) {
                                        createPreference.setSummary(tile.getSummary(getContext()));
                                    } else {
                                        Bundle bundle = tile.mMetaData;
                                        if (bundle == null
                                                || !bundle.containsKey(
                                                        "com.android.settings.summary_uri")) {
                                            createPreference.setSummary((CharSequence) null);
                                        } else {
                                            createPreference.setSummary(
                                                    R.string.summary_placeholder);
                                            ThreadUtils.postOnBackgroundThread(
                                                    new SecDynamicFragment$$ExternalSyntheticLambda5(
                                                            this, tile, createPreference, 0));
                                        }
                                    }
                                    if (Utils.hasPlatformKey(getContext(), tile.mComponentPackage)
                                            || SecTileUtils.isAllowListed(
                                                    getContext(), tile.mComponentPackage)) {
                                        if (tile.mIconResIdOverride != 0) {
                                            Icon icon = tile.getIcon(createPreference.getContext());
                                            if (icon != null) {
                                                setPreferenceIcon(createPreference, icon);
                                            }
                                        } else {
                                            Bundle bundle2 = tile.mMetaData;
                                            if (bundle2 == null
                                                    || !bundle2.containsKey(
                                                            "com.android.settings.icon_uri")) {
                                                Icon icon2 =
                                                        tile.getIcon(createPreference.getContext());
                                                if (icon2 != null) {
                                                    setPreferenceIcon(createPreference, icon2);
                                                }
                                            } else {
                                                setPreferenceIcon(
                                                        createPreference,
                                                        Icon.createWithResource(
                                                                getContext(),
                                                                android.R.color.transparent));
                                                ThreadUtils.postOnBackgroundThread(
                                                        new SecDynamicFragment$$ExternalSyntheticLambda5(
                                                                this, tile, createPreference, 1));
                                            }
                                        }
                                    }
                                    Intent intent = tile.mIntent;
                                    if (intent != null
                                            && (uriFromIntent =
                                                            this.mSecDynamicMenuFeatureProvider
                                                                    .getUriFromIntent(intent))
                                                    != null) {
                                        this.mUriList.put(dashboardKeyForTile, uriFromIntent);
                                    }
                                    boolean z = createPreference instanceof SwitchPreferenceCompat;
                                    if (((!z && !(createPreference instanceof SwitchPreference))
                                                    || (createPreference
                                                            instanceof SeslSwitchPreferenceScreen))
                                            && tile.mIntent != null) {
                                        createPreference.setOnPreferenceClickListener(
                                                new Preference
                                                        .OnPreferenceClickListener() { // from
                                                                                       // class:
                                                                                       // com.samsung.android.settings.dynamicmenu.SecDynamicFragment$$ExternalSyntheticLambda0
                                                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                                                    public final boolean onPreferenceClick(
                                                            Preference preference) {
                                                        int i;
                                                        Activity activity2 = activity;
                                                        SecDynamicFragment secDynamicFragment =
                                                                SecDynamicFragment.this;
                                                        secDynamicFragment.getContext();
                                                        Tile tile2 = tile;
                                                        String saLoggingId = tile2.getSaLoggingId();
                                                        if (TextUtils.isEmpty(saLoggingId)) {
                                                            saLoggingId =
                                                                    tile2.getKey(
                                                                            secDynamicFragment
                                                                                    .getContext());
                                                        }
                                                        LoggingHelper.insertEventLogging(
                                                                secDynamicFragment
                                                                        .getMetricsCategory(),
                                                                saLoggingId);
                                                        Intent intent2 = new Intent(tile2.mIntent);
                                                        intent2.putExtra("from_settings", true);
                                                        intent2.putExtra("launch_from", "settings");
                                                        intent2.putExtra(
                                                                GoodSettingsContract.EXTRA_NAME
                                                                        .POLICY_KEY,
                                                                tile2.getKey(
                                                                        secDynamicFragment
                                                                                .getContext()));
                                                        int i2 = 0;
                                                        if (UsefulfeatureUtils
                                                                        .isSupportedIntelligenceServiceForWork(
                                                                                secDynamicFragment
                                                                                        .getContext())
                                                                && secDynamicFragment
                                                                                .getArguments()
                                                                                .getInt(
                                                                                        ImsProfile
                                                                                                .SERVICE_PROFILE)
                                                                        == 2) {
                                                            i =
                                                                    tile2.userHandle.indexOf(
                                                                            UserHandle.of(
                                                                                    UsefulfeatureUtils
                                                                                            .getManagedProfileId(
                                                                                                    secDynamicFragment
                                                                                                            .getContext())));
                                                        } else {
                                                            i = 0;
                                                        }
                                                        if (SemPersonaManager.isSecureFolderId(
                                                                UserHandle.myUserId())) {
                                                            i =
                                                                    tile2.userHandle.indexOf(
                                                                            UserHandle.of(
                                                                                    UserHandle
                                                                                            .myUserId()));
                                                        }
                                                        if (i < 0) {
                                                            i = 0;
                                                        }
                                                        try {
                                                        } catch (ActivityNotFoundException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if (!Utils.isLaunchModeSingleInstance(
                                                                activity2, intent2)) {
                                                            if (tile2.isNewTask()) {}
                                                            activity2.startActivityForResultAsUser(
                                                                    intent2,
                                                                    i2,
                                                                    (UserHandle)
                                                                            tile2.userHandle.get(
                                                                                    i));
                                                            return true;
                                                        }
                                                        i2 = -1;
                                                        activity2.startActivityForResultAsUser(
                                                                intent2,
                                                                i2,
                                                                (UserHandle)
                                                                        tile2.userHandle.get(i));
                                                        return true;
                                                    }
                                                });
                                    }
                                    if (z) {
                                        createPreference.setOnPreferenceChangeListener(
                                                new Preference
                                                        .OnPreferenceChangeListener() { // from
                                                    // class:
                                                    // com.samsung.android.settings.dynamicmenu.SecDynamicFragment$$ExternalSyntheticLambda1
                                                    /* JADX WARN: Code restructure failed: missing block: B:19:0x0079, code lost:

                                                       if (r2 != 0) goto L21;
                                                    */
                                                    /* JADX WARN: Code restructure failed: missing block: B:28:0x00ac, code lost:

                                                       if (r2 == 0) goto L41;
                                                    */
                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    /* JADX WARN: Removed duplicated region for block: B:32:0x00b1  */
                                                    /* JADX WARN: Type inference failed for: r2v11 */
                                                    /* JADX WARN: Type inference failed for: r2v12, types: [android.content.ContentProviderClient] */
                                                    /* JADX WARN: Type inference failed for: r2v13 */
                                                    /* JADX WARN: Type inference failed for: r2v14 */
                                                    /* JADX WARN: Type inference failed for: r2v15 */
                                                    /* JADX WARN: Type inference failed for: r2v16, types: [android.content.ContentProviderClient] */
                                                    /* JADX WARN: Type inference failed for: r2v17, types: [android.content.ContentProviderClient] */
                                                    /* JADX WARN: Type inference failed for: r2v19 */
                                                    /* JADX WARN: Type inference failed for: r2v20 */
                                                    /* JADX WARN: Type inference failed for: r2v8, types: [android.content.ContentResolver] */
                                                    /* JADX WARN: Type inference failed for: r2v9 */
                                                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                    */
                                                    public final boolean onPreferenceChange(
                                                            androidx.preference.Preference r9,
                                                            java.lang.Object r10) {
                                                        /*
                                                            r8 = this;
                                                            com.samsung.android.settings.dynamicmenu.SecDynamicFragment r0 = com.samsung.android.settings.dynamicmenu.SecDynamicFragment.this
                                                            r0.getContext()
                                                            com.android.settingslib.drawer.Tile r8 = r2
                                                            java.lang.String r1 = r8.getSaLoggingId()
                                                            boolean r2 = android.text.TextUtils.isEmpty(r1)
                                                            if (r2 == 0) goto L19
                                                            android.content.Context r1 = r0.getContext()
                                                            java.lang.String r1 = r8.getKey(r1)
                                                        L19:
                                                            int r8 = r0.getMetricsCategory()
                                                            java.lang.String r8 = java.lang.String.valueOf(r8)
                                                            java.lang.Boolean r10 = (java.lang.Boolean) r10
                                                            boolean r2 = r10.booleanValue()
                                                            if (r2 == 0) goto L2c
                                                            java.lang.String r2 = "1000"
                                                            goto L2e
                                                        L2c:
                                                            java.lang.String r2 = "0"
                                                        L2e:
                                                            com.samsung.android.settings.logging.SALogging.insertSALog(r8, r1, r2)
                                                            boolean r8 = r9 instanceof androidx.preference.SwitchPreferenceCompat
                                                            r1 = 1
                                                            if (r8 == 0) goto Lde
                                                            java.lang.String r8 = r9.getKey()
                                                            boolean r2 = r10.booleanValue()
                                                            java.util.HashMap r3 = r0.mUriList
                                                            java.lang.String r4 = r9.getKey()
                                                            java.lang.Object r3 = r3.get(r4)
                                                            android.net.Uri r3 = (android.net.Uri) r3
                                                            if (r3 == 0) goto Lc3
                                                            boolean r4 = android.text.TextUtils.isEmpty(r8)
                                                            if (r4 != 0) goto Lbb
                                                            com.samsung.android.settings.external.DynamicMenuData r4 = new com.samsung.android.settings.external.DynamicMenuData
                                                            r4.<init>()
                                                            r4.mKey = r8
                                                            r8 = 0
                                                            r4.mTitle = r8
                                                            r4.mSummary = r8
                                                            r4.mIsChecked = r2
                                                            r4.mIsEnabled = r1
                                                            r4.mIsVisible = r1
                                                            r2 = 0
                                                            r4.mIsSummaryPrimaryDark = r2
                                                            com.samsung.android.settings.dynamicmenu.SecDynamicMenuFeatureProviderImpl r2 = r0.mSecDynamicMenuFeatureProvider
                                                            android.content.Context r2 = r2.mContext
                                                            java.lang.String r5 = "SecDynamicMenuFeatureProviderImpl"
                                                            if (r2 == 0) goto Lb5
                                                            android.content.ContentResolver r2 = r2.getContentResolver()
                                                            android.content.ContentProviderClient r2 = r2.acquireUnstableContentProviderClient(r3)     // Catch: java.lang.Throwable -> L9c java.lang.Throwable -> La0
                                                            if (r2 != 0) goto L7f
                                                            if (r2 == 0) goto Lc3
                                                        L7b:
                                                            r2.close()
                                                            goto Lc3
                                                        L7f:
                                                            android.os.Bundle r3 = new android.os.Bundle     // Catch: java.lang.Throwable -> L98 java.lang.Throwable -> L9a
                                                            r3.<init>()     // Catch: java.lang.Throwable -> L98 java.lang.Throwable -> L9a
                                                            java.lang.Class<com.samsung.android.settings.external.DynamicMenuData> r6 = com.samsung.android.settings.external.DynamicMenuData.class
                                                            java.lang.ClassLoader r6 = r6.getClassLoader()     // Catch: java.lang.Throwable -> L98 java.lang.Throwable -> L9a
                                                            r3.setClassLoader(r6)     // Catch: java.lang.Throwable -> L98 java.lang.Throwable -> L9a
                                                            java.lang.String r6 = "menu"
                                                            r3.putParcelable(r6, r4)     // Catch: java.lang.Throwable -> L98 java.lang.Throwable -> L9a
                                                            java.lang.String r4 = "checked_change"
                                                            r2.call(r4, r8, r3)     // Catch: java.lang.Throwable -> L98 java.lang.Throwable -> L9a
                                                            goto L7b
                                                        L98:
                                                            r8 = move-exception
                                                            goto Laf
                                                        L9a:
                                                            r8 = move-exception
                                                            goto La4
                                                        L9c:
                                                            r9 = move-exception
                                                            r2 = r8
                                                            r8 = r9
                                                            goto Laf
                                                        La0:
                                                            r2 = move-exception
                                                            r7 = r2
                                                            r2 = r8
                                                            r8 = r7
                                                        La4:
                                                            java.lang.String r3 = "can not call the provider"
                                                            android.util.Log.d(r5, r3)     // Catch: java.lang.Throwable -> L98
                                                            r8.printStackTrace()     // Catch: java.lang.Throwable -> L98
                                                            if (r2 == 0) goto Lc3
                                                            goto L7b
                                                        Laf:
                                                            if (r2 == 0) goto Lb4
                                                            r2.close()
                                                        Lb4:
                                                            throw r8
                                                        Lb5:
                                                            java.lang.String r8 = "context or uri or DynamicMenuData is null"
                                                            android.util.Log.d(r5, r8)
                                                            goto Lc3
                                                        Lbb:
                                                            com.samsung.android.settings.external.DynamicMenuData$InvalidMenuDataException r8 = new com.samsung.android.settings.external.DynamicMenuData$InvalidMenuDataException
                                                            java.lang.String r9 = "Key cannot be empty"
                                                            r8.<init>(r9)
                                                            throw r8
                                                        Lc3:
                                                            androidx.preference.SwitchPreferenceCompat r9 = (androidx.preference.SwitchPreferenceCompat) r9
                                                            java.util.HashMap r8 = r0.mPreferenceKeySettingsDbPMap
                                                            java.lang.String r9 = r9.getKey()
                                                            java.lang.Object r8 = r8.get(r9)
                                                            java.lang.String r8 = (java.lang.String) r8
                                                            if (r8 == 0) goto Lde
                                                            android.content.ContentResolver r9 = r0.getContentResolver()
                                                            boolean r10 = r10.booleanValue()
                                                            android.provider.Settings.System.putInt(r9, r8, r10)
                                                        Lde:
                                                            com.samsung.android.settings.Trace.endSection()
                                                            return r1
                                                        */
                                                        throw new UnsupportedOperationException(
                                                                "Method not decompiled:"
                                                                    + " com.samsung.android.settings.dynamicmenu.SecDynamicFragment$$ExternalSyntheticLambda1.onPreferenceChange(androidx.preference.Preference,"
                                                                    + " java.lang.Object):boolean");
                                                    }
                                                });
                                        SwitchPreferenceCompat switchPreferenceCompat =
                                                (SwitchPreferenceCompat) createPreference;
                                        String string =
                                                tile.mMetaData.getString(
                                                        "com.samsung.android.settings.settingssystemdbkey",
                                                        null);
                                        if (string == null) {
                                            Log.d("SecDynamicFragment", "dbKey is null");
                                        } else {
                                            Log.i(
                                                    "SecDynamicFragment",
                                                    "read META_DATA_KEY_PREFERENCE_SETTINGS_SYSTEM_DB_KEY : "
                                                            .concat(string));
                                            this.mExternalDBUriList.add(
                                                    Settings.System.getUriFor(string));
                                            this.mSettingsDbPreferenceKeyMap.put(
                                                    string, dashboardKeyForTile);
                                            StringBuilder sb =
                                                    new StringBuilder(
                                                            "mSettingsDbPreferenceKeyMap put : ");
                                            sb.append(string);
                                            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                                                    sb,
                                                    " , ",
                                                    dashboardKeyForTile,
                                                    "SecDynamicFragment");
                                            this.mPreferenceKeySettingsDbPMap.put(
                                                    dashboardKeyForTile, string);
                                            Log.d(
                                                    "SecDynamicFragment",
                                                    "mreferenceKeySettingsDbPMap put : "
                                                            + dashboardKeyForTile
                                                            + " , "
                                                            + string);
                                            switchPreferenceCompat.setChecked(
                                                    Settings.System.getInt(
                                                                    getContentResolver(), string, 0)
                                                            != 0);
                                        }
                                    }
                                }
                                int order = tile.getOrder();
                                if (order != 0) {
                                    createPreference.setOrder(order);
                                }
                                preferenceScreen.addPreference(createPreference);
                            }
                            if (!this.mDynamicKeyList.contains(dashboardKeyForTile)) {
                                AbsAdapter$$ExternalSyntheticOutline0.m(
                                        "mDynamicKeyList.add : ",
                                        dashboardKeyForTile,
                                        "SecDynamicFragment");
                                this.mDynamicKeyList.add(dashboardKeyForTile);
                                arrayList.remove(dashboardKeyForTile);
                            }
                        }
                    }
                }
            }
        }
        if (SettingsLibRune.MENU_SHOW_EXTERNAL_CODE
                && com.android.settingslib.Utils.isVisibleExternalCode(getContext())
                && (highlightablePreferenceGroupAdapter = this.mAdapter) != null) {
            ArrayList arrayList3 = this.mDynamicKeyList;
            highlightablePreferenceGroupAdapter.mDynamicKeyList = arrayList3;
            Iterator it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                int preferenceAdapterPosition =
                        highlightablePreferenceGroupAdapter.getPreferenceAdapterPosition(
                                (String) it3.next());
                if (preferenceAdapterPosition >= 0) {
                    highlightablePreferenceGroupAdapter.notifyItemChanged(
                            preferenceAdapterPosition);
                }
            }
        }
        Iterator it4 = arrayList.iterator();
        while (it4.hasNext()) {
            String str3 = (String) it4.next();
            Log.i("SecDynamicFragment", "removePreference : " + str3);
            Preference findPreference = preferenceScreen.findPreference(str3);
            if (findPreference != null) {
                preferenceScreen.removePreference(findPreference);
                this.mUriList.remove(str3);
            }
        }
        Trace.endSection();
    }

    public final void refreshMenuItems() {
        if (!isLoadingSupported() || !this.mFirstOnResume) {
            updateMenuFromProvider();
            return;
        }
        this.mFirstOnResume = false;
        setLoading(true, false);
        ThreadUtils.postOnBackgroundThread(
                new SecDynamicFragment$$ExternalSyntheticLambda2(this, 0));
    }

    public void setPreferenceIcon(Preference preference, Icon icon) {
        preference.setIcon(icon.loadDrawable(preference.getContext()));
    }

    public final void updateMenuFromProvider() {
        FragmentActivity activity;
        ArrayList bundleListFromUri;
        Trace.beginSection("SecDynamicFragment#updateMenuFromProvider");
        Log.i(
                "SecDynamicFragment",
                "update menu from provider. uri size : " + this.mUriList.size());
        for (Uri uri : this.mUriList.values()) {
            if (uri == null) {
                Log.i("SecDynamicFragment", "provider is null");
            } else {
                Trace.beginSection(
                        "SecDynamicFragment#updateMenuFromProvider_" + uri.getAuthority());
                Log.i("SecDynamicFragment", "get bundle from provider : " + uri.getAuthority());
                try {
                    SecDynamicMenuFeatureProviderImpl secDynamicMenuFeatureProviderImpl =
                            this.mSecDynamicMenuFeatureProvider;
                    Context context = getContext();
                    ArrayList arrayList =
                            (ArrayList)
                                    ((ArrayMap)
                                                    SecDynamicFragmentRegistry
                                                            .PARENT_TO_CATEGORY_KEY_MAP)
                                            .get(getClass().getName());
                    secDynamicMenuFeatureProviderImpl.getClass();
                    bundleListFromUri =
                            SecDynamicMenuFeatureProviderImpl.getBundleListFromUri(
                                    uri, context, arrayList);
                } catch (Exception e) {
                    Log.d("SecDynamicFragment", "updateMenuFromProvider() Exception");
                    e.printStackTrace();
                }
                if (bundleListFromUri == null) {
                    Log.i("SecDynamicFragment", "list is null");
                    Trace.endSection();
                } else {
                    if (isLoadingSupported()) {
                        FragmentActivity activity2 = getActivity();
                        if (activity2 == null) {
                            break;
                        } else {
                            activity2.runOnUiThread(
                                    new SecDynamicFragment$$ExternalSyntheticLambda3(
                                            this, bundleListFromUri));
                        }
                    } else {
                        updatePreferenceUI(bundleListFromUri);
                    }
                    Trace.endSection();
                }
            }
        }
        if (isLoadingSupported() && (activity = getActivity()) != null) {
            activity.runOnUiThread(new SecDynamicFragment$$ExternalSyntheticLambda2(this, 1));
        }
        Trace.endSection();
    }

    public final void updatePreferenceUI(ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            DynamicMenuData dynamicMenuData = (DynamicMenuData) it.next();
            StringBuilder sb = new StringBuilder("DynamicMenuData key ");
            sb.append(dynamicMenuData.mKey);
            sb.append(" ,title : ");
            sb.append(dynamicMenuData.mTitle);
            sb.append(" ,summary : ");
            sb.append(dynamicMenuData.mSummary);
            sb.append(" ,isCheck : ");
            sb.append(dynamicMenuData.mIsChecked);
            sb.append(" ,isEnable : ");
            sb.append(dynamicMenuData.mIsEnabled);
            sb.append(" ,isVisible : ");
            SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                    sb, dynamicMenuData.mIsVisible, "SecDynamicFragment");
            String str = dynamicMenuData.mKey;
            String str2 = dynamicMenuData.mTitle;
            String str3 = dynamicMenuData.mSummary;
            boolean z = dynamicMenuData.mIsChecked;
            boolean z2 = dynamicMenuData.mIsEnabled;
            boolean z3 = dynamicMenuData.mIsVisible;
            boolean z4 = dynamicMenuData.mIsSummaryPrimaryDark;
            Preference findPreference = findPreference(str);
            if (findPreference == null) {
                Log.i("SecDynamicFragment", "pref is null");
            } else {
                findPreference.setTitle(str2);
                findPreference.setSummary(str3);
                findPreference.setEnabled(z2);
                findPreference.setVisible(z3);
                if (findPreference instanceof SwitchPreferenceCompat) {
                    ((SwitchPreferenceCompat) findPreference).setChecked(z);
                }
                SecPreferenceUtils.applySummaryColor(findPreference, z4);
            }
        }
    }
}
