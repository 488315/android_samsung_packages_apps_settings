package com.android.settings.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.text.format.Formatter;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.deviceinfo.storage.StorageCacheHelper;
import com.android.settingslib.deviceinfo.PrivateStorageInfo;
import com.android.settingslib.deviceinfo.StorageManagerVolumeProvider;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.configuration.DATA;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TopLevelStoragePreferenceController extends BasePreferenceController {
    private final StorageManager mStorageManager;
    private final StorageManagerVolumeProvider mStorageManagerVolumeProvider;

    public TopLevelStoragePreferenceController(Context context, String str) {
        super(context, str);
        StorageManager storageManager =
                (StorageManager) this.mContext.getSystemService(StorageManager.class);
        this.mStorageManager = storageManager;
        this.mStorageManagerVolumeProvider = new StorageManagerVolumeProvider(storageManager);
    }

    private String getSummary(long j, long j2) {
        return this.mContext.getString(
                R.string.storage_summary,
                j2 == 0
                        ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN
                        : NumberFormat.getPercentInstance().format(j / j2),
                Formatter.formatFileSize(this.mContext, j2 - j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshSummaryThread$0(
            Preference preference, long j, PrivateStorageInfo privateStorageInfo) {
        preference.setSummary(getSummary(j, privateStorageInfo.totalBytes));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$refreshSummaryThread$1(
            StorageCacheHelper storageCacheHelper, final Preference preference) {
        final PrivateStorageInfo privateStorageInfo =
                PrivateStorageInfo.getPrivateStorageInfo(getStorageManagerVolumeProvider());
        final long j = privateStorageInfo.totalBytes - privateStorageInfo.freeBytes;
        storageCacheHelper.mSharedPreferences.edit().putLong("used_size_key", j).apply();
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.deviceinfo.TopLevelStoragePreferenceController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TopLevelStoragePreferenceController.this.lambda$refreshSummaryThread$0(
                                preference, j, privateStorageInfo);
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    public StorageManagerVolumeProvider getStorageManagerVolumeProvider() {
        return this.mStorageManagerVolumeProvider;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void refreshSummary(Preference preference) {
        if (preference == null) {
            return;
        }
        refreshSummaryThread(preference);
    }

    public Future refreshSummaryThread(final Preference preference) {
        StringBuilder sb = Utils.sBuilder;
        final StorageCacheHelper storageCacheHelper =
                new StorageCacheHelper(this.mContext, UserHandle.myUserId());
        long j = storageCacheHelper.mSharedPreferences.getLong("used_size_key", 0L);
        long j2 = storageCacheHelper.retrieveCachedSize().totalSize;
        if (j != 0 && j2 != 0) {
            preference.setSummary(getSummary(j, j2));
        }
        return ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.deviceinfo.TopLevelStoragePreferenceController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TopLevelStoragePreferenceController.this.lambda$refreshSummaryThread$1(
                                storageCacheHelper, preference);
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
