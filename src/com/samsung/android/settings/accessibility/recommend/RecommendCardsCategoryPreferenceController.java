package com.samsung.android.settings.accessibility.recommend;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.homepage.contextualcards.ContextualCardManager$$ExternalSyntheticLambda3;

import com.samsung.android.settings.accessibility.base.widget.InlineCuePreference;
import com.samsung.android.settings.accessibility.recommend.recommendcard.AbstractRoutineRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.AnsweringCallsRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.BixbyQuickCommandRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.BixbyTextCallRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.CallBackgroundRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.CameraOCRRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.CaptionRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.DarkModeRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.EditLockScreenRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.ExtraDimRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.HighContrastRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.MagnificationRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.MagnifierRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.RecommendCardItem;
import com.samsung.android.settings.accessibility.recommend.recommendcard.RoutineRecommendCardForAAS;
import com.samsung.android.settings.accessibility.recommend.recommendcard.RoutineRecommendCardForColor;
import com.samsung.android.settings.accessibility.recommend.recommendcard.RoutineRecommendCardForFlash;
import com.samsung.android.settings.accessibility.recommend.recommendcard.RoutineRecommendCardForNoProfile;
import com.samsung.android.settings.accessibility.recommend.recommendcard.RoutineRecommendCardForSoundMode;
import com.samsung.android.settings.accessibility.recommend.recommendcard.SamsungInternetHighContrastRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.SmartThingsRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.TalkBackGestureRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.VoiceInputRecommendCard;
import com.samsung.android.settings.accessibility.recommend.recommendcard.VoiceRecorderRecommendCard;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RecommendCardsCategoryPreferenceController extends BasePreferenceController {
    private static final String KEY_CATEGORY_SUFFIX = "_category";
    private static final String KEY_PROFILE_RECOMMEND_CARD_1 = "recommend_card_1";
    private static final String KEY_PROFILE_RECOMMEND_CARD_2 = "recommend_card_2";
    private static final String KEY_TALKBACK_RECOMMEND_CARD = "talkback_recommend_card";
    private AccessibilityProfile a11yProfile;
    private final Set<RecommendCardItem> cardItemList;
    private final RecommendCardItem talkBackCardItem;

    public RecommendCardsCategoryPreferenceController(Context context, String str) {
        super(context, str);
        this.cardItemList =
                Set.of(
                        (Object[])
                                new RecommendCardItem[] {
                                    new BixbyTextCallRecommendCard(),
                                    new CameraOCRRecommendCard(),
                                    new DarkModeRecommendCard(),
                                    new ExtraDimRecommendCard(),
                                    new RoutineRecommendCardForNoProfile(),
                                    new BixbyQuickCommandRecommendCard(),
                                    new CallBackgroundRecommendCard(),
                                    new CaptionRecommendCard(),
                                    new EditLockScreenRecommendCard(),
                                    new HighContrastRecommendCard(),
                                    new MagnificationRecommendCard(),
                                    new MagnifierRecommendCard(),
                                    new RoutineRecommendCardForColor(),
                                    new RoutineRecommendCardForFlash(),
                                    new SamsungInternetHighContrastRecommendCard(),
                                    new SmartThingsRecommendCard(),
                                    new VoiceInputRecommendCard(),
                                    new VoiceRecorderRecommendCard(),
                                    new RoutineRecommendCardForAAS(),
                                    new RoutineRecommendCardForSoundMode(),
                                    new AnsweringCallsRecommendCard()
                                });
        this.talkBackCardItem = new TalkBackGestureRecommendCard();
    }

    private Preference convertCardItemToPreference(RecommendCardItem recommendCardItem) {
        InlineCuePreference inlineCuePreference = new InlineCuePreference(this.mContext, null);
        inlineCuePreference.setSummary(recommendCardItem.getCardContent(this.mContext));
        final RecommendCardItem.ActionButtonInfo actionButtonInfo =
                recommendCardItem.getActionButtonInfo(this.mContext);
        if (actionButtonInfo != null) {
            CharSequence buttonText = actionButtonInfo.getButtonText(this.mContext);
            View.OnClickListener onClickListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.accessibility.recommend.RecommendCardsCategoryPreferenceController$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            RecommendCardItem.ActionButtonInfo.this.onButtonClicked();
                        }
                    };
            inlineCuePreference.firstButtonTitle = buttonText;
            inlineCuePreference.firstButtonClickListener = onClickListener;
            inlineCuePreference.notifyChanged();
            inlineCuePreference.firstButtonContentDescription =
                    actionButtonInfo.getButtonContentDescription(this.mContext);
        }
        return inlineCuePreference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lambda$updateState$0(RecommendCardItem recommendCardItem) {
        if (recommendCardItem.isAvailable(this.mContext)) {
            AccessibilityProfile accessibilityProfile = this.a11yProfile;
            if ((accessibilityProfile.profileType & recommendCardItem.getSupportProfileType())
                    != 0) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List lambda$updateState$1(ArrayList arrayList) {
        Collections.shuffle(arrayList);
        return Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference instanceof PreferenceCategory) {
            Preference convertCardItemToPreference =
                    convertCardItemToPreference(this.talkBackCardItem);
            convertCardItemToPreference.setKey(KEY_TALKBACK_RECOMMEND_CARD);
            preferenceScreen.addPreference(convertCardItemToPreference);
            PreferenceCategory preferenceCategory = new PreferenceCategory(this.mContext);
            preferenceCategory.setKey("talkback_recommend_card_category");
            preferenceScreen.addPreference(preferenceCategory);
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
        boolean z;
        if (preference instanceof PreferenceCategory) {
            PreferenceScreen preferenceScreen = (PreferenceScreen) preference.getParent();
            boolean isAvailable = this.talkBackCardItem.isAvailable(this.mContext);
            Preference findPreference =
                    preferenceScreen.findPreference(KEY_TALKBACK_RECOMMEND_CARD);
            if (findPreference != null) {
                findPreference.setVisible(isAvailable);
            }
            Preference findPreference2 =
                    preferenceScreen.findPreference("talkback_recommend_card_category");
            if (findPreference2 != null) {
                findPreference2.setVisible(isAvailable);
            }
            AccessibilityProfile accessibilityProfile = this.a11yProfile;
            if (accessibilityProfile == null) {
                this.a11yProfile = new AccessibilityProfile(this.mContext);
                z = true;
            } else {
                int initProfileType =
                        AccessibilityProfile.initProfileType(accessibilityProfile.context);
                z = accessibilityProfile.profileType != initProfileType;
                accessibilityProfile.profileType = initProfileType;
            }
            if (z) {
                preferenceScreen.removePreferenceRecursively(KEY_PROFILE_RECOMMEND_CARD_1);
                preferenceScreen.removePreferenceRecursively("recommend_card_1_category");
                preferenceScreen.removePreferenceRecursively(KEY_PROFILE_RECOMMEND_CARD_2);
                List list =
                        (List)
                                this.cardItemList.stream()
                                        .filter(
                                                new Predicate() { // from class:
                                                                  // com.samsung.android.settings.accessibility.recommend.RecommendCardsCategoryPreferenceController$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Predicate
                                                    public final boolean test(Object obj) {
                                                        boolean lambda$updateState$0;
                                                        lambda$updateState$0 =
                                                                RecommendCardsCategoryPreferenceController
                                                                        .this
                                                                        .lambda$updateState$0(
                                                                                (RecommendCardItem)
                                                                                        obj);
                                                        return lambda$updateState$0;
                                                    }
                                                })
                                        .collect(
                                                Collectors.collectingAndThen(
                                                        Collectors.toCollection(
                                                                new ContextualCardManager$$ExternalSyntheticLambda3()),
                                                        new RecommendCardsCategoryPreferenceController$$ExternalSyntheticLambda2()));
                if (list.isEmpty()) {
                    return;
                }
                int i = 0;
                boolean z2 = false;
                for (int i2 = 0; i2 < list.size() && i < 2; i2++) {
                    if (list.get(i2) instanceof AbstractRoutineRecommendCard) {
                        if (!z2) {
                            z2 = true;
                        }
                    }
                    Preference convertCardItemToPreference =
                            convertCardItemToPreference((RecommendCardItem) list.get(i2));
                    if (i == 0) {
                        convertCardItemToPreference.setKey(KEY_PROFILE_RECOMMEND_CARD_1);
                        preferenceScreen.addPreference(convertCardItemToPreference);
                    } else if (i == 1) {
                        PreferenceCategory preferenceCategory =
                                new PreferenceCategory(this.mContext);
                        preferenceCategory.setKey("recommend_card_1_category");
                        preferenceScreen.addPreference(preferenceCategory);
                        convertCardItemToPreference.setKey(KEY_PROFILE_RECOMMEND_CARD_2);
                        preferenceScreen.addPreference(convertCardItemToPreference);
                    }
                    i++;
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
