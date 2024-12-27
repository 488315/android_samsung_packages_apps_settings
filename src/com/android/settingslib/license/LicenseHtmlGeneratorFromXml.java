package com.android.settingslib.license;

import android.text.TextUtils;
import android.util.Xml;

import com.samsung.android.knox.net.apn.ApnSettings;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LicenseHtmlGeneratorFromXml {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ContentIdAndFileNames {
        public final String mContentId;
        public final Map mLibraryToFileNameMap = new TreeMap();

        public ContentIdAndFileNames(String str) {
            this.mContentId = str;
        }
    }

    public static void generateHtml(
            List<File> list,
            Map<String, Map<String, Set<String>>> map,
            Map<String, String> map2,
            PrintWriter printWriter,
            String str)
            throws IOException {
        int i;
        int i2;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(map.keySet());
        Collections.sort(arrayList);
        TreeMap treeMap = new TreeMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            treeMap.merge(
                    pathPrefix((String) it.next()),
                    1,
                    new LicenseHtmlGeneratorFromXml$$ExternalSyntheticLambda2(1));
        }
        TreeMap treeMap2 = new TreeMap();
        Iterator<Map<String, Set<String>>> it2 = map.values().iterator();
        while (it2.hasNext()) {
            for (Map.Entry<String, Set<String>> entry : it2.next().entrySet()) {
                if (!TextUtils.isEmpty(entry.getKey())) {
                    treeMap2.merge(
                            entry.getKey(),
                            entry.getValue(),
                            new LicenseHtmlGeneratorFromXml$$ExternalSyntheticLambda2(2));
                }
            }
        }
        printWriter.println(
                "<html><head>\n"
                    + "<style type=\"text/css\">\n"
                    + "body { padding: 0; font-family: sans-serif; }\n"
                    + ".same-license { background-color: #eeeeee;\n"
                    + "                border-top: 20px solid white;\n"
                    + "                padding: 10px; }\n"
                    + ".label { font-weight: bold; }\n"
                    + ".file-list { margin-left: 1em; color: blue; }\n"
                    + "</style>\n"
                    + "</head><body topmargin=\"0\" leftmargin=\"0\" rightmargin=\"0\""
                    + " bottommargin=\"0\">\n"
                    + "<div class=\"toc\">");
        if (!TextUtils.isEmpty(str)) {
            printWriter.println(str);
            printWriter.println("<br/>");
        }
        HashMap hashMap = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        if (treeMap2.isEmpty()) {
            i = 0;
        } else {
            printWriter.println("<strong>Libraries</strong>\n<ul class=\"libraries\">");
            i = 0;
            for (Map.Entry entry2 : treeMap2.entrySet()) {
                String str2 = (String) entry2.getKey();
                for (String str3 : (Set) entry2.getValue()) {
                    if (!hashMap.containsKey(str3)) {
                        hashMap.put(str3, Integer.valueOf(i));
                        arrayList2.add(new ContentIdAndFileNames(str3));
                        i++;
                    }
                    Integer num = (Integer) hashMap.get(str3);
                    num.intValue();
                    printWriter.format("<li><a href=\"#id%d\">%s</a></li>\n", num, str2);
                }
            }
            printWriter.println("</ul>\n<strong>Files</strong>");
        }
        if (arrayList.isEmpty()) {
            i2 = 0;
        } else {
            printWriter.println("<ul class=\"files\">");
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                String str4 = (String) it3.next();
                for (Map.Entry<String, Set<String>> entry3 : map.get(str4).entrySet()) {
                    String key = entry3.getKey();
                    if (key == null) {
                        key = ApnSettings.MVNO_NONE;
                    }
                    for (String str5 : entry3.getValue()) {
                        if (!hashMap.containsKey(str5)) {
                            hashMap.put(str5, Integer.valueOf(i));
                            arrayList2.add(new ContentIdAndFileNames(str5));
                            i++;
                        }
                        Integer num2 = (Integer) hashMap.get(str5);
                        ((List)
                                        ((TreeMap)
                                                        ((ContentIdAndFileNames)
                                                                        arrayList2.get(
                                                                                num2.intValue()))
                                                                .mLibraryToFileNameMap)
                                                .computeIfAbsent(
                                                        key,
                                                        new LicenseHtmlGeneratorFromXml$$ExternalSyntheticLambda0(
                                                                2)))
                                .add(str4);
                        if (TextUtils.isEmpty(key)) {
                            printWriter.format("<li><a href=\"#id%d\">%s</a></li>\n", num2, str4);
                        } else {
                            printWriter.format(
                                    "<li><a href=\"#id%d\">%s - %s</a></li>\n", num2, str4, key);
                        }
                    }
                }
            }
            printWriter.format(
                    "<li><a href=\"#id%d\">%s</a></li>\n",
                    Integer.valueOf(i), "//Written Offer for Source Code");
            printWriter.println("</ul>\n</div><!-- table of contents -->");
            i2 = i;
        }
        if (!arrayList2.isEmpty()) {
            printWriter.println("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
            Iterator it4 = arrayList2.iterator();
            while (it4.hasNext()) {
                ContentIdAndFileNames contentIdAndFileNames = (ContentIdAndFileNames) it4.next();
                boolean containsKey = hashMap.containsKey(contentIdAndFileNames.mContentId);
                String str6 = contentIdAndFileNames.mContentId;
                if (!containsKey) {
                    hashMap.put(str6, Integer.valueOf(i));
                    i++;
                }
                Integer num3 = (Integer) hashMap.get(str6);
                num3.intValue();
                printWriter.format("<tr id=\"id%d\"><td class=\"same-license\">\n", num3);
                for (Map.Entry entry4 :
                        ((TreeMap) contentIdAndFileNames.mLibraryToFileNameMap).entrySet()) {
                    String str7 = (String) entry4.getKey();
                    if (TextUtils.isEmpty(str7)) {
                        printWriter.println("<div class=\"label\">Notices for file(s):</div>");
                    } else {
                        printWriter.format(
                                "<div class=\"label\"><strong>%s</strong> used by:</div>\n", str7);
                    }
                    printWriter.println("<div class=\"file-list\">");
                    Iterator it5 = ((List) entry4.getValue()).iterator();
                    while (it5.hasNext()) {
                        printWriter.format("%s <br/>\n", (String) it5.next());
                    }
                    printWriter.println("</div><!-- file-list -->");
                }
                printWriter.println("<pre class=\"license-text\">");
                printWriter.println(map2.get(str6));
                printWriter.println("</pre><!-- license-text -->");
                printWriter.println("</td></tr><!-- same-license -->");
            }
            printWriter.format(
                    "<tr id=\"id%d\"><td class=\"same-license\">\n", Integer.valueOf(i2));
            printWriter.format(
                    "<div class=\"file-list\">//Written Offer for Source Code<br/></div><!--"
                        + " file-list -->",
                    new Object[0]);
            printWriter.println("<pre class=\"license-text\">");
            printWriter.println(
                    "The software included in this product contains open source software.");
            printWriter.println(
                    "To obtain the source code covered under licenses which have the obligation");
            printWriter.println("of publishing source code(e.g. GPL, LGPL, MPL...etc),");
            printWriter.println(
                    "please visit http://opensource.samsung.com/ and search by model name.");
            printWriter.println(
                    "A complete corresponding source code may be obtained for a period of three"
                        + " years");
            printWriter.println("after our last shipment of this product by visiting our website.");
            printWriter.println(
                    "If you want to obtain a complete corresponding source code in the physical"
                        + " medium");
            printWriter.println(
                    "such as CD-ROM, the cost of physically performing source distribution may be"
                        + " charged.");
            printWriter.println("This offer is valid to anyone in receipt of this information.");
            printWriter.println("</pre><!-- license-text -->");
            printWriter.println("</td></tr><!-- same-license -->");
            printWriter.println("</table>");
        }
        if (!list.isEmpty()) {
            printWriter.println(
                    "<div class=\"images-list\"><strong>Images</strong>\n<ul class=\"images\">");
            Iterator<File> it6 = list.iterator();
            while (it6.hasNext()) {
                printWriter.format("  <li>%s</li>\n", pathPrefix(it6.next().getCanonicalPath()));
            }
            printWriter.println("</ul></div>\n");
        }
        if (!treeMap.isEmpty()) {
            printWriter.println(
                    "<div class=\"path-counts\"><table>\n"
                        + "  <tr><th>Path prefix</th><th>Count</th></tr>\n");
            for (Map.Entry entry5 : treeMap.entrySet()) {
                printWriter.format(
                        "  <tr><td>%s</td><td>%d</td></tr>\n", entry5.getKey(), entry5.getValue());
            }
            printWriter.println("</table></div>\n");
        }
        printWriter.println("</body></html>");
    }

    public static void parse(
            InputStreamReader inputStreamReader,
            Map<String, Map<String, Set<String>>> map,
            Map<String, String> map2)
            throws XmlPullParserException, IOException {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStreamReader);
        newPullParser.nextTag();
        newPullParser.require(2, ApnSettings.MVNO_NONE, "licenses");
        for (int eventType = newPullParser.getEventType();
                eventType != 1;
                eventType = newPullParser.next()) {
            if (eventType == 2) {
                if ("file-name".equals(newPullParser.getName())) {
                    String attributeValue =
                            newPullParser.getAttributeValue(ApnSettings.MVNO_NONE, "contentId");
                    String attributeValue2 =
                            newPullParser.getAttributeValue(ApnSettings.MVNO_NONE, "lib");
                    if (!TextUtils.isEmpty(attributeValue)) {
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int next = newPullParser.next();
                                next == 4;
                                next = newPullParser.next()) {
                            stringBuffer.append(newPullParser.getText());
                        }
                        String trim = stringBuffer.toString().trim();
                        if (!TextUtils.isEmpty(trim)) {
                            ((Set)
                                            ((Map)
                                                            hashMap.computeIfAbsent(
                                                                    trim,
                                                                    new LicenseHtmlGeneratorFromXml$$ExternalSyntheticLambda0(
                                                                            0)))
                                                    .computeIfAbsent(
                                                            attributeValue2,
                                                            new LicenseHtmlGeneratorFromXml$$ExternalSyntheticLambda0(
                                                                    1)))
                                    .add(attributeValue);
                        }
                    }
                } else if ("file-content".equals(newPullParser.getName())) {
                    String attributeValue3 =
                            newPullParser.getAttributeValue(ApnSettings.MVNO_NONE, "contentId");
                    if (!TextUtils.isEmpty(attributeValue3)
                            && !map2.containsKey(attributeValue3)
                            && !hashMap2.containsKey(attributeValue3)) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        for (int next2 = newPullParser.next();
                                next2 == 4;
                                next2 = newPullParser.next()) {
                            stringBuffer2.append(newPullParser.getText());
                        }
                        String stringBuffer3 = stringBuffer2.toString();
                        if (!TextUtils.isEmpty(stringBuffer3)) {
                            hashMap2.put(attributeValue3, stringBuffer3);
                        }
                    }
                }
            }
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            map.merge(
                    (String) entry.getKey(),
                    (Map) entry.getValue(),
                    new LicenseHtmlGeneratorFromXml$$ExternalSyntheticLambda2(0));
        }
        map2.putAll(hashMap2);
    }

    public static String pathPrefix(String str) {
        while (str.length() > 0 && str.substring(0, 1).equals("/")) {
            str = str.substring(1);
        }
        int indexOf = str.indexOf("/");
        return indexOf > 0 ? str.substring(0, indexOf) : str;
    }
}
