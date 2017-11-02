import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerClientThread extends Thread
{
	static List<PrintWriter> list = Collections  //���ο� Ŭ���̾�Ʈ �ö����� ����Ʈ�� ���! �ϴ°��� 3���� ����
			.synchronizedList(new ArrayList<PrintWriter>());

	Socket socket;

	PrintWriter writer;

	public PerClientThread(Socket socket)
	{
		this.socket = socket;

		try
		{
			writer = new PrintWriter(socket.getOutputStream());
            list.add(socket);
// (#3) add new client stream into list ������ �߰��ϸ� �ȴ� �Լ��̸��� add��� �޼ҵ带 ����ؼ� ����Ʈ��
          
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void run()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			while (true)
			{
				String str = reader.readLine();
				if (str == null)
				{
					break;
				}
				sendAll(str);
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		} finally
		{

// (#4) remove disconnected client from list ü���� ���� 

			try
			{
				socket.close();
			} catch (Exception ignored)
			{
			}
		}
	}

	private void sendAll(String str)
	{

		 for (PrintWriter writer : list){
			   writer.println(str);
			   writer.flush();
			  }
// (#5) broadcast arriving message to all clients  �ּ�2���̻� ä�ù��ڿ��� ������ ������ ��� ������� ����Ʈ �ݷ��Ǻ������� �ϳ��� ������� ���ڿ� ���� ��ε��ɽ�//Ʈ for������ while����
          

	}

}
