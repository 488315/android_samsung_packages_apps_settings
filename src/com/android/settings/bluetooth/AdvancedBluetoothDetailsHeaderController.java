package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.fuelgauge.BatteryMeterView;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.graph.ThemedBatteryDrawable$sam$java_lang_Runnable$0;
import com.android.settingslib.utils.StringUtil;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.LayoutPreference;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AdvancedBluetoothDetailsHeaderController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop, OnDestroy, CachedBluetoothDevice.Callback {
    private static final String BATTERY_ESTIMATE = "battery_estimate";
    private static final int CASE_DEVICE_ID = 3;
    private static final int CASE_LOW_BATTERY_LEVEL = 19;
    private static final String DATABASE_BLUETOOTH = "Bluetooth";
    private static final String DATABASE_ID = "id";
    private static final String ESTIMATE_READY = "estimate_ready";
    private static final float HALF_ALPHA = 0.5f;
    private static final int LEFT_DEVICE_ID = 1;
    private static final int LOW_BATTERY_LEVEL = 15;
    private static final int MAIN_DEVICE_ID = 4;
    private static final String PATH = "time_remaining";
    private static final String QUERY_PARAMETER_ADDRESS = "address";
    private static final String QUERY_PARAMETER_BATTERY_ID = "battery_id";
    private static final String QUERY_PARAMETER_BATTERY_LEVEL = "battery_level";
    private static final String QUERY_PARAMETER_TIMESTAMP = "timestamp";
    private static final int RIGHT_DEVICE_ID = 2;
    private static final long TIME_OF_HOUR;
    private static final long TIME_OF_MINUTE;
    BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> mBluetoothDevices;
    private CachedBluetoothDevice mCachedDevice;
    Handler mHandler;
    final Map<String, Bitmap> mIconCache;
    boolean mIsLeftDeviceEstimateReady;
    boolean mIsRightDeviceEstimateReady;
    LayoutPreference mLayoutPreference;
    final BluetoothAdapter.OnMetadataChangedListener mMetadataListener;
    private static final String TAG = "AdvancedBtHeaderCtrl";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        TIME_OF_HOUR = timeUnit.toMillis(3600L);
        TIME_OF_MINUTE = timeUnit.toMillis(60L);
    }

    public AdvancedBluetoothDetailsHeaderController(Context context, String str) {
        super(context, str);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mMetadataListener =
                new BluetoothAdapter
                        .OnMetadataChangedListener() { // from class:
                                                       // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController.1
                    public final void onMetadataChanged(
                            BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                        Log.d(
                                AdvancedBluetoothDetailsHeaderController.TAG,
                                String.format(
                                        "Metadata updated in Device %s: %d = %s.",
                                        bluetoothDevice.getAnonymizedAddress(),
                                        Integer.valueOf(i),
                                        bArr == null ? null : new String(bArr)));
                        AdvancedBluetoothDetailsHeaderController.this.refresh();
                    }
                };
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mIconCache = new HashMap();
    }

    private boolean isUntetheredHeadset(BluetoothDevice bluetoothDevice) {
        return BluetoothUtils.getBooleanMetaData(bluetoothDevice, 6)
                || TextUtils.equals(
                        BluetoothUtils.getStringMetaData(bluetoothDevice, 17),
                        "Untethered Headset");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$refresh$3() {
        return this.mCachedDevice.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$refresh$4() {
        return Boolean.valueOf(!this.mCachedDevice.isConnected() || this.mCachedDevice.isBusy());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Boolean lambda$refresh$5() {
        return Boolean.valueOf(isUntetheredHeadset(this.mCachedDevice.mDevice));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String lambda$refresh$6(Supplier supplier, Supplier supplier2) {
        if (((Boolean) supplier.get()).booleanValue()
                || ((Boolean) supplier2.get()).booleanValue()) {
            return this.mCachedDevice.getConnectionSummary(true);
        }
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return cachedBluetoothDevice.getConnectionSummary(
                BluetoothUtils.getIntMetaData(cachedBluetoothDevice.mDevice, 18) != -1);
    }

    private void lambda$refresh$7(
            Supplier supplier, Supplier supplier2, Supplier supplier3, Supplier supplier4) {
        ((TextView) this.mLayoutPreference.mRootView.findViewById(R.id.entity_header_title))
                .setText((CharSequence) supplier.get());
        TextView textView =
                (TextView)
                        this.mLayoutPreference.mRootView.findViewById(R.id.entity_header_summary);
        if (((Boolean) supplier2.get()).booleanValue()) {
            textView.setText((CharSequence) supplier3.get());
            updateDisconnectLayout();
            return;
        }
        if (!((Boolean) supplier4.get()).booleanValue()) {
            this.mLayoutPreference.mRootView.findViewById(R.id.layout_left).setVisibility(8);
            this.mLayoutPreference.mRootView.findViewById(R.id.layout_right).setVisibility(8);
            textView.setText((CharSequence) supplier3.get());
            updateSubLayout(
                    (LinearLayout)
                            this.mLayoutPreference.mRootView.findViewById(R.id.layout_middle),
                    5,
                    18,
                    20,
                    19,
                    0,
                    4);
            return;
        }
        textView.setText((CharSequence) supplier3.get());
        updateSubLayout(
                (LinearLayout) this.mLayoutPreference.mRootView.findViewById(R.id.layout_left),
                7,
                10,
                21,
                13,
                R.string.bluetooth_left_name,
                1);
        updateSubLayout(
                (LinearLayout) this.mLayoutPreference.mRootView.findViewById(R.id.layout_middle),
                9,
                12,
                23,
                15,
                R.string.bluetooth_middle_name,
                3);
        updateSubLayout(
                (LinearLayout) this.mLayoutPreference.mRootView.findViewById(R.id.layout_right),
                8,
                11,
                22,
                14,
                R.string.bluetooth_right_name,
                2);
        showBothDevicesBatteryPredictionIfNecessary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$registerBluetoothDevice$0(CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice != null) {
            this.mBluetoothDevices.add(cachedBluetoothDevice.mDevice);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerBluetoothDevice$1(
            Set set, BluetoothDevice bluetoothDevice) {
        try {
            if (this.mBluetoothAdapter.addOnMetadataChangedListener(
                    bluetoothDevice, this.mContext.getMainExecutor(), this.mMetadataListener)) {
                return;
            }
            Log.e(TAG, bluetoothDevice.getAnonymizedAddress() + ": add into Listener failed");
            set.add(bluetoothDevice);
        } catch (IllegalArgumentException e) {
            set.add(bluetoothDevice);
            Log.e(TAG, bluetoothDevice.getAnonymizedAddress() + ":" + e.toString());
        } catch (NullPointerException e2) {
            set.add(bluetoothDevice);
            Log.e(TAG, bluetoothDevice.getAnonymizedAddress() + ":" + e2.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$showBatteryPredictionIfNecessary$14(
            int i, int i2, LinearLayout linearLayout) {
        Cursor query =
                this.mContext
                        .getContentResolver()
                        .query(
                                new Uri.Builder()
                                        .scheme("content")
                                        .authority(
                                                this.mContext.getString(
                                                        R.string
                                                                .config_battery_prediction_authority))
                                        .appendPath(PATH)
                                        .appendPath("id")
                                        .appendPath(DATABASE_BLUETOOTH)
                                        .appendQueryParameter(
                                                "address", this.mCachedDevice.mDevice.getAddress())
                                        .appendQueryParameter(
                                                QUERY_PARAMETER_BATTERY_ID, String.valueOf(i))
                                        .appendQueryParameter(
                                                QUERY_PARAMETER_BATTERY_LEVEL, String.valueOf(i2))
                                        .appendQueryParameter(
                                                "timestamp",
                                                String.valueOf(System.currentTimeMillis()))
                                        .build(),
                                new String[] {BATTERY_ESTIMATE, ESTIMATE_READY},
                                null,
                                null,
                                null);
        if (query == null) {
            Log.w(TAG, "showBatteryPredictionIfNecessary() cursor is null!");
            return;
        }
        try {
            query.moveToFirst();
            while (!query.isAfterLast()) {
                int i3 = query.getInt(query.getColumnIndex(ESTIMATE_READY));
                long j = query.getLong(query.getColumnIndex(BATTERY_ESTIMATE));
                if (DEBUG) {
                    Log.d(
                            TAG,
                            "showBatteryTimeIfNecessary() batteryId : "
                                    + i
                                    + ", ESTIMATE_READY : "
                                    + i3
                                    + ", BATTERY_ESTIMATE : "
                                    + j);
                }
                showBatteryPredictionIfNecessary(i3, j, linearLayout);
                if (i == 1) {
                    this.mIsLeftDeviceEstimateReady = i3 == 1;
                } else if (i == 2) {
                    this.mIsRightDeviceEstimateReady = i3 == 1;
                }
                query.moveToNext();
            }
            query.close();
        } catch (Throwable th) {
            query.close();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBatteryPredictionIfNecessary$15(
            LinearLayout linearLayout, int i, long j) {
        TextView textView = (TextView) linearLayout.findViewById(R.id.bt_battery_prediction);
        if (i == 1) {
            textView.setText(StringUtil.formatElapsedTime(this.mContext, j, false, false));
        } else {
            textView.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showBothDevicesBatteryPredictionIfNecessary$16(
            TextView textView, int i, TextView textView2) {
        textView.setVisibility(i);
        textView2.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unRegisterBluetoothDevice$2(
            BluetoothDevice bluetoothDevice) {
        try {
            this.mBluetoothAdapter.removeOnMetadataChangedListener(
                    bluetoothDevice, this.mMetadataListener);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, bluetoothDevice.getAnonymizedAddress() + ":" + e.toString());
        } catch (NullPointerException e2) {
            Log.e(TAG, bluetoothDevice.getAnonymizedAddress() + ":" + e2.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateIcon$17(
            String str, Bitmap bitmap, ImageView imageView) {
        this.mIconCache.put(str, bitmap);
        imageView.setAlpha(1.0f);
        imageView.setImageBitmap(bitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateIcon$18(final String str, final ImageView imageView) {
        Uri parse = Uri.parse(str);
        try {
            this.mContext.getContentResolver().takePersistableUriPermission(parse, 1);
            final Bitmap bitmap =
                    MediaStore.Images.Media.getBitmap(this.mContext.getContentResolver(), parse);
            ThreadUtils.postOnMainThread(
                    new Runnable() { // from class:
                                     // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            AdvancedBluetoothDetailsHeaderController.this.lambda$updateIcon$17(
                                    str, bitmap, imageView);
                        }
                    });
        } catch (IOException e) {
            Log.e(TAG, "Failed to get bitmap for: " + str, e);
        } catch (SecurityException e2) {
            Log.e(TAG, "Failed to take persistable permission for: " + parse, e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean lambda$updateSubLayout$10(
            BluetoothDevice bluetoothDevice, int i) {
        return Boolean.valueOf(BluetoothUtils.getBooleanMetaData(bluetoothDevice, i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer lambda$updateSubLayout$11(
            BluetoothDevice bluetoothDevice, int i, int i2) {
        int intMetaData = BluetoothUtils.getIntMetaData(bluetoothDevice, i);
        if (intMetaData == -1) {
            intMetaData = i2 == 12 ? 19 : 15;
        }
        return Integer.valueOf(intMetaData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$updateSubLayout$12(BluetoothDevice bluetoothDevice) {
        return Boolean.valueOf(isUntetheredHeadset(bluetoothDevice));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer lambda$updateSubLayout$9(
            BluetoothDevice bluetoothDevice, int i) {
        return Integer.valueOf(BluetoothUtils.getIntMetaData(bluetoothDevice, i));
    }

    private void registerBluetoothDevice() {
        if (this.mBluetoothAdapter == null) {
            Log.d(TAG, "No mBluetoothAdapter");
            return;
        }
        if (this.mBluetoothDevices == null) {
            this.mBluetoothDevices = new HashSet();
        }
        this.mBluetoothDevices.clear();
        BluetoothDevice bluetoothDevice = this.mCachedDevice.mDevice;
        if (bluetoothDevice != null) {
            this.mBluetoothDevices.add(bluetoothDevice);
        }
        this.mCachedDevice.mMemberDevices.forEach(
                new AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda1(this, 0));
        if (this.mBluetoothDevices.isEmpty()) {
            Log.d(TAG, "No BT device to register.");
            return;
        }
        this.mCachedDevice.registerCallback(this);
        final HashSet hashSet = new HashSet();
        this.mBluetoothDevices.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AdvancedBluetoothDetailsHeaderController.this
                                .lambda$registerBluetoothDevice$1(hashSet, (BluetoothDevice) obj);
                    }
                });
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) it.next();
            this.mBluetoothDevices.remove(bluetoothDevice2);
            Log.d(TAG, "mBluetoothDevices remove " + bluetoothDevice2.getAnonymizedAddress());
        }
    }

    private void showBatteryIcon(LinearLayout linearLayout, int i, int i2, boolean z) {
        boolean z2 = i <= i2 && !z;
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.bt_battery_icon);
        if (z2) {
            imageView.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_battery_alert_24dp));
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.advanced_bluetooth_battery_width),
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.advanced_bluetooth_battery_height));
            layoutParams.rightMargin =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(R.dimen.advanced_bluetooth_battery_right_margin);
            imageView.setLayoutParams(layoutParams);
        } else {
            imageView.setImageDrawable(createBtBatteryIcon(this.mContext, i, z));
            imageView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        }
        imageView.setVisibility(0);
    }

    private void showBatteryPredictionIfNecessary(
            final LinearLayout linearLayout, final int i, final int i2) {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        AdvancedBluetoothDetailsHeaderController.this
                                .lambda$showBatteryPredictionIfNecessary$14(i, i2, linearLayout);
                    }
                });
    }

    private void unRegisterBluetoothDevice() {
        if (this.mBluetoothAdapter == null) {
            Log.d(TAG, "No mBluetoothAdapter");
            return;
        }
        Set<BluetoothDevice> set = this.mBluetoothDevices;
        if (set == null || set.isEmpty()) {
            Log.d(TAG, "No BT device to unregister.");
            return;
        }
        this.mCachedDevice.unregisterCallback(this);
        this.mBluetoothDevices.forEach(
                new AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda1(this, 1));
        this.mBluetoothDevices.clear();
    }

    private void updateDisconnectLayout() {
        this.mLayoutPreference.mRootView.findViewById(R.id.layout_left).setVisibility(8);
        this.mLayoutPreference.mRootView.findViewById(R.id.layout_right).setVisibility(8);
        LinearLayout linearLayout =
                (LinearLayout) this.mLayoutPreference.mRootView.findViewById(R.id.layout_middle);
        linearLayout.setVisibility(0);
        linearLayout.findViewById(R.id.header_title).setVisibility(8);
        linearLayout.findViewById(R.id.bt_battery_summary).setVisibility(8);
        linearLayout.findViewById(R.id.bt_battery_icon).setVisibility(8);
        String stringMetaData = BluetoothUtils.getStringMetaData(this.mCachedDevice.mDevice, 5);
        if (DEBUG) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "updateDisconnectLayout() iconUri : ", stringMetaData, TAG);
        }
        if (stringMetaData != null) {
            updateIcon((ImageView) linearLayout.findViewById(R.id.header_icon), stringMetaData);
        }
    }

    private void updateSubLayout(
            LinearLayout linearLayout,
            final int i,
            final int i2,
            final int i3,
            final int i4,
            int i5,
            int i6) {
        final int i7 = 0;
        if (linearLayout == null) {
            return;
        }
        final BluetoothDevice bluetoothDevice = this.mCachedDevice.mDevice;
        Supplier memoize =
                Suppliers.memoize(
                        new Supplier() { // from class:
                                         // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda3
                            @Override // com.google.common.base.Supplier
                            public final Object get() {
                                String stringMetaData;
                                Integer lambda$updateSubLayout$9;
                                Boolean lambda$updateSubLayout$10;
                                switch (i7) {
                                    case 0:
                                        stringMetaData =
                                                BluetoothUtils.getStringMetaData(
                                                        bluetoothDevice, i);
                                        return stringMetaData;
                                    case 1:
                                        lambda$updateSubLayout$9 =
                                                AdvancedBluetoothDetailsHeaderController
                                                        .lambda$updateSubLayout$9(
                                                                bluetoothDevice, i);
                                        return lambda$updateSubLayout$9;
                                    default:
                                        lambda$updateSubLayout$10 =
                                                AdvancedBluetoothDetailsHeaderController
                                                        .lambda$updateSubLayout$10(
                                                                bluetoothDevice, i);
                                        return lambda$updateSubLayout$10;
                                }
                            }
                        });
        final int i8 = 1;
        Supplier memoize2 =
                Suppliers.memoize(
                        new Supplier() { // from class:
                                         // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda3
                            @Override // com.google.common.base.Supplier
                            public final Object get() {
                                String stringMetaData;
                                Integer lambda$updateSubLayout$9;
                                Boolean lambda$updateSubLayout$10;
                                switch (i8) {
                                    case 0:
                                        stringMetaData =
                                                BluetoothUtils.getStringMetaData(
                                                        bluetoothDevice, i2);
                                        return stringMetaData;
                                    case 1:
                                        lambda$updateSubLayout$9 =
                                                AdvancedBluetoothDetailsHeaderController
                                                        .lambda$updateSubLayout$9(
                                                                bluetoothDevice, i2);
                                        return lambda$updateSubLayout$9;
                                    default:
                                        lambda$updateSubLayout$10 =
                                                AdvancedBluetoothDetailsHeaderController
                                                        .lambda$updateSubLayout$10(
                                                                bluetoothDevice, i2);
                                        return lambda$updateSubLayout$10;
                                }
                            }
                        });
        final int i9 = 2;
        Supplier memoize3 =
                Suppliers.memoize(
                        new Supplier() { // from class:
                                         // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda3
                            @Override // com.google.common.base.Supplier
                            public final Object get() {
                                String stringMetaData;
                                Integer lambda$updateSubLayout$9;
                                Boolean lambda$updateSubLayout$10;
                                switch (i9) {
                                    case 0:
                                        stringMetaData =
                                                BluetoothUtils.getStringMetaData(
                                                        bluetoothDevice, i4);
                                        return stringMetaData;
                                    case 1:
                                        lambda$updateSubLayout$9 =
                                                AdvancedBluetoothDetailsHeaderController
                                                        .lambda$updateSubLayout$9(
                                                                bluetoothDevice, i4);
                                        return lambda$updateSubLayout$9;
                                    default:
                                        lambda$updateSubLayout$10 =
                                                AdvancedBluetoothDetailsHeaderController
                                                        .lambda$updateSubLayout$10(
                                                                bluetoothDevice, i4);
                                        return lambda$updateSubLayout$10;
                                }
                            }
                        });
        Supplier memoize4 =
                Suppliers.memoize(
                        new Supplier() { // from class:
                                         // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda6
                            @Override // com.google.common.base.Supplier
                            public final Object get() {
                                Integer lambda$updateSubLayout$11;
                                lambda$updateSubLayout$11 =
                                        AdvancedBluetoothDetailsHeaderController
                                                .lambda$updateSubLayout$11(bluetoothDevice, i3, i2);
                                return lambda$updateSubLayout$11;
                            }
                        });
        Supplier memoize5 =
                Suppliers.memoize(
                        new Supplier() { // from class:
                                         // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda7
                            @Override // com.google.common.base.Supplier
                            public final Object get() {
                                Boolean lambda$updateSubLayout$12;
                                lambda$updateSubLayout$12 =
                                        AdvancedBluetoothDetailsHeaderController.this
                                                .lambda$updateSubLayout$12(bluetoothDevice);
                                return lambda$updateSubLayout$12;
                            }
                        });
        Objects.requireNonNull(bluetoothDevice);
        Supplier memoize6 =
                Suppliers.memoize(
                        new AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda8(
                                i7, bluetoothDevice));
        List.of(memoize, memoize2, memoize3, memoize4, memoize5, memoize6);
        boolean z = Utils.DEBUG;
        lambda$updateSubLayout$13(
                linearLayout,
                i,
                i2,
                i3,
                i4,
                i5,
                i6,
                memoize,
                memoize2,
                memoize3,
                memoize4,
                memoize5,
                memoize6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateSubLayoutUi, reason: merged with bridge method [inline-methods] */
    public void lambda$updateSubLayout$13(
            LinearLayout linearLayout,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            Supplier supplier,
            Supplier supplier2,
            Supplier supplier3,
            Supplier supplier4,
            Supplier supplier5,
            Supplier supplier6) {
        BluetoothDevice bluetoothDevice = this.mCachedDevice.mDevice;
        String str = (String) supplier.get();
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.header_icon);
        if (str != null) {
            updateIcon(imageView, str);
        } else {
            Pair btRainbowDrawableWithDescription =
                    BluetoothUtils.getBtRainbowDrawableWithDescription(
                            this.mContext, this.mCachedDevice);
            imageView.setImageDrawable((Drawable) btRainbowDrawableWithDescription.first);
            imageView.setContentDescription((CharSequence) btRainbowDrawableWithDescription.second);
        }
        int intValue = ((Integer) supplier2.get()).intValue();
        boolean booleanValue = ((Boolean) supplier3.get()).booleanValue();
        int intValue2 = ((Integer) supplier4.get()).intValue();
        Log.d(
                TAG,
                "buletoothDevice: "
                        + bluetoothDevice.getAnonymizedAddress()
                        + ", updateSubLayout() icon : "
                        + i
                        + ", battery : "
                        + i2
                        + ", charge : "
                        + i4
                        + ", batteryLevel : "
                        + intValue
                        + ", charging : "
                        + booleanValue
                        + ", iconUri : "
                        + str
                        + ", lowBatteryLevel : "
                        + intValue2);
        if (i6 == 1 || i6 == 2) {
            showBatteryPredictionIfNecessary(linearLayout, i6, intValue);
        }
        TextView textView = (TextView) linearLayout.findViewById(R.id.bt_battery_summary);
        if (((Boolean) supplier5.get()).booleanValue()) {
            if (intValue != -1) {
                linearLayout.setVisibility(0);
                textView.setText(com.android.settingslib.Utils.formatPercentage(intValue));
                textView.setVisibility(0);
                showBatteryIcon(linearLayout, intValue, intValue2, booleanValue);
            } else if (i6 == 4) {
                linearLayout.setVisibility(0);
                linearLayout.findViewById(R.id.bt_battery_icon).setVisibility(8);
                int intValue3 = ((Integer) supplier6.get()).intValue();
                if (intValue3 == -1 || intValue3 == -100) {
                    textView.setVisibility(8);
                } else {
                    textView.setText(com.android.settingslib.Utils.formatPercentage(intValue3));
                    textView.setVisibility(0);
                }
            } else {
                linearLayout.setVisibility(8);
            }
        } else if (intValue != -1) {
            linearLayout.setVisibility(0);
            textView.setText(com.android.settingslib.Utils.formatPercentage(intValue));
            textView.setVisibility(0);
            showBatteryIcon(linearLayout, intValue, intValue2, booleanValue);
        } else {
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView) linearLayout.findViewById(R.id.header_title);
        if (i6 == 4) {
            textView2.setVisibility(8);
        } else {
            textView2.setText(i5);
            textView2.setVisibility(0);
        }
    }

    public Drawable createBtBatteryIcon(Context context, int i, boolean z) {
        BatteryMeterView.BatteryMeterDrawable batteryMeterDrawable =
                new BatteryMeterView.BatteryMeterDrawable(
                        context,
                        context.getColor(R.color.meter_background_color),
                        context.getResources()
                                .getDimensionPixelSize(
                                        R.dimen.advanced_bluetooth_battery_meter_width),
                        context.getResources()
                                .getDimensionPixelSize(
                                        R.dimen.advanced_bluetooth_battery_meter_height));
        batteryMeterDrawable.setBatteryLevel(i);
        batteryMeterDrawable.setColorFilter(
                new PorterDuffColorFilter(
                        com.android.settingslib.Utils.getColorAttrDefaultColor(
                                context, android.R.attr.colorControlNormal),
                        PorterDuff.Mode.SRC));
        batteryMeterDrawable.charging = z;
        batteryMeterDrawable.unscheduleSelf(
                new ThemedBatteryDrawable$sam$java_lang_Runnable$0(
                        batteryMeterDrawable.invalidateRunnable));
        batteryMeterDrawable.scheduleSelf(
                new ThemedBatteryDrawable$sam$java_lang_Runnable$0(
                        batteryMeterDrawable.invalidateRunnable),
                0L);
        return batteryMeterDrawable;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mLayoutPreference = layoutPreference;
        layoutPreference.setVisible(isAvailable());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return (cachedBluetoothDevice != null
                        && BluetoothUtils.isAdvancedDetailsHeader(cachedBluetoothDevice.mDevice))
                ? 0
                : 2;
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

    public void init(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mCachedDevice = cachedBluetoothDevice;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        for (Bitmap bitmap : this.mIconCache.values()) {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        this.mIconCache.clear();
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public void onDeviceAttributesChanged() {
        if (this.mCachedDevice != null) {
            refresh();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (isAvailable()) {
            registerBluetoothDevice();
            refresh();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        unRegisterBluetoothDevice();
    }

    public void refresh() {
        if (this.mLayoutPreference == null || this.mCachedDevice == null) {
            return;
        }
        Supplier memoize =
                Suppliers.memoize(
                        new AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda8(
                                1, this));
        final Supplier memoize2 =
                Suppliers.memoize(
                        new AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda8(
                                2, this));
        final Supplier memoize3 =
                Suppliers.memoize(
                        new AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda8(
                                3, this));
        Supplier memoize4 =
                Suppliers.memoize(
                        new Supplier() { // from class:
                                         // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda14
                            @Override // com.google.common.base.Supplier
                            public final Object get() {
                                String lambda$refresh$6;
                                lambda$refresh$6 =
                                        AdvancedBluetoothDetailsHeaderController.this
                                                .lambda$refresh$6(memoize2, memoize3);
                                return lambda$refresh$6;
                            }
                        });
        List.of(memoize, memoize2, memoize3, memoize4);
        boolean z = Utils.DEBUG;
        lambda$refresh$7(memoize, memoize2, memoize4, memoize3);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void showBothDevicesBatteryPredictionIfNecessary() {
        final TextView textView =
                (TextView)
                        this.mLayoutPreference
                                .mRootView
                                .findViewById(R.id.layout_left)
                                .findViewById(R.id.bt_battery_prediction);
        final TextView textView2 =
                (TextView)
                        this.mLayoutPreference
                                .mRootView
                                .findViewById(R.id.layout_right)
                                .findViewById(R.id.bt_battery_prediction);
        final int i = (this.mIsLeftDeviceEstimateReady && this.mIsRightDeviceEstimateReady) ? 0 : 8;
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        AdvancedBluetoothDetailsHeaderController
                                .lambda$showBothDevicesBatteryPredictionIfNecessary$16(
                                        textView, i, textView2);
                    }
                });
    }

    public void updateIcon(final ImageView imageView, final String str) {
        if (this.mIconCache.containsKey(str)) {
            imageView.setAlpha(1.0f);
            imageView.setImageBitmap(this.mIconCache.get(str));
        } else {
            imageView.setAlpha(HALF_ALPHA);
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AdvancedBluetoothDetailsHeaderController.this.lambda$updateIcon$18(
                                    str, imageView);
                        }
                    });
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public void showBatteryPredictionIfNecessary(
            final int i, final long j, final LinearLayout linearLayout) {
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.bluetooth.AdvancedBluetoothDetailsHeaderController$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        AdvancedBluetoothDetailsHeaderController.this
                                .lambda$showBatteryPredictionIfNecessary$15(linearLayout, i, j);
                    }
                });
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
