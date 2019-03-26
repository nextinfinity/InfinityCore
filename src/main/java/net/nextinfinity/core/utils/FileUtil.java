package net.nextinfinity.core.utils;

import java.io.File;

/**
 *
 */
public class FileUtil {

	//Credit to StackOverflow for recursively deleting a folder

	/**
	 * Deletes a folder recursively.
	 *
	 * @param folder the File object of the folder to delete
	 * @return true if the folder is successfully deleted, false otherwise
	 */
	public static boolean deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if(files!=null) { //some JVMs return null for empty dirs
			for(File f: files) {
				if(f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		return folder.delete();
	}
}
