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
     '���ڳ���', -- �Է°� : ���ڳ��� 
     '027088027', -- �Է°� :  �����»�� ����ó 
     '0',
     'N',
     '1',
     '82',
     'Y',
     '01042002674', -- �Է°� :  �޴»������ó 
     'A11092' -- �Է°� :  �����»����� ��Ģ=> A+���
  );
  
  COMMIT;