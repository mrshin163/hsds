-- ======================================
-- 최종결재시
/*
    내가 최종결재자 이면 최종결재가 가능하다. 단 품의상태가 'NING'이어야 한다.
    
    1. 품의결재라인에 최종결재 정보 업데이트
    2. 품의서에 품의상태 'NAPR' 및 최종결재 정보 업데이트
    3. ERP 인터페이스 전송
    4. 만약 심포지움 건이면 관련 원천 심포지움 품의서 찾아가 품의상태를 SAPR로 업데이트 한다.

*/
-- ======================================



-- ======================================
-- 1. 품의결재라인에 최종결재 정보 업데이트
-- ======================================

-- ======================================
-- 2. 품의서에 품의상태 'NAPR' 및 최종결재 정보 업데이트
-- ======================================

-- ======================================
-- 3. 3. ERP 인터페이스 전송
-- ======================================
INSERT INTO A5992_IFI_AP (
   COMPANY_CD, APPROVAL_ID, APPROVAL_SEQ, 
   APPROVAL_DATE, APPROVAL_TIME, ACCOUNT_CD, 
   ACCOUNT_NM, BUDGET_DEPT_CD, BUDGET_DEPT_NM, 
   CUSTOMER_CD, CUSTOMER_NM, SAUP_NO, 
   USER_ID, USER_NM, ACCOUNT2_CD, 
   ACCOUNT2_NM, ACCOUNT_EXP_CD, ACCOUNT_EXP_NM, 
   PRODUCT_CD, PRODUCT_NM, FTR_CD, 
   DETAILS, OPINION, CARD_OWNER_ID, 
   CARD_OWNER_NM, CARD_OWNER_DEPT_CD, CARD_OWNER_DEPT_NM, 
   CARD_OWNER_TITLE_CD, CARD_OWNER_TITLE_NM, CARD_OWNER_DUTY_CD, 
   CARD_OWNER_DUTY_NM, CARD_NO, GEORAE_DATE, 
   ADMISSION_NO, REQUEST_AMOUNT, AQUI_COLL, 
   AQUI_STAT, MERC_NAME, SETT_DATE, 
   OWNER_REG_NO, CARD_CODE, GEORAE_NAME, 
   USER_NAME, AUTH_TIME, AQUI_DATE, 
   AQUI_CAND, WON_AMOUNT, VAT_AMOUNT, 
   SER_AMOUNT, FRE_AMOUNT, AMT_MD_AMOUNT, 
   VAT_MD_AMOUNT, GEORAE_GUKGA, USD_AMOUNT, 
   AQUI_DOLL, AQUI_RATE, MERC_SAUP_NO, 
   MERC_ADDR, MERC_REPR, MERC_TEL, 
   MERC_ZIP, MERC_UPJONG, MCC_CODE, 
   UPJONG_STAT, VAT_STAT, CAN_DATE, 
   ASK_SITE, SITE_DATE, ASK_DATE, 
   ASK_TIME, GONGJE_NO_CHK, AQUI_TIME, 
   CONVERSION_FEE, ORG_COLL, UNIQUE_NO, 
   CARD_KIND, SETT_DAY, SETT_BANKCODE, 
   SETT_ACCO, JUMIN_NO, IF_DATE, 
   JUNPYO_STATUS, FINANCE_DATE, FINANCE_SEQ, 
   FINANCE_REC1, FINANCE_REC2, FINANCE_REC3, 
   JUNPYO_INS_USER, JUNPYO_INS_DATE, ERR_CHK, 
   ERR_MSG)    
 SELECT
         A.COMPANY_CD     /* COMPANY_CD */
       , A.APPROVAL_ID    /* APPROVAL_ID */
       , AD.SEQ           /* APPROVAL_SEQ */
       , TO_CHAR(A.APPROVAL_DATE,'YYYYMMDD') APPROVAL_DATE /* APPROVAL_DATE */
       , TO_CHAR(A.APPROVAL_DATE,'HH24MISS') APPROVAL_TIME /* APPROVAL_TIME */
       , A.ACCOUNT_CD     /* ACCOUNT_CD */
       , A.ACCOUNT_NM     /* ACCOUNT_NM */
       , A.BUDGET_DEPT_CD /* BUDGET_DEPT_CD */
       , A.BUDGET_DEPT_NM /* BUDGET_DEPT_NM */
       , AD.CUSTOMER_CD   /* CUSTOMER_CD */
       , AD.CUSTOMER_NM   /* CUSTOMER_NM */
       , AD.SAUP_NO       /* SAUP_NO */
       , AD.USER_ID       /* USER_ID */
       , AD.USER_NM       /* USER_NM */
       , AD.ACCOUNT2_CD   /* ACCOUNT2_CD */
       , AD.ACCOUNT2_NM   /* ACCOUNT2_NM */
       , AD.ACCOUNT_SP_CD /* ACCOUNT_EXP_CD */
       , AD.ACCOUNT_SP_NM /* ACCOUNT_EXP_NM */
       , AD.PRODUCT_CD    /* PRODUCT_CD */
       , AD.PRODUCT_NM    /* PRODUCT_NM */
       , AD.FTR_CD        /* FTR_CD */
       , AD.DETAILS       /* DETAILS */
       , AD.OPINION       /* OPINION */
       , AD.CARD_OWNER_ID /* CARD_OWNER_ID */
       , AD.CARD_OWNER_NM /* CARD_OWNER_NM */
       , AD.CARD_OWNER_DEPT_CD    /* CARD_OWNER_DEPT_CD  */
       , AD.CARD_OWNER_DEPT_NM    /* CARD_OWNER_DEPT_NM  */
       , AD.CARD_OWNER_TITLE_CD   /* CARD_OWNER_TITLE_CD */
       , AD.CARD_OWNER_TITLE_NM   /* CARD_OWNER_TITLE_NM */
       , AD.CARD_OWNER_DUTY_CD    /* CARD_OWNER_DUTY_CD  */
       , AD.CARD_OWNER_DUTY_NM    /* CARD_OWNER_DUTY_NM  */
       , AD.CARD_NUM               /* CARD_NO             */
       , AD.AUTH_DATE             /* GEORAE_DATE         */
       , AD.AUTH_NUM              /* ADMISSION_NO        */
       , AD.REQUEST_AMOUNT        /* REQUEST_AMOUNT      */
       , AD.GEORAE_COLL           /* AQUI_COLL           */
       , AD.GEORAE_STAT           /* AQUI_STAT           */
       , AD.MERC_NAME             /* MERC_NAME           */
       , AD.SETT_DATE             /* SETT_DATE           */
       , AD.OWNER_REG_NO          /* OWNER_REG_NO        */
       , AD.CARD_CODE             /* CARD_CODE           */
       , AD.CARD_NAME             /* GEORAE_NAME         */
       , AD.USER_NAME             /* USER_NAME           */
       , AD.AUTH_TIME             /* AUTH_TIME           */
       , AD.AQUI_DATE             /* AQUI_DATE           */
       , AD.GEORAE_CAND           /* AQUI_CAND           */
       , AD.AMT_AMOUNT            /* WON_AMOUNT          */
       , AD.VAT_AMOUNT            /* VAT_AMOUNT          */
       , AD.SER_AMOUNT            /* SER_AMOUNT          */
       , AD.FRE_AMOUNT            /* FRE_AMOUNT          */
       , AD.AMT_MD_AMOUNT         /* AMT_MD_AMOUNT       */
       , AD.VAT_MD_AMOUNT         /* VAT_MD_AMOUNT       */
       , AD.GEORAE_GUKGA          /* GEORAE_GUKGA        */
       , AD.USD_AMOUNT            /* USD_AMOUNT          */
       , AD.USD_AMOUNT            /* AQUI_DOLL           */
       , AD.AQUI_RATE             /* AQUI_RATE           */
       , AD.MERC_SAUP_NO          /* MERC_SAUP_NO        */
       , AD.MERC_ADDR             /* MERC_ADDR           */
       , AD.MERC_REPR             /* MERC_REPR           */
       , AD.MERC_TEL              /* MERC_TEL            */
       , AD.MERC_ZIP              /* MERC_ZIP            */
       , AD.MCC_NAME              /* MERC_UPJONG         */
       , TRIM(AD.MCC_CODE) MCC_CODE  /* MCC_CODE            */
       , TRIM(AD.MCC_STAT) MCC_STAT  /* UPJONG_STAT         */
       , AD.VAT_STAT              /* VAT_STAT            */
       , AD.CAN_DATE              /* CAN_DATE            */
       , AD.ASK_SITE              /* ASK_SITE            */
       , AD.SITE_DATE             /* SITE_DATE           */
       , AD.ASK_DATE              /* ASK_DATE            */
       , AD.ASK_TIME              /* ASK_TIME            */
       , AD.GONGJE_NO_CHK         /* GONGJE_NO_CHK       */
       , AD.AQUI_TIME             /* AQUI_TIME           */
       , AD.CONVERSION_FEE        /* CONVERSION_FEE      */
       , AD.ORG_COLL              /* ORG_COLL            */
       , NULL     UNIQUE_NO       /* UNIQUE_NO           */
       , AD.CARD_KIND             /* CARD_KIND           */
       , AD.SETT_DAY              /* SETT_DAY            */
       , AD.SETT_BANKCODE         /* SETT_BANKCODE       */
       , AD.SETT_ACCO             /* SETT_ACCO           */
       , AD.JUMIN_NO              /* JUMIN_NO            */
       , SYSDATE IF_DATE          /* IF_DATE */
       , NULL    JUNPYO_STATUS    /* JUNPYO_STATUS */
       , NULL    FINANCE_DATE     /* FINANCE_DATE */
       , NULL    FINANCE_SEQ      /* FINANCE_SEQ */
       , NULL    FINANCE_REC1     /* FINANCE_REC1 */
       , NULL    FINANCE_REC2     /* FINANCE_REC2 */
       , NULL    FINANCE_REC3     /* FINANCE_REC3 */
       , NULL    JUNPYO_INS_USER  /* JUNPYO_INS_USER */
       , NULL    JUNPYO_INS_DATE  /* JUNPYO_INS_DATE */
       , NULL    ERR_CHK          /* ERR_CHK */
       , NULL    ERR_MSG          /* ERR_MSG */ 
   FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM AD
  WHERE 1=1
    AND A.COMPANY_CD = 'PHARM' -- 파라메터 : 회사코드(세션에서)
    AND A.DEL_YN = 'N'
--    AND A.APPROVAL_ID = '' -- 파라메터 : 품의서번호
    AND A.APPROVAL_ID = AD.APPROVAL_ID
    AND A.APPROVAL_STATUS_CD = 'NAPR' -- 고정
    ;
    
-- ================================================
-- 4. 만약 심포지움 건이면 관련 원천 심포지움 품의서 찾아가 품의상태를 SAPR로 업데이트 한다.
-- ================================================
UPDATE EAC_APPROVAL A
   SET A.APPROVAL_STATUS_CD='SAPR'
     , A.APPROVAL_DATE = SYSDATE
     , A.UPD_DTTM = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')
     , A.UPD_USER_ID = '' -- 로그인한 사용자ID(세션에서)
 WHERE 1=1
   AND A.COMPANY_CD = 'PHARM' -- 파라메터 : 회사코드(세션에서)
   AND A.APPROVAL_ID IN ( 
                        SELECT S_APPROVAL_ID 
                          FROM EAC_APPROVAL_ITEM Q
                         WHERE 1=1
                           AND Q.COMPANY_CD  = 'PHARM' -- 파라메터 : 회사코드(세션에서)
                           AND Q.APPROVAL_ID = '' -- 파라메터 : 품의서 번호
                           AND Q.DEL_YN = 'N'
                        )
   AND A.DEL_YN = 'N'                