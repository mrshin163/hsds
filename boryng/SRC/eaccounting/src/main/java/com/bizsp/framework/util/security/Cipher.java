package com.bizsp.framework.util.security;
import org.apache.commons.codec.binary.Base64;

/**
 * Cipher.java Ŭ����
 * 
 * @desc kisa���� ������ SEED_CBC, SHA256 �Լ��� ���� ���ϰ� ���� Ŭ����
 * 
 */
public class Cipher {
	// KISA_SEED_CBC ��ü. ��� ��ȣȭ
	private static KISA_SEED_CBC kisaSeedCBC = new KISA_SEED_CBC();
	
	// ����ڰ� �����ϴ� �Է� Ű (16 bytes)
	String pbUserKeyString = "PYZG@Q_@GWJ~}UC|";	
	byte pbUserKey[] = pbUserKeyString.getBytes();
	
	//����ڰ� �����ϴ� �ʱ�ȭ ���� (16 bytes) 
	String bszIVString = "A)S(I(;N)@I;B)WG";		
	byte bszIV[] = bszIVString.getBytes();
	
	// KISA_SHA256 ��ü, �ؽ� ��ȣȭ
	private static KISA_SHA256 kisaSHA256= new KISA_SHA256();
	
	/**
	 * SEED_CBC ��ȣȭ (��Ͼ�ȣȭ) 
	 * @param : ��ȣȭ�� ���ڿ�
	 * @return : ��ȣȭ�� ���ڿ�
	 * */
	public String seedEncrypt(String message){
		String encryptString = "";
		byte[] pbData = message.getBytes();
		byte[] defaultCipherText = kisaSeedCBC.SEED_CBC_Encrypt(pbUserKey, bszIV, pbData,0, pbData.length);
		byte[] encoded = Base64.encodeBase64(defaultCipherText);
		encryptString = new String(encoded);
		return encryptString;
	}
	
	/**
	 * SEED_CBC ��ȣȭ (��Ͼ�ȣȭ) 
	 * @param : ��ȣȭ�� ���ڿ�
	 * @return : ��ȣȭ�� ���ڿ�
	 * */
	public String seedDecrypt(String encryptString){
		String decryptString = "";
		byte[] pbData = encryptString.getBytes();
		byte[] decoded = Base64.decodeBase64(pbData);
		byte[] PPPPP = kisaSeedCBC.SEED_CBC_Decrypt(pbUserKey, bszIV, decoded, 0,
				decoded.length);
		encryptString = new String(PPPPP);
		return encryptString;
	}
	
	/**
	 * SHA256 ��ȣȭ (�ؽ���ȣȭ) 
	 * @param : ��ȣȭ�� ���ڿ�
	 * @return : ��ȣȭ�� ���ڿ�
	 * */
	public String sha256Encrypt(String message){
		String encryptString = "";
		byte[] pszMessage = message.getBytes();
		byte[] pszDigest = new byte[32];
		
		kisaSHA256.SHA256_Encrypt(pszMessage, pszMessage.length,pszDigest);
		
		byte[] encoded = Base64.encodeBase64(pszDigest);
		encryptString = new String(encoded);
		return encryptString;
	}
	
	public static void main(String arg[]) throws Exception{
		String url = "jdbc:oracle:thin:@172.16.1.78:1521:orcl";
		Cipher chipher = new Cipher();
		String encrypt = chipher.seedEncrypt(url);
//		System.out.println("url : "+encrypt);

		String username = "EACCOUNTING_T";
		encrypt = chipher.seedEncrypt(username);
//		System.out.println("username : "+encrypt);

		String password = "EACCOUNTING1#_T";
		encrypt = chipher.seedEncrypt(password);
//		System.out.println("password : "+encrypt);

		String localhost = "jdbc:oracle:thin:@localhost:172.16.1.78:orcl";
		encrypt = chipher.seedEncrypt(localhost);
//		System.out.println("encrypt : "+encrypt);

		
		
		String pw = "1111";
		encrypt = chipher.seedEncrypt(pw);
//		System.out.println("pw : "+encrypt);
		
		/*String aa = "1111";
		String decrypt = chipher.seedDecrypt(aa);
		System.out.println("decrypt : " + decrypt);
*/
		String message = "1111";
		String shaEnc = chipher.sha256Encrypt(message);
//		System.out.println("sha : " + shaEnc);
		
	}
}
