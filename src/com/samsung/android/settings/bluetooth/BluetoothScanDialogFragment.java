package com.samsung.android.settings.bluetooth;

import android.app.Dialog;
import android.app.StatusBarManager;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.fragment.app.Fragment;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothScanDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, BluetoothCallback {
    public boolean isCastSupported;
    public AlertDialog.Builder mBuilder;
    public AlertDialog mDialog;
    public AnonymousClass3 mFoldStateListener = null;
    public LocalBluetoothAdapter mLocalAdapter;
    public LocalBluetoothCastAdapter mLocalCastAdapter;
    public LocalBluetoothManager mLocalManager;
    public ProgressBar mProgressBar;
    public Button mScanBtn;
    public String mScreenId;
    public StatusBarManager mStatusBar;
    public View mView;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 613;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        if (i == 13) {
            Log.d(
                    "BluetoothScanDialogFragment",
                    "onBluetoothStateChanged :: BluetoothScanDialog will finish by bluetooth"
                        + " disable");
        }
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        Log.d("BluetoothScanDialogFragment", "onCancel ::");
        if (getActivity() == null || getActivity().isFinishing()) {
            Log.d(
                    "BluetoothScanDialogFragment",
                    "onCancel :: getActivity() is null or isFinishing");
            return;
        }
        BluetoothScanDialog bluetoothScanDialog = (BluetoothScanDialog) getActivity();
        if (bluetoothScanDialog.isFinishing()) {
            return;
        }
        bluetoothScanDialog.finish();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Log.d("BluetoothScanDialogFragment", "onClick :: which = " + i);
        if (getActivity() == null || getActivity().isFinishing()) {
            Log.d("BluetoothScanDialogFragment", "onClick :: getActivity() is null or isFinishing");
            return;
        }
        if (i == -1) {
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources().getString(R.string.event_bluetooth_scan_dialog_done_button));
            BluetoothScanDialog bluetoothScanDialog = (BluetoothScanDialog) getActivity();
            if (bluetoothScanDialog.isFinishing()) {
                return;
            }
            bluetoothScanDialog.finish();
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateDialogHeight$1();
    }

    /* JADX WARN: Type inference failed for: r5v17, types: [com.samsung.android.settings.bluetooth.BluetoothScanDialogFragment$3] */
    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(
                        getActivity().getApplicationContext(), Utils.mOnInitCallback);
        this.mLocalManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e("BluetoothScanDialogFragment", "Bluetooth is not supported on this device");
            return null;
        }
        this.mLocalAdapter = localBluetoothManager.mLocalAdapter;
        boolean isBluetoothCastSupported = SemBluetoothCastAdapter.isBluetoothCastSupported();
        this.isCastSupported = isBluetoothCastSupported;
        if (isBluetoothCastSupported) {
            this.mLocalCastAdapter = this.mLocalManager.mLocalCastAdapter;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        this.mBuilder = builder;
        this.mView =
                ((LayoutInflater) getActivity().getSystemService("layout_inflater"))
                        .inflate(R.layout.sec_bluetooth_scan_dialog, (ViewGroup) null);
        updateDialogHeight$1();
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
        }
        if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP")
                && this.mFoldStateListener == null) {
            this.mFoldStateListener =
                    new SemWindowManager
                            .FoldStateListener() { // from class:
                                                   // com.samsung.android.settings.bluetooth.BluetoothScanDialogFragment.3
                        public final void onFoldStateChanged(boolean z) {
                            StatusBarManager statusBarManager;
                            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                    "onFoldStateChanged: isFolded = ",
                                    "BluetoothScanDialogFragment",
                                    z);
                            if (z
                                    || (statusBarManager =
                                                    BluetoothScanDialogFragment.this.mStatusBar)
                                            == null) {
                                return;
                            }
                            statusBarManager.collapsePanels();
                        }

                        public final void onTableModeChanged(boolean z) {}
                    };
            SemWindowManager.getInstance()
                    .registerFoldStateListener(this.mFoldStateListener, (Handler) null);
        }
        return this.mDialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this);
        }
        if (this.mFoldStateListener != null) {
            SemWindowManager.getInstance().unregisterFoldStateListener(this.mFoldStateListener);
            this.mFoldStateListener = null;
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateScanStateView();
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {
        Log.d("BluetoothScanDialogFragment", "onScanningStateChanged :: started - " + z);
        updateScanStateView();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        StatusBarManager statusBarManager =
                (StatusBarManager) getActivity().getSystemService("statusbar");
        this.mStatusBar = statusBarManager;
        if (statusBarManager != null && this.mFoldStateListener == null) {
            statusBarManager.collapsePanels();
        }
        this.mScreenId = getResources().getString(R.string.screen_bluetooth_scan_dialog);
        Button button = this.mDialog.getButton(-2);
        this.mScanBtn = button;
        button.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.BluetoothScanDialogFragment.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        String string;
                        BluetoothScanDialogFragment.this.updateScanStateView();
                        if (BluetoothScanDialogFragment.this.mLocalAdapter.mAdapter
                                .isDiscovering()) {
                            string =
                                    BluetoothScanDialogFragment.this
                                            .getResources()
                                            .getString(R.string.detail_bluetooth_scan_stop);
                            BluetoothScanDialogFragment bluetoothScanDialogFragment =
                                    BluetoothScanDialogFragment.this;
                            if (bluetoothScanDialogFragment.isCastSupported) {
                                bluetoothScanDialogFragment.mLocalCastAdapter.suspendDiscovery();
                            }
                            BluetoothScanDialogFragment.this.mLocalAdapter.stopScanning();
                            BluetoothScanDialog.IS_ACTION_BUTTON = true;
                        } else {
                            string =
                                    BluetoothScanDialogFragment.this
                                            .getResources()
                                            .getString(R.string.detail_bluetooth_scan_start);
                            BluetoothScanDialogFragment bluetoothScanDialogFragment2 =
                                    BluetoothScanDialogFragment.this;
                            if (bluetoothScanDialogFragment2.isCastSupported) {
                                bluetoothScanDialogFragment2.mLocalCastAdapter.startDiscovery();
                            }
                            BluetoothScanDialogFragment.this.mLocalAdapter.startScanning(true);
                        }
                        BluetoothScanDialogFragment bluetoothScanDialogFragment3 =
                                BluetoothScanDialogFragment.this;
                        SALogging.insertSALog(
                                bluetoothScanDialogFragment3.mScreenId,
                                bluetoothScanDialogFragment3
                                        .getResources()
                                        .getString(
                                                R.string.event_bluetooth_scan_dialog_scan_button),
                                string);
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

    public final void updateDialogHeight$1() {
        Fragment findFragmentById =
                getFragmentManager().findFragmentById(R.id.bluetooth_scan_dialog);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (findFragmentById == null) {
            Log.e("BluetoothScanDialogFragment", "updateDialogHeight :: Can't find BT fragment");
            BluetoothScanDialog bluetoothScanDialog = (BluetoothScanDialog) getActivity();
            if (bluetoothScanDialog.isFinishing()) {
                return;
            }
            bluetoothScanDialog.finish();
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
                                                            // com.samsung.android.settings.bluetooth.BluetoothScanDialogFragment.2
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public final void onGlobalLayout() {
                                int height = BluetoothScanDialogFragment.this.mView.getHeight();
                                int i2 =
                                        BluetoothScanDialogFragment.this
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
                                BluetoothScanDialogFragment.this
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
}
