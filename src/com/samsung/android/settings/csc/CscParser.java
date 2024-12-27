package com.samsung.android.settings.csc;

import android.os.SystemProperties;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.StringTokenizer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CscParser {
    public Node mRoot;

    public static String getOmcPath() {
        return SystemProperties.get(
                "persist.sys.omcnw_path", SystemProperties.get("persist.sys.omc_path"));
    }

    public final String get(String str) {
        Node firstChild;
        Node node = this.mRoot;
        StringTokenizer stringTokenizer = new StringTokenizer(str, ".");
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
        }
        if (node == null || (firstChild = node.getFirstChild()) == null) {
            return null;
        }
        return firstChild.getNodeValue();
    }
}
