package com.cmall.appium;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import com.cmall.utils.LogUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class DriverFactory {
	
	private static LogUtil log = new LogUtil(DriverFactory.class);
	private static AndroidDriver<MobileElement> mdriver;

	public static AndroidDriver<MobileElement> initDriver(String ip, int port, String deviceName) {

		log.info("initDriver on " + deviceName + " " + port);
		
		DesiredCapabilities dc = new DesiredCapabilities();
//		File classPath = new File(System.getProperty("user.dir"));
//		File app = new File(classPath, "apps/play-debug.apk"); // 指定app的存放目录
		File app = new File("apps/play-debug.apk"); // 指定app的存放目录
		dc.setCapability("app", app.getAbsolutePath());
		dc.setCapability("unicodeKeyboard", true); // 支持中文输入
		dc.setCapability("platformName", "Android");
		dc.setCapability("deviceName", deviceName);
		dc.setCapability("appPackage", "com.play.android");
		dc.setCapability("appActivity", "com.play.android.activity.SplashActivity");

		try {
			mdriver = new AndroidDriver<MobileElement>(new URL("http://" + ip + ":" + port + "/wd/hub"), dc);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		log.info("initDriver ok on " + deviceName + " " + port);
		return mdriver;
	}
	
	
	@Test
	public void test() {
		initDriver("127.0.0.1", 4723, "127.0.0.1:62001");
	}
	
}
