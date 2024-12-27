package com.android.settings.applications.specialaccess.deviceadmin;

import android.app.AppGlobals;
import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.applications.specialaccess.SecDeviceAdminAdd;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DeviceAdminListPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final IntentFilter FILTER;
    private static final String KEY_DEVICE_ADMIN_FOOTER = "device_admin_footer";
    private static final String KNOXGUARD_CLIENT_PKG = "com.samsung.android.kgclient";
    private static final String TAG = "DeviceAdminListPrefCtrl";
    private final ArrayList<DeviceAdminListItem> mAdmins;
    private final BroadcastReceiver mBroadcastReceiver;
    private final DevicePolicyManager mDPM;
    private boolean mEnableLaunched;
    private boolean mFirstLaunch;
    private FooterPreference mFooterPreference;
    private final IPackageManager mIPackageManager;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private final PackageManager mPackageManager;
    private PreferenceGroup mPreferenceGroup;
    private final UserManager mUm;

    static {
        IntentFilter intentFilter = new IntentFilter();
        FILTER = intentFilter;
        intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
    }

    public DeviceAdminListPreferenceController(Context context, String str) {
        super(context, str);
        this.mEnableLaunched = true;
        this.mAdmins = new ArrayList<>();
        this.mBroadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.applications.specialaccess.deviceadmin.DeviceAdminListPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (TextUtils.equals(
                                "android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED",
                                intent.getAction())) {
                            DeviceAdminListPreferenceController.this.updateList();
                        }
                    }
                };
        this.mFirstLaunch = true;
        this.mDPM = (DevicePolicyManager) context.getSystemService("device_policy");
        this.mUm = (UserManager) context.getSystemService("user");
        this.mPackageManager = this.mContext.getPackageManager();
        this.mIPackageManager = AppGlobals.getPackageManager();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    private void addActiveAdminsForProfile(List<ComponentName> list, int i) {
        if (list == null) {
            return;
        }
        for (ComponentName componentName : list) {
            if (!componentName.getPackageName().equals("com.samsung.android.knox.containercore")
                    && !componentName.getPackageName().equals("com.samsung.android.appseparation")
                    && !KNOXGUARD_CLIENT_PKG.equals(componentName.getPackageName())) {
                try {
                    DeviceAdminInfo createDeviceAdminInfo =
                            createDeviceAdminInfo(
                                    this.mContext,
                                    this.mIPackageManager.getReceiverInfo(
                                            componentName, 819328L, i));
                    if (createDeviceAdminInfo != null) {
                        this.mAdmins.add(
                                new DeviceAdminListItem(this.mContext, createDeviceAdminInfo));
                    }
                } catch (RemoteException unused) {
                    Log.w(TAG, "Unable to load component: " + componentName);
                }
            }
        }
    }

    private void addDeviceAdminBroadcastReceiversForProfile(
            Collection<ComponentName> collection, int i) {
        List<ResolveInfo> queryBroadcastReceiversAsUser =
                this.mPackageManager.queryBroadcastReceiversAsUser(
                        new Intent("android.app.action.DEVICE_ADMIN_ENABLED"), 32896, i);
        if (queryBroadcastReceiversAsUser == null) {
            return;
        }
        for (ResolveInfo resolveInfo : queryBroadcastReceiversAsUser) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            ComponentName componentName =
                    new ComponentName(activityInfo.packageName, activityInfo.name);
            if (collection == null || !collection.contains(componentName)) {
                DeviceAdminInfo createDeviceAdminInfo =
                        createDeviceAdminInfo(this.mContext, resolveInfo.activityInfo);
                if (createDeviceAdminInfo != null
                        && createDeviceAdminInfo.isVisible()
                        && createDeviceAdminInfo.getActivityInfo().applicationInfo.isInternal()
                        && !KNOXGUARD_CLIENT_PKG.equals(resolveInfo.activityInfo.packageName)
                        && !"com.samsung.knox.securefolder"
                                .equals(resolveInfo.activityInfo.packageName)
                        && !"com.samsung.android.appseparation"
                                .equals(resolveInfo.activityInfo.packageName)) {
                    this.mAdmins.add(new DeviceAdminListItem(this.mContext, createDeviceAdminInfo));
                }
            }
        }
    }

    private void bindPreference(
            final DeviceAdminListItem deviceAdminListItem,
            RestrictedSwitchPreference restrictedSwitchPreference) {
        restrictedSwitchPreference.setKey(deviceAdminListItem.mKey);
        restrictedSwitchPreference.setTitle(deviceAdminListItem.mName);
        restrictedSwitchPreference.setIconSpaceReserved(true);
        restrictedSwitchPreference.setIcon(deviceAdminListItem.mIcon);
        restrictedSwitchPreference.mIconSize = 0;
        restrictedSwitchPreference.setChecked(
                deviceAdminListItem.mDPM.isAdminActiveAsUser(
                        deviceAdminListItem.mInfo.getComponent(),
                        DeviceAdminListItem.getUserIdFromDeviceAdminInfo(
                                deviceAdminListItem.mInfo)));
        restrictedSwitchPreference.setEnabled(
                !deviceAdminListItem.mDPM.isRemovingAdmin(
                        deviceAdminListItem.mInfo.getComponent(),
                        DeviceAdminListItem.getUserIdFromDeviceAdminInfo(
                                deviceAdminListItem.mInfo)));
        restrictedSwitchPreference.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.applications.specialaccess.deviceadmin.DeviceAdminListPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        boolean lambda$bindPreference$0;
                        lambda$bindPreference$0 =
                                DeviceAdminListPreferenceController.this.lambda$bindPreference$0(
                                        deviceAdminListItem, preference);
                        return lambda$bindPreference$0;
                    }
                });
        restrictedSwitchPreference.setOnPreferenceChangeListener(
                new DeviceAdminListPreferenceController$$ExternalSyntheticLambda1());
        restrictedSwitchPreference.setSingleLineTitle(true);
        restrictedSwitchPreference.mHelper.checkEcmRestrictionAndSetDisabled(
                "android.permission.BIND_DEVICE_ADMIN", deviceAdminListItem.mInfo.getPackageName());
    }

    private static DeviceAdminInfo createDeviceAdminInfo(
            Context context, ActivityInfo activityInfo) {
        try {
            return new DeviceAdminInfo(context, activityInfo);
        } catch (IOException | XmlPullParserException e) {
            Log.w(TAG, "Skipping " + activityInfo, e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lambda$bindPreference$0(
            DeviceAdminListItem deviceAdminListItem, Preference preference) {
        if (!this.mEnableLaunched) {
            return true;
        }
        this.mEnableLaunched = false;
        this.mMetricsFeatureProvider.logClickedPreference(preference, getMetricsCategory());
        deviceAdminListItem.getClass();
        UserHandle userHandle =
                new UserHandle(
                        DeviceAdminListItem.getUserIdFromDeviceAdminInfo(
                                deviceAdminListItem.mInfo));
        Context context = this.mContext;
        context.startActivityAsUser(
                new Intent(context, (Class<?>) SecDeviceAdminAdd.class)
                        .putExtra(
                                EnterpriseDeviceManager.EXTRA_DEVICE_ADMIN,
                                deviceAdminListItem.mInfo.getComponent()),
                userHandle);
        new Handler(Looper.getMainLooper())
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.android.settings.applications.specialaccess.deviceadmin.DeviceAdminListPreferenceController.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                DeviceAdminListPreferenceController.this.mEnableLaunched = true;
                            }
                        },
                        1000L);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$bindPreference$1(
            Preference preference, Object obj) {
        return false;
    }

    private void refreshData() {
        this.mAdmins.clear();
        List<UserHandle> userProfiles = this.mUm.getUserProfiles();
        int i = 0;
        while (i < userProfiles.size()) {
            if (!SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
                if (SemPersonaManager.isSecureFolderId(userProfiles.get(i).getIdentifier())) {
                    userProfiles.remove(i);
                    i--;
                }
                if (SemDualAppManager.isDualAppId(userProfiles.get(i).getIdentifier())) {
                    userProfiles.remove(i);
                    i--;
                    i++;
                } else {
                    i++;
                }
            } else if (userProfiles.get(i).getIdentifier() != UserHandle.myUserId()) {
                userProfiles.remove(i);
                i--;
                i++;
            } else {
                i++;
            }
        }
        for (UserHandle userHandle : userProfiles) {
            if (!shouldSkipProfile(userHandle)) {
                updateAvailableAdminsForProfile(userHandle.getIdentifier());
            }
        }
        Collections.sort(this.mAdmins);
    }

    private void refreshUI() {
        if (this.mPreferenceGroup == null) {
            return;
        }
        FooterPreference footerPreference = this.mFooterPreference;
        if (footerPreference != null) {
            footerPreference.setVisible(this.mAdmins.isEmpty());
        }
        ArrayMap arrayMap = new ArrayMap();
        Context context = this.mPreferenceGroup.getContext();
        int preferenceCount = this.mPreferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = this.mPreferenceGroup.getPreference(i);
            if (preference instanceof RestrictedSwitchPreference) {
                RestrictedSwitchPreference restrictedSwitchPreference =
                        (RestrictedSwitchPreference) preference;
                arrayMap.put(restrictedSwitchPreference.getKey(), restrictedSwitchPreference);
            }
        }
        Iterator<DeviceAdminListItem> it = this.mAdmins.iterator();
        while (it.hasNext()) {
            DeviceAdminListItem next = it.next();
            RestrictedSwitchPreference restrictedSwitchPreference2 =
                    (RestrictedSwitchPreference) arrayMap.remove(next.mKey);
            if (restrictedSwitchPreference2 == null) {
                restrictedSwitchPreference2 = new RestrictedSwitchPreference(context);
                this.mPreferenceGroup.addPreference(restrictedSwitchPreference2);
            }
            bindPreference(next, restrictedSwitchPreference2);
        }
        Iterator it2 = arrayMap.values().iterator();
        while (it2.hasNext()) {
            this.mPreferenceGroup.removePreference((RestrictedSwitchPreference) it2.next());
        }
    }

    private boolean shouldSkipProfile(UserHandle userHandle) {
        return Flags.allowPrivateProfile()
                && android.multiuser.Flags.handleInterleavedSettingsForPrivateSpace()
                && android.multiuser.Flags.enablePrivateSpaceFeatures()
                && this.mUm.isQuietModeEnabled(userHandle)
                && this.mUm.getUserProperties(userHandle).getShowInQuietMode() == 1;
    }

    private void updateAvailableAdminsForProfile(int i) {
        List activeAdminsAsUser = this.mDPM.getActiveAdminsAsUser(i);
        addActiveAdminsForProfile(activeAdminsAsUser, i);
        addDeviceAdminBroadcastReceiversForProfile(activeAdminsAsUser, i);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceGroup preferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreferenceGroup = preferenceGroup;
        this.mFooterPreference =
                (FooterPreference) preferenceGroup.findPreference(KEY_DEVICE_ADMIN_FOOTER);
        updateList();
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext.registerReceiverAsUser(
                this.mBroadcastReceiver, UserHandle.ALL, FILTER, null, null);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateList() {
        refreshData();
        refreshUI();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mFirstLaunch) {
            this.mFirstLaunch = false;
        } else {
            updateList();
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
