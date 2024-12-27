package com.android.settings.deviceinfo;

import android.app.usage.StorageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.util.Log;
import android.util.SparseArray;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.deviceinfo.storage.ManageStoragePreferenceController;
import com.android.settings.deviceinfo.storage.NonCurrentUserController;
import com.android.settings.deviceinfo.storage.StorageAsyncLoader;
import com.android.settings.deviceinfo.storage.StorageCacheHelper;
import com.android.settings.deviceinfo.storage.StorageEntry;
import com.android.settings.deviceinfo.storage.StorageItemPreferenceController;
import com.android.settings.deviceinfo.storage.UserIconLoader;
import com.android.settings.deviceinfo.storage.VolumeSizesLoader;
import com.android.settingslib.applications.StorageStatsSource;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.deviceinfo.PrivateStorageInfo;
import com.android.settingslib.deviceinfo.StorageManagerVolumeProvider;

import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageCategoryFragment extends DashboardFragment
        implements LoaderManager.LoaderCallbacks {
    public SparseArray mAppsResult;
    public List mNonCurrentUsers;
    public StorageItemPreferenceController mPreferenceController;
    public int mProfileType;
    public StorageEntry mSelectedStorageEntry;
    public StorageCacheHelper mStorageCacheHelper;
    public PrivateStorageInfo mStorageInfo;
    public int mUserId;
    public UserManager mUserManager;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        StorageItemPreferenceController storageItemPreferenceController =
                new StorageItemPreferenceController(
                        context,
                        this,
                        new StorageManagerVolumeProvider(
                                (StorageManager) context.getSystemService(StorageManager.class)),
                        this.mProfileType);
        this.mPreferenceController = storageItemPreferenceController;
        arrayList.add(storageItemPreferenceController);
        List nonCurrentUserControllers =
                this.mProfileType == 1
                        ? NonCurrentUserController.getNonCurrentUserControllers(
                                context, this.mUserManager)
                        : Collections.EMPTY_LIST;
        this.mNonCurrentUsers = nonCurrentUserControllers;
        arrayList.addAll(nonCurrentUserControllers);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final void displayResourceTilesToScreen(PreferenceScreen preferenceScreen) {
        PreferenceGroup preferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference("pref_non_current_users");
        if (this.mNonCurrentUsers.isEmpty()) {
            preferenceScreen.removePreference(preferenceGroup);
        }
        super.displayResourceTilesToScreen(preferenceScreen);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "StorageCategoryFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        int i = this.mProfileType;
        if (i == 2) {
            return 1988;
        }
        return i == 4 ? 2043 : 745;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.storage_category_fragment;
    }

    public PrivateStorageInfo getPrivateStorageInfo() {
        return this.mStorageInfo;
    }

    public SparseArray<StorageAsyncLoader.StorageResult> getStorageResult() {
        return this.mAppsResult;
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
        int i = getArguments().getInt(ImsProfile.SERVICE_PROFILE);
        this.mProfileType = i;
        this.mUserId = Utils.getCurrentUserIdOfType(this.mUserManager, i);
        this.mStorageCacheHelper = new StorageCacheHelper(getContext(), this.mUserId);
        super.onAttach(context);
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
                Log.e("StorageCategoryFrag", "RUN_STORAGE_ANALYSIS : Activity Not Found");
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
        onReceivedSizes();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getLoaderManager().destroyLoader(0);
        getLoaderManager().destroyLoader(1);
        getLoaderManager().destroyLoader(2);
    }

    public final void onReceivedSizes() {
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
        Iterator it = this.mNonCurrentUsers.iterator();
        while (it.hasNext()) {
            ((NonCurrentUserController) it.next()).mTotalSizeBytes = this.mStorageInfo.totalBytes;
        }
        this.mPreferenceController.onLoadFinished(this.mAppsResult, this.mUserId);
        List<AbstractPreferenceController> list = this.mNonCurrentUsers;
        SparseArray sparseArray = this.mAppsResult;
        for (AbstractPreferenceController abstractPreferenceController : list) {
            if (abstractPreferenceController instanceof NonCurrentUserController) {
                ((NonCurrentUserController) abstractPreferenceController).handleResult(sparseArray);
            }
        }
        if (this.mNonCurrentUsers.isEmpty()) {
            return;
        }
        NonCurrentUserController nonCurrentUserController =
                (NonCurrentUserController) this.mNonCurrentUsers.get(0);
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
        StorageEntry storageEntry = this.mSelectedStorageEntry;
        if (storageEntry != null) {
            refreshUi(storageEntry);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("selected_storage_entry_key", this.mSelectedStorageEntry);
        super.onSaveInstanceState(bundle);
    }

    public final void refreshUi(StorageEntry storageEntry) {
        this.mSelectedStorageEntry = storageEntry;
        if (this.mPreferenceController == null) {
            return;
        }
        if (!this.mNonCurrentUsers.isEmpty()) {
            NonCurrentUserController nonCurrentUserController =
                    (NonCurrentUserController) this.mNonCurrentUsers.get(0);
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
        public final /* synthetic */ StorageCategoryFragment this$0;

        public /* synthetic */ IconLoaderCallbacks(
                StorageCategoryFragment storageCategoryFragment, int i) {
            this.$r8$classId = i;
            this.this$0 = storageCategoryFragment;
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final Loader onCreateLoader(int i, Bundle bundle) {
            switch (this.$r8$classId) {
                case 0:
                    return new UserIconLoader(
                            this.this$0.getContext(),
                            new UserIconLoader
                                    .FetchUserIconTask() { // from class:
                                                           // com.android.settings.deviceinfo.StorageCategoryFragment$IconLoaderCallbacks$$ExternalSyntheticLambda0
                                @Override // com.android.settings.deviceinfo.storage.UserIconLoader.FetchUserIconTask
                                public final SparseArray getUserIcons() {
                                    return UserIconLoader.loadUserIconsWithContext(
                                            StorageCategoryFragment.IconLoaderCallbacks.this.this$0
                                                    .getContext());
                                }
                            });
                default:
                    StorageCategoryFragment storageCategoryFragment = this.this$0;
                    Context context = storageCategoryFragment.getContext();
                    return new VolumeSizesLoader(
                            context,
                            new StorageManagerVolumeProvider(null),
                            (StorageStatsManager)
                                    context.getSystemService(StorageStatsManager.class),
                            storageCategoryFragment.mSelectedStorageEntry.mVolumeInfo);
            }
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoadFinished(Loader loader, Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    final SparseArray sparseArray = (SparseArray) obj;
                    this.this$0.mNonCurrentUsers.stream()
                            .filter(
                                    new StorageCategoryFragment$IconLoaderCallbacks$$ExternalSyntheticLambda1())
                            .forEach(
                                    new Consumer() { // from class:
                                                     // com.android.settings.deviceinfo.StorageCategoryFragment$IconLoaderCallbacks$$ExternalSyntheticLambda2
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
                    StorageCategoryFragment storageCategoryFragment = this.this$0;
                    if (privateStorageInfo != null) {
                        storageCategoryFragment.mStorageInfo = privateStorageInfo;
                        storageCategoryFragment.onReceivedSizes();
                        break;
                    } else {
                        storageCategoryFragment.getActivity().finish();
                        break;
                    }
            }
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoaderReset(Loader loader) {
            int i = this.$r8$classId;
        }

        private final void
                onLoaderReset$com$android$settings$deviceinfo$StorageCategoryFragment$IconLoaderCallbacks(
                        Loader loader) {}

        private final void
                onLoaderReset$com$android$settings$deviceinfo$StorageCategoryFragment$VolumeSizeCallbacks(
                        Loader loader) {}
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader loader) {}
}
