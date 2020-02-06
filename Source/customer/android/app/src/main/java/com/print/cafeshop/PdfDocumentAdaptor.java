package com.print.cafeshop;

import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.util.Log;

import com.itextpdf.text.pdf.codec.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PdfDocumentAdaptor extends PrintDocumentAdapter {
    Context context;
    String path;
    public PdfDocumentAdaptor(Context context, String path) {
        this.context = context;
        this.path = path;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        if (cancellationSignal.isCanceled()){
            onFinish();
        }else {
            PrintDocumentInfo.Builder  builder = new PrintDocumentInfo.Builder("coffee");
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                    .build();
            callback.onLayoutFinished(builder.build(),!newAttributes.equals(oldAttributes));
        }
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            File file = new File(path);
            inputStream  = new FileInputStream(file);
            outputStream = new FileOutputStream(destination.getFileDescriptor());
            byte[] bytes = new byte[16384];
            int size ;
            while ((size = inputStream.read(bytes)) >= 0 && !cancellationSignal.isCanceled()){
                outputStream.write(bytes,0,size);

            }
            if (cancellationSignal.isCanceled()){
                callback.onWriteCancelled();
            }else{
                callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
            }
        }catch (Exception e){
            e.printStackTrace();
            callback.onWriteFailed(e.getMessage());
            Log.d("error",e.getMessage());

        }
        finally {
            try{
                inputStream.close();
                outputStream.close();

            }catch (Exception e){
                e.printStackTrace();
                Log.d("error final",e.getMessage());
            }
        }
    }
}
