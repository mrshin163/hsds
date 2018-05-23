-- =============================
-- 1.결재선 존재 체크 (계정코드, 예산부서, 금액)
-- =============================

select PD.* 
  from SYS_APPROVAL_POLICY P, SYS_APPROVAL_POLICY_DTL PD
 where P.COMPANY_CD='PHARM'
   and P.USE_YN = 'Y'
   and P.DEL_YN = 'N'
   and P.ACCOUNT_CD = '1' -- 검색조건 : 계정코드
   and (P.DEPT_CD IS NULL OR P.DEPT_CD='53210') -- 검색조건 : 예산부서코드
   and 1060000 BETWEEN P.MIN_AMOUNT AND P.MAX_AMOUNT -- 검색조건 : 금액
   and P.COMPANY_CD = PD.COMPANY_CD
   and P.POLICY_ID = PD.POLICY_ID

-- =============================
-- 2.결재라인 생성
-- =============================
select P.POLICY_ID
     , PD.SEQ
     , PD.APPROVAL_LINE_DEF_CD
     , PD.APPROVAL_LINE_DEF_VAL   
     , CASE APPROVAL_LINE_DEF_CD 
       WHEN 'U' THEN
            FN_GET_USER_INFO(P.COMPANY_CD, PD.APPROVAL_LINE_DEF_VAL,'ALL') 
       WHEN 'D' THEN
            FN_GET_DEPT_MGR_INFO(P.COMPANY_CD, '53210','ALL') -- 검색조건 : 예산부서코드
       WHEN 'P' THEN
            FN_GET_UPDEPT_MGR_INFO(P.COMPANY_CD, '53210','ALL') -- 검색조건 : 예산부서코드
       WHEN 'E' THEN
            FN_GET_UNDEPT_MGR_INFO(P.COMPANY_CD, '53210','ALL') -- 검색조건 : 예산부서코드
       ELSE
            NULL
       END AS AP_USER_INFO 
  from SYS_APPROVAL_POLICY P, SYS_APPROVAL_POLICY_DTL PD
 where P.COMPANY_CD='PHARM' -- 고정 : 회사코드로 세션에서 가져다가 넣을 것
   and P.USE_YN = 'Y' -- 고정
   and P.DEL_YN = 'N' -- 고정
   and P.ACCOUNT_CD = '1' -- 검색조건 : 계정코드
   and (P.DEPT_CD IS NULL OR P.DEPT_CD='53210') -- 검색조건 : 예산부서코드
   and 1060000 BETWEEN P.MIN_AMOUNT AND P.MAX_AMOUNT -- 검색조건 : 금액
   and P.COMPANY_CD = PD.COMPANY_CD
   and P.POLICY_ID = PD.POLICY_ID


   