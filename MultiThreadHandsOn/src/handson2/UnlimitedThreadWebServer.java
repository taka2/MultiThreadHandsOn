package handson2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * �N���C�A���g����̃��N�G�X�g���󂯕t���邽�тɃX���b�h�𐶐�����web�T�[�o�̗�
 */
public class UnlimitedThreadWebServer {
	private static final int MEMORY_USAGE = 1000000;
	private static final int PROCESS_TIME = 3000;

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(8888);
		while(true) {
			System.out.println("�N���C�A���g����̐ڑ���҂��󂯂܂��B");
			final Socket client = ss.accept();
			System.out.println("�N���C�A���g����ڑ�����܂����B");
			new Thread() {
				public void run() {
					try {
						handleRequest(client);
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
			System.out.println("�N���C�A���g��ؒf���܂����B");
		}
	}

	/**
	 * �N���C�A���g����̃��N�G�X�g����������
	 * @param client �N���C�A���g��Socket�I�u�W�F�N�g
	 * @throws IOException IO�G���[������
	 */
	private static void handleRequest(final Socket client) throws IOException {
		// ��t�������擾
		Date acceptDate = new Date();
		// �N���C�A���g����̃��N�G�X�g���e��ǂݍ���
		BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String buf;
		while((buf = reader.readLine()) != null) {
			System.out.println("IN: " + buf);
			if(buf.trim().length() == 0) {
				// ��s��ǂ񂾂�ǂݍ��ݏI��
				break;
			}
		}

		// �����d������
		try {
			int[] data = new int[MEMORY_USAGE];
			Thread.sleep(PROCESS_TIME);
		} catch(InterruptedException e) {
			// �������Ȃ�
		}

		// �N���C�A���g�Ƀ��X�|���X��ԋp
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.println("HTTP/1.1 200 OK");
		writer.println("Content-Type: text/plain");
		writer.println("");
		writer.println(acceptDate);
		writer.flush();
		writer.close();
		
		client.close();
	}
}
