package com.samsung.android.settings.actions;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.util.Log;

import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableData;
import com.android.settingslib.search.SearchIndexableResourcesBase;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.sec.ims.ImsManager;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionsIndexer implements Runnable {
    public ActionDataConverter mConverter;
    public ActionsDatabaseHelper mHelper;

    public static void insertActionData(SQLiteDatabase sQLiteDatabase, List list) {
        byte[] bArr;
        StringBuilder sb = new StringBuilder("insertActionData() ");
        ArrayList arrayList = (ArrayList) list;
        sb.append(arrayList.size());
        Log.i("Command/ActionsIndexer", sb.toString());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ActionData actionData = (ActionData) it.next();
            ContentValues contentValues = new ContentValues();
            contentValues.put(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, actionData.mKey);
            contentValues.put(UniversalCredentialUtil.AGENT_TITLE, actionData.mTitle);
            contentValues.put(UniversalCredentialUtil.AGENT_SUMMARY, actionData.mSummary);
            CharSequence charSequence = actionData.mScreenTitle;
            contentValues.put(
                    "screentitle",
                    charSequence == null ? ApnSettings.MVNO_NONE : charSequence.toString());
            contentValues.put("keywords", actionData.mKeywords);
            contentValues.put("icon", Integer.valueOf(actionData.mIconResource));
            contentValues.put("fragment", actionData.mFragmentClassName);
            contentValues.put("controller", actionData.mPreferenceController);
            contentValues.put(
                    ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE,
                    Integer.valueOf(actionData.mActionType));
            contentValues.put(
                    "recoverable",
                    actionData.mRecoverable ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            contentValues.put("restore_key", actionData.mRestoreKey);
            contentValues.put("target_fragment", actionData.mTargetFragment);
            LaunchPayload launchPayload = actionData.mPayload;
            if (launchPayload != null) {
                Parcel obtain = Parcel.obtain();
                launchPayload.writeToParcel(obtain, 0);
                bArr = obtain.marshall();
                obtain.recycle();
            } else {
                bArr = null;
            }
            contentValues.put("payload", bArr);
            sQLiteDatabase.replaceOrThrow("actions_index", null, contentValues);
        }
    }

    public final List getActionData() {
        ActionDataConverter actionDataConverter = this.mConverter;
        actionDataConverter.getClass();
        Log.d("Command/ActionDataConverter", "getActionData Started");
        ArrayList arrayList = new ArrayList();
        actionDataConverter.mIndexableDataMap.clear();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        Set set =
                ((SearchIndexableResourcesBase)
                                featureFactoryImpl
                                        .getSearchFeatureProvider()
                                        .getSearchIndexableResources())
                        .mProviders;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        StringBuilder sb = new StringBuilder("bundles: ");
        HashSet hashSet = (HashSet) set;
        sb.append(hashSet.size());
        Log.d("Command/ActionDataConverter", sb.toString());
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            SearchIndexableData searchIndexableData = (SearchIndexableData) it.next();
            String name = searchIndexableData.mTargetClass.getName();
            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                    searchIndexableData.mSearchIndexProvider;
            if (indexable$SearchIndexProvider == null) {
                Log.e(
                        "Command/ActionDataConverter",
                        "Dose not implement Search Index Provider: ".concat(name));
            } else if (DevelopmentSettingsDashboardFragment.class.getName().equals(name)) {
                Log.w("Command/ActionDataConverter", "Skip development options");
            } else {
                Log.d(
                        "Command/ActionDataConverter",
                        "Start getActionData from Provider: ".concat(name));
                try {
                    arrayList2.addAll(
                            actionDataConverter.getActionDataFromProvider(
                                    indexable$SearchIndexProvider, name));
                    Log.d(
                            "Command/ActionDataConverter",
                            "End getActionData from Provider: " + name);
                    Log.d(
                            "Command/ActionDataConverter",
                            "Added: " + arrayList2.size() + ", " + name);
                } catch (Exception e) {
                    Log.e(
                            "Command/ActionDataConverter",
                            "Cannot get ActionData from: ".concat(name));
                    e.printStackTrace();
                }
            }
        }
        Log.d("Command/ActionDataConverter", "Start getActionData from Non-Searchable list");
        ArrayList arrayList4 = new ArrayList();
        if (ActionIndexableResources.mInstance == null) {
            ActionIndexableResources.mInstance = new ActionIndexableResources();
        }
        Iterator it2 = ((HashSet) ActionIndexableResources.mInstance.mProviders).iterator();
        while (it2.hasNext()) {
            String name2 = ((Class) it2.next()).getName();
            try {
                BasePreferenceController createInstance =
                        BasePreferenceController.createInstance(
                                actionDataConverter.mContext, name2);
                ActionData buildActionData =
                        actionDataConverter.buildActionData(
                                createInstance.getPreferenceKey(), null, createInstance);
                if (buildActionData == null) {
                    ActionData.Builder builder = new ActionData.Builder();
                    builder.mKey = createInstance.getPreferenceKey();
                    builder.mPrefControllerClassName = name2;
                    builder.mActionType = createInstance.getControlType();
                    buildActionData = builder.build();
                }
                arrayList4.add(buildActionData);
            } catch (Exception e2) {
                Log.e("Command/ActionDataConverter", e2.getMessage());
            }
        }
        arrayList3.addAll(arrayList4);
        Log.d("Command/ActionDataConverter", "Added: " + arrayList3.size());
        Log.d("Command/ActionDataConverter", "End getActionData from Non-Searchable list");
        arrayList.addAll(ActionDataConverter.uniqueActionData(arrayList2, arrayList3));
        Log.d("Command/ActionDataConverter", "getActionData Done");
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            ActionData actionData = (ActionData) it3.next();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(actionData.mTitle);
            sb2.append(", fragmentClass :");
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    sb2,
                    actionData.mFragmentClassName,
                    " is added to ActionData",
                    "Command/ActionDataConverter");
        }
        return arrayList;
    }

    public final void indexActionData() {
        if (this.mHelper.isActionDataIndexed()) {
            Log.d(
                    "Command/ActionsIndexer",
                    "indexActionData() Actions already indexed - returning.");
            return;
        }
        Log.d("Command/ActionsIndexer", "indexActionData() START");
        SQLiteDatabase writableDatabase = this.mHelper.getWritableDatabase();
        long currentTimeMillis = System.currentTimeMillis();
        writableDatabase.beginTransaction();
        try {
            this.mHelper.reconstruct(writableDatabase);
            insertActionData(writableDatabase, getActionData());
            this.mHelper.setIndexedState();
            Log.d(
                    "Command/ActionsIndexer",
                    "indexActionData() FINISH took: "
                            + (System.currentTimeMillis() - currentTimeMillis));
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        indexActionData();
    }
}
