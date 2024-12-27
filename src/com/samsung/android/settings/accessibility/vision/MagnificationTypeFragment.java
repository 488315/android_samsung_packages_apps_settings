package com.samsung.android.settings.accessibility.vision;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceCategory;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.accessibility.MagnificationCapabilities;
import com.android.settings.dashboard.DashboardFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MagnificationTypeFragment extends DashboardFragment {
    public LinearLayout mAnimationLayout;
    public int mAnimationRawResId;
    public LottieAnimationView mAnimationView;
    public static final int[] magnificationTypeRawId = {
        R.raw.accessibility_screen_magnification_full,
        R.raw.accessibility_screen_magnification_window,
        R.raw.accessibility_screen_magnification_all
    };
    public static final int[] magnificationTypeDescriptionId = {
        R.string.magnification_type_full_description,
        R.string.magnification_type_window_description,
        R.string.magnification_type_all_description
    };
    public final boolean mAnimationAutoPlay = true;
    public final boolean mAnimationLoop = true;
    public final AnonymousClass1 mContentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.vision.MagnificationTypeFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    MagnificationTypeFragment.this.setAnimationRawRes();
                }
            };

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3183;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.magnification_type;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            if (Utils.isTablet()) {
                HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
                if (ActivityEmbeddingController.getInstance(activity)
                        .isActivityEmbedded(activity)) {
                    activity.setTheme(2132084369);
                }
            }
            activity.setTheme(2132084354);
        }
        super.onAttach(context);
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("accessibility_magnification_capability"),
                        false,
                        this.mContentObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        int i = getResources().getConfiguration().orientation;
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) findPreference("empty_category");
        setAnimationRawRes();
        if (getPreferenceScreen() != null) {
            if (Utils.isTablet() || i != 2) {
                getPreferenceScreen().addPreference(preferenceCategory);
            } else {
                getPreferenceScreen().removePreference(preferenceCategory);
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mAnimationView =
                (LottieAnimationView) onCreateView.findViewById(R.id.preview_animation);
        LinearLayout linearLayout = (LinearLayout) onCreateView.findViewById(R.id.animation_area);
        this.mAnimationLayout = linearLayout;
        if (linearLayout != null) {
            linearLayout.semSetRoundedCorners(15);
            this.mAnimationLayout.semSetRoundedCornerColor(
                    15, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        setAnimationRawRes();
        return onCreateView;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        super.onDetach();
        try {
            getContentResolver().unregisterContentObserver(this.mContentObserver);
        } catch (IllegalArgumentException unused) {
        }
    }

    public final void setAnimationRawRes() {
        int capabilities = MagnificationCapabilities.getCapabilities(getContext()) - 1;
        this.mAnimationRawResId = magnificationTypeRawId[capabilities];
        int i = magnificationTypeDescriptionId[capabilities];
        LottieAnimationView lottieAnimationView = this.mAnimationView;
        if (lottieAnimationView != null) {
            if (i != 0) {
                lottieAnimationView.setContentDescription(getString(i));
            }
            if (this.mAnimationRawResId != 0) {
                this.mAnimationView.setVisibility(0);
                this.mAnimationView.setAnimation(this.mAnimationRawResId);
                this.mAnimationView.setRepeatCount(this.mAnimationLoop ? -1 : 0);
                if (this.mAnimationAutoPlay) {
                    this.mAnimationView.playAnimation$1();
                }
            }
        }
    }
}
