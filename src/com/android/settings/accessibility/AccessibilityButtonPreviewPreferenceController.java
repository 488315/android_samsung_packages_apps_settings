package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.IllustrationPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilityButtonPreviewPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final float DEFAULT_OPACITY = 0.55f;
    private static final int DEFAULT_SIZE = 0;
    private static final int SMALL_SIZE = 0;
    final ContentObserver mContentObserver;
    private final ContentResolver mContentResolver;
    IllustrationPreference mIllustrationPreference;
    private AccessibilityManager.TouchExplorationStateChangeListener
            mTouchExplorationStateChangeListener;

    public AccessibilityButtonPreviewPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentResolver = context.getContentResolver();
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.accessibility.AccessibilityButtonPreviewPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        AccessibilityButtonPreviewPreferenceController.this
                                .updatePreviewPreference();
                    }
                };
        this.mTouchExplorationStateChangeListener =
                new AccessibilityManager
                        .TouchExplorationStateChangeListener() { // from class:
                                                                 // com.android.settings.accessibility.AccessibilityButtonPreviewPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                    public final void onTouchExplorationStateChanged(boolean z) {
                        AccessibilityButtonPreviewPreferenceController.this.lambda$new$0(z);
                    }
                };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(boolean z) {
        updatePreviewPreference();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePreviewPreference() {
        if (AccessibilityUtil.isFloatingMenuEnabled(this.mContext)) {
            int i =
                    Settings.Secure.getInt(
                            this.mContentResolver, "accessibility_floating_menu_size", 0);
            int i2 =
                    (int)
                            (Settings.Secure.getFloat(
                                            this.mContentResolver,
                                            "accessibility_floating_menu_opacity",
                                            DEFAULT_OPACITY)
                                    * 255.0f);
            Drawable drawable =
                    this.mContext.getDrawable(
                            i == 0
                                    ? R.drawable.accessibility_shortcut_type_fab_size_small_preview
                                    : R.drawable
                                            .accessibility_shortcut_type_fab_size_large_preview);
            drawable.setAlpha(i2);
            IllustrationPreference illustrationPreference = this.mIllustrationPreference;
            if (drawable != illustrationPreference.mImageDrawable) {
                illustrationPreference.mImageResId = 0;
                illustrationPreference.mImageDrawable = drawable;
                illustrationPreference.notifyChanged();
                return;
            }
            return;
        }
        if (!AccessibilityUtil.isGestureNavigateEnabled(this.mContext)) {
            IllustrationPreference illustrationPreference2 = this.mIllustrationPreference;
            Drawable drawable2 =
                    this.mContext.getDrawable(R.drawable.accessibility_shortcut_type_navbar);
            if (drawable2 != illustrationPreference2.mImageDrawable) {
                illustrationPreference2.mImageResId = 0;
                illustrationPreference2.mImageDrawable = drawable2;
                illustrationPreference2.notifyChanged();
                return;
            }
            return;
        }
        IllustrationPreference illustrationPreference3 = this.mIllustrationPreference;
        Context context = this.mContext;
        Drawable drawable3 =
                context.getDrawable(
                        AccessibilityUtil.isTouchExploreEnabled(context)
                                ? R.drawable.accessibility_shortcut_type_gesture_touch_explore_on
                                : R.drawable.accessibility_shortcut_type_gesture);
        if (drawable3 != illustrationPreference3.mImageDrawable) {
            illustrationPreference3.mImageResId = 0;
            illustrationPreference3.mImageDrawable = drawable3;
            illustrationPreference3.notifyChanged();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mIllustrationPreference =
                (IllustrationPreference) preferenceScreen.findPreference(getPreferenceKey());
        updatePreviewPreference();
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        ((AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class))
                .removeTouchExplorationStateChangeListener(
                        this.mTouchExplorationStateChangeListener);
        this.mContentResolver.unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        ((AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class))
                .addTouchExplorationStateChangeListener(this.mTouchExplorationStateChangeListener);
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_button_mode"),
                false,
                this.mContentObserver);
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_floating_menu_size"),
                false,
                this.mContentObserver);
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_floating_menu_opacity"),
                false,
                this.mContentObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
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
