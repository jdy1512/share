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
		System.out.println("1.지갑 조회  2.주식시장 조회  3.매도  4.매수");
		while ((str = in.readLine()) != null) {
			try {
				int select = Integer.parseInt(str);
				switch (select) {
				case 1:
					pw.println("지갑 조회");
					break;
				case 2:
					pw.println("주식시장 조회");
					break;
				case 3:
					System.out.print("회사명 : ");
					companyName = in.readLine();
					System.out.print("매도량 : ");
					stockNum = Integer.parseInt(in.readLine());
					pw.println("매도:" + companyName + ":" + stockNum);
					break;
				case 4:
					System.out.print("회사명 : ");
					companyName = in.readLine();
					System.out.print("매수량 : ");
					stockNum = Integer.parseInt(in.readLine());
					pw.println("매수:" + companyName + ":" + stockNum);
					break;
				default:
					System.err.println("1 ~ 4 사이의 숫자를 입력하세요.");
					continue;
				}
			} catch(NumberFormatException nfe) {
				System.err.println("숫자를 입력하세요.");
				continue;
			}
			System.out.println("1.지갑 조회  2.주식시장 조회  3.매도  4.매수");
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
			System.out.println("게임 강제 종료");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}