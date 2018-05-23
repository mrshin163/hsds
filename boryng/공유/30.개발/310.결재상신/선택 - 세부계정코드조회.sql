-- ==================================
-- 세부계정코드 조회
-- ==================================

SELECT COM_CD    AS ACCOUNT2_CD -- 세부계정코드
     , COM_CD_NM AS ACCOUNT2_NM -- 세부계정명
  FROM SYS_COM_CD
 WHERE 1=1
   AND COMPANY_CD    = 'PHARM'       -- 고정 : 세션 회사코드
   AND USE_YN        = 'Y'           -- 고정
   AND DEL_YN        = 'N'           -- 고정
   AND COM_CD_GRP_ID = 'ACCOUNT2_CD' -- 고정
   AND COM_CD_NM like '%유지%'           -- 검색조건 : 세부계정명 (필수)
 ORDER BY COM_CD_NM ASC   