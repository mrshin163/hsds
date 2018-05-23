-- #{userId} : 사용자사번
SELECT SUM (CNT) AS CNT
  FROM (SELECT COUNT (1) AS CNT
          FROM CARD_AQ_TMP C, EAC_CARD D, EAC_CARD_AQ_MGMT G
         WHERE     1 = 1
               AND C.CARD_NUM = D.CARD_NO
               AND D.COMPANY_CD = 'PHARM'
               AND D.USE_YN = 'Y'
               AND D.DEL_YN = 'N'
               AND D.USER_ID = #{userId}
               AND C.CARD_NUM = G.CARD_NUM
               AND C.AUTH_DATE = G.AUTH_DATE
               AND C.AUTH_NUM = G.AUTH_NUM
               AND C.GEORAE_STAT = G.GEORAE_STAT
               AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
               AND C.GEORAE_COLL = G.GEORAE_COLL
               AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1')
               AND C.CARD_NUM NOT IN
                      (SELECT A.CARD_NUM
                         FROM CARD_AQ_TMP A, EAC_CARD E, EAC_CARD_SCHEDULE S
                        WHERE     1 = 1
                              AND A.CARD_NUM = E.CARD_NO
                              AND E.COMPANY_CD = 'PHARM'
                              AND E.COMPANY_CD = S.COMPANY_CD
                              AND E.CARD_NO = S.CARD_NO
                              AND A.AUTH_DATE BETWEEN S.START_DAY
                                                  AND S.END_DAY)
               AND (C.CARD_NUM,
                    C.AUTH_DATE,
                    C.AUTH_NUM,
                    C.GEORAE_STAT,
                    C.REQUEST_AMOUNT,
                    C.GEORAE_COLL) NOT IN
                      (SELECT I.CARD_NUM,
                              I.AUTH_DATE,
                              I.AUTH_NUM,
                              I.GEORAE_STAT,
                              I.REQUEST_AMOUNT,
                              I.GEORAE_COLL
                         FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                        WHERE     1 = 1
                              AND A.COMPANY_CD = 'PHARM'
                              AND A.COMPANY_CD = I.COMPANY_CD
                              AND A.APPROVAL_ID = I.APPROVAL_ID
                              AND A.APPROVAL_STATUS_CD IN
                                     ('NREQ',
                                      'NING',
                                      'NAPR',
                                      'EAPR',
                                      'SR',
                                      'SING',
                                      'SAPR')
                              AND A.DEL_YN = 'N')
        UNION ALL
        SELECT COUNT (1) AS CNT
          FROM CARD_AQ_TMP C,
               EAC_CARD E,
               EAC_CARD_SCHEDULE S,
               EAC_CARD_AQ_MGMT G
         WHERE     1 = 1
               AND C.CARD_NUM = E.CARD_NO
               AND E.COMPANY_CD = 'PHARM'
               AND E.COMPANY_CD = S.COMPANY_CD
               AND E.CARD_NO = S.CARD_NO
               AND E.USE_YN = 'Y'
               AND E.DEL_YN = 'N'
               AND S.USER_ID = #{userId}
               AND C.CARD_NUM = G.CARD_NUM
               AND C.AUTH_DATE = G.AUTH_DATE
               AND C.AUTH_NUM = G.AUTH_NUM
               AND C.GEORAE_STAT = G.GEORAE_STAT
               AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
               AND C.GEORAE_COLL = G.GEORAE_COLL
               AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1')
               AND C.AUTH_DATE BETWEEN S.START_DAY AND S.END_DAY
               AND (C.CARD_NUM,
                    C.AUTH_DATE,
                    C.AUTH_NUM,
                    C.GEORAE_STAT,
                    C.REQUEST_AMOUNT,
                    C.GEORAE_COLL) NOT IN
                      (SELECT I.CARD_NUM,
                              I.AUTH_DATE,
                              I.AUTH_NUM,
                              I.GEORAE_STAT,
                              I.REQUEST_AMOUNT,
                              I.GEORAE_COLL
                         FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                        WHERE     1 = 1
                              AND A.COMPANY_CD = 'PHARM'
                              AND A.COMPANY_CD = I.COMPANY_CD
                              AND A.APPROVAL_ID = I.APPROVAL_ID
                              AND A.APPROVAL_STATUS_CD IN
                                     ('NREQ',
                                      'NING',
                                      'NAPR',
                                      'EAPR',
                                      'SR',
                                      'SING',
                                      'SAPR')
                              AND A.DEL_YN = 'N')
        UNION ALL
        SELECT COUNT (1) AS CNT
          FROM CARD_AQ_TMP C,
               EAC_CARD E,
               EAC_CARD_MGMT M,
               EAC_CARD_AQ_MGMT G
         WHERE     1 = 1
               AND C.CARD_NUM = E.CARD_NO
               AND E.COMPANY_CD = 'PHARM'
               AND E.COMPANY_CD = M.COMPANY_CD
               AND E.CARD_NO = M.CARD_NO
               AND E.USE_YN = 'Y'
               AND E.DEL_YN = 'N'
               AND (M.SEC1_USER_ID = #{userId} OR M.SEC2_USER_ID = #{userId})
               AND C.CARD_NUM = G.CARD_NUM
               AND C.AUTH_DATE = G.AUTH_DATE
               AND C.AUTH_NUM = G.AUTH_NUM
               AND C.GEORAE_STAT = G.GEORAE_STAT
               AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
               AND C.GEORAE_COLL = G.GEORAE_COLL
               AND M.SEC_CARD_YN = 'Y'
               AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1')
               AND (C.CARD_NUM,
                    C.AUTH_DATE,
                    C.AUTH_NUM,
                    C.GEORAE_STAT,
                    C.REQUEST_AMOUNT,
                    C.GEORAE_COLL) NOT IN
                      (SELECT I.CARD_NUM,
                              I.AUTH_DATE,
                              I.AUTH_NUM,
                              I.GEORAE_STAT,
                              I.REQUEST_AMOUNT,
                              I.GEORAE_COLL
                         FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                        WHERE     1 = 1
                              AND A.COMPANY_CD = 'PHARM'
                              AND A.COMPANY_CD = I.COMPANY_CD
                              AND A.APPROVAL_ID = I.APPROVAL_ID
                              AND A.APPROVAL_STATUS_CD IN
                                     ('NREQ',
                                      'NING',
                                      'NAPR',
                                      'EAPR',
                                      'SR',
                                      'SING',
                                      'SAPR')
                              AND A.DEL_YN = 'N')
        UNION ALL
        SELECT COUNT (1) AS CNT
          FROM CARD_AQ_TMP C, EAC_CARD_AQ_MGMT G
         WHERE     1 = 1
               AND C.CARD_NUM = G.CARD_NUM
               AND C.AUTH_DATE = G.AUTH_DATE
               AND C.AUTH_NUM = G.AUTH_NUM
               AND C.GEORAE_STAT = G.GEORAE_STAT
               AND C.REQUEST_AMOUNT = G.REQUEST_AMOUNT
               AND C.GEORAE_COLL = G.GEORAE_COLL
               AND G.ACT_USER_ID IS NOT NULL
               AND G.ACT_USER_ID = #{userId}
               AND (G.CARD_USING_TP_CD IS NULL OR G.CARD_USING_TP_CD = '1')
               AND (C.CARD_NUM,
                    C.AUTH_DATE,
                    C.AUTH_NUM,
                    C.GEORAE_STAT,
                    C.REQUEST_AMOUNT,
                    C.GEORAE_COLL) NOT IN
                      (SELECT I.CARD_NUM,
                              I.AUTH_DATE,
                              I.AUTH_NUM,
                              I.GEORAE_STAT,
                              I.REQUEST_AMOUNT,
                              I.GEORAE_COLL
                         FROM EAC_APPROVAL A, EAC_APPROVAL_ITEM I
                        WHERE     1 = 1
                              AND A.COMPANY_CD = 'PHARM'
                              AND A.COMPANY_CD = I.COMPANY_CD
                              AND A.APPROVAL_ID = I.APPROVAL_ID
                              AND A.APPROVAL_STATUS_CD IN
                                     ('NREQ',
                                      'NING',
                                      'NAPR',
                                      'EAPR',
                                      'SR',
                                      'SING',
                                      'SAPR')
                              AND A.DEL_YN = 'N')
        UNION ALL
        SELECT COUNT (1) AS CNT
          FROM ( (  SELECT A.COMPANY_CD AS companyCd,
                           A.APPROVAL_ID AS approvalId
                      FROM EAC_APPROVAL A,
                           EAC_APPROVAL_ITEM AD,
                           (  SELECT COMPANY_CD, APPROVAL_ID, MIN (SEQ) SEQ
                                FROM EAC_APPROVAL_LINE
                               WHERE COMPANY_CD = 'PHARM'
                                     AND ( (FN_GET_USER_INFO ('PHARM',
                                                              #{userId},
                                                              'DEPT_CD') IN
                                               ('53210',
                                                '54110',
                                                '54120',
                                                '52140')
                                            AND (AP_USER_ID = #{userId}
                                                 OR AP_USER_ID =
                                                       'V'
                                                       || FN_GET_USER_INFO (
                                                             'PHARM',
                                                             #{userId},
                                                             'DEPT_CD')))
                                          OR (FN_GET_USER_INFO ('PHARM',
                                                                #{userId},
                                                                'DEPT_CD') NOT IN
                                                 ('53210',
                                                  '54110',
                                                  '54120',
                                                  '52140')
                                              AND AP_USER_ID = #{userId}))
                            GROUP BY COMPANY_CD, APPROVAL_ID) AL
                     WHERE     1 = 1
                           AND A.COMPANY_CD = 'PHARM'
                           AND A.APPROVAL_STATUS_CD = 'NREQ'
                           AND AL.SEQ = 1
                           AND A.DEL_YN = 'N'
                           AND A.COMPANY_CD = AD.COMPANY_CD
                           AND A.APPROVAL_ID = AD.APPROVAL_ID
                           AND A.COMPANY_CD = AL.COMPANY_CD
                           AND A.APPROVAL_ID = AL.APPROVAL_ID
                  GROUP BY A.COMPANY_CD, A.APPROVAL_ID)
                UNION ALL
                (  SELECT A.COMPANY_CD AS companyCd,
                          A.APPROVAL_ID AS approvalId
                     FROM EAC_APPROVAL A,
                          EAC_APPROVAL_ITEM AD,
                          (SELECT AL.SEQ,
                                  AL.COMPANY_CD,
                                  AL.APPROVAL_ID,
                                  AL.AP_USER_ID,
                                  AL.AP_USER_NM,
                                  AL.AP_DEPT_CD,
                                  AL.AP_DEPT_NM
                             FROM EAC_APPROVAL_LINE AL,
                                  (  SELECT q1.COMPANY_CD,
                                            q1.APPROVAL_ID,
                                            MIN (q1.SEQ) SEQ
                                       FROM EAC_APPROVAL_LINE q1
                                      WHERE q1.COMPANY_CD = 'PHARM'
                                            AND q1.APR_CD IS NULL
                                            AND ( (FN_GET_USER_INFO ('PHARM',
                                                                     #{userId},
                                                                     'DEPT_CD') IN
                                                      ('53210',
                                                       '54110',
                                                       '54120',
                                                       '52140')
                                                   AND (AP_USER_ID = #{userId}
                                                        OR AP_USER_ID =
                                                              'V'
                                                              || FN_GET_USER_INFO (
                                                                    'PHARM',
                                                                    #{userId},
                                                                    'DEPT_CD')))
                                                 OR (FN_GET_USER_INFO ('PHARM',
                                                                       #{userId},
                                                                       'DEPT_CD') NOT IN
                                                        ('53210',
                                                         '54110',
                                                         '54120',
                                                         '52140')
                                                     AND AP_USER_ID = #{userId}))
                                   GROUP BY q1.COMPANY_CD, q1.APPROVAL_ID) AL2
                            WHERE     1 = 1
                                  AND AL.COMPANY_CD = AL2.COMPANY_CD
                                  AND AL.APPROVAL_ID = AL2.APPROVAL_ID
                                  AND AL.SEQ = AL2.SEQ) ALN
                    WHERE     1 = 1
                          AND A.COMPANY_CD = 'PHARM'
                          AND A.APPROVAL_STATUS_CD = 'NING'
                          AND A.DEL_YN = 'N'
                          AND A.COMPANY_CD = AD.COMPANY_CD
                          AND A.APPROVAL_ID = AD.APPROVAL_ID
                          AND A.COMPANY_CD = ALN.COMPANY_CD
                          AND A.APPROVAL_ID = ALN.APPROVAL_ID
                 GROUP BY A.COMPANY_CD, A.APPROVAL_ID)))