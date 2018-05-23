select
         APP_TYPE
       , AUTH_DATE
       , MERC_NAME
       , AMT_AMOUNT
       , VAT_AMOUNT
       , REQUEST_AMOUNT
       , CARD_NUM
       , AUTH_NUM
       , OWNER_ID
       , TO_USER
FROM (       
    -- ------------------------------------------------------------------------                                
        -- ===================================
        -- 1.1 소유자 (일반)
        -- 카드 사용날짜에 카드사용계획이 없으면서 
        -- 카드 소유자가 나인 것 (대체자가 있어도 무방) (소유카드)
        -- 카드 소유자에게 메일이 보내짐
        -- ===================================
        SELECT '11' AS APP_TYPE
                   , C.AUTH_DATE
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM
                   , D.USER_ID AS OWNER_ID
                   , FN_GET_USER_INFO(D.COMPANY_CD, D.USER_ID, 'EMAIL_ADDR') AS TO_USER
           FROM CARD_AQ_TMP C, EAC_CARD D, EAC_CARD_AQ_MGMT G
        WHERE 1=1
             AND C.CARD_NUM = D.CARD_NO
             AND D.COMPANY_CD='PHARM'
              AND D.USE_YN = 'Y'
              AND D.DEL_YN = 'N'
              AND C.CARD_NUM = G.CARD_NUM
              AND C.AUTH_DATE = G.AUTH_DATE
              AND C.AUTH_NUM = G.AUTH_NUM
              AND C.GEORAE_STAT = G.GEORAE_STAT
              AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
              AND C.GEORAE_COLL = G.GEORAE_COLL
             AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1') -- 개인용 제외
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
        -- 1.2 사용계획에 존재하는 경우 .(공용카드)(자금팀)
        -- 카드사용계획에 카드사용날짜에 배치된 사람의 실적이므로 계획에 있는 사람에게 메일 보냄
        -- ===================================
        SELECT '12' AS APP_TYPE
                   , C.AUTH_DATE
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM           
                   , S.USER_ID AS OWNER_ID
                   , FN_GET_USER_INFO(E.COMPANY_CD, S.USER_ID, 'EMAIL_ADDR') AS TO_USER
           FROM CARD_AQ_TMP C, EAC_CARD E, EAC_CARD_SCHEDULE S, EAC_CARD_AQ_MGMT G
         WHERE 1=1
              AND C.CARD_NUM = E.CARD_NO
              AND E.COMPANY_CD = 'PHARM'                                                      
              AND E.COMPANY_CD=S.COMPANY_CD
              AND E.CARD_NO=S.CARD_NO   
              AND E.USE_YN = 'Y'
              AND E.DEL_YN = 'N'
              AND C.CARD_NUM = G.CARD_NUM
              AND C.AUTH_DATE = G.AUTH_DATE
              AND C.AUTH_NUM = G.AUTH_NUM
              AND C.GEORAE_STAT = G.GEORAE_STAT
              AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
              AND C.GEORAE_COLL = G.GEORAE_COLL
             AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1') -- 개인용 제외
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
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM           
                   , decode(M.SEC1_USER_ID, null, M.SEC2_USER_ID,M.SEC1_USER_ID)  AS OWNER_ID -- 비서
                   , FN_GET_USER_INFO(E.COMPANY_CD, decode(M.SEC1_USER_ID, null, M.SEC2_USER_ID,M.SEC1_USER_ID), 'EMAIL_ADDR') AS TO_USER
           FROM CARD_AQ_TMP C, EAC_CARD E, EAC_CARD_MGMT M, EAC_CARD_AQ_MGMT G 
         WHERE 1=1
              AND C.CARD_NUM = E.CARD_NO
              AND E.COMPANY_CD = 'PHARM'                                                      
              AND E.COMPANY_CD=M.COMPANY_CD
              AND E.CARD_NO=M.CARD_NO   
              AND E.USE_YN = 'Y'
              AND E.DEL_YN = 'N'
              AND C.CARD_NUM = G.CARD_NUM
              AND C.AUTH_DATE = G.AUTH_DATE
              AND C.AUTH_NUM = G.AUTH_NUM
              AND C.GEORAE_STAT = G.GEORAE_STAT
              AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
              AND C.GEORAE_COLL = G.GEORAE_COLL
              AND M.SEC_CARD_YN='Y'
              AND M.USE_YN = 'Y'
              AND M.DEL_YN = 'N'
             AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1') -- 개인용 제외
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
        -- 1.4 사용실적 : 카드실제 사용자가 지정된 건 (공용카드)(영업팀)
        -- 실사용자가 명기된 내역임.실사용자에게 메일이 보내
        -- ===================================
        SELECT '14' AS APP_TYPE
                   , C.AUTH_DATE
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM           
                   , G.ACT_USER_ID AS OWNER_ID           
                   , FN_GET_USER_INFO('PHARM', G.ACT_USER_ID, 'EMAIL_ADDR') AS TO_USER
           FROM CARD_AQ_TMP C, EAC_CARD_AQ_MGMT G 
         WHERE 1=1
              AND C.CARD_NUM = G.CARD_NUM
              AND C.AUTH_DATE = G.AUTH_DATE
              AND C.AUTH_NUM = G.AUTH_NUM
              AND C.GEORAE_STAT = G.GEORAE_STAT
              AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
              AND C.GEORAE_COLL = G.GEORAE_COLL
              AND G.ACT_USER_ID IS NOT NULL
              AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1') -- 개인용 제외
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
        -- 1.5 사용실적 : 카드실제 사용자가 지정된 건. (공용카드)(영업팀)
        -- 실사용자가 명기되지 않은 내역임. 공용카드관리자가 수신함.
        -- ===================================
        SELECT '15' AS APP_TYPE
                   , C.AUTH_DATE
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM           
                   , E.COM_CARD_MGR_ID AS OWNER_ID    -- 공용카드 관리자       
                   , FN_GET_USER_INFO('PHARM', E.COM_CARD_MGR_ID, 'EMAIL_ADDR') AS TO_USER
           FROM CARD_AQ_TMP C, EAC_CARD_AQ_MGMT G, EAC_CARD_MGMT E 
         WHERE 1=1
              AND C.CARD_NUM = G.CARD_NUM
              AND C.AUTH_DATE = G.AUTH_DATE
              AND C.AUTH_NUM = G.AUTH_NUM
              AND C.GEORAE_STAT = G.GEORAE_STAT
              AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
              AND C.GEORAE_COLL = G.GEORAE_COLL
              AND E.COMPANY_CD = 'PHARM'
              AND E.CARD_NO = C.CARD_NUM
              AND E.USE_YN = 'Y'
              AND E.DEL_YN = 'N'
              AND E.COM_CARD_YN = 'Y'
              AND G.ACT_USER_ID IS  NULL
              AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1') -- 개인용 제외
              AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
                     NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                                      FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                                    WHERE 1=1
                                         AND A.COMPANY_CD = I.COMPANY_CD
                                         AND A.APPROVAL_ID = I.APPROVAL_ID
                                         AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- 고정
                                         AND A.DEL_YN = 'N' --고정
                                )
    -- ------------------------------------------------------------------------                                
    ) TTT
WHERE 1=1
  AND TO_USER IS NOT NULL
ORDER BY TO_USER               