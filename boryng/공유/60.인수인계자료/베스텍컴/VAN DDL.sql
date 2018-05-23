<법인카드 최종 ERP 공통 테이블>

-- 최종 ERP 테이블은 SEQ_NO 컬럼을 사용안한다.

1) 승인내역**************************************************************************************************

CREATE TABLE CARD_AP_TMP (
 OWNER_REG_NO        CHAR(10)        NOT    NULL    -- 기업사업자번호(신고사업자번호)        
,CARD_CODE        CHAR(03)        NOT    NULL    -- 카드사코드(예:26)                     
,CARD_NAME        VARCHAR2(20)        NOT    NULL    -- 카드사명(예:신한카드)                 
,CARD_NUM        CHAR(16)        NOT    NULL    -- 카드번호                              
,USER_NAME        VARCHAR2(100)        NULL        -- 소지자명                                      
,AUTH_NUM        CHAR(10)        NOT    NULL    -- 승인번호                              
,AUTH_DATE        CHAR(08)        NOT    NULL    -- 거래일자(승인일자)                    
,AUTH_TIME        CHAR(06)        NULL        -- 거래시간                                      
,GEORAE_STAT        VARCHAR2(04)        NOT    NULL    -- 거래상태(1:'승인',0:'취소')           
,GEORAE_CAND        CHAR(08)        NULL        -- 승인취소일자                                  
,REQUEST_AMOUNT        NUMBER(18,0)        NOT    NULL    -- 국내건거래금액합계                    
,AMT_AMOUNT        NUMBER(18,0)        NULL        -- 국내건공급가액                                
,VAT_AMOUNT        NUMBER(18,0)        NULL        -- 국내건부가세                                  
,SER_AMOUNT        NUMBER(18,0)        NULL        -- 국내건봉사료                                  
,FRE_AMOUNT        NUMBER(18,0)        NULL        -- 국내건면세금액(현재는 카드사에서 내려오지않음)
,AMT_MD_AMOUNT        NUMBER(18,0)        NULL        -- 국내건공급가계산액(10/11_분리된_후)           
,VAT_MD_AMOUNT        NUMBER(18,0)        NULL        -- 국내건부가세계산액(1/11_분리된_후)            
,GEORAE_GUKGA        VARCHAR2(03)        NULL        -- 통화코드                                      
,FOR_AMOUNT        NUMBER(18,4)        NOT    NULL    -- 해외건이용금액(현지금액)              
,MERC_NAME        VARCHAR2(70)        NULL        -- 가맹점명                                      
,MERC_SAUP_NO        VARCHAR2(10)        NULL         -- 가맹점사업자번호                             
,MERC_ADDR        VARCHAR2(130)        NULL         -- 가맹점주소                                   
,MERC_REPR        VARCHAR2(100)        NULL         -- 가맹점대표자명                               
,MERC_TEL        VARCHAR2(20)        NULL         -- 가맹점전화번호                               
,MERC_ZIP        VARCHAR2(06)        NULL         -- 가맹점우편번호                               
,MCC_NAME        VARCHAR2(100)        NULL         -- 가맹점업종명                                 
,MCC_CODE        CHAR(10)        NULL         -- 가맹점업종코드                               
,MCC_STAT        VARCHAR2(02)        NULL         -- 가맹점업종구분                               
,VAT_STAT        VARCHAR2(10)        NULL         -- 가맹점과세유형                               
,CAN_DATE        VARCHAR2(08)        NULL         -- 가맹점폐업일자                               
,ASK_SITE        VARCHAR2(01)        NULL         -- 국세청조회사이트(N:NTS,H:HOMETAX)            
,SITE_DATE        VARCHAR2(08)        NULL         -- 국세청사이트기준일자                         
,ASK_DATE        VARCHAR2(08)        NULL         -- 국세청조회일자                               
,ASK_TIME        VARCHAR2(06)        NULL         -- 국세청조회시간                               
,GONGJE_NO_CHK        VARCHAR2(01)        NULL         -- 매입세액공제불가구분(Y:불가,매입된다고가정하)
,CREATEDATE        VARCHAR2(08)        NULL         -- 생성년월일                                   
,CREATETIME        VARCHAR2(06)        NULL         -- 생성시분초                                   
,SEND_YN        VARCHAR2(01)        NULL         -- 이관여부(기업에서 사용하실수    있는 컬럼)
,UPDATEDATE           VARCHAR2(8)         NULL        --업데이트년월일                
,UPDATETIME           VARCHAR2(6)        NULL        --업데이트시분초                
,MERC_NUM         VARCHAR2(15)         NULL        --가맹점번호                
,MERC_ADDR1           VARCHAR2(120)        NULL        --가맹점기본주소                
,MERC_ADDR_DTL        VARCHAR2(120)        NULL        --가맹점상세주소                
,VAT_FORCE_ZERO_CHK     VARCHAR2(01)           NULL        --불공제건_세액0원_처리여부        
,VAT_STAT_ORG        VARCHAR2(10)           NULL        --원본_가맹점과세유형            
)

--밑에는 하나씩 실행할것.

ALTER TABLE CARD_AP_TMP ADD CONSTRAINT PK_CARD_AP_TMP PRIMARY KEY
 (CARD_NUM, AUTH_DATE, AUTH_NUM, GEORAE_STAT, REQUEST_AMOUNT, FOR_AMOUNT)


CREATE INDEX IDX_MERC_NAME ON CARD_AP_TMP (MERC_NAME)

--------------------------------------------------------------------------------------------------
-- 과세유형 : 1:일반, 2:간이, 3:면세, 4:휴업, 5:폐업, 6:비영리, 8:미등록, 9:오류, 0:해외, 7:기타
-- (과세유형이 일반일때만 부가세환급 가능)
-- 업종구분: 00:일반, 01:유흥, 02:항공, 03:철도, 04:선박-버스,05:기타교통비, 06:PG-OM, 07:농축수산물,
-- 08:공공서비스, 09:면세점,10:차량유지, 98:미확인업종, 99:기타
-- 매입세액공제불가 : Y(불가), NULL(공제후보로서 최종 공제대상으로 결정은 접대비 등 제외처리해야함)




2) 매입내역**************************************************************************************************

CREATE TABLE CARD_AQ_TMP (
 OWNER_REG_NO        CHAR(10)        NOT    NULL    -- 기업사업자번호(신고사업자번호)
,CARD_CODE        CHAR(03)        NOT    NULL    -- 카드사코드(예:26)
,CARD_NAME        VARCHAR2(20)        NOT    NULL    -- 카드사명(예:신한카드)
,CARD_NUM        CHAR(16)        NOT    NULL    -- 카드번호
,USER_NAME        VARCHAR2(100)        NULL        -- 소지자명
,AUTH_NUM        CHAR(10)        NOT    NULL    -- 승인번호
,AUTH_DATE        CHAR(08)        NOT    NULL    -- 거래일자(승인일자)
,AUTH_TIME        CHAR(06)        NULL        -- 거래시간
,AQUI_DATE        CHAR(08)        NULL        -- 매입일자
,GEORAE_COLL        VARCHAR2(40)        NOT    NULL    -- 매입추심번호
,GEORAE_STAT        VARCHAR2(04)        NOT    NULL    -- 거래상태(1:'매입',0:'취소')
,GEORAE_CAND        CHAR(08)        NULL        -- 매입취소일자
,REQUEST_AMOUNT        NUMBER(18,0)        NOT    NULL    -- 원화거래금액합계(해외건은_원화환산금액)
,AMT_AMOUNT        NUMBER(18,0)        NULL        -- 국내건공급가액                                  
,VAT_AMOUNT        NUMBER(18,0)        NULL        -- 국내건부가세                                    
,SER_AMOUNT        NUMBER(18,0)        NULL        -- 국내건봉사료                                    
,FRE_AMOUNT        NUMBER(18,0)        NULL        -- 국내건면세금액(현재는 카드사에서 내려오지않음)  
,AMT_MD_AMOUNT        NUMBER(18,0)        NULL        -- 국내건공급가계산액(10/11_분리된_후)             
,VAT_MD_AMOUNT        NUMBER(18,0)        NULL        -- 국내건부가세계산액(1/11_분리된_후)              
,GEORAE_GUKGA        VARCHAR2(03)        NULL        -- 통화코드                                        
,FOR_AMOUNT        NUMBER(18,4)        NULL        -- 해외건외화이용금액(현지금액)                    
,USD_AMOUNT        NUMBER(18,4)        NULL        -- 해외건달러환산금액                              
,AQUI_RATE        NUMBER(18,4)        NULL        -- 환율                                            
,MERC_NAME        VARCHAR2(70)        NULL        -- 가맹점명                                        
,MERC_SAUP_NO        VARCHAR2(10)        NULL        -- 가맹점사업자번호                                
,MERC_ADDR        VARCHAR2(130)        NULL        -- 가맹점주소                                      
,MERC_REPR        VARCHAR2(100)        NULL        -- 가맹점대표자명                                  
,MERC_TEL        VARCHAR2(20)        NULL        -- 가맹점전화번호                                  
,MERC_ZIP        VARCHAR2(06)        NULL        -- 가맹점우편번호                                  
,MCC_NAME        VARCHAR2(100)        NULL        -- 가맹점업종명                                    
,MCC_CODE        CHAR(10)        NULL        -- 가맹점업종코드                                  
,MCC_STAT        VARCHAR2(02)        NULL        -- 가맹점업종구분                                  
,VAT_STAT        VARCHAR2(10)        NULL        -- 가맹점과세유형                                  
,CAN_DATE        VARCHAR2(08)        NULL        -- 가맹점폐업일자                                  
,ASK_SITE        VARCHAR2(01)        NULL        -- 국세청조회사이트(N:NTS,H:HOMETAX)               
,SITE_DATE        VARCHAR2(08)        NULL        -- 국세청사이트기준일자                            
,ASK_DATE        VARCHAR2(08)        NULL        -- 국세청조회일자                                  
,ASK_TIME        VARCHAR2(06)        NULL        -- 국세청조회시간                                  
,GONGJE_NO_CHK        VARCHAR2(01)        NULL        -- 매입세액공제불가구분(Y:불가,매입된다고가정하)   
,CREATEDATE        VARCHAR2(08)        NULL        -- 생성년월일                                      
,CREATETIME        VARCHAR2(06)        NULL        -- 생성시분초                                      
,SEND_YN        VARCHAR2(01)        NULL        -- 이관여부(기업에서 사용하실수    있는 컬럼)         
,AQUI_TIME        VARCHAR2(6)        NULL        -- 매입시간                                        
,CONVERSION_FEE        NUMBER(18,4)        NULL        -- 환가료                                          
,SETT_DATE        VARCHAR2(8)        NULL        -- 결제예정일자                                    
,ORG_COLL        VARCHAR2(40)                -- 원매입추심번호                                  
,UPDATEDATE           VARCHAR2(8)         NULL        --업데이트년월일                
,UPDATETIME           VARCHAR2(6)        NULL        --업데이트시분초                
,MERC_NUM         VARCHAR2(15)         NULL        --가맹점번호                
,MERC_ADDR1           VARCHAR2(120)        NULL        --가맹점기본주소                
,MERC_ADDR_DTL        VARCHAR2(120)        NULL        --가맹점상세주소                
,AP_REQUEST_AMOUNT     NUMBER(18, 0)         NULL        --국내건거래금액합계
,AP_AMT_AMOUNT        NUMBER(18, 0)         NULL          --국내건공급가액
,AP_VAT_AMOUNT        NUMBER(18, 0)         NULL          --국내건세액
,AP_SER_AMOUNT        NUMBER(18, 0)         NULL          --국내건봉사료
,AP_FRE_AMOUNT        NUMBER(18, 0)         NULL          --국내건면세금액
,AP_AMT_MD_AMOUNT     NUMBER(18, 0)         NULL          --국내건공급가계산액
,AP_VAT_MD_AMOUNT     NUMBER(18, 0)         NULL          --국내건부가세계산액
,AP_GEORAE_GUKGA     VARCHAR2(3)         NULL          --통화코드
,AP_FOR_AMOUNT         NUMBER(18, 4)        NULL          --해외건이용금액
,VAT_FORCE_ZERO_CHK     VARCHAR2(01)           NULL          --불공제건_세액0원_처리여부
,VAT_STAT_ORG           VARCHAR2(10)           NULL          --원본_가맹점과세유형
)

--밑에는 하나씩 실행할것.

ALTER TABLE CARD_AQ_TMP ADD CONSTRAINT PK_CARD_AQ_TMP PRIMARY KEY
 (CARD_NUM, AUTH_DATE, AUTH_NUM, GEORAE_STAT, REQUEST_AMOUNT, GEORAE_COLL)

CREATE INDEX IDX_AQ_MERC_NAME ON CARD_AQ_TMP (MERC_NAME)
CREATE INDEX IDX_AQ_SETT_DATE ON CARD_AQ_TMP (SETT_DATE)





3) 청구내역**************************************************************************************************

CREATE TABLE CARD_DM_TMP (
 OWNER_REG_NO        CHAR(10)        NOT    NULL    -- 기업사업자번호(신고사업자번호)       
,CARD_CODE        CHAR(03)        NOT    NULL    -- 카드사코드(예:26)                    
,CARD_NAME        VARCHAR2(20)        NOT    NULL    -- 카드사명(예:신한카드)                
,CARD_NUM        CHAR(16)        NOT    NULL    -- 카드번호                             
,USER_NAME        VARCHAR2(100)        NULL        -- 소지자명                                     
,AUTH_NUM        CHAR(10)        NOT    NULL    -- 승인번호                             
,AUTH_DATE        CHAR(08)        NOT    NULL    -- 거래일자(승인일자)                   
,AUTH_TIME        CHAR(06)        NULL        -- 거래시간                                     
,SETT_DATE        CHAR(08)        NOT    NULL    -- 결제일자                             
,GEORAE_COLL        VARCHAR2(40)        NOT    NULL    -- 매입추심번호                         
,REQUEST_AMOUNT        NUMBER(18,0)        NULL        -- 청구금액                                     
,MERC_NAME        VARCHAR2(70)        NULL        -- 가맹점명                                     
,MERC_SAUP_NO        VARCHAR2(10)        NULL        -- 가맹점사업자번호                             
,MERC_ADDR        VARCHAR2(130)        NULL        -- 가맹점주소                                   
,MERC_REPR        VARCHAR2(100)        NULL        -- 가맹점대표자명                               
,MERC_TEL        VARCHAR2(20)        NULL        -- 가맹점전화번호                               
,MERC_ZIP        VARCHAR2(6)        NULL        -- 가맹점우편번호                               
,MCC_NAME        VARCHAR2(100)        NULL        -- 가맹점업종명                                 
,MCC_CODE        CHAR(10)        NULL        -- 가맹점업종코드                               
,MCC_STAT        VARCHAR2(02)        NULL        -- 가맹점업종구분                               
,VAT_STAT        VARCHAR2(10)        NULL        -- 가맹점과세유형                               
,CAN_DATE        VARCHAR2(08)        NULL        -- 가맹점폐업일자                               
,ASK_SITE        VARCHAR2(01)        NULL        -- 국세청조회사이트(N:NTS,H:HOMETAX)            
,SITE_DATE        VARCHAR2(08)        NULL        -- 국세청사이트기준일자                         
,ASK_DATE        VARCHAR2(08)        NULL        -- 국세청조회일자                               
,ASK_TIME        VARCHAR2(06)        NULL        -- 국세청조회시간                               
,GONGJE_NO_CHK        VARCHAR2(01)        NULL        -- 매입세액공제불가구분(Y:불가,매입된다고가정하)
,CREATEDATE        VARCHAR2(08)        NULL        -- 생성년월일                                   
,CREATETIME        VARCHAR2(06)        NULL        -- 생성시분초                                   
,SEND_YN        VARCHAR2(01)        NULL        -- 이관여부(기업에서 사용하실수    있는 컬럼)      
,UPDATEDATE           VARCHAR2(8)         NULL        --업데이트년월일                
,UPDATETIME           VARCHAR2(6)        NULL        --업데이트시분초                
,MERC_NUM         VARCHAR2(15)         NULL        --가맹점번호                
,MERC_ADDR1           VARCHAR2(120)        NULL        --가맹점기본주소                
,MERC_ADDR_DTL        VARCHAR2(120)        NULL        --가맹점상세주소                
,AP_REQUEST_AMOUNT     NUMBER(18, 0)         NULL        --국내건거래금액합계
,AP_AMT_AMOUNT        NUMBER(18, 0)         NULL          --국내건공급가액
,AP_VAT_AMOUNT        NUMBER(18, 0)         NULL          --국내건세액
,AP_SER_AMOUNT        NUMBER(18, 0)         NULL          --국내건봉사료
,AP_FRE_AMOUNT        NUMBER(18, 0)         NULL          --국내건면세금액
,AP_AMT_MD_AMOUNT     NUMBER(18, 0)         NULL          --국내건공급가계산액
,AP_VAT_MD_AMOUNT     NUMBER(18, 0)         NULL          --국내건부가세계산액
,AP_GEORAE_GUKGA     VARCHAR2(3)         NULL          --통화코드
,AP_FOR_AMOUNT         NUMBER(18, 4)        NULL          --해외건이용금액
,VAT_STAT_ORG           VARCHAR2(10)           NULL          --원본_가맹점과세유형
)

--밑에는 하나씩 실행할것.

ALTER TABLE CARD_DM_TMP ADD CONSTRAINT PK_CARD_DM_TMP PRIMARY KEY
 (CARD_NUM, AUTH_DATE, AUTH_NUM, REQUEST_AMOUNT, GEORAE_COLL, SETT_DATE)






4) 회원정보**************************************************************************************************

CREATE TABLE CARD_INFO
(
  OWNER_REG_NO        CHAR(10)        NOT NULL    -- 기업사업자번호                      
  ,CARD_NUM          VARCHAR2(16)         NOT NULL     -- 카드번호                            
  ,BANK_CODE         VARCHAR2(03)                      -- 카드사코드                          
  ,BANK_NAME         VARCHAR2(20)                      -- 카드사명                            
  ,CARD_KIND         CHAR(01)                         -- 카드세부구분(1.개인기명 2.법인기명)
  ,USER_NAME         VARCHAR2(50)                      -- 소지자명                            
  ,AVAIL_TERM        CHAR(06)                         -- 유효기간                           
  ,USE_STATUS        VARCHAR2(20)                      -- 카드상태('가능','불가')             
  ,FIRST_DATE        VARCHAR2(10)                      -- 발급일자                            
  ,CANCEL_DATE       VARCHAR2(10)                      -- 탈회일자                            
  ,SETT_DATE         VARCHAR2(02)                      -- 결제일                              
  ,SETT_BANKCODE     VARCHAR2(03)                      -- 결제은행코드                        
  ,SETT_ACCO         VARCHAR2(20)                      -- 결제계좌                            
  ,USE_ONELMT        NUMBER(15)         NOT NULL     -- 국내총한도금액                     
  ,ONE_LIMIT         NUMBER(15)         NOT NULL     -- 국내잔여한도금액                   
  ,FORE_LMT          NUMBER(15)         NOT NULL     -- 해외총한도금액                     
  ,FORE_USELMT       NUMBER(15)         NOT NULL     -- 해외잔여한도금액                   
  ,STAND_DATE        VARCHAR2(08)                      -- 카드한도갱신기준일자                
  ,STAND_TIME        VARCHAR2(06)                      -- 카드한도갱신기준시간                
  ,BEFCARDNO         VARCHAR2(16)         NULL         --재발급 이전 카드번호                 
  ,JUMIN_NO          VARCHAR2(13)         NULL         -- 주민등록번호                        
  ,CARD_JOIN         VARCHAR2(05)                      -- 카드제휴코드(특정 제휴카드 구분)    
)

ALTER TABLE CARD_INFO ADD CONSTRAINT PK_CARD_INFO PRIMARY KEY
 (CARD_NUM )


-- 카드사 코드
하나카드 81
삼성카드 193
롯데카드 210
광주은행 250
KB카드   993
제주은행 970
전북은행 980
현대카드 990
수협카드 991
신한카드 997
외환카드 998
BC카드   999