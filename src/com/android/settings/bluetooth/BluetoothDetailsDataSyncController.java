package com.android.settings.bluetooth;

import android.companion.CompanionDeviceManager;
import android.companion.datatransfer.PermissionSyncRequest;
import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;
import androidx.preference.TwoStatePreference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.hidden_from_bootclasspath.android.companion.Flags;
import com.android.settings.R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsDataSyncController extends BluetoothDetailsController
        implements Preference.OnPreferenceClickListener {

    @VisibleForTesting int mAssociationId;
    public CachedBluetoothDevice mCachedDevice;
    public CompanionDeviceManager mCompanionDeviceManager;

    @VisibleForTesting PreferenceCategory mPreferenceCategory;

    @VisibleForTesting
    public TwoStatePreference createPermSyncPreference(Context context) {
        SwitchPreferenceCompat switchPreferenceCompat = new SwitchPreferenceCompat(context);
        switchPreferenceCompat.setKey("perm_sync");
        switchPreferenceCompat.setTitle(
                context.getString(R.string.bluetooth_details_permissions_sync_title));
        switchPreferenceCompat.setSummary(
                context.getString(R.string.bluetooth_details_permissions_sync_summary));
        switchPreferenceCompat.setOnPreferenceClickListener(this);
        return switchPreferenceCompat;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "data_sync_group";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        this.mPreferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference("data_sync_group");
        refresh();
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Flags.ongoingPermSync() && this.mAssociationId != -1;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        if (!twoStatePreference.getKey().equals("perm_sync")) {
            return false;
        }
        if (twoStatePreference.isChecked()) {
            this.mCompanionDeviceManager.enablePermissionsSync(this.mAssociationId);
            return false;
        }
        this.mCompanionDeviceManager.disablePermissionsSync(this.mAssociationId);
        return false;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        PermissionSyncRequest permissionSyncRequest;
        boolean z;
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) this.mPreferenceCategory.findPreference("perm_sync");
        if (twoStatePreference == null) {
            twoStatePreference = createPermSyncPreference(this.mPreferenceCategory.getContext());
            this.mPreferenceCategory.addPreference(twoStatePreference);
        }
        int i = this.mAssociationId;
        boolean z2 = false;
        if (i == -1) {
            twoStatePreference.setVisible(false);
            return;
        }
        try {
            permissionSyncRequest = this.mCompanionDeviceManager.getPermissionSyncRequest(i);
        } catch (IllegalArgumentException unused) {
            permissionSyncRequest = null;
        }
        if (permissionSyncRequest != null) {
            z = true;
            if (permissionSyncRequest.isUserConsented()) {
                z2 = true;
            } else {
                z2 = true;
                z = false;
            }
        } else {
            z = false;
        }
        twoStatePreference.setVisible(z2);
        twoStatePreference.setChecked(z);
    }
}
