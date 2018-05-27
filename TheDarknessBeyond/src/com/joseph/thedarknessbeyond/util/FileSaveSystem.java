package com.joseph.thedarknessbeyond.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import com.joseph.thedarknessbeyond.gameobject.Village;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumBuilding;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumJob;
import com.joseph.thedarknessbeyond.resource.EnumResource;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.resource.StorageManager;

public class FileSaveSystem {
	private static File theSaveFile;
	private static File prefrencesFile;
	private static Scanner prefrencesScanner;
	private static String continueLocation;
	private static final String saveVersionString = "SaveSystemVersion:0.1";
	
	public static void init() {
		prefrencesFile = new File(System.getProperty("user.home") + "/TheDarknessBeyond/prefrences.dat");
		if (!prefrencesFile.exists()) {
			try {
				prefrencesFile.getParentFile().mkdirs();
				prefrencesFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			prefrencesScanner = new Scanner(prefrencesFile);
			continueLocation = prefrencesScanner.nextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void contineGame() throws Exception {
		loadGame(new File(continueLocation));
	}
	
	public static void loadGame(File f) throws Exception {
		if (f == null || f.getAbsolutePath().lastIndexOf('.') == -1) {
			throw new RuntimeException("Error: INVALID SAVE LOCATION");
		}
		String path = f.getAbsolutePath();
		if (!path.substring(path.indexOf('.')).equals(".tbdSave")) {
			throw new RuntimeException("Error: INVALID SAVE FILE TYPE");
		}
		
		// Init reading object
		Scanner scan = new Scanner(f);
	}
	
	public static void autoSaveGame() throws Exception {
		saveGame(new File(System.getProperty("user.home") + "/TheDarknessBeyond/autoSave.tdbSave"));
	}
	
	public static void saveGame(String name) throws Exception {
		saveGame(new File(System.getProperty("user.home") + "/TheDarknessBeyond/" + name + ".tdbSave"));
	}
	
	private static void saveGame(File f) throws Exception {
		if (f == null || f.getAbsolutePath().lastIndexOf('.') == -1) {
			throw new RuntimeException("Error: INVALID SAVE LOCATION");
		}
		String path = f.getAbsolutePath();
		if (!path.substring(path.indexOf('.')).equals(".tdbSave")) {
			throw new RuntimeException("Error: INVALID SAVE FILE TYPE");
		}
		
		if (!f.exists()) {
			try {
				f.getParentFile().mkdirs();
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Init writing object
		PrintWriter pw = new PrintWriter(new FileWriter(f), true);
		pw.println(saveVersionString);
		
		pw.println();
		
		// StorageManager Resources Saving
		pw.println("STORAGE: RESOURCES");
		HashMap<EnumResource, Resource> rStorage = StorageManager.getInstance().getStores();
		EnumResource[] rKeys = rStorage.keySet().toArray(new EnumResource[rStorage.size()]);
		for (int i = 0; i < rKeys.length; i++) {
			pw.println(rStorage.get(rKeys[i]).toString());
		}
		pw.println(":END");
		
		pw.println();
		
		// StorageManager Item Saving
		// TODO
//		pw.println("STORAGE: ITEMS");
//		HashMap<EnumItem, Resource> iStorage = StorageManager.getInstance().getStores();
//		EnumItem[] keys = iStorage.keySet().toArray(new EnumItem[iStorage.size()]);
//		for (int i = 0; i < keys.length; i++) {
//			pw.println(iStorage.get(keys[i]).toString());
//		}
//		pw.println(":END");
//		
//		pw.println();
		
		// Village
		Village theVillage = Village.getInstance();
		
		// Village Buildings
		pw.println("VILLAGE: BUILDINGS");
		HashMap<EnumBuilding, Integer> buildings = theVillage.getBuildingCount();
		EnumBuilding[] bKeys = buildings.keySet().toArray(new EnumBuilding[buildings.size()]);
		for (int i = 0; i < bKeys.length; i++) {
			pw.println(bKeys[i].toString() + ": " + buildings.get(bKeys[i]));
		}
		pw.println(":END");
		
		pw.println();
		
		// Village JOBS
		pw.println("VILLAGE: JOBS");
		HashMap<EnumJob, Integer> jobs = theVillage.getJobDistrubution();
		EnumJob[] jKeys = jobs.keySet().toArray(new EnumJob[jobs.size()]);
		for (int i = 0; i < jKeys.length; i++) {
			pw.println(jKeys[i].toString() + ": " + jobs.get(jKeys[i]));
		}
		pw.println(":END");
	}
}