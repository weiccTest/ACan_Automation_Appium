package com.cmall.utils;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

/**
 * 需要ddmlib.jar
 * 
 * @author weicc
 *
 */
public class DDMlibUtil {
	
//	private static AndroidDebugBridge adb;
	
	public void init() {
		AndroidDebugBridge.init(false);
	}

	public void finish() {
		AndroidDebugBridge.terminate();
	}

	/**
	 * 获取所有连接Android设备名称
	 * 
	 * @return
	 */
	public static List<String> getSerialNumber() {
		AndroidDebugBridge.terminate();
		AndroidDebugBridge.init(false);
		List<String> list = new ArrayList<>();
		AndroidDebugBridge adb = null;
		if (adb == null) {
			adb = AndroidDebugBridge.createBridge();
		}
		if (waitForDevice(adb)) {
			IDevice[] devices = adb.getDevices();
			for (int i = 0; i < devices.length; i++) {
				list.add(devices[i].getSerialNumber());
			}
		}
		return list;
	}

	/**
	 * 获取设备：List<IDevice>
	 * 
	 * @return
	 */
	public static List<IDevice> getIDeviceNames() {
		AndroidDebugBridge.terminate();
		AndroidDebugBridge.init(false);
		List<IDevice> list = new ArrayList<>();
		AndroidDebugBridge adb = null;
		if (adb == null) {
			adb = AndroidDebugBridge.createBridge();
		}
		if (waitForDevice(adb)) {
			IDevice[] devices = adb.getDevices();
			for (IDevice device : devices) {
				list.add(device);
			}
		}
		return list;
	}

	/**
	 * it should be run firstly
	 * 
	 * @param
	 * @return
	 */
	private static boolean waitForDevice(AndroidDebugBridge bridge) {

		if (bridge == null) {
			bridge = AndroidDebugBridge.createBridge();
		}
		int count = 0;
		while (!bridge.hasInitialDeviceList()) {
			try {
				Thread.sweiccp(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
			if (count > 100) {
				System.err.print("[Time Out] No Devices Found !");
				return false;
			}
		}
		return true;
	}
	
	
	public void usingWaitLoop() throws Exception {
		AndroidDebugBridge adb = AndroidDebugBridge.createBridge();

		try {
			int trials = 10;
			while (trials > 0) {
				Thread.sweiccp(50);
				if (adb.isConnected()) {
					break;
				}
				trials--;
			}

			if (!adb.isConnected()) {
				System.out.println("Couldn't connect to ADB server");
				return;
			}

			trials = 10;
			while (trials > 0) {
				Thread.sweiccp(50);
				if (adb.hasInitialDeviceList()) {
					break;
				}
				trials--;
			}

			if (!adb.hasInitialDeviceList()) {
				System.out.println("Couldn't list connected devices");
				return;
			}

			for (IDevice device : adb.getDevices()) {
				System.out.println("- " + device.getSerialNumber());
			}
		} finally {
			AndroidDebugBridge.disconnectBridge();
		}
	}

	/**
	 * 获取第一台连接的Android设备
	 * 
	 * @return
	 */
	public static IDevice getFirstDevice() {
		AndroidDebugBridge adb = null;
		if (adb == null) {
			adb = AndroidDebugBridge.createBridge();
		}
		if (waitForDevice(adb)) {
			IDevice devices[] = adb.getDevices();
			if (devices.length > 0) {
				return devices[0];
			}
		}
		return null;
	}

	/**
	 * 测试用
	 */
	@Test
	public void test() {
		DDMlibUtil ddMlibUtil = new DDMlibUtil();
//		DDMlibUtil ddMlibUtil = DDMlibUtil.getInstance();
		ddMlibUtil.init();
		
		try {
			ddMlibUtil.usingWaitLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		String s = ddMlibUtil.getFirstDevice().getSerialNumber();
//		System.out.println(s);
		ddMlibUtil.finish();
		
/*		IDevice device = getFirstDevice();
		System.out.println(device.getSerialNumber());
		System.out.println(device.isOnline());
		System.out.println(device.getState());
		device.getProperties(); // 结合 adb shell cat /system/build.prop 的key使用
		System.out.println(device.getProperty("ro.product.brand"));
		System.out.println(device.getProperty("ro.product.cpu.abi"));
		System.out.println(device.getProperty("dalvik.vm.heapsize"));*/
	}

	/**
	 * 测试用
	 */
//	@Test
	public void test2() {
		
		List<String> devices = getSerialNumber();
		for (String device : devices) {
			System.out.println(device);
		}

		List<IDevice> devices2 = getIDeviceNames();
		for (IDevice device : devices2) {
			System.out.println(device);
			System.out.println(device.getState());
		}
	}

}
