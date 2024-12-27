package com.android.settings.deviceinfo.storage;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.os.UserManager;
import android.util.SparseArray;

import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.deviceinfo.StorageItemPreference;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NonCurrentUserController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public boolean mIsVisible;
    public PreferenceGroup mPreferenceGroup;
    public final int[] mProfiles;
    public final StorageCacheHelper mStorageCacheHelper;
    public StorageItemPreference mStoragePreference;
    public long mTotalSizeBytes;
    public final UserInfo mUser;
    public Drawable mUserIcon;

    public NonCurrentUserController(Context context, UserInfo userInfo, int[] iArr) {
        super(context);
        this.mUser = userInfo;
        this.mStorageCacheHelper = new StorageCacheHelper(context, userInfo.id);
        this.mProfiles = iArr;
    }

    public static List getNonCurrentUserControllers(Context context, UserManager userManager) {
        int currentUser = ActivityManager.getCurrentUser();
        ArrayList arrayList = new ArrayList();
        for (UserInfo userInfo : userManager.getUsers()) {
            if (userInfo.id != currentUser && userInfo.isFull()) {
                arrayList.add(
                        new NonCurrentUserController(
                                context, userInfo, userManager.getProfileIds(userInfo.id, false)));
            }
        }
        return arrayList;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        StorageItemPreference storageItemPreference;
        if (this.mStoragePreference == null) {
            this.mStoragePreference = new StorageItemPreference(preferenceScreen.getContext());
            this.mPreferenceGroup =
                    (PreferenceGroup) preferenceScreen.findPreference("pref_non_current_users");
            this.mStoragePreference.setTitle(this.mUser.name);
            this.mStoragePreference.setKey("pref_user_" + this.mUser.id);
            long j = this.mStorageCacheHelper.mSharedPreferences.getLong("used_size_key", 0L);
            StorageItemPreference storageItemPreference2 = this.mStoragePreference;
            if (storageItemPreference2 != null) {
                storageItemPreference2.setStorageSize(j, this.mTotalSizeBytes, false);
            }
            this.mPreferenceGroup.setVisible(this.mIsVisible);
            this.mPreferenceGroup.addPreference(this.mStoragePreference);
            Drawable drawable = this.mUserIcon;
            if (drawable == null || (storageItemPreference = this.mStoragePreference) == null) {
                return;
            }
            storageItemPreference.setIcon(drawable);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        StorageItemPreference storageItemPreference = this.mStoragePreference;
        if (storageItemPreference != null) {
            return storageItemPreference.getKey();
        }
        return null;
    }

    public final void handleResult(SparseArray sparseArray) {
        StorageCacheHelper storageCacheHelper = this.mStorageCacheHelper;
        long j = 0;
        if (sparseArray == null) {
            long j2 = storageCacheHelper.mSharedPreferences.getLong("used_size_key", 0L);
            StorageItemPreference storageItemPreference = this.mStoragePreference;
            if (storageItemPreference != null) {
                storageItemPreference.setStorageSize(j2, this.mTotalSizeBytes, false);
                return;
            }
            return;
        }
        if (((StorageAsyncLoader.StorageResult) sparseArray.get(this.mUser.id)) != null) {
            for (int i : this.mProfiles) {
                j +=
                        ((StorageAsyncLoader.StorageResult) sparseArray.get(i))
                                .externalStats
                                .totalBytes;
            }
            StorageItemPreference storageItemPreference2 = this.mStoragePreference;
            if (storageItemPreference2 != null) {
                storageItemPreference2.setStorageSize(j, this.mTotalSizeBytes, true);
            }
            storageCacheHelper.mSharedPreferences.edit().putLong("used_size_key", j).apply();
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public NonCurrentUserController(Context context, UserInfo userInfo) {
        super(context);
        this.mUser = userInfo;
        this.mStorageCacheHelper = new StorageCacheHelper(context, userInfo.id);
        this.mProfiles = new int[] {userInfo.id};
    }
}
