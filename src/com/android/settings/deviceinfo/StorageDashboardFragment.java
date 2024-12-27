package com.android.settings.deviceinfo;

import android.app.Activity;
import android.app.usage.StorageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.DiskInfo;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
import android.provider.SearchIndexableResource;
import android.util.Log;
import android.util.SparseArray;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.deviceinfo.storage.AutomaticStorageManagementSwitchPreferenceController;
import com.android.settings.deviceinfo.storage.DiskInitFragment;
import com.android.settings.deviceinfo.storage.ManageStoragePreferenceController;
import com.android.settings.deviceinfo.storage.NonCurrentUserController;
import com.android.settings.deviceinfo.storage.StorageAsyncLoader;
import com.android.settings.deviceinfo.storage.StorageCacheHelper;
import com.android.settings.deviceinfo.storage.StorageEntry;
import com.android.settings.deviceinfo.storage.StorageItemPreferenceController;
import com.android.settings.deviceinfo.storage.StorageSelectionPreferenceController;
import com.android.settings.deviceinfo.storage.StorageUsageProgressBarPreferenceController;
import com.android.settings.deviceinfo.storage.StorageUtils;
import com.android.settings.deviceinfo.storage.UserIconLoader;
import com.android.settings.deviceinfo.storage.VolumeSizesLoader;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.applications.StorageStatsSource;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.deviceinfo.PrivateStorageInfo;
import com.android.settingslib.deviceinfo.StorageManagerVolumeProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageDashboardFragment extends DashboardFragment
        implements LoaderManager.LoaderCallbacks {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();
    public SparseArray mAppsResult;
    public List mNonCurrentUsers;
    public VolumeOptionMenuController mOptionMenuController;
    public StorageItemPreferenceController mPreferenceController;
    public StorageEntry mSelectedStorageEntry;
    public StorageCacheHelper mStorageCacheHelper;
    public final List mStorageEntries = new ArrayList();
    public final AnonymousClass1 mStorageEventListener;
    public PrivateStorageInfo mStorageInfo;
    public StorageSelectionPreferenceController mStorageSelectionController;
    public StorageUsageProgressBarPreferenceController mStorageUsageProgressBarController;
    public int mUserId;
    public UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.deviceinfo.StorageDashboardFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            StorageManager storageManager =
                    (StorageManager) context.getSystemService(StorageManager.class);
            UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
            ArrayList arrayList = new ArrayList();
            arrayList.add(
                    new StorageItemPreferenceController(
                            context, null, new StorageManagerVolumeProvider(storageManager), 1));
            arrayList.addAll(
                    NonCurrentUserController.getNonCurrentUserControllers(context, userManager));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.storage_dashboard_fragment;
            return Arrays.asList(searchIndexableResource);
        }
    }

    public StorageDashboardFragment() {
        new StorageEventListener() { // from class:
                                     // com.android.settings.deviceinfo.StorageDashboardFragment.1
            public final void onDiskDestroyed(DiskInfo diskInfo) {
                StorageEntry storageEntry = new StorageEntry(diskInfo);
                if (((ArrayList) StorageDashboardFragment.this.mStorageEntries)
                        .remove(storageEntry)) {
                    if (StorageDashboardFragment.this.mSelectedStorageEntry.equals(storageEntry)) {
                        StorageDashboardFragment storageDashboardFragment =
                                StorageDashboardFragment.this;
                        storageDashboardFragment.mSelectedStorageEntry =
                                StorageEntry.getDefaultInternalStorageEntry(
                                        storageDashboardFragment.getContext());
                    }
                    StorageDashboardFragment.this.refreshUi$4();
                }
            }

            public final void onDiskScanned(DiskInfo diskInfo, int i) {
                if (StorageUtils.isDiskUnsupported(diskInfo)) {
                    StorageEntry storageEntry = new StorageEntry(diskInfo);
                    if (((ArrayList) StorageDashboardFragment.this.mStorageEntries)
                            .contains(storageEntry)) {
                        return;
                    }
                    ((ArrayList) StorageDashboardFragment.this.mStorageEntries).add(storageEntry);
                    StorageDashboardFragment.this.refreshUi$4();
                }
            }

            public final void onVolumeForgotten(String str) {
                StorageEntry storageEntry = new StorageEntry(new VolumeRecord(0, str));
                if (((ArrayList) StorageDashboardFragment.this.mStorageEntries)
                        .remove(storageEntry)) {
                    if (StorageDashboardFragment.this.mSelectedStorageEntry.equals(storageEntry)) {
                        StorageDashboardFragment storageDashboardFragment =
                                StorageDashboardFragment.this;
                        storageDashboardFragment.mSelectedStorageEntry =
                                StorageEntry.getDefaultInternalStorageEntry(
                                        storageDashboardFragment.getContext());
                    }
                    StorageDashboardFragment.this.refreshUi$4();
                }
            }

            public final void onVolumeRecordChanged(VolumeRecord volumeRecord) {
                StorageDashboardFragment storageDashboardFragment = StorageDashboardFragment.this;
                BaseSearchIndexProvider baseSearchIndexProvider =
                        StorageDashboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                storageDashboardFragment.getClass();
                if (!StorageUtils.isVolumeRecordMissed(null, volumeRecord)) {
                    StorageDashboardFragment.this.getClass();
                    volumeRecord.getFsUuid();
                    throw null;
                }
                StorageEntry storageEntry = new StorageEntry(volumeRecord);
                if (((ArrayList) StorageDashboardFragment.this.mStorageEntries)
                        .contains(storageEntry)) {
                    return;
                }
                ((ArrayList) StorageDashboardFragment.this.mStorageEntries).add(storageEntry);
                StorageDashboardFragment.this.refreshUi$4();
            }

            public final void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
                int type = volumeInfo.getType();
                if (type == 0 || type == 1 || type == 5) {
                    final StorageEntry storageEntry =
                            new StorageEntry(
                                    StorageDashboardFragment.this.getContext(), volumeInfo);
                    int state = volumeInfo.getState();
                    if (state == 0
                            || state == 2
                            || state == 3
                            || state == 5
                            || state == 6
                            || ((state == 7 || state == 8)
                                    && ((ArrayList) StorageDashboardFragment.this.mStorageEntries)
                                            .remove(storageEntry))) {
                        ((ArrayList) StorageDashboardFragment.this.mStorageEntries)
                                .removeIf(
                                        new Predicate() { // from class:
                                                          // com.android.settings.deviceinfo.StorageDashboardFragment$1$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return ((StorageEntry) obj)
                                                        .equals(StorageEntry.this);
                                            }
                                        });
                        if (state == 2 || state == 3 || state == 6) {
                            ((ArrayList) StorageDashboardFragment.this.mStorageEntries)
                                    .add(storageEntry);
                            if (storageEntry.equals(
                                    StorageDashboardFragment.this.mSelectedStorageEntry)) {
                                StorageDashboardFragment.this.mSelectedStorageEntry = storageEntry;
                            }
                        } else if (storageEntry.equals(
                                StorageDashboardFragment.this.mSelectedStorageEntry)) {
                            StorageDashboardFragment storageDashboardFragment =
                                    StorageDashboardFragment.this;
                            storageDashboardFragment.mSelectedStorageEntry =
                                    StorageEntry.getDefaultInternalStorageEntry(
                                            storageDashboardFragment.getContext());
                        }
                        StorageDashboardFragment.this.refreshUi$4();
                    }
                }
            }
        };
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        StorageItemPreferenceController storageItemPreferenceController =
                new StorageItemPreferenceController(
                        context,
                        this,
                        new StorageManagerVolumeProvider(
                                (StorageManager) context.getSystemService(StorageManager.class)),
                        1);
        this.mPreferenceController = storageItemPreferenceController;
        arrayList.add(storageItemPreferenceController);
        List nonCurrentUserControllers =
                NonCurrentUserController.getNonCurrentUserControllers(context, this.mUserManager);
        this.mNonCurrentUsers = nonCurrentUserControllers;
        arrayList.addAll(nonCurrentUserControllers);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final void displayResourceTilesToScreen(PreferenceScreen preferenceScreen) {
        PreferenceGroup preferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference("pref_non_current_users");
        if (((ArrayList) this.mNonCurrentUsers).isEmpty()) {
            preferenceScreen.removePreference(preferenceGroup);
        }
        super.displayResourceTilesToScreen(preferenceScreen);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "StorageDashboardFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 745;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.storage_dashboard_fragment;
    }

    public PrivateStorageInfo getPrivateStorageInfo() {
        return this.mStorageInfo;
    }

    public SparseArray<StorageAsyncLoader.StorageResult> getStorageResult() {
        return this.mAppsResult;
    }

    public void initializeOptionsMenu(Activity activity) {
        this.mOptionMenuController =
                new VolumeOptionMenuController(activity, this, this.mSelectedStorageEntry);
        getSettingsLifecycle().addObserver(this.mOptionMenuController);
        setHasOptionsMenu(true);
        activity.invalidateOptionsMenu();
    }

    public void maybeSetLoading(boolean z) {
        if (!(z && (this.mStorageInfo == null || this.mAppsResult == null))
                && (z || this.mStorageInfo != null)) {
            return;
        }
        setLoading(true, false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUserId = UserHandle.myUserId();
        this.mStorageCacheHelper = new StorageCacheHelper(getContext(), this.mUserId);
        super.onAttach(context);
        ((AutomaticStorageManagementSwitchPreferenceController)
                        use(AutomaticStorageManagementSwitchPreferenceController.class))
                .setFragmentManager(getFragmentManager());
        StorageSelectionPreferenceController storageSelectionPreferenceController =
                (StorageSelectionPreferenceController)
                        use(StorageSelectionPreferenceController.class);
        this.mStorageSelectionController = storageSelectionPreferenceController;
        storageSelectionPreferenceController.setOnItemSelectedListener(
                new StorageSelectionPreferenceController
                        .OnItemSelectedListener() { // from class:
                                                    // com.android.settings.deviceinfo.StorageDashboardFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.deviceinfo.storage.StorageSelectionPreferenceController.OnItemSelectedListener
                    public final void onItemSelected(StorageEntry storageEntry) {
                        VolumeInfo volumeInfo;
                        StorageDashboardFragment storageDashboardFragment =
                                StorageDashboardFragment.this;
                        storageDashboardFragment.mSelectedStorageEntry = storageEntry;
                        storageDashboardFragment.refreshUi$4();
                        if (storageEntry.isDiskInfoUnsupported()
                                || ((volumeInfo = storageEntry.mVolumeInfo) != null
                                        && volumeInfo.getState() == 6)) {
                            DiskInitFragment.show(
                                    storageDashboardFragment, storageEntry.getDiskId());
                        } else if (storageEntry.isVolumeRecordMissed()) {
                            StorageUtils.launchForgetMissingVolumeRecordFragment(
                                    storageDashboardFragment.getContext(), storageEntry);
                        }
                    }
                });
        this.mStorageUsageProgressBarController =
                (StorageUsageProgressBarPreferenceController)
                        use(StorageUsageProgressBarPreferenceController.class);
        ((ManageStoragePreferenceController) use(ManageStoragePreferenceController.class))
                .setUserId(this.mUserId);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            try {
                startActivity(new Intent("com.sec.android.app.myfiles.RUN_STORAGE_ANALYSIS"));
            } catch (ActivityNotFoundException unused) {
                Log.e("StorageDashboardFrag", "RUN_STORAGE_ANALYSIS : Activity Not Found");
            }
        } finally {
            finish();
        }
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final Loader onCreateLoader(int i, Bundle bundle) {
        Context context = getContext();
        return new StorageAsyncLoader(
                context,
                this.mUserManager,
                this.mSelectedStorageEntry.getFsUuid(),
                new StorageStatsSource(context),
                context.getPackageManager());
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoadFinished(Loader loader, Object obj) {
        this.mAppsResult = (SparseArray) obj;
        onReceivedSizes$1();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        throw null;
    }

    public final void onReceivedSizes$1() {
        if (this.mStorageInfo == null || this.mAppsResult == null) {
            return;
        }
        setLoading(false, false);
        PrivateStorageInfo privateStorageInfo = this.mStorageInfo;
        long j = privateStorageInfo.totalBytes - privateStorageInfo.freeBytes;
        this.mPreferenceController.setVolume(this.mSelectedStorageEntry.mVolumeInfo);
        StorageItemPreferenceController storageItemPreferenceController =
                this.mPreferenceController;
        storageItemPreferenceController.mUsedBytes = j;
        long j2 = this.mStorageInfo.totalBytes;
        storageItemPreferenceController.mTotalSize = j2;
        this.mStorageCacheHelper
                .mSharedPreferences
                .edit()
                .putLong("total_size_key", j2)
                .putLong("total_used_size_key", j)
                .apply();
        Iterator it = ((ArrayList) this.mNonCurrentUsers).iterator();
        while (it.hasNext()) {
            ((NonCurrentUserController) it.next()).mTotalSizeBytes = this.mStorageInfo.totalBytes;
        }
        this.mPreferenceController.onLoadFinished(this.mAppsResult, this.mUserId);
        List list = this.mNonCurrentUsers;
        SparseArray sparseArray = this.mAppsResult;
        Iterator it2 = ((ArrayList) list).iterator();
        while (it2.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it2.next();
            if (abstractPreferenceController instanceof NonCurrentUserController) {
                ((NonCurrentUserController) abstractPreferenceController).handleResult(sparseArray);
            }
        }
        if (((ArrayList) this.mNonCurrentUsers).isEmpty()) {
            return;
        }
        NonCurrentUserController nonCurrentUserController =
                (NonCurrentUserController) ((ArrayList) this.mNonCurrentUsers).get(0);
        nonCurrentUserController.mIsVisible = true;
        PreferenceGroup preferenceGroup = nonCurrentUserController.mPreferenceGroup;
        if (preferenceGroup != null) {
            preferenceGroup.setVisible(true);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        ((ArrayList) this.mStorageEntries).clear();
        ((ArrayList) this.mStorageEntries)
                .addAll(StorageUtils.getAllStorageEntries(getContext(), null));
        refreshUi$4();
        throw null;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("selected_storage_entry_key", this.mSelectedStorageEntry);
        super.onSaveInstanceState(bundle);
    }

    public final void refreshUi$4() {
        this.mStorageSelectionController.setStorageEntries(this.mStorageEntries);
        this.mStorageSelectionController.setSelectedStorageEntry(this.mSelectedStorageEntry);
        this.mStorageUsageProgressBarController.setSelectedStorageEntry(this.mSelectedStorageEntry);
        VolumeOptionMenuController volumeOptionMenuController = this.mOptionMenuController;
        volumeOptionMenuController.mStorageEntry = this.mSelectedStorageEntry;
        volumeOptionMenuController.updateOptionsMenu();
        getActivity().invalidateOptionsMenu();
        if (!((ArrayList) this.mNonCurrentUsers).isEmpty()) {
            NonCurrentUserController nonCurrentUserController =
                    (NonCurrentUserController) ((ArrayList) this.mNonCurrentUsers).get(0);
            nonCurrentUserController.mIsVisible = false;
            PreferenceGroup preferenceGroup = nonCurrentUserController.mPreferenceGroup;
            if (preferenceGroup != null) {
                preferenceGroup.setVisible(false);
            }
        }
        if (!this.mSelectedStorageEntry.isMounted()) {
            this.mPreferenceController.setVolume(null);
            return;
        }
        if (this.mStorageCacheHelper.hasCachedSizeInfo()
                && this.mSelectedStorageEntry.isPrivate()) {
            StorageCacheHelper.StorageCache retrieveCachedSize =
                    this.mStorageCacheHelper.retrieveCachedSize();
            this.mPreferenceController.setVolume(this.mSelectedStorageEntry.mVolumeInfo);
            StorageItemPreferenceController storageItemPreferenceController =
                    this.mPreferenceController;
            storageItemPreferenceController.mUsedBytes = retrieveCachedSize.totalUsedSize;
            storageItemPreferenceController.mTotalSize = retrieveCachedSize.totalSize;
        }
        if (!this.mSelectedStorageEntry.isPrivate()) {
            this.mPreferenceController.setVolume(this.mSelectedStorageEntry.mVolumeInfo);
            return;
        }
        this.mStorageInfo = null;
        this.mAppsResult = null;
        if (this.mStorageCacheHelper.hasCachedSizeInfo()) {
            this.mPreferenceController.onLoadFinished(this.mAppsResult, this.mUserId);
        } else {
            maybeSetLoading(
                    this.mSelectedStorageEntry.isMounted()
                            && ((StorageStatsManager)
                                            getActivity()
                                                    .getSystemService(StorageStatsManager.class))
                                    .isQuotaSupported(this.mSelectedStorageEntry.getFsUuid()));
            this.mPreferenceController.setVolume(null);
        }
        LoaderManager loaderManager = getLoaderManager();
        Bundle bundle = Bundle.EMPTY;
        loaderManager.restartLoader(0, bundle, this);
        getLoaderManager().restartLoader(2, bundle, new IconLoaderCallbacks(this, 1));
        getLoaderManager().restartLoader(1, bundle, new IconLoaderCallbacks(this, 0));
    }

    public void setPrivateStorageInfo(PrivateStorageInfo privateStorageInfo) {
        this.mStorageInfo = privateStorageInfo;
    }

    public void setStorageResult(SparseArray<StorageAsyncLoader.StorageResult> sparseArray) {
        this.mAppsResult = sparseArray;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IconLoaderCallbacks implements LoaderManager.LoaderCallbacks {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ StorageDashboardFragment this$0;

        public /* synthetic */ IconLoaderCallbacks(
                StorageDashboardFragment storageDashboardFragment, int i) {
            this.$r8$classId = i;
            this.this$0 = storageDashboardFragment;
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final Loader onCreateLoader(int i, Bundle bundle) {
            StorageDashboardFragment storageDashboardFragment = this.this$0;
            switch (this.$r8$classId) {
                case 0:
                    return new UserIconLoader(
                            storageDashboardFragment.getContext(),
                            new UserIconLoader
                                    .FetchUserIconTask() { // from class:
                                                           // com.android.settings.deviceinfo.StorageDashboardFragment$IconLoaderCallbacks$$ExternalSyntheticLambda2
                                @Override // com.android.settings.deviceinfo.storage.UserIconLoader.FetchUserIconTask
                                public final SparseArray getUserIcons() {
                                    return UserIconLoader.loadUserIconsWithContext(
                                            StorageDashboardFragment.IconLoaderCallbacks.this.this$0
                                                    .getContext());
                                }
                            });
                default:
                    Context context = storageDashboardFragment.getContext();
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            StorageDashboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                    return new VolumeSizesLoader(
                            context,
                            new StorageManagerVolumeProvider(null),
                            (StorageStatsManager)
                                    context.getSystemService(StorageStatsManager.class),
                            storageDashboardFragment.mSelectedStorageEntry.mVolumeInfo);
            }
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoadFinished(Loader loader, Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    final SparseArray sparseArray = (SparseArray) obj;
                    this.this$0.mNonCurrentUsers.stream()
                            .filter(
                                    new StorageDashboardFragment$IconLoaderCallbacks$$ExternalSyntheticLambda0())
                            .forEach(
                                    new Consumer() { // from class:
                                                     // com.android.settings.deviceinfo.StorageDashboardFragment$IconLoaderCallbacks$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj2) {
                                            StorageItemPreference storageItemPreference;
                                            NonCurrentUserController nonCurrentUserController =
                                                    (NonCurrentUserController) obj2;
                                            Drawable drawable =
                                                    (Drawable)
                                                            sparseArray.get(
                                                                    nonCurrentUserController
                                                                            .mUser
                                                                            .id);
                                            nonCurrentUserController.mUserIcon = drawable;
                                            if (drawable == null
                                                    || (storageItemPreference =
                                                                    nonCurrentUserController
                                                                            .mStoragePreference)
                                                            == null) {
                                                return;
                                            }
                                            storageItemPreference.setIcon(drawable);
                                        }
                                    });
                    break;
                default:
                    PrivateStorageInfo privateStorageInfo = (PrivateStorageInfo) obj;
                    StorageDashboardFragment storageDashboardFragment = this.this$0;
                    if (privateStorageInfo != null) {
                        storageDashboardFragment.mStorageInfo = privateStorageInfo;
                        storageDashboardFragment.onReceivedSizes$1();
                        break;
                    } else {
                        storageDashboardFragment.getActivity().finish();
                        break;
                    }
            }
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoaderReset(Loader loader) {
            int i = this.$r8$classId;
        }

        private final void
                onLoaderReset$com$android$settings$deviceinfo$StorageDashboardFragment$IconLoaderCallbacks(
                        Loader loader) {}

        private final void
                onLoaderReset$com$android$settings$deviceinfo$StorageDashboardFragment$VolumeSizeCallbacks(
                        Loader loader) {}
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader loader) {}
}
