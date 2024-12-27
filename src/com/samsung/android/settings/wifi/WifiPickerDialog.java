package com.samsung.android.settings.wifi;

import android.app.ActivityManager;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.ButtonBarHandler;
import com.android.settings.network.telephony.ToggleSubscriptionDialogActivity;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiPickerDialog extends AppCompatActivity implements ButtonBarHandler {
    public AccessibilityManager mAccessibilityManager;
    public ActivityManager mActivityManager;
    public Toast mLastToast = null;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.WifiPickerDialog.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    Log.d("WifiPickerDialog", "Received intent: " + action);
                    if ("com.samsung.android.action.LOCK_TASK_MODE".equals(action)) {
                        if (intent.getBooleanExtra(
                                ToggleSubscriptionDialogActivity.ARG_enable, false)) {
                            Log.d(
                                    "WifiPickerDialog",
                                    "Requesting system key event for Pinned Dialog");
                            WifiPickerDialog.m1318$$Nest$mrequestSystemKeyEvents(
                                    WifiPickerDialog.this, true);
                        } else {
                            Log.d("WifiPickerDialog", "Removing system key request");
                            WifiPickerDialog.m1318$$Nest$mrequestSystemKeyEvents(
                                    WifiPickerDialog.this, false);
                        }
                    }
                }
            };
    public SemWindowManager mSemWindowManager;

    /* renamed from: -$$Nest$mrequestSystemKeyEvents, reason: not valid java name */
    public static void m1318$$Nest$mrequestSystemKeyEvents(
            WifiPickerDialog wifiPickerDialog, boolean z) {
        ComponentName componentName = wifiPickerDialog.getComponentName();
        if (wifiPickerDialog.mSemWindowManager == null) {
            wifiPickerDialog.mSemWindowManager = SemWindowManager.getInstance();
        }
        SemWindowManager semWindowManager = wifiPickerDialog.mSemWindowManager;
        if (semWindowManager == null) {
            Log.e("WifiPickerDialog", "Windowmanager is not yet initialized.");
            return;
        }
        semWindowManager.requestSystemKeyEvent(4, componentName, z);
        wifiPickerDialog.mSemWindowManager.requestSystemKeyEvent(3, componentName, z);
        wifiPickerDialog.mSemWindowManager.requestSystemKeyEvent(187, componentName, z);
    }

    @Override // com.android.settings.ButtonBarHandler
    public final Button getNextButton() {
        return null;
    }

    @Override // com.android.settings.ButtonBarHandler
    public final boolean hasNextButton() {
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null && bundle.containsKey("key_finish")) {
            Log.d(
                    "WifiPickerDialog",
                    "not allow to show the picker dialog when device theme was changed");
            if (bundle.getBoolean("key_finish")) {
                finish();
            }
        }
        setFinishOnTouchOutside(true);
        requestWindowFeature(1);
        registerReceiver(
                this.mReceiver, new IntentFilter("com.samsung.android.action.LOCK_TASK_MODE"), 2);
        WifiPickerDialogFragment wifiPickerDialogFragment = new WifiPickerDialogFragment();
        wifiPickerDialogFragment.setCancelable(true);
        wifiPickerDialogFragment.mListener = new WifiPickerDialog$$ExternalSyntheticLambda0(this);
        wifiPickerDialogFragment.show(getSupportFragmentManager(), "WifiPickerDialog");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 && i != 187 && i != 3) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.mActivityManager == null) {
            this.mActivityManager = (ActivityManager) getSystemService("activity");
        }
        boolean isInLockTaskMode = this.mActivityManager.isInLockTaskMode();
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "checkAndShowWindowPinnedMsg() -:- isWindowPinned ==> ",
                "WifiPickerDialog",
                isInLockTaskMode);
        if (!isInLockTaskMode) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.mAccessibilityManager == null) {
            this.mAccessibilityManager = (AccessibilityManager) getSystemService("accessibility");
        }
        try {
            int identifier =
                    ViewConfiguration.get(this).hasPermanentMenuKey()
                            ? this.mAccessibilityManager.isEnabled()
                                    ? getResources()
                                            .getIdentifier(
                                                    "tw_lock_to_app_toast_accessible",
                                                    "string",
                                                    RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME)
                                    : getResources()
                                            .getIdentifier(
                                                    "tw_lock_to_app_toast",
                                                    "string",
                                                    RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME)
                            : this.mAccessibilityManager.isEnabled()
                                    ? getResources()
                                            .getIdentifier(
                                                    "lock_to_app_toast_accessible",
                                                    "string",
                                                    RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME)
                                    : getResources()
                                            .getIdentifier(
                                                    "lock_to_app_toast",
                                                    "string",
                                                    RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME);
            Toast toast = this.mLastToast;
            if (toast != null) {
                toast.cancel();
                this.mLastToast = null;
            }
            Toast makeText = Toast.makeText(this, identifier, 1);
            this.mLastToast = makeText;
            makeText.show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Log.d("WifiPickerDialog", "key consumed when window is pinned");
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        StatusBarManager statusBarManager = (StatusBarManager) getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.collapsePanels();
        }
        sendBroadcastAsUser(
                new Intent("com.sec.kidsplat.quickpanel.PANEL_CLOSE"), UserHandle.CURRENT);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("key_finish", true);
    }
}
