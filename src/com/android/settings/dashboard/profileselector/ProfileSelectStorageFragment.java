package com.android.settings.dashboard.profileselector;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.storage.DiskInfo;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.deviceinfo.StorageCategoryFragment;
import com.android.settings.deviceinfo.VolumeOptionMenuController;
import com.android.settings.deviceinfo.storage.AutomaticStorageManagementSwitchPreferenceController;
import com.android.settings.deviceinfo.storage.DiskInitFragment;
import com.android.settings.deviceinfo.storage.StorageCacheHelper;
import com.android.settings.deviceinfo.storage.StorageEntry;
import com.android.settings.deviceinfo.storage.StorageSelectionPreferenceController;
import com.android.settings.deviceinfo.storage.StorageUsageProgressBarPreferenceController;
import com.android.settings.deviceinfo.storage.StorageUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProfileSelectStorageFragment extends ProfileSelectFragment {
    public Fragment[] mFragments;
    public boolean mIsLoadedFromCache;
    public VolumeOptionMenuController mOptionMenuController;
    public StorageEntry mSelectedStorageEntry;
    public StorageCacheHelper mStorageCacheHelper;
    public final List mStorageEntries = new ArrayList();
    public final AnonymousClass1 mStorageEventListener =
            new StorageEventListener() { // from class:
                                         // com.android.settings.dashboard.profileselector.ProfileSelectStorageFragment.1
                public final void onDiskDestroyed(DiskInfo diskInfo) {
                    StorageEntry storageEntry = new StorageEntry(diskInfo);
                    if (((ArrayList) ProfileSelectStorageFragment.this.mStorageEntries)
                            .remove(storageEntry)) {
                        if (ProfileSelectStorageFragment.this.mSelectedStorageEntry.equals(
                                storageEntry)) {
                            ProfileSelectStorageFragment profileSelectStorageFragment =
                                    ProfileSelectStorageFragment.this;
                            profileSelectStorageFragment.mSelectedStorageEntry =
                                    StorageEntry.getDefaultInternalStorageEntry(
                                            profileSelectStorageFragment.getContext());
                        }
                        ProfileSelectStorageFragment.this.refreshUi$3();
                    }
                }

                public final void onDiskScanned(DiskInfo diskInfo, int i) {
                    if (StorageUtils.isDiskUnsupported(diskInfo)) {
                        StorageEntry storageEntry = new StorageEntry(diskInfo);
                        if (((ArrayList) ProfileSelectStorageFragment.this.mStorageEntries)
                                .contains(storageEntry)) {
                            return;
                        }
                        ((ArrayList) ProfileSelectStorageFragment.this.mStorageEntries)
                                .add(storageEntry);
                        ProfileSelectStorageFragment.this.refreshUi$3();
                    }
                }

                public final void onVolumeForgotten(String str) {
                    StorageEntry storageEntry = new StorageEntry(new VolumeRecord(0, str));
                    if (((ArrayList) ProfileSelectStorageFragment.this.mStorageEntries)
                            .remove(storageEntry)) {
                        if (ProfileSelectStorageFragment.this.mSelectedStorageEntry.equals(
                                storageEntry)) {
                            ProfileSelectStorageFragment profileSelectStorageFragment =
                                    ProfileSelectStorageFragment.this;
                            profileSelectStorageFragment.mSelectedStorageEntry =
                                    StorageEntry.getDefaultInternalStorageEntry(
                                            profileSelectStorageFragment.getContext());
                        }
                        ProfileSelectStorageFragment.this.refreshUi$3();
                    }
                }

                public final void onVolumeRecordChanged(VolumeRecord volumeRecord) {
                    if (StorageUtils.isVolumeRecordMissed(
                            ProfileSelectStorageFragment.this.mStorageManager, volumeRecord)) {
                        StorageEntry storageEntry = new StorageEntry(volumeRecord);
                        if (((ArrayList) ProfileSelectStorageFragment.this.mStorageEntries)
                                .contains(storageEntry)) {
                            return;
                        }
                        ((ArrayList) ProfileSelectStorageFragment.this.mStorageEntries)
                                .add(storageEntry);
                        ProfileSelectStorageFragment.this.refreshUi$3();
                        return;
                    }
                    VolumeInfo findVolumeByUuid =
                            ProfileSelectStorageFragment.this.mStorageManager.findVolumeByUuid(
                                    volumeRecord.getFsUuid());
                    if (findVolumeByUuid == null) {
                        return;
                    }
                    if (((ArrayList) ProfileSelectStorageFragment.this.mStorageEntries)
                            .removeIf(
                                    new ProfileSelectStorageFragment$1$$ExternalSyntheticLambda0(
                                            volumeRecord))) {
                        ((ArrayList) ProfileSelectStorageFragment.this.mStorageEntries)
                                .add(
                                        new StorageEntry(
                                                ProfileSelectStorageFragment.this.getContext(),
                                                findVolumeByUuid));
                        ProfileSelectStorageFragment.this.refreshUi$3();
                    }
                }

                public final void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
                    int type = volumeInfo.getType();
                    if (type == 0 || type == 1 || type == 5) {
                        StorageEntry storageEntry =
                                new StorageEntry(
                                        ProfileSelectStorageFragment.this.getContext(), volumeInfo);
                        int state = volumeInfo.getState();
                        if (state == 0
                                || state == 2
                                || state == 3
                                || state == 5
                                || state == 6
                                || ((state == 7 || state == 8)
                                        && ((ArrayList)
                                                        ProfileSelectStorageFragment.this
                                                                .mStorageEntries)
                                                .remove(storageEntry))) {
                            ((ArrayList) ProfileSelectStorageFragment.this.mStorageEntries)
                                    .removeIf(
                                            new ProfileSelectStorageFragment$1$$ExternalSyntheticLambda0(
                                                    storageEntry));
                            if (state != 7 && state != 8) {
                                ((ArrayList) ProfileSelectStorageFragment.this.mStorageEntries)
                                        .add(storageEntry);
                            }
                            if (storageEntry.equals(
                                    ProfileSelectStorageFragment.this.mSelectedStorageEntry)) {
                                ProfileSelectStorageFragment.this.mSelectedStorageEntry =
                                        storageEntry;
                            }
                            ProfileSelectStorageFragment.this.refreshUi$3();
                        }
                    }
                }
            };
    public StorageManager mStorageManager;
    public StorageSelectionPreferenceController mStorageSelectionController;
    public StorageUsageProgressBarPreferenceController mStorageUsageProgressBarController;

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment
    public final Fragment[] getFragments() {
        Fragment[] fragmentArr = this.mFragments;
        if (fragmentArr != null) {
            return fragmentArr;
        }
        Fragment[] fragments =
                ProfileSelectFragment.getFragments(
                        getContext(),
                        null,
                        new ProfileSelectStorageFragment$$ExternalSyntheticLambda1(),
                        new ProfileSelectStorageFragment$$ExternalSyntheticLambda1(),
                        new ProfileSelectStorageFragment$$ExternalSyntheticLambda1());
        this.mFragments = fragments;
        return fragments;
    }

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment,
              // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ProfileSelStorageFrag";
    }

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1991;
    }

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.storage_dashboard_header_fragment;
    }

    public void initializeOptionsMenu(Activity activity) {
        this.mOptionMenuController =
                new VolumeOptionMenuController(activity, this, this.mSelectedStorageEntry);
        getSettingsLifecycle().addObserver(this.mOptionMenuController);
        setHasOptionsMenu(true);
        activity.invalidateOptionsMenu();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mStorageCacheHelper = new StorageCacheHelper(getContext(), UserHandle.myUserId());
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
                                                    // com.android.settings.dashboard.profileselector.ProfileSelectStorageFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.deviceinfo.storage.StorageSelectionPreferenceController.OnItemSelectedListener
                    public final void onItemSelected(StorageEntry storageEntry) {
                        VolumeInfo volumeInfo;
                        ProfileSelectStorageFragment profileSelectStorageFragment =
                                ProfileSelectStorageFragment.this;
                        profileSelectStorageFragment.mSelectedStorageEntry = storageEntry;
                        profileSelectStorageFragment.refreshUi$3();
                        if (storageEntry.isDiskInfoUnsupported()
                                || ((volumeInfo = storageEntry.mVolumeInfo) != null
                                        && volumeInfo.getState() == 6)) {
                            DiskInitFragment.show(
                                    profileSelectStorageFragment, storageEntry.getDiskId());
                        } else if (storageEntry.isVolumeRecordMissed()) {
                            StorageUtils.launchForgetMissingVolumeRecordFragment(
                                    profileSelectStorageFragment.getContext(), storageEntry);
                        }
                    }
                });
        this.mStorageUsageProgressBarController =
                (StorageUsageProgressBarPreferenceController)
                        use(StorageUsageProgressBarPreferenceController.class);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        StorageManager storageManager =
                (StorageManager) activity.getSystemService(StorageManager.class);
        this.mStorageManager = storageManager;
        if (bundle == null) {
            Bundle arguments = getArguments();
            StringBuilder sb = Utils.sBuilder;
            VolumeInfo findVolumeById =
                    storageManager.findVolumeById(
                            arguments.getString("android.os.storage.extra.VOLUME_ID", "private"));
            if (findVolumeById == null
                    || findVolumeById.getType() != 1
                    || !findVolumeById.isMountedReadable()) {
                findVolumeById = null;
            }
            this.mSelectedStorageEntry =
                    findVolumeById == null
                            ? StorageEntry.getDefaultInternalStorageEntry(getContext())
                            : new StorageEntry(getContext(), findVolumeById);
        } else {
            this.mSelectedStorageEntry =
                    (StorageEntry) bundle.getParcelable("selected_storage_entry_key");
        }
        initializeOptionsMenu(activity);
        if (this.mStorageCacheHelper.hasCachedSizeInfo()) {
            this.mIsLoadedFromCache = true;
            ((ArrayList) this.mStorageEntries).clear();
            ((ArrayList) this.mStorageEntries)
                    .addAll(StorageUtils.getAllStorageEntries(getContext(), this.mStorageManager));
            refreshUi$3();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mStorageManager.unregisterListener(this.mStorageEventListener);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mIsLoadedFromCache) {
            this.mIsLoadedFromCache = false;
        } else {
            ((ArrayList) this.mStorageEntries).clear();
            ((ArrayList) this.mStorageEntries)
                    .addAll(StorageUtils.getAllStorageEntries(getContext(), this.mStorageManager));
            refreshUi$3();
        }
        this.mStorageManager.registerListener(this.mStorageEventListener);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("selected_storage_entry_key", this.mSelectedStorageEntry);
        super.onSaveInstanceState(bundle);
    }

    public final void refreshUi$3() {
        this.mStorageSelectionController.setStorageEntries(this.mStorageEntries);
        this.mStorageSelectionController.setSelectedStorageEntry(this.mSelectedStorageEntry);
        this.mStorageUsageProgressBarController.setSelectedStorageEntry(this.mSelectedStorageEntry);
        for (Fragment fragment : getFragments()) {
            if (!(fragment instanceof StorageCategoryFragment)) {
                throw new IllegalStateException("Wrong fragment type to refreshUi");
            }
            ((StorageCategoryFragment) fragment).refreshUi(this.mSelectedStorageEntry);
        }
        VolumeOptionMenuController volumeOptionMenuController = this.mOptionMenuController;
        volumeOptionMenuController.mStorageEntry = this.mSelectedStorageEntry;
        volumeOptionMenuController.updateOptionsMenu();
        getActivity().invalidateOptionsMenu();
    }
}
