package com.android.settingslib.spaprivileged.template.common;

import android.content.Context;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.widget.scaffold.SettingsPagerKt;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.enterprise.EnterpriseRepository;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class UserProfilePagerKt {
    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.settingslib.spaprivileged.template.common.UserProfilePagerKt$UserProfilePager$1, kotlin.jvm.internal.Lambda] */
    public static final void UserProfilePager(
            final Function3 content, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1344117575);
        if ((((i & 14) == 0 ? (composerImpl.changedInstance(content) ? 4 : 2) | i : i) & 11) == 2
                && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(1921479244);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                UserManager userManager = ContextsKt.getUserManager(context);
                ArrayList arrayList = new ArrayList();
                List profiles = userManager.getProfiles(UserHandle.myUserId());
                Intrinsics.checkNotNullExpressionValue(profiles, "getProfiles(...)");
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Object obj : profiles) {
                    UserInfo userInfo = (UserInfo) obj;
                    Intrinsics.checkNotNull(userInfo);
                    UserProperties userProperties =
                            userManager.getUserProperties(userInfo.getUserHandle());
                    Intrinsics.checkNotNullExpressionValue(
                            userProperties, "getUserProperties(...)");
                    Integer valueOf =
                            Integer.valueOf(
                                    (userInfo.isQuietModeEnabled()
                                                    && userProperties.getShowInQuietMode() == 1)
                                            ? 2
                                            : userProperties.getShowInSettings());
                    Object obj2 = linkedHashMap.get(valueOf);
                    if (obj2 == null) {
                        obj2 = new ArrayList();
                        linkedHashMap.put(valueOf, obj2);
                    }
                    ((List) obj2).add(obj);
                }
                List list = (List) linkedHashMap.get(0);
                if (list != null) {
                    arrayList.add(new UserGroup(list));
                }
                List list2 = (List) linkedHashMap.get(1);
                if (list2 != null) {
                    Iterator it = list2.iterator();
                    while (it.hasNext()) {
                        arrayList.add(
                                new UserGroup(
                                        CollectionsKt__CollectionsJVMKt.listOf(
                                                (UserInfo) it.next())));
                    }
                }
                composerImpl.updateRememberedValue(arrayList);
                rememberedValue = arrayList;
            }
            final List list3 = (List) rememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1921479310);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                EnterpriseRepository enterpriseRepository = new EnterpriseRepository(context);
                List list4 = list3;
                ArrayList arrayList2 =
                        new ArrayList(
                                CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                Iterator it2 = list4.iterator();
                while (it2.hasNext()) {
                    UserInfo userInfo2 =
                            (UserInfo)
                                    CollectionsKt___CollectionsKt.first(
                                            ((UserGroup) it2.next()).userInfos);
                    Intrinsics.checkNotNullParameter(userInfo2, "userInfo");
                    arrayList2.add(
                            userInfo2.isManagedProfile()
                                    ? enterpriseRepository.getEnterpriseString(
                                            R.string.category_work, "Settings.WORK_CATEGORY_HEADER")
                                    : userInfo2.isPrivateProfile()
                                            ? enterpriseRepository.getEnterpriseString(
                                                    R.string.category_private,
                                                    "Settings.PRIVATE_CATEGORY_HEADER")
                                            : enterpriseRepository.getEnterpriseString(
                                                    R.string.category_personal,
                                                    "Settings.PERSONAL_CATEGORY_HEADER"));
                }
                composerImpl.updateRememberedValue(arrayList2);
                rememberedValue2 = arrayList2;
            }
            composerImpl.end(false);
            SettingsPagerKt.SettingsPager(
                    (List) rememberedValue2,
                    ComposableLambdaKt.rememberComposableLambda(
                            1807524890,
                            new Function3() { // from class:
                                              // com.android.settingslib.spaprivileged.template.common.UserProfilePagerKt$UserProfilePager$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                    int intValue = ((Number) obj3).intValue();
                                    Composer composer2 = (Composer) obj4;
                                    int intValue2 = ((Number) obj5).intValue();
                                    if ((intValue2 & 14) == 0) {
                                        intValue2 |=
                                                ((ComposerImpl) composer2).changed(intValue)
                                                        ? 4
                                                        : 2;
                                    }
                                    if ((intValue2 & 91) == 18) {
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.getSkipping()) {
                                            composerImpl2.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    Function3.this.invoke(list3.get(intValue), composer2, 8);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    56);
            OpaqueKey opaqueKey2 = ComposerKt.invocation;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.common.UserProfilePagerKt$UserProfilePager$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj3, Object obj4) {
                            ((Number) obj4).intValue();
                            UserProfilePagerKt.UserProfilePager(
                                    Function3.this,
                                    (Composer) obj3,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
