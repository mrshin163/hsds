   
-- ====================================
-- ������ - ���� ��� ��ȸ (������ ���� �������ΰ�)
-- ====================================

  SELECT A.COMPANY_CD               AS COMPANY_CD,
         A.APPROVAL_ID              AS APPROVAL_ID,
         MAX(A.APPROVAL_STATUS_CD)  AS APPROVAL_STATUS_CD,
         FN_GET_COM_CD('PHARM','APPROVAL_STATUS_CD',MAX(A.APPROVAL_STATUS_CD)) AS APPROVAL_STATUS_NM,
         MAX (A.RQ_DATE)            AS RQ_DATE,
         MAX(A.APPROVAL_DATE)       AS APPROVAL_DATE,
         MAX(A.RQ_USER_ID)          AS RQ_USER_ID, --�����ID
         MAX(A.RQ_USER_NM)          AS RQ_USER_NM, --����ڸ�
         DECODE(MAX(A.RRQ_USER_ID), MAX(A.RQ_USER_ID), NULL, MAX(A.RRQ_USER_ID))   AS RRQ_USER_ID, --��ü�����ID
         DECODE(MAX(A.RRQ_USER_ID), MAX(A.RQ_USER_ID), NULL, MAX(A.RRQ_USER_NM))   AS RRQ_USER_NM, --��ü����ڸ�
         MAX (ALM.AP_DEPT_NM)       AS LAST_AP_DEPT_NM,
         MAX (ALM.AP_USER_NM)       AS LAST_AP_USER_NM,
         MAX (A.ACCOUNT_CD)         AS ACCOUNT_CD,
         MAX (A.ACCOUNT_NM)         AS ACCOUNT_NM,
         NVL (COUNT (AD.SEQ), 0)    AS ITEM_CNT,
         NVL (SUM (REQUEST_AMOUNT), 0) AS REQUEST_AMOUNT
    FROM EAC_APPROVAL A,
         EAC_APPROVAL_ITEM AD,
         (-- ǰ�Ǽ� ��������� ���� ������ ��� ����
          SELECT  AL.COMPANY_CD,
                  AL.APPROVAL_ID,
                  AL.AP_USER_ID,
                  AL.AP_USER_NM,
                  AL.AP_DEPT_CD,
                  AL.AP_DEPT_NM
             FROM EAC_APPROVAL_LINE AL,
                  (  SELECT q1.COMPANY_CD, q1.APPROVAL_ID, MIN (q1.SEQ) SEQ
                       FROM EAC_APPROVAL_LINE q1
                      WHERE q1.COMPANY_CD = 'PHARM'
                        AND Q1.APR_CD IS NULL
                   GROUP BY q1.COMPANY_CD, q1.APPROVAL_ID
                  ) AL2
            WHERE     1 = 1
                  AND AL.COMPANY_CD = AL2.COMPANY_CD
                  AND AL.APPROVAL_ID = AL2.APPROVAL_ID
                  AND AL.SEQ = AL2.SEQ
         ) ALN,
         (-- ǰ�Ǽ� ��������� ���������� ����
          SELECT  AL.COMPANY_CD,
                  AL.APPROVAL_ID,
                  AL.AP_USER_ID,
                  AL.AP_USER_NM,
                  AL.AP_DEPT_CD,
                  AL.AP_DEPT_NM
             FROM EAC_APPROVAL_LINE AL,
                  (  SELECT q1.COMPANY_CD, q1.APPROVAL_ID, MAX (q1.SEQ) SEQ
                       FROM EAC_APPROVAL_LINE q1
                      WHERE q1.COMPANY_CD = 'PHARM'
                   GROUP BY q1.COMPANY_CD, q1.APPROVAL_ID
                  ) AL2
            WHERE     1 = 1
                  AND AL.COMPANY_CD = AL2.COMPANY_CD
                  AND AL.APPROVAL_ID = AL2.APPROVAL_ID
                  AND AL.SEQ = AL2.SEQ
         ) ALM
   WHERE     1 = 1
         AND A.COMPANY_CD = 'PHARM'                                     -- ����: ȸ��CD
         AND A.APPROVAL_STATUS_CD = 'NING'
         AND A.APPROVAL_STATUS_CD NOT IN ('T','NCNL','NREJ','SR','SING','SAPR','SREJ') -- ����
         AND A.DEL_YN = 'N'                                             -- ����
         --   AND A.RQ_DATE >= TO_DATE('20150204'||'000000','YYYYMMDDHH24MISS') -- �˻����� : ��ų�/��
         --   AND A.RQ_DATE <= TO_DATE('20150204'||'235959','YYYYMMDDHH24MISS') -- �˻����� : ��ų�/��
         AND ALN.AP_USER_ID = 'V99999'              -- �˻����� : ���� ����/�ݷ���(��) ǰ�Ǽ� ��ȸ : ���ǻ����ID�� ���� ��
         AND A.COMPANY_CD  = AD.COMPANY_CD
         AND A.APPROVAL_ID = AD.APPROVAL_ID
         AND A.COMPANY_CD  = ALN.COMPANY_CD
         AND A.APPROVAL_ID = ALN.APPROVAL_ID        
         AND A.COMPANY_CD  = ALM.COMPANY_CD(+)
         AND A.APPROVAL_ID = ALM.APPROVAL_ID(+)
GROUP BY A.COMPANY_CD, A.APPROVAL_ID









-- ====================================
-- ������ - ���� ��� ��ȸ (������̰� 1�������ڰ� ���ΰ�)
-- ====================================

  SELECT A.COMPANY_CD               AS COMPANY_CD,
         A.APPROVAL_ID              AS APPROVAL_ID,
         MAX(A.APPROVAL_STATUS_CD)  AS APPROVAL_STATUS_CD,
         FN_GET_COM_CD('PHARM','APPROVAL_STATUS_CD',MAX(A.APPROVAL_STATUS_CD)) AS APPROVAL_STATUS_NM,
         MAX (A.RQ_DATE)            AS RQ_DATE,
         MAX(A.APPROVAL_DATE)       AS APPROVAL_DATE,
         MAX(A.RQ_USER_ID)          AS RQ_USER_ID, --�����ID
         MAX(A.RQ_USER_NM)          AS RQ_USER_NM, --����ڸ�
         DECODE(MAX(A.RRQ_USER_ID), MAX(A.RQ_USER_ID), NULL, MAX(A.RRQ_USER_ID))   AS RRQ_USER_ID, --��ü�����ID
         DECODE(MAX(A.RRQ_USER_ID), MAX(A.RQ_USER_ID), NULL, MAX(A.RRQ_USER_NM))   AS RRQ_USER_NM, --��ü����ڸ�
         MAX (ALM.AP_DEPT_NM)       AS LAST_AP_DEPT_NM,
         MAX (ALM.AP_USER_NM)       AS LAST_AP_USER_NM,
         MAX (A.ACCOUNT_CD)         AS ACCOUNT_CD,
         MAX (A.ACCOUNT_NM)         AS ACCOUNT_NM,
         NVL (COUNT (AD.SEQ), 0)    AS ITEM_CNT,
         NVL (SUM (REQUEST_AMOUNT), 0) AS REQUEST_AMOUNT
    FROM EAC_APPROVAL A,
         EAC_APPROVAL_ITEM AD,
         EAC_APPROVAL_LINE AL,
         (-- ǰ�Ǽ� ��������� ���������� ����
          SELECT  AL.COMPANY_CD,
                  AL.APPROVAL_ID,
                  AL.AP_USER_ID,
                  AL.AP_USER_NM,
                  AL.AP_DEPT_CD,
                  AL.AP_DEPT_NM
             FROM EAC_APPROVAL_LINE AL,
                  (  SELECT q1.COMPANY_CD, q1.APPROVAL_ID, MAX (q1.SEQ) SEQ
                       FROM EAC_APPROVAL_LINE q1
                      WHERE q1.COMPANY_CD = 'PHARM'
                   GROUP BY q1.COMPANY_CD, q1.APPROVAL_ID
                  ) AL2
            WHERE     1 = 1
                  AND AL.COMPANY_CD = AL2.COMPANY_CD
                  AND AL.APPROVAL_ID = AL2.APPROVAL_ID
                  AND AL.SEQ = AL2.SEQ
         ) ALM
   WHERE     1 = 1
         AND A.COMPANY_CD = 'PHARM'                                     -- ����: ȸ��CD
         AND A.APPROVAL_STATUS_CD = 'NREQ'
         AND A.APPROVAL_STATUS_CD NOT IN ('T','NCNL','NREJ','SR','SING','SAPR','SREJ') -- ����
         AND A.DEL_YN = 'N'                                             -- ����
         --   AND A.RQ_DATE >= TO_DATE('20150204'||'000000','YYYYMMDDHH24MISS') -- �˻����� : ��ų�/��
         --   AND A.RQ_DATE <= TO_DATE('20150204'||'235959','YYYYMMDDHH24MISS') -- �˻����� : ��ų�/��
         AND AL.SEQ = 1
         AND AL.AP_USER_ID = 'V99999'              -- �˻����� : ���� ����/�ݷ���(��) ǰ�Ǽ� ��ȸ : ���ǻ����ID�� ���� ��
         AND A.COMPANY_CD  = AD.COMPANY_CD
         AND A.APPROVAL_ID = AD.APPROVAL_ID
         AND A.COMPANY_CD  = AL.COMPANY_CD
         AND A.APPROVAL_ID = AL.APPROVAL_ID        
         AND A.COMPANY_CD  = ALM.COMPANY_CD(+)
         AND A.APPROVAL_ID = ALM.APPROVAL_ID(+)
GROUP BY A.COMPANY_CD, A.APPROVAL_ID

