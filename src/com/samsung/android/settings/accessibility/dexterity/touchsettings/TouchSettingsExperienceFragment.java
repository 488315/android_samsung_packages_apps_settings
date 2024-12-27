package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchSettingsExperienceFragment extends InstrumentedFragment {
    public Button mBackButton;
    public FrameLayout mButtonContainer1;
    public FrameLayout mButtonContainer2;
    public FrameLayout mButtonContainer3;
    public Context mContext;
    public Button mDoneButton;
    public TextView mExperienceDescription;
    public FrameLayout.LayoutParams mParams;
    public LinearLayout mPressTimeContainer;
    public View mRootView;
    public FrameLayout mStatusContainer;
    public TextView mStatusTextView;
    public TextView mTimeTextView;
    public Button mTryAgainButton;
    public LinearLayout mTryAgainContainer;
    public final Handler mHandler = new Handler(Looper.myLooper());
    public Runnable mTimer = null;
    public long mStartPressTime = -1;
    public long mTotalPressedTime = 0;
    public Status mCurrenStatus = Status.STAND_BY;
    public final AnonymousClass1 onBackPressedCallback =
            new OnBackPressedCallback() { // from class:
                                          // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment.1
                @Override // androidx.activity.OnBackPressedCallback
                public final void handleOnBackPressed() {
                    TouchSettingsExperienceFragment.this.onBackPressed();
                }
            };

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class Status {
        public static final /* synthetic */ Status[] $VALUES;
        public static final Status STAND_BY;
        public static final Status TRY_AGAIN;

        static {
            Status status = new Status("STAND_BY", 0);
            STAND_BY = status;
            Status status2 = new Status("TRY_AGAIN", 1);
            TRY_AGAIN = status2;
            $VALUES = new Status[] {status, status2};
        }

        public static Status valueOf(String str) {
            return (Status) Enum.valueOf(Status.class, str);
        }

        public static Status[] values() {
            return (Status[]) $VALUES.clone();
        }
    }

    public int getMetricsCategory() {
        return 0;
    }

    public final void init() {
        this.mCurrenStatus = Status.STAND_BY;
        this.mExperienceDescription =
                (TextView) this.mRootView.findViewById(R.id.experience_description);
        this.mStatusTextView = (TextView) this.mRootView.findViewById(R.id.status_text);
        this.mStatusContainer = (FrameLayout) this.mRootView.findViewById(R.id.status_container);
        this.mPressTimeContainer =
                (LinearLayout) this.mRootView.findViewById(R.id.press_time_container);
        this.mTryAgainContainer =
                (LinearLayout) this.mRootView.findViewById(R.id.try_again_container);
        this.mBackButton = (Button) this.mRootView.findViewById(R.id.back_button);
        this.mDoneButton = (Button) this.mRootView.findViewById(R.id.done_button);
        this.mTryAgainButton = (Button) this.mRootView.findViewById(R.id.try_again_button);
        TextView textView = (TextView) this.mRootView.findViewById(R.id.long_press_time);
        this.mTimeTextView = textView;
        textView.setText(
                String.format(
                        Locale.getDefault(),
                        "%.2f",
                        Float.valueOf(this.mTotalPressedTime / 1000.0f)));
        this.mButtonContainer1 = (FrameLayout) this.mRootView.findViewById(R.id.button_container_1);
        this.mButtonContainer2 = (FrameLayout) this.mRootView.findViewById(R.id.button_container_2);
        this.mButtonContainer3 = (FrameLayout) this.mRootView.findViewById(R.id.button_container_3);
        LinearLayout linearLayout =
                (LinearLayout) this.mRootView.findViewById(R.id.button_container);
        linearLayout.semSetRoundedCorners(15);
        linearLayout.semSetRoundedCornerColor(
                15, getContext().getColor(R.color.rounded_corner_color));
        SecAccessibilityUtils.setButtonsColor(this.mContext, this.mDoneButton);
        this.mDoneButton.setEnabled(false);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        this.mParams = layoutParams;
        layoutParams.gravity = 17;
    }

    public final void launchFragment$1(int i, String str) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.setTitleRes(i, null);
        subSettingLauncher.addFlags(335544320);
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        requireActivity().getOnBackPressedDispatcher().addCallback(this.onBackPressedCallback);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        LayoutInflater from = LayoutInflater.from(getActivity());
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
            removeViews();
            this.mRootView = from.inflate(R.layout.layout_touch_settings_experience, viewGroup);
            initViews$1();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mRootView =
                layoutInflater.inflate(R.layout.layout_touch_settings_experience, viewGroup, false);
        setHasOptionsMenu(true);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        this.mMetricsFeatureProvider.clicked(getMetricsCategory(), "A11Y0001");
        return true;
    }

    public void initViews$1() {}

    public void onBackPressed() {}

    public void removeViews() {}
}
