package com.sec.ims.presence;

import android.util.Pair;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class PersonTuple {
    public List<Pair<String, String>> mNotes;
    public String mStatusIcon;
    public String mTimestamp;

    public PersonTuple() {
        this.mStatusIcon = null;
        this.mNotes = null;
        this.mTimestamp = null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PersonTuple personTuple = (PersonTuple) obj;
        String str = this.mStatusIcon;
        if (str == null) {
            if (personTuple.mStatusIcon != null) {
                return false;
            }
        } else if (!str.equals(personTuple.mStatusIcon)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.mStatusIcon;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        List<Pair<String, String>> list = this.mNotes;
        int hashCode2 = (hashCode + (list == null ? 0 : list.hashCode())) * 31;
        String str2 = this.mTimestamp;
        return hashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    public PersonTuple(String str, List<Pair<String, String>> list, String str2) {
        this.mStatusIcon = str;
        this.mNotes = list;
        this.mTimestamp = str2;
    }
}
