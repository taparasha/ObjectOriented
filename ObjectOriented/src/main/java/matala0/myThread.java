package main.java.matala0;

import java.nio.file.*;
import java.io.IOException;
import java.nio.file.FileSystems;

import javax.swing.JOptionPane;



public class myThread extends Thread {
	/**
	 * creating a thread for the watchfile function in order to check all the time if there are changes in the data folders. 
	 */
	myThread(String name){
		super (name);
	}
	
	
	@Override
	public void run(){
		String path=null;
		if(this.getName().equals("wigle")){
			path = "C:\\objectoriented\\matala1";
		}
		
		if(this.getName().equals("csv")){
			path = "C:\\objectoriented\\algo2comb";
		}
		try {
			Watch(path);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * function that checks if there were adding or deleting files from the wigle wifi folder and from the comb folder
	 * 
	 * @param path
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void Watch(String path) throws IOException,
	InterruptedException {
		
		Path faxFolder = Paths.get(path);
		WatchService watchService = FileSystems.getDefault().newWatchService();
		faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

		boolean valid = true;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();
					System.out.println("File Created:" + fileName);
					JOptionPane.showMessageDialog(null, "File Created:"+fileName);
				}
				
				if (StandardWatchEventKinds.ENTRY_DELETE.equals(event.kind())) {
					String fileName = event.context().toString();
					System.out.println("File Deleted:" + fileName);
					JOptionPane.showMessageDialog(null, "File Deleted:"+fileName);
				}
				
				if (StandardWatchEventKinds.ENTRY_MODIFY.equals(event.kind())) {
					String fileName = event.context().toString();
					System.out.println("File Modified:" + fileName);
					JOptionPane.showMessageDialog(null, "File Modified:"+fileName);
				}
			}
			valid = watchKey.reset();

		} while (valid);

	}
	

}