package com.samsung.android.settings.bluetooth;

import android.app.Dialog;
import android.app.StatusBarManager;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SemExpandableListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HeadsetProfile;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;

import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDevicePickerFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, BluetoothCallback, SemBluetoothCallback {
    public AlertDialog.Builder mBuilder;
    public AlertDialog mDialog;
    public int mFilterType;
    public LocalBluetoothAdapter mLocalAdapter;
    public LocalBluetoothManager mLocalManager;
    public ProgressBar mProgressBar;
    public Button mScanBtn;
    public View mView;

    public final void dismissDialog$1() {
        SecDevicePickerDialog secDevicePickerDialog = (SecDevicePickerDialog) getActivity();
        if (secDevicePickerDialog == null || secDevicePickerDialog.isFinishing()) {
            return;
        }
        secDevicePickerDialog.finish();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 613;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        if (i == 13 || i == 10) {
            Log.d(
                    "SecDevicePickerFragment",
                    "onBluetoothStateChanged :: SecDevicePickerDialog will finish by bluetooth"
                        + " disable");
            if (getActivity() != null) {
                dismissDialog$1();
            }
        }
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        Log.d("SecDevicePickerFragment", "onCancel ::");
        dismissDialog$1();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "onClick :: which = ", "SecDevicePickerFragment");
        if (i == -1) {
            dismissDialog$1();
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        new DisplayMetrics();
        updateDialogHeight();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(
                        getActivity().getApplicationContext(), Utils.mOnInitCallback);
        this.mLocalManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e("SecDevicePickerFragment", "Bluetooth is not supported on this device");
            return null;
        }
        this.mLocalAdapter = localBluetoothManager.mLocalAdapter;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        this.mBuilder = builder;
        this.mView =
                ((LayoutInflater) getActivity().getSystemService("layout_inflater"))
                        .inflate(R.layout.sec_bluetooth_scan_dialog, (ViewGroup) null);
        updateDialogHeight();
        builder.setView(this.mView);
        this.mBuilder.setPositiveButton(getString(R.string.sec_common_done), this);
        this.mBuilder.setNegativeButton(getString(R.string.service_stop), this);
        View inflate =
                getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.sec_bluetooth_custom_title_view, (ViewGroup) null);
        this.mProgressBar = (ProgressBar) inflate.findViewById(R.id.progress);
        AlertDialog.Builder builder2 = this.mBuilder;
        builder2.P.mCustomTitleView = inflate;
        AlertDialog create = builder2.create();
        create.getWindow()
                .setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.sec_bluetooth_scandialog_bg));
        this.mDialog = create;
        create.setCanceledOnTouchOutside(true);
        LocalBluetoothManager localBluetoothManager2 = this.mLocalManager;
        if (localBluetoothManager2 != null) {
            localBluetoothManager2.mEventManager.registerCallback(this);
            this.mLocalManager.mEventManager.registerSemCallback(this);
        }
        this.mFilterType =
                getActivity()
                        .getIntent()
                        .getIntExtra("android.bluetooth.devicepicker.extra.FILTER_TYPE", 0);
        getActivity()
                .getIntent()
                .getStringExtra("android.bluetooth.devicepicker.extra.LAUNCH_PACKAGE");
        getActivity()
                .getIntent()
                .getStringExtra("android.bluetooth.devicepicker.extra.DEVICE_PICKER_LAUNCH_CLASS");
        return this.mDialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this);
            this.mLocalManager.mEventManager.unregisterSemCallback(this);
        }
    }

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onProfileStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile,
            int i,
            int i2) {
        StringBuilder sb = new StringBuilder("onProfileStateChanged :: profile = ");
        sb.append(localBluetoothProfile);
        sb.append(", newState = ");
        sb.append(i);
        sb.append(", oldState = ");
        Preference$$ExternalSyntheticOutline0.m(sb, i2, "SecDevicePickerFragment");
        if ((localBluetoothProfile instanceof HeadsetProfile) && i == 2) {
            int i3 = this.mFilterType;
            if (i3 == 5 || i3 == 105) {
                dismissDialog$1();
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {
        Log.d("SecDevicePickerFragment", "onScanningStateChanged :: started - " + z);
        updateScanStateView();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        StatusBarManager statusBarManager =
                (StatusBarManager) getActivity().getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.collapsePanels();
        }
        Fragment findFragmentById =
                getParentFragmentManager().findFragmentById(R.id.bluetooth_scan_dialog);
        if (findFragmentById instanceof BluetoothSettings) {}
        getResources().getString(R.string.screen_bluetooth_scan_dialog);
        Button button = this.mDialog.getButton(-2);
        this.mScanBtn = button;
        button.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.SecDevicePickerFragment.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SecDevicePickerFragment.this.updateScanStateView();
                        if (!SecDevicePickerFragment.this.mLocalAdapter.mAdapter.isDiscovering()) {
                            SecDevicePickerFragment.this
                                    .getResources()
                                    .getString(R.string.detail_bluetooth_scan_start);
                            SecDevicePickerFragment.this.mLocalAdapter.startScanning(true);
                        } else {
                            SecDevicePickerFragment.this
                                    .getResources()
                                    .getString(R.string.detail_bluetooth_scan_stop);
                            SecDevicePickerFragment.this.mLocalAdapter.stopScanning();
                            SecDevicePickerDialog.IS_ACTION_BUTTON = true;
                        }
                    }
                });
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        Button button = this.mScanBtn;
        if (button != null) {
            button.setText(R.string.bluetooth_scan_for_devices);
        }
    }

    public final void updateDialogHeight() {
        Fragment findFragmentById =
                getParentFragmentManager().findFragmentById(R.id.bluetooth_scan_dialog);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (findFragmentById == null) {
            Log.e("SecDevicePickerFragment", "updateDialogHeight :: Can't find BT fragment");
            dismissDialog$1();
            return;
        }
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int i = (int) (displayMetrics.heightPixels * 0.5d);
        findFragmentById.getView().setLayoutParams(new FrameLayout.LayoutParams(-1, i));
        final LinearLayout linearLayout =
                (LinearLayout) findFragmentById.getView().findViewById(R.id.container_material);
        final SemExpandableListView findViewById =
                findFragmentById.getView().findViewById(R.id.tw_expandable_listview);
        final int dimensionPixelSize =
                getResources().getDimensionPixelSize(R.dimen.bluetooth_scandialog_content_margin);
        View view = this.mView;
        if (view != null) {
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(
                        new ViewTreeObserver
                                .OnGlobalLayoutListener() { // from class:
                                                            // com.samsung.android.settings.bluetooth.SecDevicePickerFragment.2
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public final void onGlobalLayout() {
                                int height = SecDevicePickerFragment.this.mView.getHeight();
                                int i2 =
                                        SecDevicePickerFragment.this
                                                .getResources()
                                                .getConfiguration()
                                                .orientation;
                                int i3 = i;
                                int i4 = dimensionPixelSize;
                                if (i3 > (i4 * 2) + height && i2 == 1) {
                                    height = (i4 * 2) + i3;
                                }
                                if (i3 > height - (i4 * 2) && i2 == 2) {
                                    height = (i4 * 2) + i3;
                                }
                                LinearLayout linearLayout2 = linearLayout;
                                if (linearLayout2 != null) {
                                    linearLayout2.setLayoutParams(
                                            new FrameLayout.LayoutParams(
                                                    -1, height - (dimensionPixelSize * 2)));
                                }
                                SemExpandableListView semExpandableListView = findViewById;
                                if (semExpandableListView != null) {
                                    semExpandableListView.setLayoutParams(
                                            new RelativeLayout.LayoutParams(
                                                    -1, height - (dimensionPixelSize * 2)));
                                }
                                SecDevicePickerFragment.this
                                        .mView
                                        .getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                            }
                        });
            }
        }
    }

    public final void updateScanStateView() {
        if (this.mLocalAdapter.mAdapter.isDiscovering()) {
            Button button = this.mScanBtn;
            if (button != null) {
                button.setText(R.string.service_stop);
            }
            this.mProgressBar.setVisibility(0);
            return;
        }
        Button button2 = this.mScanBtn;
        if (button2 != null) {
            button2.setText(R.string.bluetooth_scan_for_devices);
        }
        this.mProgressBar.setVisibility(8);
    }

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onResourceUpdated() {}

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onSyncDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}
}
