package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.settings.R;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.goodsettings.GoodSettingsTopLevelPreferenceParser;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeySettingsProvider;", "Landroid/content/ContentProvider;", "<init>", "()V", "applications__sources__apps__SecSettings__android_common__SecSettings-core"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes3.dex */
public final class FunctionKeySettingsProvider extends ContentProvider {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v9 */
    @Override // android.content.ContentProvider
    public final Bundle call(String method, String str, Bundle bundle) {
        FunctionKeyAction functionKeyAction;
        ?? r1;
        FunctionKeyAction functionKeyAction2;
        FunctionKeyInfo$FunctionKeyAppInfo functionKeyInfo$FunctionKeyAppInfo;
        String str2;
        Intrinsics.checkNotNullParameter(method, "method");
        Bundle bundle2 = new Bundle();
        if (!TextUtils.equals("sidebutton/getInfo", method)) {
            throw new IllegalArgumentException("Unknown method : ".concat(method));
        }
        if (bundle != null) {
            String string = bundle.getString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
            if (TextUtils.equals("key_app", string)) {
                Context context = getContext();
                if (context != null) {
                    String string2 = Settings.Global.getString(context.getContentResolver(), "function_key_config_doublepress_app_action");
                    new Intent();
                    Intrinsics.checkNotNull(string2);
                    Intent makeSideKeyCustomizationInfoIntent = UsefulfeatureUtils.makeSideKeyCustomizationInfoIntent(string2);
                    Intrinsics.checkNotNullExpressionValue(makeSideKeyCustomizationInfoIntent, "makeSideKeyCustomizationInfoIntent(...)");
                    String string3 = Settings.Global.getString(context.getContentResolver(), "function_key_config_doublepress_app_action");
                    Log.d("FunctionKeyInfo", "Press Type : 2 / app : " + string3);
                    if (TextUtils.isEmpty(string3)) {
                        functionKeyInfo$FunctionKeyAppInfo = null;
                    } else {
                        int indexOf = string3.indexOf(47);
                        String substring = string3.substring(0, indexOf);
                        String substring2 = string3.substring(indexOf + 1);
                        ComponentName componentName = new ComponentName(substring, substring2);
                        functionKeyInfo$FunctionKeyAppInfo = new FunctionKeyInfo$FunctionKeyAppInfo();
                        functionKeyInfo$FunctionKeyAppInfo.mLabel = null;
                        functionKeyInfo$FunctionKeyAppInfo.mState = 0;
                        functionKeyInfo$FunctionKeyAppInfo.mDB = string3;
                        try {
                            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(substring, PackageManager.ApplicationInfoFlags.of(4294967808L));
                            if (!UsefulfeatureUtils.isEnabledPackage(context, substring)) {
                                Log.d("FunctionKeyInfo", substring + " is disabled.");
                                functionKeyInfo$FunctionKeyAppInfo.mState = 1;
                                functionKeyInfo$FunctionKeyAppInfo.mLabel = applicationInfo.loadLabel(context.getPackageManager());
                            } else if (applicationInfo.isArchived) {
                                Log.d("FunctionKeyInfo", substring + " is archived.");
                                functionKeyInfo$FunctionKeyAppInfo.mState = 3;
                            } else {
                                try {
                                    ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(componentName, 512);
                                    if (!UsefulfeatureUtils.isEnabledComponent(context, componentName)) {
                                        Log.d("FunctionKeyInfo", substring2 + " is disabled.");
                                        functionKeyInfo$FunctionKeyAppInfo.mState = 1;
                                    }
                                    functionKeyInfo$FunctionKeyAppInfo.mLabel = activityInfo.loadLabel(context.getPackageManager());
                                } catch (PackageManager.NameNotFoundException unused) {
                                    Log.d("FunctionKeyInfo", substring2 + " is not installed.");
                                    functionKeyInfo$FunctionKeyAppInfo.mState = 2;
                                }
                            }
                        } catch (PackageManager.NameNotFoundException unused2) {
                            Log.d("FunctionKeyInfo", substring + " is not installed.");
                            functionKeyInfo$FunctionKeyAppInfo.mState = 2;
                        }
                    }
                    if ((functionKeyInfo$FunctionKeyAppInfo != null ? functionKeyInfo$FunctionKeyAppInfo.mDB : null) == null || ApnSettings.MVNO_NONE.equals(functionKeyInfo$FunctionKeyAppInfo.mDB)) {
                        str2 = ApnSettings.MVNO_NONE;
                    } else {
                        int i = functionKeyInfo$FunctionKeyAppInfo.mState;
                        String obj = i != 1 ? i != 2 ? i != 3 ? functionKeyInfo$FunctionKeyAppInfo.mLabel.toString() : context.getString(R.string.lock_app_shortcut_archived_app) : context.getString(R.string.lock_app_shortcut_deleted_app) : context.getString(R.string.lock_app_shortcut_disabled_app, functionKeyInfo$FunctionKeyAppInfo.mLabel);
                        Intrinsics.checkNotNull(obj);
                        str2 = obj;
                    }
                    functionKeyAction2 = new FunctionKeyAction("key_app", 2, 0, null, str2, null, 0, 2, string2, 1, makeSideKeyCustomizationInfoIntent);
                    r1 = 1;
                } else {
                    r1 = 1;
                    functionKeyAction2 = null;
                }
                bundle2.putBoolean(GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_AVAILABLE, r1);
                bundle2.putString(UniversalCredentialUtil.AGENT_TITLE, functionKeyAction2 != null ? functionKeyAction2.title : null);
                bundle2.putInt("intentType", r1);
                bundle2.putString("intentUri", UsefulfeatureUtils.makeSideKeyCustomizationInfoIntent(functionKeyAction2 != null ? functionKeyAction2.actionValue : null).toUri(r1));
            } else if (TextUtils.equals("key_quick_launch_camera", string)) {
                Context context2 = getContext();
                if (context2 != null) {
                    new Intent();
                    Intent makeSideKeyCustomizationInfoIntent2 = UsefulfeatureUtils.makeSideKeyCustomizationInfoIntent("com.sec.android.app.camera/com.sec.android.app.camera.Camera");
                    Intrinsics.checkNotNullExpressionValue(makeSideKeyCustomizationInfoIntent2, "makeSideKeyCustomizationInfoIntent(...)");
                    String string4 = context2.getString(R.string.sec_quick_launch_camera);
                    Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                    functionKeyAction = new FunctionKeyAction("key_quick_launch_camera", 2, 0, null, string4, null, 0, 0, "com.sec.android.app.camera/com.sec.android.app.camera.Camera", 1, makeSideKeyCustomizationInfoIntent2);
                } else {
                    functionKeyAction = null;
                }
                bundle2.putBoolean(GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_AVAILABLE, true);
                bundle2.putString(UniversalCredentialUtil.AGENT_TITLE, functionKeyAction != null ? functionKeyAction.title : null);
                bundle2.putInt("intentType", 1);
                bundle2.putString("intentUri", UsefulfeatureUtils.makeSideKeyCustomizationInfoIntent(functionKeyAction != null ? functionKeyAction.actionValue : null).toUri(1));
            }
        }
        return bundle2;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return false;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }
}
