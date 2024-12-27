package com.samsung.android.settings.display;

import android.app.ActivityClient;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.secutil.Log;
import android.view.Choreographer;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.DisplaySettings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.widget.LayoutPreference;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.Booster;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecFontSizePreferenceFragment extends SettingsPreferenceFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public static SecSwitchPreference mBoldFontPreference;
    public static SecInsetCategoryPreference mFontSizePreviewBottomPaddingPreference;
    public static LayoutPreference mFontSizePreviewPreference;
    public static SecPreferenceScreen mFontStylePreference;
    public static SecInsetCategoryPreference mSecPreferenceDummyItem;
    public FragmentActivity mActivity;
    public Booster mBooster;
    public Context mContext;
    public DisplayMetrics mDisplayMetrics;
    public View mFontSizeLayout;
    public SeslSeekBar mFontSizeSeekBar;
    public LinearLayout mFontSizeSeekBarContainer;
    public FontSizeSupportPreferenceFragment mFontSizeSupportPreferenceFragment;
    public boolean mIsFromStore;
    public ImageButton mLargerButton;
    public final AnonymousClass3 mLargerButtonClickListener;
    public TextView mPreviewFontLowercaseAlphabetTextView;
    public TextView mPreviewFontNameTextView;
    public TextView mPreviewFontNumberTextView;
    public TextView mPreviewFontSampleTextView;
    public TextView mPreviewFontSymbolTextView;
    public TextView mPreviewFontUppercaseAlphabetTextView;
    public Resources mResources;
    public ImageButton mSmallerButton;
    public final AnonymousClass3 mSmallerButtonClickListener;
    public LinearLayout mTextPreviewLayout;
    public TextView mTextPreviewSampleTextView;
    public TextView mTextPreviewSymbolTextView;
    public final float[] previewTextSizes = {
        15.3f, 17.0f, 19.0f, 20.6f, 22.6f, 25.0f, 27.6f, 32.0f
    };
    public final float[] sampleTextSizes = {12.6f, 14.3f, 16.0f, 18.0f, 20.3f, 23.0f, 25.0f, 28.0f};
    public boolean mIsInSeekBarSetupProcess = false;
    public Uri mPreviewFontURI = null;
    public String mPreviewFontName = "_Font Name_";
    public Typeface mPreviewTypeface = null;
    public int mPreviewFontIsBold = -1;
    public int mPreviewFontSize = -1;
    public final AnonymousClass5 mApplyWindowInsetsListener =
            new View
                    .OnApplyWindowInsetsListener() { // from class:
                                                     // com.samsung.android.settings.display.SecFontSizePreferenceFragment.5
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(
                        View view, WindowInsets windowInsets) {
                    AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar);
                    View findViewById = view.findViewById(R.id.dummy_navigation_bar_right);
                    View findViewById2 = view.findViewById(R.id.dummy_navigation_bar_left);
                    Insets insets =
                            windowInsets.getInsets(
                                    WindowInsets.Type.systemBars()
                                            | WindowInsets.Type.displayCutout());
                    if (view.findViewById(R.id.sec_font_size_layout) != null) {
                        Context context = SecFontSizePreferenceFragment.this.mContext;
                        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
                        Configuration taskConfiguration =
                                ActivityClient.getInstance()
                                        .getTaskConfiguration(context.getActivityToken());
                        if (taskConfiguration == null
                                || !WindowConfiguration.inMultiWindowMode(
                                        taskConfiguration.windowConfiguration.getWindowingMode())) {
                            int rotation =
                                    SecFontSizePreferenceFragment.this
                                            .mContext
                                            .getDisplay()
                                            .getRotation();
                            int integer =
                                    SecFontSizePreferenceFragment.this
                                            .mContext
                                            .getResources()
                                            .getInteger(
                                                    android.R.integer.config_pinnerWebviewPinBytes);
                            int dimension =
                                    (int)
                                            SecFontSizePreferenceFragment.this.mResources
                                                    .getDimension(
                                                            android.R.dimen
                                                                    .resolver_max_collapsed_height);
                            boolean isRTL =
                                    Utils.isRTL(SecFontSizePreferenceFragment.this.mContext);
                            if (rotation == 1 && (integer == 0 || integer == 3)) {
                                if (isRTL) {
                                    ViewGroup.LayoutParams layoutParams =
                                            findViewById2.getLayoutParams();
                                    layoutParams.width = dimension;
                                    findViewById2.setLayoutParams(layoutParams);
                                    findViewById2.setVisibility(0);
                                    findViewById.setVisibility(8);
                                    appBarLayout.setPadding(0, 0, dimension, 0);
                                } else {
                                    ViewGroup.LayoutParams layoutParams2 =
                                            findViewById.getLayoutParams();
                                    layoutParams2.width = dimension;
                                    findViewById.setLayoutParams(layoutParams2);
                                    findViewById.setVisibility(0);
                                    findViewById2.setVisibility(8);
                                    appBarLayout.setPadding(0, 0, 0, 0);
                                }
                                view.setPadding(insets.left, insets.top, 0, insets.bottom);
                                return WindowInsets.CONSUMED;
                            }
                            if (rotation != 3 || (integer != 0 && integer != 3)) {
                                findViewById.setVisibility(8);
                                findViewById2.setVisibility(8);
                                appBarLayout.setPadding(0, 0, 0, 0);
                                view.setPadding(
                                        insets.left, insets.top, insets.right, insets.bottom);
                                return WindowInsets.CONSUMED;
                            }
                            if (isRTL) {
                                ViewGroup.LayoutParams layoutParams3 =
                                        findViewById.getLayoutParams();
                                layoutParams3.width = dimension;
                                findViewById.setLayoutParams(layoutParams3);
                                findViewById.setVisibility(0);
                                findViewById2.setVisibility(8);
                                appBarLayout.setPadding(0, 0, 0, 0);
                            } else {
                                ViewGroup.LayoutParams layoutParams4 =
                                        findViewById2.getLayoutParams();
                                layoutParams4.width = dimension;
                                findViewById2.setLayoutParams(layoutParams4);
                                findViewById2.setVisibility(0);
                                findViewById.setVisibility(8);
                                appBarLayout.setPadding(dimension, 0, 0, 0);
                            }
                            view.setPadding(0, insets.top, insets.right, insets.bottom);
                            return WindowInsets.CONSUMED;
                        }
                    }
                    findViewById.setVisibility(8);
                    findViewById2.setVisibility(8);
                    appBarLayout.setPadding(0, 0, 0, 0);
                    view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
                    return WindowInsets.CONSUMED;
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.SecFontSizePreferenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            if (UserHandle.myUserId() == 0) {
                return super.getNonIndexableKeys(context);
            }
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("bold_text");
            arrayList.add("sec_font_style");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            if (UserHandle.myUserId() == 0) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = "sec_font_size_controller";
                searchIndexableRaw.title = String.valueOf(R.string.sec_font_size_title);
                searchIndexableRaw.screenTitle =
                        context.getResources().getString(R.string.sec_font_size_and_style_title);
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = SecFontSizePreferenceFragment.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_font_size;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class FontSizeSupportPreferenceFragment extends PreferenceFragmentCompat {
        @Override // androidx.preference.PreferenceFragmentCompat
        public final void onCreatePreferences(Bundle bundle, String str) {
            setPreferencesFromResource(R.xml.sec_font_size, str);
            SecFontSizePreferenceFragment.mFontSizePreviewPreference =
                    (LayoutPreference) findPreference("sec_font_size_preview");
            SecFontSizePreferenceFragment.mFontSizePreviewBottomPaddingPreference =
                    (SecInsetCategoryPreference)
                            findPreference("sec_font_size_preview_bottom_padding");
            SecFontSizePreferenceFragment.mFontStylePreference =
                    (SecPreferenceScreen) findPreference("sec_font_style");
            SecFontSizePreferenceFragment.mBoldFontPreference =
                    (SecSwitchPreference) findPreference("bold_text");
            SecFontSizePreferenceFragment.mSecPreferenceDummyItem =
                    (SecInsetCategoryPreference) findPreference("sec_font_size_layout_dummy_item");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OnFontSizeSeekBarChangeListener
            implements SeslSeekBar.OnSeekBarChangeListener {
        public final
        SecFontSizePreferenceFragment$OnFontSizeSeekBarChangeListener$$ExternalSyntheticLambda0
                mCommit =
                        new Choreographer
                                .FrameCallback() { // from class:
                                                   // com.samsung.android.settings.display.SecFontSizePreferenceFragment$OnFontSizeSeekBarChangeListener$$ExternalSyntheticLambda0
                            @Override // android.view.Choreographer.FrameCallback
                            public final void doFrame(long j) {
                                SecFontSizePreferenceFragment.OnFontSizeSeekBarChangeListener
                                        onFontSizeSeekBarChangeListener =
                                                SecFontSizePreferenceFragment
                                                        .OnFontSizeSeekBarChangeListener.this;
                                onFontSizeSeekBarChangeListener.getClass();
                                onFontSizeSeekBarChangeListener.mLastCommitTime =
                                        SystemClock.elapsedRealtime();
                                int i = onFontSizeSeekBarChangeListener.mCurrentFontIndex;
                                LayoutPreference layoutPreference =
                                        SecFontSizePreferenceFragment.mFontSizePreviewPreference;
                                SecFontSizePreferenceFragment secFontSizePreferenceFragment =
                                        SecFontSizePreferenceFragment.this;
                                secFontSizePreferenceFragment.getClass();
                                LoggingHelper.insertEventLogging(4202, 4204, i + 1);
                                Booster booster = secFontSizePreferenceFragment.mBooster;
                                booster.getClass();
                                long uptimeMillis = SystemClock.uptimeMillis();
                                long j2 = uptimeMillis - booster.mLastBoosterTime;
                                if (j2 <= 0 || j2 >= booster.mLastBoosterDuration) {
                                    booster.mLastBoosterTime = uptimeMillis;
                                    booster.mLastBoosterDuration = 3000;
                                    if (booster.mCpuMinFreq > 0) {
                                        booster.mCPUMinBooster.acquire(3000);
                                    }
                                    if (booster.mGpuMinFreq > 0) {
                                        booster.mGPUMinBooster.acquire(3000);
                                    }
                                }
                                Settings.Global.putInt(
                                        secFontSizePreferenceFragment.mContext.getContentResolver(),
                                        "font_size",
                                        i);
                                secFontSizePreferenceFragment.mContext.sendBroadcast(
                                        SecDisplayUtils.getFontSizeChangedIntent());
                                Log.secD(
                                        "SecFontSizePreferenceFragment",
                                        "com.samsung.settings.FONT_SIZE_CHANGED broadcast.");
                                Context context = secFontSizePreferenceFragment.mContext;
                                SecDisplayUtils.writeFontScaleDBAllUser(
                                        context, SecDisplayUtils.getFontScale(context, i));
                            }
                        };
        public long mCommitDelayMs;
        public int mCurrentFontIndex;
        public long mLastCommitTime;
        public boolean mSeekByTouch;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.settings.display.SecFontSizePreferenceFragment$OnFontSizeSeekBarChangeListener$$ExternalSyntheticLambda0] */
        public OnFontSizeSeekBarChangeListener() {}

        public final void commitOnNextFrame$1() {
            if (SystemClock.elapsedRealtime() - this.mLastCommitTime < 800) {
                this.mCommitDelayMs += 800;
            }
            Choreographer choreographer = Choreographer.getInstance();
            SecFontSizePreferenceFragment$OnFontSizeSeekBarChangeListener$$ExternalSyntheticLambda0
                    secFontSizePreferenceFragment$OnFontSizeSeekBarChangeListener$$ExternalSyntheticLambda0 =
                            this.mCommit;
            choreographer.removeFrameCallback(
                    secFontSizePreferenceFragment$OnFontSizeSeekBarChangeListener$$ExternalSyntheticLambda0);
            choreographer.postFrameCallbackDelayed(
                    secFontSizePreferenceFragment$OnFontSizeSeekBarChangeListener$$ExternalSyntheticLambda0,
                    this.mCommitDelayMs);
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
            SecFontSizePreferenceFragment secFontSizePreferenceFragment =
                    SecFontSizePreferenceFragment.this;
            if (!Utils.isTalkBackEnabled(secFontSizePreferenceFragment.mContext)) {
                secFontSizePreferenceFragment.mFontSizeSeekBar.setContentDescription(
                        Integer.toString(i));
            }
            this.mCurrentFontIndex = i;
            if (z) {
                seslSeekBar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
            }
            if (secFontSizePreferenceFragment.mIsFromStore) {
                secFontSizePreferenceFragment.mPreviewFontSize = this.mCurrentFontIndex;
            }
            secFontSizePreferenceFragment.changeFontSizeOnPreview(this.mCurrentFontIndex);
            if (this.mSeekByTouch
                    || secFontSizePreferenceFragment.mIsFromStore
                    || secFontSizePreferenceFragment.mIsInSeekBarSetupProcess) {
                this.mCommitDelayMs = 100L;
            } else {
                this.mCommitDelayMs = 300L;
                commitOnNextFrame$1();
            }
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
            this.mSeekByTouch = true;
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
            this.mSeekByTouch = false;
            if (SecFontSizePreferenceFragment.this.mIsFromStore) {
                return;
            }
            commitOnNextFrame$1();
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.display.SecFontSizePreferenceFragment$3] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.display.SecFontSizePreferenceFragment$5] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.samsung.android.settings.display.SecFontSizePreferenceFragment$3] */
    public SecFontSizePreferenceFragment() {
        final int i = 0;
        this.mSmallerButtonClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.SecFontSizePreferenceFragment.3
                    public final /* synthetic */ SecFontSizePreferenceFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                int progress = this.this$0.mFontSizeSeekBar.getProgress();
                                if (progress > this.this$0.mFontSizeSeekBar.getMin()) {
                                    this.this$0.mFontSizeSeekBar.setProgressInternal(
                                            progress - 1, false, false);
                                    break;
                                }
                                break;
                            default:
                                int progress2 = this.this$0.mFontSizeSeekBar.getProgress();
                                if (progress2 < this.this$0.mFontSizeSeekBar.getMax()) {
                                    this.this$0.mFontSizeSeekBar.setProgressInternal(
                                            progress2 + 1, false, false);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mLargerButtonClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.SecFontSizePreferenceFragment.3
                    public final /* synthetic */ SecFontSizePreferenceFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                int progress = this.this$0.mFontSizeSeekBar.getProgress();
                                if (progress > this.this$0.mFontSizeSeekBar.getMin()) {
                                    this.this$0.mFontSizeSeekBar.setProgressInternal(
                                            progress - 1, false, false);
                                    break;
                                }
                                break;
                            default:
                                int progress2 = this.this$0.mFontSizeSeekBar.getProgress();
                                if (progress2 < this.this$0.mFontSizeSeekBar.getMax()) {
                                    this.this$0.mFontSizeSeekBar.setProgressInternal(
                                            progress2 + 1, false, false);
                                    break;
                                }
                                break;
                        }
                    }
                };
    }

    public final void addPreferencesOnResume(Preference... preferenceArr) {
        try {
            for (Preference preference : preferenceArr) {
                this.mFontSizeSupportPreferenceFragment
                        .getPreferenceScreen()
                        .addPreference(preference);
            }
        } catch (IllegalStateException e) {
            Log.e(
                    "SecFontSizePreferenceFragment",
                    "Cannot add Preferences OnResume. " + e.getMessage());
        }
    }

    public final void changeFontSizeOnPreview(int i) {
        if (!this.mIsFromStore) {
            this.mTextPreviewSampleTextView.setTextSize(1, this.previewTextSizes[i]);
            this.mTextPreviewSymbolTextView.setTextSize(1, this.previewTextSizes[i]);
            return;
        }
        this.mPreviewFontNameTextView.setTextSize(1, this.sampleTextSizes[i]);
        this.mPreviewFontSampleTextView.setTextSize(1, this.sampleTextSizes[i]);
        this.mPreviewFontNumberTextView.setTextSize(1, this.sampleTextSizes[i]);
        this.mPreviewFontSymbolTextView.setTextSize(1, this.sampleTextSizes[i]);
        this.mPreviewFontUppercaseAlphabetTextView.setTextSize(1, this.sampleTextSizes[i]);
        this.mPreviewFontLowercaseAlphabetTextView.setTextSize(1, this.sampleTextSizes[i]);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_font_size_and_style_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4202;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDisplayMetrics = this.mResources.getDisplayMetrics();
        setFontSizePreviewLayout();
        setFontSizeSeekBarLayout();
        updateSeekBar$1();
        SecPreferenceScreen secPreferenceScreen = mFontStylePreference;
        if (secPreferenceScreen != null) {
            secPreferenceScreen.setSummary(SecDisplayUtils.getCurrentFontName(this.mContext));
            SecPreferenceScreen secPreferenceScreen2 = mFontStylePreference;
            secPreferenceScreen2.getClass();
            SecPreferenceUtils.applySummaryColor(secPreferenceScreen2, true);
        }
        setBoldFontPreference();
        updateFontLayoutPreferences();
        ((LinearLayout) this.mFontSizeLayout.findViewById(R.id.sec_font_size_layout))
                .setPadding(
                        configuration.orientation == 2
                                ? Utils.getListHorizontalPadding(this.mContext)
                                : 0,
                        0,
                        0,
                        0);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        this.mContext = getContext();
        this.mActivity = getActivity();
        Resources resources = this.mContext.getResources();
        this.mResources = resources;
        this.mDisplayMetrics = resources.getDisplayMetrics();
        this.mBooster = new Booster(this.mActivity);
        this.mActivity.getWindow().setDecorFitsSystemWindows(false);
        boolean booleanExtra = getIntent().getBooleanExtra("fromStore", false);
        this.mIsFromStore = booleanExtra;
        if (booleanExtra) {
            Context context = this.mContext;
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null
                    && packageManager.hasSystemFeature(
                            "com.samsung.feature.samsung_experience_mobile")
                    && (i = Build.VERSION.SEM_PLATFORM_INT) < 100500) {
                Log.d(
                        "SecFontSizePreferenceFragment",
                        "Activity finished, cause : mIsFromStore="
                                + this.mIsFromStore
                                + ", Build.VERSION.SEM_PLATFORM_INT="
                                + i);
                finish();
            }
        }
        if (this.mIsFromStore) {
            this.mActivity.setTitle(R.string.font_preview_see_sample_title);
        } else if (WizardManagerHelper.isUserSetupComplete(this.mContext)) {
            this.mActivity.setTitle(R.string.sec_font_size_and_style_title);
        } else {
            this.mActivity.setTitle(R.string.sec_font_size_title);
        }
        if (this.mIsFromStore) {
            this.mPreviewFontURI = (Uri) getIntent().getParcelableExtra("fontFileUri");
            this.mPreviewFontName = getIntent().getStringExtra("fontName");
            this.mPreviewTypeface =
                    SecDisplayUtils.getPreviewFont(this.mContext, this.mPreviewFontURI);
            if (bundle != null) {
                this.mPreviewFontIsBold = bundle.getInt("PreviewFontIsBold", -1);
                this.mPreviewFontSize = bundle.getInt("PreviewFontSize", -1);
            }
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        if (this.mFontSizeSupportPreferenceFragment == null) {
            this.mFontSizeSupportPreferenceFragment = new FontSizeSupportPreferenceFragment();
        }
        FragmentManager childFragmentManager = getChildFragmentManager();
        BackStackRecord m =
                DialogFragment$$ExternalSyntheticOutline0.m(
                        childFragmentManager, childFragmentManager);
        m.replace(
                R.id.font_size_preference_container, this.mFontSizeSupportPreferenceFragment, null);
        m.commitInternal(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mFontSizeLayout =
                layoutInflater.inflate(R.layout.sec_font_size_layout, (ViewGroup) null);
        ((LinearLayout) this.mFontSizeLayout.findViewById(R.id.sec_font_size_layout))
                .setPadding(
                        this.mResources.getConfiguration().orientation == 2
                                ? Utils.getListHorizontalPadding(this.mContext)
                                : 0,
                        0,
                        0,
                        0);
        this.mActivity
                .findViewById(android.R.id.content)
                .setOnApplyWindowInsetsListener(this.mApplyWindowInsetsListener);
        return this.mFontSizeLayout;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        File file;
        super.onDestroy();
        if (this.mIsFromStore && (file = SecDisplayUtils.mPreviewFontFile) != null) {
            file.delete();
        }
        mFontSizePreviewPreference = null;
        mFontSizePreviewBottomPaddingPreference = null;
        mFontStylePreference = null;
        mBoldFontPreference = null;
        mSecPreferenceDummyItem = null;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        int i = this.mResources.getConfiguration().semDesktopModeEnabled;
        if (!this.mIsFromStore && i == 1 && !Utils.isNewDexMode(this.mContext)) {
            finish();
        }
        setFontSizePreviewLayout();
        setFontSizeSeekBarLayout();
        updateSeekBar$1();
        SecPreferenceScreen secPreferenceScreen = mFontStylePreference;
        if (secPreferenceScreen != null) {
            secPreferenceScreen.setSummary(SecDisplayUtils.getCurrentFontName(this.mContext));
            SecPreferenceScreen secPreferenceScreen2 = mFontStylePreference;
            secPreferenceScreen2.getClass();
            SecPreferenceUtils.applySummaryColor(secPreferenceScreen2, true);
        }
        setBoldFontPreference();
        updateFontLayoutPreferences();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mIsFromStore) {
            bundle.putInt("PreviewFontIsBold", this.mPreviewFontIsBold);
            bundle.putInt("PreviewFontSize", this.mPreviewFontSize);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        View findViewById = this.mActivity.findViewById(R.id.round_corner);
        if (findViewById != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
    }

    public final void setBoldFontForFlipFontsPreview(boolean z) {
        Typeface typeface;
        if (z) {
            typeface =
                    Typeface.create(
                            this.mPreviewTypeface,
                            Math.min(Math.max(this.mPreviewTypeface.getWeight() + 300, 1), 1000),
                            (this.mPreviewTypeface.getStyle() & 2) != 0);
        } else {
            typeface = this.mPreviewTypeface;
        }
        this.mPreviewFontNameTextView.setTypeface(typeface);
        this.mPreviewFontSampleTextView.setTypeface(typeface);
        this.mPreviewFontNumberTextView.setTypeface(typeface);
        this.mPreviewFontSymbolTextView.setTypeface(typeface);
        this.mPreviewFontUppercaseAlphabetTextView.setTypeface(typeface);
        this.mPreviewFontLowercaseAlphabetTextView.setTypeface(typeface);
    }

    public final void setBoldFontPreference() {
        int i;
        if (mBoldFontPreference != null) {
            boolean z = Settings.Global.getInt(getContentResolver(), "bold_text", 0) != 0;
            if (this.mIsFromStore && (i = this.mPreviewFontIsBold) != -1) {
                z = i == 1;
            }
            mBoldFontPreference.setChecked(z);
            if (this.mIsFromStore && this.mPreviewTypeface != null) {
                setBoldFontForFlipFontsPreview(z);
            }
            mBoldFontPreference.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.samsung.android.settings.display.SecFontSizePreferenceFragment.2
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            boolean booleanValue = ((Boolean) obj).booleanValue();
                            SecFontSizePreferenceFragment secFontSizePreferenceFragment =
                                    SecFontSizePreferenceFragment.this;
                            if (secFontSizePreferenceFragment.mIsFromStore
                                    && secFontSizePreferenceFragment.mPreviewTypeface != null) {
                                secFontSizePreferenceFragment.mPreviewFontIsBold =
                                        booleanValue ? 1 : 0;
                                secFontSizePreferenceFragment.setBoldFontForFlipFontsPreview(
                                        booleanValue);
                                return true;
                            }
                            SecDisplayUtils.writeFontWeightDBAllUser(
                                    secFontSizePreferenceFragment.mContext, booleanValue ? 300 : 0);
                            Settings.Global.putInt(
                                    secFontSizePreferenceFragment.getContentResolver(),
                                    "bold_text",
                                    booleanValue ? 1 : 0);
                            LoggingHelper.insertEventLogging(7444, booleanValue ? 1 : 0);
                            return true;
                        }
                    });
        }
    }

    public final void setFontSizePreviewLayout() {
        View view = this.mFontSizeLayout;
        if (view == null) {
            return;
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.landscape_layout);
        LinearLayout linearLayout2 =
                (LinearLayout) this.mFontSizeLayout.findViewById(R.id.portrait_layout);
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 =
                (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        if (shouldBeDisplayedAsPortrait()) {
            layoutParams.weight = 0.0f;
            layoutParams2.weight = 1.0f;
            this.mFontSizeLayout.findViewById(R.id.landscape_layout_separator).setVisibility(8);
            this.mFontSizeLayout.findViewById(R.id.text_preview_layout).setVisibility(8);
            LayoutPreference layoutPreference = mFontSizePreviewPreference;
            if (layoutPreference != null) {
                LinearLayout linearLayout3 =
                        (LinearLayout)
                                layoutPreference.mRootView.findViewById(
                                        R.id.font_size_preview_layout);
                this.mTextPreviewLayout = linearLayout3;
                linearLayout3.setVisibility(0);
            }
            SecInsetCategoryPreference secInsetCategoryPreference =
                    mFontSizePreviewBottomPaddingPreference;
            if (secInsetCategoryPreference != null) {
                secInsetCategoryPreference.setHeight(
                        (int)
                                this.mContext
                                        .getResources()
                                        .getDimension(R.dimen.sec_widget_inset_category_height));
            }
            ViewGroup.LayoutParams layoutParams3 = this.mTextPreviewLayout.getLayoutParams();
            layoutParams3.height =
                    Math.round(
                            this.mDisplayMetrics.heightPixels * (this.mIsFromStore ? 0.6f : 0.4f));
            this.mTextPreviewLayout.setLayoutParams(layoutParams3);
        } else {
            layoutParams.weight = 5.0f;
            layoutParams2.weight = 5.0f;
            this.mFontSizeLayout.findViewById(R.id.landscape_layout_separator).setVisibility(0);
            LinearLayout linearLayout4 =
                    (LinearLayout) this.mFontSizeLayout.findViewById(R.id.text_preview_layout);
            this.mTextPreviewLayout = linearLayout4;
            linearLayout4.setVisibility(0);
            LayoutPreference layoutPreference2 = mFontSizePreviewPreference;
            if (layoutPreference2 != null) {
                layoutPreference2
                        .mRootView
                        .findViewById(R.id.font_size_preview_layout)
                        .setVisibility(8);
            }
            SecInsetCategoryPreference secInsetCategoryPreference2 =
                    mFontSizePreviewBottomPaddingPreference;
            if (secInsetCategoryPreference2 != null) {
                secInsetCategoryPreference2.setHeight(0);
            }
            this.mTextPreviewLayout.semSetRoundedCorners(15);
            this.mTextPreviewLayout.semSetRoundedCornerColor(
                    15, this.mResources.getColor(R.color.sec_widget_round_and_bgcolor));
        }
        linearLayout.setLayoutParams(layoutParams);
        linearLayout2.setLayoutParams(layoutParams2);
        this.mTextPreviewSampleTextView =
                (TextView) this.mTextPreviewLayout.findViewById(R.id.text_preview_sample_text_view);
        this.mTextPreviewSymbolTextView =
                (TextView) this.mTextPreviewLayout.findViewById(R.id.text_preview_symbol_text_view);
        if (this.mIsFromStore) {
            this.mPreviewFontNameTextView =
                    (TextView)
                            this.mTextPreviewLayout.findViewById(
                                    R.id.font_preview_font_name_text_view);
            this.mPreviewFontSampleTextView =
                    (TextView)
                            this.mTextPreviewLayout.findViewById(
                                    R.id.font_preview_sample_text_view);
            this.mPreviewFontNumberTextView =
                    (TextView)
                            this.mTextPreviewLayout.findViewById(
                                    R.id.font_preview_number_text_view);
            this.mPreviewFontSymbolTextView =
                    (TextView)
                            this.mTextPreviewLayout.findViewById(
                                    R.id.font_preview_symbol_text_view);
            this.mPreviewFontUppercaseAlphabetTextView =
                    (TextView)
                            this.mTextPreviewLayout.findViewById(
                                    R.id.font_preview_uppercase_alphabet_text_view);
            this.mPreviewFontLowercaseAlphabetTextView =
                    (TextView)
                            this.mTextPreviewLayout.findViewById(
                                    R.id.font_preview_lowercase_alphabet_text_view);
            this.mTextPreviewSampleTextView.setVisibility(8);
            this.mTextPreviewSymbolTextView.setVisibility(8);
            this.mPreviewFontNameTextView.setText(this.mPreviewFontName);
            this.mPreviewFontNameTextView.setTypeface(this.mPreviewTypeface);
            this.mPreviewFontNameTextView.setVisibility(0);
            this.mPreviewFontSampleTextView.setText(
                    "\n" + getString(R.string.font_preview_sample_text));
            this.mPreviewFontSampleTextView.setTypeface(this.mPreviewTypeface);
            this.mPreviewFontSampleTextView.setVisibility(0);
            this.mPreviewFontNumberTextView.setText(
                    "\n" + getString(R.string.font_preview_numbers));
            this.mPreviewFontNumberTextView.setTypeface(this.mPreviewTypeface);
            this.mPreviewFontNumberTextView.setVisibility(0);
            this.mPreviewFontSymbolTextView.setTypeface(this.mPreviewTypeface);
            this.mPreviewFontSymbolTextView.setVisibility(0);
            this.mPreviewFontUppercaseAlphabetTextView.setTypeface(this.mPreviewTypeface);
            this.mPreviewFontUppercaseAlphabetTextView.setVisibility(0);
            this.mPreviewFontLowercaseAlphabetTextView.setTypeface(this.mPreviewTypeface);
            this.mPreviewFontLowercaseAlphabetTextView.setVisibility(0);
        }
    }

    public final void setFontSizeSeekBarLayout() {
        float f;
        LinearLayout linearLayout =
                (LinearLayout) this.mFontSizeLayout.findViewById(R.id.font_size_seek_bar_container);
        this.mFontSizeSeekBarContainer = linearLayout;
        linearLayout.setOnTouchListener(
                new SecFontSizePreferenceFragment$$ExternalSyntheticLambda0());
        SeslSeekBar seslSeekBar =
                (SeslSeekBar) this.mFontSizeSeekBarContainer.findViewById(R.id.font_size_seek_bar);
        this.mFontSizeSeekBar = seslSeekBar;
        seslSeekBar.setOnSeekBarChangeListener(new OnFontSizeSeekBarChangeListener());
        int dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.sec_widget_inset_category_height);
        if (shouldBeDisplayedAsPortrait()) {
            f = this.mDisplayMetrics.density * 280.0f;
        } else {
            f = dimensionPixelSize + (this.mDisplayMetrics.density * 560.0f);
        }
        if (this.mDisplayMetrics.widthPixels < f) {
            this.mFontSizeSeekBarContainer
                    .findViewById(R.id.font_size_seek_bar_smaller_button)
                    .setVisibility(8);
            this.mFontSizeSeekBarContainer
                    .findViewById(R.id.font_size_seek_bar_larger_button)
                    .setVisibility(8);
            ImageButton imageButton =
                    (ImageButton)
                            this.mFontSizeSeekBarContainer.findViewById(
                                    R.id.font_size_seek_bar_smaller_button_bottom);
            this.mSmallerButton = imageButton;
            imageButton.setVisibility(0);
            ImageButton imageButton2 =
                    (ImageButton)
                            this.mFontSizeSeekBarContainer.findViewById(
                                    R.id.font_size_seek_bar_larger_button_bottom);
            this.mLargerButton = imageButton2;
            imageButton2.setVisibility(0);
        } else {
            this.mFontSizeSeekBarContainer
                    .findViewById(R.id.font_size_seek_bar_smaller_button_bottom)
                    .setVisibility(8);
            this.mFontSizeSeekBarContainer
                    .findViewById(R.id.font_size_seek_bar_larger_button_bottom)
                    .setVisibility(8);
            ImageButton imageButton3 =
                    (ImageButton)
                            this.mFontSizeSeekBarContainer.findViewById(
                                    R.id.font_size_seek_bar_smaller_button);
            this.mSmallerButton = imageButton3;
            imageButton3.setVisibility(0);
            ImageButton imageButton4 =
                    (ImageButton)
                            this.mFontSizeSeekBarContainer.findViewById(
                                    R.id.font_size_seek_bar_larger_button);
            this.mLargerButton = imageButton4;
            imageButton4.setVisibility(0);
        }
        this.mSmallerButton.setOnClickListener(this.mSmallerButtonClickListener);
        this.mLargerButton.setOnClickListener(this.mLargerButtonClickListener);
    }

    public final boolean shouldBeDisplayedAsPortrait() {
        if (this.mResources.getConfiguration().orientation == 1) {
            return true;
        }
        FragmentActivity fragmentActivity = this.mActivity;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        return ActivityEmbeddingController.getInstance(fragmentActivity)
                .isActivityEmbedded(fragmentActivity);
    }

    public final void updateFontLayoutPreferences() {
        this.mFontSizeSupportPreferenceFragment.getListView().setVerticalScrollBarEnabled(false);
        if (shouldBeDisplayedAsPortrait()) {
            addPreferencesOnResume(
                    mFontSizePreviewPreference, mFontSizePreviewBottomPaddingPreference);
        } else {
            for (Preference preference :
                    new Preference[] {
                        mFontSizePreviewPreference, mFontSizePreviewBottomPaddingPreference
                    }) {
                this.mFontSizeSupportPreferenceFragment
                        .getPreferenceScreen()
                        .removePreference(preference);
            }
        }
        mSecPreferenceDummyItem.setHeight(
                this.mResources.getDimensionPixelSize(
                                R.dimen.sec_display_setting_seek_bar_container_height)
                        + this.mResources.getDimensionPixelSize(
                                R.dimen.sec_widget_inset_category_height));
        if (!this.mIsFromStore
                && !KnoxUtils.isApplicationRestricted(this.mContext, "sec_font_style", "hide")
                && WizardManagerHelper.isUserSetupComplete(this.mContext)) {
            addPreferencesOnResume(mFontStylePreference);
            if (KnoxUtils.isApplicationRestricted(this.mContext, "sec_font_style", "grayout")) {
                mFontStylePreference.setEnabled(false);
                return;
            }
            return;
        }
        for (Preference preference2 : new Preference[] {mFontStylePreference}) {
            this.mFontSizeSupportPreferenceFragment
                    .getPreferenceScreen()
                    .removePreference(preference2);
        }
        int i = this.mPreviewFontSize;
        if (i != -1) {
            this.mFontSizeSeekBar.setProgress(i);
            changeFontSizeOnPreview(this.mPreviewFontSize);
        }
    }

    public final void updateSeekBar$1() {
        this.mIsInSeekBarSetupProcess = true;
        this.mFontSizeSeekBar.setMax(7);
        SeslSeekBar seslSeekBar = this.mFontSizeSeekBar;
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        seslSeekBar.setProgress(
                Settings.Global.getInt(context.getContentResolver(), "font_size", 2));
        this.mFontSizeSeekBar.setMode(8);
        this.mIsInSeekBarSetupProcess = false;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {}
}
