package com.sunita.tabviewlist.setting;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.sunita.tabviewlist.R;
 
public class UserSettingsActivity extends PreferenceActivity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.xml.settings);
 
    }
}
