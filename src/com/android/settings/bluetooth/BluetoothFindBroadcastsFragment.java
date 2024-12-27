package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceCategory;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settingslib.bluetooth.BluetoothLeBroadcastMetadataExt;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastMetadata;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.jvm.internal.Intrinsics;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothFindBroadcastsFragment extends RestrictedDashboardFragment {
    public BluetoothFindBroadcastsHeaderController mBluetoothFindBroadcastsHeaderController;
    public final AnonymousClass1 mBroadcastAssistantCallback;
    PreferenceCategory mBroadcastSourceListCategory;
    CachedBluetoothDevice mCachedDevice;
    String mDeviceAddress;
    public Executor mExecutor;
    public final AnonymousClass3 mInputFilter;
    public LocalBluetoothLeBroadcastAssistant mLeBroadcastAssistant;
    public LocalBluetoothLeBroadcastMetadata mLocalBroadcastMetadata;
    LocalBluetoothManager mManager;
    BluetoothBroadcastSourcePreference mSelectedPreference;
    public int mSourceId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$3, reason: invalid class name */
    public final class AnonymousClass3 implements InputFilter {
        @Override // android.text.InputFilter
        public final CharSequence filter(
                CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            return charSequence.toString().getBytes(StandardCharsets.UTF_8).length
                            == charSequence.length()
                    ? charSequence
                    : ApnSettings.MVNO_NONE;
        }
    }

    public BluetoothFindBroadcastsFragment() {
        super("no_config_bluetooth");
        this.mBroadcastAssistantCallback = new AnonymousClass1();
        this.mInputFilter = new AnonymousClass3();
    }

    public final void addConnectedSourcePreference() {
        List allSources = this.mLeBroadcastAssistant.getAllSources(this.mCachedDevice.mDevice);
        if (allSources.isEmpty()) {
            return;
        }
        BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState =
                (BluetoothLeBroadcastReceiveState) allSources.get(0);
        BluetoothBroadcastSourcePreference bluetoothBroadcastSourcePreference =
                (BluetoothBroadcastSourcePreference)
                        this.mBroadcastSourceListCategory.findPreference(
                                Integer.toString(
                                        bluetoothLeBroadcastReceiveState.getBroadcastId()));
        if (bluetoothBroadcastSourcePreference == null) {
            bluetoothBroadcastSourcePreference =
                    new BluetoothBroadcastSourcePreference(getContext());
            bluetoothBroadcastSourcePreference.setOnPreferenceClickListener(
                    new BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda0(
                            this, bluetoothBroadcastSourcePreference));
            bluetoothBroadcastSourcePreference.setKey(
                    Integer.toString(bluetoothLeBroadcastReceiveState.getBroadcastId()));
            this.mBroadcastSourceListCategory.addPreference(bluetoothBroadcastSourcePreference);
        }
        bluetoothBroadcastSourcePreference.mBluetoothLeBroadcastReceiveState =
                bluetoothLeBroadcastReceiveState;
        String programInfo = bluetoothBroadcastSourcePreference.getProgramInfo();
        bluetoothBroadcastSourcePreference.mTitle = programInfo;
        bluetoothBroadcastSourcePreference.mStatus = true;
        bluetoothBroadcastSourcePreference.setTitle(programInfo);
        bluetoothBroadcastSourcePreference.updateStatusButton();
        bluetoothBroadcastSourcePreference.setOrder(0);
        this.mSourceId = bluetoothLeBroadcastReceiveState.getSourceId();
        this.mSelectedPreference = bluetoothBroadcastSourcePreference;
        BluetoothFindBroadcastsHeaderController bluetoothFindBroadcastsHeaderController =
                this.mBluetoothFindBroadcastsHeaderController;
        if (bluetoothFindBroadcastsHeaderController != null) {
            bluetoothFindBroadcastsHeaderController.updateHeaderLayout();
        }
    }

    public void addSource(BluetoothBroadcastSourcePreference bluetoothBroadcastSourcePreference) {
        if (this.mLeBroadcastAssistant == null || this.mCachedDevice == null) {
            Log.w(
                    "BtFindBroadcastsFrg",
                    "addSource: LeBroadcastAssistant or CachedDevice is null!");
            return;
        }
        BluetoothBroadcastSourcePreference bluetoothBroadcastSourcePreference2 =
                this.mSelectedPreference;
        if (bluetoothBroadcastSourcePreference2 != null) {
            if (bluetoothBroadcastSourcePreference2.mBluetoothLeBroadcastReceiveState != null) {
                Log.d(
                        "BtFindBroadcastsFrg",
                        "addSource: Remove preference that created by getAllSources()");
                final int i = 0;
                getActivity()
                        .runOnUiThread(
                                new Runnable(
                                        this) { // from class:
                                                // com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */ BluetoothFindBroadcastsFragment
                                            f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i2 = i;
                                        BluetoothFindBroadcastsFragment
                                                bluetoothFindBroadcastsFragment = this.f$0;
                                        switch (i2) {
                                            case 0:
                                                bluetoothFindBroadcastsFragment
                                                        .mBroadcastSourceListCategory
                                                        .removePreference(
                                                                bluetoothFindBroadcastsFragment
                                                                        .mSelectedPreference);
                                                break;
                                            default:
                                                BluetoothBroadcastSourcePreference
                                                        bluetoothBroadcastSourcePreference3 =
                                                                bluetoothFindBroadcastsFragment
                                                                        .mSelectedPreference;
                                                bluetoothBroadcastSourcePreference3
                                                        .updateMetadataAndRefreshUi(
                                                                bluetoothBroadcastSourcePreference3
                                                                        .mBluetoothLeBroadcastMetadata,
                                                                false);
                                                bluetoothFindBroadcastsFragment.mSelectedPreference
                                                        .setOrder(1);
                                                break;
                                        }
                                    }
                                });
                LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                        this.mLeBroadcastAssistant;
                if (localBluetoothLeBroadcastAssistant != null
                        && !localBluetoothLeBroadcastAssistant.isSearchInProgress()) {
                    Log.d(
                            "BtFindBroadcastsFrg",
                            "addSource: Start Searching For Broadcast Sources");
                    this.mLeBroadcastAssistant.startSearchingForSources(Collections.emptyList());
                }
            } else {
                Log.d(
                        "BtFindBroadcastsFrg",
                        "addSource: Update preference that created by onSourceFound()");
                final int i2 = 1;
                getActivity()
                        .runOnUiThread(
                                new Runnable(
                                        this) { // from class:
                                                // com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */ BluetoothFindBroadcastsFragment
                                            f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i22 = i2;
                                        BluetoothFindBroadcastsFragment
                                                bluetoothFindBroadcastsFragment = this.f$0;
                                        switch (i22) {
                                            case 0:
                                                bluetoothFindBroadcastsFragment
                                                        .mBroadcastSourceListCategory
                                                        .removePreference(
                                                                bluetoothFindBroadcastsFragment
                                                                        .mSelectedPreference);
                                                break;
                                            default:
                                                BluetoothBroadcastSourcePreference
                                                        bluetoothBroadcastSourcePreference3 =
                                                                bluetoothFindBroadcastsFragment
                                                                        .mSelectedPreference;
                                                bluetoothBroadcastSourcePreference3
                                                        .updateMetadataAndRefreshUi(
                                                                bluetoothBroadcastSourcePreference3
                                                                        .mBluetoothLeBroadcastMetadata,
                                                                false);
                                                bluetoothFindBroadcastsFragment.mSelectedPreference
                                                        .setOrder(1);
                                                break;
                                        }
                                    }
                                });
            }
        }
        this.mSelectedPreference = bluetoothBroadcastSourcePreference;
        this.mLeBroadcastAssistant.addSource(
                this.mCachedDevice.mDevice,
                bluetoothBroadcastSourcePreference.mBluetoothLeBroadcastMetadata,
                true);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        if (this.mCachedDevice != null) {
            BluetoothFindBroadcastsHeaderController bluetoothFindBroadcastsHeaderController =
                    new BluetoothFindBroadcastsHeaderController(
                            context, this, this.mCachedDevice, getSettingsLifecycle());
            bluetoothFindBroadcastsHeaderController.mBluetoothFindBroadcastsFragment = this;
            this.mBluetoothFindBroadcastsHeaderController = bluetoothFindBroadcastsHeaderController;
            arrayList.add(bluetoothFindBroadcastsHeaderController);
        }
        return arrayList;
    }

    public void finishFragmentIfNecessary() {
        if (this.mCachedDevice.mBondState == 10) {
            finish();
        }
    }

    public CachedBluetoothDevice getCachedDevice(String str) {
        return this.mManager.mCachedDeviceManager.findDevice(
                this.mManager.mLocalAdapter.mAdapter.getRemoteDevice(str));
    }

    public LocalBluetoothManager getLocalBluetoothManager(Context context) {
        return LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BtFindBroadcastsFrg";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2019;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.bluetooth_find_broadcasts_fragment;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult: ", ", resultCode: ", i, i2, "BtFindBroadcastsFrg");
        if (i == 0 && i2 == -1) {
            String qrCodeString = intent.getStringExtra("key_broadcast_metadata");
            this.mLocalBroadcastMetadata.getClass();
            Intrinsics.checkNotNullParameter(qrCodeString, "qrCodeString");
            BluetoothLeBroadcastMetadata convertToBroadcastMetadata =
                    BluetoothLeBroadcastMetadataExt.convertToBroadcastMetadata(qrCodeString);
            if (convertToBroadcastMetadata == null) {
                Toast.makeText(getContext(), R.string.find_broadcast_join_broadcast_error, 0)
                        .show();
                return;
            }
            Log.d(
                    "BtFindBroadcastsFrg",
                    "onActivityResult source Id = " + convertToBroadcastMetadata.getBroadcastId());
            updateListCategoryFromBroadcastMetadata(convertToBroadcastMetadata, false);
            addSource(
                    (BluetoothBroadcastSourcePreference)
                            this.mBroadcastSourceListCategory.findPreference(
                                    Integer.toString(convertToBroadcastMetadata.getBroadcastId())));
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        this.mDeviceAddress = getArguments().getString("device_address");
        LocalBluetoothManager localBluetoothManager = getLocalBluetoothManager(context);
        this.mManager = localBluetoothManager;
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = null;
        this.mCachedDevice = null;
        if (localBluetoothManager == null) {
            Log.w("BtFindBroadcastsFrg", "getLeBroadcastAssistant: LocalBluetoothManager is null!");
        } else {
            LocalBluetoothProfileManager localBluetoothProfileManager =
                    localBluetoothManager.mProfileManager;
            if (localBluetoothProfileManager == null) {
                Log.w(
                        "BtFindBroadcastsFrg",
                        "getLeBroadcastAssistant: LocalBluetoothProfileManager is null!");
            } else {
                localBluetoothLeBroadcastAssistant =
                        localBluetoothProfileManager.mLeAudioBroadcastAssistant;
            }
        }
        this.mLeBroadcastAssistant = localBluetoothLeBroadcastAssistant;
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mLocalBroadcastMetadata = new LocalBluetoothLeBroadcastMetadata();
        super.onAttach(context);
        if (this.mCachedDevice == null || this.mLeBroadcastAssistant == null) {
            Log.w(
                    "BtFindBroadcastsFrg",
                    "onAttach() CachedDevice or LeBroadcastAssistant is null!");
            finish();
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mBroadcastSourceListCategory =
                (PreferenceCategory) findPreference("broadcast_source_list");
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
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null
                || localBluetoothLeBroadcastAssistant.isSearchInProgress()) {
            addConnectedSourcePreference();
        } else {
            this.mLeBroadcastAssistant.startSearchingForSources(Collections.emptyList());
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant != null) {
            localBluetoothLeBroadcastAssistant.registerServiceCallBack(
                    this.mExecutor, this.mBroadcastAssistantCallback);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant != null) {
            if (localBluetoothLeBroadcastAssistant.isSearchInProgress()) {
                this.mLeBroadcastAssistant.stopSearchingForSources$1();
            }
            this.mLeBroadcastAssistant.unregisterServiceCallBack(this.mBroadcastAssistantCallback);
        }
    }

    public final void updateListCategoryFromBroadcastMetadata(
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata, boolean z) {
        BluetoothBroadcastSourcePreference bluetoothBroadcastSourcePreference =
                (BluetoothBroadcastSourcePreference)
                        this.mBroadcastSourceListCategory.findPreference(
                                Integer.toString(bluetoothLeBroadcastMetadata.getBroadcastId()));
        if (bluetoothBroadcastSourcePreference == null) {
            bluetoothBroadcastSourcePreference =
                    new BluetoothBroadcastSourcePreference(getContext());
            bluetoothBroadcastSourcePreference.setOnPreferenceClickListener(
                    new BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda0(
                            this, bluetoothBroadcastSourcePreference));
            bluetoothBroadcastSourcePreference.setKey(
                    Integer.toString(bluetoothLeBroadcastMetadata.getBroadcastId()));
            this.mBroadcastSourceListCategory.addPreference(bluetoothBroadcastSourcePreference);
        }
        bluetoothBroadcastSourcePreference.updateMetadataAndRefreshUi(
                bluetoothLeBroadcastMetadata, z);
        bluetoothBroadcastSourcePreference.setOrder(!z ? 1 : 0);
        BluetoothFindBroadcastsHeaderController bluetoothFindBroadcastsHeaderController =
                this.mBluetoothFindBroadcastsHeaderController;
        if (bluetoothFindBroadcastsHeaderController != null) {
            bluetoothFindBroadcastsHeaderController.updateHeaderLayout();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 implements BluetoothLeBroadcastAssistant.Callback {
        public AnonymousClass1() {}

        public final void onReceiveStateChanged(
                BluetoothDevice bluetoothDevice,
                int i,
                BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
            Log.d("BtFindBroadcastsFrg", "onReceiveStateChanged:");
        }

        public final void onSearchStartFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onSearchStartFailed: ", "BtFindBroadcastsFrg");
        }

        public final void onSearchStarted(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onSearchStarted: ", "BtFindBroadcastsFrg");
            BluetoothFindBroadcastsFragment.this
                    .getActivity()
                    .runOnUiThread(
                            new BluetoothFindBroadcastsFragment$1$$ExternalSyntheticLambda0(
                                    this, 0));
        }

        public final void onSearchStopFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onSearchStopFailed: ", "BtFindBroadcastsFrg");
        }

        public final void onSearchStopped(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onSearchStopped: ", "BtFindBroadcastsFrg");
        }

        public final void onSourceAddFailed(
                BluetoothDevice bluetoothDevice,
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
                int i) {
            BluetoothFindBroadcastsFragment.this.mSelectedPreference = null;
            Log.d("BtFindBroadcastsFrg", "onSourceAddFailed: clear the mSelectedPreference.");
        }

        public final void onSourceAdded(BluetoothDevice bluetoothDevice, int i, int i2) {
            BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment =
                    BluetoothFindBroadcastsFragment.this;
            bluetoothFindBroadcastsFragment.mSourceId = i;
            if (bluetoothFindBroadcastsFragment.mSelectedPreference == null) {
                Log.w("BtFindBroadcastsFrg", "onSourceAdded: mSelectedPreference == null!");
                return;
            }
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                    bluetoothFindBroadcastsFragment.mLeBroadcastAssistant;
            if (localBluetoothLeBroadcastAssistant != null
                    && localBluetoothLeBroadcastAssistant.isSearchInProgress()) {
                BluetoothFindBroadcastsFragment.this.mLeBroadcastAssistant
                        .stopSearchingForSources$1();
            }
            BluetoothFindBroadcastsFragment.this
                    .getActivity()
                    .runOnUiThread(
                            new BluetoothFindBroadcastsFragment$1$$ExternalSyntheticLambda0(
                                    this, 2));
        }

        public final void onSourceFound(
                final BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            Log.d("BtFindBroadcastsFrg", "onSourceFound:");
            BluetoothFindBroadcastsFragment.this
                    .getActivity()
                    .runOnUiThread(
                            new Runnable() { // from class:
                                             // com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$1$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BluetoothFindBroadcastsFragment.AnonymousClass1
                                            anonymousClass1 =
                                                    BluetoothFindBroadcastsFragment.AnonymousClass1
                                                            .this;
                                    BluetoothFindBroadcastsFragment.this
                                            .updateListCategoryFromBroadcastMetadata(
                                                    bluetoothLeBroadcastMetadata, false);
                                }
                            });
        }

        public final void onSourceRemoveFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
            Log.d("BtFindBroadcastsFrg", "onSourceRemoveFailed:");
        }

        public final void onSourceRemoved(BluetoothDevice bluetoothDevice, int i, int i2) {
            Log.d("BtFindBroadcastsFrg", "onSourceRemoved:");
            BluetoothFindBroadcastsFragment.this
                    .getActivity()
                    .runOnUiThread(
                            new BluetoothFindBroadcastsFragment$1$$ExternalSyntheticLambda0(
                                    this, 1));
        }

        public final void onSourceModified(BluetoothDevice bluetoothDevice, int i, int i2) {}

        public final void onSourceModifyFailed(BluetoothDevice bluetoothDevice, int i, int i2) {}
    }
}
