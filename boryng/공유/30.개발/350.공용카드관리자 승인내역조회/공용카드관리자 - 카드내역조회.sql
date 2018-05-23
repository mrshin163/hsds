-- ==================================
-- 공용카드 승인내역  목록 조회(공용카드 결재상신요청)
-- ==================================

SELECT C.AUTH_DATE
     , C.MERC_NAME
     , C.AMT_AMOUNT
     , C.VAT_AMOUNT
     , C.REQUEST_AMOUNT
     , C.GEORAE_STAT
     , C.GEORAE_COLL
     , AQM.ACT_USER_ID
     , C.CARD_NUM
     , C.AUTH_NUM
     , FN_GET_USER_INFO('PHARM', AQM.ACT_USER_ID,'USER_NM') ACT_USER_NM
  FROM CARD_AQ_TMP C, EAC_CARD_AQ_MGMT AQM
 WHERE 1=1
   AND C.CARD_NUM       = AQM.CARD_NUM       (+)
   AND C.AUTH_DATE      = AQM.AUTH_DATE      (+)
   AND C.AUTH_NUM       = AQM.AUTH_NUM       (+)
   AND C.GEORAE_STAT    = AQM.GEORAE_STAT    (+)
   AND C.REQUEST_AMOUNT = AQM.REQUEST_AMOUNT (+)
   AND C.GEORAE_COLL    = AQM.GEORAE_COLL    (+)
--   AND AQM.ACT_USER_ID IS NOT NULL -- 검색조건 : 실사용자 지정건만 조회
--   AND AQM.ACT_USER_ID IS NULL     -- 검색조건 : 실사용자 미지정건만 조회
   AND C.AUTH_DATE LIKE '2014'||'12'||'%' -- 검색조건 : 사용년/월



-- ==================================
-- 공용카드 승인내역  실사용자 지정
-- ==================================
UPDATE EAC_CARD_AQ_MGMT
   SET ACT_USER_ID = '' -- 파라메터 : 실사용자ID
     , UPD_DTTM    = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
     , UPD_USER_ID = '' -- 로그인한 사용자 : 세션사용자ID
 WHERE CARD_NUM    = '' -- 카드번호
   AND AUTH_DATE   = '' -- 거래일자(승인일자)
   AND AUTH_NUM    = '' -- 승인번호
   AND GEORAE_STAT = '' -- 거래상태
   AND REQUEST_AMOUNT = 0 -- 원화거래금액합계
   AND GEORAE_COLL = '' -- 매입추심번호