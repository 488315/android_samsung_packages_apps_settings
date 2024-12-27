package com.samsung.android.settings.biometrics;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.biometrics.face.FaceLockSettings;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings;
import com.samsung.android.settings.widget.SecUnclickablePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsTipPreference extends SecUnclickablePreference {
    public int mBiometricType;
    public final Context mContext;
    public long mGkPwHandle;
    public int mUserId;

    public BiometricsTipPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // com.samsung.android.settings.widget.SecUnclickablePreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ClickableSpan clickableSpan =
                new ClickableSpan() { // from class:
                                      // com.samsung.android.settings.biometrics.BiometricsTipPreference.1
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view) {
                        Intent intent;
                        view.playSoundEffect(0);
                        int i = BiometricsTipPreference.this.mBiometricType;
                        if (i == 256) {
                            intent =
                                    new Intent()
                                            .setClass(
                                                    BiometricsTipPreference.this.mContext,
                                                    FaceLockSettings.class);
                            intent.putExtra("previousStage", "lock_screen_face");
                        } else if (i == 1) {
                            intent =
                                    new Intent()
                                            .setClass(
                                                    BiometricsTipPreference.this.mContext,
                                                    FingerprintLockSettings.class);
                            intent.putExtra("previousStage", "lock_screen_fingerprint");
                        } else {
                            intent = null;
                        }
                        if (intent != null) {
                            intent.putExtra(
                                    "gk_pw_handle", BiometricsTipPreference.this.mGkPwHandle);
                            intent.putExtra("fromTip", true);
                            intent.putExtra(
                                    "android.intent.extra.USER_ID",
                                    BiometricsTipPreference.this.mUserId);
                            BiometricsTipPreference.this.mContext.startActivity(intent);
                        }
                    }
                };
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.tips_description);
        int i = this.mBiometricType;
        if (BiometricsGenericHelper.makeTipLink(
                this.mContext,
                textView,
                i == 256
                        ? R.string.sec_biometrics_tip_register_face
                        : i == 1 ? R.string.sec_biometrics_tip_register_fingerprints : 0,
                clickableSpan)) {
            textView.invalidate();
        } else {
            Log.w("BiometricsTipPreference", "Tip Link is wrong!!");
            setVisible(false);
        }
    }

    public BiometricsTipPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mGkPwHandle = 0L;
        this.mBiometricType = 0;
        this.mUserId = UserHandle.myUserId();
        this.mContext = context;
        setLayoutResource(R.layout.sec_biometrics_useful_features_tip);
    }
}
