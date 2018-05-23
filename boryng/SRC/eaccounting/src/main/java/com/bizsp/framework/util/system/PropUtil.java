package com.bizsp.framework.util.system;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.bizsp.framework.util.exception.BaseException;
import com.bizsp.framework.util.web.ContextHolder;

/**
 * 포로퍼티를 서버타입별로 불러와서 유틸성으로 사용가능하도록 한다.
 * @author Administrator
 * @since 2011. 9. 19.
 * @version 1.0
 */
public class PropUtil {
	private static Properties	commonProp	= new Properties();
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	//serverType
	public void initilize() {
		try {
			Resource resources[] = ContextHolder.getContext().getResources("classpath:/properties/*.properties");
			if(resources == null || resources.length == 0)throw new BaseException("Exception Code :: No Properties....");
            Resource arr[] = resources;
            int len$ = arr.length;
            for(int i = 0; i < len$; i++){
                Resource resource = arr[i];
                PropertiesLoaderUtils.fillProperties(commonProp, resource);
                log.info("@@@@@@@@@@@@@ properties load => "+resource.getFilename()+" @@@@@@@@@@@@@ ");
            }
		} catch (Exception e) {
			log.error("PROPERTIES LOAD FAIL!!!!! >>>>>>>>>> "+e.getMessage(), e);
		}
	}

	/**
	 *
	 * @return 공통설정파일 key
	 */
	public static String getValue(String key) {
		String tmp = PropUtil.commonProp.getProperty(key);
		return tmp;
	}

	/**
	 * prop 다시불러오기
	 */
	public static void realoadCommonProperties() {
		try {
			org.springframework.core.io.Resource resources[] = ContextHolder.getContext().getResources("classpath:/properties/*.properties");
			if(resources == null || resources.length == 0)
                throw new BaseException("Exception Code :: No Properties....");
            org.springframework.core.io.Resource arr[] = resources;
            int len$ = arr.length;
            for(int i = 0; i < len$; i++)
            {
                org.springframework.core.io.Resource resource = arr[i];
                PropertiesLoaderUtils.fillProperties(commonProp, resource);
            }
		} catch (Exception e) {
		}
	}
}
