package com.android.settings.activityembedding;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.window.embedding.ActivityFilter;
import androidx.window.embedding.ActivityRule;
import androidx.window.embedding.EmbeddingAspectRatio;
import androidx.window.embedding.RuleController;
import androidx.window.embedding.SplitAttributes;
import androidx.window.embedding.SplitPairFilter;
import androidx.window.embedding.SplitPairRule;
import androidx.window.embedding.SplitPlaceholderRule;
import androidx.window.embedding.SplitRule;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.SubSettings;
import com.android.settings.biometrics.face.FaceEnrollIntroduction;
import com.android.settings.biometrics.face.FaceEnrollIntroductionInternal;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroductionInternal;
import com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity;
import com.android.settings.homepage.DeepLinkHomepageActivity;
import com.android.settings.homepage.DeepLinkHomepageActivityInternal;
import com.android.settings.homepage.SettingsHomepageActivity;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ChooseLockPassword;
import com.android.settings.password.ChooseLockPattern;
import com.android.settings.password.ConfirmLockPassword;
import com.android.settings.password.ConfirmLockPattern;
import com.android.settings.privatespace.PrivateSpaceSetupActivity;
import com.android.settings.remoteauth.RemoteAuthActivity;
import com.android.settings.remoteauth.RemoteAuthActivityInternal;

import com.samsung.android.settings.Trace;
import com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity;
import com.samsung.android.settings.biometrics.BiometricsDisclaimerWithConfirmLock;
import com.samsung.android.settings.biometrics.BiometricsRotationGuide;
import com.samsung.android.settings.biometrics.face.FaceLockSettings;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings;
import com.samsung.android.settings.homepage.SecTopLevelFeature;
import com.samsung.android.settings.lockscreen.ChooseLockHintSettings;
import com.samsung.android.settings.lockscreen.bixbyroutine.LockScreenRoutineActionActivity;
import com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceActivity;

import java.util.Collection;
import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ActivityEmbeddingRulesController {
    public static final ComponentName COMPONENT_NAME_WILDCARD = new ComponentName("*", "*");
    public final Context mContext;
    public final RuleController mRuleController;

    public ActivityEmbeddingRulesController(Context context) {
        this.mContext = context;
        this.mRuleController = RuleController.getInstance(context);
    }

    public static void registerSubSettingsPairRule(Context context, boolean z) {
        Trace.beginSection("ActivityEmbeddingRulesController#registerSubSettingsPairRule");
        if (SecTopLevelFeature.getInstance().getBoolean("feature_is_embedding_activity_enabled")
                || ActivityEmbeddingUtils.isEmbeddingActivityEnabled(context)) {
            registerTwoPanePairRuleForSettingsHome(
                    context, new ComponentName(context, (Class<?>) SubSettings.class), null, z);
            registerTwoPanePairRuleForSettingsHome(
                    context, COMPONENT_NAME_WILDCARD, "android.intent.action.SAFETY_CENTER", z);
            Trace.endSection();
        }
    }

    public static void registerTwoPanePairRule(
            Context context,
            ComponentName componentName,
            ComponentName componentName2,
            String str,
            SplitRule.FinishBehavior finishBehavior,
            SplitRule.FinishBehavior finishBehavior2,
            boolean z) {
        if (SecTopLevelFeature.getInstance().getBoolean("feature_is_embedding_activity_enabled")
                || ActivityEmbeddingUtils.isEmbeddingActivityEnabled(context)) {
            Trace.beginSection("ActivityEmbeddingRulesController#registerTwoPanePairRule");
            HashSet hashSet = new HashSet();
            hashSet.add(new SplitPairFilter(componentName, componentName2, str));
            SplitAttributes.Builder builder = new SplitAttributes.Builder();
            boolean z2 = ActivityEmbeddingUtils.SHOULD_ENABLE_LARGE_SCREEN_OPTIMIZATION;
            RuleController.getInstance(context)
                    .addRule(
                            new SplitPairRule.Builder(hashSet)
                                    .setFinishPrimaryWithSecondary(finishBehavior)
                                    .setFinishSecondaryWithPrimary(finishBehavior2)
                                    .setClearTop(z)
                                    .setMinWidthDp(
                                            context.getResources()
                                                    .getInteger(
                                                            R.integer
                                                                    .config_activity_embed_split_min_cur_dp))
                                    .setMinSmallestWidthDp(
                                            context.getResources()
                                                    .getInteger(
                                                            R.integer
                                                                    .config_activity_embed_split_min_sw_dp))
                                    .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ALWAYS_ALLOW)
                                    .setDefaultSplitAttributes(
                                            builder.setSplitType(
                                                            SplitAttributes.SplitType.ratio(
                                                                    context.getResources()
                                                                            .getFloat(
                                                                                    R.dimen
                                                                                            .config_activity_embed_split_ratio)))
                                                    .setLayoutDirection(
                                                            SplitAttributes.LayoutDirection.LOCALE)
                                                    .build())
                                    .build());
            Trace.endSection();
        }
    }

    public static void registerTwoPanePairRuleForSettingsHome(
            Context context, ComponentName componentName, String str, boolean z, boolean z2) {
        if (SecTopLevelFeature.getInstance().getBoolean("feature_is_embedding_activity_enabled")
                || ActivityEmbeddingUtils.isEmbeddingActivityEnabled(context)) {
            Trace.beginSection(
                    "ActivityEmbeddingRulesController#1registerTwoPanePairRuleForSettingsHome");
            ComponentName componentName2 = new ComponentName(context, (Class<?>) Settings.class);
            SplitRule.FinishBehavior finishBehavior =
                    z ? SplitRule.FinishBehavior.ADJACENT : SplitRule.FinishBehavior.NEVER;
            SplitRule.FinishBehavior finishBehavior2 = SplitRule.FinishBehavior.ADJACENT;
            registerTwoPanePairRule(
                    context,
                    componentName2,
                    componentName,
                    str,
                    finishBehavior,
                    finishBehavior2,
                    z2);
            registerTwoPanePairRule(
                    context,
                    new ComponentName(context, (Class<?>) SettingsHomepageActivity.class),
                    componentName,
                    str,
                    z ? finishBehavior2 : SplitRule.FinishBehavior.NEVER,
                    finishBehavior2,
                    z2);
            ComponentName componentName3 =
                    new ComponentName(context, (Class<?>) DeepLinkHomepageActivity.class);
            SplitRule.FinishBehavior finishBehavior3 =
                    z ? SplitRule.FinishBehavior.ALWAYS : SplitRule.FinishBehavior.NEVER;
            SplitRule.FinishBehavior finishBehavior4 = SplitRule.FinishBehavior.ALWAYS;
            registerTwoPanePairRule(
                    context,
                    componentName3,
                    componentName,
                    str,
                    finishBehavior3,
                    finishBehavior4,
                    z2);
            registerTwoPanePairRule(
                    context,
                    new ComponentName(context, (Class<?>) DeepLinkHomepageActivityInternal.class),
                    componentName,
                    str,
                    z ? finishBehavior4 : SplitRule.FinishBehavior.NEVER,
                    finishBehavior4,
                    z2);
            Trace.endSection();
        }
    }

    public final void addActivityFilter(Collection collection, Class cls) {
        ((HashSet) collection)
                .add(
                        new ActivityFilter(
                                new ComponentName(this.mContext, (Class<?>) cls), (String) null));
    }

    public final void initRules() {
        if (!ActivityEmbeddingUtils.isEmbeddingActivityEnabled(this.mContext)) {
            Log.d("ActivityEmbeddingCtrl", "Not support this feature now");
            return;
        }
        Trace.beginSection("ActivityEmbeddingRulesController#initRules");
        RuleController ruleController = this.mRuleController;
        ruleController.clearRules();
        Trace.beginSection("ActivityEmbeddingRulesController#registerHomepagePlaceholderRule");
        HashSet hashSet = new HashSet();
        addActivityFilter(hashSet, SettingsHomepageActivity.class);
        addActivityFilter(hashSet, Settings.class);
        Intent intent =
                new Intent(this.mContext, (Class<?>) Settings.ConnectionsSettingsActivity.class);
        intent.putExtra(":settings:is_second_layer_page", true);
        ruleController.addRule(
                new SplitPlaceholderRule.Builder(hashSet, intent)
                        .setMinWidthDp(
                                this.mContext
                                        .getResources()
                                        .getInteger(
                                                R.integer.config_activity_embed_split_min_cur_dp))
                        .setMinSmallestWidthDp(
                                this.mContext
                                        .getResources()
                                        .getInteger(
                                                R.integer.config_activity_embed_split_min_sw_dp))
                        .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ALWAYS_ALLOW)
                        .setSticky(false)
                        .setFinishPrimaryWithPlaceholder(SplitRule.FinishBehavior.ADJACENT)
                        .setDefaultSplitAttributes(
                                new SplitAttributes.Builder()
                                        .setSplitType(
                                                SplitAttributes.SplitType.ratio(
                                                        this.mContext
                                                                .getResources()
                                                                .getFloat(
                                                                        R.dimen
                                                                                .config_activity_embed_split_ratio)))
                                        .build())
                        .build());
        Trace.endSection();
        Trace.beginSection("ActivityEmbeddingRulesController#registerAlwaysExpandRule");
        HashSet hashSet2 = new HashSet();
        addActivityFilter(hashSet2, IntelligenceServiceActivity.class);
        addActivityFilter(hashSet2, FingerprintEnrollmentActivity.class);
        addActivityFilter(hashSet2, FingerprintEnrollmentActivity.InternalActivity.class);
        addActivityFilter(hashSet2, FingerprintEnrollIntroduction.class);
        addActivityFilter(hashSet2, FingerprintEnrollIntroductionInternal.class);
        addActivityFilter(hashSet2, FingerprintEnrollEnrolling.class);
        addActivityFilter(hashSet2, FaceEnrollIntroductionInternal.class);
        addActivityFilter(hashSet2, FaceEnrollIntroduction.class);
        addActivityFilter(hashSet2, RemoteAuthActivity.class);
        addActivityFilter(hashSet2, RemoteAuthActivityInternal.class);
        addActivityFilter(hashSet2, ChooseLockPattern.class);
        addActivityFilter(hashSet2, PrivateSpaceSetupActivity.class);
        addActivityFilter(hashSet2, ChooseLockPattern.class);
        addActivityFilter(hashSet2, ChooseLockPassword.class);
        addActivityFilter(hashSet2, ConfirmLockPattern.class);
        addActivityFilter(hashSet2, ConfirmLockPassword.class);
        addActivityFilter(hashSet2, ChooseLockHintSettings.class);
        addActivityFilter(hashSet2, ConfirmLockPattern.InternalActivity.class);
        addActivityFilter(hashSet2, ConfirmLockPassword.InternalActivity.class);
        addActivityFilter(hashSet2, LockScreenRoutineActionActivity.class);
        addActivityFilter(
                hashSet2,
                ChooseLockGeneric.ChooseLockGenericFragment
                        .FactoryResetprotectionWarningDialogActivity.class);
        hashSet2.add(
                new ActivityFilter(
                        COMPONENT_NAME_WILDCARD,
                        new Intent(this.mContext.getString(R.string.config_avatar_picker_action))
                                .getAction()));
        addActivityFilter(hashSet2, BiometricsDisclaimerActivity.class);
        addActivityFilter(hashSet2, BiometricsDisclaimerWithConfirmLock.class);
        addActivityFilter(hashSet2, BiometricsRotationGuide.class);
        addActivityFilter(hashSet2, FaceLockSettings.class);
        addActivityFilter(hashSet2, FingerprintLockSettings.class);
        hashSet2.add(
                new ActivityFilter(
                        new ComponentName(
                                "com.samsung.android.biometrics.app.setting",
                                "com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity"),
                        (String) null));
        hashSet2.add(
                new ActivityFilter(
                        new ComponentName(
                                "com.samsung.android.biometrics.app.setting",
                                "com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity"),
                        (String) null));
        hashSet2.add(
                new ActivityFilter(
                        new ComponentName(
                                "com.sec.android.app.myfiles",
                                "com.sec.android.app.myfiles.ui.SettingDashBoardActivity"),
                        (String) null));
        ruleController.addRule(new ActivityRule.Builder(hashSet2).setAlwaysExpand(true).build());
        Trace.endSection();
        Trace.endSection();
    }

    public static void registerTwoPanePairRuleForSettingsHome(
            Context context, ComponentName componentName, String str, boolean z) {
        if (SecTopLevelFeature.getInstance().getBoolean("feature_is_embedding_activity_enabled")
                || ActivityEmbeddingUtils.isEmbeddingActivityEnabled(context)) {
            Trace.beginSection(
                    "ActivityEmbeddingRulesController#2registerTwoPanePairRuleForSettingsHome");
            registerTwoPanePairRuleForSettingsHome(context, componentName, str, true, z);
            Trace.endSection();
        }
    }
}
