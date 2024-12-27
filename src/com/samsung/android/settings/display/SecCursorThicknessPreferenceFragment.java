package com.samsung.android.settings.display;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecCursorThicknessPreferenceFragment extends SettingsPreferenceFragment {
    public FragmentActivity mContext;
    public View mCursorThicknessLayout;
    public SeslSeekBar mCursorThicknessSeekBar;
    public LinearLayout mCursorThicknessSeekBarContainer;
    public int mOrientation;
    public LinearLayout mPreviewContainer;
    public EditText mPreviewEditText;
    public Resources mResources;
    public ImageButton mThickerButton;
    public final AnonymousClass1 mThickerButtonClickListener;
    public ImageButton mThinnerButton;
    public final AnonymousClass1 mThinnerButtonClickListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OnCursorThicknessSeekBarChangeListener
            implements SeslSeekBar.OnSeekBarChangeListener {
        public final
        SecCursorThicknessPreferenceFragment$OnCursorThicknessSeekBarChangeListener$$ExternalSyntheticLambda0
                mCommit =
                        new Choreographer
                                .FrameCallback() { // from class:
                                                   // com.samsung.android.settings.display.SecCursorThicknessPreferenceFragment$OnCursorThicknessSeekBarChangeListener$$ExternalSyntheticLambda0
                            @Override // android.view.Choreographer.FrameCallback
                            public final void doFrame(long j) {
                                SecCursorThicknessPreferenceFragment
                                                .OnCursorThicknessSeekBarChangeListener
                                        onCursorThicknessSeekBarChangeListener =
                                                SecCursorThicknessPreferenceFragment
                                                        .OnCursorThicknessSeekBarChangeListener
                                                        .this;
                                onCursorThicknessSeekBarChangeListener.getClass();
                                onCursorThicknessSeekBarChangeListener.mLastCommitTime =
                                        SystemClock.elapsedRealtime();
                                Settings.System.putFloatForUser(
                                        SecCursorThicknessPreferenceFragment.this.mContext
                                                .getContentResolver(),
                                        "cursor_thickness",
                                        onCursorThicknessSeekBarChangeListener.mCurrentScale,
                                        -2);
                            }
                        };
        public long mCommitDelayMs;
        public float mCurrentScale;
        public long mLastCommitTime;
        public boolean mSeekByTouch;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.settings.display.SecCursorThicknessPreferenceFragment$OnCursorThicknessSeekBarChangeListener$$ExternalSyntheticLambda0] */
        public OnCursorThicknessSeekBarChangeListener() {}

        public final void commitOnNextFrame() {
            if (SystemClock.elapsedRealtime() - this.mLastCommitTime < 800) {
                this.mCommitDelayMs += 800;
            }
            Choreographer choreographer = Choreographer.getInstance();
            SecCursorThicknessPreferenceFragment$OnCursorThicknessSeekBarChangeListener$$ExternalSyntheticLambda0
                    secCursorThicknessPreferenceFragment$OnCursorThicknessSeekBarChangeListener$$ExternalSyntheticLambda0 =
                            this.mCommit;
            choreographer.removeFrameCallback(
                    secCursorThicknessPreferenceFragment$OnCursorThicknessSeekBarChangeListener$$ExternalSyntheticLambda0);
            choreographer.postFrameCallbackDelayed(
                    secCursorThicknessPreferenceFragment$OnCursorThicknessSeekBarChangeListener$$ExternalSyntheticLambda0,
                    this.mCommitDelayMs);
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
            SecCursorThicknessPreferenceFragment secCursorThicknessPreferenceFragment =
                    SecCursorThicknessPreferenceFragment.this;
            secCursorThicknessPreferenceFragment.getClass();
            if (i < 0) {
                i = 0;
            } else if (i > 4) {
                i = 4;
            }
            this.mCurrentScale =
                    Float.parseFloat(
                            secCursorThicknessPreferenceFragment
                                    .mResources
                                    .getStringArray(R.array.sec_cursor_thickness_scale)[i]);
            if (z) {
                seslSeekBar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
            }
            secCursorThicknessPreferenceFragment.mPreviewEditText.setCursorThicknessScale(
                    this.mCurrentScale);
            if (this.mSeekByTouch) {
                this.mCommitDelayMs = 100L;
            } else {
                this.mCommitDelayMs = 300L;
                commitOnNextFrame();
            }
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
            this.mSeekByTouch = true;
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
            this.mSeekByTouch = false;
            commitOnNextFrame();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.display.SecCursorThicknessPreferenceFragment$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.display.SecCursorThicknessPreferenceFragment$1] */
    public SecCursorThicknessPreferenceFragment() {
        final int i = 0;
        this.mThinnerButtonClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.SecCursorThicknessPreferenceFragment.1
                    public final /* synthetic */ SecCursorThicknessPreferenceFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                int progress = this.this$0.mCursorThicknessSeekBar.getProgress();
                                if (progress > this.this$0.mCursorThicknessSeekBar.getMin()) {
                                    this.this$0.mCursorThicknessSeekBar.setProgressInternal(
                                            progress - 1, false, true);
                                    break;
                                }
                                break;
                            default:
                                int progress2 = this.this$0.mCursorThicknessSeekBar.getProgress();
                                if (progress2 < this.this$0.mCursorThicknessSeekBar.getMax()) {
                                    this.this$0.mCursorThicknessSeekBar.setProgressInternal(
                                            progress2 + 1, false, true);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mThickerButtonClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.SecCursorThicknessPreferenceFragment.1
                    public final /* synthetic */ SecCursorThicknessPreferenceFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                int progress = this.this$0.mCursorThicknessSeekBar.getProgress();
                                if (progress > this.this$0.mCursorThicknessSeekBar.getMin()) {
                                    this.this$0.mCursorThicknessSeekBar.setProgressInternal(
                                            progress - 1, false, true);
                                    break;
                                }
                                break;
                            default:
                                int progress2 = this.this$0.mCursorThicknessSeekBar.getProgress();
                                if (progress2 < this.this$0.mCursorThicknessSeekBar.getMax()) {
                                    this.this$0.mCursorThicknessSeekBar.setProgressInternal(
                                            progress2 + 1, false, true);
                                    break;
                                }
                                break;
                        }
                    }
                };
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = this.mOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.mOrientation = i2;
            setCursorThicknessPreviewLayout();
            this.mPreviewEditText.setSelection(0);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        Resources resources = activity.getResources();
        this.mResources = resources;
        this.mOrientation = resources.getConfiguration().orientation;
        getActivity().getWindow().setSoftInputMode(3);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_cursor_thickness, (ViewGroup) null);
        this.mCursorThicknessLayout = inflate;
        LinearLayout linearLayout =
                (LinearLayout) inflate.findViewById(R.id.sec_cursor_thickness_layout);
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        int i = 0;
        linearLayout.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        setCursorThicknessPreviewLayout();
        this.mCursorThicknessSeekBarContainer =
                (LinearLayout)
                        this.mCursorThicknessLayout.findViewById(
                                R.id.cursor_thickness_seek_bar_container);
        int color = this.mContext.getColor(R.color.sec_widget_round_and_bgcolor);
        this.mCursorThicknessSeekBarContainer.semSetRoundedCorners(15);
        this.mCursorThicknessSeekBarContainer.semSetRoundedCornerColor(15, color);
        SeslSeekBar seslSeekBar =
                (SeslSeekBar)
                        this.mCursorThicknessLayout.findViewById(R.id.cursor_thickness_seek_bar);
        this.mCursorThicknessSeekBar = seslSeekBar;
        seslSeekBar.setMax(4);
        this.mCursorThicknessSeekBar.setMode(8);
        float floatForUser =
                Settings.System.getFloatForUser(
                        this.mContext.getContentResolver(), "cursor_thickness", 1.0f, -2);
        String[] stringArray = this.mResources.getStringArray(R.array.sec_cursor_thickness_scale);
        while (true) {
            if (i >= stringArray.length) {
                i = -1;
                break;
            }
            if (Float.parseFloat(stringArray[i]) == floatForUser) {
                break;
            }
            i++;
        }
        this.mCursorThicknessSeekBar.setOnSeekBarChangeListener(
                new OnCursorThicknessSeekBarChangeListener());
        this.mCursorThicknessSeekBar.setProgress(i);
        this.mThinnerButton =
                (ImageButton)
                        this.mCursorThicknessLayout.findViewById(
                                R.id.cursor_thickness_seek_bar_thinner_button);
        this.mThickerButton =
                (ImageButton)
                        this.mCursorThicknessLayout.findViewById(
                                R.id.cursor_thickness_seek_bar_thicker_button);
        this.mThinnerButton.setOnClickListener(this.mThinnerButtonClickListener);
        this.mThickerButton.setOnClickListener(this.mThickerButtonClickListener);
        return this.mCursorThicknessLayout;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mPreviewEditText.setSelection(0);
    }

    public final void setCursorThicknessPreviewLayout() {
        View view = this.mCursorThicknessLayout;
        if (view == null) {
            return;
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.portrait_layout);
        LinearLayout linearLayout2 =
                (LinearLayout) this.mCursorThicknessLayout.findViewById(R.id.landscape_layout);
        View findViewById =
                this.mCursorThicknessLayout.findViewById(R.id.portrait_layout_separator);
        View findViewById2 =
                this.mCursorThicknessLayout.findViewById(R.id.landscape_layout_separator);
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 =
                (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        if (this.mOrientation == 1) {
            layoutParams2.weight = 0.0f;
            layoutParams.weight = 1.0f;
            findViewById.setVisibility(0);
            findViewById2.setVisibility(8);
            this.mCursorThicknessLayout
                    .findViewById(R.id.cursor_thickness_preview_container_landscape)
                    .setVisibility(8);
            this.mCursorThicknessLayout
                    .findViewById(R.id.cursor_thickness_preview_edittext_landscape)
                    .clearFocus();
            this.mPreviewContainer =
                    (LinearLayout)
                            this.mCursorThicknessLayout.findViewById(
                                    R.id.cursor_thickness_preview_container_portrait);
            this.mPreviewEditText =
                    (EditText)
                            this.mCursorThicknessLayout.findViewById(
                                    R.id.cursor_thickness_preview_edittext_portrait);
            DisplayMetrics displayMetrics = this.mResources.getDisplayMetrics();
            ViewGroup.LayoutParams layoutParams3 = this.mPreviewContainer.getLayoutParams();
            layoutParams3.height = Math.round(displayMetrics.heightPixels * 0.4f);
            this.mPreviewContainer.setLayoutParams(layoutParams3);
        } else {
            layoutParams2.weight = 5.0f;
            layoutParams.weight = 5.0f;
            findViewById.setVisibility(8);
            findViewById2.setVisibility(0);
            this.mCursorThicknessLayout
                    .findViewById(R.id.cursor_thickness_preview_container_portrait)
                    .setVisibility(8);
            this.mCursorThicknessLayout
                    .findViewById(R.id.cursor_thickness_preview_edittext_portrait)
                    .clearFocus();
            this.mPreviewContainer =
                    (LinearLayout)
                            this.mCursorThicknessLayout.findViewById(
                                    R.id.cursor_thickness_preview_container_landscape);
            this.mPreviewEditText =
                    (EditText)
                            this.mCursorThicknessLayout.findViewById(
                                    R.id.cursor_thickness_preview_edittext_landscape);
        }
        this.mPreviewContainer.setVisibility(0);
        linearLayout2.setLayoutParams(layoutParams2);
        linearLayout.setLayoutParams(layoutParams);
        int color = this.mContext.getColor(R.color.sec_widget_round_and_bgcolor);
        this.mPreviewContainer.semSetRoundedCorners(15);
        this.mPreviewContainer.semSetRoundedCornerColor(15, color);
        this.mPreviewEditText.requestFocus();
        this.mPreviewEditText.setOnTouchListener(
                new SecCursorThicknessPreferenceFragment$$ExternalSyntheticLambda0());
    }
}
