package com.samsung.android.settings.eternal.manager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.lib.episode.EpisodeUtils;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.settings.eternal.constant.EternalConstants;
import com.samsung.android.settings.eternal.data.EpisodeHolder;
import com.samsung.android.settings.eternal.data.RestoreInfo;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.policy.PolicyManager;
import com.samsung.android.settings.eternal.policy.RestorePolicy;
import com.samsung.android.settings.eternal.validate.BackupDataType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class XmlManager implements RepositoryManager {
    public final Context mContext;
    public boolean mEnableDefaultValueSkipPolicy = true;
    public String mBaseDTDVersion = ApnSettings.MVNO_NONE;

    public XmlManager(Context context) {
        this.mContext = context;
    }

    public static void buildBackupDataSetElement(HashMap hashMap, HashMap hashMap2, Document document) {
        Element createElement = document.createElement("BackupDataSet");
        Node searchNode = searchNode("DeviceConfiguration", document);
        if (searchNode != null) {
            searchNode.appendChild(createElement);
        }
        for (Map.Entry entry : hashMap2.entrySet()) {
            String str = (String) entry.getKey();
            List<Scene> list = (List) entry.getValue();
            SourceInfo sourceInfo = (SourceInfo) hashMap.get(str);
            if (list != null) {
                Element createElement2 = document.createElement("BackupData");
                createElement.appendChild(createElement2);
                for (Scene scene : list) {
                    String str2 = scene.mSceneKey;
                    Bundle bundle = scene.mSceneValue;
                    if (bundle == null) {
                        DialogFragment$$ExternalSyntheticOutline0.m("buildBackupDataElement() keyBundle is null - key : ", str2, "Eternal/XmlManager");
                    } else {
                        Node node = null;
                        for (String str3 : bundle.keySet()) {
                            String string = bundle.getString(str3);
                            if (string != null) {
                                node = getOrCreateNode(createElement2, str2);
                                if (node != null) {
                                    if ("value".equals(str3)) {
                                        node.appendChild(createElement2.getOwnerDocument().createTextNode(string));
                                    } else {
                                        ((Element) node).setAttribute(str3, string);
                                    }
                                }
                            } else if ("value".equals(str3)) {
                                DialogFragment$$ExternalSyntheticOutline0.m("buildBackupDataElement() value is null - key : ", str2, "Eternal/XmlManager");
                            } else {
                                Log.d("Eternal/XmlManager", "buildBackupDataElement() attribute value is null - key : " + str2 + " / attribute : " + str3);
                            }
                        }
                        if (node != null && scene.isDefault()) {
                            ((Element) node).setAttribute("defaultValue", String.valueOf((int) scene.mDefaultType));
                        }
                    }
                }
                Node firstChild = createElement2.getFirstChild();
                if (firstChild != null && sourceInfo != null) {
                    if (!TextUtils.isEmpty(sourceInfo.mVersion)) {
                        ((Element) firstChild).setAttribute(FieldName.VERSION, sourceInfo.mVersion);
                    }
                    if (!TextUtils.isEmpty(sourceInfo.mDTDVersion)) {
                        ((Element) firstChild).setAttribute("dtd_version", sourceInfo.mDTDVersion);
                    }
                }
            }
        }
    }

    public static void buildGeneralInfo(EpisodeHolder episodeHolder, Document document) {
        if (episodeHolder.mGeneralInfoList == null) {
            Log.e("Eternal/XmlManager", "buildGeneralInfo sceneList is null");
            return;
        }
        Element createElement = document.createElement("DeviceConfiguration");
        document.appendChild(createElement);
        Iterator it = ((ArrayList) episodeHolder.mGeneralInfoList).iterator();
        while (it.hasNext()) {
            Scene scene = (Scene) it.next();
            getOrCreateNode(createElement, scene.mSceneKey).appendChild(document.createTextNode(scene.getValue(null, false)));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [org.w3c.dom.Node] */
    public static Node getOrCreateNode(Element element, String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, "/");
        Document ownerDocument = element.getOwnerDocument();
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            ?? searchFromChildNode = searchFromChildNode(nextToken, element);
            if (searchFromChildNode == 0) {
                Element createElement = ownerDocument.createElement(nextToken);
                element.appendChild(createElement);
                element = createElement;
            } else {
                element = searchFromChildNode;
            }
        }
        return element;
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

    public static void parseDTDItems(Node node, String str, HashMap hashMap) {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                if (item.hasChildNodes()) {
                    StringBuilder m = PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(str, "/");
                    m.append(item.getNodeName());
                    parseDTDItems(item, m.toString(), hashMap);
                }
            } else if (item.getNodeType() == 3) {
                String textContent = item.getTextContent();
                if (" ".equals(textContent) || (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(textContent.replaceAll("\n", ApnSettings.MVNO_NONE).replaceAll(" ", ApnSettings.MVNO_NONE)))) {
                    BackupDataType backupDataType = new BackupDataType();
                    HashMap hashMap2 = new HashMap();
                    backupDataType.mKeyValue = hashMap2;
                    hashMap2.put("value", textContent);
                    if (node.hasAttributes()) {
                        NamedNodeMap attributes = node.getAttributes();
                        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
                            Node item2 = attributes.item(i2);
                            if (item2 != null && !"defaultValue".equals(item2.getNodeName())) {
                                backupDataType.mKeyValue.put(item2.getNodeName(), item2.getTextContent());
                            }
                        }
                    }
                    hashMap.put(str, backupDataType);
                }
            }
        }
    }

    public static Node searchFromChildNode(String str, Node node) {
        NodeList childNodes;
        if (node != null && (childNodes = node.getChildNodes()) != null) {
            int length = childNodes.getLength();
            for (int i = 0; i < length; i++) {
                Node item = childNodes.item(i);
                if (item.getNodeName().equals(str)) {
                    return item;
                }
            }
        }
        return null;
    }

    public static Node searchNode(String str, Node node) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, "/");
        while (stringTokenizer.hasMoreTokens()) {
            node = searchFromChildNode(stringTokenizer.nextToken(), node);
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.android.settings.eternal.manager.RepositoryManager
    public final EpisodeHolder createEpisodeHolder(RestoreInfo restoreInfo, HashMap hashMap) {
        Document document;
        String str;
        int i;
        List list;
        Object obj;
        NodeList nodeList;
        int i2;
        int i3;
        short s;
        Node searchFromChildNode;
        char c;
        int i4;
        NamedNodeMap namedNodeMap;
        XmlManager xmlManager = this;
        int i5 = 0;
        short s2 = 1;
        EternalFileLog.d("Eternal/XmlManager", "createEpisodeHolder()");
        File file = restoreInfo.mRestoreFile;
        Object obj2 = null;
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            Log.e("Eternal/XmlManager", "parseXml failed : " + e.toString());
            document = null;
        }
        if (document == null) {
            return null;
        }
        Node searchNode = searchNode("/GeneralInfo/InitialOsVersion", document.getDocumentElement());
        if (searchNode != null && searchNode.getFirstChild() != null) {
            String nodeValue = searchNode.getFirstChild().getNodeValue();
            if (TextUtils.isEmpty(nodeValue)) {
                xmlManager.mEnableDefaultValueSkipPolicy = false;
            } else {
                xmlManager.mEnableDefaultValueSkipPolicy = Integer.parseInt(nodeValue) >= 28;
            }
        }
        Node searchNode2 = searchNode("BackupDataSet", document.getDocumentElement());
        if (searchNode2 == null) {
            return null;
        }
        NodeList childNodes = searchNode2.getChildNodes();
        EpisodeHolder episodeHolder = new EpisodeHolder();
        List parseBackupDataNode = xmlManager.parseBackupDataNode(document.getDocumentElement(), new ArrayList(Arrays.asList(KeyListManager.GENERAL_KEY_LIST)));
        if (parseBackupDataNode != null) {
            ((ArrayList) episodeHolder.mGeneralInfoList).clear();
            ((ArrayList) episodeHolder.mGeneralInfoList).addAll(parseBackupDataNode);
        }
        if (parseBackupDataNode == null) {
            EternalFileLog.i("Eternal/XmlManager", "getManufacturer() - -1");
            i = -1;
        } else {
            Iterator it = ((ArrayList) parseBackupDataNode).iterator();
            while (true) {
                if (!it.hasNext()) {
                    str = null;
                    break;
                }
                Scene scene = (Scene) it.next();
                if (TextUtils.equals(scene.mSceneKey, "/GeneralInfo/BuildNum")) {
                    str = scene.getValue(null, false);
                    break;
                }
            }
            if (str == null || !str.toLowerCase().startsWith(new StringBuffer(EternalConstants.PREFIX_LO).reverse().toString())) {
                EternalFileLog.i("Eternal/XmlManager", "getManufacturer() - 0");
                i = 0;
            } else {
                EternalFileLog.i("Eternal/XmlManager", "getManufacturer() - 1");
                i = 1;
            }
        }
        RestorePolicy restorePolicy = PolicyManager.LazyHolder.sInstance.mRestorePolicy;
        if (i == 1) {
            restorePolicy.getClass();
            ArrayList arrayList = new ArrayList(restorePolicy.mRestoreRestrictionItems);
            arrayList.removeAll(RestorePolicy.RESTORE_ALLOW_LIST_FOR_IOS);
            list = arrayList;
        } else {
            list = restorePolicy.mRestoreRestrictionItems;
        }
        list.addAll(restoreInfo.mRestoreSkipList);
        EternalFileLog.i("Eternal/PolicyManager", "getRestoreRestrictedKeys() manufacturer : " + i + " / " + list);
        Iterator it2 = hashMap.entrySet().iterator();
        while (it2.hasNext()) {
            ((List) ((Map.Entry) it2.next()).getValue()).removeAll(list);
        }
        int i6 = 0;
        while (i6 < childNodes.getLength()) {
            Node item = childNodes.item(i6);
            if (item.getNodeType() != s2) {
                s = s2;
                obj = obj2;
                nodeList = childNodes;
                i2 = i6;
                i3 = i5;
            } else {
                String uidOfBackupDataNode = getUidOfBackupDataNode(item);
                List parseBackupDataNode2 = xmlManager.parseBackupDataNode(item, (List) hashMap.get(uidOfBackupDataNode));
                if (parseBackupDataNode2 == null || (searchFromChildNode = searchFromChildNode(uidOfBackupDataNode, item)) == null) {
                    obj = obj2;
                    nodeList = childNodes;
                    i2 = i6;
                    i3 = i5;
                } else {
                    HashMap hashMap2 = new HashMap();
                    NamedNodeMap attributes = searchFromChildNode.getAttributes();
                    nodeList = childNodes;
                    int i7 = 0;
                    while (true) {
                        i2 = i6;
                        if (i7 < attributes.getLength()) {
                            Node item2 = attributes.item(i7);
                            if (item2 == null) {
                                namedNodeMap = attributes;
                            } else {
                                namedNodeMap = attributes;
                                boolean equals = FieldName.VERSION.equals(item2.getNodeName());
                                String str2 = ApnSettings.MVNO_NONE;
                                if (equals) {
                                    if (!TextUtils.isEmpty(item2.getNodeValue())) {
                                        str2 = item2.getNodeValue();
                                    }
                                    hashMap2.put(FieldName.VERSION, str2);
                                } else if ("dtd_version".equals(item2.getNodeName())) {
                                    if (!TextUtils.isEmpty(item2.getNodeValue())) {
                                        str2 = item2.getNodeValue();
                                    }
                                    hashMap2.put("dtd_version", str2);
                                }
                            }
                            i7++;
                            i6 = i2;
                            attributes = namedNodeMap;
                        } else {
                            EternalFileLog.d("Eternal/XmlManager", "parseXml() - [" + searchFromChildNode.getNodeName() + "] DTDVersion = " + ((String) hashMap2.get(FieldName.VERSION)) + " / providerVersion = " + ((String) hashMap2.get("dtd_version")));
                            Scene parseNode = xmlManager.parseNode(uidOfBackupDataNode, searchFromChildNode);
                            SourceInfo sourceInfo = new SourceInfo();
                            sourceInfo.mDeviceType = null;
                            sourceInfo.mVersion = null;
                            sourceInfo.mDTDVersion = null;
                            sourceInfo.mRestoreViaFastTrack = false;
                            sourceInfo.mRequestFrom = -1;
                            sourceInfo.mPackageList = null;
                            sourceInfo.mManufacturer = -1;
                            if (parseBackupDataNode != null) {
                                Iterator it3 = ((ArrayList) parseBackupDataNode).iterator();
                                while (it3.hasNext()) {
                                    Scene scene2 = (Scene) it3.next();
                                    String str3 = scene2.mSceneKey;
                                    str3.getClass();
                                    switch (str3.hashCode()) {
                                        case -1228368090:
                                            if (str3.equals("/GeneralInfo/OneUIVersion")) {
                                                c = 0;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case -610089000:
                                            if (str3.equals("/GeneralInfo/DeviceType")) {
                                                c = 1;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 307747916:
                                            if (str3.equals("/GeneralInfo/OSVersion")) {
                                                c = 2;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 728705500:
                                            if (str3.equals("/GeneralInfo/PackageList")) {
                                                c = 3;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        default:
                                            c = 65535;
                                            break;
                                    }
                                    switch (c) {
                                        case 0:
                                            sourceInfo.mOneUIVersion = scene2.getValue(null, false);
                                            break;
                                        case 1:
                                            sourceInfo.mDeviceType = scene2.getValue(null, false);
                                            break;
                                        case 2:
                                            Bundle bundle = scene2.mSceneValue;
                                            if (bundle != null && bundle.containsKey("value")) {
                                                String string = scene2.mSceneValue.getString("value");
                                                try {
                                                } catch (NumberFormatException e2) {
                                                    Log.e("Eternal/Scene", e2.getStackTrace()[0].toString());
                                                }
                                                if (!TextUtils.isEmpty(string)) {
                                                    i4 = Integer.valueOf(string).intValue();
                                                    sourceInfo.mOSVersion = i4;
                                                    break;
                                                }
                                            }
                                            i4 = -1;
                                            sourceInfo.mOSVersion = i4;
                                            break;
                                        case 3:
                                            sourceInfo.mPackageList = EpisodeUtils.convertStringToArrayList(EpisodeUtils.decompressString(scene2.getValue(null, false)));
                                            break;
                                    }
                                }
                            }
                            obj = null;
                            i3 = 0;
                            if (parseNode != null) {
                                sourceInfo.mVersion = parseNode.getAttribute(FieldName.VERSION);
                                sourceInfo.mDTDVersion = parseNode.getAttribute("dtd_version");
                            }
                            sourceInfo.mRequestFrom = restoreInfo.mRequestFrom;
                            sourceInfo.mRestoreViaFastTrack = restoreInfo.mIsFastTrack;
                            sourceInfo.mManufacturer = i;
                            episodeHolder.mSourceInfoMap.put(uidOfBackupDataNode, sourceInfo);
                        }
                    }
                }
                StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("parseXml() - [", uidOfBackupDataNode, "] parsedSceneList size = ");
                m.append(parseBackupDataNode2 == null ? i3 : ((ArrayList) parseBackupDataNode2).size());
                EternalFileLog.d("Eternal/XmlManager", m.toString());
                episodeHolder.putBackupSceneList(uidOfBackupDataNode, parseBackupDataNode2);
                s = 1;
            }
            i6 = i2 + 1;
            obj2 = obj;
            s2 = s;
            i5 = i3;
            childNodes = nodeList;
            xmlManager = this;
        }
        return episodeHolder;
    }

    public final Context getRemoteContextByPkgName(String str) {
        if (str == null) {
            Log.e("Eternal/XmlManager", "Fatal case : No pkgName - " + str);
            return null;
        }
        try {
            Context createPackageContext = this.mContext.createPackageContext(str, 0);
            EternalFileLog.d("Eternal/XmlManager", "getRemoteContextByPkgName() Get Context from pkg: ".concat(str));
            return createPackageContext;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("Eternal/XmlManager", "getRemoteContextByPkgName() Fatal case : No PKG with pkgName  - ".concat(str));
            return null;
        }
    }

    public final List parseBackupDataNode(Node node, List list) {
        Scene parseNode;
        if (node == null || list == null) {
            EternalFileLog.d("Eternal/XmlManager", "parseBackupDataNode() backupDataHeadNode or keySetList is null");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Node searchNode = searchNode(str, node);
            if (searchNode != null && searchNode.getFirstChild() != null && (parseNode = parseNode(str, searchNode)) != null) {
                arrayList.add(parseNode);
            }
        }
        return arrayList;
    }

    public final Scene parseNode(String str, Node node) {
        Scene.Builder builder = new Scene.Builder(str);
        if (node.hasAttributes()) {
            NamedNodeMap attributes = node.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node item = attributes.item(i);
                if (item != null) {
                    if (!"defaultValue".equals(item.getNodeName())) {
                        builder.addAttribute(item.getNodeValue(), item.getNodeName());
                    } else if (this.mEnableDefaultValueSkipPolicy) {
                        int i2 = 1;
                        builder.setDefault(true);
                        String nodeValue = item.getNodeValue();
                        try {
                            if (!TextUtils.isEmpty(nodeValue)) {
                                i2 = Integer.valueOf(nodeValue).intValue();
                            }
                        } catch (NumberFormatException unused) {
                            EternalFileLog.e("Eternal/XmlManager", "convertDefaultType() - NumberFormatException " + str);
                        }
                        builder.mDefaultType = (byte) i2;
                        if (i2 > 0) {
                            builder.mIsDefault = Boolean.TRUE;
                        }
                    }
                }
            }
        }
        builder.setValue(node.getFirstChild().getNodeValue(), false);
        return builder.build();
    }
}
