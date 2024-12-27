package com.android.settings.core;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.Trace;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.homepage.SecTopLevelFeature;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PreferenceControllerListHelper {
    public static List filterControllers(List list, List list2) {
        if (list2 == null) {
            return list;
        }
        TreeSet treeSet = new TreeSet();
        ArrayList arrayList = new ArrayList();
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            String preferenceKey = ((AbstractPreferenceController) it.next()).getPreferenceKey();
            if (preferenceKey != null) {
                treeSet.add(preferenceKey);
            }
        }
        Iterator it2 = ((ArrayList) list).iterator();
        while (it2.hasNext()) {
            BasePreferenceController basePreferenceController =
                    (BasePreferenceController) it2.next();
            if (!treeSet.contains(basePreferenceController.getPreferenceKey())) {
                arrayList.add(basePreferenceController);
            }
        }
        return arrayList;
    }

    public static List getPreferenceControllersFromXml(Context context, int i) {
        List<Bundle> extractMetadata;
        BasePreferenceController createInstance;
        ArrayList arrayList = new ArrayList();
        if (i == R.xml.sec_top_level_settings) {
            SecTopLevelFeature secTopLevelFeature = SecTopLevelFeature.getInstance();
            if (secTopLevelFeature.mTopLevelPreferenceXmlMeta == null) {
                secTopLevelFeature.loadTopLevelPreferenceXmlMeta(context);
            }
            extractMetadata = secTopLevelFeature.mTopLevelPreferenceXmlMeta;
        } else {
            try {
                extractMetadata = PreferenceXmlParserUtils.extractMetadata(context, i, 4107);
            } catch (IOException | XmlPullParserException e) {
                Log.e(
                        "PrefCtrlListHelper",
                        "Failed to parse preference xml for getting controllers",
                        e);
                return arrayList;
            }
        }
        for (Bundle bundle : extractMetadata) {
            String string = bundle.getString("controller");
            if (!TextUtils.isEmpty(string)) {
                Trace.beginSection("PrefHelper#createIns_" + string);
                try {
                    createInstance = BasePreferenceController.createInstance(context, string);
                } catch (IllegalStateException unused) {
                    String string2 = bundle.getString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
                    boolean z = bundle.getBoolean("for_work", false);
                    if (TextUtils.isEmpty(string2)) {
                        Log.w(
                                "PrefCtrlListHelper",
                                "Controller requires key but it's not defined in xml: " + string);
                        Trace.endSection();
                    } else {
                        try {
                            createInstance =
                                    BasePreferenceController.createInstance(
                                            context, string, string2, z);
                        } catch (IllegalStateException unused2) {
                            Log.w(
                                    "PrefCtrlListHelper",
                                    "Cannot instantiate controller from reflection: " + string);
                            Trace.endSection();
                        }
                    }
                }
                arrayList.add(createInstance);
                Trace.endSection();
            }
        }
        return arrayList;
    }
}
