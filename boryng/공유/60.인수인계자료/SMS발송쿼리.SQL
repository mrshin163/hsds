INSERT INTO emma.EM_SMT_TRAN@SMSEXLIB2 (
     MT_PR,
     MT_REFKEY,
     PRIORITY,
     DATE_CLIENT_REQ,
     CONTENT,
     CALLBACK,
     SERVICE_TYPE,
     BROADCAST_YN,
     MSG_STATUS,
     COUNTRY_CODE,
     CRYPTO_YN,
     RECIPIENT_NUM,
     EMMA_ETC3)
   VALUES(
     SQ_EM_SMT_TRAN_01.NEXTVAL@SMSEXLIB2,
     'groupware',
     'S',
     SYSDATE,
     '문자내용', -- 입력값 : 문자내용 
     '027088027', -- 입력값 :  보내는사람 연락처 
     '0',
     'N',
     '1',
     '82',
     'Y',
     '01042002674', -- 입력값 :  받는사람연락처 
     'A11092' -- 입력값 :  보내는사람사번 규칙=> A+사번
  );
  
  COMMIT;