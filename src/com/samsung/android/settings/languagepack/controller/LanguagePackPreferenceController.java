package com.samsung.android.settings.languagepack.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.data.PackageInfo;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.manager.LanguagePackManager;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePackPreferenceController extends BasePreferenceController {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    private static final String TAG = "LanguagePackPreferenceController";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.controller.LanguagePackPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(final Context context) {
            ArrayList arrayList = new ArrayList();
            LanguagePackManager languagePackManager = LanguagePackManager.getInstance(context);
            ArrayList arrayList2 = new ArrayList();
            try {
                arrayList2.addAll(languagePackManager.makeLanguageList());
            } catch (JSONException e) {
                Log.e(
                        LanguagePackPreferenceController.TAG,
                        "failed to make language info list : " + e.getMessage());
            }
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                LanguageInfo languageInfo = (LanguageInfo) it.next();
                if (languageInfo.checkDownloadedWithType(context, 0, arrayList2)) {
                    arrayList3.add(languageInfo.mLanguageCode);
                }
                if (languageInfo.mList.stream()
                        .allMatch(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.languagepack.controller.LanguagePackPreferenceController$1$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((PackageInfo) obj).isPackageInstalled(context);
                                    }
                                })) {
                    arrayList4.add(languageInfo.mLanguageCode);
                }
            }
            String join = String.join(",", arrayList3);
            String join2 = String.join(",", arrayList4);
            String valueOf = String.valueOf(8153);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = join;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            String valueOf2 = String.valueOf(8152);
            String valueOf3 = String.valueOf(arrayList3.size());
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = valueOf3;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            String valueOf4 = String.valueOf(8155);
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = join2;
            statusData3.mStatusKey = valueOf4;
            arrayList.add(statusData3);
            String valueOf5 = String.valueOf(8154);
            String valueOf6 = String.valueOf(arrayList4.size());
            StatusData statusData4 = new StatusData();
            statusData4.mStatusValue = valueOf6;
            statusData4.mStatusKey = valueOf5;
            arrayList.add(statusData4);
            return arrayList;
        }
    }

    public LanguagePackPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Rune.FEATURE_OFFLINE_LANGUAGE_PACK ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
