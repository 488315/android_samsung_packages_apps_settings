package com.samsung.android.settings.eternal.validate;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import com.samsung.android.settings.eternal.constant.EternalRune;
import com.samsung.android.settings.eternal.data.BackupInfo;
import com.samsung.android.settings.eternal.data.EpisodeHolder;
import com.samsung.android.settings.eternal.manager.KeyListManager;
import com.samsung.android.settings.eternal.manager.XmlManager;
import com.samsung.android.settings.eternal.utils.FileUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BaseValidationWorker implements Callable {
    public final Context mContext;
    public final EpisodeHolder mEpisodeHolder;

    public BaseValidationWorker(Context context, EpisodeHolder episodeHolder) {
        this.mContext = context;
        this.mEpisodeHolder = episodeHolder;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        removeValidationFile();
        EpisodeHolder episodeHolder = this.mEpisodeHolder;
        if (episodeHolder == null) {
            return null;
        }
        KeyListManager keyListManager = new KeyListManager(episodeHolder.mSupplierInfo);
        XmlManager xmlManager = new XmlManager(this.mContext);
        BackupInfo backupInfo = episodeHolder.mBackupInfo;
        HashMap dTDItemHashMap =
                keyListManager.getDTDItemHashMap(
                        xmlManager, backupInfo == null ? -1 : backupInfo.mRequestFrom);
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        JSONObject jSONObject = new JSONObject();
        if (dTDItemHashMap == null || episodeHolder.mBackupSceneMap == null) {
            return null;
        }
        for (Map.Entry entry : dTDItemHashMap.entrySet()) {
            String str = (String) entry.getKey();
            arrayList.addAll(
                    validateSceneList(
                            entry, (List) episodeHolder.mBackupSceneMap.get(entry.getKey())));
            EternalRune.isTestMode();
            jSONObject.put(str, (Object) null);
        }
        EternalRune.isTestMode();
        onValidationFinish();
        if (arrayList.isEmpty()) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dataTypeValidation", arrayList);
        return bundle;
    }

    public abstract String getValidationFileName();

    public void removeValidationFile() {
        Context context = this.mContext;
        String m =
                ComponentActivity$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder(context.getFilesDir().getPath()),
                        "/BackupValidation/",
                        getValidationFileName());
        if (TextUtils.isEmpty(m)) {
            return;
        }
        FileUtils.deleteFile(new File(m), "Eternal/FileUtils", "deleteFile()");
    }

    public abstract List validateSceneList(Map.Entry entry, List list);

    public void onValidationFinish() {}
}
