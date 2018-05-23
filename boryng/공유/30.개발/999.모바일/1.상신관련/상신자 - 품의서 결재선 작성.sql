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



		SELECT distinct *
		  FROM (SELECT POLICY_ID,
		               SEQ,
		               APPROVAL_LINE_DEF_CD,
		               APPROVAL_LINE_DEF_VAL,
		               AP_USER_INFO,
		               SUBSTR (ap_user_info, -8, 5) AS DEPT_CD,
		               SUBSTR (ap_user_info, -2, 1) AS DEPT_LVL
		          FROM (	
		                SELECT P.POLICY_ID
		                     , PD.SEQ
		                     , PD.APPROVAL_LINE_DEF_CD
		                     , PD.APPROVAL_LINE_DEF_VAL   
		                     , CASE APPROVAL_LINE_DEF_CD 
		                       WHEN 'U' THEN
		                            FN_GET_USER_INFO(P.COMPANY_CD, PD.APPROVAL_LINE_DEF_VAL,'ALL') 
		                       WHEN 'D' THEN
		                            FN_GET_DEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- 검색조건 : 예산부서코드
		                       WHEN 'P' THEN
		                            FN_GET_UPDEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- 검색조건 : 예산부서코드
		                       WHEN 'PP' THEN
		                            FN_GET_UPUPDEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- 검색조건 : 예산부서코드
		                       WHEN 'UD' THEN
		                            FN_GET_UNDEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- 검색조건 : 예산부서코드
		                       WHEN 'UP' THEN
		                            FN_GET_UNUPDEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- 검색조건 : 예산부서코드
		                       WHEN 'UPP' THEN
		                            FN_GET_UNUPUPDEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- 검색조건 : 예산부서코드
		                       ELSE
		                            NULL
		                       END AS AP_USER_INFO 
						  FROM (
					                        SELECT *
					                          FROM (
					                                   select q.*                                            
					                                     from SYS_APPROVAL_POLICY q 
	                                                     where q.COMPANY_CD=#{c_cd} -- 고정 : 회사코드로 세션에서 가져다가 넣을 것
	                                                       and q.USE_YN = 'Y' -- 고정
	                                                       and q.DEL_YN = 'N' -- 고정
	                                                       and q.ACCOUNT_CD = #{account_cd} -- 검색조건 : 계정코드
	                                                       and (q.DEPT_CD IS NULL OR q.DEPT_CD=#{dept_cd}) -- 검색조건 : 예산부서코드
	                                                       and #{amount} BETWEEN q.MIN_AMOUNT AND q.MAX_AMOUNT -- 검색조건 : 금액
	                                    				 order by dept_cd
	                              				   ) r
					                         WHERE 1=1
					                           AND ROWNUM = 1                   
	                            ) P, SYS_APPROVAL_POLICY_DTL PD
						  WHERE 1=1
							AND P.COMPANY_CD=#{c_cd}
							AND P.USE_YN = 'Y'
							AND P.DEL_YN = 'N'
							AND P.ACCOUNT_CD = #{account_cd}
							AND (P.DEPT_CD IS NULL OR P.DEPT_CD=#{dept_cd})
							AND #{amount} BETWEEN P.MIN_AMOUNT AND P.MAX_AMOUNT
							AND P.COMPANY_CD = PD.COMPANY_CD
							AND P.POLICY_ID = PD.POLICY_ID
						)
				)
		 WHERE 1 = 1 
		   and (DEPT_LVL <![CDATA[>=]]> 4  AND #{dept_cd} IN (SELECT DEPT_CD FROM SYS_DEPT WHERE 1=1 CONNECT BY PRIOR DEPT_CD=UP_DEPT_CD START WITH DEPT_CD='85000')) 
		       OR 
		       (DEPT_LVL <![CDATA[>=]]> 3 AND #{dept_cd} NOT IN (SELECT DEPT_CD FROM SYS_DEPT WHERE 1=1 CONNECT BY PRIOR DEPT_CD=UP_DEPT_CD START WITH DEPT_CD='85000'))
		 order by seq   


--V52140$준법경영팀$52140$준법경영팀$6010$$23$$190$$$$$$N$Y
--01155$김경태$55210$서울CLINIC 1팀$4010$차장$14$팀장$140$차장$02-708-8015$02-3395-7781$010-4599-3398$ktkim@boryung.co.kr$N$N
--13002$안재현$30000   $전략기획본부$1050     $전무    $04       $본부장$050$전무$02-708-8025$$010-4722-7869$jaehyun.an@boryung.co.kr$N$N


--10153$박세용$12000   $인사팀      $4010      $차장    $14      $팀장  $140      $차장   $02-708-8120 $02-437-9503$010-2927-2923$psypsy95@boryung.co.kr$N$N
--사번 /이름  /부서코드/부서명      /직급코드 /직급명  /직책코드/직책명 /호칭코드/호칭명  /사무실번호   / 집전화 / 핸드폰 / 이메일 / 퇴직여부 / 가상여부?
-- 0   / 1    / 2      / 3          / 4       / 5      / 6      / 7     / 8      / 9      / 10