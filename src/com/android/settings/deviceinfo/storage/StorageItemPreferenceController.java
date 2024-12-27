package com.android.settings.deviceinfo.storage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.VolumeInfo;
import android.util.DataUnit;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.Utils;
import com.android.settings.applications.manageapplications.ManageApplications;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.deviceinfo.StorageItemPreference;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.deviceinfo.StorageManagerVolumeProvider;
import com.android.settingslib.deviceinfo.StorageVolumeProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StorageItemPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    static final String APPS_KEY = "pref_apps";
    static final String AUDIO_KEY = "pref_audio";
    static final String CATEGORY_SPLITTER = "storage_category_splitter";
    static final String DOCUMENTS_KEY = "pref_documents";
    static final String GAMES_KEY = "pref_games";
    static final String IMAGES_KEY = "pref_images";
    static final String OTHER_KEY = "pref_other";
    static final String PUBLIC_STORAGE_KEY = "pref_public_storage";
    static final String SYSTEM_KEY = "pref_system";
    static final String TEMPORARY_FILES_KEY = "temporary_files";
    static final String TRASH_KEY = "pref_trash";
    static final String VIDEOS_KEY = "pref_videos";
    StorageItemPreference mAppsPreference;
    StorageItemPreference mAudioPreference;
    final Uri mAudioUri;
    PreferenceCategory mCategorySplitterPreferenceCategory;
    StorageItemPreference mDocumentsPreference;
    final Uri mDocumentsUri;
    public final Fragment mFragment;
    StorageItemPreference mGamesPreference;
    StorageItemPreference mImagesPreference;
    final Uri mImagesUri;
    public boolean mIsDocumentsPrefShown;
    public boolean mIsPreferenceOrderedBySize;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    StorageItemPreference mOtherPreference;
    final Uri mOtherUri;
    public final PackageManager mPackageManager;
    public List mPrivateStorageItemPreferences;
    public final int mProfileType;
    Preference mPublicStoragePreference;
    public PreferenceScreen mScreen;
    public final StorageCacheHelper mStorageCacheHelper;
    public final StorageVolumeProvider mSvp;
    StorageItemPreference mSystemPreference;
    StorageItemPreference mTemporaryFilesPreference;
    public long mTotalSize;
    StorageItemPreference mTrashPreference;
    public long mUsedBytes;
    public final int mUserId;
    public final UserManager mUserManager;
    StorageItemPreference mVideosPreference;
    final Uri mVideosUri;
    public VolumeInfo mVolume;

    public StorageItemPreferenceController(
            Context context,
            Fragment fragment,
            StorageManagerVolumeProvider storageManagerVolumeProvider,
            int i) {
        super(context);
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mFragment = fragment;
        this.mVolume = null;
        this.mSvp = storageManagerVolumeProvider;
        this.mProfileType = i;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        int currentUserId = getCurrentUserId();
        this.mUserId = currentUserId;
        VolumeInfo findEmulatedForPrivate =
                storageManagerVolumeProvider.mStorageManager.findEmulatedForPrivate(this.mVolume);
        this.mIsDocumentsPrefShown =
                findEmulatedForPrivate != null && findEmulatedForPrivate.isMountedReadable();
        this.mStorageCacheHelper = new StorageCacheHelper(this.mContext, currentUserId);
        this.mImagesUri =
                Uri.parse(
                        context.getResources()
                                .getString(R.string.config_images_storage_category_uri));
        this.mVideosUri =
                Uri.parse(
                        context.getResources()
                                .getString(R.string.config_videos_storage_category_uri));
        this.mAudioUri =
                Uri.parse(
                        context.getResources()
                                .getString(R.string.config_audio_storage_category_uri));
        this.mDocumentsUri =
                Uri.parse(
                        context.getResources()
                                .getString(R.string.config_documents_storage_category_uri));
        this.mOtherUri =
                Uri.parse(
                        context.getResources()
                                .getString(R.string.config_other_storage_category_uri));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mScreen = preferenceScreen;
        this.mPublicStoragePreference = preferenceScreen.findPreference(PUBLIC_STORAGE_KEY);
        this.mImagesPreference =
                (StorageItemPreference) preferenceScreen.findPreference(IMAGES_KEY);
        this.mVideosPreference =
                (StorageItemPreference) preferenceScreen.findPreference(VIDEOS_KEY);
        this.mAudioPreference = (StorageItemPreference) preferenceScreen.findPreference(AUDIO_KEY);
        this.mAppsPreference = (StorageItemPreference) preferenceScreen.findPreference(APPS_KEY);
        this.mGamesPreference = (StorageItemPreference) preferenceScreen.findPreference(GAMES_KEY);
        this.mDocumentsPreference =
                (StorageItemPreference) preferenceScreen.findPreference(DOCUMENTS_KEY);
        this.mOtherPreference = (StorageItemPreference) preferenceScreen.findPreference(OTHER_KEY);
        this.mCategorySplitterPreferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(CATEGORY_SPLITTER);
        this.mSystemPreference =
                (StorageItemPreference) preferenceScreen.findPreference(SYSTEM_KEY);
        this.mTemporaryFilesPreference =
                (StorageItemPreference) preferenceScreen.findPreference(TEMPORARY_FILES_KEY);
        this.mTrashPreference = (StorageItemPreference) preferenceScreen.findPreference(TRASH_KEY);
    }

    public int getCurrentUserId() {
        return Utils.getCurrentUserIdOfType(this.mUserManager, this.mProfileType);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    public final Bundle getWorkAnnotatedBundle(int i) {
        Bundle bundle = new Bundle(i + 1);
        int i2 = this.mProfileType;
        if (i2 == 2) {
            bundle.putInt(":settings:show_fragment_tab", 1);
        } else if (i2 == 4) {
            bundle.putInt(":settings:show_fragment_tab", 2);
        } else {
            bundle.putInt(":settings:show_fragment_tab", 0);
        }
        return bundle;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        int i;
        SettingsMetricsFeatureProvider settingsMetricsFeatureProvider;
        Fragment fragment;
        if (preference.getKey() == null) {
            return false;
        }
        String key = preference.getKey();
        key.getClass();
        i = this.mUserId;
        settingsMetricsFeatureProvider = this.mMetricsFeatureProvider;
        fragment = this.mFragment;
        switch (key) {
            case "pref_audio":
                launchActivityWithUri(this.mAudioUri);
                break;
            case "pref_games":
                Bundle workAnnotatedBundle = getWorkAnnotatedBundle(1);
                workAnnotatedBundle.putString(
                        "classname", Settings.GamesStorageActivity.class.getName());
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
                String name = ManageApplications.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName = name;
                subSettingLauncher.setTitleRes(R.string.game_storage_settings, null);
                launchRequest.mArguments = workAnnotatedBundle;
                settingsMetricsFeatureProvider.getClass();
                launchRequest.mSourceMetricsCategory =
                        MetricsFeatureProvider.getMetricsCategory(fragment);
                Intent intent = subSettingLauncher.toIntent();
                intent.putExtra("android.intent.extra.USER_ID", i);
                Utils.launchIntent(fragment, intent);
                break;
            case "pref_other":
                launchActivityWithUri(this.mOtherUri);
                break;
            case "pref_trash":
                Intent intent2 = new Intent("android.settings.VIEW_TRASH");
                if (this.mPackageManager.resolveActivityAsUser(intent2, 0, i) != null) {
                    this.mContext.startActivityAsUser(intent2, new UserHandle(i));
                    break;
                } else {
                    long j = this.mTrashPreference.mStorageSize;
                    if (j <= 0) {
                        Toast.makeText(
                                        this.mContext,
                                        R.string.storage_trash_dialog_empty_message,
                                        0)
                                .show();
                        break;
                    } else {
                        EmptyTrashFragment emptyTrashFragment =
                                new EmptyTrashFragment(this.mFragment, this.mUserId, j, this);
                        emptyTrashFragment.show(
                                emptyTrashFragment.mParentFragment.getFragmentManager(),
                                "empty_trash");
                        break;
                    }
                }
            case "pref_apps":
                Bundle workAnnotatedBundle2 = getWorkAnnotatedBundle(3);
                workAnnotatedBundle2.putString(
                        "classname", Settings.StorageUseActivity.class.getName());
                workAnnotatedBundle2.putString("volumeUuid", this.mVolume.getFsUuid());
                workAnnotatedBundle2.putString("volumeName", this.mVolume.getDescription());
                SubSettingLauncher subSettingLauncher2 = new SubSettingLauncher(this.mContext);
                String name2 = ManageApplications.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest2 =
                        subSettingLauncher2.mLaunchRequest;
                launchRequest2.mDestinationName = name2;
                subSettingLauncher2.setTitleRes(R.string.apps_storage, null);
                launchRequest2.mArguments = workAnnotatedBundle2;
                settingsMetricsFeatureProvider.getClass();
                launchRequest2.mSourceMetricsCategory =
                        MetricsFeatureProvider.getMetricsCategory(fragment);
                Intent intent3 = subSettingLauncher2.toIntent();
                intent3.putExtra("android.intent.extra.USER_ID", i);
                Utils.launchIntent(fragment, intent3);
                break;
            case "pref_public_storage":
                Intent buildBrowseIntent = this.mVolume.buildBrowseIntent();
                if (buildBrowseIntent != null) {
                    this.mContext.startActivityAsUser(buildBrowseIntent, new UserHandle(i));
                    break;
                }
                break;
            case "pref_images":
                launchActivityWithUri(this.mImagesUri);
                break;
            case "temporary_files":
                StorageUtils.TemporaryFilesInfoFragment temporaryFilesInfoFragment =
                        new StorageUtils.TemporaryFilesInfoFragment();
                temporaryFilesInfoFragment.setTargetFragment(fragment, 0);
                temporaryFilesInfoFragment.show(
                        fragment.getFragmentManager(), "TemporaryFilesInfo");
                break;
            case "pref_system":
                StorageUtils.SystemInfoFragment systemInfoFragment =
                        new StorageUtils.SystemInfoFragment();
                systemInfoFragment.setTargetFragment(fragment, 0);
                systemInfoFragment.show(fragment.getFragmentManager(), "SystemInfo");
                break;
            case "pref_videos":
                launchActivityWithUri(this.mVideosUri);
                break;
            case "pref_documents":
                launchActivityWithUri(this.mDocumentsUri);
                break;
        }
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public final boolean isValidPrivateVolume() {
        VolumeInfo volumeInfo = this.mVolume;
        return volumeInfo != null
                && volumeInfo.getType() == 1
                && (this.mVolume.getState() == 2 || this.mVolume.getState() == 3);
    }

    public final void launchActivityWithUri(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(uri);
        this.mContext.startActivityAsUser(intent, new UserHandle(this.mUserId));
    }

    public final void onLoadFinished(SparseArray sparseArray, int i) {
        StorageCacheHelper.StorageCache storageCache;
        boolean z = sparseArray != null && this.mIsPreferenceOrderedBySize;
        StorageCacheHelper storageCacheHelper = this.mStorageCacheHelper;
        if (sparseArray == null) {
            storageCache = storageCacheHelper.retrieveCachedSize();
        } else {
            StorageAsyncLoader.StorageResult storageResult =
                    (StorageAsyncLoader.StorageResult) sparseArray.get(i);
            StorageCacheHelper.StorageCache storageCache2 = new StorageCacheHelper.StorageCache();
            storageCache2.imagesSize = storageResult.imagesSize;
            storageCache2.videosSize = storageResult.videosSize;
            storageCache2.audioSize = storageResult.audioSize;
            storageCache2.allAppsExceptGamesSize = storageResult.allAppsExceptGamesSize;
            storageCache2.gamesSize = storageResult.gamesSize;
            storageCache2.documentsSize = storageResult.documentsSize;
            storageCache2.otherSize = storageResult.otherSize;
            storageCache2.trashSize = storageResult.trashSize;
            storageCache2.systemSize = storageResult.systemSize;
            long j = 0;
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                StorageAsyncLoader.StorageResult storageResult2 =
                        (StorageAsyncLoader.StorageResult) sparseArray.valueAt(i2);
                j =
                        ((((((((storageResult2.gamesSize + storageResult2.audioSize)
                                                                                        + storageResult2
                                                                                                .videosSize)
                                                                                + storageResult2
                                                                                        .imagesSize)
                                                                        + storageResult2
                                                                                .documentsSize)
                                                                + storageResult2.otherSize)
                                                        + storageResult2.trashSize)
                                                + storageResult2.allAppsExceptGamesSize)
                                        + j)
                                - storageResult2.duplicateCodeSize;
            }
            storageCache2.temporaryFilesSize =
                    Math.max(
                            DataUnit.GIBIBYTES.toBytes(1L),
                            this.mUsedBytes - (j + storageResult.systemSize));
            storageCache = storageCache2;
        }
        boolean z2 = z;
        this.mImagesPreference.setStorageSize(storageCache.imagesSize, this.mTotalSize, z2);
        this.mVideosPreference.setStorageSize(storageCache.videosSize, this.mTotalSize, z2);
        this.mAudioPreference.setStorageSize(storageCache.audioSize, this.mTotalSize, z2);
        this.mAppsPreference.setStorageSize(
                storageCache.allAppsExceptGamesSize, this.mTotalSize, z2);
        this.mGamesPreference.setStorageSize(storageCache.gamesSize, this.mTotalSize, z2);
        this.mDocumentsPreference.setStorageSize(storageCache.documentsSize, this.mTotalSize, z2);
        this.mOtherPreference.setStorageSize(storageCache.otherSize, this.mTotalSize, z2);
        this.mTrashPreference.setStorageSize(storageCache.trashSize, this.mTotalSize, z2);
        StorageItemPreference storageItemPreference = this.mSystemPreference;
        if (storageItemPreference != null) {
            storageItemPreference.setStorageSize(storageCache.systemSize, this.mTotalSize, z);
            this.mSystemPreference.setTitle(
                    this.mContext.getString(R.string.storage_os_name, Build.VERSION.RELEASE));
        }
        StorageItemPreference storageItemPreference2 = this.mTemporaryFilesPreference;
        if (storageItemPreference2 != null) {
            storageItemPreference2.setStorageSize(
                    storageCache.temporaryFilesSize, this.mTotalSize, z);
        }
        if (sparseArray != null) {
            storageCacheHelper
                    .mSharedPreferences
                    .edit()
                    .putLong("images_size_key", storageCache.imagesSize)
                    .putLong("videos_size_key", storageCache.videosSize)
                    .putLong("audio_size_key", storageCache.audioSize)
                    .putLong("apps_size_key", storageCache.allAppsExceptGamesSize)
                    .putLong("games_size_key", storageCache.gamesSize)
                    .putLong("documents_size_key", storageCache.documentsSize)
                    .putLong("other_size_key", storageCache.otherSize)
                    .putLong("trash_size_key", storageCache.trashSize)
                    .putLong("system_size_key", storageCache.systemSize)
                    .putLong("temporary_files_size_key", storageCache.temporaryFilesSize)
                    .apply();
        }
        if (!this.mIsPreferenceOrderedBySize) {
            updatePrivateStorageCategoryPreferencesOrder();
            this.mIsPreferenceOrderedBySize = true;
        }
        setPrivateStorageCategoryPreferencesVisibility(true);
    }

    public void setPrivateStorageCategoryPreferencesVisibility(boolean z) {
        if (this.mScreen == null) {
            return;
        }
        this.mImagesPreference.setVisible(z);
        this.mVideosPreference.setVisible(z);
        this.mAudioPreference.setVisible(z);
        this.mAppsPreference.setVisible(z);
        this.mGamesPreference.setVisible(z);
        this.mSystemPreference.setVisible(z);
        this.mTemporaryFilesPreference.setVisible(z);
        this.mCategorySplitterPreferenceCategory.setVisible(z);
        this.mTrashPreference.setVisible(z);
        if (z) {
            this.mDocumentsPreference.setVisible(this.mIsDocumentsPrefShown);
            this.mOtherPreference.setVisible(this.mIsDocumentsPrefShown);
        } else {
            this.mDocumentsPreference.setVisible(false);
            this.mOtherPreference.setVisible(false);
        }
    }

    public final void setVolume(VolumeInfo volumeInfo) {
        this.mVolume = volumeInfo;
        Preference preference = this.mPublicStoragePreference;
        if (preference != null) {
            preference.setVisible(
                    volumeInfo != null
                            && (volumeInfo.getType() == 0 || this.mVolume.getType() == 5)
                            && ((this.mVolume.getState() == 2 || this.mVolume.getState() == 3)
                                    && this.mProfileType == 1));
        }
        if (!isValidPrivateVolume()) {
            setPrivateStorageCategoryPreferencesVisibility(false);
        } else {
            VolumeInfo findEmulatedForPrivate =
                    ((StorageManagerVolumeProvider) this.mSvp)
                            .mStorageManager.findEmulatedForPrivate(this.mVolume);
            this.mIsDocumentsPrefShown =
                    findEmulatedForPrivate != null && findEmulatedForPrivate.isMountedReadable();
        }
    }

    public final void updatePrivateStorageCategoryPreferencesOrder() {
        if (this.mScreen == null || !isValidPrivateVolume()) {
            return;
        }
        if (this.mPrivateStorageItemPreferences == null) {
            ArrayList arrayList = new ArrayList();
            this.mPrivateStorageItemPreferences = arrayList;
            arrayList.add(this.mTrashPreference);
            ((ArrayList) this.mPrivateStorageItemPreferences).add(this.mOtherPreference);
            ((ArrayList) this.mPrivateStorageItemPreferences).add(this.mDocumentsPreference);
            ((ArrayList) this.mPrivateStorageItemPreferences).add(this.mGamesPreference);
            ((ArrayList) this.mPrivateStorageItemPreferences).add(this.mAppsPreference);
            ((ArrayList) this.mPrivateStorageItemPreferences).add(this.mAudioPreference);
            ((ArrayList) this.mPrivateStorageItemPreferences).add(this.mVideosPreference);
            ((ArrayList) this.mPrivateStorageItemPreferences).add(this.mImagesPreference);
        }
        this.mScreen.removePreference(this.mImagesPreference);
        this.mScreen.removePreference(this.mVideosPreference);
        this.mScreen.removePreference(this.mAudioPreference);
        this.mScreen.removePreference(this.mAppsPreference);
        this.mScreen.removePreference(this.mGamesPreference);
        this.mScreen.removePreference(this.mDocumentsPreference);
        this.mScreen.removePreference(this.mOtherPreference);
        this.mScreen.removePreference(this.mTrashPreference);
        Collections.sort(
                this.mPrivateStorageItemPreferences,
                Comparator.comparingLong(
                        new StorageItemPreferenceController$$ExternalSyntheticLambda0()));
        Iterator it = ((ArrayList) this.mPrivateStorageItemPreferences).iterator();
        int i = 200;
        while (it.hasNext()) {
            StorageItemPreference storageItemPreference = (StorageItemPreference) it.next();
            storageItemPreference.setOrder(i);
            this.mScreen.addPreference(storageItemPreference);
            i--;
        }
    }
}
