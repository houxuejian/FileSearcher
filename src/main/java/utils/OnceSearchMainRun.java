package utils;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.SizeFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class OnceSearchMainRun {
	
	public OnceSearchMainRun() throws InterruptedException, ExecutionException {
		String dir = "D:\\java5-gm\\universal_etl\\trunk\\script";
		
		SizeFileFilter sizeFileFilter = new SizeFileFilter((long) (2 * FileUtils.ONE_MB), false);
//		SizeFileFilter sizeFileFilter = new SizeFileFilter(2 * FileUtils.ONE_MB, false);
		
//		RegexFileFilter regexFileFilter = new RegexFileFilter(".*HiveDriver.java.*", IOCase.INSENSITIVE);
		
		NotFileFilter regexFileFilterNot = new NotFileFilter(new RegexFileFilter(".*Runtime.*", IOCase.INSENSITIVE));
		NotFileFilter regexFileFilterNot2 = new NotFileFilter(new RegexFileFilter(".*ThinkPHP.*", IOCase.INSENSITIVE));
		
		
		List<IOFileFilter> fileFilterList = new ArrayList<>();
		fileFilterList.add(TrueFileFilter.INSTANCE);
		fileFilterList.add(sizeFileFilter);
//		fileFilterList.add(regexFileFilterNot);
//		fileFilterList.add(regexFileFilter);
		
		IOFileFilter dirFilter = new AndFileFilter(regexFileFilterNot, regexFileFilterNot2);
		IOFileFilter fileFilter = new AndFileFilter(fileFilterList);
		
		
		long t = System.currentTimeMillis();
		Collection<File> files = FilePathSearcher.INSTANCE.getFileList(new File(dir), fileFilter, dirFilter, 10000);
//		Collection<File> files = FilePathSearcher.INSTANCE.getFileList(new File(dir), fileFilter, TrueFileFilter.INSTANCE, 10000);
//		Collection<File> files = getFileAndDirList(new File("D:/myJar"), fileFilter, TrueFileFilter.INSTANCE, 1);
		
		/*for (File file : files) {
			System.out.println(file.getPath() + "\t" + file.getName() + "\t" + file.length() / 1024 + "k" + "\t" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified())));
		}*/
		System.out.println("file count: " + files.size());
		if (files.isEmpty()) {
			return;
		}
		List<File> files2 = FileContentSearcher.INSTANCE.parallelSearch(files, "update_cm_start_inc", false, Charset.forName("UTF-8"));
		System.out.println("time cost: " + (System.currentTimeMillis() - t));
		
		System.out.println(files2.size());
		for (File file : files2) {
			System.out.println(file.getPath());
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println(ManagementFactory.getRuntimeMXBean().getName());
		new OnceSearchMainRun();
	}
	
}
