-- ==================================
-- ���ΰ����ڵ� ��ȸ
-- ==================================

SELECT COM_CD    AS ACCOUNT2_CD -- ���ΰ����ڵ�
     , COM_CD_NM AS ACCOUNT2_NM -- ���ΰ�����
  FROM SYS_COM_CD
 WHERE 1=1
   AND COMPANY_CD    = 'PHARM'       -- ���� : ���� ȸ���ڵ�
   AND USE_YN        = 'Y'           -- ����
   AND DEL_YN        = 'N'           -- ����
   AND COM_CD_GRP_ID = 'ACCOUNT2_CD' -- ����
   AND COM_CD_NM like '%����%'           -- �˻����� : ���ΰ����� (�ʼ�)
 ORDER BY COM_CD_NM ASC   