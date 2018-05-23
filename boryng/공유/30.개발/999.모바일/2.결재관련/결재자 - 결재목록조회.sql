   
-- ====================================
-- 결재자 - 결재 목록 조회 (결재중 내가 할차례인것)
-- ====================================

  SELECT A.COMPANY_CD               AS COMPANY_CD,
         A.APPROVAL_ID              AS APPROVAL_ID,
         MAX(A.APPROVAL_STATUS_CD)  AS APPROVAL_STATUS_CD,
         FN_GET_COM_CD('PHARM','APPROVAL_STATUS_CD',MAX(A.APPROVAL_STATUS_CD)) AS APPROVAL_STATUS_NM,
         MAX (A.RQ_DATE)            AS RQ_DATE,
         MAX(A.APPROVAL_DATE)       AS APPROVAL_DATE,
         MAX(A.RQ_USER_ID)          AS RQ_USER_ID, --상신자ID
         MAX(A.RQ_USER_NM)          AS RQ_USER_NM, --상신자명
         DECODE(MAX(A.RRQ_USER_ID), MAX(A.RQ_USER_ID), NULL, MAX(A.RRQ_USER_ID))   AS RRQ_USER_ID, --대체상신자ID
         DECODE(MAX(A.RRQ_USER_ID), MAX(A.RQ_USER_ID), NULL, MAX(A.RRQ_USER_NM))   AS RRQ_USER_NM, --대체상신자명
         MAX (ALM.AP_DEPT_NM)       AS LAST_AP_DEPT_NM,
         MAX (ALM.AP_USER_NM)       AS LAST_AP_USER_NM,
         MAX (A.ACCOUNT_CD)         AS ACCOUNT_CD,
         MAX (A.ACCOUNT_NM)         AS ACCOUNT_NM,
         NVL (COUNT (AD.SEQ), 0)    AS ITEM_CNT,
         NVL (SUM (REQUEST_AMOUNT), 0) AS REQUEST_AMOUNT
    FROM EAC_APPROVAL A,
         EAC_APPROVAL_ITEM AD,
         (-- 품의서 결재라인중 지금 결재할 사람 정보
          SELECT  AL.COMPANY_CD,
                  AL.APPROVAL_ID,
                  AL.AP_USER_ID,
                  AL.AP_USER_NM,
                  AL.AP_DEPT_CD,
                  AL.AP_DEPT_NM
             FROM EAC_APPROVAL_LINE AL,
                  (  SELECT q1.COMPANY_CD, q1.APPROVAL_ID, MIN (q1.SEQ) SEQ
                       FROM EAC_APPROVAL_LINE q1
                      WHERE q1.COMPANY_CD = 'PHARM'
                        AND Q1.APR_CD IS NULL
                   GROUP BY q1.COMPANY_CD, q1.APPROVAL_ID
                  ) AL2
            WHERE     1 = 1
                  AND AL.COMPANY_CD = AL2.COMPANY_CD
                  AND AL.APPROVAL_ID = AL2.APPROVAL_ID
                  AND AL.SEQ = AL2.SEQ
         ) ALN,
         (-- 품의서 결재라인중 최종결재자 정보
          SELECT  AL.COMPANY_CD,
                  AL.APPROVAL_ID,
                  AL.AP_USER_ID,
                  AL.AP_USER_NM,
                  AL.AP_DEPT_CD,
                  AL.AP_DEPT_NM
             FROM EAC_APPROVAL_LINE AL,
                  (  SELECT q1.COMPANY_CD, q1.APPROVAL_ID, MAX (q1.SEQ) SEQ
                       FROM EAC_APPROVAL_LINE q1
                      WHERE q1.COMPANY_CD = 'PHARM'
                   GROUP BY q1.COMPANY_CD, q1.APPROVAL_ID
                  ) AL2
            WHERE     1 = 1
                  AND AL.COMPANY_CD = AL2.COMPANY_CD
                  AND AL.APPROVAL_ID = AL2.APPROVAL_ID
                  AND AL.SEQ = AL2.SEQ
         ) ALM
   WHERE     1 = 1
         AND A.COMPANY_CD = 'PHARM'                                     -- 세션: 회사CD
         AND A.APPROVAL_STATUS_CD = 'NING'
         AND A.APPROVAL_STATUS_CD NOT IN ('T','NCNL','NREJ','SR','SING','SAPR','SREJ') -- 고정
         AND A.DEL_YN = 'N'                                             -- 고정
         --   AND A.RQ_DATE >= TO_DATE('20150204'||'000000','YYYYMMDDHH24MISS') -- 검색조건 : 상신년/월
         --   AND A.RQ_DATE <= TO_DATE('20150204'||'235959','YYYYMMDDHH24MISS') -- 검색조건 : 상신년/월
         AND ALN.AP_USER_ID = 'V99999'              -- 검색조건 : 내가 결재/반려할(한) 품의서 조회 : 세션사용자ID를 넣을 것
         AND A.COMPANY_CD  = AD.COMPANY_CD
         AND A.APPROVAL_ID = AD.APPROVAL_ID
         AND A.COMPANY_CD  = ALN.COMPANY_CD
         AND A.APPROVAL_ID = ALN.APPROVAL_ID        
         AND A.COMPANY_CD  = ALM.COMPANY_CD(+)
         AND A.APPROVAL_ID = ALM.APPROVAL_ID(+)
GROUP BY A.COMPANY_CD, A.APPROVAL_ID









-- ====================================
-- 결재자 - 결재 목록 조회 (상신중이고 1차결재자가 나인것)
-- ====================================

  SELECT A.COMPANY_CD               AS COMPANY_CD,
         A.APPROVAL_ID              AS APPROVAL_ID,
         MAX(A.APPROVAL_STATUS_CD)  AS APPROVAL_STATUS_CD,
         FN_GET_COM_CD('PHARM','APPROVAL_STATUS_CD',MAX(A.APPROVAL_STATUS_CD)) AS APPROVAL_STATUS_NM,
         MAX (A.RQ_DATE)            AS RQ_DATE,
         MAX(A.APPROVAL_DATE)       AS APPROVAL_DATE,
         MAX(A.RQ_USER_ID)          AS RQ_USER_ID, --상신자ID
         MAX(A.RQ_USER_NM)          AS RQ_USER_NM, --상신자명
         DECODE(MAX(A.RRQ_USER_ID), MAX(A.RQ_USER_ID), NULL, MAX(A.RRQ_USER_ID))   AS RRQ_USER_ID, --대체상신자ID
         DECODE(MAX(A.RRQ_USER_ID), MAX(A.RQ_USER_ID), NULL, MAX(A.RRQ_USER_NM))   AS RRQ_USER_NM, --대체상신자명
         MAX (ALM.AP_DEPT_NM)       AS LAST_AP_DEPT_NM,
         MAX (ALM.AP_USER_NM)       AS LAST_AP_USER_NM,
         MAX (A.ACCOUNT_CD)         AS ACCOUNT_CD,
         MAX (A.ACCOUNT_NM)         AS ACCOUNT_NM,
         NVL (COUNT (AD.SEQ), 0)    AS ITEM_CNT,
         NVL (SUM (REQUEST_AMOUNT), 0) AS REQUEST_AMOUNT
    FROM EAC_APPROVAL A,
         EAC_APPROVAL_ITEM AD,
         EAC_APPROVAL_LINE AL,
         (-- 품의서 결재라인중 최종결재자 정보
          SELECT  AL.COMPANY_CD,
                  AL.APPROVAL_ID,
                  AL.AP_USER_ID,
                  AL.AP_USER_NM,
                  AL.AP_DEPT_CD,
                  AL.AP_DEPT_NM
             FROM EAC_APPROVAL_LINE AL,
                  (  SELECT q1.COMPANY_CD, q1.APPROVAL_ID, MAX (q1.SEQ) SEQ
                       FROM EAC_APPROVAL_LINE q1
                      WHERE q1.COMPANY_CD = 'PHARM'
                   GROUP BY q1.COMPANY_CD, q1.APPROVAL_ID
                  ) AL2
            WHERE     1 = 1
                  AND AL.COMPANY_CD = AL2.COMPANY_CD
                  AND AL.APPROVAL_ID = AL2.APPROVAL_ID
                  AND AL.SEQ = AL2.SEQ
         ) ALM
   WHERE     1 = 1
         AND A.COMPANY_CD = 'PHARM'                                     -- 세션: 회사CD
         AND A.APPROVAL_STATUS_CD = 'NREQ'
         AND A.APPROVAL_STATUS_CD NOT IN ('T','NCNL','NREJ','SR','SING','SAPR','SREJ') -- 고정
         AND A.DEL_YN = 'N'                                             -- 고정
         --   AND A.RQ_DATE >= TO_DATE('20150204'||'000000','YYYYMMDDHH24MISS') -- 검색조건 : 상신년/월
         --   AND A.RQ_DATE <= TO_DATE('20150204'||'235959','YYYYMMDDHH24MISS') -- 검색조건 : 상신년/월
         AND AL.SEQ = 1
         AND AL.AP_USER_ID = 'V99999'              -- 검색조건 : 내가 결재/반려할(한) 품의서 조회 : 세션사용자ID를 넣을 것
         AND A.COMPANY_CD  = AD.COMPANY_CD
         AND A.APPROVAL_ID = AD.APPROVAL_ID
         AND A.COMPANY_CD  = AL.COMPANY_CD
         AND A.APPROVAL_ID = AL.APPROVAL_ID        
         AND A.COMPANY_CD  = ALM.COMPANY_CD(+)
         AND A.APPROVAL_ID = ALM.APPROVAL_ID(+)
GROUP BY A.COMPANY_CD, A.APPROVAL_ID

