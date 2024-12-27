package com.samsung.android.settings.lockscreen.bixbyroutine;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.bio.face.SemBioFaceManager;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LockScreenRoutineActionActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Button mCancelButton;
    public TextView mDescriptionTextView;
    public Button mDoneButton;
    public RelativeLayout mFaceLayout;
    public SemBioFaceManager mFaceManager;
    public Switch mFaceSwitch;
    public RelativeLayout mFingerprintLayout;
    public FingerprintManager mFingerprintManager;
    public Switch mFingerprintSwitch;
    public LinearLayout mMainLayout;
    public ParameterValues mParameterValues;
    public ViewGroup mRootView;
    public TextView mTitle;
    public Toolbar mToolBar;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult : requestCode : ",
                " resultCode : ",
                i,
                i2,
                "LockScreenRoutineActionActivity");
        if (i == 10011) {
            if (i2 == 0) {
                finish();
                return;
            } else {
                if (i2 == -1) {
                    this.mToolBar.setVisibility(0);
                    this.mFingerprintSwitch.setChecked(true);
                    this.mParameterValues.put(
                            "unlock_with_fingerprint",
                            Boolean.valueOf(this.mFingerprintSwitch.isChecked()));
                    setView();
                    return;
                }
                return;
            }
        }
        if (i != 10012) {
            return;
        }
        if (i2 == 0) {
            finish();
        } else if (i2 == -1) {
            this.mToolBar.setVisibility(0);
            this.mFaceSwitch.setChecked(true);
            this.mParameterValues.put(
                    "unlock_with_face", Boolean.valueOf(this.mFaceSwitch.isChecked()));
            setView();
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int id = compoundButton.getId();
        if (id == R.id.sec_lockscreen_biometric_fingerprint_switch) {
            FingerprintManager fingerprintManager = this.mFingerprintManager;
            if (fingerprintManager != null
                    && fingerprintManager.hasEnrolledFingerprints(UserHandle.myUserId())) {
                this.mParameterValues.put(
                        "unlock_with_fingerprint",
                        Boolean.valueOf(this.mFingerprintSwitch.isChecked()));
                return;
            }
            this.mToolBar.setVisibility(8);
            this.mDescriptionTextView.setVisibility(0);
            this.mMainLayout.setVisibility(8);
            this.mDescriptionTextView.setText(
                    getResources().getString(R.string.sec_register_your_fingerprints_first));
            this.mDoneButton.setText(getResources().getString(R.string.sec_register));
            this.mDoneButton.setOnClickListener(
                    new LockScreenRoutineActionActivity$$ExternalSyntheticLambda0(this, 0));
            return;
        }
        if (id == R.id.sec_lockscreen_biometric_face_switch) {
            SemBioFaceManager semBioFaceManager = this.mFaceManager;
            if (semBioFaceManager != null
                    && semBioFaceManager.hasEnrolledFaces(UserHandle.myUserId())) {
                this.mParameterValues.put(
                        "unlock_with_face", Boolean.valueOf(this.mFaceSwitch.isChecked()));
                return;
            }
            this.mToolBar.setVisibility(8);
            this.mDescriptionTextView.setVisibility(0);
            this.mMainLayout.setVisibility(8);
            this.mDescriptionTextView.setText(
                    getResources().getString(R.string.sec_register_your_face_first));
            this.mDoneButton.setText(getResources().getString(R.string.sec_register));
            this.mDoneButton.setOnClickListener(
                    new LockScreenRoutineActionActivity$$ExternalSyntheticLambda0(this, 3));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ViewGroup viewGroup =
                (ViewGroup)
                        LayoutInflater.from(this)
                                .inflate(
                                        R.layout.sec_lockscreen_routine_action_biometrics_status,
                                        (ViewGroup) null);
        this.mRootView = viewGroup;
        setContentView(viewGroup);
        getWindow().setGravity(80);
        Toolbar toolbar = (Toolbar) this.mRootView.findViewById(R.id.action_bar);
        this.mToolBar = toolbar;
        this.mTitle = (TextView) toolbar.findViewById(R.id.title);
        this.mParameterValues = ParameterValues.fromIntent(getIntent());
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this);
        this.mFingerprintManager = Utils.getFingerprintManagerOrNull(this);
        this.mFaceManager = SemBioFaceManager.createInstance(this);
        this.mDescriptionTextView = (TextView) findViewById(R.id.description);
        this.mMainLayout = (LinearLayout) findViewById(R.id.main_layout);
        this.mFingerprintLayout =
                (RelativeLayout)
                        findViewById(R.id.sec_lockscreen_biometric_fingerprint_switch_main_layout);
        this.mFaceLayout =
                (RelativeLayout)
                        findViewById(R.id.sec_lockscreen_biometric_face_switch_main_layout);
        this.mFingerprintSwitch =
                (Switch) findViewById(R.id.sec_lockscreen_biometric_fingerprint_switch);
        this.mFaceSwitch = (Switch) findViewById(R.id.sec_lockscreen_biometric_face_switch);
        this.mDoneButton = (Button) findViewById(R.id.okayButton);
        this.mCancelButton = (Button) findViewById(R.id.cancelButton);
        Boolean bool =
                this.mParameterValues.getBoolean(
                        "unlock_with_fingerprint",
                        Boolean.valueOf(
                                lockPatternUtils.getBiometricState(1, UserHandle.myUserId()) == 1));
        this.mFingerprintSwitch.setChecked(bool.booleanValue());
        this.mParameterValues.put("unlock_with_fingerprint", bool);
        Boolean bool2 =
                this.mParameterValues.getBoolean(
                        "unlock_with_face",
                        Boolean.valueOf(
                                lockPatternUtils.getBiometricState(256, UserHandle.myUserId())
                                        == 1));
        this.mFaceSwitch.setChecked(bool2.booleanValue());
        this.mParameterValues.put("unlock_with_face", bool2);
        setView();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        this.mFaceManager = null;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        this.mFingerprintSwitch.setOnCheckedChangeListener(null);
        this.mFaceSwitch.setOnCheckedChangeListener(null);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mFingerprintSwitch.setOnCheckedChangeListener(this);
        this.mFaceSwitch.setOnCheckedChangeListener(this);
    }

    public final void setView() {
        this.mTitle.setText(getResources().getString(R.string.sec_unlock_with_biometrics));
        this.mDescriptionTextView.setVisibility(8);
        this.mMainLayout.setVisibility(0);
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
            this.mFingerprintLayout.setVisibility(8);
        }
        SemBioFaceManager semBioFaceManager = this.mFaceManager;
        if (semBioFaceManager == null || !semBioFaceManager.isHardwareDetected()) {
            this.mFaceLayout.setVisibility(8);
        }
        this.mDoneButton.setText(getResources().getString(R.string.done));
        this.mDoneButton.setOnClickListener(
                new LockScreenRoutineActionActivity$$ExternalSyntheticLambda0(this, 1));
        this.mCancelButton.setOnClickListener(
                new LockScreenRoutineActionActivity$$ExternalSyntheticLambda0(this, 2));
    }
}
