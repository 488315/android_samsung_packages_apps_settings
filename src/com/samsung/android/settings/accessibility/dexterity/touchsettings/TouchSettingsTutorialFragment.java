package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchSettingsTutorialFragment extends InstrumentedFragment {
    public Context mContext;
    public ImageView mImageView;
    public View mRootView;
    public Button mSetButton;
    public TextView mTutorialDescription;

    public int getMetricsCategory() {
        return 0;
    }

    public String getSharedPreferenceKey() {
        return null;
    }

    public final void launchFragment$1(int i, String str) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.addFlags(335544320);
        subSettingLauncher.setTitleRes(i, null);
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        SecAccessibilityUtils.getAccessibilitySharedPreferences(context)
                .getBoolean(getSharedPreferenceKey(), false);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getActivity().setRequestedOrientation(1);
        View inflate =
                layoutInflater.inflate(R.layout.layout_touch_settings_tutorial, viewGroup, false);
        this.mRootView = inflate;
        this.mTutorialDescription = (TextView) inflate.findViewById(R.id.tutorial_description);
        Button button = (Button) this.mRootView.findViewById(R.id.set_button);
        this.mSetButton = button;
        SecAccessibilityUtils.setButtonsColor(this.mContext, button);
        ImageView imageView = (ImageView) this.mRootView.findViewById(R.id.tutorial_image);
        this.mImageView = imageView;
        imageView.semSetRoundedCorners(15);
        this.mImageView.semSetRoundedCornerColor(
                15, getContext().getColor(R.color.rounded_corner_color));
        return this.mRootView;
    }

    public final void setDescription(int i) {
        this.mTutorialDescription.setText(i);
        this.mTutorialDescription.setMovementMethod(new ScrollingMovementMethod());
        this.mTutorialDescription.setClickable(false);
        this.mTutorialDescription.setLongClickable(false);
    }
}
