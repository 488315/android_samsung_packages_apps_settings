package com.samsung.android.settings.accessibility.recommend;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.accessibility.advanced.flashnotification.SecCameraFlashNotificationPreferenceController$$ExternalSyntheticLambda0;
import com.samsung.android.settings.accessibility.recommend.tryitem.AccessibilityTimeoutTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.AudioDescriptionTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.BixbyVisionForA11yTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.BixbyVisionTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.BoldFontTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.CameraFlashNotificationTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.CaptionPreferencesTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.DarkModeTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.HighContrastKeyboardTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.LiveCaptionTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.LiveTranscribeTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.PointerSizeColorTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.RemoveAnimationsTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.ScreenFlashNotificationTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.SpeakKeyboardInputAloudTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.TryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.VibrationIntensityTryItem;
import com.samsung.android.settings.accessibility.recommend.tryitem.VibrationPatternTryItem;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TryCategoryPreferenceController extends BasePreferenceController {
    private static final int MAX_TRY_IT_COUNT = 4;
    private final List<UsingFunctionItem> functionItems;
    private final Set<TryItem> tryItems;

    public TryCategoryPreferenceController(Context context, String str) {
        super(context, str);
        Context context2 = this.mContext;
        LiveTranscribeTryItem liveTranscribeTryItem = new LiveTranscribeTryItem(context2);
        BixbyVisionTryItem bixbyVisionTryItem = new BixbyVisionTryItem(context2);
        Context context3 = this.mContext;
        AccessibilityTimeoutTryItem accessibilityTimeoutTryItem =
                new AccessibilityTimeoutTryItem(context3);
        AudioDescriptionTryItem audioDescriptionTryItem = new AudioDescriptionTryItem(context3);
        BixbyVisionForA11yTryItem bixbyVisionForA11yTryItem =
                new BixbyVisionForA11yTryItem(context3);
        BoldFontTryItem boldFontTryItem = new BoldFontTryItem(this.mContext);
        Context context4 = this.mContext;
        CaptionPreferencesTryItem captionPreferencesTryItem =
                new CaptionPreferencesTryItem(context4);
        DarkModeTryItem darkModeTryItem = new DarkModeTryItem(context4);
        CameraFlashNotificationTryItem cameraFlashNotificationTryItem =
                new CameraFlashNotificationTryItem(context4);
        ScreenFlashNotificationTryItem screenFlashNotificationTryItem =
                new ScreenFlashNotificationTryItem(context4);
        HighContrastKeyboardTryItem highContrastKeyboardTryItem =
                new HighContrastKeyboardTryItem(context4);
        Context context5 = this.mContext;
        this.tryItems =
                Set.of(
                        (Object[])
                                new TryItem[] {
                                    liveTranscribeTryItem,
                                    bixbyVisionTryItem,
                                    accessibilityTimeoutTryItem,
                                    audioDescriptionTryItem,
                                    bixbyVisionForA11yTryItem,
                                    boldFontTryItem,
                                    captionPreferencesTryItem,
                                    darkModeTryItem,
                                    cameraFlashNotificationTryItem,
                                    screenFlashNotificationTryItem,
                                    highContrastKeyboardTryItem,
                                    new LiveCaptionTryItem(context5),
                                    new PointerSizeColorTryItem(context5),
                                    new RemoveAnimationsTryItem(context5),
                                    new SpeakKeyboardInputAloudTryItem(context5),
                                    new VibrationIntensityTryItem(context5),
                                    new VibrationPatternTryItem(context5)
                                });
        this.functionItems = UsingFunctionsProvider.getInstance(this.mContext).usingFunctionItems;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateState$0(UsingFunctionItem usingFunctionItem) {
        return usingFunctionItem.isTurnedOn(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateState$1(Set set, TryItem tryItem) {
        if (tryItem.isAvailable()) {
            Stream stream = tryItem.getMappedUsingFunctionKeySet().stream();
            Objects.requireNonNull(set);
            if (stream.anyMatch(
                    new SecCameraFlashNotificationPreferenceController$$ExternalSyntheticLambda0(
                            1, set))) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference instanceof PreferenceCategory) {
            PreferenceCategory preferenceCategory = (PreferenceCategory) findPreference;
            for (int i = 0; i < 4; i++) {
                Preference preference = new Preference(this.mContext);
                preference.setVisible(false);
                preferenceCategory.addPreference(preference);
            }
        }
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
        if (preference instanceof PreferenceCategory) {
            PreferenceCategory preferenceCategory = (PreferenceCategory) preference;
            final int i = 0;
            final Set set =
                    (Set)
                            this.functionItems.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.samsung.android.settings.accessibility.recommend.TryCategoryPreferenceController$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    boolean lambda$updateState$0;
                                                    boolean lambda$updateState$1;
                                                    int i2 = i;
                                                    Object obj2 = this;
                                                    switch (i2) {
                                                        case 0:
                                                            lambda$updateState$0 =
                                                                    ((TryCategoryPreferenceController)
                                                                                    obj2)
                                                                            .lambda$updateState$0(
                                                                                    (UsingFunctionItem)
                                                                                            obj);
                                                            return lambda$updateState$0;
                                                        default:
                                                            lambda$updateState$1 =
                                                                    TryCategoryPreferenceController
                                                                            .lambda$updateState$1(
                                                                                    (Set) obj2,
                                                                                    (TryItem) obj);
                                                            return lambda$updateState$1;
                                                    }
                                                }
                                            })
                                    .map(
                                            new TryCategoryPreferenceController$$ExternalSyntheticLambda1())
                                    .collect(Collectors.toSet());
            final int i2 = 1;
            List<TryItem> list =
                    this.tryItems.stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.settings.accessibility.recommend.TryCategoryPreferenceController$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            boolean lambda$updateState$0;
                                            boolean lambda$updateState$1;
                                            int i22 = i2;
                                            Object obj2 = set;
                                            switch (i22) {
                                                case 0:
                                                    lambda$updateState$0 =
                                                            ((TryCategoryPreferenceController) obj2)
                                                                    .lambda$updateState$0(
                                                                            (UsingFunctionItem)
                                                                                    obj);
                                                    return lambda$updateState$0;
                                                default:
                                                    lambda$updateState$1 =
                                                            TryCategoryPreferenceController
                                                                    .lambda$updateState$1(
                                                                            (Set) obj2,
                                                                            (TryItem) obj);
                                                    return lambda$updateState$1;
                                            }
                                        }
                                    })
                            .limit(4L)
                            .toList();
            int size = list.size();
            boolean z = size > 0;
            preferenceCategory.setVisible(z);
            if (z) {
                for (int i3 = 0; i3 < 4; i3++) {
                    Preference preference2 = preferenceCategory.getPreference(i3);
                    if (i3 < size) {
                        TryItem tryItem = list.get(i3);
                        preference2.setTitle(tryItem.getTitle());
                        preference2.setIntent(tryItem.getLaunchIntent());
                        preference2.setVisible(true);
                    } else {
                        preference2.setVisible(false);
                    }
                }
            }
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
