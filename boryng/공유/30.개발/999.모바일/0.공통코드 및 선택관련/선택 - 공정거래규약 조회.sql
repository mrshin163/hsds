-- ==================================
-- �����ŷ��Ծ� ��ȸ
-- ==================================

SELECT MUNSEO_NO
     , MUNSEO_DATE
     , GEORAE_NAME
     , PUMMOK
     , JANGSO
     , AMOUNT
     , SABUN
     , FN_GET_USER_INFO('PHARM', SABUN,'USER_NM') DAMDANG_NM
  FROM V_EACCOUNTING_MUNSEO
 WHERE 1=1
   AND MUNSEO_DATE like '2015'||'01'||'%' -- �˻����� : ��ų�/�� 
   AND MUNSEO_GUBUN_NM like '%�ڻ�%'      -- �˻����� : �������и�
   AND JANGSO like '%%'                   -- �˻����� : ��Ҹ�
   AND PUMMOK like '%%'                   -- �˻����� : ǰ���
   AND GEORAE_NAME like '%%'              -- �˻����� : �ŷ�ó




SELECT 
		CC.FTR_CD 
FROM EAC_APPROVAL BB , EAC_APPROVAL_ITEM CC
WHERE 1=1
		AND BB.COMPANY_CD=CC.COMPANY_CD
		AND BB.APPROVAL_ID=CC.APPROVAL_ID
		AND BB.ACCOUNT_CD='1'
		AND BB.APPROVAL_STATUS_CD NOT IN ('T','NCNL','NREJ')
		AND BB.RQ_USER_ID='09158'
   