-- ==================================
-- 공정거래규약 조회
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
   AND MUNSEO_DATE like '2015'||'01'||'%' -- 검색조건 : 상신년/월 
   AND MUNSEO_GUBUN_NM like '%자사%'      -- 검색조건 : 문서구분명
   AND JANGSO like '%%'                   -- 검색조건 : 장소명
   AND PUMMOK like '%%'                   -- 검색조건 : 품목명
   AND GEORAE_NAME like '%%'              -- 검색조건 : 거래처




SELECT 
		CC.FTR_CD 
FROM EAC_APPROVAL BB , EAC_APPROVAL_ITEM CC
WHERE 1=1
		AND BB.COMPANY_CD=CC.COMPANY_CD
		AND BB.APPROVAL_ID=CC.APPROVAL_ID
		AND BB.ACCOUNT_CD='1'
		AND BB.APPROVAL_STATUS_CD NOT IN ('T','NCNL','NREJ')
		AND BB.RQ_USER_ID='09158'
   