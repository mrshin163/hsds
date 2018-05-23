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
		                            FN_GET_DEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- �˻����� : ����μ��ڵ�
		                       WHEN 'P' THEN
		                            FN_GET_UPDEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- �˻����� : ����μ��ڵ�
		                       WHEN 'PP' THEN
		                            FN_GET_UPUPDEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- �˻����� : ����μ��ڵ�
		                       WHEN 'UD' THEN
		                            FN_GET_UNDEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- �˻����� : ����μ��ڵ�
		                       WHEN 'UP' THEN
		                            FN_GET_UNUPDEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- �˻����� : ����μ��ڵ�
		                       WHEN 'UPP' THEN
		                            FN_GET_UNUPUPDEPT_MGR_INFO(P.COMPANY_CD, #{dept_cd},'ALL') -- �˻����� : ����μ��ڵ�
		                       ELSE
		                            NULL
		                       END AS AP_USER_INFO 
						  FROM (
					                        SELECT *
					                          FROM (
					                                   select q.*                                            
					                                     from SYS_APPROVAL_POLICY q 
	                                                     where q.COMPANY_CD=#{c_cd} -- ���� : ȸ���ڵ�� ���ǿ��� �����ٰ� ���� ��
	                                                       and q.USE_YN = 'Y' -- ����
	                                                       and q.DEL_YN = 'N' -- ����
	                                                       and q.ACCOUNT_CD = #{account_cd} -- �˻����� : �����ڵ�
	                                                       and (q.DEPT_CD IS NULL OR q.DEPT_CD=#{dept_cd}) -- �˻����� : ����μ��ڵ�
	                                                       and #{amount} BETWEEN q.MIN_AMOUNT AND q.MAX_AMOUNT -- �˻����� : �ݾ�
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


--V52140$�ع��濵��$52140$�ع��濵��$6010$$23$$190$$$$$$N$Y
--01155$�����$55210$����CLINIC 1��$4010$����$14$����$140$����$02-708-8015$02-3395-7781$010-4599-3398$ktkim@boryung.co.kr$N$N
--13002$������$30000   $������ȹ����$1050     $����    $04       $������$050$����$02-708-8025$$010-4722-7869$jaehyun.an@boryung.co.kr$N$N


--10153$�ڼ���$12000   $�λ���      $4010      $����    $14      $����  $140      $����   $02-708-8120 $02-437-9503$010-2927-2923$psypsy95@boryung.co.kr$N$N
--��� /�̸�  /�μ��ڵ�/�μ���      /�����ڵ� /���޸�  /��å�ڵ�/��å�� /ȣĪ�ڵ�/ȣĪ��  /�繫�ǹ�ȣ   / ����ȭ / �ڵ��� / �̸��� / �������� / ���󿩺�?
-- 0   / 1    / 2      / 3          / 4       / 5      / 6      / 7     / 8      / 9      / 10