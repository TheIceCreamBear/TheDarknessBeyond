package com.joseph.thedarknessbeyond.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import com.joseph.thedarknessbeyond.gameobject.Village;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumBuilding;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumJob;
import com.joseph.thedarknessbeyond.gui.screens.VillageScreen;
import com.joseph.thedarknessbeyond.gui.windows.PauseMenuWindow;
import com.joseph.thedarknessbeyond.resource.EnumItem;
import com.joseph.thedarknessbeyond.resource.EnumResource;
import com.joseph.thedarknessbeyond.resource.ItemStack;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.resource.StorageManager;

/**
 * The class responsible for ALL file IO
 * @author Joseph
 *
 */
public class FileSaveSystem {
	/**
	 * the file object of the file that contains prefrences
	 */
	private static File prefrencesFile;
	
	/**
	 * the file that holds a blank game
	 */
	private static File newGameFile;
	
	/**
	 * scanner for the preferences file
	 */
	private static Scanner prefrencesScanner;
	
	/**
	 * writer for the prefrences file
	 */
	private static PrintWriter prefrencesWriter;
	
	/**
	 * string representation of the file that was last saved/loaded
	 */
	private static String continueLocation;
	
	private static final String saveVersionString = "SaveSystemVersion:0.2";
	
	/**
	 * initializes the File system
	 */
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
			if (prefrencesScanner.hasNext()) {
				continueLocation = prefrencesScanner.nextLine();
			} else {
				continueLocation = System.getProperty("user.home") + "/TheDarknessBeyond/saves/default.tdbSave";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			newGameFile = new File(System.getProperty("user.home") + "/TheDarknessBeyond/NEW_GAME_FILE.tbdSave");
			if (newGameFile.exists()) {
				newGameFile.delete();
			}
			newGameFile.createNewFile();
			
			InputStream inStream = FileSaveSystem.class.getClassLoader().getResourceAsStream("assets/NEW_GAME_FILE.tdbSave");
		    byte[] buffer = new byte[inStream.available()];
		    inStream.read(buffer);
		 
		    OutputStream outStream = new FileOutputStream(newGameFile);
		    outStream.write(buffer);
		    outStream.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * to be called after all object have been initialized. Finds the last saved game
	 * and loads it.
	 */
	public static void postInit() {
		
	}
	
	/**
	 * 
	 * @return - and array with all the possilbe save games to load
	 */
	public static File[] getPossibleLoadableFiles() {
		File tmp = new File(System.getProperty("user.home") + "/TheDarknessBeyond/saves/tmp.tmp");
		if (!tmp.exists()) {
			try {
				tmp.getParentFile().mkdirs();
				tmp.createNewFile();
				tmp.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File[] f = new File(System.getProperty("user.home") + "/TheDarknessBeyond/saves/").listFiles();
		if (f == null) {
			f = new File[0];
		}
		File[] f1 = new File[f.length + 1];
		for (int i = 0; i < f1.length; i++) {
			if (i == 0) {
				f1[i] = newGameFile;
			} else {
				f1[i] = f[i - 1];
			}
		}
		return f1;
	}
	
	/**
	 * continues the game from the last loaded/saved game
	 * @throws Exception
	 */
	public static void contineGame() throws Exception {
		loadGame(new File(continueLocation));
	}
	
	/**
	 * Loads the game form the given file
	 * @param f - the file
	 * @throws Exception
	 */
	public static void loadGame(File f) throws Exception {
		if (f == null || f.getAbsolutePath().lastIndexOf('.') == -1 || f.isDirectory()) {
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
		
		markNewContinueLocation(f);
		
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
		label = scan.nextLine();
		if (!label.equals("STORAGE: ITEMS")) {
			scan.close();
			throw new RuntimeException("Error: BAD FILE FORMAT");
		}
		
		HashMap<EnumItem, ItemStack> iMap = new HashMap<EnumItem, ItemStack>();
		s = scan.nextLine();
		do {
			ItemStack is = ItemStack.fromString(s);
			iMap.put(is.getItem(), is);
			s = scan.nextLine();
		} while (!s.equals(":END"));
		scan.nextLine();
		
		
		new StorageManager(rMap, iMap);
		
		
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
	
	/**
	 * save the game into the autosave file
	 * @throws Exception
	 */
	public static void autoSaveGame() throws Exception {
		saveGame(new File(System.getProperty("user.home") + "/TheDarknessBeyond/saves/autoSave.tdbSave"));
	}
	
	/**
	 * save the game in the save location with the given name
	 * @param name - the name
	 * @throws Exception
	 */
	public static void saveGame(String name) throws Exception {
		saveGame(new File(System.getProperty("user.home") + "/TheDarknessBeyond/saves/" + name + ".tdbSave"));
	}
	
	
	/**
	 * does the actual saving of the game
	 * @param f - the file
	 * @throws Exception
	 */
	private static void saveGame(File f) throws Exception {
		if (f == null || f.getAbsolutePath().lastIndexOf('.') == -1 || f.isDirectory()) {
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
		
		if (!path.contains("autoSave")) {
			markNewContinueLocation(f);
		}
		
		// Init writing object
		PrintWriter pw = new PrintWriter(new FileWriter(f), true);
		pw.println(saveVersionString);
		
		pw.println();
		
		// StorageManager Resources Saving
		pw.println("STORAGE: RESOURCES");
		HashMap<EnumResource, Resource> rStorage = StorageManager.getInstance().getResources();
//		EnumResource[] rKeys = rStorage.keySet().toArray(new EnumResource[rStorage.size()]);
		EnumResource[] rKeys = EnumResource.values();
		for (int i = 0; i < rKeys.length; i++) {
			pw.println(rStorage.get(rKeys[i]).toString());
		}
		pw.println(":END");
		
		pw.println();
		
		// StorageManager Item Saving
		// TODO
		pw.println("STORAGE: ITEMS");
		HashMap<EnumItem, ItemStack> iStorage = StorageManager.getInstance().getItems();
//		EnumItem[] keys = iStorage.keySet().toArray(new EnumItem[iStorage.size()]);
		EnumItem[] iKeys = EnumItem.values();
		for (int i = 0; i < iKeys.length; i++) {
			pw.println(iStorage.get(iKeys[i]).toString());
		}
		pw.println(":END");
		
		pw.println();
		
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
		
		PauseMenuWindow.getInstance().notifyNewFiles(getPossibleLoadableFiles());
	}
	
	/**
	 * marks the given file as the last edited file
	 * @param f - the file
	 * @throws IOException
	 */
	private static void markNewContinueLocation(File f) throws IOException {
		if (!f.exists()) {
			throw new IllegalArgumentException("Some programmer called this method on a non existant file. Report this bug and wait for an update.");
		}
		
		continueLocation = f.getAbsolutePath();
		prefrencesFile.delete();
		prefrencesFile.createNewFile();
		prefrencesWriter = new PrintWriter(new FileWriter(prefrencesFile), true);
		prefrencesWriter.println(continueLocation);
		prefrencesScanner.close();
		prefrencesScanner = new Scanner(prefrencesFile);
	}
}