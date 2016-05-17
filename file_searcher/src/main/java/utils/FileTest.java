package utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.SizeFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class FileTest {
	public static void main(String[] args) {
		Arrays.asList(new SizeFileFilter(1 * FileUtils.ONE_MB, true), new RegexFileFilter("", IOCase.INSENSITIVE));
		
		IOFileFilter dirFilter = new AndFileFilter(TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		IOFileFilter fileFilter = new AndFileFilter(TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		Collection<File> files = LimitFileUtils.listFiles(new File("D:/myJar"), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE, 1);
		for (File file : files) {
			System.out.println(file.getPath() + "\t\t" + file.getName());
		}
		System.out.println(files.size());
	}
	
}
