-- ============================================================
-- 심포지움 결재요청 품의서ID 발행
-- ============================================================
   SELECT 'PHARM',
             'APP'
          || TO_CHAR (SYSDATE, 'YYYYMMDD')
          || LPAD (SEQ_APP.NEXTVAL, 5, '0')
     FROM DUAL;





-- ============================================================
-- 심포지움 결재요청 품의서 기초작성 (결재미상신내역에서 선택한 파라메터 세팅필요)
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
          'APP2015020400018', -- 파라메터 : 발행된 품의서ID
          NULL,
          NULL,
          'SR',
          NULL,
          '2', -- 고정 : 계정코드 (심포지움)
          FN_GET_COM_CD('PHARM','ACCOUNT_CD','2'), -- 고정
          NULL,
          NULL,
          REQ.USER_ID,
          REQ.USER_NM,
          REQ.DEPT_CD,
          REQ.DEPT_NM,
          REQ.TITLE_CD,
          REQ.TITLE_NM,
          REQ.DUTY_CD,
          REQ.DUTY_NM,
          REP.USER_ID,
          REP.USER_NM,
          REP.DEPT_CD,
          REP.DEPT_NM,
          REP.TITLE_CD,
          REP.TITLE_NM,
          REP.DUTY_CD,
          REP.DUTY_NM,
          'N',
          TO_CHAR (SYSDATE, 'YYYYMMDDHH24MISS'),
          'system',                                                -- 세션 사용자ID
          NULL,
          NULL
     FROM V_SYS_USER REQ, -- 상신자 정보
          (SELECT COMPANY_CD, -- 대체자 정보
                  USER_ID,
                  USER_NM,
                  DEPT_CD,
                  DEPT_NM,
                  TITLE_CD,
                  TITLE_NM,
                  DUTY_CD,
                  DUTY_NM
             FROM V_SYS_USER
            WHERE 1 = 1 AND COMPANY_CD = 'PHARM' AND USER_ID = '10108' -- 파라메터 : NREQ_USER_ID
                                                                      ) REP
    WHERE     1 = 1
          AND REQ.COMPANY_CD = REP.COMPANY_CD(+)
          AND REQ.USER_ID = REP.USER_ID(+)
          AND REQ.COMPANY_CD = 'PHARM'
          AND REQ.USER_ID = '10108'                         -- 파라메터 : OWNER_ID (상신주체자)
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
-- ============================================================
-- 심포지움 결재요청 품의내역서 기초작성 (결재미상신내역에서 선택한 매입건의 6개 키로 관련자료 조회
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
   'PHARM'                  /* 회사코드 */
 , 'APP2015020400018'     /* 품의서ID */  -- 파라메터 : 발행된 품의서ID
 , (SELECT COUNT(*)+1 FROM EAC_APPROVAL_ITEM WHERE COMPANY_CD='PHARM' AND APPROVAL_ID='APP2015020400018')                  /* 품의내역 일련번호 */
 , NULL                      /* S_APPROVAL_ID */
 , NULL                      /* S_APPROVAL_SEQ */
 , NULL                      /* CUSTOMER_CD */
 , NULL                      /* CUSTOMER_NM */
 , NULL                      /* SAUP_NO */
 , '10108'                  /* USER_ID */ -- 파라메터 : OWNER_ID
 , (SELECT USER_NM FROM V_SYS_USER WHERE USER_ID='10108') /* USER_NM */ -- 파라메터 : OWNER_ID
 , NULL                      /* ACCOUNT2_CD */
 , NULL                      /* ACCOUNT2_NM */
 , NULL                      /* PRODUCT_CD */
 , NULL                      /* PRODUCT_NM */
 , NULL                      /* FTR_CD */
 , NULL                      /* DETAILS */
 , NULL                      /* OPINION */
 , CU.CARD_OWNER_ID          /* CARD_OWNER_ID       */
 , CU.CARD_OWNER_NM          /* CARD_OWNER_NM       */
 , CU.CARD_OWNER_DEPT_CD  /* CARD_OWNER_DEPT_CD  */
 , CU.CARD_OWNER_DEPT_NM  /* CARD_OWNER_DEPT_NM  */
 , CU.CARD_OWNER_TITLE_CD /* CARD_OWNER_TITLE_CD */
 , CU.CARD_OWNER_TITLE_NM /* CARD_OWNER_TITLE_NM */
 , CU.CARD_OWNER_DUTY_CD  /* CARD_OWNER_DUTY_CD  */
 , CU.CARD_OWNER_DUTY_NM  /* CARD_OWNER_DUTY_NM  */
 , NULL                      /* AP_GEORAE_CAND */
 , C.CARD_NUM              /* CARD_NUM        */
 , C.AUTH_DATE              /* AUTH_DATE       */
 , C.AUTH_NUM              /* AUTH_NUM        */
 , C.GEORAE_STAT          /* GEORAE_STAT     */
 , C.REQUEST_AMOUNT          /* REQUEST_AMOUNT  */
 , C.GEORAE_COLL          /* GEORAE_COLL     */
 , C.MERC_NAME              /* MERC_NAME       */
 , C.SETT_DATE              /* SETT_DATE       */
 , C.OWNER_REG_NO          /* OWNER_REG_NO    */
 , C.CARD_CODE              /* CARD_CODE       */
 , C.CARD_NAME              /* CARD_NAME       */
 , C.USER_NAME              /* USER_NAME       */
 , C.AUTH_TIME              /* AUTH_TIME       */
 , C.AQUI_DATE              /* AQUI_DATE       */
 , C.GEORAE_CAND          /* GEORAE_CAND     */
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
 , CI.CARD_KIND              /* CARD_KIND       */
 , CI.SETT_DATE              /* SETT_DAY        */
 , CI.SETT_BANKCODE          /* SETT_BANKCODE   */
 , CI.SETT_ACCO              /* SETT_ACCO       */
 , CI.JUMIN_NO               /* JUMIN_NO        */ 
 , NULL                      /* FINANCE_DATE    */
 , NULL                      /* FINANCE_SEQ     */
 , NULL                      /* JUNPYO_INS_DATE */
 , '0'                      /* JUNPYO_ST_CD    */
 , TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')            /* REG_DTTM */
 , 'system'                  /* REG_USER_ID */ -- 고정 파라메터 : 로그인한 사용자ID
 , NULL                      /* UPD_DTTM */
 , NULL                      /* UPD_USER_ID */ 
FROM CARD_AQ_TMP C, 
     (
         SELECT  D.USER_ID      AS CARD_OWNER_ID     
               , D.CARD_NO      AS CARD_NO 
               , U.USER_NM      AS CARD_OWNER_NM      
               , U.DEPT_CD      AS CARD_OWNER_DEPT_CD 
               , U.DEPT_NM      AS CARD_OWNER_DEPT_NM 
               , U.TITLE_CD      AS CARD_OWNER_TITLE_CD
               , U.TITLE_NM      AS CARD_OWNER_TITLE_NM
               , U.DUTY_CD      AS CARD_OWNER_DUTY_CD 
               , U.DUTY_NM      AS CARD_OWNER_DUTY_NM 
          FROM EAC_CARD D, V_SYS_USER U
         WHERE     1 = 1
               AND D.COMPANY_CD = 'PHARM'
               AND D.USER_ID = U.USER_ID(+)
               AND D.COMPANY_CD = U.COMPANY_CD 
     ) CU,
     CARD_INFO CI
WHERE 1=1
  AND C.CARD_NUM  = CU.CARD_NO(+)
  AND C.CARD_NUM = CI.CARD_NUM(+)
  AND C.CARD_NUM = '9410644096133319' -- 파라메터 : 카드번호
  AND C.AUTH_DATE = '20141215' -- 파라메터 : 승인일자
  AND C.AUTH_NUM  = '14642078  ' -- 파라메터 : 승인번호
  AND C.GEORAE_STAT = '1' -- 파라메터 : 거래상태
  AND C.REQUEST_AMOUNT = '68000' -- 파라메터 : 금액합계
  AND C.GEORAE_COLL = '201412160069FZ3281680' -- 파라메터 : 매입추심번호
   




-- ============================================================
-- 
-- ============================================================
    