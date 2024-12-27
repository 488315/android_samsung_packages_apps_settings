package com.samsung.android.settings.deviceinfo.regulatoryinfo;

import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

import com.android.settings.R;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import org.xmlpull.v1.XmlPullParser;

import java.io.FileReader;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecRegulatoryInfoActivityIndia extends SecRegulatoryInfoActivityGlobal {
    public final HashMap mRegulatoryPlantMap = new HashMap();

    /* JADX WARN: Removed duplicated region for block: B:19:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f0 A[EDGE_INSN: B:44:0x00f0->B:45:0x00f0 BREAK  A[LOOP:0: B:33:0x00d3->B:42:0x00ed], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00fd A[ADDED_TO_REGION, ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0090  */
    @Override // com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityGlobal
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getRegulatoryFileName() {
        /*
            Method dump skipped, instructions count: 257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityIndia.getRegulatoryFileName():java.lang.String");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        StringBuilder sb;
        XmlPullParser xml;
        FileReader fileReader;
        super.onCreate(bundle);
        FileReader fileReader2 = null;
        try {
            try {
                if (SecRegulatoryInfoActivityGlobal.isRegulatoryFileExist(
                        "regulatory_image_plant_table.xml")) {
                    xml = Xml.newPullParser();
                    fileReader =
                            new FileReader(
                                    SecRegulatoryInfoActivityGlobal.getRegulatoryFilePath(
                                            "regulatory_image_plant_table.xml", false));
                    try {
                        xml.setInput(fileReader);
                    } catch (Exception e) {
                        e = e;
                        fileReader2 = fileReader;
                        Log.w(
                                "SecRegulatoryInfoActivityIndia",
                                "sec_regulatory_image_plant parser fail  : " + e.getMessage());
                        if (fileReader2 != null) {
                            try {
                                fileReader2.close();
                                return;
                            } catch (Exception e2) {
                                e = e2;
                                sb = new StringBuilder("Exception on closing..");
                                CloneBackend$$ExternalSyntheticOutline0.m(
                                        e, sb, "SecRegulatoryInfoActivityIndia");
                            }
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        fileReader2 = fileReader;
                        if (fileReader2 != null) {
                            try {
                                fileReader2.close();
                            } catch (Exception e3) {
                                CloneBackend$$ExternalSyntheticOutline0.m(
                                        e3,
                                        new StringBuilder("Exception on closing.."),
                                        "SecRegulatoryInfoActivityIndia");
                            }
                        }
                        throw th;
                    }
                } else {
                    xml = getResources().getXml(R.xml.sec_regulatory_image_plant);
                    fileReader = null;
                }
                while (xml.getEventType() != 1) {
                    if (xml.getEventType() == 2 && xml.getName().equals("entry")) {
                        this.mRegulatoryPlantMap.put(
                                xml.getAttributeValue(null, "plantCode"),
                                xml.getAttributeValue(null, "imageName"));
                    }
                    xml.next();
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (Exception e4) {
                        e = e4;
                        sb = new StringBuilder("Exception on closing..");
                        CloneBackend$$ExternalSyntheticOutline0.m(
                                e, sb, "SecRegulatoryInfoActivityIndia");
                    }
                }
            } catch (Exception e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
