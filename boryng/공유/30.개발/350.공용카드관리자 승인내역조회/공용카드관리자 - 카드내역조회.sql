-- ==================================
-- ����ī�� ���γ���  ��� ��ȸ(����ī�� �����ſ�û)
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
--   AND AQM.ACT_USER_ID IS NOT NULL -- �˻����� : �ǻ���� �����Ǹ� ��ȸ
--   AND AQM.ACT_USER_ID IS NULL     -- �˻����� : �ǻ���� �������Ǹ� ��ȸ
   AND C.AUTH_DATE LIKE '2014'||'12'||'%' -- �˻����� : ����/��



-- ==================================
-- ����ī�� ���γ���  �ǻ���� ����
-- ==================================
UPDATE EAC_CARD_AQ_MGMT
   SET ACT_USER_ID = '' -- �Ķ���� : �ǻ����ID
     , UPD_DTTM    = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')
     , UPD_USER_ID = '' -- �α����� ����� : ���ǻ����ID
 WHERE CARD_NUM    = '' -- ī���ȣ
   AND AUTH_DATE   = '' -- �ŷ�����(��������)
   AND AUTH_NUM    = '' -- ���ι�ȣ
   AND GEORAE_STAT = '' -- �ŷ�����
   AND REQUEST_AMOUNT = 0 -- ��ȭ�ŷ��ݾ��հ�
   AND GEORAE_COLL = '' -- �����߽ɹ�ȣ