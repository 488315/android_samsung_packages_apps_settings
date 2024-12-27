package com.android.settings.password;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.core.graphics.Insets;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.notification.RedactionInterstitial;

import com.samsung.android.settings.lockscreen.LockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BiometricsChooseLockGeneric extends ChooseLockGeneric {
    public static View mMainLayout;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class BiometricsChooseLockGenericFragment
            extends ChooseLockGeneric.ChooseLockGenericFragment {
        public static final /* synthetic */ int $r8$clinit = 0;
        public View mHeaderView = null;
        public boolean mIsSetBiometricLock;
        public Intent mResultIntent;
        public int mUserId;

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment,
                  // androidx.fragment.app.Fragment
        public final void onActivityResult(int i, int i2, Intent intent) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                            "onActivityResult : requestCode : ",
                            " resultCode : ",
                            i,
                            i2,
                            " data is NULL : "),
                    intent == null,
                    "BiometricsChooseLockGeneric");
            if (i != 102 || i2 != 1) {
                if (i == 10100) {
                    this.mChoosePref = 0;
                    setResult(1, this.mResultIntent);
                    getActivity().overridePendingTransition(0, 0);
                    finish();
                    return;
                }
                return;
            }
            this.mResultIntent = intent;
            View view = BiometricsChooseLockGeneric.mMainLayout;
            if (view != null) {
                view.setVisibility(4);
            }
            Intent createStartIntent =
                    RedactionInterstitial.createStartIntent(getContext(), this.mUserId);
            if (createStartIntent != null) {
                this.mChoosePref |= 1;
                startActivityForResult(createStartIntent, 10100);
            } else {
                super.onActivityResult(i, i2, intent);
            }
            getActivity().overridePendingTransition(0, 0);
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment,
                  // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (!this.mForFace && !this.mForFingerprint) {
                finish();
                return;
            }
            this.mUserId = Utils.getUserIdFromBundle(getActivity(), getIntent().getExtras(), false);
            this.mIsSetBiometricLock = getIntent().getBooleanExtra("set_biometric_lock", false);
            int i =
                    this.mForFace
                            ? R.string.bio_face_recognition_title
                            : this.mForFingerprint ? R.string.sec_fingerprint : R.string.biometrics;
            setPreferenceTitle(ScreenLockType.PIN, i);
            setPreferenceTitle(ScreenLockType.PASSWORD, i);
            setPreferenceTitle(ScreenLockType.PATTERN, i);
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            Drawable drawable;
            super.onViewCreated(view, bundle);
            View pinnedHeaderView = setPinnedHeaderView(R.layout.sec_biometrics_lock_header_layout);
            this.mHeaderView = pinnedHeaderView;
            ImageView imageView = (ImageView) pinnedHeaderView.findViewById(R.id.icon_image);
            if (imageView != null
                    && (drawable = getContext().getDrawable(R.drawable.sec_ic_biometrics_lock))
                            != null) {
                imageView.setImageDrawable(drawable);
            }
            TextView textView = (TextView) this.mHeaderView.findViewById(R.id.description_text);
            if (textView != null) {
                textView.setText(
                        this.mForFace
                                ? R.string.sec_biometrics_set_secure_screen_lock_face
                                : this.mForFingerprint
                                        ? R.string
                                                .sec_biometrics_set_secure_screen_lock_fingerprints
                                        : R.string.sec_setup_lock_settings_picker_message);
                Utils.setMaxFontScale(getContext(), textView);
            }
            View inflate =
                    LayoutInflater.from(getActivity())
                            .inflate(R.layout.sec_biometrics_lock_cancel_button, (ViewGroup) null);
            Button button = (Button) inflate.findViewById(R.id.button);
            Utils.setMaxFontScale$1(getContext(), button);
            button.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.password.BiometricsChooseLockGeneric$BiometricsChooseLockGenericFragment$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            BiometricsChooseLockGeneric.BiometricsChooseLockGenericFragment
                                    biometricsChooseLockGenericFragment =
                                            BiometricsChooseLockGeneric
                                                    .BiometricsChooseLockGenericFragment.this;
                            int i =
                                    BiometricsChooseLockGeneric.BiometricsChooseLockGenericFragment
                                            .$r8$clinit;
                            biometricsChooseLockGenericFragment.finish();
                        }
                    });
            ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.button_bar);
            if (viewGroup != null) {
                viewGroup.removeAllViews();
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                layoutParams.gravity = 8388613;
                viewGroup.addView(inflate, layoutParams);
                viewGroup.setVisibility(0);
            }
            RecyclerView listViewWithSpacing = getListViewWithSpacing();
            if (listViewWithSpacing != null) {
                int dimension =
                        (int)
                                getResources()
                                        .getDimension(R.dimen.sec_biometrics_disclaimer_padding);
                if (LockUtils.isApplyingTabletGUI(getContext())) {
                    dimension = Utils.getListHorizontalPadding(getContext());
                }
                listViewWithSpacing.setPadding(dimension, 0, dimension, 0);
                final SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(getContext());
                seslRoundedCorner.setRoundedCorners(3);
                listViewWithSpacing.addItemDecoration(
                        new RecyclerView
                                .ItemDecoration() { // from class:
                                                    // com.android.settings.password.BiometricsChooseLockGeneric.BiometricsChooseLockGenericFragment.1
                            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                            public final void seslOnDispatchDraw(
                                    Canvas canvas,
                                    RecyclerView recyclerView,
                                    RecyclerView.State state) {
                                SeslRoundedCorner.this.drawRoundedCorner(
                                        canvas,
                                        Insets.of(
                                                recyclerView.getPaddingStart(),
                                                0,
                                                recyclerView.getPaddingEnd(),
                                                0));
                            }
                        });
            }
        }

        public final void setPreferenceTitle(ScreenLockType screenLockType, int i) {
            Preference findPreference = findPreference(screenLockType.preferenceKey);
            if (findPreference != null) {
                findPreference.setSummary((CharSequence) null);
                if (this.mIsSetBiometricLock) {
                    String language = getResources().getConfiguration().locale.getLanguage();
                    String str =
                            "ar".equals(language)
                                    ? "، "
                                    : "ja".equals(language)
                                            ? "、"
                                            : "zh".equals(language) ? "，" : ", ";
                    StringBuilder sb = new StringBuilder();
                    sb.append(findPreference.getTitle());
                    sb.append(str);
                    sb.append(getString(i));
                    findPreference.setTitle(sb);
                }
            }
        }
    }

    @Override // com.android.settings.password.ChooseLockGeneric
    public final Class getFragmentClass() {
        return BiometricsChooseLockGenericFragment.class;
    }

    @Override // com.android.settings.password.ChooseLockGeneric,
              // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return BiometricsChooseLockGenericFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.password.ChooseLockGeneric,
              // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mMainLayout = findViewById(R.id.content_parent);
        hideAppBar();
    }
}
