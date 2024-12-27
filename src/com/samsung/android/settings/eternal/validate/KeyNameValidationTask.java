package com.samsung.android.settings.eternal.validate;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.samsung.android.lib.episode.Scene;
import com.samsung.android.settings.eternal.constant.EternalRune;
import com.samsung.android.settings.eternal.data.EpisodeHolder;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KeyNameValidationTask extends FutureTask {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KeyNameValidationWorker extends BaseValidationWorker {
        public static final String[] KEY_NAME_ALLOW_LIST = {
            "/Accessibility/Default/SoundBalace",
            "/Accessibility/Default/HighContrast",
            "/Accessibility/UniversalSwitch/ScanPause",
            "/Accessibility/Default/MonoAudioDb",
            "/Accessibility/UniversalSwitch/ActionWithASwitch",
            "/Settings/LockScreen/FaceWidget"
        };
        public final ArrayList mKeyNameAllowList;
        public final List mKeyNameViolationItems;
        public final List mSummaryItemList;

        public KeyNameValidationWorker(Context context, EpisodeHolder episodeHolder) {
            super(context, episodeHolder);
            this.mKeyNameViolationItems = new ArrayList();
            this.mKeyNameAllowList = new ArrayList(Arrays.asList(KEY_NAME_ALLOW_LIST));
            this.mSummaryItemList = new ArrayList();
        }

        @Override // com.samsung.android.settings.eternal.validate.BaseValidationWorker
        public final String getValidationFileName() {
            return "backupKeyNameValidation.json";
        }

        @Override // com.samsung.android.settings.eternal.validate.BaseValidationWorker
        public final void onValidationFinish() {
            EternalRune.isTestMode();
        }

        @Override // com.samsung.android.settings.eternal.validate.BaseValidationWorker
        public final void removeValidationFile() {
            super.removeValidationFile();
            String str =
                    this.mContext.getFilesDir().getPath() + "/BackupValidation/backupSummary.json";
            if (TextUtils.isEmpty(str)) {
                return;
            }
            FileUtils.deleteFile(new File(str), "Eternal/FileUtils", "deleteFile()");
        }

        @Override // com.samsung.android.settings.eternal.validate.BaseValidationWorker
        public final List validateSceneList(Map.Entry entry, List list) {
            String sb;
            ArrayList arrayList = new ArrayList();
            EternalRune.isTestMode();
            ((ArrayList) this.mKeyNameViolationItems).clear();
            HashMap hashMap = (HashMap) entry.getValue();
            int size = hashMap == null ? 0 : hashMap.size();
            int size2 = list != null ? list.size() : 0;
            StringBuilder sb2 = new StringBuilder("validateBackupKeyName() [");
            sb2.append((String) entry.getKey());
            sb2.append("] DTD size = ");
            sb2.append(size);
            sb2.append(" / backupSceneList size = ");
            Preference$$ExternalSyntheticOutline0.m(sb2, size2, "Eternal/KeyNameValidationTask");
            List list2 = this.mSummaryItemList;
            String str = (String) entry.getKey();
            BackupSummaryItem backupSummaryItem = new BackupSummaryItem();
            backupSummaryItem.mUID = str;
            backupSummaryItem.mDTDSize = size;
            backupSummaryItem.mBackupItemSize = size2;
            ((ArrayList) list2).add(backupSummaryItem);
            if (list == null) {
                if (hashMap != null) {
                    Iterator it = hashMap.keySet().iterator();
                    while (it.hasNext()) {
                        ((ArrayList) this.mKeyNameViolationItems)
                                .add(
                                        new KeyNameValidationItem(
                                                (String) it.next(), "NOTHING_BACKUP", "null"));
                    }
                }
                return arrayList;
            }
            Iterator it2 = new ArrayList(list).iterator();
            while (it2.hasNext()) {
                Scene scene = (Scene) it2.next();
                BackupDataType backupDataType =
                        hashMap == null ? null : (BackupDataType) hashMap.get(scene.mSceneKey);
                if (this.mKeyNameAllowList.contains(scene.mSceneKey) && hashMap != null) {
                    hashMap.remove(scene.mSceneKey);
                } else if (backupDataType == null) {
                    arrayList.add(scene);
                    List list3 = this.mKeyNameViolationItems;
                    String str2 = scene.mSceneKey;
                    KeyNameValidationItem keyNameValidationItem = new KeyNameValidationItem();
                    keyNameValidationItem.mKey = str2;
                    keyNameValidationItem.mResultType = "UNDEFINED_KEY";
                    Bundle bundle = scene.mSceneValue;
                    if (bundle == null) {
                        sb = "null";
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        Iterator<String> it3 = bundle.keySet().iterator();
                        while (it3.hasNext()) {
                            sb3.append(it3.next());
                            sb3.append("/");
                        }
                        sb = sb3.toString();
                    }
                    keyNameValidationItem.mAttributeNames = sb;
                    ((ArrayList) list3).add(keyNameValidationItem);
                    EternalFileLog.e(
                            "Eternal/KeyNameValidationTask",
                            "validateBackupKeyName() UNDEFINED_KEY - " + scene.mSceneKey);
                } else {
                    Bundle bundle2 = scene.mSceneValue;
                    if (bundle2 == null) {
                        ((ArrayList) this.mKeyNameViolationItems)
                                .add(
                                        new KeyNameValidationItem(
                                                scene.mSceneKey, "NOTHING_BACKUP", "null"));
                        if (hashMap != null) {
                            hashMap.remove(scene.mSceneKey);
                        }
                    } else {
                        HashMap hashMap2 = backupDataType.mKeyValue;
                        Iterator<String> it4 = bundle2.keySet().iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                break;
                            }
                            String next = it4.next();
                            if (!hashMap2.containsKey(next)
                                    && !TextUtils.equals(next, "compressedEternalItems")) {
                                ((ArrayList) this.mKeyNameViolationItems)
                                        .add(
                                                new KeyNameValidationItem(
                                                        scene.mSceneKey,
                                                        "UNDEFINED_ATTRIBUTE",
                                                        next));
                                EternalFileLog.e(
                                        "Eternal/KeyNameValidationTask",
                                        "validateBackupKeyName() UNDEFINED_ATTRIBUTE - "
                                                + scene.mSceneKey
                                                + " / "
                                                + next);
                                arrayList.add(scene);
                                break;
                            }
                            hashMap2.remove(next);
                        }
                        if (hashMap != null) {
                            hashMap.remove(scene.mSceneKey);
                        }
                    }
                }
            }
            EternalRune.isTestMode();
            return arrayList;
        }
    }
}
