package com.bizsp.eaccount.cache;

import java.lang.reflect.Method;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService{
	

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	CacheManager cacheManager;

	@Override
	public Object getCache(Object obj, String methodname, String cachekey, long interval, Object... objparam) throws Exception {
		
		logger.info("::::::::::::::::::::::::::::: cache servcie ::::::::::::::::::::::::::::::::::");
		
		// TODO Auto-generated method stub
        Object result = null;
        
        Cache cache = cacheManager.getCache("selectCache");
        StringBuffer sbCachekey = new StringBuffer(cachekey);
         
        // 고유한 캐쉬키를 만들기 위해 그 당시 사용했던 파라미터값을 같이 조합하여 캐쉬키를 유니크하게 만든다(파라미터 값을 -(하이픈)으로 연결하여 캐쉬키를 만든다)
        if((objparam != null) && (objparam.length > 0)){
            for(Object param : objparam){
                sbCachekey.append("-");
                sbCachekey.append(param);
            }
        }
         
        String useCachekey = sbCachekey.toString();
        logger.info("사용 캐쉬키  : " + useCachekey);
         
        Element element = cache.get(useCachekey);
         
        if(element == null){
            logger.info("캐쉬에 없어서 새로 넣는다");
            logger.info("생성때 currentTimeMillis : " + System.currentTimeMillis());
            result = getData(obj, methodname, objparam);
            cache.put(new Element(useCachekey, result));
        }else{
            logger.info("캐쉬꺼 꺼내오기");
            logger.info("getCreationTime : " + element.getCreationTime());
            logger.info("검색때 currentTimeMillis : " + System.currentTimeMillis());
             
            if(interval == 0){              // interval이 0일때는 갱신간격 체크를 하지 말고 바로 캐쉬에서 가져와서 리턴한다
                result = element.getObjectValue();
            }else if(interval == -1){       // interval이 -1일때는 무조건 조회한뒤에 캐쉬에 넣고 리턴
                try{
                    result = getData(obj, methodname, objparam);
                    cache.put(new Element(useCachekey, result));
                }catch(Exception e){
                    result = element.getObjectValue();
                }
            }else{                          // interval이 0이 아닐때는 갱신간격 체크를 해서 DB에서 조회해서 캐시에 넣거나 또는 바로 캐쉬에서 가져와서 리턴한다
                long timeoutgap = System.currentTimeMillis() - element.getCreationTime();
                logger.info("timeoutgap : " + timeoutgap);
                if(timeoutgap >= interval){
                    try{
                        result = getData(obj, methodname, objparam);
                        cache.put(new Element(useCachekey, result));
                    }catch(Exception e){
                        result = element.getObjectValue();
                    }
                }else{
                    result = element.getObjectValue();
                }
            }
             
        }
         
        return result;
	}

	@Override
	public Object getCache(String cachekey, Object... objparam) throws Exception {
		Object result = null;
        Cache cache = cacheManager.getCache("selectCache");
        StringBuffer sbCachekey = new StringBuffer(cachekey);
         
        // 고유한 캐쉬키를 만들기 위해 그 당시 사용했던 파라미터값을 같이 조합하여 캐쉬키를 유니크하게 만든다(파라미터 값을 -(하이픈)으로 연결하여 캐쉬키를 만든다)
        if((objparam != null) && (objparam.length > 0)){
            for(Object param : objparam){
                sbCachekey.append("-");
                sbCachekey.append(param);
            }
        }
         
        String useCachekey = sbCachekey.toString();
         
        Element element = cache.get(useCachekey);
         
        // 캐쉬에 저장되어 있을 경우
        if(element != null){
            result = element.getObjectValue();
        }
         
        return result;
	}

	private Object getData(Object obj, String methodname, Object... objparam) throws Exception{
        
        Class clazz = obj.getClass();
        Class [] args = new Class[objparam.length];
        for(int i=0; i < args.length; i++){
             
            logger.info("Class name : " + objparam[i].getClass().getName());
             
            /*
             * Java Reflection에서 method에 파라미터 사용시 해당 파라미터 타입이 int(primitive 타입)를 사용하든 Integer를 사용하든 똑같이 Integer 클래스로 인식한다
             * 때문에 캐쉬를 사용하는 함수의 파라미터는 primitive 함수로 설계해야 한다
             */
            if(objparam[i] instanceof String){
                args[i] = String.class;
            }else if(objparam[i] instanceof Integer){
                args[i] = int.class;
            }else if(objparam[i] instanceof Long){
                args[i] = long.class;
            }else if(objparam[i] instanceof Float){
                args[i] = float.class;
            }else if(objparam[i] instanceof Double){
                args[i] = double.class;
            }else if(objparam[i] instanceof Boolean){
                args[i] = boolean.class;
            }else if(objparam[i] instanceof Byte){
                args[i] = byte.class;
            }else if(objparam[i] instanceof Character){
                args[i] = char.class;
            }else{
                args[i] = objparam[i].getClass();
            }
        }
        
        Method method = clazz.getMethod(methodname, args);
        Object result = method.invoke(obj, objparam);
        return result;
    }
	
	@Override
	public long getCacheSize() throws Exception {
		// 캐시 크기가 이상하다고 느껴질경우엔 캐시에 저장되는 클래스들이 Serializable 인터페이스를 구현했는지 확인한다
		Cache cache = cacheManager.getCache("selectCache");
		return cache.calculateInMemorySize();
	}
	
}
