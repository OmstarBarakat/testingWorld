package org.testinglab.mytgcompination.Utils;

/**
 * Created by omstar on 01/03/2016.
 */

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class UIHelper {
    public static void displayText(Activity activity, int id, String text) {
        TextView tv = (TextView) activity.findViewById(id);
        tv.setText(text);
    }

    public static String getText(Activity activity, int id) {
        EditText et = (EditText) activity.findViewById(id);
        return et.getText().toString();
    }

    public static boolean getCBFlag(Activity activity, int id) {
        CheckBox cb = (CheckBox) activity.findViewById(id);
        return cb.isChecked();
    }

    public static void setCBFlag(Activity activity, int id, boolean value) {
        CheckBox cb = (CheckBox) activity.findViewById(id);
        cb.setChecked(value);
    }
}
