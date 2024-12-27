package com.android.settings.wifi.addappnetworks;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.view.Window;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settingslib.core.lifecycle.HideNonSystemOverlayMixin;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AddAppNetworksActivity extends FragmentActivity {

    @VisibleForTesting final Bundle mBundle = new Bundle();

    @VisibleForTesting IActivityManager mActivityManager = ActivityManager.getService();

    @VisibleForTesting
    public String getCallingAppPackageName() {
        try {
            return this.mActivityManager.getLaunchedFromPackage(getActivityToken());
        } catch (RemoteException unused) {
            Log.e("AddAppNetworksActivity", "Can not get the package from activity manager");
            return null;
        }
    }

    @VisibleForTesting
    public boolean isAddWifiConfigAllow() {
        return WifiEnterpriseRestrictionUtils.isAddWifiConfigAllowed(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.settings_panel);
        if (!showAddNetworksFragment()) {
            finish();
            return;
        }
        getLifecycle().addObserver(new HideNonSystemOverlayMixin(this));
        Window window = getWindow();
        window.setGravity(80);
        window.setLayout(-1, -2);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (showAddNetworksFragment()) {
            return;
        }
        finish();
    }

    @VisibleForTesting
    public boolean showAddNetworksFragment() {
        UserManager userManager;
        Context applicationContext = getApplicationContext();
        if ((applicationContext == null
                        || (userManager =
                                        (UserManager)
                                                applicationContext.getSystemService(
                                                        UserManager.class))
                                == null)
                ? false
                : userManager.isGuestUser()) {
            Log.e("AddAppNetworksActivity", "Guest user is not allowed to configure Wi-Fi!");
            EventLog.writeEvent(1397638484, "224772678", -1, "User is a guest");
            return false;
        }
        if (!isAddWifiConfigAllow()) {
            Log.d("AddAppNetworksActivity", "Not allowed by Enterprise Restriction");
            return false;
        }
        String callingAppPackageName = getCallingAppPackageName();
        if (TextUtils.isEmpty(callingAppPackageName)) {
            Log.d("AddAppNetworksActivity", "Package name is null");
            return false;
        }
        this.mBundle.putString("panel_calling_package_name", callingAppPackageName);
        this.mBundle.putParcelableArrayList(
                "android.provider.extra.WIFI_NETWORK_LIST",
                getIntent()
                        .getParcelableArrayListExtra("android.provider.extra.WIFI_NETWORK_LIST"));
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        Fragment findFragmentByTag =
                supportFragmentManager.findFragmentByTag("AddAppNetworksActivity");
        if (findFragmentByTag == null) {
            AddAppNetworksFragment addAppNetworksFragment = new AddAppNetworksFragment();
            addAppNetworksFragment.setArguments(this.mBundle);
            BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
            backStackRecord.doAddOp(
                    R.id.main_content, addAppNetworksFragment, "AddAppNetworksActivity", 1);
            backStackRecord.commitInternal(false);
        } else {
            ((AddAppNetworksFragment) findFragmentByTag).createContent(this.mBundle);
        }
        return true;
    }
}
