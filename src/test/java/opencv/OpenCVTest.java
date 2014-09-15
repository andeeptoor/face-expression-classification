package opencv;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

public class OpenCVTest {

	static {
		System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary("opencv_java249");
	}

	@Test
	public void test() {
		Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
		System.out.println("OpenCV Mat: " + m);
		Mat mr1 = m.row(1);
		mr1.setTo(new Scalar(1));
		Mat mc5 = m.col(5);
		mc5.setTo(new Scalar(5));
		System.out.println("OpenCV Mat data:\n" + m.dump());
	}
	
	@Test
	public void testConvertImages() throws Exception {
		File file = new File("src/main/resources/fer2013/fer2013.csv");
		LineIterator lineIterator = FileUtils.lineIterator(file);
		if (lineIterator.hasNext()){
			lineIterator.next();
		}
		int imageCount = 1;
		while (lineIterator.hasNext()) {
			System.out.println("Image [" + imageCount + "]");
			String[] split = StringUtils.split(lineIterator.next(), ",");
			String usage = split[2];
			Integer emotion = Integer.parseInt(split[0]);
			String[] pixels = StringUtils.split(split[1]," ");
			Mat mat = new Mat(48, 48, CvType.CV_8UC1);
			Mat current = null;
			int row = -1;
			int column = 0;
			for (int i = 0; i < pixels.length; i++) {
				column = i%48;
				if (column == 0){
					row++;
					current = mat.row(row);
				}
				current.col(column).setTo(new Scalar(Integer.parseInt(pixels[i])));
//				System.out.println("[" + row + "," + column + "]");
			}
			Highgui.imwrite("target/" + usage + "/image"+ imageCount + ".png", mat);
			imageCount++;
//			System.out.println(emotion + " " + usage + " " + pixels.length);
		}
	}

}
