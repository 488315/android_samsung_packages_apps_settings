package com.samsung.android.settings.accessibility.exclusive;

import android.content.Context;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;

import com.samsung.android.settings.accessibility.exclusive.info.ActionAfterPointerStopsTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.AssistantMenuTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.BlueLightFilterTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.BounceKeysTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.ClickAfterPointerStopsTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.ColorAdjustmentTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.ColorFilterTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.CornerActionTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.DirectAccessTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.DirectCallTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.EasyMuteTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.EasyScreenTurnOnTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.EdgeFeedTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.IgnoreRepeatedTouchesTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.InteractionControlTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.MagnificationGestureTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.MagnificationShortcutTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.MuteAllSoundTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.OneHandedModeTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.PalmMotionTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.PinWindowTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.QuickLaunchCameraTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.RemoveAnimationsTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.SlowKeysTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.SmartAlertTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.StickyKeysTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.TalkBackSETaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.TalkBackTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.TapDurationTaskInfo;
import com.samsung.android.settings.accessibility.exclusive.info.UniversalSwitchTaskInfo;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityExclusiveUtil {
    public static final Set INFO_SET =
            Set.of(
                    (Object[])
                            new ExclusiveTaskInfo[] {
                                new MuteAllSoundTaskInfo(),
                                new TalkBackSETaskInfo(),
                                new TalkBackTaskInfo(),
                                new DirectCallTaskInfo(),
                                new EasyMuteTaskInfo(),
                                new ColorAdjustmentTaskInfo(),
                                new ClickAfterPointerStopsTaskInfo(),
                                new ActionAfterPointerStopsTaskInfo(),
                                new ColorFilterTaskInfo(),
                                new MagnificationGestureTaskInfo(),
                                new MagnificationShortcutTaskInfo(),
                                new RemoveAnimationsTaskInfo(),
                                new UniversalSwitchTaskInfo(),
                                new AssistantMenuTaskInfo(),
                                new InteractionControlTaskInfo(),
                                new EdgeFeedTaskInfo(),
                                new TapDurationTaskInfo(),
                                new IgnoreRepeatedTouchesTaskInfo(),
                                new BlueLightFilterTaskInfo(),
                                new StickyKeysTaskInfo(),
                                new SlowKeysTaskInfo(),
                                new BounceKeysTaskInfo(),
                                new OneHandedModeTaskInfo(),
                                new SmartAlertTaskInfo(),
                                new PalmMotionTaskInfo(),
                                new CornerActionTaskInfo(),
                                new PinWindowTaskInfo(),
                                new QuickLaunchCameraTaskInfo(),
                                new EasyScreenTurnOnTaskInfo(),
                                new DirectAccessTaskInfo()
                            });

    public static List getExclusiveTaskInfoList(final Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return List.of();
        }
        final List semGetExclusiveTaskList =
                ((AccessibilityManager) context.getSystemService("accessibility"))
                        .semGetExclusiveTaskList(context, str);
        return (semGetExclusiveTaskList == null || semGetExclusiveTaskList.isEmpty())
                ? List.of()
                : INFO_SET.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        ExclusiveTaskInfo exclusiveTaskInfo =
                                                (ExclusiveTaskInfo) obj;
                                        return semGetExclusiveTaskList.contains(
                                                        exclusiveTaskInfo.getTaskName())
                                                && exclusiveTaskInfo.isAvailable(context);
                                    }
                                })
                        .toList();
    }

    public static boolean isExclusiveTaskEnabled(Context context, String str) {
        return !getExclusiveTaskInfoList(context, str).isEmpty();
    }
}
