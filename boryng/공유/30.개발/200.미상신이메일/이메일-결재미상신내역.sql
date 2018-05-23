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
        -- 1.1 ������ (�Ϲ�)
        -- ī�� ��볯¥�� ī�����ȹ�� �����鼭 
        -- ī�� �����ڰ� ���� �� (��ü�ڰ� �־ ����) (����ī��)
        -- ī�� �����ڿ��� ������ ������
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
             AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1') -- ���ο� ����
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
                                         AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- ����
                                         AND A.DEL_YN = 'N' --����
                                )
UNION ALL                                    
        -- ===================================
        -- 1.2 ����ȹ�� �����ϴ� ��� .(����ī��)(�ڱ���)
        -- ī�����ȹ�� ī���볯¥�� ��ġ�� ����� �����̹Ƿ� ��ȹ�� �ִ� ������� ���� ����
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
             AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1') -- ���ο� ����
              AND C.AUTH_DATE BETWEEN S.START_DAY AND S.END_DAY 
              AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
                     NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                                      FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                                    WHERE 1=1
                                         AND A.COMPANY_CD = I.COMPANY_CD
                                         AND A.APPROVAL_ID = I.APPROVAL_ID
                                         AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- ����
                                         AND A.DEL_YN = 'N' --����
                                )                 
UNION  ALL                                  
        -- ===================================
        -- 1.3 �� : ī�� ��1,2�� ������ ī�� ��� ��  (ȸ��,��ȸ��,���� ī��)
        -- ===================================
        SELECT '13' AS APP_TYPE
                   , C.AUTH_DATE
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM           
                   , decode(M.SEC1_USER_ID, null, M.SEC2_USER_ID,M.SEC1_USER_ID)  AS OWNER_ID -- ��
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
             AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1') -- ���ο� ����
              AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
                     NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                                      FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                                    WHERE 1=1
                                         AND A.COMPANY_CD = I.COMPANY_CD
                                         AND A.APPROVAL_ID = I.APPROVAL_ID
                                         AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- ����
                                         AND A.DEL_YN = 'N' --����
                                )       
UNION  ALL                       
        -- ===================================
        -- 1.4 ������ : ī����� ����ڰ� ������ �� (����ī��)(������)
        -- �ǻ���ڰ� ���� ������.�ǻ���ڿ��� ������ ����
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
              AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1') -- ���ο� ����
              AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
                     NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                                      FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                                    WHERE 1=1
                                         AND A.COMPANY_CD = I.COMPANY_CD
                                         AND A.APPROVAL_ID = I.APPROVAL_ID
                                         AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- ����
                                         AND A.DEL_YN = 'N' --����
                                )
UNION ALL                                                     
        -- ===================================
        -- 1.5 ������ : ī����� ����ڰ� ������ ��. (����ī��)(������)
        -- �ǻ���ڰ� ������ ���� ������. ����ī������ڰ� ������.
        -- ===================================
        SELECT '15' AS APP_TYPE
                   , C.AUTH_DATE
                   , C.MERC_NAME
                   , C.AMT_AMOUNT
                   , C.VAT_AMOUNT
                   , C.REQUEST_AMOUNT
                   , C.CARD_NUM
                   , C.AUTH_NUM           
                   , E.COM_CARD_MGR_ID AS OWNER_ID    -- ����ī�� ������       
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
              AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1') -- ���ο� ����
              AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
                     NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                                      FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                                    WHERE 1=1
                                         AND A.COMPANY_CD = I.COMPANY_CD
                                         AND A.APPROVAL_ID = I.APPROVAL_ID
                                         AND A.APPROVAL_STATUS_CD IN ('NREQ','NING','NAPR','EAPR','SR','SING','SAPR') -- ����
                                         AND A.DEL_YN = 'N' --����
                                )
    -- ------------------------------------------------------------------------                                
    ) TTT
WHERE 1=1
  AND TO_USER IS NOT NULL
ORDER BY TO_USER               