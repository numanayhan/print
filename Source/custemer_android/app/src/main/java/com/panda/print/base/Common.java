package com.panda.print.base;

import android.content.Context;
import android.os.Environment;

import com.panda.print.R;

import java.io.File;

public class Common {

    public static String getAppPath(Context context){
        File dir = new File(new File(Environment.getExternalStorageState())
                + File.separator + context.getResources().getString(R.string.app_name));

        if (!dir.exists())
            dir.mkdir();
            return dir.getPath() + File.separator;
    }
}
