package com.print.cafeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);
        Button print = (Button)findViewById(R.id.print);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printDocument(v);
            }
        });
    }
    public void printDocument(View view)
    {
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = this.getString(R.string.app_name) +
                " Document";

        printManager.print(jobName, new MyPrintDocumentAdapter(this),
                null);
    }

}
