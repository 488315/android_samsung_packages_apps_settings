package com.android.settings.biometrics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.biometrics.face.FaceFeatureProvider;
import com.android.settings.biometrics.face.FaceFeatureProviderImpl;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling;
import com.android.settings.core.InstrumentedActivity;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.unfold.updates.FoldProvider$FoldCallback;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.util.ThemeHelper;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BiometricEnrollBase extends InstrumentedActivity {
    public long mChallenge;
    public int mDevicePostureState;
    public FooterBarMixin mFooterBarMixin;
    public boolean mFromSettingsSummary;
    public boolean mLaunchedConfirmLock;
    public boolean mLaunchedPostureGuidance;
    public boolean mNextLaunched;
    public ScreenSizeFoldProvider mScreenSizeFoldProvider;
    public int mSensorId;
    public byte[] mToken;
    public int mUserId;
    public boolean mShouldSetFooterBarBackground = true;
    public Intent mPostureGuidanceIntent = null;
    public FoldProvider$FoldCallback mFoldCallback = null;

    public Intent getFingerprintEnrollingIntent() {
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                FingerprintEnrollEnrolling.class.getName());
        intent.putExtra("hw_auth_token", this.mToken);
        intent.putExtra("from_settings_summary", this.mFromSettingsSummary);
        intent.putExtra("challenge", this.mChallenge);
        intent.putExtra("sensor_id", this.mSensorId);
        BiometricUtils.copyMultiBiometricExtras(getIntent(), intent);
        int i = this.mUserId;
        if (i != -10000) {
            intent.putExtra("android.intent.extra.USER_ID", i);
        }
        return intent;
    }

    public final GlifLayout getLayout() {
        return (GlifLayout) findViewById(R.id.setup_wizard_layout);
    }

    public void initViews$1() {
        getWindow().setStatusBarColor(0);
    }

    public final void launchPostureGuidance() {
        if (this.mPostureGuidanceIntent == null || this.mLaunchedPostureGuidance) {
            return;
        }
        BiometricUtils.copyMultiBiometricExtras(getIntent(), this.mPostureGuidanceIntent);
        startActivityForResult(this.mPostureGuidanceIntent, 7);
        this.mLaunchedPostureGuidance = true;
        overridePendingTransition(0, 0);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        ColorStateList colorAttr = Utils.getColorAttr(this, android.R.attr.windowBackground);
        window.setStatusBarColor(colorAttr != null ? colorAttr.getDefaultColor() : 0);
    }

    @Override // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Intent intent;
        ComponentName unflattenFromString;
        super.onCreate(bundle);
        setTheme(SetupWizardUtils.getTheme(this, getIntent()));
        ThemeHelper.trySetDynamicColor(this);
        this.mChallenge = getIntent().getLongExtra("challenge", -1L);
        this.mSensorId = getIntent().getIntExtra("sensor_id", -1);
        if (this.mToken == null) {
            this.mToken = getIntent().getByteArrayExtra("hw_auth_token");
        }
        this.mFromSettingsSummary = getIntent().getBooleanExtra("from_settings_summary", false);
        if (bundle != null) {
            if (this.mToken == null) {
                this.mLaunchedConfirmLock = bundle.getBoolean("launched_confirm_lock");
                this.mToken = bundle.getByteArray("hw_auth_token");
                this.mFromSettingsSummary = bundle.getBoolean("from_settings_summary", false);
                this.mChallenge = bundle.getLong("challenge");
                this.mSensorId = bundle.getInt("sensor_id");
            }
            this.mLaunchedPostureGuidance = bundle.getBoolean("launched_posture_guidance");
            this.mNextLaunched = bundle.getBoolean("next_launched");
        }
        this.mUserId =
                getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        FaceFeatureProvider faceFeatureProvider = featureFactoryImpl.getFaceFeatureProvider();
        Context applicationContext = getApplicationContext();
        ((FaceFeatureProviderImpl) faceFeatureProvider).getClass();
        String string = applicationContext.getString(R.string.config_face_enroll_guidance_page);
        if (TextUtils.isEmpty(string)
                || (unflattenFromString = ComponentName.unflattenFromString(string)) == null) {
            intent = null;
        } else {
            intent = new Intent();
            intent.setComponent(unflattenFromString);
        }
        this.mPostureGuidanceIntent = intent;
        BiometricsSplitScreenDialog biometricsSplitScreenDialog =
                (BiometricsSplitScreenDialog)
                        getSupportFragmentManager()
                                .findFragmentByTag(BiometricsSplitScreenDialog.class.getName());
        if (biometricsSplitScreenDialog != null) {
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            supportFragmentManager.getClass();
            BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
            backStackRecord.remove(biometricsSplitScreenDialog);
            backStackRecord.commitInternal(false);
        }
    }

    @Override // android.app.Activity
    public final void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        initViews$1();
        if (this.mShouldSetFooterBarBackground) {
            FooterBarMixin footerBarMixin = this.mFooterBarMixin;
            LinearLayout buttonContainer =
                    footerBarMixin != null ? footerBarMixin.getButtonContainer() : null;
            if (buttonContainer != null) {
                ColorStateList colorAttr =
                        Utils.getColorAttr(this, android.R.attr.windowBackground);
                buttonContainer.setBackgroundColor(
                        colorAttr != null ? colorAttr.getDefaultColor() : 0);
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("launched_confirm_lock", this.mLaunchedConfirmLock);
        bundle.putByteArray("hw_auth_token", this.mToken);
        bundle.putBoolean("from_settings_summary", this.mFromSettingsSummary);
        bundle.putLong("challenge", this.mChallenge);
        bundle.putInt("sensor_id", this.mSensorId);
        bundle.putBoolean("launched_posture_guidance", this.mLaunchedPostureGuidance);
        bundle.putBoolean("next_launched", this.mNextLaunched);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        FoldProvider$FoldCallback foldProvider$FoldCallback;
        super.onStop();
        ScreenSizeFoldProvider screenSizeFoldProvider = this.mScreenSizeFoldProvider;
        if (screenSizeFoldProvider != null
                && (foldProvider$FoldCallback = this.mFoldCallback) != null) {
            ((ArrayList) screenSizeFoldProvider.callbacks).remove(foldProvider$FoldCallback);
        }
        this.mScreenSizeFoldProvider = null;
        this.mFoldCallback = null;
        if (isChangingConfigurations()
                || !shouldFinishWhenBackgrounded()
                || BiometricUtils.isAnyMultiBiometricFlow(this)) {
            return;
        }
        setResult(3);
        finish();
    }

    public final void setDescriptionText(int i) {
        if (getLayout() == null
                || TextUtils.equals(getLayout().getDescriptionText(), getString(i))) {
            return;
        }
        getLayout().setDescriptionText(i);
    }

    public final void setHeaderText(int i) {
        TextView headerTextView;
        CharSequence text;
        CharSequence text2;
        if (getLayout() == null) {
            return;
        }
        if (getLayout() != null
                && (text = (headerTextView = getLayout().getHeaderTextView()).getText())
                        != (text2 = getText(i))) {
            if (!TextUtils.isEmpty(text)) {
                headerTextView.setAccessibilityLiveRegion(1);
            }
            getLayout().setHeaderText(text2);
            getLayout().getHeaderTextView().setContentDescription(text2);
            setTitle(text2);
        }
        getLayout().getHeaderTextView().setContentDescription(getText(i));
    }

    public boolean shouldFinishWhenBackgrounded() {
        return !WizardManagerHelper.isAnySetupWizard(getIntent());
    }

    public final void setDescriptionText(CharSequence charSequence) {
        if (getLayout() == null) {
            return;
        }
        getLayout().setDescriptionText(charSequence);
    }
}
