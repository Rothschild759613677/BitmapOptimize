package com.moonsky.bitmapoptimize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 通过缩放图片来实现加载图片时不漰溃的问题
 *
 * @author Nick
 * @date 2018-01-20
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.iv);

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();

        Bitmap optimizeBitmap = processOptimizeBitmap(R.drawable.image, layoutParams.width, layoutParams.height);

        if (optimizeBitmap != null) {
            imageView.setImageBitmap(optimizeBitmap);
        }
    }

    /**
     * 处理缩放图片
     *
     * @param sourceId      资源ID
     * @param requestWidth  目标宽度
     * @param requestHeight 目标高度
     * @return Bitmap
     */
    private Bitmap processOptimizeBitmap(int sourceId, int requestWidth, int requestHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), sourceId, options);
        options.inJustDecodeBounds = false;

        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);

        return BitmapFactory.decodeResource(getResources(), sourceId, options);
    }

    /**
     * 缩放的比例因子
     *
     * @param options   BitmapFactory.Options
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return int
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            //计算图片高度和我们需要高度的最接近比例值
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            //宽度比例值
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            //取比例值中的较大值作为inSampleSize
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

}
