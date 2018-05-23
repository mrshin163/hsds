-- ==================================
-- 거래처  조회
-- ==================================
SELECT CUSTOMER_CD -- 거래처코드
     , CUSTOMER_NM -- 거래처명
     , CEO_NM      -- 대표자명
     , BIZ_NO      -- 사업자번호
     , ADDRESS     -- 주소
     , CUSTOMER_TP_NM -- 거래처구분명
     , CUSTOMER_ST_NM -- 거래처상태명
  FROM EAC_CUSTOMER
 WHERE 1=1
   AND COMPANY_CD = 'PHARM'
   AND USE_YN        = 'Y'           -- 고정
   AND DEL_YN        = 'N'           -- 고정
   AND CUSTOMER_NM LIKE '%%' -- 검색조건 : 거래처명 
   AND CUSTOMER_TP_CD = ''   -- 검색조건 : 거래처구분코드
   AND CUSTOMER_ST_CD = ''   -- 검색조건 : 거래처상태코드