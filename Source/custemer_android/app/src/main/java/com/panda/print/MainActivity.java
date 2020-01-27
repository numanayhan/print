package com.panda.print;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.panda.print.base.BaseDevice;
import com.panda.print.base.Common;
import com.panda.print.base.PdfDocumentAdaptor;
import com.panda.print.base.PrinterSearchHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PrinterSearchHelper.ScanListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterSearchHelper printerSearchUtils = PrinterSearchHelper.getInstance(MainActivity.this, 50);
                printerSearchUtils.setScanListener(MainActivity.this);
                //printerSearchUtils.addMac("00:1a:4b");
                printerSearchUtils.startScan();
            }
        });*/

    }
    private void createFile(String path){
        if (new File(path).exists())
            new File(path).delete();
        try {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document,new FileOutputStream(path));
                document.open();
                document.setPageSize(PageSize.A4);
                document.addCreationDate();
                BaseColor baseColor = new BaseColor(0,153,204,255);
                float fontSize = 20.0f;
                float valueFontSize = 26.0f;

                //Create Fİle
                addNewItem(document,"Print Numan", Element.ALIGN_CENTER);

                printPDF();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private  void printPDF(){
        PrintManager printManager =   (PrintManager)getSystemService(Context.PRINT_SERVICE);
        try {

            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdaptor(MainActivity.this,Common.getAppPath(MainActivity.this) + "numan.pdf");
            printManager.print("Document",printDocumentAdapter,new PrintAttributes.Builder().build());

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void addNewItem(Document document,String text,int align){
        Chunk chunk = new Chunk(text);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
    @Override
    public <T extends BaseDevice> void scanOver(List<T> t) {
        Log.d("Tanck", "");
        if (0 < t.size()) {
            Log.d("Tanck", t.get(0).ip + "--" + t.get(0).mac + "--size:" + t.size());
        }
    }

    @Override
    public void currentPosition(int progress) {
        Log.d("Tanck", "-----" + progress);
    }
}

