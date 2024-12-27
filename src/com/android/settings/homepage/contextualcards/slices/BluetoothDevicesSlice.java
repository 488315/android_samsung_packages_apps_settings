package com.android.settings.homepage.contextualcards.slices;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.Utils;
import com.android.settings.bluetooth.AvailableMediaBluetoothDeviceUpdater;
import com.android.settings.bluetooth.BluetoothDeviceDetailsFragment;
import com.android.settings.bluetooth.BluetoothPairingDetail;
import com.android.settings.bluetooth.SavedBluetoothDeviceUpdater;
import com.android.settings.connecteddevice.ConnectedDeviceDashboardFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settings.slices.SliceBroadcastReceiver;
import com.android.settings.slices.SliceBuilderUtils;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothDevicesSlice implements CustomSliceable {

    @VisibleForTesting
    static final String BLUETOOTH_DEVICE_HASH_CODE = "bluetooth_device_hash_code";

    public static final Comparator COMPARATOR = Comparator.naturalOrder();

    @VisibleForTesting static final int DEFAULT_EXPANDED_ROW_COUNT = 2;

    @VisibleForTesting static final String EXTRA_ENABLE_BLUETOOTH = "enable_bluetooth";
    public static boolean sBluetoothEnabling;
    public AvailableMediaBluetoothDeviceUpdater mAvailableMediaBtDeviceUpdater;
    public final Context mContext;
    public SavedBluetoothDeviceUpdater mSavedBtDeviceUpdater;

    public BluetoothDevicesSlice(Context context) {
        this.mContext = context;
        if (BluetoothUpdateWorker.sLocalBluetoothManager == null) {
            BluetoothUpdateWorker.LoadBtManagerHandler m939$$Nest$smgetInstance =
                    BluetoothUpdateWorker.LoadBtManagerHandler.m939$$Nest$smgetInstance(context);
            BluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0
                    bluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0 =
                            m939$$Nest$smgetInstance.mLoadBtManagerTask;
            if (m939$$Nest$smgetInstance.hasCallbacks(
                    bluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0)) {
                return;
            }
            m939$$Nest$smgetInstance.post(
                    bluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0);
        }
    }

    @VisibleForTesting
    public SliceAction buildBluetoothDetailDeepLinkAction(
            CachedBluetoothDevice cachedBluetoothDevice) {
        return SliceAction.createDeeplink(
                getBluetoothDetailIntent(cachedBluetoothDevice),
                IconCompat.createWithResource(this.mContext, R.drawable.ic_settings_accent),
                0,
                cachedBluetoothDevice.getName());
    }

    @VisibleForTesting
    public SliceAction buildPrimaryBluetoothAction(CachedBluetoothDevice cachedBluetoothDevice) {
        return new SliceAction(
                PendingIntent.getBroadcast(
                        this.mContext,
                        cachedBluetoothDevice.hashCode(),
                        new Intent(CustomSliceRegistry.BLUETOOTH_DEVICES_SLICE_URI.toString())
                                .setClass(this.mContext, SliceBroadcastReceiver.class)
                                .putExtra(
                                        BLUETOOTH_DEVICE_HASH_CODE,
                                        cachedBluetoothDevice.hashCode()),
                        67108864),
                getBluetoothDeviceIcon(cachedBluetoothDevice),
                0,
                cachedBluetoothDevice.getName());
    }

    @Override // com.android.settings.slices.Sliceable
    public final Class getBackgroundWorkerClass() {
        return BluetoothUpdateWorker.class;
    }

    @VisibleForTesting
    public PendingIntent getBluetoothDetailIntent(CachedBluetoothDevice cachedBluetoothDevice) {
        Bundle bundle = new Bundle();
        bundle.putString("device_address", cachedBluetoothDevice.mDevice.getAddress());
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = BluetoothDeviceDetailsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.device_details_title, null);
        launchRequest.mSourceMetricsCategory =
                EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE;
        return PendingIntent.getActivity(
                this.mContext,
                cachedBluetoothDevice.hashCode(),
                subSettingLauncher.toIntent(),
                67108864);
    }

    @VisibleForTesting
    public IconCompat getBluetoothDeviceIcon(CachedBluetoothDevice cachedBluetoothDevice) {
        Drawable drawable =
                (Drawable)
                        BluetoothUtils.getBtRainbowDrawableWithDescription(
                                        this.mContext, cachedBluetoothDevice)
                                .first;
        return drawable == null
                ? IconCompat.createWithResource(this.mContext, android.R.drawable.ic_visibility)
                : Utils.createIconWithDrawable(drawable);
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        return SliceBuilderUtils.buildSearchResultPageIntent(
                        VolteConstants.ErrorCode.RTP_TIME_OUT,
                        R.string.menu_key_connected_devices,
                        this.mContext,
                        ConnectedDeviceDashboardFragment.class.getName(),
                        ApnSettings.MVNO_NONE,
                        this.mContext
                                .getText(R.string.connected_devices_dashboard_title)
                                .toString())
                .setClassName(this.mContext.getPackageName(), SubSettings.class.getName())
                .setData(CustomSliceRegistry.BLUETOOTH_DEVICES_SLICE_URI);
    }

    @VisibleForTesting
    public List<CachedBluetoothDevice> getPairedBluetoothDevices() {
        ArrayList arrayList = new ArrayList();
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Log.i("BluetoothDevicesSlice", "Cannot get Bluetooth devices, Bluetooth is disabled.");
            return arrayList;
        }
        LocalBluetoothManager localBluetoothManager = BluetoothUpdateWorker.sLocalBluetoothManager;
        if (localBluetoothManager != null) {
            return (List)
                    localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy().stream()
                            .filter(new BluetoothDevicesSlice$$ExternalSyntheticLambda0())
                            .sorted(COMPARATOR)
                            .collect(Collectors.toList());
        }
        Log.i("BluetoothDevicesSlice", "Cannot get Bluetooth devices, Bluetooth is not ready.");
        return arrayList;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.i("BluetoothDevicesSlice", "Bluetooth is not supported on this hardware platform");
            return null;
        }
        Context context = this.mContext;
        Uri uri = CustomSliceRegistry.BLUETOOTH_DEVICES_SLICE_URI;
        final ListBuilder listBuilder = new ListBuilder(context, uri);
        listBuilder.mImpl.setColor(-1);
        int state = defaultAdapter.getState();
        if (!(state == 11 || state == 12) && !sBluetoothEnabling) {
            Drawable drawable = this.mContext.getDrawable(R.drawable.ic_bluetooth_disabled);
            Context context2 = this.mContext;
            drawable.setTint(
                    com.android.settingslib.Utils.getDisabled(
                            context2,
                            com.android.settingslib.Utils.getColorAttrDefaultColor(
                                    context2, android.R.attr.colorControlNormal)));
            IconCompat createIconWithDrawable = Utils.createIconWithDrawable(drawable);
            CharSequence text = this.mContext.getText(R.string.bluetooth_devices_card_off_title);
            CharSequence text2 = this.mContext.getText(R.string.bluetooth_devices_card_off_summary);
            SliceAction sliceAction =
                    new SliceAction(
                            PendingIntent.getBroadcast(
                                    this.mContext,
                                    0,
                                    new Intent(uri.toString())
                                            .setClass(this.mContext, SliceBroadcastReceiver.class)
                                            .putExtra(EXTRA_ENABLE_BLUETOOTH, true),
                                    67108864),
                            createIconWithDrawable,
                            0,
                            text);
            ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
            rowBuilder.setTitleItem(createIconWithDrawable);
            rowBuilder.mTitle = text;
            rowBuilder.mTitleLoading = false;
            rowBuilder.mSubtitle = text2;
            rowBuilder.mSubtitleLoading = false;
            rowBuilder.mPrimaryAction = sliceAction;
            listBuilder.mImpl.addRow(rowBuilder);
            return listBuilder.build();
        }
        sBluetoothEnabling = false;
        Drawable drawable2 = this.mContext.getDrawable(android.R.drawable.ic_visibility);
        drawable2.setTint(
                com.android.settingslib.Utils.getColorAttrDefaultColor(
                        this.mContext, android.R.attr.colorAccent));
        IconCompat createIconWithDrawable2 = Utils.createIconWithDrawable(drawable2);
        CharSequence text3 = this.mContext.getText(R.string.bluetooth_devices);
        SliceAction createDeeplink =
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(this.mContext, 0, getIntent(), 67108864),
                        createIconWithDrawable2,
                        0,
                        text3);
        ListBuilder.RowBuilder rowBuilder2 = new ListBuilder.RowBuilder();
        rowBuilder2.setTitleItem(createIconWithDrawable2);
        rowBuilder2.mTitle = text3;
        rowBuilder2.mTitleLoading = false;
        rowBuilder2.mPrimaryAction = createDeeplink;
        Drawable drawable3 = this.mContext.getDrawable(R.drawable.ic_add_24dp);
        drawable3.setTint(
                com.android.settingslib.Utils.getColorAttrDefaultColor(
                        this.mContext, android.R.attr.colorAccent));
        IconCompat createIconWithDrawable3 = Utils.createIconWithDrawable(drawable3);
        String string = this.mContext.getString(R.string.bluetooth_pairing_pref_title);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = BluetoothPairingDetail.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.bluetooth_pairing_page_title, null);
        launchRequest.mSourceMetricsCategory = 1018;
        Intent intent = subSettingLauncher.toIntent();
        rowBuilder2.addEndItem(
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(
                                this.mContext, intent.hashCode(), intent, 67108864),
                        createIconWithDrawable3,
                        0,
                        string));
        listBuilder.addRow(rowBuilder2);
        ArrayList arrayList = new ArrayList();
        List<CachedBluetoothDevice> pairedBluetoothDevices = getPairedBluetoothDevices();
        if (!pairedBluetoothDevices.isEmpty()) {
            if (this.mAvailableMediaBtDeviceUpdater == null) {
                this.mAvailableMediaBtDeviceUpdater =
                        new AvailableMediaBluetoothDeviceUpdater(this.mContext, null, 0);
            }
            if (this.mSavedBtDeviceUpdater == null) {
                this.mSavedBtDeviceUpdater =
                        new SavedBluetoothDeviceUpdater(this.mContext, null, false, 0);
            }
            for (CachedBluetoothDevice cachedBluetoothDevice : pairedBluetoothDevices) {
                if (arrayList.size() >= 2) {
                    break;
                }
                String connectionSummary = cachedBluetoothDevice.getConnectionSummary();
                if (connectionSummary == null) {
                    connectionSummary =
                            this.mContext.getString(
                                    R.string.connected_device_previously_connected_screen_title);
                }
                ListBuilder.RowBuilder rowBuilder3 = new ListBuilder.RowBuilder();
                rowBuilder3.setTitleItem(getBluetoothDeviceIcon(cachedBluetoothDevice));
                rowBuilder3.mTitle = cachedBluetoothDevice.getName();
                rowBuilder3.mTitleLoading = false;
                rowBuilder3.mSubtitle = connectionSummary;
                rowBuilder3.mSubtitleLoading = false;
                if (this.mAvailableMediaBtDeviceUpdater.isFilterMatched(cachedBluetoothDevice)
                        || this.mSavedBtDeviceUpdater.isFilterMatched(cachedBluetoothDevice)) {
                    rowBuilder3.mPrimaryAction = buildPrimaryBluetoothAction(cachedBluetoothDevice);
                    rowBuilder3.addEndItem(
                            buildBluetoothDetailDeepLinkAction(cachedBluetoothDevice));
                } else {
                    rowBuilder3.mPrimaryAction =
                            buildBluetoothDetailDeepLinkAction(cachedBluetoothDevice);
                }
                arrayList.add(rowBuilder3);
            }
        }
        arrayList.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.homepage.contextualcards.slices.BluetoothDevicesSlice$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Comparator comparator = BluetoothDevicesSlice.COMPARATOR;
                        ListBuilder.this.addRow((ListBuilder.RowBuilder) obj);
                    }
                });
        return listBuilder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return R.string.menu_key_connected_devices;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.BLUETOOTH_DEVICES_SLICE_URI;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final void onNotifyChange(Intent intent) {
        if (intent.getBooleanExtra(EXTRA_ENABLE_BLUETOOTH, false)) {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            int state = defaultAdapter.getState();
            if (state == 11 || state == 12) {
                return;
            }
            sBluetoothEnabling = true;
            defaultAdapter.enable();
            this.mContext
                    .getContentResolver()
                    .notifyChange(CustomSliceRegistry.BLUETOOTH_DEVICES_SLICE_URI, null);
            return;
        }
        int intExtra = intent.getIntExtra(BLUETOOTH_DEVICE_HASH_CODE, -1);
        for (CachedBluetoothDevice cachedBluetoothDevice : getPairedBluetoothDevices()) {
            if (cachedBluetoothDevice.hashCode() == intExtra) {
                if (cachedBluetoothDevice.isConnected()) {
                    cachedBluetoothDevice.setActive();
                    return;
                } else {
                    if (cachedBluetoothDevice.isBusy()) {
                        return;
                    }
                    cachedBluetoothDevice.connect$1();
                    return;
                }
            }
        }
    }
}
