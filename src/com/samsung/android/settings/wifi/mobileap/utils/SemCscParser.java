package com.samsung.android.settings.wifi.mobileap.utils;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;

import com.samsung.android.knox.net.apn.ApnSettings;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SemCscParser {

    @VisibleForTesting static final String DEFAULT_CUSTOM_XML_FILE = "/system/csc/customer.xml";

    @VisibleForTesting static final String PROPERTY_PERSIST_SYS_OMC_PATH = "persist.sys.omc_path";
    public final Node xmlRootNode;

    public SemCscParser(String str) {
        if (!Files.isReadable(Paths.get(str, new String[0]))) {
            throw new IllegalArgumentException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "could not access ", str));
        }
        try {
            InputStream newInputStream =
                    Files.newInputStream(Paths.get(str, new String[0]), new OpenOption[0]);
            try {
                Element documentElement =
                        DocumentBuilderFactory.newInstance()
                                .newDocumentBuilder()
                                .parse(newInputStream)
                                .getDocumentElement();
                if (newInputStream != null) {
                    newInputStream.close();
                }
                this.xmlRootNode = documentElement;
            } catch (Throwable th) {
                if (newInputStream != null) {
                    try {
                        newInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "could not parse file ", str));
        }
    }

    public static String getRegion() {
        String str;
        try {
            String str2 =
                    SystemProperties.get(PROPERTY_PERSIST_SYS_OMC_PATH, ApnSettings.MVNO_NONE);
            if (!TextUtils.isEmpty(str2)) {
                str = str2 + "/customer.xml";
                if (Files.isReadable(Paths.get(str, new String[0]))) {
                    String str3 = new SemCscParser(str).get();
                    Log.i("SemCscParser_mhs_ui", "csc region: " + str3);
                    return str3;
                }
            }
            str = DEFAULT_CUSTOM_XML_FILE;
            String str32 = new SemCscParser(str).get();
            Log.i("SemCscParser_mhs_ui", "csc region: " + str32);
            return str32;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ApnSettings.MVNO_NONE;
        }
    }

    public final String get() {
        Node node = this.xmlRootNode;
        StringTokenizer stringTokenizer = new StringTokenizer("GeneralInfo.Region", ".");
        while (true) {
            if (!stringTokenizer.hasMoreTokens()) {
                break;
            }
            String nextToken = stringTokenizer.nextToken();
            if (node == null) {
                node = null;
                break;
            }
            NodeList childNodes = node.getChildNodes();
            if (childNodes != null) {
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node item = childNodes.item(i);
                    if (item.getNodeName().equals(nextToken)) {
                        node = item;
                        break;
                    }
                }
            }
            node = null;
        }
        String nodeValue =
                (node == null || node.getFirstChild() == null)
                        ? ApnSettings.MVNO_NONE
                        : node.getFirstChild().getNodeValue();
        return TextUtils.isEmpty(nodeValue) ? ApnSettings.MVNO_NONE : nodeValue;
    }
}
