package com.android.settings.deviceinfo.storage;

import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.UsageProgressBarPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageUsageProgressBarPreferenceController extends BasePreferenceController {
    private static final String TAG = "StorageProgressCtrl";
    boolean mIsUpdateStateFromSelectedStorageEntry;
    private StorageCacheHelper mStorageCacheHelper;
    private StorageEntry mStorageEntry;
    private final StorageStatsManager mStorageStatsManager;
    long mTotalBytes;
    private UsageProgressBarPreference mUsageProgressBarPreference;
    long mUsedBytes;

    public StorageUsageProgressBarPreferenceController(Context context, String str) {
        super(context, str);
        this.mStorageStatsManager =
                (StorageStatsManager) context.getSystemService(StorageStatsManager.class);
        this.mStorageCacheHelper = new StorageCacheHelper(context, UserHandle.myUserId());
    }

    private void getStorageStatsAndUpdateUi() {
        StorageEntry storageEntry = this.mStorageEntry;
        if (storageEntry != null && storageEntry.isMounted() && this.mStorageEntry.isPrivate()) {
            StorageCacheHelper.StorageCache retrieveCachedSize =
                    this.mStorageCacheHelper.retrieveCachedSize();
            this.mTotalBytes = retrieveCachedSize.totalSize;
            this.mUsedBytes = retrieveCachedSize.totalUsedSize;
            this.mIsUpdateStateFromSelectedStorageEntry = true;
            updateState(this.mUsageProgressBarPreference);
        }
        ThreadUtils.postOnBackgroundThread(
                new StorageUsageProgressBarPreferenceController$$ExternalSyntheticLambda0(this, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getStorageStatsAndUpdateUi$0() {
        updateState(this.mUsageProgressBarPreference);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$getStorageStatsAndUpdateUi$1() {
        StorageEntry storageEntry;
        try {
            storageEntry = this.mStorageEntry;
        } catch (IOException unused) {
            this.mTotalBytes = 0L;
            this.mUsedBytes = 0L;
        }
        if (storageEntry == null || !storageEntry.isMounted()) {
            throw new IOException();
        }
        if (this.mStorageEntry.isPrivate()) {
            long totalBytes =
                    this.mStorageStatsManager.getTotalBytes(this.mStorageEntry.getFsUuid());
            this.mTotalBytes = totalBytes;
            this.mUsedBytes =
                    totalBytes
                            - this.mStorageStatsManager.getFreeBytes(
                                    this.mStorageEntry.getFsUuid());
        } else {
            VolumeInfo volumeInfo = this.mStorageEntry.mVolumeInfo;
            File path = volumeInfo == null ? null : volumeInfo.getPath();
            if (path == null) {
                Log.d(TAG, "Mounted public storage has null root path: " + this.mStorageEntry);
                throw new IOException();
            }
            long totalSpace = path.getTotalSpace();
            this.mTotalBytes = totalSpace;
            this.mUsedBytes = totalSpace - path.getFreeSpace();
        }
        if (this.mUsageProgressBarPreference == null) {
            return;
        }
        this.mIsUpdateStateFromSelectedStorageEntry = true;
        ThreadUtils.postOnMainThread(
                new StorageUsageProgressBarPreferenceController$$ExternalSyntheticLambda0(this, 0));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mUsageProgressBarPreference =
                (UsageProgressBarPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setSelectedStorageEntry(StorageEntry storageEntry) {
        this.mStorageEntry = storageEntry;
        getStorageStatsAndUpdateUi();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (this.mIsUpdateStateFromSelectedStorageEntry) {
            this.mIsUpdateStateFromSelectedStorageEntry = false;
            this.mUsageProgressBarPreference.setUsageSummary(
                    StorageUtils.getStorageSummary(
                            R.string.storage_usage_summary, this.mUsedBytes, this.mContext));
            UsageProgressBarPreference usageProgressBarPreference =
                    this.mUsageProgressBarPreference;
            String storageSummary =
                    StorageUtils.getStorageSummary(
                            R.string.storage_total_summary, this.mTotalBytes, this.mContext);
            if (!TextUtils.equals(usageProgressBarPreference.mTotalSummary, storageSummary)) {
                usageProgressBarPreference.mTotalSummary = storageSummary;
                usageProgressBarPreference.notifyChanged();
            }
            this.mUsageProgressBarPreference.setPercent(this.mUsedBytes, this.mTotalBytes);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
