package com.samsung.android.settings.goodsettings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.drawer.Tile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GoodSettingsTopLevelPreferenceParser {
    private static final String CATEGORY_PREFERENCE = "CategoryPreference";
    private static final String HOMEPAGE_PREFERENCE = "HomepagePreference";
    public static final String PREFERENCE_INFO_INTENT = "intent";
    public static final String PREFERENCE_INFO_IS_AVAILABLE = "isAvailable";
    public static final String PREFERENCE_INFO_IS_VISIBLE = "isVisible";
    public static final String PREFERENCE_INFO_TYPE = "type";
    private static final String PREFERENCE_INFO_TYPE_CATEGORY = "category";
    private static final String PREFERENCE_INFO_TYPE_MENU = "menu";
    private static final String SAMSUNG_ACCOUNT_PREFERENCE = "SecHomepageAccountPreference";
    private static final String TAG = "GoodSettingsTopLevelPreferenceParser";
    public static final HashSet<String> SKIPPING_PREFERENCE_KEY_SET =
            new HashSet<>(
                    Arrays.asList(
                            "top_level_mde_suggestions",
                            "top_level_mde_suggestions_top_inset_category"));
    private static final NullPointerException INVALID_PREFERENCE_EXCEPTION =
            new NullPointerException("invalid preference information");

    public static HashMap convertAttrMap(AttributeSet attributeSet) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            hashMap.put(attributeSet.getAttributeName(i), attributeSet.getAttributeValue(i));
            Log.i(
                    TAG,
                    attributeSet.getAttributeName(i) + " : " + attributeSet.getAttributeValue(i));
        }
        return hashMap;
    }

    public static Map convertToMap(JsonArray jsonArray) {
        HashMap hashMap = new HashMap();
        Iterator it = jsonArray.elements.iterator();
        while (it.hasNext()) {
            JsonObject asJsonObject = ((JsonElement) it.next()).getAsJsonObject();
            if (asJsonObject.members.containsKey(GoodSettingsContract.EXTRA_NAME.POLICY_KEY)) {
                hashMap.put(
                        asJsonObject.get(GoodSettingsContract.EXTRA_NAME.POLICY_KEY).getAsString(),
                        asJsonObject);
            }
        }
        return hashMap;
    }

    public static BasePreferenceController createController(
            Context context, JsonObject jsonObject) {
        LinkedTreeMap linkedTreeMap = jsonObject.members;
        if (!linkedTreeMap.containsKey("controller")) {
            return null;
        }
        String asString = jsonObject.get("controller").getAsString();
        if (TextUtils.isEmpty(asString)) {
            return null;
        }
        try {
            return BasePreferenceController.createInstance(context, asString);
        } catch (ClassCastException | IllegalStateException unused) {
            Log.d(TAG, "Could not find Context-only controller for pref: " + asString);
            try {
                return BasePreferenceController.createInstance(
                        context,
                        asString,
                        jsonObject.get(GoodSettingsContract.EXTRA_NAME.POLICY_KEY).getAsString(),
                        linkedTreeMap.containsKey("for_work")
                                ? jsonObject.get("for_work").getAsBoolean()
                                : false);
            } catch (ClassCastException | IllegalStateException unused2) {
                MotionLayout$$ExternalSyntheticOutline0.m(
                        "Cannot instantiate controller from reflection: ", asString, TAG);
                return null;
            }
        }
    }

    public static String getKeyFromTile(Context context, Tile tile) {
        String key = tile.getKey(context);
        if (!TextUtils.isEmpty(key)) {
            return key;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            return featureFactoryImpl.getDashboardFeatureProvider().getDashboardKeyForTile(tile);
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }

    public static JsonObject getPreferenceInfoFromAttributeSet(Context context, Map map, long j) {
        try {
            JsonObject jsonObject = new JsonObject();
            if (hasFlag(j, 2L)) {
                jsonObject.addProperty(
                        GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
                        (String) ((HashMap) map).get(GoodSettingsContract.EXTRA_NAME.POLICY_KEY));
            }
            if (hasFlag(j, 32768L)) {
                jsonObject.members.put(
                        "order",
                        new JsonPrimitive(
                                Integer.valueOf(
                                        Integer.parseInt((String) ((HashMap) map).get("order")))));
            }
            if (hasFlag(j, 16L)) {
                jsonObject.addProperty(
                        UniversalCredentialUtil.AGENT_TITLE,
                        getStringFromResourceId(
                                context,
                                (String) ((HashMap) map).get(UniversalCredentialUtil.AGENT_TITLE)));
            }
            if (hasFlag(j, 32L)) {
                jsonObject.addProperty(
                        UniversalCredentialUtil.AGENT_SUMMARY,
                        getStringFromResourceId(
                                context,
                                (String)
                                        ((HashMap) map)
                                                .get(UniversalCredentialUtil.AGENT_SUMMARY)));
            }
            if (hasFlag(j, 64L)) {
                String str = null;
                try {
                    int parseInt =
                            Integer.parseInt(
                                    ((String) ((HashMap) map).get("icon"))
                                            .replace("@", ApnSettings.MVNO_NONE));
                    if (parseInt > 0) {
                        str = context.getResources().getResourceName(parseInt);
                    }
                } catch (NullPointerException | NumberFormatException e) {
                    Log.w(TAG, e.getMessage());
                }
                jsonObject.addProperty("icon", str);
            }
            if (hasFlag(j, 65536L)) {
                jsonObject.addProperty("intent", (String) ((HashMap) map).get("intent"));
            }
            if (hasFlag(j, 131072L)) {
                jsonObject.addProperty("fragment", (String) ((HashMap) map).get("fragment"));
            }
            if (hasFlag(j, 8L)) {
                jsonObject.addProperty("controller", (String) ((HashMap) map).get("controller"));
            }
            if (hasFlag(j, 4096L)) {
                jsonObject.addProperty(
                        "for_work",
                        Boolean.valueOf(
                                Boolean.parseBoolean((String) ((HashMap) map).get("for_work"))));
            }
            return jsonObject;
        } catch (NumberFormatException e2) {
            Log.w(TAG, "invalid preference : " + e2.getMessage());
            throw INVALID_PREFERENCE_EXCEPTION;
        }
    }

    public static JsonObject getPreferenceInfoFromTile(Context context, Tile tile, long j) {
        Tile.ResourceInfo resourceInfo;
        JsonObject jsonObject = new JsonObject();
        String keyFromTile = getKeyFromTile(context, tile);
        int order = tile.getOrder();
        String str = (String) tile.getTitle(context);
        String str2 = (String) tile.getSummary(context);
        if (tile.mIconResIdOverride != 0) {
            resourceInfo =
                    new Tile.ResourceInfo(
                            context.getResources().getResourceName(tile.mIconResIdOverride));
        } else {
            if (tile.mMetaData != null) {
                tile.ensureMetadataNotStale(context);
                ComponentInfo componentInfo = tile.getComponentInfo(context);
                if (componentInfo == null) {
                    Log.w("Tile", "Cannot find ComponentInfo for " + tile.getDescription());
                } else {
                    int i = tile.mMetaData.getInt("com.android.settings.icon");
                    if (i != 0 && i != 17170445) {
                        try {
                            resourceInfo =
                                    new Tile.ResourceInfo(
                                            context.getPackageManager()
                                                    .getResourcesForApplication(
                                                            componentInfo.packageName)
                                                    .getResourceName(i));
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            resourceInfo = null;
        }
        String str3 = resourceInfo.mResourceId;
        Intent intent = tile.mIntent;
        String m =
                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                        keyFromTile, ApnSettings.MVNO_NONE);
        String str4 = order + ApnSettings.MVNO_NONE;
        String m2 =
                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ApnSettings.MVNO_NONE);
        String m3 =
                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, ApnSettings.MVNO_NONE);
        String m4 =
                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str3, ApnSettings.MVNO_NONE);
        StringBuilder m5 =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "key = ", m, ", order = ", str4, ", title = ");
        ConstraintWidget$$ExternalSyntheticOutline0.m(m5, m2, ", summary = ", m3, ", icon = ");
        Utils$$ExternalSyntheticOutline0.m(m5, m4, TAG);
        if (hasFlag(j, 2L)) {
            jsonObject.addProperty(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, keyFromTile);
        }
        if (hasFlag(j, 32768L)) {
            jsonObject.members.put("order", new JsonPrimitive(Integer.valueOf(order)));
        }
        if (hasFlag(j, 16L)) {
            jsonObject.addProperty(UniversalCredentialUtil.AGENT_TITLE, str);
        }
        if (hasFlag(j, 32L)) {
            jsonObject.addProperty(UniversalCredentialUtil.AGENT_SUMMARY, str2);
        }
        if (hasFlag(j, 64L)) {
            jsonObject.addProperty("icon", str3);
        }
        if (hasFlag(j, 65536L)) {
            jsonObject.addProperty("intent", intent != null ? intent.toUri(1) : null);
        }
        return jsonObject;
    }

    public static String getStringFromResourceId(Context context, String str) {
        try {
            int parseInt = Integer.parseInt(str.replace("@", ApnSettings.MVNO_NONE));
            if (parseInt <= 0) {
                return null;
            }
            return context.getResources().getString(parseInt);
        } catch (NullPointerException | NumberFormatException e) {
            Log.w(TAG, e.getMessage());
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0222  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.gson.JsonArray getTopLevelPreferences(
            android.content.Context r20, java.util.ArrayList r21, long r22) {
        /*
            Method dump skipped, instructions count: 646
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.goodsettings.GoodSettingsTopLevelPreferenceParser.getTopLevelPreferences(android.content.Context,"
                    + " java.util.ArrayList, long):com.google.gson.JsonArray");
    }

    public static boolean hasFlag(long j, long j2) {
        return (j & j2) != 0;
    }

    public static String merge(Context context, String str) {
        JsonArray jsonArray = (JsonArray) new Gson().fromJson(str, JsonArray.class);
        JsonArray topLevelPreferences =
                getTopLevelPreferences(context, null, GoodSettingsContract.FLAGS_PHASE_1);
        Map convertToMap = convertToMap(jsonArray);
        Map convertToMap2 = convertToMap(topLevelPreferences);
        HashMap hashMap = (HashMap) convertToMap;
        Iterator it = hashMap.entrySet().iterator();
        int i = Integer.MIN_VALUE;
        while (it.hasNext()) {
            i =
                    Integer.max(
                            i,
                            ((JsonObject) ((Map.Entry) it.next()).getValue())
                                    .get("order")
                                    .getAsInt());
        }
        HashMap hashMap2 = (HashMap) convertToMap2;
        Iterator it2 = hashMap2.entrySet().iterator();
        int i2 = Preference.DEFAULT_ORDER;
        while (it2.hasNext()) {
            i2 =
                    Integer.min(
                            i2,
                            ((JsonObject) ((Map.Entry) it2.next()).getValue())
                                    .get("order")
                                    .getAsInt());
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            if (!hashMap2.containsKey(entry.getKey())) {
                jsonArray.elements.remove((JsonElement) entry.getValue());
            }
        }
        for (Map.Entry entry2 : hashMap2.entrySet()) {
            if (!hashMap.containsKey(entry2.getKey())) {
                JsonObject jsonObject = (JsonObject) entry2.getValue();
                jsonObject.members.put(
                        "order",
                        new JsonPrimitive(
                                Integer.valueOf(
                                        (i - i2) + jsonObject.get("order").getAsInt() + 1)));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray.toString();
    }
}
