package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.SubtitleView;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CaptioningPreviewPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    static final List<String> CAPTIONING_FEATURE_KEYS =
            Arrays.asList(
                    "accessibility_captioning_foreground_color",
                    "accessibility_captioning_background_color",
                    "accessibility_captioning_window_color",
                    "accessibility_captioning_edge_color",
                    "accessibility_captioning_preset",
                    "accessibility_captioning_edge_type",
                    "accessibility_captioning_typeface",
                    "accessibility_captioning_font_scale");
    private CaptionHelper mCaptionHelper;
    private final Handler mHandler;
    private LayoutPreference mPreference;
    AccessibilitySettingsContentObserver mSettingsContentObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accessibility.CaptioningPreviewPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnLayoutChangeListener {
        public final /* synthetic */ View val$previewViewport;

        public AnonymousClass1(View view) {
            this.val$previewViewport = view;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(
                View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (i7 - i5 != i3 - i) {
                this.val$previewViewport.removeOnLayoutChangeListener(this);
                CaptioningPreviewPreferenceController.this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.settings.accessibility.CaptioningPreviewPreferenceController$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CaptioningPreviewPreferenceController.this.refreshPreviewText();
                            }
                        });
            }
        }
    }

    public CaptioningPreviewPreferenceController(Context context, String str) {
        super(context, str);
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mCaptionHelper = new CaptionHelper(context);
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                new AccessibilitySettingsContentObserver(handler);
        this.mSettingsContentObserver = accessibilitySettingsContentObserver;
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(
                CAPTIONING_FEATURE_KEYS,
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.android.settings.accessibility.CaptioningPreviewPreferenceController$$ExternalSyntheticLambda0
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str2) {
                        CaptioningPreviewPreferenceController.this.lambda$new$0(str2);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(String str) {
        refreshPreviewText();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshPreviewText() {
        SubtitleView findViewById = this.mPreference.mRootView.findViewById(R.id.preview_text);
        if (findViewById != null) {
            this.mPreference.mRootView.findViewById(R.id.preview_viewport);
            int rawUserStyle = this.mCaptionHelper.mCaptioningManager.getRawUserStyle();
            CaptionHelper captionHelper = this.mCaptionHelper;
            captionHelper.getClass();
            findViewById.setStyle(rawUserStyle);
            findViewById.setTextSize(
                    captionHelper.mCaptioningManager.getFontScale()
                            * findViewById
                                    .getContext()
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.captioning_preview_caption_font_size));
            Locale locale = this.mCaptionHelper.mCaptioningManager.getLocale();
            if (locale != null) {
                Context context = this.mContext;
                Configuration configuration =
                        new Configuration(context.getResources().getConfiguration());
                configuration.setLocale(locale);
                findViewById.setText(
                        context.createConfigurationContext(configuration)
                                .getText(R.string.captioning_preview_text));
            } else {
                findViewById.setText(R.string.captioning_preview_text);
            }
            View findViewById2 = this.mPreference.mRootView.findViewById(R.id.preview_window);
            CaptioningManager.CaptionStyle userStyle =
                    this.mCaptionHelper.mCaptioningManager.getUserStyle();
            if (userStyle.hasWindowColor()) {
                findViewById2.setBackgroundColor(userStyle.windowColor);
            } else {
                findViewById2.setBackgroundColor(
                        CaptioningManager.CaptionStyle.DEFAULT.windowColor);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = layoutPreference;
        View findViewById = layoutPreference.mRootView.findViewById(R.id.preview_viewport);
        findViewById.addOnLayoutChangeListener(new AnonymousClass1(findViewById));
        findViewById.setPadding(0, 0, 0, 0);
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mSettingsContentObserver.register(this.mContext.getContentResolver());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsContentObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        ImageView imageView = (ImageView) this.mPreference.mRootView.findViewById(R.id.preview_img);
        if (imageView != null) {
            Resources resources = this.mContext.getResources();
            Configuration configuration = resources.getConfiguration();
            imageView.getLayoutParams().height =
                    (configuration.orientation == 2 || configuration.semMobileKeyboardCovered == 1)
                            ? resources.getDimensionPixelSize(
                                    R.dimen.captioning_preview_height_landscape)
                            : resources.getDimensionPixelSize(R.dimen.captioning_preview_height);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
