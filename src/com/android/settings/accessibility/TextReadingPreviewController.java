package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.internal.util.Preconditions;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.instrumentation.SettingsStatsLog;
import com.android.settings.display.PreviewPagerAdapter;
import com.android.settings.widget.LabeledSeekBarPreference;
import com.android.settingslib.display.DisplayDensityUtils;
import com.android.settingslib.display.DisplayDensityUtils$$ExternalSyntheticLambda0;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
class TextReadingPreviewController extends BasePreferenceController
        implements PreviewSizeSeekBarController.ProgressInteractionListener {
    private static final long CHANGE_BY_BUTTON_DELAY_MS = 300;
    private static final long CHANGE_BY_SEEKBAR_DELAY_MS = 100;
    private static final String DISPLAY_SIZE_KEY = "display_size";
    private static final String FONT_SIZE_KEY = "font_size";
    private static final int FRAME_INITIAL_INDEX = 0;
    private static final int LAYER_INITIAL_INDEX = 0;
    private static final long MIN_COMMIT_INTERVAL_MS = 800;
    private static final String PREVIEW_KEY = "preview";
    private static final String TAG = "TextReadingPreviewCtrl";
    private final Choreographer.FrameCallback mCommit;
    private final DisplaySizeData mDisplaySizeData;
    private LabeledSeekBarPreference mDisplaySizePreference;
    private int mEntryPoint;
    private final FontSizeData mFontSizeData;
    private LabeledSeekBarPreference mFontSizePreference;
    private long mLastCommitTime;
    private int mLastDisplayProgress;
    private int mLastFontProgress;
    private TextReadingPreviewPreference mPreviewPreference;

    public TextReadingPreviewController(
            Context context,
            String str,
            FontSizeData fontSizeData,
            DisplaySizeData displaySizeData) {
        super(context, str);
        this.mCommit =
                new Choreographer
                        .FrameCallback() { // from class:
                                           // com.android.settings.accessibility.TextReadingPreviewController$$ExternalSyntheticLambda0
                    @Override // android.view.Choreographer.FrameCallback
                    public final void doFrame(long j) {
                        TextReadingPreviewController.this.lambda$new$0(j);
                    }
                };
        this.mFontSizeData = fontSizeData;
        this.mDisplaySizeData = displaySizeData;
    }

    private Configuration[] createConfig(Configuration configuration) {
        int size = this.mFontSizeData.mValues.size();
        int size2 = this.mDisplaySizeData.mValues.size();
        Configuration[] configurationArr = new Configuration[size * size2];
        for (int i = 0; i < size; i++) {
            for (int i2 = 0; i2 < size2; i2++) {
                Configuration configuration2 = new Configuration(configuration);
                configuration2.fontScale = ((Float) this.mFontSizeData.mValues.get(i)).floatValue();
                configuration2.densityDpi =
                        ((Integer) this.mDisplaySizeData.mValues.get(i2)).intValue();
                configurationArr[(i * size2) + i2] = configuration2;
            }
        }
        return configurationArr;
    }

    private int getPagerIndex() {
        int size = this.mDisplaySizeData.mValues.size();
        return (this.mFontSizePreference.mProgress * size) + this.mDisplaySizePreference.mProgress;
    }

    public static int[] getPreviewSampleLayouts(Context context) {
        TypedArray obtainTypedArray =
                context.getResources()
                        .obtainTypedArray(R.array.config_text_reading_preview_samples);
        int length = obtainTypedArray.length();
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = obtainTypedArray.getResourceId(i, R.layout.screen_zoom_preview_1);
        }
        obtainTypedArray.recycle();
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(long j) {
        tryCommitFontSizeConfig();
        tryCommitDisplaySizeConfig();
        this.mLastCommitTime = SystemClock.elapsedRealtime();
    }

    private void tryCommitDisplaySizeConfig() {
        int i = this.mDisplaySizePreference.mProgress;
        if (i != this.mLastDisplayProgress) {
            DisplaySizeData displaySizeData = this.mDisplaySizeData;
            int intValue = ((Integer) displaySizeData.mValues.get(i)).intValue();
            int intValue2 = ((Integer) displaySizeData.mDefaultValue).intValue();
            final DisplayDensityUtils displayDensityUtils = displaySizeData.mDensity;
            if (intValue == intValue2) {
                displayDensityUtils.getClass();
                final int myUserId = UserHandle.myUserId();
                AsyncTask.execute(
                        new Runnable() { // from class:
                            // com.android.settingslib.display.DisplayDensityUtils$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                DisplayDensityUtils displayDensityUtils2 = DisplayDensityUtils.this;
                                int i2 = myUserId;
                                displayDensityUtils2.getClass();
                                try {
                                    for (Display display :
                                            displayDensityUtils2.mDisplayManager.getDisplays(
                                                    "android.hardware.display.category.ALL_INCLUDING_DISABLED")) {
                                        int displayId = display.getDisplayId();
                                        DisplayInfo displayInfo = new DisplayInfo();
                                        if (!display.getDisplayInfo(displayInfo)) {
                                            Log.w(
                                                    "DisplayDensityUtils",
                                                    "Unable to clear forced display density setting"
                                                        + " for display "
                                                            + displayId);
                                        } else if (displayDensityUtils2.mPredicate.test(
                                                displayInfo)) {
                                            WindowManagerGlobal.getWindowManagerService()
                                                    .clearForcedDisplayDensityForUser(
                                                            displayId, i2);
                                        }
                                    }
                                } catch (RemoteException unused) {
                                    Log.w(
                                            "DisplayDensityUtils",
                                            "Unable to clear forced display density setting");
                                }
                            }
                        });
            } else {
                displayDensityUtils.getClass();
                AsyncTask.execute(
                        new DisplayDensityUtils$$ExternalSyntheticLambda0(
                                displayDensityUtils, i, UserHandle.myUserId()));
            }
            this.mLastDisplayProgress = i;
            if (Log.isLoggable(TAG, 3)) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "Display size: ", TAG);
            }
            SettingsStatsLog.write(
                    454,
                    AccessibilityStatsLogUtils.convertToItemKeyName(
                            this.mDisplaySizePreference.getKey()),
                    i,
                    AccessibilityStatsLogUtils.convertToEntryPoint(this.mEntryPoint));
        }
    }

    private void tryCommitFontSizeConfig() {
        int i = this.mFontSizePreference.mProgress;
        if (i != this.mLastFontProgress) {
            FontSizeData fontSizeData = this.mFontSizeData;
            ContentResolver contentResolver = fontSizeData.mContext.getContentResolver();
            if (Settings.Secure.getInt(
                            contentResolver, "accessibility_font_scaling_has_been_changed", 0)
                    != 1) {
                Settings.Secure.putInt(
                        contentResolver, "accessibility_font_scaling_has_been_changed", 1);
            }
            Settings.System.putFloat(
                    contentResolver,
                    "font_scale",
                    ((Float) fontSizeData.mValues.get(i)).floatValue());
            this.mLastFontProgress = i;
            if (Log.isLoggable(TAG, 3)) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "Font size: ", TAG);
            }
            SettingsStatsLog.write(
                    454,
                    AccessibilityStatsLogUtils.convertToItemKeyName(
                            this.mFontSizePreference.getKey()),
                    i,
                    AccessibilityStatsLogUtils.convertToEntryPoint(this.mEntryPoint));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreviewPreference =
                (TextReadingPreviewPreference) preferenceScreen.findPreference(PREVIEW_KEY);
        this.mFontSizePreference =
                (LabeledSeekBarPreference) preferenceScreen.findPreference(FONT_SIZE_KEY);
        this.mDisplaySizePreference =
                (LabeledSeekBarPreference) preferenceScreen.findPreference(DISPLAY_SIZE_KEY);
        Objects.requireNonNull(
                this.mFontSizePreference,
                "Font size preference is null, the preview controller couldn't get the info");
        Objects.requireNonNull(
                this.mDisplaySizePreference,
                "Display size preference is null, the preview controller couldn't get the info");
        this.mLastFontProgress = this.mFontSizeData.mInitialIndex;
        this.mLastDisplayProgress = this.mDisplaySizeData.mInitialIndex;
        Configuration configuration = this.mContext.getResources().getConfiguration();
        boolean z = configuration.getLayoutDirection() == 1;
        int[] previewSampleLayouts = getPreviewSampleLayouts(this.mContext);
        Context context = this.mContext;
        Configuration[] createConfig = createConfig(configuration);
        final PreviewPagerAdapter previewPagerAdapter = new PreviewPagerAdapter();
        previewPagerAdapter.mIsLayoutRtl = z;
        previewPagerAdapter.mPreviewFrames = new FrameLayout[previewSampleLayouts.length];
        previewPagerAdapter.mViewStubInflated =
                (boolean[][])
                        Array.newInstance(
                                (Class<?>) Boolean.TYPE,
                                previewSampleLayouts.length,
                                createConfig.length);
        for (final int i = 0; i < previewSampleLayouts.length; i++) {
            int length =
                    previewPagerAdapter.mIsLayoutRtl ? (previewSampleLayouts.length - 1) - i : i;
            previewPagerAdapter.mPreviewFrames[length] = new FrameLayout(context);
            previewPagerAdapter.mPreviewFrames[length].setLayoutParams(
                    new LinearLayout.LayoutParams(-1, -1));
            previewPagerAdapter.mPreviewFrames[length].setClipToPadding(true);
            previewPagerAdapter.mPreviewFrames[length].setClipChildren(true);
            for (final int i2 = 0; i2 < createConfig.length; i2++) {
                Context createConfigurationContext =
                        context.createConfigurationContext(createConfig[i2]);
                createConfigurationContext.getTheme().setTo(context.getTheme());
                ViewStub viewStub = new ViewStub(createConfigurationContext);
                viewStub.setLayoutResource(previewSampleLayouts[i]);
                viewStub.setOnInflateListener(
                        new ViewStub
                                .OnInflateListener() { // from class:
                                                       // com.android.settings.display.PreviewPagerAdapter$$ExternalSyntheticLambda0
                            @Override // android.view.ViewStub.OnInflateListener
                            public final void onInflate(ViewStub viewStub2, View view) {
                                PreviewPagerAdapter previewPagerAdapter2 = PreviewPagerAdapter.this;
                                int i3 = i;
                                int i4 = i2;
                                previewPagerAdapter2.getClass();
                                view.setVisibility(viewStub2.getVisibility());
                                previewPagerAdapter2.mViewStubInflated[i3][i4] = true;
                            }
                        });
                previewPagerAdapter.mPreviewFrames[length].addView(viewStub);
            }
        }
        TextReadingPreviewPreference textReadingPreviewPreference = this.mPreviewPreference;
        if (previewPagerAdapter != textReadingPreviewPreference.mPreviewAdapter) {
            textReadingPreviewPreference.mPreviewAdapter = previewPagerAdapter;
            textReadingPreviewPreference.notifyChanged();
        }
        TextReadingPreviewPreference textReadingPreviewPreference2 = this.mPreviewPreference;
        int length2 = z ? previewSampleLayouts.length - 1 : 0;
        Preconditions.checkNotNull(
                textReadingPreviewPreference2.mPreviewAdapter,
                "Preview adapter is null, you should init the preview adapter first");
        if (length2 != textReadingPreviewPreference2.mCurrentItem) {
            textReadingPreviewPreference2.mCurrentItem = length2;
            textReadingPreviewPreference2.notifyChanged();
        }
        int size =
                (this.mDisplaySizeData.mValues.size() * this.mLastFontProgress)
                        + this.mLastDisplayProgress;
        this.mPreviewPreference.mLastLayerIndex = size;
        previewPagerAdapter.setPreviewLayer(size, 0, 0);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public int getCurrentItem() {
        return this.mPreviewPreference.mCurrentItem;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController.ProgressInteractionListener
    public void notifyPreferenceChanged() {
        TextReadingPreviewPreference textReadingPreviewPreference = this.mPreviewPreference;
        int pagerIndex = getPagerIndex();
        Preconditions.checkNotNull(
                textReadingPreviewPreference.mPreviewAdapter,
                "Preview adapter is null, you should init the preview adapter first");
        int i = textReadingPreviewPreference.mLastLayerIndex;
        if (pagerIndex != i) {
            textReadingPreviewPreference.mPreviewAdapter.setPreviewLayer(
                    pagerIndex, i, textReadingPreviewPreference.mCurrentItem);
        }
        textReadingPreviewPreference.mLastLayerIndex = pagerIndex;
    }

    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController.ProgressInteractionListener
    public void onEndTrackingTouch() {
        postCommitDelayed(CHANGE_BY_SEEKBAR_DELAY_MS);
    }

    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController.ProgressInteractionListener
    public void onProgressChanged() {
        postCommitDelayed(CHANGE_BY_BUTTON_DELAY_MS);
    }

    public void postCommitDelayed(long j) {
        if (SystemClock.elapsedRealtime() - this.mLastCommitTime < MIN_COMMIT_INTERVAL_MS) {
            j += MIN_COMMIT_INTERVAL_MS;
        }
        Choreographer choreographer = Choreographer.getInstance();
        choreographer.removeFrameCallback(this.mCommit);
        choreographer.postFrameCallbackDelayed(this.mCommit, j);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setCurrentItem(int i) {
        TextReadingPreviewPreference textReadingPreviewPreference = this.mPreviewPreference;
        Preconditions.checkNotNull(
                textReadingPreviewPreference.mPreviewAdapter,
                "Preview adapter is null, you should init the preview adapter first");
        if (i != textReadingPreviewPreference.mCurrentItem) {
            textReadingPreviewPreference.mCurrentItem = i;
            textReadingPreviewPreference.notifyChanged();
        }
    }

    public void setEntryPoint(int i) {
        this.mEntryPoint = i;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
