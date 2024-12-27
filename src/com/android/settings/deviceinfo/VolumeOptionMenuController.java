package com.android.settings.deviceinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserManager;
import android.os.storage.VolumeInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.deviceinfo.storage.StorageEntry;
import com.android.settings.deviceinfo.storage.StorageRenameFragment;
import com.android.settings.deviceinfo.storage.StorageUtils;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreateOptionsMenu;
import com.android.settingslib.core.lifecycle.events.OnOptionsItemSelected;
import com.android.settingslib.core.lifecycle.events.OnPrepareOptionsMenu;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VolumeOptionMenuController
        implements LifecycleObserver,
                OnCreateOptionsMenu,
                OnPrepareOptionsMenu,
                OnOptionsItemSelected {
    public final Context mContext;
    MenuItem mForget;
    MenuItem mFormat;
    MenuItem mFormatAsInternal;
    MenuItem mFormatAsPortable;
    public final Fragment mFragment;
    MenuItem mFree;
    MenuItem mMigrate;
    MenuItem mMount;
    public final PackageManager mPackageManager;
    MenuItem mRename;
    public StorageEntry mStorageEntry;
    MenuItem mUnmount;

    public VolumeOptionMenuController(
            Context context, Fragment fragment, StorageEntry storageEntry) {
        this.mContext = context;
        this.mFragment = fragment;
        this.mPackageManager = context.getPackageManager();
        this.mStorageEntry = storageEntry;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreateOptionsMenu
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.storage_volume, menu);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnOptionsItemSelected
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        Fragment fragment = this.mFragment;
        if (!fragment.isAdded()) {
            return false;
        }
        int itemId = menuItem.getItemId();
        if (itemId == R.id.storage_mount) {
            VolumeInfo volumeInfo = this.mStorageEntry.mVolumeInfo;
            if (volumeInfo == null || volumeInfo.getState() != 0) {
                return false;
            }
            new StorageUtils.MountTask(fragment.getActivity(), this.mStorageEntry.mVolumeInfo)
                    .execute(new Void[0]);
            return true;
        }
        if (itemId == R.id.storage_unmount) {
            if (this.mStorageEntry.isMounted()) {
                if (this.mStorageEntry.isPublic()) {
                    new StorageUtils.MountTask(
                                    (Context) fragment.getActivity(),
                                    this.mStorageEntry.mVolumeInfo)
                            .execute(new Void[0]);
                    return true;
                }
                if (this.mStorageEntry.isPrivate()
                        && !this.mStorageEntry.isDefaultInternalStorage()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(
                            "android.os.storage.extra.VOLUME_ID", this.mStorageEntry.getId());
                    SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
                    String canonicalName = PrivateVolumeUnmount.class.getCanonicalName();
                    SubSettingLauncher.LaunchRequest launchRequest =
                            subSettingLauncher.mLaunchRequest;
                    launchRequest.mDestinationName = canonicalName;
                    subSettingLauncher.setTitleRes(R.string.storage_menu_unmount, null);
                    launchRequest.mSourceMetricsCategory = 42;
                    launchRequest.mArguments = bundle;
                    subSettingLauncher.launch();
                    return true;
                }
            }
            return false;
        }
        if (itemId == R.id.storage_rename) {
            if ((!this.mStorageEntry.isPrivate() || this.mStorageEntry.isDefaultInternalStorage())
                    && !this.mStorageEntry.isPublic()) {
                return false;
            }
            VolumeInfo volumeInfo2 = this.mStorageEntry.mVolumeInfo;
            StorageRenameFragment storageRenameFragment = new StorageRenameFragment();
            storageRenameFragment.setTargetFragment(fragment, 0);
            Bundle bundle2 = new Bundle();
            bundle2.putString("android.os.storage.extra.FS_UUID", volumeInfo2.getFsUuid());
            storageRenameFragment.setArguments(bundle2);
            storageRenameFragment.show(fragment.getFragmentManager(), "rename");
            return true;
        }
        if (itemId == R.id.storage_format) {
            if (!this.mStorageEntry.isDiskInfoUnsupported() && !this.mStorageEntry.isPublic()) {
                return false;
            }
            FragmentActivity activity = fragment.getActivity();
            String diskId = this.mStorageEntry.getDiskId();
            if (activity instanceof StorageWizardInit) {
                StorageWizardFormatConfirm.mCallerActivity = (StorageWizardInit) activity;
            } else {
                StorageWizardFormatConfirm.mCallerActivity = null;
            }
            StorageWizardFormatConfirm.show(activity, diskId, false);
            return true;
        }
        if (itemId == R.id.storage_format_as_portable) {
            if (this.mStorageEntry.isPrivate()) {
                if (UserManager.get(this.mContext).isAdminUser()
                        && !ActivityManager.isUserAMonkey()) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putString(
                            "android.os.storage.extra.VOLUME_ID", this.mStorageEntry.getId());
                    SubSettingLauncher subSettingLauncher2 = new SubSettingLauncher(this.mContext);
                    String canonicalName2 = PrivateVolumeFormat.class.getCanonicalName();
                    SubSettingLauncher.LaunchRequest launchRequest2 =
                            subSettingLauncher2.mLaunchRequest;
                    launchRequest2.mDestinationName = canonicalName2;
                    subSettingLauncher2.setTitleRes(R.string.storage_menu_format, null);
                    launchRequest2.mSourceMetricsCategory = 42;
                    launchRequest2.mArguments = bundle3;
                    subSettingLauncher2.launch();
                    return true;
                }
                Toast.makeText(fragment.getActivity(), R.string.storage_wizard_guest, 1).show();
                fragment.getActivity().finish();
            }
            return false;
        }
        if (itemId == R.id.storage_format_as_internal) {
            if (!this.mStorageEntry.isPublic()) {
                return false;
            }
            Intent intent = new Intent(fragment.getActivity(), (Class<?>) StorageWizardInit.class);
            intent.putExtra("android.os.storage.extra.VOLUME_ID", this.mStorageEntry.getId());
            this.mContext.startActivity(intent);
            return true;
        }
        if (itemId != R.id.storage_migrate) {
            if (itemId != R.id.storage_forget || !this.mStorageEntry.isVolumeRecordMissed()) {
                return false;
            }
            StorageUtils.launchForgetMissingVolumeRecordFragment(this.mContext, this.mStorageEntry);
            return true;
        }
        if (!this.mStorageEntry.isPrivate()) {
            return false;
        }
        Intent intent2 = new Intent(this.mContext, (Class<?>) StorageWizardMigrateConfirm.class);
        intent2.putExtra("android.os.storage.extra.VOLUME_ID", this.mStorageEntry.getId());
        this.mContext.startActivity(intent2);
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPrepareOptionsMenu
    public final void onPrepareOptionsMenu(Menu menu) {
        this.mRename = menu.findItem(R.id.storage_rename);
        this.mMount = menu.findItem(R.id.storage_mount);
        this.mUnmount = menu.findItem(R.id.storage_unmount);
        this.mFormat = menu.findItem(R.id.storage_format);
        this.mFormatAsPortable = menu.findItem(R.id.storage_format_as_portable);
        this.mFormatAsInternal = menu.findItem(R.id.storage_format_as_internal);
        this.mMigrate = menu.findItem(R.id.storage_migrate);
        this.mFree = menu.findItem(R.id.storage_free);
        this.mForget = menu.findItem(R.id.storage_forget);
        updateOptionsMenu();
    }

    public final void updateOptionsMenu() {
        MenuItem menuItem = this.mRename;
        if (menuItem == null
                || this.mMount == null
                || this.mUnmount == null
                || this.mFormat == null
                || this.mFormatAsPortable == null
                || this.mFormatAsInternal == null
                || this.mMigrate == null
                || this.mFree == null
                || this.mForget == null) {
            Log.d("VolumeOptionMenuController", "Menu items are not available");
            return;
        }
        boolean z = false;
        menuItem.setVisible(false);
        this.mMount.setVisible(false);
        this.mUnmount.setVisible(false);
        this.mFormat.setVisible(false);
        this.mFormatAsPortable.setVisible(false);
        this.mFormatAsInternal.setVisible(false);
        this.mMigrate.setVisible(false);
        this.mFree.setVisible(false);
        this.mForget.setVisible(false);
        if (this.mStorageEntry.isDiskInfoUnsupported()) {
            this.mFormat.setVisible(true);
            return;
        }
        if (this.mStorageEntry.isVolumeRecordMissed()) {
            this.mForget.setVisible(true);
            return;
        }
        VolumeInfo volumeInfo = this.mStorageEntry.mVolumeInfo;
        if (volumeInfo != null && volumeInfo.getState() == 0) {
            this.mMount.setVisible(true);
            return;
        }
        if (this.mStorageEntry.isMounted()) {
            if (!this.mStorageEntry.isPrivate()) {
                if (this.mStorageEntry.isPublic()) {
                    this.mRename.setVisible(true);
                    this.mUnmount.setVisible(true);
                    this.mFormatAsInternal.setVisible(true);
                    return;
                }
                return;
            }
            if (!this.mStorageEntry.isDefaultInternalStorage()) {
                this.mRename.setVisible(true);
                this.mFormatAsPortable.setVisible(true);
            }
            VolumeInfo primaryStorageCurrentVolume =
                    this.mPackageManager.getPrimaryStorageCurrentVolume();
            VolumeInfo volumeInfo2 = this.mStorageEntry.mVolumeInfo;
            MenuItem menuItem2 = this.mMigrate;
            if (primaryStorageCurrentVolume != null
                    && primaryStorageCurrentVolume.getType() == 1
                    && !Objects.equals(volumeInfo2, primaryStorageCurrentVolume)
                    && primaryStorageCurrentVolume.isMountedWritable()) {
                z = true;
            }
            menuItem2.setVisible(z);
        }
    }
}
