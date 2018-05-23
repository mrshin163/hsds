-- ==========================================
-- ������������ڰ� ǰ���ۼ��� �����û�� ��ȸ
-- ==========================================

SELECT A.COMPANY_CD
     , A.APPROVAL_ID
     , AD.SEQ
     , AD.AUTH_DATE
     , AD.MCC_NAME
     , AD.AMT_AMOUNT
     , AD.VAT_AMOUNT
     , AD.REQUEST_AMOUNT
     , AD.USER_ID
     , AD.USER_NM
     , AD.USER_NAME
     , AD.ACCOUNT2_CD
     , AD.ACCOUNT2_NM
     , AD.PRODUCT_CD
     , AD.PRODUCT_NM
     , AD.CUSTOMER_CD
     , AD.CUSTOMER_NM
     , AD.FTR_CD
     , AD.DETAILS
     , AD.OPINION
  FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM AD
 WHERE 1=1
   AND A.COMPANY_CD = AD.COMPANY_CD
   AND A.APPROVAL_ID = AD.APPROVAL_ID
   AND A.APPROVAL_STATUS_CD = 'SR'
   AND AD.AUTH_DATE LIKE '2014'||'12'||'%' -- �˻����� : ���γ�/��
   
   







-- ============================================================
-- ǰ�Ǽ�ID ����
-- ============================================================
   SELECT 'PHARM',
             'APP'
          || TO_CHAR (SYSDATE, 'YYYYMMDD')
          || LPAD (SEQ_APP.NEXTVAL, 5, '0')
     FROM DUAL;





-- ============================================================
-- ǰ�Ǽ� �����ۼ� (������������� �������)
-- ============================================================
INSERT INTO EACCOUNTING.EAC_APPROVAL (COMPANY_CD,
                                      APPROVAL_ID,
                                      RQ_DATE,
                                      RQ_OPINION,
                                      APPROVAL_STATUS_CD,
                                      APPROVAL_DATE,
                                      ACCOUNT_CD,
                                      ACCOUNT_NM,
                                      BUDGET_DEPT_CD,
                                      BUDGET_DEPT_NM,
                                      RQ_USER_ID,
                                      RQ_USER_NM,
                                      RQ_DEPT_CD,
                                      RQ_DEPT_NM,
                                      RQ_TITLE_CD,
                                      RQ_TITLE_NM,
                                      RQ_DUTY_CD,
                                      RQ_DUTY_NM,
                                      RRQ_USER_ID,
                                      RRQ_USER_NM,
                                      RRQ_DEPT_CD,
                                      RRQ_DEPT_NM,
                                      RRQ_TITLE_CD,
                                      RRQ_TITLE_NM,
                                      RRQ_DUTY_CD,
                                      RRQ_DUTY_NM,
                                      DEL_YN,
                                      REG_DTTM,
                                      REG_USER_ID,
                                      UPD_DTTM,
                                      UPD_USER_ID)
   SELECT 'PHARM',
          'APP2015020400019', -- �Ķ���� : ����� ǰ�Ǽ�ID
          NULL,     /* ����Ͻ� */
          NULL,     /* ����ǰ� */
          'T',      /* ������������ڵ� */
          NULL,     /* ���������� */
          '2',      /* �����ڵ� */ -- ���� : ��������(2)
          FN_GET_COM_CD('PHARM','ACCOUNT_CD','2'),     /* ������ */   -- ����
          NULL,     /* ����μ��ڵ� */
          NULL,     /* ����μ��� */
          REQ.USER_ID,
          REQ.USER_NM,
          REQ.DEPT_CD,
          REQ.DEPT_NM,
          REQ.TITLE_CD,
          REQ.TITLE_NM,
          REQ.DUTY_CD,
          REQ.DUTY_NM,
          NULL, --  REP.USER_ID,
          NULL, --  REP.USER_NM,
          NULL, --  REP.DEPT_CD,
          NULL, --  REP.DEPT_NM,
          NULL, --  REP.TITLE_CD,
          NULL, --  REP.TITLE_NM,
          NULL, --  REP.DUTY_CD,
          NULL, --  REP.DUTY_NM,
          'N',
          TO_CHAR (SYSDATE, 'YYYYMMDDHH24MISS'),
          'V99999',   -- �α����� �����ID
          NULL,
          NULL
     FROM V_SYS_USER REQ -- ����� ����
    WHERE     1 = 1
              AND REQ.COMPANY_CD = 'PHARM'
          AND REQ.USER_ID = 'V99999'  -- �Ķ���� : ������������ڷμ� �α����� �����ID(����ȹ����)
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
-- ============================================================
-- ǰ�ǳ����� �����ۼ� (������������û �����ȸ����  ������ ���� �����Ͽ� ����
-- :::: ��Ͽ��� ������ ������ŭ �Ķ���� �����Ͽ� �ݺ�����
-- ============================================================
INSERT INTO EACCOUNTING.EAC_APPROVAL_ITEM (
   COMPANY_CD, APPROVAL_ID, SEQ, 
   S_APPROVAL_ID, S_APPROVAL_SEQ, CUSTOMER_CD, 
   CUSTOMER_NM, SAUP_NO, USER_ID, 
   USER_NM, ACCOUNT2_CD, ACCOUNT2_NM, 
   PRODUCT_CD, PRODUCT_NM, FTR_CD, 
   DETAILS, OPINION, CARD_OWNER_ID, 
   CARD_OWNER_NM, CARD_OWNER_DEPT_CD, CARD_OWNER_DEPT_NM, 
   CARD_OWNER_TITLE_CD, CARD_OWNER_TITLE_NM, CARD_OWNER_DUTY_CD, 
   CARD_OWNER_DUTY_NM, AP_GEORAE_CAND, CARD_NUM, 
   AUTH_DATE, AUTH_NUM, GEORAE_STAT, 
   REQUEST_AMOUNT, GEORAE_COLL, MERC_NAME, 
   SETT_DATE, OWNER_REG_NO, CARD_CODE, 
   CARD_NAME, USER_NAME, AUTH_TIME, 
   AQUI_DATE, GEORAE_CAND, AMT_AMOUNT, 
   VAT_AMOUNT, SER_AMOUNT, FRE_AMOUNT, 
   AMT_MD_AMOUNT, VAT_MD_AMOUNT, GEORAE_GUKGA, 
   FOR_AMOUNT, USD_AMOUNT, AQUI_RATE, 
   MERC_SAUP_NO, MERC_ADDR, MERC_REPR, 
   MERC_TEL, MERC_ZIP, MCC_NAME, 
   MCC_CODE, MCC_STAT, VAT_STAT, 
   CAN_DATE, ASK_SITE, SITE_DATE, 
   ASK_DATE, ASK_TIME, GONGJE_NO_CHK, 
   CREATEDATE, CREATETIME, SEND_YN, 
   AQUI_TIME, CONVERSION_FEE, ORG_COLL, 
   CARD_KIND, SETT_DAY, SETT_BANKCODE, 
   SETT_ACCO, JUMIN_NO, FINANCE_DATE, 
   FINANCE_SEQ, JUNPYO_INS_DATE, JUNPYO_ST_CD, 
   REG_DTTM, REG_USER_ID, UPD_DTTM, 
   UPD_USER_ID)    
SELECT
   'PHARM'                  /* ȸ���ڵ� */
 , 'APP2015020400019'       /* ǰ�Ǽ�ID */  -- �Ķ���� : ����� ǰ�Ǽ�ID
 , (SELECT COUNT(*)+1 FROM EAC_APPROVAL_ITEM WHERE COMPANY_CD='PHARM' AND APPROVAL_ID='APP2015020400019')                  /* ǰ�ǳ��� �Ϸù�ȣ */
 , 'APP2015020400018'       /* S_APPROVAL_ID */ -- �Ķ���� : �����ûǰ�ǹ�ȣ
 , 2                        /* S_APPROVAL_SEQ */ -- �Ķ���� : �����ûǰ�� �Ϸù�ȣ 
 , C.CUSTOMER_CD            /* CUSTOMER_CD */
 , C.CUSTOMER_NM            /* CUSTOMER_NM */
 , C.SAUP_NO                /* SAUP_NO */
 , C.USER_ID                /* USER_ID */ 
 , C.USER_NM                /* USER_NM */ 
 , C.ACCOUNT2_CD            /* ACCOUNT2_CD */
 , C.ACCOUNT2_NM            /* ACCOUNT2_NM */
 , C.PRODUCT_CD             /* PRODUCT_CD */
 , C.PRODUCT_NM             /* PRODUCT_NM */
 , C.FTR_CD                 /* FTR_CD */
 , C.DETAILS                /* DETAILS */
 , C.OPINION                /* OPINION */
 , C.CARD_OWNER_ID          /* CARD_OWNER_ID       */
 , C.CARD_OWNER_NM          /* CARD_OWNER_NM       */
 , C.CARD_OWNER_DEPT_CD     /* CARD_OWNER_DEPT_CD  */
 , C.CARD_OWNER_DEPT_NM     /* CARD_OWNER_DEPT_NM  */
 , C.CARD_OWNER_TITLE_CD    /* CARD_OWNER_TITLE_CD */
 , C.CARD_OWNER_TITLE_NM    /* CARD_OWNER_TITLE_NM */
 , C.CARD_OWNER_DUTY_CD     /* CARD_OWNER_DUTY_CD  */
 , C.CARD_OWNER_DUTY_NM     /* CARD_OWNER_DUTY_NM  */
 , C.AP_GEORAE_CAND         /* AP_GEORAE_CAND */
 , C.CARD_NUM               /* CARD_NUM        */
 , C.AUTH_DATE              /* AUTH_DATE       */
 , C.AUTH_NUM               /* AUTH_NUM        */
 , C.GEORAE_STAT            /* GEORAE_STAT     */
 , C.REQUEST_AMOUNT         /* REQUEST_AMOUNT  */
 , C.GEORAE_COLL            /* GEORAE_COLL     */
 , C.MERC_NAME              /* MERC_NAME       */
 , C.SETT_DATE              /* SETT_DATE       */
 , C.OWNER_REG_NO           /* OWNER_REG_NO    */
 , C.CARD_CODE              /* CARD_CODE       */
 , C.CARD_NAME              /* CARD_NAME       */
 , C.USER_NAME              /* USER_NAME       */
 , C.AUTH_TIME              /* AUTH_TIME       */
 , C.AQUI_DATE              /* AQUI_DATE       */
 , C.GEORAE_CAND            /* GEORAE_CAND     */
 , C.AMT_AMOUNT              /* AMT_AMOUNT      */
 , C.VAT_AMOUNT              /* VAT_AMOUNT      */
 , C.SER_AMOUNT              /* SER_AMOUNT      */
 , C.FRE_AMOUNT              /* FRE_AMOUNT      */
 , C.AMT_MD_AMOUNT           /* AMT_MD_AMOUNT   */
 , C.VAT_MD_AMOUNT           /* VAT_MD_AMOUNT   */
 , C.GEORAE_GUKGA            /* GEORAE_GUKGA    */
 , C.FOR_AMOUNT              /* FOR_AMOUNT      */
 , C.USD_AMOUNT              /* USD_AMOUNT      */
 , C.AQUI_RATE               /* AQUI_RATE       */
 , C.MERC_SAUP_NO            /* MERC_SAUP_NO    */
 , C.MERC_ADDR               /* MERC_ADDR       */
 , C.MERC_REPR               /* MERC_REPR       */
 , C.MERC_TEL                /* MERC_TEL        */
 , C.MERC_ZIP                /* MERC_ZIP        */
 , C.MCC_NAME                /* MCC_NAME        */
 , C.MCC_CODE                /* MCC_CODE        */
 , C.MCC_STAT                /* MCC_STAT        */
 , C.VAT_STAT                /* VAT_STAT        */
 , C.CAN_DATE                /* CAN_DATE        */
 , C.ASK_SITE                /* ASK_SITE        */
 , C.SITE_DATE               /* SITE_DATE       */
 , C.ASK_DATE                /* ASK_DATE        */
 , C.ASK_TIME                /* ASK_TIME        */
 , C.GONGJE_NO_CHK           /* GONGJE_NO_CHK   */
 , C.CREATEDATE              /* CREATEDATE      */
 , C.CREATETIME              /* CREATETIME      */
 , C.SEND_YN                 /* SEND_YN         */
 , C.AQUI_TIME               /* AQUI_TIME       */
 , C.CONVERSION_FEE          /* CONVERSION_FEE  */
 , C.ORG_COLL                /* ORG_COLL        */     
 , C.CARD_KIND              /* CARD_KIND       */
 , C.SETT_DAY               /* SETT_DAY        */
 , C.SETT_BANKCODE          /* SETT_BANKCODE   */
 , C.SETT_ACCO              /* SETT_ACCO       */
 , C.JUMIN_NO               /* JUMIN_NO        */ 
 , NULL                      /* FINANCE_DATE    */
 , NULL                      /* FINANCE_SEQ     */
 , NULL                      /* JUNPYO_INS_DATE */
 , '0'                      /* JUNPYO_ST_CD    */
 , TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')            /* REG_DTTM */
 , 'V99999'                  /* REG_USER_ID */ -- ���� �Ķ���� : �α����� �����ID
 , NULL                      /* UPD_DTTM */
 , NULL                      /* UPD_USER_ID */ 
 FROM EAC_APPROVAL_ITEM C
WHERE 1=1
  AND (C.COMPANY_CD, C.APPROVAL_ID, C.SEQ) IN (('PHARM','APP2015020400018',2))  -- �Ķ���� : ȸ���ڵ�, �����ûǰ�ǹ�ȣ, �����ûǰ���Ϸù�ȣ
 

-- ============================================================
-- 
-- ============================================================
       