package com.samsung.android.settings.knox;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class WorkProfileConfirmCredentialUnavailableActivity extends Activity {
    public DevicePolicyManager mDevicePolicyManager;
    public int mUserId;
    public SemPersonaManager mPersonaManager = null;
    public boolean mDeviceHasSoftKeys = false;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sec_confirm_credential_unavailable_work_profile);
        this.mDevicePolicyManager = (DevicePolicyManager) getSystemService("device_policy");
        this.mPersonaManager = (SemPersonaManager) getSystemService("persona");
        this.mDeviceHasSoftKeys =
                getResources()
                        .getBoolean(
                                Resources.getSystem()
                                        .getIdentifier(
                                                "config_showNavigationBar",
                                                "bool",
                                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME));
        int intExtra = getIntent().getIntExtra("mUserId", UserHandle.myUserId());
        this.mUserId = intExtra;
        View decorView = getWindow().getDecorView();
        ImageView imageView = (ImageView) decorView.findViewById(R.id.background_image);
        if (imageView != null) {
            imageView.setImageDrawable(
                    KnoxUtils.getInflatedLayoutType(this) == 1000
                            ? getResources().getDrawable(R.drawable.sec_knox_credential_bg)
                            : getResources().getDrawable(R.drawable.sec_knox_credential_bg_land));
        }
        TextView textView = (TextView) decorView.findViewById(R.id.knoxTitleText);
        int color = getResources().getColor(R.color.work_profile_lock_screen_text_color);
        textView.setTextColor(color);
        textView.setText(this.mPersonaManager.getContainerName(intExtra, this));
        TextView textView2 = (TextView) decorView.findViewById(R.id.knoxTitleSubText);
        CharSequence organizationNameForUser =
                this.mDevicePolicyManager.getOrganizationNameForUser(intExtra);
        textView2.setTextColor(color);
        textView2.setText(organizationNameForUser);
        ImageView imageView2 = (ImageView) decorView.findViewById(R.id.knoxLogo);
        Drawable drawable = getResources().getDrawable(R.drawable.knox_basic);
        if (imageView2 != null) {
            imageView2.setImageDrawable(drawable);
            imageView2.setColorFilter(Color.parseColor("#fffafafa"));
        }
        LinearLayout linearLayout = (LinearLayout) decorView.findViewById(R.id.knox_logo_layout);
        boolean z = false;
        if (linearLayout == null) {
            Log.d("KKG::WorkProfileConfirmCredentialUnavailableActivity", "SecuredLogo is NULL");
        } else if (this.mDeviceHasSoftKeys) {
            linearLayout.setPadding(0, 0, 0, KnoxUtils.getNavigationBarSize(this));
        }
        Window window = getWindow();
        if (window != null) {
            window.setNavigationBarColor(0);
            window.setNavigationBarContrastEnforced(false);
            window.setStatusBarColor(0);
            window.getDecorView().setSystemUiVisibility(768);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.samsungFlags |= 67108864;
            window.setAttributes(attributes);
        }
        TextView textView3 = (TextView) decorView.findViewById(R.id.KnoxLockedText);
        TextView textView4 = (TextView) decorView.findViewById(R.id.KnoxLockedDetailText);
        if (KnoxUtils.isFingerprintLockSetForUser(this, this.mUserId)
                && KnoxUtils.isFingerPrintDisabledByPolicy(this, this.mUserId)) {
            z = true;
        }
        String containerName = this.mPersonaManager.getContainerName(this.mUserId, this);
        textView3.setText(
                String.format(getString(R.string.knox_keyguard_locked_premium), containerName));
        if (z) {
            textView4.setText(
                    String.format(
                            getString(R.string.knox_keyguard_locked_fingerprint_disabled_premium),
                            containerName));
        } else {
            textView4.setText(
                    String.format(
                            getString(
                                    R.string
                                            .knox_keyguard_locked_multi_biometrics_disabled_premium),
                            containerName));
        }
    }
}
