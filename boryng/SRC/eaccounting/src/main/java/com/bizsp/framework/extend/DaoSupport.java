package com.bizsp.framework.extend;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bizsp.framework.security.vo.LoginUserVO;
import com.bizsp.framework.util.lang.StringUtil;
import com.bizsp.framework.util.model.BaseCndModel;
import com.bizsp.framework.util.paging.PagingList;

public class DaoSupport {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SqlSessionTemplate sqlSession;

	/**
	 * <strong>title</strong> : writeLog  <BR/>
	 * <strong>description</strong> : 쿼리실행 시간을 기록한다. <BR/>
	 * <strong>date</strong> : 2010. 12. 7.
	 * @param statementName
	 * @param start
	 * @param end
	 */
	public void writeLog(String statementName,long start, long end){
		logger.info("\n실행 sqlMap : "+statementName+"\n쿼리 실행 시간[TimeMillis] : "+(end-start)+"ms");
	}

	/**
	 * <strong>title</strong> : getSqlMapClientTemplate  <BR/>
	 * <strong>description</strong> : SqlMapClientTemplate 를 반환한다. <BR/>
	 * <strong>date</strong> : 2010. 12. 7.
	 * @param db
	 * @return
	 */
	protected SqlSessionTemplate getSqlSession() {
		
		return sqlSession;
	}

	
	public long selectCount(String statementName){
		return selectCount(statementName,null);
	}

	/**
	 * 페이징을 위해 카운트를 조회한다.
	 * @param db
	 * @param statementName
	 * @param parameterObject
	 * @return
	 */
	public long selectCount(String statementName,Object parameterObject){
		Object obj = select(statementName,parameterObject);
		return StringUtil.parseLong(obj);
	}

	public Object select(String statementName){
		return select(statementName,null);
	}

	/**
	 * <strong>description</strong> : 단일행을 조회한다. <BR/>
	 * <strong>date</strong> : 2010. 12. 7.
	 * @param db
	 * @param statementName
	 * @return
	 */
	public Object select(String statementName,Object parameterObject){
		long start = System.currentTimeMillis();
		Object obj = null;
		if(parameterObject==null){
			obj = getSqlSession().selectOne(statementName);
		}else{
			obj = getSqlSession().selectOne(statementName, parameterObject);
		}
		long end = System.currentTimeMillis();
		writeLog(statementName,start,end);
		return obj;
	}

	/**
	 * <strong>description</strong> : 다중행을 조회한다. <BR/>
	 * @param db
	 * @param statementName
	 * @param parameterObject
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectList(String statementName,Object parameterObject){
		long start = System.currentTimeMillis();
		List list = null;
		if(parameterObject==null){
			list = getSqlSession().selectList(statementName);
		}else{
			list = getSqlSession().selectList(statementName, parameterObject);
		}

		long end = System.currentTimeMillis();
		writeLog(statementName,start,end);
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String statementName){
		return selectList(statementName,null);
	}


	/**
	 * pagingList를 생성한다.
	 * @param db
	 * @param statementName : 목록조회 쿼리
	 * @param parameterObject : 인자
	 * @param countStatementName : 목록카운트쿼리
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public PagingList pagingList(String statementName, Object parameterObject, String countStatementName){
		if(StringUtil.isEmpty(countStatementName)){
			return new PagingList(selectList(statementName, parameterObject));
		}
		
		BaseCndModel cndModel = (BaseCndModel)parameterObject;
		
		if(StringUtil.isEquals("Y", cndModel.getPagingYn())){
			
			//토탈카운트를 가지고 온다.
			long totalRow = selectCount(countStatementName, parameterObject);
			
			cndModel.setTotalRow(totalRow);
			int lastPage = (int)Math.ceil((double)totalRow / (double)cndModel.getRowPerPage());
			if(lastPage < cndModel.getPageNo()){
				cndModel.setPageNo(lastPage);
			}
			
			List dataList = selectList(statementName, parameterObject);
			
			return new PagingList(dataList, cndModel);
			
		}else{
			return new PagingList(selectList(statementName, parameterObject));
		}
		
		/*return new PagingList(selectList(statementName, parameterObject));*/
	}
	
	public PagingList pagingList(String statementName){
		return pagingList(statementName, null, null);
	}

	public PagingList pagingList(String statementName,Object parameterObject){
		return pagingList(statementName,parameterObject, null);
	}

	@SuppressWarnings("rawtypes")
	public List pagingList(String statementName,String countStatementName){
		return pagingList(statementName,null,countStatementName);
	}


	/**
	 * <strong>title</strong> : insert  <BR/>
	 * <strong>description</strong> : 데이타를 등록한다. <BR/>
	 * ArrayList일 경우 ArrayList.size()만큼 루프 후 처리한다. <BR/>
	 * <strong>date</strong> : 2010. 12. 7.
	 * @param db
	 * @param statementName
	 * @param parameterObject
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	public Object insert(String statementName,Object parameterObject){
		long start = System.currentTimeMillis();
		Object obj = null;
		if(parameterObject==null){
			getSqlSession().insert(statementName);
		}else{
			if(parameterObject instanceof ArrayList){
				List<Object> list = (ArrayList<Object>)parameterObject;
				int cnt = 0;
				for(Object tmpObj :list){
					cnt += getSqlSession().update(statementName,tmpObj);
				}
				return cnt;
			}else{
				obj = getSqlSession().insert(statementName,parameterObject);
			}
		}

		long end = System.currentTimeMillis();
		writeLog(statementName,start,end);
		return obj;
	}
*/
	
	public Object insert(String statementName,Object parameterObject){
		long start = System.currentTimeMillis();
		Object obj = null;
		
		obj = getSqlSession().insert(statementName,parameterObject);
		
		long end = System.currentTimeMillis();
		writeLog(statementName,start,end);
		return obj;
	}

	
	public Object insert(String statementName){
		return insert(statementName,null);
	}

	/**
	 * <strong>title</strong> : update  <BR/>
	 * <strong>description</strong> : 데이타를 갱신한다. <BR/>
	 * ArrayList일 경우 ArrayList.size()만큼 루프 후 처리한다. <BR/>
	 * <strong>date</strong> : 2010. 12. 7.
	 * @param db
	 * @param statementName
	 * @param parameterObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int update(String statementName,Object parameterObject){
		long start = System.currentTimeMillis();
		int cnt = 0;
		if(parameterObject==null){
			cnt = getSqlSession().update(statementName);
		}else{
			if(parameterObject instanceof ArrayList){
				List<Object> list = (ArrayList<Object>)parameterObject;
				for(Object tmpObj :list){
					cnt += getSqlSession().update(statementName,tmpObj);
				}
			}else{
				cnt = getSqlSession().update(statementName,parameterObject);
			}
		}

		long end = System.currentTimeMillis();
		writeLog(statementName,start,end);
		return cnt;
	}
	
	public int update(String statementName){
		return update(statementName,null);
	}

	/**
	 * <strong>title</strong> : delete  <BR/>
	 * <strong>description</strong> : 데이타를 삭제한다. <BR/>
	 * ArrayList일 경우 ArrayList.size()만큼 루프 후 처리한다. <BR/>
	 * <strong>date</strong> : 2010. 12. 7.
	 * @param db
	 * @param statementName
	 * @param parameterObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int delete(String statementName,Object parameterObject){
		long start = System.currentTimeMillis();
		int cnt = 0;
		if(parameterObject==null){
			cnt = getSqlSession().delete(statementName);
		}else{
			if(parameterObject instanceof ArrayList){
				List<Object> list = (ArrayList<Object>)parameterObject;
				for(Object tmpObj :list){
					cnt += getSqlSession().update(statementName,tmpObj);
				}
			}else{
				cnt = getSqlSession().delete(statementName,parameterObject);
			}
		}
		long end = System.currentTimeMillis();
		writeLog(statementName,start,end);
		return cnt;
	}
	
	public int delete(String statementName){
		return delete(statementName,null);
	}
}
