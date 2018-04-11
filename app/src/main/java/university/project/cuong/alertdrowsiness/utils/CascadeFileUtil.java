package university.project.cuong.alertdrowsiness.utils;

import android.content.Context;

import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import university.project.cuong.alertdrowsiness.R;


/**
 * Created by cuong on 3/13/2018.
 */

public class CascadeFileUtil {
    public static CascadeClassifier loadCascadeFileFace(Context context) {
        CascadeClassifier mJavaDetector = null;
        try {
            // load cascade file from application resources
            InputStream is = context.getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
            File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_alt.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

            mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            if (mJavaDetector.empty()) {
                mJavaDetector = null;
            }

            cascadeDir.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mJavaDetector;
    }

    public static CascadeClassifier loadCascadeFileEyeRight(Context context) {
        CascadeClassifier mJavaDetector = null;
        try {
            // load cascade file from application resources
            InputStream is = context.getResources().openRawResource(R.raw.haarcascade_eye);
            File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "haarcascade_eye.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

            mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            if (mJavaDetector.empty()) {
                mJavaDetector = null;
            }

            cascadeDir.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mJavaDetector;
    }

    public static CascadeClassifier loadCascadeFileEyeLeft(Context context) {
        CascadeClassifier mJavaDetector = null;
        try {
            // load cascade file from application resources
            InputStream is = context.getResources().openRawResource(R.raw.haarcascade_eye);
            File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "haarcascade_eye.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

            mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            if (mJavaDetector.empty()) {
                mJavaDetector = null;
            }

            cascadeDir.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mJavaDetector;
    }
}
