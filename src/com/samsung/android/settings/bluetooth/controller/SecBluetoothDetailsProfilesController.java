package com.samsung.android.settings.bluetooth.controller;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.provider.DeviceConfig;
import android.util.Log;
import android.view.ContextThemeWrapper;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.bluetooth.BluetoothDetailsController;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HeadsetProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.bluetooth.MapProfile;
import com.android.settingslib.bluetooth.PbapServerProfile;
import com.android.settingslib.bluetooth.SapProfile;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settingslib.bluetooth.SppProfile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothDetailsProfilesController extends BluetoothDetailsController
        implements Preference.OnPreferenceClickListener,
                LocalBluetoothProfileManager.ServiceListener,
                Preference.OnPreferenceChangeListener {
    public static final boolean DBG = Debug.semIsProductDev();
    public List mAllOfCachedDevices;
    public CachedBluetoothDevice mCachedDevice;
    public AnonymousClass1 mHandler;
    public boolean mIsLeAudioToggleEnabled;
    public boolean mIsPaused;
    public boolean mIsWorkingSwitchAnimation;
    public int mMCFFeature;
    public LocalBluetoothManager mManager;
    public boolean mNeedUiUpdate;
    public PreferenceGroup mProfileContainer;
    public Map mProfileDeviceMap;
    public boolean mProfileGroupIsRemoved;
    public LocalBluetoothProfileManager mProfileManager;
    public String mScreenId;

    public final void addAutoSwitchPreference() {
        if ((this.mMCFFeature & 2) <= 0
                || this.mCachedDevice.mDevice.semGetAutoSwitchMode() == -1) {
            return;
        }
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        SwitchPreferenceCompat switchPreferenceCompat =
                (SwitchPreferenceCompat) preferenceFragmentCompat.findPreference("AUTOSWITCH");
        if (switchPreferenceCompat != null) {
            refreshAutoSwitchPreference(switchPreferenceCompat);
            return;
        }
        Log.d(
                "SecBluetoothDetailsProfilesController",
                "createGeneralSwitchPreference :: AUTOSWITCH");
        SwitchPreferenceCompat switchPreferenceCompat2 =
                new SwitchPreferenceCompat(
                        new ContextThemeWrapper(
                                this.mProfileContainer.getContext(),
                                R.style.PreferenceThemeOverlay));
        switchPreferenceCompat2.setKey("AUTOSWITCH");
        switchPreferenceCompat2.setTitle(R.string.sec_bluetooth_detail_auto_switching);
        switchPreferenceCompat2.setPersistent();
        switchPreferenceCompat2.setOrder(999);
        switchPreferenceCompat2.setOnPreferenceChangeListener(this);
        Resources resources = preferenceFragmentCompat.getActivity().getResources();
        Drawable drawable =
                ((BluetoothDetailsController) this)
                        .mContext
                        .getResources()
                        .getDrawable(R.drawable.sec_bluetooth_2d_auto_switching);
        if (drawable != null) {
            drawable.setTint(resources.getColor(R.color.bt_device_icon_tint_color));
            switchPreferenceCompat2.setIcon(drawable);
        }
        refreshAutoSwitchPreference(switchPreferenceCompat2);
        this.mProfileContainer.addPreference(switchPreferenceCompat2);
    }

    public final SwitchPreferenceCompat createProfileSwitchPreference(
            LocalBluetoothProfile localBluetoothProfile) {
        Log.d(
                "SecBluetoothDetailsProfilesController",
                "createProfileSwitchPreference :: profile : " + localBluetoothProfile);
        SwitchPreferenceCompat switchPreferenceCompat =
                new SwitchPreferenceCompat(
                        new ContextThemeWrapper(
                                this.mProfileContainer.getContext(),
                                R.style.PreferenceThemeOverlay));
        switchPreferenceCompat.setKey(localBluetoothProfile.toString());
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        switchPreferenceCompat.setTitle(
                localBluetoothProfile.getNameResource(cachedBluetoothDevice.mDevice));
        switchPreferenceCompat.setPersistent();
        switchPreferenceCompat.setOrder(
                (localBluetoothProfile.getOrdinal() * 10) + this.mProfileContainer.getOrder());
        switchPreferenceCompat.setOnPreferenceChangeListener(this);
        switchPreferenceCompat.seslSetDividerStartOffset(
                Math.round(
                        ((BluetoothDetailsController) this)
                                        .mContext
                                        .getResources()
                                        .getDisplayMetrics()
                                        .density
                                * 60.0f));
        if (localBluetoothProfile instanceof LeAudioProfile) {
            switchPreferenceCompat.setSummary(R.string.sec_bluetooth_details_summary_leaudio);
        }
        Resources resources = this.mFragment.getActivity().getResources();
        Drawable drawable =
                localBluetoothProfile instanceof HeadsetProfile
                        ? resources.getDrawable(R.drawable.sec_bluetooth_2d_call_phone)
                        : resources.getDrawable(
                                localBluetoothProfile.getDrawableResource(
                                        cachedBluetoothDevice.mDevice.getBluetoothClass()));
        if (drawable != null) {
            drawable.setTint(resources.getColor(R.color.bt_device_icon_tint_color));
            switchPreferenceCompat.setIcon(drawable);
        }
        refreshProfileSwitchPreference(switchPreferenceCompat, localBluetoothProfile);
        return switchPreferenceCompat;
    }

    public final List getAllOfCachedBluetoothDevices() {
        ArrayList arrayList = new ArrayList();
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice == null) {
            return arrayList;
        }
        arrayList.add(cachedBluetoothDevice);
        if (cachedBluetoothDevice.mGroupId != -1) {
            Iterator it = cachedBluetoothDevice.mMemberDevices.iterator();
            while (it.hasNext()) {
                arrayList.add((CachedBluetoothDevice) it.next());
            }
        }
        return arrayList;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_profiles";
    }

    public final List getProfiles$1() {
        ArrayList arrayList = new ArrayList();
        this.mProfileDeviceMap.clear();
        List list = this.mAllOfCachedDevices;
        if (list != null && !list.isEmpty()) {
            for (CachedBluetoothDevice cachedBluetoothDevice : this.mAllOfCachedDevices) {
                Iterator it =
                        ((ArrayList) cachedBluetoothDevice.getConnectableProfiles()).iterator();
                while (it.hasNext()) {
                    LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                    if (this.mProfileDeviceMap.containsKey(localBluetoothProfile.toString())) {
                        ((List) this.mProfileDeviceMap.get(localBluetoothProfile.toString()))
                                .add(cachedBluetoothDevice);
                        Log.d(
                                "SecBluetoothDetailsProfilesController",
                                "getProfiles: "
                                        + localBluetoothProfile.toString()
                                        + " add device "
                                        + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(cachedBluetoothDevice);
                        this.mProfileDeviceMap.put(localBluetoothProfile.toString(), arrayList2);
                        arrayList.add(localBluetoothProfile);
                    }
                }
            }
            Log.d("SecBluetoothDetailsProfilesController", "getProfiles:result:" + arrayList);
        }
        return arrayList;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        this.mScreenId =
                SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                        this.mFragment, R.string.screen_device_profiles_setting);
        this.mProfileContainer =
                (PreferenceGroup) preferenceScreen.findPreference("bluetooth_profiles");
        this.mMCFFeature =
                SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_MCF_SUPPORT_CONTINUITY");
        this.mIsLeAudioToggleEnabled =
                DeviceConfig.getBoolean("settings_ui", "bt_le_audio_device_detail_enabled", true);
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        Iterator it = ((ArrayList) this.mAllOfCachedDevices).iterator();
        while (it.hasNext()) {
            ((CachedBluetoothDevice) it.next()).unregisterCallback(this);
        }
        List allOfCachedBluetoothDevices = getAllOfCachedBluetoothDevices();
        this.mAllOfCachedDevices = allOfCachedBluetoothDevices;
        Iterator it2 = ((ArrayList) allOfCachedBluetoothDevices).iterator();
        while (it2.hasNext()) {
            ((CachedBluetoothDevice) it2.next()).registerCallback(this);
        }
        refresh();
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        super.onPause();
        this.mIsPaused = true;
        Iterator it = ((ArrayList) this.mAllOfCachedDevices).iterator();
        while (it.hasNext()) {
            ((CachedBluetoothDevice) it.next()).unregisterCallback(this);
        }
        this.mProfileManager.removeServiceListener(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0060  */
    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onPreferenceChange(
            final androidx.preference.Preference r14, java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 699
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsProfilesController.onPreferenceChange(androidx.preference.Preference,"
                    + " java.lang.Object):boolean");
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        super.onResume();
        this.mIsPaused = false;
        Iterator it = this.mAllOfCachedDevices.iterator();
        while (it.hasNext()) {
            ((CachedBluetoothDevice) it.next()).registerCallback(this);
        }
        this.mProfileManager.addServiceListener(this);
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        SapProfile sapProfile;
        Preference findPreference;
        if (this.mIsWorkingSwitchAnimation) {
            this.mNeedUiUpdate = true;
            return;
        }
        getProfiles$1();
        if (this.mCachedDevice.mIsRestored) {
            addAutoSwitchPreference();
            showOrHideProfileGroup();
            Log.d(
                    "SecBluetoothDetailsProfilesController",
                    "refreshProfiles :: Device is restored. It is not support container");
            return;
        }
        Iterator it = ((ArrayList) getProfiles$1()).iterator();
        while (it.hasNext()) {
            LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
            if (!(localBluetoothProfile instanceof SppProfile)) {
                SwitchPreferenceCompat switchPreferenceCompat =
                        (SwitchPreferenceCompat)
                                this.mFragment.findPreference(localBluetoothProfile.toString());
                if (switchPreferenceCompat == null) {
                    SwitchPreferenceCompat createProfileSwitchPreference =
                            createProfileSwitchPreference(localBluetoothProfile);
                    if (!(localBluetoothProfile instanceof SapProfile)
                            && !(localBluetoothProfile instanceof PbapServerProfile)
                            && !(localBluetoothProfile instanceof MapProfile)) {
                        this.mProfileContainer.addPreference(createProfileSwitchPreference);
                    }
                } else {
                    refreshProfileSwitchPreference(switchPreferenceCompat, localBluetoothProfile);
                }
            }
        }
        int phonebookPermissionChoice = this.mCachedDevice.getPhonebookPermissionChoice();
        PbapServerProfile pbapServerProfile = this.mManager.mProfileManager.mPbapProfile;
        if (pbapServerProfile != null
                && this.mFragment.findPreference(PbapServerProfile.NAME) == null
                && phonebookPermissionChoice != 0) {
            SwitchPreferenceCompat createProfileSwitchPreference2 =
                    createProfileSwitchPreference(pbapServerProfile);
            Log.d(
                    "SecBluetoothDetailsProfilesController",
                    "Refresh profile preference : PBAP Server");
            this.mProfileContainer.addPreference(createProfileSwitchPreference2);
        }
        int messagePermissionChoice = this.mCachedDevice.getMessagePermissionChoice();
        MapProfile mapProfile = this.mManager.mProfileManager.mMapProfile;
        if (mapProfile != null
                && this.mFragment.findPreference("MAP") == null
                && messagePermissionChoice != 0) {
            SwitchPreferenceCompat createProfileSwitchPreference3 =
                    createProfileSwitchPreference(mapProfile);
            Log.d("SecBluetoothDetailsProfilesController", "Refresh profile preference : MAP");
            this.mProfileContainer.addPreference(createProfileSwitchPreference3);
        }
        int simPermissionChoice = this.mCachedDevice.getSimPermissionChoice();
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mManager.mProfileManager;
        synchronized (localBluetoothProfileManager) {
            sapProfile = localBluetoothProfileManager.mSapProfile;
        }
        if (sapProfile != null
                && this.mFragment.findPreference("SAP") == null
                && simPermissionChoice != 0) {
            SwitchPreferenceCompat createProfileSwitchPreference4 =
                    createProfileSwitchPreference(sapProfile);
            Log.d("SecBluetoothDetailsProfilesController", "Refresh profile preference : SAP");
            this.mProfileContainer.addPreference(createProfileSwitchPreference4);
        }
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        cachedBluetoothDevice.getClass();
        ArrayList arrayList = new ArrayList();
        synchronized (cachedBluetoothDevice.mProfileLock) {
            arrayList.addAll(cachedBluetoothDevice.mRemovedProfiles);
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            LocalBluetoothProfile localBluetoothProfile2 = (LocalBluetoothProfile) it2.next();
            if (!(localBluetoothProfile2 instanceof SapProfile)
                    && !(localBluetoothProfile2 instanceof PbapServerProfile)
                    && !(localBluetoothProfile2 instanceof MapProfile)
                    && (findPreference =
                                    this.mFragment.findPreference(
                                            localBluetoothProfile2.toString()))
                            != null) {
                Log.d(
                        "SecBluetoothDetailsProfilesController",
                        "Removing " + localBluetoothProfile2.toString() + " from profile list");
                this.mProfileContainer.removePreference(findPreference);
            }
        }
        addAutoSwitchPreference();
        showOrHideProfileGroup();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0023, code lost:

       if (r0 == 1) goto L11;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void refreshAutoSwitchPreference(androidx.preference.SwitchPreferenceCompat r5) {
        /*
            r4 = this;
            com.android.settingslib.bluetooth.CachedBluetoothDevice r0 = r4.mCachedDevice
            android.bluetooth.BluetoothDevice r0 = r0.mDevice
            int r0 = r0.semGetAutoSwitchMode()
            r1 = -1
            java.lang.String r2 = "com.osp.app.signin"
            if (r0 != r1) goto L13
            androidx.preference.PreferenceGroup r1 = r4.mProfileContainer
            r1.removePreference(r5)
            goto L2a
        L13:
            android.content.Context r1 = r4.mContext
            boolean r3 = com.android.settings.bluetooth.Utils.DEBUG
            android.accounts.AccountManager r1 = android.accounts.AccountManager.get(r1)
            android.accounts.Account[] r1 = r1.getAccountsByType(r2)
            int r1 = r1.length
            if (r1 <= 0) goto L26
            r1 = 1
            if (r0 != r1) goto L26
            goto L27
        L26:
            r1 = 0
        L27:
            r5.setChecked(r1)
        L2a:
            android.content.Context r4 = r4.mContext
            boolean r1 = com.android.settings.bluetooth.Utils.DEBUG
            android.accounts.AccountManager r4 = android.accounts.AccountManager.get(r4)
            android.accounts.Account[] r4 = r4.getAccountsByType(r2)
            int r4 = r4.length
            if (r4 <= 0) goto L40
            r4 = 2132025786(0x7f1421ba, float:1.9690086E38)
            r5.setSummary(r4)
            goto L46
        L40:
            r4 = 2132025696(0x7f142160, float:1.9689904E38)
            r5.setSummary(r4)
        L46:
            java.lang.String r4 = "refreshAutoSwitchPreference:"
            java.lang.String r5 = "SecBluetoothDetailsProfilesController"
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r0, r4, r5)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsProfilesController.refreshAutoSwitchPreference(androidx.preference.SwitchPreferenceCompat):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0125, code lost:

       if (com.android.settingslib.bluetooth.BluetoothUtils.isTablet() != false) goto L50;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0127, code lost:

       r3 = com.android.settings.R.string.bluetooth_pan_profile_summary_use_for_tablet;
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0148, code lost:

       if (com.android.settingslib.bluetooth.BluetoothUtils.isTablet() != false) goto L50;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void refreshProfileSwitchPreference(
            androidx.preference.SwitchPreferenceCompat r12,
            final com.android.settingslib.bluetooth.LocalBluetoothProfile r13) {
        /*
            Method dump skipped, instructions count: 374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsProfilesController.refreshProfileSwitchPreference(androidx.preference.SwitchPreferenceCompat,"
                    + " com.android.settingslib.bluetooth.LocalBluetoothProfile):void");
    }

    public final void showOrHideProfileGroup() {
        PreferenceGroup preferenceGroup = this.mProfileContainer;
        if (preferenceGroup != null) {
            int preferenceCount = preferenceGroup.getPreferenceCount();
            boolean z = this.mProfileGroupIsRemoved;
            PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
            if (!z && preferenceCount == 0) {
                preferenceFragmentCompat
                        .getPreferenceScreen()
                        .removePreference(this.mProfileContainer);
                this.mProfileGroupIsRemoved = true;
            } else {
                if (!z || preferenceCount == 0) {
                    return;
                }
                preferenceFragmentCompat
                        .getPreferenceScreen()
                        .addPreference(this.mProfileContainer);
                this.mProfileGroupIsRemoved = false;
            }
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {}

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {}
}
