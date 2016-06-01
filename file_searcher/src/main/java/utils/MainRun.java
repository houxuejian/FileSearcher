package utils;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.SizeFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class MainRun {
	private FileContentSearcher contentSearcher = FileContentSearcher.INSTANCE;
	
	public MainRun() {
		String dir = "D:/java3z - 副本";
		
		SizeFileFilter sizeFileFilter = new SizeFileFilter(20 * FileUtils.ONE_MB, true);
//		SizeFileFilter sizeFileFilter = new SizeFileFilter(2 * FileUtils.ONE_MB, false);
		RegexFileFilter regexFileFilter = new RegexFileFilter(".*", IOCase.INSENSITIVE);
		
		List<IOFileFilter> fileFilterList = new ArrayList<>();
		fileFilterList.add(TrueFileFilter.INSTANCE);
//		fileFilterList.add(sizeFileFilter);
//		fileFilterList.add(regexFileFilter);
		
		IOFileFilter dirFilter = new AndFileFilter(TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		IOFileFilter fileFilter = new AndFileFilter(fileFilterList);
		
		
		long t = System.currentTimeMillis();
		Collection<File> files = FilePathSearcher.getFileList(new File(dir), fileFilter, TrueFileFilter.INSTANCE, Integer.MAX_VALUE);
//		Collection<File> files = getFileAndDirList(new File("D:/myJar"), fileFilter, TrueFileFilter.INSTANCE, 1);
		
//		for (File file : files) {
//			System.out.println(file.getPath() + "\t" + file.getName() + "\t" + file.length() / 1024 + "k" + "\t" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified())));
//		}
		System.out.println("file count: " + files.size());
		
		List<File> files2 = contentSearcher.parallelSearch(files, "上周二的外挂ip计算", false, StandardCharsets.UTF_8, Charset.forName("gbk"));
//		List<File> files2 = parallelSearchFileOld(files, "logger\\.error\\(this\\.toIdentity\\(\\) \\+ \"Retried", true, StandardCharsets.ISO_8859_1);
//		List<File> files2 = searchFile(files, Pattern.compile("啊是的"), Charset.forName("gbk"));
		System.out.println("time cost: " + (System.currentTimeMillis() - t));
		
		System.out.println(files2.size());
		for (File file : files2) {
			System.out.println(file.getPath());
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(ManagementFactory.getRuntimeMXBean().getName());
		new MainRun();
	}
	
}
