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
import com.joseph.thedarknessbeyond.gui.screens.VillageScreen;
import com.joseph.thedarknessbeyond.resource.EnumResource;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.resource.StorageManager;

public class FileSaveSystem {
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
		if (!path.substring(path.indexOf('.')).equals(".tdbSave")) {
			throw new RuntimeException("Error: INVALID SAVE FILE TYPE");
		}
		
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		}
		
		// Init reading object
		Scanner scan = new Scanner(f);
		
		// Version Check
		String s = scan.nextLine();
		if (!s.equals(saveVersionString)) {
			scan.close();
			throw new RuntimeException("AHHHHHHHHHH: BAD FILE SAVE VERSION");
		}
		
		scan.nextLine(); // blank
		
		// StorageManager Resources Loading
		String label = scan.nextLine();
		if (!label.equals("STORAGE: RESOURCES")) {
			scan.close();
			throw new RuntimeException("Error: BAD FILE FORMAT");
		}
		
		HashMap<EnumResource, Resource> rMap = new HashMap<EnumResource, Resource>();
		s = scan.nextLine();
		do {
			Resource r = Resource.fromString(s);
			rMap.put(r.getResourceEnum(), r);
			s = scan.nextLine();
		} while (!s.equals(":END"));
		scan.nextLine();
		
		// StorageManager Items Loading
//		String label = scan.nextLine();
//		if (!label.equals("STORAGE: ITEMS")) {
//			throw new RuntimeException("Error: BAD FILE FORMAT");
//		}
//		
//		EnumResource[] eRes = EnumResource.values();
//		Resource[] res = new Resource[eRes.length];
//		s = scan.nextLine();
//		{
//			int i = 0;
//			do {
//				res[i] = Resource.fromString(s);
//				i++;
//				s = scan.nextLine();
//			} while (!s.equals(":END"));
//		}
//		HashMap<EnumResource, Resource> rMap = new HashMap<EnumResource, Resource>();
//		for (int i = 0; i < res.length; i++) {
//			rMap.put(eRes[i], res[i]);
//		}
//		scan.nextLine();
		
		new StorageManager(rMap);
		
		
		// Village Buildings
		label = scan.nextLine();
		if (!label.equals("VILLAGE: BUILDINGS")) {
			scan.close();
			throw new RuntimeException("Error: BAD FILE FORMAT");
		}
		
		HashMap<EnumBuilding, Integer> buildings = new HashMap<EnumBuilding, Integer>();
		s = scan.nextLine();
		do {
			String[] sa = s.split(": ");
			buildings.put(EnumBuilding.valueOf(sa[0]), Integer.parseInt(sa[1]));
			s = scan.nextLine();
		} while (!s.equals(":END"));
		scan.nextLine();
		
		// Village Jobs
		label = scan.nextLine();
		if (!label.equals("VILLAGE: JOBS")) {
			scan.close();
			throw new RuntimeException("Error: BAD FILE FORMAT");
		}
		
		HashMap<EnumJob, Integer> jobs = new HashMap<EnumJob, Integer>();
		s = scan.nextLine();
		do {
			String[] sa = s.split(": ");
			jobs.put(EnumJob.valueOf(sa[0]), Integer.parseInt(sa[1]));
			s = scan.nextLine();
		} while (!s.equals(":END"));
		
		VillageScreen.setVillage(new Village(buildings, jobs));
		
		scan.close();
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
//		EnumResource[] rKeys = rStorage.keySet().toArray(new EnumResource[rStorage.size()]);
		EnumResource[] rKeys = EnumResource.values();
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
//		EnumBuilding[] bKeys = buildings.keySet().toArray(new EnumBuilding[buildings.size()]);
		EnumBuilding[] bKeys = EnumBuilding.values();
		for (int i = 0; i < bKeys.length; i++) {
			pw.println(bKeys[i].toString() + ": " + buildings.get(bKeys[i]));
		}
		pw.println(":END");
		
		pw.println();
		
		// Village JOBS
		pw.println("VILLAGE: JOBS");
		HashMap<EnumJob, Integer> jobs = theVillage.getJobDistrubution();
//		EnumJob[] jKeys = jobs.keySet().toArray(new EnumJob[jobs.size()]);
		EnumJob[] jKeys = EnumJob.values();
		for (int i = 0; i < jKeys.length; i++) {
			pw.println(jKeys[i].toString() + ": " + jobs.get(jKeys[i]));
		}
		pw.println(":END");
		
		pw.flush();
		pw.close();
	}
}