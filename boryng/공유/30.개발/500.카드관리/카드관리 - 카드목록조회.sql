SELECT E.CARD_NO
     , E.CARD_TP_CD
     , E.CARD_TP_NM   
     , E.CARD_ST_CD
     , E.CARD_ST_NM
     , E.JOIN_DATE
     , E.EXPIRE_DATE
     , E.USER_ID
     , U.USER_NM
     , U.EMP_NO
     , U.DEPT_NM
     , U.TITLE_NM
     , M.REP_USER_ID
     , M.SEC1_USER_ID
     , M.SEC2_USER_ID
     , DECODE(S.START_DAY,NULL,'N','Y') S_CARD_YN
     , S.START_DAY
     , S.END_DAY
     , S.USER_ID AS S_USER_ID
  FROM EAC_CARD E, EAC_CARD_MGMT M, EAC_CARD_SCHEDULE S, V_SYS_USER U
 WHERE 1=1
   AND E.COMPANY_CD = M.COMPANY_CD (+)
   AND E.CARD_NO    = M.CARD_NO (+)
   AND E.COMPANY_CD = S.COMPANY_CD (+)
   AND E.CARD_NO    = S.CARD_NO (+)
   AND E.COMPANY_CD = U.COMPANY_CD (+)
   AND E.USER_ID    = U.USER_ID (+)
   AND E.USE_YN = 'Y'
   AND E.DEL_YN = 'N'
   AND M.USE_YN(+) = 'Y'
   AND M.DEL_YN(+) = 'N'
   AND S.DEL_YN(+) = 'N'
   AND E.CARD_OWN_CD = '2' -- 검색조건 : 카드종류(법인카드1/법인개인카드2)
--   AND E.CARD_NO = '' -- 검색조건 : 카드번호
--   AND E.USER_ID = '' -- 검색조건 : 카드소유자
--   AND E.CARD_ST_CD = '' -- 검색조건 : 카드상태
--   AND M.COM_CARD_YN = 'Y' -- 검색조건 : 공용카드건만 조회
   AND M.REP_CARD_YN = 'Y' -- 검색조건 : 대체자 카드건만 조회
--   AND M.SEC_CARD_YN = 'Y' -- 검색조건 : 공용카드건만 조회


      