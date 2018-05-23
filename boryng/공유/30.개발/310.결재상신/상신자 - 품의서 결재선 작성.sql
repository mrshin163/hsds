-- =============================
-- 1.���缱 ���� üũ (�����ڵ�, ����μ�, �ݾ�)
-- =============================

select PD.* 
  from SYS_APPROVAL_POLICY P, SYS_APPROVAL_POLICY_DTL PD
 where P.COMPANY_CD='PHARM'
   and P.USE_YN = 'Y'
   and P.DEL_YN = 'N'
   and P.ACCOUNT_CD = '1' -- �˻����� : �����ڵ�
   and (P.DEPT_CD IS NULL OR P.DEPT_CD='53210') -- �˻����� : ����μ��ڵ�
   and 1060000 BETWEEN P.MIN_AMOUNT AND P.MAX_AMOUNT -- �˻����� : �ݾ�
   and P.COMPANY_CD = PD.COMPANY_CD
   and P.POLICY_ID = PD.POLICY_ID

-- =============================
-- 2.������� ����
-- =============================
select P.POLICY_ID
     , PD.SEQ
     , PD.APPROVAL_LINE_DEF_CD
     , PD.APPROVAL_LINE_DEF_VAL   
     , CASE APPROVAL_LINE_DEF_CD 
       WHEN 'U' THEN
            FN_GET_USER_INFO(P.COMPANY_CD, PD.APPROVAL_LINE_DEF_VAL,'ALL') 
       WHEN 'D' THEN
            FN_GET_DEPT_MGR_INFO(P.COMPANY_CD, '53210','ALL') -- �˻����� : ����μ��ڵ�
       WHEN 'P' THEN
            FN_GET_UPDEPT_MGR_INFO(P.COMPANY_CD, '53210','ALL') -- �˻����� : ����μ��ڵ�
       WHEN 'E' THEN
            FN_GET_UNDEPT_MGR_INFO(P.COMPANY_CD, '53210','ALL') -- �˻����� : ����μ��ڵ�
       ELSE
            NULL
       END AS AP_USER_INFO 
  from SYS_APPROVAL_POLICY P, SYS_APPROVAL_POLICY_DTL PD
 where P.COMPANY_CD='PHARM' -- ���� : ȸ���ڵ�� ���ǿ��� �����ٰ� ���� ��
   and P.USE_YN = 'Y' -- ����
   and P.DEL_YN = 'N' -- ����
   and P.ACCOUNT_CD = '1' -- �˻����� : �����ڵ�
   and (P.DEPT_CD IS NULL OR P.DEPT_CD='53210') -- �˻����� : ����μ��ڵ�
   and 1060000 BETWEEN P.MIN_AMOUNT AND P.MAX_AMOUNT -- �˻����� : �ݾ�
   and P.COMPANY_CD = PD.COMPANY_CD
   and P.POLICY_ID = PD.POLICY_ID


   