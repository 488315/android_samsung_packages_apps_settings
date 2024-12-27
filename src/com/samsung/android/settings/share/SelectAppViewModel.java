package com.samsung.android.settings.share;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoDataImpl;

import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.share.common.ResolveInfoTargetPresentationGetter;
import com.samsung.android.settings.share.common.ShareConstants;
import com.samsung.android.settings.share.structure.ShareComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SelectAppViewModel extends ViewModel {
    public static ShareComponent quickShareShareComponent;
    public Pair mInitialListPair;
    public boolean mIsAllChecked;
    public final ArrayList mAppItemPackageName = new ArrayList();
    public final ArrayList mAppItems = new ArrayList();
    public ArrayList mOtherComponentList = new ArrayList();
    public ArrayList mFavoriteComponentList = new ArrayList();
    public final Map mSharedComponentMap = new HashMap();
    public final List mSourcesIntentList = new ArrayList();
    public final MutableLiveData modifiedLiveData = new MutableLiveData(Boolean.FALSE);

    public final AppInfoDataImpl makeAppData(ShareComponent shareComponent, Context context) {
        Bitmap bitmap;
        Optional of;
        String charSequence;
        String packageName = shareComponent.mComponentName.getPackageName();
        String className = shareComponent.mComponentName.getClassName();
        int i = shareComponent.mUserId;
        AppInfo.Companion companion = AppInfo.Companion;
        AppInfo obtain = AppInfo.Companion.obtain(i, packageName, className);
        AppInfoDataImpl appInfoDataImpl = new AppInfoDataImpl(obtain, 2);
        List list = this.mSourcesIntentList;
        Drawable drawable = null;
        if (shareComponent.mCachedInfo == null) {
            ResolveInfoTargetPresentationGetter makePresentationGetter =
                    ResolveInfoTargetPresentationGetter.makePresentationGetter(
                            shareComponent.mUserId, shareComponent.mComponentName, context, list);
            if (makePresentationGetter != null) {
                if (makePresentationGetter.mHasSubstitutePermission) {
                    shareComponent.mCachedInfo =
                            new ShareComponent.CachedInfo(makePresentationGetter, null);
                } else {
                    shareComponent.mCachedInfo =
                            new ShareComponent.CachedInfo(
                                    makePresentationGetter, shareComponent.mLabelPair);
                }
                ShareComponent.CachedInfo cachedInfo = shareComponent.mCachedInfo;
                if (cachedInfo.mLabelPair == null) {
                    ResolveInfoTargetPresentationGetter resolveInfoTargetPresentationGetter =
                            cachedInfo.mTargetPresentationGetter;
                    String label = resolveInfoTargetPresentationGetter.getLabel();
                    if (resolveInfoTargetPresentationGetter.mHasSubstitutePermission) {
                        charSequence =
                                resolveInfoTargetPresentationGetter
                                        .mRi
                                        .loadLabel(resolveInfoTargetPresentationGetter.mPm)
                                        .toString();
                        if (TextUtils.isEmpty(charSequence)
                                || TextUtils.equals(
                                        charSequence,
                                        resolveInfoTargetPresentationGetter.getLabel())) {
                            charSequence = null;
                        }
                    } else {
                        charSequence =
                                resolveInfoTargetPresentationGetter
                                        .mRi
                                        .loadLabel(resolveInfoTargetPresentationGetter.mPm)
                                        .toString();
                    }
                    cachedInfo.mLabelPair =
                            label.equals(charSequence)
                                    ? new Pair(label, ApnSettings.MVNO_NONE)
                                    : new Pair(label, charSequence);
                }
            }
        }
        Pair pair = shareComponent.mLabelPair;
        if (pair != null) {
            appInfoDataImpl.label = (String) pair.first;
            appInfoDataImpl.subLabel = (String) pair.second;
        }
        List list2 = this.mSourcesIntentList;
        if (shareComponent.mCachedInfo == null) {
            ResolveInfoTargetPresentationGetter makePresentationGetter2 =
                    ResolveInfoTargetPresentationGetter.makePresentationGetter(
                            shareComponent.mUserId, shareComponent.mComponentName, context, list2);
            if (makePresentationGetter2 != null) {
                if (makePresentationGetter2.mHasSubstitutePermission) {
                    shareComponent.mCachedInfo =
                            new ShareComponent.CachedInfo(makePresentationGetter2, null);
                } else {
                    shareComponent.mCachedInfo =
                            new ShareComponent.CachedInfo(
                                    makePresentationGetter2, shareComponent.mLabelPair);
                }
                ShareComponent.CachedInfo cachedInfo2 = shareComponent.mCachedInfo;
                Drawable drawable2 = cachedInfo2.mIconDrawable;
                if (drawable2 != null) {
                    of = Optional.of(drawable2);
                } else {
                    ResolveInfoTargetPresentationGetter resolveInfoTargetPresentationGetter2 =
                            cachedInfo2.mTargetPresentationGetter;
                    if (resolveInfoTargetPresentationGetter2 == null) {
                        of = Optional.empty();
                    } else {
                        Resources resources =
                                resolveInfoTargetPresentationGetter2.mCtx.getResources();
                        if (resolveInfoTargetPresentationGetter2.mHasSubstitutePermission) {
                            try {
                                drawable =
                                        resolveInfoTargetPresentationGetter2.loadIconFromResource(
                                                resolveInfoTargetPresentationGetter2.mPm
                                                        .getResourcesForApplication(
                                                                resolveInfoTargetPresentationGetter2
                                                                        .mRi
                                                                        .resolvePackageName),
                                                resolveInfoTargetPresentationGetter2.mRi.icon);
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.e(
                                        "TargetPresentationGetter",
                                        "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission"
                                            + " granted but couldn't find resources for package",
                                        e);
                                try {
                                    ActivityInfo activityInfo =
                                            resolveInfoTargetPresentationGetter2.mRi.activityInfo;
                                    if (activityInfo.icon != 0) {
                                        drawable =
                                                resolveInfoTargetPresentationGetter2
                                                        .loadIconFromResource(
                                                                resolveInfoTargetPresentationGetter2
                                                                        .mPm
                                                                        .getResourcesForApplication(
                                                                                activityInfo
                                                                                        .applicationInfo),
                                                                activityInfo.icon);
                                    }
                                } catch (PackageManager.NameNotFoundException e2) {
                                    Log.e(
                                            "TargetPresentationGetter",
                                            "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission"
                                                + " granted but couldn't find resources for"
                                                + " package",
                                            e2);
                                }
                            }
                        }
                        if (Settings.System.getString(
                                        resolveInfoTargetPresentationGetter2.mCtx
                                                .getContentResolver(),
                                        "current_sec_appicon_theme_package")
                                == null) {
                            if (drawable == null) {
                                try {
                                    ApplicationInfo applicationInfo =
                                            resolveInfoTargetPresentationGetter2.mAi;
                                    if (applicationInfo.icon != 0) {
                                        drawable =
                                                resolveInfoTargetPresentationGetter2
                                                        .loadIconFromResource(
                                                                resolveInfoTargetPresentationGetter2
                                                                        .mPm
                                                                        .getResourcesForApplication(
                                                                                applicationInfo),
                                                                resolveInfoTargetPresentationGetter2
                                                                        .mAi
                                                                        .icon);
                                    }
                                } catch (PackageManager.NameNotFoundException e3) {
                                    Log.e("TargetPresentationGetter", e3.toString());
                                }
                            }
                            if (drawable == null) {
                                Utils$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("last icon load = "),
                                        resolveInfoTargetPresentationGetter2.mAi.packageName,
                                        "TargetPresentationGetter");
                                drawable =
                                        resolveInfoTargetPresentationGetter2.mAi.loadIcon(
                                                resolveInfoTargetPresentationGetter2.mPm);
                            }
                            if (drawable != null
                                    && resolveInfoTargetPresentationGetter2.mPm
                                            .semShouldPackIntoIconTray(
                                                    resolveInfoTargetPresentationGetter2
                                                            .mAi
                                                            .packageName)) {
                                drawable =
                                        resolveInfoTargetPresentationGetter2.mPm
                                                .semGetDrawableForIconTray(drawable, 48);
                            }
                        } else if (drawable == null) {
                            drawable =
                                    resolveInfoTargetPresentationGetter2.mAi.loadUnbadgedIcon(
                                            resolveInfoTargetPresentationGetter2.mPm);
                        } else if (resolveInfoTargetPresentationGetter2.mPm
                                .semShouldPackIntoIconTray(
                                        resolveInfoTargetPresentationGetter2.mAi.packageName)) {
                            drawable =
                                    resolveInfoTargetPresentationGetter2.mPm
                                            .semGetDrawableForIconTray(drawable, 1);
                        }
                        Drawable userBadgedIcon =
                                resolveInfoTargetPresentationGetter2.mPm.getUserBadgedIcon(
                                        drawable,
                                        ShareConstants.ICON_CLASS_FOR_INTENT_FORWARD_TO_PARENT
                                                        .equals(
                                                                resolveInfoTargetPresentationGetter2
                                                                        .mRi
                                                                        .activityInfo
                                                                        .name)
                                                ? UserHandle.getUserHandleForUid(0)
                                                : UserHandle.getUserHandleForUid(
                                                        resolveInfoTargetPresentationGetter2
                                                                .mAi
                                                                .uid));
                        if (userBadgedIcon instanceof BitmapDrawable) {
                            bitmap = ((BitmapDrawable) userBadgedIcon).getBitmap();
                        } else {
                            Bitmap createBitmap =
                                    Bitmap.createBitmap(
                                            userBadgedIcon.getIntrinsicWidth(),
                                            userBadgedIcon.getIntrinsicHeight(),
                                            Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(createBitmap);
                            userBadgedIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                            userBadgedIcon.draw(canvas);
                            bitmap = createBitmap;
                        }
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);
                        cachedInfo2.mIconDrawable = bitmapDrawable;
                        of = Optional.of(bitmapDrawable);
                    }
                }
                drawable = (Drawable) of.get();
            }
        }
        if (drawable != null) {
            appInfoDataImpl.icon = drawable;
        }
        ((HashMap) this.mSharedComponentMap).put(obtain, shareComponent);
        this.mAppItemPackageName.add(shareComponent.mComponentName.getClassName());
        return appInfoDataImpl;
    }
}
