package com.android.settings.bluetooth;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.connecteddevice.BluetoothDashboardFragment;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.SliceBroadcastReceiver;
import com.android.settings.slices.SliceBuilderUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BluetoothSliceBuilder {
    public static final IntentFilter INTENT_FILTER;

    static {
        IntentFilter intentFilter = new IntentFilter();
        INTENT_FILTER = intentFilter;
        intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
    }

    public static Intent getIntent(Context context) {
        String charSequence = context.getText(R.string.bluetooth_settings_title).toString();
        return SliceBuilderUtils.buildSearchResultPageIntent(
                        747,
                        R.string.menu_key_connected_devices,
                        context,
                        BluetoothDashboardFragment.class.getName(),
                        null,
                        charSequence)
                .setClassName(context.getPackageName(), SubSettings.class.getName())
                .setData(new Uri.Builder().appendPath("bluetooth").build());
    }

    public static Slice getSlice(Context context) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean z = defaultAdapter.getState() == 12 || defaultAdapter.getState() == 11;
        CharSequence text = context.getText(R.string.bluetooth_settings);
        IconCompat createWithResource =
                IconCompat.createWithResource(context, android.R.drawable.ic_visibility);
        int defaultColor =
                com.android.settingslib.Utils.getColorAttr(context, android.R.attr.colorAccent)
                        .getDefaultColor();
        PendingIntent broadcast =
                PendingIntent.getBroadcast(
                        context,
                        0,
                        new Intent("com.android.settings.bluetooth.action.BLUETOOTH_MODE_CHANGED")
                                .setClass(context, SliceBroadcastReceiver.class),
                        167772160);
        SliceAction createDeeplink =
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(context, 0, getIntent(context), 67108864),
                        createWithResource,
                        0,
                        text);
        SliceAction sliceAction = new SliceAction(broadcast, null, z);
        ListBuilder listBuilder = new ListBuilder(context, CustomSliceRegistry.BLUETOOTH_URI);
        listBuilder.mImpl.setColor(defaultColor);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = text;
        rowBuilder.mTitleLoading = false;
        rowBuilder.addEndItem(sliceAction);
        rowBuilder.mPrimaryAction = createDeeplink;
        listBuilder.mImpl.addRow(rowBuilder);
        return listBuilder.build();
    }
}
