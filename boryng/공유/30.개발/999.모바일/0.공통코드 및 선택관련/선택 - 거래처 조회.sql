-- ==================================
-- �ŷ�ó  ��ȸ
-- ==================================
SELECT CUSTOMER_CD -- �ŷ�ó�ڵ�
     , CUSTOMER_NM -- �ŷ�ó��
     , CEO_NM      -- ��ǥ�ڸ�
     , BIZ_NO      -- ����ڹ�ȣ
     , ADDRESS     -- �ּ�
     , CUSTOMER_TP_NM -- �ŷ�ó���и�
     , CUSTOMER_ST_NM -- �ŷ�ó���¸�
  FROM EAC_CUSTOMER
 WHERE 1=1
   AND COMPANY_CD = 'PHARM'
   AND USE_YN        = 'Y'           -- ����
   AND DEL_YN        = 'N'           -- ����
   AND CUSTOMER_NM LIKE '%%' -- �˻����� : �ŷ�ó�� 
   AND CUSTOMER_TP_CD = ''   -- �˻����� : �ŷ�ó�����ڵ�
   AND CUSTOMER_ST_CD = ''   -- �˻����� : �ŷ�ó�����ڵ�