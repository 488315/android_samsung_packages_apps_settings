package com.android.settingslib.drawer;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;

import androidx.picker.features.search.InitialSearchUtils$$ExternalSyntheticOutline0;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TileUtils {
    static final String SETTING_PKG = "com.android.settings";

    public static Uri buildUri(String str, String str2, String str3) {
        return InitialSearchUtils$$ExternalSyntheticOutline0.m("content", str, str2, str3);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x006a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.os.Bundle getBundleFromUri(
            android.content.Context r9,
            android.net.Uri r10,
            java.util.Map r11,
            android.os.Bundle r12) {
        /*
            r0 = 0
            if (r10 != 0) goto L5
        L3:
            r1 = r0
            goto L2c
        L5:
            java.util.List r1 = r10.getPathSegments()
            if (r1 == 0) goto L3
            boolean r2 = r1.isEmpty()
            if (r2 == 0) goto L12
            goto L3
        L12:
            r2 = 0
            java.lang.Object r2 = r1.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            int r3 = r1.size()
            r4 = 1
            if (r3 <= r4) goto L27
            java.lang.Object r1 = r1.get(r4)
            java.lang.String r1 = (java.lang.String) r1
            goto L28
        L27:
            r1 = r0
        L28:
            android.util.Pair r1 = android.util.Pair.create(r2, r1)
        L2c:
            if (r1 != 0) goto L2f
            return r0
        L2f:
            java.lang.Object r2 = r1.first
            r6 = r2
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r1 = r1.second
            java.lang.String r1 = (java.lang.String) r1
            boolean r2 = android.text.TextUtils.isEmpty(r6)
            if (r2 == 0) goto L3f
            return r0
        L3f:
            if (r10 != 0) goto L43
        L41:
            r3 = r0
            goto L68
        L43:
            java.lang.String r2 = r10.getAuthority()
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 == 0) goto L4e
            goto L41
        L4e:
            android.util.ArrayMap r11 = (android.util.ArrayMap) r11
            boolean r3 = r11.containsKey(r2)
            if (r3 != 0) goto L61
            android.content.ContentResolver r3 = r9.getContentResolver()
            android.content.IContentProvider r3 = r3.acquireUnstableProvider(r10)
            r11.put(r2, r3)
        L61:
            java.lang.Object r11 = r11.get(r2)
            android.content.IContentProvider r11 = (android.content.IContentProvider) r11
            r3 = r11
        L68:
            if (r3 != 0) goto L6b
            return r0
        L6b:
            boolean r11 = android.text.TextUtils.isEmpty(r1)
            if (r11 != 0) goto L7d
            if (r12 != 0) goto L78
            android.os.Bundle r12 = new android.os.Bundle
            r12.<init>()
        L78:
            java.lang.String r11 = "com.android.settings.keyhint"
            r12.putString(r11, r1)
        L7d:
            r8 = r12
            android.content.AttributionSource r4 = r9.getAttributionSource()     // Catch: android.os.RemoteException -> L8f
            java.lang.String r5 = r10.getAuthority()     // Catch: android.os.RemoteException -> L8f
            java.lang.String r7 = r10.toString()     // Catch: android.os.RemoteException -> L8f
            android.os.Bundle r9 = r3.call(r4, r5, r6, r7, r8)     // Catch: android.os.RemoteException -> L8f
            return r9
        L8f:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.drawer.TileUtils.getBundleFromUri(android.content.Context,"
                    + " android.net.Uri, java.util.Map, android.os.Bundle):android.os.Bundle");
    }

    public static Uri getCompleteUri(Tile tile, String str, String str2) {
        String string = tile.mMetaData.getString(str);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        Uri parse = Uri.parse(string);
        List<String> pathSegments = parse.getPathSegments();
        if (pathSegments != null && !pathSegments.isEmpty()) {
            return parse;
        }
        String string2 = tile.mMetaData.getString("com.android.settings.keyhint");
        if (!TextUtils.isEmpty(string2)) {
            return buildUri(parse.getAuthority(), str2, string2);
        }
        Log.w(
                "TileUtils",
                "Please specify the meta-data com.android.settings.keyhint in AndroidManifest.xml"
                    + " for "
                        + string);
        return buildUri(parse.getAuthority(), str2);
    }

    public static Pair getIconFromUri(Context context, String str, Uri uri, Map map) {
        int i;
        Bundle bundleFromUri = getBundleFromUri(context, uri, map, null);
        if (bundleFromUri == null) {
            return null;
        }
        String string = bundleFromUri.getString("com.android.settings.icon_package");
        if (TextUtils.isEmpty(string)
                || (i = bundleFromUri.getInt("com.android.settings.icon", 0)) == 0) {
            return null;
        }
        if (string.equals(str) || string.equals(context.getPackageName())) {
            return Pair.create(string, Integer.valueOf(i));
        }
        return null;
    }

    public static void loadTile(
            UserHandle userHandle,
            Map map,
            String str,
            List list,
            Intent intent,
            Bundle bundle,
            ComponentInfo componentInfo) {
        Tile activityTile;
        if (userHandle.getIdentifier() != ActivityManager.getCurrentUser()
                && Tile.isPrimaryProfileOnly(componentInfo.metaData)) {
            Log.w(
                    "TileUtils",
                    "Found "
                            + componentInfo.name
                            + " for intent "
                            + intent
                            + " is primary profile only, skip loading tile for uid "
                            + userHandle.getIdentifier());
            return;
        }
        if ((bundle == null || !bundle.containsKey("com.android.settings.category"))
                && str == null) {
            StringBuilder sb = new StringBuilder("Found ");
            sb.append(componentInfo.name);
            sb.append(" for intent ");
            sb.append(intent);
            sb.append(" missing metadata ");
            MainClear$$ExternalSyntheticOutline0.m$1(
                    sb,
                    bundle == null ? ApnSettings.MVNO_NONE : "com.android.settings.category",
                    "TileUtils");
            return;
        }
        String string = bundle.getString("com.android.settings.category");
        boolean z = componentInfo instanceof ProviderInfo;
        Pair pair =
                z
                        ? new Pair(
                                ((ProviderInfo) componentInfo).authority,
                                bundle.getString("com.android.settings.keyhint"))
                        : new Pair(componentInfo.packageName, componentInfo.name);
        Tile tile = (Tile) map.get(pair);
        if (tile == null) {
            if (z) {
                activityTile = new ProviderTile((ProviderInfo) componentInfo, string, bundle);
            } else {
                ActivityInfo activityInfo = (ActivityInfo) componentInfo;
                activityTile = new ActivityTile(activityInfo, string, activityInfo.metaData);
            }
            tile = activityTile;
            map.put(pair, tile);
        } else {
            tile.mMetaData = bundle;
        }
        tile.mGroupKey = bundle.getString("com.android.settings.group_key");
        if (!tile.userHandle.contains(userHandle)) {
            tile.userHandle.add(userHandle);
        }
        if (bundle.containsKey("com.android.settings.pending_intent")) {
            tile.pendingIntentMap.put(
                    userHandle,
                    (PendingIntent) bundle.getParcelable("com.android.settings.pending_intent"));
        }
        if (list.contains(tile)) {
            return;
        }
        list.add(tile);
    }

    public static void loadTilesForAction(
            Context context,
            UserHandle userHandle,
            String str,
            Map<Pair<String, String>, Tile> map,
            String str2,
            List<Tile> list,
            boolean z) {
        ArrayList parcelableArrayList;
        Intent intent = new Intent(str);
        if (z) {
            intent.setPackage("com.android.settings");
        }
        for (ResolveInfo resolveInfo :
                context.getPackageManager()
                        .queryIntentActivitiesAsUser(intent, 128, userHandle.getIdentifier())) {
            if (resolveInfo.system) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                loadTile(userHandle, map, str2, list, intent, activityInfo.metaData, activityInfo);
            }
        }
        for (ResolveInfo resolveInfo2 :
                context.getPackageManager()
                        .queryIntentContentProvidersAsUser(intent, 0, userHandle.getIdentifier())) {
            if (resolveInfo2.system) {
                ProviderInfo providerInfo = resolveInfo2.providerInfo;
                Context createContextAsUser = context.createContextAsUser(userHandle, 0);
                String str3 = providerInfo.authority;
                ArrayMap arrayMap = new ArrayMap();
                Bundle bundleFromUri =
                        getBundleFromUri(
                                createContextAsUser,
                                buildUri(str3, "getEntryData"),
                                arrayMap,
                                null);
                if (bundleFromUri != null) {
                    parcelableArrayList = bundleFromUri.getParcelableArrayList("entry_data");
                } else {
                    Bundle bundleFromUri2 =
                            getBundleFromUri(
                                    createContextAsUser,
                                    buildUri(str3, "getSwitchData"),
                                    arrayMap,
                                    null);
                    parcelableArrayList =
                            bundleFromUri2 != null
                                    ? bundleFromUri2.getParcelableArrayList("switch_data")
                                    : null;
                }
                if (parcelableArrayList != null && !parcelableArrayList.isEmpty()) {
                    Iterator it = parcelableArrayList.iterator();
                    while (it.hasNext()) {
                        loadTile(
                                userHandle,
                                map,
                                str2,
                                list,
                                intent,
                                (Bundle) it.next(),
                                providerInfo);
                    }
                }
            }
        }
    }

    public static Uri buildUri(String str, String str2) {
        return new Uri.Builder().scheme("content").authority(str).appendPath(str2).build();
    }
}
