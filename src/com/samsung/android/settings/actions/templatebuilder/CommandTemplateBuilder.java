package com.samsung.android.settings.actions.templatebuilder;

import android.text.TextUtils;
import android.util.Log;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SecSingleChoicePreferenceController;
import com.android.settings.core.SliderPreferenceController;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.sdk.command.template.CommandTemplate;
import com.samsung.android.sdk.command.template.SingleChoiceTemplate;
import com.samsung.android.sdk.command.template.SliderTemplate;
import com.samsung.android.sdk.command.template.ToggleTemplate;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CommandTemplateBuilder {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.sdk.command.template.CommandTemplate$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.samsung.android.sdk.command.template.CommandTemplate, com.samsung.android.sdk.command.template.ToggleTemplate] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.samsung.android.sdk.command.template.CommandTemplate, com.samsung.android.sdk.command.template.SingleChoiceTemplate] */
    public static CommandTemplate build(BasePreferenceController basePreferenceController) {
        int controlType = basePreferenceController.getControlType();
        CommandTemplate commandTemplate = CommandTemplate.ERROR_TEMPLATE;
        if (controlType != 1) {
            if (controlType == 2 || controlType == 100) {
                if (basePreferenceController instanceof SliderPreferenceController) {
                    SliderPreferenceController sliderPreferenceController =
                            (SliderPreferenceController) basePreferenceController;
                    int min = sliderPreferenceController.getMin();
                    int max = sliderPreferenceController.getMax();
                    int sliderPosition = sliderPreferenceController.getSliderPosition();
                    Log.i(
                            "Command/CommandTemplateBuilder",
                            "buildRangeTemplate() "
                                    + basePreferenceController.getPreferenceKey()
                                    + " : "
                                    + sliderPosition);
                    commandTemplate =
                            new SliderTemplate((float) min, (float) max, (float) sliderPosition);
                }
            } else {
                if (controlType != 101) {
                    return CommandTemplate.NO_TEMPLATE;
                }
                if (basePreferenceController instanceof SecSingleChoicePreferenceController) {
                    SecSingleChoicePreferenceController secSingleChoicePreferenceController =
                            (SecSingleChoicePreferenceController) basePreferenceController;
                    ArrayList<String> entries = secSingleChoicePreferenceController.getEntries();
                    ArrayList<String> subEntries =
                            secSingleChoicePreferenceController.getSubEntries();
                    ArrayList<String> entryValues =
                            secSingleChoicePreferenceController.getEntryValues();
                    ArrayList<Integer> imageEntries =
                            secSingleChoicePreferenceController.getImageEntries();
                    String selectedValue = secSingleChoicePreferenceController.getSelectedValue();
                    int size = entries.size();
                    ArrayList arrayList = new ArrayList();
                    if (selectedValue == null) {
                        throw new IllegalArgumentException("active value must be set");
                    }
                    Log.i(
                            "Command/CommandTemplateBuilder",
                            "buildSingleChoiceTemplate() "
                                    + basePreferenceController.getPreferenceKey()
                                    + " : "
                                    + selectedValue);
                    int i = 0;
                    while (i < size) {
                        String str = entries.get(i);
                        String str2 = null;
                        String str3 =
                                (subEntries == null || subEntries.size() <= i)
                                        ? null
                                        : subEntries.get(i);
                        int intValue =
                                (imageEntries == null || imageEntries.size() <= i)
                                        ? 0
                                        : imageEntries.get(i).intValue();
                        if (entryValues != null && entryValues.size() > i) {
                            str2 = entryValues.get(i);
                        }
                        if (str2 != null) {
                            SingleChoiceTemplate.Entry entry =
                                    new SingleChoiceTemplate.Entry(str, str3, intValue, str2);
                            if (!arrayList.contains(entry)) {
                                arrayList.add(entry);
                            }
                        }
                        i++;
                    }
                    if (arrayList.isEmpty()) {
                        throw new IllegalArgumentException("more than one entry must be added");
                    }
                    commandTemplate = new SingleChoiceTemplate("singlechoice");
                    commandTemplate.mEntryPrimaryTitleList = new ArrayList();
                    commandTemplate.mEntrySecondaryTitleList = new ArrayList();
                    commandTemplate.mEntryValueList = new ArrayList();
                    commandTemplate.mEntryImageList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    commandTemplate.mEntries = arrayList2;
                    commandTemplate.mCurrentActiveValue = selectedValue;
                    if (arrayList.size() > 0) {
                        arrayList2.addAll(arrayList);
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            SingleChoiceTemplate.Entry entry2 =
                                    (SingleChoiceTemplate.Entry) it.next();
                            String str4 = entry2.mPrimaryTitle;
                            if (!TextUtils.isEmpty(str4)) {
                                commandTemplate.mEntryPrimaryTitleList.add(str4);
                            }
                            String str5 = entry2.mSecondaryTitle;
                            if (!TextUtils.isEmpty(str5)) {
                                commandTemplate.mEntrySecondaryTitleList.add(str5);
                            }
                            int i2 = entry2.mIconResId;
                            if (i2 > 0) {
                                commandTemplate.mEntryImageList.add(Integer.valueOf(i2));
                            }
                            String str6 = entry2.mValue;
                            if (!TextUtils.isEmpty(str6)) {
                                commandTemplate.mEntryValueList.add(str6);
                            }
                        }
                    }
                }
            }
        } else if (basePreferenceController instanceof TogglePreferenceController) {
            boolean threadEnabled =
                    ((TogglePreferenceController) basePreferenceController).getThreadEnabled();
            Log.i(
                    "Command/CommandTemplateBuilder",
                    "buildToggleTemplate() "
                            + basePreferenceController.getPreferenceKey()
                            + " : "
                            + threadEnabled);
            commandTemplate = new ToggleTemplate("toggle");
            commandTemplate.mIsChecked = threadEnabled;
        }
        return commandTemplate;
    }
}
