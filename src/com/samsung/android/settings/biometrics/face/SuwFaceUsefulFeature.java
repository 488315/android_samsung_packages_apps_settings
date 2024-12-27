package com.samsung.android.settings.biometrics.face;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;

import com.android.settings.R;
import com.android.settings.biometrics.GatekeeperPasswordProvider;

import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SuwFaceUsefulFeature extends SuwBaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View mBrightenScreen;
    public Switch mBrightenScreenSwitch;
    public View mFaceUnlock;
    public Switch mFaceUnlockSwitch;
    public long mGkPwHandle;
    public ActivityResultRegistry.AnonymousClass2 mLaunchFingerLock;
    public View mOpenEyes;
    public Switch mOpenEyesSwitch;
    public View mRecognizeWithMask;
    public Switch mRecognizeWithMaskSwitch;
    public View mStayOnLockScreen;
    public Switch mStayOnLockScreenSwitch;
    public LinearLayout mTipLayout;
    public int mUserId;

    public static void setViewEnable(View view, boolean z) {
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
        if (intent == null
                || !GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
            Log.e("FcstSuwFaceUsefulFeature", "getIntent() is null !!");
            setResult(0);
            finish();
            return;
        }
        this.mUserId = intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        this.mGkPwHandle = intent.getLongExtra("gk_pw_handle", 0L);
        setContentView(R.layout.sec_face_useful_features_suw, true);
        setHeaderTitle(R.string.sec_face_useful_face_recognition_feature_title);
        setDescriptionText(R.string.sec_face_useful_face_recognition_feature_msg);
        setPrimaryActionButton(
                R.string.sec_biometrics_common_continue_button_text,
                new SuwFaceUsefulFeature$$ExternalSyntheticLambda0(this, 0));
        this.mLaunchFingerLock =
                (ActivityResultRegistry.AnonymousClass2)
                        registerForActivityResult(
                                new ActivityResultContracts$StartActivityForResult(0),
                                new ActivityResultCallback() { // from class:
                                                               // com.samsung.android.settings.biometrics.face.SuwFaceUsefulFeature$$ExternalSyntheticLambda1
                                    @Override // androidx.activity.result.ActivityResultCallback
                                    public final void onActivityResult(Object obj) {
                                        int i = SuwFaceUsefulFeature.$r8$clinit;
                                        SuwFaceUsefulFeature suwFaceUsefulFeature =
                                                SuwFaceUsefulFeature.this;
                                        ViewGroup viewGroup =
                                                (ViewGroup)
                                                        suwFaceUsefulFeature.findViewById(
                                                                R.id.sswl_glif_root);
                                        if (viewGroup != null) {
                                            viewGroup.setVisibility(4);
                                        }
                                        suwFaceUsefulFeature.setResult(-1);
                                        suwFaceUsefulFeature.finish();
                                    }
                                });
        this.mFaceUnlock = findViewById(R.id.face_unlock);
        this.mFaceUnlockSwitch = (Switch) findViewById(R.id.face_unlock_switch);
        this.mOpenEyes = findViewById(R.id.face_useful_feature_open_eyes);
        this.mOpenEyesSwitch = (Switch) findViewById(R.id.face_useful_feature_open_eyes_switch);
        this.mBrightenScreen = findViewById(R.id.face_useful_feature_brighten_screen);
        this.mBrightenScreenSwitch =
                (Switch) findViewById(R.id.face_useful_feature_brighten_screen_switch);
        this.mStayOnLockScreen = findViewById(R.id.face_useful_feature_stay_on_lock);
        this.mStayOnLockScreenSwitch =
                (Switch) findViewById(R.id.face_useful_feature_stay_on_lock_switch);
        this.mRecognizeWithMask = findViewById(R.id.face_recognize_with_mask);
        this.mRecognizeWithMaskSwitch =
                (Switch) findViewById(R.id.face_useful_feature_recognize_with_mask_switch);
        this.mTipLayout = (LinearLayout) findViewById(R.id.tip_register_fingerprints);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        Log.d("FcstSuwFaceUsefulFeature", "onDestroy");
        Log.d("FcstSuwFaceUsefulFeature", "sendFaceFeatureLogging");
        try {
            View view = this.mFaceUnlock;
            String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            if (view != null) {
                LoggingHelper.insertEventLogging(
                        8424,
                        8404,
                        0L,
                        this.mFaceUnlockSwitch.isChecked()
                                ? "1"
                                : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            }
            boolean z = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
            LoggingHelper.insertEventLogging(
                    8424,
                    8437,
                    0L,
                    this.mStayOnLockScreenSwitch.isChecked()
                            ? "1"
                            : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            if (FaceSettingsHelper.isSupportBioFaceRecognizeWithMask(this)) {
                LoggingHelper.insertEventLogging(
                        8424,
                        8456,
                        0L,
                        this.mRecognizeWithMaskSwitch.isChecked()
                                ? "1"
                                : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            }
            if (FaceSettingsHelper.isSupportBioFaceOpenEyes()) {
                LoggingHelper.insertEventLogging(
                        8424,
                        8447,
                        0L,
                        this.mOpenEyesSwitch.isChecked() ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            }
            if (this.mBrightenScreenSwitch.isChecked()) {
                str = "1";
            }
            LoggingHelper.insertEventLogging(8424, 8430, 0L, str);
        } catch (NullPointerException e) {
            Log.e(
                    "FcstSuwFaceUsefulFeature",
                    "sendFaceFeatureLogging: error fail to send  SA logging");
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00f7  */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.biometrics.face.SuwFaceUsefulFeature.onResume():void");
    }
}
