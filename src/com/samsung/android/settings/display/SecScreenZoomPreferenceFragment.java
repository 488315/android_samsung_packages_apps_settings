package com.samsung.android.settings.display;

import android.app.ActivityClient;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Choreographer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManagerGlobal;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.widget.SecLabeledSeekBar;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.configuration.DATA;

import java.io.File;
import java.lang.reflect.Array;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecScreenZoomPreferenceFragment extends SettingsPreferenceFragment {
    public static final float[] DENSITY_BASE_PIXEL;
    public static boolean mIsListenerRegistered;
    public View mContent;
    public Context mContext;
    public float mConvertRatio;
    public int mCurrentIndex;
    public DisplayMetrics mDisplayMetrics;
    public final Point mDisplaySize = new Point();
    public SecFontTrialSettings mFontTrial;
    public int mInitialIndex;
    public boolean mIsBottomButtonVisible;
    public boolean mIsFromAccessibility;
    public boolean mIsMultiPaneMode;
    public boolean mIsMultiWindowMode;
    public boolean mIsTabletDevice;
    public View mLarger;
    public long mLastCommitTime;
    public int mOrientation;
    public ViewPager mPreviewPager;
    public SecPreviewPagerAdapter mPreviewPagerAdapter;
    public Resources mResources;
    public SecLabeledSeekBar mSeekBar;
    public View mSmaller;
    public int[] mValues;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class onPreviewKeyListener implements View.OnKeyListener {
        public onPreviewKeyListener() {}

        @Override // android.view.View.OnKeyListener
        public final boolean onKey(View view, int i, KeyEvent keyEvent) {
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onKey: code=", " action=");
            m.append(keyEvent.getAction());
            Log.d("SecScreenZoomPreferenceFragment", m.toString());
            if (keyEvent.getAction() != 0) {
                return false;
            }
            int progress = SecScreenZoomPreferenceFragment.this.mSeekBar.getProgress();
            if (i != 21) {
                if (i != 22) {
                    if (i != 69) {
                        if (i != 81) {
                            return false;
                        }
                    }
                }
                if (progress < SecScreenZoomPreferenceFragment.this.mSeekBar.getMax()) {
                    int i2 = progress + 1;
                    TooltipPopup$$ExternalSyntheticOutline0.m(
                            new StringBuilder("plus key: progress="), i2, "ScreenZoomEvent");
                    SecScreenZoomPreferenceFragment.this.mSeekBar.setProgress(i2, true);
                }
                return true;
            }
            if (progress > 0) {
                int i3 = progress - 1;
                TooltipPopup$$ExternalSyntheticOutline0.m(
                        new StringBuilder("minus key: progress="), i3, "ScreenZoomEvent");
                SecScreenZoomPreferenceFragment.this.mSeekBar.setProgress(i3, true);
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class onPreviewSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public final
        SecScreenZoomPreferenceFragment$onPreviewSeekBarChangeListener$$ExternalSyntheticLambda0
                mCommit =
                        new Choreographer
                                .FrameCallback() { // from class:
                                                   // com.samsung.android.settings.display.SecScreenZoomPreferenceFragment$onPreviewSeekBarChangeListener$$ExternalSyntheticLambda0
                            @Override // android.view.Choreographer.FrameCallback
                            public final void doFrame(long j) {
                                SecScreenZoomPreferenceFragment.onPreviewSeekBarChangeListener
                                        onpreviewseekbarchangelistener =
                                                SecScreenZoomPreferenceFragment
                                                        .onPreviewSeekBarChangeListener
                                                        .this;
                                SecScreenZoomPreferenceFragment secScreenZoomPreferenceFragment =
                                        SecScreenZoomPreferenceFragment.this;
                                secScreenZoomPreferenceFragment.getClass();
                                int i = secScreenZoomPreferenceFragment.mCurrentIndex;
                                int i2 = secScreenZoomPreferenceFragment.mValues[i];
                                DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                                        "commit: index=", " densityDpi=", i, i2, "ScreenZoomEvent");
                                if (i2 != 0) {
                                    SecDisplayUtils.applyForcedDisplayDensity(-1, -1, i2);
                                }
                                SecScreenZoomPreferenceFragment.this.mLastCommitTime =
                                        SystemClock.elapsedRealtime();
                                Log.i(
                                        "ScreenZoomEvent",
                                        "commit: mLastCommitTime="
                                                + SecScreenZoomPreferenceFragment.this
                                                        .mLastCommitTime);
                            }
                        };
        public long mCommitDelayMs;
        public boolean mIsChanged;
        public boolean mSeekByTouch;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.settings.display.SecScreenZoomPreferenceFragment$onPreviewSeekBarChangeListener$$ExternalSyntheticLambda0] */
        public onPreviewSeekBarChangeListener() {}

        public final void commitOnNextFrame() {
            if (SystemClock.elapsedRealtime() - SecScreenZoomPreferenceFragment.this.mLastCommitTime
                    < 800) {
                this.mCommitDelayMs += 800;
                Log.i("ScreenZoomEvent", "commit delay update: delayMs=" + this.mCommitDelayMs);
            }
            Choreographer choreographer = Choreographer.getInstance();
            choreographer.removeFrameCallback(this.mCommit);
            choreographer.postFrameCallbackDelayed(this.mCommit, this.mCommitDelayMs);
            Log.i("ScreenZoomEvent", "post commit: delayMs=" + this.mCommitDelayMs);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            Log.d(
                    "SecScreenZoomPreferenceFragment",
                    "onProgressChanged: progress=" + i + " fromUser= " + z);
            SecScreenZoomPreferenceFragment secScreenZoomPreferenceFragment =
                    SecScreenZoomPreferenceFragment.this;
            if (secScreenZoomPreferenceFragment.mCurrentIndex == i) {
                this.mIsChanged = false;
                return;
            }
            this.mIsChanged = true;
            secScreenZoomPreferenceFragment.setPreviewLayer(i);
            if (this.mSeekByTouch) {
                this.mCommitDelayMs = 100L;
                Log.i(
                        "ScreenZoomEvent",
                        "onProgressChanged: mSeekByTouch=true delayMs=" + this.mCommitDelayMs);
            } else {
                this.mCommitDelayMs = 300L;
                Log.i(
                        "ScreenZoomEvent",
                        "onProgressChanged: mSeekByTouch=false delayMs=" + this.mCommitDelayMs);
                commitOnNextFrame();
            }
            if (Utils.isTalkBackEnabled(SecScreenZoomPreferenceFragment.this.mContext)
                    || (!this.mSeekByTouch && z)) {
                SecScreenZoomPreferenceFragment.this.getClass();
                LoggingHelper.insertEventLogging(7448, 7430, i + 1);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            Log.d(
                    "SecScreenZoomPreferenceFragment",
                    "onStartTrackingTouch: progress="
                            + SecScreenZoomPreferenceFragment.this.mSeekBar.getProgress());
            this.mSeekByTouch = true;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            this.mSeekByTouch = false;
            SecScreenZoomPreferenceFragment.this.getClass();
            LoggingHelper.insertEventLogging(7448, 7430, seekBar.getProgress() + 1);
            if (this.mIsChanged) {
                SecPreviewPagerAdapter secPreviewPagerAdapter =
                        SecScreenZoomPreferenceFragment.this.mPreviewPagerAdapter;
                if (secPreviewPagerAdapter == null
                        || secPreviewPagerAdapter.mAnimationCounter <= 0) {
                    commitOnNextFrame();
                } else {
                    secPreviewPagerAdapter.mAnimationEndAction =
                            new SecScreenZoomPreferenceFragment$$ExternalSyntheticLambda2(1, this);
                }
                Log.d(
                        "SecScreenZoomPreferenceFragment",
                        "onStopTrackingTouch: progress="
                                + SecScreenZoomPreferenceFragment.this.mSeekBar.getProgress());
            }
        }
    }

    static {
        DENSITY_BASE_PIXEL =
                Build.VERSION.SEM_PLATFORM_INT >= 110100
                        ? new float[] {3.5f, 3.75f, 4.0f, 4.25f, 4.5f}
                        : new float[] {3.5f, 4.0f, 4.5f};
        mIsListenerRegistered = false;
    }

    public final View generateContent(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        int i2;
        SecLabeledSeekBar secLabeledSeekBar;
        final int i3 = 0;
        if (bundle != null) {
            this.mLastCommitTime = bundle.getLong("mLastCommitTime");
            Log.i("ScreenZoomEvent", "generateContent: mLastCommitTime=" + this.mLastCommitTime);
        }
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        this.mDisplayMetrics = this.mResources.getDisplayMetrics();
        this.mOrientation = this.mResources.getConfiguration().orientation;
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        Configuration taskConfiguration =
                ActivityClient.getInstance().getTaskConfiguration(context.getActivityToken());
        this.mIsMultiWindowMode =
                taskConfiguration != null
                        && WindowConfiguration.inMultiWindowMode(
                                taskConfiguration.windowConfiguration.getWindowingMode());
        boolean isActivityEmbedded =
                ActivityEmbeddingController.getInstance(getActivity())
                        .isActivityEmbedded(getActivity());
        this.mIsMultiPaneMode = isActivityEmbedded;
        mIsListenerRegistered = false;
        View inflate =
                layoutInflater.inflate(
                        this.mFontTrial != null
                                ? R.layout.sec_font_trial
                                : (this.mIsTabletDevice && isActivityEmbedded)
                                        ? R.layout.sec_screen_zoom_preview_tablet
                                        : this.mIsMultiWindowMode
                                                ? R.layout.sec_screen_zoom_preview_mw
                                                : R.layout.sec_screen_zoom_preview,
                        viewGroup);
        this.mContent = inflate;
        final SecFontTrialSettings secFontTrialSettings = this.mFontTrial;
        if (secFontTrialSettings != null) {
            final Resources resources = secFontTrialSettings.mContext.getResources();
            Typeface previewFont =
                    SecDisplayUtils.getPreviewFont(
                            secFontTrialSettings.mContext,
                            (Uri) secFontTrialSettings.mIntent.getParcelableExtra("fontFileUri"));
            FontPreviewBubbleListAdapter fontPreviewBubbleListAdapter =
                    new FontPreviewBubbleListAdapter();
            secFontTrialSettings.mBubbleListViewAdapter = fontPreviewBubbleListAdapter;
            fontPreviewBubbleListAdapter.mTypeface = previewFont;
            final ListView listView =
                    (ListView) inflate.findViewById(R.id.font_preview_bubble_list_view);
            listView.setAdapter((ListAdapter) secFontTrialSettings.mBubbleListViewAdapter);
            listView.addOnLayoutChangeListener(
                    new View
                            .OnLayoutChangeListener() { // from class:
                                                        // com.samsung.android.settings.display.SecFontTrialSettings$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(
                                View view,
                                int i4,
                                int i5,
                                int i6,
                                int i7,
                                int i8,
                                int i9,
                                int i10,
                                int i11) {
                            ListView listView2 = listView;
                            int i12 = i11 - i7;
                            if (i12 > 0) {
                                listView2.semSmoothScrollBy(i12);
                            }
                        }
                    });
            if (bundle != null) {
                String[] stringArray = bundle.getStringArray("PreviewFontMessageList");
                int i4 = 0;
                while (i4 < stringArray.length) {
                    secFontTrialSettings.mBubbleListViewAdapter.addItem(stringArray[i4], i4 == 1);
                    i4++;
                }
            } else {
                secFontTrialSettings.mBubbleListViewAdapter.addItem(
                        resources.getString(R.string.font_preview_bubble_list_header_message1),
                        false);
                secFontTrialSettings.mBubbleListViewAdapter.addItem(
                        resources.getString(R.string.font_preview_bubble_list_header_message2),
                        true);
                secFontTrialSettings.mBubbleListViewAdapter.addItem(
                        resources.getString(
                                R.string.font_preview_bubble_list_header_message3,
                                secFontTrialSettings.mIntent.getStringExtra("fontName")),
                        false);
            }
            final TextView textView =
                    (TextView) inflate.findViewById(R.id.font_preview_bubble_count_text_view);
            textView.setTypeface(previewFont);
            final EditText editText = (EditText) inflate.findViewById(R.id.font_preview_edit_text);
            editText.requestFocus();
            editText.setTypeface(previewFont);
            if (secFontTrialSettings.mBubbleListViewAdapter.mFontPreviewBubbleList.size() - 3
                    >= 20) {
                editText.setEnabled(false);
            }
            editText.addTextChangedListener(
                    new TextWatcher() { // from class:
                                        // com.samsung.android.settings.display.SecFontTrialSettings.1
                        public final /* synthetic */ Resources val$res;
                        public final /* synthetic */ TextView val$simpleTrialBubbleCountTextView;

                        public AnonymousClass1(
                                final TextView textView2, final Resources resources2) {
                            r2 = textView2;
                            r3 = resources2;
                        }

                        @Override // android.text.TextWatcher
                        public final void onTextChanged(
                                CharSequence charSequence, int i5, int i6, int i7) {
                            if (charSequence.length() == 0) {
                                r2.setVisibility(8);
                            } else if (r2.getVisibility() != 0) {
                                r2.setVisibility(0);
                            }
                            r2.setText(charSequence.length() + " / 100");
                            if (charSequence.length() >= 100) {
                                if (SecFontTrialSettings.this.mMaxCharacterToast == null) {
                                    String string =
                                            r3.getString(
                                                    R.string
                                                            .font_preview_max_characters_toast_message,
                                                    100);
                                    SecFontTrialSettings secFontTrialSettings2 =
                                            SecFontTrialSettings.this;
                                    secFontTrialSettings2.mMaxCharacterToast =
                                            Toast.makeText(
                                                    secFontTrialSettings2.mContext, string, 0);
                                }
                                SecFontTrialSettings.this.mMaxCharacterToast.show();
                            }
                        }

                        @Override // android.text.TextWatcher
                        public final void afterTextChanged(Editable editable) {}

                        @Override // android.text.TextWatcher
                        public final void beforeTextChanged(
                                CharSequence charSequence, int i5, int i6, int i7) {}
                    });
            Drawable drawable =
                    resources2.getDrawable(R.drawable.font_preview_send_button_icon, null);
            drawable.setTint(
                    resources2.getColor(R.color.font_preview_send_button_tint_color, null));
            final ImageButton imageButton =
                    (ImageButton) inflate.findViewById(R.id.font_preview_send_button);
            imageButton.setImageDrawable(drawable);
            imageButton.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.display.SecFontTrialSettings$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SecFontTrialSettings secFontTrialSettings2 = SecFontTrialSettings.this;
                            EditText editText2 = editText;
                            ListView listView2 = listView;
                            ImageButton imageButton2 = imageButton;
                            Resources resources2 = resources2;
                            TextView textView2 = textView2;
                            secFontTrialSettings2.getClass();
                            String editable = editText2.getText().toString();
                            if (editable.length() > 0) {
                                secFontTrialSettings2.mBubbleListViewAdapter.addItem(
                                        editable, false);
                                secFontTrialSettings2.mBubbleListViewAdapter.notifyDataSetChanged();
                                editText2.setText(ApnSettings.MVNO_NONE);
                                listView2.smoothScrollToPosition(
                                        secFontTrialSettings2.mBubbleListViewAdapter
                                                .mFontPreviewBubbleList.size());
                            }
                            if (secFontTrialSettings2.mBubbleListViewAdapter.mFontPreviewBubbleList
                                                    .size()
                                            - 3
                                    >= 20) {
                                imageButton2.setEnabled(false);
                                editText2.setEnabled(false);
                                Toast.makeText(
                                                secFontTrialSettings2.mContext,
                                                resources2.getString(
                                                        R.string
                                                                .font_preview_max_bubble_toast_message),
                                                0)
                                        .show();
                            }
                            if (textView2.getVisibility() == 0) {
                                textView2.setVisibility(8);
                            }
                        }
                    });
            View findViewById = inflate.findViewById(R.id.ScreenPreview);
            findViewById.semSetRoundedCorners(15);
            findViewById.semSetRoundedCornerColor(
                    15, resources2.getColor(R.color.sec_widget_round_and_bgcolor));
            return inflate;
        }
        try {
            WindowManagerGlobal.getWindowManagerService()
                    .getInitialDisplaySize(0, this.mDisplaySize);
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            this.mConvertRatio =
                    new float[] {0.5f, 0.75f, 1.0f}
                            [SecDisplayUtils.getScreenResolution(this.mContext)];
            int currentDensity = SecDisplayUtils.getCurrentDensity(this.mContext);
            Context context2 = getContext();
            Configuration configuration = context2.getResources().getConfiguration();
            configuration.getLayoutDirection();
            int length = this.mValues.length;
            Configuration[] configurationArr = new Configuration[length];
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("generateContent: mValues.length="),
                    this.mValues.length,
                    "SecScreenZoomPreferenceFragment");
            for (int i5 = 0; i5 < this.mValues.length; i5++) {
                Configuration configuration2 = new Configuration(configuration);
                if (!Rune.isSamsungDexMode(this.mContext)
                        && !Utils.isDesktopStandaloneMode(this.mContext)) {
                    configuration2.densityDpi = this.mValues[i5];
                }
                configuration2.fontScale = 1.0f;
                configurationArr[i5] = configuration2;
                if (currentDensity == this.mValues[i5]) {
                    this.mInitialIndex = i5;
                }
            }
            int[] iArr = {R.layout.sec_screen_zoom_preview_1};
            this.mPreviewPager = (ViewPager) inflate.findViewById(R.id.preview_pager);
            final SecPreviewPagerAdapter secPreviewPagerAdapter = new SecPreviewPagerAdapter();
            FrameLayout[] frameLayoutArr = {r10};
            secPreviewPagerAdapter.mPreviewFrames = frameLayoutArr;
            secPreviewPagerAdapter.mViewStubInflated =
                    (boolean[][]) Array.newInstance((Class<?>) Boolean.TYPE, 1, length);
            secPreviewPagerAdapter.mContext = context2;
            FrameLayout frameLayout = new FrameLayout(context2);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            frameLayoutArr[0].setClipToPadding(true);
            frameLayoutArr[0].setClipChildren(true);
            for (final int i6 = 0; i6 < length; i6++) {
                Context createConfigurationContext =
                        context2.createConfigurationContext(configurationArr[i6]);
                createConfigurationContext.getTheme().setTo(context2.getTheme());
                ViewStub viewStub = new ViewStub(createConfigurationContext);
                viewStub.setLayoutResource(iArr[0]);
                viewStub.setOnInflateListener(
                        new ViewStub
                                .OnInflateListener() { // from class:
                                                       // com.samsung.android.settings.display.SecPreviewPagerAdapter$$ExternalSyntheticLambda0
                            @Override // android.view.ViewStub.OnInflateListener
                            public final void onInflate(ViewStub viewStub2, View view) {
                                SecPreviewPagerAdapter secPreviewPagerAdapter2 =
                                        SecPreviewPagerAdapter.this;
                                int i7 = i3;
                                int i8 = i6;
                                secPreviewPagerAdapter2.getClass();
                                view.setVisibility(viewStub2.getVisibility());
                                secPreviewPagerAdapter2.mViewStubInflated[i7][i8] = true;
                            }
                        });
                secPreviewPagerAdapter.mPreviewFrames[0].addView(viewStub);
            }
            this.mPreviewPagerAdapter = secPreviewPagerAdapter;
            this.mPreviewPager.setAdapter(secPreviewPagerAdapter);
            this.mPreviewPager.setCurrentItem(0);
            Context context3 = this.mContext;
            Point point = new Point();
            String string =
                    Settings.Global.getString(context3.getContentResolver(), "display_size_forced");
            if (string == null || ApnSettings.MVNO_NONE.equals(string)) {
                try {
                    WindowManagerGlobal.getWindowManagerService().getInitialDisplaySize(0, point);
                    Log.secD("SecDisplayUtils", "getScreenSizeinformation() size : " + point);
                    i2 = point.x;
                } catch (RemoteException unused) {
                    Log.secD("SecDisplayUtils", "getInitialDisplaySize() exception!!!");
                    i = -1;
                }
            } else {
                String[] split = string.split(",");
                i2 = (split == null || split.length <= 1) ? 1440 : Integer.parseInt(split[0]);
            }
            i = i2 >= 1440 ? 2 : (i2 <= 720 || i2 > 1080) ? 0 : 1;
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "getCurrentResolution: width = ",
                    "currentResolution = ",
                    i2,
                    i,
                    "SecDisplayUtils");
            if (i == -1) {
                inflate.findViewById(R.id.screenSizeLayout).setVisibility(8);
                secLabeledSeekBar = null;
            } else {
                secLabeledSeekBar = (SecLabeledSeekBar) inflate.findViewById(R.id.seek_bar);
            }
            this.mSeekBar = secLabeledSeekBar;
            if (secLabeledSeekBar != null) {
                int[] iArr2 = this.mValues;
                secLabeledSeekBar.mLabels = new String[iArr2.length];
                for (int i7 = 0; i7 < iArr2.length; i7++) {
                    secLabeledSeekBar.mLabels[i7] = Integer.toString(iArr2[i7]);
                }
                this.mSeekBar.setMax(this.mValues.length - 1);
                this.mSeekBar.setProgress(this.mInitialIndex);
                if (this.mValues.length == 1
                        || Settings.System.getInt(getContentResolver(), "easy_mode_switch", 1) == 0
                        || !mIsListenerRegistered) {
                    this.mSeekBar.setEnabled(false);
                }
                if (this.mIsMultiWindowMode) {
                    this.mIsBottomButtonVisible =
                            ((float) this.mDisplayMetrics.widthPixels)
                                    < (this.mOrientation == 1
                                            ? this.mDisplayMetrics.density * 265.0f
                                            : ((float)
                                                            this.mResources.getDimensionPixelSize(
                                                                    R.dimen
                                                                            .sec_widget_inset_category_height))
                                                    + (this.mDisplayMetrics.density * 530.0f));
                } else {
                    this.mIsBottomButtonVisible = false;
                }
                if (this.mIsBottomButtonVisible) {
                    inflate.findViewById(R.id.smaller).setVisibility(8);
                    inflate.findViewById(R.id.larger).setVisibility(8);
                    this.mSmaller = inflate.findViewById(R.id.smaller_bottom);
                    this.mLarger = inflate.findViewById(R.id.larger_bottom);
                    View view = this.mSmaller;
                    if (view != null) {
                        view.setVisibility(0);
                    }
                    View view2 = this.mLarger;
                    if (view2 != null) {
                        view2.setVisibility(0);
                    }
                } else {
                    this.mSmaller = inflate.findViewById(R.id.smaller);
                    this.mLarger = inflate.findViewById(R.id.larger);
                }
                View view3 = this.mSmaller;
                if (view3 != null && this.mLarger != null) {
                    view3.setOnClickListener(
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.display.SecScreenZoomPreferenceFragment$$ExternalSyntheticLambda0
                                public final /* synthetic */ SecScreenZoomPreferenceFragment f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view4) {
                                    int i8 = i3;
                                    SecScreenZoomPreferenceFragment
                                            secScreenZoomPreferenceFragment = this.f$0;
                                    switch (i8) {
                                        case 0:
                                            if (!SecScreenZoomPreferenceFragment
                                                    .mIsListenerRegistered) {
                                                secScreenZoomPreferenceFragment.getClass();
                                                break;
                                            } else {
                                                int progress =
                                                        secScreenZoomPreferenceFragment.mSeekBar
                                                                .getProgress();
                                                if (progress > 0) {
                                                    int i9 = progress - 1;
                                                    TooltipPopup$$ExternalSyntheticOutline0.m(
                                                            new StringBuilder(
                                                                    "minus click: progress="),
                                                            i9,
                                                            "ScreenZoomEvent");
                                                    secScreenZoomPreferenceFragment.mSeekBar
                                                            .setProgress(i9, true);
                                                    break;
                                                }
                                            }
                                            break;
                                        default:
                                            if (!SecScreenZoomPreferenceFragment
                                                    .mIsListenerRegistered) {
                                                secScreenZoomPreferenceFragment.getClass();
                                                break;
                                            } else {
                                                int progress2 =
                                                        secScreenZoomPreferenceFragment.mSeekBar
                                                                .getProgress();
                                                if (progress2
                                                        < secScreenZoomPreferenceFragment.mSeekBar
                                                                .getMax()) {
                                                    int i10 = progress2 + 1;
                                                    TooltipPopup$$ExternalSyntheticOutline0.m(
                                                            new StringBuilder(
                                                                    "plus click: progress="),
                                                            i10,
                                                            "ScreenZoomEvent");
                                                    secScreenZoomPreferenceFragment.mSeekBar
                                                            .setProgress(i10, true);
                                                    break;
                                                }
                                            }
                                            break;
                                    }
                                }
                            });
                    this.mLarger.setOnClickListener(
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.display.SecScreenZoomPreferenceFragment$$ExternalSyntheticLambda0
                                public final /* synthetic */ SecScreenZoomPreferenceFragment f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view4) {
                                    int i8 = r2;
                                    SecScreenZoomPreferenceFragment
                                            secScreenZoomPreferenceFragment = this.f$0;
                                    switch (i8) {
                                        case 0:
                                            if (!SecScreenZoomPreferenceFragment
                                                    .mIsListenerRegistered) {
                                                secScreenZoomPreferenceFragment.getClass();
                                                break;
                                            } else {
                                                int progress =
                                                        secScreenZoomPreferenceFragment.mSeekBar
                                                                .getProgress();
                                                if (progress > 0) {
                                                    int i9 = progress - 1;
                                                    TooltipPopup$$ExternalSyntheticOutline0.m(
                                                            new StringBuilder(
                                                                    "minus click: progress="),
                                                            i9,
                                                            "ScreenZoomEvent");
                                                    secScreenZoomPreferenceFragment.mSeekBar
                                                            .setProgress(i9, true);
                                                    break;
                                                }
                                            }
                                            break;
                                        default:
                                            if (!SecScreenZoomPreferenceFragment
                                                    .mIsListenerRegistered) {
                                                secScreenZoomPreferenceFragment.getClass();
                                                break;
                                            } else {
                                                int progress2 =
                                                        secScreenZoomPreferenceFragment.mSeekBar
                                                                .getProgress();
                                                if (progress2
                                                        < secScreenZoomPreferenceFragment.mSeekBar
                                                                .getMax()) {
                                                    int i10 = progress2 + 1;
                                                    TooltipPopup$$ExternalSyntheticOutline0.m(
                                                            new StringBuilder(
                                                                    "plus click: progress="),
                                                            i10,
                                                            "ScreenZoomEvent");
                                                    secScreenZoomPreferenceFragment.mSeekBar
                                                            .setProgress(i10, true);
                                                    break;
                                                }
                                            }
                                            break;
                                    }
                                }
                            });
                }
            }
            TextView textView2 = (TextView) inflate.findViewById(R.id.fontSizeStyleLink);
            if (textView2 != null) {
                String string2 =
                        this.mContext.getString(
                                R.string.sec_screen_zoom_top_font_description,
                                DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
                                DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
                int indexOf = TextUtils.indexOf(string2, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
                int indexOf2 =
                        TextUtils.indexOf(string2, DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
                if (indexOf != -1 && indexOf2 != -1) {
                    int i8 = indexOf2 + 2;
                    if (indexOf <= i8) {
                        i8 = indexOf;
                        indexOf = i8;
                    }
                    String replaceAll =
                            string2.replaceAll(
                                            DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
                                            ApnSettings.MVNO_NONE)
                                    .replaceAll(
                                            DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF,
                                            ApnSettings.MVNO_NONE);
                    int i9 = indexOf - 4;
                    if (i9 > replaceAll.length()) {
                        textView2.setText(replaceAll);
                    } else {
                        ClickableSpan clickableSpan =
                                new ClickableSpan() { // from class:
                                                      // com.samsung.android.settings.display.SecScreenZoomPreferenceFragment.1
                                    @Override // android.text.style.ClickableSpan
                                    public final void onClick(View view4) {
                                        view4.playSoundEffect(0);
                                        SubSettingLauncher subSettingLauncher =
                                                new SubSettingLauncher(
                                                        SecScreenZoomPreferenceFragment.this
                                                                .mContext);
                                        String name = SecFontSizePreferenceFragment.class.getName();
                                        SubSettingLauncher.LaunchRequest launchRequest =
                                                subSettingLauncher.mLaunchRequest;
                                        launchRequest.mDestinationName = name;
                                        launchRequest.mSourceMetricsCategory = 4202;
                                        subSettingLauncher.launch();
                                    }
                                };
                        SpannableStringBuilder spannableStringBuilder =
                                new SpannableStringBuilder(replaceAll);
                        spannableStringBuilder.setSpan(clickableSpan, i8, i9, 0);
                        spannableStringBuilder.setSpan(
                                new ForegroundColorSpan(
                                        this.mContext.getColor(
                                                R.color.sec_tips_description_link_text_color)),
                                i8,
                                i9,
                                0);
                        textView2.setMovementMethod(LinkMovementMethod.getInstance());
                        textView2.setText(spannableStringBuilder);
                    }
                }
            }
            View findViewById2 = inflate.findViewById(R.id.ScreenPreview);
            findViewById2.semSetRoundedCorners(15);
            findViewById2.semSetRoundedCornerColor(
                    15, this.mResources.getColor(R.color.sec_widget_round_and_bgcolor));
            int dimensionPixelSize =
                    this.mResources.getDimensionPixelSize(
                            R.dimen.sec_preference_horizontal_padding);
            findViewById2.semSetRoundedCorners(
                    15,
                    new Pair(
                            Integer.valueOf(dimensionPixelSize),
                            Integer.valueOf(dimensionPixelSize)));
            setPreviewLayer(this.mInitialIndex);
            r5 = this.mOrientation != 1 ? 0 : 1;
            int dimension =
                    Rune.supportNavigationBar()
                            ? (int)
                                    this.mResources.getDimension(
                                            android.R.dimen.resolver_max_collapsed_height)
                            : 0;
            int dimension2 = (int) this.mResources.getDimension(17106286);
            float f = this.mDisplaySize.x;
            float f2 = this.mConvertRatio;
            float max = Math.max(f * f2, r6.y * f2);
            float f3 =
                    (r5 != 0 || this.mIsTabletDevice)
                            ? this.mDisplayMetrics.heightPixels + dimension + dimension2
                            : this.mDisplayMetrics.heightPixels;
            if (max >= f3) {
                max = f3;
            }
            float f4 = max / 100.0f;
            if (this.mIsTabletDevice) {
                if (this.mIsMultiPaneMode) {
                    i3 =
                            this.mResources.getInteger(
                                    R.dimen.sec_screen_zoom_fragment_preview_pager_height_percent);
                } else if (r5 != 0) {
                    i3 =
                            this.mResources.getInteger(
                                    R.dimen
                                            .sec_screen_zoom_fragment_preview_pager_height_percent_tablet);
                }
            } else if (r5 != 0) {
                i3 =
                        this.mResources.getInteger(
                                R.dimen.sec_screen_zoom_fragment_preview_pager_height_percent);
            }
            if (i3 > 0) {
                View findViewById3 = inflate.findViewById(R.id.preview_pager_layout);
                ViewGroup.LayoutParams layoutParams = findViewById3.getLayoutParams();
                layoutParams.height = (int) (f4 * i3);
                findViewById3.setLayoutParams(layoutParams);
            }
            return inflate;
        } catch (RemoteException e) {
            Log.d(
                    "SecScreenZoomPreferenceFragment",
                    "createScreenZoomView: RemoteException e=" + e);
            return inflate;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7448;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return this.mIsFromAccessibility ? "top_level_accessibility" : "top_level_display";
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        generateContent(
                LayoutInflater.from(this.mContext),
                (ViewGroup) getActivity().findViewById(R.id.main_content),
                null);
        this.mContent.postDelayed(
                new SecScreenZoomPreferenceFragment$$ExternalSyntheticLambda2(0, this), 800L);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mIsFromAccessibility = arguments.getBoolean("FROM_ACCESSIBILITY");
        }
        Context context = getContext();
        this.mContext = context;
        this.mResources = context.getResources();
        Context context2 = this.mContext;
        Intent intent = getIntent();
        SecFontTrialSettings secFontTrialSettings =
                intent.getBooleanExtra("fromStore", false)
                        ? new SecFontTrialSettings(context2, intent)
                        : null;
        this.mFontTrial = secFontTrialSettings;
        if (secFontTrialSettings != null) {
            Context context3 = secFontTrialSettings.mContext;
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            PackageManager packageManager = context3.getPackageManager();
            if (packageManager != null
                    && packageManager.hasSystemFeature(
                            "com.samsung.feature.samsung_experience_mobile")
                    && (i = Build.VERSION.SEM_PLATFORM_INT) < 100500) {
                Log.d(
                        "SecFontTrialSettings",
                        "invalid: cause: Build.VERSION.SEM_PLATFORM_INT=" + i);
                finish();
                return;
            }
        }
        Signature[] signatureArr2 = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        this.mIsTabletDevice = Utils.isTablet();
        this.mValues = SecDisplayUtils.getProperDensities(this.mContext, DENSITY_BASE_PIXEL);
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("onCreate: Density length="),
                this.mValues.length,
                "SecScreenZoomPreferenceFragment");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getActivity()
                .setTitle(
                        getString(
                                this.mFontTrial != null
                                        ? R.string.font_preview_try_it_out_title
                                        : R.string.sec_screen_zoom_title));
        return generateContent(layoutInflater, null, bundle);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        if (this.mFontTrial != null) {
            File file = SecDisplayUtils.mPreviewFontFile;
            if (file != null) {
                file.delete();
            }
            this.mFontTrial = null;
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.main_content);
        if (viewGroup != null) {
            viewGroup.removeView(getView());
            View findViewById = viewGroup.findViewById(R.id.ScreenZoomLayout);
            if (findViewById == null) {
                Log.d("SecScreenZoomPreferenceFragment", "onDestroyView: layout has been removed.");
                return;
            }
            Log.d("SecScreenZoomPreferenceFragment", "onDestroyView: layout=" + findViewById);
            viewGroup.removeView(findViewById);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.i("SecScreenZoomPreferenceFragment", "onResume");
        if (this.mFontTrial == null) {
            if (Settings.System.getInt(getContentResolver(), "easy_mode_switch", 1) != 0
                    && !Rune.isSamsungDexMode(this.mContext)
                    && !Utils.isDesktopStandaloneMode(this.mContext)) {
                Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            } else {
                if (Utils.isNewDexMode(this.mContext)) {
                    return;
                }
                finish();
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        SecFontTrialSettings secFontTrialSettings = this.mFontTrial;
        if (secFontTrialSettings == null) {
            bundle.putLong("mLastCommitTime", this.mLastCommitTime);
            return;
        }
        int size = secFontTrialSettings.mBubbleListViewAdapter.mFontPreviewBubbleList.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] =
                    ((FontPreviewBubbleListItem)
                                    secFontTrialSettings.mBubbleListViewAdapter
                                            .mFontPreviewBubbleList.get(i))
                            .mMessageText;
        }
        bundle.putStringArray("PreviewFontMessageList", strArr);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        registerControlListeners();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        mIsListenerRegistered = false;
        SecLabeledSeekBar secLabeledSeekBar = this.mSeekBar;
        if (secLabeledSeekBar != null) {
            secLabeledSeekBar.setOnSeekBarChangeListener(null);
            this.mSeekBar.setOnKeyListener(null);
        }
    }

    public final void registerControlListeners() {
        SecLabeledSeekBar secLabeledSeekBar = this.mSeekBar;
        if (secLabeledSeekBar != null) {
            secLabeledSeekBar.setEnabled(true);
            this.mSeekBar.setProgress(this.mCurrentIndex);
            this.mSeekBar.setOnSeekBarChangeListener(new onPreviewSeekBarChangeListener());
            this.mSeekBar.setOnKeyListener(new onPreviewKeyListener());
        }
        mIsListenerRegistered = true;
    }

    public final void setPreviewLayer(int i) {
        View view = this.mSmaller;
        if (view != null && this.mLarger != null) {
            view.setEnabled(i > 0);
            this.mLarger.setEnabled(i < this.mValues.length - 1);
        }
        SecPreviewPagerAdapter secPreviewPagerAdapter = this.mPreviewPagerAdapter;
        if (secPreviewPagerAdapter != null) {
            int i2 = this.mCurrentIndex;
            int currentItem = this.mPreviewPager.getCurrentItem();
            for (FrameLayout frameLayout : secPreviewPagerAdapter.mPreviewFrames) {
                boolean[][] zArr = secPreviewPagerAdapter.mViewStubInflated;
                if (i2 >= 0) {
                    View childAt = frameLayout.getChildAt(i2);
                    if (zArr[currentItem][i2]) {
                        if (frameLayout == secPreviewPagerAdapter.mPreviewFrames[currentItem]) {
                            secPreviewPagerAdapter.setVisibility$1(childAt, 4);
                        } else {
                            secPreviewPagerAdapter.setVisibility$1(childAt, 4);
                        }
                    }
                }
                View childAt2 = frameLayout.getChildAt(i);
                if (frameLayout == secPreviewPagerAdapter.mPreviewFrames[currentItem]) {
                    if (!zArr[currentItem][i]) {
                        childAt2 = ((ViewStub) childAt2).inflate();
                        childAt2.setAlpha(0.0f);
                    }
                    secPreviewPagerAdapter.setVisibility$1(childAt2, 0);
                } else {
                    secPreviewPagerAdapter.setVisibility$1(childAt2, 0);
                }
            }
        }
        this.mCurrentIndex = i;
        TooltipPopup$$ExternalSyntheticOutline0.m(
                new StringBuilder("setPreviewLayer: mCurrentIndex="),
                this.mCurrentIndex,
                "ScreenZoomEvent");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {}
}
