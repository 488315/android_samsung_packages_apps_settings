package com.samsung.android.settings.biometrics.fingerprint;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.GatekeeperPasswordProvider;

import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SuwFingerprintUsefulFeature extends SuwBaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View mAlwaysOn;
    public TextView mAlwaysOnSummary;
    public Switch mAlwaysOnSwitch;
    public View mAlwaysOnType;
    public SuwFingerprintUsefulFeature mContext;
    public View mFingerEffect;
    public Switch mFingerEffectSwitch;
    public View mFingerUnlock;
    public Switch mFingerUnlockSwitch;
    public long mGkPwHandle;
    public ActivityResultRegistry.AnonymousClass2 mLaunchFaceLock;
    public View mScreenOffIcon;
    public View mScreenOffIconAod;
    public Spinner mScreenOffIconAodSpinner;
    public TextView mScreenOffIconAodSummary;
    public Switch mScreenOffIconSwitch;
    public View mShowFingerprintIcon;
    public TextView mShowFingerprintIconSummary;
    public View mStayOnLockScreen;
    public Switch mStayOnLockScreenSwitch;
    public LinearLayout mTipLayout;
    public int mUserId;

    public static void setViewEnable$1(View view, boolean z) {
        if (view != null) {
            view.setEnabled(z);
            view.setAlpha(z ? 1.0f : 0.4f);
        }
    }

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.mContext = this;
        if (intent == null
                || !GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
            Log.e("FpstSuwFingerprintUsefulFeature", "getIntent() is null !!");
            setResult(0);
            finish();
            return;
        }
        this.mUserId = intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        this.mGkPwHandle = intent.getLongExtra("gk_pw_handle", 0L);
        setContentView(R.layout.sec_fingerprint_useful_feature_suw, true);
        this.mFingerUnlock = findViewById(R.id.fingerprint_unlock);
        this.mFingerUnlockSwitch = (Switch) findViewById(R.id.fingerprint_unlock_switch);
        this.mAlwaysOn = findViewById(R.id.fingerprint_useful_feature_always_on);
        this.mAlwaysOnSwitch =
                (Switch) findViewById(R.id.fingerprint_useful_feature_always_on_switch);
        this.mAlwaysOnSummary =
                (TextView) findViewById(R.id.fingerprint_useful_feature_always_on_summary);
        this.mAlwaysOnType = findViewById(R.id.fingerprint_useful_feature_always_on_type);
        this.mStayOnLockScreen = findViewById(R.id.fingerprint_useful_feature_stay_on_lock);
        this.mStayOnLockScreenSwitch =
                (Switch) findViewById(R.id.fingerprint_useful_feature_stay_on_lock_switch);
        this.mScreenOffIcon = findViewById(R.id.fingerprint_useful_feature_screen_off_icon);
        this.mScreenOffIconSwitch =
                (Switch) findViewById(R.id.fingerprint_useful_feature_screen_off_icon_switch);
        this.mScreenOffIconAod = findViewById(R.id.fingerprint_useful_feature_screen_off_icon_aod);
        this.mScreenOffIconAodSpinner =
                (Spinner) findViewById(R.id.fingerprint_useful_feature_screen_off_icon_aod_spinner);
        this.mScreenOffIconAodSummary =
                (TextView)
                        findViewById(R.id.fingerprint_useful_feature_screen_off_icon_aod_summary);
        this.mShowFingerprintIcon =
                findViewById(R.id.fingerprint_useful_feature_show_fingerprint_icon);
        this.mShowFingerprintIconSummary =
                (TextView)
                        findViewById(R.id.fingerprint_useful_feature_show_fingerprint_icon_summary);
        this.mFingerEffect = findViewById(R.id.fingerprint_useful_feature_finger_effect);
        this.mFingerEffectSwitch =
                (Switch) findViewById(R.id.fingerprint_useful_feature_finger_effect_switch);
        this.mTipLayout = (LinearLayout) findViewById(R.id.tip_register_face);
        setHeaderTitle(R.string.sec_fingerprint_useful_feature_title);
        if (FingerprintSettingsUtils.getEnrolledFingerNumber(
                        Utils.getFingerprintManagerOrNull(this), this.mUserId)
                == 1) {
            setDescriptionText(R.string.sec_fingerprint_useful_feature_msg_2);
        } else {
            setDescriptionText(R.string.sec_fingerprint_useful_feature_msg_4);
        }
        setPrimaryActionButton(
                R.string.sec_biometrics_common_continue_button_text,
                new SuwFingerprintUsefulFeature$$ExternalSyntheticLambda1(this, 4));
        this.mLaunchFaceLock =
                (ActivityResultRegistry.AnonymousClass2)
                        registerForActivityResult(
                                new ActivityResultContracts$StartActivityForResult(0),
                                new ActivityResultCallback() { // from class:
                                                               // com.samsung.android.settings.biometrics.fingerprint.SuwFingerprintUsefulFeature$$ExternalSyntheticLambda9
                                    @Override // androidx.activity.result.ActivityResultCallback
                                    public final void onActivityResult(Object obj) {
                                        int i = SuwFingerprintUsefulFeature.$r8$clinit;
                                        SuwFingerprintUsefulFeature suwFingerprintUsefulFeature =
                                                SuwFingerprintUsefulFeature.this;
                                        ViewGroup viewGroup =
                                                (ViewGroup)
                                                        suwFingerprintUsefulFeature.findViewById(
                                                                R.id.sswl_glif_root);
                                        if (viewGroup != null) {
                                            viewGroup.setVisibility(4);
                                        }
                                        suwFingerprintUsefulFeature.setResult(-1);
                                        suwFingerprintUsefulFeature.finish();
                                    }
                                });
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        Log.d("FpstSuwFingerprintUsefulFeature", "onDestroy");
        Log.d("FpstSuwFingerprintUsefulFeature", "sendFingerprintFeatureLogging");
        try {
            Switch r1 = this.mFingerUnlockSwitch;
            String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            if (r1 != null) {
                LoggingHelper.insertEventLogging(
                        8286, 8224, 0L, r1.isChecked() ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            }
            FingerprintSettingsUtils.isSupportFingerprintAlwaysOn();
            LoggingHelper.insertEventLogging(
                    8286,
                    8263,
                    0L,
                    this.mAlwaysOnSwitch.isChecked() ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            FingerprintSettingsUtils.isSupportFingerprintAlwaysOnType();
            Switch r12 = this.mStayOnLockScreenSwitch;
            if (r12 != null) {
                LoggingHelper.insertEventLogging(
                        8286, 8291, 0L, r12.isChecked() ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            }
            if (FingerprintSettingsUtils.isSupportFingerprintScreenOffIconAod()) {
                LoggingHelper.insertEventLogging(
                        8286,
                        8276,
                        0L,
                        String.valueOf(
                                FingerprintSettingsUtils.getFingerprintScreenOffIconAodDbValue(
                                        this.mContext, this.mUserId)));
            }
            if (this.mFingerEffectSwitch.isChecked()) {
                str = "1";
            }
            LoggingHelper.insertEventLogging(8286, 8280, 0L, str);
        } catch (NullPointerException e) {
            Log.e(
                    "FpstSuwFingerprintUsefulFeature",
                    "sendFingerprintsFeatureLogging: error fail to send  SA logging");
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0173  */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 494
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.biometrics.fingerprint.SuwFingerprintUsefulFeature.onResume():void");
    }

    public static void setViewEnable$1(Switch r0, boolean z) {
        if (r0 != null) {
            r0.setEnabled(z);
        }
    }
}
