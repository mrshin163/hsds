package com.bizsp.framework.util.system;

/**
 * 시스템 정보를 가지고 온다.
 * @author <a href='devkim@devkim.co.kr'><b>KIM JIN HYUG</b></a>
 * @version SysUtil.java - 2009. 5. 25
 * @project childFund-admin
 */
public class SystemInfo{

    public static final String FILE_ENCODING = getSystemProperty("file.encoding");
    public static final String FILE_SEPARATOR = getSystemProperty("file.separator");
    public static final String JAVA_CLASS_PATH = getSystemProperty("java.class.path");
    public static final String JAVA_CLASS_VERSION = getSystemProperty("java.class.version");
    public static final String JAVA_COMPILER = getSystemProperty("java.compiler");
    public static final String JAVA_EXT_DIRS = getSystemProperty("java.ext.dirs");
    public static final String JAVA_ENDORSED_DIRS = getSystemProperty("java.endorsed.dirs");
    public static final String JAVA_HOME = getSystemProperty("java.home");
    public static final String JAVA_IO_TMPDIR = getSystemProperty("java.io.tmpdir");
    public static final String JAVA_LIBRARY_PATH = getSystemProperty("java.library.path");
    public static final String JAVA_RUNTIME_NAME = getSystemProperty("java.runtime.name");
    public static final String JAVA_RUNTIME_VERSION = getSystemProperty("java.runtime.version");
    public static final String JAVA_SPECIFICATION_NAME = getSystemProperty("java.specification.name");
    public static final String JAVA_SPECIFICATION_VENDOR = getSystemProperty("java.specification.vendor");
    public static final String JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version");
    public static final String JAVA_VENDOR = getSystemProperty("java.vendor");
    public static final String JAVA_VENDOR_URL = getSystemProperty("java.vendor.url");
    public static final String JAVA_VERSION = getSystemProperty("java.version");
    public static final String JAVA_VM_INFO = getSystemProperty("java.vm.info");
    public static final String JAVA_VM_NAME = getSystemProperty("java.vm.name");
    public static final String JAVA_VM_SPECIFICATION_NAME = getSystemProperty("java.vm.specification.name");
    public static final String JAVA_VM_SPECIFICATION_VENDOR = getSystemProperty("java.vm.specification.vendor");
    public static final String JAVA_VM_SPECIFICATION_VERSION = getSystemProperty("java.vm.specification.version");
    public static final String JAVA_VM_VENDOR = getSystemProperty("java.vm.vendor");
    public static final String JAVA_VM_VERSION = getSystemProperty("java.vm.version");
    public static final String LINE_SEPARATOR = getSystemProperty("line.separator");
    public static final String OS_ARCH = getSystemProperty("os.arch");
    public static final String OS_NAME = getSystemProperty("os.name");
    public static final String OS_VERSION = getSystemProperty("os.version");
    public static final String PATH_SEPARATOR = getSystemProperty("path.separator");
    public static final String USER_COUNTRY = getSystemProperty("user.country") != null ? getSystemProperty("user.country") : getSystemProperty("user.region");
    public static final String USER_DIR = getSystemProperty("user.dir");
    public static final String USER_HOME = getSystemProperty("user.home");
    public static final String USER_LANGUAGE = getSystemProperty("user.language");
    public static final String USER_NAME = getSystemProperty("user.name");
    public static final float JAVA_VERSION_FLOAT = getJavaVersionAsFloat();
    public static final int JAVA_VERSION_INT = getJavaVersionAsInt();
    public static final boolean IS_JAVA_1_1 = getJavaVersionMatches("1.1");
    public static final boolean IS_JAVA_1_2 = getJavaVersionMatches("1.2");
    public static final boolean IS_JAVA_1_3 = getJavaVersionMatches("1.3");
    public static final boolean IS_JAVA_1_4 = getJavaVersionMatches("1.4");
    public static final boolean IS_JAVA_1_5 = getJavaVersionMatches("1.5");
    public static final boolean IS_OS_AIX = getOSMatches("AIX");
    public static final boolean IS_OS_HP_UX = getOSMatches("HP-UX");
    public static final boolean IS_OS_IRIX = getOSMatches("Irix");
    public static final boolean IS_OS_LINUX = getOSMatches("Linux") || getOSMatches("LINUX");
    public static final boolean IS_OS_MAC = getOSMatches("Mac");
    public static final boolean IS_OS_MAC_OSX = getOSMatches("Mac OS X");
    public static final boolean IS_OS_OS2 = getOSMatches("OS/2");
    public static final boolean IS_OS_SOLARIS = getOSMatches("Solaris");
    public static final boolean IS_OS_SUN_OS = getOSMatches("SunOS");
    public static final boolean IS_OS_WINDOWS = getOSMatches("Windows");
    public static final boolean IS_OS_WINDOWS_2000 = getOSMatches("Windows", "5.0");
    public static final boolean IS_OS_WINDOWS_95 = getOSMatches("Windows 9", "4.0");
    public static final boolean IS_OS_WINDOWS_98 = getOSMatches("Windows 9", "4.1");
    public static final boolean IS_OS_WINDOWS_ME = getOSMatches("Windows", "4.9");
    public static final boolean IS_OS_WINDOWS_NT = getOSMatches("Windows NT");
    public static final boolean IS_OS_WINDOWS_XP = getOSMatches("Windows", "5.1");

    private static float getJavaVersionAsFloat()
    {
        if(JAVA_VERSION == null)
            return 0.0F;
        String str = JAVA_VERSION.substring(0, 3);
        if(JAVA_VERSION.length() >= 5)
            str = (new StringBuilder(String.valueOf(str))).append(JAVA_VERSION.substring(4, 5)).toString();
        return Float.parseFloat(str);
    }

    private static int getJavaVersionAsInt()
    {
        if(JAVA_VERSION == null)
            return 0;
        String str = JAVA_VERSION.substring(0, 1);
        str = (new StringBuilder(String.valueOf(str))).append(JAVA_VERSION.substring(2, 3)).toString();
        if(JAVA_VERSION.length() >= 5)
            str = (new StringBuilder(String.valueOf(str))).append(JAVA_VERSION.substring(4, 5)).toString();
        else
            str = (new StringBuilder(String.valueOf(str))).append("0").toString();
        return Integer.parseInt(str);
    }

    private static boolean getJavaVersionMatches(String versionPrefix)
    {
        if(JAVA_VERSION == null)
            return false;
        else
            return JAVA_VERSION.startsWith(versionPrefix);
    }

    private static boolean getOSMatches(String osNamePrefix)
    {
        if(OS_NAME == null)
            return false;
        else
            return OS_NAME.startsWith(osNamePrefix);
    }

    private static boolean getOSMatches(String osNamePrefix, String osVersionPrefix)
    {
        if(OS_NAME == null || OS_VERSION == null)
            return false;
        return OS_NAME.startsWith(osNamePrefix) && OS_VERSION.startsWith(osVersionPrefix);
    }

    private static String getSystemProperty(String property)
    {
        try
        {
            return System.getProperty(property);
        }
        catch(SecurityException ex)
        {
            System.err.println((new StringBuilder("Caught a SecurityException reading the system property '")).append(property).append("'; the SystemUtil property value will default to null.").toString());
        }
        return null;
    }

    public static boolean isJavaVersionAtLeast(float requiredVersion)
    {
        return JAVA_VERSION_FLOAT >= requiredVersion;
    }

    public static boolean isJavaVersionAtLeast(int requiredVersion)
    {
        return JAVA_VERSION_INT >= requiredVersion;
    }



}