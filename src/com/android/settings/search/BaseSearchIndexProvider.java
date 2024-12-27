package com.android.settings.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.provider.SearchIndexableResource;
import android.util.Log;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;

import com.android.settings.biometrics.combination.CombinedBiometricSearchIndexProvider;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerListHelper;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.PreferenceXmlParserUtils;
import com.android.settings.notification.modes.ZenModesListFragment;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BaseSearchIndexProvider implements Indexable$SearchIndexProvider {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public final int mXmlRes;

    public BaseSearchIndexProvider() {
        this.mXmlRes = 0;
    }

    public List createPreferenceControllers(Context context) {
        return null;
    }

    @Override // com.android.settingslib.search.Indexable$SearchIndexProvider
    public List getDynamicRawDataToIndex(Context context) {
        List<Object> preferenceControllers;
        ArrayList arrayList = new ArrayList();
        if (isPageSearchEnabled(context)
                && supportDynamicRawIndexingWithController()
                && (preferenceControllers = getPreferenceControllers(context)) != null
                && !preferenceControllers.isEmpty()) {
            for (Object obj : preferenceControllers) {
                if (obj instanceof PreferenceControllerMixin) {
                    ((PreferenceControllerMixin) obj).updateDynamicRawDataToIndex(arrayList);
                } else if (obj instanceof BasePreferenceController) {
                    ((BasePreferenceController) obj).updateDynamicRawDataToIndex(arrayList);
                } else {
                    Log.e(
                            "BaseSearchIndex",
                            obj.getClass().getName()
                                    + " must implement "
                                    + PreferenceControllerMixin.class.getName()
                                    + " treating the dynamic indexable");
                }
            }
        }
        return arrayList;
    }

    public HashMap getGtsResourceGroup() {
        return new HashMap();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settingslib.search.Indexable$SearchIndexProvider
    public List getNonIndexableKeys(Context context) {
        ArrayList arrayList = new ArrayList();
        if (!isPageSearchEnabled(context)) {
            arrayList.addAll(getNonIndexableKeysFromXml(context, true));
            List rawDataToIndex = getRawDataToIndex(context);
            if (rawDataToIndex != null) {
                arrayList.addAll(
                        rawDataToIndex.stream()
                                .map(new BaseSearchIndexProvider$$ExternalSyntheticLambda0())
                                .toList());
            }
            return arrayList;
        }
        arrayList.addAll(getNonIndexableKeysFromXml(context, false));
        List<AbstractPreferenceController> preferenceControllers =
                getPreferenceControllers(context);
        if (preferenceControllers != null && !preferenceControllers.isEmpty()) {
            for (AbstractPreferenceController abstractPreferenceController :
                    preferenceControllers) {
                long currentTimeMillis = System.currentTimeMillis();
                if (arrayList.contains(abstractPreferenceController.getPreferenceKey())) {
                    Log.d(
                            "BaseSearchIndex",
                            "getNonIndexableKeys() already indexed from xml. key : "
                                    + abstractPreferenceController.getPreferenceKey());
                } else {
                    if (abstractPreferenceController instanceof PreferenceControllerMixin) {
                        ((PreferenceControllerMixin) abstractPreferenceController)
                                .updateNonIndexableKeys(arrayList);
                    } else if (abstractPreferenceController instanceof BasePreferenceController) {
                        ((BasePreferenceController) abstractPreferenceController)
                                .updateNonIndexableKeys(arrayList);
                    } else {
                        Log.e(
                                "BaseSearchIndex",
                                abstractPreferenceController.getClass().getName()
                                        + " must implement "
                                        + PreferenceControllerMixin.class.getName()
                                        + " treating the key non-indexable");
                        arrayList.add(abstractPreferenceController.getPreferenceKey());
                    }
                    long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                    if (DEBUG || currentTimeMillis2 > 50) {
                        Log.w(
                                "BaseSearchIndex",
                                "getNonIndexableKeys() key : "
                                        + abstractPreferenceController.getPreferenceKey()
                                        + ", spent time : "
                                        + currentTimeMillis2);
                    }
                }
            }
        }
        return arrayList;
    }

    public final List getNonIndexableKeysFromXml(Context context, boolean z) {
        List xmlResourcesToIndex = getXmlResourcesToIndex(context);
        if (xmlResourcesToIndex == null || xmlResourcesToIndex.isEmpty()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = xmlResourcesToIndex.iterator();
        while (it.hasNext()) {
            arrayList.addAll(
                    getNonIndexableKeysFromXml(
                            context, ((SearchIndexableResource) it.next()).xmlResId, z));
        }
        return arrayList;
    }

    public final List getPreferenceControllers(Context context) {
        List arrayList = new ArrayList();
        try {
            arrayList = createPreferenceControllers(context);
        } catch (Exception e) {
            Log.w(
                    "BaseSearchIndex",
                    "Error initializing controller in fragment: " + this + ", e: " + e);
        }
        List xmlResourcesToIndex = getXmlResourcesToIndex(context);
        if (xmlResourcesToIndex == null || xmlResourcesToIndex.isEmpty()) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = xmlResourcesToIndex.iterator();
        while (it.hasNext()) {
            arrayList2.addAll(
                    PreferenceControllerListHelper.getPreferenceControllersFromXml(
                            context, ((SearchIndexableResource) it.next()).xmlResId));
        }
        List filterControllers =
                PreferenceControllerListHelper.filterControllers(arrayList2, arrayList);
        ArrayList arrayList3 = new ArrayList();
        if (arrayList != null) {
            arrayList3.addAll(arrayList);
        }
        arrayList3.addAll(filterControllers);
        return arrayList3;
    }

    @Override // com.android.settingslib.search.Indexable$SearchIndexProvider
    public List getRawDataToIndex(Context context) {
        ArrayList arrayList = new ArrayList();
        List<Object> preferenceControllers = getPreferenceControllers(context);
        if (preferenceControllers != null && !preferenceControllers.isEmpty()) {
            for (Object obj : preferenceControllers) {
                if (obj instanceof PreferenceControllerMixin) {
                    ((PreferenceControllerMixin) obj).updateRawDataToIndex(arrayList);
                } else if (obj instanceof BasePreferenceController) {
                    ((BasePreferenceController) obj).updateRawDataToIndex(arrayList);
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.settingslib.search.Indexable$SearchIndexProvider
    public List getXmlResourcesToIndex(Context context) {
        int i = this.mXmlRes;
        if (i == 0) {
            return null;
        }
        SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
        searchIndexableResource.xmlResId = i;
        return Arrays.asList(searchIndexableResource);
    }

    public boolean isPageSearchEnabled(Context context) {
        return !(this instanceof CombinedBiometricSearchIndexProvider);
    }

    public boolean supportDynamicRawIndexingWithController() {
        return this instanceof ZenModesListFragment.AnonymousClass1;
    }

    public BaseSearchIndexProvider(int i) {
        this.mXmlRes = i;
    }

    public List<String> getNonIndexableKeysFromXml(Context context, int i, boolean z) {
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it =
                    ((ArrayList) PreferenceXmlParserUtils.extractMetadata(context, i, FileType.SCC))
                            .iterator();
            while (it.hasNext()) {
                Bundle bundle = (Bundle) it.next();
                if (z || !bundle.getBoolean("searchable", true)) {
                    arrayList.add(bundle.getString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY));
                }
            }
        } catch (IOException | XmlPullParserException unused) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    i, "Error parsing non-indexable from xml ", "BaseSearchIndex");
        }
        return arrayList;
    }
}
