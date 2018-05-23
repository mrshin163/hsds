<����ī�� ���� ERP ���� ���̺�>

-- ���� ERP ���̺��� SEQ_NO �÷��� �����Ѵ�.

1) ���γ���**************************************************************************************************

CREATE TABLE CARD_AP_TMP (
 OWNER_REG_NO        CHAR(10)        NOT    NULL    -- �������ڹ�ȣ(�Ű����ڹ�ȣ)        
,CARD_CODE        CHAR(03)        NOT    NULL    -- ī����ڵ�(��:26)                     
,CARD_NAME        VARCHAR2(20)        NOT    NULL    -- ī����(��:����ī��)                 
,CARD_NUM        CHAR(16)        NOT    NULL    -- ī���ȣ                              
,USER_NAME        VARCHAR2(100)        NULL        -- �����ڸ�                                      
,AUTH_NUM        CHAR(10)        NOT    NULL    -- ���ι�ȣ                              
,AUTH_DATE        CHAR(08)        NOT    NULL    -- �ŷ�����(��������)                    
,AUTH_TIME        CHAR(06)        NULL        -- �ŷ��ð�                                      
,GEORAE_STAT        VARCHAR2(04)        NOT    NULL    -- �ŷ�����(1:'����',0:'���')           
,GEORAE_CAND        CHAR(08)        NULL        -- �����������                                  
,REQUEST_AMOUNT        NUMBER(18,0)        NOT    NULL    -- �����ǰŷ��ݾ��հ�                    
,AMT_AMOUNT        NUMBER(18,0)        NULL        -- �����ǰ��ް���                                
,VAT_AMOUNT        NUMBER(18,0)        NULL        -- �����Ǻΰ���                                  
,SER_AMOUNT        NUMBER(18,0)        NULL        -- �����Ǻ����                                  
,FRE_AMOUNT        NUMBER(18,0)        NULL        -- �����Ǹ鼼�ݾ�(����� ī��翡�� ������������)
,AMT_MD_AMOUNT        NUMBER(18,0)        NULL        -- �����ǰ��ް�����(10/11_�и���_��)           
,VAT_MD_AMOUNT        NUMBER(18,0)        NULL        -- �����Ǻΰ�������(1/11_�и���_��)            
,GEORAE_GUKGA        VARCHAR2(03)        NULL        -- ��ȭ�ڵ�                                      
,FOR_AMOUNT        NUMBER(18,4)        NOT    NULL    -- �ؿܰ��̿�ݾ�(�����ݾ�)              
,MERC_NAME        VARCHAR2(70)        NULL        -- ��������                                      
,MERC_SAUP_NO        VARCHAR2(10)        NULL         -- ����������ڹ�ȣ                             
,MERC_ADDR        VARCHAR2(130)        NULL         -- �������ּ�                                   
,MERC_REPR        VARCHAR2(100)        NULL         -- ��������ǥ�ڸ�                               
,MERC_TEL        VARCHAR2(20)        NULL         -- ��������ȭ��ȣ                               
,MERC_ZIP        VARCHAR2(06)        NULL         -- �����������ȣ                               
,MCC_NAME        VARCHAR2(100)        NULL         -- ������������                                 
,MCC_CODE        CHAR(10)        NULL         -- �����������ڵ�                               
,MCC_STAT        VARCHAR2(02)        NULL         -- ��������������                               
,VAT_STAT        VARCHAR2(10)        NULL         -- ��������������                               
,CAN_DATE        VARCHAR2(08)        NULL         -- �������������                               
,ASK_SITE        VARCHAR2(01)        NULL         -- ����û��ȸ����Ʈ(N:NTS,H:HOMETAX)            
,SITE_DATE        VARCHAR2(08)        NULL         -- ����û����Ʈ��������                         
,ASK_DATE        VARCHAR2(08)        NULL         -- ����û��ȸ����                               
,ASK_TIME        VARCHAR2(06)        NULL         -- ����û��ȸ�ð�                               
,GONGJE_NO_CHK        VARCHAR2(01)        NULL         -- ���Լ��װ����Ұ�����(Y:�Ұ�,���Եȴٰ�����)
,CREATEDATE        VARCHAR2(08)        NULL         -- ���������                                   
,CREATETIME        VARCHAR2(06)        NULL         -- �����ú���                                   
,SEND_YN        VARCHAR2(01)        NULL         -- �̰�����(������� ����ϽǼ�    �ִ� �÷�)
,UPDATEDATE           VARCHAR2(8)         NULL        --������Ʈ�����                
,UPDATETIME           VARCHAR2(6)        NULL        --������Ʈ�ú���                
,MERC_NUM         VARCHAR2(15)         NULL        --��������ȣ                
,MERC_ADDR1           VARCHAR2(120)        NULL        --�������⺻�ּ�                
,MERC_ADDR_DTL        VARCHAR2(120)        NULL        --���������ּ�                
,VAT_FORCE_ZERO_CHK     VARCHAR2(01)           NULL        --�Ұ�����_����0��_ó������        
,VAT_STAT_ORG        VARCHAR2(10)           NULL        --����_��������������            
)

--�ؿ��� �ϳ��� �����Ұ�.

ALTER TABLE CARD_AP_TMP ADD CONSTRAINT PK_CARD_AP_TMP PRIMARY KEY
 (CARD_NUM, AUTH_DATE, AUTH_NUM, GEORAE_STAT, REQUEST_AMOUNT, FOR_AMOUNT)


CREATE INDEX IDX_MERC_NAME ON CARD_AP_TMP (MERC_NAME)

--------------------------------------------------------------------------------------------------
-- �������� : 1:�Ϲ�, 2:����, 3:�鼼, 4:�޾�, 5:���, 6:�񿵸�, 8:�̵��, 9:����, 0:�ؿ�, 7:��Ÿ
-- (���������� �Ϲ��϶��� �ΰ���ȯ�� ����)
-- ��������: 00:�Ϲ�, 01:����, 02:�װ�, 03:ö��, 04:����-����,05:��Ÿ�����, 06:PG-OM, 07:������깰,
-- 08:��������, 09:�鼼��,10:��������, 98:��Ȯ�ξ���, 99:��Ÿ
-- ���Լ��װ����Ұ� : Y(�Ұ�), NULL(�����ĺ��μ� ���� ����������� ������ ����� �� ����ó���ؾ���)




2) ���Գ���**************************************************************************************************

CREATE TABLE CARD_AQ_TMP (
 OWNER_REG_NO        CHAR(10)        NOT    NULL    -- �������ڹ�ȣ(�Ű����ڹ�ȣ)
,CARD_CODE        CHAR(03)        NOT    NULL    -- ī����ڵ�(��:26)
,CARD_NAME        VARCHAR2(20)        NOT    NULL    -- ī����(��:����ī��)
,CARD_NUM        CHAR(16)        NOT    NULL    -- ī���ȣ
,USER_NAME        VARCHAR2(100)        NULL        -- �����ڸ�
,AUTH_NUM        CHAR(10)        NOT    NULL    -- ���ι�ȣ
,AUTH_DATE        CHAR(08)        NOT    NULL    -- �ŷ�����(��������)
,AUTH_TIME        CHAR(06)        NULL        -- �ŷ��ð�
,AQUI_DATE        CHAR(08)        NULL        -- ��������
,GEORAE_COLL        VARCHAR2(40)        NOT    NULL    -- �����߽ɹ�ȣ
,GEORAE_STAT        VARCHAR2(04)        NOT    NULL    -- �ŷ�����(1:'����',0:'���')
,GEORAE_CAND        CHAR(08)        NULL        -- �����������
,REQUEST_AMOUNT        NUMBER(18,0)        NOT    NULL    -- ��ȭ�ŷ��ݾ��հ�(�ؿܰ���_��ȭȯ��ݾ�)
,AMT_AMOUNT        NUMBER(18,0)        NULL        -- �����ǰ��ް���                                  
,VAT_AMOUNT        NUMBER(18,0)        NULL        -- �����Ǻΰ���                                    
,SER_AMOUNT        NUMBER(18,0)        NULL        -- �����Ǻ����                                    
,FRE_AMOUNT        NUMBER(18,0)        NULL        -- �����Ǹ鼼�ݾ�(����� ī��翡�� ������������)  
,AMT_MD_AMOUNT        NUMBER(18,0)        NULL        -- �����ǰ��ް�����(10/11_�и���_��)             
,VAT_MD_AMOUNT        NUMBER(18,0)        NULL        -- �����Ǻΰ�������(1/11_�и���_��)              
,GEORAE_GUKGA        VARCHAR2(03)        NULL        -- ��ȭ�ڵ�                                        
,FOR_AMOUNT        NUMBER(18,4)        NULL        -- �ؿܰǿ�ȭ�̿�ݾ�(�����ݾ�)                    
,USD_AMOUNT        NUMBER(18,4)        NULL        -- �ؿܰǴ޷�ȯ��ݾ�                              
,AQUI_RATE        NUMBER(18,4)        NULL        -- ȯ��                                            
,MERC_NAME        VARCHAR2(70)        NULL        -- ��������                                        
,MERC_SAUP_NO        VARCHAR2(10)        NULL        -- ����������ڹ�ȣ                                
,MERC_ADDR        VARCHAR2(130)        NULL        -- �������ּ�                                      
,MERC_REPR        VARCHAR2(100)        NULL        -- ��������ǥ�ڸ�                                  
,MERC_TEL        VARCHAR2(20)        NULL        -- ��������ȭ��ȣ                                  
,MERC_ZIP        VARCHAR2(06)        NULL        -- �����������ȣ                                  
,MCC_NAME        VARCHAR2(100)        NULL        -- ������������                                    
,MCC_CODE        CHAR(10)        NULL        -- �����������ڵ�                                  
,MCC_STAT        VARCHAR2(02)        NULL        -- ��������������                                  
,VAT_STAT        VARCHAR2(10)        NULL        -- ��������������                                  
,CAN_DATE        VARCHAR2(08)        NULL        -- �������������                                  
,ASK_SITE        VARCHAR2(01)        NULL        -- ����û��ȸ����Ʈ(N:NTS,H:HOMETAX)               
,SITE_DATE        VARCHAR2(08)        NULL        -- ����û����Ʈ��������                            
,ASK_DATE        VARCHAR2(08)        NULL        -- ����û��ȸ����                                  
,ASK_TIME        VARCHAR2(06)        NULL        -- ����û��ȸ�ð�                                  
,GONGJE_NO_CHK        VARCHAR2(01)        NULL        -- ���Լ��װ����Ұ�����(Y:�Ұ�,���Եȴٰ�����)   
,CREATEDATE        VARCHAR2(08)        NULL        -- ���������                                      
,CREATETIME        VARCHAR2(06)        NULL        -- �����ú���                                      
,SEND_YN        VARCHAR2(01)        NULL        -- �̰�����(������� ����ϽǼ�    �ִ� �÷�)         
,AQUI_TIME        VARCHAR2(6)        NULL        -- ���Խð�                                        
,CONVERSION_FEE        NUMBER(18,4)        NULL        -- ȯ����                                          
,SETT_DATE        VARCHAR2(8)        NULL        -- ������������                                    
,ORG_COLL        VARCHAR2(40)                -- �������߽ɹ�ȣ                                  
,UPDATEDATE           VARCHAR2(8)         NULL        --������Ʈ�����                
,UPDATETIME           VARCHAR2(6)        NULL        --������Ʈ�ú���                
,MERC_NUM         VARCHAR2(15)         NULL        --��������ȣ                
,MERC_ADDR1           VARCHAR2(120)        NULL        --�������⺻�ּ�                
,MERC_ADDR_DTL        VARCHAR2(120)        NULL        --���������ּ�                
,AP_REQUEST_AMOUNT     NUMBER(18, 0)         NULL        --�����ǰŷ��ݾ��հ�
,AP_AMT_AMOUNT        NUMBER(18, 0)         NULL          --�����ǰ��ް���
,AP_VAT_AMOUNT        NUMBER(18, 0)         NULL          --�����Ǽ���
,AP_SER_AMOUNT        NUMBER(18, 0)         NULL          --�����Ǻ����
,AP_FRE_AMOUNT        NUMBER(18, 0)         NULL          --�����Ǹ鼼�ݾ�
,AP_AMT_MD_AMOUNT     NUMBER(18, 0)         NULL          --�����ǰ��ް�����
,AP_VAT_MD_AMOUNT     NUMBER(18, 0)         NULL          --�����Ǻΰ�������
,AP_GEORAE_GUKGA     VARCHAR2(3)         NULL          --��ȭ�ڵ�
,AP_FOR_AMOUNT         NUMBER(18, 4)        NULL          --�ؿܰ��̿�ݾ�
,VAT_FORCE_ZERO_CHK     VARCHAR2(01)           NULL          --�Ұ�����_����0��_ó������
,VAT_STAT_ORG           VARCHAR2(10)           NULL          --����_��������������
)

--�ؿ��� �ϳ��� �����Ұ�.

ALTER TABLE CARD_AQ_TMP ADD CONSTRAINT PK_CARD_AQ_TMP PRIMARY KEY
 (CARD_NUM, AUTH_DATE, AUTH_NUM, GEORAE_STAT, REQUEST_AMOUNT, GEORAE_COLL)

CREATE INDEX IDX_AQ_MERC_NAME ON CARD_AQ_TMP (MERC_NAME)
CREATE INDEX IDX_AQ_SETT_DATE ON CARD_AQ_TMP (SETT_DATE)





3) û������**************************************************************************************************

CREATE TABLE CARD_DM_TMP (
 OWNER_REG_NO        CHAR(10)        NOT    NULL    -- �������ڹ�ȣ(�Ű����ڹ�ȣ)       
,CARD_CODE        CHAR(03)        NOT    NULL    -- ī����ڵ�(��:26)                    
,CARD_NAME        VARCHAR2(20)        NOT    NULL    -- ī����(��:����ī��)                
,CARD_NUM        CHAR(16)        NOT    NULL    -- ī���ȣ                             
,USER_NAME        VARCHAR2(100)        NULL        -- �����ڸ�                                     
,AUTH_NUM        CHAR(10)        NOT    NULL    -- ���ι�ȣ                             
,AUTH_DATE        CHAR(08)        NOT    NULL    -- �ŷ�����(��������)                   
,AUTH_TIME        CHAR(06)        NULL        -- �ŷ��ð�                                     
,SETT_DATE        CHAR(08)        NOT    NULL    -- ��������                             
,GEORAE_COLL        VARCHAR2(40)        NOT    NULL    -- �����߽ɹ�ȣ                         
,REQUEST_AMOUNT        NUMBER(18,0)        NULL        -- û���ݾ�                                     
,MERC_NAME        VARCHAR2(70)        NULL        -- ��������                                     
,MERC_SAUP_NO        VARCHAR2(10)        NULL        -- ����������ڹ�ȣ                             
,MERC_ADDR        VARCHAR2(130)        NULL        -- �������ּ�                                   
,MERC_REPR        VARCHAR2(100)        NULL        -- ��������ǥ�ڸ�                               
,MERC_TEL        VARCHAR2(20)        NULL        -- ��������ȭ��ȣ                               
,MERC_ZIP        VARCHAR2(6)        NULL        -- �����������ȣ                               
,MCC_NAME        VARCHAR2(100)        NULL        -- ������������                                 
,MCC_CODE        CHAR(10)        NULL        -- �����������ڵ�                               
,MCC_STAT        VARCHAR2(02)        NULL        -- ��������������                               
,VAT_STAT        VARCHAR2(10)        NULL        -- ��������������                               
,CAN_DATE        VARCHAR2(08)        NULL        -- �������������                               
,ASK_SITE        VARCHAR2(01)        NULL        -- ����û��ȸ����Ʈ(N:NTS,H:HOMETAX)            
,SITE_DATE        VARCHAR2(08)        NULL        -- ����û����Ʈ��������                         
,ASK_DATE        VARCHAR2(08)        NULL        -- ����û��ȸ����                               
,ASK_TIME        VARCHAR2(06)        NULL        -- ����û��ȸ�ð�                               
,GONGJE_NO_CHK        VARCHAR2(01)        NULL        -- ���Լ��װ����Ұ�����(Y:�Ұ�,���Եȴٰ�����)
,CREATEDATE        VARCHAR2(08)        NULL        -- ���������                                   
,CREATETIME        VARCHAR2(06)        NULL        -- �����ú���                                   
,SEND_YN        VARCHAR2(01)        NULL        -- �̰�����(������� ����ϽǼ�    �ִ� �÷�)      
,UPDATEDATE           VARCHAR2(8)         NULL        --������Ʈ�����                
,UPDATETIME           VARCHAR2(6)        NULL        --������Ʈ�ú���                
,MERC_NUM         VARCHAR2(15)         NULL        --��������ȣ                
,MERC_ADDR1           VARCHAR2(120)        NULL        --�������⺻�ּ�                
,MERC_ADDR_DTL        VARCHAR2(120)        NULL        --���������ּ�                
,AP_REQUEST_AMOUNT     NUMBER(18, 0)         NULL        --�����ǰŷ��ݾ��հ�
,AP_AMT_AMOUNT        NUMBER(18, 0)         NULL          --�����ǰ��ް���
,AP_VAT_AMOUNT        NUMBER(18, 0)         NULL          --�����Ǽ���
,AP_SER_AMOUNT        NUMBER(18, 0)         NULL          --�����Ǻ����
,AP_FRE_AMOUNT        NUMBER(18, 0)         NULL          --�����Ǹ鼼�ݾ�
,AP_AMT_MD_AMOUNT     NUMBER(18, 0)         NULL          --�����ǰ��ް�����
,AP_VAT_MD_AMOUNT     NUMBER(18, 0)         NULL          --�����Ǻΰ�������
,AP_GEORAE_GUKGA     VARCHAR2(3)         NULL          --��ȭ�ڵ�
,AP_FOR_AMOUNT         NUMBER(18, 4)        NULL          --�ؿܰ��̿�ݾ�
,VAT_STAT_ORG           VARCHAR2(10)           NULL          --����_��������������
)

--�ؿ��� �ϳ��� �����Ұ�.

ALTER TABLE CARD_DM_TMP ADD CONSTRAINT PK_CARD_DM_TMP PRIMARY KEY
 (CARD_NUM, AUTH_DATE, AUTH_NUM, REQUEST_AMOUNT, GEORAE_COLL, SETT_DATE)






4) ȸ������**************************************************************************************************

CREATE TABLE CARD_INFO
(
  OWNER_REG_NO        CHAR(10)        NOT NULL    -- �������ڹ�ȣ                      
  ,CARD_NUM          VARCHAR2(16)         NOT NULL     -- ī���ȣ                            
  ,BANK_CODE         VARCHAR2(03)                      -- ī����ڵ�                          
  ,BANK_NAME         VARCHAR2(20)                      -- ī����                            
  ,CARD_KIND         CHAR(01)                         -- ī�弼�α���(1.���α�� 2.���α��)
  ,USER_NAME         VARCHAR2(50)                      -- �����ڸ�                            
  ,AVAIL_TERM        CHAR(06)                         -- ��ȿ�Ⱓ                           
  ,USE_STATUS        VARCHAR2(20)                      -- ī�����('����','�Ұ�')             
  ,FIRST_DATE        VARCHAR2(10)                      -- �߱�����                            
  ,CANCEL_DATE       VARCHAR2(10)                      -- Żȸ����                            
  ,SETT_DATE         VARCHAR2(02)                      -- ������                              
  ,SETT_BANKCODE     VARCHAR2(03)                      -- ���������ڵ�                        
  ,SETT_ACCO         VARCHAR2(20)                      -- ��������                            
  ,USE_ONELMT        NUMBER(15)         NOT NULL     -- �������ѵ��ݾ�                     
  ,ONE_LIMIT         NUMBER(15)         NOT NULL     -- �����ܿ��ѵ��ݾ�                   
  ,FORE_LMT          NUMBER(15)         NOT NULL     -- �ؿ����ѵ��ݾ�                     
  ,FORE_USELMT       NUMBER(15)         NOT NULL     -- �ؿ��ܿ��ѵ��ݾ�                   
  ,STAND_DATE        VARCHAR2(08)                      -- ī���ѵ����ű�������                
  ,STAND_TIME        VARCHAR2(06)                      -- ī���ѵ����ű��ؽð�                
  ,BEFCARDNO         VARCHAR2(16)         NULL         --��߱� ���� ī���ȣ                 
  ,JUMIN_NO          VARCHAR2(13)         NULL         -- �ֹε�Ϲ�ȣ                        
  ,CARD_JOIN         VARCHAR2(05)                      -- ī�������ڵ�(Ư�� ����ī�� ����)    
)

ALTER TABLE CARD_INFO ADD CONSTRAINT PK_CARD_INFO PRIMARY KEY
 (CARD_NUM )


-- ī��� �ڵ�
�ϳ�ī�� 81
�Ｚī�� 193
�Ե�ī�� 210
�������� 250
KBī��   993
�������� 970
�������� 980
����ī�� 990
����ī�� 991
����ī�� 997
��ȯī�� 998
BCī��   999