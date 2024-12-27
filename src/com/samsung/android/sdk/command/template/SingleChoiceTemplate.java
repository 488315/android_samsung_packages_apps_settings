package com.samsung.android.sdk.command.template;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SingleChoiceTemplate extends CommandTemplate {
    public String mCurrentActiveValue;
    public List mEntries;
    public ArrayList mEntryImageList;
    public ArrayList mEntryPrimaryTitleList;
    public ArrayList mEntrySecondaryTitleList;
    public ArrayList mEntryValueList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Entry {
        public final int mIconResId;
        public final String mPrimaryTitle;
        public final String mSecondaryTitle;
        public final String mValue;

        public Entry(String str, String str2, int i, String str3) {
            this.mPrimaryTitle = str;
            this.mSecondaryTitle = str2;
            this.mIconResId = i;
            this.mValue = str3;
        }
    }

    public SingleChoiceTemplate(Bundle bundle) {
        super(bundle);
        this.mEntryPrimaryTitleList = new ArrayList();
        this.mEntrySecondaryTitleList = new ArrayList();
        this.mEntryValueList = new ArrayList();
        this.mEntryImageList = new ArrayList();
        this.mEntries = new ArrayList();
        this.mCurrentActiveValue = bundle.getString("key_current_active_mode_value");
        ArrayList<String> stringArrayList =
                bundle.getStringArrayList("key_entry_primary_title_list");
        this.mEntryPrimaryTitleList = stringArrayList;
        this.mEntrySecondaryTitleList = bundle.getStringArrayList("key_entry_secondary_title_list");
        this.mEntryValueList = bundle.getStringArrayList("key_entry_value_list");
        this.mEntryImageList = bundle.getIntegerArrayList("key_entry_image_list");
        if (stringArrayList != null) {
            int i = 0;
            while (i < this.mEntryPrimaryTitleList.size()) {
                String str = (String) this.mEntryPrimaryTitleList.get(i);
                ArrayList arrayList = this.mEntrySecondaryTitleList;
                String str2 = null;
                String str3 =
                        (arrayList == null || arrayList.size() <= i)
                                ? null
                                : (String) this.mEntrySecondaryTitleList.get(i);
                ArrayList arrayList2 = this.mEntryImageList;
                int intValue =
                        (arrayList2 == null || arrayList2.size() <= i)
                                ? 0
                                : ((Integer) this.mEntryImageList.get(i)).intValue();
                ArrayList arrayList3 = this.mEntryValueList;
                if (arrayList3 != null && arrayList3.size() > i) {
                    str2 = (String) this.mEntryValueList.get(i);
                }
                this.mEntries.add(new Entry(str, str3, intValue, str2));
                i++;
            }
        }
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putString("key_current_active_mode_value", this.mCurrentActiveValue);
        dataBundle.putStringArrayList("key_entry_primary_title_list", this.mEntryPrimaryTitleList);
        dataBundle.putStringArrayList(
                "key_entry_secondary_title_list", this.mEntrySecondaryTitleList);
        dataBundle.putStringArrayList("key_entry_value_list", this.mEntryValueList);
        dataBundle.putIntegerArrayList("key_entry_image_list", this.mEntryImageList);
        return dataBundle;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final int getTemplateType() {
        return 5;
    }
}
