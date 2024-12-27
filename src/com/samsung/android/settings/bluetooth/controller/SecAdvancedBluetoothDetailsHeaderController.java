package com.samsung.android.settings.bluetooth.controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils;
import com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAdvancedBluetoothDetailsHeaderController extends BasePreferenceController
        implements LifecycleObserver,
                OnResume,
                OnPause,
                OnDestroy,
                CachedBluetoothDevice.Callback,
                BluetoothSmepReceiver.SmepCallBack {
    private static final int BATTERY_RANGE = 15;
    private static final int CASE_LOW_BATTERY_LEVEL = 19;
    private static final boolean DBG = true;
    private static final int LOW_BATTERY_LEVEL = 15;
    private static final String TAG = "SecAdvancedBtHeaderCtrl";
    private static final int WEARING_STATE_IN_CLOSE_CASE = 4;
    private static final int WEARING_STATE_IN_EAR = 1;
    private static final int WEARING_STATE_IN_OPEN_CASE = 3;
    private static final int WEARING_STATE_OUTSIDE = 2;
    private static final int WEARING_STATE_UNKNOWN = 0;
    BluetoothAdapter mBluetoothAdapter;
    private CachedBluetoothDevice mCachedDevice;
    private BluetoothDevice mDevice;
    Handler mHandler;
    final Map<String, Bitmap> mIconCache;
    boolean mIsInCase;
    boolean mIsRegisterCallback;
    boolean mIsSingleBattery;
    LayoutPreference mLayoutPreference;
    private BluetoothSmepReceiver mSmepReceiver;

    public SecAdvancedBluetoothDetailsHeaderController(Context context, String str) {
        super(context, str);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mIsRegisterCallback = false;
        this.mIsSingleBattery = false;
        this.mIsInCase = false;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mIconCache = new HashMap();
    }

    private Drawable getBatteryDrawable(int i) {
        return i <= 0
                ? this.mContext.getDrawable(R.drawable.ic_bluetooth_battery_disconnect)
                : i <= 10
                        ? this.mContext.getDrawable(R.drawable.ic_bluetooth_battery_10)
                        : i <= 30
                                ? this.mContext.getDrawable(R.drawable.ic_bluetooth_battery_30)
                                : i <= 55
                                        ? this.mContext.getDrawable(
                                                R.drawable.ic_bluetooth_battery_55)
                                        : i <= 80
                                                ? this.mContext.getDrawable(
                                                        R.drawable.ic_bluetooth_battery_80)
                                                : this.mContext.getDrawable(
                                                        R.drawable.ic_bluetooth_battery_100);
    }

    private int getDeviceIconIndex() {
        ManufacturerData manufacturerData;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice == null
                || (manufacturerData = cachedBluetoothDevice.mManufacturerData) == null) {
            return 0;
        }
        return manufacturerData.getDeviceIcon();
    }

    private Drawable getHeaderCaseDrawable() {
        String resourcePath = this.mCachedDevice.getResourcePath();
        Context context = this.mContext;
        String str = ScspUtils.FILE_PATH_ROOT;
        Log.d("ScspUtils", "getCaseIcon: path = " + resourcePath);
        BitmapDrawable icon =
                ScspUtils.getIcon(
                        context,
                        resourcePath + ScspUtils.FILE_NAME_CASE + ScspUtils.FILE_EXTENSION_SVG);
        if (icon != null) {
            return icon;
        }
        int deviceIconIndex = getDeviceIconIndex();
        return (deviceIconIndex == 0 || deviceIconIndex != R.drawable.list_ic_earbuds_stem)
                ? this.mContext.getResources().getDrawable(R.drawable.ic_bluetooth_earbuds_case)
                : this.mContext
                        .getResources()
                        .getDrawable(R.drawable.ic_bluetooth_earbuds_stem_case);
    }

    private Drawable getHeaderLeftDrawable() {
        String resourcePath = this.mCachedDevice.getResourcePath();
        Context context = this.mContext;
        String str = ScspUtils.FILE_PATH_ROOT;
        Log.d("ScspUtils", "getLeftIcon: path = " + resourcePath);
        BitmapDrawable icon =
                ScspUtils.getIcon(
                        context,
                        resourcePath + ScspUtils.FILE_NAME_LEFT + ScspUtils.FILE_EXTENSION_SVG);
        if (icon != null) {
            return icon;
        }
        int deviceIconIndex = getDeviceIconIndex();
        return (deviceIconIndex == 0 || deviceIconIndex != R.drawable.list_ic_earbuds_stem)
                ? this.mIsSingleBattery
                        ? this.mContext
                                .getResources()
                                .getDrawable(R.drawable.ic_bluetooth_earbuds_left_right)
                        : this.mContext
                                .getResources()
                                .getDrawable(R.drawable.ic_bluetooth_earbuds_left)
                : this.mIsSingleBattery
                        ? this.mContext
                                .getResources()
                                .getDrawable(R.drawable.ic_bluetooth_earbuds_stem_left_right)
                        : this.mContext
                                .getResources()
                                .getDrawable(R.drawable.ic_bluetooth_earbuds_stem_left);
    }

    private Drawable getHeaderRightDrawable() {
        String resourcePath = this.mCachedDevice.getResourcePath();
        Context context = this.mContext;
        String str = ScspUtils.FILE_PATH_ROOT;
        Log.d("ScspUtils", "getRightIcon: path = " + resourcePath);
        BitmapDrawable icon =
                ScspUtils.getIcon(
                        context,
                        resourcePath + ScspUtils.FILE_NAME_RIGHT + ScspUtils.FILE_EXTENSION_SVG);
        if (icon != null) {
            return icon;
        }
        int deviceIconIndex = getDeviceIconIndex();
        return (deviceIconIndex == 0 || deviceIconIndex != R.drawable.list_ic_earbuds_stem)
                ? this.mContext.getResources().getDrawable(R.drawable.ic_bluetooth_earbuds_right)
                : this.mContext
                        .getResources()
                        .getDrawable(R.drawable.ic_bluetooth_earbuds_stem_right);
    }

    private boolean isDimState(int i) {
        return i < 0 || i > 100;
    }

    private void setBatteryArcProgress(LinearLayout linearLayout, int i, int i2) {
        ProgressBar progressBar = (ProgressBar) linearLayout.findViewById(i);
        if (progressBar != null) {
            progressBar.setProgress(i2);
            if (i2 < 30) {
                progressBar.setProgressTintList(
                        this.mContext.getColorStateList(
                                R.color.sec_bluetooth_battery_progress_low_color));
            } else {
                progressBar.setProgressTintList(
                        this.mContext.getColorStateList(
                                R.color.sec_bluetooth_battery_progress_color));
            }
        }
    }

    private void setBatteryIcon(LinearLayout linearLayout, int i, int i2) {
        ImageView imageView = (ImageView) linearLayout.findViewById(i);
        if (imageView != null) {
            imageView.setVisibility(0);
            imageView.setImageDrawable(getBatteryDrawable(i2));
        }
    }

    private void setBatteryLevel(LinearLayout linearLayout, int i, int i2) {
        TextView textView = (TextView) linearLayout.findViewById(i);
        if (textView != null) {
            if (isDimState(i2)) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
            }
            textView.setText(Utils.formatPercentage(i2));
            if (i == R.id.bt_battery_summary_cradle) {
                textView.setContentDescription(
                        this.mContext.getString(R.string.sec_bluetooth_battery_cradle)
                                + " "
                                + ((Object) textView.getText()));
                return;
            }
            if (linearLayout.equals(
                            this.mLayoutPreference.mRootView.findViewById(R.id.layout_one_battery))
                    || linearLayout.equals(
                            this.mLayoutPreference.mRootView.findViewById(
                                    R.id.layout_no_cradle_one_battery))) {
                StringBuilder sb = new StringBuilder();
                TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                        this.mContext, R.string.sec_bluetooth_battery_left, sb, " ");
                sb.append(this.mContext.getString(R.string.sec_bluetooth_battery_right));
                sb.append(" ");
                sb.append((Object) textView.getText());
                textView.setContentDescription(sb.toString());
                return;
            }
            if (i == R.id.bt_battery_summary_left) {
                textView.setContentDescription(
                        this.mContext.getString(R.string.sec_bluetooth_battery_left)
                                + " "
                                + ((Object) textView.getText()));
                return;
            }
            if (i != R.id.bt_battery_summary_right) {
                Log.d(TAG, "setBatteryLevel wrong viewById");
                return;
            }
            textView.setContentDescription(
                    this.mContext.getString(R.string.sec_bluetooth_battery_right)
                            + " "
                            + ((Object) textView.getText()));
        }
    }

    private void setHeaderIcon() {
        int deviceIconIndex = getDeviceIconIndex();
        if (deviceIconIndex != 0) {
            if (deviceIconIndex == R.drawable.list_ic_earbuds_stem) {
                this.mLayoutPreference
                        .mRootView
                        .findViewById(R.id.bluetooth_title_top_icon_header_imageview)
                        .setBackground(
                                this.mContext
                                        .getResources()
                                        .getDrawable(
                                                R.drawable.ic_bluetooth_earbuds_stem_left_right));
            } else {
                this.mLayoutPreference
                        .mRootView
                        .findViewById(R.id.bluetooth_title_top_icon_header_imageview)
                        .setBackground(
                                this.mContext
                                        .getResources()
                                        .getDrawable(R.drawable.ic_bluetooth_earbuds_left_right));
            }
        }
        View findViewById =
                this.mLayoutPreference.mRootView.findViewById(
                        R.id.bluetooth_title_top_icon_header_view);
        if (findViewById != null) {
            findViewById.setBackground(
                    this.mContext
                            .getResources()
                            .getDrawable(
                                    R.drawable.ic_bluetooth_battery_header,
                                    this.mContext.getTheme()));
            findViewById.setAlpha(1.0f);
        }
    }

    private void setHeaderText(LinearLayout linearLayout, int i, int i2, int i3) {
        TextView textView = (TextView) linearLayout.findViewById(i);
        if (textView != null) {
            if (isDimState(i2)) {
                textView.setAlpha(0.45f);
            } else {
                textView.setAlpha(1.0f);
            }
            textView.setText(this.mContext.getResources().getString(i3));
        }
    }

    private void setLayoutGone() {
        this.mLayoutPreference.mRootView.findViewById(R.id.layout_connected).setVisibility(8);
        this.mLayoutPreference.mRootView.findViewById(R.id.layout_no_cradle).setVisibility(8);
        this.mLayoutPreference
                .mRootView
                .findViewById(R.id.layout_no_cradle_one_battery)
                .setVisibility(8);
        this.mLayoutPreference.mRootView.findViewById(R.id.layout_one_battery).setVisibility(8);
    }

    private void setLayoutResource(LinearLayout linearLayout, int i) {
        setLayoutResource(linearLayout, i, -1);
    }

    private void updateDisconnectLayout() {
        int deviceIconIndex;
        LinearLayout linearLayout =
                (LinearLayout) this.mLayoutPreference.mRootView.findViewById(R.id.layout_connected);
        this.mIsSingleBattery = false;
        this.mIsInCase = false;
        setLayoutGone();
        linearLayout.setVisibility(0);
        ProgressBar progressBar =
                (ProgressBar) linearLayout.findViewById(R.id.header_icon_progress_left);
        ProgressBar progressBar2 =
                (ProgressBar) linearLayout.findViewById(R.id.header_icon_progress_right);
        ProgressBar progressBar3 =
                (ProgressBar) linearLayout.findViewById(R.id.header_icon_progress_cradle);
        if (progressBar != null) {
            progressBar.setProgress(0);
        }
        if (progressBar2 != null) {
            progressBar2.setProgress(0);
        }
        if (progressBar3 != null) {
            progressBar3.setProgress(0);
        }
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.header_icon_left);
        ImageView imageView2 = (ImageView) linearLayout.findViewById(R.id.header_icon_right);
        ImageView imageView3 = (ImageView) linearLayout.findViewById(R.id.header_icon_cradle);
        if (imageView != null) {
            imageView.setImageDrawable(getHeaderLeftDrawable());
            imageView.getDrawable().setAlpha(114);
        }
        if (imageView2 != null) {
            imageView2.setImageDrawable(getHeaderRightDrawable());
            imageView2.getDrawable().setAlpha(114);
        }
        if (imageView3 != null) {
            imageView3.setImageDrawable(getHeaderCaseDrawable());
            imageView3.getDrawable().setAlpha(114);
        }
        TextView textView = (TextView) linearLayout.findViewById(R.id.header_title_left);
        TextView textView2 = (TextView) linearLayout.findViewById(R.id.header_title_right);
        TextView textView3 = (TextView) linearLayout.findViewById(R.id.header_title_cradle);
        if (textView != null) {
            textView.setAlpha(0.45f);
            textView.setText(
                    this.mContext.getResources().getString(R.string.sec_bluetooth_battery_left));
        }
        if (textView2 != null) {
            textView2.setAlpha(0.45f);
            textView2.setText(
                    this.mContext.getResources().getString(R.string.sec_bluetooth_battery_right));
        }
        if (textView3 != null) {
            textView3.setAlpha(0.45f);
            textView3.setText(
                    this.mContext.getResources().getString(R.string.sec_bluetooth_battery_cradle));
        }
        TextView textView4 = (TextView) linearLayout.findViewById(R.id.bt_battery_summary_left);
        TextView textView5 = (TextView) linearLayout.findViewById(R.id.bt_battery_summary_right);
        TextView textView6 = (TextView) linearLayout.findViewById(R.id.bt_battery_summary_cradle);
        if (textView4 != null) {
            textView4.setVisibility(8);
        }
        if (textView5 != null) {
            textView5.setVisibility(8);
        }
        if (textView6 != null) {
            textView6.setVisibility(8);
        }
        View findViewById =
                this.mLayoutPreference.mRootView.findViewById(
                        R.id.bluetooth_title_top_icon_header_view);
        ImageView imageView4 =
                (ImageView)
                        this.mLayoutPreference.mRootView.findViewById(
                                R.id.bluetooth_title_top_icon_header_imageview);
        if (findViewById != null) {
            findViewById.setBackground(
                    this.mContext
                            .getResources()
                            .getDrawable(
                                    R.drawable.ic_bluetooth_battery_header_disconnect,
                                    this.mContext.getTheme()));
        }
        if (imageView4 == null || (deviceIconIndex = getDeviceIconIndex()) == 0) {
            return;
        }
        if (deviceIconIndex == R.drawable.list_ic_earbuds_stem) {
            imageView4.setBackground(
                    this.mContext
                            .getResources()
                            .getDrawable(
                                    R.drawable.ic_bluetooth_earbuds_stem_left_right_disconnect));
        } else {
            imageView4.setBackground(
                    this.mContext
                            .getResources()
                            .getDrawable(R.drawable.ic_bluetooth_earbuds_left_right_disconnect));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x009e A[Catch: NullPointerException -> 0x00f7, TryCatch #0 {NullPointerException -> 0x00f7, blocks: (B:3:0x000f, B:8:0x0094, B:11:0x009a, B:13:0x009e, B:19:0x00a9, B:20:0x00ab, B:25:0x00b6, B:26:0x00ed, B:29:0x00f3, B:33:0x00c4, B:37:0x00d5, B:38:0x00e0), top: B:2:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateSubLayout() {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.controller.SecAdvancedBluetoothDetailsHeaderController.updateSubLayout():void");
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
        return BluetoothUtils.isSupportSmep(this.mDevice) ? 0 : 3;
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

    public void init(
            CachedBluetoothDevice cachedBluetoothDevice,
            BluetoothSmepReceiver bluetoothSmepReceiver) {
        this.mCachedDevice = cachedBluetoothDevice;
        this.mDevice = cachedBluetoothDevice.mDevice;
        this.mSmepReceiver = bluetoothSmepReceiver;
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
        Log.d(TAG, "Attri");
        if (this.mCachedDevice != null) {
            refresh();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (this.mIsRegisterCallback) {
            this.mCachedDevice.unregisterCallback(this);
            this.mSmepReceiver.unRegisterCallback(this);
            this.mIsRegisterCallback = false;
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (!isAvailable()) {
            this.mLayoutPreference.setVisible(false);
        } else {
            this.mLayoutPreference.setVisible(true);
            refresh();
        }
    }

    public void refresh() {
        if (this.mLayoutPreference == null || this.mCachedDevice == null) {
            return;
        }
        if (!isAvailable()) {
            this.mLayoutPreference.setVisible(false);
            return;
        }
        if (!this.mIsRegisterCallback) {
            this.mIsRegisterCallback = true;
            this.mCachedDevice.registerCallback(this);
            this.mSmepReceiver.registerCallback(this);
        }
        this.mLayoutPreference.setVisible(true);
        TextView textView =
                (TextView)
                        this.mLayoutPreference.mRootView.findViewById(R.id.bluetooth_title_header);
        if (textView != null) {
            textView.setText(this.mCachedDevice.getName());
        }
        TextView textView2 =
                (TextView)
                        this.mLayoutPreference.mRootView.findViewById(
                                R.id.bluetooth_subtitle_header);
        if (textView2 != null) {
            if (this.mCachedDevice.isConnected()) {
                textView2.setText(this.mContext.getString(R.string.sec_bluetooth_connected));
            } else {
                textView2.setText(this.mContext.getString(R.string.bluetooth_disconnected));
            }
        }
        if (this.mCachedDevice.isConnected()) {
            updateSubLayout();
        } else {
            updateDisconnectLayout();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public void updateBatteryState(BluetoothDevice bluetoothDevice, Map<Integer, byte[]> map) {
        if (this.mDevice.equals(bluetoothDevice)) {
            refresh();
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    private void setLayoutResource(LinearLayout linearLayout, int i, int i2) {
        setLayoutResource(linearLayout, i, i2, -1);
    }

    private void setLayoutResource(LinearLayout linearLayout, int i, int i2, int i3) {
        linearLayout.setVisibility(0);
        setHeaderIcon();
        setHeaderIcon(linearLayout, R.id.header_icon_left, getHeaderLeftDrawable(), i);
        setHeaderIcon(linearLayout, R.id.header_icon_right, getHeaderRightDrawable(), i2);
        setHeaderIcon(linearLayout, R.id.header_icon_cradle, getHeaderCaseDrawable(), i3);
        setBatteryArcProgress(linearLayout, R.id.header_icon_progress_left, i);
        setBatteryArcProgress(linearLayout, R.id.header_icon_progress_right, i2);
        setBatteryArcProgress(linearLayout, R.id.header_icon_progress_cradle, i3);
        setHeaderText(linearLayout, R.id.header_title_left, i, R.string.sec_bluetooth_battery_left);
        setHeaderText(
                linearLayout, R.id.header_title_right, i2, R.string.sec_bluetooth_battery_right);
        setHeaderText(
                linearLayout, R.id.header_title_cradle, i3, R.string.sec_bluetooth_battery_cradle);
        setBatteryLevel(linearLayout, R.id.bt_battery_summary_left, i);
        setBatteryLevel(linearLayout, R.id.bt_battery_summary_right, i2);
        setBatteryLevel(linearLayout, R.id.bt_battery_summary_cradle, i3);
    }

    private void setHeaderIcon(LinearLayout linearLayout, int i, Drawable drawable, int i2) {
        ImageView imageView = (ImageView) linearLayout.findViewById(i);
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
            if (isDimState(i2)) {
                imageView.getDrawable().setAlpha(114);
            } else {
                imageView.getDrawable().setAlpha(255);
            }
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public void connectionStateChange(BluetoothDevice bluetoothDevice, int i) {}

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public void updateNoiseControlState(
            BluetoothDevice bluetoothDevice, Map<Integer, byte[]> map) {}

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public void updateTouchPadState(BluetoothDevice bluetoothDevice, Map<Integer, byte[]> map) {}
}
