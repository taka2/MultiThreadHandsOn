package handson2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CancellableWebServer {
	private static final int THREAD_POOL_SIZE = 100;
	private static final String STOP_URI = "/stop";

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(8888);
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		while(true) {
			System.out.println("�N���C�A���g����̐ڑ���҂��󂯂܂��B");
			final Socket client = ss.accept();
			System.out.println("�N���C�A���g����ڑ�����܂����B");
			Callable<Boolean> task = new Callable<Boolean>() {
				public Boolean call() throws IOException {
					return Boolean.valueOf(handleRequest(client));
				}
			};
			Future<Boolean> future = executor.submit(task);
			if(!future.get()) {
				// web�T�[�o�I��
				break;
			}
			System.out.println("�N���C�A���g��ؒf���܂����B");
		}

		executor.shutdown();
	}

	/**
	 * �N���C�A���g����̃��N�G�X�g����������
	 * @param client �N���C�A���g��Socket�I�u�W�F�N�g
	 * @throws IOException IO�G���[������
	 */
	private static boolean handleRequest(final Socket client) throws IOException {
		// ��t�������擾
		Date acceptDate = new Date();
		// �N���C�A���g����̃��N�G�X�g���e��ǂݍ���
		BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String buf;
		String httpRequest = null;
		boolean isNormalCommand = true;
		while((buf = reader.readLine()) != null) {
			System.out.println("IN: " + buf);
			if(httpRequest == null) {
				httpRequest = buf;
				String httpRequestURI = httpRequest.split(" ")[1];
				if(STOP_URI.equals(httpRequestURI)) {
					isNormalCommand = false;
				}
			}
			if(buf.trim().length() == 0) {
				// ��s��ǂ񂾂�ǂݍ��ݏI��
				break;
			}
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
		
		return isNormalCommand;
	}
}
