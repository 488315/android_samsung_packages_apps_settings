package com.samsung.android.settings.eternal.defaultvalue;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class XmlManager {
    public static final String[] DEFAULT_VALUE_BLOCK_LIST = {
        "/Settings/Connections/Location",
        "/Settings/Connections/LocationMethod",
        "/Settings/Connections/LocationWifiScan",
        "/Settings/Connections/LocationBluetoothScan",
        "/Settings/Connections/Nfc",
        "/Settings/Display/NightMode"
    };

    public static void buildKeySetOfBackupData(Node node, String str, List list) {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                if (item.hasChildNodes()) {
                    StringBuilder m =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    str, "/");
                    m.append(item.getNodeName());
                    buildKeySetOfBackupData(item, m.toString(), list);
                }
            } else if (item.getNodeType() == 3) {
                String textContent = item.getTextContent();
                if (!TextUtils.isEmpty(textContent)
                        && !TextUtils.isEmpty(
                                textContent.replaceAll("\\s", ApnSettings.MVNO_NONE))) {
                    ((ArrayList) list).add(str);
                }
            }
        }
    }

    public static void deleteOldDefaultXmlFile() {
        File file = new File("/efs/sec_efs/SettingsBackup.xml");
        if (file.exists()) {
            Log.d("Eternal/XmlManager", "deleteOldDefaultXmlFile");
            file.delete();
        }
    }

    public static HashMap getKeyListMapOfDTD(Context context) {
        Document document;
        HashMap hashMap = new HashMap();
        try {
            try {
                document =
                        DocumentBuilderFactory.newInstance()
                                .newDocumentBuilder()
                                .parse(context.getAssets().open("SettingsBnRDTD_T_OS.xml"));
            } catch (IOException | SAXException e) {
                CloneBackend$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("getKeyListMapOfDTD() "), "Eternal/XmlManager");
                document = null;
            }
            if (document == null) {
                Log.e("Eternal/XmlManager", "getKeySetOfDTD() - doc is null");
                return null;
            }
            document.getDocumentElement().normalize();
            Node searchNode = searchNode("BackupDataSet", document.getDocumentElement());
            if (searchNode == null) {
                Log.d("Eternal/XmlManager", "getKeyListMapOfDTD() backupDataSet is null");
                return null;
            }
            NodeList childNodes = searchNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                String uidOfBackupDataNode = getUidOfBackupDataNode(item);
                if (!TextUtils.isEmpty(uidOfBackupDataNode)
                        && (TextUtils.isEmpty("Settings")
                                || "Settings".equals(uidOfBackupDataNode))) {
                    ArrayList arrayList = new ArrayList();
                    if (!TextUtils.isEmpty(uidOfBackupDataNode)) {
                        buildKeySetOfBackupData(item, ApnSettings.MVNO_NONE, arrayList);
                        if (!arrayList.isEmpty()) {
                            hashMap.put(uidOfBackupDataNode, arrayList);
                        }
                    }
                }
            }
            List list = (List) hashMap.get("Settings");
            if (list != null) {
                String[] strArr = DEFAULT_VALUE_BLOCK_LIST;
                for (int i2 = 0; i2 < 6; i2++) {
                    list.remove(strArr[i2]);
                }
            }
            return hashMap;
        } catch (ParserConfigurationException e2) {
            Log.e("Eternal/XmlManager", "getKeyListMapOfDTD() " + e2.getMessage());
            return null;
        }
    }

    public static String getUidOfBackupDataNode(Node node) {
        NodeList childNodes = node.getChildNodes();
        if (childNodes != null) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item.getNodeType() == 1) {
                    return item.getNodeName();
                }
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    public static Node searchNode(String str, Node node) {
        NodeList childNodes;
        StringTokenizer stringTokenizer = new StringTokenizer(str, "/");
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            if (node != null && (childNodes = node.getChildNodes()) != null) {
                int length = childNodes.getLength();
                for (int i = 0; i < length; i++) {
                    Node item = childNodes.item(i);
                    if (item.getNodeName().equals(nextToken)) {
                        node = item;
                        break;
                    }
                }
            }
            node = null;
            if (node == null) {
                return null;
            }
        }
        return node;
    }
}
