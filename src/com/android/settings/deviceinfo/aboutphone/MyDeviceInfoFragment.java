package com.android.settings.deviceinfo.aboutphone;

import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.Process;
import android.os.UserManager;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.deviceinfo.BluetoothAddressPreferenceController;
import com.android.settings.deviceinfo.BuildNumberPreferenceController;
import com.android.settings.deviceinfo.DeviceNamePreferenceController;
import com.android.settings.deviceinfo.FccEquipmentIdPreferenceController;
import com.android.settings.deviceinfo.FeedbackPreferenceController;
import com.android.settings.deviceinfo.IpAddressPreferenceController;
import com.android.settings.deviceinfo.ManualPreferenceController;
import com.android.settings.deviceinfo.RegulatoryInfoPreferenceController;
import com.android.settings.deviceinfo.SafetyInfoPreferenceController;
import com.android.settings.deviceinfo.UptimePreferenceController;
import com.android.settings.deviceinfo.WifiMacAddressPreferenceController;
import com.android.settings.deviceinfo.imei.ImeiInfoPreferenceController;
import com.android.settings.deviceinfo.simstatus.EidStatus;
import com.android.settings.deviceinfo.simstatus.SimEidPreferenceController;
import com.android.settings.deviceinfo.simstatus.SimStatusPreferenceController;
import com.android.settings.deviceinfo.simstatus.SlotSimStatus;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.LayoutPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MyDeviceInfoFragment extends DashboardFragment
        implements DeviceNamePreferenceController.DeviceNamePreferenceHost {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.my_device_info);
    public BuildNumberPreferenceController mBuildNumberPreferenceController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.deviceinfo.aboutphone.MyDeviceInfoFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return MyDeviceInfoFragment.buildPreferenceControllers(context, null, null);
        }
    }

    public static List buildPreferenceControllers(
            Context context, MyDeviceInfoFragment myDeviceInfoFragment, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        ExecutorService newSingleThreadExecutor =
                myDeviceInfoFragment == null ? null : Executors.newSingleThreadExecutor();
        SlotSimStatus slotSimStatus =
                new SlotSimStatus(
                        context,
                        newSingleThreadExecutor,
                        myDeviceInfoFragment != null ? myDeviceInfoFragment.getLifecycle() : null);
        arrayList.add(new IpAddressPreferenceController(context, lifecycle));
        arrayList.add(new WifiMacAddressPreferenceController(context, lifecycle));
        arrayList.add(new BluetoothAddressPreferenceController(context, lifecycle));
        arrayList.add(new RegulatoryInfoPreferenceController(context));
        arrayList.add(new SafetyInfoPreferenceController(context));
        arrayList.add(new ManualPreferenceController(context));
        arrayList.add(new FeedbackPreferenceController(context, myDeviceInfoFragment));
        arrayList.add(new FccEquipmentIdPreferenceController(context));
        arrayList.add(new UptimePreferenceController(context, lifecycle));
        if (myDeviceInfoFragment != null) {
            ImeiInfoPreferenceController imeiInfoPreferenceController =
                    new ImeiInfoPreferenceController(
                            context, ImeiInfoPreferenceController.DEFAULT_KEY);
            imeiInfoPreferenceController.init(myDeviceInfoFragment, slotSimStatus);
            arrayList.add(imeiInfoPreferenceController);
        }
        for (int i = 0; i < slotSimStatus.size(); i++) {
            SimStatusPreferenceController simStatusPreferenceController =
                    new SimStatusPreferenceController(
                            context, i != -1 ? "sim_status" + (i + 1) : "sim_status");
            simStatusPreferenceController.init(myDeviceInfoFragment, slotSimStatus);
            arrayList.add(simStatusPreferenceController);
            if (myDeviceInfoFragment != null) {
                ImeiInfoPreferenceController imeiInfoPreferenceController2 =
                        new ImeiInfoPreferenceController(
                                context, ImeiInfoPreferenceController.DEFAULT_KEY + (i + 1));
                imeiInfoPreferenceController2.init(myDeviceInfoFragment, slotSimStatus);
                arrayList.add(imeiInfoPreferenceController2);
            }
        }
        EidStatus eidStatus = new EidStatus(slotSimStatus, context, newSingleThreadExecutor);
        SimEidPreferenceController simEidPreferenceController =
                new SimEidPreferenceController(context, "eid_info");
        simEidPreferenceController.init(slotSimStatus, eidStatus);
        arrayList.add(simEidPreferenceController);
        if (newSingleThreadExecutor != null) {
            newSingleThreadExecutor.shutdown();
        }
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, this, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "MyDeviceInfoFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 40;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.my_device_info;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (this.mBuildNumberPreferenceController.onActivityResult(i, i2, intent)) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((DeviceNamePreferenceController) use(DeviceNamePreferenceController.class)).setHost(this);
        BuildNumberPreferenceController buildNumberPreferenceController =
                (BuildNumberPreferenceController) use(BuildNumberPreferenceController.class);
        this.mBuildNumberPreferenceController = buildNumberPreferenceController;
        buildNumberPreferenceController.setHost(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        LayoutPreference layoutPreference =
                (LayoutPreference) getPreferenceScreen().findPreference("my_device_info_header");
        boolean z =
                getContext()
                        .getResources()
                        .getBoolean(R.bool.config_show_device_header_in_device_info);
        layoutPreference.setVisible(z);
        if (z) {
            View findViewById = layoutPreference.mRootView.findViewById(R.id.entity_header);
            FragmentActivity activity = getActivity();
            Bundle arguments = getArguments();
            EntityHeaderController entityHeaderController =
                    new EntityHeaderController(activity, this, findViewById);
            entityHeaderController.mAction1 = 0;
            entityHeaderController.mAction2 = 0;
            if (arguments.getInt("icon_id", 0) == 0) {
                UserManager userManager = (UserManager) getActivity().getSystemService("user");
                UserInfo existingUser = Utils.getExistingUser(Process.myUserHandle(), userManager);
                entityHeaderController.mLabel = existingUser.name;
                entityHeaderController.setIcon(
                        com.android.settingslib.Utils.getUserIcon(
                                getActivity(), userManager, existingUser));
            }
            entityHeaderController.done(true);
        }
    }
}
