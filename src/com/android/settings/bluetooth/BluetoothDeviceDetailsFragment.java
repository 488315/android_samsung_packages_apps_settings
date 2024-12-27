package com.android.settings.bluetooth;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.companion.AssociationInfo;
import android.companion.CompanionDeviceManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.connecteddevice.stylus.StylusDevicesController;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settings.inputmethod.KeyboardSettingsPreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.google.common.base.Objects;
import com.samsung.android.knox.EnterpriseContainerCallback;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothDeviceDetailsFragment extends RestrictedDashboardFragment {
    static int EDIT_DEVICE_NAME_ITEM_ID = 1;
    static TestDataFactory sTestDataFactory;
    public BluetoothAdapter mBluetoothAdapter;
    public final AnonymousClass1 mBluetoothCallback;
    CachedBluetoothDevice mCachedDevice;
    String mDeviceAddress;
    public final BluetoothDeviceDetailsFragment$$ExternalSyntheticLambda0
            mExtraControlMetadataListener;
    public boolean mExtraControlUriLoaded;
    public int mExtraControlViewWidth;
    public InputDevice mInputDevice;
    LocalBluetoothManager mManager;
    public final AnonymousClass2 mOnGlobalLayoutListener;
    public UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface TestDataFactory {
        CachedBluetoothDevice getDevice();

        LocalBluetoothManager getManager();

        UserManager getUserManager();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.bluetooth.BluetoothDeviceDetailsFragment$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.bluetooth.BluetoothDeviceDetailsFragment$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.bluetooth.BluetoothDeviceDetailsFragment$2] */
    public BluetoothDeviceDetailsFragment() {
        super("no_config_bluetooth");
        this.mExtraControlViewWidth = 0;
        this.mExtraControlUriLoaded = false;
        this.mBluetoothCallback =
                new BluetoothCallback() { // from class:
                                          // com.android.settings.bluetooth.BluetoothDeviceDetailsFragment.1
                    @Override // com.android.settingslib.bluetooth.BluetoothCallback
                    public final void onBluetoothStateChanged(int i) {
                        if (i == 10) {
                            Log.i("BTDeviceDetailsFrg", "Bluetooth is off, exit activity.");
                            FragmentActivity activity =
                                    BluetoothDeviceDetailsFragment.this.getActivity();
                            if (activity != null) {
                                activity.finish();
                            }
                        }
                    }
                };
        this.mExtraControlMetadataListener =
                new BluetoothAdapter
                        .OnMetadataChangedListener() { // from class:
                                                       // com.android.settings.bluetooth.BluetoothDeviceDetailsFragment$$ExternalSyntheticLambda0
                    public final void onMetadataChanged(
                            BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                        BluetoothDeviceDetailsFragment bluetoothDeviceDetailsFragment =
                                BluetoothDeviceDetailsFragment.this;
                        if (i != 25) {
                            bluetoothDeviceDetailsFragment.getClass();
                        } else {
                            if (bluetoothDeviceDetailsFragment.mExtraControlViewWidth <= 0
                                    || bluetoothDeviceDetailsFragment.mExtraControlUriLoaded) {
                                return;
                            }
                            Log.i(
                                    "BTDeviceDetailsFrg",
                                    "Update extra control UI because of metadata change.");
                            bluetoothDeviceDetailsFragment.updateExtraControlUri$1(
                                    bluetoothDeviceDetailsFragment.mExtraControlViewWidth);
                        }
                    }
                };
        this.mOnGlobalLayoutListener =
                new ViewTreeObserver
                        .OnGlobalLayoutListener() { // from class:
                                                    // com.android.settings.bluetooth.BluetoothDeviceDetailsFragment.2
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        View view = BluetoothDeviceDetailsFragment.this.getView();
                        if (view != null && view.getWidth() > 0) {
                            BluetoothDeviceDetailsFragment bluetoothDeviceDetailsFragment =
                                    BluetoothDeviceDetailsFragment.this;
                            int width = view.getWidth();
                            TypedArray obtainStyledAttributes =
                                    BluetoothDeviceDetailsFragment.this
                                            .getContext()
                                            .obtainStyledAttributes(
                                                    new int[] {
                                                        R.attr.listPreferredItemPaddingStart,
                                                        R.attr.listPreferredItemPaddingEnd
                                                    });
                            int dimensionPixelSize =
                                    obtainStyledAttributes.getDimensionPixelSize(1, 0)
                                            + obtainStyledAttributes.getDimensionPixelSize(0, 0);
                            obtainStyledAttributes.recycle();
                            bluetoothDeviceDetailsFragment.mExtraControlViewWidth =
                                    width - dimensionPixelSize;
                            BluetoothDeviceDetailsFragment bluetoothDeviceDetailsFragment2 =
                                    BluetoothDeviceDetailsFragment.this;
                            bluetoothDeviceDetailsFragment2.updateExtraControlUri$1(
                                    bluetoothDeviceDetailsFragment2.mExtraControlViewWidth);
                            view.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(
                                            BluetoothDeviceDetailsFragment.this
                                                    .mOnGlobalLayoutListener);
                        }
                    }
                };
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        if (this.mCachedDevice != null) {
            Lifecycle settingsLifecycle = getSettingsLifecycle();
            arrayList.add(
                    new BluetoothDetailsHeaderController(
                            context, this, this.mCachedDevice, settingsLifecycle));
            CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
            BluetoothDetailsButtonsController bluetoothDetailsButtonsController =
                    new BluetoothDetailsButtonsController(
                            context, this, cachedBluetoothDevice, settingsLifecycle);
            bluetoothDetailsButtonsController.mIsConnected = cachedBluetoothDevice.isConnected();
            arrayList.add(bluetoothDetailsButtonsController);
            BluetoothDetailsCompanionAppsController bluetoothDetailsCompanionAppsController =
                    new BluetoothDetailsCompanionAppsController(
                            context, this, this.mCachedDevice, settingsLifecycle);
            bluetoothDetailsCompanionAppsController.mCompanionDeviceManager =
                    (CompanionDeviceManager) context.getSystemService(CompanionDeviceManager.class);
            bluetoothDetailsCompanionAppsController.mPackageManager = context.getPackageManager();
            settingsLifecycle.addObserver(bluetoothDetailsCompanionAppsController);
            arrayList.add(bluetoothDetailsCompanionAppsController);
            arrayList.add(
                    new BluetoothDetailsAudioDeviceTypeController(
                            context, this, this.mManager, this.mCachedDevice, settingsLifecycle));
            arrayList.add(
                    new BluetoothDetailsSpatialAudioController(
                            context, this, this.mCachedDevice, settingsLifecycle));
            arrayList.add(
                    new BluetoothDetailsProfilesController(
                            context, this, this.mManager, this.mCachedDevice, settingsLifecycle));
            arrayList.add(
                    new BluetoothDetailsMacAddressController(
                            context, this, this.mCachedDevice, settingsLifecycle));
            arrayList.add(
                    new StylusDevicesController(
                            context, this.mInputDevice, this.mCachedDevice, settingsLifecycle));
            BluetoothDetailsRelatedToolsController bluetoothDetailsRelatedToolsController =
                    new BluetoothDetailsRelatedToolsController(
                            context, this, this.mCachedDevice, settingsLifecycle);
            settingsLifecycle.addObserver(bluetoothDetailsRelatedToolsController);
            arrayList.add(bluetoothDetailsRelatedToolsController);
            BluetoothDetailsPairOtherController bluetoothDetailsPairOtherController =
                    new BluetoothDetailsPairOtherController(
                            context, this, this.mCachedDevice, settingsLifecycle);
            settingsLifecycle.addObserver(bluetoothDetailsPairOtherController);
            arrayList.add(bluetoothDetailsPairOtherController);
            CachedBluetoothDevice cachedBluetoothDevice2 = this.mCachedDevice;
            final BluetoothDetailsDataSyncController bluetoothDetailsDataSyncController =
                    new BluetoothDetailsDataSyncController(
                            context, this, cachedBluetoothDevice2, settingsLifecycle);
            bluetoothDetailsDataSyncController.mAssociationId = -1;
            bluetoothDetailsDataSyncController.mCachedDevice = cachedBluetoothDevice2;
            CompanionDeviceManager companionDeviceManager =
                    (CompanionDeviceManager) context.getSystemService(CompanionDeviceManager.class);
            bluetoothDetailsDataSyncController.mCompanionDeviceManager = companionDeviceManager;
            companionDeviceManager.getAllAssociations().stream()
                    .filter(new BluetoothDetailsDataSyncController$$ExternalSyntheticLambda0())
                    .filter(
                            new Predicate() { // from class:
                                              // com.android.settings.bluetooth.BluetoothDetailsDataSyncController$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return Objects.equal(
                                            BluetoothDetailsDataSyncController.this.mCachedDevice
                                                    .mDevice.getAddress(),
                                            ((AssociationInfo) obj)
                                                    .getDeviceMacAddress()
                                                    .toString()
                                                    .toUpperCase());
                                }
                            })
                    .max(
                            Comparator.comparingLong(
                                    new BluetoothDetailsDataSyncController$$ExternalSyntheticLambda2()))
                    .ifPresent(
                            new Consumer() { // from class:
                                             // com.android.settings.bluetooth.BluetoothDetailsDataSyncController$$ExternalSyntheticLambda3
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    BluetoothDetailsDataSyncController
                                            bluetoothDetailsDataSyncController2 =
                                                    BluetoothDetailsDataSyncController.this;
                                    bluetoothDetailsDataSyncController2.getClass();
                                    bluetoothDetailsDataSyncController2.mAssociationId =
                                            ((AssociationInfo) obj).getId();
                                }
                            });
            arrayList.add(bluetoothDetailsDataSyncController);
            arrayList.add(
                    new BluetoothDetailsExtraOptionsController(
                            context, this, this.mCachedDevice, settingsLifecycle));
            BluetoothDetailsHearingDeviceController bluetoothDetailsHearingDeviceController =
                    new BluetoothDetailsHearingDeviceController(
                            context, this, this.mManager, this.mCachedDevice, settingsLifecycle);
            arrayList.add(bluetoothDetailsHearingDeviceController);
            Intent intent = getIntent();
            boolean z = false;
            if (intent != null && intent.getIntExtra(":settings:source_metrics", 0) == 2024) {
                z = true;
            }
            ((ArrayList) bluetoothDetailsHearingDeviceController.mControllers).clear();
            if (!z) {
                List list = bluetoothDetailsHearingDeviceController.mControllers;
                Context context2 =
                        ((BluetoothDetailsController) bluetoothDetailsHearingDeviceController)
                                .mContext;
                Lifecycle lifecycle = bluetoothDetailsHearingDeviceController.mLifecycle;
                BluetoothDetailsHearingDeviceSettingsController
                        bluetoothDetailsHearingDeviceSettingsController =
                                new BluetoothDetailsHearingDeviceSettingsController(
                                        context2,
                                        bluetoothDetailsHearingDeviceController.mFragment,
                                        bluetoothDetailsHearingDeviceController.mCachedDevice,
                                        lifecycle);
                lifecycle.addObserver(bluetoothDetailsHearingDeviceSettingsController);
                ((ArrayList) list).add(bluetoothDetailsHearingDeviceSettingsController);
            }
            List list2 = bluetoothDetailsHearingDeviceController.mControllers;
            ArrayList arrayList2 = (ArrayList) list2;
            arrayList2.add(
                    new BluetoothDetailsHearingAidsPresetsController(
                            ((BluetoothDetailsController) bluetoothDetailsHearingDeviceController)
                                    .mContext,
                            bluetoothDetailsHearingDeviceController.mFragment,
                            bluetoothDetailsHearingDeviceController.mManager,
                            bluetoothDetailsHearingDeviceController.mCachedDevice,
                            bluetoothDetailsHearingDeviceController.mLifecycle));
            arrayList.addAll(bluetoothDetailsHearingDeviceController.mControllers);
        }
        return arrayList;
    }

    public void finishFragmentIfNecessary() {
        if (this.mCachedDevice.mBondState == 10) {
            finish();
        }
    }

    public CachedBluetoothDevice getCachedDevice(String str) {
        TestDataFactory testDataFactory = sTestDataFactory;
        if (testDataFactory != null) {
            return testDataFactory.getDevice();
        }
        BluetoothDevice remoteDevice = this.mManager.mLocalAdapter.mAdapter.getRemoteDevice(str);
        if (remoteDevice == null) {
            return null;
        }
        CachedBluetoothDevice findDevice =
                this.mManager.mCachedDeviceManager.findDevice(remoteDevice);
        if (findDevice != null) {
            return findDevice;
        }
        Log.i(
                "BTDeviceDetailsFrg",
                "Add device to cached device manager: " + remoteDevice.getAnonymizedAddress());
        return this.mManager.mCachedDeviceManager.addDevice(remoteDevice);
    }

    public InputDevice getInputDevice(Context context) {
        InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
        for (int i : inputManager.getInputDeviceIds()) {
            String inputDeviceBluetoothAddress = inputManager.getInputDeviceBluetoothAddress(i);
            if (inputDeviceBluetoothAddress != null
                    && inputDeviceBluetoothAddress.equals(this.mDeviceAddress)) {
                return inputManager.getInputDevice(i);
            }
        }
        return null;
    }

    public LocalBluetoothManager getLocalBluetoothManager(Context context) {
        TestDataFactory testDataFactory = sTestDataFactory;
        return testDataFactory != null
                ? testDataFactory.getManager()
                : LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BTDeviceDetailsFrg";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return com.android.settings.R.xml.bluetooth_device_details_fragment;
    }

    public UserManager getUserManager() {
        TestDataFactory testDataFactory = sTestDataFactory;
        return testDataFactory != null
                ? testDataFactory.getUserManager()
                : (UserManager) getSystemService(UserManager.class);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        this.mDeviceAddress = getArguments().getString("device_address");
        this.mManager = getLocalBluetoothManager(context);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mCachedDevice = getCachedDevice(this.mDeviceAddress);
        this.mUserManager = getUserManager();
        if (FeatureFlagUtils.isEnabled(context, "settings_show_stylus_preferences")) {
            this.mInputDevice = getInputDevice(context);
        }
        super.onAttach(context);
        if (this.mCachedDevice == null) {
            Log.w("BTDeviceDetailsFrg", "onAttach() CachedDevice is null!");
            finish();
            return;
        }
        ((AdvancedBluetoothDetailsHeaderController)
                        use(AdvancedBluetoothDetailsHeaderController.class))
                .init(this.mCachedDevice);
        ((LeAudioBluetoothDetailsHeaderController)
                        use(LeAudioBluetoothDetailsHeaderController.class))
                .init(this.mCachedDevice, this.mManager);
        ((KeyboardSettingsPreferenceController) use(KeyboardSettingsPreferenceController.class))
                .init(this.mCachedDevice);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        BluetoothFeatureProviderImpl bluetoothFeatureProvider =
                featureFactoryImpl.getBluetoothFeatureProvider();
        boolean z = DeviceConfig.getBoolean("settings_ui", "bt_slice_settings_enabled", true);
        BlockingPrefWithSliceController blockingPrefWithSliceController =
                (BlockingPrefWithSliceController) use(BlockingPrefWithSliceController.class);
        Uri uri = null;
        if (z) {
            BluetoothDevice bluetoothDevice = this.mCachedDevice.mDevice;
            bluetoothFeatureProvider.getClass();
            byte[] metadata = bluetoothDevice.getMetadata(16);
            if (metadata != null) {
                uri = Uri.parse(new String(metadata));
            }
        }
        blockingPrefWithSliceController.setSliceUri(uri);
        this.mManager.mEventManager.registerCallback(this.mBluetoothCallback);
        this.mBluetoothAdapter.addOnMetadataChangedListener(
                this.mCachedDevice.mDevice,
                context.getMainExecutor(),
                this.mExtraControlMetadataListener);
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitleForInputDevice();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (!this.mUserManager.isGuestUser()) {
            MenuItem add =
                    menu.add(
                            0,
                            EDIT_DEVICE_NAME_ITEM_ID,
                            0,
                            com.android.settings.R.string.bluetooth_rename_button);
            add.setIcon(R.drawable.ic_popup_sync_4);
            add.setShowAsAction(2);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        if (onCreateView != null) {
            onCreateView
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        }
        return onCreateView;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        super.onDetach();
        this.mManager.mEventManager.unregisterCallback(this.mBluetoothCallback);
        this.mBluetoothAdapter.removeOnMetadataChangedListener(
                this.mCachedDevice.mDevice, this.mExtraControlMetadataListener);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != EDIT_DEVICE_NAME_ITEM_ID) {
            return super.onOptionsItemSelected(menuItem);
        }
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        Bundle bundle = new Bundle(1);
        bundle.putString("cached_device", cachedBluetoothDevice.mDevice.getAddress());
        RemoteDeviceNameDialogFragment remoteDeviceNameDialogFragment =
                new RemoteDeviceNameDialogFragment();
        remoteDeviceNameDialogFragment.setArguments(bundle);
        remoteDeviceNameDialogFragment.show(getFragmentManager(), "RemoteDeviceName");
        return true;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        finishFragmentIfNecessary();
    }

    public void setTitleForInputDevice() {
        InputDevice inputDevice = this.mInputDevice;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if ((inputDevice == null || !inputDevice.supportsSource(16386))
                ? cachedBluetoothDevice != null
                        ? TextUtils.equals(
                                BluetoothUtils.getStringMetaData(cachedBluetoothDevice.mDevice, 17),
                                "Stylus")
                        : false
                : true) {
            getActivity()
                    .setTitle(
                            getContext()
                                    .getString(
                                            com.android.settings.R.string
                                                    .stylus_device_details_title));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateExtraControlUri$1(int r6) {
        /*
            r5 = this;
            com.android.settings.overlay.FeatureFactoryImpl r0 = com.android.settings.overlay.FeatureFactoryImpl._factory
            if (r0 == 0) goto L8e
            com.android.settings.bluetooth.BluetoothFeatureProviderImpl r0 = r0.getBluetoothFeatureProvider()
            java.lang.String r1 = "settings_ui"
            java.lang.String r2 = "bt_slice_settings_enabled"
            r3 = 1
            boolean r1 = android.provider.DeviceConfig.getBoolean(r1, r2, r3)
            com.android.settingslib.bluetooth.CachedBluetoothDevice r2 = r5.mCachedDevice
            android.bluetooth.BluetoothDevice r2 = r2.mDevice
            r0.getClass()
            java.lang.String r0 = com.android.settingslib.bluetooth.BluetoothUtils.getControlUriMetaData(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            r4 = 0
            if (r2 != 0) goto L3e
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.NullPointerException -> L37
            r2.<init>()     // Catch: java.lang.NullPointerException -> L37
            r2.append(r0)     // Catch: java.lang.NullPointerException -> L37
            r2.append(r6)     // Catch: java.lang.NullPointerException -> L37
            java.lang.String r6 = r2.toString()     // Catch: java.lang.NullPointerException -> L37
            android.net.Uri r6 = android.net.Uri.parse(r6)     // Catch: java.lang.NullPointerException -> L37
            goto L3f
        L37:
            java.lang.String r6 = "BTDeviceDetailsFrg"
            java.lang.String r0 = "unable to parse uri"
            android.util.Log.d(r6, r0)
        L3e:
            r6 = r4
        L3f:
            boolean r0 = r5.mExtraControlUriLoaded
            if (r6 == 0) goto L44
            goto L45
        L44:
            r3 = 0
        L45:
            r0 = r0 | r3
            r5.mExtraControlUriLoaded = r0
            java.lang.Class<com.android.settings.slices.SlicePreferenceController> r0 = com.android.settings.slices.SlicePreferenceController.class
            com.android.settingslib.core.AbstractPreferenceController r0 = r5.use(r0)
            com.android.settings.slices.SlicePreferenceController r0 = (com.android.settings.slices.SlicePreferenceController) r0
            if (r1 == 0) goto L53
            r4 = r6
        L53:
            r0.setSliceUri(r4)
            r0.onStart()
            androidx.preference.PreferenceScreen r6 = r5.getPreferenceScreen()
            r0.displayPreference(r6)
            java.lang.Class<com.android.settings.bluetooth.LeAudioBluetoothDetailsHeaderController> r6 = com.android.settings.bluetooth.LeAudioBluetoothDetailsHeaderController.class
            com.android.settingslib.core.AbstractPreferenceController r6 = r5.use(r6)
            com.android.settings.bluetooth.LeAudioBluetoothDetailsHeaderController r6 = (com.android.settings.bluetooth.LeAudioBluetoothDetailsHeaderController) r6
            androidx.preference.PreferenceScreen r0 = r5.getPreferenceScreen()
            r6.displayPreference(r0)
            java.lang.Class<com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController> r6 = com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController.class
            com.android.settingslib.core.AbstractPreferenceController r6 = r5.use(r6)
            com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController r6 = (com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController) r6
            androidx.preference.PreferenceScreen r0 = r5.getPreferenceScreen()
            r6.displayPreference(r0)
            java.lang.Class<com.android.settings.bluetooth.BluetoothDetailsHeaderController> r6 = com.android.settings.bluetooth.BluetoothDetailsHeaderController.class
            com.android.settingslib.core.AbstractPreferenceController r6 = r5.use(r6)
            com.android.settings.bluetooth.BluetoothDetailsHeaderController r6 = (com.android.settings.bluetooth.BluetoothDetailsHeaderController) r6
            androidx.preference.PreferenceScreen r5 = r5.getPreferenceScreen()
            r6.displayPreference(r5)
            return
        L8e:
            java.lang.UnsupportedOperationException r5 = new java.lang.UnsupportedOperationException
            java.lang.String r6 = "No feature factory configured"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.bluetooth.BluetoothDeviceDetailsFragment.updateExtraControlUri$1(int):void");
    }
}
