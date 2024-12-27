package com.android.settings.core;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.lib.episode.EpisodeUtils;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.eternal.BackupFeatureProviderImpl;
import com.samsung.android.settings.eternal.provider.items.RecoverableItemFactory;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SecCustomPreferenceController extends BasePreferenceController {
    public SecCustomPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getControlType() {
        return 102;
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
    public ControlValue getValue() {
        List<String> backupKeys = getBackupKeys();
        if (backupKeys == null || backupKeys.isEmpty()) {
            return null;
        }
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        Bundle bundle = new Bundle();
        for (String str : backupKeys) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            Bundle value =
                    ((BackupFeatureProviderImpl)
                                    featureFactoryImpl.backupFeatureProvider$delegate.getValue())
                            .getValue(this.mContext, str);
            if (value != null) {
                bundle.putBundle(str, value);
            }
        }
        CharSequence summary = getSummary();
        builder.mBundle = bundle;
        builder.setValue(Integer.valueOf(bundle.size()));
        builder.mSummary = summary != null ? (String) summary : null;
        return builder.build();
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
    public ControlResult setValue(ControlValue controlValue) {
        String[] split;
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        List<String> backupKeys = getBackupKeys();
        ControlResult.ResultCode resultCode = ControlResult.ResultCode.FAIL;
        if (backupKeys == null || backupKeys.isEmpty() || controlValue.mBundle == null) {
            builder.mResultCode = resultCode;
            return new ControlResult(builder);
        }
        boolean z = true;
        for (String str : backupKeys) {
            Scene scene = null;
            if ((controlValue.mBundle.containsKey(str) ? controlValue.mBundle.getBundle(str) : null)
                    != null) {
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                BackupFeatureProviderImpl backupFeatureProviderImpl =
                        (BackupFeatureProviderImpl)
                                featureFactoryImpl.backupFeatureProvider$delegate.getValue();
                Context context = this.mContext;
                backupFeatureProviderImpl.getClass();
                String str2 =
                        (TextUtils.isEmpty(str)
                                        || (split = str.split("/")) == null
                                        || split.length <= 2)
                                ? ApnSettings.MVNO_NONE
                                : split[2];
                if (!TextUtils.isEmpty(str2)) {
                    new Bundle();
                    ArrayList arrayList = new ArrayList();
                    Bundle bundle =
                            controlValue.mBundle.containsKey(str)
                                    ? controlValue.mBundle.getBundle(str)
                                    : null;
                    if (bundle != null) {
                        if (!arrayList.isEmpty()) {
                            bundle.putString(
                                    "compressedEternalItems",
                                    EpisodeUtils.convertListToString(arrayList));
                        }
                        if (!bundle.isEmpty() && !TextUtils.isEmpty(str)) {
                            Scene scene2 = new Scene();
                            scene2.mSceneKey = str;
                            scene2.mSceneValue = bundle;
                            scene2.mIsDefault = null;
                            scene2.mDefaultType = (byte) 0;
                            scene = scene2;
                        }
                        if (scene == null) {
                            SemLog.e(
                                    "BackupFeatureProviderImpl",
                                    "setValue() failed to build a scene for : " + str);
                        } else {
                            SceneResult value =
                                    RecoverableItemFactory.getItem(context, str2)
                                            .setValue(
                                                    context, str, scene, controlValue.mSourceInfo);
                            if (value != null
                                    && value.mResultType == SceneResult.ResultType.RESULT_OK) {}
                        }
                    }
                }
            }
            z = false;
        }
        if (z) {
            resultCode = ControlResult.ResultCode.SUCCESS;
        }
        builder.mResultCode = resultCode;
        return new ControlResult(builder);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
