package com.android.settings.bluetooth;

import android.app.StatusBarManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.Toolbar;

import com.android.settings.R;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bluetooth.BluetoothEnablingActivity;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DevicePickerActivity extends AppCompatActivity implements BluetoothCallback {
    public static boolean mMyPlacesCalled = false;
    public boolean mBTEnableDisplayed;
    public CollapsingToolbarLayout mCollapsingToolbarLayout;
    public String mDomesticOtaStart;
    public boolean mInitiateScan = false;
    public LocalBluetoothAdapter mLocalAdapter;
    public LocalBluetoothManager mLocalManager;

    public final void measureContentFrame() {
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.sec_settings_side_width_ratio, typedValue, true);
        float f = typedValue.getFloat();
        TypedValue typedValue2 = new TypedValue();
        getResources().getValue(R.dimen.sec_settings_content_width_ratio, typedValue2, true);
        float f2 = typedValue2.getFloat();
        Configuration configuration = getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        if (i >= 800 && configuration.orientation == 2) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) getSystemService("window"))
                    .getDefaultDisplay()
                    .getRealMetrics(displayMetrics);
            if (i > Math.round(displayMetrics.heightPixels / displayMetrics.density)) {
                f2 = com.android.settings.Utils.getContentFrameWidthRatio(this);
                f = (1.0f - f2) / 2.0f;
            }
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content_start_pane);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.content_end_pane);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        if (linearLayout == null || linearLayout2 == null || frameLayout == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 =
                (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams.weight = f;
        layoutParams2.weight = f;
        linearLayout.setLayoutParams(layoutParams);
        linearLayout2.setLayoutParams(layoutParams2);
        LinearLayout.LayoutParams layoutParams3 =
                (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams3.weight = f2;
        frameLayout.setLayoutParams(layoutParams3);
        Log.d("DevicePickerActivity", "measureContentFrame : " + f2 + " : " + f);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "onBluetoothStateChanged :: state = ", "DevicePickerActivity");
        if (i != 12 || this.mLocalAdapter.mAdapter.isDiscovering()) {
            return;
        }
        new Thread(
                        new Runnable() { // from class:
                                         // com.android.settings.bluetooth.DevicePickerActivity.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                try {
                                    Thread.sleep(500L);
                                } catch (InterruptedException e) {
                                    Log.d("DevicePickerActivity", "InterruptedException" + e);
                                    Thread.currentThread().interrupt();
                                }
                                if (DevicePickerActivity.this.mLocalAdapter.mAdapter
                                        .isDiscovering()) {
                                    return;
                                }
                                DevicePickerActivity.this.mLocalAdapter.startScanning(true);
                            }
                        })
                .start();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        measureContentFrame();
        com.android.settings.Utils.applyLandscapeFullScreen(this, getWindow());
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mInitiateScan = bundle == null;
        Log.d("DevicePickerActivity", "onCreate :: mInitiateScan : " + this.mInitiateScan);
        String action = getIntent().getAction();
        if (action == null) {
            Log.d("DevicePickerActivity", "onCreate :: Intent.getAction() is return null");
            return;
        }
        Log.d("DevicePickerActivity", "onCreate :: getAction = ".concat(action));
        if (action.equals("com.samsung.settings.bluetooth.CheckBluetoothLEStateActivity")) {
            mMyPlacesCalled = true;
        } else if (action.equals("com.samsung.settings.bluetooth.CheckBluetoothStateActivity")) {
            mMyPlacesCalled = false;
        }
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(getApplicationContext(), Utils.mOnInitCallback);
        this.mLocalManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e("DevicePickerActivity", "Bluetooth is not supported on this device");
            return;
        }
        this.mLocalAdapter = localBluetoothManager.mLocalAdapter;
        localBluetoothManager.mEventManager.registerCallback(this);
        if (getActionBar() != null) {
            getActionBar().setDisplayShowHomeEnabled(false);
        }
        setContentView(R.layout.bluetooth_device_picker);
        setSupportActionBar((Toolbar) findViewById(R.id.action_bar));
        this.mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_app_bar);
        measureContentFrame();
        getIntent().getBooleanExtra("fromHelp", false);
        if (this.mLocalAdapter.getBluetoothState() == 10
                && BluetoothAdapter.getDefaultAdapter().enable()) {
            Intent intent = new Intent(this, (Class<?>) BluetoothEnablingActivity.class);
            intent.setFlags(268435456);
            startActivityForResult(intent, 47);
            this.mBTEnableDisplayed = true;
        }
        this.mDomesticOtaStart = SystemProperties.get("ril.domesticOtaStart");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        Log.d("DevicePickerActivity", "inside onDestroy of DevicePickerActivity");
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this);
        }
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        Log.d("DevicePickerActivity", "inside onPause of DevicePickerActivity");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onPostResume() {
        LocalBluetoothAdapter localBluetoothAdapter;
        boolean z;
        super.onPostResume();
        Log.d("DevicePickerActivity", "onPostResume");
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager == null
                || !localBluetoothManager.isForegroundActivity()
                || (localBluetoothAdapter = this.mLocalAdapter) == null
                || localBluetoothAdapter.mAdapter.isDiscovering()
                || !(z = this.mInitiateScan)) {
            LocalBluetoothAdapter localBluetoothAdapter2 = this.mLocalAdapter;
            if (localBluetoothAdapter2 != null) {
                localBluetoothAdapter2.stopScanning();
            }
            Log.d("DevicePickerActivity", "Does not start scanning.");
        } else {
            this.mLocalAdapter.startScanning(z);
        }
        this.mInitiateScan = false;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        LocalBluetoothAdapter localBluetoothAdapter;
        super.onResume();
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("inside onResume with mBTEnableDisplayed= "),
                this.mBTEnableDisplayed,
                "DevicePickerActivity");
        String str = this.mDomesticOtaStart;
        if ((str != null && str.equals("true"))
                || (Rune.isDomesticLGTModel()
                        && "lock"
                                .equals(
                                        Settings.System.getString(
                                                getContentResolver(), "missing_phone_lock")))) {
            SemWindowManager semWindowManager = SemWindowManager.getInstance();
            if (semWindowManager != null) {
                semWindowManager.requestSystemKeyEvent(26, getComponentName(), true);
            }
            SemWindowManager semWindowManager2 = SemWindowManager.getInstance();
            if (semWindowManager2 != null) {
                semWindowManager2.requestSystemKeyEvent(3, getComponentName(), true);
            }
            SemWindowManager semWindowManager3 = SemWindowManager.getInstance();
            if (semWindowManager3 != null) {
                semWindowManager3.requestSystemKeyEvent(187, getComponentName(), true);
            }
            ((StatusBarManager) getSystemService("statusbar")).disable(65536);
        }
        if (!this.mBTEnableDisplayed
                && ((localBluetoothAdapter = this.mLocalAdapter) == null
                        || localBluetoothAdapter.getBluetoothState() == 10)) {
            finish();
        }
        com.android.settings.Utils.applyLandscapeFullScreen(this, getWindow());
    }

    @Override // android.app.Activity
    public final void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        CollapsingToolbarLayout collapsingToolbarLayout = this.mCollapsingToolbarLayout;
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(charSequence);
        }
    }

    @Override // android.app.Activity
    public final void setTitle(int i) {
        CollapsingToolbarLayout collapsingToolbarLayout;
        super.setTitle(i);
        if (i == 0 || (collapsingToolbarLayout = this.mCollapsingToolbarLayout) == null) {
            return;
        }
        collapsingToolbarLayout.setTitle(getResources().getString(i));
    }
}
