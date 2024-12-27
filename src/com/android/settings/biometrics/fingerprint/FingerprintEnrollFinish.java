package com.android.settings.biometrics.fingerprint;

import android.content.ComponentName;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricEnrollBase;
import com.android.settings.biometrics.fingerprint.feature.SfpsRestToUnlockFeatureImpl;

import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintEnrollFinish extends BiometricEnrollBase {
    public static final String FINGERPRINT_SUGGESTION_ACTIVITY =
            "com.android.settings.SetupFingerprintSuggestionActivity";
    public boolean mCanAssumeSfps;
    public boolean mIsAddAnotherOrFinish;
    public SfpsRestToUnlockFeatureImpl mSfpsRestToUnlockFeature;

    public final void finishAndToNext(int i) {
        FingerprintManager fingerprintManagerOrNull;
        this.mIsAddAnotherOrFinish = true;
        setResult(i);
        if (WizardManagerHelper.isAnySetupWizard(getIntent())
                && (fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(this)) != null) {
            fingerprintManagerOrNull.revokeChallenge(this.mUserId, this.mChallenge);
        }
        finish();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        updateFingerprintSuggestionEnableState();
        if (i == 7 && i2 == 3) {
            finishAndToNext(i2);
        } else if (i != 7 || i2 == 0) {
            super.onActivityResult(i, i2, intent);
        } else {
            finishAndToNext(1);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        updateFingerprintSuggestionEnableState();
        setResult(0, getIntent().putExtra("finished_enrolling_fingerprint", true));
        finish();
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0023, code lost:

       if (((android.hardware.fingerprint.FingerprintSensorPropertiesInternal) r7.get(0)).isAnySidefpsType() != false) goto L10;
    */
    @Override // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r7) {
        /*
            r6 = this;
            super.onCreate(r7)
            java.lang.Class<android.hardware.fingerprint.FingerprintManager> r7 = android.hardware.fingerprint.FingerprintManager.class
            java.lang.Object r7 = r6.getSystemService(r7)
            android.hardware.fingerprint.FingerprintManager r7 = (android.hardware.fingerprint.FingerprintManager) r7
            java.util.List r7 = r7.getSensorPropertiesInternal()
            r0 = 0
            if (r7 == 0) goto L26
            int r1 = r7.size()
            r2 = 1
            if (r1 != r2) goto L26
            java.lang.Object r7 = r7.get(r0)
            android.hardware.fingerprint.FingerprintSensorPropertiesInternal r7 = (android.hardware.fingerprint.FingerprintSensorPropertiesInternal) r7
            boolean r7 = r7.isAnySidefpsType()
            if (r7 == 0) goto L26
            goto L27
        L26:
            r2 = r0
        L27:
            r6.mCanAssumeSfps = r2
            if (r2 == 0) goto L5f
            com.android.settings.overlay.FeatureFactoryImpl r7 = com.android.settings.overlay.FeatureFactoryImpl._factory
            if (r7 == 0) goto L57
            com.android.settings.biometrics.fingerprint.FingerprintFeatureProviderImpl r7 = r7.getFingerprintFeatureProvider()
            com.android.settings.biometrics.fingerprint.feature.SfpsRestToUnlockFeatureImpl r1 = r7.mSfpsRestToUnlockFeature
            if (r1 != 0) goto L3e
            com.android.settings.biometrics.fingerprint.feature.SfpsRestToUnlockFeatureImpl r1 = new com.android.settings.biometrics.fingerprint.feature.SfpsRestToUnlockFeatureImpl
            r1.<init>()
            r7.mSfpsRestToUnlockFeature = r1
        L3e:
            com.android.settings.biometrics.fingerprint.feature.SfpsRestToUnlockFeatureImpl r7 = r7.mSfpsRestToUnlockFeature
            r6.mSfpsRestToUnlockFeature = r7
            r7 = 2131561262(0x7f0d0b2e, float:1.874792E38)
            r6.setContentView(r7)
            r7 = 2131365384(0x7f0a0e08, float:1.8350632E38)
            android.view.View r7 = r6.findViewById(r7)
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            com.android.settings.biometrics.fingerprint.feature.SfpsRestToUnlockFeatureImpl r7 = r6.mSfpsRestToUnlockFeature
            r7.getClass()
            goto L65
        L57:
            java.lang.UnsupportedOperationException r6 = new java.lang.UnsupportedOperationException
            java.lang.String r7 = "No feature factory configured"
            r6.<init>(r7)
            throw r6
        L5f:
            r7 = 2131558960(0x7f0d0230, float:1.874325E38)
            r6.setContentView(r7)
        L65:
            r7 = 2132027825(0x7f1429b1, float:1.9694222E38)
            r6.setHeaderText(r7)
            int r7 = r6.mUserId
            android.content.Context r1 = r6.getApplicationContext()
            boolean r7 = com.android.settings.Utils.isPrivateProfile(r1, r7)
            if (r7 == 0) goto L7b
            r7 = 2132024616(0x7f141d28, float:1.9687713E38)
            goto L7e
        L7b:
            r7 = 2132027827(0x7f1429b3, float:1.9694226E38)
        L7e:
            r6.setDescriptionText(r7)
            com.android.settings.biometrics.fingerprint.feature.SfpsRestToUnlockFeatureImpl r7 = r6.mSfpsRestToUnlockFeature
            r1 = 0
            if (r7 == 0) goto L93
            r7 = 2132027826(0x7f1429b2, float:1.9694224E38)
            java.lang.String r7 = r6.getString(r7)
            java.lang.String r2 = "getString(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r2)
            goto L94
        L93:
            r7 = r1
        L94:
            boolean r2 = r6.mCanAssumeSfps
            if (r2 == 0) goto Lbf
            boolean r2 = android.text.TextUtils.isEmpty(r7)
            if (r2 != 0) goto Lbf
            android.hardware.fingerprint.FingerprintManager r2 = com.android.settings.Utils.getFingerprintManagerOrNull(r6)
            if (r2 == 0) goto Lbf
            java.util.List r3 = r2.getSensorPropertiesInternal()
            java.lang.Object r3 = r3.get(r0)
            android.hardware.fingerprint.FingerprintSensorPropertiesInternal r3 = (android.hardware.fingerprint.FingerprintSensorPropertiesInternal) r3
            int r3 = r3.maxEnrollmentsPerUser
            int r4 = r6.mUserId
            java.util.List r2 = r2.getEnrolledFingerprints(r4)
            int r2 = r2.size()
            if (r2 >= r3) goto Lbf
            r6.setDescriptionText(r7)
        Lbf:
            com.google.android.setupdesign.GlifLayout r7 = r6.getLayout()
            java.lang.Class<com.google.android.setupcompat.template.FooterBarMixin> r2 = com.google.android.setupcompat.template.FooterBarMixin.class
            com.google.android.setupcompat.template.Mixin r7 = r7.getMixin(r2)
            com.google.android.setupcompat.template.FooterBarMixin r7 = (com.google.android.setupcompat.template.FooterBarMixin) r7
            r6.mFooterBarMixin = r7
            r2 = 2132021135(0x7f140f8f, float:1.9680653E38)
            java.lang.String r2 = r6.getString(r2)
            com.google.android.setupcompat.template.FooterButton r3 = new com.google.android.setupcompat.template.FooterButton
            r4 = 7
            r5 = 2132083806(0x7f15045e, float:1.9807765E38)
            r3.<init>(r2, r1, r4, r5)
            r7.setSecondaryButton(r3, r0)
            com.google.android.setupcompat.template.FooterBarMixin r7 = r6.mFooterBarMixin
            r0 = 2132027812(0x7f1429a4, float:1.9694195E38)
            java.lang.String r0 = r6.getString(r0)
            com.android.settings.biometrics.fingerprint.FingerprintEnrollFinish$$ExternalSyntheticLambda0 r1 = new com.android.settings.biometrics.fingerprint.FingerprintEnrollFinish$$ExternalSyntheticLambda0
            r2 = 1
            r1.<init>(r6, r2)
            com.google.android.setupcompat.template.FooterButton r6 = new com.google.android.setupcompat.template.FooterButton
            r2 = 5
            r3 = 2132083805(0x7f15045d, float:1.9807763E38)
            r6.<init>(r0, r1, r2, r3)
            r7.setPrimaryButton(r6)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.fingerprint.FingerprintEnrollFinish.onCreate(android.os.Bundle):void");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        FooterButton footerButton = this.mFooterBarMixin.secondaryButton;
        FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(this);
        if (fingerprintManagerOrNull != null) {
            if (fingerprintManagerOrNull.getEnrolledFingerprints(this.mUserId).size()
                    >= ((FingerprintSensorPropertiesInternal)
                                    fingerprintManagerOrNull.getSensorPropertiesInternal().get(0))
                            .maxEnrollmentsPerUser) {
                footerButton.setVisibility(4);
                return;
            }
        }
        footerButton.onClickListener =
                new FingerprintEnrollFinish$$ExternalSyntheticLambda0(this, 0);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStart() {
        super.onStart();
        this.mIsAddAnotherOrFinish = false;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public final boolean shouldFinishWhenBackgrounded() {
        return !this.mIsAddAnotherOrFinish && super.shouldFinishWhenBackgrounded();
    }

    public final void updateFingerprintSuggestionEnableState() {
        FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(this);
        if (fingerprintManagerOrNull != null) {
            int size = fingerprintManagerOrNull.getEnrolledFingerprints(this.mUserId).size();
            getPackageManager()
                    .setComponentEnabledSetting(
                            new ComponentName(
                                    getApplicationContext(), FINGERPRINT_SUGGESTION_ACTIVITY),
                            size == 1 ? 1 : 2,
                            1);
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "com.android.settings.SetupFingerprintSuggestionActivity enabled state"
                                + " = "),
                    size == 1,
                    "FingerprintEnrollFinish");
        }
    }
}
