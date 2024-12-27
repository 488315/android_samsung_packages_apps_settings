package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.goodsettings.GoodSettingsTopLevelPreferenceParser;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyAction;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyApplication;

import kotlin.jvm.internal.Intrinsics;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class FunctionKeyUtils {
    public static FunctionKeyAction getFunctionKeyAction(
            Context context, FunctionKeyApplication functionKeyApplication) {
        String string;
        Map map;
        if (functionKeyApplication != null
                && functionKeyApplication.pressType == 2
                && (string =
                                Settings.Global.getString(
                                        context.getContentResolver(),
                                        "function_key_config_doublepress_selected_actions"))
                        != null
                && (map =
                                (Map)
                                        new Gson()
                                                .fromJson(
                                                        string,
                                                        new TypeToken<
                                                                Map<
                                                                        String,
                                                                        String>>() { // from class:
                                                                                     // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyUtils.1
                                                        }.getType()))
                        != null) {
            ArrayList loadDynamicFunctionKeyActions =
                    loadDynamicFunctionKeyActions(
                            context,
                            (ProviderInfo) functionKeyApplication.componentInfo,
                            new ArrayList(
                                    Collections.singletonList(
                                            (String) map.get(functionKeyApplication.key))));
            if (loadDynamicFunctionKeyActions.size() == 1) {
                return (FunctionKeyAction) loadDynamicFunctionKeyActions.get(0);
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x045c  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x04a8  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x04fa  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0511  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x04d6  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x042d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0558  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0416  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x044e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List getFunctionKeyItems(
            android.content.Context r30, int r31, final java.lang.String r32) {
        /*
            Method dump skipped, instructions count: 1565
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyUtils.getFunctionKeyItems(android.content.Context,"
                    + " int, java.lang.String):java.util.List");
    }

    public static ArrayList loadDynamicFunctionKeyActions(
            Context context, ProviderInfo providerInfo, ArrayList arrayList) {
        ContentProviderClient acquireUnstableContentProviderClient;
        Intent actionIntent;
        int i;
        int i2;
        ArrayList arrayList2 = new ArrayList();
        try {
            acquireUnstableContentProviderClient =
                    context.getContentResolver()
                            .acquireUnstableContentProviderClient(
                                    new Uri.Builder()
                                            .scheme("content")
                                            .authority(providerInfo.authority)
                                            .build());
            try {
            } finally {
            }
        } catch (RemoteException | RuntimeException e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "can not call the provider : ", e, "FunctionKeyUtils");
        }
        if (acquireUnstableContentProviderClient == null) {
            Log.e("FunctionKeyUtils", "provider is null.");
            if (acquireUnstableContentProviderClient != null) {
                acquireUnstableContentProviderClient.close();
            }
            return arrayList2;
        }
        Log.i("FunctionKeyUtils", "provider acquired successfully.");
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Bundle bundle = new Bundle();
            bundle.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, key);
            Intent intent = null;
            Bundle call =
                    acquireUnstableContentProviderClient.call("sidebutton/getInfo", null, bundle);
            if (call != null) {
                boolean z =
                        call.getBoolean(
                                GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_AVAILABLE,
                                true);
                String title = call.getString(UniversalCredentialUtil.AGENT_TITLE);
                if (TextUtils.isEmpty(title)) {
                    Log.d("FunctionKeyUtils", "action title is null. : " + key);
                    acquireUnstableContentProviderClient.close();
                    return arrayList2;
                }
                int i3 = call.getInt("intentType");
                String string = call.getString("intentUri");
                Log.d(
                        "FunctionKeyUtils",
                        "isAvailable : "
                                + z
                                + ", title : "
                                + title
                                + ", intentType : "
                                + i3
                                + ", intentUri : "
                                + string);
                if (!TextUtils.isEmpty(string)) {
                    try {
                        intent = Intent.parseUri(string, 1);
                    } catch (URISyntaxException e2) {
                        throw new RuntimeException(e2);
                    }
                }
                if (intent != null && intent.getComponent() != null) {
                    String actionValue = intent.getComponent().flattenToString();
                    if (TextUtils.equals(key, "key_quick_launch_camera")) {
                        actionValue =
                                "com.sec.android.app.camera/com.sec.android.app.camera.Camera";
                        actionIntent =
                                UsefulfeatureUtils.makeSideKeyCustomizationInfoIntent(
                                        "com.sec.android.app.camera/com.sec.android.app.camera.Camera");
                        i = 0;
                    } else if (TextUtils.equals(key, "key_app")
                            && "com.samsung.android.bixby.agent/com.samsung.android.bixby.assistanthome.AssistantHomeLauncherActivity"
                                    .equalsIgnoreCase(actionValue)) {
                        actionValue = "wakeBixby_openApps/wakeBixby_openApps";
                        actionIntent =
                                UsefulfeatureUtils.makeSideKeyCustomizationInfoIntent(
                                        "wakeBixby_openApps/wakeBixby_openApps");
                        i2 = 3;
                        i = 2;
                        new Intent();
                        Intrinsics.checkNotNullParameter(actionValue, "actionValue");
                        Intrinsics.checkNotNullParameter(actionIntent, "actionIntent");
                        Intrinsics.checkNotNullParameter(title, "title");
                        Intrinsics.checkNotNullParameter(key, "key");
                        arrayList2.add(
                                new FunctionKeyAction(
                                        key,
                                        2,
                                        0,
                                        null,
                                        title,
                                        null,
                                        0,
                                        i,
                                        actionValue,
                                        i2,
                                        actionIntent));
                    } else {
                        actionIntent = intent;
                        i = 2;
                    }
                    i2 = i3;
                    new Intent();
                    Intrinsics.checkNotNullParameter(actionValue, "actionValue");
                    Intrinsics.checkNotNullParameter(actionIntent, "actionIntent");
                    Intrinsics.checkNotNullParameter(title, "title");
                    Intrinsics.checkNotNullParameter(key, "key");
                    arrayList2.add(
                            new FunctionKeyAction(
                                    key,
                                    2,
                                    0,
                                    null,
                                    title,
                                    null,
                                    0,
                                    i,
                                    actionValue,
                                    i2,
                                    actionIntent));
                }
                Log.d(
                        "FunctionKeyUtils",
                        "action intent is null or intent component is null. : " + key);
                acquireUnstableContentProviderClient.close();
                return arrayList2;
            }
            Log.e("FunctionKeyUtils", "get_info bundle is null");
        }
        acquireUnstableContentProviderClient.close();
        return arrayList2;
    }
}
