package com.samsung.android.settings.eternal.policy.provider;

import com.samsung.android.knox.ddar.DualDARClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DefaultPolicy extends PolicyProvider {
    public static final String[] SIZE_LIMIT_ALLOW_LIST = {
        "/AOD/ClockType",
        "/AirCommand/RemoteSPen/AppDefinedRule",
        "/Accessibility/Default/NotificationReminderAppList",
        "/Routine/Item/RoutineItemList",
        "/HBD/TextShortcut",
        "/HBD/UseSuggestSticker",
        "/Settings/Advanced/VideoEnhancerApp",
        "/HBD/Emoji/SkinTone",
        "/Settings/Display/SwitchAppsToFrontScreen",
        "/Finder/Settings/ManageApps",
        "/DigitalWellbeing/CommonDb/FocusModeTargetApps",
        "/Accessibility/Default/AccessibilityButtonTargets",
        "/Accessibility/Default/AccessibilityDirectAccessTargetService",
        "/Accessibility/Default/AccessibilityShortcutTargetService",
        "/Settings/Advanced/LabsRotateAllApps",
        "/Settings/Advanced/LabsFlexModePanel",
        "/Settings/Advanced/LabsAppSplitView",
        "/Settings/Advanced/LabsAspectRatioApps",
        "/Settings/Advanced/LabsRotateApps",
        "/Accessibility/Default/ScreenFlashNotificationColorApps",
        "/HBD/SuggestTextCorrections/ManageApps",
        "/AOD/SubUiClockAttr",
        "/AOD/SubUiWidget"
    };
    public static final String[] RESTORE_RESTRICTED_LIST = {
        "/Settings/Advanced/LiftToWake",
        "/Settings/Display/ScreenResolution",
        "/Settings/Display/AutoBrightness",
        "/Settings/Advanced/FingerSensorGestures",
        "/Settings/Advanced/DoubleTapToWake",
        "/Settings/Display/BlockAccidentalTouches"
    };

    public DefaultPolicy() {
        initializePolicy();
    }

    @Override // com.samsung.android.settings.eternal.policy.provider.PolicyProvider
    public final String getPolicyId() {
        return "DefaultPolicy";
    }

    @Override // com.samsung.android.settings.eternal.policy.provider.PolicyProvider
    public final String getPolicyVersion() {
        return DualDARClient.DUAL_DAR_SDK_VERSION_1_0_0;
    }

    @Override // com.samsung.android.settings.eternal.policy.provider.PolicyProvider
    public final void initializePolicy() {
        this.mBackupSizeAllowedItems = new ArrayList();
        this.mRestoreRestrictedItems = new ArrayList();
        this.mDeferredRestorationItems = new ArrayList();
        final int i = 0;
        Arrays.stream(SIZE_LIMIT_ALLOW_LIST)
                .forEach(
                        new Consumer(
                                this) { // from class:
                                        // com.samsung.android.settings.eternal.policy.provider.DefaultPolicy$$ExternalSyntheticLambda0
                            public final /* synthetic */ DefaultPolicy f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i2 = i;
                                DefaultPolicy defaultPolicy = this.f$0;
                                String str = (String) obj;
                                switch (i2) {
                                    case 0:
                                        ((ArrayList) defaultPolicy.mBackupSizeAllowedItems)
                                                .add(new PolicyItem(str));
                                        break;
                                    default:
                                        ((ArrayList) defaultPolicy.mRestoreRestrictedItems)
                                                .add(new PolicyItem(str));
                                        break;
                                }
                            }
                        });
        final int i2 = 1;
        Arrays.stream(RESTORE_RESTRICTED_LIST)
                .forEach(
                        new Consumer(
                                this) { // from class:
                                        // com.samsung.android.settings.eternal.policy.provider.DefaultPolicy$$ExternalSyntheticLambda0
                            public final /* synthetic */ DefaultPolicy f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i22 = i2;
                                DefaultPolicy defaultPolicy = this.f$0;
                                String str = (String) obj;
                                switch (i22) {
                                    case 0:
                                        ((ArrayList) defaultPolicy.mBackupSizeAllowedItems)
                                                .add(new PolicyItem(str));
                                        break;
                                    default:
                                        ((ArrayList) defaultPolicy.mRestoreRestrictedItems)
                                                .add(new PolicyItem(str));
                                        break;
                                }
                            }
                        });
    }

    @Override // com.samsung.android.settings.eternal.policy.provider.PolicyProvider
    public final boolean isValidProvider() {
        return (((ArrayList) this.mBackupSizeAllowedItems).isEmpty()
                        || ((ArrayList) this.mRestoreRestrictedItems).isEmpty())
                ? false
                : true;
    }
}
