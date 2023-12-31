package Utility;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

//import com.technokid.FileManagerDesktopApp.model.Applications;
//import com.technokid.FileManagerDesktopApp.model.Archieves;
//import com.technokid.FileManagerDesktopApp.model.Documents;
//import com.technokid.FileManagerDesktopApp.model.Drive;
//import com.technokid.FileManagerDesktopApp.model.FileEnhanced;
//import com.technokid.FileManagerDesktopApp.model.FileType;
//import com.technokid.FileManagerDesktopApp.model.Images;
//import com.technokid.FileManagerDesktopApp.model.Music;
//import com.technokid.FileManagerDesktopApp.model.Videos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.AllFileType;
import model.Applications;
import model.Archieves;
import model.Documents;
import model.Drive;
import model.FileEnhanced;
import model.FileType;
import model.Images;
import model.Music;
import model.Videos;

public class Utility {

	private double totalGB;
	private double usedGB;
	private double freeGB;
	private ObservableList<FileEnhanced> fileEnhancedsDocs;
	private ObservableList<FileEnhanced> fileEnhancedsImages;
	private ObservableList<FileEnhanced> fileEnhancedsVideos;
	private ObservableList<FileEnhanced> fileEnhancedsArch;
	private ObservableList<FileEnhanced> fileEnhancedsMusic;
	private ObservableList<FileEnhanced> fileEnhancedsApps;
	private int counter;
	private int counter1;
	private int counter2;
	private int counter3;
	private int counter4;
	private int counter5;
	private int counter6;

	public Utility() {
		fileEnhancedsDocs = FXCollections.observableArrayList();
		fileEnhancedsImages = FXCollections.observableArrayList();
		fileEnhancedsVideos = FXCollections.observableArrayList();
		fileEnhancedsArch = FXCollections.observableArrayList();
		fileEnhancedsMusic = FXCollections.observableArrayList();
		fileEnhancedsApps = FXCollections.observableArrayList();
	}

	public ArrayList<Drive> getAllDrives() {
		ArrayList<Drive> drives = new ArrayList<Drive>();
		FileSystemView fsv = FileSystemView.getFileSystemView();

		File[] drivesList = File.listRoots();
		if (drivesList != null && drivesList.length > 0) {
			for (File aDrive : drivesList) {
				Drive drive = new Drive();
				drive.setFile(aDrive);
				drive.setDriveName(aDrive.getName());
				System.out.println("Drive Letter: " + aDrive);
				System.out.println("\tType: " + fsv.getSystemTypeDescription(aDrive));
				System.out.println();

				double totalSpace = aDrive.getTotalSpace();
				double usedSpace = aDrive.getTotalSpace() - aDrive.getFreeSpace();
				double remSpace = aDrive.getFreeSpace();

				totalGB = totalSpace / 1073741824.0;

				drive.setTotalSpace(String.format("%.2f", totalGB) + " GB");

				usedGB = usedSpace / 1073741824.0;

				drive.setUsedSpace(String.format("%.2f", usedGB) + " GB");

				freeGB = remSpace / 1073741824.0;

				drive.setFreeSpace(String.format("%.2f", freeGB) + " GB");

//				System.out.println("\tTotal space: " + totalSpace);
//				System.out.println("\tUsable space: " + usedSpace);
//				System.out.println("\tFree space: " + remSpace);

				double usedPer = (usedSpace / totalSpace) * 100;
				System.out.println(Math.round(usedPer) + " %");

				double remPer = (remSpace / totalSpace) * 100;
				System.out.println(Math.round(remPer) + " %");

				drive.setDblFreeSpace(remSpace);
				drive.setDblTotalSpace(totalSpace);
				drive.setDblUsedSpace(usedSpace);
				drive.setPer(Math.round(usedPer));
				drive.setRemPer(Math.round(remPer));
				drives.add(drive);
			}
		}
		return drives;
	}

	public ObservableList<FileEnhanced> recursiveGetAllDocumentFiles(File[] arr, int level) {
		if (arr != null) {
			for (File f : arr) {
				
				if (f.isFile()) {
					String extension = getExtensionByStringHandling(f.getName());
					for (Documents document : Documents.values()) {
						if (extension.equalsIgnoreCase(document.name())) {
							FileEnhanced enhanced = new FileEnhanced();
							enhanced.setFile(f);
							enhanced.setFileType(FileType.Documents);
							
							if (fileEnhancedsDocs.add(enhanced)) {
								++counter;
								System.out.print(enhanced.getFile()+"  ");
								System.err.println(counter + " Document file added to list ");
							}
							break;
						}
					}
				} else if (f.isDirectory()) {
					System.out.println("[" + f.getName() + "]");
					recursiveGetAllDocumentFiles(f.listFiles(), level + 1);
				}
			}
		}
		return fileEnhancedsDocs;
	}

	public ObservableList<FileEnhanced> recursiveGetAllImageFiles(File[] arr, int level) {
		// for-each loop for main directory files
		if (arr != null) {
			for (File f : arr) {
				// tabs for internal levels
				for (int i = 0; i < level; i++)
					System.out.print("\t");

				if (f.isFile()) {
					String extension = getExtensionByStringHandling(f.getName());
					for (Images image : Images.values()) {
						if (extension.equalsIgnoreCase(image.name())) {
							FileEnhanced enhanced = new FileEnhanced();
							enhanced.setFile(f);
							enhanced.setFileType(FileType.Images);
							
							if (fileEnhancedsImages.add(enhanced)) {
								++counter1;
								System.out.print(enhanced.getFile()+"  ");
								System.err.println(counter1 + " Image file added to list ");
							}
							break;
						}
					}
				} else if (f.isDirectory()) {
					System.out.println("[" + f.getName() + "]");
					// recursion for sub-directories
					recursiveGetAllImageFiles(f.listFiles(), level + 1);
				}
			}
		}
		return fileEnhancedsImages;
	}

	public ObservableList<FileEnhanced> recursiveGetAllVideoFiles(File[] arr, int level) {
		if (arr != null) {
			for (File f : arr) {
				
				if (f.isFile()) {
					String extension = getExtensionByStringHandling(f.getName());
					for (Videos video : Videos.values()) {
						if (extension.equalsIgnoreCase(video.name())) {
							FileEnhanced enhanced = new FileEnhanced();
							enhanced.setFile(f);
							enhanced.setFileType(FileType.Videos);
							
							if (fileEnhancedsVideos.add(enhanced)) {
								++counter2;
								System.out.print(enhanced.getFile()+"  ");
								System.err.println(counter2 + " Video file added to list ");
								
							}
							break;
						}
					}
				} else if (f.isDirectory()) {
					System.out.println("[" + f.getName() + "]");
					recursiveGetAllVideoFiles(f.listFiles(), level + 1);
				}
			}
		}
		return fileEnhancedsVideos;
	}

	public ObservableList<FileEnhanced> recursiveGetAllArchFiles(File[] arr, int level) {
		if (arr != null) {
			for (File f : arr) {
				if (f.isFile()) {
					String extension = getExtensionByStringHandling(f.getName());
					for (Archieves arch : Archieves.values()) {
						if (extension.equalsIgnoreCase(arch.name())) {
							FileEnhanced enhanced = new FileEnhanced();
							enhanced.setFile(f);
							enhanced.setFileType(FileType.Archives);
							
							if (fileEnhancedsArch.add(enhanced)) {
								++counter3;
								System.out.print(enhanced.getFile()+"  ");
								System.err.println(counter3 + " Arch file added to list ");
							}
							break;
						}
					}
				} else if (f.isDirectory()) {
					System.out.println("[" + f.getName() + "]");
					recursiveGetAllArchFiles(f.listFiles(), level + 1);
				}
			}
		}
		return fileEnhancedsArch;
	}

	public ObservableList<FileEnhanced> recursiveGetAllMusicFiles(File[] arr, int level) {
		if (arr != null) {
			for (File f : arr) {
				
				if (f.isFile()) {
					String extension = getExtensionByStringHandling(f.getName());
					for (Music music : Music.values()) {
						if (extension.equalsIgnoreCase(music.name())) {
							FileEnhanced enhanced = new FileEnhanced();
							enhanced.setFile(f);
							enhanced.setFileType(FileType.Music);
							
							if (fileEnhancedsMusic.add(enhanced)) {
								++counter4;
								System.out.print(enhanced.getFile()+"  ");
								System.err.println(counter4 + " Music file added to list ");
							}
							break;
						}
					}
				} else if (f.isDirectory()) {
					System.out.println("[" + f.getName() + "]");
					recursiveGetAllMusicFiles(f.listFiles(), level + 1);
				}
			}
		}
		return fileEnhancedsMusic;
	}

	public ObservableList<FileEnhanced> recursiveGetAllAppFiles(File[] arr, int level) {
		if (arr != null) {
			for (File f : arr) {
				if (f.isFile()) {
					String extension = getExtensionByStringHandling(f.getName());
					for (Applications app : Applications.values()) {
						if (extension.equalsIgnoreCase(app.name())) {
							FileEnhanced enhanced = new FileEnhanced();
							enhanced.setFile(f);
							enhanced.setFileType(FileType.Apps);
							
							if (fileEnhancedsApps.add(enhanced)) {
								++counter5;
								System.out.print(enhanced.getFile()+"  ");
								System.err.println(counter5 + " App/Setup file added to list ");
							}
							break;
						}
					}
				} else if (f.isDirectory()) {
					System.out.println("[" + f.getName() + "]");
					recursiveGetAllAppFiles(f.listFiles(), level + 1);
				}
			}
		}
		return fileEnhancedsApps;
	}
	
	public ObservableList<FileEnhanced> getAllFiles(File rootFolder) {
		File files[] = rootFolder.listFiles(
				new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String extension = getExtensionByStringHandling(pathname.getName());
				for (AllFileType document : AllFileType.values()) {
					if (extension.equals(document.name())) {
						return true;
					}
				}
				return false;
			}
		});
		for (File file : files) {
			FileEnhanced enhanced = new FileEnhanced();
			enhanced.setFile(file);
			enhanced.setFileType(FileType.AllFileType);
			fileEnhancedsDocs.add(enhanced);
		}
		return fileEnhancedsDocs;
	}
	public ObservableList<FileEnhanced> recursiveGetAllTypeFiles(File[] arr, int level) {
		if (arr != null) {
			for (File f : arr) {
				
				if (f.isFile()) {
					String extension = getExtensionByStringHandling(f.getName());
					for (AllFileType app : AllFileType.values()) {
						if (extension.equalsIgnoreCase(app.name())) {
							FileEnhanced enhanced = new FileEnhanced();
							enhanced.setFile(f);
							enhanced.setFileType(FileType.AllFileType);
							
							if (fileEnhancedsApps.add(enhanced)) {
								++counter6;
								System.out.print(enhanced.getFile()+"  ");
								System.err.println(counter6 + " App/Setup file added to list ");
							}
							break;
						}
					}
				} else if (f.isDirectory()) {
					System.out.println("[" + f.getName() + "]");
					recursiveGetAllTypeFiles(f.listFiles(), level + 1);
				}
			}
		}
		return fileEnhancedsApps;
	}

	public String getExtensionByStringHandling(String filename) {
		String extension = "";
		int index = filename.lastIndexOf('.');
		if (index > 0) {
			extension = filename.substring(index + 1);
		}
		return extension;
	}

	public boolean isEmpty(Path path) throws IOException {
		if (Files.isDirectory(path)) {
			try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
				return !directory.iterator().hasNext();
			}
		}

		return false;
	}

//	public static void main(String[] args) {
//		Utility utility = new Utility();
//		System.out.println(utility.recursiveGetAllDocumentFiles(new File("D:\\").listFiles(), 0).size());
//	}

	public void reset() {
		fileEnhancedsDocs.clear();
		fileEnhancedsImages.clear();
		fileEnhancedsVideos.clear();
		fileEnhancedsArch.clear();
		fileEnhancedsMusic.clear();
		fileEnhancedsApps.clear();
	}
}