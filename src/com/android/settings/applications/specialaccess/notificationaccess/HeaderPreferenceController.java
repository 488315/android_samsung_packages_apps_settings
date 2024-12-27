package com.android.settings.applications.specialaccess.notificationaccess;

import android.companion.ICompanionDeviceManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.IconDrawableFactory;
import android.util.Log;

import androidx.lifecycle.LifecycleObserver;
import androidx.preference.PreferenceScreen;

import com.android.internal.util.CollectionUtils;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.NotificationBackend$$ExternalSyntheticLambda0;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class HeaderPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin, LifecycleObserver {
    private LocalBluetoothManager mBm;
    private ICompanionDeviceManager mCdm;
    private ComponentName mCn;
    private DashboardFragment mFragment;
    private EntityHeaderController mHeaderController;
    private PackageInfo mPackageInfo;
    private PackageManager mPm;
    private CharSequence mServiceName;
    private int mUserId;

    public HeaderPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        String sb;
        super.displayPreference(preferenceScreen);
        if (this.mFragment == null) {
            return;
        }
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(
                        this.mFragment.getActivity(),
                        this.mFragment,
                        ((LayoutPreference) preferenceScreen.findPreference(getPreferenceKey()))
                                .mRootView.findViewById(R.id.entity_header));
        this.mHeaderController = entityHeaderController;
        entityHeaderController.setIcon(
                IconDrawableFactory.newInstance(this.mFragment.getActivity())
                        .getBadgedIcon(this.mPackageInfo.applicationInfo));
        entityHeaderController.mLabel = this.mPackageInfo.applicationInfo.loadLabel(this.mPm);
        entityHeaderController.mSummary = this.mServiceName;
        new NotificationBackend();
        ICompanionDeviceManager iCompanionDeviceManager = this.mCdm;
        LocalBluetoothManager localBluetoothManager = this.mBm;
        String packageName = this.mCn.getPackageName();
        int i = this.mUserId;
        if (iCompanionDeviceManager == null) {
            sb = ApnSettings.MVNO_NONE;
        } else {
            StringBuilder sb2 = new StringBuilder();
            try {
                List<String> mapNotNull =
                        CollectionUtils.mapNotNull(
                                iCompanionDeviceManager.getAssociations(packageName, i),
                                new NotificationBackend$$ExternalSyntheticLambda0());
                if (mapNotNull != null) {
                    boolean z = false;
                    for (String str : mapNotNull) {
                        Iterator it =
                                ((ArrayList)
                                                localBluetoothManager.mCachedDeviceManager
                                                        .getCachedDevicesCopy())
                                        .iterator();
                        while (it.hasNext()) {
                            CachedBluetoothDevice cachedBluetoothDevice =
                                    (CachedBluetoothDevice) it.next();
                            if (Objects.equals(str, cachedBluetoothDevice.mDevice.getAddress())) {
                                if (z) {
                                    sb2.append(", ");
                                } else {
                                    z = true;
                                }
                                sb2.append(cachedBluetoothDevice.getName());
                            }
                        }
                    }
                }
            } catch (RemoteException e) {
                Log.w("NotificationBackend", "Error calling CDM", e);
            }
            sb = sb2.toString();
        }
        entityHeaderController.mSecondSummary = sb;
        entityHeaderController.mIsInstantApp =
                AppUtils.isInstant(this.mPackageInfo.applicationInfo);
        PackageInfo packageInfo = this.mPackageInfo;
        entityHeaderController.mPackageName = packageInfo.packageName;
        entityHeaderController.mUid = packageInfo.applicationInfo.uid;
        entityHeaderController.mHasAppInfoLink = true;
        entityHeaderController.mAction1 = 0;
        entityHeaderController.mAction2 = 0;
        entityHeaderController
                .done(this.mContext)
                .mRootView
                .findViewById(R.id.entity_header)
                .setVisibility(0);
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

    public HeaderPreferenceController setBluetoothManager(
            LocalBluetoothManager localBluetoothManager) {
        this.mBm = localBluetoothManager;
        return this;
    }

    public HeaderPreferenceController setCdm(ICompanionDeviceManager iCompanionDeviceManager) {
        this.mCdm = iCompanionDeviceManager;
        return this;
    }

    public HeaderPreferenceController setCn(ComponentName componentName) {
        this.mCn = componentName;
        return this;
    }

    public HeaderPreferenceController setFragment(DashboardFragment dashboardFragment) {
        this.mFragment = dashboardFragment;
        return this;
    }

    public HeaderPreferenceController setPackageInfo(PackageInfo packageInfo) {
        this.mPackageInfo = packageInfo;
        return this;
    }

    public HeaderPreferenceController setPm(PackageManager packageManager) {
        this.mPm = packageManager;
        return this;
    }

    public HeaderPreferenceController setServiceName(CharSequence charSequence) {
        this.mServiceName = charSequence;
        return this;
    }

    public HeaderPreferenceController setUserId(int i) {
        this.mUserId = i;
        return this;
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
