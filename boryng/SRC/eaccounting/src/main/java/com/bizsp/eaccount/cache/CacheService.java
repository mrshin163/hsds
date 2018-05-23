package com.bizsp.eaccount.cache;

public interface CacheService {
	/**
     * 캐시에 저장할 값을 리턴하는 객체의 함수를 대신 실행하여 캐시에 고유의 캐시키를 주어 저장한다
     * @param obj           캐시에 저장할 값을 리턴하는 함수가 있는 객체      
     * @param methodname    캐시에 저장할 값을 리턴하는 함수의 함수명
     * @param cachekey      캐시키
     * @param interval      캐시 갱신 간격(0일 경우 캐시 갱신 간격을 체크하지 말고 바로 캐시에서 가져온다)
     * @param objparam      캐시에 저장할 값을 리턴하는 함수에서 사용하는 파라미터값들
     * @return              캐시에 저장된 내용
     * @throws Exception
     */
    public Object getCache(Object obj, String methodname, String cachekey, long interval, Object ... objparam) throws Exception;
     
    /**
     * 캐시키를 주어 해당 캐시키에 저장되어 있는 캐시내용을 리턴한다
     * @param cachekey      캐시키
     * @param objparam      캐시키를 만들때 사용하는 파라미터 값들
     * @return              캐시에 저장된 내용
     * @throws Exception
     */
    public Object getCache(String cachekey, Object ... objparam) throws Exception;
     
    /**
     * 현재 사용중인 캐쉬의 크기를 리턴한다
     * @return              사용중인 캐쉬의 크기
     * @throws Exception
     */
    public long getCacheSize() throws Exception;
}
