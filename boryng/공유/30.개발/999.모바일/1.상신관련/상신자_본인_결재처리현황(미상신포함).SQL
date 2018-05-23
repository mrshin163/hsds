1. - 본인 조회                                                                    실제상신자 규정
         
1.1 일반 : 카드 사용날짜에 카드사용계획이 없으면서                                (상신자는 소유자ID)
          카드 소유자가 나인 것 (대체자가 있어도 무방) (소유카드)
          
1.2. 사용계획 : 카드사용계획에 카드사용날짜에 내가 배치된 것.(공용카드)(자금팀)   (상신자는 사용계획자ID) 

1.3 비서 : 카드 비서1,2에 지정된 카드 사용 건  (회장,부회장,사장 카드)            (상신자는 비서ID) 

1.4 사용실적 : 카드실제 사용자가 나인것 (공용카드)(영업팀)                        (상신자는 실사용자ID) 

2. - 대체자 조회
          
2.1. 대체자 : 대체자에 내가 지정된 카드 사용 건                                   (상신대체자 : 대체자ID,  상신자:소유자ID)


3.1 상신자  : 본인이 사용한 카드처리 결재현황 내역조회


SELECT 
    AA.APP_TYPE
    ,TO_CHAR(TO_DATE(AA.AUTH_DATE),'YYYY.MM.DD') AS AUTH_DATE
    ,SUBSTR(AA.AUTH_TIME,1,2)||':'||SUBSTR(AA.AUTH_TIME,3,2)||':'||SUBSTR(AA.AUTH_TIME,5,2) AS AUTH_TIME
    ,AA.MERC_NAME,AA.AMT_AMOUNT,AA.VAT_AMOUNT,AA.REQUEST_AMOUNT
    ,AA.CARD_NUM,AA.AUTH_NUM,AA.GEORAE_STAT,AA.GEORAE_COLL,AA.OWNER_ID,AA.NREQ_USER_ID,AA.APPROVAL_STATUS_CD
    ,AA.ACCOUNT_CD      
FROM (

        -- ===================================
        -- 1.1 소유자 (일반)
        -- 카드 사용날짜에 카드사용계획이 없으면서 
        -- 카드 소유자가 나인 것 (대체자가 있어도 무방) (소유카드)
        -- ===================================
        SELECT '11' AS APP_TYPE
                   , C.AUTH_DATE
                   , C.AUTH_TIME
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM
                   , C.GEORAE_STAT
                   , C.GEORAE_COLL
                   , D.USER_ID AS OWNER_ID
                   , D.USER_ID AS NREQ_USER_ID   
                   , '' AS APPROVAL_STATUS_CD    
                   , '' AS ACCOUNT_CD          
           FROM CARD_AQ_TMP C, EAC_CARD D, EAC_CARD_AQ_MGMT G
        WHERE 1=1
             AND C.CARD_NUM = D.CARD_NO
             AND D.COMPANY_CD='PHARM'
              AND C.CARD_NUM = G.CARD_NUM
              AND C.AUTH_DATE = G.AUTH_DATE
              AND C.AUTH_NUM = G.AUTH_NUM
              AND C.GEORAE_STAT = G.GEORAE_STAT
              AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
              AND C.GEORAE_COLL = G.GEORAE_COLL
             AND D.USER_ID='09158'                          -- 검색조건 : 로그인사용자ID
             AND C.AUTH_DATE BETWEEN '20150216' AND '20150223'           -- 검색조건 : 날짜 
--             AND G.CARD_USING_TP_CD = '2'           -- 검색조건 : 사용용도구분(1:법인용, 2:개인용)
             AND C.CARD_NUM NOT IN (
                                                        SELECT A.CARD_NUM                    
                                                           FROM CARD_AQ_TMP A, EAC_CARD E, EAC_CARD_SCHEDULE S
                                                         WHERE 1=1
                                                              AND A.CARD_NUM = E.CARD_NO
                                                              AND E.COMPANY_CD = 'PHARM'
                                                              AND E.COMPANY_CD=S.COMPANY_CD
                                                              AND E.CARD_NO=S.CARD_NO                  
                                                              AND A.AUTH_DATE BETWEEN S.START_DAY AND S.END_DAY 
                     )      
              AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
                     NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                                      FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                                    WHERE 1=1
                                         AND A.COMPANY_CD = I.COMPANY_CD
                                         AND A.APPROVAL_ID = I.APPROVAL_ID
                                         AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- 고정
                                         AND A.DEL_YN = 'N' --고정
                                )
UNION ALL                                    
        -- ===================================
        -- 1.2 사용계획에 내가 있는 경우
        -- 카드사용계획에 카드사용날짜에 내가 배치된 것.(공용카드)(자금팀)
        -- ===================================
        SELECT '12' AS APP_TYPE
                   , C.AUTH_DATE
                   , C.AUTH_TIME
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM           
                   , C.GEORAE_STAT
                   , C.GEORAE_COLL
                   , E.USER_ID AS OWNER_ID
                   , S.USER_ID AS NREQ_USER_ID        
                   , '' AS APPROVAL_STATUS_CD   
                   , '' AS ACCOUNT_CD      
           FROM CARD_AQ_TMP C, EAC_CARD E, EAC_CARD_SCHEDULE S, EAC_CARD_AQ_MGMT G
         WHERE 1=1
              AND C.CARD_NUM = E.CARD_NO
              AND E.COMPANY_CD = 'PHARM'                                                      
              AND E.COMPANY_CD=S.COMPANY_CD
              AND E.CARD_NO=S.CARD_NO   
              AND C.CARD_NUM = G.CARD_NUM
              AND C.AUTH_DATE = G.AUTH_DATE
              AND C.AUTH_NUM = G.AUTH_NUM
              AND C.GEORAE_STAT = G.GEORAE_STAT
              AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
              AND C.GEORAE_COLL = G.GEORAE_COLL
              AND S.USER_ID = '09158'     -- 검색조건 : 로그인한 사용자ID
              AND C.AUTH_DATE BETWEEN '20150216' AND '20150223'           -- 검색조건 : 날짜                
--              AND G.CARD_USING_TP_CD = '2'           -- 검색조건 : 사용용도구분(1:법인용, 2:개인용)
              AND C.AUTH_DATE BETWEEN S.START_DAY AND S.END_DAY 
              AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
                     NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                                      FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                                    WHERE 1=1
                                         AND A.COMPANY_CD = I.COMPANY_CD
                                         AND A.APPROVAL_ID = I.APPROVAL_ID
                                         AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- 고정
                                         AND A.DEL_YN = 'N' --고정
                                )                                    
UNION  ALL                                  
        -- ===================================
        -- 1.3 비서 : 카드 비서1,2에 지정된 카드 사용 건  (회장,부회장,사장 카드)
        -- ===================================
        SELECT '13' AS APP_TYPE
                   , C.AUTH_DATE
                   , C.AUTH_TIME
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM           
                   , C.GEORAE_STAT
                   , C.GEORAE_COLL
                   , E.USER_ID AS OWNER_ID
                   , decode('09158', M.SEC1_USER_ID,M.SEC1_USER_ID,M.SEC2_USER_ID,M.SEC2_USER_ID,null)  AS NREQ_USER_ID         -- 검색조건 : 로그인한 사용자ID (비서 ID)  
                   , '' AS APPROVAL_STATUS_CD
                   , '' AS ACCOUNT_CD      
           FROM CARD_AQ_TMP C, EAC_CARD E, EAC_CARD_MGMT M, EAC_CARD_AQ_MGMT G 
         WHERE 1=1
              AND C.CARD_NUM = E.CARD_NO
              AND E.COMPANY_CD = 'PHARM'                                                      
              AND E.COMPANY_CD=M.COMPANY_CD
              AND E.CARD_NO=M.CARD_NO   
              AND C.CARD_NUM = G.CARD_NUM
              AND C.AUTH_DATE = G.AUTH_DATE
              AND C.AUTH_NUM = G.AUTH_NUM
              AND C.GEORAE_STAT = G.GEORAE_STAT
              AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
              AND C.GEORAE_COLL = G.GEORAE_COLL
              AND (M.SEC_CARD_YN='Y' AND (M.SEC1_USER_ID = '09158' OR M.SEC2_USER_ID = '09158'))   -- 검색조건 : 로그인한 사용자ID (비서 ID)
              AND C.AUTH_DATE BETWEEN '20150216' AND '20150223'          -- 검색조건 : 날짜
--              AND G.CARD_USING_TP_CD = '2'           -- 검색조건 : 사용용도구분(1:법인용, 2:개인용)
              AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
                     NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                                      FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                                    WHERE 1=1
                                         AND A.COMPANY_CD = I.COMPANY_CD
                                         AND A.APPROVAL_ID = I.APPROVAL_ID
                                         AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- 고정
                                         AND A.DEL_YN = 'N' --고정
                                )       
UNION  ALL                                  
        -- ===================================
        -- 1.4 사용실적 : 카드실제 사용자가 나인것 (공용카드)(영업팀)
        -- ===================================
        SELECT '14' AS APP_TYPE
                   , C.AUTH_DATE
                   , C.AUTH_TIME
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM           
                   , C.GEORAE_STAT
                   , C.GEORAE_COLL
                   , (SELECT USER_ID FROM EAC_CARD R WHERE C.CARD_NUM=R.CARD_NO) AS OWNER_ID
                   , G.ACT_USER_ID AS NREQ_USER_ID   
                   , '' AS APPROVAL_STATUS_CD  
                   , '' AS ACCOUNT_CD            
           FROM CARD_AQ_TMP C, EAC_CARD_AQ_MGMT G 
         WHERE 1=1
              AND C.CARD_NUM = G.CARD_NUM
              AND C.AUTH_DATE = G.AUTH_DATE
              AND C.AUTH_NUM = G.AUTH_NUM
              AND C.GEORAE_STAT = G.GEORAE_STAT
              AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
              AND C.GEORAE_COLL = G.GEORAE_COLL
              AND C.AUTH_DATE BETWEEN '20150216' AND '20150223'           -- 검색조건 : 날짜
              AND G.ACT_USER_ID =  '09158'              -- 검색조건 : 로그인사용자ID 
--              AND G.CARD_USING_TP_CD = '2'           -- 검색조건 : 사용용도구분(1:법인용, 2:개인용)
              AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
                     NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                                      FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                                    WHERE 1=1
                                         AND A.COMPANY_CD = I.COMPANY_CD
                                         AND A.APPROVAL_ID = I.APPROVAL_ID
                                         AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- 고정
                                         AND A.DEL_YN = 'N' --고정
                                )
             
UNION  ALL 

-- ===================================
-- 2.1 대체자 : 대체자에 내가 지정된 카드 사용 건 
-- ===================================
SELECT '21' AS APP_TYPE
           , C.AUTH_DATE
           , C.AUTH_TIME
           , C.MERC_NAME
           , C.AMT_AMOUNT
           , C.VAT_AMOUNT
           , C.REQUEST_AMOUNT
           , C.CARD_NUM
           , C.AUTH_NUM           
           , C.GEORAE_STAT
           , C.GEORAE_COLL
           , E.USER_ID AS OWNER_ID
           , M.REP_USER_ID AS NREQ_USER_ID     
           , '' AS APPROVAL_STATUS_CD
           , '' AS ACCOUNT_CD      
   FROM CARD_AQ_TMP C, EAC_CARD E, EAC_CARD_MGMT M, EAC_CARD_AQ_MGMT G 
 WHERE 1=1
      AND C.CARD_NUM = E.CARD_NO
      AND E.COMPANY_CD = 'PHARM'                                                      
      AND E.COMPANY_CD=M.COMPANY_CD
      AND E.CARD_NO=M.CARD_NO   
      AND C.CARD_NUM = G.CARD_NUM
      AND C.AUTH_DATE = G.AUTH_DATE
      AND C.AUTH_NUM = G.AUTH_NUM
      AND C.GEORAE_STAT = G.GEORAE_STAT
      AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
      AND C.GEORAE_COLL = G.GEORAE_COLL
      AND (M.REP_CARD_YN='Y' AND M.REP_USER_ID = '09158' )   -- 검색조건 : 로그인한 사용자ID (대체자)
      AND C.AUTH_DATE BETWEEN '20150216' AND '20150223'           -- 검색조건 : 날짜
--      AND G.CARD_USING_TP_CD = '2'           -- 검색조건 : 사용용도구분(1:법인용, 2:개인용)
      AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
             NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                              FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                            WHERE 1=1
                                 AND A.COMPANY_CD = I.COMPANY_CD
                                 AND A.APPROVAL_ID = I.APPROVAL_ID
                                 AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- 고정
                                 AND A.DEL_YN = 'N' --고정
                        )


UNION  ALL 

-- ===================================
-- 3.1 상신자  : 본인이 사용한 카드처리 결재현황 내역조회 
-- ===================================
SELECT 
     '31' AS APP_TYPE
     ,I.AUTH_DATE
     ,I.AUTH_TIME
     ,I.MERC_NAME
     ,I.AMT_AMOUNT
     ,I.VAT_AMOUNT
     ,I.REQUEST_AMOUNT
     ,I.CARD_NUM
     ,I.AUTH_NUM
     ,I.GEORAE_STAT
     ,I.GEORAE_COLL
     ,A.RQ_USER_ID AS OWNER_ID
     ,A.RQ_USER_ID AS NREQ_USER_ID 
     ,A.APPROVAL_STATUS_CD
     ,A.ACCOUNT_CD
FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
WHERE 1=1
     AND A.COMPANY_CD = I.COMPANY_CD
     AND A.APPROVAL_ID = I.APPROVAL_ID
     AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR','NREJ') -- 고정
     AND A.DEL_YN = 'N' --고정
     --AND I.SEQ='1'
     AND A.ACCOUNT_CD = '1' -- 계정구분 
     AND A.RQ_USER_ID = '09158' -- 상신자 사번 
     AND I.AUTH_DATE BETWEEN '20150216' AND '20150223' -- 카드거래(승인일자)
     
     
) AA  
ORDER BY AA.AUTH_DATE||AA.AUTH_TIME DESC    



-- AND I.SEQ = '1' -- 품의서일련번호      
-- 계정구분  1	세미나비 2 심포지움 3 영업경비 4 기타경비 5 현업 및 일반경비
     
-- 상신자 > T 임시저장  NREQ 상신중  NING 결재중  NAPR 결재완료 NCNL 상신취소 NREJ 반려 EAPR 전표완료 
-- 심포지움카드사용자 또는 대체자 > T 임시저장  SR 결재요청  SING 결재요청처리중  SAPR 결재요청처리완료 SREJ 결재요청반려

SELECT 
 *
FROM EAC_APPROVAL A WHERE A.RQ_USER_ID = '09158' AND APPROVAL_STATUS_CD NOT IN 'T' -- 상신자 사번 ;

SELECT * FROM EAC_APPROVAL_ITEM WHERE USER_ID = '09158' AND APPROVAL_ID='APP2015021100087' ;


미상신
임시저장
상신중
결재중
결재완료
상신취소
반려
전표완료
결재요청
결재요청처리중
결재요청처리완료
결재요청반려