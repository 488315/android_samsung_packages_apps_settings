package com.android.settings.biometrics.face;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.R;
import com.android.settings.biometrics.BiometricEnrollSidecar;
import com.android.settings.biometrics.BiometricErrorDialog;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.biometrics.BiometricsEnrollEnrolling;
import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.BiometricsSplitScreenDialog;
import com.android.settings.slices.CustomSliceRegistry;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceEnrollEnrolling extends BiometricsEnrollEnrolling {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextView mErrorText;
    public Interpolator mLinearOutSlowInInterpolator;
    public FaceEnrollPreviewFragment mPreviewFragment;
    public final ArrayList mDisabledFeatures = new ArrayList();
    public final AnonymousClass1 mListener =
            new ParticleCollection
                    .Listener() { // from class:
                                  // com.android.settings.biometrics.face.FaceEnrollEnrolling.1
                @Override // com.android.settings.biometrics.face.ParticleCollection.Listener
                public final void onEnrolled() {
                    int i = FaceEnrollEnrolling.$r8$clinit;
                    FaceEnrollEnrolling faceEnrollEnrolling = FaceEnrollEnrolling.this;
                    faceEnrollEnrolling.launchFinish(faceEnrollEnrolling.mToken);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class FaceErrorDialog extends BiometricErrorDialog {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 1510;
        }
    }

    @Override // com.android.settings.biometrics.BiometricsEnrollEnrolling
    public final Intent getFinishIntent() {
        return new Intent(this, (Class<?>) FaceEnrollFinish.class);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.VONR_NOT_POSSIBLE;
    }

    @Override // com.android.settings.biometrics.BiometricsEnrollEnrolling
    public final BiometricEnrollSidecar getSidecar() {
        int[] iArr = new int[this.mDisabledFeatures.size()];
        for (int i = 0; i < this.mDisabledFeatures.size(); i++) {
            iArr[i] = ((Integer) this.mDisabledFeatures.get(i)).intValue();
        }
        return new FaceEnrollSidecar(iArr, getIntent());
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isInMultiWindowMode()
                && !ActivityEmbeddingController.getInstance(this).isActivityEmbedded(this)) {
            BiometricsSplitScreenDialog biometricsSplitScreenDialog =
                    new BiometricsSplitScreenDialog();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("biometrics_modality", 8);
            bundle2.putBoolean("destroy_activity", true);
            biometricsSplitScreenDialog.setArguments(bundle2);
            biometricsSplitScreenDialog.setCancelable(false);
            biometricsSplitScreenDialog.show(
                    getSupportFragmentManager(), BiometricsSplitScreenDialog.class.getName());
        }
        setContentView(R.layout.face_enroll_enrolling);
        setHeaderText(R.string.security_settings_face_enroll_repeat_title);
        this.mErrorText = (TextView) findViewById(R.id.error_text);
        this.mLinearOutSlowInInterpolator =
                AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        FooterBarMixin footerBarMixin = (FooterBarMixin) getLayout().getMixin(FooterBarMixin.class);
        this.mFooterBarMixin = footerBarMixin;
        footerBarMixin.setSecondaryButton(
                new FooterButton(
                        getString(R.string.security_settings_face_enroll_enrolling_skip),
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.biometrics.face.FaceEnrollEnrolling$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                FaceEnrollEnrolling faceEnrollEnrolling = FaceEnrollEnrolling.this;
                                int i = FaceEnrollEnrolling.$r8$clinit;
                                faceEnrollEnrolling.cancelEnrollment();
                                faceEnrollEnrolling.setResult(2);
                                faceEnrollEnrolling.finish();
                            }
                        },
                        7,
                        2132083806),
                false);
        if (!getIntent().getBooleanExtra("accessibility_diversity", true)) {
            this.mDisabledFeatures.add(2);
        }
        if (!getIntent().getBooleanExtra("accessibility_vision", true)) {
            this.mDisabledFeatures.add(1);
        }
        if (!isInMultiWindowMode()
                || ActivityEmbeddingController.getInstance(this).isActivityEmbedded(this)) {
            startEnrollmentInternal();
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentError(int i, CharSequence charSequence) {
        int i2 =
                i != 3
                        ? R.string.security_settings_face_enroll_error_generic_dialog_message
                        : R.string.security_settings_face_enroll_error_timeout_dialog_message;
        this.mPreviewFragment.onEnrollmentError(i, charSequence);
        CharSequence text = getText(i2);
        FaceErrorDialog faceErrorDialog = new FaceErrorDialog();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("error_msg", text);
        bundle.putInt("error_id", i);
        faceErrorDialog.setArguments(bundle);
        faceErrorDialog.show(getSupportFragmentManager(), FaceErrorDialog.class.getName());
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentHelp(int i, CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            showError(charSequence);
        }
        this.mPreviewFragment.onEnrollmentHelp(i, charSequence);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentProgressChange(int i, int i2) {
        this.mPreviewFragment.onEnrollmentProgressChange(i, i2);
        showError("Steps: " + i + " Remaining: " + i2);
        if (i2 == 0) {
            getApplicationContext()
                    .getContentResolver()
                    .notifyChange(CustomSliceRegistry.FACE_ENROLL_SLICE_URI, null);
            launchFinish(this.mToken);
        }
    }

    @Override // com.android.settings.biometrics.BiometricsEnrollEnrolling,
              // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        if (!isChangingConfigurations()) {
            if (!WizardManagerHelper.isAnySetupWizard(getIntent())
                    && !BiometricUtils.isAnyMultiBiometricFlow(this)) {
                setResult(3);
            }
            finish();
        }
        super.onStop();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public final boolean shouldFinishWhenBackgrounded() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricsEnrollEnrolling
    public final boolean shouldStartAutomatically() {
        return false;
    }

    public final void showError(CharSequence charSequence) {
        this.mErrorText.setText(charSequence);
        if (this.mErrorText.getVisibility() != 4) {
            this.mErrorText.animate().cancel();
            this.mErrorText.setAlpha(1.0f);
            this.mErrorText.setTranslationY(0.0f);
        } else {
            this.mErrorText.setVisibility(0);
            this.mErrorText.setTranslationY(
                    getResources()
                            .getDimensionPixelSize(R.dimen.fingerprint_error_text_appear_distance));
            this.mErrorText.setAlpha(0.0f);
            this.mErrorText
                    .animate()
                    .alpha(1.0f)
                    .translationY(0.0f)
                    .setDuration(200L)
                    .setInterpolator(this.mLinearOutSlowInInterpolator)
                    .start();
        }
    }

    @Override // com.android.settings.biometrics.BiometricsEnrollEnrolling
    public final void startEnrollmentInternal() {
        if (!(isInMultiWindowMode()
                && !ActivityEmbeddingController.getInstance(this).isActivityEmbedded(this))) {
            startEnrollmentInternal();
        }
        FaceEnrollPreviewFragment faceEnrollPreviewFragment =
                (FaceEnrollPreviewFragment)
                        getSupportFragmentManager().findFragmentByTag("tag_preview");
        this.mPreviewFragment = faceEnrollPreviewFragment;
        if (faceEnrollPreviewFragment == null) {
            this.mPreviewFragment = new FaceEnrollPreviewFragment();
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            BackStackRecord m =
                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                            supportFragmentManager, supportFragmentManager);
            m.doAddOp(0, this.mPreviewFragment, "tag_preview", 1);
            m.commitInternal(true);
        }
        this.mPreviewFragment.mListener = this.mListener;
    }
}
