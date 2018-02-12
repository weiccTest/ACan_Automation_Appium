package com.cmall.test;

import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.cmall.appium.AppiumServer2;
import com.cmall.appium.DriverFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class BaseCase {
	
	protected static AndroidDriver<MobileElement> mdriver = null;
	private AppiumServer2 appiumSvr = new AppiumServer2();
	private String ip = "127.0.0.1";
	private int port = 4723;
	private String deviceName = "127.0.0.1:62001";
	
	@BeforeSuite
	public AndroidDriver<MobileElement> setup() {
		Reporter.log("========== 正在准备测试环境，预计20s，请稍后 ==========", true);
		appiumSvr.startServer(ip, port,deviceName);
		mdriver = DriverFactory.initDriver(ip, port, deviceName);
		return mdriver;
	}

	
	@AfterSuite
	public void teardown() throws InterruptedException {
		if (mdriver != null) {
			mdriver.quit();
		}
		appiumSvr.stopServer();
		Reporter.log("========== 测试用例执行完成，BYE BYE ==========", true);
	}
	
}
