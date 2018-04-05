package university.project.cuong.alertdrowsiness.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.SVM;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import university.project.cuong.alertdrowsiness.R;
import university.project.cuong.alertdrowsiness.utils.CascadeFileUtil;
import university.project.cuong.alertdrowsiness.utils.SVMUtil;


public class DetectDrowsinessActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2, View.OnClickListener {
    private JavaCameraView cameraView;
    private Mat mRgba;
    private Mat mGray;
    private Mat teplateR;
    private Mat teplateL;
    private Mat mResult;
    private Mat mZoomCorner;
    private Mat mZoomWindow;
    private Mat mZoomWindow2;
    CascadeClassifier mJavaDetectorFace;
    CascadeClassifier mCascadeER;
    CascadeClassifier mCascadeEL;
    private double match_value;
    private Rect eyearea = new Rect();
    private float mRelativeFaceSize = 0.2f;
    private int mAbsoluteFaceSize = 0;
    private int learn_frames = 0;
    public static int method = 1;

    private static final int TM_SQDIFF = 0;
    private static final int TM_SQDIFF_NORMED = 1;
    private static final int TM_CCOEFF = 2;
    private static final int TM_CCOEFF_NORMED = 3;
    private static final int TM_CCORR = 4;
    private static final int TM_CCORR_NORMED = 5;
    private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
    private static final Scalar EYE_RECT_COLOR = new Scalar(0, 255, 255, 255);
    private static final int EYE_OPEN = 1;
    private static final int EYE_CLOSE = -1;
    private static final long TIME_CLOSE_MILISECONDS = 1500;
    protected PowerManager.WakeLock mWakeLock;

    long timeClose = System.currentTimeMillis();
    boolean closeEye = false;
    SVM svm;
    MediaPlayer alarm;
    BaseLoaderCallback callback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case BaseLoaderCallback.SUCCESS:
                    mJavaDetectorFace = CascadeFileUtil.loadCascadeFileFace(DetectDrowsinessActivity.this);
                    mCascadeEL = CascadeFileUtil.loadCascadeFileEyeLeft(DetectDrowsinessActivity.this);
                    mCascadeER = CascadeFileUtil.loadCascadeFileEyeRight(DetectDrowsinessActivity.this);
                    svm = SVMUtil.loadSVM(DetectDrowsinessActivity.this);
                    cameraView.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_drowsiness);
        getControls();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()) {
            callback.onManagerConnected(BaseLoaderCallback.SUCCESS);
        } else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, callback);
        }
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "Alert");
        this.mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraView != null)
            cameraView.disableView();
        if (alarm.isPlaying())
            alarm.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mWakeLock.release();
    }

    void getControls() {
        cameraView = findViewById(R.id.java_camera_view);
        cameraView.setEnabled(true);
        cameraView.setCameraIndex(1);
        cameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();
        Imgproc.equalizeHist(mGray, mGray);
        if (mAbsoluteFaceSize == 0) {
            int height = mGray.rows();
            if (Math.round(height * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
            }
        }

        MatOfRect faces = new MatOfRect();
        if (mJavaDetectorFace != null)
            mJavaDetectorFace.detectMultiScale(mGray, faces, 1.1, 2, 2,
                    new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
        if (mZoomCorner == null || mZoomWindow == null)
            CreateAuxiliaryMats();
        Rect[] facesArray = faces.toArray();
        for (int i = 0; i < facesArray.length; i++) {
            Rect r = facesArray[i];
            Imgproc.rectangle(mGray, r.tl(), r.br(), new Scalar(0, 255, 0, 255), 3);
            Imgproc.rectangle(mRgba, r.tl(), r.br(), new Scalar(0, 255, 0, 255), 3);

            eyearea = new Rect(r.x + r.width / 8, (int) (r.y + (r.height / 4.5)), r.width - 2 * r.width / 8, (int) (r.height / 3.0));
            Imgproc.rectangle(mRgba, eyearea.tl(), eyearea.br(), new Scalar(255, 0, 0, 255), 2);
            Rect eyearea_right = new Rect(r.x + r.width / 16, (int) (r.y + (r.height / 4.5)), (r.width - 2 * r.width / 16) / 2, (int) (r.height / 3.0));
            Rect eyearea_left = new Rect(r.x + r.width / 16 + (r.width - 2 * r.width / 16) / 2, (int) (r.y + (r.height / 4.5)), (r.width - 2 * r.width / 16) / 2, (int) (r.height / 3.0));
            Imgproc.rectangle(mRgba, eyearea_left.tl(), eyearea_left.br(), new Scalar(255, 0, 0, 255), 2);
            Imgproc.rectangle(mRgba, eyearea_right.tl(), eyearea_right.br(), new Scalar(255, 0, 0, 255), 2);

            boolean eyeRight = false, eyeLeft = false;
            int status = 0;
            //phat hien mat phai
            Mat mROIRight = mGray.submat(eyearea_right);
            MatOfRect eyesRight = new MatOfRect();
            mCascadeER.detectMultiScale(mROIRight, eyesRight, 1.15, 2, Objdetect.CASCADE_SCALE_IMAGE, new Size(30, 30), new Size());
            Rect[] eyesRightArray = eyesRight.toArray();
            for (int j = 0; j < eyesRightArray.length; j++) {
                Rect e = eyesRightArray[j];
                e.x = eyearea_right.x + e.x;
                e.y = eyearea_right.y + e.y;
                Imgproc.rectangle(mRgba, e.tl(), e.br(), new Scalar(255, 255, 0, 255), 2);
                if (e.x > e.y)
                    e.x = e.y;
                else
                    e.y = e.x;
                //Xacs dinh trang thai cua mat
                Mat eye = mGray.submat(e);
                Imgproc.resize(eye, eye, new Size(24, 24));
                status = SVMUtil.detectStatusEye(svm, eye);
                if (status == EYE_OPEN)
                    eyeRight = true;
                else if (status == EYE_CLOSE)
                    eyeRight = false;
                Log.i("Status Eye Right", String.valueOf(status));
                break;
            }

            //phat hien mat trai
            Mat mROILeft = mGray.submat(eyearea_right);
            MatOfRect eyesLeft = new MatOfRect();
            mCascadeER.detectMultiScale(mROILeft, eyesLeft, 1.15, 2, Objdetect.CASCADE_SCALE_IMAGE, new Size(30, 30), new Size());
            Rect[] eyesLeftArray = eyesLeft.toArray();
            for (int j = 0; j < eyesLeftArray.length; j++) {
                Rect e = eyesLeftArray[j];
                e.x = eyearea_left.x + e.x;
                e.y = eyearea_left.y + e.y;
                Imgproc.rectangle(mRgba, e.tl(), e.br(), new Scalar(255, 255, 0, 255), 2);
                if (e.x > e.y)
                    e.x = e.y;
                else
                    e.y = e.x;
                //Xacs dinh trang thai cua mat
                Mat eye = mGray.submat(e);
                Imgproc.resize(eye, eye, new Size(24, 24));
                status = SVMUtil.detectStatusEye(svm, eye);
                if (status == EYE_OPEN)
                    eyeLeft = true;
                else if (status == EYE_CLOSE)
                    eyeLeft = false;
                Log.i("Status Eye Left", String.valueOf(status));
                break;
            }
//            if (learn_frames < 5) {
//                teplateR = get_template(mCascadeER, eyearea_right, 24);
//                teplateL = get_template(mCascadeEL, eyearea_left, 24);
//                learn_frames++;
//            } else {
//
//
//                match_value = match_eye(eyearea_right, teplateR, DetectDrowsinessActivity.method);
//
//                match_value = match_eye(eyearea_left, teplateL, DetectDrowsinessActivity.method);
//            }
//            Imgproc.resize(eyearea_right, mZoomWindow2, mZoomWindow2.size());
//            Imgproc.resize(mRgba.submat(eyearea_right), mZoomWindow, mZoomWindow.size());
            break;
        }

        return mRgba;
    }

    private void CreateAuxiliaryMats() {
        if (mGray.empty())
            return;

        int rows = mGray.rows();
        int cols = mGray.cols();

        if (mZoomWindow == null) {
            mZoomWindow = mRgba.submat(rows / 2 + rows / 10, rows, cols / 2 + cols / 10, cols);
            mZoomWindow2 = mRgba.submat(0, rows / 2 - rows / 10, cols / 2 + cols / 10, cols);
        }

    }

    private double match_eye(Rect area, Mat mTemplate, int type) {
        Point matchLoc;
        Mat mROI = mGray.submat(area);
        int result_cols = mGray.cols() - mTemplate.cols() + 1;
        int result_rows = mGray.rows() - mTemplate.rows() + 1;
        if (mTemplate.cols() == 0 || mTemplate.rows() == 0) {
            return 0.0;
        }
        mResult = new Mat(result_cols, result_rows, CvType.CV_32FC1);

        switch (type) {
            case TM_SQDIFF:
                Imgproc.matchTemplate(mROI, mTemplate, mResult, Imgproc.TM_SQDIFF);
                break;
            case TM_SQDIFF_NORMED:
                Imgproc.matchTemplate(mROI, mTemplate, mResult, Imgproc.TM_SQDIFF_NORMED);
                break;
            case TM_CCOEFF:
                Imgproc.matchTemplate(mROI, mTemplate, mResult, Imgproc.TM_CCOEFF);
                break;
            case TM_CCOEFF_NORMED:
                Imgproc.matchTemplate(mROI, mTemplate, mResult, Imgproc.TM_CCOEFF_NORMED);
                break;
            case TM_CCORR:
                Imgproc.matchTemplate(mROI, mTemplate, mResult, Imgproc.TM_CCORR);
                break;
            case TM_CCORR_NORMED:
                Imgproc.matchTemplate(mROI, mTemplate, mResult, Imgproc.TM_CCORR_NORMED);
                break;
        }

        Core.MinMaxLocResult mmres = Core.minMaxLoc(mResult);

        if (type == TM_SQDIFF || type == TM_SQDIFF_NORMED) {
            matchLoc = mmres.minLoc;
        } else {
            matchLoc = mmres.maxLoc;
        }

        Point matchLoc_tx = new Point(matchLoc.x + area.x, matchLoc.y + area.y);
        Point matchLoc_ty = new Point(matchLoc.x + mTemplate.cols() + area.x, matchLoc.y + mTemplate.rows() + area.y);

        Imgproc.rectangle(mRgba, matchLoc_tx, matchLoc_ty, new Scalar(255, 255, 0, 255));

        if (type == TM_SQDIFF || type == TM_SQDIFF_NORMED) {
            return mmres.maxVal;
        } else {
            return mmres.minVal;
        }

    }

    private Mat get_template(CascadeClassifier clasificator, Rect area, int size) {
        Mat template = new Mat();
        Mat mROI = mGray.submat(area);
        MatOfRect eyes = new MatOfRect();
        Point iris = new Point();
        Rect eye_template = new Rect();
        clasificator.detectMultiScale(mROI, eyes, 1.15, 2, Objdetect.CASCADE_FIND_BIGGEST_OBJECT | Objdetect.CASCADE_SCALE_IMAGE, new Size(30, 30), new Size());


        Rect[] eyesArray = eyes.toArray();
        for (int i = 0; i < eyesArray.length; i++) {
            Rect e = eyesArray[i];
            e.x = area.x + e.x;
            e.y = area.y + e.y;
            Rect eye_only_rectangle = new Rect((int) e.tl().x, (int) (e.tl().y + e.height * 0.4), (int) e.width, (int) (e.height * 0.6));
            mROI = mGray.submat(eye_only_rectangle);
            Mat vyrez = mRgba.submat(eye_only_rectangle);
            Core.MinMaxLocResult mmG = Core.minMaxLoc(mROI);

            Imgproc.circle(vyrez, mmG.minLoc, 2, new Scalar(255, 255, 255, 255), 2);
            iris.x = mmG.minLoc.x + eye_only_rectangle.x;
            iris.y = mmG.minLoc.y + eye_only_rectangle.y;
            eye_template = new Rect((int) iris.x - size / 2, (int) iris.y - size / 2, size, size);
            Imgproc.rectangle(mRgba, eye_template.tl(), eye_template.br(), new Scalar(255, 0, 0, 255), 2);
            template = (mGray.submat(eye_template)).clone();
            return template;
        }
        return template;
    }

    @Override
    public void onClick(View view) {

    }
}
