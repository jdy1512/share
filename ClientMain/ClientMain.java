import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain {
	
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("192.168.7.250", 12345);
		PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		new ClientThread(new BufferedReader(new InputStreamReader(socket.getInputStream()))).start();
		
		String str = null;
		String companyName = null;
		int stockNum = 0;
		System.out.println("1.���� ��ȸ  2.�ֽĽ��� ��ȸ  3.�ŵ�  4.�ż�");
		while ((str = in.readLine()) != null) {
			try {
				int select = Integer.parseInt(str);
				switch (select) {
				case 1:
					pw.println("���� ��ȸ");
					break;
				case 2:
					pw.println("�ֽĽ��� ��ȸ");
					break;
				case 3:
					System.out.print("ȸ��� : ");
					companyName = in.readLine();
					System.out.print("�ŵ��� : ");
					stockNum = Integer.parseInt(in.readLine());
					pw.println("�ŵ�:" + companyName + ":" + stockNum);
					break;
				case 4:
					System.out.print("ȸ��� : ");
					companyName = in.readLine();
					System.out.print("�ż��� : ");
					stockNum = Integer.parseInt(in.readLine());
					pw.println("�ż�:" + companyName + ":" + stockNum);
					break;
				default:
					System.err.println("1 ~ 4 ������ ���ڸ� �Է��ϼ���.");
					continue;
				}
			} catch(NumberFormatException nfe) {
				System.err.println("���ڸ� �Է��ϼ���.");
				continue;
			}
			System.out.println("1.���� ��ȸ  2.�ֽĽ��� ��ȸ  3.�ŵ�  4.�ż�");
		}
		
		pw.close();
		in.close();
		socket.close();
	}
}

class ClientThread extends Thread {
	BufferedReader br;
	
	public ClientThread(BufferedReader br) {
		this.br = br;
	}
	@Override
	public void run() {
		String str = null;
		try {
			while ((str = br.readLine()) != null) {
				System.out.println(str);
			}
		} catch (IOException e) {
			System.out.println("���� ���� ����");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}