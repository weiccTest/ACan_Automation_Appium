package com.cmall.appium;

import java.util.List;
import java.util.Vector;
import com.cmall.testcases.ITestCase;
import com.cmall.utils.DDMlibUtil;
import com.cmall.utils.LogUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * 利用AppiumDriverLocalService，测试多设备，失败，始终使用第一台设备
 * 
 * @author cm
 *
 */
public class MultideviceManage2 {

	LogUtil log = new LogUtil(MultideviceManage2.class);
	List<String> list = DDMlibUtil.getSerialNumber();

	public void runTestCase(ITestCase testcase) {
		Vector<Thread> threads = new Vector<Thread>();
		log.info("一共检测到设备：" + list.size() + "台");
		for (int i = 0; i < list.size(); i++) {
			log.info("第" + (i + 1) + "设备名称：" + list.get(i));
		}
		int port = 4723;
		for (int i = 0; i < list.size(); i++) {
			MyRunnable runnable = new MyRunnable(port, list.get(i), testcase);
			Thread thread = new Thread(runnable);
			threads.add(thread);
			thread.start();
			port += 2;
		}

		for (Thread iThread : threads) {
			try {
				iThread.join();// 等待所有子线程执行完毕
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		log.info("所有线程任务，全部执行完毕！");
	}
	
	AppiumServer2 appiumServer = new AppiumServer2();

	class MyRunnable implements Runnable {
		private int port;
		private String deviceName;
		private String ip;
		private ITestCase testcase;
		private AndroidDriver<MobileElement> mdriver;
		@Override
		public void run() {
			try {
				// 启动Appium服务器
//				appiumServer.startServer(port, deviceName);
				ip = "127.0.0.1";
				appiumServer.startServer(ip, port, deviceName);
				Thread.sleep(3000);
				// 初始化driver
				mdriver = DriverFactory.initDriver(ip,port, deviceName);
				testcase.setDriver(mdriver);
				// 执行测试用例
				testcase.runCase();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				appiumServer.stopServer();
				log.info("执行结束：" + deviceName);
			}
		}
		
		public MyRunnable() {
		}

		public MyRunnable(ITestCase testcase) {
			this.testcase = testcase;
		}

		public MyRunnable(int port, String deviceName, ITestCase testcase) {
			this.port = port;
			this.deviceName = deviceName;
			this.testcase = testcase;
		}
	}
	

}
