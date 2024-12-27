package com.android.settings.applications.appcompat;

import android.app.ActivityManager;
import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RadioWithImagePreference extends CheckBoxPreference {
    public OnClickListener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickListener {}

    public RadioWithImagePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mListener = null;
        setWidgetLayoutResource(R.layout.preference_widget_radiobutton);
        setLayoutResource(R.layout.radio_with_image_preference);
        setIconSpaceReserved(false);
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.summary_container);
        if (findViewById != null) {
            findViewById.setVisibility(TextUtils.isEmpty(getSummary()) ? 8 : 0);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        char c;
        int i;
        int i2;
        int i3 = 0;
        OnClickListener onClickListener = this.mListener;
        if (onClickListener != null) {
            UserAspectRatioDetails userAspectRatioDetails =
                    (UserAspectRatioDetails) onClickListener;
            String key = getKey();
            if (userAspectRatioDetails.mSelectedKey.equals(key)) {
                return;
            }
            int selectedUserMinAspectRatio =
                    userAspectRatioDetails.getSelectedUserMinAspectRatio(key);
            try {
                userAspectRatioDetails
                        .getAspectRatioManager()
                        .mIPm
                        .setUserMinAspectRatio(
                                userAspectRatioDetails.mPackageName,
                                userAspectRatioDetails.mUserId,
                                selectedUserMinAspectRatio);
                String str = userAspectRatioDetails.mSelectedKey;
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                SettingsMetricsFeatureProvider metricsFeatureProvider =
                        featureFactoryImpl.getMetricsFeatureProvider();
                FragmentActivity activity = userAspectRatioDetails.getActivity();
                metricsFeatureProvider.getClass();
                int attribution = MetricsFeatureProvider.getAttribution(activity);
                str.getClass();
                switch (str.hashCode()) {
                    case -1374840932:
                        if (str.equals("3_2_pref")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -645634038:
                        if (str.equals("half_screen_pref")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -479265753:
                        if (str.equals("fullscreen_pref")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -464397212:
                        if (str.equals("display_size_pref")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -181964097:
                        if (str.equals("app_default_pref")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 396598554:
                        if (str.equals("4_3_pref")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1388071363:
                        if (str.equals("16_9_pref")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = 1878;
                        i2 = i;
                        break;
                    case 1:
                        i = 1872;
                        i2 = i;
                        break;
                    case 2:
                        i = 1870;
                        i2 = i;
                        break;
                    case 3:
                        i = 1880;
                        i2 = i;
                        break;
                    case 4:
                        i = 1868;
                        i2 = i;
                        break;
                    case 5:
                        i = 1874;
                        i2 = i;
                        break;
                    case 6:
                        i = 1876;
                        i2 = i;
                        break;
                    default:
                        i2 = 0;
                        break;
                }
                metricsFeatureProvider.action(
                        attribution,
                        i2,
                        2054,
                        userAspectRatioDetails.mUserId,
                        userAspectRatioDetails.mPackageName);
                key.getClass();
                switch (key) {
                    case "3_2_pref":
                        i3 = 1877;
                        break;
                    case "half_screen_pref":
                        i3 = 1871;
                        break;
                    case "fullscreen_pref":
                        i3 = 1869;
                        break;
                    case "display_size_pref":
                        i3 = 1879;
                        break;
                    case "app_default_pref":
                        i3 = 1867;
                        break;
                    case "4_3_pref":
                        i3 = 1873;
                        break;
                    case "16_9_pref":
                        i3 = 1875;
                        break;
                }
                metricsFeatureProvider.action(
                        attribution,
                        i3,
                        2054,
                        userAspectRatioDetails.mUserId,
                        userAspectRatioDetails.mPackageName);
                userAspectRatioDetails.mSelectedKey = key;
                Iterator it =
                        ((ArrayList) userAspectRatioDetails.mAspectRatioPreferences).iterator();
                while (it.hasNext()) {
                    RadioWithImagePreference radioWithImagePreference =
                            (RadioWithImagePreference) it.next();
                    radioWithImagePreference.setChecked(
                            key.equals(radioWithImagePreference.getKey()));
                }
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Killing application process "),
                        userAspectRatioDetails.mPackageName,
                        "UserAspectRatioDetails");
                try {
                    ActivityManager.getService()
                            .stopAppForUser(
                                    userAspectRatioDetails.mPackageName,
                                    userAspectRatioDetails.mUserId);
                } catch (RemoteException unused) {
                    MainClear$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Unable to stop application "),
                            userAspectRatioDetails.mPackageName,
                            "UserAspectRatioDetails");
                }
            } catch (RemoteException unused2) {
                Log.e("UserAspectRatioDetails", "Unable to set user min aspect ratio");
            }
        }
    }

    public RadioWithImagePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mListener = null;
        setWidgetLayoutResource(R.layout.preference_widget_radiobutton);
        setLayoutResource(R.layout.radio_with_image_preference);
        setIconSpaceReserved(false);
    }

    public RadioWithImagePreference(Context context) {
        this(context, null);
    }
}
