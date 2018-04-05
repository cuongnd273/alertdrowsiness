package university.project.cuong.alertdrowsiness.utils;

import android.content.Context;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.ml.SVM;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import university.project.cuong.alertdrowsiness.R;


/**
 * Created by cuong on 3/21/2018.
 */

public class SVMUtil {

    public static SVM loadSVM(Context context) {
        SVM clasificador = null;
        try {
            // load cascade file from application resources
            InputStream is = context.getResources().openRawResource(R.raw.svm);
            File svmDir = context.getDir("svm", Context.MODE_PRIVATE);
            File svmFile = new File(svmDir, "svm.xml");
            FileOutputStream os = new FileOutputStream(svmFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

            clasificador = SVM.load(svmFile.getAbsolutePath());

            svmDir.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return clasificador;
    }

    public static int detectStatusEye(SVM clasificador, Mat in) {
        float response = 0;
        try {
            Mat out = new Mat();
            in.convertTo(out, CvType.CV_32FC1);
            out = out.reshape(1, 1);
            response = clasificador.predict(out);
        } catch (Exception e) {
            e.printStackTrace();
            response = 0;
        }
        return (int) response;
    }
}
