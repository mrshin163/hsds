
-- ==========================
-- 1. ī��ó����Ȳ
-- ==========================

-- -----------------------------------------------------------
-- �̻��
-- -----------------------------------------------------------
SELECT '00' AS APPROVAL_STATUS_CD
     , '�̻��' AS APPROVAL_STATUS_NM
     , COUNT(*) AS CNT
     , NVL(SUM(C.AMT_AMOUNT), 0) AS AMT_AMOUNT 
     , NVL(SUM(C.VAT_AMOUNT), 0) AS VAT_AMOUNT 
     , NVL(SUM(C.REQUEST_AMOUNT), 0) AS REQUEST_AMOUNT 
  FROM CARD_AQ_TMP C
     , EAC_CARD_AQ_MGMT G 
 WHERE 1=1
      AND C.CARD_NUM        = G.CARD_NUM
      AND C.AUTH_DATE       = G.AUTH_DATE
      AND C.AUTH_NUM        = G.AUTH_NUM
      AND C.GEORAE_STAT     = G.GEORAE_STAT
      AND C.REQUEST_AMOUNT  = G.REQUEST_AMOUNT
      AND C.GEORAE_COLL     = G.GEORAE_COLL
      AND (G.CARD_USING_TP_CD = '1' OR G.CARD_USING_TP_CD IS NULL) -- ���ο�  �Ǵ� ���� �ȵ� ��       
--      AND C.AUTH_DATE   like '201412%'           -- �˻����� : ��¥
      AND (C.CARD_NUM, C.AUTH_DATE, C.AUTH_NUM, C.GEORAE_STAT, C.REQUEST_AMOUNT, C.GEORAE_COLL) 
         NOT IN ( SELECT I.CARD_NUM, I.AUTH_DATE, I.AUTH_NUM, I.GEORAE_STAT, I.REQUEST_AMOUNT, I.GEORAE_COLL
                    FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                   WHERE 1=1
                     AND A.COMPANY_CD = I.COMPANY_CD
                     AND A.APPROVAL_ID = I.APPROVAL_ID
                     AND A.APPROVAL_STATUS_CD IN ('NREJ','NREQ','NING','NAPR','EAPR') -- ����
                     AND A.DEL_YN = 'N' --����
                )                                           
-- -----------------------------------------------------------
UNION     
-- -----------------------------------------------------------
-- �����, ������, ����Ϸ�, ��ǥ�Ϸ�
-- -----------------------------------------------------------
SELECT A.APPROVAL_STATUS_CD
     , FN_GET_COM_CD('PHARM','APPROVAL_STATUS_CD',A.APPROVAL_STATUS_CD) AS APPROVAL_STATUS_NM
     , COUNT(*) AS CNT
     , NVL(SUM(AD.AMT_AMOUNT), 0) AS AMT_AMOUNT 
     , NVL(SUM(AD.VAT_AMOUNT), 0) AS VAT_AMOUNT 
     , NVL(SUM(AD.REQUEST_AMOUNT), 0) AS REQUEST_AMOUNT 
  FROM CARD_AQ_TMP C
     , EAC_APPROVAL A
     , EAC_APPROVAL_ITEM AD
     , EAC_CARD_AQ_MGMT G 
 WHERE 1=1
      AND A.COMPANY_CD      = 'PHARM'                                                      
      AND A.DEL_YN          = 'N'
      AND A.APPROVAL_STATUS_CD IN ('NREJ', 'NREQ', 'NING', 'NAPR', 'EAPR') -- ����
      AND (G.CARD_USING_TP_CD = '1' OR G.CARD_USING_TP_CD IS NULL) -- ���ο�  �Ǵ� ���� �ȵ� ��       
      AND C.CARD_NUM        = AD.CARD_NUM
      AND C.AUTH_DATE       = AD.AUTH_DATE
      AND C.AUTH_NUM        = AD.AUTH_NUM
      AND C.GEORAE_STAT     = AD.GEORAE_STAT
      AND C.REQUEST_AMOUNT  = AD.REQUEST_AMOUNT
      AND C.GEORAE_COLL     = AD.GEORAE_COLL
      AND AD.COMPANY_CD     = A.COMPANY_CD
      AND AD.APPROVAL_ID    = A.APPROVAL_ID
      AND C.CARD_NUM        = G.CARD_NUM
      AND C.AUTH_DATE       = G.AUTH_DATE
      AND C.AUTH_NUM        = G.AUTH_NUM
      AND C.GEORAE_STAT     = G.GEORAE_STAT
      AND C.REQUEST_AMOUNT  = G.REQUEST_AMOUNT
      AND C.GEORAE_COLL     = G.GEORAE_COLL      
--      AND C.AUTH_DATE   like '201412%'           -- �˻����� : ��¥
--      AND A.ACCOUNT_CD      = '2'                -- �˻����� : ��������
--      AND A.BUDGET_DEPT_CD  = ''               -- �˻����� : ����(����μ��ڵ�)
--      AND AD.JUNPYO_INS_DATE like '201412%'    -- �˻����� : ȸ����ǥ��ȣ
    GROUP BY A.APPROVAL_STATUS_CD       
-- -----------------------------------------------------------
UNION     
-- -----------------------------------------------------------
-- ���ο뵵
-- -----------------------------------------------------------
SELECT '11' AS APPROVAL_STATUS_CD
     , '�������' AS APPROVAL_STATUS_NM
     , COUNT(*) AS CNT
     , NVL(SUM(C.AMT_AMOUNT), 0) AS AMT_AMOUNT 
     , NVL(SUM(C.VAT_AMOUNT), 0) AS VAT_AMOUNT 
     , NVL(SUM(C.REQUEST_AMOUNT), 0) AS REQUEST_AMOUNT 
  FROM CARD_AQ_TMP C
     , EAC_CARD_AQ_MGMT G 
 WHERE 1=1
      AND C.CARD_NUM        = G.CARD_NUM
      AND C.AUTH_DATE       = G.AUTH_DATE
      AND C.AUTH_NUM        = G.AUTH_NUM
      AND C.GEORAE_STAT     = G.GEORAE_STAT
      AND C.REQUEST_AMOUNT  = G.REQUEST_AMOUNT
      AND C.GEORAE_COLL     = G.GEORAE_COLL
      AND G.CARD_USING_TP_CD = '2' -- ���ο�       


-- ==========================
-- 2. ī��ó����Ȳ
-- ==========================
SELECT  
         C.CARD_NUM
       , E.USER_ID
       , FN_GET_USER_INFO(E.COMPANY_CD, E.USER_ID, 'USER_NM') AS USER_NM
       , C.AUTH_DATE
       , C.AMT_AMOUNT
       , C.VAT_AMOUNT
       , C.REQUEST_AMOUNT
       , A.ACCOUNT_CD
       , FN_GET_COM_CD(A.COMPANY_CD, 'ACCOUNT_CD',A.ACCOUNT_CD) AS ACCOUNT_NM
       , AD.ACCOUNT2_CD
       , FN_GET_COM_CD(A.COMPANY_CD, 'ACCOUNT2_CD',AD.ACCOUNT2_CD) AS ACCOUNT2_NM
       , AD.DETAILS
       , A.APPROVAL_ID
       , AD.SEQ
       , AD.JUNPYO_INS_DATE
       , AD.JUNPYO_ST_CD
       , FN_GET_COM_CD(A.COMPANY_CD, 'JUNPYO_ST_CD',AD.JUNPYO_ST_CD) AS JUNPYO_ST_NM
       , A.APPROVAL_STATUS_CD
       , FN_GET_COM_CD(A.COMPANY_CD, 'APPROVAL_STATUS_CD',A.APPROVAL_STATUS_CD) AS APPROVAL_STATUS_NM
  FROM CARD_AQ_TMP C
     , EAC_APPROVAL A
     , EAC_APPROVAL_ITEM AD
     , EAC_CARD_AQ_MGMT G
     , EAC_CARD E 
 WHERE 1=1
      AND A.COMPANY_CD      = 'PHARM'                                                      
      AND A.DEL_YN          = 'N'
      AND C.CARD_NUM        = AD.CARD_NUM
      AND C.AUTH_DATE       = AD.AUTH_DATE
      AND C.AUTH_NUM        = AD.AUTH_NUM
      AND C.GEORAE_STAT     = AD.GEORAE_STAT
      AND C.REQUEST_AMOUNT  = AD.REQUEST_AMOUNT
      AND C.GEORAE_COLL     = AD.GEORAE_COLL
      AND AD.COMPANY_CD     = A.COMPANY_CD
      AND AD.APPROVAL_ID    = A.APPROVAL_ID
      AND C.CARD_NUM        = G.CARD_NUM
      AND C.AUTH_DATE       = G.AUTH_DATE
      AND C.AUTH_NUM        = G.AUTH_NUM
      AND C.GEORAE_STAT     = G.GEORAE_STAT
      AND C.REQUEST_AMOUNT  = G.REQUEST_AMOUNT
      AND C.GEORAE_COLL     = G.GEORAE_COLL
      AND C.CARD_NUM        = E.CARD_NO
      AND A.COMPANY_CD      = E.COMPANY_CD      
--      AND A.APPROVAL_STATUS_CD = 'NREQ' -- �Ķ���� :  ǰ�ǻ����ڵ�
--      AND (G.CARD_USING_TP_CD = '1' OR G.CARD_USING_TP_CD IS NULL) -- ���ο�  �Ǵ� ���� �ȵ� ��       
--      AND C.AUTH_DATE   like '201412%'           -- �˻����� : ��¥
--      AND A.ACCOUNT_CD      = '2'                -- �˻����� : ��������
--      AND A.BUDGET_DEPT_CD  = ''               -- �˻����� : ����(����μ��ڵ�)
--      AND AD.JUNPYO_INS_DATE like '201412%'    -- �˻����� : ȸ����ǥ��ȣ



-- ==========================
-- 3. �������
-- ==========================

-- 1. �������
SELECT APPROVAL_ID
     , '���' AS APPROVAL_STATUS_NM
     , DECODE(A.RRQ_USER_ID, NULL, A.RQ_USER_ID, A.RRQ_USER_ID) AS USER_ID
     , DECODE(A.RRQ_USER_ID, NULL, A.RQ_USER_NM, A.RRQ_USER_NM) AS USER_NM
     , DECODE(A.RRQ_USER_ID, NULL, A.RQ_DEPT_NM, A.RRQ_DEPT_NM) AS DEPT_NM
     , DECODE(A.RRQ_USER_ID, NULL, A.RQ_DUTY_NM, A.RRQ_DUTY_NM) AS DUTY_NM
     , A.RQ_OPINION AS OPINION
     , A.RQ_DATE AS APR_DATE
  FROM EAC_APPROVAL A
 WHERE 1=1
--   AND A.APPROVAL_ID = '' -- �Ķ���� : ǰ�ǹ�ȣ
-- --------------------------------------------------   
UNION
-- --------------------------------------------------   
-- 2. �����������
SELECT A.APPROVAL_ID
     , FN_GET_COM_CD(L.COMPANY_CD, 'APR_CD', L.APR_CD) AS APPROVAL_STATUS_NM
     , L.AP_USER_ID AS USER_ID
     , L.AP_USER_NM AS USER_NM
     , L.AP_DEPT_NM AS DEPT_NM
     , L.AP_DUTY_NM AS DUTY_NM
     , L.APR_COMMENT AS OPINION
     , TO_DATE(L.APR_DATE||L.APR_TIME,'YYYYMMDDHH24MISS')   AS APR_DATE
  FROM EAC_APPROVAL A, EAC_APPROVAL_LINE L
 WHERE 1=1
--   AND A.APPROVAL_ID = '' -- �Ķ���� : ǰ�ǹ�ȣ
   AND A.COMPANY_CD = L.COMPANY_CD
   AND A.APPROVAL_ID = L.APPROVAL_ID
