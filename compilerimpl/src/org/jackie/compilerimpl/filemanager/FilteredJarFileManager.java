package org.jackie.compilerimpl.filemanager;

import org.jackie.utils.Assert;

import java.io.File;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

/**
 * @author Patrik Beno
 */
public class FilteredJarFileManager extends JarFileManager {

	List<Pattern> excludePatterns;

	{
		excludePatterns = new ArrayList<Pattern>();
	}

	public FilteredJarFileManager(File jarfile, String ... excludePatterns) {
		super(jarfile);
		for (String s : excludePatterns) {
			this.excludePatterns.add(Pattern.compile(s));
		}
	}

	protected Set<String> populatePathNames() {
		Set<String> pathnames = super.populatePathNames();
		for (Iterator<String> i = pathnames.iterator(); i.hasNext();) {
			String next = i.next();
			if (isExcluded(next)) {
				i.remove();
			}
		}
		return pathnames;
	}

	boolean isExcluded(String pathname) {
		for (Pattern p : excludePatterns) {
			if (p.matcher(pathname).matches()) {
				return true;
			}
		}
		return false;
	}
}
